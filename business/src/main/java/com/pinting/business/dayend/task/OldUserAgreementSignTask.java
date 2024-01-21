package com.pinting.business.dayend.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pinting.business.accounting.service.SignSealService;
import com.pinting.business.dao.BsFileSealRelationMapper;
import com.pinting.business.dao.BsSubAccountMapper;
import com.pinting.business.dao.BsUserSealFileMapper;
import com.pinting.business.enums.*;
import com.pinting.business.model.BsFileSealRelation;
import com.pinting.business.model.BsUserSealFile;
import com.pinting.business.model.BsUserSignSeal;
import com.pinting.business.model.vo.BsUserAgreementSignVO;
import com.pinting.business.util.Constants;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.GlobEnvUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 老云贷、老七贷投资人《委托授权协议》补签定时任务
 * Created by zousheng on 2018/4/18.
 */
@Service
public class OldUserAgreementSignTask {

    private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);
    private Logger log = LoggerFactory.getLogger(OldUserAgreementSignTask.class);

    @Autowired
    private SignSealService signSealService;
    @Autowired
    private BsFileSealRelationMapper bsFileSealRelationMapper;
    @Autowired
    private BsUserSealFileMapper bsUserSealFileMapper;
    @Autowired
    private BsSubAccountMapper bsSubAccountMapper;

    /**
     * 老云贷、老七贷投资人补签《委托授权协议》
     */
    public void execute() {
        try {
            jsClientDaoSupport.lock(RedisLockEnum.OLD_USER_AGREEMENT_SIGN.getKey());
            log.info("老云贷、老七贷投资人补签----开始");

            int pageNum = 1; // 第几批次
            int numPerPage = 100; // 一批次处理签章数量
            int signaturesNumber = 0; // 当前批次处理签章数量
            int signaturesTotal = 0; // 实际处理签章总数
            // 当查询出当前批次处理签章数量不足时，循环关闭
            do {
                int start = (pageNum <= 1) ? 0 : ((pageNum - 1) * numPerPage); // mysql的分页

                // 老云贷，老七贷提前赎回信息
                List<BsUserAgreementSignVO> userAgreementSignVOS = bsSubAccountMapper.selectOldYUNOrSevenSignedSupplementInfo(start, numPerPage);
                signaturesNumber = userAgreementSignVOS.size();
                log.info("补签处理批次->批次：" + pageNum + "，批次补签数量：" + signaturesNumber);
                pageNum++;

                for (BsUserAgreementSignVO userAgreementSign : userAgreementSignVOS) {
                    try {
                        if (agreementSign(userAgreementSign)) {
                            signaturesTotal++;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        log.error("委托协议补签失败->站岗户ID:" + userAgreementSign.getSubAccountId());
                    }
                }
            } while (numPerPage <= signaturesNumber);

            log.info("补签处理补签总数：" + signaturesTotal);
            log.info("老云贷、老七贷投资人补签----结束");
        } finally {
            jsClientDaoSupport.unlock(RedisLockEnum.OLD_USER_AGREEMENT_SIGN.getKey());
        }
    }

    /**
     * 用户协议签章入库，入redis
     *
     * @param userAgreementSign
     * @return 补签成功：true, 补签失败：false
     */
    private boolean agreementSign(BsUserAgreementSignVO userAgreementSign) {
        //协议签章
        String orderNo = String.valueOf(userAgreementSign.getSubAccountId());

        String propertySymbol = signSealService.checkPropertySymbol(userAgreementSign.getSubAccountId());
        if (!Constants.PROPERTY_SYMBOL_YUN_DAI.equals(propertySymbol) &&
                !Constants.PROPERTY_SYMBOL_7_DAI.equals(propertySymbol)) {
            log.info("补签协议投资人非云贷自主放款/七贷自主放款->站岗户ID:" + orderNo + "," + propertySymbol);
            return false;
        }
        // 签章异步处理，保存表信息：Bs_User_Seal_File,Bs_User_Sign_Seal,Bs_File_Seal_Relation
        BsUserSignSeal userSealReq = new BsUserSignSeal();
        userSealReq.setIdCard(userAgreementSign.getIdCard());
        userSealReq.setUserName(userAgreementSign.getUserName());
        userSealReq.setUserId(userAgreementSign.getId());
        BsUserSignSeal signSeal = signSealService.findUserSignSeal(userSealReq);

        // 用户没有签章信息,新增签章信息
        if (signSeal == null) {
            signSeal = userSealReq;
            signSealService.addUserSeal(signSeal);
        }

        String buyHtml = GlobEnvUtil.get("cfca.seal.buy.pdfSrcHtml") + "?investId="
                + orderNo;

        // 新增用户签章文件记录表
        BsUserSealFile sealFile = new BsUserSealFile();
        sealFile.setAgreementNo(orderNo);
        sealFile.setSrcAddress(buyHtml);
        sealFile.setSealStatus(SealStatus.UNDOWNLOAD.getCode());
        sealFile.setSealType(SealBusiness.BUY.getCode());
        sealFile.setUserId(signSeal.getUserId());
        sealFile.setUserSrc(SealPdfPathEnum.BIGANGWAN.getCode());
        sealFile.setRelativeInfo(orderNo);
        sealFile.setCreateTime(new Date());
        bsUserSealFileMapper.insertSelective(sealFile);

        // 新增签章文件与客户签章关系表
        BsFileSealRelation sealRelation = new BsFileSealRelation();
        sealRelation.setSealFileId(sealFile.getId());
        sealRelation.setCreateTime(new Date());
        sealRelation.setUpdateTime(new Date());

        // 客户签章
        sealRelation.setKeywords("甲方（委托方）：" + signSeal.getUserName());
        sealRelation.setContentType(SealType.PERSON.name());
        sealRelation.setUserSealId(signSeal.getId());
        bsFileSealRelationMapper.insertSelective(sealRelation);

        // 币港湾签章
        sealRelation.setKeywords("乙方（受托方）：杭州币港湾科技有限公司");
        sealRelation.setContentType(SealType.COMPANY.name());
        sealRelation.setUserSealId(SealPdfPathEnum.BIGANGWAN.getSealId()); // 填写币港湾签章ID
        bsFileSealRelationMapper.insertSelective(sealRelation);

        log.info(">>>签章入redis走定时-file_id:" + sealRelation.getSealFileId());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("file_id", sealRelation.getSealFileId());
        jsClientDaoSupport.rpush(Constants.SIGN_FILE_QUEUE_KEY, JSON.toJSONString(jsonObject));
        return true;
    }
}

package com.pinting.business.aspect.agreement;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pinting.business.dao.BsFileSealRelationMapper;
import com.pinting.business.dao.BsUserSealFileMapper;
import com.pinting.business.enums.SealBusiness;
import com.pinting.business.enums.SealPdfPathEnum;
import com.pinting.business.enums.SealStatus;
import com.pinting.business.model.BsFileSealRelation;
import com.pinting.business.model.BsUserSealFile;
import com.pinting.business.redis.core.model.RedisContext;
import com.pinting.business.redis.sign.model.SignRedisVO;
import com.pinting.business.util.Constants;
import com.pinting.core.redis.JedisClientDaoSupport;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;


public class AgreementSignAspect {

    private Logger log = LoggerFactory.getLogger(AgreementSignAspect.class);
    protected static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);

    @Autowired
    protected BsFileSealRelationMapper bsFileSealRelationMapper;
    @Autowired
    protected BsUserSealFileMapper bsUserSealFileMapper;

    /**
     * 新增签章文件记录表
     *
     * @param agreementNo
     * @param pdfHtml
     * @param sealBusiness
     * @param sealPdfPathEnum
     * @param orderNo
     * @param userId
     * @return
     */
    protected BsUserSealFile addBsUserSealFile(String agreementNo, String pdfHtml, SealBusiness sealBusiness, SealPdfPathEnum sealPdfPathEnum, String orderNo, Integer userId, String note) {
        BsUserSealFile sealFile = new BsUserSealFile();
        sealFile.setAgreementNo(agreementNo);
        sealFile.setSrcAddress(pdfHtml);
        sealFile.setSealStatus(SealStatus.UNDOWNLOAD.getCode());
        sealFile.setSealType(sealBusiness.getCode());
        sealFile.setUserId(userId);
        sealFile.setUserSrc(sealPdfPathEnum.getCode());
        sealFile.setRelativeInfo(orderNo);
        sealFile.setCreateTime(new Date());
        sealFile.setUpdateTime(new Date());
        sealFile.setNote(note);
        bsUserSealFileMapper.insertSelective(sealFile);
        return sealFile;
    }

    /**
     * 新增签章文件与签章用户关系表
     *
     * @param sealFileId
     * @param keywords
     * @param contentType
     * @param userSealId
     * @return
     */
    protected BsFileSealRelation addBsFileSealRelation(Integer sealFileId, String keywords, String contentType, Integer userSealId) {
        BsFileSealRelation sealRelation = new BsFileSealRelation();
        sealRelation.setSealFileId(sealFileId);
        sealRelation.setCreateTime(new Date());
        sealRelation.setUpdateTime(new Date());
        sealRelation.setKeywords(keywords);
        sealRelation.setContentType(contentType);
        sealRelation.setUserSealId(userSealId);
        bsFileSealRelationMapper.insertSelective(sealRelation);
        return sealRelation;
    }

    /**
     * 签章文件id入redis库
     *
     * @param sealFileId
     */
    protected void rpushRedisSignSealFileId(Integer sealFileId) {
        log.info(">>>签章入redis走定时-file_id:" + sealFileId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("file_id", sealFileId);
        jsClientDaoSupport.rpush(Constants.SIGN_FILE_QUEUE_KEY, JSON.toJSONString(jsonObject));
    }

    /**
     * 入多份签章文件reids队列
     *
     * @param signRedisVO
     */
    protected void rpushRedisSignVO(RedisContext signRedisVO) {
        log.info(">>>签章入redis走定时-signRedisVO:" + JSONObject.toJSONString(signRedisVO));
        jsClientDaoSupport.rpush(signRedisVO.getRedisKey(), JSONObject.toJSONString(signRedisVO));
    }
}

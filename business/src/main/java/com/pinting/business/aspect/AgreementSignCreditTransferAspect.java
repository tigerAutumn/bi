package com.pinting.business.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.service.SignSealService;
import com.pinting.business.dao.BsFileSealRelationMapper;
import com.pinting.business.dao.BsUserSealFileMapper;
import com.pinting.business.enums.SealBusiness;
import com.pinting.business.enums.SealPdfPathEnum;
import com.pinting.business.enums.SealStatus;
import com.pinting.business.enums.SealType;
import com.pinting.business.model.BsFileSealRelation;
import com.pinting.business.model.BsUser;
import com.pinting.business.model.BsUserSealFile;
import com.pinting.business.model.BsUserSignSeal;
import com.pinting.business.model.vo.BsLoanRelationTransferVO;
import com.pinting.business.service.site.RegularSiteService;
import com.pinting.business.service.site.UserService;
import com.pinting.business.util.Constants;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.GlobEnvUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * 协议签章：
 * 1. 债权转让协议签章（云贷、七贷）
 */
@Aspect
@Component
public class AgreementSignCreditTransferAspect {

    private Logger log = LoggerFactory.getLogger(AgreementSignCreditTransferAspect.class);

    private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);

    @Pointcut("execution(public * com.pinting.business.accounting.loan.service.impl.DepFixedLoanRelationshipServiceImpl.doDepFixedTransferDetail(..))" +
            "|| execution(public * com.pinting.business.accounting.loan.service.impl.DepFixedLoanRelationshipServiceImpl.doTransfer4Free(..))")
    public void agreementSignPointcut() {
    }

    @Autowired
    private SignSealService signSealService;
    @Autowired
    private BsFileSealRelationMapper bsFileSealRelationMapper;
    @Autowired
    private BsUserSealFileMapper bsUserSealFileMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private RegularSiteService regularSiteService;

    /**
     * 后处理
     * @param call
     * @param retVal
     */
    @AfterReturning(value = "agreementSignPointcut()", returning = "retVal")
    public void doAfter(JoinPoint call, Object retVal) {
        if (retVal != null && retVal instanceof Map) {
            log.info("理财人债权转让协议签章->开始");
            try {
                Map<String, Object> dataMap = (Map<String, Object>) retVal;
                Integer loanRelationId = (Integer) dataMap.get("loanRelationId");
                PartnerEnum partnerEnum = (PartnerEnum) dataMap.get("partnerEnum");
                creditTransferAgreement(loanRelationId, partnerEnum);
            } catch (Exception e) {
                e.printStackTrace();
                log.info("理财人债权转让协议签章->错误");
            }
            log.info("理财人债权转让协议签章->结束");
        }
    }

    /**
     * 生成债权转让协议加签章
     *
     * @param loanRelationId 借贷关系id 债权出让人
     * @param partnerEnum    平台标识
     */
    public void creditTransferAgreement(Integer loanRelationId, PartnerEnum partnerEnum) {

        //存管港湾产品-债权转让协议 根据借贷关系编号查询债权转让数据
        BsLoanRelationTransferVO loanRelationTransferVO = regularSiteService.queryCustodyTransferClaims(loanRelationId);

        if (loanRelationTransferVO != null) {

            // 生成债权转让协议号
            String orderNo = "";
            String pdfHtml = "";
            if (PartnerEnum.YUN_DAI_SELF.getCode().equals(partnerEnum.getCode())) {
                // 云贷自主放款
                orderNo = "26100000" + loanRelationId.toString();
                if(Constants.LN_LOAN_PARTNER_BUSINESS_FLAG_FIXED_INSTALLMENT.equals(loanRelationTransferVO.getPartnerBusinessFlag()) ||
                        Constants.LN_LOAN_PARTNER_BUSINESS_FLAG_FIXED_PRINCIPAL_INTEREST.equals(loanRelationTransferVO.getPartnerBusinessFlag())) {
                    pdfHtml = GlobEnvUtil.get("cfca.seal.yundaiClaimsStagingProductPdf.pdfSrcHtml") + "?loanRelationId=" + loanRelationId;
                }else {
                    pdfHtml = GlobEnvUtil.get("cfca.seal.yundaiClaims.pdfSrcHtml") + "?loanRelationId=" + loanRelationId;
                }
            } else {
                // 7贷自主放款
                orderNo = "27100000" + loanRelationId.toString();
                pdfHtml = GlobEnvUtil.get("cfca.seal.7daiClaims.pdfSrcHtml") + "?loanRelationId=" + loanRelationId;
            }

            // 甲方协议签章，债权出让人
            BsUser outUser = userService.findUserById(loanRelationTransferVO.getOutUserId());
            // 乙方协议签章，债权受让人
            BsUser inUser = userService.findUserById(loanRelationTransferVO.getInUserId());

            // 甲方签章异步处理，保存表信息：Bs_User_Seal_File,Bs_User_Sign_Seal,Bs_File_Seal_Relation
            BsUserSignSeal outUserSealReq = new BsUserSignSeal();
            outUserSealReq.setIdCard(outUser.getIdCard());
            outUserSealReq.setUserName(outUser.getUserName());
            outUserSealReq.setUserId(outUser.getId());
            BsUserSignSeal outSignSeal = signSealService.findUserSignSeal(outUserSealReq);

            // 用户没有签章信息,新增签章信息
            if (outSignSeal == null) {
                outSignSeal = outUserSealReq;
                signSealService.addUserSeal(outSignSeal);
            }

            // 乙方签章异步处理，保存表信息：Bs_User_Seal_File,Bs_User_Sign_Seal,Bs_File_Seal_Relation
            BsUserSignSeal inUserSealReq = new BsUserSignSeal();
            inUserSealReq.setIdCard(inUser.getIdCard());
            inUserSealReq.setUserName(inUser.getUserName());
            inUserSealReq.setUserId(inUser.getId());
            BsUserSignSeal inSignSeal = signSealService.findUserSignSeal(inUserSealReq);

            // 用户没有签章信息,新增签章信息
            if (inSignSeal == null) {
                inSignSeal = inUserSealReq;
                signSealService.addUserSeal(inSignSeal);
            }

            // 甲方用户签章文件记录表
            BsUserSealFile sealFile = new BsUserSealFile();
            sealFile.setAgreementNo(orderNo);
            sealFile.setSrcAddress(pdfHtml);
            sealFile.setSealStatus(SealStatus.UNDOWNLOAD.getCode());
            sealFile.setSealType(SealBusiness.BGW_CLAIMS_AGREEMENT.getCode());
            sealFile.setUserId(outSignSeal.getUserId());
            sealFile.setUserSrc(SealPdfPathEnum.BIGANGWAN.getCode());
            sealFile.setRelativeInfo(loanRelationId.toString());
            sealFile.setCreateTime(new Date());
            bsUserSealFileMapper.insertSelective(sealFile);

            // 新增签章文件与客户签章关系表
            BsFileSealRelation sealRelation = new BsFileSealRelation();
            sealRelation.setSealFileId(sealFile.getId());
            sealRelation.setCreateTime(new Date());
            sealRelation.setUpdateTime(new Date());

            // 甲方客户签章
            sealRelation.setKeywords("甲方（签字）：" + outSignSeal.getUserName());
            sealRelation.setContentType(SealType.PERSON.name());
            sealRelation.setUserSealId(outSignSeal.getId());
            bsFileSealRelationMapper.insertSelective(sealRelation);

            // 乙方客户签章
            sealRelation.setKeywords("乙方（签字）：" + inSignSeal.getUserName());
            sealRelation.setContentType(SealType.PERSON.name());
            sealRelation.setUserSealId(inSignSeal.getId());
            bsFileSealRelationMapper.insertSelective(sealRelation);

            // 币港湾签章
            sealRelation.setKeywords("丙方（盖章）：杭州币港湾科技有限公司");
            sealRelation.setContentType(SealType.COMPANY.name());
            sealRelation.setUserSealId(SealPdfPathEnum.BIGANGWAN.getSealId()); // 填写币港湾签章ID
            bsFileSealRelationMapper.insertSelective(sealRelation);

            log.info(">>>理财人债权转让协议签章入redis走定时-file_id:" + sealRelation.getSealFileId());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("file_id", sealRelation.getSealFileId());
            jsClientDaoSupport.rpush(Constants.SIGN_FILE_QUEUE_KEY, JSON.toJSONString(jsonObject));
        } else {
            log.info("理财人债权转让协议签章没有数据：loanRelationId：" + loanRelationId);
        }
    }
}

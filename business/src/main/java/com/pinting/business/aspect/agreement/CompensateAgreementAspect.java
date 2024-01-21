package com.pinting.business.aspect.agreement;

import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.coreflow.compensate.helper.impl.CompensateAgreementSignHelperImpl;
import com.pinting.business.coreflow.compensate.model.vo.AgreementSignInfoVO;
import com.pinting.business.coreflow.compensate.util.ConstantsForFields;
import com.pinting.business.coreflow.core.model.FlowContext;
import com.pinting.business.dao.LnUserMapper;
import com.pinting.business.enums.SealBusiness;
import com.pinting.business.enums.SealPdfPathEnum;
import com.pinting.business.enums.SealType;
import com.pinting.business.model.*;
import com.pinting.business.redis.core.model.RedisContext;
import com.pinting.business.redis.sign.model.CompensateSignRedisVO;
import com.pinting.business.util.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 1. 分期产品代偿协议签章（确认函、通知书）
 *
 * @author zousheng
 * @date 2018年7月4日 上午10:16:08
 */
@Aspect
@Component
public class CompensateAgreementAspect extends AgreementSignAspect implements ConstantsForFields {

    private Logger log = LoggerFactory.getLogger(CompensateAgreementAspect.class);

    @Pointcut("execution(public * com.pinting.business.coreflow.compensate.helper.impl.CompensateAgreementHelperImpl.agreementSignGenerate(..)) " +
            "|| execution(public * com.pinting.business.coreflow.compensate.helper.impl.CompensateStagesAgreementHelperImpl.agreementSignGenerate(..))")
    public void agreementSignGenerate() {
    }

    @Autowired
    private LnUserMapper lnUserMapper;

    @AfterReturning(value = "agreementSignGenerate()")
    public void agreementSignGenerateDoAfter(JoinPoint jp) {
        FlowContext flowContext = (FlowContext) jp.getArgs()[0];
        LnCompensateDetail lnCompensateDetail = (LnCompensateDetail) flowContext.getExtendData(LnCompensateDetail.class.getSimpleName());
        Integer lnLoanId = (Integer) flowContext.getExtendData(LN_LOAN_ID);
        PartnerEnum partnerEnum = flowContext.getPartnerEnum();
        AgreementSignInfoVO receiptConfirmAgreement = (AgreementSignInfoVO) flowContext.getExtendData(AGREEMENT_RECEIPTCONFIRM);
        AgreementSignInfoVO creditorNoticeAgreement = (AgreementSignInfoVO) flowContext.getExtendData(AGREEMENT_CREDITORNOTICE);
        Integer lnUserId = getLnUserId(lnCompensateDetail.getPartnerUserId(), partnerEnum);

        CompensateSignRedisVO receiptConfirmSignRedisVO = receiptConfirmSignDoAfter(receiptConfirmAgreement, lnLoanId);
        CompensateSignRedisVO creditorNoticeSignRedisVO = creditorNoticeSignDoAfter(creditorNoticeAgreement, lnLoanId, lnUserId);

        RedisContext signRedisVO = new RedisContext(Constants.MORE_SIGN_FILE_QUEUE_KEY);
        signRedisVO.getAfterProcess().add(CompensateAgreementSignHelperImpl.class.getSimpleName()); // 设置签章服务后处理
        signRedisVO.getRedisVOList().add(receiptConfirmSignRedisVO);
        signRedisVO.getRedisVOList().add(creditorNoticeSignRedisVO);
        rpushRedisSignVO(signRedisVO);
        log.info(">>>代偿签章协议切面结束<<<");
    }

    /**
     * 代偿签章协议确认函入队列
     *
     * @param receiptConfirmAgreement
     * @param lnLoanId
     * @return
     */
    private CompensateSignRedisVO receiptConfirmSignDoAfter(AgreementSignInfoVO receiptConfirmAgreement, Integer lnLoanId) {
        log.info(">>>代偿签章协议切面开始生成收款确认函<<<");
        log.info("收款确认函担保url>>>: " + receiptConfirmAgreement.getAgreementHtml());

        // 1.新增签章文件记录表
        BsUserSealFile sealFile = addBsUserSealFile(receiptConfirmAgreement.getAgreementNo(), receiptConfirmAgreement.getAgreementHtml(),
                SealBusiness.DEBT_TRANS_CONFIRM, SealPdfPathEnum.YUN_DAI_SELF, lnLoanId.toString(), null, null);
        // 2.新增签章文件与签章用户关系表
        BsFileSealRelation sealRelation = addBsFileSealRelation(sealFile.getId(), "杭州币港湾科技有限公司（签章）", SealType.COMPANY.name(), SealPdfPathEnum.BIGANGWAN.getSealId());
        // 3.签章文件ID入redis
        CompensateSignRedisVO receiptConfirmSignRedisVO = new CompensateSignRedisVO();
        receiptConfirmSignRedisVO.setSignFileId(sealRelation.getSealFileId());
        receiptConfirmSignRedisVO.setLnLoanId(lnLoanId);
        receiptConfirmSignRedisVO.setAgreementPdfName(receiptConfirmAgreement.getAgreementPdfName());
        return receiptConfirmSignRedisVO;
    }

    /**
     * 代偿签章协议通知书入队列
     *
     * @param creditorNoticeAgreement
     * @param lnLoanId
     * @param lnUserId
     * @return
     */
    private CompensateSignRedisVO creditorNoticeSignDoAfter(AgreementSignInfoVO creditorNoticeAgreement, Integer lnLoanId, Integer lnUserId) {
        log.info(">>>代偿签章协议切面开始生成债权转让通知书<<<");
        log.info("债权转让通知书url>>>: " + creditorNoticeAgreement.getAgreementHtml());

        // 1.新增签章文件记录表
        BsUserSealFile sealFileAgreement = addBsUserSealFile(creditorNoticeAgreement.getAgreementNo(), creditorNoticeAgreement.getAgreementHtml(),
                SealBusiness.DEBT_TRANS_NOTICES, SealPdfPathEnum.YUN_DAI_SELF, lnLoanId.toString(), lnUserId, null);
        // 2.新增签章文件与签章用户关系表
        BsFileSealRelation sealRelationAgreement = addBsFileSealRelation(sealFileAgreement.getId(), "服务方（签章）：杭州币港湾科技有限公司", SealType.COMPANY.name(), SealPdfPathEnum.BIGANGWAN.getSealId());
        // 3.签章文件ID入redis
        CompensateSignRedisVO creditorNoticeSignRedisVO = new CompensateSignRedisVO();
        creditorNoticeSignRedisVO.setSignFileId(sealRelationAgreement.getSealFileId());
        creditorNoticeSignRedisVO.setLnLoanId(lnLoanId);
        creditorNoticeSignRedisVO.setAgreementPdfName(creditorNoticeAgreement.getAgreementPdfName());
        return creditorNoticeSignRedisVO;
    }

    /**
     * 查询借款用户ID
     *
     * @param partnerUserId
     * @param partnerEnum
     * @return
     */
    private Integer getLnUserId(String partnerUserId, PartnerEnum partnerEnum) {
        LnUserExample lnUserExample = new LnUserExample();
        lnUserExample.createCriteria().andPartnerUserIdEqualTo(partnerUserId)
                .andPartnerCodeEqualTo(partnerEnum.getCode());
        List<LnUser> lnUserList = lnUserMapper.selectByExample(lnUserExample);
        if (CollectionUtils.isNotEmpty(lnUserList)) {
            return lnUserList.get(0).getId();
        }
        return null;
    }
}

package com.pinting.business.coreflow.compensate.helper.impl;

import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.coreflow.core.enums.AgreeTypeEnum;
import com.pinting.core.util.GlobEnvUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * (分期产品 + 定期产品)新代偿协议帮助类
 */
@Service("compensateStagesAgreementHelperImpl")
public class CompensateStagesAgreementHelperImpl extends CompensateAgreementHelperImpl {

    private final Logger logger = LoggerFactory.getLogger(CompensateStagesAgreementHelperImpl.class);

    @Override
    protected String getReceiptConfirmHtml(String partnerLoanId, String orderNo, PartnerEnum partnerEnum) {
        String receiptConfirmHtml = GlobEnvUtil.get("cfca.seal.LetterOfCreditConfirmation.pdfSrcHtml") + "?loanId=" + partnerLoanId + "&orderNo=" + orderNo + "&partnerEnum=" + partnerEnum.getCode()
                + "&agreementType=" + AgreeTypeEnum.HF_YUNDAI_RECEIPTCONFIRMAGREEMENT_STAGE.getCode();
        logger.info("收款确认函债转url>>>: " + receiptConfirmHtml);
        return receiptConfirmHtml;
    }

    @Override
    protected String getCreditorNoticeHtml(String partnerLoanId, String orderNo, PartnerEnum partnerEnum) {
        String creditorNoticeHtml = GlobEnvUtil.get("cfca.seal.lateRepayCreditorNotice.pdfSrcHtml") + "?loanId=" + partnerLoanId + "&orderNo=" + orderNo + "&partnerEnum=" + partnerEnum.getCode()
                + "&agreementType=" + AgreeTypeEnum.HF_YUNDAI_CREDITORNOTICEAGREEMENT_STAGE.getCode();
        logger.info("债权转让通知书url>>>: " + creditorNoticeHtml);
        return creditorNoticeHtml;
    }
}
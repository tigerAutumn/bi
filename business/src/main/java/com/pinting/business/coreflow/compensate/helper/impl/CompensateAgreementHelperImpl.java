package com.pinting.business.coreflow.compensate.helper.impl;

import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.coreflow.compensate.enums.CompensateStatusEnum;
import com.pinting.business.coreflow.compensate.helper.CompensateAgreementHelper;
import com.pinting.business.coreflow.compensate.model.vo.AgreementSignInfoVO;
import com.pinting.business.coreflow.compensate.util.ConstantsForFields;
import com.pinting.business.coreflow.core.enums.AgreeTypeEnum;
import com.pinting.business.coreflow.core.model.FlowContext;
import com.pinting.business.dao.LnCompensateAgreementAddressMapper;
import com.pinting.business.dao.LnCompensateMapper;
import com.pinting.business.enums.SealBusiness;
import com.pinting.business.model.LnCompensate;
import com.pinting.business.model.LnCompensateAgreementAddress;
import com.pinting.business.model.LnCompensateDetail;
import com.pinting.business.util.Constants;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.GlobEnvUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 代偿协议帮助类
 */
@Service("compensateAgreementHelperImpl")
public class CompensateAgreementHelperImpl implements CompensateAgreementHelper, ConstantsForFields {

    private final Logger logger = LoggerFactory.getLogger(CompensateAgreementHelperImpl.class);
    @Autowired
    private LnCompensateAgreementAddressMapper lnCompensateAgreementAddressMapper;
    @Autowired
    private LnCompensateMapper lnCompensateMapper;

    @Override
    public void agreementSignGenerate(FlowContext flowContext) {
        LnCompensateDetail lnCompensateDetail = (LnCompensateDetail) flowContext.getExtendData(LnCompensateDetail.class.getSimpleName());
        Integer lnLoanId = (Integer) flowContext.getExtendData(LN_LOAN_ID);
        PartnerEnum partnerEnum = flowContext.getPartnerEnum();

        LnCompensate lnCompensate = lnCompensateMapper.selectByPrimaryKey(lnCompensateDetail.getCompensateId());
        flowContext.setExtendData(ORDER_NO, lnCompensate.getOrderNo());

        flowContext.setExtendData(AGREEMENT_RECEIPTCONFIRM, receiptConfirm2Pdf(lnLoanId, lnCompensate.getOrderNo(), lnCompensateDetail, partnerEnum));
        flowContext.setExtendData(AGREEMENT_CREDITORNOTICE, creditorNotice2Pdf(lnLoanId, lnCompensate.getOrderNo(), lnCompensateDetail, partnerEnum));
    }

    /**
     * 代偿确认函
     *
     * @param orderNo
     * @param lnCompensateDetail
     * @param partnerEnum
     */
    private AgreementSignInfoVO receiptConfirm2Pdf(Integer lnLoanId, String orderNo, LnCompensateDetail lnCompensateDetail, PartnerEnum partnerEnum) {

        AgreementSignInfoVO agreementSignInfoVO = new AgreementSignInfoVO();
        agreementSignInfoVO.setAgreementNo(getReceiptConfirmAgreementNo());
        agreementSignInfoVO.setAgreementHtml(getReceiptConfirmHtml(lnCompensateDetail.getPartnerLoanId(), orderNo, partnerEnum));
        agreementSignInfoVO.setAgreementPdfName(getReceiptConfirmPdfName(lnLoanId));

        // 收款确认函（债转）
        List<LnCompensateAgreementAddress> debtTransConfirmList = lnCompensateAgreementAddressMapper.selectCompensateAgreementList(lnCompensateDetail.getCompensateId(),
                lnCompensateDetail.getPartnerLoanId(), SealBusiness.DEBT_TRANS_CONFIRM.getCode());
        if (CollectionUtils.isEmpty(debtTransConfirmList)) {
            LnCompensateAgreementAddress lnCompensateAgreementAddress = new LnCompensateAgreementAddress();
            lnCompensateAgreementAddress.setPartnerCode(partnerEnum.getCode());
            lnCompensateAgreementAddress.setOrderNo(orderNo);
            lnCompensateAgreementAddress.setPartnerLoanId(lnCompensateDetail.getPartnerLoanId());
            lnCompensateAgreementAddress.setDownloadNum(0);
            lnCompensateAgreementAddress.setIsOpen(Constants.COMPENSATE_AGREEMENT_IS_OPEN_OPEN);
            lnCompensateAgreementAddress.setInformStatus(CompensateStatusEnum.NOTICE_STATUS_INIT.getCode());
            lnCompensateAgreementAddress.setCreateTime(new Date());
            lnCompensateAgreementAddress.setUpdateTime(new Date());
            lnCompensateAgreementAddress.setAgreementType(SealBusiness.DEBT_TRANS_CONFIRM.getCode());
            lnCompensateAgreementAddressMapper.insertSelective(lnCompensateAgreementAddress);
        }
        return agreementSignInfoVO;
    }

    private String getReceiptConfirmPdfName(Integer lnLoanId) {
        return "debtTransConfirm-" + lnLoanId + "-" + DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmssSSS");
    }

    protected String getReceiptConfirmHtml(String partnerLoanId, String orderNo, PartnerEnum partnerEnum) {
        String receiptConfirmHtml = GlobEnvUtil.get("cfca.seal.LetterOfCreditConfirmation.pdfSrcHtml") + "?loanId=" + partnerLoanId + "&orderNo=" + orderNo + "&partnerEnum=" + partnerEnum.getCode()
                + "&agreementType=" + AgreeTypeEnum.HF_YUNDAI_RECEIPTCONFIRMAGREEMENT.getCode();
        logger.info("收款确认函债转url>>>: " + receiptConfirmHtml);
        return receiptConfirmHtml;
    }

    private String getReceiptConfirmAgreementNo() {
        //收款确认函债转协议编号 SKZZ yyyyMMddHHmmssSSS 3位随机数
        int randomInt = (int) ((Math.random() * 9 + 1) * 100);
        String randomNumber = String.valueOf(randomInt);
        String agreementNo = "SKZZ" + DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmssSSS") + randomNumber;
        return agreementNo;
    }

    /**
     * 代偿通知书
     *
     * @param lnLoanId
     * @param orderNo
     * @param lnCompensateDetail
     * @param partnerEnum
     */
    private AgreementSignInfoVO creditorNotice2Pdf(Integer lnLoanId, String orderNo, LnCompensateDetail lnCompensateDetail, PartnerEnum partnerEnum) {

        AgreementSignInfoVO agreementSignInfoVO = new AgreementSignInfoVO();
        agreementSignInfoVO.setAgreementNo(getCreditorNoticeAgreementNo());
        agreementSignInfoVO.setAgreementHtml(getCreditorNoticeHtml(lnCompensateDetail.getPartnerLoanId(), orderNo, partnerEnum));
        agreementSignInfoVO.setAgreementPdfName(getCreditorNoticePdfName(lnLoanId));

        // 债权转让通知书
        List<LnCompensateAgreementAddress> debtTransNoticesList = lnCompensateAgreementAddressMapper.selectCompensateAgreementList(lnCompensateDetail.getCompensateId(),
                lnCompensateDetail.getPartnerLoanId(), SealBusiness.DEBT_TRANS_NOTICES.getCode());
        if (CollectionUtils.isEmpty(debtTransNoticesList)) {
            LnCompensateAgreementAddress lnCompensateAgreementAddress = new LnCompensateAgreementAddress();
            lnCompensateAgreementAddress.setPartnerCode(partnerEnum.getCode());
            lnCompensateAgreementAddress.setOrderNo(orderNo);
            lnCompensateAgreementAddress.setPartnerLoanId(lnCompensateDetail.getPartnerLoanId());
            lnCompensateAgreementAddress.setDownloadNum(0);
            lnCompensateAgreementAddress.setIsOpen(Constants.COMPENSATE_AGREEMENT_IS_OPEN_OPEN);
            lnCompensateAgreementAddress.setInformStatus(CompensateStatusEnum.NOTICE_STATUS_INIT.getCode());
            lnCompensateAgreementAddress.setCreateTime(new Date());
            lnCompensateAgreementAddress.setUpdateTime(new Date());
            lnCompensateAgreementAddress.setAgreementType(SealBusiness.DEBT_TRANS_NOTICES.getCode());
            lnCompensateAgreementAddressMapper.insertSelective(lnCompensateAgreementAddress);
        }
        return agreementSignInfoVO;
    }

    private String getCreditorNoticePdfName(Integer lnLoanId) {
        return "debtTransNotices-" + lnLoanId + "-" + DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmssSSS");
    }

    protected String getCreditorNoticeHtml(String partnerLoanId, String orderNo, PartnerEnum partnerEnum) {
        String creditorNoticeHtml = GlobEnvUtil.get("cfca.seal.lateRepayCreditorNotice.pdfSrcHtml") + "?loanId=" + partnerLoanId + "&orderNo=" + orderNo + "&partnerEnum=" + partnerEnum.getCode()
                + "&agreementType=" + AgreeTypeEnum.HF_YUNDAI_CREDITORNOTICEAGREEMENT.getCode();
        logger.info("债权转让通知书url>>>: " + creditorNoticeHtml);
        return creditorNoticeHtml;
    }

    private String getCreditorNoticeAgreementNo() {
        //债权转让通知书 ZZTZS yyyyMMddHHmmssSSS 3位随机数
        int randomInt = (int) ((Math.random() * 9 + 1) * 100);
        String randomNumber = String.valueOf(randomInt);
        String agreementNo = "ZZTZS" + DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmssSSS") + randomNumber;
        return agreementNo;
    }
}
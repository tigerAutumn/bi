package com.pinting.business.service.loan.impl;

import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.loan.service.DepFixedRepayPaymentService;
import com.pinting.business.coreflow.core.enums.BusinessTypeEnum;
import com.pinting.business.dao.*;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.enums.SealBusiness;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.*;
import com.pinting.business.model.vo.BsUserCompensateVO;
import com.pinting.business.model.vo.CompensateTransfersPdfVO;
import com.pinting.business.model.vo.LnCompensateRelationVO;
import com.pinting.business.service.loan.LateRepayAgreementService;
import com.pinting.business.service.site.BsLoanRelationShipService;
import com.pinting.business.util.Constants;
import com.pinting.business.util.ITextPdfUtil;
import com.pinting.core.util.*;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_DafyLoanNotice_AgreementNotice;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_DafyLoanNotice_AgreementNotice;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_DafyLoan_AgreementNotice;
import com.pinting.gateway.hessian.message.dafy.G2BResMsg_DafyLoan_AgreementNotice;
import com.pinting.gateway.hessian.message.dafy.model.Agreements;
import com.pinting.gateway.hessian.message.dafy.model.DebtTransferInfo;
import com.pinting.gateway.hessian.message.dafy.model.DebtTransfers;
import com.pinting.gateway.hessian.message.loan7.B2GReqMsg_DepLoan7Notice_AgreementNotice;
import com.pinting.gateway.hessian.message.loan7.B2GResMsg_DepLoan7Notice_AgreementNotice;
import com.pinting.gateway.hessian.message.loan7.G2BReqMsg_DepLoan7_AgreementNotice;
import com.pinting.gateway.hessian.message.loan7.G2BResMsg_DepLoan7_AgreementNotice;
import com.pinting.gateway.out.service.loan.DafyNoticeService;
import com.pinting.gateway.out.service.loan7.DepLoan7NoticeService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 代偿协议相关服务（云贷、7贷）
 * 代偿协议生成、通知、查询
 * Created by shh on 2016/12/23 11:36.
 */
@Service
public class LateRepayAgreementServiceImpl implements LateRepayAgreementService {

    @Autowired
    private LnLoanMapper lnLoanMapper;
    @Autowired
    private LnUserMapper lnUserMapper;
    @Autowired
    private LnCompensateDetailMapper lnCompensateDetailMapper;
    @Autowired
    private BsLoanRelationShipService bsLoanRelationShipService;
    @Autowired
    private DafyNoticeService dafyNoticeService;
    @Autowired
    private LnCompensateAgreementAddressMapper lnCompensateAgreementAddressMapper;
    @Autowired
    private DepFixedRepayPaymentService depFixedRepayPaymentService;
    @Autowired
    private LnRepayScheduleMapper lnRepayScheduleMapper;
    @Autowired
    private LnCompensateRelationMapper lnCompensateRelationMapper;
    @Autowired
    private DepLoan7NoticeService depLoan7NoticeService;
    @Autowired
    private LnDepositionRepayScheduleMapper lnDepositionRepayScheduleMapper;

    private Logger log = LoggerFactory.getLogger(LateRepayAgreementServiceImpl.class);

    /**
     * 云贷查询代偿协议地址
     */
    @Override
    public void findAgreementUrls(G2BReqMsg_DafyLoan_AgreementNotice req, G2BResMsg_DafyLoan_AgreementNotice res) {

        // 查询有本金代偿成功的明细列表
        LnCompensateDetailExample lnCompensateDetailExample = new LnCompensateDetailExample();
        lnCompensateDetailExample.createCriteria().andPartnerLoanIdEqualTo(req.getLoanId())
                .andStatusEqualTo(Constants.COMPENSATE_REPAYS_STATUS_SUCC)
                .andPrincipalGreaterThan(0d);
        lnCompensateDetailExample.setOrderByClause("repay_serial DESC");
        List<LnCompensateDetail> lnCompensateDetailList = lnCompensateDetailMapper.selectByExample(lnCompensateDetailExample);
        if (CollectionUtils.isEmpty(lnCompensateDetailList)) {
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "本金代偿明细数据不存在");
        }

        // 取最后一笔代偿的明细信息
        LnCompensateDetail lnCompensateDetail = lnCompensateDetailList.get(0);
        if (!Constants.AGREEMENT_GRNERATE_STATUS_SUCC.equals(lnCompensateDetail.getAgreementGenerateStatus())) {
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "代偿签章协议未生成");
        }

        // 收款确认函（债转）
        List<LnCompensateAgreementAddress> debtTransConfirmList =
                lnCompensateAgreementAddressMapper.selectCompensateAgreementList(lnCompensateDetail.getCompensateId(),
                        lnCompensateDetail.getPartnerLoanId(), SealBusiness.DEBT_TRANS_CONFIRM.getCode());
        if (CollectionUtils.isEmpty(debtTransConfirmList)) {
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "代偿协议地址(债转收款确认函)不存在");
        }
        LnCompensateAgreementAddress debtTransConfirm = debtTransConfirmList.get(0);
        if (StringUtils.isBlank(debtTransConfirm.getDownloadUrl())) {
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "代偿协议文件(债转收款确认函)不存在");
        }
        File file = new File(GlobEnvUtil.get("sign.task.pdfPath") + "/" + debtTransConfirm.getDownloadUrl());
        if (!file.exists()) {
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "代偿协议文件(债转收款确认函)不存在");
        }

        // 债权转让通知书
        List<LnCompensateAgreementAddress> debtTransNoticesList =
                lnCompensateAgreementAddressMapper.selectCompensateAgreementList(lnCompensateDetail.getCompensateId(),
                        lnCompensateDetail.getPartnerLoanId(), SealBusiness.DEBT_TRANS_NOTICES.getCode());
        if (CollectionUtils.isEmpty(debtTransNoticesList)) {
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "代偿协议地址(债权转让通知书)不存在");
        }
        LnCompensateAgreementAddress debtTransNotices = debtTransNoticesList.get(0);
        if (StringUtils.isBlank(debtTransNotices.getDownloadUrl())) {
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "代偿协议文件(债权转让通知书)不存在");
        }
        File file2 = new File(GlobEnvUtil.get("sign.task.pdfPath") + "/" + debtTransNotices.getDownloadUrl());
        if (!file2.exists()) {
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "代偿协议文件(债权转让通知书)不存在");
        }

        //获取借款信息
        LnLoanExample example = new LnLoanExample();
        example.createCriteria().andPartnerLoanIdEqualTo(req.getLoanId());
        List<LnLoan> lnLoanList = lnLoanMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(lnLoanList)) {
            throw new PTMessageException(PTMessageEnum.LOAN_DATA_NOT_FOUND, "云贷协议下载地址查询时借款信息未找到");
        }
        LnLoan lnLoan = lnLoanList.get(0);
        //获取借款用户信息
        LnUser lnUser = lnUserMapper.selectByPrimaryKey(lnLoan.getLnUserId());

        // 查询合作方代偿人
        BsUserCompensateVO userCompensateVO = depFixedRepayPaymentService.compensaterInfo(lnLoan.getLoanTime(), PartnerEnum.YUN_DAI_SELF.getCode());

        // 根据借款ID查询本金代偿对应的数据列表
        List<LnCompensateRelationVO> compensateRelationVOList = lnCompensateRelationMapper.selectRelationIdListByLoanId(lnLoan.getId());
        if (CollectionUtils.isEmpty(compensateRelationVOList)) {
            throw new PTMessageException(PTMessageEnum.DAFY_LATE_REPAY_AGREEMENT_NOT_EXIST, "本金代偿数据不存在");
        }
        // 查询借款对应的代偿本金数据列表
        List<CompensateTransfersPdfVO> transfersPdfVOList = lnDepositionRepayScheduleMapper.selectCompensateTransfer4StageList(lnLoan.getId());
        if (CollectionUtils.isEmpty(transfersPdfVOList)) {
            throw new PTMessageException(PTMessageEnum.DAFY_LATE_REPAY_AGREEMENT_NOT_EXIST, "本金代偿数据不存在");
        }

        List<Agreements> agreeList = new ArrayList<>();
        for (CompensateTransfersPdfVO transfersPdfVO : transfersPdfVOList) {
            Agreements agreement = new Agreements();
            agreement.setLoanId(lnLoan.getPartnerLoanId());
            agreement.setBorrowerName(lnUser.getUserName());
            agreement.setBorrowerIdCard(lnUser.getIdCard());
            if (BusinessTypeEnum.REPAY_ANY_TIME.getCode().equals(lnLoan.getPartnerBusinessFlag())) {
                // 定期产品代偿默认1期
                agreement.setPeroid(1);
            } else {
                agreement.setPeroid(transfersPdfVO.getSerialId());
            }
            agreement.setLoanServiceFee(MoneyUtil.multiply(transfersPdfVO.getLoanServiceFee(), 100).longValue());
            //收款确认函（债转）下载地址赋值
            agreement.setDebtTransConfirmUrl(debtTransConfirm.getDownloadUrl());
            //债权转让通知书下载地址赋值
            agreement.setDebtTransNoticesUrl(debtTransNotices.getDownloadUrl());
            // 设置代偿债权转让信息
            agreement.setDebtTransferInfo(queryTransferInfo(transfersPdfVO.getSerialId(), compensateRelationVOList, userCompensateVO, lnLoan));
            agreeList.add(agreement);
        }
        res.setAgreements(agreeList);
    }

    /**
     * 组装每期代偿债权转让信息
     *
     * @param serialId
     * @param compensateRelationVOList
     * @param userCompensateVO
     * @return
     */
    private List<DebtTransferInfo> queryTransferInfo(Integer serialId, List<LnCompensateRelationVO> compensateRelationVOList, BsUserCompensateVO userCompensateVO, LnLoan lnLoan) {
        List<DebtTransferInfo> transferList = new ArrayList<>();
        for (LnCompensateRelationVO lnCompensateRelationVO : compensateRelationVOList) {
            if (serialId != null && serialId.equals(lnCompensateRelationVO.getRepaySerial())) {
                DebtTransferInfo transferVO = new DebtTransferInfo();
                //债权出让人姓名
                transferVO.setOutUserName(getBlurName(lnCompensateRelationVO.getBsUserName()));
                //债权出让人身份证号
                transferVO.setOutIdCard(getBlurIdNo(lnCompensateRelationVO.getBsIdCard()));

                if (userCompensateVO != null) {
                    //债权受让人姓名
                    transferVO.setInUserName(getBlurName(userCompensateVO.getUserName()));
                    //债权受让人身份证号
                    transferVO.setInIdCard(getBlurIdNo(userCompensateVO.getIdCard()));
                }
                //债权转让金额 Double转成长整形
                transferVO.setTransAmount(MoneyUtil.multiply(lnCompensateRelationVO.getAmount(), 100).longValue());
                if (BusinessTypeEnum.REPAY_ANY_TIME.getCode().equals(lnLoan.getPartnerBusinessFlag())) {
                    // 定期产品代偿默认1期
                    transferVO.setPeroid(1);
                } else {
                    // 代偿期数
                    transferVO.setPeroid(lnCompensateRelationVO.getRepaySerial());
                }
                // 代偿转让时间
                transferVO.setTransTime(lnCompensateRelationVO.getCreateTime());
                transferVO.setNote(""); //备注
                transferList.add(transferVO);
            }
        }
        return transferList;
    }

    /**
     * 7贷查询代偿协议地址
     */
    @Override
    public void findSevenAgreementUrls(G2BReqMsg_DepLoan7_AgreementNotice req, G2BResMsg_DepLoan7_AgreementNotice res) {
        //获取借款信息
        LnLoanExample example = new LnLoanExample();
        example.createCriteria().andPartnerLoanIdEqualTo(req.getLoanId());
        List<LnLoan> lnLoanList = lnLoanMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(lnLoanList)) {
            throw new PTMessageException(PTMessageEnum.LOAN_DATA_NOT_FOUND, "7贷协议下载地址查询时借款信息未找到");
        }
        LnLoan lnLoan = lnLoanList.get(0);
        //获取借款用户信息
        LnUser lnUser = lnUserMapper.selectByPrimaryKey(lnLoan.getLnUserId());

        res.setLoanId(req.getLoanId());
        res.setBorrowerName(lnUser.getUserName());
        res.setBorrowerIdCard(lnUser.getIdCard());
        //收款确认函（服务费）下载地址赋值
        List<String> file1AddrList = bsLoanRelationShipService.queryCompenAgreeAddressList(PartnerEnum.SEVEN_DAI_SELF.getCode(),
                SealBusiness.SERVICE_FEE_CONFIRM.getCode(), req.getLoanId());
        if (CollectionUtils.isEmpty(file1AddrList)) {
            throw new PTMessageException(PTMessageEnum.DEPLOAN7_LATE_REPAY_AGREEMENT_NOT_EXIST, "7贷代偿协议地址(服务费收款确认函)不存在");
        }
        res.setServiceFeeConfirmUrl(file1AddrList.get(0));
        //收款确认函（债转）下载地址赋值
        List<String> file2AddrList = bsLoanRelationShipService.queryCompenAgreeAddressList(PartnerEnum.SEVEN_DAI_SELF.getCode(),
                SealBusiness.DEBT_TRANS_CONFIRM.getCode(), req.getLoanId());
        if (CollectionUtils.isEmpty(file2AddrList)) {
            throw new PTMessageException(PTMessageEnum.DEPLOAN7_LATE_REPAY_AGREEMENT_NOT_EXIST, "7贷偿协议地址(债转收款确认函)不存在");
        }
        res.setDebtTransConfirmUrl(file2AddrList.get(0));
        //债权转让通知书下载地址赋值
        List<String> file3AddrList = bsLoanRelationShipService.queryCompenAgreeAddressList(PartnerEnum.SEVEN_DAI_SELF.getCode(),
                SealBusiness.DEBT_TRANS_NOTICES.getCode(), req.getLoanId());
        if (CollectionUtils.isEmpty(file3AddrList)) {
            throw new PTMessageException(PTMessageEnum.DEPLOAN7_LATE_REPAY_AGREEMENT_NOT_EXIST, "7贷代偿协议地址(债权转让通知书)不存在");
        }
        res.setDebtTransNoticesUrl(file3AddrList.get(0));
        //债权转让信息赋值
        //获取计划还款最后一期合作方还款编号
        String partnerRepayId = lnRepayScheduleMapper.selectLastPeriodPartnerRepayId(lnLoan.getId());
        if (StringUtil.isEmpty(partnerRepayId)) {
            throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND, "7贷获取计划还款最后一期合作方还款编号为空");
        }
        List<DebtTransferInfo> debtTransferInfoList = bsLoanRelationShipService.queryTransferInfo(req.getLoanId());
        res.setDebtTransferInfo(debtTransferInfoList);
        //债权转让协议下载地址列表赋值
        List<String> fileAddrList = bsLoanRelationShipService.queryCompenAgreeAddressList(PartnerEnum.SEVEN_DAI_SELF.getCode(),
                SealBusiness.DEBT_TRANSFER.getCode(), req.getLoanId());
        List<DebtTransfers> debtTransferList = new ArrayList<DebtTransfers>();

        for (int i = 0; i < fileAddrList.size(); i++) {
            DebtTransfers debtTransfers = new DebtTransfers();
            debtTransfers.setDebtTransferUrl(fileAddrList.get(i));
            debtTransferList.add(debtTransfers);
        }
        res.setDebtTransfers(debtTransferList);
    }

    //==========================代偿协议重新生成合规前的版本 start==========================

    @Override
    public void renewReceiptConfirmServiceFee2Pdf(LnCompensateDetail lnCompensateDetail, String orderNo, PartnerEnum partnerEnum) {
        if (lnCompensateDetail == null) {
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }
        // 1、生成代偿收款确认函-服务费（重新生成）
        try {
            String receiptConfirmPdfPath = "";
            if (Constants.PROPERTY_SYMBOL_YUN_DAI_SELF.equals(partnerEnum.getCode())) {
                receiptConfirmPdfPath = GlobEnvUtil.get("cfca.agreement.pdfSrcPath") + "/serviceFeeConfirm/"
                        + "serviceFeeConfirm-" + DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmssSSS")
                        + ".pdf";
            } else if (Constants.PROPERTY_SYMBOL_7_DAI_SELF.equals(partnerEnum.getCode())) {
                receiptConfirmPdfPath = GlobEnvUtil.get("cfca.seven.agreement.pdfSrcPath") + "/serviceFeeConfirm/"
                        + "serviceFeeConfirm-" + DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmssSSS")
                        + ".pdf";
            }
            log.info(receiptConfirmPdfPath);

            String receiptConfirmHtml = GlobEnvUtil.get("cfca.seal.renewReceiptConfirmServiceFee.pdfSrcHtml")
                    + "?orderNo=" + orderNo + "&partnerEnum=" + partnerEnum.getCode() + "&loanId=" + lnCompensateDetail.getPartnerLoanId();

            log.info("收款确认函服务费重新生成url>>>: " + receiptConfirmHtml);
            //收款确认函服务费的协议编号 SKFWF yyyyMMddHHmmssSSS 3位随机数
            int randomInt = (int) ((Math.random() * 9 + 1) * 100);
            String randomNumber = String.valueOf(randomInt);
            String agreementNo = "SKFWF" + DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmssSSS") + randomNumber;

            ITextPdfUtil.createHtm2Pdf(receiptConfirmHtml, receiptConfirmPdfPath, "收款确认函服务费", "收款确认函服务费", agreementNo);//代偿收款确认函-服务费
            LnCompensateAgreementAddress lnCompensateAgreementAddress = new LnCompensateAgreementAddress();
            lnCompensateAgreementAddress.setPartnerCode(partnerEnum.getCode());
            lnCompensateAgreementAddress.setOrderNo(orderNo);
            lnCompensateAgreementAddress.setPartnerLoanId(lnCompensateDetail.getPartnerLoanId());
            lnCompensateAgreementAddress.setDownloadUrl(receiptConfirmPdfPath.substring(receiptConfirmPdfPath.indexOf("/ftp", 0) + 4));
            lnCompensateAgreementAddress.setDownloadNum(0);
            lnCompensateAgreementAddress.setIsOpen(Constants.COMPENSATE_AGREEMENT_IS_OPEN_OPEN);
            lnCompensateAgreementAddress.setCreateTime(new Date());
            lnCompensateAgreementAddress.setUpdateTime(new Date());
            lnCompensateAgreementAddress.setAgreementType(SealBusiness.SERVICE_FEE_CONFIRM.getCode());
            lnCompensateAgreementAddressMapper.insertSelective(lnCompensateAgreementAddress);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void renewReceiptConfirmLate2Pdf(LnCompensateDetail lnCompensateDetail, String orderNo, PartnerEnum partnerEnum) {
        if (lnCompensateDetail == null) {
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }
        // 2、生成代偿收款确认函-债转（重新生成）
        try {

            String LetterOfCreditConfirmationPdfPath = "";
            if (Constants.PROPERTY_SYMBOL_YUN_DAI_SELF.equals(partnerEnum.getCode())) {
                LetterOfCreditConfirmationPdfPath = GlobEnvUtil.get("cfca.agreement.pdfSrcPath") + "/debtTransConfirm/"
                        + "debtTransConfirm-" + DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmssSSS")
                        + ".pdf";
            } else if (Constants.PROPERTY_SYMBOL_7_DAI_SELF.equals(partnerEnum.getCode())) {
                LetterOfCreditConfirmationPdfPath = GlobEnvUtil.get("cfca.seven.agreement.pdfSrcPath") + "/debtTransConfirm/"
                        + "debtTransConfirm-" + DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmssSSS")
                        + ".pdf";
            }
            log.info(LetterOfCreditConfirmationPdfPath);


            String receiptConfirmHtml = GlobEnvUtil.get("cfca.seal.renewLetterOfCreditConfirmation.pdfSrcHtml") +
                    "?orderNo=" + orderNo + "&partnerEnum=" + partnerEnum.getCode() + "&loanId=" + lnCompensateDetail.getPartnerLoanId();

            log.info("收款确认函债转重新生成url>>>: " + receiptConfirmHtml);
            //收款确认函债转协议编号 SKZZ yyyyMMddHHmmssSSS 3位随机数
            int randomInt = (int) ((Math.random() * 9 + 1) * 100);
            String randomNumber = String.valueOf(randomInt);
            String agreementNo = "SKZZ" + DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmssSSS") + randomNumber;

            ITextPdfUtil.createHtm2Pdf(receiptConfirmHtml, LetterOfCreditConfirmationPdfPath, "收款确认函债转", "收款确认函债转", agreementNo);//代偿收款确认函-债转

            LnCompensateAgreementAddress lnCompensateAgreementAddress = new LnCompensateAgreementAddress();
            lnCompensateAgreementAddress.setPartnerCode(partnerEnum.getCode());
            lnCompensateAgreementAddress.setOrderNo(orderNo);
            lnCompensateAgreementAddress.setPartnerLoanId(lnCompensateDetail.getPartnerLoanId());
            lnCompensateAgreementAddress.setDownloadUrl(LetterOfCreditConfirmationPdfPath.substring(LetterOfCreditConfirmationPdfPath.indexOf("/ftp", 0) + 4));
            lnCompensateAgreementAddress.setDownloadNum(0);
            lnCompensateAgreementAddress.setIsOpen(Constants.COMPENSATE_AGREEMENT_IS_OPEN_OPEN);
            lnCompensateAgreementAddress.setCreateTime(new Date());
            lnCompensateAgreementAddress.setUpdateTime(new Date());
            lnCompensateAgreementAddress.setAgreementType(SealBusiness.DEBT_TRANS_CONFIRM.getCode());
            lnCompensateAgreementAddressMapper.insertSelective(lnCompensateAgreementAddress);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void renewCreditorNotice2Pdf(LnCompensateDetail lnCompensateDetail, String orderNo, PartnerEnum partnerEnum) {
        if (lnCompensateDetail == null) {
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }
        // 3、生成代偿债权转让通知书（重新生成）
        try {
            //借款表id
            LnLoan lnLoan = lnLoanMapper.selectLoanByPartnerLoanId(lnCompensateDetail.getPartnerLoanId());
            Integer id = lnLoan.getId();

            String creditorNoticePath = "";
            if (Constants.PROPERTY_SYMBOL_YUN_DAI_SELF.equals(partnerEnum.getCode())) {
                creditorNoticePath = GlobEnvUtil.get("cfca.agreement.pdfSrcPath") + "/debtTransNotices/"
                        + "debtTransNotices-" + id + "-" + DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmssSSS")
                        + ".pdf";
            } else if (Constants.PROPERTY_SYMBOL_7_DAI_SELF.equals(partnerEnum.getCode())) {
                creditorNoticePath = GlobEnvUtil.get("cfca.seven.agreement.pdfSrcPath") + "/debtTransNotices/"
                        + "debtTransNotices-" + id + "-" + DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmssSSS")
                        + ".pdf";
            }
            log.info(creditorNoticePath);

            String creditorNoticeHtml = GlobEnvUtil.get("cfca.seal.renewLateRepayCreditorNotice.pdfSrcHtml") + "?loanId=" + lnCompensateDetail.getPartnerLoanId() + "&orderNo=" + orderNo + "&partnerEnum=" + partnerEnum.getCode();
            log.info("债权转让通知书重新生成url>>>: " + creditorNoticeHtml);

            //债权转让通知书 ZZTZS yyyyMMddHHmmssSSS 3位随机数
            int randomInt = (int) ((Math.random() * 9 + 1) * 100);
            String randomNumber = String.valueOf(randomInt);
            String agreementNo = "ZZTZS" + DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmssSSS") + randomNumber;

            ITextPdfUtil.createHtm2Pdf(creditorNoticeHtml, creditorNoticePath, "债权转让通知书", "债权转让通知书", agreementNo);

            LnCompensateAgreementAddress lnCompensateAgreementAddress = new LnCompensateAgreementAddress();
            lnCompensateAgreementAddress.setPartnerCode(partnerEnum.getCode());
            lnCompensateAgreementAddress.setOrderNo(orderNo);
            lnCompensateAgreementAddress.setPartnerLoanId(lnCompensateDetail.getPartnerLoanId());
            lnCompensateAgreementAddress.setDownloadUrl(creditorNoticePath.substring(creditorNoticePath.indexOf("/ftp", 0) + 4));
            lnCompensateAgreementAddress.setDownloadNum(0);
            lnCompensateAgreementAddress.setIsOpen(Constants.COMPENSATE_AGREEMENT_IS_OPEN_OPEN);
            lnCompensateAgreementAddress.setCreateTime(new Date());
            lnCompensateAgreementAddress.setUpdateTime(new Date());
            lnCompensateAgreementAddress.setAgreementType(SealBusiness.DEBT_TRANS_NOTICES.getCode());
            lnCompensateAgreementAddressMapper.insertSelective(lnCompensateAgreementAddress);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void renewReditor2Pdf(LnCompensateDetail lnCompensateDetail, String orderNo, PartnerEnum partnerEnum) {
        if (lnCompensateDetail == null) {
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }
        // 4、生成代偿债权转让协议
        try {
            LnLoan lnLoan = lnLoanMapper.selectLoanByPartnerLoanId(lnCompensateDetail.getPartnerLoanId());

            Integer loanId = lnLoan.getId();
            List<LnCompensateRelationVO> relationIdList = lnCompensateRelationMapper.selectRelationIdListByLoanId(loanId);
            if (CollectionUtils.isNotEmpty(relationIdList)) {
                for (LnCompensateRelationVO comRelationVo : relationIdList) {
                    String creditorPdfPath = "";
                    if (Constants.PROPERTY_SYMBOL_YUN_DAI_SELF.equals(partnerEnum.getCode())) {
                        creditorPdfPath = GlobEnvUtil.get("cfca.agreement.pdfSrcPath") + "/debtTransfer/"
                                + "debtTransfer-" + loanId + "-" + DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmssSSS")
                                + ".pdf";
                    } else if (Constants.PROPERTY_SYMBOL_7_DAI_SELF.equals(partnerEnum.getCode())) {
                        creditorPdfPath = GlobEnvUtil.get("cfca.seven.agreement.pdfSrcPath") + "/debtTransfer/"
                                + "debtTransfer-" + loanId + "-" + DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmssSSS")
                                + ".pdf";
                    }
                    log.info(creditorPdfPath);
                    String creditorPdfHtml = GlobEnvUtil.get("cfca.seal.renewLateRepayCreditor.pdfSrcHtml") + "?loanRelationId=" + comRelationVo.getLoanRelationId() + "&loanId=" + loanId + "&partnerEnum=" + partnerEnum.getCode();
                    log.info("债权转让协议重新生成url>>>: " + creditorPdfHtml);

                    //债权转让协议 ZZXY yyyyMMddHHmmssSSS 3位随机数
                    int randomInt = (int) ((Math.random() * 9 + 1) * 100);
                    String randomNumber = String.valueOf(randomInt);
                    String agreementNo = "ZZXY" + DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmssSSS") + randomNumber;
                    ITextPdfUtil.createHtm2Pdf(creditorPdfHtml, creditorPdfPath, "债权转让协议", "债权转让协议", agreementNo);

                    LnCompensateAgreementAddress lnCompensateAgreementAddress = new LnCompensateAgreementAddress();
                    lnCompensateAgreementAddress.setPartnerCode(partnerEnum.getCode());
                    lnCompensateAgreementAddress.setOrderNo(orderNo);
                    lnCompensateAgreementAddress.setPartnerLoanId(lnCompensateDetail.getPartnerLoanId());
                    lnCompensateAgreementAddress.setDownloadUrl(creditorPdfPath.substring(creditorPdfPath.indexOf("/ftp", 0) + 4));
                    lnCompensateAgreementAddress.setDownloadNum(0);
                    lnCompensateAgreementAddress.setIsOpen(Constants.COMPENSATE_AGREEMENT_IS_OPEN_OPEN);
                    lnCompensateAgreementAddress.setCreateTime(new Date());
                    lnCompensateAgreementAddress.setUpdateTime(new Date());
                    lnCompensateAgreementAddress.setAgreementType(SealBusiness.DEBT_TRANSFER.getCode());
                    lnCompensateAgreementAddressMapper.insertSelective(lnCompensateAgreementAddress);

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void renewNotifyAgreementUrls(final String orderNo, final List<LnCompensateDetail> detailList, final PartnerEnum partnerEnum) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                B2GReqMsg_DafyLoanNotice_AgreementNotice req = new B2GReqMsg_DafyLoanNotice_AgreementNotice();
                req.setOrderNo(orderNo);
                List<Agreements> agreeList = new ArrayList<Agreements>();
                if (CollectionUtils.isNotEmpty(detailList)) {
                    for (LnCompensateDetail detail : detailList) {
                        if (StringUtil.isEmpty(detail.getPartnerLoanId())) {
                            log.error("代偿协议重新生成,协议地址通知时代偿通知明细表id:" + detail.getId() + "对应的合作方借款编号为空");
                            continue;
                        }
                        LnLoanExample example = new LnLoanExample();
                        example.createCriteria().andPartnerLoanIdEqualTo(detail.getPartnerLoanId());
                        List<LnLoan> lnLoanList = lnLoanMapper.selectByExample(example);
                        if (CollectionUtils.isEmpty(lnLoanList)) {
                            log.error("代偿协议重新生成，协议地址通知时合作方借款编号id:" + detail.getPartnerLoanId() + "对应的币港湾借款信息为空");
                            continue;
                        }
                        LnLoan lnLoan = lnLoanList.get(0);
                        //获取借款用户信息
                        LnUser lnUser = lnUserMapper.selectByPrimaryKey(lnLoan.getLnUserId());

                        Agreements agreement = new Agreements();
                        agreement.setLoanId(detail.getPartnerLoanId());
                        agreement.setBorrowerName(lnUser.getUserName());
                        agreement.setBorrowerIdCard(lnUser.getIdCard());

                        //收款确认函（服务费）下载地址赋值
                        List<String> file1AddrList = bsLoanRelationShipService.queryCompenAgreeAddressList(partnerEnum.getCode(),
                                SealBusiness.SERVICE_FEE_CONFIRM.getCode(), lnLoan.getPartnerLoanId());
                        if (CollectionUtils.isEmpty(file1AddrList)) {
                            log.error("代偿协议重新生成时，用户签章文件记录表合作方借款id:" + lnLoan.getPartnerLoanId() + "对应的代偿协议地址(服务费收款确认函)还未生成或不存在");
                            continue;
                        }
                        agreement.setServiceFeeConfirmUrl(file1AddrList.get(0));

                        //收款确认函（债转）下载地址赋值
                        List<String> file2AddrList = bsLoanRelationShipService.queryCompenAgreeAddressList(partnerEnum.getCode(),
                                SealBusiness.DEBT_TRANS_CONFIRM.getCode(), lnLoan.getPartnerLoanId());
                        if (CollectionUtils.isEmpty(file2AddrList)) {
                            log.error("代偿协议重新生成时，用户签章文件记录表合作方借款id:" + lnLoan.getPartnerLoanId() + "对应的代偿协议地址(债转收款确认函)还未生成或不存在");
                            continue;
                        }
                        agreement.setDebtTransConfirmUrl(file2AddrList.get(0));

                        //债权转让通知书下载地址赋值
                        List<String> file3AddrList = new ArrayList<String>();
                        if (Constants.PROPERTY_SYMBOL_YUN_DAI_SELF.equals(partnerEnum.getCode())) {
                            file3AddrList = bsLoanRelationShipService.queryCompenAgreeAddressList(PartnerEnum.YUN_DAI_SELF.getCode(),
                                    SealBusiness.DEBT_TRANS_NOTICES.getCode(), lnLoan.getPartnerLoanId());
                        } else if (Constants.PROPERTY_SYMBOL_7_DAI_SELF.equals(partnerEnum.getCode())) {
                            file3AddrList = bsLoanRelationShipService.queryCompenAgreeAddressList(PartnerEnum.SEVEN_DAI_SELF.getCode(),
                                    SealBusiness.DEBT_TRANS_NOTICES.getCode(), lnLoan.getPartnerLoanId());
                        }

                        if (CollectionUtils.isEmpty(file3AddrList)) {
                            log.error("代偿协议重新生成时，用户签章文件记录表合作方借款id:" + lnLoan.getPartnerLoanId() + "对应的代偿协议地址(债权转让通知书)还未生成或不存在");
                            continue;
                        }
                        agreement.setDebtTransNoticesUrl(file3AddrList.get(0));

                        //债权转让信息赋值
                        List<DebtTransferInfo> debtTransferInfoList = bsLoanRelationShipService.queryTransferInfo(lnLoan.getPartnerLoanId());
                        agreement.setDebtTransferInfo(debtTransferInfoList);

                        //债权转让协议下载地址列表赋值
                        List<String> fileAddrList = new ArrayList<String>();
                        if (Constants.PROPERTY_SYMBOL_YUN_DAI_SELF.equals(partnerEnum.getCode())) {
                            fileAddrList = bsLoanRelationShipService.queryCompenAgreeAddressList(PartnerEnum.YUN_DAI_SELF.getCode(),
                                    SealBusiness.DEBT_TRANSFER.getCode(), lnLoan.getPartnerLoanId());
                        } else if (Constants.PROPERTY_SYMBOL_7_DAI_SELF.equals(partnerEnum.getCode())) {
                            fileAddrList = bsLoanRelationShipService.queryCompenAgreeAddressList(PartnerEnum.SEVEN_DAI_SELF.getCode(),
                                    SealBusiness.DEBT_TRANSFER.getCode(), lnLoan.getPartnerLoanId());
                        }

                        List<DebtTransfers> debtTransferList = new ArrayList<DebtTransfers>();
                        for (int i = 0; i < fileAddrList.size(); i++) {
                            DebtTransfers debtTransfers = new DebtTransfers();
                            debtTransfers.setDebtTransferUrl(fileAddrList.get(i));
                            debtTransferList.add(debtTransfers);
                        }
                        agreement.setDebtTransfers(debtTransferList);
                        agreeList.add(agreement);
                    }
                }
                req.setAgreements(agreeList);
                B2GResMsg_DafyLoanNotice_AgreementNotice res = null;

                //7贷自主放款-代偿协议下载地址通知
                B2GReqMsg_DepLoan7Notice_AgreementNotice sevenReq = new B2GReqMsg_DepLoan7Notice_AgreementNotice();
                sevenReq.setOrderNo(orderNo);
                sevenReq.setAgreements(agreeList);
                B2GResMsg_DepLoan7Notice_AgreementNotice sevenRes = null;

                try {
                    if (Constants.PROPERTY_SYMBOL_YUN_DAI_SELF.equals(partnerEnum.getCode())) {
                        res = dafyNoticeService.agreementNotice(req);
                        if (!ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
                            throw new PTMessageException(PTMessageEnum.CONNECTION_ERROR, "代偿协议重新生成时，代偿协议地址通知给云贷通讯异常");
                        }
                    } else if (Constants.PROPERTY_SYMBOL_7_DAI_SELF.equals(partnerEnum.getCode())) {
                        sevenRes = depLoan7NoticeService.agreementNotice(sevenReq);
                        if (!ConstantUtil.BSRESCODE_SUCCESS.equals(sevenRes.getResCode())) {
                            throw new PTMessageException(PTMessageEnum.CONNECTION_ERROR, "代偿协议重新生成时，代偿协议地址通知给7贷通讯异常");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //==========================代偿协议重新生成合规前的版本 end==========================

    private String getBlurName(String name) {
        String str = "";
        if (StringUtil.isNotBlank(name)) {
            str = name.substring(0, 1);
            for (int i = 1; i < name.length(); i++) {
                str += "*";
            }
        }
        return str;
    }

    /**
     * 18位的隐藏掉中间10位，如123456**********12；15位隐藏中间8位，如123456********1
     * 身份证
     *
     * @param idNo
     * @return
     */
    private String getBlurIdNo(String idNo) {
        String str = "";
        if (StringUtil.isNotBlank(idNo)) {
            str = idNo.substring(0, 6);
            if (idNo.length() == 18) {
                str += "**********" + idNo.substring(idNo.length() - 2);
            } else {
                str += "********" + idNo.substring(idNo.length() - 1);
            }
        }

        return str;
    }

}

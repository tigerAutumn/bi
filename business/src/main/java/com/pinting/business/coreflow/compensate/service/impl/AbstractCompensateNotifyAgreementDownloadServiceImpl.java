package com.pinting.business.coreflow.compensate.service.impl;

import com.pinting.business.accounting.loan.service.DepFixedRepayPaymentService;
import com.pinting.business.coreflow.compensate.enums.CompensateStatusEnum;
import com.pinting.business.coreflow.compensate.util.ConstantsForFields;
import com.pinting.business.coreflow.core.enums.BusinessTypeEnum;
import com.pinting.business.coreflow.core.model.FlowContext;
import com.pinting.business.coreflow.core.service.DepFixedService;
import com.pinting.business.dao.*;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.enums.SealBusiness;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.*;
import com.pinting.business.model.vo.BsUserCompensateVO;
import com.pinting.business.model.vo.CompensateTransfersPdfVO;
import com.pinting.business.model.vo.LnCompensateRelationVO;
import com.pinting.business.util.Constants;
import com.pinting.core.hessian.msg.ResMsg;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_DafyLoanNotice_AgreementNotice;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_DafyLoanNotice_AgreementNotice;
import com.pinting.gateway.hessian.message.dafy.model.Agreements;
import com.pinting.gateway.hessian.message.dafy.model.DebtTransferInfo;
import com.pinting.gateway.out.service.loan.DafyNoticeService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 代偿签章协议通知下载抽象实现类
 * Created by zousheng on 2018/7/30.
 */
public abstract class AbstractCompensateNotifyAgreementDownloadServiceImpl implements DepFixedService, ConstantsForFields {

    private final Logger logger = LoggerFactory.getLogger(AbstractCompensateNotifyAgreementDownloadServiceImpl.class);

    @Autowired
    private LnLoanMapper lnLoanMapper;
    @Autowired
    private LnUserMapper lnUserMapper;
    @Autowired
    private LnCompensateDetailMapper lnCompensateDetailMapper;
    @Autowired
    private DafyNoticeService dafyNoticeService;
    @Autowired
    private LnDepositionRepayScheduleMapper lnDepositionRepayScheduleMapper;
    @Autowired
    private LnCompensateRelationMapper lnCompensateRelationMapper;
    @Autowired
    private LnCompensateAgreementAddressMapper lnCompensateAgreementAddressMapper;
    @Autowired
    private DepFixedRepayPaymentService depFixedRepayPaymentService;

    @Override
    public ResMsg execute(FlowContext flowContext) {
        String partnerLoanId = (String) flowContext.getExtendData(PARTNER_LOAN_ID);

        // 取最后一笔本金代偿明细信息
        LnCompensateDetail lnCompensateDetail = getLnCompensateDetail(partnerLoanId);

        // 收款确认函（代偿）
        LnCompensateAgreementAddress debtTransConfirm = getLnCompensateAgreementAddress(lnCompensateDetail, SealBusiness.DEBT_TRANS_CONFIRM);

        // 债权转让通知书（代偿）
        LnCompensateAgreementAddress debtTransNotices = getLnCompensateAgreementAddress(lnCompensateDetail, SealBusiness.DEBT_TRANS_NOTICES);

        //获取借款信息
        LnLoan lnLoan = getLnLoan(partnerLoanId);

        //获取借款用户信息
        LnUser lnUser = lnUserMapper.selectByPrimaryKey(lnLoan.getLnUserId());

        // 查询合作方代偿人
        BsUserCompensateVO userCompensateVO = depFixedRepayPaymentService.compensaterInfo(lnLoan.getLoanTime(), flowContext.getPartnerEnum().getCode());

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

        B2GReqMsg_DafyLoanNotice_AgreementNotice req = new B2GReqMsg_DafyLoanNotice_AgreementNotice();
        req.setOrderNo(debtTransConfirm.getOrderNo());
        req.setAgreements(agreeList);
        try {
            B2GResMsg_DafyLoanNotice_AgreementNotice res = dafyNoticeService.agreementNotice(req);
            if (ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
                updateLnCompensateAgreementAddress(debtTransConfirm.getId(), CompensateStatusEnum.NOTICE_STATUS_SUCCESS);
                updateLnCompensateAgreementAddress(debtTransNotices.getId(), CompensateStatusEnum.NOTICE_STATUS_SUCCESS);
            } else {
                updateLnCompensateAgreementAddress(debtTransConfirm.getId(), CompensateStatusEnum.NOTICE_STATUS_FAIL);
                updateLnCompensateAgreementAddress(debtTransNotices.getId(), CompensateStatusEnum.NOTICE_STATUS_FAIL);
                throw new PTMessageException(PTMessageEnum.CONNECTION_ERROR, "代偿协议地址通知给云贷通讯异常");
            }
        } catch (Exception e) {
            logger.info("代偿协议地址通知给云贷通讯异常", e);
        }
        return flowContext.getRes();
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

    /**
     * 获取代偿协议地址（通知书、确认函）
     *
     * @param lnCompensateDetail
     * @param sealBusiness
     * @return
     */
    private LnCompensateAgreementAddress getLnCompensateAgreementAddress(LnCompensateDetail lnCompensateDetail, SealBusiness sealBusiness) {
        List<LnCompensateAgreementAddress> debtTransConfirmList =
                lnCompensateAgreementAddressMapper.selectCompensateAgreementList(lnCompensateDetail.getCompensateId(),
                        lnCompensateDetail.getPartnerLoanId(), sealBusiness.getCode());
        if (CollectionUtils.isEmpty(debtTransConfirmList)) {
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "代偿协议地址(" + sealBusiness.getDescription() + ")不存在");
        }
        LnCompensateAgreementAddress debtTransAgreement = debtTransConfirmList.get(0);
        if (StringUtils.isBlank(debtTransAgreement.getDownloadUrl())) {
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "代偿协议文件(" + sealBusiness.getDescription() + ")不存在");
        }
        File file = new File(GlobEnvUtil.get("sign.task.pdfPath") + "/" + debtTransAgreement.getDownloadUrl());
        if (!file.exists()) {
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "代偿协议文件(" + sealBusiness.getDescription() + ")不存在");
        }
        return debtTransAgreement;
    }

    /**
     * 更新代偿协议通知合作方状态
     *
     * @param id
     * @param informStatus
     */
    private void updateLnCompensateAgreementAddress(Integer id, CompensateStatusEnum informStatus) {
        LnCompensateAgreementAddress debtTransConfirmTemp = new LnCompensateAgreementAddress();
        debtTransConfirmTemp.setId(id);
        debtTransConfirmTemp.setInformStatus(informStatus.getCode());
        debtTransConfirmTemp.setUpdateTime(new Date());
        lnCompensateAgreementAddressMapper.updateByPrimaryKeySelective(debtTransConfirmTemp);
    }

    /**
     * 获取借款信息
     *
     * @param partnerLoanId
     */
    private LnLoan getLnLoan(String partnerLoanId) {
        LnLoanExample example = new LnLoanExample();
        example.createCriteria().andPartnerLoanIdEqualTo(partnerLoanId);
        List<LnLoan> lnLoanList = lnLoanMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(lnLoanList)) {
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "云贷协议下载地址查询时借款信息未找到");
        }
        return lnLoanList.get(0);
    }

    /**
     * 查询有本金代偿成功的明细列表, 取最后一笔代偿的明细信息
     * @param partnerLoanId
     * @return
     */
    private LnCompensateDetail getLnCompensateDetail(String partnerLoanId) {
        LnCompensateDetailExample lnCompensateDetailExample = new LnCompensateDetailExample();
        lnCompensateDetailExample.createCriteria().andPartnerLoanIdEqualTo(partnerLoanId)
                .andStatusEqualTo(Constants.COMPENSATE_REPAYS_STATUS_SUCC)
                .andPrincipalGreaterThan(0d);
        lnCompensateDetailExample.setOrderByClause("repay_serial DESC");
        List<LnCompensateDetail> lnCompensateDetailList = lnCompensateDetailMapper.selectByExample(lnCompensateDetailExample);
        if (CollectionUtils.isEmpty(lnCompensateDetailList)) {
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "本金代偿明细数据不存在");
        }

        LnCompensateDetail lnCompensateDetail = lnCompensateDetailList.get(0);
        if (!Constants.AGREEMENT_GRNERATE_STATUS_SUCC.equals(lnCompensateDetail.getAgreementGenerateStatus())) {
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "代偿签章协议未生成");
        }
        return lnCompensateDetail;
    }
}

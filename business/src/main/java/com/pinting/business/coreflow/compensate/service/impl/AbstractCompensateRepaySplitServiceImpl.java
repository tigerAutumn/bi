package com.pinting.business.coreflow.compensate.service.impl;

import com.pinting.business.accounting.loan.enums.LoanSubjects;
import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.loan.model.FinanceRepayCalVO;
import com.pinting.business.accounting.loan.model.FixedRepaySysSplitInfo;
import com.pinting.business.accounting.loan.model.LnRepayScheduleVO;
import com.pinting.business.accounting.loan.model.LoanRelation4TransferVO;
import com.pinting.business.accounting.loan.service.DepFixedRepayAccountService;
import com.pinting.business.accounting.loan.service.DepFixedRepayPaymentService;
import com.pinting.business.coreflow.compensate.model.vo.CompensateRepaySplitVO;
import com.pinting.business.coreflow.core.model.FlowContext;
import com.pinting.business.coreflow.core.service.DepFixedService;
import com.pinting.business.dao.*;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.enums.RedisLockEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.*;
import com.pinting.business.model.vo.AverageCapitalPlusInterestVO;
import com.pinting.business.model.vo.BsUserCompensateVO;
import com.pinting.business.service.site.SpecialJnlService;
import com.pinting.business.util.CalculatorUtil;
import com.pinting.business.util.Constants;
import com.pinting.core.hessian.msg.ResMsg;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 代偿还款系统分支抽象实现类
 * Created by zousheng on 2018/6/19.
 */
public abstract class AbstractCompensateRepaySplitServiceImpl implements DepFixedService {

    private final Logger logger = LoggerFactory.getLogger(AbstractCompensateRepaySplitServiceImpl.class);

    private JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);

    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private DepFixedRepayAccountService depFixedRepayAccountService;
    @Autowired
    private LnRepayScheduleMapper lnRepayScheduleMapper;
    @Autowired
    private LnRepayScheduleDetailMapper lnRepayScheduleDetailMapper;
    @Autowired
    private LnLoanRelationMapper lnLoanRelationMapper;
    @Autowired
    private LnDepositionRepayScheduleMapper lnDepositionRepayScheduleMapper;
    @Autowired
    private LnDepositionRepayScheduleDetailMapper lnDepositionRepayScheduleDetailMapper;
    @Autowired
    private LnCompensateRelationMapper lnCompensateRelationMapper;
    @Autowired
    private LnCompensateDetailMapper lnCompensateDetailMapper;
    @Autowired
    private SpecialJnlService specialJnlService;
    @Autowired
    private LnFinanceRepayScheduleMapper lnFinanceRepayScheduleMapper;
    @Autowired
    private BsRevenueTransDetailMapper bsRevenueTransDetailMapper;
    @Autowired
    private DepFixedRepayPaymentService depFixedRepayPaymentService;
    @Autowired
    private LnRepeatRepayRecordMapper lnRepeatRepayRecordMapper;
    @Autowired
    private LnRepayMapper lnRepayMapper;

    @Override
    public ResMsg execute(FlowContext flowContext) {
        PartnerEnum partner = flowContext.getPartnerEnum();
        LnLoan lnLoan = (LnLoan) flowContext.getExtendData(LnLoan.class.getSimpleName());
        LnCompensateDetail lnCompensateDetail = (LnCompensateDetail) flowContext.getExtendData(LnCompensateDetail.class.getSimpleName());
        LnCompensate lnCompensate = (LnCompensate) flowContext.getExtendData(LnCompensate.class.getSimpleName());

        // 还款期数大于第一期，判断前一期还款计划已还款或已代偿
        verifyBeforeLnRepayScheduleRepayment(lnLoan.getId(), lnCompensateDetail, partner);

        // 4.查询当前代偿明细中对应的还款计划
        LnRepaySchedule lnRepaySchedule = getLnRepaySchedule(lnLoan.getId(), lnCompensateDetail, partner);
        if ((PartnerEnum.YUN_DAI_SELF.getCode().equals(partner.getCode()) && isLnRepayRepayment(lnRepaySchedule)) ||
                Constants.LN_REPAY_REPAIED.equals(lnRepaySchedule.getStatus()) ||
                Constants.LN_REPAY_LATE_REPAIED.equals(lnRepaySchedule.getStatus())) {
            // 5.若还款计划已还款，调重复还款处理
            repeatRepayProcess(lnCompensate, lnCompensateDetail, lnRepaySchedule);
        } else if (Constants.LN_REPAY_LATE_NOT.equals(lnRepaySchedule.getStatus())) {
            // 5.已代偿的账单不进行处理，做告警处理
            logger.info(">>>" + partner.getName() + "代偿单号[" + lnCompensate.getOrderNo() + "]对账单编号[" + lnRepaySchedule.getId() + "]代偿重复！<<<");
            specialJnlService.warn4Fail(0d, partner.getName() + "代偿单号[" + lnCompensate.getOrderNo() + "]对账单编号[" + lnRepaySchedule.getId() + "]代偿重复",
                    lnCompensate.getOrderNo(), "代偿重复", false);
        } else if (Constants.LN_REPAY_CANCELLED.equals(lnRepaySchedule.getStatus())) {
            // 5.已废除的账单不进行处理，做告警处理
            logger.info(">>>代偿单号[" + lnCompensate.getOrderNo() + "]对账单编号[" + lnRepaySchedule.getId() + "]lnRepaySchedule已废除！<<<");
            specialJnlService.warn4Fail(0d, partner.getName() + "代偿单号[" + lnCompensate.getOrderNo() + "]对账单编号[" + lnRepaySchedule.getId() + "]还款计划已废除",
                    lnCompensate.getOrderNo(), "还款计划已废除", false);
        } else {
            // 5.循环调用代偿系统分账处理
            compensateRepaySysSplit(lnLoan, lnCompensateDetail, partner, lnRepaySchedule);
        }

        return flowContext.getRes();
    }

    /**
     * 单笔代偿还款系统分账处理
     *
     * @param lnLoan
     * @param compensateDetail
     * @param partner
     * @param lnRepaySchedule
     */
    protected void compensateRepaySysSplit(final LnLoan lnLoan, final LnCompensateDetail compensateDetail, final PartnerEnum partner, final LnRepaySchedule lnRepaySchedule) {
        // 1.查询初始状态的代偿明细
        final LnCompensateDetail lnCompensateDetail = getLnCompensateDetail(compensateDetail.getId(), partner);

        // 获得实际代偿的利息本金
        final Double repayPrincipal = lnCompensateDetail.getPrincipal();
        final String compensateTypeString = repayPrincipal > 0 ? "本金" : "利息";

        // 2.校验代偿本金与还款计划本金一致，不一致则报错 + 平台告警
        verifyRepaySchedulPrincipal(lnRepaySchedule.getId(), lnCompensateDetail.getId(), repayPrincipal, compensateTypeString, partner);
        try {
            jsClientDaoSupport.lock(RedisLockEnum.LOCK_DEP_LOAN_RELATION.getKey());

            // 3.查询有债权关系的债权数据
            final List<LoanRelation4TransferVO> relationList = lnLoanRelationMapper.getRelationList(null, lnRepaySchedule.getLoanId());
            if (CollectionUtils.isEmpty(relationList)) {
                logger.info("===========还款结果处理：无对应的债权================");
                throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
            }
            try {
                transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                    @Override
                    protected void doInTransactionWithoutResult(TransactionStatus status) {
                        logger.info("===========【" + partner.getName() + "," + compensateTypeString + "代偿还款系统分账】开始：代偿本金：" + repayPrincipal + "，代偿利息：" + lnCompensateDetail.getInterest() + "================");

                        // 4.查询上一期还款计划时间
                        LnRepayScheduleVO repayScheduleVO = lnRepayScheduleMapper.selectLnRepayScheduleVOByLoanId(lnRepaySchedule.getLoanId(), lnRepaySchedule.getPlanDate());

                        // 5.计算当期借款的计息天数
                        Integer interestDay = 0;
                        if (repayScheduleVO.getPlanDate() == null) {
                            interestDay = DateUtil.getDiffeDay(lnRepaySchedule.getPlanDate(), repayScheduleVO.getLoanDate()) + 1;
                        } else {
                            interestDay = DateUtil.getDiffeDay(lnRepaySchedule.getPlanDate(), repayScheduleVO.getPlanDate());
                        }

                        // 5.获取结算利率(云贷自主和币港湾的结算利率)----20%
                        Double initRate = lnLoan.getBgwSettleRate() != null ? lnLoan.getBgwSettleRate() : getSysSettleRate();
                        Double settleRate = initRate == null ? 20 : initRate;
                        lnLoan.setBgwSettleRate(settleRate); // 防止结算利率值缺失

                        // 获得实际代偿的利息
                        Double interestAmount = lnCompensateDetail.getInterest();

                        // 6.每月结算总利息
                        AverageCapitalPlusInterestVO settleInterestVO = calSettleInterestAmount(lnLoan, lnRepaySchedule.getSerialId());
                        //应收利息 = 结算本息-代偿本金
                        Double settleInterest = MoneyUtil.subtract(settleInterestVO.getPlanTotal(), repayPrincipal).doubleValue();
                        // 校验结算利息与实际代偿利息一致
                        verifySettleInterestAmount(settleInterest, interestAmount, lnCompensateDetail.getId(), compensateTypeString, settleRate, partner);
                        logger.info("=============【" + partner.getName() + "," + compensateTypeString + "代偿还款系统分账】应还币港湾" + settleRate + "%总本息F=" + settleInterestVO.getPlanTotal() + "===============");

                        // 7.借款服务费（营收）
                        Double loanServiceRate = lnLoan.getLoanServiceRate() != null ? lnLoan.getLoanServiceRate() : getSysLoanServerRate();
                        lnLoan.setLoanServiceRate(loanServiceRate);
                        Double loanServiceAmount = loanServiceRate != null ? calLoanServiceAmount(lnLoan, lnRepaySchedule.getSerialId(), settleInterestVO.getPlanInterest()) : 0;

                        // 获取最大的SerialId
                        Integer maxSerialId = lnDepositionRepayScheduleMapper.selectMaxSerialIdByLoanId(lnLoan.getId());

                        // 8.根据债权列表，计算每笔债权的借款协议本息总和，记录ln_finance_repay_schedule表
                        FinanceRepayCalVO financeRepayCalVO = do4FinanceRepay(relationList, lnRepaySchedule, interestDay, lnLoan, maxSerialId, partner, settleInterestVO.getPlanInterest());

                        // 10.系统营收户金额（THD_BGW_REVENUE_YUN）= 币港湾20%总本息 - 13%协议利率本息-2%借款服务费
                        Double bgwRevenueAmount = MoneyUtil.subtract(MoneyUtil.subtract(settleInterestVO.getPlanTotal(), financeRepayCalVO.getAgreementSumAmount()).doubleValue(), loanServiceAmount).doubleValue();

                        // 11.合作方（云贷）营收= 代偿金额 - 结算本息，非0 ，都记营收明细记录表和系统云贷营收账户
                        Double yunRevenueAmount = MoneyUtil.subtract(lnCompensateDetail.getTotal(), settleInterestVO.getPlanTotal()).doubleValue();

                        // 12.系统还款户金额（THD_REPAY）= 借款协议本息 + 2%借款服务费
                        Double repayAmount = MoneyUtil.add(financeRepayCalVO.getAgreementSumAmount(), loanServiceAmount).doubleValue();

                        updateBsRevenueTransDetail(partner, lnLoan, lnCompensateDetail, lnRepaySchedule, yunRevenueAmount);
                        updateLnCompensateDetail(lnCompensateDetail);
                        updateLnRepaySchedule(lnRepaySchedule);
                        addLnDepositionRepaySchedule(lnLoan, lnRepaySchedule, financeRepayCalVO.getList(), maxSerialId,
                                interestDay, repayAmount, repayPrincipal, loanServiceAmount);
                        repaySysSplit(partner, lnRepaySchedule, bgwRevenueAmount, yunRevenueAmount, repayAmount);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        } finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_DEP_LOAN_RELATION.getKey());
        }
    }

    /**
     * 记录合作方还款营收收入（bs_revenue_trans_detail）
     *
     * @param partner
     * @param lnLoan
     * @param lnCompensateDetail
     * @param lnRepaySchedule
     * @param yunRevenueAmount
     */
    private void updateBsRevenueTransDetail(PartnerEnum partner, LnLoan lnLoan, LnCompensateDetail lnCompensateDetail, LnRepaySchedule lnRepaySchedule, Double yunRevenueAmount) {
        BsRevenueTransDetail bsRevenueTransDetail = new BsRevenueTransDetail();
        bsRevenueTransDetail.setPartnerCode(partner.getCode());
        bsRevenueTransDetail.setTransType(Constants.REVENUE_TYPE_REPAY_INCOME);
        bsRevenueTransDetail.setLoanId(lnLoan.getId());
        bsRevenueTransDetail.setRepaySerial(lnRepaySchedule.getSerialId());
        bsRevenueTransDetail.setRepayScheduleId(lnRepaySchedule.getId());
        bsRevenueTransDetail.setRepayAmount(lnCompensateDetail.getTotal());
        bsRevenueTransDetail.setRevenueAmount(yunRevenueAmount);
        bsRevenueTransDetail.setRepayScheduleId(lnRepaySchedule.getId());
        bsRevenueTransDetail.setCreateTime(new Date());
        bsRevenueTransDetail.setUpdateTime(new Date());
        bsRevenueTransDetailMapper.insertSelective(bsRevenueTransDetail);
    }

    /**
     * 更新代偿明细表,标识代偿成功（ln_compensate_detail）
     */
    private void updateLnCompensateDetail(LnCompensateDetail lnCompensateDetail) {
        LnCompensateDetail detailTemp = new LnCompensateDetail();
        detailTemp.setId(lnCompensateDetail.getId());
        detailTemp.setStatus(Constants.COMPENSATE_REPAYS_STATUS_SUCC);
        detailTemp.setUpdateTime(new Date());
        lnCompensateDetailMapper.updateByPrimaryKeySelective(detailTemp);
    }

    /**
     * 更新还款计划表，标识逾期未还款（ln_repay_schedule）
     *
     * @param lnRepaySchedule
     */
    private void updateLnRepaySchedule(LnRepaySchedule lnRepaySchedule) {
        LnRepaySchedule lnRepayScheduleTemp = new LnRepaySchedule();
        lnRepayScheduleTemp.setId(lnRepaySchedule.getId());
        lnRepayScheduleTemp.setFinishTime(new Date());
        lnRepayScheduleTemp.setUpdateTime(new Date());
        lnRepayScheduleTemp.setStatus(Constants.LN_REPAY_LATE_NOT);
        lnRepayScheduleMapper.updateByPrimaryKeySelective(lnRepayScheduleTemp);
    }

    /**
     * 1.新增存管还款计信息（ln_deposition_repay_schedule）
     * 2.新增存管还款计划明细信息（ln_deposition_repay_schedule_detail）
     * 3.本金代偿，记录ln_compensate_relation
     *
     * @param lnLoan
     * @param lnRepaySchedule
     * @param repayAmount
     * @param loanServiceAmount
     */
    private void addLnDepositionRepaySchedule(LnLoan lnLoan, LnRepaySchedule lnRepaySchedule, List<LnCompensateRelation> lnCompensateRelations, Integer maxSerialId,
                                              Integer interestDay, Double repayAmount, Double repayPrincipal, Double loanServiceAmount) {
        //存管还款计划表
        LnDepositionRepaySchedule schedul = new LnDepositionRepaySchedule();
        schedul.setLnUserId(lnLoan.getLnUserId());
        schedul.setLoanId(lnLoan.getId());
        schedul.setPartnerRepayId(lnRepaySchedule.getPartnerRepayId());
        schedul.setSerialId(maxSerialId + 1);
        schedul.setPlanDate(lnRepaySchedule.getPlanDate());
        schedul.setPlanTotal(repayAmount);
        schedul.setStatus(Constants.LN_REPAY_STATUS_INIT);
        schedul.setReturnFlag(Constants.DEP_REPAY_RETURN_FLAG_DF_PENDING);
        if (repayPrincipal > 0) {
            //本金代偿，记逾期未还款
            schedul.setRepayType(Constants.LN_DEP_REPAY_SCHEDULE_REPAY_TYPE_LATE_NOT);
        } else {
            schedul.setRepayType(Constants.LN_DEP_REPAY_SCHEDULE_REPAY_TYPE_NORMAL_REPAY);
        }
        schedul.setCreateTime(new Date());
        schedul.setUpdateTime(new Date());
        lnDepositionRepayScheduleMapper.insertSelective(schedul);

        //存管还款计划表明细
        LnDepositionRepayScheduleDetail detailInterest = new LnDepositionRepayScheduleDetail();
        detailInterest.setPlanId(schedul.getId());
        Double agreementInterest = MoneyUtil.subtract(MoneyUtil.subtract(repayAmount, repayPrincipal).doubleValue(),
                loanServiceAmount==null?0:loanServiceAmount).doubleValue();
        detailInterest.setPlanAmount(agreementInterest);
        detailInterest.setSubjectCode(LoanSubjects.SUBJECT_CODE_INTEREST.getCode());
        detailInterest.setCreateTime(new Date());
        detailInterest.setUpdateTime(new Date());
        lnDepositionRepayScheduleDetailMapper.insertSelective(detailInterest);

        LnDepositionRepayScheduleDetail detailPrincipal = new LnDepositionRepayScheduleDetail();
        detailPrincipal.setPlanId(schedul.getId());
        detailPrincipal.setPlanAmount(repayPrincipal);
        detailPrincipal.setSubjectCode(LoanSubjects.SUBJECT_CODE_PRINCIPAL.getCode());
        detailPrincipal.setCreateTime(new Date());
        detailPrincipal.setUpdateTime(new Date());
        lnDepositionRepayScheduleDetailMapper.insertSelective(detailPrincipal);

        if (loanServiceAmount != null && loanServiceAmount > 0) {
            //存管还款计划表明细-借款服务费
            LnDepositionRepayScheduleDetail detailLoanService = new LnDepositionRepayScheduleDetail();
            detailLoanService.setPlanId(schedul.getId());
            detailLoanService.setPlanAmount(loanServiceAmount);
            detailLoanService.setSubjectCode(LoanSubjects.SUBJECT_CODE_LOAN_SERVICE_FEE.getCode());
            detailLoanService.setCreateTime(new Date());
            detailLoanService.setUpdateTime(new Date());
            lnDepositionRepayScheduleDetailMapper.insertSelective(detailLoanService);
        }

        if (repayPrincipal > 0) {
            //本金代偿，记录ln_compensate_relation
            if (CollectionUtils.isNotEmpty(lnCompensateRelations)) {
                for (LnCompensateRelation compensateRelation : lnCompensateRelations) {
                    compensateRelation.setDepPlanId(schedul.getId());
                    compensateRelation.setInterestDay(interestDay);
                    lnCompensateRelationMapper.insertSelective(compensateRelation);
                }
            }
        }
    }

    /**
     * 系统记账
     *
     * @param partner
     * @param lnRepaySchedule
     * @param bgwRevenueAmount
     * @param yunRevenueAmount
     * @param repayAmount
     */
    private void repaySysSplit(PartnerEnum partner, LnRepaySchedule lnRepaySchedule,
                               Double bgwRevenueAmount, Double yunRevenueAmount, Double repayAmount) {
        FixedRepaySysSplitInfo repaySysSplitInfo = new FixedRepaySysSplitInfo();
        repaySysSplitInfo.setThdRepayAmount(repayAmount);
        repaySysSplitInfo.setThdRevenueAmount(yunRevenueAmount);
        repaySysSplitInfo.setThdBGWRevenueAmount(bgwRevenueAmount);
        repaySysSplitInfo.setPartner(partner);
        repaySysSplitInfo.setThdMarginAmount(0d);
        repaySysSplitInfo.setFee(0d);
        repaySysSplitInfo.setLnRepayScheduleId(lnRepaySchedule.getId());
        depFixedRepayAccountService.repaySysSplit(repaySysSplitInfo);
    }

    /**
     * 查询初始状态的代偿明细
     *
     * @param lnCompensateDetailId
     * @param partner
     * @return
     */
    private LnCompensateDetail getLnCompensateDetail(Integer lnCompensateDetailId, PartnerEnum partner) {
        LnCompensateDetailExample example = new LnCompensateDetailExample();
        example.createCriteria().andIdEqualTo(lnCompensateDetailId)
                .andStatusEqualTo(Constants.COMPENSATE_REPAYS_STATUS_INIT);
        List<LnCompensateDetail> list = lnCompensateDetailMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(list)) {
            logger.info("===========【" + partner.getName() + "代偿还款系统分账】：代偿明细数据未获得================");
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }
        return list.get(0);
    }

    /**
     * 根据还款编号和科目查询还款详情表中对应科目的还款金额
     *
     * @param repayScheduleId
     * @param subjectCode
     * @return
     */
    private Double getRepayDetailAmount(Integer repayScheduleId, LoanSubjects subjectCode) {
        LnRepayScheduleDetailExample example = new LnRepayScheduleDetailExample();
        example.createCriteria().andPlanIdEqualTo(repayScheduleId).andSubjectCodeEqualTo(subjectCode.getCode());
        List<LnRepayScheduleDetail> list = lnRepayScheduleDetailMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0).getPlanAmount();
        }
        return 0d;
    }

    /**
     * 校验代偿本金与还款计划本金一致，不一致则报错 + 平台告警
     *
     * @param lnRepayScheduleId
     * @param lnCompensateDetailId
     * @param repayPrincipal
     * @param compensateTypeString
     * @param partner
     */
    private void verifyRepaySchedulPrincipal(Integer lnRepayScheduleId, Integer lnCompensateDetailId, Double repayPrincipal, String compensateTypeString, PartnerEnum partner) {
        // 计划表还款本金校验
        final Double repaySchedulPrincipal = getRepayDetailAmount(lnRepayScheduleId, LoanSubjects.SUBJECT_CODE_PRINCIPAL);
        if (repaySchedulPrincipal.compareTo(repayPrincipal) != 0) {
            logger.info("===========【" + partner.getName() + compensateTypeString + "代偿还款系统分账】：代偿本金：" + repayPrincipal + "，计划表应还本金：" + repaySchedulPrincipal + "================");
            //告警表添加数据
            specialJnlService.warn4FailNoSMS(MoneyUtil.subtract(repaySchedulPrincipal, repayPrincipal).doubleValue(),
                    "【" + partner.getName() + compensateTypeString + "代偿还款系统分账】：代偿本金：" + repayPrincipal + "，计划表应还本金：" + repaySchedulPrincipal,
                    "代偿明细记录编号：" + lnCompensateDetailId.toString(),
                    "【" + partner.getName() + compensateTypeString + "代偿还款系统分账】");
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "代偿本金与还款计划本金不一致");
        }
    }

    /**
     * 校验结算利息和代偿利息一致，不一致则平台告警
     *
     * @param settleInterestAmount
     * @param interestAmount
     * @param lnCompensateDetailId
     * @param compensateTypeString
     * @param settleRate
     * @param partner
     */
    private void verifySettleInterestAmount(Double settleInterestAmount, Double interestAmount, Integer lnCompensateDetailId, String compensateTypeString, Double settleRate, PartnerEnum partner) {
        if (settleInterestAmount.compareTo(interestAmount) != 0) {
            //利息代偿，实付和应收的利息不等，告警
            //告警表添加数据
            specialJnlService.warn4FailNoSMS(MoneyUtil.subtract(interestAmount, settleInterestAmount).doubleValue(),
                    "【" + partner.getName() + "," + compensateTypeString + "代偿还款系统分账】代偿还款利息总和 - bgw" + settleRate + "%总利息 =" + interestAmount + "-" + settleInterestAmount + "=" + MoneyUtil.subtract(interestAmount, settleInterestAmount).doubleValue(),
                    "代偿明细记录编号：" + lnCompensateDetailId.toString(),
                    "【" + partner.getName() + "," + compensateTypeString + "代偿还款系统分账】");
        }
    }

    /**
     * 根据债权列表，计算每笔债权的，记录ln_finance_repay_schedule表，并借款协议利息求和，返回
     *
     * @param relationList         债权关系列表
     * @param lnRepaySchedule      还款计划
     * @param interestTotalDays    借款人当期计息天数
     * @param lnLoan               借款信息
     * @param maxSerial            已还的最大期数
     * @param partner              借款合作方标识
     * @param settleInterestAmount 结算总利息
     * @return
     */
    protected FinanceRepayCalVO do4FinanceRepay(List<LoanRelation4TransferVO> relationList, LnRepaySchedule lnRepaySchedule,
                                                Integer interestTotalDays, LnLoan lnLoan, Integer maxSerial, PartnerEnum partner, Double settleInterestAmount) {
        FinanceRepayCalVO financeRepayCalVO = new FinanceRepayCalVO();
        List<LnCompensateRelation> compensateList = new ArrayList<>();
        Double sumRepayPrincipal = 0d; //累计还到用户的本金
        Double sumRepayAgreementAmount = 0d; //借款协议本息总和

        //计算当前借款剩余总本金
        Double leftTotalPrincipal = lnLoanRelationMapper.sumLeftAmountByLoanId(lnLoan.getId());
        for (LoanRelation4TransferVO record : relationList) {
            // 计算协议利率本息
            AverageCapitalPlusInterestVO agreementVo = calAgreementPrincipalInterestAmount(record, lnLoan, lnRepaySchedule.getSerialId(), settleInterestAmount, leftTotalPrincipal);

            // 协议利率本息
            sumRepayAgreementAmount = MoneyUtil.add(agreementVo.getPlanTotal(), sumRepayAgreementAmount).doubleValue();

            // 计算应回理财人本金
            Double repay2UserPrincipal = agreementVo.getPlanPrincipal();
            sumRepayPrincipal = MoneyUtil.add(sumRepayPrincipal, repay2UserPrincipal).doubleValue();

            // 计算产品利息
            Double productInterestAmount = calProductInterestAmount(record, lnLoan, lnRepaySchedule.getSerialId(), settleInterestAmount, leftTotalPrincipal);
            // 理财人应得利息
            Double userInterestAmount = 0d;
            //承接债权后还款，应付理财人的债权垫付金额
            Double planTransInterest = 0d;

            // 计算理财人已经拥有债权拥有的总天数
            // 还款计划计息日后一天
            Date finishDate = DateUtil.addDays(lnRepaySchedule.getPlanDate(), 1);
            Date beginDate = record.getRelationBeginDate(); // 受让人获取债权的时间（债权开始时间）
            Integer ownTotalDays = DateUtil.getDiffeDay(finishDate, beginDate); // 起息日到最后一次计息日的所有计息天数
            if (Constants.TRANS_MARK_TRANS_IN.equals(record.getTransMark())
                    && ownTotalDays.compareTo(interestTotalDays) < 0
                    && lnRepaySchedule.getSerialId().equals(record.getFirstTerm())) {
                logger.info("债权转让标识{}，债权转让计算债权持有天数", record.getTransMark());
                // 理财人应得利息 = 期满产品利息*当期债权持有天数/当期还款月总天数
                userInterestAmount = CalculatorUtil.calculate("a*a/a", productInterestAmount, ownTotalDays.doubleValue(), interestTotalDays.doubleValue());

                // 此次应付理财人的债权垫付金额 = 债转付息
                planTransInterest = record.getLastPayInterest();
            } else {
                // 债权持有天数 > 当期代偿还款月总天数
                userInterestAmount = productInterestAmount;
                ownTotalDays = interestTotalDays;
            }
            logger.info("============【还款处理】此次结束计息时间：" + DateUtil.format(finishDate) + "，债权开始时间："+ DateUtil.format(beginDate) +"，本次代偿计息总天数：" + interestTotalDays + " 当期债权拥有天数：" + ownTotalDays + "============================");
            // 新增理财人还款计划
            addLnFinanceRepaySchedule(repay2UserPrincipal, userInterestAmount, planTransInterest, agreementVo.getPlanInterest(), record.getId(), maxSerial);
            logger.info("=============本金代偿,是则生成ln_compensate_relation========================");
            compensateList.add(createLnCompensateRelation(lnLoan, partner, record.getId(), repay2UserPrincipal, agreementVo.getPlanInterest()));
        }
        financeRepayCalVO.setList(compensateList);
        financeRepayCalVO.setAgreementSumAmount(sumRepayAgreementAmount);
        financeRepayCalVO.setFinanceSumPrincipal(sumRepayPrincipal);
        return financeRepayCalVO;
    }

    /**
     * 新增理财人还款计划表（ln_finance_repay_schedule）
     *
     * @param thisRepayAmount
     * @param interestAmount
     * @param planTransInterest
     * @param agreementInterestAmount
     * @param relationId
     * @param maxSerial
     */
    private void addLnFinanceRepaySchedule(Double thisRepayAmount, Double interestAmount, Double planTransInterest, Double agreementInterestAmount, Integer relationId, Integer maxSerial) {
        LnFinanceRepaySchedule scheduleTemp = new LnFinanceRepaySchedule();
        //此时为还款，理财人分账 = 还款本金 + 理财人应得利息 + 应付理财人的债权垫付金额（债权转让垫付金额）
        Double financeAmount = MoneyUtil.add(thisRepayAmount,
                MoneyUtil.add(interestAmount, planTransInterest).doubleValue()).doubleValue();
        // 计划还款总金额 = 借款协议利息 + 还款本金
        scheduleTemp.setPlanTotal(MoneyUtil.add(agreementInterestAmount, thisRepayAmount).doubleValue());
        //手续费分账 = 计划还款总金额 - 理财人分账
        Double planFee = MoneyUtil.subtract(scheduleTemp.getPlanTotal(), financeAmount).doubleValue();
        scheduleTemp.setPlanFee(planFee);
        scheduleTemp.setPlanInterest(interestAmount);
        scheduleTemp.setPlanPrincipal(thisRepayAmount);
        scheduleTemp.setPlanTransInterest(planTransInterest);
        scheduleTemp.setRepaySerial(maxSerial + 1);
        Date today = DateUtil.parseDate(DateUtil.formatYYYYMMDD(new Date()));
        scheduleTemp.setCreateTime(new Date());
        scheduleTemp.setPlanDate(today);
        scheduleTemp.setRelationId(relationId);
        scheduleTemp.setStatus(Constants.FINANCE_REPAY_SATAE_REPAYING);
        scheduleTemp.setUpdateTime(new Date());
        lnFinanceRepayScheduleMapper.insertSelective(scheduleTemp);
    }

    /**
     * 创建存管代偿人债权关系记录
     *
     * @param lnLoan
     * @param partner
     * @param relationId
     * @param thisRepayAmount
     * @param agreementInterestAmount
     * @return
     */
    private LnCompensateRelation createLnCompensateRelation(LnLoan lnLoan, PartnerEnum partner, Integer relationId, Double thisRepayAmount, Double agreementInterestAmount) {
        // 查询代偿人信息
        BsUserCompensateVO vo = depFixedRepayPaymentService.compensaterInfo(lnLoan.getLoanTime(), partner.getCode());
        if (vo == null) {
            throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND, partner.getCode() + "本金代偿代偿人信息未找到");
        }

        LnCompensateRelation lnCompensateRelation = new LnCompensateRelation();
        lnCompensateRelation.setLoanRelationId(relationId);
        lnCompensateRelation.setCompUserId(vo.getId());
        lnCompensateRelation.setPartnerCode(partner.getCode());
        lnCompensateRelation.setCompHfUserId(vo.getHfUserId());
        lnCompensateRelation.setAmount(MoneyUtil.add(thisRepayAmount, agreementInterestAmount).doubleValue());
        lnCompensateRelation.setPrincipal(thisRepayAmount);
        lnCompensateRelation.setInterest(agreementInterestAmount);
        lnCompensateRelation.setCreateTime(new Date());
        lnCompensateRelation.setUpdateTime(new Date());
        return lnCompensateRelation;
    }

    /**
     * 查询有效的还款计划lnRepaySchedule
     *
     * @param loanId
     * @param lnCompensateDetail
     * @param partner
     * @return
     */
    private LnRepaySchedule getLnRepaySchedule(Integer loanId, LnCompensateDetail lnCompensateDetail, PartnerEnum partner) {
        LnRepayScheduleExample repayScheduleExample = new LnRepayScheduleExample();
        repayScheduleExample.createCriteria().andPartnerRepayIdEqualTo(lnCompensateDetail.getPartnerRepayId())
                .andLoanIdEqualTo(loanId).andSerialIdEqualTo(lnCompensateDetail.getRepaySerial()).andStatusNotEqualTo(Constants.LN_REPAY_CANCELLED);
        List<LnRepaySchedule> repaySchedulList = lnRepayScheduleMapper.selectByExample(repayScheduleExample);
        if (CollectionUtils.isEmpty(repaySchedulList)) {
            logger.info("===========" + partner.getName() + "【代偿还款】还款计划表数据未获得================");
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }
        return repaySchedulList.get(0);
    }

    /**
     * 查询上一笔有效的还款计划lnRepaySchedule是否已还款或已代偿
     *
     * @param loanId
     * @param lnCompensateDetail
     * @param partner
     * @return
     */
    protected void verifyBeforeLnRepayScheduleRepayment(Integer loanId, LnCompensateDetail lnCompensateDetail, PartnerEnum partner) {
        // 还款期数大于第一期，判断前一期还款计划已还款或已代偿
        if (lnCompensateDetail.getRepaySerial() > 1) {
            List<String> values = new ArrayList<>();
            values.add(Constants.LN_REPAY_REPAIED);
            values.add(Constants.LN_REPAY_LATE_NOT);
            values.add(Constants.LN_REPAY_LATE_REPAIED);
            LnRepayScheduleExample repayScheduleExample = new LnRepayScheduleExample();
            repayScheduleExample.createCriteria().andSerialIdEqualTo(lnCompensateDetail.getRepaySerial() - 1)
                    .andLoanIdEqualTo(loanId).andStatusIn(values);
            Long count = lnRepayScheduleMapper.countByExample(repayScheduleExample);
            if (count == 0) {
                logger.info("===========" + partner.getName() + "【代偿还款】前一期还款计划未还款，不能代偿当期================");
                throw new PTMessageException(PTMessageEnum.ZAN_REPAY_TERM_ERROR);
            }
        }
    }

    /**
     * 判断还款计划是否已还款(账单表、还款记录表)
     *
     * @param lnRepaySchedule
     * @return
     */
    private boolean isLnRepayRepayment(LnRepaySchedule lnRepaySchedule) {
        LnRepayExample countExample = new LnRepayExample();
        countExample.createCriteria().andRepayPlanIdEqualTo(lnRepaySchedule.getId()).andLoanIdEqualTo(lnRepaySchedule.getLoanId())
                .andStatusEqualTo(Constants.LN_REPAY_PAY_STATUS_REPAIED);
        Integer count = lnRepayMapper.countByExample(countExample);
        return count != null && count > 0;
    }

    /**
     * 重复还款处理
     *
     * @param lnCompensate
     * @param lnCompensateDetail
     * @param lnRepaySchedule
     */
    private void repeatRepayProcess(LnCompensate lnCompensate, LnCompensateDetail lnCompensateDetail, LnRepaySchedule lnRepaySchedule) {
        Date today = com.pinting.core.util.DateUtil.parseDate(com.pinting.core.util.DateUtil.formatYYYYMMDD(new Date()));
        LnRepeatRepayRecord lnRepeatRepayRecord = new LnRepeatRepayRecord();
        lnRepeatRepayRecord.setPartnerCode(lnCompensate.getPartnerCode());
        lnRepeatRepayRecord.setRepayAmount(lnCompensateDetail.getTotal());
        lnRepeatRepayRecord.setRepayOrderNo(lnCompensate.getOrderNo());
        lnRepeatRepayRecord.setRepayPlanId(lnRepaySchedule.getId());
        lnRepeatRepayRecord.setRepayType(Constants.REPAY_TYPE_COMPENSATE);
        lnRepeatRepayRecord.setReturnAmount(lnCompensateDetail.getTotal());
        lnRepeatRepayRecord.setSettleDate(today); //结算日期
        lnRepeatRepayRecord.setStatus(Constants.REPEAT_REPAY_STATUS_INIT);
        lnRepeatRepayRecord.setCreateTime(new Date());
        lnRepeatRepayRecord.setUpdateTime(new Date());
        lnRepeatRepayRecordMapper.insertSelective(lnRepeatRepayRecord);

        //调用“重复还款入营收账记账”
        depFixedRepayAccountService.repayRepeat2AccRecord(lnRepeatRepayRecord, null);

        //修改代偿明细
        LnCompensateDetail detailTemp = new LnCompensateDetail();
        detailTemp.setId(lnCompensateDetail.getId());
        detailTemp.setStatus(Constants.COMPENSATE_REPAYS_STATUS_REPEAT);
        detailTemp.setUpdateTime(new Date());
        lnCompensateDetailMapper.updateByPrimaryKeySelective(detailTemp);
    }

    /**
     * 计算当期协议本息
     *
     * @param record                record.initAmount：债权匹配/债权转让的初始借款金额;record.firstTerm：首次还款期数
     * @param lnLoan                lnLoan.loanPeriod：借款期数;lnLoan.agreementRate：借款协议费率
     * @param repayScheduleSerialId 还款计划当期序号
     * @param settleInterestAmount  结算利息
     * @return AverageCapitalPlusInterestVO
     */
    protected abstract AverageCapitalPlusInterestVO calAgreementPrincipalInterestAmount(LoanRelation4TransferVO record, LnLoan lnLoan, Integer repayScheduleSerialId, Double settleInterestAmount, Double leftTotalPrincipal);

    /**
     * 计算当期产品利息
     *
     * @param record                record.initAmount：债权匹配/债权转让的初始借款金额;record.firstTerm：首次还款期数;record.baseRate：产品利率
     * @param lnLoan                lnLoan.loanPeriod：借款期数;lnLoan.agreeRate：借款协议费率
     * @param repayScheduleSerialId 还款计划当期序号
     * @param settleInterestAmount  结算利息
     * @return
     */
    protected abstract Double calProductInterestAmount(LoanRelation4TransferVO record, LnLoan lnLoan, Integer repayScheduleSerialId, Double settleInterestAmount, Double leftTotalPrincipal);

    /**
     * 计算当期结算利息
     *
     * @param lnLoan                lnLoan.approveAmount借款金额;lnLoan.loanPeriod：借款期数;lnLoan.settleRate：结算费率
     * @param repayScheduleSerialId 还款计划当期序号
     * @return
     */
    protected abstract AverageCapitalPlusInterestVO calSettleInterestAmount(LnLoan lnLoan, Integer repayScheduleSerialId);

    /**
     * 计算当期借款服务费
     *
     * @param lnLoan                lnLoan.approveAmount借款金额;lnLoan.loanPeriod：借款期数;lnLoan.settleRate：结算费率
     * @param settleInterestAmount  结算利息
     * @param repayScheduleSerialId 还款计划当期序号
     * @return
     */
    protected abstract Double calLoanServiceAmount(LnLoan lnLoan, Integer repayScheduleSerialId, Double settleInterestAmount);

    /**
     * 查询结算费率
     *
     * @return
     */
    protected abstract Double getSysSettleRate();

    /**
     * 查询借款服务费率
     *
     * @return
     */
    protected abstract Double getSysLoanServerRate();
}

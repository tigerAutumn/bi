package com.pinting.business.accounting.loan.service.impl;

import com.pinting.business.accounting.loan.enums.LoanStatus;
import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.loan.model.FinanceRepayInfo;
import com.pinting.business.accounting.loan.model.ReceiveMoneyInfo;
import com.pinting.business.accounting.loan.model.ReceiveMoneyNoticeInfo;
import com.pinting.business.accounting.loan.model.RepayInfo;
import com.pinting.business.accounting.loan.service.FinanceReceiveMoneyService;
import com.pinting.business.accounting.loan.service.RepayAccountService;
import com.pinting.business.accounting.loan.util.redisUtil;
import com.pinting.business.accounting.service.CustomerReceiveMoneyService;
import com.pinting.business.accounting.service.PayOrdersService;
import com.pinting.business.dao.*;
import com.pinting.business.enums.BaoFooEnum;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.enums.RedisLockEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.*;
import com.pinting.business.model.vo.AverageCapitalPlusInterestVO;
import com.pinting.business.model.vo.BsSubAccountVO;
import com.pinting.business.model.vo.LoanNoticeVO;
import com.pinting.business.model.vo.RepayScheduleVO;
import com.pinting.business.service.common.AlgorithmService;
import com.pinting.business.service.site.*;
import com.pinting.business.util.Constants;
import com.pinting.business.util.Util;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.hessian.message.baofoo.B2GReqMsg_BaoFooQuickPay_Pay4Trans;
import com.pinting.gateway.hessian.message.baofoo.B2GResMsg_BaoFooQuickPay_Pay4Trans;
import com.pinting.gateway.hessian.message.pay19.enums.OrderStatus;
import com.pinting.gateway.out.service.BaoFooTransportService;
import com.pinting.gateway.out.service.SMSServiceClient;
import com.pinting.gateway.smsEnums.TemplateKey;
import net.sf.json.JSONObject;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by babyshark on 2016/8/31.
 */
@Service
public class FinanceReceiveMoneyServiceImpl implements FinanceReceiveMoneyService {
    private Logger log = LoggerFactory.getLogger(FinanceReceiveMoneyServiceImpl.class);
    private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private BaoFooTransportService baoFooTransportService;
    @Autowired
    private RepayAccountService repayAccountService;
    @Autowired
    private AlgorithmService algorithmService;
    @Autowired
    private UserService userService;
    @Autowired
    private SubAccountService subAccountService;
    @Autowired
    private CustomerReceiveMoneyService customerReceiveMoneyService;
    @Autowired
    private SMSServiceClient smsServiceClient;
    @Autowired
    private SendWechatService sendWechatService;
    @Autowired
    private SpecialJnlService specialJnlService;
    @Autowired
    private BankCardService bankCardService;
    @Autowired
    private PayOrdersService payOrdersService;
    @Autowired
    private LnRepayScheduleMapper lnRepayScheduleMapper;
    @Autowired
    private BsLoanFinanceRepayMapper bsLoanFinanceRepayMapper;
    @Autowired
    private LnFinanceRepayScheduleMapper lnFinanceRepayScheduleMapper;
    @Autowired
    private LnLoanMapper lnLoanMapper;
    @Autowired
    private LnPartnerChargeRuleMapper lnPartnerChargeRuleMapper;
    @Autowired
    private LnLoanRelationMapper lnLoanRelationMapper;
    @Autowired
    private LnLoanAmountChangeMapper lnLoanAmountChangeMapper;
    @Autowired
    private BsSubAccountMapper bsSubAccountMapper;
    @Autowired
    private BsUserTransDetailMapper bsUserTransDetailMapper;
    @Autowired
    private BsUserMapper bsUserMapper;
    @Autowired
    private LnUserMapper lnUserMapper;
    @Autowired
    private BsPayOrdersMapper bsPayOrdersMapper;
    @Autowired
    private BsPayOrdersJnlMapper bsPayOrdersJnlMapper;
    @Autowired
    private BsDailyInterestMapper bsDailyInterestMapper;
    @Autowired
    private BsPayResultQueueMapper queueMapper;
    @Autowired
    private LnPayOrdersMapper lnPayOrdersMapper;
    @Autowired
    private BsRevenueTransDetailMapper bsRevenueTransDetailMapper;

    /**
     * 实时查询当前所有已逾期的还款计划
     *
     * @return 已逾期的还款计划列表
     */
    @Override
    public List<RepayScheduleVO> queryOverdueRepaySchedules() {
        List<RepayScheduleVO> scheduleVOs = lnRepayScheduleMapper.selectOverdueRepaySchedules();
        if(CollectionUtils.isEmpty(scheduleVOs))
            return null;
        List<String> excludeRepaySchedules = new ArrayList<>();
        //此循环获取列表中多余的、需排除的还款计划编码
        for (RepayScheduleVO scheduleVO: scheduleVOs) {
            String bgwOrderNo = scheduleVO.getBgwOrderNo();
            String partnerRepayId = scheduleVO.getPartnerRepayId();
            //获取不应逾期垫付的记录
            if(StringUtil.isNotEmpty(bgwOrderNo)){
                //如果是还款中状态，并且订单表状态处理中和成功状态，则需排除
                if(LoanStatus.REPAY_STATUS_PAYING.getCode().equals(scheduleVO.getRepayPayStatus())){
                    LnPayOrdersExample example = new LnPayOrdersExample();
                    List<Integer> statuses = new ArrayList<>();
                    statuses.add(Constants.ORDER_STATUS_PAYING);
                    statuses.add(Constants.ORDER_STATUS_SUCCESS);
                    example.createCriteria().andOrderNoEqualTo(bgwOrderNo).andStatusIn(statuses);
                    List<LnPayOrders> orders = lnPayOrdersMapper.selectByExample(example);
                    if(!CollectionUtils.isEmpty(orders)){
                        excludeRepaySchedules.add(partnerRepayId);
                    }
                }
                //如果是已还款状态，直接排除
                else if(LoanStatus.REPAY_STATUS_REPAIED.getCode().equals(scheduleVO.getRepayPayStatus())){
                    excludeRepaySchedules.add(partnerRepayId);
                }
            }
        }
        List<RepayScheduleVO> scheduleRes = new ArrayList<>();
        //此循环获得最终需逾期垫付的列表
        for (RepayScheduleVO scheduleVO: scheduleVOs) {
            String partnerRepayId = scheduleVO.getPartnerRepayId();
            boolean isAdd = true;
            if(!CollectionUtils.isEmpty(excludeRepaySchedules)){
                for (String excludePartnerRepayId: excludeRepaySchedules){
                    if(partnerRepayId.equals(excludePartnerRepayId)){
                        isAdd = false;
                        break;
                    }
                }
            }
            if(isAdd){
                scheduleRes.add(scheduleVO);
            }
        }
        return scheduleRes;
    }

    /**
     * 生成逾期垫付的回款计划
     *
     * @param financeRepayInfo
     */
    @Override
    @Transactional
    public void generateOverdueRepayPlan(FinanceRepayInfo financeRepayInfo) {
        //根据借款编号，查询借贷关系列表
        Integer loanId = financeRepayInfo.getLoanId();
        LnLoanRelationExample relationExample = new LnLoanRelationExample();
        relationExample.createCriteria().andLoanIdEqualTo(loanId);
        List<LnLoanRelation> relations = lnLoanRelationMapper.selectByExample(relationExample);
        LnLoan loan = lnLoanMapper.selectByPrimaryKey(loanId);

        //生成回款计划
        RepayInfo repayPlanInfo = generateRepayPlan(relations, loan.getPeriod(), financeRepayInfo.getRepaySerial(),financeRepayInfo.getRepayScheduleId());
        Double planTotalAmount = repayPlanInfo.getAmount();
        Double planPrincipal = repayPlanInfo.getPrincipal();

        //还款计划表更新状态为逾期未还款
        LnRepaySchedule tempLnRepaySchedule = new LnRepaySchedule();
        tempLnRepaySchedule.setId(financeRepayInfo.getRepayScheduleId());
        tempLnRepaySchedule.setUpdateTime(new Date());
        tempLnRepaySchedule.setStatus(Constants.REPAY_SCHEDULE_STATUS_LATE_NOT);
        lnRepayScheduleMapper.updateByPrimaryKeySelective(tempLnRepaySchedule);

        Double bgwServiceFee = algorithmService.calBGWServiceFee(loanId, financeRepayInfo.getRepaySerial());
        //逾期垫付系统记账
        LnPartnerChargeRule chargeRule = lnPartnerChargeRuleMapper.selectByPrimaryKey(loan.getChargeRuleId());
        RepayInfo repayInfo = new RepayInfo();
        repayInfo.setPartner(PartnerEnum.getEnumByCode(chargeRule.getPartnerCode()));
        repayInfo.setLoanActId(relations.get(0).getLnSubAccountId());
        repayInfo.setAmount(planTotalAmount);
        repayInfo.setPrincipal(planPrincipal);
        //币港湾服务费(不是信息服务费)
        repayInfo.setServiceFee(bgwServiceFee);
        repayInfo.setLnFinancePlanId(financeRepayInfo.getRepayScheduleId());
        repayAccountService.chargeOverdueAdvance(repayInfo);
        //赞分期营收扣除记录
        BsRevenueTransDetail detail=new BsRevenueTransDetail();
        detail.setPartnerCode(chargeRule.getPartnerCode());
        detail.setTransType(Constants.REVENUE_TYPE_OVERDUE_DEDUCT);
        detail.setLoanId(loanId);
        detail.setRepaySerial(financeRepayInfo.getRepaySerial());
        detail.setCommissionFee(0d);
        detail.setCreateTime(new Date());
        detail.setDeposit(0d);
        detail.setRepayAmount(planTotalAmount);
        detail.setRepayId(null);
        detail.setRevenueTransId(null);
        detail.setUpdateTime(new Date());
        detail.setOtherFee(0d);
        detail.setBgwServiceFee(bgwServiceFee);
        detail.setRevenueAmount(-bgwServiceFee);
        bsRevenueTransDetailMapper.insertSelective(detail);
    }

    /**
     * 生成正常还款的回款计划
     *
     * @param financeRepayInfo
     */
    @Override
    @Transactional
    public void generateNormalRepayPlan(FinanceRepayInfo financeRepayInfo) {
        //根据借款编号，查询借贷关系列表
        Integer loanId = financeRepayInfo.getLoanId();
        LnLoanRelationExample relationExample = new LnLoanRelationExample();
        relationExample.createCriteria().andLoanIdEqualTo(loanId);
        List<LnLoanRelation> relations = lnLoanRelationMapper.selectByExample(relationExample);
        LnLoan loan = lnLoanMapper.selectByPrimaryKey(loanId);
        //生成回款计划
        generateRepayPlan(relations, loan.getPeriod(), financeRepayInfo.getRepaySerial(),financeRepayInfo.getRepayScheduleId());
    }

    /**
     * 生成回款计划
     * @param relations 借贷关系列表
     * @param period 总期数
     * @param repaySerial 还款期数
     * @return 返回此笔还款的回款总金额，和回款总本金
     */
    private RepayInfo generateRepayPlan(List<LnLoanRelation> relations, Integer period, Integer repaySerial,Integer lnRepayScheduleId){
        if(relations == null || period == null || repaySerial == null){
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }
        try {
            jsClientDaoSupport.lock(RedisLockEnum.LOCK_USER_RECEIVEMONEY.getKey());
            Double planTotalAmount = 0d;
            Double planPrincipal = 0d;
            //循环借贷关系列表
            for (LnLoanRelation relation : relations) {
            	//判断借贷关系总本金是否为0,0则表示已转让
            	if(relation.getTotalAmount() == 0){
            		continue;
            	}
                //根据理财子帐户编号获得利率等信息，计算利息
                Integer regSubActId = relation.getBsSubAccountId();
                BsSubAccount regSubAct = bsSubAccountMapper.selectByPrimaryKey(regSubActId);
                Double rate = regSubAct.getProductRate();

                LnFinanceRepayScheduleExample financeRepayScheduleExample = new LnFinanceRepayScheduleExample();
                financeRepayScheduleExample.createCriteria().andRelationIdEqualTo(relation.getId())
                        .andRepaySerialEqualTo(repaySerial).andStatusEqualTo(Constants.FINANCE_REPAY_SATAE_INIT);
                List<LnFinanceRepaySchedule> financeRepaySchedules = lnFinanceRepayScheduleMapper.selectByExample(financeRepayScheduleExample);
                
                if(CollectionUtils.isEmpty(financeRepaySchedules) || financeRepaySchedules.size() != 1){
                    throw new PTMessageException(PTMessageEnum.ZAN_REPAY_TERM_ERROR);
                }
                List<AverageCapitalPlusInterestVO> averageVOs = algorithmService
                        .calAverageCapitalPlusInterestPlan(relation.getTotalAmount(), period, rate);
                AverageCapitalPlusInterestVO currAverage = null;
                for (AverageCapitalPlusInterestVO averageVO: averageVOs) {
                    if(repaySerial.equals(averageVO.getRepaySerial())){
                        currAverage = averageVO;
                        break;
                    }
                }
                if(currAverage == null)
                    throw new PTMessageException(PTMessageEnum.ZAN_REPAY_TERM_ERROR);
                //理财人回款计划与实际回款结算比较
                Double subPlanTotal = financeRepaySchedules.get(0).getPlanTotal();
                if(MoneyUtil.subtract(subPlanTotal, currAverage.getPlanTotal()).doubleValue() != 0){
                    log.error(">>>计划回款金额["+ subPlanTotal +"]，回款结算金额["+ currAverage.getPlanTotal() +"]");
                    specialJnlService.warn4Fail(subPlanTotal, "计划回款金额["+ subPlanTotal +"]，回款结算金额["+ currAverage.getPlanTotal() +"]不匹配",
                            null,"还款时回款结算与计划不匹配",false);
                    throw new PTMessageException(PTMessageEnum.ZAN_RETURN_NOT_MATCH);
                }
                //生成逾期垫付回款结算计划
                BsLoanFinanceRepay financeRepayPlan = new BsLoanFinanceRepay();
                financeRepayPlan.setCreateTime(new Date());
                financeRepayPlan.setFnUserId(relation.getBsUserId());
                financeRepayPlan.setFinanceRepayScheduleId(financeRepaySchedules.get(0).getId());
                financeRepayPlan.setInterest(currAverage.getPlanInterest());
                financeRepayPlan.setPrincipal(currAverage.getPlanPrincipal());
                financeRepayPlan.setOrderNo(null);
                financeRepayPlan.setStatus(Constants.FINANCE_REPAY_SATAE_INIT);
                financeRepayPlan.setTotal(currAverage.getPlanTotal());
                financeRepayPlan.setUpdateTime(new Date());
                //回款执行时间为还款计划中的每期应还时间
                LnRepaySchedule tempLnRepaySchedule=lnRepayScheduleMapper.selectByPrimaryKey(lnRepayScheduleId);
                financeRepayPlan.setPlanDate(tempLnRepaySchedule.getPlanDate());
                BsLoanFinanceRepayExample financeRepayExample = new BsLoanFinanceRepayExample();
                financeRepayExample.createCriteria().andFinanceRepayScheduleIdEqualTo(financeRepayPlan.getFinanceRepayScheduleId());
                List<BsLoanFinanceRepay> financeRepays = bsLoanFinanceRepayMapper.selectByExample(financeRepayExample);
                if(CollectionUtils.isEmpty(financeRepays))
                    bsLoanFinanceRepayMapper.insertSelective(financeRepayPlan);
                //更新理财人还款计划表
                LnFinanceRepaySchedule tempFinanceRepayPlan = new LnFinanceRepaySchedule();
                tempFinanceRepayPlan.setId(financeRepaySchedules.get(0).getId());
                tempFinanceRepayPlan.setStatus(Constants.FINANCE_REPAY_SATAE_REPAYING);
                tempFinanceRepayPlan.setUpdateTime(new Date());
                lnFinanceRepayScheduleMapper.updateByPrimaryKeySelective(tempFinanceRepayPlan);

                planTotalAmount = MoneyUtil.add(planTotalAmount, currAverage.getPlanTotal()).doubleValue();
                planPrincipal = MoneyUtil.add(planPrincipal, currAverage.getPlanPrincipal()).doubleValue();
            }
            RepayInfo repayInfo = new RepayInfo();
            repayInfo.setAmount(planTotalAmount);
            repayInfo.setPrincipal(planPrincipal);
            return repayInfo;
        } finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_USER_RECEIVEMONEY.getKey());
        }
    }

    /**
     * 理财人单笔回款（回款到余额）
     *
     * @param loanFinanceRepay
     */
    @Override
    public void receiveMoney2Balance(final BsLoanFinanceRepay loanFinanceRepay) {
        try {
            jsClientDaoSupport.lock(RedisLockEnum.LOCK_USER_RECEIVEMONEY.getKey());
            log.info("========={单笔客户回款}开始=========");
            BsLoanFinanceRepay returnCheck = bsLoanFinanceRepayMapper.selectByPrimaryKey(loanFinanceRepay.getId());
            if(Constants.FINANCE_REPAY_SATAE_REPAYING.equals(returnCheck.getStatus()) ||
                    Constants.FINANCE_REPAY_SATAE_REPAIED.equals(returnCheck.getStatus())){
                log.warn("========={单笔客户回款}回款编号["+loanFinanceRepay.getId()+"]已回款或处理中，不需重复处理=========");
                return;
            }
            try {
                //查询用户信息
                final Integer fnUserId = loanFinanceRepay.getFnUserId();
                final BsUser fnUser = userService.findUserById(fnUserId);
                //查询回款计划及借贷关系
                final LnFinanceRepaySchedule financeRepaySchedule = lnFinanceRepayScheduleMapper.selectByPrimaryKey(loanFinanceRepay.getFinanceRepayScheduleId());
                final LnLoanRelation loanRelation = lnLoanRelationMapper.selectByPrimaryKey(financeRepaySchedule.getRelationId());
                final Integer subAccountId = loanRelation.getBsSubAccountId();
                //产品户信息
                final BsSubAccountVO productAccount = subAccountService.findMyInvestByUserIdAndId(fnUserId, subAccountId);
                log.info("========={单笔客户回款}回款到余额开始=========");
                final Integer detailId = loanFinanceRepay.getId();
                final Double amount = loanFinanceRepay.getTotal();
                final Double productPrincipal = loanFinanceRepay.getPrincipal();
                final Double productInterest = loanFinanceRepay.getInterest();
                transactionTemplate.execute(new TransactionCallbackWithoutResult(){
                    @Override
                    protected void doInTransactionWithoutResult(TransactionStatus status) {
                        //待回款分期产品明细表状态设为回款成功
                        Date now = new Date();
                        BsLoanFinanceRepay updateDetail = new BsLoanFinanceRepay();
                        updateDetail.setId(detailId);
                        updateDetail.setStatus(Constants.FINANCE_REPAY_SATAE_REPAIED);
                        updateDetail.setUpdateTime(now);
                        bsLoanFinanceRepayMapper.updateByPrimaryKeySelective(updateDetail);
                        //回款计划表状态改为回款成功
                        LnFinanceRepaySchedule updateRepaySchedule = new LnFinanceRepaySchedule();
                        updateRepaySchedule.setId(loanFinanceRepay.getFinanceRepayScheduleId());
                        updateRepaySchedule.setStatus(Constants.FINANCE_REPAY_SATAE_REPAIED);
                        updateRepaySchedule.setDoneTime(now);
                        updateRepaySchedule.setUpdateTime(now);
                        lnFinanceRepayScheduleMapper.updateByPrimaryKeySelective(updateRepaySchedule);

                        //记录用户交易明细
                        BsUserTransDetail transDetail = new BsUserTransDetail();
                        transDetail.setUserId(fnUserId);
                        transDetail.setCardNo(null);
                        transDetail.setTransType(Constants.Trans_TYPE_ZAN_RETURN);
                        transDetail.setTransStatus(Constants.Trans_STATUS_SUCCESS);
                        transDetail.setOrderNo(null);
                        transDetail.setCreateTime(now);
                        transDetail.setAmount(amount);
                        transDetail.setUpdateTime(now);
                        bsUserTransDetailMapper.insertSelective(transDetail);
                        //用户表累计收益增加，可提现金额增加
                        BsUser userUpdate = new BsUser();
                        userUpdate.setId(fnUserId);
                        userUpdate.setCanWithdraw(amount);
                        userUpdate.setTotalInterest(productInterest);
                        bsUserMapper.updateUserAmountInfoById(userUpdate);
                        //当日利息表记录一条利息数据
                        BsDailyInterest dailyInterest = new BsDailyInterest();
                        dailyInterest.setUserId(fnUser.getId());
                        dailyInterest.setInterest(productInterest);
                        dailyInterest.setTime(now);
                        bsDailyInterestMapper.insertSelective(dailyInterest);
                        //登记该条回款记录
                        BsCustomerReceiveMoney receiveMoney = new BsCustomerReceiveMoney();
                        receiveMoney.setAmountBase(productPrincipal);
                        receiveMoney.setAmountInterest(productInterest);
                        receiveMoney.setBankNo(null);
                        receiveMoney.setCardNo(fnUser.getIdCard());
                        receiveMoney.setCreateTime(now);
                        receiveMoney.setCustomerName(fnUser.getUserName());
                        receiveMoney.setOrderNo(null);
                        receiveMoney.setProductCode(String.valueOf(productAccount.getProductId()));
                        receiveMoney.setStatus(0);
                        receiveMoney.setSuccessTime(now);
                        customerReceiveMoneyService.addCustomerReceiveMoney(receiveMoney);
                        //更新借贷关系还款金额，记录借贷变动表
                        LnLoanRelation tempLoanRelation = new LnLoanRelation();
                        tempLoanRelation.setId(loanRelation.getId());
                        tempLoanRelation.setUpdateTime(now);
                        tempLoanRelation.setLeftAmount(MoneyUtil.defaultRound(MoneyUtil.subtract(loanRelation.getLeftAmount(), productPrincipal)).doubleValue());
                        if(tempLoanRelation.getLeftAmount() == 0){
                            tempLoanRelation.setStatus(Constants.LOAN_RELATION_STATUS_REPAID);
                        }
                        lnLoanRelationMapper.updateByPrimaryKeySelective(tempLoanRelation);
                        LnLoanAmountChange tempAmountChange = new LnLoanAmountChange();
                        tempAmountChange.setUpdateTime(now);
                        tempAmountChange.setAfterAmount(tempLoanRelation.getLeftAmount());
                        tempAmountChange.setBeforeAmount(loanRelation.getLeftAmount());
                        tempAmountChange.setChangeAmount(productPrincipal);
                        tempAmountChange.setCreateTime(now);
                        tempAmountChange.setRelationId(loanRelation.getId());
                        lnLoanAmountChangeMapper.insertSelective(tempAmountChange);

                        //记账
                        LnUser lnUser = lnUserMapper.selectByPrimaryKey(loanRelation.getLnUserId());
                        ReceiveMoneyInfo receiveMoneyInfo = new ReceiveMoneyInfo();
                        receiveMoneyInfo.setAmount(loanFinanceRepay.getTotal());
                        receiveMoneyInfo.setPrincipal(loanFinanceRepay.getPrincipal());
                        receiveMoneyInfo.setInterest(loanFinanceRepay.getInterest());
                        receiveMoneyInfo.setPartner(PartnerEnum.getEnumByCode(lnUser.getPartnerCode()));
                        receiveMoneyInfo.setInvestorRegActId(loanRelation.getBsSubAccountId());
                        receiveMoneyInfo.setInvestorUserId(fnUserId);
                        receiveMoneyInfo.setLnFinancePlanId(financeRepaySchedule.getId());
                        repayAccountService.chargeReceiveMoney2Balance(receiveMoneyInfo);

                    }
                });
               /* smsServiceClient.sendTemplateMessage(fnUser.getMobile(), TemplateKey.RETURN_SUCCESS2BALANCE,
                        String.valueOf(MoneyUtil.add(productPrincipal, productInterest).doubleValue()), productPrincipal.toString(), productInterest.toString());
                //发送微信模板消息
                sendWechatService.paymentMgs2Balance(fnUserId,"",
                        String.valueOf(MoneyUtil.add(productPrincipal, productInterest).doubleValue()), productPrincipal.toString(), productInterest.toString());*/
                log.info("========={单笔客户回款}结束=========");
            } catch (Exception e) {
                log.info("========={单笔客户回款}回款结算记录编号["+loanFinanceRepay.getId()+"]回款产生异常=========", e);
                //告警
                specialJnlService.warn4Fail(loanFinanceRepay.getTotal(), "{单笔客户回款}回款结算记录编号["+loanFinanceRepay.getId()+"]回款产生异常",
                        null,"分期产品单笔回款到余额",false);
            }
        } finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_USER_RECEIVEMONEY.getKey());
        }
    }

    /**
     * 理财人单笔回款（回款到卡）
     *
     * @param loanFinanceRepay
     */
    @Override
    public void receiveMoney2Card(BsLoanFinanceRepay loanFinanceRepay) {
        try {
            jsClientDaoSupport.lock(RedisLockEnum.LOCK_USER_RECEIVEMONEY.getKey());
            log.info("========={单笔客户回款}开始=========");
            BsLoanFinanceRepay returnCheck = bsLoanFinanceRepayMapper.selectByPrimaryKey(loanFinanceRepay.getId());
            if(Constants.FINANCE_REPAY_SATAE_REPAYING.equals(returnCheck.getStatus()) ||
                    Constants.FINANCE_REPAY_SATAE_REPAIED.equals(returnCheck.getStatus())){
                log.warn("========={单笔客户回款}回款编号["+loanFinanceRepay.getId()+"]已回款或处理中，不需重复处理=========");
                return;
            }
            BsPayOrders order = new BsPayOrders();
            BsPayOrdersJnl orderJnl = new BsPayOrdersJnl();
            try {
                //查询用户信息
                final Integer fnUserId = loanFinanceRepay.getFnUserId();
                final BsUser fnUser = userService.findUserById(fnUserId);
                //查询回款计划及借贷关系
                LnFinanceRepaySchedule financeRepaySchedule = lnFinanceRepayScheduleMapper.selectByPrimaryKey(loanFinanceRepay.getFinanceRepayScheduleId());
                final LnLoanRelation loanRelation = lnLoanRelationMapper.selectByPrimaryKey(financeRepaySchedule.getRelationId());
                final Integer subAccountId = loanRelation.getBsSubAccountId();
                //产品户信息
                final BsSubAccountVO productAccount = subAccountService.findMyInvestByUserIdAndId(fnUserId, subAccountId);
                final Integer detailId = loanFinanceRepay.getId();
                final Double amount = loanFinanceRepay.getTotal();
                final Double productPrincipal = loanFinanceRepay.getPrincipal();
                final Double productInterest = loanFinanceRepay.getInterest();
                log.info("========={单笔客户回款}回款到卡开始=========");
                //查询回款卡
                BsBankCard receiveCard = bankCardService.findFirstBankCardByUserId(fnUserId);
                //订单表插入
                String orderNo = Util.generateOrderNo4Pay19(productAccount.getId());
                Date now = new Date();
                order.setAccountType(Constants.ORDER_ACCOUNT_TYPE_USER);
                order.setAmount(amount);//回款金额为本金+收益
                order.setBankCardNo(receiveCard.getCardNo());
                order.setBankId(receiveCard.getBankId());
                order.setBankName(receiveCard.getBankName());
                order.setChannel(Constants.CHANNEL_BAOFOO);
                order.setChannelTransType(Constants.CHANNEL_TRS_DF);
                order.setCreateTime(now);
                order.setIdCard(receiveCard.getIdCard());
                order.setMobile(receiveCard.getMobile());
                order.setMoneyType(Constants.ORDER_CURRENCY_CNY);
                order.setOrderNo(orderNo);
                order.setStatus(Constants.ORDER_STATUS_CREATE);
                order.setSubAccountId(subAccountId);
                order.setTransType(Constants.TRANS_RETURN_REG_D_2_USER_BANK_CARD);
                order.setUpdateTime(now);
                order.setUserId(fnUserId);
                order.setUserName(receiveCard.getCardOwner());
                bsPayOrdersMapper.insertSelective(order);
                //订单明细表插入
                orderJnl.setCreateTime(now);
                orderJnl.setOrderId(order.getId());
                orderJnl.setOrderNo(order.getOrderNo());
                orderJnl.setSubAccountCode(productAccount.getCode());
                orderJnl.setSubAccountId(subAccountId);
                orderJnl.setSysTime(now);
                orderJnl.setTransAmount(amount);
                orderJnl.setTransCode(Constants.TRANS_RETURN_REG_D_2_USER_BANK_CARD);
                orderJnl.setUserId(fnUserId);
                bsPayOrdersJnlMapper.insertSelective(orderJnl);

                //发起代付接口进行回款
                /**
                 * 宝付代付回款
                 */
                B2GReqMsg_BaoFooQuickPay_Pay4Trans req = new B2GReqMsg_BaoFooQuickPay_Pay4Trans();
                req.setTrans_no(order.getOrderNo());
                req.setTrans_money(String.valueOf(amount));
                req.setTo_acc_no(receiveCard.getCardNo());
                req.setTo_acc_name(receiveCard.getCardOwner());
                req.setTo_bank_name(BaoFooEnum.pay4BankMap.get(String.valueOf(receiveCard.getBankId())));
                req.setTrans_card_id(receiveCard.getIdCard());
                req.setTrans_mobile(receiveCard.getMobile());
                req.setTrans_summary("分期产品回款");
                B2GResMsg_BaoFooQuickPay_Pay4Trans res = null;
                try {
                    res = baoFooTransportService.pay4Trans(req);
                    //mock
                    /*res = new B2GResMsg_BaoFooQuickPay_Pay4Trans();
                    res.setResCode(ConstantUtil.BSRESCODE_SUCCESS);*/
                } catch (Exception e) {
                    throw new PTMessageException(PTMessageEnum.SYSTEM_ERROR);
                }
                //代付下单成功，直接调用成功处理
                if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())){
                    //更新订单表
                    BsPayOrders updateOrder = new BsPayOrders();
                    updateOrder.setId(order.getId());
                    updateOrder.setStatus(Constants.ORDER_STATUS_PAYING);

                    String thirdReturnCode = "";
                    if (res.getExtendMap()!= null) {
                        thirdReturnCode =  (String)res.getExtendMap().get("thirdReturnCode");

                    }else {
                        thirdReturnCode = res.getResCode();
                    }

                    updateOrder.setReturnCode(thirdReturnCode);
                    updateOrder.setReturnMsg(res.getRes_Msg());
                    updateOrder.setUpdateTime(new Date());
                    bsPayOrdersMapper.updateByPrimaryKeySelective(updateOrder);
                    //待回款产品明细表状态设为处理中
                    BsLoanFinanceRepay updateDetail = new BsLoanFinanceRepay();
                    updateDetail.setId(loanFinanceRepay.getId());
                    updateDetail.setOrderNo(order.getOrderNo());
                    updateDetail.setStatus(Constants.FINANCE_REPAY_SATAE_REPAYING);
                    updateDetail.setUpdateTime(new Date());
                    bsLoanFinanceRepayMapper.updateByPrimaryKeySelective(updateDetail);

                    ReceiveMoneyNoticeInfo succReq = new ReceiveMoneyNoticeInfo();
                    succReq.setAmount(Double.valueOf(res.getTrans_money()));
                    succReq.setFinishTime(new Date());//宝付代付接口同步成功时 无完成时间，此处只能用本地服务时间
                    succReq.setMxOrderId(order.getOrderNo());
                    succReq.setOrderStatus(OrderStatus.SUCCESS);
                    succReq.setRetCode(res.getRes_Code());
                    notifyReceiveMoney2CardResult(succReq);
                }
                //代付下单申请成功，等待异步处理
                else if("920003".equals(res.getResCode())){
                    //更新订单表
                    BsPayOrders updateOrder = new BsPayOrders();
                    updateOrder.setId(order.getId());
                    updateOrder.setStatus(Constants.ORDER_STATUS_PAYING);
                    
                    String thirdReturnCode = "";
                    if (res.getExtendMap()!= null) {
                        thirdReturnCode =  (String)res.getExtendMap().get("thirdReturnCode");

                    }else {
                        thirdReturnCode = res.getResCode();
                    }

                    updateOrder.setReturnCode(thirdReturnCode);
                    updateOrder.setReturnMsg(res.getRes_Msg());
                    updateOrder.setUpdateTime(new Date());
                    bsPayOrdersMapper.updateByPrimaryKeySelective(updateOrder);
                    //待回款产品明细表状态设为处理中
                    BsLoanFinanceRepay updateDetail = new BsLoanFinanceRepay();
                    updateDetail.setId(loanFinanceRepay.getId());
                    updateDetail.setOrderNo(order.getOrderNo());
                    updateDetail.setStatus(Constants.FINANCE_REPAY_SATAE_REPAYING);
                    updateDetail.setUpdateTime(new Date());
                    bsLoanFinanceRepayMapper.updateByPrimaryKeySelective(updateDetail);
                    
                    //放到redis中
                    LoanNoticeVO vo = new LoanNoticeVO();
                    vo.setPayOrderNo(order.getOrderNo());
                    vo.setChannel(order.getChannel());
                    vo.setChannelTransType(Constants.CHANNEL_TRS_DF);
                    vo.setTransType(order.getTransType());
                    vo.setStatus(Constants.ORDER_STATUS_PAYING);
                    vo.setAmount(order.getAmount().toString());
                    redisUtil.rpushRedis(vo); 
                    
                    //并插入到消息队列表中
                    BsPayResultQueue queue = new BsPayResultQueue();
                    queue.setChannel(Constants.ORDER_CHANNEL_BAOFOO);
                    queue.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DF.getCode());
                    queue.setCreateTime(new Date());
                    queue.setDealNum(0);
                    queue.setOrderNo(order.getOrderNo());
                    queue.setStatus(LoanStatus.NOTICE_STATUS_INIT.getCode());
                    queue.setTransType(order.getTransType());
                    queue.setUpdateTime(new Date());
                    queueMapper.insertSelective(queue);
                }
                //代付下单申请失败
                else{
                    //更新订单表，记录订单流水表
                    BsPayOrders updateOrder = new BsPayOrders();
                    updateOrder.setId(order.getId());
                    updateOrder.setStatus(Constants.ORDER_STATUS_FAIL);
                    
                    String thirdReturnCode = "";
                    if (res.getExtendMap()!= null) {
                        thirdReturnCode =  (String)res.getExtendMap().get("thirdReturnCode");

                    }else {
                        thirdReturnCode = res.getResCode();
                    }

                    updateOrder.setReturnCode(thirdReturnCode);
                    
                    updateOrder.setReturnMsg(res.getRes_Msg());
                    updateOrder.setUpdateTime(new Date());
                    bsPayOrdersMapper.updateByPrimaryKeySelective(updateOrder);
                    BsPayOrdersJnl insertOrderJnl = new BsPayOrdersJnl();
                    insertOrderJnl.setCreateTime(new Date());
                    insertOrderJnl.setOrderId(order.getId());
                    insertOrderJnl.setOrderNo(order.getOrderNo());
                    insertOrderJnl.setSubAccountCode(productAccount.getCode());
                    insertOrderJnl.setSubAccountId(subAccountId);
                    insertOrderJnl.setSysTime(new Date());
                    insertOrderJnl.setTransAmount(amount);
                    insertOrderJnl.setTransCode(Constants.TRANS_RETURN_REG_D_2_USER_BANK_CARD);
                    insertOrderJnl.setUserId(fnUserId);
                    insertOrderJnl.setReturnCode(thirdReturnCode);
                    insertOrderJnl.setReturnMsg(res.getResMsg());
                    bsPayOrdersJnlMapper.insertSelective(insertOrderJnl);
                    //待回款产品明细表状态设为失败
                    BsLoanFinanceRepay updateDetail = new BsLoanFinanceRepay();
                    updateDetail.setId(loanFinanceRepay.getId());
                    updateDetail.setOrderNo(order.getOrderNo());
                    updateDetail.setStatus(Constants.FINANCE_REPAY_SATAE_FAIL);
                    updateDetail.setUpdateTime(new Date());
                    bsLoanFinanceRepayMapper.updateByPrimaryKeySelective(updateDetail);
                    //告警
                    specialJnlService.warn4Fail(order.getAmount(), "{单笔客户回款}回款编号["+loanFinanceRepay.getId()+"]回款产生异常",
                            order.getOrderNo(),"分期产品单笔回款到卡",false);
                }
                log.info("========={单笔客户回款}结束=========");
            } catch (Exception e) {
                log.info("========={单笔客户回款}回款编号["+loanFinanceRepay.getId()+"]回款产生异常=========", e);
                if(e instanceof PTMessageException){
                    //待回款产品明细表状态设为回款失败
                    BsLoanFinanceRepay updateDetail = new BsLoanFinanceRepay();
                    updateDetail.setId(loanFinanceRepay.getId());
                    updateDetail.setOrderNo(order.getOrderNo());
                    updateDetail.setStatus(Constants.FINANCE_REPAY_SATAE_FAIL);
                    updateDetail.setUpdateTime(new Date());
                    bsLoanFinanceRepayMapper.updateByPrimaryKeySelective(updateDetail);

                    PTMessageException pte = (PTMessageException) e;
                    //更新订单表，记录订单流水表
                    BsPayOrders updateOrder = new BsPayOrders();
                    updateOrder.setId(order.getId());
                    updateOrder.setStatus(Constants.ORDER_STATUS_CON_FAIL);
                    updateOrder.setReturnCode(pte.getErrCode());
                    updateOrder.setReturnMsg(pte.getErrMessage()+":通讯失败");
                    updateOrder.setUpdateTime(new Date());
                    bsPayOrdersMapper.updateByPrimaryKeySelective(updateOrder);
                    BsPayOrdersJnl insertOrderJnl = new BsPayOrdersJnl();
                    insertOrderJnl.setCreateTime(new Date());
                    insertOrderJnl.setOrderId(order.getId());
                    insertOrderJnl.setOrderNo(order.getOrderNo());
                    insertOrderJnl.setSubAccountCode(orderJnl.getSubAccountCode());
                    insertOrderJnl.setSubAccountId(orderJnl.getSubAccountId());
                    insertOrderJnl.setSysTime(new Date());
                    insertOrderJnl.setTransAmount(orderJnl.getTransAmount());
                    insertOrderJnl.setTransCode(Constants.TRANS_RETURN_REG_D_2_USER_BANK_CARD);
                    insertOrderJnl.setUserId(orderJnl.getUserId());
                    insertOrderJnl.setReturnCode(pte.getErrCode());
                    insertOrderJnl.setReturnMsg(pte.getErrMessage()+":通讯失败");
                    bsPayOrdersJnlMapper.insertSelective(insertOrderJnl);
                }

                //告警
                specialJnlService.warn4Fail(order.getAmount(), "{单笔客户回款}回款编号["+loanFinanceRepay.getId()+"]回款产生异常",
                        order.getOrderNo(),"单笔客户回款",false);
            }
        } finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_USER_RECEIVEMONEY.getKey());
        }
    }

    /**
     * 通知理财人单笔回款结果（回款到卡）
     *
     * @param notice
     */
    @Override
    public void notifyReceiveMoney2CardResult(final ReceiveMoneyNoticeInfo notice) {
        try {
            //jsClientDaoSupport.lock(RedisLockEnum.LOCK_USER_RECEIVEMONEY.getKey());
            log.info("========={用户回款到卡通知}用户回款到卡通知处理开始=========");
            //检查是否是否重复通知（根据订单信息是否是处理中，如果不是，则已经处理过，拒绝再次处理）
            String orderNo = notice.getMxOrderId();
            final BsPayOrders order = payOrdersService.findOrderByOrderNo(orderNo);
            if(order.getStatus() != Constants.ORDER_STATUS_PAYING){//订单状态已经被修改，此次通知认为是重复通知，不做处理
                //告警
                //specialJnlService.warn4Fail(order.getAmount(), "{用户回款到卡通知}订单号["+orderNo+"]重复通知",order.getOrderNo(),"用户回款到卡通知",false);
                log.warn("========={用户回款到卡通知}订单号["+orderNo+"]重复通知=========");
                return;
            }
            BsLoanFinanceRepayExample loanFinanceRepayExample = new BsLoanFinanceRepayExample();
            loanFinanceRepayExample.createCriteria().andOrderNoEqualTo(orderNo);
            final List<BsLoanFinanceRepay> loanFinanceRepays = bsLoanFinanceRepayMapper.selectByExample(loanFinanceRepayExample);
            //回款金额
            final Double amount = order.getAmount();
            final Double productPrincipal = loanFinanceRepays.get(0).getPrincipal();
            final Double productInterest = loanFinanceRepays.get(0).getInterest();
            final BsUser user = userService.findUserById(order.getUserId());
            String mobile = user.getMobile();
            final BsSubAccount proSubAccount = subAccountService.findSubAccountById(order.getSubAccountId());
            //登记该条回款通知
            BsCustomerReceiveMoney receiveMoney = new BsCustomerReceiveMoney();
            receiveMoney.setAmountBase(productPrincipal);
            receiveMoney.setAmountInterest(productInterest);
            receiveMoney.setBankNo(order.getBankCardNo());
            receiveMoney.setCardNo(order.getIdCard());
            receiveMoney.setCreateTime(new Date());
            receiveMoney.setCustomerName(order.getUserName());
            receiveMoney.setOrderNo(order.getOrderNo());
            receiveMoney.setProductCode(String.valueOf(proSubAccount.getProductId()));
            receiveMoney.setStatus(OrderStatus.SUCCESS.equals(notice.getOrderStatus())?0:1);
            receiveMoney.setSuccessTime(notice.getFinishTime());
            customerReceiveMoneyService.addCustomerReceiveMoney(receiveMoney);
            //回款到卡成功
            if(OrderStatus.SUCCESS.equals(notice.getOrderStatus())) {
                log.info("========={用户回款到卡通知}用户回款到卡通知结果成功！=========");
                transactionTemplate.execute(new TransactionCallbackWithoutResult(){
                    @Override
                    protected void doInTransactionWithoutResult(TransactionStatus status) {
                        //待回款产品明细表状态设为成功
                        BsLoanFinanceRepay updateDetail = new BsLoanFinanceRepay();
                        updateDetail.setId(loanFinanceRepays.get(0).getId());
                        updateDetail.setStatus(Constants.FINANCE_REPAY_SATAE_REPAIED);
                        updateDetail.setUpdateTime(new Date());
                        bsLoanFinanceRepayMapper.updateByPrimaryKeySelective(updateDetail);
                        //回款计划表状态改为回款成功
                        LnFinanceRepaySchedule updateRepaySchedule = new LnFinanceRepaySchedule();
                        updateRepaySchedule.setId(loanFinanceRepays.get(0).getFinanceRepayScheduleId());
                        updateRepaySchedule.setStatus(Constants.FINANCE_REPAY_SATAE_REPAIED);
                        updateRepaySchedule.setUpdateTime(new Date());
                        updateRepaySchedule.setDoneTime(new Date());
                        lnFinanceRepayScheduleMapper.updateByPrimaryKeySelective(updateRepaySchedule);
                        //更新订单表，记录订单流水表
                        BsPayOrders updateOrder = new BsPayOrders();
                        updateOrder.setId(order.getId());
                        updateOrder.setStatus(Constants.ORDER_STATUS_SUCCESS);
                        updateOrder.setReturnCode(notice.getRetCode());
                        updateOrder.setReturnMsg(notice.getErrorMsg());
                        updateOrder.setUpdateTime(new Date());
                        bsPayOrdersMapper.updateByPrimaryKeySelective(updateOrder);
                        BsPayOrdersJnl insertOrderJnl = new BsPayOrdersJnl();
                        insertOrderJnl.setCreateTime(new Date());
                        insertOrderJnl.setOrderId(order.getId());
                        insertOrderJnl.setOrderNo(order.getOrderNo());
                        insertOrderJnl.setSubAccountCode(proSubAccount.getCode());
                        insertOrderJnl.setSubAccountId(order.getSubAccountId());
                        insertOrderJnl.setSysTime(new Date());
                        insertOrderJnl.setTransAmount(order.getAmount());
                        insertOrderJnl.setTransCode(Constants.TRANS_RETURN_REG_D_2_USER_BANK_CARD);
                        insertOrderJnl.setUserId(order.getUserId());
                        insertOrderJnl.setReturnCode(notice.getRetCode());
                        insertOrderJnl.setReturnMsg(notice.getErrorMsg());
                        bsPayOrdersJnlMapper.insertSelective(insertOrderJnl);
                        //记录用户交易明细
                        BsUserTransDetail transDetail = new BsUserTransDetail();
                        transDetail.setUserId(order.getUserId());
                        transDetail.setCardNo(order.getBankCardNo());
                        transDetail.setTransType(Constants.Trans_TYPE_RETURN);
                        transDetail.setTransStatus(Constants.Trans_STATUS_SUCCESS);
                        transDetail.setOrderNo(order.getOrderNo());
                        transDetail.setCreateTime(new Date());
                        transDetail.setAmount(order.getAmount());
                        transDetail.setUpdateTime(new Date());
                        bsUserTransDetailMapper.insertSelective(transDetail);
                        BsUserTransDetail transDetail2 = new BsUserTransDetail();
                        transDetail2.setUserId(order.getUserId());
                        transDetail2.setCardNo(order.getBankCardNo());
                        transDetail2.setTransType(Constants.Trans_TYPE_WITHDRAW);
                        transDetail2.setTransStatus(Constants.Trans_STATUS_SUCCESS);
                        transDetail2.setOrderNo(order.getOrderNo());
                        transDetail2.setCreateTime(new Date());
                        transDetail2.setAmount(-order.getAmount());
                        transDetail2.setUpdateTime(new Date());
                        bsUserTransDetailMapper.insertSelective(transDetail2);
                        //用户表累计收益增加
                        BsUser userUpdate = new BsUser();
                        userUpdate.setId(user.getId());
                        userUpdate.setTotalInterest(productInterest);
                        bsUserMapper.updateUserAmountInfoById(userUpdate);
                        //当日利息表记录一条利息数据
                        BsDailyInterest dailyInterest = new BsDailyInterest();
                        dailyInterest.setUserId(order.getUserId());
                        dailyInterest.setInterest(productInterest);
                        dailyInterest.setTime(new Date());
                        bsDailyInterestMapper.insertSelective(dailyInterest);
                        //更新借贷关系还款金额，记录借贷变动表
                        LnFinanceRepaySchedule financeRepaySchedule = lnFinanceRepayScheduleMapper.selectByPrimaryKey(loanFinanceRepays.get(0).getFinanceRepayScheduleId());
                        final LnLoanRelation loanRelation = lnLoanRelationMapper.selectByPrimaryKey(financeRepaySchedule.getRelationId());
                        LnLoanRelation tempLoanRelation = new LnLoanRelation();
                        tempLoanRelation.setId(loanRelation.getId());
                        tempLoanRelation.setUpdateTime(new Date());
                        tempLoanRelation.setLeftAmount(MoneyUtil.subtract(loanRelation.getLeftAmount(), productPrincipal).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
                        if(tempLoanRelation.getLeftAmount() == 0){
                            tempLoanRelation.setStatus(Constants.LOAN_RELATION_STATUS_REPAID);
                        }
                        lnLoanRelationMapper.updateByPrimaryKeySelective(tempLoanRelation);
                        LnLoanAmountChange tempAmountChange = new LnLoanAmountChange();
                        tempAmountChange.setUpdateTime(new Date());
                        tempAmountChange.setAfterAmount(tempLoanRelation.getLeftAmount());
                        tempAmountChange.setBeforeAmount(loanRelation.getLeftAmount());
                        tempAmountChange.setChangeAmount(productPrincipal);
                        tempAmountChange.setCreateTime(new Date());
                        tempAmountChange.setRelationId(loanRelation.getId());
                        lnLoanAmountChangeMapper.insertSelective(tempAmountChange);

                        //记账
                        LnUser lnUser = lnUserMapper.selectByPrimaryKey(loanRelation.getLnUserId());
                        ReceiveMoneyInfo receiveMoneyInfo = new ReceiveMoneyInfo();
                        receiveMoneyInfo.setAmount(amount);
                        receiveMoneyInfo.setPrincipal(productPrincipal);
                        receiveMoneyInfo.setInterest(productInterest);
                        receiveMoneyInfo.setPartner(PartnerEnum.getEnumByCode(lnUser.getPartnerCode()));
                        receiveMoneyInfo.setInvestorRegActId(loanRelation.getBsSubAccountId());
                        receiveMoneyInfo.setInvestorUserId(user.getId());
                        repayAccountService.chargeReceiveMoney2Card(receiveMoneyInfo);
                    }
                });
                String bankCardNo = order.getBankCardNo();
                if(StringUtil.isEmpty(bankCardNo)){
                    BsBankCard card = bankCardService.findFirstBankCardByUserId(order.getUserId());
                    bankCardNo = card!=null?card.getCardNo().substring(card.getCardNo().length()-4,card.getCardNo().length()):"";
                }else{
                    bankCardNo = bankCardNo.substring(bankCardNo.length()-4,bankCardNo.length());
                }
                smsServiceClient.sendTemplateMessage(mobile, TemplateKey.RETURN_SUCCESS2CARD, bankCardNo,
                        String.valueOf(MoneyUtil.add(productPrincipal.doubleValue(), productInterest.doubleValue()).doubleValue()),
                        productPrincipal.toString(), productInterest.toString());

                //发送微信模板消息
                sendWechatService.paymentMgs2Card(order.getUserId(),"",
                        String.valueOf(MoneyUtil.add(productPrincipal.doubleValue(), productInterest.doubleValue()).doubleValue()),
                        productPrincipal.toString(), productInterest.toString(), bankCardNo);
            }
            //回款到卡失败
            else{
                log.info("========={用户回款到卡通知}用户回款到卡通知结果失败！=========");
                //待回款产品明细表状态设为失败
                BsLoanFinanceRepay updateDetail = new BsLoanFinanceRepay();
                updateDetail.setId(loanFinanceRepays.get(0).getId());
                updateDetail.setStatus(Constants.FINANCE_REPAY_SATAE_FAIL);
                updateDetail.setUpdateTime(new Date());
                bsLoanFinanceRepayMapper.updateByPrimaryKeySelective(updateDetail);
                //更新订单表，记录订单流水表
                BsPayOrders updateOrder = new BsPayOrders();
                updateOrder.setId(order.getId());
                updateOrder.setStatus(Constants.ORDER_STATUS_FAIL);
                updateOrder.setReturnCode(notice.getRetCode());
                updateOrder.setReturnMsg(notice.getErrorMsg());
                updateOrder.setUpdateTime(new Date());
                bsPayOrdersMapper.updateByPrimaryKeySelective(updateOrder);
                BsPayOrdersJnl insertOrderJnl = new BsPayOrdersJnl();
                insertOrderJnl.setCreateTime(new Date());
                insertOrderJnl.setOrderId(order.getId());
                insertOrderJnl.setOrderNo(order.getOrderNo());
                insertOrderJnl.setSubAccountCode(proSubAccount.getCode());
                insertOrderJnl.setSubAccountId(order.getSubAccountId());
                insertOrderJnl.setSysTime(new Date());
                insertOrderJnl.setTransAmount(order.getAmount());
                insertOrderJnl.setTransCode(Constants.TRANS_RETURN_REG_D_2_USER_BANK_CARD);
                insertOrderJnl.setUserId(order.getUserId());
                insertOrderJnl.setReturnCode(notice.getRetCode());
                insertOrderJnl.setReturnMsg(notice.getErrorMsg());
                bsPayOrdersJnlMapper.insertSelective(insertOrderJnl);
                //告警
                specialJnlService.warn4Fail(order.getAmount(), "{用户回款到卡通知}产品户编号["+order.getOrderNo()+"]回款通知结果为失败",
                        order.getOrderNo(),"用户回款到卡通知",false);
            }

            //修改支付结果表状态
            BsPayResultQueueExample queueExample = new BsPayResultQueueExample();
            queueExample.createCriteria().andOrderNoEqualTo(order.getOrderNo());
            List<BsPayResultQueue> queueList = queueMapper.selectByExample(queueExample);

            if (org.apache.commons.collections.CollectionUtils.isNotEmpty(queueList) && queueList.size() > 0) {
                BsPayResultQueue queueTemp = new BsPayResultQueue();
                queueTemp.setId(queueList.get(0).getId());
                queueTemp.setUpdateTime(new Date());
                if (OrderStatus.SUCCESS.getCode().equals(notice.getOrderStatus())) {
                    queueTemp.setStatus("SUCC");
                }else {
                    queueTemp.setStatus("FAIL");
                }
                queueMapper.updateByPrimaryKeySelective(queueTemp);
            }
            log.info("========={用户回款到卡通知}用户回款到卡通知处理结束=========");
        } finally {
            //jsClientDaoSupport.unlock(RedisLockEnum.LOCK_USER_RECEIVEMONEY.getKey());
        }

    }

}

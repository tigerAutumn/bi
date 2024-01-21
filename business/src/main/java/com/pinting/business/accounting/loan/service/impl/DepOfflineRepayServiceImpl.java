package com.pinting.business.accounting.loan.service.impl;

import com.pinting.business.accounting.finance.model.Repay2DepTargetInfo;
import com.pinting.business.accounting.loan.enums.DepIsPayOffEnum;
import com.pinting.business.accounting.loan.enums.DepRepayFlagEnum;
import com.pinting.business.accounting.loan.enums.LoanStatus;
import com.pinting.business.accounting.loan.enums.LoanSubjects;
import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.loan.model.*;
import com.pinting.business.accounting.loan.service.DepOfflineRepayService;
import com.pinting.business.accounting.loan.service.RepayAccountService;
import com.pinting.business.accounting.loan.util.redisUtil;
import com.pinting.business.accounting.service.BsSysSubAccountService;
import com.pinting.business.accounting.service.CustomerReceiveMoneyService;
import com.pinting.business.accounting.service.PayOrdersService;
import com.pinting.business.accounting.service.impl.process.DataBackUpProcess;
import com.pinting.business.dao.*;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.enums.RedisLockEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.*;
import com.pinting.business.model.vo.BsSubAccountVO;
import com.pinting.business.model.vo.LnFinanceRepayScheduleVO;
import com.pinting.business.model.vo.LoanNoticeVO;
import com.pinting.business.service.site.BankCardService;
import com.pinting.business.service.site.SpecialJnlService;
import com.pinting.business.service.site.SubAccountService;
import com.pinting.business.service.site.UserService;
import com.pinting.business.util.CalculatorUtil;
import com.pinting.business.util.Constants;
import com.pinting.business.util.DateUtil;
import com.pinting.business.util.Util;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.hessian.message.baofoo.B2GReqMsg_BaoFooQuickPay_Pay4Trans;
import com.pinting.gateway.hessian.message.baofoo.B2GResMsg_BaoFooQuickPay_Pay4Trans;
import com.pinting.gateway.hessian.message.hfbank.*;
import com.pinting.gateway.hessian.message.hfbank.model.*;
import com.pinting.gateway.hessian.message.pay19.enums.OrderStatus;
import com.pinting.gateway.out.service.BaoFooTransportService;
import com.pinting.gateway.out.service.HfbankTransportService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by babyshark on 2017/4/4.
 */
@Service
public class DepOfflineRepayServiceImpl implements DepOfflineRepayService {
    private Logger log = LoggerFactory.getLogger(DepOfflineRepayServiceImpl.class);
    private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private BankCardService bankCardService;
    @Autowired
    private LnPayOrdersMapper lnPayOrdersMapper;
    @Autowired
    private LnPayOrdersJnlMapper lnPayOrdersJnlMapper;
    @Autowired
    private BaoFooTransportService baoFooTransportService;
    @Autowired
    private PayOrdersService payOrdersService;
    @Autowired
    private BsPayResultQueueMapper queueMapper;
    @Autowired
    private SpecialJnlService specialJnlService;
    @Autowired
    private LnDepositionRepayScheduleMapper lnDepositionRepayScheduleMapper;
    @Autowired
    private BsSysSubAccountService bsSysSubAccountService;
    @Autowired
    private BsSysSubAccountMapper bsSysSubAccountMapper;
    @Autowired
    private BsSysAccountJnlMapper bsSysAccountJnlMapper;
    @Autowired
    private HfbankTransportService hfbankTransportService;
    @Autowired
    private LnSubAccountMapper lnSubAccountMapper;
    @Autowired
    private LnAccountJnlMapper lnAccountJnlMapper;
    @Autowired
    private LnFinanceRepayScheduleMapper lnFinanceRepayScheduleMapper;
    @Autowired
    private LnLoanRelationMapper lnLoanRelationMapper;
    @Autowired
    private BsSubAccountMapper bsSubAccountMapper;
    @Autowired
    private BsSubAccountPairMapper bsSubAccountPairMapper;
    @Autowired
    private BsAccountJnlMapper bsAccountJnlMapper;
    @Autowired
    private RepayAccountService repayAccountService;
    @Autowired
    private LnCompensateRelationMapper lnCompensateRelationMapper;
    @Autowired
    private BsHfbankUserExtMapper bsHfbankUserExtMapper;
    @Autowired
    private LnLoanAmountChangeMapper lnLoanAmountChangeMapper;
    @Autowired
    private BsLoanFinanceRepayMapper bsLoanFinanceRepayMapper;
    @Autowired
    private BsUserTransDetailMapper bsUserTransDetailMapper;
    @Autowired
    private BsUserMapper bsUserMapper;
    @Autowired
    private BsDailyInterestMapper bsDailyInterestMapper;
    @Autowired
    private CustomerReceiveMoneyService customerReceiveMoneyService;
    @Autowired
    private UserService userService;
    @Autowired
    private SubAccountService subAccountService;
    @Autowired
    private LnUserMapper lnUserMapper;
    @Autowired
    private LnDepositionRepayScheduleDetailMapper lnDepositionRepayScheduleDetailMapper;


    /**
     * 提现到恒丰还款实体户（卡）
     *
     * @param req
     */
    @Override
    public void withdraw2DepRepayCard(Withdraw2DepRepayCardReq req) {
        List<DepRepaySchedule> depRepaySchedules = req.getDepRepaySchedules();
        //记录ln_pay_orders表
        String orderNo = Util.generateOrderNo4BaoFoo(8);
        LnPayOrders lnPayOrders = new LnPayOrders();
        lnPayOrders.setCreateTime(new Date());
        lnPayOrders.setAccountType(Constants.ORDER_ACCOUNT_TYPE_SYS);
        lnPayOrders.setAmount(req.getAmount());
        lnPayOrders.setBankCardNo(Withdraw2DepRepayCardReq.getDepRepayCard());
        lnPayOrders.setBankName(Withdraw2DepRepayCardReq.getDepBankName());
        lnPayOrders.setUserName(Withdraw2DepRepayCardReq.getDepRepayCardName());
        //BsCardBin bsCardBin = bankCardService.queryBankBin(Withdraw2DepRepayCardReq.getDepRepayCard());
        lnPayOrders.setBankId(null);
        lnPayOrders.setChannel(Constants.CHANNEL_BAOFOO);
        lnPayOrders.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DF.getCode());
        lnPayOrders.setMoneyType(0);
        lnPayOrders.setOrderNo(orderNo);
        lnPayOrders.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_INIT.getCode()));
        lnPayOrders.setTransType(LoanStatus.TRANS_TYPE_WITHDRAW_2_DEP_REPAY_CARD.getCode());
        lnPayOrders.setUpdateTime(new Date());
        lnPayOrdersMapper.insertSelective(lnPayOrders);
        //记录ln_pay_orders_jnl表
        LnPayOrdersJnl lnPayOrdersJnl = new LnPayOrdersJnl();
        lnPayOrdersJnl.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DF.getCode());
        lnPayOrdersJnl.setCreateTime(new Date());
        lnPayOrdersJnl.setOrderId(lnPayOrders.getId());
        lnPayOrdersJnl.setOrderNo(lnPayOrders.getOrderNo());
        lnPayOrdersJnl.setTransAmount(lnPayOrders.getAmount());
        lnPayOrdersJnl.setTransCode(LoanStatus.ORDERS_JNL_STATUS_INIT.getCode());
        lnPayOrdersJnl.setSysTime(new Date());
        lnPayOrdersJnlMapper.insertSelective(lnPayOrdersJnl);
        //ln_deposition_repay_schedule订单号更新
        for (DepRepaySchedule depRepaySchedule : depRepaySchedules){
            LnDepositionRepaySchedule schedule = new LnDepositionRepaySchedule();
            schedule.setId(depRepaySchedule.getId());
            schedule.setDfOrderNo(lnPayOrders.getOrderNo());
            schedule.setReturnFlag(LoanStatus.DEP_REPAY_SCHEDULE_RETURN_FLAG_DF_PROCESS.getCode());
            schedule.setUpdateTime(new Date());
            lnDepositionRepayScheduleMapper.updateByPrimaryKeySelective(schedule);
        }
        //发起代付请求
        B2GReqMsg_BaoFooQuickPay_Pay4Trans payPay4Trans = new B2GReqMsg_BaoFooQuickPay_Pay4Trans();
        payPay4Trans.setTo_acc_no(Withdraw2DepRepayCardReq.getDepRepayCard());
        payPay4Trans.setTrans_money(String.valueOf(req.getAmount()));
        payPay4Trans.setTo_acc_name(Withdraw2DepRepayCardReq.getDepRepayCardName());
        payPay4Trans.setTo_bank_name(Withdraw2DepRepayCardReq.getDepBankName());
        payPay4Trans.setTrans_no(lnPayOrders.getOrderNo());
        payPay4Trans.setTo_pro_name(Withdraw2DepRepayCardReq.getDepProvinceName());
        payPay4Trans.setTo_city_name(Withdraw2DepRepayCardReq.getDepCityName());
        payPay4Trans.setTo_acc_dept(Withdraw2DepRepayCardReq.getDepAccDept());
        payPay4Trans.setTrans_summary("线下还款代付到恒丰实体户");
        B2GResMsg_BaoFooQuickPay_Pay4Trans res = null;
        try {
            res = baoFooTransportService.pay4Trans(payPay4Trans);
        } catch (Exception e) {
            log.error("提现到恒丰还款实体户通讯失败：{}", e);
            res = new B2GResMsg_BaoFooQuickPay_Pay4Trans();
            res.setResCode(LoanStatus.BAOFOO_PAY_STATUS_PROCESS.getCode());
            res.setResMsg("通讯失败，置为处理中");
        }
        log.info("订单号="+lnPayOrders.getOrderNo()+"的提现到恒丰还款实体户（卡）操作结果=["+res.getResCode()+"]");
        if (res.getResCode().equals(ConstantUtil.BSRESCODE_SUCCESS)) {
            //修改Ln_pay_orders,状态为支付中
            payOrdersService.modifyLnOrderStatus4Safe(lnPayOrders.getId(), Integer.parseInt(LoanStatus.ORDERS_STATUS_PAYING.getCode()),
                    null, res.getResCode(), res.getResMsg());
            //代付成功
            DFResultInfo resultInfo = new DFResultInfo();
            resultInfo.setAmount(lnPayOrders.getAmount());
            resultInfo.setFinishTime(new Date());
            resultInfo.setMxOrderId(lnPayOrders.getOrderNo());
            resultInfo.setOrderStatus(OrderStatus.SUCCESS.getCode());
            resultInfo.setSysOrderId(null);
            resultInfo.setRetCode(res.getResCode());
            resultInfo.setErrorMsg(res.getResMsg());
            notifyWithdraw2DepRepayCardResult(resultInfo);

        } else if (res.getResCode().equals(LoanStatus.BAOFOO_PAY_STATUS_PROCESS.getCode())) {
            //修改Ln_pay_orders,状态为支付中
            payOrdersService.modifyLnOrderStatus4Safe(lnPayOrders.getId(), Integer.parseInt(LoanStatus.ORDERS_STATUS_PAYING.getCode()),
                    null, res.getResCode(), res.getResMsg());

            //放到redis中
            LoanNoticeVO vo = new LoanNoticeVO();
            vo.setPayOrderNo(lnPayOrders.getOrderNo());
            vo.setChannel(lnPayOrders.getChannel());
            vo.setChannelTransType(lnPayOrders.getChannelTransType());
            vo.setTransType(lnPayOrders.getTransType());
            vo.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_PAYING.getCode()));
            vo.setAmount(MoneyUtil.defaultRound(lnPayOrders.getAmount()).toString());
            redisUtil.rpushRedis(vo);

            //并插入到消息队列表中
            BsPayResultQueue queue = new BsPayResultQueue();
            queue.setChannel(Constants.ORDER_CHANNEL_BAOFOO);
            queue.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DF.getCode());
            queue.setCreateTime(new Date());
            queue.setDealNum(0);
            queue.setOrderNo(lnPayOrders.getOrderNo());
            queue.setStatus(LoanStatus.NOTICE_STATUS_INIT.getCode());
            queue.setTransType(lnPayOrders.getTransType());
            queue.setUpdateTime(new Date());
            queueMapper.insertSelective(queue);

            lnPayOrdersJnl = new LnPayOrdersJnl();
            lnPayOrdersJnl.setTransCode(LoanStatus.ORDERS_JNL_STATUS_PAYING.getCode());
            //记录ln_pay_orders_jnl表
            lnPayOrdersJnl.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DF.getCode());
            lnPayOrdersJnl.setCreateTime(new Date());
            lnPayOrdersJnl.setOrderId(lnPayOrders.getId());
            lnPayOrdersJnl.setOrderNo(lnPayOrders.getOrderNo());
            lnPayOrdersJnl.setTransAmount(lnPayOrders.getAmount());
            lnPayOrdersJnl.setSysTime(new Date());
            lnPayOrdersJnl.setReturnCode(res.getResCode());
            lnPayOrdersJnl.setReturnMsg(res.getResMsg());

            lnPayOrdersJnlMapper.insertSelective(lnPayOrdersJnl);

        } else {
            //代付失败
            //告警
            specialJnlService.warn4FailNoSMS(lnPayOrders.getAmount(), res.getResMsg(), lnPayOrders.getOrderNo(), "提现到恒丰还款实体户");
            //修改Ln_pay_orders,状态为支付中
            payOrdersService.modifyLnOrderStatus4Safe(lnPayOrders.getId(), Integer.parseInt(LoanStatus.ORDERS_STATUS_PAYING.getCode()),
                    null, res.getResCode(), res.getResMsg());
            DFResultInfo resultInfo = new DFResultInfo();
            resultInfo.setAmount(lnPayOrders.getAmount());
            resultInfo.setFinishTime(new Date());
            resultInfo.setMxOrderId(lnPayOrders.getOrderNo());
            resultInfo.setOrderStatus(OrderStatus.FAIL.getCode());
            resultInfo.setSysOrderId(null);
            resultInfo.setErrorMsg(res.getResMsg());
            resultInfo.setRetCode(res.getResCode());
            notifyWithdraw2DepRepayCardResult(resultInfo);
        }

    }

    /**
     * 提现到恒丰还款实体户（卡）结果处理
     */
    @Override
    public void notifyWithdraw2DepRepayCardResult(final DFResultInfo req) {
        try {
            jsClientDaoSupport.lock(RedisLockEnum.LOCK_WITHDRAW_2_DEP_REPAY_CARD.getKey());
            //前置校验
            LnPayOrdersExample payOrdersExample = new LnPayOrdersExample();
            payOrdersExample.createCriteria().andOrderNoEqualTo(req.getMxOrderId());
            final List<LnPayOrders> lnPayOrdersList = lnPayOrdersMapper.selectByExample(payOrdersExample);
            if (CollectionUtils.isEmpty(lnPayOrdersList) ||
                    lnPayOrdersList.get(0).getStatus() != Constants.ORDER_STATUS_PAYING) {
                throw new PTMessageException(PTMessageEnum.PAYMENT_ORDER_NOT_EXIST);
            }
            LnDepositionRepayScheduleExample scheduleExample = new LnDepositionRepayScheduleExample();
            scheduleExample.createCriteria().andDfOrderNoEqualTo(req.getMxOrderId());
            final List<LnDepositionRepaySchedule> scheduleList = lnDepositionRepayScheduleMapper.selectByExample(scheduleExample);
            if (CollectionUtils.isEmpty(scheduleList) ||
                    LoanStatus.DEP_REPAY_SCHEDULE_RETURN_FLAG_DF_PROCESS.getCode().equals(scheduleList.get(0).getStatus())) {
                throw new PTMessageException(PTMessageEnum.REPAY_DATA_NOT_FOUND);
            }
            //失败：订单表状态置失败，存管体系还款计划表return_flag置代付失败
            //成功：订单表状态置成功，存管体系还款计划表return_flag置代付成功，系统还款资金户-，//异步发起行内代扣
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                public void doInTransactionWithoutResult(TransactionStatus status) {
                    LnPayOrders order = lnPayOrdersMapper.selectByPKForLock(lnPayOrdersList.get(0).getId());
                    LnPayOrders payOrdersTemp = new LnPayOrders();
                    LnPayOrdersJnl lnPayOrdersJnl = new LnPayOrdersJnl();
                    LnDepositionRepaySchedule schedule = new LnDepositionRepaySchedule();
                    if (OrderStatus.SUCCESS.getCode().equals(req.getOrderStatus())) {
                        payOrdersTemp.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_SUCCESS.getCode()));
                        lnPayOrdersJnl.setTransCode(LoanStatus.ORDERS_JNL_STATUS_SUCCESS.getCode());
                        schedule.setReturnFlag(LoanStatus.DEP_REPAY_SCHEDULE_RETURN_FLAG_DF_SUCC.getCode());
                    } else {
                        payOrdersTemp.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_FAIL.getCode()));
                        lnPayOrdersJnl.setTransCode(LoanStatus.ORDERS_JNL_STATUS_FAIL.getCode());
                        schedule.setReturnFlag(LoanStatus.DEP_REPAY_SCHEDULE_RETURN_FLAG_DF_FAIL.getCode());
                    }
                    //修改ln_pay_orders状态
                    payOrdersTemp.setId(order.getId());
                    payOrdersTemp.setUpdateTime(new Date());
                    payOrdersTemp.setReturnCode(req.getRetCode());
                    payOrdersTemp.setReturnMsg(req.getErrorMsg());
                    lnPayOrdersMapper.updateByPrimaryKeySelective(payOrdersTemp);
                    //记录ln_pay_orders_jnl表
                    lnPayOrdersJnl.setSubAccountId(order.getSubAccountId());
                    lnPayOrdersJnl.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DF.getCode());
                    lnPayOrdersJnl.setCreateTime(new Date());
                    lnPayOrdersJnl.setOrderId(order.getId());
                    lnPayOrdersJnl.setOrderNo(order.getOrderNo());
                    lnPayOrdersJnl.setTransAmount(order.getAmount());
                    lnPayOrdersJnl.setUserId(order.getLnUserId());
                    lnPayOrdersJnl.setSysTime(new Date());
                    lnPayOrdersJnl.setReturnCode(req.getRetCode());
                    lnPayOrdersJnl.setReturnMsg(req.getErrorMsg());
                    lnPayOrdersJnlMapper.insertSelective(lnPayOrdersJnl);
                    //修改lnDepositionRepaySchedule状态
                    LnDepositionRepayScheduleExample depScheduleExample = new LnDepositionRepayScheduleExample();
                    depScheduleExample.createCriteria().andDfOrderNoEqualTo(order.getOrderNo());
                    schedule.setUpdateTime(new Date());
                    lnDepositionRepayScheduleMapper.updateByExampleSelective(schedule, depScheduleExample);

                    //修改支付结果表状态
                    BsPayResultQueueExample queueExample = new BsPayResultQueueExample();
                    queueExample.createCriteria().andOrderNoEqualTo(order.getOrderNo());
                    List<BsPayResultQueue> queueList = queueMapper.selectByExample(queueExample);

                    if (CollectionUtils.isNotEmpty(queueList) && queueList.size() > 0) {
                        BsPayResultQueue queueTemp = new BsPayResultQueue();
                        queueTemp.setId(queueList.get(0).getId());
                        queueTemp.setUpdateTime(new Date());
                        if (OrderStatus.SUCCESS.getCode().equals(req.getOrderStatus())) {
                            queueTemp.setStatus(Constants.PAY_RESULT_QUEUE_STATUS_SUCCESS);
                        } else {
                            queueTemp.setStatus(Constants.PAY_RESULT_QUEUE_STATUS_FAIL);
                        }
                        queueMapper.updateByPrimaryKeySelective(queueTemp);
                    }
                    //成功
                    if (OrderStatus.SUCCESS.getCode().equals(req.getOrderStatus())) {
                        //还款资金户金额-
                        BaseAccount account = new BaseAccount();
                        account.setAmount(req.getAmount());
                        chargeWithdraw2DepRepayCard(account);
                    }
                }
            });
            //成功：发起行内代扣 >>> 行内代扣为批量接口采用定时发起，不再直接调用
        }finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_WITHDRAW_2_DEP_REPAY_CARD.getKey());
        }
    }

    /**
     * （批量）代扣还款到借款人/代偿人
     */
    @Override
    @Transactional
    public void cutRepay2Borrower(CutRepay2BorrowerReq req) {
        String orderNo = Util.generateOrderNo4BaoFoo(8);
        Date transTime = new Date();
        Double amount = req.getAmount();
        List<DepRepaySchedule> depRepaySchedules = req.getDepRepaySchedules();
        if(CollectionUtils.isEmpty(depRepaySchedules)){
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }
        //记录ln_pay_orders表
        LnPayOrders lnPayOrders = new LnPayOrders();
        lnPayOrders.setCreateTime(new Date());
        lnPayOrders.setAccountType(Constants.ORDER_ACCOUNT_TYPE_SYS);
        lnPayOrders.setAmount(amount);
        lnPayOrders.setChannel(Constants.CHANNEL_HFBANK);
        lnPayOrders.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DK.getCode());
        lnPayOrders.setMoneyType(0);
        lnPayOrders.setOrderNo(orderNo);
        lnPayOrders.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_INIT.getCode()));
        lnPayOrders.setTransType(LoanStatus.TRANS_TYPE_CUT_REPAY_2_BORROWER.getCode());
        lnPayOrders.setUpdateTime(new Date());
        lnPayOrdersMapper.insertSelective(lnPayOrders);
        //记录ln_pay_orders_jnl表
        LnPayOrdersJnl lnPayOrdersJnl = new LnPayOrdersJnl();
        lnPayOrdersJnl.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DK.getCode());
        lnPayOrdersJnl.setCreateTime(new Date());
        lnPayOrdersJnl.setOrderId(lnPayOrders.getId());
        lnPayOrdersJnl.setOrderNo(lnPayOrders.getOrderNo());
        lnPayOrdersJnl.setTransAmount(lnPayOrders.getAmount());
        lnPayOrdersJnl.setTransCode(LoanStatus.ORDERS_JNL_STATUS_INIT.getCode());
        lnPayOrdersJnl.setSysTime(new Date());
        lnPayOrdersJnlMapper.insertSelective(lnPayOrdersJnl);
        //ln_deposition_repay_schedule订单号更新
        List<BorrowCutRepayPlatcust> platcustList = new ArrayList<>();
        for(DepRepaySchedule depRepaySchedule : depRepaySchedules){
            LnDepositionRepaySchedule schedule = new LnDepositionRepaySchedule();
            schedule.setId(depRepaySchedule.getId());
            schedule.setDkOrderNo(lnPayOrders.getOrderNo());
            schedule.setReturnFlag(LoanStatus.DEP_REPAY_SCHEDULE_RETURN_FLAG_DK_PROCESS.getCode());
            schedule.setUpdateTime(new Date());
            lnDepositionRepayScheduleMapper.updateByPrimaryKeySelective(schedule);
            
            log.info("借款人扣款还款订单:"+orderNo+"|客户号=["+depRepaySchedule.getHfUserId()+"]该平台客户入账金额=["+depRepaySchedule.getRepayAmount()+"]");
            
            BorrowCutRepayPlatcust cust = new BorrowCutRepayPlatcust();
            cust.setAmt(depRepaySchedule.getRepayAmount());
            cust.setPlatcust(depRepaySchedule.getHfUserId());
            platcustList.add(cust);
        }
        //发起代扣还款请求
        B2GReqMsg_HFBank_BorrowCutRepayDK cutRepayDKReq = new B2GReqMsg_HFBank_BorrowCutRepayDK();
        cutRepayDKReq.setAmt(lnPayOrders.getAmount());
        cutRepayDKReq.setOrder_no(orderNo);
        cutRepayDKReq.setPlatcustList(platcustList);
        cutRepayDKReq.setPartner_trans_date(transTime);
        cutRepayDKReq.setPartner_trans_time(transTime);
        cutRepayDKReq.setRemark("代扣到借款人或代偿人");
        B2GResMsg_HFBank_BorrowCutRepayDK resMsg = null;
        try {
            resMsg = hfbankTransportService.borrowCutRepayDK(cutRepayDKReq);
        } catch (Exception e) {
            log.error("代扣到借款人通讯失败：{}", e);
            resMsg = new B2GResMsg_HFBank_BorrowCutRepayDK();
            resMsg.setResCode(LoanStatus.BAOFOO_PAY_STATUS_PROCESS.getCode());
            resMsg.setResMsg("通讯失败，置为处理中");
        }
        log.info("借款人扣款还款订单号=["+orderNo+"][客户号=["+platcustList+"]resMsg=["+resMsg+"]");
        //支付结果异步，成功受理，置为处理中
        if(ConstantUtil.BSRESCODE_SUCCESS.equals(resMsg.getResCode()) ||
                LoanStatus.BAOFOO_PAY_STATUS_PROCESS.getCode().equals(resMsg.getResCode())){
            payOrdersService.modifyLnOrderStatus4Safe(lnPayOrders.getId(), Integer.parseInt(LoanStatus.ORDERS_STATUS_PAYING.getCode()),
                    null, resMsg.getResCode(), resMsg.getResMsg());
            LnPayOrdersJnl jnlProcess = new LnPayOrdersJnl();
            jnlProcess.setOrderId(lnPayOrders.getId());
            jnlProcess.setOrderNo(lnPayOrders.getOrderNo());
            jnlProcess.setTransCode(Constants.ORDER_TRANS_CODE_PROCESS);
            jnlProcess.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DK.getCode());
            jnlProcess.setTransAmount(lnPayOrders.getAmount());
            jnlProcess.setSysTime(new Date());
            jnlProcess.setCreateTime(new Date());
            lnPayOrdersJnlMapper.insertSelective(jnlProcess);
        }
        //失败
        else{
            //告警
            specialJnlService.warn4FailNoSMS(lnPayOrders.getAmount(), resMsg.getResMsg(), lnPayOrders.getOrderNo(), "代扣到借款人或代偿人");
            payOrdersService.modifyLnOrderStatus4Safe(lnPayOrders.getId(), Integer.parseInt(LoanStatus.ORDERS_STATUS_PAYING.getCode()),
                    null, resMsg.getResCode(), resMsg.getResMsg());
            RepayResultInfo repayResultInfo = new RepayResultInfo();
            repayResultInfo.setErrorCode(resMsg.getResCode());
            repayResultInfo.setErrorMsg(resMsg.getResMsg());
            repayResultInfo.setOrderFinTime(new Date());
            repayResultInfo.setAmount(lnPayOrders.getAmount());
            repayResultInfo.setOrderId(lnPayOrders.getOrderNo());
            repayResultInfo.setStatus(OrderStatus.FAIL.getCode());
            notifyCutRepay2BorrowerResult(repayResultInfo);
        }
    }

    /**
     * 代扣还款到借款人结果处理
     */
    @Override
    public void notifyCutRepay2BorrowerResult(final RepayResultInfo req) {
        try {
            jsClientDaoSupport.lock(RedisLockEnum.LOCK_CUT_REPAY_2_BORROWER.getKey());
            //前置校验
            LnPayOrdersExample payOrdersExample = new LnPayOrdersExample();
            payOrdersExample.createCriteria().andOrderNoEqualTo(req.getOrderId());
            final List<LnPayOrders> lnPayOrdersList = lnPayOrdersMapper.selectByExample(payOrdersExample);
            if (CollectionUtils.isEmpty(lnPayOrdersList) ||
                    lnPayOrdersList.get(0).getStatus() != Constants.ORDER_STATUS_PAYING) {
                throw new PTMessageException(PTMessageEnum.PAYMENT_ORDER_NOT_EXIST);
            }
            LnDepositionRepayScheduleExample scheduleExample = new LnDepositionRepayScheduleExample();
            scheduleExample.createCriteria().andDkOrderNoEqualTo(req.getOrderId());
            final List<LnDepositionRepaySchedule> scheduleList = lnDepositionRepayScheduleMapper.selectByExample(scheduleExample);
            if (CollectionUtils.isEmpty(scheduleList) ||
                    LoanStatus.DEP_REPAY_SCHEDULE_RETURN_FLAG_DK_PROCESS.getCode().equals(scheduleList.get(0).getStatus())) {
                throw new PTMessageException(PTMessageEnum.REPAY_DATA_NOT_FOUND);
            }
            //失败：订单表状态置失败，存管体系还款计划表return_flag置代扣失败
            //成功：订单表状态置成功，存管体系还款计划表return_flag置代扣成功，借款人余额户+
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                public void doInTransactionWithoutResult(TransactionStatus status) {
                    LnPayOrders order = lnPayOrdersMapper.selectByPKForLock(lnPayOrdersList.get(0).getId());
                    LnPayOrders payOrdersTemp = new LnPayOrders();
                    LnPayOrdersJnl lnPayOrdersJnl = new LnPayOrdersJnl();
                    LnDepositionRepaySchedule schedule = new LnDepositionRepaySchedule();
                    if (OrderStatus.SUCCESS.getCode().equals(req.getStatus())) {
                        payOrdersTemp.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_SUCCESS.getCode()));
                        lnPayOrdersJnl.setTransCode(LoanStatus.ORDERS_JNL_STATUS_SUCCESS.getCode());
                        schedule.setReturnFlag(LoanStatus.DEP_REPAY_SCHEDULE_RETURN_FLAG_DK_SUCC.getCode());
                    } else {
                        payOrdersTemp.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_FAIL.getCode()));
                        lnPayOrdersJnl.setTransCode(LoanStatus.ORDERS_JNL_STATUS_FAIL.getCode());
                        schedule.setReturnFlag(LoanStatus.DEP_REPAY_SCHEDULE_RETURN_FLAG_DK_FAIL.getCode());
                    }
                    //修改ln_pay_orders状态
                    payOrdersTemp.setId(order.getId());
                    payOrdersTemp.setUpdateTime(new Date());
                    payOrdersTemp.setReturnCode(req.getErrorCode());
                    payOrdersTemp.setReturnMsg(req.getErrorMsg());
                    lnPayOrdersMapper.updateByPrimaryKeySelective(payOrdersTemp);
                    //记录ln_pay_orders_jnl表
                    lnPayOrdersJnl.setSubAccountId(order.getSubAccountId());
                    lnPayOrdersJnl.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DK.getCode());
                    lnPayOrdersJnl.setCreateTime(new Date());
                    lnPayOrdersJnl.setOrderId(order.getId());
                    lnPayOrdersJnl.setOrderNo(order.getOrderNo());
                    lnPayOrdersJnl.setTransAmount(order.getAmount());
                    lnPayOrdersJnl.setUserId(order.getLnUserId());
                    lnPayOrdersJnl.setSysTime(new Date());
                    lnPayOrdersJnl.setReturnCode(req.getErrorCode());
                    lnPayOrdersJnl.setReturnMsg(req.getErrorMsg());
                    lnPayOrdersJnlMapper.insertSelective(lnPayOrdersJnl);
                    //修改lnDepositionRepaySchedule状态
                    LnDepositionRepayScheduleExample depScheduleExample = new LnDepositionRepayScheduleExample();
                    depScheduleExample.createCriteria().andDkOrderNoEqualTo(order.getOrderNo());
                    schedule.setUpdateTime(new Date());
                    lnDepositionRepayScheduleMapper.updateByExampleSelective(schedule, depScheduleExample);
                    //成功
                    if (OrderStatus.SUCCESS.getCode().equals(req.getStatus())) {
                        for (LnDepositionRepaySchedule depRepaySchedule : scheduleList){
                            LnCompensateRelationExample compRelationExample = new LnCompensateRelationExample();
                            compRelationExample.createCriteria().andDepPlanIdEqualTo(depRepaySchedule.getId());
                            List<LnCompensateRelation> comRelations = lnCompensateRelationMapper.selectByExample(compRelationExample);
                            if(CollectionUtils.isEmpty(comRelations)){
                                //非代偿：借款人余额记账
                                BaseAccount account = new BaseAccount();
                                account.setAmount(depRepaySchedule.getPlanTotal());
                                account.setBorrowerUserId(depRepaySchedule.getLnUserId());
                                chargeCutRepay2Borrower(account);
                            }else{
                                //代偿：代偿人余额记账
                                BaseAccount account = new BaseAccount();
                                account.setAmount(depRepaySchedule.getPlanTotal());
                                account.setInvestorUserId(comRelations.get(0).getCompUserId());
                                chargeCutRepay2Comp(account);
                            }

                        }
                    }
                }
            });
            //成功：发起借款人（批量）还款至标的 >>> 借款人批量还款为批量接口采用定时发起，不再直接调用
        }finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_CUT_REPAY_2_BORROWER.getKey());
        }

    }

    /**
     * 借款人（批量）还款至标的
     */
    @Override
    public void repay2DepTarget(Repay2DepTargetReq req) {
        //发起借款人批量还款
        Date transTime = new Date();
        B2GReqMsg_HFBank_BatchExtRepay batchExtRepay = new B2GReqMsg_HFBank_BatchExtRepay();
        String order_No = Util.generateOrderNo4BaoFoo(8);
        Double payOrderAmount = 0d;
        //组装恒丰数据
        batchExtRepay.setOrder_no(order_No);
        batchExtRepay.setPartner_trans_date(transTime);
        batchExtRepay.setPartner_trans_time(transTime);
        List<BatchExtRepayReqData> dataList = new ArrayList<>();
        List<DepRepaySchedule> depRepaySchedules = req.getDepRepaySchedules();
        for (DepRepaySchedule depRepaySchedule : depRepaySchedules){
            BatchExtRepayReqData data = new BatchExtRepayReqData();
            data.setDetail_no(order_No + "_" + depRepaySchedule.getId());
            data.setFee_amt(depRepaySchedule.getRepayFee()!=null?depRepaySchedule.getRepayFee():0);
            data.setPlatcust(depRepaySchedule.getHfUserId());
            data.setReal_repay_amt(depRepaySchedule.getRepayAmount());
            data.setReal_repay_date(new Date());
            data.setProd_id(String.valueOf(depRepaySchedule.getTargetId()));
            data.setRepay_amt(depRepaySchedule.getRepayAmount());
            data.setRepay_date(depRepaySchedule.getPlanDate());
            data.setRepay_num(String.valueOf(depRepaySchedules.size()));
            data.setTrans_amt(depRepaySchedule.getPlanTotal());//实际还款金额+手续费（2%或无）
            dataList.add(data);
            //LnDepositionRepaySchedule修改
            LnDepositionRepaySchedule schedule = new LnDepositionRepaySchedule();
            schedule.setId(depRepaySchedule.getId());
            schedule.setRepayOrderNo(batchExtRepay.getOrder_no());
            schedule.setUpdateTime(new Date());
            schedule.setReturnFlag(LoanStatus.DEP_REPAY_SCHEDULE_RETURN_FLAG_REPAY_PROCESS.getCode());
            lnDepositionRepayScheduleMapper.updateByPrimaryKeySelective(schedule);
            payOrderAmount = MoneyUtil.add(payOrderAmount, depRepaySchedule.getPlanTotal()).doubleValue();
        }

        //记录ln_pay_orders表
        LnPayOrders lnPayOrders = new LnPayOrders();
        lnPayOrders.setCreateTime(new Date());
        lnPayOrders.setAccountType(Constants.ORDER_ACCOUNT_TYPE_SYS);
        lnPayOrders.setAmount(payOrderAmount);
        lnPayOrders.setChannel(Constants.CHANNEL_HFBANK);
        lnPayOrders.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_REPAY_2_DEP_TARGET.getCode());
        lnPayOrders.setMoneyType(0);
        lnPayOrders.setOrderNo(order_No);
        lnPayOrders.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_INIT.getCode()));
        lnPayOrders.setTransType(LoanStatus.TRANS_TYPE_REPAY_2_DEP_TARGET.getCode());
        lnPayOrders.setUpdateTime(new Date());
        lnPayOrdersMapper.insertSelective(lnPayOrders);
        //记录ln_pay_orders_jnl表
        LnPayOrdersJnl lnPayOrdersJnl = new LnPayOrdersJnl();
        lnPayOrdersJnl.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_REPAY_2_DEP_TARGET.getCode());
        lnPayOrdersJnl.setCreateTime(new Date());
        lnPayOrdersJnl.setOrderId(lnPayOrders.getId());
        lnPayOrdersJnl.setOrderNo(lnPayOrders.getOrderNo());
        lnPayOrdersJnl.setTransAmount(lnPayOrders.getAmount());
        lnPayOrdersJnl.setTransCode(LoanStatus.ORDERS_JNL_STATUS_INIT.getCode());
        lnPayOrdersJnl.setSysTime(new Date());
        lnPayOrdersJnlMapper.insertSelective(lnPayOrdersJnl);
        
        batchExtRepay.setData(dataList);
        batchExtRepay.setTotal_num(depRepaySchedules.size());
        B2GResMsg_HFBank_BatchExtRepay resMsg = null;
        try {
            resMsg = hfbankTransportService.batchExtRepay(batchExtRepay);
        } catch (Exception e) {
            log.error("借款人批量还款["+batchExtRepay.getOrder_no()+"]通讯失败：{}", e);
            return;
        }
        log.info("借款人（批量）还款至标的=["+order_No+"]resMsg=["+resMsg+"]");
        if(ConstantUtil.BSRESCODE_SUCCESS.equals(resMsg.getResCode())){
        	LnPayOrders order = lnPayOrdersMapper.selectByPKForLock(lnPayOrders.getId());
            LnPayOrders payOrdersTemp = new LnPayOrders();
            LnPayOrdersJnl lnPayOrdersJnlSucc = new LnPayOrdersJnl();
            //修改ln_pay_orders状态
            payOrdersTemp.setId(order.getId());
            payOrdersTemp.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_SUCCESS.getCode()));
            payOrdersTemp.setUpdateTime(new Date());
            payOrdersTemp.setReturnCode(resMsg.getResCode());
            payOrdersTemp.setReturnMsg(resMsg.getResMsg());
            lnPayOrdersMapper.updateByPrimaryKeySelective(payOrdersTemp);
            //记录ln_pay_orders_jnl表
            lnPayOrdersJnlSucc.setSubAccountId(order.getSubAccountId());
            lnPayOrdersJnlSucc.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_REPAY_2_DEP_TARGET.getCode());
            lnPayOrdersJnlSucc.setTransCode(LoanStatus.ORDERS_JNL_STATUS_SUCCESS.getCode());
            lnPayOrdersJnlSucc.setCreateTime(new Date());
            lnPayOrdersJnlSucc.setOrderId(order.getId());
            lnPayOrdersJnlSucc.setOrderNo(order.getOrderNo());
            lnPayOrdersJnlSucc.setTransAmount(order.getAmount());
            lnPayOrdersJnlSucc.setUserId(order.getLnUserId());
            lnPayOrdersJnlSucc.setSysTime(new Date());
            lnPayOrdersJnlSucc.setReturnCode(resMsg.getResCode());
            lnPayOrdersJnlSucc.setReturnMsg(resMsg.getResMsg());
            lnPayOrdersJnlMapper.insertSelective(lnPayOrdersJnlSucc);
            //遍历：账单表更新return_flag，记账B:DEP_JSH-
            List<BatchExtRepayResSuccessData> successDatas = resMsg.getSuccess_data();
            if(CollectionUtils.isNotEmpty(successDatas)){
                for (BatchExtRepayResSuccessData successData : successDatas) {
                    LnDepositionRepaySchedule scheduleTemp = new LnDepositionRepaySchedule();
                    //截取下划线后字符作为账单表编号
                    Integer scheduleId = Integer.valueOf(StringUtil.split(successData.getDetail_no(), "_")[1]);
                    scheduleTemp.setId(scheduleId);
                    scheduleTemp.setUpdateTime(new Date());
                    scheduleTemp.setReturnFlag(LoanStatus.DEP_REPAY_SCHEDULE_RETURN_FLAG_REPAY_SUCC.getCode());
                    lnDepositionRepayScheduleMapper.updateByPrimaryKeySelective(scheduleTemp);
                    //记账
                    BaseAccount account = new BaseAccount();
                    account.setAmount(Double.valueOf(successData.getTrans_amt()));
                    LnDepositionRepaySchedule schedule = lnDepositionRepayScheduleMapper.selectByPrimaryKey(scheduleId);
                    account.setBorrowerUserId(schedule.getLnUserId());
                    //借款服务费入参
                    Repay2DepTargetInfo repay2DepTargetInfo = new Repay2DepTargetInfo();
                    LnDepositionRepayScheduleDetailExample detailExample = new LnDepositionRepayScheduleDetailExample();
                    detailExample.createCriteria().andPlanIdEqualTo(schedule.getId())
                    	.andSubjectCodeEqualTo(LoanSubjects.SUBJECT_CODE_LOAN_SERVICE_FEE.getCode());
                    List<LnDepositionRepayScheduleDetail> detailList = lnDepositionRepayScheduleDetailMapper.selectByExample(detailExample);
                    if(CollectionUtils.isNotEmpty(detailList)){
                    	repay2DepTargetInfo.setDepPlanId(schedule.getId());
                        repay2DepTargetInfo.setLoanServiceFee(detailList.get(0).getPlanAmount());
                    }                    
                    chargeRepay2DepTarget(account,repay2DepTargetInfo);
                    
                }
            }else{
                specialJnlService.warn4FailNoSMS(null, "借款人（批量）还款至标的返回成功列表为空", null, "借款人（批量）还款至标的");
            }

            List<BatchExtRepayResErrorData> errorDatas = resMsg.getError_data();
            if(CollectionUtils.isNotEmpty(errorDatas)){
                for (BatchExtRepayResErrorData errorData : errorDatas) {
                    LnDepositionRepaySchedule scheduleTemp = new LnDepositionRepaySchedule();
                    Integer scheduleId = Integer.valueOf(StringUtil.split(errorData.getDetail_no(), "_")[1]);
                    scheduleTemp.setId(scheduleId);
                    scheduleTemp.setUpdateTime(new Date());
                    scheduleTemp.setReturnFlag(LoanStatus.DEP_REPAY_SCHEDULE_RETURN_FLAG_REPAY_FAIL.getCode());
                    scheduleTemp.setNote(errorData.getError_info());
                    lnDepositionRepayScheduleMapper.updateByPrimaryKeySelective(scheduleTemp);
                }
            }
        }else if(LoanStatus.BAOFOO_PAY_STATUS_PROCESS.getCode().equals(resMsg.getResCode())){
            log.error("借款人批量还款["+batchExtRepay.getOrder_no()+"]异常，置为处理中，请检查");
            //告警
            specialJnlService.warn4Fail(0d, "借款人批量还款["+batchExtRepay.getOrder_no()+"]异常，置为处理中，请检查",
                    batchExtRepay.getOrder_no(), "借款人批量还款异常处理中", false);
        }else {
            //return_flag 整个批次还款失败
            LnDepositionRepaySchedule scheduleTemp = new LnDepositionRepaySchedule();
            LnDepositionRepayScheduleExample depScheduleExample = new LnDepositionRepayScheduleExample();
            depScheduleExample.createCriteria().andRepayOrderNoEqualTo(batchExtRepay.getOrder_no());
            scheduleTemp.setReturnFlag(LoanStatus.DEP_REPAY_SCHEDULE_RETURN_FLAG_REPAY_FAIL.getCode());
            scheduleTemp.setUpdateTime(new Date());
            scheduleTemp.setNote(resMsg.getRemsg());
            lnDepositionRepayScheduleMapper.updateByExampleSelective(scheduleTemp, depScheduleExample);
            
            LnPayOrders order = lnPayOrdersMapper.selectByPKForLock(lnPayOrders.getId());
            LnPayOrders payOrdersTemp = new LnPayOrders();
            LnPayOrdersJnl lnPayOrdersJnlFail = new LnPayOrdersJnl();
            //修改ln_pay_orders状态
            payOrdersTemp.setId(order.getId());
            payOrdersTemp.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_FAIL.getCode()));
            payOrdersTemp.setUpdateTime(new Date());
            payOrdersTemp.setReturnCode(resMsg.getResCode());
            payOrdersTemp.setReturnMsg(resMsg.getResMsg());
            lnPayOrdersMapper.updateByPrimaryKeySelective(payOrdersTemp);
            //记录ln_pay_orders_jnl表
            lnPayOrdersJnlFail.setSubAccountId(order.getSubAccountId());
            lnPayOrdersJnlFail.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_REPAY_2_DEP_TARGET.getCode());
            lnPayOrdersJnlFail.setTransCode(LoanStatus.ORDERS_JNL_STATUS_FAIL.getCode());
            lnPayOrdersJnlFail.setCreateTime(new Date());
            lnPayOrdersJnlFail.setOrderId(order.getId());
            lnPayOrdersJnlFail.setOrderNo(order.getOrderNo());
            lnPayOrdersJnlFail.setTransAmount(order.getAmount());
            lnPayOrdersJnlFail.setUserId(order.getLnUserId());
            lnPayOrdersJnlFail.setSysTime(new Date());
            lnPayOrdersJnlFail.setReturnCode(resMsg.getResCode());
            lnPayOrdersJnlFail.setReturnMsg(resMsg.getResMsg());
            lnPayOrdersJnlMapper.insertSelective(lnPayOrdersJnlFail);
        }

    }

    /**
     * （单个标的）代偿人还款至标的
     *
     * @param req
     */
    @Override
    public void compRepay2DepTarget(DepRepaySchedule req) {
    	String orderNo = Util.generateOrderNo4BaoFoo(8);
    	//记录ln_pay_orders表
        LnPayOrders lnPayOrders = new LnPayOrders();
        lnPayOrders.setCreateTime(new Date());
        lnPayOrders.setAccountType(Constants.ORDER_ACCOUNT_TYPE_SYS);
        lnPayOrders.setAmount(req.getPlanTotal());
        lnPayOrders.setChannel(Constants.CHANNEL_HFBANK);
        lnPayOrders.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_COMP_REPAY_2_DEP_TARGET.getCode());
        lnPayOrders.setMoneyType(0);
        lnPayOrders.setOrderNo(orderNo);
        lnPayOrders.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_INIT.getCode()));
        lnPayOrders.setTransType(LoanStatus.TRANS_TYPE_COMP_REPAY_2_DEP_TARGET.getCode());
        lnPayOrders.setUpdateTime(new Date());
        lnPayOrdersMapper.insertSelective(lnPayOrders);
        //记录ln_pay_orders_jnl表
        LnPayOrdersJnl lnPayOrdersJnl = new LnPayOrdersJnl();
        lnPayOrdersJnl.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_COMP_REPAY_2_DEP_TARGET.getCode());
        lnPayOrdersJnl.setCreateTime(new Date());
        lnPayOrdersJnl.setOrderId(lnPayOrders.getId());
        lnPayOrdersJnl.setOrderNo(lnPayOrders.getOrderNo());
        lnPayOrdersJnl.setTransAmount(lnPayOrders.getAmount());
        lnPayOrdersJnl.setTransCode(LoanStatus.ORDERS_JNL_STATUS_INIT.getCode());
        lnPayOrdersJnl.setSysTime(new Date());
        lnPayOrdersJnlMapper.insertSelective(lnPayOrdersJnl);
        
    	//ln_deposition_repay_schedule对应账单更新还款订单号、return_flag更新为REPAY_PROCESS
    	LnDepositionRepaySchedule schedulTemp = new LnDepositionRepaySchedule();
    	schedulTemp.setRepayOrderNo(orderNo);
		schedulTemp.setId(req.getId());
		schedulTemp.setUpdateTime(new Date());
		schedulTemp.setReturnFlag(Constants.DEP_REPAY_RETURN_FLAG_REPAY_PROCESS);
		lnDepositionRepayScheduleMapper.updateByPrimaryKeySelective(schedulTemp);
		
    	B2GReqMsg_HFBank_CompensateRepay compensateRepayReq = new B2GReqMsg_HFBank_CompensateRepay();
    	
    	compensateRepayReq.setOrder_no(orderNo);
    	compensateRepayReq.setPartner_trans_date(new Date());
    	compensateRepayReq.setPartner_trans_time(new Date());
    	compensateRepayReq.setProd_id(req.getTargetId().toString());
    	compensateRepayReq.setRepay_num(Constants.HF_REPAY_NUM_ALL);
    	compensateRepayReq.setRepay_date(req.getPlanDate());
    	compensateRepayReq.setRepay_amt(req.getRepayAmount());
    	compensateRepayReq.setReal_repay_amt(req.getRepayAmount());
    	compensateRepayReq.setReal_repay_date(new Date());
    	compensateRepayReq.setPlatcust(req.getLnHfUserId());
        compensateRepayReq.setCompensation_platcust(req.getHfUserId());
    	compensateRepayReq.setTrans_amt(req.getPlanTotal());
    	compensateRepayReq.setFee_amt(req.getRepayFee());
    	compensateRepayReq.setRepay_type(Constants.HF_COMPENSATE_REPAY_TYPE_COMPENSATE);
    	compensateRepayReq.setRemark("代偿还款");
    	
    	B2GResMsg_HFBank_CompensateRepay compensateRepayRes = hfbankTransportService.compensateRepay(compensateRepayReq);
    
    	if(ConstantUtil.BSRESCODE_SUCCESS.equals(compensateRepayRes.getResCode())){
    		LnPayOrders order = lnPayOrdersMapper.selectByPKForLock(lnPayOrders.getId());
            LnPayOrders payOrdersTemp = new LnPayOrders();
            LnPayOrdersJnl lnPayOrdersJnlSucc = new LnPayOrdersJnl();
            //修改ln_pay_orders状态
            payOrdersTemp.setId(order.getId());
            payOrdersTemp.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_SUCCESS.getCode()));
            payOrdersTemp.setUpdateTime(new Date());
            payOrdersTemp.setReturnCode(compensateRepayRes.getResCode());
            payOrdersTemp.setReturnMsg(compensateRepayRes.getResMsg());
            lnPayOrdersMapper.updateByPrimaryKeySelective(payOrdersTemp);
            //记录ln_pay_orders_jnl表
            lnPayOrdersJnlSucc.setSubAccountId(order.getSubAccountId());
            lnPayOrdersJnlSucc.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_COMP_REPAY_2_DEP_TARGET.getCode());
            lnPayOrdersJnlSucc.setTransCode(LoanStatus.ORDERS_JNL_STATUS_SUCCESS.getCode());
            lnPayOrdersJnlSucc.setCreateTime(new Date());
            lnPayOrdersJnlSucc.setOrderId(order.getId());
            lnPayOrdersJnlSucc.setOrderNo(order.getOrderNo());
            lnPayOrdersJnlSucc.setTransAmount(order.getAmount());
            lnPayOrdersJnlSucc.setUserId(order.getLnUserId());
            lnPayOrdersJnlSucc.setSysTime(new Date());
            lnPayOrdersJnlSucc.setReturnCode(compensateRepayRes.getResCode());
            lnPayOrdersJnlSucc.setReturnMsg(compensateRepayRes.getResMsg());
            lnPayOrdersJnlMapper.insertSelective(lnPayOrdersJnlSucc);
            
    		//更新存管账单表状态为REPAY_SUCC，代偿人DEP_JSH-，系统用户余额户BGW_USER-
    		schedulTemp.setId(req.getId());
    		schedulTemp.setUpdateTime(new Date());
    		schedulTemp.setReturnFlag(Constants.DEP_REPAY_RETURN_FLAG_REPAY_SUCC);
    		lnDepositionRepayScheduleMapper.updateByPrimaryKeySelective(schedulTemp);
    		
    		BaseAccount account = new BaseAccount();
			account.setInvestorUserId(req.getBsUserId());
			account.setAmount(req.getPlanTotal());
			//借款服务费入参
            Repay2DepTargetInfo repay2DepTargetInfo = new Repay2DepTargetInfo();
            LnDepositionRepayScheduleDetailExample detailExample = new LnDepositionRepayScheduleDetailExample();
            detailExample.createCriteria().andPlanIdEqualTo(req.getId())
            	.andSubjectCodeEqualTo(LoanSubjects.SUBJECT_CODE_LOAN_SERVICE_FEE.getCode());
            List<LnDepositionRepayScheduleDetail> detailList = lnDepositionRepayScheduleDetailMapper.selectByExample(detailExample);
            repay2DepTargetInfo.setPartner(req.getPartnerEnum());
            if(CollectionUtils.isNotEmpty(detailList)){
            	repay2DepTargetInfo.setDepPlanId(req.getId());
                repay2DepTargetInfo.setLoanServiceFee(detailList.get(0).getPlanAmount());
            }        
			chargeCompRepay2DepTarget(account,repay2DepTargetInfo);
    	} else {
    		LnPayOrders order = lnPayOrdersMapper.selectByPKForLock(lnPayOrders.getId());
            LnPayOrders payOrdersTemp = new LnPayOrders();
            LnPayOrdersJnl lnPayOrdersJnlFail = new LnPayOrdersJnl();
            //修改ln_pay_orders状态
            payOrdersTemp.setId(order.getId());
            payOrdersTemp.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_FAIL.getCode()));
            payOrdersTemp.setUpdateTime(new Date());
            payOrdersTemp.setReturnCode(compensateRepayRes.getResCode());
            payOrdersTemp.setReturnMsg(compensateRepayRes.getResMsg());
            lnPayOrdersMapper.updateByPrimaryKeySelective(payOrdersTemp);
            //记录ln_pay_orders_jnl表
            lnPayOrdersJnlFail.setSubAccountId(order.getSubAccountId());
            lnPayOrdersJnlFail.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_COMP_REPAY_2_DEP_TARGET.getCode());
            lnPayOrdersJnlFail.setTransCode(LoanStatus.ORDERS_JNL_STATUS_FAIL.getCode());
            lnPayOrdersJnlFail.setCreateTime(new Date());
            lnPayOrdersJnlFail.setOrderId(order.getId());
            lnPayOrdersJnlFail.setOrderNo(order.getOrderNo());
            lnPayOrdersJnlFail.setTransAmount(order.getAmount());
            lnPayOrdersJnlFail.setUserId(order.getLnUserId());
            lnPayOrdersJnlFail.setSysTime(new Date());
            lnPayOrdersJnlFail.setReturnCode(compensateRepayRes.getResCode());
            lnPayOrdersJnlFail.setReturnMsg(compensateRepayRes.getResMsg());
            lnPayOrdersJnlMapper.insertSelective(lnPayOrdersJnlFail);
    		
    		//更新存管账单表状态为REPAY_FAIL
    		schedulTemp.setId(req.getId());
    		schedulTemp.setUpdateTime(new Date());
    		schedulTemp.setReturnFlag(Constants.DEP_REPAY_RETURN_FLAG_REPAY_FAIL);
            schedulTemp.setNote(compensateRepayRes.getRemsg());
    		lnDepositionRepayScheduleMapper.updateByPrimaryKeySelective(schedulTemp);
    	}
    }


    /**
     * 标的还款至投资人账户
     */
    @Override
    public void repay2Investor(Repay2InvestorReq req) {
        final DepRepaySchedule depRepaySchedule = req.getDepRepaySchedule();
        B2GReqMsg_HFBank_ProdRepay prodRepay = new B2GReqMsg_HFBank_ProdRepay();
        String order_No = Util.generateOrderNo4BaoFoo(8);
        //记录ln_pay_orders表
        LnPayOrders lnPayOrders = new LnPayOrders();
        lnPayOrders.setPartnerCode(depRepaySchedule.getPartnerEnum().getCode());
        lnPayOrders.setCreateTime(new Date());
        lnPayOrders.setAccountType(Constants.ORDER_ACCOUNT_TYPE_SYS);
        lnPayOrders.setAmount(depRepaySchedule.getRepayAmount());
        lnPayOrders.setChannel(Constants.CHANNEL_HFBANK);
        lnPayOrders.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_REPAY_2_INVESTOR.getCode());
        lnPayOrders.setMoneyType(0);
        lnPayOrders.setOrderNo(order_No);
        lnPayOrders.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_INIT.getCode()));
        lnPayOrders.setTransType(LoanStatus.TRANS_TYPE_REPAY_2_INVESTOR.getCode());
        lnPayOrders.setUpdateTime(new Date());
        lnPayOrdersMapper.insertSelective(lnPayOrders);
        //记录ln_pay_orders_jnl表
        LnPayOrdersJnl lnPayOrdersJnl = new LnPayOrdersJnl();
        lnPayOrdersJnl.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_REPAY_2_INVESTOR.getCode());
        lnPayOrdersJnl.setCreateTime(new Date());
        lnPayOrdersJnl.setOrderId(lnPayOrders.getId());
        lnPayOrdersJnl.setOrderNo(lnPayOrders.getOrderNo());
        lnPayOrdersJnl.setTransAmount(lnPayOrders.getAmount());
        lnPayOrdersJnl.setTransCode(LoanStatus.ORDERS_JNL_STATUS_INIT.getCode());
        lnPayOrdersJnl.setSysTime(new Date());
        lnPayOrdersJnlMapper.insertSelective(lnPayOrdersJnl);
        
        prodRepay.setOrder_no(order_No);
        //获取此标的下投资人及应还款金额列表
        List<LnFinanceRepayScheduleVO> fnRepaySchedules =  lnFinanceRepayScheduleMapper
                .selectFnRepayScheduleBySerial(depRepaySchedule.getLoanId(), depRepaySchedule.getSerialId());
        if(CollectionUtils.isEmpty(fnRepaySchedules)){
            log.error("标的还款账单编号["+depRepaySchedule.getId()+"]找不到对应借贷关系列表");
            return;
        }
        List<ProdRepayReqFundDataCustRepay> repayCusts = new ArrayList<>();
        Double realRepayPrinTotal = 0d;
        Map<Integer, ProdRepayReqFundDataCustRepay> repayCustMap = new HashMap<>();
        //生成标的还款成功的记账数据
        List<RepayInfo> yunAccountList = new ArrayList<RepayInfo>();
        
        //数据准备
        for (LnFinanceRepayScheduleVO repaySchedule : fnRepaySchedules){
            log.info("标的还款至投资人账户数据准备>>>"+ repaySchedule.toString());
            //a.赋值应计利息I、债转付息T、实际还款金额R、实际还款本金P、 初始平台营收IS
            Double planInterest = repaySchedule.getPlanInterest();//应计利息I
            Double panTransInterest = repaySchedule.getPlanTransInterest() != null ? repaySchedule.getPlanTransInterest() : 0d;//债转付息T
            Double realRepayAmt = repaySchedule.getPlanTotal();//实际还款总金额R
            Double realRepayPrin = repaySchedule.getPlanPrincipal();//实际还款本金P
            realRepayPrinTotal = MoneyUtil.add(realRepayPrinTotal, realRepayPrin).doubleValue();//本金累加
            Double initRepayFee = repaySchedule.getPlanFee();//初始平台营收IS
            Double diffAmount = 0d;//实际补差B，暂时设为0
            Double realRepayInter = 0d;//实际还款利息X，暂时设为0
            Double repayFee = 0d;//平台实际营收S，暂时设为0
            BsSubAccount diffAct = null;//补差户，暂时设为null
            Double leftPlanInterest = null;//剩余应付利息Y，暂时设为null
            //b.查询对应站岗户
            LnLoanRelation relationTemp = lnLoanRelationMapper.selectByPrimaryKey(repaySchedule.getRelationId());
            BsSubAccount authAct = bsSubAccountMapper.selectByPrimaryKey(relationTemp.getBsSubAccountId());
            //c.if 为非自由站岗户 则调用原业务逻辑，Y默认为null;
            if(!Constants.PRODUCT_TYPE_AUTH_FREE.equals(authAct.getProductType())){
                log.info(authAct.getProductType()+"标的还款至投资人账户初始金额[relationId="+repaySchedule.getRelationId()+"]：" +
                        "应计利息I="+planInterest+"/债转付息T="+panTransInterest+"/实际还款金额R="+realRepayAmt+
                        "/实际还款本金P="+realRepayPrin+"/初始平台营收IS="+initRepayFee+"/剩余应付利息Y="+leftPlanInterest);
                //查询对应补差户，获得补差金额B = B>0 ? B : 0
                diffAct = bsSubAccountPairMapper.selectDiffActByLoanRelationId(repaySchedule.getRelationId());
                diffAmount = diffAct != null && diffAct.getBalance().compareTo(0d)>0 ? diffAct.getBalance() : 0d;
                //平台营收、补差再次确认
                //S=(IS-B)<0 ? 0 : (IS-B)
                repayFee = (initRepayFee.compareTo(diffAmount) < 0) ? 0d : CalculatorUtil.calculate("a-a", initRepayFee, diffAmount);
                //B`=B= (IS-B)<0 ? IS : B
                diffAmount = (initRepayFee.compareTo(diffAmount) < 0) ? initRepayFee : diffAmount;
                //X= (I+T)+B
                realRepayInter = CalculatorUtil.calculate("a+a+a", planInterest, panTransInterest, diffAmount);
            }else {//d.else if 为自由站岗户，获得剩余应付利息Y
                leftPlanInterest = authAct.getLeftPlanInterest();//剩余应付利息Y
                log.info(authAct.getProductType()+"标的还款至投资人账户初始金额[relationId="+repaySchedule.getRelationId()+"]：" +
                        "应计利息I="+planInterest+"/债转付息T="+panTransInterest+"/实际还款金额R="+realRepayAmt+
                        "/实际还款本金P="+realRepayPrin+"/初始平台营收IS="+initRepayFee+"/剩余应付利息Y="+leftPlanInterest);
                //da.if Y<=0 实际还款利息X=0，平台营收S=IS+I+T，补差B=0
                if(leftPlanInterest <= 0){
                    realRepayInter = 0d;
                    diffAmount = 0d;
                    repayFee =  CalculatorUtil.calculate("a+a+a", initRepayFee, planInterest, panTransInterest);
                }else if(leftPlanInterest > 0){ //db.else if Y>0  考虑补差B
                    Double interestTemp = CalculatorUtil.calculate("a+a", planInterest, panTransInterest);
                    if(leftPlanInterest.compareTo(interestTemp) == 0){//db.1、if Y=(I+T) 则B=0,X=Y,S=IS
                        diffAmount = 0d;
                        realRepayInter = leftPlanInterest;
                        repayFee = initRepayFee;
                    }else if(leftPlanInterest.compareTo(interestTemp) < 0){//db.2、else if Y<(I+T) 则B=0,X=Y,S=IS+((I+T)-Y)
                        diffAmount = 0d;
                        realRepayInter = leftPlanInterest;
                        repayFee = CalculatorUtil.calculate("a+a-a", initRepayFee, interestTemp, leftPlanInterest);
                    }else if(leftPlanInterest.compareTo(interestTemp) > 0){//db.3、else if Y>(I+T) 则考虑补差B
                        //查询对应补差户，获得补差金额B = B>0 ? B : 0
                        diffAct = bsSubAccountPairMapper.selectDiffActByLoanRelationId(repaySchedule.getRelationId());
                        diffAmount = diffAct != null && diffAct.getBalance().compareTo(0d)>0 ? diffAct.getBalance() : 0d;
                        //平台营收、补差再次确认
                        //S=(IS-B)<0 ? 0 : (IS-B)
                        repayFee = (initRepayFee.compareTo(diffAmount) < 0) ? 0d : CalculatorUtil.calculate("a-a", initRepayFee, diffAmount);
                        //B`=B= (IS-B)<0 ? IS : B
                        diffAmount = (initRepayFee.compareTo(diffAmount) < 0) ? initRepayFee : diffAmount;
                        Double diffAmountTemp = diffAmount; // B`=B
                        //B= (Y-(I+T)) > B ? B : (Y-(I+T))
                        diffAmount = (CalculatorUtil.calculate("a-a-a", leftPlanInterest, interestTemp, diffAmount) > 0 )
                                ? diffAmount : CalculatorUtil.calculate("a-a", leftPlanInterest, interestTemp);
                        //S= S + (B`- B)
                        repayFee = CalculatorUtil.calculate("a+a-a", repayFee, diffAmountTemp, diffAmount);
                        //X= (I+T)+B
                        realRepayInter = CalculatorUtil.calculate("a+a", interestTemp, diffAmount);
                    }
                }

            }
            log.info(authAct.getProductType()+"标的还款至投资人账户计算后金额:实际还款利息X="+realRepayInter+"/平台实际营收S="+
                    repayFee+"/实际补差B="+diffAmount+"/分账前剩余应付利息Y="+leftPlanInterest);

            ProdRepayReqFundDataCustRepay repayCust = new ProdRepayReqFundDataCustRepay();
            repayCust.setRepay_num(String.valueOf(repaySchedule.getRepaySerial()));
            repayCust.setRepay_date(DateUtil.format(repaySchedule.getPlanDate(), DateUtil.PAT_DATE));
            repayCust.setCust_no(repaySchedule.getHfUserId());
            repayCust.setExperience_amt("0");
            repayCust.setRates_amt("0");
            repayCust.setReal_repay_amount(String.valueOf(realRepayPrin));
            repayCust.setReal_repay_amt(String.valueOf(realRepayAmt));
            repayCust.setReal_repay_date(DateUtil.format(new Date(), DateUtil.PAT_DATE));
            repayCust.setReal_repay_val(String.valueOf(realRepayInter));
            repayCust.setRepay_fee(String.valueOf(repayFee));
            repayCusts.add(repayCust);
            repayCustMap.put(repaySchedule.getId(), repayCust);
            LnFinanceRepaySchedule fnScheduleTemp = new LnFinanceRepaySchedule();
            fnScheduleTemp.setId(repaySchedule.getId());
            fnScheduleTemp.setDiffAmount(diffAmount);
            fnScheduleTemp.setLeftPlanInterest(leftPlanInterest);
            fnScheduleTemp.setStatus(Constants.FINANCE_REPAY_SATAE_REPAYING);
            fnScheduleTemp.setUpdateTime(new Date());
            lnFinanceRepayScheduleMapper.updateByPrimaryKeySelective(fnScheduleTemp);
            //LnDepositionRepaySchedule修改
            LnDepositionRepaySchedule schedule = new LnDepositionRepaySchedule();
            schedule.setId(depRepaySchedule.getId());
            schedule.setReturnOrderNo(prodRepay.getOrder_no());
            schedule.setUpdateTime(new Date());
            schedule.setReturnFlag(LoanStatus.DEP_REPAY_SCHEDULE_RETURN_FLAG_RETURN_PROCESS.getCode());
            lnDepositionRepayScheduleMapper.updateByPrimaryKeySelective(schedule);
            //查询子账户信息，判断是否为REG户，是则不需要数据准备
        	BsSubAccount subAccount = bsSubAccountMapper.selectByPrimaryKey(repaySchedule.getBsSubAccountId());
            if(!PartnerEnum.ZAN.equals(depRepaySchedule.getPartnerEnum()) && 
            		(!Constants.PRODUCT_TYPE_REG.equals(subAccount.getProductType()))){
                RepayInfo account = new RepayInfo();
                account.setPartner(depRepaySchedule.getPartnerEnum());
                account.setInvestorUserId(repaySchedule.getUserId());
                account.setAuthActId(repaySchedule.getBsSubAccountId());
                account.setDiffActId(diffAct!=null ? diffAct.getId() : null);
                account.setDiffAmount(diffAmount);
                account.setPrincipal(Double.valueOf(repayCust.getReal_repay_amount()));
                account.setInterest(Double.valueOf(repayCust.getReal_repay_val()));
                account.setServiceFee(Double.valueOf(repayCust.getRepay_fee()));
                account.setLnFinancePlanId(repaySchedule.getId());
                //云贷记账数据添加
                yunAccountList.add(account);
            }
        }
        if(!PartnerEnum.ZAN.equals(depRepaySchedule.getPartnerEnum()) && CollectionUtils.isNotEmpty(yunAccountList)){
        	DataBackUpProcess process = new DataBackUpProcess();
        	process.setRepayInfos(yunAccountList);
        	process.setDepRepayScheduleId(depRepaySchedule.getId());
        	new Thread(process).start();
        }
        
        //发起标的还款
        ProdRepayReqFundData fundData = new ProdRepayReqFundData();
        fundData.setCustRepayList(repayCusts);
        prodRepay.setFunddata(fundData);
        //判断标的是否还清
        Double leftAmount = lnLoanRelationMapper.getRelationAmountByLoanId(depRepaySchedule.getLoanId());
        prodRepay.setIs_payoff(leftAmount.compareTo(MoneyUtil.defaultRound(realRepayPrinTotal).doubleValue()) == 0 ?
                DepIsPayOffEnum.DEP_IS_PAY_OFF_YES.getCode() : DepIsPayOffEnum.DEP_IS_PAY_OFF_NO.getCode());
        prodRepay.setPartner_trans_date(new Date());
        prodRepay.setPartner_trans_time(new Date());
        prodRepay.setProd_id(String.valueOf(depRepaySchedule.getTargetId()));
        prodRepay.setRepay_num(depRepaySchedule.getSerialId());
        prodRepay.setTrans_amt(depRepaySchedule.getRepayAmount());
        prodRepay.setRepay_flag(DepRepayFlagEnum.DEP_REPAY_FLAG_YES.getCode());//本期是否已还清 0-是 1-否
        //发起标的还款
        B2GResMsg_HFBank_ProdRepay resMsg = null;
        try {
            resMsg = hfbankTransportService.prodRepay(prodRepay);
        } catch (Exception e) {
            log.error("标的还款至投资人通讯失败：{}", e);
            resMsg = new B2GResMsg_HFBank_ProdRepay();
            resMsg.setResCode(LoanStatus.BAOFOO_PAY_STATUS_PROCESS.getCode());
            resMsg.setResMsg("通讯失败，置为处理中");
            //标的还款异常告警
        	specialJnlService.warn4Fail(null, "标的还款异常", "标的编号：[" + depRepaySchedule.getTargetId() + "]", "【标的还款】",false);
            log.info("标的还款异常:" + "标的编号：[" + depRepaySchedule.getTargetId() + "]");
            return;
        }
        
        log.info("标的还款至投资人账户的订单号=["+order_No+"]产品id=["+String.valueOf(depRepaySchedule.getTargetId())+"]resMsg=["+resMsg+"]");
        //结果处理
        LnDepositionRepaySchedule scheduleTemp = new LnDepositionRepaySchedule();
        LnDepositionRepayScheduleExample depScheduleExample = new LnDepositionRepayScheduleExample();
        depScheduleExample.createCriteria().andReturnOrderNoEqualTo(prodRepay.getOrder_no());
        if(ConstantUtil.BSRESCODE_SUCCESS.equals(resMsg.getResCode())){
            //return_flag 整个订单标的还款成功
            scheduleTemp.setReturnFlag(LoanStatus.DEP_REPAY_SCHEDULE_RETURN_FLAG_RETURN_SUCC.getCode());
            scheduleTemp.setStatus(LoanStatus.REPAY_SCHEDULE_STATUS_REPAIED.getCode());
            scheduleTemp.setFinishTime(new Date());
            //记账
            if(!PartnerEnum.ZAN.equals(depRepaySchedule.getPartnerEnum())){
            	for (RepayInfo repayInfo : yunAccountList) {
            		//查询子账户信息，判断是否为REG户，是则不需要数据准备
                	BsSubAccount subAccount = bsSubAccountMapper.selectByPrimaryKey(repayInfo.getAuthActId());
                	if(!Constants.PRODUCT_TYPE_REG.equals(subAccount.getProductType())){
                		//云贷记账
                        chargeRepay2Investor4Yun(repayInfo);
                	}
				}
            }
            for (final LnFinanceRepayScheduleVO repaySchedule : fnRepaySchedules){
                final ProdRepayReqFundDataCustRepay repayCust = repayCustMap.get(repaySchedule.getId());
                if(PartnerEnum.ZAN.equals(depRepaySchedule.getPartnerEnum())){
                    //赞分期回款表增加已回款记录
                    BsLoanFinanceRepayExample financeRepayExample = new BsLoanFinanceRepayExample();
                    financeRepayExample.createCriteria().andFinanceRepayScheduleIdEqualTo(repaySchedule.getId());
                    List<BsLoanFinanceRepay> financeRepays = bsLoanFinanceRepayMapper.selectByExample(financeRepayExample);
                    BsLoanFinanceRepay finalLoanFinanceRepay;
                    if(CollectionUtils.isNotEmpty(financeRepays)){
                        BsLoanFinanceRepay financeRepayPlan = new BsLoanFinanceRepay();
                        financeRepayPlan.setId(financeRepays.get(0).getId());
                        financeRepayPlan.setStatus(Constants.FINANCE_REPAY_SATAE_REPAIED);
                        financeRepayPlan.setUpdateTime(new Date());
                        bsLoanFinanceRepayMapper.updateByPrimaryKeySelective(financeRepayPlan);
                        finalLoanFinanceRepay = bsLoanFinanceRepayMapper.selectByPrimaryKey(financeRepayPlan.getId());
                    }else {
                        BsLoanFinanceRepay financeRepayPlan = new BsLoanFinanceRepay();
                        financeRepayPlan.setCreateTime(new Date());
                        financeRepayPlan.setFnUserId(repaySchedule.getUserId());
                        financeRepayPlan.setFinanceRepayScheduleId(repaySchedule.getId());
                        financeRepayPlan.setInterest(repaySchedule.getPlanInterest());
                        financeRepayPlan.setPrincipal(repaySchedule.getPlanPrincipal());
                        financeRepayPlan.setOrderNo(null);
                        financeRepayPlan.setStatus(Constants.FINANCE_REPAY_SATAE_REPAIED);
                        financeRepayPlan.setTotal(MoneyUtil.add(repaySchedule.getPlanPrincipal(), repaySchedule.getPlanInterest()).doubleValue());
                    		/*MoneyUtil.defaultRound(repaySchedule.getPlanPrincipal() + repaySchedule.getPlanInterest()).doubleValue());*/
                        financeRepayPlan.setUpdateTime(new Date());
                        financeRepayPlan.setPlanDate(repaySchedule.getPlanDate());
                        bsLoanFinanceRepayMapper.insertSelective(financeRepayPlan);
                        finalLoanFinanceRepay = financeRepayPlan;
                    }
                    final Date now = new Date();
                    final Integer fnUserId = repaySchedule.getUserId();
                    final Double amount = finalLoanFinanceRepay.getTotal();
                    final Double productPrincipal = finalLoanFinanceRepay.getPrincipal();
                    final Double productInterest = finalLoanFinanceRepay.getInterest();
                    final BsUser fnUser = userService.findUserById(fnUserId);
                    //查询回款计划及借贷关系
                    final LnFinanceRepaySchedule financeRepaySchedule = lnFinanceRepayScheduleMapper.selectByPrimaryKey(finalLoanFinanceRepay.getFinanceRepayScheduleId());
                    final LnLoanRelation loanRelation = lnLoanRelationMapper.selectByPrimaryKey(financeRepaySchedule.getRelationId());
                    final Integer subAccountId = loanRelation.getBsSubAccountId();
                    final BsSubAccountVO productAccount = subAccountService.findMyInvestByUserIdAndId(fnUserId, subAccountId);
                    transactionTemplate.execute(new TransactionCallback<Boolean>() {
                        @Override
                        public Boolean doInTransaction(TransactionStatus status) {
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

                            //赞分期记账
                            ReceiveMoneyInfo receiveMoneyInfo = new ReceiveMoneyInfo();
                            receiveMoneyInfo.setAmount(MoneyUtil.add(repaySchedule.getPlanPrincipal(), repaySchedule.getPlanInterest()).doubleValue());
                            receiveMoneyInfo.setPrincipal(repaySchedule.getPlanPrincipal());
                            receiveMoneyInfo.setInterest(repaySchedule.getPlanInterest());
                            receiveMoneyInfo.setPartner(depRepaySchedule.getPartnerEnum());
                            receiveMoneyInfo.setInvestorRegActId(repaySchedule.getBsSubAccountId());
                            receiveMoneyInfo.setInvestorUserId(repaySchedule.getUserId());
                            receiveMoneyInfo.setServiceFee(Double.valueOf(repayCust.getRepay_fee()));
                            receiveMoneyInfo.setLnFinancePlanId(repaySchedule.getId());
                            repayAccountService.chargeReceiveMoney2Balance(receiveMoneyInfo);
                            return null;
                        }
                    });
                }
                LnFinanceRepaySchedule fnScheduleTemp = new LnFinanceRepaySchedule();
                fnScheduleTemp.setId(repaySchedule.getId());
                fnScheduleTemp.setStatus(Constants.FINANCE_REPAY_SATAE_REPAIED);
                fnScheduleTemp.setDoneTime(new Date());
                fnScheduleTemp.setUpdateTime(new Date());
                lnFinanceRepayScheduleMapper.updateByPrimaryKeySelective(fnScheduleTemp);

                //计划还款本金大于0，修改借贷关系表，金额变动表
                if(repaySchedule.getPlanPrincipal() > 0){
                    LnLoanRelation relation = lnLoanRelationMapper.selectByPrimaryKey(repaySchedule.getRelationId());
                    LnLoanRelation relationTemp = new LnLoanRelation();
                    relationTemp.setId(relation.getId());
                    relationTemp.setLeftAmount(MoneyUtil.subtract(relation.getLeftAmount(),repaySchedule.getPlanPrincipal()).doubleValue());
                    if(repaySchedule.getPlanPrincipal().compareTo(relation.getLeftAmount()) == 0){
                        //计划还款本金和借贷关系表剩余本金相等
                        if(!Constants.LOAN_RELATION_STATUS_TRANSFERRED.equals(relation.getStatus())){
                            //已经是转让，状态不修改；否则修改为已还完
                            relationTemp.setStatus(Constants.LOAN_RELATION_STATUS_REPAID);
                        }

                    }
                    relationTemp.setUpdateTime(new Date());
                    lnLoanRelationMapper.updateByPrimaryKeySelective(relationTemp);

                    LnLoanAmountChange change = new LnLoanAmountChange();
                    change.setRelationId(relation.getId());
                    change.setBeforeAmount(relation.getLeftAmount());
                    change.setAfterAmount(relationTemp.getLeftAmount());
                    change.setChangeAmount(repaySchedule.getPlanPrincipal());
                    change.setCreateTime(new Date());
                    change.setUpdateTime(new Date());

                    lnLoanAmountChangeMapper.insertSelective(change);
                }
            }
            
            LnPayOrders order = lnPayOrdersMapper.selectByPKForLock(lnPayOrders.getId());
            LnPayOrders payOrdersTemp = new LnPayOrders();
            LnPayOrdersJnl lnPayOrdersJnlSucc = new LnPayOrdersJnl();
            //修改ln_pay_orders状态
            payOrdersTemp.setId(order.getId());
            payOrdersTemp.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_SUCCESS.getCode()));
            payOrdersTemp.setUpdateTime(new Date());
            payOrdersTemp.setReturnCode(resMsg.getResCode());
            payOrdersTemp.setReturnMsg(resMsg.getResMsg());
            lnPayOrdersMapper.updateByPrimaryKeySelective(payOrdersTemp);
            //记录ln_pay_orders_jnl表
            lnPayOrdersJnlSucc.setSubAccountId(order.getSubAccountId());
            lnPayOrdersJnlSucc.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_REPAY_2_INVESTOR.getCode());
            lnPayOrdersJnlSucc.setTransCode(LoanStatus.ORDERS_JNL_STATUS_SUCCESS.getCode());
            lnPayOrdersJnlSucc.setCreateTime(new Date());
            lnPayOrdersJnlSucc.setOrderId(order.getId());
            lnPayOrdersJnlSucc.setOrderNo(order.getOrderNo());
            lnPayOrdersJnlSucc.setTransAmount(order.getAmount());
            lnPayOrdersJnlSucc.setUserId(order.getLnUserId());
            lnPayOrdersJnlSucc.setSysTime(new Date());
            lnPayOrdersJnlSucc.setReturnCode(resMsg.getResCode());
            lnPayOrdersJnlSucc.setReturnMsg(resMsg.getResMsg());
            lnPayOrdersJnlMapper.insertSelective(lnPayOrdersJnlSucc);

        }else if(LoanStatus.BAOFOO_PAY_STATUS_PROCESS.getCode().equals(resMsg.getResCode())){
            //超时告警
            specialJnlService.warn4Fail(null, "标的还款超时", "标的编号：[" + depRepaySchedule.getTargetId() + "]", "【标的还款】",false);
            log.info("标的还款超时:" + "标的编号：[" + depRepaySchedule.getTargetId() + "]");
            
            LnPayOrders order = lnPayOrdersMapper.selectByPKForLock(lnPayOrders.getId());
            LnPayOrders payOrdersTemp = new LnPayOrders();
            LnPayOrdersJnl lnPayOrdersJnlProcess = new LnPayOrdersJnl();
            //修改ln_pay_orders状态
            payOrdersTemp.setId(order.getId());
            payOrdersTemp.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_PAYING.getCode()));
            payOrdersTemp.setUpdateTime(new Date());
            payOrdersTemp.setReturnCode(resMsg.getResCode());
            payOrdersTemp.setReturnMsg(resMsg.getResMsg());
            lnPayOrdersMapper.updateByPrimaryKeySelective(payOrdersTemp);
            //记录ln_pay_orders_jnl表
            lnPayOrdersJnlProcess.setSubAccountId(order.getSubAccountId());
            lnPayOrdersJnlProcess.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_REPAY_2_INVESTOR.getCode());
            lnPayOrdersJnlProcess.setTransCode(LoanStatus.ORDERS_JNL_STATUS_PAYING.getCode());
            lnPayOrdersJnlProcess.setCreateTime(new Date());
            lnPayOrdersJnlProcess.setOrderId(order.getId());
            lnPayOrdersJnlProcess.setOrderNo(order.getOrderNo());
            lnPayOrdersJnlProcess.setTransAmount(order.getAmount());
            lnPayOrdersJnlProcess.setUserId(order.getLnUserId());
            lnPayOrdersJnlProcess.setSysTime(new Date());
            lnPayOrdersJnlProcess.setReturnCode(resMsg.getResCode());
            lnPayOrdersJnlProcess.setReturnMsg(resMsg.getResMsg());
            lnPayOrdersJnlMapper.insertSelective(lnPayOrdersJnlProcess);
            return;
        }else{
        	//标的还款失败告警
        	specialJnlService.warn4Fail(null, "标的还款失败", "标的编号：[" + depRepaySchedule.getTargetId() + "]", "【标的还款】",false);
            log.info("标的还款失败:" + "标的编号：[" + depRepaySchedule.getTargetId() + "]");
            //return_flag 整个订单标的还款失败
            scheduleTemp.setReturnFlag(LoanStatus.DEP_REPAY_SCHEDULE_RETURN_FLAG_RETURN_FAIL.getCode());

            LnPayOrders order = lnPayOrdersMapper.selectByPKForLock(lnPayOrders.getId());
            LnPayOrders payOrdersTemp = new LnPayOrders();
            LnPayOrdersJnl lnPayOrdersJnlFail = new LnPayOrdersJnl();
            //修改ln_pay_orders状态
            payOrdersTemp.setId(order.getId());
            payOrdersTemp.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_FAIL.getCode()));
            payOrdersTemp.setUpdateTime(new Date());
            payOrdersTemp.setReturnCode(resMsg.getResCode());
            payOrdersTemp.setReturnMsg(resMsg.getResMsg());
            lnPayOrdersMapper.updateByPrimaryKeySelective(payOrdersTemp);
            //记录ln_pay_orders_jnl表
            lnPayOrdersJnlFail.setSubAccountId(order.getSubAccountId());
            lnPayOrdersJnlFail.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_REPAY_2_INVESTOR.getCode());
            lnPayOrdersJnlFail.setTransCode(LoanStatus.ORDERS_JNL_STATUS_FAIL.getCode());
            lnPayOrdersJnlFail.setCreateTime(new Date());
            lnPayOrdersJnlFail.setOrderId(order.getId());
            lnPayOrdersJnlFail.setOrderNo(order.getOrderNo());
            lnPayOrdersJnlFail.setTransAmount(order.getAmount());
            lnPayOrdersJnlFail.setUserId(order.getLnUserId());
            lnPayOrdersJnlFail.setSysTime(new Date());
            lnPayOrdersJnlFail.setReturnCode(resMsg.getResCode());
            lnPayOrdersJnlFail.setReturnMsg(resMsg.getResMsg());
            lnPayOrdersJnlMapper.insertSelective(lnPayOrdersJnlFail);
        }
        scheduleTemp.setUpdateTime(new Date());
        scheduleTemp.setNote(resMsg.getRemsg());
        lnDepositionRepayScheduleMapper.updateByExampleSelective(scheduleTemp, depScheduleExample);

    }

    /**
     * 赞分期标的还款记账（回款）
     * F:REG_D - 本金
     * F:DEP_JSH + (本金+用户利息)
     * S:BGW_USER +
     * S:BGW_RETURN_ZAN - (本金+用户利息)
     * S:DEP_BGW_REVENUE_ZAN +
     * @param receiveMoneyInfo
     */
    private void chargeRepay2Investor4Zan(ReceiveMoneyInfo receiveMoneyInfo){
        repayAccountService.chargeReceiveMoney2Balance(receiveMoneyInfo);
    }

    /**
     * 云贷标的还款记账
     * F:AUTH +
     * F:DIFF -
     * S:AUTH +
     * S:REG -
     * S:DEP_BGW_REVENUE_YUN +
     * @param account
     */
    private void chargeRepay2Investor4Yun(RepayInfo account) {
        log.info("[chargeRepay2Investor4Yun]入参：" + account.toString());
        final PartnerEnum partner = account.getPartner();
        final Integer userId = account.getInvestorUserId();
        final Integer authActId = account.getAuthActId();
        final Integer diffActId = account.getDiffActId();
        final Double diffAmount = account.getDiffAmount();
        final Double principal = account.getPrincipal();
        final Double interest = account.getInterest();
        final Double serviceFee = account.getServiceFee();
        final Integer lnFinancePlanId = account.getLnFinancePlanId();
        if(partner == null || userId == null || authActId == null || serviceFee == null ||
                diffAmount == null || (diffAmount > 0 && diffActId == null) || principal == null || interest == null){
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            public void doInTransactionWithoutResult(TransactionStatus status) {
                //F:AUTH +
                BsSubAccount authAct = bsSubAccountMapper.selectSubAccountByIdForLock(authActId);
                Double prinAndInter = MoneyUtil.add(principal, interest).doubleValue();
                BsSubAccount tempAuthAct = new BsSubAccount();
                tempAuthAct.setId(authAct.getId());
                tempAuthAct.setBalance(MoneyUtil.add(authAct.getBalance(), prinAndInter).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
                tempAuthAct.setAvailableBalance(MoneyUtil.add(authAct.getAvailableBalance(), prinAndInter).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
                tempAuthAct.setCanWithdraw(MoneyUtil.add(authAct.getCanWithdraw(), prinAndInter).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
                //若此站岗户当前应付利息Y不为null，则更新此字段为Y-实际还款利息X
                tempAuthAct.setLeftPlanInterest(authAct.getLeftPlanInterest()==null ? null :
                        MoneyUtil.subtract(authAct.getLeftPlanInterest(), interest).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
                tempAuthAct.setLastTransDate(new Date());
                bsSubAccountMapper.updateByPrimaryKeySelective(tempAuthAct);
                //站岗户记账
                BsAccountJnl authActJnl = new BsAccountJnl();
                authActJnl.setTransTime(new Date());
                authActJnl.setTransCode(Constants.USER_AUTH_REPAY_ADD);
                authActJnl.setTransName("标的还款到站岗户");
                authActJnl.setTransAmount(prinAndInter);
                authActJnl.setSysTime(new Date());
                authActJnl.setCdFlag1(Constants.CD_FLAG_D);
                authActJnl.setUserId1(userId);
                authActJnl.setAccountId1(authAct.getAccountId());
                authActJnl.setSubAccountId1(authAct.getId());
                authActJnl.setBeforeBalance1(authAct.getBalance());
                authActJnl.setAfterBalance1(tempAuthAct.getBalance());
                authActJnl.setBeforeAvialableBalance1(authAct.getAvailableBalance());
                authActJnl.setAfterAvialableBalance1(tempAuthAct.getAvailableBalance());
                authActJnl.setBeforeFreezeBalance1(authAct.getFreezeBalance());
                authActJnl.setAfterFreezeBalance1(authAct.getFreezeBalance());
                authActJnl.setFee(0.0);
                bsAccountJnlMapper.insertSelective(authActJnl);
                //F:DIFF -
                if(diffAmount>0){
                    BsSubAccount diffAct = bsSubAccountMapper.selectSubAccountByIdForLock(diffActId);
                    BsSubAccount tempDiffAct = new BsSubAccount();
                    tempDiffAct.setId(diffAct.getId());
                    tempDiffAct.setBalance(MoneyUtil.subtract(diffAct.getBalance(), diffAmount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
                    tempDiffAct.setAvailableBalance(MoneyUtil.subtract(diffAct.getAvailableBalance(), diffAmount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
                    tempDiffAct.setCanWithdraw(MoneyUtil.subtract(diffAct.getCanWithdraw(), diffAmount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
                    tempDiffAct.setLastTransDate(new Date());
                    bsSubAccountMapper.updateByPrimaryKeySelective(tempDiffAct);
                    //补差记账
                    BsAccountJnl diffActJnl = new BsAccountJnl();
                    diffActJnl.setTransTime(new Date());
                    diffActJnl.setTransCode(Constants.USER_DIFF_OUT);
                    diffActJnl.setTransName("补差");
                    diffActJnl.setTransAmount(diffAmount);
                    diffActJnl.setSysTime(new Date());
                    diffActJnl.setCdFlag1(Constants.CD_FLAG_C);
                    diffActJnl.setUserId1(userId);
                    diffActJnl.setAccountId1(diffAct.getAccountId());
                    diffActJnl.setSubAccountId1(diffAct.getId());
                    diffActJnl.setBeforeBalance1(diffAct.getBalance());
                    diffActJnl.setAfterBalance1(tempDiffAct.getBalance());
                    diffActJnl.setBeforeAvialableBalance1(diffAct.getAvailableBalance());
                    diffActJnl.setAfterAvialableBalance1(tempDiffAct.getAvailableBalance());
                    diffActJnl.setBeforeFreezeBalance1(diffAct.getFreezeBalance());
                    diffActJnl.setAfterFreezeBalance1(diffAct.getFreezeBalance());
                    diffActJnl.setFee(0.0);
                    bsAccountJnlMapper.insertSelective(diffActJnl);
                }
                //S:AUTH +
                PartnerSysActCode partnerActCode = BaseAccount.partnerActCodeMap.get(partner);
                if(Constants.PRODUCT_TYPE_AUTH_FREE.equals(authAct.getProductType())){
                    partnerActCode = BaseAccount.getFreeActLoanPartner(partner);//若是用户子账户是自由站岗户，则系统户需要重新获取
                }
                BsSysSubAccount patAuthAct = bsSysSubAccountService.findSysSubAccount4Lock(partnerActCode.getAuthActCode());
                BsSysSubAccount tempPatAuthAct = new BsSysSubAccount();
                tempPatAuthAct.setId(patAuthAct.getId());
                tempPatAuthAct.setBalance(MoneyUtil.add(patAuthAct.getBalance(), prinAndInter).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                tempPatAuthAct.setAvailableBalance(MoneyUtil.add(patAuthAct.getAvailableBalance(), prinAndInter).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
                tempPatAuthAct.setCanWithdraw(MoneyUtil.add(patAuthAct.getCanWithdraw(), prinAndInter).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
                bsSysSubAccountMapper.updateByPrimaryKeySelective(tempPatAuthAct);
                //S:REG -
                BsSysSubAccount patRegAct = bsSysSubAccountService.findSysSubAccount4Lock(partnerActCode.getRegActCode());
                BsSysSubAccount tempPatRegAct = new BsSysSubAccount();
                tempPatRegAct.setId(patRegAct.getId());
                tempPatRegAct.setBalance(MoneyUtil.subtract(patRegAct.getBalance(), principal).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                tempPatRegAct.setAvailableBalance(MoneyUtil.subtract(patRegAct.getAvailableBalance(), principal).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
                tempPatRegAct.setCanWithdraw(MoneyUtil.subtract(patRegAct.getCanWithdraw(), principal).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
                bsSysSubAccountMapper.updateByPrimaryKeySelective(tempPatRegAct);
                //系统标的还款到站岗户记账
                BsSysAccountJnl patAuthActJnl = new BsSysAccountJnl();
                patAuthActJnl.setTransTime(new Date());
                patAuthActJnl.setTransCode(Constants.SYS_REPAY_2_INVESTOR_AUTH);
                patAuthActJnl.setTransName("标的还款到站岗户");
                patAuthActJnl.setTransAmount(prinAndInter);
                patAuthActJnl.setSysTime(new Date());
                patAuthActJnl.setChannelTime(null);
                patAuthActJnl.setChannelJnlNo(null);
                patAuthActJnl.setCdFlag1(Constants.CD_FLAG_D);
                patAuthActJnl.setSubAccountCode1(patAuthAct.getCode());
                patAuthActJnl.setBeforeBalance1(patAuthAct.getBalance());
                patAuthActJnl.setAfterBalance1(tempPatAuthAct.getBalance());
                patAuthActJnl.setBeforeAvialableBalance1(patAuthAct.getAvailableBalance());
                patAuthActJnl.setAfterAvialableBalance1(tempPatAuthAct.getAvailableBalance());
                patAuthActJnl.setBeforeFreezeBalance1(patAuthAct.getFreezeBalance());
                patAuthActJnl.setAfterFreezeBalance1(patAuthAct.getFreezeBalance());
                patAuthActJnl.setCdFlag2(Constants.CD_FLAG_C);
                patAuthActJnl.setSubAccountCode2(patRegAct.getCode());
                patAuthActJnl.setBeforeBalance2(patRegAct.getBalance());
                patAuthActJnl.setAfterBalance2(tempPatRegAct.getBalance());
                patAuthActJnl.setBeforeAvialableBalance2(patRegAct.getAvailableBalance());
                patAuthActJnl.setAfterAvialableBalance2(tempPatRegAct.getAvailableBalance());
                patAuthActJnl.setBeforeFreezeBalance2(patRegAct.getFreezeBalance());
                patAuthActJnl.setAfterFreezeBalance2(patRegAct.getFreezeBalance());
                bsSysAccountJnlMapper.insertSelective(patAuthActJnl);
                //S:DEP_BGW_REVENUE +
                if(serviceFee >= 0){
                    BsSysSubAccount patBgwDepRevenueAct = bsSysSubAccountService.findSysSubAccount4Lock(partnerActCode.getBgwDepRevenueActCode());
                    BsSysSubAccount tempPatBgwDepRevenueAct = new BsSysSubAccount();
                    tempPatBgwDepRevenueAct.setId(patBgwDepRevenueAct.getId());
                    tempPatBgwDepRevenueAct.setBalance(MoneyUtil.add(patBgwDepRevenueAct.getBalance(), serviceFee).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    tempPatBgwDepRevenueAct.setAvailableBalance(MoneyUtil.add(patBgwDepRevenueAct.getAvailableBalance(), serviceFee).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
                    tempPatBgwDepRevenueAct.setCanWithdraw(MoneyUtil.add(patBgwDepRevenueAct.getCanWithdraw(), serviceFee).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
                    bsSysSubAccountMapper.updateByPrimaryKeySelective(tempPatBgwDepRevenueAct);
                    //系统标的还款营收记账
                    BsSysAccountJnl patBgwDepRevenueActJnl = new BsSysAccountJnl();
                    patBgwDepRevenueActJnl.setTransTime(new Date());
                    patBgwDepRevenueActJnl.setTransCode(Constants.SYS_REPAY_2_INVESTOR_REVENUE);
                    patBgwDepRevenueActJnl.setTransName("标的还款营收");
                    patBgwDepRevenueActJnl.setTransAmount(serviceFee);
                    patBgwDepRevenueActJnl.setSysTime(new Date());
                    patBgwDepRevenueActJnl.setChannelTime(null);
                    patBgwDepRevenueActJnl.setChannelJnlNo(null);
                    patBgwDepRevenueActJnl.setCdFlag1(Constants.CD_FLAG_D);
                    patBgwDepRevenueActJnl.setSubAccountCode1(patBgwDepRevenueAct.getCode());
                    patBgwDepRevenueActJnl.setBeforeBalance1(patBgwDepRevenueAct.getBalance());
                    patBgwDepRevenueActJnl.setAfterBalance1(tempPatBgwDepRevenueAct.getBalance());
                    patBgwDepRevenueActJnl.setBeforeAvialableBalance1(patBgwDepRevenueAct.getAvailableBalance());
                    patBgwDepRevenueActJnl.setAfterAvialableBalance1(tempPatBgwDepRevenueAct.getAvailableBalance());
                    patBgwDepRevenueActJnl.setBeforeFreezeBalance1(patBgwDepRevenueAct.getFreezeBalance());
                    patBgwDepRevenueActJnl.setAfterFreezeBalance1(patBgwDepRevenueAct.getFreezeBalance());
                    
                    patBgwDepRevenueActJnl.setOpId(lnFinancePlanId);
                    bsSysAccountJnlMapper.insertSelective(patBgwDepRevenueActJnl);
                }
            }
        });
    }

    /**
     * 提现到还款专户记账
     * S:THD_REPAY -
     * @param account
     */
    private void chargeWithdraw2DepRepayCard(BaseAccount account){
        log.info("[chargeWithdraw2DepRepayCard]入参：" + account.toString());
        final Double amount = account.getAmount();
        if(amount == null){
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }
        /*transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            public void doInTransactionWithoutResult(TransactionStatus status) {*/
                // S:THD_REPAY  - 还款金额
                BsSysSubAccount patRepayAct = bsSysSubAccountService.findSysSubAccount4Lock(Constants.SYS_ACCOUNT_THD_REPAY);
                BsSysSubAccount tempPatRepayAct = new BsSysSubAccount();
                tempPatRepayAct.setId(patRepayAct.getId());
                tempPatRepayAct.setBalance(MoneyUtil.subtract(patRepayAct.getBalance(), amount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                tempPatRepayAct.setAvailableBalance(MoneyUtil.subtract(patRepayAct.getAvailableBalance(), amount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
                tempPatRepayAct.setCanWithdraw(MoneyUtil.subtract(patRepayAct.getCanWithdraw(), amount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
                bsSysSubAccountMapper.updateByPrimaryKeySelective(tempPatRepayAct);

                //系统账户记账
                BsSysAccountJnl sysAccountJnl = new BsSysAccountJnl();
                sysAccountJnl.setTransTime(new Date());
                sysAccountJnl.setTransCode(Constants.SYS_WITHDRAW_2_DEP_REPAY_CARD);
                sysAccountJnl.setTransName("提现到还款专户");
                sysAccountJnl.setTransAmount(amount);
                sysAccountJnl.setSysTime(new Date());
                sysAccountJnl.setChannelTime(null);
                sysAccountJnl.setChannelJnlNo(null);
                sysAccountJnl.setCdFlag1(Constants.CD_FLAG_C);
                sysAccountJnl.setSubAccountCode1(patRepayAct.getCode());
                sysAccountJnl.setBeforeBalance1(patRepayAct.getBalance());
                sysAccountJnl.setAfterBalance1(tempPatRepayAct.getBalance());
                sysAccountJnl.setBeforeAvialableBalance1(patRepayAct.getAvailableBalance());
                sysAccountJnl.setAfterAvialableBalance1(tempPatRepayAct.getAvailableBalance());
                sysAccountJnl.setBeforeFreezeBalance1(patRepayAct.getFreezeBalance());
                sysAccountJnl.setAfterFreezeBalance1(patRepayAct.getFreezeBalance());
                bsSysAccountJnlMapper.insertSelective(sysAccountJnl);
           /* }
        });*/
    }

    /**
     * 代扣还款到代偿人记账
     * F:DEP_JSH + (加到冻结余额)
     * S:BGW_USER + (加到冻结余额)
     * @param account
     */
    private void chargeCutRepay2Comp(BaseAccount account){
        log.info("[chargeCutRepay2Comp]入参：" + account.toString());
        final Double amount = account.getAmount();
        final Integer bsUserId = account.getInvestorUserId();
        if(amount == null || bsUserId == null){
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        } 
        //DEP_JSH + (加到冻结余额)
        BsSubAccount bsSubAccount = bsSubAccountMapper.selectSingleSubActByUserAndType(bsUserId, Constants.PRODUCT_TYPE_DEP_JSH);
        BsSubAccount bsSubAccountJSH = bsSubAccountMapper.selectSubAccountByIdForLock(bsSubAccount.getId());
        
        BsSubAccount bsSubAccountTemp = new BsSubAccount();
        bsSubAccountTemp.setId(bsSubAccountJSH.getId());
        bsSubAccountTemp.setBalance(MoneyUtil.add(bsSubAccountJSH.getBalance(), amount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        bsSubAccountTemp.setFreezeBalance(MoneyUtil.add(bsSubAccountJSH.getFreezeBalance(), amount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        bsSubAccountMapper.updateByPrimaryKeySelective(bsSubAccountTemp);
        
        //借款申请授权金额 用户账 记账
		BsAccountJnl accountJnl = new BsAccountJnl();
		accountJnl.setTransTime(new Date());
		accountJnl.setTransCode(Constants.USER_JSH_FREEZE_BALANCE_ADD);
		accountJnl.setTransName("代偿人结算户金额冻结增加");
		accountJnl.setTransAmount(amount);
		accountJnl.setSysTime(new Date());
		accountJnl.setChannelTime(null);
		accountJnl.setChannelJnlNo(null);
		accountJnl.setCdFlag1(Constants.CD_FLAG_D);
		accountJnl.setUserId1(null);
		accountJnl.setAccountId1(bsSubAccountJSH.getAccountId());
		accountJnl.setSubAccountId1(bsSubAccountJSH.getId());
		accountJnl.setSubAccountCode1(bsSubAccountJSH.getCode());
		accountJnl.setBeforeBalance1(bsSubAccountJSH.getBalance());
		accountJnl.setAfterBalance1(bsSubAccountTemp.getBalance());
		accountJnl.setBeforeAvialableBalance1(bsSubAccountJSH.getAvailableBalance());
		accountJnl.setAfterAvialableBalance1(bsSubAccountJSH.getAvailableBalance());
		accountJnl.setBeforeFreezeBalance1(bsSubAccountJSH.getFreezeBalance());
		accountJnl.setAfterFreezeBalance1(bsSubAccountTemp.getFreezeBalance());
		accountJnl.setFee(0.0);
		bsAccountJnlMapper.insertSelective(accountJnl);
		
		// S:BGW_USER + (加到冻结余额)
        BsSysSubAccount bgwUserAct = bsSysSubAccountService.findSysSubAccount4Lock(Constants.SYS_ACCOUNT_BGW_USER);
        BsSysSubAccount tempBgwUserAct = new BsSysSubAccount();
        tempBgwUserAct.setId(bgwUserAct.getId());
        tempBgwUserAct.setBalance(MoneyUtil.add(bgwUserAct.getBalance(), amount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        tempBgwUserAct.setFreezeBalance(MoneyUtil.add(bgwUserAct.getFreezeBalance(), amount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        bsSysSubAccountMapper.updateByPrimaryKeySelective(tempBgwUserAct);

        //系统账户记账
        BsSysAccountJnl sysAccountJnl = new BsSysAccountJnl();
        sysAccountJnl.setTransTime(new Date());
        sysAccountJnl.setTransCode(Constants.SYS_ACCOUNT_BGW_USER_FREEZE_ADD);
        sysAccountJnl.setTransName("本金代偿冻结金额增加");
        sysAccountJnl.setTransAmount(amount);
        sysAccountJnl.setSysTime(new Date());
        sysAccountJnl.setChannelTime(null);
        sysAccountJnl.setChannelJnlNo(null);
        sysAccountJnl.setCdFlag1(Constants.CD_FLAG_D);
        sysAccountJnl.setSubAccountCode1(bgwUserAct.getCode());
        sysAccountJnl.setBeforeBalance1(bgwUserAct.getBalance());
        sysAccountJnl.setAfterBalance1(tempBgwUserAct.getBalance());
        sysAccountJnl.setBeforeAvialableBalance1(bgwUserAct.getAvailableBalance());
        sysAccountJnl.setAfterAvialableBalance1(bgwUserAct.getAvailableBalance());
        sysAccountJnl.setBeforeFreezeBalance1(bgwUserAct.getFreezeBalance());
        sysAccountJnl.setAfterFreezeBalance1(tempBgwUserAct.getFreezeBalance());
        bsSysAccountJnlMapper.insertSelective(sysAccountJnl);
    }

    /**
     * 代扣还款到借款人记账
     * B:DEP_JSH +
     * @param account
     */
    private void chargeCutRepay2Borrower(BaseAccount account){
        log.info("[chargeCutRepay2Borrower]入参：" + account.toString());
        final Double amount = account.getAmount();
        final Integer lnUserId = account.getBorrowerUserId();
        if(amount == null || lnUserId == null){
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }
        // B:DEP_JSH + 还款金额
        LnSubAccountExample accountExample = new LnSubAccountExample();
        accountExample.createCriteria().andLnUserIdEqualTo(lnUserId).andAccountTypeEqualTo(Constants.LOAN_ACT_TYPE_DEP_JSH);
        List<LnSubAccount> lnSubAccounts = lnSubAccountMapper.selectByExample(accountExample);
        LnSubAccount depJsh = lnSubAccountMapper.selectByPrimaryKey4Lock(lnSubAccounts.get(0).getId());
        LnSubAccount tempDepJsh = new LnSubAccount();
        tempDepJsh.setId(depJsh.getId());
        tempDepJsh.setUpdateTime(new Date());
        tempDepJsh.setBalance(MoneyUtil.add(depJsh.getBalance(), amount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        tempDepJsh.setFreezeBalance(MoneyUtil.add(depJsh.getFreezeBalance(), amount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        lnSubAccountMapper.updateByPrimaryKeySelective(tempDepJsh);
        LnAccountJnl accountJnl = new LnAccountJnl();
        accountJnl.setTransTime(new Date());
        accountJnl.setTransCode(Constants.LN_USER_CUT_REPAY_2_BORROWER);
        accountJnl.setTransName("代扣还款到借款人");
        accountJnl.setTransAmount(amount);
        accountJnl.setSysTime(new Date());
        accountJnl.setCdFlag1(Constants.CD_FLAG_D);
        accountJnl.setUserId1(lnUserId);
        accountJnl.setSubAccountId1(null);
        accountJnl.setBeforeBalance1(depJsh.getBalance());
        accountJnl.setAfterBalance1(tempDepJsh.getBalance());
        accountJnl.setBeforeAvialableBalance1(depJsh.getAvailableBalance());
        accountJnl.setAfterAvialableBalance1(depJsh.getAvailableBalance());
        accountJnl.setBeforeFreezeBalance1(depJsh.getFreezeBalance());
        accountJnl.setAfterFreezeBalance1(tempDepJsh.getFreezeBalance());
        accountJnl.setFee(0d);
        lnAccountJnlMapper.insertSelective(accountJnl);
    }

    /**
     * 代偿人还款到标的记账
     * F:DEP_JSH - 
     * S:BGW_USER -
     * S:DEP_BGW_REVENUE +  借款服务费>0时
     * @param account
     * @param repayInfo
     */
    private void chargeCompRepay2DepTarget(BaseAccount account, Repay2DepTargetInfo repayInfo){
    	
    	log.info("[chargeCompRepay2DepTarget]入参：" + account.toString());
        final Double amount = account.getAmount();
        final Integer bsUserId = account.getInvestorUserId();
        final Double loanServiceFee = repayInfo.getLoanServiceFee();
		final Integer depPlanId = repayInfo.getDepPlanId();
		final PartnerEnum partner = repayInfo.getPartner();
        if(amount == null || bsUserId == null || partner == null){
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        } 
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            public void doInTransactionWithoutResult(TransactionStatus status) {
            	//还款到标的，系统对合作方营收记账
            	if(loanServiceFee != null && loanServiceFee > 0){
                    PartnerSysActCode partnerActCode = BaseAccount.partnerActCodeMap.get(partner);
            		BsSysSubAccount patBgwDepRevenueAct = bsSysSubAccountService.findSysSubAccount4Lock(partnerActCode.getBgwDepRevenueActCode());
                    BsSysSubAccount tempPatBgwDepRevenueAct = new BsSysSubAccount();
                    tempPatBgwDepRevenueAct.setId(patBgwDepRevenueAct.getId());
                    tempPatBgwDepRevenueAct.setBalance(MoneyUtil.add(patBgwDepRevenueAct.getBalance(), loanServiceFee).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    tempPatBgwDepRevenueAct.setAvailableBalance(MoneyUtil.add(patBgwDepRevenueAct.getAvailableBalance(), loanServiceFee).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
                    tempPatBgwDepRevenueAct.setCanWithdraw(MoneyUtil.add(patBgwDepRevenueAct.getCanWithdraw(), loanServiceFee).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
                    bsSysSubAccountMapper.updateByPrimaryKeySelective(tempPatBgwDepRevenueAct);
                    //还款到标的营收
                    BsSysAccountJnl patBgwDepRevenueActJnl = new BsSysAccountJnl();
                    patBgwDepRevenueActJnl.setTransTime(new Date());
                    patBgwDepRevenueActJnl.setTransCode(Constants.SYS_REPAY_2_TARGET_REVENUE);
                    patBgwDepRevenueActJnl.setTransName("还款到标的营收");
                    patBgwDepRevenueActJnl.setTransAmount(loanServiceFee);
                    patBgwDepRevenueActJnl.setSysTime(new Date());
                    patBgwDepRevenueActJnl.setChannelTime(null);
                    patBgwDepRevenueActJnl.setChannelJnlNo(null);
                    patBgwDepRevenueActJnl.setCdFlag1(Constants.CD_FLAG_D);
                    patBgwDepRevenueActJnl.setSubAccountCode1(patBgwDepRevenueAct.getCode());
                    patBgwDepRevenueActJnl.setBeforeBalance1(patBgwDepRevenueAct.getBalance());
                    patBgwDepRevenueActJnl.setAfterBalance1(tempPatBgwDepRevenueAct.getBalance());
                    patBgwDepRevenueActJnl.setBeforeAvialableBalance1(patBgwDepRevenueAct.getAvailableBalance());
                    patBgwDepRevenueActJnl.setAfterAvialableBalance1(tempPatBgwDepRevenueAct.getAvailableBalance());
                    patBgwDepRevenueActJnl.setBeforeFreezeBalance1(patBgwDepRevenueAct.getFreezeBalance());
                    patBgwDepRevenueActJnl.setAfterFreezeBalance1(patBgwDepRevenueAct.getFreezeBalance());
                    
                    patBgwDepRevenueActJnl.setOpId(depPlanId);
                    bsSysAccountJnlMapper.insertSelective(patBgwDepRevenueActJnl);
                }
		        //DEP_JSH - (冻结余额)
		        BsSubAccount bsSubAccount = bsSubAccountMapper.selectSingleSubActByUserAndType(bsUserId, Constants.PRODUCT_TYPE_DEP_JSH);
		        BsSubAccount bsSubAccountJSH = bsSubAccountMapper.selectSubAccountByIdForLock(bsSubAccount.getId());
		        
		        BsSubAccount bsSubAccountTemp = new BsSubAccount();
		        bsSubAccountTemp.setId(bsSubAccountJSH.getId());
		        bsSubAccountTemp.setBalance(MoneyUtil.subtract(bsSubAccountJSH.getBalance(), amount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
		        bsSubAccountTemp.setFreezeBalance(MoneyUtil.subtract(bsSubAccountJSH.getFreezeBalance(), amount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
		        bsSubAccountMapper.updateByPrimaryKeySelective(bsSubAccountTemp);
		        
		        //借款申请授权金额 用户账 记账
				BsAccountJnl accountJnl = new BsAccountJnl();
				accountJnl.setTransTime(new Date());
				accountJnl.setTransCode(Constants.USER_JSH_FREEZE_BALANCE_SUB);
				accountJnl.setTransName("代偿人结算户金额冻结减少");
				accountJnl.setTransAmount(amount);
				accountJnl.setSysTime(new Date());
				accountJnl.setChannelTime(null);
				accountJnl.setChannelJnlNo(null);
				accountJnl.setCdFlag1(Constants.CD_FLAG_C);
				accountJnl.setUserId1(bsUserId);
				accountJnl.setAccountId1(bsSubAccountJSH.getAccountId());
				accountJnl.setSubAccountId1(bsSubAccountJSH.getId());
				accountJnl.setSubAccountCode1(bsSubAccountJSH.getCode());
				accountJnl.setBeforeBalance1(bsSubAccountJSH.getBalance());
				accountJnl.setAfterBalance1(bsSubAccountTemp.getBalance());
				accountJnl.setBeforeAvialableBalance1(bsSubAccountJSH.getAvailableBalance());
				accountJnl.setAfterAvialableBalance1(bsSubAccountJSH.getAvailableBalance());
				accountJnl.setBeforeFreezeBalance1(bsSubAccountJSH.getFreezeBalance());
				accountJnl.setAfterFreezeBalance1(bsSubAccountTemp.getFreezeBalance());
				accountJnl.setFee(0.0);
				bsAccountJnlMapper.insertSelective(accountJnl);
				
				// S:BGW_USER - (冻结余额)
		        BsSysSubAccount bgwUserAct = bsSysSubAccountService.findSysSubAccount4Lock(Constants.SYS_ACCOUNT_BGW_USER);
		        BsSysSubAccount tempBgwUserAct = new BsSysSubAccount();
		        tempBgwUserAct.setId(bgwUserAct.getId());
		        tempBgwUserAct.setBalance(MoneyUtil.subtract(bgwUserAct.getBalance(), amount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
		        tempBgwUserAct.setFreezeBalance(MoneyUtil.subtract(bgwUserAct.getFreezeBalance(), amount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
		        bsSysSubAccountMapper.updateByPrimaryKeySelective(tempBgwUserAct);
		
		        //系统账户记账
		        BsSysAccountJnl sysAccountJnl = new BsSysAccountJnl();
		        sysAccountJnl.setTransTime(new Date());
		        sysAccountJnl.setTransCode(Constants.SYS_ACCOUNT_BGW_USER_FREEZE_SUB);
		        sysAccountJnl.setTransName("本金代偿冻结金额减少");
		        sysAccountJnl.setTransAmount(amount);
		        sysAccountJnl.setSysTime(new Date());
		        sysAccountJnl.setChannelTime(null);
		        sysAccountJnl.setChannelJnlNo(null);
		        sysAccountJnl.setCdFlag1(Constants.CD_FLAG_C);
		        sysAccountJnl.setSubAccountCode1(bgwUserAct.getCode());
		        sysAccountJnl.setBeforeBalance1(bgwUserAct.getBalance());
		        sysAccountJnl.setAfterBalance1(tempBgwUserAct.getBalance());
		        sysAccountJnl.setBeforeAvialableBalance1(bgwUserAct.getAvailableBalance());
		        sysAccountJnl.setAfterAvialableBalance1(bgwUserAct.getAvailableBalance());
		        sysAccountJnl.setBeforeFreezeBalance1(bgwUserAct.getFreezeBalance());
		        sysAccountJnl.setAfterFreezeBalance1(tempBgwUserAct.getFreezeBalance());
		        bsSysAccountJnlMapper.insertSelective(sysAccountJnl);
            }
        });
    }

    /**
     * 借款人还款到标的记账
     * B:DEP_JSH -
     * S:DEP_BGW_REVENUE +  借款服务费>0时
     * @param account
     */
    private void chargeRepay2DepTarget(BaseAccount account,Repay2DepTargetInfo repayInfo){
        log.info("[chargeRepay2DepTarget]入参：" + account.toString());
        final Double amount = account.getAmount();
        final Integer lnUserId = account.getBorrowerUserId();
        final Double loanServiceFee = repayInfo.getLoanServiceFee();
		final Integer depPlanId = repayInfo.getDepPlanId();
        if(amount == null || lnUserId == null){
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }
        
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            public void doInTransactionWithoutResult(TransactionStatus status) {
            	
            	//还款到标的，系统对合作方营收记账
            	if(loanServiceFee != null && loanServiceFee > 0){
                	//查询借款人信息
                    LnUser lnUser = lnUserMapper.selectByPrimaryKey(lnUserId);
                    if(lnUser == null){
                        throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR,"借款人信息不存在");
                    }
                    PartnerEnum partner = PartnerEnum.getEnumByCode(lnUser.getPartnerCode());
                    if(partner == null){
                    	 throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR,"合作方不存在");
                    }
                    PartnerSysActCode partnerActCode = BaseAccount.partnerActCodeMap.get(partner);
            		BsSysSubAccount patBgwDepRevenueAct = bsSysSubAccountService.findSysSubAccount4Lock(partnerActCode.getBgwDepRevenueActCode());
                    BsSysSubAccount tempPatBgwDepRevenueAct = new BsSysSubAccount();
                    tempPatBgwDepRevenueAct.setId(patBgwDepRevenueAct.getId());
                    tempPatBgwDepRevenueAct.setBalance(MoneyUtil.add(patBgwDepRevenueAct.getBalance(), loanServiceFee).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    tempPatBgwDepRevenueAct.setAvailableBalance(MoneyUtil.add(patBgwDepRevenueAct.getAvailableBalance(), loanServiceFee).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
                    tempPatBgwDepRevenueAct.setCanWithdraw(MoneyUtil.add(patBgwDepRevenueAct.getCanWithdraw(), loanServiceFee).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
                    bsSysSubAccountMapper.updateByPrimaryKeySelective(tempPatBgwDepRevenueAct);
                    //还款到标的营收
                    BsSysAccountJnl patBgwDepRevenueActJnl = new BsSysAccountJnl();
                    patBgwDepRevenueActJnl.setTransTime(new Date());
                    patBgwDepRevenueActJnl.setTransCode(Constants.SYS_REPAY_2_TARGET_REVENUE);
                    patBgwDepRevenueActJnl.setTransName("还款到标的营收");
                    patBgwDepRevenueActJnl.setTransAmount(loanServiceFee);
                    patBgwDepRevenueActJnl.setSysTime(new Date());
                    patBgwDepRevenueActJnl.setChannelTime(null);
                    patBgwDepRevenueActJnl.setChannelJnlNo(null);
                    patBgwDepRevenueActJnl.setCdFlag1(Constants.CD_FLAG_D);
                    patBgwDepRevenueActJnl.setSubAccountCode1(patBgwDepRevenueAct.getCode());
                    patBgwDepRevenueActJnl.setBeforeBalance1(patBgwDepRevenueAct.getBalance());
                    patBgwDepRevenueActJnl.setAfterBalance1(tempPatBgwDepRevenueAct.getBalance());
                    patBgwDepRevenueActJnl.setBeforeAvialableBalance1(patBgwDepRevenueAct.getAvailableBalance());
                    patBgwDepRevenueActJnl.setAfterAvialableBalance1(tempPatBgwDepRevenueAct.getAvailableBalance());
                    patBgwDepRevenueActJnl.setBeforeFreezeBalance1(patBgwDepRevenueAct.getFreezeBalance());
                    patBgwDepRevenueActJnl.setAfterFreezeBalance1(patBgwDepRevenueAct.getFreezeBalance());
                    
                    patBgwDepRevenueActJnl.setOpId(depPlanId);
                    bsSysAccountJnlMapper.insertSelective(patBgwDepRevenueActJnl);
                }
                // B:DEP_JSH - 还款金额
                LnSubAccountExample accountExample = new LnSubAccountExample();
                accountExample.createCriteria().andLnUserIdEqualTo(lnUserId).andAccountTypeEqualTo(Constants.LOAN_ACT_TYPE_DEP_JSH);
                List<LnSubAccount> lnSubAccounts = lnSubAccountMapper.selectByExample(accountExample);
                LnSubAccount depJsh = lnSubAccountMapper.selectByPrimaryKey4Lock(lnSubAccounts.get(0).getId());
                LnSubAccount tempDepJsh = new LnSubAccount();
                tempDepJsh.setId(depJsh.getId());
                tempDepJsh.setUpdateTime(new Date());
                tempDepJsh.setBalance(MoneyUtil.subtract(depJsh.getBalance(), amount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                tempDepJsh.setFreezeBalance(MoneyUtil.subtract(depJsh.getFreezeBalance(), amount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                lnSubAccountMapper.updateByPrimaryKeySelective(tempDepJsh);
                LnAccountJnl accountJnl = new LnAccountJnl();
                accountJnl.setTransTime(new Date());
                accountJnl.setTransCode(Constants.LN_USER_REPAY_2_DEP_TARGET);
                accountJnl.setTransName("借款人还款到标的");
                accountJnl.setTransAmount(amount);
                accountJnl.setSysTime(new Date());
                accountJnl.setCdFlag1(Constants.CD_FLAG_C);
                accountJnl.setUserId1(lnUserId);
                accountJnl.setSubAccountId1(null);
                accountJnl.setBeforeBalance1(depJsh.getBalance());
                accountJnl.setAfterBalance1(tempDepJsh.getBalance());
                accountJnl.setBeforeAvialableBalance1(depJsh.getAvailableBalance());
                accountJnl.setAfterAvialableBalance1(depJsh.getAvailableBalance());
                accountJnl.setBeforeFreezeBalance1(depJsh.getFreezeBalance());
                accountJnl.setAfterFreezeBalance1(tempDepJsh.getFreezeBalance());
                accountJnl.setFee(0d);
                lnAccountJnlMapper.insertSelective(accountJnl);
                
            }
        });
    }

	@Override
	public void repay2InvestorSucc(Repay2InvestorReq req,List<RepayInfo> yunAccountList) {
		 final DepRepaySchedule depRepaySchedule = req.getDepRepaySchedule();
	        //获取此标的下投资人及应还款金额列表
	        List<LnFinanceRepayScheduleVO> fnRepaySchedules =  lnFinanceRepayScheduleMapper
	                .selectFnRepayScheduleBySerial(depRepaySchedule.getLoanId(), depRepaySchedule.getSerialId());
	        if(CollectionUtils.isEmpty(fnRepaySchedules)){
	            log.error("标的还款账单编号["+depRepaySchedule.getId()+"]找不到对应借贷关系列表");
	            return;
	        }
	        if(PartnerEnum.YUN_DAI_SELF.equals(depRepaySchedule.getPartnerEnum()) || 
            		PartnerEnum.ZSD.equals(depRepaySchedule.getPartnerEnum()) ||
            		PartnerEnum.SEVEN_DAI_SELF.equals(depRepaySchedule.getPartnerEnum())){
                for (RepayInfo repayInfo : yunAccountList) {
                	//查询子账户信息，判断是否为REG户，是则不需要数据准备
                	BsSubAccount subAccount = bsSubAccountMapper.selectByPrimaryKey(repayInfo.getAuthActId());
                	if(!Constants.PRODUCT_TYPE_RED.equals(subAccount.getProductType())){
                		//云贷记账/赞时贷记账
                        chargeRepay2Investor4Yun(repayInfo);
                	}
				}
            }
	        //return_flag 整个订单标的还款成功
	        LnDepositionRepaySchedule scheduleTemp = new LnDepositionRepaySchedule();
	        scheduleTemp.setId(depRepaySchedule.getId());
	        scheduleTemp.setReturnFlag(LoanStatus.DEP_REPAY_SCHEDULE_RETURN_FLAG_RETURN_SUCC.getCode());
	        scheduleTemp.setStatus(LoanStatus.REPAY_SCHEDULE_STATUS_REPAIED.getCode());
	        scheduleTemp.setFinishTime(new Date());
	        //记账
	        for (final LnFinanceRepayScheduleVO repaySchedule : fnRepaySchedules){
	            //final ProdRepayReqFundDataCustRepay repayCust = repayCustMap.get(repaySchedule.getId());
	            if(PartnerEnum.ZAN.equals(depRepaySchedule.getPartnerEnum())){
	                //赞分期回款表增加已回款记录
	                BsLoanFinanceRepayExample financeRepayExample = new BsLoanFinanceRepayExample();
	                financeRepayExample.createCriteria().andFinanceRepayScheduleIdEqualTo(repaySchedule.getId());
	                List<BsLoanFinanceRepay> financeRepays = bsLoanFinanceRepayMapper.selectByExample(financeRepayExample);
	                BsLoanFinanceRepay finalLoanFinanceRepay;
	                if(CollectionUtils.isNotEmpty(financeRepays)){
	                    BsLoanFinanceRepay financeRepayPlan = new BsLoanFinanceRepay();
	                    financeRepayPlan.setId(financeRepays.get(0).getId());
	                    financeRepayPlan.setStatus(Constants.FINANCE_REPAY_SATAE_REPAIED);
	                    financeRepayPlan.setUpdateTime(new Date());
	                    bsLoanFinanceRepayMapper.updateByPrimaryKeySelective(financeRepayPlan);
	                    finalLoanFinanceRepay = bsLoanFinanceRepayMapper.selectByPrimaryKey(financeRepayPlan.getId());
	                }else {
	                    BsLoanFinanceRepay financeRepayPlan = new BsLoanFinanceRepay();
	                    financeRepayPlan.setCreateTime(new Date());
	                    financeRepayPlan.setFnUserId(repaySchedule.getUserId());
	                    financeRepayPlan.setFinanceRepayScheduleId(repaySchedule.getId());
	                    financeRepayPlan.setInterest(repaySchedule.getPlanInterest());
	                    financeRepayPlan.setPrincipal(repaySchedule.getPlanPrincipal());
	                    financeRepayPlan.setOrderNo(null);
	                    financeRepayPlan.setStatus(Constants.FINANCE_REPAY_SATAE_REPAIED);
	                    financeRepayPlan.setTotal(MoneyUtil.add(repaySchedule.getPlanPrincipal(), repaySchedule.getPlanInterest()).doubleValue());
	                		/*MoneyUtil.defaultRound(repaySchedule.getPlanPrincipal() + repaySchedule.getPlanInterest()).doubleValue());*/
	                    financeRepayPlan.setUpdateTime(new Date());
	                    financeRepayPlan.setPlanDate(repaySchedule.getPlanDate());
	                    bsLoanFinanceRepayMapper.insertSelective(financeRepayPlan);
	                    finalLoanFinanceRepay = financeRepayPlan;
	                }
	                final Date now = new Date();
	                final Integer fnUserId = repaySchedule.getUserId();
	                final Double amount = finalLoanFinanceRepay.getTotal();
	                final Double productPrincipal = finalLoanFinanceRepay.getPrincipal();
	                final Double productInterest = finalLoanFinanceRepay.getInterest();
	                final BsUser fnUser = userService.findUserById(fnUserId);
	                //查询回款计划及借贷关系
	                final LnFinanceRepaySchedule financeRepaySchedule = lnFinanceRepayScheduleMapper.selectByPrimaryKey(finalLoanFinanceRepay.getFinanceRepayScheduleId());
	                final LnLoanRelation loanRelation = lnLoanRelationMapper.selectByPrimaryKey(financeRepaySchedule.getRelationId());
	                final Integer subAccountId = loanRelation.getBsSubAccountId();
	                final BsSubAccountVO productAccount = subAccountService.findMyInvestByUserIdAndId(fnUserId, subAccountId);
	                transactionTemplate.execute(new TransactionCallback<Boolean>() {
	                    @Override
	                    public Boolean doInTransaction(TransactionStatus status) {
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

	                        //赞分期记账
	                        ReceiveMoneyInfo receiveMoneyInfo = new ReceiveMoneyInfo();
	                        receiveMoneyInfo.setAmount(MoneyUtil.add(repaySchedule.getPlanPrincipal(), repaySchedule.getPlanInterest()).doubleValue());
	                        receiveMoneyInfo.setPrincipal(repaySchedule.getPlanPrincipal());
	                        receiveMoneyInfo.setInterest(repaySchedule.getPlanInterest());
	                        receiveMoneyInfo.setPartner(depRepaySchedule.getPartnerEnum());
	                        receiveMoneyInfo.setInvestorRegActId(repaySchedule.getBsSubAccountId());
	                        receiveMoneyInfo.setInvestorUserId(repaySchedule.getUserId());
	                        receiveMoneyInfo.setServiceFee(repaySchedule.getPlanFee());
	                        receiveMoneyInfo.setLnFinancePlanId(repaySchedule.getId());
	                        repayAccountService.chargeReceiveMoney2Balance(receiveMoneyInfo);
	                        return null;
	                    }
	                });
	            }
	            LnFinanceRepaySchedule fnScheduleTemp = new LnFinanceRepaySchedule();
	            fnScheduleTemp.setId(repaySchedule.getId());
	            fnScheduleTemp.setStatus(Constants.FINANCE_REPAY_SATAE_REPAIED);
	            fnScheduleTemp.setDoneTime(new Date());
	            fnScheduleTemp.setUpdateTime(new Date());
	            lnFinanceRepayScheduleMapper.updateByPrimaryKeySelective(fnScheduleTemp);

	            //计划还款本金大于0，修改借贷关系表，金额变动表
	            if(repaySchedule.getPlanPrincipal() > 0){
	                LnLoanRelation relation = lnLoanRelationMapper.selectByPrimaryKey(repaySchedule.getRelationId());
	                LnLoanRelation relationTemp = new LnLoanRelation();
	                relationTemp.setId(relation.getId());
	                relationTemp.setLeftAmount(MoneyUtil.subtract(relation.getLeftAmount(),repaySchedule.getPlanPrincipal()).doubleValue());
	                if(repaySchedule.getPlanPrincipal().compareTo(relation.getLeftAmount()) == 0){
	                    //计划还款本金和借贷关系表剩余本金相等
	                    if(!Constants.LOAN_RELATION_STATUS_TRANSFERRED.equals(relation.getStatus())){
	                        //已经是转让，状态不修改；否则修改为已还完
	                        relationTemp.setStatus(Constants.LOAN_RELATION_STATUS_REPAID);
	                    }

	                }
	                relationTemp.setUpdateTime(new Date());
	                lnLoanRelationMapper.updateByPrimaryKeySelective(relationTemp);

	                LnLoanAmountChange change = new LnLoanAmountChange();
	                change.setRelationId(relation.getId());
	                change.setBeforeAmount(relation.getLeftAmount());
	                change.setAfterAmount(relationTemp.getLeftAmount());
	                change.setChangeAmount(repaySchedule.getPlanPrincipal());
	                change.setCreateTime(new Date());
	                change.setUpdateTime(new Date());

	                lnLoanAmountChangeMapper.insertSelective(change);
	            }
	        }
	        scheduleTemp.setUpdateTime(new Date());
	        scheduleTemp.setNote(depRepaySchedule.getNote()!=null ? depRepaySchedule.getNote():""+"管理台操作成功");
	        lnDepositionRepayScheduleMapper.updateByPrimaryKeySelective(scheduleTemp);
		
	}

}

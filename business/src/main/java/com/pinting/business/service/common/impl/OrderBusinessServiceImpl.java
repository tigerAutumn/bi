package com.pinting.business.service.common.impl;

import com.pinting.business.accounting.finance.model.EBankResultInfo;
import com.pinting.business.accounting.finance.model.QuickPayResultInfo;
import com.pinting.business.accounting.finance.model.SysActTransResultInfo;
import com.pinting.business.accounting.finance.service.*;
import com.pinting.business.accounting.loan.enums.DepTargetEnum;
import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.loan.model.*;
import com.pinting.business.accounting.loan.service.*;
import com.pinting.business.dao.LnPayOrdersMapper;
import com.pinting.business.dao.LnRepeatRepayRecordMapper;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.enums.RedisLockEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.LnPayOrders;
import com.pinting.business.model.LnPayOrdersExample;
import com.pinting.business.model.LnRepeatRepayRecord;
import com.pinting.business.model.LnRepeatRepayRecordExample;
import com.pinting.business.model.dto.OrderResultInfo;
import com.pinting.business.model.dto.TransPartnerRevenueInfo;
import com.pinting.business.service.common.OrderBusinessService;
import com.pinting.business.util.Constants;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.DateUtil;
import com.pinting.gateway.hessian.message.hfbank.G2BReqMsg_HFBank_OutOfAccount;
import com.pinting.gateway.hessian.message.pay19.enums.AcctTransTradeResult;
import com.pinting.gateway.hessian.message.pay19.enums.OrderStatus;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by babyshark on 2016/9/11.
 */
@Service
public class OrderBusinessServiceImpl implements OrderBusinessService {
    private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);
    @Autowired
    private UserTopUpService userTopUpService;
    @Autowired
    private UserBalanceWithdrawService userBalanceWithdrawService;
    @Autowired
    private UserReturnMoneyService userReturnMoneyService;
    @Autowired
    private FinanceReceiveMoneyService financeReceiveMoneyService;
    @Autowired
    private SysProductBuyService sysProductBuyService;
    @Autowired
    private LoanPaymentService loanPaymentService;
    @Autowired
    private RepayPaymentService repayPaymentService;
    @Autowired
    private SysDailyBizService sysDailyBizService;
    @Autowired
    private PartnerTransService partnerTransService;
    @Autowired
    private DepOfflineRepayService depOfflineRepayService;
    @Autowired
    private DepUserTopUpService depUserTopUpService;
    @Autowired
    private DepFixedRepayPaymentService depFixedRepayPaymentService;
    @Autowired
    private DepUserBalanceWithdrawService depUserBalanceWithdrawService;
    @Autowired
    private DepFixedLoanPaymentService depFixedLoanPaymentService;
    @Autowired
    private LnPayOrdersMapper lnPayOrdersMapper;
    @Autowired
    private DepFixedRevenueSettleService depFixedRevenueSettleService;
    @Autowired
    private LnRepeatRepayRecordMapper lnRepeatRepayRecordMapper;

    /**
     * 理财人快捷充值通知
     *
     * @param orderResultInfo
     */
    @Override
    public void financerQuickPayTopUp(OrderResultInfo orderResultInfo) {
        QuickPayResultInfo resultInfo = new QuickPayResultInfo();
        resultInfo.setOrderId(orderResultInfo.getOrderNo());
        resultInfo.setAmount(orderResultInfo.getAmount());
        resultInfo.setMpOrderId(orderResultInfo.getPayOrderNo());
        resultInfo.setErrorMsg(orderResultInfo.getReturnMsg());
        resultInfo.setErrorCode(orderResultInfo.getReturnCode());
        resultInfo.setOrderFinTime(orderResultInfo.getFinishTime());
        resultInfo.setStatus(orderResultInfo.isSuccess() ? OrderStatus.SUCCESS.getCode() : OrderStatus.FAIL.getCode());
        userTopUpService.notify(resultInfo);
    }

    /**
     * 理财人网银充值通知
     *
     * @param orderResultInfo
     */
    @Override
    public void financerEBankTopUp(OrderResultInfo orderResultInfo) {
        EBankResultInfo resultInfo = new EBankResultInfo();
        resultInfo.setMxOrderId(orderResultInfo.getOrderNo());
        resultInfo.setAmount(orderResultInfo.getAmount());
        resultInfo.setMpOrderId(orderResultInfo.getPayOrderNo());
        resultInfo.setPayDate(orderResultInfo.getFinishTime());
        resultInfo.setResult(orderResultInfo.isSuccess() ? OrderStatus.SUCCESS.getCode() : OrderStatus.FAIL.getCode());
        resultInfo.setErrorMsg(orderResultInfo.getReturnMsg());
        resultInfo.setRetCode(orderResultInfo.getReturnCode());
        userTopUpService.notifyEBank(resultInfo);
    }

    /**
     * 理财人提现通知
     *
     * @param orderResultInfo
     */
    @Override
    public void financerWithdraw(OrderResultInfo orderResultInfo) {
        try {
            jsClientDaoSupport.lock(RedisLockEnum.LOCK_WITHDRAW_SERVICE.getKey());
            com.pinting.business.accounting.finance.model.DFResultInfo resultInfo = new com.pinting.business.accounting.finance.model.DFResultInfo();
            resultInfo.setAmount(orderResultInfo.getAmount());
            resultInfo.setFinishTime(orderResultInfo.getFinishTime());
            resultInfo.setMxOrderId(orderResultInfo.getOrderNo());
            resultInfo.setSysOrderId(orderResultInfo.getPayOrderNo());
            resultInfo.setOrderStatus(orderResultInfo.isSuccess() ? OrderStatus.SUCCESS.getCode() : OrderStatus.FAIL.getCode());
            resultInfo.setRetCode(orderResultInfo.getReturnCode());
            resultInfo.setErrorMsg(orderResultInfo.getReturnMsg());
            userBalanceWithdrawService.notify(resultInfo);
        }finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_WITHDRAW_SERVICE.getKey());
        }
    }

    /**
     * 理财人普通产品回款通知
     *
     * @param orderResultInfo
     */
    @Override
    public void financerRegProductReturn(OrderResultInfo orderResultInfo) {
        try {
            jsClientDaoSupport.lock(RedisLockEnum.LOCK_USER_RECEIVEMONEY.getKey());
            com.pinting.business.accounting.finance.model.DFResultInfo resultInfo = new com.pinting.business.accounting.finance.model.DFResultInfo();
            resultInfo.setAmount(orderResultInfo.getAmount());
            resultInfo.setFinishTime(orderResultInfo.getFinishTime());
            resultInfo.setMxOrderId(orderResultInfo.getOrderNo());
            resultInfo.setOrderStatus(orderResultInfo.isSuccess() ? OrderStatus.SUCCESS.getCode() : OrderStatus.FAIL.getCode());
            resultInfo.setRetCode(orderResultInfo.getReturnCode());
            resultInfo.setErrorMsg(orderResultInfo.getReturnMsg());
            userReturnMoneyService.notifyReturn2CardResult(resultInfo);
        }finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_USER_RECEIVEMONEY.getKey());
        }
    }

    /**
     * 理财人分期产品回款通知
     *
     * @param orderResultInfo
     */
    @Override
    public void financerRegDProductReturn(OrderResultInfo orderResultInfo) {
        try {
            jsClientDaoSupport.lock(RedisLockEnum.LOCK_USER_RECEIVEMONEY.getKey());
            ReceiveMoneyNoticeInfo returnInfo = new ReceiveMoneyNoticeInfo();
            returnInfo.setAmount(orderResultInfo.getAmount());
            returnInfo.setFinishTime(orderResultInfo.getFinishTime());
            returnInfo.setMxOrderId(orderResultInfo.getOrderNo());
            returnInfo.setOrderStatus(orderResultInfo.isSuccess() ? OrderStatus.SUCCESS : OrderStatus.FAIL);
            returnInfo.setRetCode(orderResultInfo.getReturnCode());
            returnInfo.setErrorMsg(orderResultInfo.getReturnMsg());
            financeReceiveMoneyService.notifyReceiveMoney2CardResult(returnInfo);
        }finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_USER_RECEIVEMONEY.getKey());
        }
    }

    /**
     * 借款人借款通知
     *
     * @param orderResultInfo
     */
    @Override
    public void loanerLoan(OrderResultInfo orderResultInfo) {
        DFResultInfo resultInfo = new DFResultInfo();
        resultInfo.setErrorMsg(orderResultInfo.getReturnMsg());
        resultInfo.setRetCode(orderResultInfo.getReturnCode());
        resultInfo.setAmount(orderResultInfo.getAmount());
        resultInfo.setFinishTime(orderResultInfo.getFinishTime());
        resultInfo.setMxOrderId(orderResultInfo.getOrderNo());
        resultInfo.setOrderStatus(orderResultInfo.isSuccess() ? OrderStatus.SUCCESS.getCode() : OrderStatus.FAIL.getCode());
        resultInfo.setSysOrderId(orderResultInfo.getPayOrderNo());
        loanPaymentService.notifyLoan(resultInfo);

    }

    /**
     * 借款人还款通知
     *
     * @param orderResultInfo
     */
    @Override
    public void loanerRepay(OrderResultInfo orderResultInfo) {
        RepayResultInfo resultInfo = new RepayResultInfo();
        resultInfo.setAmount(orderResultInfo.getAmount());
        resultInfo.setOrderId(orderResultInfo.getOrderNo());
        resultInfo.setErrorMsg(orderResultInfo.getReturnMsg());
        resultInfo.setErrorCode(orderResultInfo.getReturnCode());
        resultInfo.setMpOrderId(orderResultInfo.getPayOrderNo());
        resultInfo.setOrderFinTime(resultInfo.getOrderFinTime());
        resultInfo.setStatus(orderResultInfo.isSuccess() ? OrderStatus.SUCCESS.getCode() : OrderStatus.FAIL.getCode());
        resultInfo.setExtendMap(orderResultInfo.getExtendMap());
        repayPaymentService.notifyRepay(resultInfo);

    }

    /**
     * 系统产品购买转账
     *
     * @param orderResultInfo
     */
    @Override
    public void sysRegTrans(OrderResultInfo orderResultInfo) {
        SysActTransResultInfo resultInfo = new SysActTransResultInfo();
        resultInfo.setErrorMsg(orderResultInfo.getResMsg());
        resultInfo.setFinTime(orderResultInfo.getFinishTime());
        resultInfo.setMpOrderId(orderResultInfo.getPayOrderNo());
        resultInfo.setOrderAmount(orderResultInfo.getAmount());
        resultInfo.setOrderId(orderResultInfo.getOrderNo());
        resultInfo.setRetCode(orderResultInfo.getReturnCode());
        resultInfo.setTradeResult(orderResultInfo.isSuccess() ? AcctTransTradeResult.Y : AcctTransTradeResult.F);
        sysProductBuyService.notifyTrans2PartnerResult(resultInfo);
    }

    /**
     * 系统营收结算转账
     *
     * @param orderResultInfo
     */
    @Override
    public void sysRevenueTrans(OrderResultInfo orderResultInfo) {

        //转账结果处理
        TransPartnerRevenueInfo transPartnerRevenueInfo = new TransPartnerRevenueInfo();
        transPartnerRevenueInfo.setFinishTime(new Date());
        if (orderResultInfo.isSuccess()){
            transPartnerRevenueInfo.setStatus("1");
        }else {
            transPartnerRevenueInfo.setStatus("-1");
        }
        transPartnerRevenueInfo.setAmount(orderResultInfo.getAmount());
        transPartnerRevenueInfo.setOrderNo(orderResultInfo.getOrderNo());
        transPartnerRevenueInfo.setReturnCode(ConstantUtil.BSRESCODE_SUCCESS); //结果处理方法用status判断成功或失败
        transPartnerRevenueInfo.setReturnMsg(orderResultInfo.getResMsg());

        sysDailyBizService.notifyTransDailyPartnerRevenue(transPartnerRevenueInfo);
    }

    /**
     * 合作方营销代付
     *
     * @param orderResultInfo
     */
    @Override
    public void partnerMarketingTrans(OrderResultInfo orderResultInfo) {
        DFResultInfo resultInfo = new DFResultInfo();
        resultInfo.setAmount(orderResultInfo.getAmount());
        resultInfo.setFinishTime(orderResultInfo.getFinishTime()!= null ? orderResultInfo.getFinishTime() : new Date());
        resultInfo.setMxOrderId(orderResultInfo.getOrderNo());
        resultInfo.setOrderStatus(orderResultInfo.isSuccess() ? OrderStatus.SUCCESS.getCode() : OrderStatus.FAIL.getCode());
        resultInfo.setSysOrderId(orderResultInfo.getPayOrderNo());
        resultInfo.setErrorMsg(orderResultInfo.getResMsg());
        resultInfo.setRetCode(orderResultInfo.getResCode());

        partnerTransService.notifyMarketing(resultInfo);
    }

    /**
     * 提现到存管还款专户
     *
     * @param orderResultInfo
     */
    @Override
    public void withdraw2DepRepayCard(OrderResultInfo orderResultInfo) {
        DFResultInfo resultInfo = new DFResultInfo();
        resultInfo.setAmount(orderResultInfo.getAmount());
        resultInfo.setFinishTime(orderResultInfo.getFinishTime()!= null ? orderResultInfo.getFinishTime() : new Date());
        resultInfo.setMxOrderId(orderResultInfo.getOrderNo());
        resultInfo.setOrderStatus(orderResultInfo.isSuccess() ? OrderStatus.SUCCESS.getCode() : OrderStatus.FAIL.getCode());
        resultInfo.setSysOrderId(orderResultInfo.getPayOrderNo());
        resultInfo.setErrorMsg(orderResultInfo.getResMsg());
        resultInfo.setRetCode(orderResultInfo.getResCode());
        depOfflineRepayService.notifyWithdraw2DepRepayCardResult(resultInfo);

    }

    @Override
    public void financeHFTopUp(OrderResultInfo orderResultInfo) {
        QuickPayResultInfo resultInfo = new QuickPayResultInfo();
        resultInfo.setOrderId(orderResultInfo.getOrderNo());
        resultInfo.setAmount(orderResultInfo.getAmount());
        resultInfo.setMpOrderId(orderResultInfo.getPayOrderNo());
        resultInfo.setErrorMsg(orderResultInfo.getReturnMsg());
        resultInfo.setErrorCode(orderResultInfo.getReturnCode());
        resultInfo.setOrderFinTime(orderResultInfo.getFinishTime());
        resultInfo.setStatus(orderResultInfo.isSuccess() ? OrderStatus.SUCCESS.getCode() : OrderStatus.FAIL.getCode());
        depUserTopUpService.hfNotify(resultInfo);
    }

    /**
     * 云贷借款人还款通知
     * @param orderResultInfo
     */
    public void repayResult(OrderResultInfo orderResultInfo){
        DepFixedRepayPayResultInfo payResult = new DepFixedRepayPayResultInfo();
        payResult.setSuccess(orderResultInfo.isSuccess());
        payResult.setAmount(orderResultInfo.getAmount());
        payResult.setOrderNo(orderResultInfo.getOrderNo());
        payResult.setReturnCode(orderResultInfo.getReturnCode());
        payResult.setReturnMsg(orderResultInfo.getReturnMsg());
        //还款成功支付业务处理
        depFixedRepayPaymentService.doRepayResultPayProcess(payResult);
    }

    @Override
    public void financeHFWithdraw(OrderResultInfo orderResultInfo) {
        try {
            jsClientDaoSupport.lock(RedisLockEnum.LOCK_WITHDRAW_SERVICE.getKey());
            com.pinting.business.accounting.finance.model.DFResultInfo resultInfo = new com.pinting.business.accounting.finance.model.DFResultInfo();
            resultInfo.setAmount(orderResultInfo.getAmount());
            resultInfo.setFinishTime(orderResultInfo.getFinishTime());
            resultInfo.setMxOrderId(orderResultInfo.getOrderNo());
            resultInfo.setSysOrderId(orderResultInfo.getPayOrderNo());
            resultInfo.setOrderStatus(orderResultInfo.isSuccess() ? OrderStatus.SUCCESS.getCode() : OrderStatus.FAIL.getCode());
            resultInfo.setRetCode(orderResultInfo.getReturnCode());
            resultInfo.setErrorMsg(orderResultInfo.getReturnMsg());
            resultInfo.setHfUserId(orderResultInfo.getHfUserId());
            resultInfo.setPayPath(orderResultInfo.getPayPath());
            depUserBalanceWithdrawService.notify(resultInfo);
        }finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_WITHDRAW_SERVICE.getKey());
        }
    }

    @Override
    public void borrowerDFWithdraw(OrderResultInfo orderResultInfo) {
        com.pinting.business.accounting.finance.model.DFResultInfo resultInfo = new com.pinting.business.accounting.finance.model.DFResultInfo();
        resultInfo.setAmount(orderResultInfo.getAmount());
        resultInfo.setFinishTime(orderResultInfo.getFinishTime());
        resultInfo.setMxOrderId(orderResultInfo.getOrderNo());
        resultInfo.setSysOrderId(orderResultInfo.getPayOrderNo());
        resultInfo.setOrderStatus(orderResultInfo.isSuccess() ? OrderStatus.SUCCESS.getCode() : OrderStatus.FAIL.getCode());
        resultInfo.setRetCode(orderResultInfo.getReturnCode());
        resultInfo.setErrorMsg(orderResultInfo.getReturnMsg());
        resultInfo.setHfUserId(orderResultInfo.getHfUserId());
        resultInfo.setPayPath(orderResultInfo.getPayPath());
        depFixedLoanPaymentService.borrowerDFWithdraw(resultInfo);
    }

    @Override
    public void outOfAccountResultAccept(OrderResultInfo orderResultInfo) {
        LnPayOrdersExample example = new LnPayOrdersExample();
        example.createCriteria().andOrderNoEqualTo(orderResultInfo.getOrderNo());
        List<LnPayOrders> orders = lnPayOrdersMapper.selectByExample(example);
        LnPayOrders order = orders.get(0);
        G2BReqMsg_HFBank_OutOfAccount req = new G2BReqMsg_HFBank_OutOfAccount();
        req.setOut_amt(orderResultInfo.getAmount());
        req.setPlatucst(orderResultInfo.getHfUserId());
        req.setOpen_branch(order.getBankName());
        req.setWithdraw_account(order.getBankCardNo());
        req.setPayee_name(order.getUserName());
        req.setPay_finish_date(new Date());
        req.setPay_finish_time(new Date());
        req.setOrder_status(orderResultInfo.isSuccess() == true ? Constants.HF_OUT_OF_ACCOUNT_SUCCESS : Constants.HF_OUT_OF_ACCOUNT_FAIL);
        req.setHost_req_serial_no(orderResultInfo.getPayOrderNo());
        req.setOrder_no(orderResultInfo.getOrderNo());
        req.setError_info(orderResultInfo.getReturnMsg());
        req.setError_no(orderResultInfo.getReturnCode());
        req.setPlat_no(null);
        PartnerEnum partnerEnum = PartnerEnum.getEnumByCode(order.getPartnerCode());
        if(partnerEnum == null) {
            throw new PTMessageException(PTMessageEnum.TRANS_ERROR.getCode(), "未找到合作方编码");
        }
        depFixedLoanPaymentService.outOfAccountResultAccept(req, DepTargetEnum.DEP_TARGET_OPERATE_CHARGE_OFF, partnerEnum);
    }

    @Override
    public void outOfAccountResultAcceptZan(OrderResultInfo orderResultInfo) {
        LnPayOrdersExample example = new LnPayOrdersExample();
        example.createCriteria().andOrderNoEqualTo(orderResultInfo.getOrderNo());
        List<LnPayOrders> orders = lnPayOrdersMapper.selectByExample(example);
        LnPayOrders order = orders.get(0);

        G2BReqMsg_HFBank_OutOfAccount req = new G2BReqMsg_HFBank_OutOfAccount();
        req.setOut_amt(orderResultInfo.getAmount());
        req.setPlatucst(orderResultInfo.getHfUserId());
        req.setOpen_branch(order.getBankName());
        req.setWithdraw_account(order.getBankCardNo());
        req.setPayee_name(order.getUserName());
        req.setPay_finish_date(new Date());
        req.setPay_finish_time(new Date());
        req.setOrder_status(orderResultInfo.isSuccess() == true ? Constants.HF_OUT_OF_ACCOUNT_SUCCESS : Constants.HF_OUT_OF_ACCOUNT_FAIL);
        req.setHost_req_serial_no(orderResultInfo.getPayOrderNo());
        req.setOrder_no(orderResultInfo.getOrderNo());
        req.setError_info(orderResultInfo.getReturnMsg());
        req.setError_no(orderResultInfo.getReturnCode());
        req.setPlat_no(null);
        loanPaymentService.outOfAccountResultAcceptZan(req, DepTargetEnum.DEP_TARGET_OPERATE_CHARGE_OFF);

    }

    @Override
    public void revenueSettle(OrderResultInfo orderResultInfo) {
        RevenueSettleResultInfo revenueSettleResultInfo = new RevenueSettleResultInfo();
        revenueSettleResultInfo.setOrderNo(orderResultInfo.getOrderNo());
        revenueSettleResultInfo.setSuc(orderResultInfo.isSuccess());
        //获取重复还款id列表
        Date nowDate = DateUtil.parseDate(DateUtil.formatYYYYMMDD(new Date()));
        Date yestday = DateUtil.addDays(nowDate, -1);
        List<String> statusList = new ArrayList<String>();
        statusList.add(Constants.REPEAT_REPAY_STATUS_PROCESS);
        LnRepeatRepayRecordExample example = new LnRepeatRepayRecordExample();
        example.createCriteria().andSettleDateEqualTo(yestday).andPartnerCodeEqualTo(Constants.PRODUCT_PROPERTY_SYMBOL_YUN_DAI_SELF).andStatusIn(statusList);
        List<LnRepeatRepayRecord> repeatRepayList = lnRepeatRepayRecordMapper.selectByExample(example);
        List<Integer> repeatRepayIds = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(repeatRepayList)){
            for(LnRepeatRepayRecord lnRepeatRepayRecord: repeatRepayList){
                repeatRepayIds.add(lnRepeatRepayRecord.getId());
            }
        }
        revenueSettleResultInfo.setRepeatRepayIds(repeatRepayIds);
        revenueSettleResultInfo.setReturnCode(orderResultInfo.getReturnCode());
        revenueSettleResultInfo.setReturnMsg(orderResultInfo.getReturnMsg());
        depFixedRevenueSettleService.revenueSettleResult(revenueSettleResultInfo, PartnerEnum.YUN_DAI_SELF);
    }

    @Override
    public void sevenDaiRevenueSettle(OrderResultInfo orderResultInfo) {
        RevenueSettleResultInfo revenueSettleResultInfo = new RevenueSettleResultInfo();
        revenueSettleResultInfo.setOrderNo(orderResultInfo.getOrderNo());
        revenueSettleResultInfo.setSuc(orderResultInfo.isSuccess());
        //获取重复还款id列表
        Date nowDate = DateUtil.parseDate(DateUtil.formatYYYYMMDD(new Date()));
        Date yestday = DateUtil.addDays(nowDate, -1);
        List<String> statusList = new ArrayList<String>();
        statusList.add(Constants.REPEAT_REPAY_STATUS_PROCESS);
        LnRepeatRepayRecordExample example = new LnRepeatRepayRecordExample();
        example.createCriteria().andSettleDateEqualTo(yestday).andPartnerCodeEqualTo(PartnerEnum.SEVEN_DAI_SELF.getCode()).andStatusIn(statusList);
        List<LnRepeatRepayRecord> repeatRepayList = lnRepeatRepayRecordMapper.selectByExample(example);
        List<Integer> repeatRepayIds = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(repeatRepayList)){
            for(LnRepeatRepayRecord lnRepeatRepayRecord: repeatRepayList){
                repeatRepayIds.add(lnRepeatRepayRecord.getId());
            }
        }
        revenueSettleResultInfo.setRepeatRepayIds(repeatRepayIds);
        revenueSettleResultInfo.setReturnCode(orderResultInfo.getReturnCode());
        revenueSettleResultInfo.setReturnMsg(orderResultInfo.getReturnMsg());
        depFixedRevenueSettleService.revenueSettleResult(revenueSettleResultInfo, PartnerEnum.SEVEN_DAI_SELF);
    }

    @Override
    public void cutRepayToBorrower(OrderResultInfo orderResultInfo) {
        RepayResultInfo repayResultInfo = new RepayResultInfo();
        repayResultInfo.setErrorCode(orderResultInfo.getReturnCode());
        repayResultInfo.setErrorMsg(orderResultInfo.getReturnMsg());
        repayResultInfo.setOrderFinTime(new Date());
        repayResultInfo.setAmount(orderResultInfo.getAmount());
        repayResultInfo.setOrderId(orderResultInfo.getOrderNo());
        if(orderResultInfo.isSuccess()){
            repayResultInfo.setStatus(OrderStatus.SUCCESS.getCode());
        }else{
            repayResultInfo.setStatus(OrderStatus.FAIL.getCode());
        }
        depOfflineRepayService.notifyCutRepay2BorrowerResult(repayResultInfo);
    }
}

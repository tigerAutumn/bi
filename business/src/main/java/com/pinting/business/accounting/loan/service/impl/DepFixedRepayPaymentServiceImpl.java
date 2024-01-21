package com.pinting.business.accounting.loan.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.pinting.business.accounting.loan.enums.LoanStatus;
import com.pinting.business.accounting.loan.enums.LoanSubjects;
import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.loan.model.*;
import com.pinting.business.accounting.loan.service.*;
import com.pinting.business.accounting.loan.util.redisUtil;
import com.pinting.business.accounting.service.PayOrdersService;
import com.pinting.business.coreflow.core.enums.BusinessTypeEnum;
import com.pinting.business.coreflow.core.enums.TransCodeEnum;
import com.pinting.business.coreflow.core.model.FlowContext;
import com.pinting.business.coreflow.core.service.DispatcherService;
import com.pinting.business.dao.*;
import com.pinting.business.dayend.task.process.LateRepayNoticeProcess;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.enums.PayPlatformEnum;
import com.pinting.business.enums.RedisLockEnum;
import com.pinting.business.enums.TransTypeEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.*;
import com.pinting.business.model.dto.RepayQueueDTO;
import com.pinting.business.model.vo.*;
import com.pinting.business.service.common.AlgorithmService;
import com.pinting.business.service.common.CommissionService;
import com.pinting.business.service.loan.LateRepayAgreementService;
import com.pinting.business.service.site.SMSService;
import com.pinting.business.service.site.SpecialJnlService;
import com.pinting.business.service.site.SysConfigService;
import com.pinting.business.util.CalculatorUtil;
import com.pinting.business.util.Constants;
import com.pinting.business.util.Util;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.*;
import com.pinting.gateway.hessian.message.baofoo.B2GReqMsg_BaoFooAgreementPay_DirectAgreementPay;
import com.pinting.gateway.hessian.message.baofoo.B2GReqMsg_BaoFooCutpayment_Cutpayment;
import com.pinting.gateway.hessian.message.baofoo.B2GResMsg_BaoFooAgreementPay_DirectAgreementPay;
import com.pinting.gateway.hessian.message.baofoo.B2GResMsg_BaoFooCutpayment_Cutpayment;
import com.pinting.gateway.hessian.message.baofoo.model.RiskItems;
import com.pinting.gateway.hessian.message.dafy.*;
import com.pinting.gateway.hessian.message.dafy.model.QueryBillRepayment;
import com.pinting.gateway.hessian.message.loan.model.Repayment;
import com.pinting.gateway.hessian.message.loan7.*;
import com.pinting.gateway.hessian.message.zsd.*;
import com.pinting.gateway.out.service.BaoFooTransportService;
import com.pinting.gateway.out.service.loan.DafyNoticeService;
import com.pinting.gateway.out.service.loan7.DepLoan7NoticeService;
import com.pinting.gateway.out.service.zsd.ZsdNoticeService;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

/**
 *
 * @project business
 * @title DepFixedRepayPaymentServiceImpl.java
 * @author Dragon & cat
 * @date 2017-4-4
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
@Service
public class DepFixedRepayPaymentServiceImpl implements DepFixedRepayPaymentService {
    private final Logger logger = LoggerFactory.getLogger(DepFixedRepayPaymentServiceImpl.class);

    private JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);

    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private LnRepeatRepayRecordMapper lnRepeatRepayRecordMapper;
    @Autowired
    private DepFixedRepayAccountService depFixedRepayAccountService;
    @Autowired
    private LnPayOrdersMapper lnPayOrdersMapper;
    @Autowired
    private LnRepayScheduleMapper lnRepayScheduleMapper;
    @Autowired
    private SysConfigService sysConfigService;
    @Autowired
    private DepFixedLoanRelationshipService depFixedLoanRelationshipService;
    @Autowired
    private LnRepayScheduleDetailMapper lnRepayScheduleDetailMapper;
    @Autowired
    private LnLoanRelationMapper lnLoanRelationMapper;
    @Autowired
    private LnAccountFillDetailMapper lnAccountFillDetailMapper;
    @Autowired
    private LnLoanMapper lnLoanMapper;
    @Autowired
    private LnDepositionRepayScheduleMapper lnDepositionRepayScheduleMapper;
    @Autowired
    private LnDepositionRepayScheduleDetailMapper lnDepositionRepayScheduleDetailMapper;
    @Autowired
    private CommissionService commissionService;
    @Autowired
    private RepayPaymentService repayPaymentService;
    @Autowired
    private LnRepayMapper lnRepayMapper;
    @Autowired
    private LnUserMapper lnUserMapper;
    @Autowired
    private Bs19payBankMapper bs19payBankMapper;
    @Autowired
    private LnRepayDetailMapper lnRepayDetailMapper;
    @Autowired
    private LnPayOrdersJnlMapper lnPayOrdersJnlMapper;
    @Autowired
    private SMSService smsService;
    @Autowired
    private BaoFooTransportService baoFooTransportService;
    @Autowired
    private PayOrdersService payOrdersService;
    @Autowired
    private DafyNoticeService dafyNoticeService;
    @Autowired
    private BsPayResultQueueMapper queueMapper;
    @Autowired
    private LnCompensateMapper lnCompensateMapper;
    @Autowired
    private BsHfbankUserExtMapper bsHfbankUserExtMapper;
    @Autowired
    private LnCompensateRelationMapper lnCompensateRelationMapper;
    @Autowired
    private LnCompensateDetailMapper lnCompensateDetailMapper;
    @Autowired
    private SpecialJnlService specialJnlService;
    @Autowired
    private LnFinanceRepayScheduleMapper lnFinanceRepayScheduleMapper;
    @Autowired
    private BsServiceFeeMapper serviceFeeMapper;
    @Autowired
    private LateRepayAgreementService lateRepayAgreementService;
    @Autowired
    private ZsdNoticeService zsdNoticeService;
    @Autowired
    private LnBindCardMapper lnBindCardMapper;
    @Autowired
    private RepayAccountService repayAccountService;
    @Autowired
    private BsRevenueTransDetailMapper bsRevenueTransDetailMapper;
    @Autowired
    private BsPaymentChannelMapper paymentChannelMapper;
    @Autowired
    private DepFixedBillSyncService depFixedBillSyncService;
//    @Autowired
//    private DepFixedRepayPaymentService depFixedRepayPaymentService;
    @Autowired
    private DepLoan7NoticeService depLoan7NoticeService;
    @Autowired
    private LnBillBizInfoMapper billBizInfoMapper;
    @Autowired
    private LnCreditTransferMapper lnCreditTransferMapper;
    @Autowired
    private BsUserMapper bsUserMapper;
    @Autowired
    private BsBgwNuccSignRelationMapper bsBgwNuccSignRelationMapper;
    @Autowired
    private AlgorithmService algorithmService;
    @Autowired
    private DispatcherService dispatcherService;

    /**
     * 主动还款预下单
     *
     * @param req
     * @return 支付单号
     */
    public String doPreRepay(final G2BReqMsg_DafyLoan_ActiveRepayPre req) {
        //订单是否重复
        LnRepayExample repayExample = new LnRepayExample();
        repayExample.createCriteria().andPartnerOrderNoEqualTo(req.getOrderNo());
        List<LnRepay> repayList = lnRepayMapper.selectByExample(repayExample);
        if (CollectionUtils.isNotEmpty(repayList)) {
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "订单号重复");
        }
        //账单不为空
        if(CollectionUtils.isEmpty(req.getRepayments())){
            throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND, "还款预下单云贷传入账单为空");
        }
        //借款用户是否存在
        LnUserExample lnUserExample = new LnUserExample();
        lnUserExample.createCriteria().andPartnerCodeEqualTo(PartnerEnum.YUN_DAI_SELF.getCode())
                .andPartnerUserIdEqualTo(req.getUserId());
        List<LnUser> lnUsers = lnUserMapper.selectByExample(lnUserExample);
        if (CollectionUtils.isEmpty(lnUsers)){
            throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND, "找不到借款用户信息");
        }
        final LnUser lnUser = lnUsers.get(0);
        //借款是否存在
        LnLoanExample loanExample = new LnLoanExample();
        loanExample.createCriteria().andPartnerLoanIdEqualTo(req.getLoanId())
                .andLnUserIdEqualTo(lnUser.getId()).andStatusEqualTo(LoanStatus.LOAN_STATUS_PAIED.getCode());
        List<LnLoan> loans = lnLoanMapper.selectByExample(loanExample);
        if (CollectionUtils.isEmpty(loans)) {
            throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND, "找不到借款["+req.getLoanId()+"]信息");
        }
        final LnLoan lnLoan = loans.get(0);
        //该笔借款是否有还款正在进行中
        if(lnLoan.getIsRepaying().equals(Constants.DEP_REPAY_REPAYING)){
            throw new PTMessageException(PTMessageEnum.DEP_REPAY_REPAYING);
        }
        //代扣银行是否存在
        List<Pay19BankVO> pay19BankVOs = bs19payBankMapper.selectByChanelPayTypeBankCode(Constants.CHANNEL_BAOFOO, Constants.PAY_TYPE_HOLD, req.getBankCode());
        if(CollectionUtils.isEmpty(pay19BankVOs)){
            throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND, "银行编码["+req.getBankCode()+"]不支持");
        }
        final Pay19BankVO pay19BankVO = pay19BankVOs.get(0);
        final String orderNo = Util.generateOrderNo4BaoFoo(8);
        final String bgwOrderNo = Util.generateSysOrderNo(Constants.BGW_ORDER_NUMBER_GENERATE_WITHHOLD);
        transactionTemplate.execute(new TransactionCallbackWithoutResult(){
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                for(HashMap<String, Object> bill : req.getRepayments()){
                    //是否有对应账单
                    LnRepayScheduleExample lnRepayScheduleExample = new LnRepayScheduleExample();
                    lnRepayScheduleExample.createCriteria().andPartnerRepayIdEqualTo((String)bill.get("repayId")).andLoanIdEqualTo(lnLoan.getId());
                    List<LnRepaySchedule> lnRepaySchedules = lnRepayScheduleMapper.selectByExample(lnRepayScheduleExample);
                    //预下单记录存库
                    LnRepay repay = new LnRepay();
                    if (CollectionUtils.isNotEmpty(lnRepaySchedules)) {
                        //有对应账单时存账单id，无对应账单时为空
                        LnRepaySchedule lnRepaySchedule = lnRepaySchedules.get(0);
                        if(lnRepaySchedule.getReturnFlag().equals(Constants.LN_REPAY_RETURN_FLAG_PENDING) ||
                                lnRepaySchedule.getReturnFlag().equals(Constants.LN_REPAY_RETURN_FLAG_PROCESS)){
                            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "账单[" + (String)bill.get("repayId") + "]正在处理中");
                        }
                        repay.setRepayPlanId(lnRepaySchedule.getId());
                        String scheduleStatus = lnRepaySchedule.getStatus();
                        if(LoanStatus.REPAY_SCHEDULE_STATUS_REPAIED.getCode().equals(scheduleStatus) ||
                                LoanStatus.REPAY_SCHEDULE_STATUS_LATE_REPAIED.getCode().equals(scheduleStatus) ||
                                LoanStatus.REPAY_SCHEDULE_STATUS_CANCELLED.getCode().equals(scheduleStatus)){
                            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "账单[" + (String)bill.get("repayId") + "]已存在还款或已废除");
                        }
                        //是否已存在重复还款记录
                        repeatRepayJudge(lnRepaySchedule.getId(), (String)bill.get("repayId"));
                    }
                    repay.setStatus(LoanStatus.REPAY_STATUS_INIT.getCode());
                    repay.setUpdateTime(new Date());
                    repay.setBgwOrderNo(bgwOrderNo);
                    repay.setCreateTime(new Date());
                    repay.setDoneTotal((Double)bill.get("total"));
                    repay.setLnUserId(lnUser.getId());
                    repay.setPayOrderNo(orderNo);
                    repay.setLoanId(lnLoan.getId());
                    repay.setPartnerOrderNo(req.getOrderNo());
                    repay.setInformStatus(LoanStatus.NOTICE_STATUS_INIT.getCode());
                    lnRepayMapper.insertSelective(repay);

                    LnRepayDetail lnRepayDetail = new LnRepayDetail();
                    lnRepayDetail.setUpdateTime(new Date());
                    lnRepayDetail.setCreateTime(new Date());
                    lnRepayDetail.setRepayId(repay.getId());
                    lnRepayDetail.setDoneAmount((Double)bill.get("principal") != null ? (Double)bill.get("principal") : 0d);
                    lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_PRINCIPAL.getCode());
                    lnRepayDetailMapper.insertSelective(lnRepayDetail);

                    lnRepayDetail.setDoneAmount((Double)bill.get("interest") != null ? (Double)bill.get("interest") : 0d);
                    lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_INTEREST.getCode());
                    lnRepayDetailMapper.insertSelective(lnRepayDetail);

                    lnRepayDetail.setDoneAmount((Double)bill.get("principalOverdue") != null ? (Double)bill.get("principalOverdue") : 0d);
                    lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_PRINCIPAL_OVERDUE.getCode());
                    lnRepayDetailMapper.insertSelective(lnRepayDetail);

                    lnRepayDetail.setDoneAmount((Double)bill.get("interestOverdue") != null ? (Double)bill.get("interestOverdue") : 0d);
                    lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_INTEREST_OVERDUE.getCode());
                    lnRepayDetailMapper.insertSelective(lnRepayDetail);

                }
            }
        });
        //根据借款支付订单号，查询子账户id
        LnPayOrdersExample ordersExample = new LnPayOrdersExample();
        ordersExample.createCriteria().andOrderNoEqualTo(lnLoan.getPayOrderNo()).andPartnerCodeEqualTo(PartnerEnum.YUN_DAI_SELF.getCode());
        List<LnPayOrders> loanOrders = lnPayOrdersMapper.selectByExample(ordersExample);
        //记录ln_pay_orders,ln_pay_orders_jnl表
        LnPayOrders lnPayOrders = new LnPayOrders();
        lnPayOrders.setCreateTime(new Date());
        lnPayOrders.setAccountType(1);
        lnPayOrders.setAmount(req.getTotalAmount());
        lnPayOrders.setBankCardNo(req.getBankCard());
        Integer bankId = pay19BankVO.getBankId();
        lnPayOrders.setBankId(bankId);
        lnPayOrders.setBankName(pay19BankVO.getBankName());
        lnPayOrders.setChannel(Constants.CHANNEL_BAOFOO);
        lnPayOrders.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DK.getCode());
        lnPayOrders.setIdCard(req.getIdCard());
        lnPayOrders.setLnUserId(lnUser.getId());
        lnPayOrders.setMobile(req.getMobile());
        lnPayOrders.setMoneyType(0);
        lnPayOrders.setOrderNo(orderNo);
        lnPayOrders.setPartnerCode(PartnerEnum.YUN_DAI_SELF.getCode());
        lnPayOrders.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_INIT.getCode()));
        lnPayOrders.setSubAccountId(loanOrders.get(0).getSubAccountId());
        lnPayOrders.setTransType(LoanStatus.TRANS_TYPE_REPAY.getCode());
        lnPayOrders.setUpdateTime(new Date());
        lnPayOrders.setUserName(req.getName());
        //查询支付渠道代扣优先商户号信息
    	BsPaymentChannel channelInfo = paymentChannelMapper.selectFisrtDK();
    	if(channelInfo != null){
    		//修改订单表payment_channel_id
    		lnPayOrders.setPaymentChannelId(channelInfo.getId());
    	}
        lnPayOrdersMapper.insertSelective(lnPayOrders);
        
        LnPayOrdersJnl lnPayOrdersJnl = new LnPayOrdersJnl();
        lnPayOrdersJnl.setSubAccountId(loanOrders.get(0).getSubAccountId());
        lnPayOrdersJnl.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DK.getCode());
        lnPayOrdersJnl.setCreateTime(new Date());
        lnPayOrdersJnl.setOrderId(lnPayOrders.getId());
        lnPayOrdersJnl.setOrderNo(lnPayOrders.getOrderNo());
        lnPayOrdersJnl.setTransAmount(lnPayOrders.getAmount());
        lnPayOrdersJnl.setTransCode(LoanStatus.ORDERS_JNL_STATUS_INIT.getCode());
        lnPayOrdersJnl.setUserId(lnUser.getId());
        lnPayOrdersJnl.setSysTime(new Date());
        lnPayOrdersJnlMapper.insertSelective(lnPayOrdersJnl);
        //发送短信
        String sendCode = null;
        String returnMsg = null;
        try {
            sendCode = smsService.sendRepayPreToken(req.getMobile());
        }catch (Exception e){
            e.printStackTrace();
            sendCode = Constants.SEND_CODE_ERROR;
            if(e instanceof PTMessageException){
                returnMsg = ((PTMessageException) e).getErrMessage();
            }
        }
        if(Constants.SEND_CODE_ERROR.equals(sendCode)){
            //置订单表为预下单失败
            LnPayOrders ordersTemp = new LnPayOrders();
            ordersTemp.setId(lnPayOrders.getId());
            ordersTemp.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_PRE_FAIL.getCode()));
            ordersTemp.setReturnMsg(returnMsg == null ? "短信发送失败" : returnMsg);
            ordersTemp.setUpdateTime(new Date());
            lnPayOrdersMapper.updateByPrimaryKeySelective(ordersTemp);

            //新增订单流水
            lnPayOrdersJnl = new LnPayOrdersJnl();
            lnPayOrdersJnl.setSubAccountId(loanOrders.get(0).getSubAccountId());
            lnPayOrdersJnl.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DK.getCode());
            lnPayOrdersJnl.setCreateTime(new Date());
            lnPayOrdersJnl.setOrderId(lnPayOrders.getId());
            lnPayOrdersJnl.setOrderNo(lnPayOrders.getOrderNo());
            lnPayOrdersJnl.setTransAmount(lnPayOrders.getAmount());
            lnPayOrdersJnl.setTransCode(LoanStatus.ORDERS_JNL_STATUS_PRE_FAIL.getCode());
            lnPayOrdersJnl.setUserId(lnUser.getId());
            lnPayOrdersJnl.setSysTime(new Date());
            lnPayOrdersJnlMapper.insertSelective(lnPayOrdersJnl);

            //还款表置失败
            LnRepay repayTemp = new LnRepay();
            repayTemp.setUpdateTime(new Date());
            repayTemp.setStatus(LoanStatus.REPAY_STATUS_FAIL.getCode());
            LnRepayExample lnRepayExample = new LnRepayExample();
            lnRepayExample.createCriteria().andPayOrderNoEqualTo(orderNo).andLnUserIdEqualTo(lnUser.getId());
            lnRepayMapper.updateByExampleSelective(repayTemp, lnRepayExample);
            throw new PTMessageException(PTMessageEnum.USER_ORDER_PRE_FAIL, ordersTemp.getReturnMsg());
        }else {
            //置订单表为预下单成功
            LnPayOrders ordersTemp = new LnPayOrders();
            ordersTemp.setId(lnPayOrders.getId());
            ordersTemp.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_PRE_SUCCESS.getCode()));
            ordersTemp.setUpdateTime(new Date());
            lnPayOrdersMapper.updateByPrimaryKeySelective(ordersTemp);

            //新增订单流水
            lnPayOrdersJnl = new LnPayOrdersJnl();
            lnPayOrdersJnl.setSubAccountId(loanOrders.get(0).getSubAccountId());
            lnPayOrdersJnl.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DK.getCode());
            lnPayOrdersJnl.setCreateTime(new Date());
            lnPayOrdersJnl.setOrderId(lnPayOrders.getId());
            lnPayOrdersJnl.setOrderNo(lnPayOrders.getOrderNo());
            lnPayOrdersJnl.setTransAmount(lnPayOrders.getAmount());
            lnPayOrdersJnl.setTransCode(LoanStatus.ORDERS_JNL_STATUS_PRE_SUCCESS.getCode());
            lnPayOrdersJnl.setUserId(lnUser.getId());
            lnPayOrdersJnl.setSysTime(new Date());
            lnPayOrdersJnlMapper.insertSelective(lnPayOrdersJnl);

            //还款表置处理中
            LnRepay repayTemp = new LnRepay();
            repayTemp.setUpdateTime(new Date());
            repayTemp.setStatus(LoanStatus.REPAY_STATUS_PAYING.getCode());
            LnRepayExample lnRepayExample = new LnRepayExample();
            lnRepayExample.createCriteria().andPayOrderNoEqualTo(orderNo).andLnUserIdEqualTo(lnUser.getId());
            lnRepayMapper.updateByExampleSelective(repayTemp, lnRepayExample);
            return bgwOrderNo;
        }
    }

    @Override
    public String doPreRepayRepeat(String partnerOrderNo) {
        String idCardNo = "";
        LnRepayExample repayExample = new LnRepayExample();
        repayExample.createCriteria().andPartnerOrderNoEqualTo(partnerOrderNo);
        final List<LnRepay> repayList = lnRepayMapper.selectByExample(repayExample);
        if (CollectionUtils.isEmpty(repayList)) {
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "请重新预下单");
        }
        List<LnPayOrders> repayOrdersList = null;
        LnRepay repay = repayList.get(0);
        LnPayOrdersExample ordersExample = new LnPayOrdersExample();
        ordersExample.createCriteria().andOrderNoEqualTo(repay.getPayOrderNo()).andPartnerCodeEqualTo(PartnerEnum.YUN_DAI_SELF.getCode());
        repayOrdersList = lnPayOrdersMapper.selectByExample(ordersExample);
        if(CollectionUtils.isNotEmpty(repayOrdersList)){
            idCardNo = repayOrdersList.get(0).getIdCard();
        }else{
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "请重新预下单");
        }
        try {
            jsClientDaoSupport.lock(RedisLockEnum.LOCK_REPAY_SELF.getKey() + PartnerEnum.YUN_DAI_SELF.getCode() + idCardNo);
            logger.info("=========云贷还款重发短信，加锁："+ RedisLockEnum.LOCK_REPAY_SELF.getKey() + PartnerEnum.YUN_DAI_SELF.getCode() + idCardNo +"=======");
            List<LnPayOrders> repayOrdersListTemp = lnPayOrdersMapper.selectByExample(ordersExample);
            if(CollectionUtils.isEmpty(repayOrdersListTemp)){
                throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "请重新预下单");
            }
            LnPayOrders repayOrder = repayOrdersListTemp.get(0);
            //只能预下单成功和预下单失败才允许重发
            if (!repayOrder.getStatus().equals(Integer.parseInt(LoanStatus.ORDERS_STATUS_PRE_SUCCESS.getCode())) &&
                    !repayOrder.getStatus().equals(Integer.parseInt(LoanStatus.ORDERS_STATUS_PRE_FAIL.getCode()))) {
                throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "该订单为非预下单状态，请重新预下单");
            }

            //判断该笔订单是否超过15分钟
            if (DateUtil.getDiffeMinute(repayOrder.getUpdateTime()) > 15) {
                throw new PTMessageException(PTMessageEnum.ORDERS_TIMEOUT_FAILURE);
            }


            //借款用户是否存在
            final LnUser lnUser = lnUserMapper.selectByPrimaryKey(repayList.get(0).getLnUserId());
            if (null == lnUser) {
                throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND, "找不到借款用户信息");
            }

            //借款是否存在
            final LnLoan lnLoan = lnLoanMapper.selectByPrimaryKey(repay.getLoanId());
            if (null == lnLoan) {
                throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND, "找不到借款[" + repay.getLoanId() + "]信息");
            }
            //该笔借款是否有还款正在进行中
            if (lnLoan.getIsRepaying().equals(Constants.DEP_REPAY_REPAYING)) {
                throw new PTMessageException(PTMessageEnum.DEP_REPAY_REPAYING);
            }

            String payOrderNo = Util.generateOrderNo4BaoFoo(8);
            String bgwOrderNo = repay.getBgwOrderNo();


            //判断还款订单状态
            LnPayOrders newRepayOrder = new LnPayOrders();
            //根据借款支付订单号，查询子账户id
            LnPayOrdersExample loanOrdersExample = new LnPayOrdersExample();
            loanOrdersExample.createCriteria().andOrderNoEqualTo(lnLoan.getPayOrderNo()).andPartnerCodeEqualTo(PartnerEnum.YUN_DAI_SELF.getCode());
            List<LnPayOrders> loanOrders = lnPayOrdersMapper.selectByExample(loanOrdersExample);

            if (repayOrder.getStatus().equals(Integer.parseInt(LoanStatus.ORDERS_STATUS_PRE_SUCCESS.getCode()))) {
                payOrderNo = repay.getPayOrderNo();

                newRepayOrder.setId(repayOrder.getId());
                newRepayOrder.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_INIT.getCode()));
                newRepayOrder.setUpdateTime(new Date());
                lnPayOrdersMapper.updateByPrimaryKeySelective(newRepayOrder);

                //记录ln_pay_orders_jnl表
                LnPayOrdersJnl lnPayOrdersJnl = new LnPayOrdersJnl();
                lnPayOrdersJnl.setSubAccountId(loanOrders.get(0).getSubAccountId());
                lnPayOrdersJnl.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DK.getCode());
                lnPayOrdersJnl.setCreateTime(new Date());
                lnPayOrdersJnl.setOrderId(repayOrder.getId());
                lnPayOrdersJnl.setOrderNo(repayOrder.getOrderNo());
                lnPayOrdersJnl.setTransAmount(repayOrder.getAmount());
                lnPayOrdersJnl.setTransCode(LoanStatus.ORDERS_JNL_STATUS_INIT.getCode());
                lnPayOrdersJnl.setUserId(lnUser.getId());
                lnPayOrdersJnl.setSysTime(new Date());
                lnPayOrdersJnl.setNote("云贷还款重发短信验证码,更新订单");
                lnPayOrdersJnlMapper.insertSelective(lnPayOrdersJnl);

            } else if (repayOrder.getStatus().equals(Integer.parseInt(LoanStatus.ORDERS_STATUS_PRE_FAIL.getCode()))) {

                for (LnRepay lnRepay : repayList) {
                    LnRepay updateRepay = new LnRepay();
                    updateRepay.setId(lnRepay.getId());
                    updateRepay.setPayOrderNo(payOrderNo);
                    updateRepay.setUpdateTime(new Date());
                    lnRepayMapper.updateByPrimaryKeySelective(updateRepay);
                }

                newRepayOrder.setCreateTime(new Date());
                newRepayOrder.setAccountType(1);
                newRepayOrder.setAmount(repayOrder.getAmount());
                newRepayOrder.setBankCardNo(repayOrder.getBankCardNo());
                newRepayOrder.setBankId(repayOrder.getBankId());
                newRepayOrder.setBankName(repayOrder.getBankName());
                newRepayOrder.setChannel(Constants.CHANNEL_BAOFOO);
                newRepayOrder.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DK.getCode());
                newRepayOrder.setIdCard(repayOrder.getIdCard());
                newRepayOrder.setLnUserId(lnUser.getId());
                newRepayOrder.setMobile(repayOrder.getMobile());
                newRepayOrder.setMoneyType(0);
                newRepayOrder.setOrderNo(payOrderNo);
                newRepayOrder.setPartnerCode(PartnerEnum.YUN_DAI_SELF.getCode());
                newRepayOrder.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_INIT.getCode()));
                newRepayOrder.setSubAccountId(loanOrders.get(0).getSubAccountId());
                newRepayOrder.setTransType(LoanStatus.TRANS_TYPE_REPAY.getCode());
                newRepayOrder.setUpdateTime(new Date());
                newRepayOrder.setUserName(repayOrder.getUserName());
                lnPayOrdersMapper.insertSelective(newRepayOrder);

                //记录ln_pay_orders_jnl表
                LnPayOrdersJnl lnPayOrdersJnl = new LnPayOrdersJnl();
                lnPayOrdersJnl.setSubAccountId(loanOrders.get(0).getSubAccountId());
                lnPayOrdersJnl.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DK.getCode());
                lnPayOrdersJnl.setCreateTime(new Date());
                lnPayOrdersJnl.setOrderId(repayOrder.getId());
                lnPayOrdersJnl.setOrderNo(repayOrder.getOrderNo());
                lnPayOrdersJnl.setTransAmount(repayOrder.getAmount());
                lnPayOrdersJnl.setTransCode(LoanStatus.ORDERS_JNL_STATUS_INIT.getCode());
                lnPayOrdersJnl.setUserId(lnUser.getId());
                lnPayOrdersJnl.setSysTime(new Date());
                lnPayOrdersJnl.setNote("云贷还款重发短信验证码");
                lnPayOrdersJnlMapper.insertSelective(lnPayOrdersJnl);

            } else {
                //订单表状态既不是预下单成功状态也不是预下单失败状态，直接抛错
                throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "订单状态校验失败，非PRE_FAIL & PRE_SUCCESS");
            }

            //发送短信
            String sendCode = null;
            String returnMsg = null;
            try {
                sendCode = smsService.sendRepayPreToken(repayOrder.getMobile());
            } catch (Exception e) {
                e.printStackTrace();
                sendCode = Constants.SEND_CODE_ERROR;
                if (e instanceof PTMessageException) {
                    returnMsg = ((PTMessageException) e).getErrMessage();
                }
            }
            if (Constants.SEND_CODE_ERROR.equals(sendCode)) {
                LnPayOrders ordersTemp = new LnPayOrders();
                ordersTemp.setId(newRepayOrder.getId());
                ordersTemp.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_PRE_FAIL.getCode()));
                ordersTemp.setReturnMsg(returnMsg == null ? "短信发送失败" : returnMsg);
                ordersTemp.setUpdateTime(new Date());
                lnPayOrdersMapper.updateByPrimaryKeySelective(ordersTemp);
                //还款表置失败
                LnRepay repayTemp = new LnRepay();
                repayTemp.setUpdateTime(new Date());
                repayTemp.setStatus(LoanStatus.REPAY_STATUS_FAIL.getCode());
                LnRepayExample lnRepayExample = new LnRepayExample();
                lnRepayExample.createCriteria().andPayOrderNoEqualTo(payOrderNo).andLnUserIdEqualTo(lnUser.getId());
                lnRepayMapper.updateByExampleSelective(repayTemp, lnRepayExample);
                throw new PTMessageException(PTMessageEnum.USER_ORDER_PRE_FAIL, ordersTemp.getReturnMsg());
            } else {
                LnPayOrders ordersTemp = new LnPayOrders();
                ordersTemp.setId(newRepayOrder.getId());
                ordersTemp.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_PRE_SUCCESS.getCode()));
                ordersTemp.setUpdateTime(new Date());
                lnPayOrdersMapper.updateByPrimaryKeySelective(ordersTemp);
                //还款表置处理中
                LnRepay repayTemp = new LnRepay();
                repayTemp.setUpdateTime(new Date());
                repayTemp.setStatus(LoanStatus.REPAY_STATUS_PAYING.getCode());
                LnRepayExample lnRepayExample = new LnRepayExample();
                lnRepayExample.createCriteria().andPayOrderNoEqualTo(payOrderNo).andLnUserIdEqualTo(lnUser.getId());
                lnRepayMapper.updateByExampleSelective(repayTemp, lnRepayExample);
                return bgwOrderNo;
            }
        }finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_REPAY_SELF.getKey() + PartnerEnum.YUN_DAI_SELF.getCode() + idCardNo);
            logger.info("=========云贷还款重发短信，解锁："+ RedisLockEnum.LOCK_REPAY_SELF.getKey() + PartnerEnum.YUN_DAI_SELF.getCode() + idCardNo +"=======");
        }
    }

    /**
     * 主动还款，代扣是否已存在重复还款
     * @param billId
     * @param parnterBillId
     */
    public void repeatRepayJudge(Integer billId,String parnterBillId){
        LnRepeatRepayRecordExample example = new LnRepeatRepayRecordExample();
        example.createCriteria().andRepayPlanIdEqualTo(billId);
        List<LnRepeatRepayRecord> repeatRepayRecords = lnRepeatRepayRecordMapper.selectByExample(example);
        if(CollectionUtils.isNotEmpty(repeatRepayRecords)){
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "账单[" + parnterBillId + "]已存在重复还款");
        }
    }
    
    public void doRepayConfirm(G2BReqMsg_DafyLoan_ActiveRepayConfirm req) throws Exception {
    	String idCardNo = "";
    	LnRepayExample repayEx = new LnRepayExample();
        repayEx.createCriteria().andBgwOrderNoEqualTo(req.getBgwOrderNo());
        List<LnRepay> repayList = lnRepayMapper.selectByExample(repayEx);
        List<LnPayOrders> ordersList = null;
        PartnerEnum partnerEnum = PartnerEnum.YUN_DAI_SELF;
        if(CollectionUtils.isNotEmpty(repayList)){
        	LnPayOrdersExample ordersExample1 = new LnPayOrdersExample();
            ordersExample1.createCriteria().andOrderNoEqualTo(repayList.get(0).getPayOrderNo()).andPartnerCodeEqualTo(partnerEnum.getCode());
            ordersList = lnPayOrdersMapper.selectByExample(ordersExample1);
            if(CollectionUtils.isNotEmpty(ordersList)){
            	idCardNo = ordersList.get(0).getIdCard();
            }
        }
        try {
            jsClientDaoSupport.lock(RedisLockEnum.LOCK_REPAY_SELF.getKey() + partnerEnum.getCode() + idCardNo);
            logger.info("=========云贷确认还款，加锁："+ RedisLockEnum.LOCK_REPAY_SELF.getKey() + partnerEnum.getCode() + idCardNo +"=======");

            if (CollectionUtils.isEmpty(repayList)) {
                throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "订单号不存在");
            }
            if (CollectionUtils.isEmpty(ordersList)) {
                throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND, "请重新发送验证码");
            }
            LnPayOrders lnPayOrder = ordersList.get(0);
            LnRepay lnRepay = repayList.get(0);
            //判断如果订单是成功的则返回成功
            if (lnPayOrder.getStatus().equals(Integer.parseInt(LoanStatus.ORDERS_STATUS_SUCCESS.getCode())) ||
            		lnPayOrder.getStatus().equals(Integer.parseInt(LoanStatus.ORDERS_STATUS_PAYING.getCode()))) {
				return;
			}
            
            if (lnPayOrder.getStatus().equals(Integer.parseInt(LoanStatus.ORDERS_STATUS_FAIL.getCode()))) {
            	throw new PTMessageException(PTMessageEnum.TRANS_ERROR, StringUtil.isBlank(lnPayOrder.getReturnMsg())?"订单失败":lnPayOrder.getReturnMsg());
			}
            
            if (!LoanStatus.REPAY_STATUS_PAYING.getCode().equals(lnRepay.getStatus())) {
            	throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "还款记录非预下单成功状态");
			}
            
            if (!lnPayOrder.getStatus().equals(Integer.parseInt(LoanStatus.ORDERS_STATUS_PRE_SUCCESS.getCode()))) {
            	throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "还款订单非预下单成功状态");
			}

            //该笔借款是否有还款正在进行中
            
            LnLoan lnLoan = lnLoanMapper.selectByPrimaryKey(lnRepay.getLoanId());
            if(lnLoan.getIsRepaying().equals(Constants.DEP_REPAY_REPAYING)){
                throw new PTMessageException(PTMessageEnum.DEP_REPAY_REPAYING);
            }

            //判断该笔订单是否超过15分钟
            if (DateUtil.getDiffeMinute(lnPayOrder.getUpdateTime()) > 15) {
                throw new PTMessageException(PTMessageEnum.ORDERS_TIMEOUT_FAILURE);
            }

            for (LnRepay repay : repayList) {
                //查询还款记录，判断此笔下单是否包含已还款的记录
                if (repay.getStatus().equals(LoanStatus.REPAY_STATUS_REPAIED.getCode())) {
                    throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "还款repayId="+ repay.getId() +"已有还款的记录");
                }
                if(repay.getRepayPlanId() != null){
                    LnRepaySchedule repaySchedule = lnRepayScheduleMapper.selectByPrimaryKey(repay.getRepayPlanId());

                    if(repaySchedule.getReturnFlag().equals(Constants.LN_REPAY_RETURN_FLAG_PENDING) ||
                            repaySchedule.getReturnFlag().equals(Constants.LN_REPAY_RETURN_FLAG_PROCESS)){
                        throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "账单[" + repaySchedule.getPartnerRepayId() + "]正在处理中");
                    }
                    if(LoanStatus.REPAY_SCHEDULE_STATUS_REPAIED.getCode().equals(repaySchedule.getStatus()) ||
                            LoanStatus.REPAY_SCHEDULE_STATUS_LATE_REPAIED.getCode().equals(repaySchedule.getStatus()) ||
                            LoanStatus.REPAY_SCHEDULE_STATUS_CANCELLED.getCode().equals(repaySchedule.getStatus())){
                        throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "账单[" + repaySchedule.getPartnerRepayId() + "]已存在还款或已废除");
                    }

                    //是否已存在重复还款记录
                    repeatRepayJudge(repaySchedule.getId(), repaySchedule.getPartnerRepayId());
                }
            }
            //预下单短信验证码校验
            Boolean isSuc = smsService.validateIdentityExpire(lnPayOrder.getMobile(), req.getSmsCode(), 600);
            if(!isSuc){
                throw new PTMessageException(PTMessageEnum.MOBILE_CODE_WRONG_ERROR);
            }
            BaoFooDKRes dkRes = new BaoFooDKRes();
            B2GReqMsg_BaoFooAgreementPay_DirectAgreementPay agreementPayReq = new B2GReqMsg_BaoFooAgreementPay_DirectAgreementPay();
            B2GReqMsg_BaoFooCutpayment_Cutpayment cutpayment = new B2GReqMsg_BaoFooCutpayment_Cutpayment();
            boolean isAgreementPsy = false;
            //通过银行bank_id得到银行编码
            Bs19payBankExample example = new Bs19payBankExample();
            example.createCriteria().andBankIdEqualTo(lnPayOrder.getBankId()).andChannelEqualTo(Constants.ORDER_CHANNEL_BAOFOO);
            List<Bs19payBank> bs19payBank = bs19payBankMapper.selectByExample(example);
            
            //查询支付渠道代扣优先商户号信息
            BsPaymentChannel channelInfo = paymentChannelMapper.selectFisrtDK();
            if(channelInfo != null){
            	BsBgwNuccSignRelation signRelation = getSingRelation(lnLoan.getLnUserId(),partnerEnum.getCode(),
            			channelInfo.getMerchantNo(),lnPayOrder.getBankCardNo(),lnPayOrder.getUserName(),
            			lnPayOrder.getIdCard(),lnPayOrder.getMobile());
            	if(signRelation != null && StringUtil.isNotBlank(signRelation.getProtocolNo())){
            		isAgreementPsy = true;
            		//修改订单表payment_channel_id，是否为协议支付（isProtocolPay）
                	payOrdersService.updatePaymentChannelId(lnPayOrder.getId(), channelInfo.getId(), Constants.BAOFOO_IS_PROTOCOLPAY_YES);
            		//已签约，发签约代扣
                	agreementPayReq.setIsMain(channelInfo.getIsMain());
                	agreementPayReq.setSend_time(lnPayOrder.getCreateTime());
            		agreementPayReq.setMember_id(channelInfo.getMerchantNo());
            		agreementPayReq.setTrans_id(lnPayOrder.getOrderNo());
            		agreementPayReq.setUser_id(signRelation.getUserType()+signRelation.getUserId());
            		agreementPayReq.setProtocol_no(signRelation.getProtocolNo());
            		//云贷宝付代扣交易金额入参需要转成分，赞分期就是以分为单位，赞分期无需改动
            		agreementPayReq.setTxn_amt(MoneyUtil.multiply(lnPayOrder.getAmount(), 100).longValue());
            		B2GResMsg_BaoFooAgreementPay_DirectAgreementPay agreementPayRes = null;
            		try {
            			RiskItems riskItems = getRiskItems(lnLoan.getLnUserId(),lnLoan,lnPayOrder,"");
            			agreementPayReq.setRiskItems(riskItems);
            			logger.info("协议支付入参："+agreementPayReq.toString());
            			agreementPayRes = baoFooTransportService.directAgreementPay(agreementPayReq);
            			dkRes.setPay4OnlineOrderNo(agreementPayRes.getPay4OnlineOrderNo());
                        dkRes.setResCode(agreementPayRes.getResCode());
                        dkRes.setResMsg(agreementPayRes.getResMsg());
					} catch (Exception e) {
						 e.printStackTrace();
			                throw new PTMessageException(PTMessageEnum.CONNECTION_ERROR);
					}
            	}else{
            		//修改订单表payment_channel_id，是否为协议支付（isProtocolPay）
                	payOrdersService.updatePaymentChannelId(lnPayOrder.getId(), channelInfo.getId(), Constants.BAOFOO_IS_PROTOCOLPAY_NO);
            	}
            	cutpayment.setMerchantNo(channelInfo.getMerchantNo());
            	cutpayment.setIsMain(channelInfo.getIsMain());
            	
            }
            if(!isAgreementPsy){
            	cutpayment.setTrans_serial_no(Util.generateUserOrderNo4Pay19(lnPayOrder.getLnUserId()));
                cutpayment.setTrans_id(lnPayOrder.getOrderNo());
                //云贷宝付代扣交易金额入参需要转成分，赞分期就是以分为单位，赞分期无需改动
                cutpayment.setTxnAmt(MoneyUtil.multiply(lnPayOrder.getAmount(), 100).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                cutpayment.setAcc_no(lnPayOrder.getBankCardNo());
                cutpayment.setId_card(lnPayOrder.getIdCard());
                cutpayment.setId_holder(lnPayOrder.getUserName());
                cutpayment.setMobile(lnPayOrder.getMobile());
               

                cutpayment.setPay_code(bs19payBank.get(0).getPay19BankCode());
                cutpayment.setAdditional_info(PartnerEnum.getEnumByCode(lnPayOrder.getPartnerCode()).getName() + "用户代扣还款");
                
                B2GResMsg_BaoFooCutpayment_Cutpayment res = null;
                try {
                    res = baoFooTransportService.withholding(cutpayment);
                    dkRes.setPay4OnlineOrderNo(res.getPay4OnlineOrderNo());
                    dkRes.setResCode(res.getResCode());
                    dkRes.setResMsg(res.getResMsg());
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new PTMessageException(PTMessageEnum.CONNECTION_ERROR);
                }
            }
            
            

            LnRepay repayTemp = new LnRepay();
            repayTemp.setUpdateTime(new Date());

            if (!dkRes.getResCode().equals(ConstantUtil.BSRESCODE_SUCCESS)) {

                if (dkRes.getResCode().equals(LoanStatus.BAOFOO_PAY_STATUS_PROCESS.getCode())) {
                    //更新订单表状态
                    payOrdersService.modifyLnOrderStatus4Safe(lnPayOrder.getId(), Integer.parseInt(LoanStatus.ORDERS_STATUS_PAYING.getCode()),
                            null, dkRes.getResCode(), dkRes.getResMsg());

                    //更新还款信息表状态 置为还款中状态（预下单时已置成处理中）
//					for (LnRepay repay : repayList) {
//						repayTemp.setId(repay.getId());
//						repayTemp.setStatus(LoanStatus.REPAY_STATUS_PAYING.getCode());
//						lnRepayMapper.updateByPrimaryKeySelective(repayTemp);
//					}
                    LnPayOrdersJnl lnPayOrdersJnl = new LnPayOrdersJnl();
                    //记录订单流水表
                    lnPayOrdersJnl.setSubAccountId(lnPayOrder.getSubAccountId());
                    lnPayOrdersJnl.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DK.getCode());
                    lnPayOrdersJnl.setCreateTime(new Date());
                    lnPayOrdersJnl.setOrderId(lnPayOrder.getId());
                    lnPayOrdersJnl.setOrderNo(lnPayOrder.getOrderNo());
                    lnPayOrdersJnl.setTransAmount(lnPayOrder.getAmount());
                    lnPayOrdersJnl.setUserId(lnPayOrder.getLnUserId());
                    lnPayOrdersJnl.setSysTime(new Date());
                    lnPayOrdersJnl.setTransCode(Constants.ORDER_TRANS_CODE_PROCESS);
                    lnPayOrdersJnlMapper.insertSelective(lnPayOrdersJnl);

                    //放到redis中
                    LoanNoticeVO vo = new LoanNoticeVO();
                    vo.setPayOrderNo(lnPayOrder.getOrderNo());
                    vo.setChannel(lnPayOrder.getChannel());
                    vo.setChannelTransType(lnPayOrder.getChannelTransType());
                    vo.setTransType(lnPayOrder.getTransType());
                    vo.setStatus(Constants.ORDER_STATUS_PAYING);
                    vo.setAmount(MoneyUtil.defaultRound(lnPayOrder.getAmount()).toString());
                    redisUtil.rpushRedis(vo);

                    //并插入到消息队列表中
                    BsPayResultQueue queue = new BsPayResultQueue();
                    queue.setChannel(Constants.ORDER_CHANNEL_BAOFOO);
                    queue.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DK.getCode());
                    queue.setCreateTime(new Date());
                    queue.setDealNum(0);
                    queue.setOrderNo(lnPayOrder.getOrderNo());
                    queue.setStatus(LoanStatus.NOTICE_STATUS_INIT.getCode());
                    queue.setTransType(lnPayOrder.getTransType());
                    queue.setUpdateTime(new Date());
                    queueMapper.insertSelective(queue);

                } else {
                    //还款失败
                    //更新订单表状态
                    payOrdersService.modifyLnOrderStatus4Safe(lnPayOrder.getId(), Integer.parseInt(LoanStatus.ORDERS_STATUS_PAYING.getCode()),
                            null, dkRes.getResCode(), dkRes.getResMsg());

                    //更新还款信息表状态 置为还款中状态
//					for (LnRepay repay : repayList) {
//						repayTemp.setId(repay.getId());
//						repayTemp.setStatus(LoanStatus.REPAY_STATUS_PAYING.getCode());
//						lnRepayMapper.updateByPrimaryKeySelective(repayTemp);
//					}
                    //还款支付结果处理
                    DepFixedRepayPayResultInfo payResult = new DepFixedRepayPayResultInfo();
                    payResult.setSuccess(false);
                    payResult.setAmount(lnPayOrder.getAmount());
                    payResult.setOrderNo(lnPayOrder.getOrderNo());
                    payResult.setReturnCode(dkRes.getResCode());
                    payResult.setReturnMsg(dkRes.getResMsg());
                    doRepayResultPayProcess(payResult);
                }

            } else {
            	//还款成功
            	if(channelInfo != null && channelInfo.getIsMain() != null && channelInfo.getIsMain() !=1){
            		//使用的是辅助通道
            		if(StringUtil.isBlank(dkRes.getPay4OnlineOrderNo())){
            			//钱包转账支付订单号为空，告警
            			specialJnlService.warn4FailNoSMS(lnPayOrder.getAmount(),"代扣商户间钱包转账失败，代扣订单号："+lnPayOrder.getOrderNo(),lnPayOrder.getOrderNo(),"【代扣商户间转账】");
            		}else{
	            		//钱包转账支付订单号不为空，记钱包转账支付的订单及流水
	            		LnPayOrders lnPayOrders = new LnPayOrders();
	                    lnPayOrders.setCreateTime(new Date());
	                    lnPayOrders.setAccountType(2);//转账算系统，记2
	                    lnPayOrders.setAmount(lnPayOrder.getAmount());
	                    lnPayOrders.setChannel(Constants.CHANNEL_BAOFOO);
	                    lnPayOrders.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_TRANSFER.getCode());
	                    lnPayOrders.setMoneyType(0);
	                    lnPayOrders.setOrderNo(dkRes.getPay4OnlineOrderNo());
	                    lnPayOrders.setPartnerCode(partnerEnum.getCode());
	                    lnPayOrders.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_SUCCESS.getCode()));
	                    lnPayOrders.setSubAccountId(lnPayOrder.getSubAccountId());
	                    lnPayOrders.setTransType(LoanStatus.TRANS_TYPE_REPAY.getCode());
	                    lnPayOrders.setUpdateTime(new Date());
	                    lnPayOrders.setPaymentChannelId(channelInfo.getId());
	                    lnPayOrdersMapper.insertSelective(lnPayOrders);
	                    //记录订单流水表
	            		LnPayOrdersJnl lnPayOrdersJnl = new LnPayOrdersJnl();
	                    lnPayOrdersJnl.setSubAccountId(lnPayOrder.getSubAccountId());
	                    lnPayOrdersJnl.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_TRANSFER.getCode());
	                    lnPayOrdersJnl.setCreateTime(new Date());
	                    lnPayOrdersJnl.setOrderId(lnPayOrders.getId());
	                    lnPayOrdersJnl.setOrderNo(lnPayOrders.getOrderNo());
	                    lnPayOrdersJnl.setTransAmount(lnPayOrders.getAmount());
	                    lnPayOrdersJnl.setUserId(lnPayOrders.getLnUserId());
	                    lnPayOrdersJnl.setSysTime(new Date());
	                    lnPayOrdersJnl.setTransCode(Constants.ORDER_TRANS_CODE_SUCCESS);
	                    lnPayOrdersJnlMapper.insertSelective(lnPayOrdersJnl);
            		}
            	}
               
                //更新订单表状态
                payOrdersService.modifyLnOrderStatus4Safe(lnPayOrder.getId(), Integer.parseInt(LoanStatus.ORDERS_STATUS_PAYING.getCode()),
                        null, dkRes.getResCode(), dkRes.getResMsg());
//				//更新还款信息表状态 置为还款中状态
//				for (LnRepay repay : repayList) {
//					repayTemp.setId(repay.getId());
//					repayTemp.setStatus(LoanStatus.REPAY_STATUS_PAYING.getCode());
//					lnRepayMapper.updateByPrimaryKeySelective(repayTemp);
//				}

                //还款支付结果处理
                DepFixedRepayPayResultInfo payResult = new DepFixedRepayPayResultInfo();
                payResult.setSuccess(true);
                payResult.setAmount(lnPayOrder.getAmount());
                payResult.setOrderNo(lnPayOrder.getOrderNo());
                payResult.setReturnCode(dkRes.getResCode());
                payResult.setReturnMsg(dkRes.getResMsg());
                doRepayResultPayProcess(payResult);
            }
        } finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_REPAY_SELF.getKey() + partnerEnum.getCode() + idCardNo);
            logger.info("=========云贷确认还款，解锁："+ RedisLockEnum.LOCK_REPAY_SELF.getKey() + partnerEnum.getCode() + idCardNo +"=======");
        }
    }

    /**
     * 协议支付风控参数-互金消金参数
     * @param lnUserId
     * @param lnLoan
     * @param lnPayOrder
     * @return
     */
    private RiskItems getRiskItems(Integer lnUserId, LnLoan lnLoan, LnPayOrders lnPayOrder, String payIP) {
    	String dateTimeString = "yyyyMMddHHmmss";
    	RiskItems riskItems = new RiskItems();
    	LnUser lnUser = lnUserMapper.selectByPrimaryKey(lnUserId);
    	riskItems.setUserLoginId(lnUser.getMobile());
    	riskItems.setUserEmail("");
    	riskItems.setUserMobile(lnUser.getMobile());
    	riskItems.setRegisterUserName(lnUser.getUserName());
    	riskItems.setIdentifyState("1");//是否实名认证:1是 0不是
    	riskItems.setUserIdNo(lnUser.getIdCard());
    	riskItems.setRegisterTime(DateUtil.formatDateTime(lnUser.getCreateTime(), dateTimeString));
    	riskItems.setRegisterIp("");
    	riskItems.setChName(lnPayOrder.getUserName());
    	riskItems.setChIdNo(lnPayOrder.getIdCard());
    	riskItems.setChCardNo(lnPayOrder.getBankCardNo());
    	riskItems.setChMobile(lnPayOrder.getMobile());
    	if (StringUtil.isEmpty(payIP)) {
			riskItems.setChPayIp("");
		} else {
			riskItems.setChPayIp(payIP);
		}
    	riskItems.setDeviceOrderNo("");
    	riskItems.setTradeType(Constants.BAOFOO_PROTOCOLPAY_TRANS_TYPE_REPAY);
    	riskItems.setCustomerType(Constants.BAOFOO_PROTOCOLPAY_CUSTOMER_TYPE_LN);
    	riskItems.setHasBalance("");
    	riskItems.setHasBindCard("");
    	riskItems.setRepaymentDate("");
    	riskItems.setLendingRate(lnLoan.getAgreementRate().toString());
    	riskItems.setBidYields("");
    	riskItems.setLatestTradeDate("");
    	
    	//查询该笔借款的最后账单日
    	/*LnRepaySchedule repaySchedule = lnRepayScheduleMapper.lastPlanByLoanId(lnLoan.getId());
    	riskItems.setRepaymentDate(DateUtil.formatYYYYMMDD(repaySchedule.getPlanDate())+" 23:59:59");
    	
    	//查询订单表同银行卡上次协议支付成功时间
    	LnPayOrders payOrders = lnPayOrdersMapper.selectLastByCard(lnPayOrder.getBankCardNo(),lnPayOrder.getId());
    	if(payOrders == null || payOrders.getUpdateTime() == null){
    		riskItems.setLatestTradeDate("0");
    	}else{
    		riskItems.setLatestTradeDate(DateUtil.formatDateTime(payOrders.getUpdateTime(), dateTimeString));
    	}*/
    	
		return riskItems;
	}

	/**
     * 查询BsBgwNuccSignRelation
     * @param lnUserId
     * @param userType
     * @param merchantNo
     * @param bankCardNo
     * @param userName
     * @param idCard
     * @param mobile
     * @return
     */
    private BsBgwNuccSignRelation getSingRelation(Integer lnUserId,
			String userType, String merchantNo, String bankCardNo, String userName,
			String idCard, String mobile) {
    	BsBgwNuccSignRelationExample example = new BsBgwNuccSignRelationExample();
    	example.createCriteria().andUserIdEqualTo(lnUserId).andUserTypeEqualTo(userType).andMerchanntNoEqualTo(merchantNo)
    		.andCardNoEqualTo(bankCardNo).andCardOwnerEqualTo(userName).andIdCardEqualTo(idCard).andMobileEqualTo(mobile)
    		.andStatusEqualTo(1);
    	List<BsBgwNuccSignRelation> list = bsBgwNuccSignRelationMapper.selectByExample(example);
    	if(CollectionUtils.isNotEmpty(list)){
    		return list.get(0);
    	}
		return null;
	}

    @Override
    public void dispatcherRepayApply(final G2BReqMsg_DafyLoan_CutRepayConfirm req) {
        try{
        	ArrayList<HashMap<String, Object>> loans = req.getLoans();

        	if( loans.size() > 0 ) {
        		String loanId = (String) loans.get(0).get("loanId");
                LnLoan lnLoan = lnLoanMapper.selectLoanByPartnerLoanId(loanId);
        		//云贷等本等息/等额本息走新流程
                if( lnLoan.getPartnerBusinessFlag().equals(BusinessTypeEnum.AVERAGE_CAPITAL_PLUS_INTEREST.getCode()) 
                		|| lnLoan.getPartnerBusinessFlag().equals(BusinessTypeEnum.EQUAL_PRINCIPAL_INTEREST.getCode()) ) {
                	G2BResMsg_DafyLoan_CutRepayConfirm res = new G2BResMsg_DafyLoan_CutRepayConfirm();
                	FlowContext flowContext = new FlowContext();
                	flowContext.setReq(req);
                	flowContext.setRes(res);
                	flowContext.setTransCode(TransCodeEnum.APPLY_REPAY.getCode());
                	flowContext.setPartnerEnum(PartnerEnum.YUN_DAI_SELF);
                	flowContext.setBusinessType("");
                	dispatcherService.dispatcherService(flowContext);
                } else {
                	repayApply(req);
                }
        	} else {
        		throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "loans列表未空");
        	}
        } catch(Exception e) {
        	logger.info("代扣还款申请异常信息:"+e.getMessage());
        } finally {

        }
    }

	/**
     * 前置校验，入还款队列
     * @param req
     */
    @Override
    public void repayApply(final G2BReqMsg_DafyLoan_CutRepayConfirm req) {
        try{
            //订单是否重复
            LnRepayExample repayExample = new LnRepayExample();
            repayExample.createCriteria().andPartnerOrderNoEqualTo(req.getOrderNo());
            List<LnRepay> repayList = lnRepayMapper.selectByExample(repayExample);
            if (CollectionUtils.isNotEmpty(repayList) && repayList.size() > 0) {
                throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "订单号重复");
            }
            if (CollectionUtils.isNotEmpty(req.getLoans())) {
                //借款用户是否存在
                LnUserExample lnUserExample = new LnUserExample();
                lnUserExample.createCriteria().andPartnerCodeEqualTo(PartnerEnum.YUN_DAI_SELF.getCode())
                        .andPartnerUserIdEqualTo(req.getUserId());
                List<LnUser> lnUsers = lnUserMapper.selectByExample(lnUserExample);
                if (CollectionUtils.isEmpty(lnUsers)) {
                    throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND, "找不到借款用户信息");
                }
                //该银行是否支持代扣
                List<Pay19BankVO> pay19BankVOs = bs19payBankMapper.selectByChanelPayTypeBankCode(Constants.CHANNEL_BAOFOO, Constants.PAY_TYPE_HOLD, req.getBankCode());
                if (CollectionUtils.isEmpty(pay19BankVOs)) {
                    throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND, "银行编码[" + req.getBankCode() + "]不存在或该银行不支持代扣");
                }
                final LnUser lnUser = lnUsers.get(0);
                final String orderNo = Util.generateOrderNo4BaoFoo(8);
                final String bgwOrderNo = Util.generateSysOrderNo(Constants.BGW_ORDER_NUMBER_GENERATE_WITHHOLD);
                transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                    @Override
                    protected void doInTransactionWithoutResult(TransactionStatus status) {
                        ArrayList<HashMap<String, Object>> loans = req.getLoans();
                        for (HashMap<String, Object> loan : loans) {
                            String loanId = (String) loan.get("loanId");
                            ArrayList<HashMap<String, Object>> repayments = (ArrayList<HashMap<String, Object>>) loan.get("repayments");
                            //每笔借款是否存在
                            LnLoanExample loanExample = new LnLoanExample();
                            loanExample.createCriteria().andPartnerLoanIdEqualTo(loanId)
                                    .andLnUserIdEqualTo(lnUser.getId()).andStatusEqualTo(LoanStatus.LOAN_STATUS_PAIED.getCode());
                            List<LnLoan> loanList = lnLoanMapper.selectByExample(loanExample);
                            if (CollectionUtils.isEmpty(loanList)) {
                                throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND, "找不到借款[" + loanId + "]信息");
                            }
                            final LnLoan lnLoan = loanList.get(0);
                            //该笔借款是否有还款正在进行中
                            if (lnLoan.getIsRepaying().equals(Constants.DEP_REPAY_REPAYING)) {
                                throw new PTMessageException(PTMessageEnum.DEP_REPAY_REPAYING);
                            }

                            if (CollectionUtils.isEmpty(repayments)) {
                                throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND, "代扣还款云贷传入账单为空");
                            }
                            for (HashMap<String, Object> repay : repayments) {
                                //是否已还款/是否需要账单同步
                                String repayId = (String) repay.get("repayId");
                                Double total = (Double) repay.get("total");
                                Double principal = (Double) repay.get("principal");
                                Double interest = (Double) repay.get("interest");
                                Double principalOverdue = (Double) repay.get("principalOverdue");
                                Double interestOverdue = (Double) repay.get("interestOverdue");
                                //预留字段
                                //String reservedField1 = (String) repay.get("reservedField1");

                                LnRepayScheduleExample lnRepayScheduleExample = new LnRepayScheduleExample();
                                lnRepayScheduleExample.createCriteria().andPartnerRepayIdEqualTo(repayId).andLoanIdEqualTo(lnLoan.getId());
                                List<LnRepaySchedule> lnRepaySchedules = lnRepayScheduleMapper.selectByExample(lnRepayScheduleExample);

                                //记录还款信息表和还款信息明细表
                                LnRepay lnRepay = new LnRepay();
                                if (CollectionUtils.isNotEmpty(lnRepaySchedules)) {
                                    LnRepaySchedule lnRepaySchedule = lnRepaySchedules.get(0);
                                    if (lnRepaySchedule.getReturnFlag().equals(Constants.LN_REPAY_RETURN_FLAG_PENDING) ||
                                            lnRepaySchedule.getReturnFlag().equals(Constants.LN_REPAY_RETURN_FLAG_PROCESS)) {
                                        throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "账单[" + lnRepaySchedule.getPartnerRepayId() + "]正在处理中");
                                    }
                                    lnRepay.setRepayPlanId(lnRepaySchedule.getId());
                                    String scheduleStatus = lnRepaySchedule.getStatus();
                                    if (LoanStatus.REPAY_SCHEDULE_STATUS_REPAIED.getCode().equals(scheduleStatus) ||
                                            LoanStatus.REPAY_SCHEDULE_STATUS_CANCELLED.getCode().equals(scheduleStatus) ||
                                            LoanStatus.REPAY_SCHEDULE_STATUS_LATE_REPAIED.getCode().equals(scheduleStatus)) {
                                        throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "账单[" + repayId + "]已存在还款或已废除");
                                    }

                                    //是否已存在重复还款记录
                                    repeatRepayJudge(lnRepaySchedule.getId(), repayId);
                                }
                                lnRepay.setStatus(LoanStatus.REPAY_STATUS_PAYING.getCode());
                                lnRepay.setUpdateTime(new Date());
                                lnRepay.setBgwOrderNo(bgwOrderNo);
                                lnRepay.setCreateTime(new Date());
                                lnRepay.setDoneTotal(total);
                                lnRepay.setLnUserId(lnUser.getId());
                                lnRepay.setPayOrderNo(orderNo);
                                lnRepay.setLoanId(lnLoan.getId());
                                lnRepay.setPartnerOrderNo(req.getOrderNo());
                                lnRepay.setInformStatus(LoanStatus.NOTICE_STATUS_INIT.getCode());
                                lnRepayMapper.insertSelective(lnRepay);

                                LnRepayDetail lnRepayDetail = new LnRepayDetail();
                                lnRepayDetail.setUpdateTime(new Date());
                                lnRepayDetail.setCreateTime(new Date());
                                lnRepayDetail.setRepayId(lnRepay.getId());
                                lnRepayDetail.setDoneAmount(principal != null ? principal : 0d);
                                lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_PRINCIPAL.getCode());
                                lnRepayDetailMapper.insertSelective(lnRepayDetail);

                                lnRepayDetail.setDoneAmount(interest != null ? interest : 0d);
                                lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_INTEREST.getCode());
                                lnRepayDetailMapper.insertSelective(lnRepayDetail);

                                lnRepayDetail.setDoneAmount(principalOverdue != null ? principalOverdue : 0d);
                                lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_PRINCIPAL_OVERDUE.getCode());
                                lnRepayDetailMapper.insertSelective(lnRepayDetail);

                                lnRepayDetail.setDoneAmount(interestOverdue != null ? interestOverdue : 0d);
                                lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_INTEREST_OVERDUE.getCode());
                                lnRepayDetailMapper.insertSelective(lnRepayDetail);
                            }

                        }
                    }
                });
                //记录ln_pay_orders,ln_pay_orders_jnl表
                LnPayOrders lnPayOrders = new LnPayOrders();
                lnPayOrders.setCreateTime(new Date());
                lnPayOrders.setAccountType(1);
                lnPayOrders.setAmount(req.getTotalAmount());
                lnPayOrders.setBankCardNo(req.getBankCard());
                lnPayOrders.setBankId(pay19BankVOs.get(0).getBankId());
                lnPayOrders.setBankName(pay19BankVOs.get(0).getBankName());
                lnPayOrders.setChannel(Constants.CHANNEL_BAOFOO);
                lnPayOrders.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DK.getCode());
                lnPayOrders.setIdCard(req.getIdCard());
                lnPayOrders.setLnUserId(lnUser.getId());
                lnPayOrders.setMobile(req.getMobile());
                lnPayOrders.setMoneyType(0);
                lnPayOrders.setOrderNo(orderNo);
                lnPayOrders.setPartnerCode(PartnerEnum.YUN_DAI_SELF.getCode());
                lnPayOrders.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_INIT.getCode()));
                lnPayOrders.setSubAccountId(null);
                lnPayOrders.setTransType(LoanStatus.TRANS_TYPE_REPAY.getCode());
                lnPayOrders.setUpdateTime(new Date());
                lnPayOrders.setUserName(req.getName());
                lnPayOrdersMapper.insertSelective(lnPayOrders);

                LnPayOrdersJnl lnPayOrdersJnl = new LnPayOrdersJnl();
                lnPayOrdersJnl.setSubAccountId(null);
                lnPayOrdersJnl.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DK.getCode());
                lnPayOrdersJnl.setCreateTime(new Date());
                lnPayOrdersJnl.setOrderId(lnPayOrders.getId());
                lnPayOrdersJnl.setOrderNo(lnPayOrders.getOrderNo());
                lnPayOrdersJnl.setTransAmount(lnPayOrders.getAmount());
                lnPayOrdersJnl.setTransCode(LoanStatus.ORDERS_JNL_STATUS_INIT.getCode());
                lnPayOrdersJnl.setUserId(lnUser.getId());
                lnPayOrdersJnl.setSysTime(new Date());
                lnPayOrdersJnlMapper.insertSelective(lnPayOrdersJnl);

                //入还款队列
                LnBindCard lnBindCard = new LnBindCard();
                lnBindCard.setLnUserId(lnUser.getId());
                lnBindCard.setBankCard(req.getBankCard());
                lnBindCard.setIdCard(req.getIdCard());
                lnBindCard.setUserName(req.getName());
                lnBindCard.setBankCode(req.getBankCode());
                lnBindCard.setMobile(req.getMobile());

                //入redis
                try {
                    RepayQueueDTO repayQueueDTO = new RepayQueueDTO();
                    repayQueueDTO.setLnBindCard(lnBindCard);
                    repayQueueDTO.setLnPayOrder(lnPayOrders);
                    repayQueueDTO.setChannel(PartnerEnum.YUN_DAI_SELF.getCode());
                    if (!StringUtil.isEmpty(req.getUserSignNo())) {
                    	repayQueueDTO.setUserSignNo(req.getUserSignNo());
					}
                    if (!StringUtil.isEmpty(req.getPayIP())) {
                    	repayQueueDTO.setPayIP(req.getPayIP());
					}
                    jsClientDaoSupport.rpush("repay_queue", JSON.toJSONString(repayQueueDTO));
                    logger.info(">>>入还款队列数据:" + JSON.toJSONString(repayQueueDTO) + "<<<");
                }catch (Exception e){
                    logger.error("入还款队列异常", e);
                }
            }
        }finally{

        }
    }

    /**
     * 发起代扣还款请求,结果处理
     * @param lnPayOrder
     * @param lnBindCard
     */
    @Override
    public void withholdingRepayAsync(LnPayOrders lnPayOrder, LnBindCard lnBindCard, String userSignNo, String payIP)  {
        try {
            jsClientDaoSupport.lock(RedisLockEnum.LOCK_REPAY_SELF.getKey()+ lnPayOrder.getPartnerCode() +lnBindCard.getIdCard());
            logger.info("=========代扣还款，结果处理，加锁："+RedisLockEnum.LOCK_REPAY_SELF.getKey()+ lnPayOrder.getPartnerCode() +lnBindCard.getIdCard()+"=======");
            LnRepayExample lnRepayExample = new LnRepayExample();
            lnRepayExample.createCriteria().andPayOrderNoEqualTo(lnPayOrder.getOrderNo());
            List<LnRepay> repayList = lnRepayMapper.selectByExample(lnRepayExample);
            // 前置校验
            if (CollectionUtils.isNotEmpty(repayList)) {
                LnPayOrders newestOrder = lnPayOrdersMapper.selectByPrimaryKey(lnPayOrder.getId());
                if(null == newestOrder){
                    throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "无订单记录");
                }
                if(newestOrder.getStatus() == Integer.parseInt(LoanStatus.ORDERS_STATUS_SUCCESS.getCode()) ||
                        newestOrder.getStatus() == Integer.parseInt(LoanStatus.ORDERS_STATUS_PAYING.getCode())){
                    //订单已成功或处理中时
                    throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "订单"+newestOrder.getOrderNo()+"正在处理中或者已经成功");
                } else if(newestOrder.getStatus() == Integer.parseInt(LoanStatus.ORDERS_STATUS_FAIL.getCode()) ||
                        newestOrder.getStatus() == Integer.parseInt(LoanStatus.ORDERS_STATUS_PRE_FAIL.getCode())) {
                    //订单非成功非处理中时，返回错误信息
                    String errorMsg = newestOrder.getReturnMsg();
                    throw new PTMessageException(PTMessageEnum.TRANS_ERROR, "：" + errorMsg);
                }
            } else {
                throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "无还款记录");
            }
            
            BaoFooDKRes dkRes = new BaoFooDKRes();
            //宝付协议支付
            B2GReqMsg_BaoFooAgreementPay_DirectAgreementPay agreementPayReq = new B2GReqMsg_BaoFooAgreementPay_DirectAgreementPay();
            //宝付代扣
            B2GReqMsg_BaoFooCutpayment_Cutpayment cutpayment = new B2GReqMsg_BaoFooCutpayment_Cutpayment();
            boolean isAgreementPsy = false;//是否为协议支付标识
            //查询支付渠道代扣优先商户号信息
            BsPaymentChannel channelInfo = paymentChannelMapper.selectFisrtDK();
            if(channelInfo != null){
            	if (!StringUtil.isEmpty(userSignNo) && !StringUtil.isEmpty(payIP)) {
            		isAgreementPsy = true;
            		//修改订单表payment_channel_id，是否为协议支付（isProtocolPay）
                	payOrdersService.updatePaymentChannelId(lnPayOrder.getId(), channelInfo.getId(), Constants.BAOFOO_IS_PROTOCOLPAY_YES);
            		//已签约，发签约代扣
                	agreementPayReq.setIsMain(channelInfo.getIsMain());
            		agreementPayReq.setMember_id(channelInfo.getMerchantNo());
            		agreementPayReq.setSend_time(lnPayOrder.getCreateTime());
            		agreementPayReq.setTrans_id(lnPayOrder.getOrderNo());
            		//user_type 和user_id 不从nucc 表中取
            		//七贷不传user_id，云贷传币港湾存储的云贷user_id
            		if (Constants.PROPERTY_SYMBOL_YUN_DAI_SELF.equals(lnPayOrder.getPartnerCode())) {
						//查询云贷提供的用户编号
            			LnUser user = lnUserMapper.selectByPrimaryKey(lnPayOrder.getLnUserId());
            			agreementPayReq.setUser_id(user.getPartnerUserId());
					} else if(Constants.PROPERTY_SYMBOL_7_DAI_SELF.equals(lnPayOrder.getPartnerCode())) {
						agreementPayReq.setUser_id("");
					}
					agreementPayReq.setProtocol_no(userSignNo);
            		//云贷宝付代扣交易金额入参需要转成分，赞分期就是以分为单位，赞分期无需改动
            		agreementPayReq.setTxn_amt(MoneyUtil.multiply(lnPayOrder.getAmount(), 100).longValue());
            		B2GResMsg_BaoFooAgreementPay_DirectAgreementPay agreementPayRes = null;
            		try {
            			LnLoan lnLoan = lnLoanMapper.selectByPrimaryKey(repayList.get(0).getLoanId());
            			RiskItems riskItems = getRiskItems(repayList.get(0).getLnUserId(),lnLoan,lnPayOrder,payIP);
            			agreementPayReq.setRiskItems(riskItems);
            			logger.info("协议支付入参："+agreementPayReq.toString());
            			agreementPayRes = baoFooTransportService.directAgreementPay(agreementPayReq);;
            			dkRes.setPay4OnlineOrderNo(agreementPayRes.getPay4OnlineOrderNo());
                        dkRes.setResCode(agreementPayRes.getResCode());
                        dkRes.setResMsg(agreementPayRes.getResMsg());
					} catch (Exception e) {
						 e.printStackTrace();
						 dkRes.setResCode(LoanStatus.BAOFOO_PAY_STATUS_PROCESS.getCode());
						 dkRes.setResMsg("通讯失败，置为处理中");
					}
				} else {
					BsBgwNuccSignRelation signRelation = getSingRelation(repayList.get(0).getLnUserId(),lnPayOrder.getPartnerCode(),
	            			channelInfo.getMerchantNo(),lnPayOrder.getBankCardNo(),lnPayOrder.getUserName(),
	            			lnPayOrder.getIdCard(),lnPayOrder.getMobile());
					if (signRelation != null && StringUtil.isNotBlank(signRelation.getProtocolNo())){
						isAgreementPsy = true;
	            		//修改订单表payment_channel_id，是否为协议支付（isProtocolPay）
	                	payOrdersService.updatePaymentChannelId(lnPayOrder.getId(), channelInfo.getId(), Constants.BAOFOO_IS_PROTOCOLPAY_YES);
	            		//已签约，发签约代扣
	                	agreementPayReq.setIsMain(channelInfo.getIsMain());
	            		agreementPayReq.setMember_id(channelInfo.getMerchantNo());
	            		agreementPayReq.setSend_time(lnPayOrder.getCreateTime());
	            		agreementPayReq.setTrans_id(lnPayOrder.getOrderNo());
	            		agreementPayReq.setUser_id(signRelation.getUserId()+"");
	            		agreementPayReq.setProtocol_no(signRelation.getProtocolNo());
	            		//云贷宝付代扣交易金额入参需要转成分，赞分期就是以分为单位，赞分期无需改动
	            		agreementPayReq.setTxn_amt(MoneyUtil.multiply(lnPayOrder.getAmount(), 100).longValue());
	            		B2GResMsg_BaoFooAgreementPay_DirectAgreementPay agreementPayRes = null;
	            		try {
	            			LnLoan lnLoan = lnLoanMapper.selectByPrimaryKey(repayList.get(0).getLoanId());
	            			RiskItems riskItems = getRiskItems(repayList.get(0).getLnUserId(),lnLoan,lnPayOrder,"");
	            			agreementPayReq.setRiskItems(riskItems);
	            			logger.info("协议支付入参："+agreementPayReq.toString());
	            			agreementPayRes = baoFooTransportService.directAgreementPay(agreementPayReq);;
	            			dkRes.setPay4OnlineOrderNo(agreementPayRes.getPay4OnlineOrderNo());
	                        dkRes.setResCode(agreementPayRes.getResCode());
	                        dkRes.setResMsg(agreementPayRes.getResMsg());
						} catch (Exception e) {
							 e.printStackTrace();
							 dkRes.setResCode(LoanStatus.BAOFOO_PAY_STATUS_PROCESS.getCode());
							 dkRes.setResMsg("通讯失败，置为处理中");
						}
					} else{
	            		//修改订单表payment_channel_id，是否为协议支付（isProtocolPay）
	                	payOrdersService.updatePaymentChannelId(lnPayOrder.getId(), channelInfo.getId(), Constants.BAOFOO_IS_PROTOCOLPAY_NO);
	            	}
					
				} 
            	cutpayment.setMerchantNo(channelInfo.getMerchantNo());
            	cutpayment.setIsMain(channelInfo.getIsMain());
            }
            if(!isAgreementPsy){
            	cutpayment.setTrans_serial_no(Util.generateUserOrderNo4Pay19(lnBindCard.getLnUserId()));
                cutpayment.setTrans_id(lnPayOrder.getOrderNo());
                //云贷宝付代扣交易金额入参需要转成分
                String partnerName = PartnerEnum.getEnumByCode(lnPayOrder.getPartnerCode()) == null ? "" :
                		PartnerEnum.getEnumByCode(lnPayOrder.getPartnerCode()).getName();
                cutpayment.setTxnAmt(MoneyUtil.multiply(lnPayOrder.getAmount(), 100).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                cutpayment.setAcc_no(lnBindCard.getBankCard());
                cutpayment.setId_card(lnBindCard.getIdCard());
                cutpayment.setId_holder(lnBindCard.getUserName());
                cutpayment.setMobile(lnBindCard.getMobile());
                cutpayment.setPay_code(lnBindCard.getBankCode());
                cutpayment.setAdditional_info(partnerName + "用户代扣还款");
                
                B2GResMsg_BaoFooCutpayment_Cutpayment res = null;
                try {
                    res = baoFooTransportService.withholding(cutpayment);
                    dkRes.setPay4OnlineOrderNo(res.getPay4OnlineOrderNo());
                    dkRes.setResCode(res.getResCode());
                    dkRes.setResMsg(res.getResMsg());
                } catch (Exception e) {
                    e.printStackTrace();
                    dkRes.setResCode(LoanStatus.BAOFOO_PAY_STATUS_PROCESS.getCode());
                    dkRes.setResMsg("通讯失败，置为处理中");
                }
            }
            

            if (!dkRes.getResCode().equals(ConstantUtil.BSRESCODE_SUCCESS)) {

                if (dkRes.getResCode().equals(LoanStatus.BAOFOO_PAY_STATUS_PROCESS.getCode())) {

                    //更新订单表为处理中
                    payOrdersService.modifyLnOrderStatus4Safe(lnPayOrder.getId(), Integer.parseInt(LoanStatus.ORDERS_STATUS_PAYING.getCode()),
                            null, dkRes.getResCode(), dkRes.getResMsg());

                    //还款处理中，放到redis中
                    LoanNoticeVO vo = new LoanNoticeVO();
                    vo.setPayOrderNo(lnPayOrder.getOrderNo());
                    vo.setChannel(lnPayOrder.getChannel());
                    vo.setChannelTransType(lnPayOrder.getChannelTransType());
                    vo.setTransType(lnPayOrder.getTransType());
                    vo.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_PAYING.getCode()));
                    vo.setAmount(MoneyUtil.defaultRound(lnPayOrder.getAmount()).toString());
                    redisUtil.rpushRedis(vo);

                    //并插入到消息队列表中
                    BsPayResultQueue queue = new BsPayResultQueue();
                    queue.setChannel(Constants.ORDER_CHANNEL_BAOFOO);
                    queue.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DK.getCode());
                    queue.setCreateTime(new Date());
                    queue.setDealNum(0);
                    queue.setOrderNo(lnPayOrder.getOrderNo());
                    queue.setStatus(LoanStatus.NOTICE_STATUS_INIT.getCode());
                    queue.setTransType(lnPayOrder.getTransType());
                    queue.setUpdateTime(new Date());
                    queueMapper.insertSelective(queue);

                } else {
                    //更	新订单表为处理中
                    payOrdersService.modifyLnOrderStatus4Safe(lnPayOrder.getId(), Integer.parseInt(LoanStatus.ORDERS_STATUS_PAYING.getCode()),
                            null, dkRes.getResCode(), dkRes.getResMsg());

                    //还款失败，还款支付结果处理
                    DepFixedRepayPayResultInfo payResult = new DepFixedRepayPayResultInfo();
                    payResult.setSuccess(false);
                    payResult.setAmount(lnPayOrder.getAmount());
                    payResult.setOrderNo(lnPayOrder.getOrderNo());
                    payResult.setReturnCode(dkRes.getResCode());
                    payResult.setReturnMsg(dkRes.getResMsg());
                    doRepayResultPayProcess(payResult);
                }

            } else {
            	if(channelInfo != null && channelInfo.getIsMain() != null && channelInfo.getIsMain() !=1 ){
            		//使用的是辅助通道
            		if(StringUtil.isBlank(dkRes.getPay4OnlineOrderNo())){
            			//钱包转账支付订单号为空，告警
            			specialJnlService.warn4FailNoSMS(lnPayOrder.getAmount(),"代扣商户间钱包转账失败，代扣订单号："+lnPayOrder.getOrderNo(),lnPayOrder.getOrderNo(),"【代扣商户间转账】");
            		}else{
	            		//钱包转账支付订单号不为空，记钱包转账支付的订单及流水
	            		LnPayOrders lnPayOrders = new LnPayOrders();
	                    lnPayOrders.setCreateTime(new Date());
	                    lnPayOrders.setAccountType(2);//转账算系统，记2
	                    lnPayOrders.setAmount(lnPayOrder.getAmount());
	                    lnPayOrders.setChannel(Constants.CHANNEL_BAOFOO);
	                    lnPayOrders.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_TRANSFER.getCode());
	                    lnPayOrders.setMoneyType(0);
	                    lnPayOrders.setOrderNo(dkRes.getPay4OnlineOrderNo());
	                    lnPayOrders.setPartnerCode(lnPayOrder.getPartnerCode());
	                    lnPayOrders.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_SUCCESS.getCode()));
	                    lnPayOrders.setSubAccountId(lnPayOrder.getSubAccountId());
	                    lnPayOrders.setTransType(LoanStatus.TRANS_TYPE_REPAY.getCode());
	                    lnPayOrders.setUpdateTime(new Date());
	                    lnPayOrders.setPaymentChannelId(channelInfo.getId());
	                    lnPayOrdersMapper.insertSelective(lnPayOrders);
	                    //记录订单流水表
	            		LnPayOrdersJnl lnPayOrdersJnl = new LnPayOrdersJnl();
	                    lnPayOrdersJnl.setSubAccountId(lnPayOrder.getSubAccountId());
	                    lnPayOrdersJnl.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_TRANSFER.getCode());
	                    lnPayOrdersJnl.setCreateTime(new Date());
	                    lnPayOrdersJnl.setOrderId(lnPayOrders.getId());
	                    lnPayOrdersJnl.setOrderNo(lnPayOrders.getOrderNo());
	                    lnPayOrdersJnl.setTransAmount(lnPayOrders.getAmount());
	                    lnPayOrdersJnl.setTransCode(Constants.ORDER_TRANS_CODE_SUCCESS);
	                    lnPayOrdersJnl.setUserId(lnPayOrders.getLnUserId());
	                    lnPayOrdersJnl.setSysTime(new Date());
	                    lnPayOrdersJnlMapper.insertSelective(lnPayOrdersJnl);
            		}
            	}
                //更新订单表为处理中
                payOrdersService.modifyLnOrderStatus4Safe(lnPayOrder.getId(), Integer.parseInt(LoanStatus.ORDERS_STATUS_PAYING.getCode()),
                        null, dkRes.getResCode(), dkRes.getResMsg());

                //还款成功,还款支付结果处理
                DepFixedRepayPayResultInfo payResult = new DepFixedRepayPayResultInfo();
                payResult.setSuccess(true);
                payResult.setAmount(lnPayOrder.getAmount());
                payResult.setOrderNo(lnPayOrder.getOrderNo());
                payResult.setReturnCode(dkRes.getResCode());
                payResult.setReturnMsg(dkRes.getResMsg());
                doRepayResultPayProcess(payResult);
            }
        }finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_REPAY_SELF.getKey()+ lnPayOrder.getPartnerCode() +lnBindCard.getIdCard());
            logger.info("=========代扣还款，结果处理，解锁："+RedisLockEnum.LOCK_REPAY_SELF.getKey()+ lnPayOrder.getPartnerCode() +lnBindCard.getIdCard()+"=======");
        }
    }

    /**
     * 修改还款信息表状态
     *
     * @param repayIds
     * @param status
     */
    private void modifyRepayStatus(List<Integer> repayIds, String status) {

        LnRepay repayTemp = new LnRepay();
        repayTemp.setStatus(status);

        for (Integer id : repayIds) {
            repayTemp.setId(id);
            repayTemp.setUpdateTime(new Date());
            lnRepayMapper.updateByPrimaryKeySelective(repayTemp);
        }
    }

    private Map<String, Object> checkDoRepayResultPayProcess(DepFixedRepayPayResultInfo payResult) {
        Map<String, Object> result = new HashMap<>();
        // 根据我方还款订单号获取订单信息
        LnPayOrdersExample exampleOrder = new LnPayOrdersExample();
        exampleOrder.createCriteria().andOrderNoEqualTo(payResult.getOrderNo());
        List<LnPayOrders> lnPayOrders = lnPayOrdersMapper.selectByExample(exampleOrder);
        if(CollectionUtils.isEmpty(lnPayOrders)){
            throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND, "找不到orderNo=" + payResult.getOrderNo() + "对应的订单信息");
        }
        LnPayOrders lnPayOrder = lnPayOrdersMapper.selectByPKForLock(lnPayOrders.get(0).getId());
        //根据我方订单号获取还款信息列表
        LnRepayExample exampleRepay = new LnRepayExample();
        exampleRepay.createCriteria().andPayOrderNoEqualTo(payResult.getOrderNo());
        List<LnRepay> lnRepays = lnRepayMapper.selectByExample(exampleRepay);
        if(CollectionUtils.isEmpty(lnRepays)){
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR,"找不到orderNo=" + payResult.getOrderNo() + "对应的还款信息");
        }
        //判断订单是否处理中
        if(lnPayOrder.getStatus() != Constants.ORDER_STATUS_PAYING){
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR,"还款结果支付业务处理：未找到进行中的订单");
        }

        result.put("lnPayOrder", lnPayOrder);
        result.put("lnRepays", lnRepays);
        return result;
    }

    /**
     * 还款结果支付业务处理（主动还款、代扣还款）
     */
    @Override
    public void doRepayResultPayProcess(final DepFixedRepayPayResultInfo payResult) {
        /**
         * ln_pay_orders状态更新FAIL/SUCC，ln_pay_orders_jnl插入记录；（非代偿）
         * ln_repay更新为REPAY_FAIL/REPAIED；（非代偿）
         * Ln_repay_schedule如果无账单编号，则对应借款编号下的所有未完成（非LATE_NOT、REPAIED、CANCELLED）账单
         * ，统一加还款处理中标识；如果有对应账单编号，则只针对该账单加还款处理中标识；（非代偿SUCC）
         * Redis-list增加上述待处理账单，等待“B还款处理轮询定时”轮询
         * 通知云贷还款结果/FAIL/SUCC（非代偿）
         */
        logger.info(">>>进入还款结果支付业务处理开始<<<");
        Map<String, Object> result = checkDoRepayResultPayProcess(payResult);
        final LnPayOrders lnPayOrder = (LnPayOrders) result.get("lnPayOrder");
        final List<LnRepay> lnRepays = (List<LnRepay>) result.get("lnRepays");
        try {
            transactionTemplate.execute(new TransactionCallbackWithoutResult(){
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {
                    //更新ln_pay_orders表,记录ln_pay_orders_jnl表
                    LnPayOrdersJnl lnPayOrdersJnl = new LnPayOrdersJnl();
                    //修改ln_pay_orders状态
                    LnPayOrders payOrdersTemp = new LnPayOrders();
                    payOrdersTemp.setId(lnPayOrder.getId());
                    payOrdersTemp.setUpdateTime(new Date());
                    if(!payResult.isSuccess()){
                        payOrdersTemp.setReturnCode(payResult.getReturnCode());
                        payOrdersTemp.setReturnMsg(payResult.getReturnMsg());
                        payOrdersTemp.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_FAIL.getCode()));
                        lnPayOrder.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_FAIL.getCode()));
                        lnPayOrdersJnl.setTransCode(LoanStatus.ORDERS_JNL_STATUS_FAIL.getCode());
                    }else {
                        payOrdersTemp.setReturnCode(ConstantUtil.BSRESCODE_SUCCESS);
                        payOrdersTemp.setReturnMsg(ConstantUtil.DEFAULT_SUCESSMSG);
                        payOrdersTemp.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_SUCCESS.getCode()));
                        lnPayOrder.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_SUCCESS.getCode()));
                        lnPayOrdersJnl.setTransCode(LoanStatus.ORDERS_JNL_STATUS_SUCCESS.getCode());
                    }
                    lnPayOrdersMapper.updateByPrimaryKeySelective(payOrdersTemp);
                    lnPayOrdersJnl.setSubAccountId(lnPayOrder.getSubAccountId());
                    lnPayOrdersJnl.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DK.getCode());
                    lnPayOrdersJnl.setCreateTime(new Date());
                    lnPayOrdersJnl.setOrderId(lnPayOrder.getId());
                    lnPayOrdersJnl.setOrderNo(lnPayOrder.getOrderNo());
                    lnPayOrdersJnl.setTransAmount(lnPayOrder.getAmount());
                    lnPayOrdersJnl.setUserId(lnPayOrder.getLnUserId());
                    lnPayOrdersJnl.setSysTime(new Date());
                    lnPayOrdersJnlMapper.insertSelective(lnPayOrdersJnl);

                    //ln_repay更新
                    for(LnRepay repay :lnRepays){
                        LnRepay lnRepayTemp = new LnRepay();
                        lnRepayTemp.setId(repay.getId());
                        if(!payResult.isSuccess()){
                            lnRepayTemp.setStatus(Constants.LN_REPAY_PAY_STATUS_REPAY_FAIL);
                        }else{
                            lnRepayTemp.setStatus(Constants.LN_REPAY_PAY_STATUS_REPAIED);
                            lnRepayTemp.setDoneTime(new Date());
                        }
                        lnRepayTemp.setUpdateTime(new Date());
                        lnRepayMapper.updateByPrimaryKeySelective(lnRepayTemp);
                    }
                    if(Constants.PROPERTY_SYMBOL_YUN_DAI_SELF.equals(lnPayOrder.getPartnerCode())) {
                        if(payResult.isSuccess()){
                            /**
                             * 还款成功时
                             * 记录手续费,Ln_repay_schedule如果无账单编号，则对应借款编号下的所有未完成（非LATE_NOT、REPAIED、CANCELLED）账单，
                             * 统一加还款处理中标识；如果有对应账单编号，则只针对该账单加还款处理中标识；
                             * Redis-list增加上述待处理账单，等待“B还款处理轮询定时”轮询
                             */
                            //手续费金额查询
                            CommissionVO commission = commissionService.calServiceFee(payResult.getAmount(), TransTypeEnum.YUN_DAI_SELF_DK, PayPlatformEnum.BAOFOO);
                            Double actFee = commission.getActPayAmount();
                            Double needFee = commission.getNeedPayAmount();

                            //记录手续费
                            BsServiceFee bsServiceFee = new BsServiceFee();
                            bsServiceFee.setPlanFee(needFee);
                            bsServiceFee.setDoneFee(actFee);
                            bsServiceFee.setTransAmount(payResult.getAmount());
                            bsServiceFee.setFeeType(Constants.FEE_TYPE_REPAY);
                            bsServiceFee.setStatus(Constants.FEE_STATUS_COLLECT);
                            bsServiceFee.setCreateTime(new Date());
                            bsServiceFee.setOrderNo(lnPayOrder.getOrderNo());
                            bsServiceFee.setSubAccountId(lnPayOrder.getSubAccountId());
                            bsServiceFee.setUpdateTime(new Date());
                            bsServiceFee.setNote("应扣" + needFee + "，实扣" + actFee);
                            bsServiceFee.setPaymentPlatformFee(commission.getThreePartyPaymentServiceFee());
                            serviceFeeMapper.insertSelective(bsServiceFee);

                            //账单匹配成功的账单编号集合
                            List<Integer> repayPlanIds = new ArrayList<Integer>();
                            //账单匹配不成功的借款编号集合
                            List<Integer> notMatchLoanIds = new ArrayList<Integer>();
                            final List<Integer> allLoanIds = lnRepayMapper.selectDistinctLoanIds(lnPayOrder.getOrderNo());
                            for(LnRepay repay :lnRepays){
                                if(repay.getRepayPlanId() == null){
                                    //提前还款
                                    if(CollectionUtils.isEmpty(notMatchLoanIds)){
                                        notMatchLoanIds.add(repay.getLoanId());
                                    }else{
                                        if(!notMatchLoanIds.contains(repay.getLoanId())){
                                            notMatchLoanIds.add(repay.getLoanId());
                                        }
                                    }
                                }else{
                                    //正常还款
                                    repayPlanIds.add(repay.getRepayPlanId());
                                }
                            }
                            //统一加还款处理中标识
                            Map<String,Object> params = new HashMap<String,Object>();
                            params.put("loanIds", notMatchLoanIds);
                            params.put("repayPlanIds", repayPlanIds);
//							params.put("returnFlag", Constants.LN_REPAY_RETURN_FLAG_PENDING);
                            final LnUser lnUser = lnUserMapper.selectByPrimaryKey(lnPayOrder.getLnUserId());
                            if(CollectionUtils.isEmpty(notMatchLoanIds) && CollectionUtils.isEmpty(repayPlanIds)){
                                //放到redis中
                                pushRedis(allLoanIds, lnPayOrder, lnUser);
                            }else{
//								lnRepayScheduleMapper.changeRepayScheFlagByIds(params);
                                //获取全部还款处理中账单
                                List<RepayBillVO> repayBillVOList = lnRepayScheduleMapper.selectRepayBill(params);
                                if(CollectionUtils.isEmpty(repayBillVOList)){
                                    //放到redis中
                                    pushRedis(allLoanIds, lnPayOrder, lnUser);
                                    //通知云贷还款结果
                                    notifyPartner(lnPayOrder, payResult.getReturnMsg());
                                    return;
                                }
                                //账单为late_not状态检查是否已重复还款
                                List<RepayBillVO> repayBillSelectList = new ArrayList<RepayBillVO>();
                                for(RepayBillVO repayBill :repayBillVOList){
                                    if(repayBill.getScheStatus().equals(Constants.LN_REPAY_LATE_NOT)){
                                        LnRepeatRepayRecordExample example = new LnRepeatRepayRecordExample();
                                        example.createCriteria().andRepayPlanIdEqualTo(repayBill.getRepayPlanId());
                                        List<LnRepeatRepayRecord> repeatRepayRecords = lnRepeatRepayRecordMapper.selectByExample(example);
                                        if(CollectionUtils.isNotEmpty(repeatRepayRecords)){
                                            repayBillSelectList.add(repayBill);
                                        }
                                    }
                                }
                                if(CollectionUtils.isNotEmpty(repayBillSelectList)){
                                    repayBillVOList.removeAll(repayBillSelectList);
                                }
                                //检查过滤后是否为空
                                if(CollectionUtils.isEmpty(repayBillVOList)){
                                    //放到redis中
                                    pushRedis(allLoanIds, lnPayOrder, lnUser);
                                    //通知云贷还款结果
                                    notifyPartner(lnPayOrder, payResult.getReturnMsg());
                                    return;
                                }
                                final List<Integer> allLoanIdsFinal = allLoanIds;
                                final List<RepayBillVO> repayBillVOListFinal = repayBillVOList;
                                final LnPayOrders lnPayOrderFinal = lnPayOrder;
                                transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                                    @Override
                                    protected void doInTransactionWithoutResult(TransactionStatus status) {

                                        //批量修改账单表return_flag为PENDING
                                        for(RepayBillVO repayBillVO :repayBillVOListFinal){
                                            LnRepaySchedule repaySchedule = new LnRepaySchedule();
                                            repaySchedule.setId(repayBillVO.getRepayPlanId());
                                            repaySchedule.setReturnFlag(Constants.LN_REPAY_RETURN_FLAG_PENDING);
                                            repaySchedule.setUpdateTime(new Date());
                                            lnRepayScheduleMapper.updateByPrimaryKeySelective(repaySchedule);
                                        }
                                        //借款表置为还款中
                                        if(CollectionUtils.isNotEmpty(allLoanIdsFinal)){
                                            for(Integer loanId :allLoanIdsFinal){
                                                LnLoan loanUpdate = new LnLoan();
                                                loanUpdate.setId(loanId);
                                                loanUpdate.setIsRepaying(Constants.DEP_REPAY_REPAYING);
                                                loanUpdate.setUpdateTime(new Date());
                                                lnLoanMapper.updateByPrimaryKeySelective(loanUpdate);
                                            }
                                        }
                                        //待处理账单入redis
                                        for(Integer loanId :allLoanIdsFinal){
                                            List<RepayBillVO> repayBills = new ArrayList<RepayBillVO>();
                                            for(RepayBillVO repayBill :repayBillVOListFinal){
                                                if(repayBill.getLoanId().equals(loanId)){
                                                    repayBills.add(repayBill);
                                                }
                                            }
                                            try{
                                                //放到redis中
                                                JSONObject billObject = new JSONObject();
                                                LnLoan lnLoan = lnLoanMapper.selectByPrimaryKey(loanId);
                                                billObject.put("partnerLoanId", lnLoan.getPartnerLoanId());
                                                billObject.put("lnPayOrderId", lnPayOrderFinal.getId());
                                                billObject.put("partnerUserId", lnUser.getPartnerUserId());
                                                billObject.put("loanId", loanId);
                                                billObject.put("repayBills", repayBills);
                                                jsClientDaoSupport.rpush("repayBill", billObject.toString());
                                                logger.info(">>>云贷入还款队列(账单对比)数据:" + JSON.toJSONString(billObject) + "<<<");
                                            }catch (Exception e){
                                                logger.error("云贷入还款处理轮询(账单对比)redis异常",e);
                                            }
                                        }
                                    }
                                });
                            }
                        }
                        //通知云贷还款结果
                        notifyPartner(lnPayOrder, payResult.getReturnMsg());
                    } else if(Constants.PROPERTY_SYMBOL_ZSD.equals(lnPayOrder.getPartnerCode())) {
                        if(payResult.isSuccess()){
                            /**
                             * 还款成功且非线下还款时
                             * 记录手续费
                             */
                            LnRepay lnRepay = lnRepays.get(0);
                            if(StringUtil.isEmpty(lnRepay.getRepayType())){
                                //手续费金额查询
                                CommissionVO commission = commissionService.calServiceFee(payResult.getAmount(), TransTypeEnum.ZSD_REPAY_DK, PayPlatformEnum.BAOFOO);
                                Double actFee = commission.getActPayAmount();
                                Double needFee = commission.getNeedPayAmount();

                                //记录手续费
                                BsServiceFee bsServiceFee = new BsServiceFee();
                                bsServiceFee.setPlanFee(needFee);
                                bsServiceFee.setDoneFee(actFee);
                                bsServiceFee.setTransAmount(payResult.getAmount());
                                bsServiceFee.setFeeType(Constants.FEE_TYPE_REPAY);
                                bsServiceFee.setStatus(Constants.FEE_STATUS_COLLECT);
                                bsServiceFee.setCreateTime(new Date());
                                bsServiceFee.setOrderNo(lnPayOrder.getOrderNo());
                                bsServiceFee.setSubAccountId(lnPayOrder.getSubAccountId());
                                bsServiceFee.setUpdateTime(new Date());
                                bsServiceFee.setNote("应扣" + needFee + "，实扣" + actFee);
                                bsServiceFee.setPaymentPlatformFee(commission.getThreePartyPaymentServiceFee());
                                serviceFeeMapper.insertSelective(bsServiceFee);
                            }

                            //账单匹配成功的账单编号集合
                            List<Integer> repayPlanIds = new ArrayList<Integer>();
                            //账单匹配不成功的借款编号集合
                            final List<Integer> notMatchLoanIds = new ArrayList<Integer>();
                            final List<Integer> allLoanIds = lnRepayMapper.selectDistinctLoanIds(lnPayOrder.getOrderNo());
                            for(LnRepay repay :lnRepays){
                                if(repay.getRepayPlanId() == null){
                                    //提前还款
                                    if(CollectionUtils.isEmpty(notMatchLoanIds)){
                                        notMatchLoanIds.add(repay.getLoanId());
                                    }else{
                                        if(!notMatchLoanIds.contains(repay.getLoanId())){
                                            notMatchLoanIds.add(repay.getLoanId());
                                        }
                                    }
                                }else{
                                    //正常还款
                                    repayPlanIds.add(repay.getRepayPlanId());
                                }
                            }
                            //统一加还款处理中标识
                            Map<String,Object> params = new HashMap<String,Object>();
                            params.put("loanIds", notMatchLoanIds);
                            params.put("repayPlanIds", repayPlanIds);
                            final LnUser lnUser = lnUserMapper.selectByPrimaryKey(lnPayOrder.getLnUserId());
                            if(CollectionUtils.isEmpty(notMatchLoanIds) && CollectionUtils.isEmpty(repayPlanIds)){
                                //放到redis中
                                pushRedis(allLoanIds, lnPayOrder, lnUser);
                            }else{
                                //获取全部还款处理中账单
                                List<RepayBillVO> repayBillVOList = lnRepayScheduleMapper.selectRepayBill(params);
                                if(CollectionUtils.isEmpty(repayBillVOList)){
                                    //放到redis中
                                    pushRedis(allLoanIds, lnPayOrder, lnUser);
                                    //通知云贷还款结果
                                    noticePartner2Dsd(lnPayOrder, payResult.getReturnMsg());
                                    return;
                                }
                                final List<Integer> allLoanIdsFinal = allLoanIds;
                                final List<RepayBillVO> repayBillVOListFinal = repayBillVOList;
                                final LnPayOrders lnPayOrderFinal = lnPayOrder;
                                transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                                    @Override
                                    protected void doInTransactionWithoutResult(TransactionStatus status) {
                                        //批量修改账单表return_flag为PENDING
                                        for(RepayBillVO repayBillVO :repayBillVOListFinal){
                                            LnRepaySchedule repaySchedule = new LnRepaySchedule();
                                            repaySchedule.setId(repayBillVO.getRepayPlanId());
                                            repaySchedule.setReturnFlag(Constants.LN_REPAY_RETURN_FLAG_PENDING);
                                            repaySchedule.setUpdateTime(new Date());
                                            repaySchedule.setPlanTotal(payResult.getAmount());
                                            List<LnRepayDetail> detailList = new ArrayList<>();
                                            for (LnRepay repay: lnRepays) {
                                                logger.info("repay.getRepayPlanId() 还款计划ID：{}", repay.getRepayPlanId());
                                                logger.info("repaySchedule.getId() 还款计划ID：{}", repaySchedule.getId());
                                                if(repaySchedule.getId().equals(repay.getRepayPlanId())) {
                                                    logger.info("还款时间ln_repay.done_time：{}", repay.getDoneTime());
                                                    repaySchedule.setPlanDate(DateUtil.parse(DateUtil.formatYYYYMMDD(repay.getDoneTime()), "yyyy-MM-dd"));
                                                    LnRepayDetailExample detailExample = new LnRepayDetailExample();
                                                    detailExample.createCriteria().andRepayIdEqualTo(repay.getId());
                                                    detailList = lnRepayDetailMapper.selectByExample(detailExample);
                                                    break;
                                                }
                                            }
                                            lnRepayScheduleMapper.updateByPrimaryKeySelective(repaySchedule);

                                            LnRepayScheduleDetail lnRepayScheduleDetail = new LnRepayScheduleDetail();
                                            LnRepayScheduleDetailExample example = new LnRepayScheduleDetailExample();
                                            example.createCriteria().andPlanIdEqualTo(repaySchedule.getId());
                                            List<LnRepayScheduleDetail> scheduleDetailList = lnRepayScheduleDetailMapper.selectByExample(example);

                                            Double interest = 0d;
                                            for (LnRepayDetail detail: detailList) {
                                                if(detail.getSubjectCode().equals(LoanSubjects.SUBJECT_CODE_INTEREST.getCode())) {
                                                    // 利息
                                                    interest += detail.getDoneAmount();
                                                } else if(detail.getSubjectCode().equals(LoanSubjects.SUBJECT_CODE_LATE_FEE.getCode())) {
                                                    // 滞纳金
                                                    interest += detail.getDoneAmount();
                                                }
                                                logger.info("detail.getSubjectCode：{}", detail.getSubjectCode());
                                                for (LnRepayScheduleDetail scheduleDetail: scheduleDetailList) {
                                                    if(scheduleDetail.getSubjectCode().equals(detail.getSubjectCode())
                                                            && LoanSubjects.SUBJECT_CODE_PRINCIPAL.getCode().equals(detail.getSubjectCode())) {
                                                        logger.info("scheduleDetail.getSubjectCode：{}", scheduleDetail.getSubjectCode());
                                                        scheduleDetail.setPlanAmount(detail.getDoneAmount());
                                                        scheduleDetail.setUpdateTime(new Date());
                                                        lnRepayScheduleDetailMapper.updateByPrimaryKeySelective(scheduleDetail);
                                                    }
                                                }
                                            }
                                            // 利息
                                            lnRepayScheduleDetail.setPlanAmount(interest);
                                            lnRepayScheduleDetail.setUpdateTime(new Date());
                                            LnRepayScheduleDetailExample detailExample = new LnRepayScheduleDetailExample();
                                            detailExample.createCriteria().andPlanIdEqualTo(repaySchedule.getId())
                                                    .andSubjectCodeEqualTo(LoanSubjects.SUBJECT_CODE_INTEREST.getCode());
                                            lnRepayScheduleDetailMapper.updateByExampleSelective(lnRepayScheduleDetail, detailExample);
                                        }
                                        //借款表置为还款中
                                        if(CollectionUtils.isNotEmpty(allLoanIdsFinal)){
                                            for(Integer loanId :allLoanIdsFinal){
                                                LnLoan loanUpdate = new LnLoan();
                                                loanUpdate.setId(loanId);
                                                loanUpdate.setIsRepaying(Constants.DEP_REPAY_REPAYING);
                                                loanUpdate.setUpdateTime(new Date());
                                                lnLoanMapper.updateByPrimaryKeySelective(loanUpdate);
                                            }
                                        }
                                        //待处理账单入redis
                                        for(Integer loanId :allLoanIdsFinal){
                                            List<RepayBillVO> repayBills = new ArrayList<RepayBillVO>();
                                            for(RepayBillVO repayBill :repayBillVOListFinal){
                                                if(repayBill.getLoanId().equals(loanId)){
                                                    repayBills.add(repayBill);
                                                }
                                            }
                                            try{
                                                //放到redis中
                                                JSONObject billObject = new JSONObject();
                                                LnLoan lnLoan = lnLoanMapper.selectByPrimaryKey(loanId);
                                                billObject.put("partnerLoanId", lnLoan.getPartnerLoanId());
                                                billObject.put("lnPayOrderId", lnPayOrderFinal.getId());
                                                billObject.put("partnerUserId", lnUser.getPartnerUserId());
                                                billObject.put("loanId", loanId);
                                                billObject.put("repayBills", repayBills);
                                                jsClientDaoSupport.rpush("repayBill", billObject.toString());
                                                logger.info(">>>赞时贷入还款队列(账单对比)数据:" + JSON.toJSONString(billObject) + "<<<");
                                            }catch (Exception e){
                                                logger.error("赞时贷入还款处理轮询(账单对比)redis异常",e);
                                            }
                                        }
                                    }
                                });
                            }

                        }
                        //通知赞时贷还款结果
                        noticePartner2Dsd(lnPayOrder, payResult.getReturnMsg());
                    } else if(PartnerEnum.SEVEN_DAI_SELF.getCode().equals(lnPayOrder.getPartnerCode())) {
                        // 7贷还款业务处理
                        doRepayResultPayProcessSevenDai(payResult, lnPayOrder, lnRepays);
                    }
                }
            });
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean containsSameValue(List<Integer> list, Integer value) {
        boolean contains = false;
        if(CollectionUtils.isNotEmpty(list)) {
            for (Integer data : list) {
                if(data.equals(value)) {
                    contains = true;
                    break;
                }
            }
        }
        return contains;
    }

    private void doRepayResultPayProcessSevenDai(DepFixedRepayPayResultInfo payResult, LnPayOrders lnPayOrder, List<LnRepay> lnRepays) {
        /**
         * 还款成功时
         * 记录手续费,Ln_repay_schedule如果无账单编号，则对应借款编号下的所有未完成（非LATE_NOT、REPAIED、CANCELLED）账单，
         * 统一加还款处理中标识；如果有对应账单编号，则只针对该账单加还款处理中标识；
         * Redis-list增加上述待处理账单，等待“B还款处理轮询定时”轮询
         */
        if(payResult.isSuccess()){

            List<Integer> repayScheduleIdList = new ArrayList<>();
            List<Integer> loanIdList = new ArrayList<>();
            final List<Map<String, Object>> maps = new ArrayList<>();
            for(LnRepay lnRepay : lnRepays) {
                Map<String, Object> map = new HashMap<>();
                if(null != lnRepay.getRepayPlanId()) {
                    repayScheduleIdList.add(lnRepay.getRepayPlanId());
                }
                if(!containsSameValue(loanIdList, lnRepay.getLoanId())) {
                    loanIdList.add(lnRepay.getLoanId());
                }
                map.put("applyTime", lnRepay.getApplyTime());
                map.put("loanId", lnRepay.getLoanId());
                map.put("repayId", lnRepay.getId());
                map.put("repayPlanId", lnRepay.getRepayPlanId());
                LnRepayDetailExample lnRepayDetailExample = new LnRepayDetailExample();
                lnRepayDetailExample.createCriteria().andRepayIdEqualTo(lnRepay.getId()).andSubjectCodeEqualTo(LoanSubjects.SUBJECT_CODE_INTEREST.getCode());
                LnRepayDetail interestDetail = lnRepayDetailMapper.selectByExample(lnRepayDetailExample).get(0);
                map.put("interest", interestDetail.getDoneAmount());
                maps.add(map);
            }

            LnRepayScheduleExample scheduleExample = new LnRepayScheduleExample();
            scheduleExample.createCriteria().andIdIn(repayScheduleIdList);
            List<LnRepaySchedule> repayScheduleList = lnRepayScheduleMapper.selectByExample(scheduleExample);
            LnLoanExample loanExample = new LnLoanExample();
            loanExample.createCriteria().andIdIn(loanIdList);
            List<LnLoan> loanList = lnLoanMapper.selectByExample(loanExample);

            LnBillBizInfoExample billBizInfoExample = new LnBillBizInfoExample();
            billBizInfoExample.createCriteria().andLoanIdIn(loanIdList);
            billBizInfoExample.setOrderByClause("id desc");
            List<LnBillBizInfo> billList = billBizInfoMapper.selectByExample(billBizInfoExample);
            Date lastSettleTime = null;
            if(!CollectionUtils.isEmpty(billList)) {
                lastSettleTime = DateUtil.addDays(billList.get(0).getRepayTime(), 1);
            }
            for (Map<String, Object> map: maps) {
                for(LnLoan loan : loanList) {
                    if(loan.getId().equals(map.get("loanId"))) {
                        map.put("loanTime", loan.getLoanTime());
                        break;
                    }
                }
                for(LnRepaySchedule schedule : repayScheduleList) {
                    if(schedule.getId().equals(map.get("repayPlanId"))) {
                        map.put("serialId", schedule.getSerialId());
                        map.put("status", schedule.getStatus());
                        break;
                    }
                }
            }

            Integer maxSerialId = -1;
            for(Map<String, Object> map: maps) {
                // 获取最大serialId
                maxSerialId = maxSerialId.compareTo((Integer) map.get("serialId")) < 0 ? (Integer) map.get("serialId") : maxSerialId;
            }
            // 存在免息还款，一期免息，还款的时候传来一期和二期的还款
            if(CollectionUtils.isNotEmpty(lnRepays)) {
                for(Map<String, Object> map: maps) {
                    if(map.get("serialId").equals(maxSerialId)) {
                        LnBillBizInfo billBizInfo = new LnBillBizInfo();
                        billBizInfo.setRepayType(Constants.REPAY_TYPE_NORMAL_REPAY); // 7贷无逾期还款，代偿不进入此方法
                        billBizInfo.setRepayTime((Date) map.get("applyTime"));
                        billBizInfo.setRepayScheduleId((Integer) map.get("repayPlanId"));
                        billBizInfo.setRepayId((Integer) map.get("repayId"));
                        if(lastSettleTime == null) {
                            billBizInfo.setLastSettleTime((Date) map.get("loanTime"));
                        } else {
                            billBizInfo.setLastSettleTime(lastSettleTime);
                        }
                        try {
                            int interestDays = com.pinting.business.util.DateUtil.getBetweenDays(billBizInfo.getLastSettleTime(), billBizInfo.getRepayTime());
                            billBizInfo.setInterestDays(interestDays < 0 ? 0 : interestDays);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        billBizInfo.setLoanId((Integer)map.get("loanId"));
                        billBizInfo.setCreateTime(new Date());
                        billBizInfo.setUpdateTime(new Date());
                        billBizInfoMapper.insertSelective(billBizInfo);
                        map.put("billId", billBizInfo.getId());
                        break;
                    }
                }
            }

            //手续费金额查询
            CommissionVO commission = commissionService.calServiceFee(payResult.getAmount(), TransTypeEnum.SEVEN_DAI_SELF_DK, PayPlatformEnum.BAOFOO);
            Double actFee = commission.getActPayAmount();
            Double needFee = commission.getNeedPayAmount();

            //记录手续费
            BsServiceFee bsServiceFee = new BsServiceFee();
            bsServiceFee.setPlanFee(needFee);
            bsServiceFee.setDoneFee(actFee);
            bsServiceFee.setTransAmount(lnPayOrder.getAmount());
            bsServiceFee.setFeeType(Constants.FEE_TYPE_REPAY);
            bsServiceFee.setStatus(Constants.FEE_STATUS_COLLECT);
            bsServiceFee.setCreateTime(new Date());
            bsServiceFee.setOrderNo(lnPayOrder.getOrderNo());
            bsServiceFee.setSubAccountId(lnPayOrder.getSubAccountId());
            bsServiceFee.setUpdateTime(new Date());
            bsServiceFee.setNote("应扣" + needFee + "，实扣" + actFee);
            bsServiceFee.setPaymentPlatformFee(commission.getThreePartyPaymentServiceFee());
            serviceFeeMapper.insertSelective(bsServiceFee);

            //账单匹配成功的账单编号集合
            List<Integer> repayPlanIds = new ArrayList<Integer>();
            //账单匹配不成功的借款编号集合
            List<Integer> notMatchLoanIds = new ArrayList<Integer>();
            final List<Integer> allLoanIds = lnRepayMapper.selectDistinctLoanIds(lnPayOrder.getOrderNo());
            for(LnRepay repay :lnRepays){
                if(repay.getRepayPlanId() == null){
                    //提前还款
                    if(CollectionUtils.isEmpty(notMatchLoanIds)){
                        notMatchLoanIds.add(repay.getLoanId());
                    }else{
                        if(!notMatchLoanIds.contains(repay.getLoanId())){
                            notMatchLoanIds.add(repay.getLoanId());
                        }
                    }
                }else{
                    //正常还款
                    repayPlanIds.add(repay.getRepayPlanId());
                }
            }
            //统一加还款处理中标识
            Map<String,Object> params = new HashMap<String,Object>();
            params.put("loanIds", notMatchLoanIds);
            params.put("repayPlanIds", repayPlanIds);
            final LnUser lnUser = lnUserMapper.selectByPrimaryKey(lnPayOrder.getLnUserId());
            if(CollectionUtils.isEmpty(notMatchLoanIds) && CollectionUtils.isEmpty(repayPlanIds)){
                //放到redis中
                pushRedis(allLoanIds, lnPayOrder, lnUser);
            }else{
                //获取全部还款处理中账单
                List<RepayBillVO> repayBillVOList = lnRepayScheduleMapper.selectRepayBill(params);
                if(CollectionUtils.isEmpty(repayBillVOList)){
                    //放到redis中
                    pushRedis(allLoanIds, lnPayOrder, lnUser);
                    //通知7贷还款结果
                    notifyPartner(lnPayOrder, payResult.getReturnMsg());
                    return;
                }
                //账单为late_not状态检查是否已重复还款
                List<RepayBillVO> repayBillSelectList = new ArrayList<RepayBillVO>();
                for(RepayBillVO repayBill :repayBillVOList){
                    if(repayBill.getScheStatus().equals(Constants.LN_REPAY_LATE_NOT)){
                        LnRepeatRepayRecordExample example = new LnRepeatRepayRecordExample();
                        example.createCriteria().andRepayPlanIdEqualTo(repayBill.getRepayPlanId());
                        List<LnRepeatRepayRecord> repeatRepayRecords = lnRepeatRepayRecordMapper.selectByExample(example);
                        if(CollectionUtils.isNotEmpty(repeatRepayRecords)){
                            repayBillSelectList.add(repayBill);
                        }
                    }
                }
                if(CollectionUtils.isNotEmpty(repayBillSelectList)){
                    repayBillVOList.removeAll(repayBillSelectList);
                }
                //检查过滤后是否为空
                if(CollectionUtils.isEmpty(repayBillVOList)){
                    //放到redis中
                    pushRedis(allLoanIds, lnPayOrder, lnUser);
                    //通知7贷还款结果
                    notifyPartner(lnPayOrder, payResult.getReturnMsg());
                    return;
                }
                final List<Integer> allLoanIdsFinal = allLoanIds;
                final List<RepayBillVO> repayBillVOListFinal = repayBillVOList;
                final LnPayOrders lnPayOrderFinal = lnPayOrder;
                final List<LnRepay> allLnRepays = lnRepays;
                transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                    @Override
                    protected void doInTransactionWithoutResult(TransactionStatus status) {

                        //批量修改账单表return_flag为PENDING
                        for(RepayBillVO repayBillVO :repayBillVOListFinal) {
                            if (Constants.PARTNER_BUSINESS_FLAG_REPAY_ANY_TIME.equals(repayBillVO.getPartnerBusinessFlag())) {
                                // 随借随还产品：由于传入的还款信息，本金金额，利息金额不固定，更新对应的还款账单ln_Repay_Schedule，ln_repay_schedule_detail
                                for(LnRepay lnRepay : allLnRepays) {
                                   if (repayBillVO.getRepayPlanId().equals(lnRepay.getRepayPlanId())) {
                                       modifyRepayBill(lnRepay, Constants.LN_REPAY_RETURN_FLAG_PENDING);
                                   }
                                }
                            } else {
                                LnRepaySchedule repaySchedule = new LnRepaySchedule();
                                repaySchedule.setId(repayBillVO.getRepayPlanId());
                                repaySchedule.setReturnFlag(Constants.LN_REPAY_RETURN_FLAG_PENDING);
                                repaySchedule.setUpdateTime(new Date());
                                lnRepayScheduleMapper.updateByPrimaryKeySelective(repaySchedule);
                            }
                        }
                        //借款表置为还款中
                        if(CollectionUtils.isNotEmpty(allLoanIdsFinal)){
                            for(Integer loanId :allLoanIdsFinal){
                                LnLoan loanUpdate = new LnLoan();
                                loanUpdate.setId(loanId);
                                loanUpdate.setIsRepaying(Constants.DEP_REPAY_REPAYING);
                                loanUpdate.setUpdateTime(new Date());
                                lnLoanMapper.updateByPrimaryKeySelective(loanUpdate);
                            }
                        }
                        //待处理账单入redis
                        for(Integer loanId :allLoanIdsFinal){
                            List<RepayBillVO> repayBills = new ArrayList<RepayBillVO>();
                            for(RepayBillVO repayBill :repayBillVOListFinal){
                                if(repayBill.getLoanId().equals(loanId)){
                                    repayBills.add(repayBill);
                                }
                                for(Map<String, Object> map : maps) {
                                    if(repayBill.getRepayPlanId().equals(map.get("repayPlanId"))) {
                                        repayBill.setBillId((Integer)map.get("billId"));
                                    }
                                }
                            }
                            try{
                                //放到redis中
                                JSONObject billObject = new JSONObject();
                                LnLoan lnLoan = lnLoanMapper.selectByPrimaryKey(loanId);
                                billObject.put("partnerLoanId", lnLoan.getPartnerLoanId());
                                billObject.put("lnPayOrderId", lnPayOrderFinal.getId());
                                billObject.put("partnerUserId", lnUser.getPartnerUserId());
                                billObject.put("loanId", loanId);
                                billObject.put("repayBills", repayBills);
                                jsClientDaoSupport.rpush("repayBill", billObject.toString());
                                logger.info(">>>7贷入还款队列(账单对比)数据:" + JSON.toJSONString(billObject) + "<<<");
                            }catch (Exception e){
                                logger.error("7贷入还款处理轮询(账单对比)redis异常",e);
                            }
                        }
                    }
                });
            }
        } else {
            // 随借随还产品：还款失败，0期账单废除，批量修改账单表return_flag为FAILED
            if (CollectionUtils.isNotEmpty(lnRepays)) {
                LnLoan lnLoan = lnLoanMapper.selectByPrimaryKey(lnRepays.get(0).getLoanId());
                if (Constants.PARTNER_BUSINESS_FLAG_REPAY_ANY_TIME.equals(lnLoan.getPartnerBusinessFlag())) {
                    for (LnRepay lnRepay : lnRepays) {
                        LnRepaySchedule lnRepaySchedule = lnRepayScheduleMapper.selectByPrimaryKey(lnRepay.getRepayPlanId());
                        if (lnRepaySchedule != null && lnRepaySchedule.getSerialId() == 0
                                && LoanStatus.REPAY_SCHEDULE_STATUS_INIT.getCode().equals(lnRepaySchedule.getStatus())) {
                            LnRepaySchedule repaySchedule = new LnRepaySchedule();
                            repaySchedule.setId(lnRepaySchedule.getId());
                            repaySchedule.setReturnFlag(Constants.LN_REPAY_RETURN_FLAG_FAILED);
                            repaySchedule.setStatus(LoanStatus.REPAY_SCHEDULE_STATUS_CANCELLED.getCode());
                            repaySchedule.setUpdateTime(new Date());
                            lnRepayScheduleMapper.updateByPrimaryKeySelective(repaySchedule);
                        }
                    }
                }
            }
        }
        // 通知7贷还款结果
        this.noticePartner2Seven(lnPayOrder, payResult.getReturnMsg());
    }

    /**
     * 账单入redis
     * @param allLoanIds
     * @param lnPayOrder
     * @param lnUser
     */
    public void pushRedis(List<Integer> allLoanIds,LnPayOrders lnPayOrder,LnUser lnUser){
        if(CollectionUtils.isNotEmpty(allLoanIds)){
            for(Integer loanId :allLoanIds){
                //借款表置为还款中
                LnLoan loanUpdate = new LnLoan();
                loanUpdate.setId(loanId);
                loanUpdate.setIsRepaying(Constants.DEP_REPAY_REPAYING);
                loanUpdate.setUpdateTime(new Date());
                lnLoanMapper.updateByPrimaryKeySelective(loanUpdate);
            }
        }
        for(Integer loanId :allLoanIds){
            //进入还款处理队列
            JSONObject billObject = new JSONObject();
            LnLoan lnLoan = lnLoanMapper.selectByPrimaryKey(loanId);
            billObject.put("partnerLoanId", lnLoan.getPartnerLoanId());
            billObject.put("lnPayOrderId", lnPayOrder.getId());
            billObject.put("partnerUserId", lnUser.getPartnerUserId());
            billObject.put("loanId", loanId);
            billObject.put("repayBills","");
            try{
                jsClientDaoSupport.rpush("repayBill", billObject.toString());
                logger.info(">>>入还款队列(账单对比)数据:" + JSON.toJSONString(billObject) + "<<<");
            }catch (Exception e){
                logger.error("入还款处理轮询(账单对比)redis异常",e);
            }

        }
    }

    @Override
    public void notifyPartner(final LnPayOrders lnPayOrder, final String errorMsg) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                logger.info(">>>还款通知合作方开始：[入参]" + JSON.toJSONString(lnPayOrder) + "|errorMsg=" + errorMsg + "<<<");
                B2GReqMsg_DafyLoanNotice_RepayResultNotice noticeRepay = new B2GReqMsg_DafyLoanNotice_RepayResultNotice();
                //通过订单号在还款信息列表其中一条记录中获取合作方订单号
                LnRepayExample example = new LnRepayExample();
                example.createCriteria().andPayOrderNoEqualTo(lnPayOrder.getOrderNo());
                List<LnRepay> repayList = lnRepayMapper.selectByExample(example);
                if(CollectionUtils.isEmpty(repayList)){
                    logger.error("通知云贷时未找到BgwOrderNo=" + lnPayOrder.getOrderNo() + "的还款信息记录");
                }
                LnRepay repay = repayList.get(0);
                noticeRepay.setOrderNo(repay.getPartnerOrderNo());
                noticeRepay.setBgwOrderNo(repay.getBgwOrderNo());
                noticeRepay.setChannelLoan(lnPayOrder.getChannel());
                List<Integer> loanIds = new ArrayList<Integer>();
                //循环判断多笔借款信息是否存在
                for(LnRepay lnRepay :repayList){
                    //根据借款编号 查询借款信息
                    LnLoan lnLoan = lnLoanMapper.selectByPrimaryKey(lnRepay.getLoanId());
                    if (lnLoan == null) {
                        loanIds.add(lnRepay.getLoanId());
                    }
                }
                if(lnPayOrder.getStatus() == Constants.ORDER_STATUS_SUCCESS){
                    noticeRepay.setResultCode("SUCCESS");
                    noticeRepay.setResultMsg(LoanStatus.REPAY_STATUS_REPAIED.getDescription());
                    noticeRepay.setFinishTime(lnPayOrder.getUpdateTime());
                }else if(lnPayOrder.getStatus() == Constants.ORDER_STATUS_PAYING){
                    noticeRepay.setResultCode("PROCESS");
                    noticeRepay.setResultMsg(LoanStatus.REPAY_STATUS_PAYING.getDescription());
                }else{
                    noticeRepay.setResultCode("FAIL");
                    noticeRepay.setResultMsg(StringUtil.isEmpty(lnPayOrder.getReturnMsg())?LoanStatus.REPAY_STATUS_FAIL.getDescription():lnPayOrder.getReturnMsg());
                }
                if(CollectionUtils.isNotEmpty(loanIds)){
                    logger.error("还款通知时找不到的借款信息id为："+JSONObject.fromObject(loanIds).toString());
                }
                B2GResMsg_DafyLoanNotice_RepayResultNotice res = null;
                LnRepay repayTemp = new LnRepay();
                try {
                    res = dafyNoticeService.noticeRepay(noticeRepay);

                    if (res != null && ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
                        repayTemp.setInformStatus(LoanStatus.NOTICE_STATUS_SUCCESS.getCode());
                    } else {
                        repayTemp.setInformStatus(LoanStatus.NOTICE_STATUS_FAIL.getCode());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    repayTemp.setInformStatus(LoanStatus.NOTICE_STATUS_FAIL.getCode());
                }
                LnRepayExample lnRepayExample = new LnRepayExample();
                lnRepayExample.createCriteria().andPayOrderNoEqualTo(lnPayOrder.getOrderNo());
                repayTemp.setUpdateTime(new Date());
                lnRepayMapper.updateByExampleSelective(repayTemp, lnRepayExample);
            }
        }).start();
    }

    @Override
    public void repeatRepayProcess(LnRepeatRepayRecord repeatRepay) {
        lnRepeatRepayRecordMapper.insertSelective(repeatRepay);
        //调用“重复还款入营收账记账”
        depFixedRepayAccountService.repayRepeat2AccRecord(repeatRepay, null);

    }

    @Override
    public void repeatRepayProcess(LnRepeatRepayRecord repeatRepay, Double marginAmount) {
        lnRepeatRepayRecordMapper.insertSelective(repeatRepay);
        //调用“重复还款入营收账记账”
        depFixedRepayAccountService.repayRepeat2AccRecord(repeatRepay, marginAmount);

    }

    @Override
    public void normalRepaySysSplit(final NormalRepaySysSplitInfo info) {
        /**
         * 前置校验：判断订单是否成功，还款信息ln_repay是否存在
         * 加锁、事务
         * 判断是否需要免息补账，需要则计算补账金额，记入合作方补账记录表（ln_account_fill）
         * bs_revenue_trans_detail记录还款营收收入，
         * 分账计算系统还款户金额（THD_REPAY）、系统营收户金额（THD_BGW_REVENUE_YUN）、云贷营收户金额（THD_REVENUE_YUN）
         * bs_revenue_trans_detail记录云贷自主还款营收收入
         * ln_repay_schedule更新状态为已还款
         * 手续费表新增数据
         * 新增存管还款计划表（ln_deposition_repay_schedule）及明细
         * 调用系统记账
         */
        //必传入参校验
        if(info.getLnPayOrdersId() == null || info.getLoanId() == null
                || info.getRepayAmount() == null || StringUtils.isBlank(info.getOrderNo())
                || StringUtils.isBlank(info.getPartnerRepayId())){
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }
        //1、前置校验：借款表是否存在
        final LnLoan lnLoan = lnLoanMapper.selectByPrimaryKey(info.getLoanId());
        if(lnLoan == null){
            logger.info("===========【代扣还款系统分账】：校验，借款表数据未获得================");
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }
        //借款表存在，查询借款用户，确定合作方
        String partnerName = "";
        final LnUser lnUser = lnUserMapper.selectByPrimaryKey(lnLoan.getLnUserId());
        if(PartnerEnum.YUN_DAI_SELF.getCode().equals(lnUser.getPartnerCode())){
            partnerName = "云贷自主";
        }else if(PartnerEnum.ZSD.getCode().equals(lnUser.getPartnerCode())){
            partnerName = "赞时贷";
        }

        //2、前置校验：判断订单是否处理中，还款信息是否存在等
        LnPayOrdersExample payOrdersExample = new LnPayOrdersExample();
        payOrdersExample.createCriteria().andIdEqualTo(info.getLnPayOrdersId())
                .andStatusEqualTo(Constants.ORDER_STATUS_SUCCESS);
        List<LnPayOrders> ordersList = lnPayOrdersMapper.selectByExample(payOrdersExample);
        if(CollectionUtils.isEmpty(ordersList)){
            logger.info("==========="+partnerName+"【主动、代扣还款系统分账】：校验，订单数据未获得================");
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }

        //查询ln_repay_schedule
        LnRepayScheduleExample repayScheduleExample = new LnRepayScheduleExample();
        repayScheduleExample.createCriteria().andPartnerRepayIdEqualTo(info.getPartnerRepayId())
                .andLoanIdEqualTo(info.getLoanId()).andStatusNotEqualTo(Constants.LN_REPAY_CANCELLED);
        List<LnRepaySchedule> repaySchedulList = lnRepayScheduleMapper.selectByExample(repayScheduleExample);
        //3、前置校验：还款计划表中是否存在，状态是否已还
        if(CollectionUtils.isEmpty(repaySchedulList)){
            logger.info("==========="+partnerName+"【主动、代扣还款系统分账】：还款计划表数据未获得================");
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }
        final LnRepaySchedule lnRepaySchedule = repaySchedulList.get(0);

        logger.info("==========="+partnerName+"【主动、代扣还款系统分账】：LnRepaySchedule.id="+lnRepaySchedule.getId()+"================");

        try {
			jsClientDaoSupport.lock(RedisLockEnum.LOCK_DEP_LOAN_RELATION.getKey());
			//查询有债权关系的债权数据
			final List<LoanRelation4TransferVO> relationList = lnLoanRelationMapper.getRelationList(null,lnRepaySchedule.getLoanId());
			if(CollectionUtils.isEmpty(relationList)){
				logger.info("===========还款结果处理：无对应的债权================");
				throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
			}
			try {
				transactionTemplate.execute(new TransactionCallbackWithoutResult(){
					@Override
					protected void doInTransactionWithoutResult(TransactionStatus status) {
						if(PartnerEnum.YUN_DAI_SELF.getCode().equals(lnUser.getPartnerCode())){
							//判断是否需要免息补账，需要则计算补账金额，记入ln_account_fill_detail（代偿不需要）/仅SUCC
							//补息的利率(云贷自主和币港湾的结算利率)---24%
							Double initRate = lnLoan.getBgwSettleRate() != null?lnLoan.getBgwSettleRate() :sysConfigService.findRatePercentByKey(Constants.YUN_DAI_SELF_SYS_SETTLE_RATE);
							Double rate = initRate == null ? 24 : initRate;
							//免息天数
							BsSysConfig initFreeDaysConfig = sysConfigService.findConfigByKey(Constants.YUN_DAI_SELF_FREE_DAYS);
							Integer freeDays = initFreeDaysConfig == null ? 5 : Integer.parseInt(initFreeDaysConfig.getConfValue());
							//初始计息天数
							Integer inDays = 0;
							Double fillInterest = 0d;//补息金额初始化
							//查询上次还利息时间
							LnRepayScheduleVO repayScheduleVO = lnRepayScheduleMapper.selectVoByLoanId(lnRepaySchedule.getLoanId(),null,lnRepaySchedule.getId());

							//还款本金
							Double repayPrincipal = getRepayDetailAmount(lnRepaySchedule.getId(), LoanSubjects.SUBJECT_CODE_PRINCIPAL);
							//计息本金
							Double P_amount = repayPrincipal == 0 ?
									lnLoanRelationMapper.getRelationAmountNotRepayingByLoanId(info.getLoanId()):repayPrincipal;
							logger.info("==================【YUN主动、代扣还款系统分账】计息本金："+P_amount+"=======================");

							//免息补账
							if(repayScheduleVO.getPlanDate() == null){
								//未发生利息还款，计算目前总的计息天数 = 此次planDate - loanDate + 1
								Integer dateNum = DateUtil.getDiffeDay(lnRepaySchedule.getPlanDate(), repayScheduleVO.getLoanDate())+1;
								inDays = dateNum;
								if(dateNum>freeDays){
									dateNum = freeDays;
								}
								fillInterest = depFixedLoanRelationshipService.calInterest(dateNum, rate, P_amount, 2);
								logger.info("==================【YUN主动、代扣还款系统分账】免息，freeDays："+freeDays+"lnRepaySchedule.PlanDate："+DateUtil.formatYYYYMMDD(lnRepaySchedule.getPlanDate())
										+"，LoanDate："+DateUtil.formatYYYYMMDD(repayScheduleVO.getLoanDate())
										+"，免息天数："+dateNum+",利率："+rate+",还款金额："
										+info.getRepayAmount()+",免息金额："+fillInterest+"========================");
							}else{
								//计算上次的计息天数 = 上次planDate - loanDate + 1
								Integer dateNumLast = DateUtil.getDiffeDay(repayScheduleVO.getPlanDate(), repayScheduleVO.getLoanDate())+1;
								//计算目前总的计息天数 = 此次planDate - loanDate + 1
								Integer dateNum = DateUtil.getDiffeDay(lnRepaySchedule.getPlanDate(), repayScheduleVO.getLoanDate())+1;
								inDays = dateNum-dateNumLast;
								if(dateNumLast<freeDays){
									//上次计息天数小于免费计息天数，则该次计息天数=免费计息天数-已计息天数
									if(dateNum>freeDays){
										dateNum = freeDays;
									}
									dateNum = dateNum-dateNumLast;
									fillInterest = depFixedLoanRelationshipService.calInterest(dateNum, rate, P_amount, 2);
									logger.info("==================【YUN主动、代扣还款系统分账】免息，上次还款时间："+DateUtil.formatYYYYMMDD(repayScheduleVO.getPlanDate())
											+"免息天数："+dateNum+",利率："+rate+",还款金额："
											+info.getRepayAmount()+",免息金额："+fillInterest+"========================");
								}else{
									logger.info("==================主动还款免息，非首次且不需要补息========================");
									//上次还款已经产生全部免息
									fillInterest=0d;
								}

							}
							//免息补账明细表记录
							if(fillInterest > 0){
								LnAccountFillDetail lnAccountFillDetail = new LnAccountFillDetail();
								lnAccountFillDetail.setPartnerCode(PartnerEnum.YUN_DAI_SELF.getCode());
								lnAccountFillDetail.setFillType(Constants.FILL_DETAIL_FILL_TYPE_INTEREST);
								lnAccountFillDetail.setFillDate(new Date());
								lnAccountFillDetail.setAmount(fillInterest);
								lnAccountFillDetail.setRelativeNo(lnRepaySchedule.getId().toString());
								lnAccountFillDetail.setStatus(Constants.FILL_DETAIL_FILL_STATUS_SUCCESS);
								lnAccountFillDetail.setCreateTime(new Date());
								lnAccountFillDetail.setUpdateTime(new Date());
								lnAccountFillDetailMapper.insertSelective(lnAccountFillDetail);
							}
							logger.info("==================【YUN主动、代扣还款系统分账】lnRepaySchedule.getId()："+lnRepaySchedule.getId()
									+"，计息天数："+inDays+"，计息本金："+P_amount+"========================");
							//获得还款的利息总和C=A（补息金额_fillAmount）+B还款利息+本金滞纳金+利息滞纳金
							Double repayInterest = getRepayDetailAmount(lnRepaySchedule.getId(), LoanSubjects.SUBJECT_CODE_INTEREST);
							Double principalOverdue = getRepayDetailAmount(lnRepaySchedule.getId(), LoanSubjects.SUBJECT_CODE_PRINCIPAL_OVERDUE);
							Double interestOverdue = getRepayDetailAmount(lnRepaySchedule.getId(), LoanSubjects.SUBJECT_CODE_INTEREST_OVERDUE);

							Double C_amount = MoneyUtil.add(repayInterest, principalOverdue).doubleValue();
							C_amount = MoneyUtil.add(C_amount, interestOverdue).doubleValue();
							C_amount = MoneyUtil.add(fillInterest, C_amount).doubleValue();

							//获得币港湾24%总利息F_amount=P_amount计息本金*计天数息(inDays)*24%/365
							Double F_amount = depFixedLoanRelationshipService.calInterest(inDays, rate, P_amount, 2);

							//币港湾应收借款人服务费 P_amount计息本金*计天数息(inDays)*借款服务费利率/365
							Double loanServiceAmount = lnLoan.getLoanServiceRate() == null 
									? 0d :depFixedLoanRelationshipService.calInterest(inDays, lnLoan.getLoanServiceRate(), P_amount, 2);
							
							//获得云贷营收(THD_REVENUE_YUN)K=C-F
							Double K_amount = MoneyUtil.subtract(C_amount, F_amount).doubleValue();

							//13%协议利率的利息
							Double agreementRate = lnLoan.getAgreementRate();

							//获取最大的SerialId
							Integer maxSerialId = lnDepositionRepayScheduleMapper.selectMaxSerialIdByLoanId(lnLoan.getId());

							//根据债权列表，计算每笔债权的（13%*计息本金*计息总天数/365），记录ln_finance_repay_schedule表，并求和，返回
							FinanceRepayCalVO vo = do4FinanceRepayYUN(relationList,repayPrincipal,lnRepaySchedule,
									agreementRate,inDays,false, maxSerialId);
							Double agreement_amount = vo.getAgreementSumAmount();
							//系统营收户金额（THD_BGW_REVENUE_YUN）= 币港湾24%总利息 - 13%协议利率的利息-2%借款服务费
							Double bgw_revenue_amount = MoneyUtil.subtract(MoneyUtil.subtract(F_amount,agreement_amount).doubleValue(),loanServiceAmount).doubleValue();

							//系统还款户金额（THD_REPAY）= P_amount计息本金*计天数息(inDays)*13%协议利率/365 + 本金+2%借款服务费
							Double repay_amount = MoneyUtil.add(MoneyUtil.add(agreement_amount, repayPrincipal).doubleValue(),loanServiceAmount).doubleValue();

							//1、记录云贷还款营收收入 2、ln_repay_schedule更新状态为已还 3、新增存管还款计划表及明细
							repayPaymentService.doNormalRepayDetail(lnLoan, lnRepaySchedule, info.getRepayAmount(),info.getOrderNo(), K_amount,
									relationList.get(0).getLnSubAccountId(), repay_amount, PartnerEnum.YUN_DAI_SELF, maxSerialId, loanServiceAmount);

							//系统记账
							FixedRepaySysSplitInfo repaySysSplitInfo = new FixedRepaySysSplitInfo();
							repaySysSplitInfo.setThdRepayAmount(repay_amount);
							repaySysSplitInfo.setThdRevenueAmount(K_amount);
							repaySysSplitInfo.setThdBGWRevenueAmount(bgw_revenue_amount);
							repaySysSplitInfo.setPartner(PartnerEnum.YUN_DAI_SELF);
							repaySysSplitInfo.setThdMarginAmount(0d);
							repaySysSplitInfo.setFee(0d);
							repaySysSplitInfo.setLnRepayScheduleId(lnRepaySchedule.getId());
							depFixedRepayAccountService.repaySysSplit(repaySysSplitInfo);
						}else if(PartnerEnum.ZSD.getCode().equals(lnUser.getPartnerCode())){
							//补息的利率(赞时贷和币港湾的结算利率)---15%
							Double initRate = sysConfigService.findRatePercentByKey(Constants.ZSD_SYS_SETTLE_RATE);
							Double rate = initRate == null ? 15 : initRate;
							//还款本金
							Double repayPrincipal = getRepayDetailAmount(lnRepaySchedule.getId(), LoanSubjects.SUBJECT_CODE_PRINCIPAL);
							//计息本金
							Double P_amount = lnLoanRelationMapper.getRelationAmountNotRepayingByLoanId(info.getLoanId());
							logger.info("==================赞时贷【主动、代扣还款系统分账】计息本金："+P_amount+"=======================");

							if(repayPrincipal.compareTo(P_amount) != 0){
								specialJnlService.warn4FailNoSMS(MoneyUtil.subtract(P_amount,repayPrincipal).doubleValue(),
										"赞时贷【主动、代扣还款系统分账】还款本金- 应收本金 ="+repayPrincipal+"-"+P_amount+"="+MoneyUtil.subtract(repayPrincipal,P_amount).doubleValue(),
										"还款计划编号："+lnRepaySchedule.getId(),
										"赞时贷【主动、代扣还款系统分账");
								logger.info("===========赞时贷【主动、代扣还款系统分账】：计划还款本金与理财人计息总本金不符================");
								throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "计划还款本金与理财人计息总本金不符");
							}


							//计息天数
							Integer inDays = DateUtil.getDiffeDay(lnRepaySchedule.getPlanDate(), DateUtil.parseDate(DateUtil.formatYYYYMMDD(lnLoan.getLoanTime())))+1;

							//获得币港湾15%总利息F_amount=P_amount计息本金*计天数息(inDays)*24%/365
							Double F_amount = depFixedLoanRelationshipService.calInterest(inDays, rate, repayPrincipal, 2);
							//计划还款总金额-还款本金
							Double C_amount = MoneyUtil.subtract(lnRepaySchedule.getPlanTotal(), repayPrincipal).doubleValue();
							//赞时贷保证金 = 借款本金*3%/12
							Double marginRate = sysConfigService.findRatePercentByKey(Constants.ZSD_SYS_MARGIN_RATE);
							Double mRate = marginRate == null ? 3 : marginRate;
							Double M_amount = MoneyUtil.divide(MoneyUtil.multiply(lnLoan.getApproveAmount(),mRate).doubleValue(),1200).doubleValue();
							if(C_amount < F_amount){
								//免息补账金额 = |计划还款总金额 - 币港湾15%总利息| = |C-F|
								LnAccountFillDetail lnAccountFillDetail = new LnAccountFillDetail();
								lnAccountFillDetail.setPartnerCode(PartnerEnum.ZSD.getCode());
								lnAccountFillDetail.setFillType(Constants.FILL_DETAIL_FILL_TYPE_INTEREST);
								lnAccountFillDetail.setFillDate(new Date());
								lnAccountFillDetail.setAmount(MoneyUtil.subtract(F_amount, C_amount).doubleValue());
								lnAccountFillDetail.setRelativeNo(lnRepaySchedule.getId().toString());
								lnAccountFillDetail.setStatus(Constants.FILL_DETAIL_FILL_STATUS_SUCCESS);
								lnAccountFillDetail.setCreateTime(new Date());
								lnAccountFillDetail.setUpdateTime(new Date());
								lnAccountFillDetailMapper.insertSelective(lnAccountFillDetail);

							}
							//赞时贷营收 = （计划还款总金额 -还款本金）- 币港湾15%总利息 - 赞时贷保证金 (可能为负数)
							Double K_amount = MoneyUtil.subtract(MoneyUtil.subtract(C_amount, F_amount).doubleValue(),M_amount).doubleValue();

							//系统营收户金额（THD_BGW_REVENUE_ZSD）= 币港湾15%总利息 - 13%协议利率的利息
							Double agreementRate = lnLoan.getAgreementRate();

							//根据债权列表，计算每笔债权的（13%*计息本金*计息总天数/365），记录ln_finance_repay_schedule表，并求和，返回
							FinanceRepayCalVO vo = do4FinanceRepayZSD(relationList,repayPrincipal,lnRepaySchedule,
									agreementRate,inDays,false);
							Double agreement_amount = vo.getAgreementSumAmount();
							//币港湾营收
							Double bgw_revenue_amount = MoneyUtil.subtract(F_amount,agreement_amount).doubleValue();
							//系统还款户金额（THD_REPAY）= P_amount计息本金*计天数息(inDays)*13%协议利率/365 + 本金
							Double repay_amount = MoneyUtil.add(agreement_amount, repayPrincipal).doubleValue();
							//1、记录赞时贷还款营收收入 2、ln_repay_schedule更新状态为已还 3、新增存管还款计划表及明细
							repayPaymentService.doNormalRepayDetail(lnLoan, lnRepaySchedule, info.getRepayAmount(),info.getOrderNo(), K_amount,
									relationList.get(0).getLnSubAccountId(), repay_amount, PartnerEnum.ZSD, 0, null);

							//系统记账
							FixedRepaySysSplitInfo repaySysSplitInfo = new FixedRepaySysSplitInfo();
							repaySysSplitInfo.setThdRepayAmount(repay_amount);
							repaySysSplitInfo.setThdRevenueAmount(K_amount);
							repaySysSplitInfo.setThdBGWRevenueAmount(bgw_revenue_amount);
							repaySysSplitInfo.setPartner(PartnerEnum.ZSD);
							repaySysSplitInfo.setThdMarginAmount(M_amount);
							repaySysSplitInfo.setFee(0d);
							repaySysSplitInfo.setLnRepayScheduleId(lnRepaySchedule.getId());
							depFixedRepayAccountService.repaySysSplit(repaySysSplitInfo);
						}

                    }
                });
            }catch (Exception e) {
                e.printStackTrace();
            }
        } finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_DEP_LOAN_RELATION.getKey());
        }
    }

    @Override
    public void normalRepaySysSplit4Seven(final NormalRepaySysSplitInfo info) {
        /**
         * 前置校验：判断订单是否成功，还款信息ln_repay是否存在
         * 加锁、事务
         * 判断是否需要免息补账，需要则计算补账金额，记入合作方补账记录表（ln_account_fill）
         * bs_revenue_trans_detail记录还款营收收入，
         * 分账计算系统还款户金额（THD_REPAY）、系统营收户金额（THD_BGW_REVENUE_YUN）、云贷营收户金额（THD_REVENUE_YUN）
         * bs_revenue_trans_detail记录云贷自主还款营收收入
         * ln_repay_schedule更新状态为已还款
         * 手续费表新增数据
         * 新增存管还款计划表（ln_deposition_repay_schedule）及明细
         * 调用系统记账
         */
        //必传入参校验
        if(info.getLnPayOrdersId() == null || info.getLoanId() == null
                || info.getRepayAmount() == null || StringUtils.isBlank(info.getOrderNo())
                || StringUtils.isBlank(info.getPartnerRepayId())){
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }
        //1、前置校验：借款表是否存在
        final LnLoan lnLoan = lnLoanMapper.selectByPrimaryKey(info.getLoanId());
        if(lnLoan == null){
            logger.info("===========【代扣还款系统分账】：校验，借款表数据未获得================");
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }
        //借款表存在，查询借款用户，确定合作方
        final LnUser lnUser = lnUserMapper.selectByPrimaryKey(lnLoan.getLnUserId());
        String partnerName = PartnerEnum.getEnumByCode(lnUser.getPartnerCode()).getName();
        //2、前置校验：判断订单是否处理中，还款信息是否存在等
        LnPayOrdersExample payOrdersExample = new LnPayOrdersExample();
        payOrdersExample.createCriteria().andIdEqualTo(info.getLnPayOrdersId())
                .andStatusEqualTo(Constants.ORDER_STATUS_SUCCESS);
        List<LnPayOrders> ordersList = lnPayOrdersMapper.selectByExample(payOrdersExample);
        if(CollectionUtils.isEmpty(ordersList)){
            logger.info("==========="+partnerName+"【主动、代扣还款系统分账】：校验，订单数据未获得================");
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }

        //查询ln_repay_schedule
        LnRepayScheduleExample repayScheduleExample = new LnRepayScheduleExample();
        repayScheduleExample.createCriteria().andPartnerRepayIdEqualTo(info.getPartnerRepayId())
                .andLoanIdEqualTo(info.getLoanId()).andStatusNotEqualTo(Constants.LN_REPAY_CANCELLED);
        List<LnRepaySchedule> repaySchedulList = lnRepayScheduleMapper.selectByExample(repayScheduleExample);
        //3、前置校验：还款计划表中是否存在，状态是否已还
        if(CollectionUtils.isEmpty(repaySchedulList)){
            logger.info("==========="+partnerName+"【主动、代扣还款系统分账】：还款计划表数据未获得================");
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }
        final LnRepaySchedule lnRepaySchedule = repaySchedulList.get(0);

        logger.info("==========="+partnerName+"【主动、代扣还款系统分账】：LnRepaySchedule.id="+lnRepaySchedule.getId()+"================");
        //最后一期账单编号
        final String lastPeriodPartnerRepayId = lnRepayScheduleMapper.selectLastPeriodPartnerRepayId(lnRepaySchedule.getLoanId());
        try {
			jsClientDaoSupport.lock(RedisLockEnum.LOCK_DEP_LOAN_RELATION.getKey());
			//查询有债权关系的债权数据
			final List<LoanRelation4TransferVO> relationList = lnLoanRelationMapper.getSevenRelationList(null,lnRepaySchedule.getLoanId());
			if(CollectionUtils.isEmpty(relationList)){
				logger.info("===========还款结果处理：无对应的债权================");
				throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
			}
			try {
				transactionTemplate.execute(new TransactionCallbackWithoutResult(){
					@Override
					protected void doInTransactionWithoutResult(TransactionStatus status) {
						if(PartnerEnum.SEVEN_DAI_SELF.getCode().equals(lnUser.getPartnerCode())){

							LnBillBizInfo billBizInfo = billBizInfoMapper.selectByPrimaryKey(info.getBillBizInfoId());//待分账账单

	                    	if( billBizInfo  == null ) {
	                    		logger.info("===========待分账账单业务列表为空================");
	                    		throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
	                    	}
	                    	
	                    	Integer repayId = billBizInfo.getRepayId();
	                    	Integer inDays = billBizInfo.getInterestDays();
                    		Date currSettleDay = billBizInfo.getRepayTime();

                    		LnRepay lnRepay = lnRepayMapper.selectByPrimaryKey(repayId);
                    		Double repay_amount = lnRepay.getDoneTotal();//还款总金额
                    		Double repay_principal_amount = 0d;  //待还款本金
                    		Double repay_interest_amount_split = 0d;  //待分账利息
	                    	LnRepayDetailExample lnRepayDetailExample = new LnRepayDetailExample();
                    		lnRepayDetailExample.createCriteria().andRepayIdEqualTo(repayId).andSubjectCodeEqualTo(LoanSubjects.SUBJECT_CODE_PRINCIPAL.getCode());
                    		List<LnRepayDetail> lnRepayDetailList = lnRepayDetailMapper.selectByExample(lnRepayDetailExample);
	                    	if(CollectionUtils.isNotEmpty(lnRepayDetailList)){
	                    		repay_principal_amount =  lnRepayDetailList.get(0).getDoneAmount();
	                    	}

                    		repay_interest_amount_split = MoneyUtil.subtract(repay_amount , repay_principal_amount).doubleValue();
                    		
                    		//补息的利率(七贷和币港湾的结算利率)---22%
							Double initRate = sysConfigService.findRatePercentByKey(Constants.SEVEN_DAI_SELF_SYS_SETTLE_RATE);
							Double rate = initRate == null ? 22 : initRate;
                    		//借款利率
	                        Double agreementRate = lnLoan.getAgreementRate();
	                        //获取最大的SerialId
	                        Integer maxSerialId = lnDepositionRepayScheduleMapper.selectMaxSerialIdByLoanId(lnLoan.getId());
                			//理财人计息本金
							Double P_amount = lnLoanRelationMapper.getRelationAmountNotRepayingByLoanId(info.getLoanId());
							
							double diffRepayPrincipal = 0d;//理财人计息本金-还款本金  差额>0 从七贷的营收户中扣除
							boolean isLastPeriodRepay = false;
							//最后一期账单还款 还款总金额=理财人计息本金+还款利息,还款本金=理财人本金
	                    	if( lastPeriodPartnerRepayId.equals(lnRepaySchedule.getPartnerRepayId() )) {
	                    		diffRepayPrincipal = CalculatorUtil.calculate("a-a", P_amount, repay_principal_amount);
	                    		repay_principal_amount = repay_principal_amount.compareTo(P_amount) < 0 ? P_amount : repay_principal_amount;
	                    		isLastPeriodRepay = true;
	                    	}
	                    	logger.info("还款Id=["+repayId+"]账单编号=["+info.getPartnerRepayId()+"]待分账的利息=["+repay_interest_amount_split+"]还款本金=["+repay_principal_amount+"]最后一期还款的还款本金补差从七贷营收户中扣除{"+diffRepayPrincipal+"}");
							//币港湾应收借款人服务费 P_amount计息本金*计天数息(inDays)*借款服务费利率/365
							Double loanServiceAmount = lnLoan.getLoanServiceRate() == null 
									? 0d :depFixedLoanRelationshipService.calInterest(inDays, lnLoan.getLoanServiceRate(), P_amount, 2);
                			//币港湾22%总利息F_amount=P_amount计息本金*计天数息(inDays)*22%/365
							Double F_amount = depFixedLoanRelationshipService.calInterest(inDays, rate, P_amount, 2);
							//查询上次还款时间
	                    	LnBillBizInfo billBizInfoLast = billBizInfoMapper.selectLastByLoanIdExcept(lnRepaySchedule.getLoanId(),billBizInfo.getId());
	                        
	                        Date lastSettleTime = null;
	                        if(billBizInfoLast == null || billBizInfoLast.getRepayTime() == null){
	                        	//未发生过还款，起息日为借款日期
	                            lastSettleTime = lnLoan.getLoanTime();
	                        }else{
	                        	//已发生过还款，则计息天数从上次还款日到计划还款日,前不计息(上次还款日在上次还款已计算日期),后计息
	                            lastSettleTime = DateUtil.addDays(billBizInfoLast.getRepayTime(), 1);
	                        }
	                        //根据债权列表，计算每笔债权的(xx%*计息本金*计息总天数/365),记录ln_finance_repay_schedule表,并求和,返回
							FinanceRepayCalVO vo = do4FinanceRepaySeven(relationList, repay_principal_amount, lnRepaySchedule, agreementRate,
										inDays, false, maxSerialId, currSettleDay, lastSettleTime);
							Double agreement_amount = vo.getAgreementSumAmount();
	                        //币港湾系统营收户金额(THD_BGW_REVENUE_7)= 币港湾22%总收益 -13%协议利率的利息-2%借款服务费
	                        Double bgw_revenue_amount = MoneyUtil.subtract(MoneyUtil.subtract(F_amount, agreement_amount).doubleValue(),loanServiceAmount).doubleValue();
							//七贷的系统营收(THD_REVENUE_7) = （还款总金额 -本金） - 币港湾22%总收益  - (还款本金补差部分)
							Double thd_revenue_7 = CalculatorUtil.calculate("a-a-a", repay_interest_amount_split , F_amount, diffRepayPrincipal);//MoneyUtil.subtract(repay_interest_amount_split, F_amount).doubleValue();
							//系统还款户金额(THD_REPAY) = P_amount计息本金*计天数息(inDays)*13%协议利率/365 + 本金+2%借款服务费
							Double thd_repay_amount = MoneyUtil.add(MoneyUtil.add(agreement_amount, repay_principal_amount).doubleValue(),
									loanServiceAmount).doubleValue();
							
							logger.info("计息本金: "+P_amount+"币港湾营收: "+ rate +"% "+F_amount+"币港湾系统营收户金额："+bgw_revenue_amount+" 七贷系统营收："+thd_revenue_7+" 系统还款户金额 : "+thd_repay_amount);
							//1、记录七贷自主还款营收收入  2、ln_repay_schedule更新状态为已还  3、新增存管还款计划表及明细 
							repayPaymentService.doNormalRepayDetail4Seven(lnLoan, lnRepaySchedule, repay_amount, info.getOrderNo(), thd_revenue_7,
									relationList.get(0).getLnSubAccountId(), thd_repay_amount, PartnerEnum.SEVEN_DAI_SELF, maxSerialId, loanServiceAmount, 
									isLastPeriodRepay, repay_principal_amount, diffRepayPrincipal);
							//系统记账
							FixedRepaySysSplitInfo repaySysSplitInfo = new FixedRepaySysSplitInfo();
							repaySysSplitInfo.setThdRepayAmount(thd_repay_amount);
							repaySysSplitInfo.setThdRevenueAmount(thd_revenue_7);
							repaySysSplitInfo.setThdBGWRevenueAmount(bgw_revenue_amount);
							repaySysSplitInfo.setPartner(PartnerEnum.getEnumByCode(lnUser.getPartnerCode()));
							repaySysSplitInfo.setThdMarginAmount(0d);
							repaySysSplitInfo.setFee(0d);
							repaySysSplitInfo.setLnRepayScheduleId(lnRepaySchedule.getId());
							depFixedRepayAccountService.repaySysSplit(repaySysSplitInfo);
						}

                    }
                });
            }catch (Exception e) {
                e.printStackTrace();
            }
        } finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_DEP_LOAN_RELATION.getKey());
        }
    }


    /**
     * 根据债权列表，计算每笔债权的（13%*计息本金*计息总天数/365），记录ln_finance_repay_schedule表，并求和，返回
     * @param relationList 债权关系列表
     * @param repayPrincipal 借款人还款本金
     * @param lnRepaySchedule 还款计划
     * @param agreementRate 借款协议利率
     * @param inDays 借款人计息天数
     * @param compensateFlag 代偿标识
     * @return
     */
    protected FinanceRepayCalVO do4FinanceRepayZSD(
            List<LoanRelation4TransferVO> relationList, Double repayPrincipal,
            LnRepaySchedule lnRepaySchedule, Double agreementRate,
            Integer inDays, boolean compensateFlag) {
        FinanceRepayCalVO financeRepayCalVO = new FinanceRepayCalVO();
        List<LnCompensateRelation> compensateList = new ArrayList<LnCompensateRelation>();//代偿列表
        Double D_amount = 0d;//协议利率计算的利息总和
        Double repayPrincipalTemp = repayPrincipal; //还款金额初始化
        for (LoanRelation4TransferVO record : relationList) {
            Double thisRepayAmount = 0d;
            //确定还款本金
            if(repayPrincipalTemp < record.getLeftAmount()){
                thisRepayAmount = repayPrincipalTemp;
            }else{
                thisRepayAmount = record.getLeftAmount();
            }
            //协议利率利息=计息本金*计天数息(inDays)*13%/365
            Double agreementAmount = depFixedLoanRelationshipService.calInterest(inDays, agreementRate, thisRepayAmount, 2);
            D_amount = MoneyUtil.add(agreementAmount, D_amount).doubleValue();
            //还款计划计息日后一天
            Date lastDay = DateUtil.addDays(lnRepaySchedule.getPlanDate(), 1);
            //生成理财人还款计划数据
            LnFinanceRepaySchedule scheduleTemp = depFixedLoanRelationshipService.generateFinanceRepaySchedule(record,thisRepayAmount, lastDay, agreementAmount,null,thisRepayAmount);
            scheduleTemp.setRepaySerial(1);
            //当天时间
            Date today = DateUtil.parseDate(DateUtil.formatYYYYMMDD(new Date()));
            scheduleTemp.setCreateTime(new Date());
            scheduleTemp.setPlanDate(today);
            scheduleTemp.setRelationId(record.getId());
            scheduleTemp.setStatus(Constants.FINANCE_REPAY_SATAE_REPAYING);
            scheduleTemp.setUpdateTime(new Date());
            lnFinanceRepayScheduleMapper.insertSelective(scheduleTemp);
            //判断是否是本金代偿,是则生成ln_compensate_relation相关信息并返回
            if(compensateFlag){
                logger.info("=============本金代偿,是则生成ln_compensate_relation========================");
                LnCompensateRelation lnCompensateRelation = new LnCompensateRelation();
                BsSysConfig config = sysConfigService.findConfigByKey(Constants.ZSD_COMPENSATE_USER_ID);
                Integer compUserId = config == null ? 0 : Integer.valueOf(config.getConfValue());
                BsHfbankUserExtExample extExample = new BsHfbankUserExtExample();
                extExample.createCriteria().andUserIdEqualTo(compUserId);
                List<BsHfbankUserExt> ext = bsHfbankUserExtMapper.selectByExample(extExample);
                if (CollectionUtils.isEmpty(ext)) {
                    throw new PTMessageException(PTMessageEnum.HF_NOT_BIND);
                }
                lnCompensateRelation.setLoanRelationId(record.getId());
                lnCompensateRelation.setCompUserId(compUserId);
                lnCompensateRelation.setPartnerCode(Constants.PROPERTY_SYMBOL_ZSD);
                lnCompensateRelation.setCompHfUserId(ext.get(0).getHfUserId());
                lnCompensateRelation.setAmount(MoneyUtil.add(thisRepayAmount, agreementAmount).doubleValue());
                lnCompensateRelation.setPrincipal(thisRepayAmount);
                lnCompensateRelation.setInterest(agreementAmount);
                lnCompensateRelation.setCreateTime(new Date());
                lnCompensateRelation.setUpdateTime(new Date());
                compensateList.add(lnCompensateRelation);
            }
            repayPrincipalTemp = MoneyUtil.subtract(repayPrincipalTemp, thisRepayAmount).doubleValue();
            if(MoneyUtil.subtract(repayPrincipalTemp, 0).doubleValue() <= 0){
                break;
            }
        }

        financeRepayCalVO.setList(compensateList);
        financeRepayCalVO.setAgreementSumAmount(D_amount);
        return financeRepayCalVO;
    }

    /**
     * 根据债权列表，计算每笔债权的（13%*计息本金*计息总天数/365），记录ln_finance_repay_schedule表，并求和，返回
     * @param relationList 债权关系列表
     * @param repayPrincipal 借款人还款本金
     * @param lnRepaySchedule 还款计划
     * @param agreementRate 借款协议利率
     * @param inDays 借款人计息天数
     * @param compensateFlag 代偿标识
     * @param maxSerial 已还的最大期数
     * @return
     */
    protected FinanceRepayCalVO do4FinanceRepayYUN(
            List<LoanRelation4TransferVO> relationList, Double repayPrincipal,
            LnRepaySchedule lnRepaySchedule, Double agreementRate,
            Integer inDays, boolean compensateFlag, Integer maxSerial) {
        FinanceRepayCalVO financeRepayCalVO = new FinanceRepayCalVO();
        List<LnCompensateRelation> compensateList = new ArrayList<LnCompensateRelation>();
        //借款协议利息总和
        Double D_amount = 0d;
        if(repayPrincipal > 0){
            /**
             * 有本金还款：
             * 1、比较匹配关系的剩余金额和本金部分大小，确定某笔债权的还款金额
             * 2、是否是转入的债权，是则，计算需还到本金部分的金额
             * 3、根据计息天数计算13%的利息
             * 5、生成ln_finance_repay_schedule记录
             * 6、记账：判断还款类型，准备记账数据（考虑初始本金产生利息-已还本金产生的利息），调用“B非代偿还款记账”或“B代偿还款记账”
             */
        	LnLoan lnLoan = lnLoanMapper.selectByPrimaryKey(lnRepaySchedule.getLoanId());
        	
        	
            Double repayPrincipalTemp = repayPrincipal; //还款金额初始化
            for (LoanRelation4TransferVO record : relationList) {
                Double thisRepayAmount = 0d;
                //确定还款本金
                if(repayPrincipalTemp < record.getLeftAmount()){
                    thisRepayAmount = repayPrincipalTemp;
                }else{
                    thisRepayAmount = record.getLeftAmount();
                }
                //协议利率利息=计息本金*计天数息(inDays)*13%/365
                Double agreementAmount = depFixedLoanRelationshipService.calInterest(inDays, agreementRate, thisRepayAmount, 2);
                D_amount = MoneyUtil.add(agreementAmount, D_amount).doubleValue();

                //还款计划计息日后一天
                Date lastDay = DateUtil.addDays(lnRepaySchedule.getPlanDate(), 1);
                //生成理财人还款计划数据
                LnFinanceRepaySchedule scheduleTemp = depFixedLoanRelationshipService.generateFinanceRepaySchedule(record,thisRepayAmount, lastDay, agreementAmount,null,thisRepayAmount);
                scheduleTemp.setRepaySerial(maxSerial+1);
                //当天时间
                Date today = DateUtil.parseDate(DateUtil.formatYYYYMMDD(new Date()));
                scheduleTemp.setCreateTime(new Date());
                scheduleTemp.setPlanDate(today);
                scheduleTemp.setRelationId(record.getId());
                scheduleTemp.setStatus(Constants.FINANCE_REPAY_SATAE_REPAYING);
                scheduleTemp.setUpdateTime(new Date());
                lnFinanceRepayScheduleMapper.insertSelective(scheduleTemp);

                //判断是否是本金代偿,是则生成ln_compensate_relation相关信息并返回
                if(compensateFlag){
                    logger.info("=============本金代偿,是则生成ln_compensate_relation========================");
                    LnCompensateRelation lnCompensateRelation = new LnCompensateRelation();
                    
//                    BsSysConfig config = sysConfigService.findConfigByKey(Constants.YUN_DAI_COMPENSATE_USER_ID);
//                    Integer compUserId = config == null ? 0 : Integer.valueOf(config.getConfValue());
//                    BsHfbankUserExtExample extExample = new BsHfbankUserExtExample();
//                    extExample.createCriteria().andUserIdEqualTo(compUserId);
//                    List<BsHfbankUserExt> ext = bsHfbankUserExtMapper.selectByExample(extExample);
//                    if (CollectionUtils.isEmpty(ext)) {
//                        throw new PTMessageException(PTMessageEnum.HF_NOT_BIND);
//                    }
                    BsUserCompensateVO vo = this.compensaterInfo(lnLoan.getLoanTime(), Constants.PROPERTY_SYMBOL_YUN_DAI_SELF);
                    if (vo==null ) {
                        throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND, Constants.PROPERTY_SYMBOL_YUN_DAI_SELF+"本金代偿代偿人信息未找到");
                    }
                    
                    lnCompensateRelation.setLoanRelationId(record.getId());
                    lnCompensateRelation.setCompUserId(vo.getId());
                    lnCompensateRelation.setPartnerCode(Constants.PROPERTY_SYMBOL_YUN_DAI_SELF);
                    lnCompensateRelation.setCompHfUserId(vo.getHfUserId());
                    lnCompensateRelation.setAmount(MoneyUtil.add(thisRepayAmount, agreementAmount).doubleValue());
                    lnCompensateRelation.setPrincipal(thisRepayAmount);
                    lnCompensateRelation.setInterest(agreementAmount);
                    lnCompensateRelation.setCreateTime(new Date());
                    lnCompensateRelation.setUpdateTime(new Date());
                    compensateList.add(lnCompensateRelation);
                }
                repayPrincipalTemp = MoneyUtil.subtract(repayPrincipalTemp, thisRepayAmount).doubleValue();
                if(MoneyUtil.subtract(repayPrincipalTemp, 0).doubleValue() <= 0){
                    break;
                }
            }
        }else{
            for (LoanRelation4TransferVO record : relationList) {
                //协议利率利息=计息本金*计天数息(inDays)*13%/365
                Double agreementAmount = depFixedLoanRelationshipService.calInterest(inDays, agreementRate, record.getLeftAmount(), 2);
                D_amount = MoneyUtil.add(agreementAmount, D_amount).doubleValue();

                //还款计划计息日后一天
                Date lastDay = DateUtil.addDays(lnRepaySchedule.getPlanDate(), 1);

                //生成理财人还款计划数据
                LnFinanceRepaySchedule scheduleTemp = depFixedLoanRelationshipService.generateFinanceRepaySchedule(record,record.getLeftAmount(), lastDay, agreementAmount,null,0d);
                scheduleTemp.setRepaySerial(maxSerial+1);
                //当天时间
                Date today = DateUtil.parseDate(DateUtil.formatYYYYMMDD(new Date()));
                scheduleTemp.setCreateTime(new Date());
                scheduleTemp.setPlanDate(today);
                scheduleTemp.setRelationId(record.getId());
                scheduleTemp.setStatus(Constants.FINANCE_REPAY_SATAE_REPAYING);
                scheduleTemp.setUpdateTime(new Date());
                lnFinanceRepayScheduleMapper.insertSelective(scheduleTemp);
            }
        }
        financeRepayCalVO.setList(compensateList);
        financeRepayCalVO.setAgreementSumAmount(D_amount);
        return financeRepayCalVO;
    }
    
    
    public BsUserCompensateVO compensaterInfo(Date loanTime,String partnerCode){
    	//COMPENSATE_USER_INFO = {"YUN_DAI": {"SEPARATE_DATE": "2018-03-15","COMPENSATE_USER_ID": "1035895|2000055"},"7_DAI": {"SEPARATE_DATE": "2018-03-15","COMPENSATE_USER_ID": "1037195|2000055"}}
    	logger.info("查询代偿人信息：loanTime = "+loanTime+";partnerCode="+partnerCode);
    	
    	HashMap<String, String> compensateUserInfo = (HashMap<String, String>) jsClientDaoSupport.getHashMapFromObj("compensate_user_info");
    	
    	if (compensateUserInfo == null || compensateUserInfo.isEmpty()) {
    		BsSysConfig config = sysConfigService.findConfigByKey(Constants.COMPENSATE_USER_INFO);
        	if (config == null ) {
        		logger.error("查询不到代偿人信息COMPENSATE_USER_INFO");
        		throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
    		}
        	JSONObject jsonObject = JSONObject.fromObject(config.getConfValue());
        	
        	String[] partners = {"YUN_DAI","7_DAI"};
        	for (int i = 0; i < partners.length; i++) {
        		JSONObject yunDaiInfo= (JSONObject) jsonObject.get(partners[i]);
        		String separateDate =  yunDaiInfo.getString("SEPARATE_DATE");
        		String compenstateUser =  yunDaiInfo.getString("COMPENSATE_USER_ID");
        		String[]  compenstateUsers =  compenstateUser.split("\\|");
        		if (compenstateUsers.length != 2) {
        			 throw new PTMessageException(PTMessageEnum.COMPENSATE_USER_INFO_DATA_DEFINED_ERROR);
				}
        		String oldCompenstateUserId = compenstateUsers[0];
        		String newCompenstateUserId = compenstateUsers[1];
        		
        		if (StringUtil.isBlank(oldCompenstateUserId)) {
        			throw new PTMessageException(PTMessageEnum.OLD_COMPENSATE_USER_ID_DATA_EMPTY_ERROR);
				}
        		
        		if (StringUtil.isBlank(newCompenstateUserId)) {
        			throw new PTMessageException(PTMessageEnum.NEW_COMPENSATE_USER_ID_DATA_EMPTY_ERROR);
				}
        		
        		BsUser bsUserOld = bsUserMapper.selectByPrimaryKey(Integer.valueOf(oldCompenstateUserId));
        		if (bsUserOld == null) {
        			throw new PTMessageException(PTMessageEnum.OLD_COMPENSATE_USER_INFO_NOT_EXIST.getCode(),PTMessageEnum.OLD_COMPENSATE_USER_INFO_NOT_EXIST.getMessage()+"_"+partners[i]);
				}
        		
        		BsHfbankUserExtExample extExample = new BsHfbankUserExtExample();
                extExample.createCriteria().andUserIdEqualTo(bsUserOld.getId());
                List<BsHfbankUserExt> extOld = bsHfbankUserExtMapper.selectByExample(extExample);
                if (CollectionUtils.isEmpty(extOld)) {
                    throw new PTMessageException(PTMessageEnum.HF_NOT_BIND);
                }
                BsUserCompensateVO voOld = new BsUserCompensateVO();
                voOld.setId(bsUserOld.getId());
                voOld.setMobile(bsUserOld.getMobile());
                voOld.setUserName(bsUserOld.getUserName());
                voOld.setIdCard(bsUserOld.getIdCard());
                voOld.setHfUserId(extOld.get(0).getHfUserId());
                voOld.setSeparateDate(DateUtil.parse(separateDate, DateUtil.DATE_FORMAT));
        		compensateUserInfo.put(partners[i]+"_OLD_COMPENSATE", JSONObject.fromObject(voOld).toString());
        		
        		BsUser bsUserNew = bsUserMapper.selectByPrimaryKey(Integer.valueOf(newCompenstateUserId));
        		if (bsUserNew == null) {
        			throw new PTMessageException(PTMessageEnum.NEW_COMPENSATE_USER_INFO_NOT_EXIST.getCode(),PTMessageEnum.NEW_COMPENSATE_USER_INFO_NOT_EXIST.getMessage()+"_"+partners[i]);
				}
        		
        		BsHfbankUserExtExample extNewExample = new BsHfbankUserExtExample();
                extNewExample.createCriteria().andUserIdEqualTo(bsUserNew.getId());
                List<BsHfbankUserExt> extNew = bsHfbankUserExtMapper.selectByExample(extNewExample);
                if (CollectionUtils.isEmpty(extNew)) {
                    throw new PTMessageException(PTMessageEnum.HF_NOT_BIND);
                }
                BsUserCompensateVO voNew = new BsUserCompensateVO();
                voNew.setId(bsUserNew.getId());
                voNew.setMobile(bsUserNew.getMobile());
                voNew.setUserName(bsUserNew.getUserName());
                voNew.setIdCard(bsUserNew.getIdCard());
                voNew.setHfUserId(extNew.get(0).getHfUserId());
                voNew.setSeparateDate(DateUtil.parse(separateDate, DateUtil.DATE_FORMAT));
        		compensateUserInfo.put(partners[i]+"_NEW_COMPENSATE", JSONObject.fromObject(voNew).toString());
        		
			}
        	jsClientDaoSupport.addObjOfHashMap(compensateUserInfo, "compensate_user_info", 3600*12);
		}
    	
    	
    	if (partnerCode.equals(Constants.PROPERTY_SYMBOL_YUN_DAI_SELF)) {
    		String yunDaiOldCompensate = compensateUserInfo.get("YUN_DAI_OLD_COMPENSATE");
    		JSONObject jsonObject = JSONObject.fromObject(yunDaiOldCompensate);
    		BsUserCompensateVO yunOldBsUserCompensateVO = (BsUserCompensateVO) JSONObject.toBean(jsonObject, BsUserCompensateVO.class);
    		
    		if (loanTime.compareTo(yunOldBsUserCompensateVO.getSeparateDate())<0) {
				return yunOldBsUserCompensateVO;
			}else {
				String yunDaiNewCompensate =  compensateUserInfo.get("YUN_DAI_NEW_COMPENSATE");
				JSONObject jsonObjectNew = JSONObject.fromObject(yunDaiNewCompensate);
				BsUserCompensateVO yunNewBsUserCompensateVO = (BsUserCompensateVO) JSONObject.toBean(jsonObjectNew, BsUserCompensateVO.class);
				return yunNewBsUserCompensateVO;
			}
    		
    	}else if (partnerCode.equals(Constants.PROPERTY_SYMBOL_7_DAI_SELF)) {

    		String oldCompensate = compensateUserInfo.get("7_DAI_OLD_COMPENSATE");
    		JSONObject jsonObject = JSONObject.fromObject(oldCompensate);
    		BsUserCompensateVO oldBsUserCompensateVO = (BsUserCompensateVO) JSONObject.toBean(jsonObject, BsUserCompensateVO.class);
    		
    		if (loanTime.compareTo(oldBsUserCompensateVO.getSeparateDate())<0) {
				return oldBsUserCompensateVO;
			}else {
				String newCompensate =  compensateUserInfo.get("7_DAI_NEW_COMPENSATE");
				JSONObject jsonObjectNew = JSONObject.fromObject(newCompensate);
				BsUserCompensateVO newBsUserCompensateVO = (BsUserCompensateVO) JSONObject.toBean(jsonObjectNew, BsUserCompensateVO.class);
				return newBsUserCompensateVO;
			}
    	}
    	return null;

    }
    

    /**
     * 根据还款编号和科目查询还款详情表中对应科目的还款金额
     * @param repayScheduleId
     * @param subjectCode
     * @return
     */
    private Double getRepayDetailAmount(Integer repayScheduleId, LoanSubjects subjectCode) {
        LnRepayScheduleDetailExample example = new LnRepayScheduleDetailExample();
        example.createCriteria().andPlanIdEqualTo(repayScheduleId).andSubjectCodeEqualTo(subjectCode.getCode());
        List<LnRepayScheduleDetail> list = lnRepayScheduleDetailMapper.selectByExample(example);
        if(CollectionUtils.isNotEmpty(list)){
            return list.get(0).getPlanAmount();
        }
        return 0d;
    }

    @Override
    public void compensateRepaySysSplit(final CompensateRepaySysSplitInfo info) {
        /**
         * 前置校验：判断订单是否成功，还款信息ln_repay是否存在
         * 加锁、事务
         * 分账计算系统还款户金额（THD_REPAY）、系统营收户金额（THD_BGW_REVENUE_YUN）
         * 修改代偿明细
         * ln_repay_schedule更新还款标识为处理完成
         * 新增存管还款计划表（ln_deposition_repay_schedule）及明细
         * 调用系统记账
         */

        //必传入参校验
        if(info.getLoanId() == null || StringUtils.isBlank(info.getPartnerLoanId())
                || StringUtils.isBlank(info.getPartnerRepayId())
                || info.getRepayAmount() == null
                || info.getLnCompensateDetailId() == null ){
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }

        //前置校验：1、借款表是否存在
        final LnLoan lnLoan = lnLoanMapper.selectByPrimaryKey(info.getLoanId());
        if(lnLoan == null){
            logger.info("===========【代偿还款系统分账】：校验，借款表数据未获得================");
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }
        //借款表存在，查询借款用户，确定合作方
        final LnUser lnUser = lnUserMapper.selectByPrimaryKey(lnLoan.getLnUserId());
        PartnerEnum partnerEnum = PartnerEnum.getEnumByCode(lnUser.getPartnerCode());
        if(!PartnerEnum.YUN_DAI_SELF.getCode().equals(lnUser.getPartnerCode())
        		&&  !PartnerEnum.ZSD.getCode().equals(lnUser.getPartnerCode())){
        	 logger.info("===========【代偿还款系统分账】：合作方校验失败================");
             throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }
        final String partnerName = partnerEnum.getName();

        //前置校验：2、校验是否存在初始状态的代偿明细
        LnCompensateDetailExample example = new LnCompensateDetailExample();
        example.createCriteria().andIdEqualTo(info.getLnCompensateDetailId())
                .andStatusEqualTo(Constants.COMPENSATE_REPAYS_STATUS_INIT);
        List<LnCompensateDetail> list = lnCompensateDetailMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(list)){
            logger.info("===========【"+partnerName+"代偿还款系统分账】：代偿明细数据未获得================");
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }
        final LnCompensateDetail lnCompensateDetail = list.get(0);
        //获得实际代偿的利息本金
        final Double repayPrincipal = lnCompensateDetail.getPrincipal();
        final String compensateTypeString = repayPrincipal>0 ? "本金": "利息";


        //查询ln_repay_schedule
        LnRepayScheduleExample repayScheduleExample = new LnRepayScheduleExample();
        repayScheduleExample.createCriteria().andPartnerRepayIdEqualTo(info.getPartnerRepayId())
                .andLoanIdEqualTo(info.getLoanId()).andStatusNotEqualTo(Constants.LN_REPAY_CANCELLED);
        List<LnRepaySchedule> repaySchedulList = lnRepayScheduleMapper.selectByExample(repayScheduleExample);
        //前置校验：3、还款计划表中是否存在
        if(CollectionUtils.isEmpty(repaySchedulList)){
            logger.info("===========【"+partnerName+compensateTypeString+"代偿还款系统分账】：还款计划表数据未获得================");
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }
        final LnRepaySchedule lnRepaySchedule = repaySchedulList.get(0);

        //计划表还款本金校验
        final Double repayPrincipalSchedul = getRepayDetailAmount(lnRepaySchedule.getId(), LoanSubjects.SUBJECT_CODE_PRINCIPAL);
        if(repayPrincipalSchedul.compareTo(repayPrincipal) != 0){
            logger.info("===========【"+partnerName+compensateTypeString+"代偿还款系统分账】：代偿本金："+repayPrincipal+"，计划表应还本金："+repayPrincipalSchedul+"================");
            //告警表添加数据
            specialJnlService.warn4FailNoSMS(MoneyUtil.subtract(repayPrincipalSchedul,repayPrincipal).doubleValue(),
                    "【"+partnerName+compensateTypeString+"代偿还款系统分账】：代偿本金："+repayPrincipal+"，计划表应还本金："+repayPrincipalSchedul,
                    "代偿明细记录编号："+info.getLnCompensateDetailId().toString(),
                    "【"+partnerName+compensateTypeString+"代偿还款系统分账】");

            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }
        try {
            if(repayPrincipal > 0){
                jsClientDaoSupport.lock(RedisLockEnum.LOCK_DEP_LOAN_RELATION.getKey());
            }
            //查询有债权关系的债权数据
            final List<LoanRelation4TransferVO> relationList = lnLoanRelationMapper.getRelationList(null,lnRepaySchedule.getLoanId());
            if(CollectionUtils.isEmpty(relationList)){
                logger.info("===========还款结果处理：无对应的债权================");
                throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
            }
            try {
                transactionTemplate.execute(new TransactionCallbackWithoutResult(){
                    @Override
                    protected void doInTransactionWithoutResult(TransactionStatus status) {
                        if(PartnerEnum.YUN_DAI_SELF.getCode().equals(lnUser.getPartnerCode())){
                            logger.info("===========【"+partnerName+","+compensateTypeString+"代偿还款系统分账】开始：代偿本金："+repayPrincipal+"，代偿利息："+lnCompensateDetail.getInterest()+"================");
                            //查询上次还利息时间
                            LnRepayScheduleVO repayScheduleVO = lnRepayScheduleMapper.selectVoByLoanId(lnRepaySchedule.getLoanId(), null,lnRepaySchedule.getId());
                            //借款计息天数
                            Integer inDays = 0;
                            if(repayScheduleVO.getPlanDate() == null){
                                inDays = DateUtil.getDiffeDay(lnRepaySchedule.getPlanDate(), repayScheduleVO.getLoanDate())+1;
                            }else{
                                inDays = DateUtil.getDiffeDay(lnRepaySchedule.getPlanDate(), repayScheduleVO.getPlanDate());
                            }

                            //补息的利率(云贷自主和币港湾的结算利率)---24%
                            Double initRate = null;
                            initRate = lnLoan.getBgwSettleRate() != null?lnLoan.getBgwSettleRate() : sysConfigService.findRatePercentByKey(Constants.YUN_DAI_SELF_SYS_SETTLE_RATE);
                            Double rate = initRate == null ? 24 : initRate;

                            //获得实际代偿的利息
                            Double C_amount = lnCompensateDetail.getInterest();

                            //计息本金
                            Double P_amount = repayPrincipal == 0 ?
                                    lnLoanRelationMapper.getRelationAmountNotRepayingByLoanId(info.getLoanId()):repayPrincipal;

                            //获得应代偿币港湾24%总利息F_amount=P_amount计息本金*计天数息(inDays)*24%/365
                            Double F_amount = depFixedLoanRelationshipService.calInterest(inDays, rate, P_amount, 2);
                            logger.info("=============【"+partnerName+","+compensateTypeString+"代偿还款系统分账】应还币港湾"+rate+"%总利息F="+F_amount+"===============");

                            if(F_amount.compareTo(C_amount) != 0){
                                //利息代偿，实付和应收的利息不等，告警
                                //告警表添加数据
                                specialJnlService.warn4FailNoSMS(MoneyUtil.subtract(C_amount,F_amount).doubleValue(),
                                        "【"+partnerName+","+compensateTypeString+"代偿还款系统分账】利息还款总和- bgw"+rate+"%总利息 ="+C_amount+"-"+F_amount+"="+MoneyUtil.subtract(C_amount,F_amount).doubleValue(),
                                        "代偿明细记录编号："+info.getLnCompensateDetailId().toString(),
                                        "【"+partnerName+","+compensateTypeString+"代偿还款系统分账】");
                            }

                            Double agreementRate = lnLoan.getAgreementRate();

                            //根据债权列表，计算每笔债权的（13%*计息本金*计息总天数/365），记录ln_finance_repay_schedule表，并求和，返回
                            FinanceRepayCalVO vo = new FinanceRepayCalVO();
                            //获取最大的SerialId
                            Integer maxSerialId = lnDepositionRepayScheduleMapper.selectMaxSerialIdByLoanId(lnLoan.getId());
                            if(repayPrincipal > 0){
                                //本金代偿
                                vo = do4FinanceRepayYUN(relationList,repayPrincipal,lnRepaySchedule,
                                        agreementRate,inDays,true,maxSerialId);
                            }else{
                                vo = do4FinanceRepayYUN(relationList,repayPrincipal,lnRepaySchedule,
                                        agreementRate,inDays,false,maxSerialId);
                            }
                            Double agreement_amount = vo.getAgreementSumAmount();

                            //币港湾应收借款人服务费 P_amount计息本金*计天数息(inDays)*借款服务费利率/365
    						Double loanServiceAmount = lnLoan.getLoanServiceRate() == null 
    								? 0d :depFixedLoanRelationshipService.calInterest(inDays, lnLoan.getLoanServiceRate(), P_amount, 2);
                            //系统营收户金额（THD_BGW_REVENUE_YUN）= 币港湾24%总利息 - 13%协议利率的利息-2%借款服务费
                            Double bgw_revenue_amount = MoneyUtil.subtract(MoneyUtil.subtract(F_amount,agreement_amount).doubleValue(),loanServiceAmount).doubleValue();
                            //合作方（云贷）营收=云贷代偿利息-应付利息，非0 ，都记营收明细记录表和系统云贷营收账户
                            Double yun_revenue_amount = MoneyUtil.subtract(C_amount,F_amount).doubleValue();
                            
                            //系统还款户金额（THD_REPAY）= P_amount计息本金*计天数息(inDays)*13%协议利率/365 + 本金+2%借款服务费
							Double repay_amount = MoneyUtil.add(MoneyUtil.add(agreement_amount, repayPrincipal).doubleValue(),loanServiceAmount).doubleValue();

                            //bs_revenue_trans_detail记录合作方还款营收收入
                    		BsRevenueTransDetail bsRevenueTransDetail = new BsRevenueTransDetail();
                    		bsRevenueTransDetail.setPartnerCode(lnUser.getPartnerCode());
                    		bsRevenueTransDetail.setTransType(Constants.REVENUE_TYPE_REPAY_INCOME);
                    		bsRevenueTransDetail.setLoanId(lnLoan.getId());
                    		bsRevenueTransDetail.setRepaySerial(lnRepaySchedule.getSerialId());
                    		bsRevenueTransDetail.setRepayScheduleId(lnRepaySchedule.getId());
                    		bsRevenueTransDetail.setRepayAmount(lnCompensateDetail.getTotal());
                    		bsRevenueTransDetail.setRevenueAmount(yun_revenue_amount);
                    		bsRevenueTransDetail.setRepayScheduleId(lnRepaySchedule.getId());
                    		bsRevenueTransDetail.setCreateTime(new Date());
                    		bsRevenueTransDetail.setUpdateTime(new Date());
                    		bsRevenueTransDetailMapper.insertSelective(bsRevenueTransDetail);
                    		
                            //修改代偿明细
                            LnCompensateDetail detailTemp = new LnCompensateDetail();
                            detailTemp.setId(lnCompensateDetail.getId());
                            detailTemp.setStatus(Constants.COMPENSATE_REPAYS_STATUS_SUCC);
                            detailTemp.setUpdateTime(new Date());
                            lnCompensateDetailMapper.updateByPrimaryKeySelective(detailTemp);

                            //ln_repay_schedule更新
                            LnRepaySchedule lnRepayScheduleTemp = new LnRepaySchedule();
                            lnRepayScheduleTemp.setId(lnRepaySchedule.getId());
                            lnRepayScheduleTemp.setFinishTime(new Date());
                            lnRepayScheduleTemp.setUpdateTime(new Date());
                            lnRepayScheduleTemp.setStatus(Constants.LN_REPAY_LATE_NOT);
                            //lnRepayScheduleTemp.setReturnFlag(Constants.LN_REPAY_RETURN_FLAG_FINISH);
                            lnRepayScheduleMapper.updateByPrimaryKeySelective(lnRepayScheduleTemp);

                            //存管还款计划表
                            LnDepositionRepaySchedule schedul = new LnDepositionRepaySchedule();
                            schedul.setLnUserId(lnLoan.getLnUserId());
                            schedul.setLoanId(lnLoan.getId());
                            schedul.setPartnerRepayId(lnRepaySchedule.getPartnerRepayId());
                            schedul.setSerialId(maxSerialId+1);
                            schedul.setPlanDate(lnRepaySchedule.getPlanDate());
                            schedul.setPlanTotal(repay_amount);
                            schedul.setStatus(Constants.LN_REPAY_STATUS_INIT);
                            schedul.setReturnFlag(Constants.DEP_REPAY_RETURN_FLAG_DF_PENDING);
                            if(repayPrincipal > 0){
                            	//云贷本金代偿
                            	schedul.setRepayType(Constants.LN_DEP_REPAY_SCHEDULE_REPAY_TYPE_LATE_NOT);
                            }else{
                            	schedul.setRepayType(Constants.LN_DEP_REPAY_SCHEDULE_REPAY_TYPE_NORMAL_REPAY);
                            }

                            schedul.setCreateTime(new Date());
                            schedul.setUpdateTime(new Date());
                            lnDepositionRepayScheduleMapper.insertSelective(schedul);

                            //存管还款计划表明细
                            LnDepositionRepayScheduleDetail detailInterest = new LnDepositionRepayScheduleDetail();
                            detailInterest.setPlanId(schedul.getId());
                            detailInterest.setPlanAmount(agreement_amount);
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
                            
                            if(loanServiceAmount != null && loanServiceAmount > 0){
                            	//存管还款计划表明细-借款服务费
                				LnDepositionRepayScheduleDetail detailLoanService = new LnDepositionRepayScheduleDetail();
                				detailLoanService.setPlanId(schedul.getId());
                				detailLoanService.setPlanAmount(loanServiceAmount);
                				detailLoanService.setSubjectCode(LoanSubjects.SUBJECT_CODE_LOAN_SERVICE_FEE.getCode());
                				detailLoanService.setCreateTime(new Date());
                				detailLoanService.setUpdateTime(new Date());
                				lnDepositionRepayScheduleDetailMapper.insertSelective(detailLoanService);
                            }
                            if(repayPrincipal > 0){
                                //本金代偿，记录ln_compensate_relation
                                List<LnCompensateRelation> list = vo.getList();
                                if(CollectionUtils.isNotEmpty(list)){
                                    for (LnCompensateRelation compensateRelation : list) {
                                        compensateRelation.setDepPlanId(schedul.getId());
                                        compensateRelation.setInterestDay(inDays);
                                        lnCompensateRelationMapper.insertSelective(compensateRelation);
                                    }
                                }
                            }

                            //系统记账
                            FixedRepaySysSplitInfo repaySysSplitInfo = new FixedRepaySysSplitInfo();
                            repaySysSplitInfo.setThdRepayAmount(repay_amount);
                            repaySysSplitInfo.setThdRevenueAmount(yun_revenue_amount);
                            repaySysSplitInfo.setThdBGWRevenueAmount(bgw_revenue_amount);
                            repaySysSplitInfo.setPartner(PartnerEnum.getEnumByCode(lnUser.getPartnerCode()));
                            repaySysSplitInfo.setThdMarginAmount(0d);
                            repaySysSplitInfo.setFee(0d);
                            repaySysSplitInfo.setLnRepayScheduleId(lnRepaySchedule.getId());
                            depFixedRepayAccountService.repaySysSplit(repaySysSplitInfo);
                        }else if(PartnerEnum.ZSD.getCode().equals(lnUser.getPartnerCode())){
                            if(repayPrincipal.compareTo(0d) == 0){
                                logger.info("===========赞时贷【代偿还款系统分账】：代偿还款本金为0================");
                                throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR,"代偿还款本金为0");
                            }

                            //补息的利率(赞时贷和币港湾的结算利率)---20%
                            Double initRate = sysConfigService.findRatePercentByKey(Constants.ZSD_SYS_SETTLE_RATE);
                            Double rate = initRate == null ? 20 : initRate;
                            //代偿还款本金
                            Double repayPrincipal = lnCompensateDetail.getPrincipal();
                            //getRepayDetailAmount(lnRepaySchedule.getId(), LoanSubjects.SUBJECT_CODE_PRINCIPAL);
                            //计息本金
                            Double P_amount = lnLoanRelationMapper.getRelationAmountNotRepayingByLoanId(info.getLoanId());
                            if(repayPrincipal.compareTo(P_amount) != 0){
                                specialJnlService.warn4FailNoSMS(MoneyUtil.subtract(P_amount,repayPrincipal).doubleValue(),
                                        "赞时贷【代偿还款系统分账】还款本金- 应收本金 ="+repayPrincipal+"-"+P_amount+"="+MoneyUtil.subtract(repayPrincipal,P_amount).doubleValue(),
                                        "代偿明细记录编号："+info.getLnCompensateDetailId().toString(),
                                        "赞时贷【代偿还款系统分账】");
                                logger.info("===========赞时贷【主动、代扣还款系统分账】：计划还款本金与理财人计息总本金不符================");
                                throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "计划还款本金与理财人计息总本金不符");
                            }

                            //计息天数
                            Integer inDays = DateUtil.getDiffeDay(lnRepaySchedule.getPlanDate(), DateUtil.parseDate(DateUtil.formatYYYYMMDD(lnLoan.getLoanTime())))+1;

                            //获得币港湾20%总利息F_amount=P_amount计息本金*计天数息(inDays)*20%/365
                            Double F_amount = depFixedLoanRelationshipService.calInterest(inDays, rate, repayPrincipal, 2);
                            //代偿总金额-代偿还款本金
                            Double C_amount = MoneyUtil.subtract(lnCompensateDetail.getTotal(),repayPrincipal).doubleValue();
                            //赞时贷营收 初始化
                            Double K_amount = 0d;
                            if(C_amount < F_amount){
                                //免息补账金额 = |计划还款总金额-本金 - 币港湾15%总利息| = |C-F|
                                LnAccountFillDetail lnAccountFillDetail = new LnAccountFillDetail();
                                lnAccountFillDetail.setPartnerCode(PartnerEnum.ZSD.getCode());
                                lnAccountFillDetail.setFillType(Constants.FILL_DETAIL_FILL_TYPE_INTEREST);
                                lnAccountFillDetail.setFillDate(new Date());
                                lnAccountFillDetail.setAmount(MoneyUtil.subtract(F_amount, C_amount).doubleValue());
                                lnAccountFillDetail.setRelativeNo(lnRepaySchedule.getId().toString());
                                lnAccountFillDetail.setStatus(Constants.FILL_DETAIL_FILL_STATUS_SUCCESS);
                                lnAccountFillDetail.setCreateTime(new Date());
                                lnAccountFillDetail.setUpdateTime(new Date());
                                lnAccountFillDetailMapper.insertSelective(lnAccountFillDetail);

                            }
                            //赞时贷营收 = 代偿总金额 - 币港湾20%总利息
                            K_amount = MoneyUtil.subtract(C_amount, F_amount).doubleValue();

                            //借款利率
                            Double agreementRate = lnLoan.getAgreementRate();

                            //根据债权列表，计算每笔债权的（13%*计息本金*计息总天数/365），记录ln_finance_repay_schedule表，并求和，返回
                            FinanceRepayCalVO vo = do4FinanceRepayZSD(relationList,repayPrincipal,lnRepaySchedule,
                                    agreementRate,inDays,true);
                            Double agreement_amount = vo.getAgreementSumAmount();
                            //币港湾营收系统（THD_BGW_REVENUE_ZSD）= 币港湾15%总利息 - 13%协议利率的利息
                            Double bgw_revenue_amount = MoneyUtil.subtract(F_amount,agreement_amount).doubleValue();
                            //系统还款户金额（THD_REPAY）= P_amount计息本金*计天数息(inDays)*13%协议利率/365 + 本金
                            Double repay_amount = MoneyUtil.add(agreement_amount, repayPrincipal).doubleValue();

                            //修改代偿明细
                            LnCompensateDetail detailTemp = new LnCompensateDetail();
                            detailTemp.setId(lnCompensateDetail.getId());
                            detailTemp.setStatus(Constants.COMPENSATE_REPAYS_STATUS_SUCC);
                            detailTemp.setUpdateTime(new Date());
                            lnCompensateDetailMapper.updateByPrimaryKeySelective(detailTemp);

                            //ln_repay_schedule更新
                            LnRepaySchedule lnRepayScheduleTemp = new LnRepaySchedule();
                            lnRepayScheduleTemp.setId(lnRepaySchedule.getId());
                            lnRepayScheduleTemp.setFinishTime(new Date());
                            lnRepayScheduleTemp.setUpdateTime(new Date());
                            lnRepayScheduleTemp.setStatus(Constants.LN_REPAY_LATE_NOT);
                            lnRepayScheduleMapper.updateByPrimaryKeySelective(lnRepayScheduleTemp);

                            //存管还款计划表
                            LnDepositionRepaySchedule schedul = new LnDepositionRepaySchedule();
                            schedul.setLnUserId(lnLoan.getLnUserId());
                            schedul.setLoanId(lnLoan.getId());
                            schedul.setPartnerRepayId(lnRepaySchedule.getPartnerRepayId());
                            schedul.setSerialId(1);
                            schedul.setPlanDate(lnRepaySchedule.getPlanDate());
                            schedul.setPlanTotal(MoneyUtil.add(repayPrincipal,agreement_amount).doubleValue());
                            schedul.setStatus(Constants.LN_REPAY_STATUS_INIT);
                            schedul.setReturnFlag(Constants.DEP_REPAY_RETURN_FLAG_DF_PENDING);
                            schedul.setRepayType(Constants.LN_DEP_REPAY_SCHEDULE_REPAY_TYPE_LATE_NOT);
                            schedul.setCreateTime(new Date());
                            schedul.setUpdateTime(new Date());
                            lnDepositionRepayScheduleMapper.insertSelective(schedul);

                            //存管还款计划表明细
                            LnDepositionRepayScheduleDetail detailInterest = new LnDepositionRepayScheduleDetail();
                            detailInterest.setPlanId(schedul.getId());
                            detailInterest.setPlanAmount(agreement_amount);
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

                            if(K_amount.compareTo(0d) != 0){
	                            //bs_revenue_trans_detail记录合作方还款营收收入
	                    		BsRevenueTransDetail bsRevenueTransDetail = new BsRevenueTransDetail();
	                    		bsRevenueTransDetail.setPartnerCode(PartnerEnum.ZSD.getCode());
	                    		bsRevenueTransDetail.setTransType(Constants.REVENUE_TYPE_REPAY_INCOME);
	                    		bsRevenueTransDetail.setLoanId(lnLoan.getId());
	                    		bsRevenueTransDetail.setRepaySerial(lnRepaySchedule.getSerialId());
	                    		bsRevenueTransDetail.setRepayScheduleId(lnRepaySchedule.getId());
	                    		bsRevenueTransDetail.setRepayAmount(lnCompensateDetail.getTotal());
	                    		bsRevenueTransDetail.setRevenueAmount(K_amount);
	                    		bsRevenueTransDetail.setRepayScheduleId(lnRepaySchedule.getId());
	                    		bsRevenueTransDetail.setCreateTime(new Date());
	                    		bsRevenueTransDetail.setUpdateTime(new Date());
	                    		bsRevenueTransDetailMapper.insertSelective(bsRevenueTransDetail);
	                    	}

                            if(repayPrincipal > 0){
                                //本金代偿，记录ln_compensate_relation
                                List<LnCompensateRelation> list = vo.getList();
                                if(CollectionUtils.isNotEmpty(list)){
                                    for (LnCompensateRelation compensateRelation : list) {
                                        compensateRelation.setDepPlanId(schedul.getId());
                                        compensateRelation.setInterestDay(inDays);
                                        lnCompensateRelationMapper.insertSelective(compensateRelation);
                                    }
                                }
                            }

                            //系统记账
                            FixedRepaySysSplitInfo repaySysSplitInfo = new FixedRepaySysSplitInfo();
                            repaySysSplitInfo.setThdRepayAmount(repay_amount);
                            repaySysSplitInfo.setThdRevenueAmount(K_amount);
                            repaySysSplitInfo.setThdBGWRevenueAmount(bgw_revenue_amount);
                            repaySysSplitInfo.setPartner(PartnerEnum.ZSD);
                            repaySysSplitInfo.setThdMarginAmount(0d);
                            repaySysSplitInfo.setFee(0d);
                            repaySysSplitInfo.setLnRepayScheduleId(lnRepaySchedule.getId());
                            depFixedRepayAccountService.repaySysSplit(repaySysSplitInfo);
                        }
                    }
                });
            }catch (Exception e) {
                e.printStackTrace();
            }
        } finally {
            if(repayPrincipal > 0){
                jsClientDaoSupport.unlock(RedisLockEnum.LOCK_DEP_LOAN_RELATION.getKey());
            }
        }
    }

    @Override
    public void repayResultQuery(G2BReqMsg_DafyLoan_QueryRepayResult req,
                                 G2BResMsg_DafyLoan_QueryRepayResult res) {
        List<LnRepay> lnRepayList = lnRepayMapper.selectByParOrderNoAndFlag(req.getOrderNo(),PartnerEnum.YUN_DAI_SELF.getCode());
        if(CollectionUtils.isEmpty(lnRepayList)){
            res.setOrderNo(req.getOrderNo());
            res.setChannel(Constants.ORDER_CHANNEL_BAOFOO);
            res.setResultCode(Constants.ORDER_TRANS_CODE_FAIL);
            res.setResultMsg("还款结果查询未找到还款信息");
            return;
        }
        LnRepay lnRepay = lnRepayList.get(0);
        res.setOrderNo(req.getOrderNo());
        res.setBgwOrderNo(lnRepay.getBgwOrderNo());
        res.setChannel(Constants.ORDER_CHANNEL_BAOFOO);
        //根据还款订单状态返回支付状态
        LnPayOrdersExample example = new LnPayOrdersExample();
        example.createCriteria().andPartnerCodeEqualTo(PartnerEnum.YUN_DAI_SELF.getCode()).andOrderNoEqualTo(lnRepay.getPayOrderNo());
        List<LnPayOrders> lnPayOrderList = lnPayOrdersMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(lnPayOrderList)){
            res.setOrderNo(req.getOrderNo());
            res.setChannel(Constants.ORDER_CHANNEL_BAOFOO);
            res.setResultCode(Constants.ORDER_TRANS_CODE_FAIL);
            res.setResultMsg("增计划还款结果查询未找到orderNo="+req.getOrderNo()+"的还款订单信息");
            return;
        }
        LnPayOrders lnPayOrder = lnPayOrderList.get(0);
        if(lnPayOrder.getStatus() == Constants.ORDER_STATUS_SUCCESS){
            res.setResultCode("SUCCESS");
            res.setResultMsg(LoanStatus.REPAY_STATUS_REPAIED.getDescription());
        }else if(lnPayOrder.getStatus() == Constants.ORDER_STATUS_PAYING
                || lnPayOrder.getStatus() == Constants.ORDER_STATUS_CREATE){
            res.setResultCode("PROCESS");
            res.setResultMsg(LoanStatus.REPAY_STATUS_PAYING.getDescription());
        }else if(lnPayOrder.getStatus() == Constants.ORDER_STATUS_PRE_SUCC){
            if (DateUtil.getDiffeMinute(lnPayOrder.getUpdateTime()) <= 15) {
                res.setResultCode("PROCESS");
                res.setResultMsg(LoanStatus.REPAY_STATUS_PAYING.getDescription());
            }else {
                res.setResultCode("FAIL");
                res.setResultMsg("该订单已失效");
            }
        }else{
            res.setResultCode("FAIL");
            res.setResultMsg(StringUtil.isEmpty(lnPayOrder.getReturnMsg())?LoanStatus.REPAY_STATUS_FAIL.getDescription():lnPayOrder.getReturnMsg());
            //res.setResultMsg(LoanStatus.REPAY_STATUS_FAIL.getDescription());
        }
        res.setFinishTime(new Date());

    }

    @Override
    public Integer queryInterestDays(String partnerRepayId) {

        Integer day = -1;

        LnRepayScheduleExample repayScheduleExample = new LnRepayScheduleExample();
        repayScheduleExample.createCriteria().andPartnerRepayIdEqualTo(partnerRepayId);
        List<LnRepaySchedule> repaySchedulList = lnRepayScheduleMapper.selectByExample(repayScheduleExample);
        if(CollectionUtils.isEmpty(repaySchedulList)){
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }
        LnRepaySchedule lnRepaySchedule = repaySchedulList.get(0);


        //查询上次还利息时间
        LnRepayScheduleVO repayScheduleVO = lnRepayScheduleMapper.selectVoByLoanId(lnRepaySchedule.getLoanId(),lnRepaySchedule.getPlanDate(),lnRepaySchedule.getId());

        if(repayScheduleVO.getPlanDate() == null){
            //未发生利息还款，计算目前总的计息时间 = 此次planDate - loanDate + 1
            day = DateUtil.getDiffeDay(lnRepaySchedule.getPlanDate(), repayScheduleVO.getLoanDate())+1;
        }else{
            day = DateUtil.getDiffeDay(lnRepaySchedule.getPlanDate(), repayScheduleVO.getPlanDate());
        }


        return day;
    }

    @Override
    public void lateRepayNotice(final LnCompensate lnCompensate, final List<LnCompensateDetail> detailList) {

        PartnerEnum partner = PartnerEnum.getEnumByCode(lnCompensate.getPartnerCode());

        Double totalMount = 0.0d;
        for (LnCompensateDetail lnCompensateDetail : detailList) {
            totalMount = MoneyUtil.add(totalMount, lnCompensateDetail.getTotal()).doubleValue();
        }

        if (lnCompensate.getTotalMount().compareTo(totalMount) != 0) {
            // 校验代偿总金额与详情信息中总金额不一致，做告警处理
            logger.info(">>>" + partner.getName()+"代偿单号["+lnCompensate.getOrderNo()+"]代偿总金额[" + lnCompensate.getTotalMount() +"]与明细总金额["+ totalMount +"]不一致！<<<");
            specialJnlService.warnDevelop4Fail(0d,partner.getName()+"代偿单号["+lnCompensate.getOrderNo()+"]代偿总金额[" + lnCompensate.getTotalMount() +"]与明细总金额["+ totalMount +"]不一致",
                    lnCompensate.getOrderNo(), "代偿还款处理",false);
        }

        /**
         * 1、数据校验
         * 2、代偿通知及明细记录ln_compensate、ln_compensate_detail
         * 3、循环调用还款结果处理
         * 4、3协议生成及通知云贷ln_compensate_agreement_address
         */
        try {
            jsClientDaoSupport.lock(RedisLockEnum.LOCK_DEP_FIXED_LATE_REPAY_NOTICE.getKey() + lnCompensate.getPartnerCode());
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {

                    //校验数据
                    LnCompensateExample compensateExample = new LnCompensateExample();
                    compensateExample.createCriteria().andOrderNoEqualTo(lnCompensate.getOrderNo())
                            .andPartnerCodeEqualTo(lnCompensate.getPartnerCode());
                    //单号重复
                    List<LnCompensate> list = lnCompensateMapper.selectByExample(compensateExample);
                    if (CollectionUtils.isNotEmpty(list)) {
                        throw new PTMessageException(PTMessageEnum.COMPENSATE_NOTICE_SAME);
                    }
                    LnCompensateExample compensateExample4Pay = new LnCompensateExample();
                    compensateExample4Pay.createCriteria()
                            .andPayOrderNoEqualTo(lnCompensate.getPayOrderNo())
                            .andPartnerCodeEqualTo(lnCompensate.getPartnerCode());
                    //支付单号重复
                    List<LnCompensate> list4Pay = lnCompensateMapper.selectByExample(compensateExample4Pay);
                    if (CollectionUtils.isNotEmpty(list4Pay)) {
                        throw new PTMessageException(PTMessageEnum.COMPENSATE_NOTICE_SAME);
                    }
                    lnCompensateMapper.insertSelective(lnCompensate);

                    for (LnCompensateDetail lnCompensateDetail : detailList) {
                        lnCompensateDetail.setCompensateId(lnCompensate.getId());
                        lnCompensateDetail.setStatus(Constants.COMPENSATE_REPAYS_STATUS_INIT);
                        lnCompensateDetail.setUpdateTime(new Date());
                        lnCompensateDetail.setCreateTime(new Date());
                        lnCompensateDetailMapper.insertSelective(lnCompensateDetail);
                    }
                }
            });

            if (PartnerEnum.YUN_DAI_SELF.getCode().equals(partner.getCode())) {
                LateRepayNoticeProcess process = new LateRepayNoticeProcess();
                process.setDepFixedRepayPaymentService(this);
                process.setLnCompensate(lnCompensate);
                process.setDetailList(detailList);
                new Thread(process).start();
            } else if (PartnerEnum.SEVEN_DAI_SELF.getCode().equals(partner.getCode())) {
                lateRepayNotice(lnCompensate, detailList, partner);
            }

        } finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_DEP_FIXED_LATE_REPAY_NOTICE.getKey() + lnCompensate.getPartnerCode());
        }
    }

    /**
     * 1.代偿还款结果处理（云贷/7贷）
     * 2.生成代偿协议（云贷）
     * 3.通知代偿协议地址信息（云贷）
     *
     * @param lnCompensate
     * @param detailList
     * @param partner
     */
    public void lateRepayNotice(final LnCompensate lnCompensate, final List<LnCompensateDetail> detailList, final PartnerEnum partner) {

        for (LnCompensateDetail lnCompensateDetail : detailList) {
        	String redisKey_IdCard = null;
        	String redisKey_PartnerCode = null;
        	Integer loanId = null;
            try {
                /**
                 * 循环判断Ln_repay_schedule状态是否为REPAID，
                 * 			是则做重复还款处理，return；
                 * 		否则需要把对应记录状态改为LATE_NOT，
                 * 			调用“B还款结果处理”；（仅代偿SUCC）
                 */
                LnUserExample lnUserExample = new LnUserExample();
                lnUserExample.createCriteria().andPartnerUserIdEqualTo(lnCompensateDetail.getPartnerUserId())
                        .andPartnerCodeEqualTo(lnCompensate.getPartnerCode());
                List<LnUser> lnUserList = lnUserMapper.selectByExample(lnUserExample);
                if(CollectionUtils.isEmpty(lnUserList)){
                    logger.info("==========="+partner.getName()+"【代偿还款】借款用户数据未获得================");
                    throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
                }
                redisKey_IdCard = lnUserList.get(0).getIdCard(); //身份证号
                redisKey_PartnerCode = lnUserList.get(0).getPartnerCode(); //合作方
                jsClientDaoSupport.lock( RedisLockEnum.LOCK_REPAY_SELF.getKey() + redisKey_PartnerCode + redisKey_IdCard );
                logger.info( "=========合作方代偿还款-同代扣还款，加锁："+ RedisLockEnum.LOCK_REPAY_SELF.getKey() + redisKey_PartnerCode + redisKey_IdCard +"=======" );

                
                LnLoanExample loanExample = new LnLoanExample();
                loanExample.createCriteria().andPartnerLoanIdEqualTo(lnCompensateDetail.getPartnerLoanId())
                        .andLnUserIdEqualTo(lnUserList.get(0).getId()).andStatusEqualTo(LoanStatus.LOAN_STATUS_PAIED.getCode());
                List<LnLoan>  lnLoanList= lnLoanMapper.selectByExample(loanExample);
                if(CollectionUtils.isEmpty(lnLoanList)){
                    logger.info("==========="+partner.getName()+"【代偿还款】lnCompensateDetail.id="+lnCompensateDetail.getId()+"未查询到借款信息=============");
                    continue;
                }
                LnLoan lnLoan = lnLoanList.get(0);
                //借款状态置代偿处理中(还款处理中)
                loanId = lnLoan.getId();
                LnLoan loanUpdate = new LnLoan();
                loanUpdate.setId(loanId);
                loanUpdate.setIsRepaying(Constants.DEP_REPAY_REPAYING);
                loanUpdate.setUpdateTime(new Date());
                lnLoanMapper.updateByPrimaryKeySelective(loanUpdate);

                //前置校验：2、还款计划表中是否存在，状态是否已还
                LnRepayScheduleExample repayScheduleExample = new LnRepayScheduleExample();
                repayScheduleExample.createCriteria().andPartnerRepayIdEqualTo(lnCompensateDetail.getPartnerRepayId())
                        .andLoanIdEqualTo(lnLoan.getId()).andStatusNotEqualTo(Constants.LN_REPAY_CANCELLED);
                List<LnRepaySchedule> repaySchedulList = lnRepayScheduleMapper.selectByExample(repayScheduleExample);

                if(CollectionUtils.isEmpty(repaySchedulList)){
                    logger.info("==========="+partner.getName()+"【代偿还款】还款计划表数据未获得================");
                    throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
                }
                Date today = DateUtil.parseDate(DateUtil.formatYYYYMMDD(new Date()));
                LnRepaySchedule lnRepaySchedule = repaySchedulList.get(0);
                //判断是否已还款(账单表、还款记录表)，若已还款，ln_repeat_repay_record记库，调用“重复还款入营收账记账”
                LnRepayExample repayExample = new LnRepayExample();
                repayExample.createCriteria().andRepayPlanIdEqualTo(lnRepaySchedule.getId()).andLoanIdEqualTo(lnRepaySchedule.getLoanId())
                        .andStatusEqualTo(Constants.LN_REPAY_PAY_STATUS_REPAIED);
                List<LnRepay> repayList = lnRepayMapper.selectByExample(repayExample);
                if((PartnerEnum.YUN_DAI_SELF.getCode().equals(partner.getCode()) && CollectionUtils.isNotEmpty(repayList)) ||
                        Constants.LN_REPAY_REPAIED.equals(lnRepaySchedule.getStatus()) ||
                        Constants.LN_REPAY_LATE_REPAIED.equals(lnRepaySchedule.getStatus())){
                    //调 重复还款处理
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
                    repeatRepayProcess(lnRepeatRepayRecord);

                    //修改代偿明细
                    LnCompensateDetail detailTemp = new LnCompensateDetail();
                    detailTemp.setId(lnCompensateDetail.getId());
                    detailTemp.setStatus(Constants.COMPENSATE_REPAYS_STATUS_REPEAT);
                    detailTemp.setUpdateTime(new Date());
                    lnCompensateDetailMapper.updateByPrimaryKeySelective(detailTemp);

                } else if(Constants.LN_REPAY_LATE_NOT.equals(lnRepaySchedule.getStatus())){
                    //已代偿的账单不进行处理，做告警处理
                    logger.info(">>>"+partner.getName()+"代偿单号["+lnCompensate.getOrderNo()+"]对账单编号["+lnRepaySchedule.getId()+"]代偿重复！<<<");
                    specialJnlService.warn4Fail(0d,partner.getName()+"代偿单号["+lnCompensate.getOrderNo()+"]对账单编号["+lnRepaySchedule.getId()+"]代偿重复",
                            lnCompensate.getOrderNo(), "代偿重复",false);
                } else if(Constants.LN_REPAY_CANCELLED.equals(lnRepaySchedule.getStatus())){
                    //已废除的账单不进行处理，做告警处理
                    logger.info(">>>代偿单号["+lnCompensate.getOrderNo()+"]对账单编号["+lnRepaySchedule.getId()+"]lnRepaySchedule已废除！<<<");
                    specialJnlService.warn4Fail(0d,partner.getName()+"代偿单号["+lnCompensate.getOrderNo()+"]对账单编号["+lnRepaySchedule.getId()+"]还款计划已废除",
                            lnCompensate.getOrderNo(), "还款计划已废除",false);
                } else{
                    //循环调用代偿系统分账处理
                    CompensateRepaySysSplitInfo info = new CompensateRepaySysSplitInfo();
                    info.setLoanId(lnLoan.getId());
                    info.setPartnerRepayId(lnCompensateDetail.getPartnerRepayId());
                    info.setPartnerLoanId(lnLoan.getPartnerLoanId());
                    info.setOrderNo(lnCompensate.getOrderNo());
                    info.setRepayAmount(lnCompensateDetail.getTotal());
                    info.setLnCompensateDetailId(lnCompensateDetail.getId());

                    if(PartnerEnum.YUN_DAI_SELF.getCode().equals(partner.getCode())){
                        //云贷代偿分账处理
                        compensateRepaySysSplit(info);
                    }else if(PartnerEnum.SEVEN_DAI_SELF.getCode().equals(partner.getCode())){
                        //七贷分账处理
                        compRepaySysSplit4Seven(info);
                        // 合规化，代偿后7贷协议不再生成
                        	/*if(lnCompensateDetail.getPrincipal() > 0){
                                //七贷本金代偿，生成协议
                                agreementList.add(lnCompensateDetail);
                            }*/
                    }
                }

            } catch (Exception e) {
                logger.error(partner.getName()+"代偿明细编号["+lnCompensateDetail.getId()+"]处理异常", e);
                specialJnlService.warn4Fail(lnCompensateDetail.getTotal(),partner.getName()+"代偿明细编号["+lnCompensateDetail.getId()+"]处理异常",
                        lnCompensate.getOrderNo(), "代偿明细处理",false);
            } finally {
            	jsClientDaoSupport.unlock( RedisLockEnum.LOCK_REPAY_SELF.getKey() + redisKey_PartnerCode + redisKey_IdCard );
                logger.info("=========合作方代偿还款-同代扣还款，解锁：" + RedisLockEnum.LOCK_REPAY_SELF.getKey() + redisKey_PartnerCode + redisKey_IdCard + "=======");
                //本次代偿业务结束时，借款记录设为可还标识
                if( loanId != null ) {
                	LnLoan lnLoanUpdate = new LnLoan();
                	lnLoanUpdate.setId(loanId);
                	lnLoanUpdate.setIsRepaying(Constants.DEP_REPAY_AVAILABLE);
                	lnLoanUpdate.setUpdateTime(new Date());
                	lnLoanMapper.updateByPrimaryKeySelective(lnLoanUpdate);
                }
            }
        }
    }

    @Override
    public void noticePartner2Dsd(final LnPayOrders lnPayOrder, final String repayResultMsg) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                logger.info(">>>通知赞时贷还款通知：[入参]" + JSON.toJSONString(lnPayOrder) + "|errorMsg=" + repayResultMsg + "<<<");
                B2GReqMsg_ZsdNotice_NoticeRepay zsdNoticeRepay = new B2GReqMsg_ZsdNotice_NoticeRepay();
                //通过订单号在还款信息列表其中一条记录中 获取合作方订单号
                LnRepayExample example = new LnRepayExample();
                example.createCriteria().andPayOrderNoEqualTo(lnPayOrder.getOrderNo());
                List<LnRepay> repayList = lnRepayMapper.selectByExample(example);
                if(CollectionUtils.isEmpty(repayList)) {
                    logger.error("通知赞时贷时未找到BgwOrderNo=" + lnPayOrder.getOrderNo() + "的还款信息记录");
                }
                LnRepay repay = repayList.get(0);
                zsdNoticeRepay.setOrderNo(repay.getPartnerOrderNo());
                zsdNoticeRepay.setPayChannel(lnPayOrder.getChannel());

                if(repay.getLoanId() == null){
                    logger.error("还款通知赞时贷时找不到的借款信息id为："+JSONObject.fromObject(repay.getLoanId()).toString());
                }
                LnLoan lnLoan = lnLoanMapper.selectByPrimaryKey(repay.getLoanId());
                zsdNoticeRepay.setLoanId(lnLoan.getPartnerLoanId());

                if(lnPayOrder.getStatus() == Constants.ORDER_STATUS_SUCCESS){
                    zsdNoticeRepay.setRepayResultCode("SUCCESS");
                    zsdNoticeRepay.setRepayResultMsg(LoanStatus.REPAY_STATUS_REPAIED.getDescription());
                }else if(lnPayOrder.getStatus() == Constants.ORDER_STATUS_PAYING){
                    zsdNoticeRepay.setRepayResultCode("PROCESS");
                    zsdNoticeRepay.setRepayResultMsg(LoanStatus.REPAY_STATUS_PAYING.getDescription());
                }else{
                    zsdNoticeRepay.setRepayResultCode("FAIL");
                    zsdNoticeRepay.setRepayResultMsg(StringUtil.isEmpty(lnPayOrder.getReturnMsg())?LoanStatus.REPAY_STATUS_FAIL.getDescription():lnPayOrder.getReturnMsg());
                }

                B2GResMsg_ZsdNotice_NoticeRepay res = null;
                LnRepay repayTemp = new LnRepay();
                try {
                    res = zsdNoticeService.noticeRepay(zsdNoticeRepay);
                    if (res != null && ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
                        repayTemp.setInformStatus(LoanStatus.NOTICE_STATUS_SUCCESS.getCode());
                    } else {
                        repayTemp.setInformStatus(LoanStatus.NOTICE_STATUS_FAIL.getCode());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    repayTemp.setInformStatus(LoanStatus.NOTICE_STATUS_FAIL.getCode());
                }
                LnRepayExample lnRepayExample = new LnRepayExample();
                lnRepayExample.createCriteria().andPayOrderNoEqualTo(lnPayOrder.getOrderNo());
                repayTemp.setUpdateTime(new Date());
                lnRepayMapper.updateByExampleSelective(repayTemp, lnRepayExample);
            }
        }).start();
    }

    @Override
    public void zsdRepayResultQuery(G2BReqMsg_ZsdRepay_QueryRepayResult req,
                                    G2BResMsg_ZsdRepay_QueryRepayResult res) {

        List<LnRepay> lnRepayList = lnRepayMapper.selectByParOrderNoAndFlag(req.getOrderNo(),PartnerEnum.ZSD.getCode());
        if(CollectionUtils.isEmpty(lnRepayList)) {
            res.setChannel(Constants.CHANNEL_BAOFOO);
            res.setRepayResultCode(Constants.ORDER_TRANS_CODE_FAIL);
            res.setRepayResultMsg("赞时贷还款结果查询未找到还款信息");
            return;
        }
        LnRepay lnRepay = lnRepayList.get(0);
        res.setChannel(Constants.CHANNEL_BAOFOO);

        //根据还款订单状态返回支付状态
        LnPayOrdersExample example = new LnPayOrdersExample();
        example.createCriteria().andPartnerCodeEqualTo(PartnerEnum.ZSD.getCode()).andOrderNoEqualTo(lnRepay.getPayOrderNo());
        List<LnPayOrders> lnPayOrderList = lnPayOrdersMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(lnPayOrderList)){
            res.setLoanId(lnRepay.getLoanId().toString());
            res.setChannel(Constants.CHANNEL_BAOFOO);
            res.setRepayResultCode(Constants.ORDER_TRANS_CODE_FAIL);
            res.setRepayResultMsg("赞时贷还款结果查询未找到orderNo="+req.getOrderNo()+"的还款订单信息");
            return;
        }
        LnPayOrders lnPayOrder = lnPayOrderList.get(0);
        if(lnPayOrder.getStatus() == Constants.ORDER_STATUS_SUCCESS){
            res.setRepayResultCode("SUCCESS");
            res.setRepayResultMsg(LoanStatus.REPAY_STATUS_REPAIED.getDescription());
        }else if(lnPayOrder.getStatus() == Constants.ORDER_STATUS_PAYING
                || lnPayOrder.getStatus() == Constants.ORDER_STATUS_CREATE){
            res.setRepayResultCode("PROCESS");
            res.setRepayResultMsg(LoanStatus.REPAY_STATUS_PAYING.getDescription());
        }else if(lnPayOrder.getStatus() == Constants.ORDER_STATUS_PRE_SUCC){
            if (DateUtil.getDiffeMinute(lnPayOrder.getUpdateTime()) <= 15) {
                res.setRepayResultCode("PROCESS");
                res.setRepayResultMsg(LoanStatus.REPAY_STATUS_PAYING.getDescription());
            }else {
                res.setRepayResultCode("FAIL");
                res.setRepayResultMsg("该订单已失效");
            }
        }else{
            res.setRepayResultCode("FAIL");
            res.setRepayResultMsg(StringUtil.isEmpty(lnPayOrder.getReturnMsg())?LoanStatus.REPAY_STATUS_FAIL.getDescription():lnPayOrder.getReturnMsg());
        }
    }

    @Override
    public String doZsdPreRepay(final G2BReqMsg_ZsdRepay_PreRepay req) {
        //订单是否重复
        LnRepayExample repayExample = new LnRepayExample();
        repayExample.createCriteria().andPartnerOrderNoEqualTo(req.getOrderNo());
        List<LnRepay> repayList = lnRepayMapper.selectByExample(repayExample);
        if (CollectionUtils.isNotEmpty(repayList)) {
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "订单号重复");
        }
        //账单不为空
        if(CollectionUtils.isEmpty(req.getRepayments())) {
            throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND, "还款预下单赞时贷传入账单为空");
        }
        //借款用户是否存在
        LnUserExample lnUserExample = new LnUserExample();
        lnUserExample.createCriteria().andPartnerCodeEqualTo(PartnerEnum.ZSD.getCode())
                .andPartnerUserIdEqualTo(req.getUserId());
        List<LnUser> lnUsers = lnUserMapper.selectByExample(lnUserExample);
        if (CollectionUtils.isEmpty(lnUsers)){
            throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND, "找不到借款用户信息");
        }
        final LnUser lnUser = lnUsers.get(0);
        //借款是否存在
        LnLoanExample loanExample = new LnLoanExample();
        loanExample.createCriteria().andPartnerLoanIdEqualTo(req.getLoanId())
                .andLnUserIdEqualTo(lnUser.getId()).andStatusEqualTo(LoanStatus.LOAN_STATUS_PAIED.getCode());
        List<LnLoan> loans = lnLoanMapper.selectByExample(loanExample);
        if (CollectionUtils.isEmpty(loans)) {
            throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND, "找不到借款["+req.getLoanId()+"]信息");
        }
        final LnLoan lnLoan = loans.get(0);
        //该笔借款是否有还款正在进行中
        if(lnLoan.getIsRepaying().equals(Constants.DEP_REPAY_REPAYING)){
            throw new PTMessageException(PTMessageEnum.DEP_REPAY_REPAYING);
        }
        //代扣银行是否存在
        List<Pay19BankVO> pay19BankVOs = bs19payBankMapper.selectByChanelPayTypeBankCode(Constants.CHANNEL_BAOFOO, Constants.PAY_TYPE_HOLD, req.getBankCode());
        if(CollectionUtils.isEmpty(pay19BankVOs)){
            throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND, "银行编码["+req.getBankCode()+"]不支持");
        }
        final Pay19BankVO pay19BankVO = pay19BankVOs.get(0);
        final String orderNo = Util.generateOrderNo4BaoFoo(8);
        final String bgwOrderNo = Util.generateSysOrderNo(Constants.BGW_ORDER_NUMBER_GENERATE_WITHHOLD);
        transactionTemplate.execute(new TransactionCallbackWithoutResult(){
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                for (Repayment zsdRepaymentReq : req.getRepayments()) {

                    //是否有对应账单
                    LnRepayScheduleExample lnRepayScheduleExample = new LnRepayScheduleExample();
                    lnRepayScheduleExample.createCriteria().andPartnerRepayIdEqualTo(zsdRepaymentReq.getRepayId()).andLoanIdEqualTo(lnLoan.getId());
                    List<LnRepaySchedule> lnRepaySchedules = lnRepayScheduleMapper.selectByExample(lnRepayScheduleExample);
                    //预下单记录存库
                    LnRepay repay = new LnRepay();
                    if (CollectionUtils.isNotEmpty(lnRepaySchedules)) {
                        //有对应账单时存账单id，无对应账单时为空
                        LnRepaySchedule lnRepaySchedule = lnRepaySchedules.get(0);
                        if(lnRepaySchedule.getReturnFlag().equals(Constants.LN_REPAY_RETURN_FLAG_PENDING) ||
                                lnRepaySchedule.getReturnFlag().equals(Constants.LN_REPAY_RETURN_FLAG_PROCESS)){
                            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "账单[" + zsdRepaymentReq.getRepayId() + "]正在处理中");
                        }
                        repay.setRepayPlanId(lnRepaySchedule.getId());
                        String scheduleStatus = lnRepaySchedule.getStatus();
                        if(LoanStatus.REPAY_SCHEDULE_STATUS_REPAIED.getCode().equals(scheduleStatus) ||
                                LoanStatus.REPAY_SCHEDULE_STATUS_LATE_REPAIED.getCode().equals(scheduleStatus) ||
                                LoanStatus.REPAY_SCHEDULE_STATUS_CANCELLED.getCode().equals(scheduleStatus)){
                            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "账单[" + zsdRepaymentReq.getRepayId() + "]已存在还款或已废除");
                        }
                        //是否已存在重复还款记录
                        repeatRepayJudge(lnRepaySchedule.getId(), zsdRepaymentReq.getRepayId());
                    } else {
                        throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "账单[" + zsdRepaymentReq.getRepayId() + "]不存在");
                    }
                    repay.setStatus(LoanStatus.REPAY_STATUS_INIT.getCode());
                    repay.setUpdateTime(new Date());
                    repay.setBgwOrderNo(bgwOrderNo);
                    repay.setCreateTime(new Date());
                    repay.setDoneTotal(MoneyUtil.divide(zsdRepaymentReq.getTotal().doubleValue(), 100).doubleValue());
                    repay.setLnUserId(lnUser.getId());
                    repay.setPayOrderNo(orderNo);
                    repay.setLoanId(lnLoan.getId());
                    repay.setPartnerOrderNo(req.getOrderNo());
                    repay.setInformStatus(LoanStatus.NOTICE_STATUS_INIT.getCode());
                    lnRepayMapper.insertSelective(repay);

                    LnRepayDetail lnRepayDetail = new LnRepayDetail();
                    lnRepayDetail.setUpdateTime(new Date());
                    lnRepayDetail.setCreateTime(new Date());
                    lnRepayDetail.setRepayId(repay.getId());
                    // 本金
                    Double principalDoneAmount = zsdRepaymentReq.getPrincipal() != null ?
                            MoneyUtil.divide(zsdRepaymentReq.getPrincipal().doubleValue(), 100).doubleValue() : 0d;
                    lnRepayDetail.setDoneAmount(principalDoneAmount);
                    lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_PRINCIPAL.getCode());
                    lnRepayDetailMapper.insertSelective(lnRepayDetail);
                    // 利息
                    Double interestDoneAmount = zsdRepaymentReq.getInterest() != null ?
                            MoneyUtil.divide(zsdRepaymentReq.getInterest().doubleValue(), 100).doubleValue() : 0d;
                    lnRepayDetail.setDoneAmount(interestDoneAmount);
                    lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_INTEREST.getCode());
                    lnRepayDetailMapper.insertSelective(lnRepayDetail);
                    // 滞纳金
                    Double lateFeeDoneAmount = zsdRepaymentReq.getLateFee() != null ?
                            MoneyUtil.divide(zsdRepaymentReq.getLateFee().doubleValue(), 100).doubleValue() : 0d;
                    lnRepayDetail.setDoneAmount(lateFeeDoneAmount);
                    lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_LATE_FEE.getCode());
                    lnRepayDetailMapper.insertSelective(lnRepayDetail);
                    // 手续费
                    Double serviceFeeDoneAmount = zsdRepaymentReq.getServiceFee() != null ?
                            MoneyUtil.divide(zsdRepaymentReq.getServiceFee().doubleValue(), 100).doubleValue() : 0d;
                    lnRepayDetail.setDoneAmount(serviceFeeDoneAmount);
                    lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_SERVICE_FEE.getCode());
                    lnRepayDetailMapper.insertSelective(lnRepayDetail);
                    // 平台服务费
                    Double pServiceFeeDoneAmount = zsdRepaymentReq.getPlatformServiceFee() != null ?
                            MoneyUtil.divide(zsdRepaymentReq.getPlatformServiceFee().doubleValue(), 100).doubleValue() : 0d;
                    lnRepayDetail.setDoneAmount(pServiceFeeDoneAmount);
                    lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_PLATFORM_SERVICE_FEE.getCode());
                    lnRepayDetailMapper.insertSelective(lnRepayDetail);
                    // 信息认证费
                    Double infoCertifiedFeeDoneAmount = zsdRepaymentReq.getInfoCertifiedFee() != null ?
                            MoneyUtil.divide(zsdRepaymentReq.getInfoCertifiedFee().doubleValue(), 100).doubleValue() : 0d;
                    lnRepayDetail.setDoneAmount(infoCertifiedFeeDoneAmount);
                    lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_INFO_CERTIFIED_FEE.getCode());
                    lnRepayDetailMapper.insertSelective(lnRepayDetail);
                    // 风控服务费
                    Double riskManageServiceFeeDoneAmount = zsdRepaymentReq.getRiskManageServiceFee() != null ?
                            MoneyUtil.divide(zsdRepaymentReq.getRiskManageServiceFee().doubleValue(), 100).doubleValue() : 0d;
                    lnRepayDetail.setDoneAmount(riskManageServiceFeeDoneAmount);
                    lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_RISK_MANAGE_SERVICE_FEE.getCode());
                    lnRepayDetailMapper.insertSelective(lnRepayDetail);
                    // 代收通道费
                    Double collectionChannelFeeDoneAmount = zsdRepaymentReq.getCollectionChannelFee() != null ?
                            MoneyUtil.divide(zsdRepaymentReq.getCollectionChannelFee().doubleValue(), 100).doubleValue() : 0d;
                    lnRepayDetail.setDoneAmount(collectionChannelFeeDoneAmount);
                    lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_COLLECTION_CHANNEL_FEE.getCode());
                    lnRepayDetailMapper.insertSelective(lnRepayDetail);
                    // 其他费用
                    Double otherDoneAmount = zsdRepaymentReq.getOther() != null ?
                            MoneyUtil.divide(zsdRepaymentReq.getOther().doubleValue(), 100).doubleValue() : 0d;
                    lnRepayDetail.setDoneAmount(otherDoneAmount);
                    lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_OTHER.getCode());
                    lnRepayDetailMapper.insertSelective(lnRepayDetail);
                }
            }
        });
        //根据借款支付订单号，查询子账户id
        LnPayOrdersExample ordersExample = new LnPayOrdersExample();
        ordersExample.createCriteria().andOrderNoEqualTo(lnLoan.getPayOrderNo()).andPartnerCodeEqualTo(PartnerEnum.ZSD.getCode());
        List<LnPayOrders> loanOrders = lnPayOrdersMapper.selectByExample(ordersExample);
        //记录ln_pay_orders,ln_pay_orders_jnl表
        LnPayOrders lnPayOrders = new LnPayOrders();
        lnPayOrders.setCreateTime(new Date());
        lnPayOrders.setAccountType(1);   // 1用户
        lnPayOrders.setAmount(MoneyUtil.divide(req.getTotalAmount(), "100").doubleValue());
        lnPayOrders.setBankCardNo(req.getBankCard());
        Integer bankId = pay19BankVO.getBankId();
        lnPayOrders.setBankId(bankId);
        lnPayOrders.setBankName(pay19BankVO.getBankName());
        lnPayOrders.setChannel(Constants.CHANNEL_BAOFOO);
        lnPayOrders.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DK.getCode());
        lnPayOrders.setIdCard(req.getIdCard());
        lnPayOrders.setLnUserId(lnUser.getId());
        lnPayOrders.setMobile(req.getMobile());
        lnPayOrders.setMoneyType(0);     // 0人民币
        lnPayOrders.setOrderNo(orderNo);
        lnPayOrders.setPartnerCode(PartnerEnum.ZSD.getCode());
        lnPayOrders.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_INIT.getCode()));
        lnPayOrders.setSubAccountId(loanOrders.get(0).getSubAccountId());
        lnPayOrders.setTransType(LoanStatus.TRANS_TYPE_REPAY.getCode());
        lnPayOrders.setUpdateTime(new Date());
        lnPayOrders.setUserName(req.getCardHolder());
        //查询支付渠道代扣优先商户号信息
    	BsPaymentChannel channelInfo = paymentChannelMapper.selectFisrtDK();
    	if(channelInfo != null){
    		//修改订单表payment_channel_id
    		lnPayOrders.setPaymentChannelId(channelInfo.getId());
    	}
        lnPayOrdersMapper.insertSelective(lnPayOrders);

        LnPayOrdersJnl lnPayOrdersJnl = new LnPayOrdersJnl();
        lnPayOrdersJnl.setSubAccountId(loanOrders.get(0).getSubAccountId());
        lnPayOrdersJnl.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DK.getCode());
        lnPayOrdersJnl.setCreateTime(new Date());
        lnPayOrdersJnl.setOrderId(lnPayOrders.getId());
        lnPayOrdersJnl.setOrderNo(lnPayOrders.getOrderNo());
        lnPayOrdersJnl.setTransAmount(lnPayOrders.getAmount());
        lnPayOrdersJnl.setTransCode(LoanStatus.ORDERS_JNL_STATUS_INIT.getCode());
        lnPayOrdersJnl.setUserId(lnUser.getId());
        lnPayOrdersJnl.setSysTime(new Date());
        lnPayOrdersJnlMapper.insertSelective(lnPayOrdersJnl);
        //发送短信
        String sendCode = null;
        String returnMsg = null;
        try {
            sendCode = smsService.sendRepayPreToken(req.getMobile(), PartnerEnum.ZSD.getCode());
        }catch (Exception e){
            sendCode = Constants.SEND_CODE_ERROR;
            if(e instanceof PTMessageException){
                returnMsg = ((PTMessageException) e).getErrMessage();
            }
        }
        if(Constants.SEND_CODE_ERROR.equals(sendCode)){
            //置订单表为预下单失败
            LnPayOrders ordersTemp = new LnPayOrders();
            ordersTemp.setId(lnPayOrders.getId());
            ordersTemp.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_PRE_FAIL.getCode()));
            ordersTemp.setReturnMsg(returnMsg == null ? "短信发送失败" : returnMsg);
            ordersTemp.setUpdateTime(new Date());
            lnPayOrdersMapper.updateByPrimaryKeySelective(ordersTemp);

            //新增订单流水
            lnPayOrdersJnl = new LnPayOrdersJnl();
            lnPayOrdersJnl.setSubAccountId(loanOrders.get(0).getSubAccountId());
            lnPayOrdersJnl.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DK.getCode());
            lnPayOrdersJnl.setCreateTime(new Date());
            lnPayOrdersJnl.setOrderId(lnPayOrders.getId());
            lnPayOrdersJnl.setOrderNo(lnPayOrders.getOrderNo());
            lnPayOrdersJnl.setTransAmount(lnPayOrders.getAmount());
            lnPayOrdersJnl.setTransCode(LoanStatus.ORDERS_JNL_STATUS_PRE_FAIL.getCode());
            lnPayOrdersJnl.setUserId(lnUser.getId());
            lnPayOrdersJnl.setSysTime(new Date());
            lnPayOrdersJnlMapper.insertSelective(lnPayOrdersJnl);

            //还款表置失败
            LnRepay repayTemp = new LnRepay();
            repayTemp.setUpdateTime(new Date());
            repayTemp.setStatus(LoanStatus.REPAY_STATUS_FAIL.getCode());
            LnRepayExample lnRepayExample = new LnRepayExample();
            lnRepayExample.createCriteria().andPayOrderNoEqualTo(orderNo).andLnUserIdEqualTo(lnUser.getId());
            lnRepayMapper.updateByExampleSelective(repayTemp, lnRepayExample);
            throw new PTMessageException(PTMessageEnum.USER_ORDER_PRE_FAIL, ordersTemp.getReturnMsg());
        }else {
            //置订单表为预下单成功
            LnPayOrders ordersTemp = new LnPayOrders();
            ordersTemp.setId(lnPayOrders.getId());
            ordersTemp.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_PRE_SUCCESS.getCode()));
            ordersTemp.setUpdateTime(new Date());
            lnPayOrdersMapper.updateByPrimaryKeySelective(ordersTemp);

            //新增订单流水
            lnPayOrdersJnl = new LnPayOrdersJnl();
            lnPayOrdersJnl.setSubAccountId(loanOrders.get(0).getSubAccountId());
            lnPayOrdersJnl.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DK.getCode());
            lnPayOrdersJnl.setCreateTime(new Date());
            lnPayOrdersJnl.setOrderId(lnPayOrders.getId());
            lnPayOrdersJnl.setOrderNo(lnPayOrders.getOrderNo());
            lnPayOrdersJnl.setTransAmount(lnPayOrders.getAmount());
            lnPayOrdersJnl.setTransCode(LoanStatus.ORDERS_JNL_STATUS_PRE_SUCCESS.getCode());
            lnPayOrdersJnl.setUserId(lnUser.getId());
            lnPayOrdersJnl.setSysTime(new Date());
            lnPayOrdersJnlMapper.insertSelective(lnPayOrdersJnl);

            //还款表置处理中
            LnRepay repayTemp = new LnRepay();
            repayTemp.setUpdateTime(new Date());
            repayTemp.setStatus(LoanStatus.REPAY_STATUS_PAYING.getCode());
            LnRepayExample lnRepayExample = new LnRepayExample();
            lnRepayExample.createCriteria().andPayOrderNoEqualTo(orderNo).andLnUserIdEqualTo(lnUser.getId());
            lnRepayMapper.updateByExampleSelective(repayTemp, lnRepayExample);
            return bgwOrderNo;
        }
    }

    @Override
    public void doZsdRepayConfirm(G2BReqMsg_ZsdRepay_RepayConfirm req) throws Exception {
    	String idCardNo = "";
    	LnRepayExample repayExample = new LnRepayExample();
        repayExample.createCriteria().andBgwOrderNoEqualTo(req.getBgwOrderNo());
        List<LnRepay> repayList = lnRepayMapper.selectByExample(repayExample);
        List<LnPayOrders> ordersList = null;
        if (CollectionUtils.isNotEmpty(repayList)) {
        	LnPayOrdersExample ordersExample = new LnPayOrdersExample();
            ordersExample.createCriteria().andOrderNoEqualTo(repayList.get(0).getPayOrderNo());
            ordersList = lnPayOrdersMapper.selectByExample(ordersExample);
            idCardNo = CollectionUtils.isNotEmpty(ordersList)?ordersList.get(0).getIdCard() : "";
        }

        try {
            jsClientDaoSupport.lock(RedisLockEnum.LOCK_ZSD_REPAY.getKey()+idCardNo);
            logger.info("=========赞时贷确认还款，加锁："+RedisLockEnum.LOCK_ZSD_REPAY.getKey()+idCardNo+"=======");

            if (CollectionUtils.isEmpty(repayList)) {
                throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "订单号不存在");
            }
            if (CollectionUtils.isEmpty(ordersList)) {
                throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND, "请重新发送验证码");
            }
            LnPayOrders lnPayOrder = ordersList.get(0);
            LnRepay lnRepay = repayList.get(0);


            //判断如果订单是成功的则返回成功
            if (lnPayOrder.getStatus().equals(Integer.parseInt(LoanStatus.ORDERS_STATUS_SUCCESS.getCode())) ||
            		lnPayOrder.getStatus().equals(Integer.parseInt(LoanStatus.ORDERS_STATUS_PAYING.getCode()))) {
				return;
			}

            if (lnPayOrder.getStatus().equals(Integer.parseInt(LoanStatus.ORDERS_STATUS_FAIL.getCode()))) {
            	throw new PTMessageException(PTMessageEnum.TRANS_ERROR, StringUtil.isBlank(lnPayOrder.getReturnMsg())?"订单失败":lnPayOrder.getReturnMsg());
			}

            if (!LoanStatus.REPAY_STATUS_PAYING.getCode().equals(lnRepay.getStatus())) {
            	throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "还款记录非预下单成功状态");
			}

            if (!lnPayOrder.getStatus().equals(Integer.parseInt(LoanStatus.ORDERS_STATUS_PRE_SUCCESS.getCode()))) {
            	throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "还款订单非预下单成功状态");
			}
            //该笔借款是否有还款正在进行中
            LnLoan lnLoan = lnLoanMapper.selectByPrimaryKey(lnRepay.getLoanId());
            if(lnLoan.getIsRepaying().equals(Constants.DEP_REPAY_REPAYING)){
                throw new PTMessageException(PTMessageEnum.DEP_REPAY_REPAYING);
            }

            //判断该笔订单是否超过15分钟
            if (DateUtil.getDiffeMinute(lnPayOrder.getUpdateTime()) > 15) {
                throw new PTMessageException(PTMessageEnum.ORDERS_TIMEOUT_FAILURE);
            }

            for (LnRepay repay : repayList) {
                //查询还款记录，判断此笔下单是否包含已还款的记录
                if (repay.getStatus().equals(LoanStatus.REPAY_STATUS_REPAIED.getCode())) {
                    throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "还款repayId="+ repay.getId() +"已有还款的记录");
                }
                if(repay.getRepayPlanId() != null){
                    LnRepaySchedule repaySchedule = lnRepayScheduleMapper.selectByPrimaryKey(repay.getRepayPlanId());

                    if(repaySchedule.getReturnFlag().equals(Constants.LN_REPAY_RETURN_FLAG_PENDING) ||
                            repaySchedule.getReturnFlag().equals(Constants.LN_REPAY_RETURN_FLAG_PROCESS)){
                        throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "账单[" + repaySchedule.getPartnerRepayId() + "]正在处理中");
                    }
                    if(LoanStatus.REPAY_SCHEDULE_STATUS_REPAIED.getCode().equals(repaySchedule.getStatus()) ||
                            LoanStatus.REPAY_SCHEDULE_STATUS_LATE_REPAIED.getCode().equals(repaySchedule.getStatus()) ||
                            LoanStatus.REPAY_SCHEDULE_STATUS_CANCELLED.getCode().equals(repaySchedule.getStatus())){
                        throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "账单[" + repaySchedule.getPartnerRepayId() + "]已存在还款或已废除");
                    }

                    //是否已存在重复还款记录
                    repeatRepayJudge(repaySchedule.getId(), repaySchedule.getPartnerRepayId());
                }
            }
            //预下单短信验证码校验
            Boolean isSuc = smsService.validateIdentityExpire(lnPayOrder.getMobile(), req.getSmsCode(), 600);
            if(!isSuc){
                throw new PTMessageException(PTMessageEnum.MOBILE_CODE_WRONG_ERROR);
            }
            B2GReqMsg_BaoFooCutpayment_Cutpayment cutpayment = new B2GReqMsg_BaoFooCutpayment_Cutpayment();
            cutpayment.setTrans_serial_no(Util.generateUserOrderNo4Pay19(lnPayOrder.getLnUserId()));
            cutpayment.setTrans_id(lnPayOrder.getOrderNo());
            //云贷宝付代扣交易金额入参需要转成分，赞分期就是以分为单位，无需改动
            cutpayment.setTxnAmt(MoneyUtil.multiply(lnPayOrder.getAmount(), 100).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            cutpayment.setAcc_no(lnPayOrder.getBankCardNo());
            cutpayment.setId_card(lnPayOrder.getIdCard());
            cutpayment.setId_holder(lnPayOrder.getUserName());
            cutpayment.setMobile(lnPayOrder.getMobile());
            //通过银行bank_id得到银行编码
            Bs19payBankExample example = new Bs19payBankExample();
            example.createCriteria().andBankIdEqualTo(lnPayOrder.getBankId()).andChannelEqualTo(Constants.ORDER_CHANNEL_BAOFOO);
            List<Bs19payBank> bs19payBank = bs19payBankMapper.selectByExample(example);

            cutpayment.setPay_code(bs19payBank.get(0).getPay19BankCode());
            cutpayment.setAdditional_info(PartnerEnum.getEnumByCode(lnPayOrder.getPartnerCode()).getName() + "用户代扣还款");

            //查询支付渠道代扣优先商户号信息
            BsPaymentChannel channelInfo = paymentChannelMapper.selectFisrtDK();
            if(channelInfo != null){
            	cutpayment.setMerchantNo(channelInfo.getMerchantNo());
            	cutpayment.setIsMain(channelInfo.getIsMain());
            	//修改订单表payment_channel_id
            	payOrdersService.updatePaymentChannelId(lnPayOrder.getId(), channelInfo.getId(), null);
            }
            B2GResMsg_BaoFooCutpayment_Cutpayment res = null;
            try {
                res = baoFooTransportService.withholding(cutpayment);
            } catch (Exception e) {
                throw new PTMessageException(PTMessageEnum.CONNECTION_ERROR);
            }

            LnRepay repayTemp = new LnRepay();
            repayTemp.setUpdateTime(new Date());

            if (!res.getResCode().equals(ConstantUtil.BSRESCODE_SUCCESS)) {
                if (res.getResCode().equals(LoanStatus.BAOFOO_PAY_STATUS_PROCESS.getCode())) {
                    //更新订单表状态
                    payOrdersService.modifyLnOrderStatus4Safe(lnPayOrder.getId(), Integer.parseInt(LoanStatus.ORDERS_STATUS_PAYING.getCode()),
                            null, res.getResCode(), res.getResMsg());
                    LnPayOrdersJnl lnPayOrdersJnl = new LnPayOrdersJnl();
                    //记录订单流水表
                    lnPayOrdersJnl.setSubAccountId(lnPayOrder.getSubAccountId());
                    lnPayOrdersJnl.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DK.getCode());
                    lnPayOrdersJnl.setCreateTime(new Date());
                    lnPayOrdersJnl.setOrderId(lnPayOrder.getId());
                    lnPayOrdersJnl.setOrderNo(lnPayOrder.getOrderNo());
                    lnPayOrdersJnl.setTransAmount(lnPayOrder.getAmount());
                    lnPayOrdersJnl.setUserId(lnPayOrder.getLnUserId());
                    lnPayOrdersJnl.setTransCode(Constants.ORDER_TRANS_CODE_PROCESS);
                    lnPayOrdersJnl.setSysTime(new Date());
                    lnPayOrdersJnlMapper.insertSelective(lnPayOrdersJnl);

                    //放到redis中
                    LoanNoticeVO vo = new LoanNoticeVO();
                    vo.setPayOrderNo(lnPayOrder.getOrderNo());
                    vo.setChannel(lnPayOrder.getChannel());
                    vo.setChannelTransType(lnPayOrder.getChannelTransType());
                    vo.setTransType(lnPayOrder.getTransType());
                    vo.setStatus(Constants.ORDER_STATUS_PAYING);
                    vo.setAmount(MoneyUtil.defaultRound(lnPayOrder.getAmount()).toString());
                    redisUtil.rpushRedis(vo);

                    //并插入到消息队列表中
                    BsPayResultQueue queue = new BsPayResultQueue();
                    queue.setChannel(Constants.ORDER_CHANNEL_BAOFOO);
                    queue.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DK.getCode());
                    queue.setCreateTime(new Date());
                    queue.setDealNum(0);
                    queue.setOrderNo(lnPayOrder.getOrderNo());
                    queue.setStatus(LoanStatus.NOTICE_STATUS_INIT.getCode());
                    queue.setTransType(lnPayOrder.getTransType());
                    queue.setUpdateTime(new Date());
                    queueMapper.insertSelective(queue);
                } else {
                    //还款失败
                    //更新订单表状态
                    payOrdersService.modifyLnOrderStatus4Safe(lnPayOrder.getId(), Integer.parseInt(LoanStatus.ORDERS_STATUS_PAYING.getCode()),
                            null, res.getResCode(), res.getResMsg());
                    //还款支付结果处理
                    DepFixedRepayPayResultInfo payResult = new DepFixedRepayPayResultInfo();
                    payResult.setSuccess(false);
                    payResult.setAmount(lnPayOrder.getAmount());
                    payResult.setOrderNo(lnPayOrder.getOrderNo());
                    payResult.setReturnCode(res.getResCode());
                    payResult.setReturnMsg(res.getResMsg());
                    doRepayResultPayProcess(payResult);
                }

            } else {
            	//还款成功
            	if(channelInfo != null && channelInfo.getIsMain() != null && channelInfo.getIsMain() !=1 ){
            		//使用的是辅助通道
            		if(StringUtil.isBlank(res.getPay4OnlineOrderNo())){
            			//钱包转账支付订单号为空，告警
            			specialJnlService.warn4FailNoSMS(lnPayOrder.getAmount(),"代扣商户间钱包转账失败，代扣订单号："+lnPayOrder.getOrderNo(),lnPayOrder.getOrderNo(),"【代扣商户间转账】");
            		}else{
	            		//钱包转账支付订单号不为空，记钱包转账支付的订单及流水
	            		LnPayOrders lnPayOrders = new LnPayOrders();
	                    lnPayOrders.setCreateTime(new Date());
	                    lnPayOrders.setAccountType(2);//转账算系统，记2
	                    lnPayOrders.setAmount(lnPayOrder.getAmount());
	                    lnPayOrders.setChannel(Constants.CHANNEL_BAOFOO);
	                    lnPayOrders.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_TRANSFER.getCode());
	                    lnPayOrders.setMoneyType(0);
	                    lnPayOrders.setOrderNo(res.getPay4OnlineOrderNo());
	                    lnPayOrders.setPartnerCode(PartnerEnum.ZSD.getCode());
	                    lnPayOrders.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_SUCCESS.getCode()));
	                    lnPayOrders.setSubAccountId(lnPayOrder.getSubAccountId());
	                    lnPayOrders.setTransType(LoanStatus.TRANS_TYPE_REPAY.getCode());
	                    lnPayOrders.setUpdateTime(new Date());
	                    lnPayOrders.setPaymentChannelId(channelInfo.getId());
	                    lnPayOrdersMapper.insertSelective(lnPayOrders);
	                    //记录订单流水表
	            		LnPayOrdersJnl lnPayOrdersJnl = new LnPayOrdersJnl();
	                    lnPayOrdersJnl.setSubAccountId(lnPayOrder.getSubAccountId());
	                    lnPayOrdersJnl.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_TRANSFER.getCode());
	                    lnPayOrdersJnl.setCreateTime(new Date());
	                    lnPayOrdersJnl.setOrderId(lnPayOrders.getId());
	                    lnPayOrdersJnl.setOrderNo(lnPayOrders.getOrderNo());
	                    lnPayOrdersJnl.setTransAmount(lnPayOrders.getAmount());
	                    lnPayOrdersJnl.setTransCode(Constants.ORDER_TRANS_CODE_SUCCESS);
	                    lnPayOrdersJnl.setUserId(lnPayOrders.getLnUserId());
	                    lnPayOrdersJnl.setSysTime(new Date());
	                    lnPayOrdersJnlMapper.insertSelective(lnPayOrdersJnl);
            		}
            	}

                //更新订单表状态
                payOrdersService.modifyLnOrderStatus4Safe(lnPayOrder.getId(), Integer.parseInt(LoanStatus.ORDERS_STATUS_PAYING.getCode()),
                        null, res.getResCode(), res.getResMsg());
                //还款支付结果处理
                DepFixedRepayPayResultInfo payResult = new DepFixedRepayPayResultInfo();
                payResult.setSuccess(true);
                payResult.setAmount(lnPayOrder.getAmount());
                payResult.setOrderNo(lnPayOrder.getOrderNo());
                payResult.setReturnCode(res.getResCode());
                payResult.setReturnMsg(res.getResMsg());
                doRepayResultPayProcess(payResult);
            }
        } finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_ZSD_REPAY.getKey()+idCardNo);
            logger.info("=========赞时贷确认还款，解锁："+RedisLockEnum.LOCK_ZSD_REPAY.getKey()+idCardNo+"=======");
        }
    }

    /**
     * 前置校验，入还款队列
     * @param req
     */
    @Override
    public void repayApplyZsd(final G2BReqMsg_ZsdRepay_CutpaymentRepay req) {
        try {
            //订单是否重复
            LnRepayExample repayExample = new LnRepayExample();
            repayExample.createCriteria().andPartnerOrderNoEqualTo(req.getOrderNo());
            List<LnRepay> repayList = lnRepayMapper.selectByExample(repayExample);
            if (CollectionUtils.isNotEmpty(repayList)) {
                throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "订单号重复");
            }
            if (CollectionUtils.isNotEmpty(req.getRepayments())) {
                //借款用户是否存在
                LnUserExample lnUserExample = new LnUserExample();
                lnUserExample.createCriteria().andPartnerCodeEqualTo(PartnerEnum.ZSD.getCode()).andPartnerUserIdEqualTo(req.getUserId());
                List<LnUser> lnUsers = lnUserMapper.selectByExample(lnUserExample);
                if (CollectionUtils.isEmpty(lnUsers)) {
                    throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND, "找不到借款用户信息");
                }
                //该银行是否支持代扣
                List<Pay19BankVO> pay19BankVOs = bs19payBankMapper.selectByChanelPayTypeBankCode(Constants.CHANNEL_BAOFOO, Constants.PAY_TYPE_HOLD, req.getBankCode());
                if (CollectionUtils.isEmpty(pay19BankVOs)) {
                    throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND, "银行编码[" + req.getBankCode() + "]不存在或该银行不支持代扣");
                }
                final LnUser lnUser = lnUsers.get(0);
                final String orderNo = Util.generateOrderNo4BaoFoo(8);
                // 云贷是BQP? 赞时贷改为BDK-还款代扣
                final String bgwOrderNo = Util.generateSysOrderNo(Constants.BGW_ORDER_NUMBER_GENERATE_WITHHOLD);
                transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                    @Override
                    protected void doInTransactionWithoutResult(TransactionStatus status) {
                        String loanId = req.getLoanId();
                        List<Repayment> repayments = req.getRepayments();
                        //每笔借款是否存在
                        LnLoanExample loanExample = new LnLoanExample();
                        loanExample.createCriteria().andPartnerLoanIdEqualTo(loanId)
                                .andLnUserIdEqualTo(lnUser.getId()).andStatusEqualTo(LoanStatus.LOAN_STATUS_PAIED.getCode());
                        List<LnLoan> loanList = lnLoanMapper.selectByExample(loanExample);
                        if (CollectionUtils.isEmpty(loanList)) {
                            throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND, "找不到借款[" + loanId + "]信息");
                        }
                        final LnLoan lnLoan = loanList.get(0);
                        //该笔借款是否有还款正在进行中
                        if (lnLoan.getIsRepaying().equals(Constants.DEP_REPAY_REPAYING)) {
                            throw new PTMessageException(PTMessageEnum.DEP_REPAY_REPAYING);
                        }

                        if (CollectionUtils.isEmpty(repayments)) {
                            throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND, "代扣还款赞时贷传入账单为空");
                        }
                        for (Repayment repayment : repayments) {
                            //是否已还款/是否需要账单同步
                            String repayId = repayment.getRepayId();
                            Double total = repayment.getTotal().doubleValue();
                            Double principal = repayment.getPrincipal().doubleValue();
                            Double interest = repayment.getInterest().doubleValue();
                            //预留字段
                            //String reservedField1 = (String) repay.get("reservedField1");

                            LnRepayScheduleExample lnRepayScheduleExample = new LnRepayScheduleExample();
                            lnRepayScheduleExample.createCriteria().andPartnerRepayIdEqualTo(repayId).andLoanIdEqualTo(lnLoan.getId());
                            List<LnRepaySchedule> lnRepaySchedules = lnRepayScheduleMapper.selectByExample(lnRepayScheduleExample);

                            //记录还款信息表和还款信息明细表
                            LnRepay lnRepay = new LnRepay();
                            if (CollectionUtils.isNotEmpty(lnRepaySchedules)) {
                                LnRepaySchedule lnRepaySchedule = lnRepaySchedules.get(0);
                                if (lnRepaySchedule.getReturnFlag().equals(Constants.LN_REPAY_RETURN_FLAG_PENDING) ||
                                        lnRepaySchedule.getReturnFlag().equals(Constants.LN_REPAY_RETURN_FLAG_PROCESS)) {
                                    throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "账单[" + lnRepaySchedule.getPartnerRepayId() + "]正在处理中");
                                }
                                lnRepay.setRepayPlanId(lnRepaySchedule.getId());
                                String scheduleStatus = lnRepaySchedule.getStatus();
                                if (LoanStatus.REPAY_SCHEDULE_STATUS_REPAIED.getCode().equals(scheduleStatus) ||
                                        LoanStatus.REPAY_SCHEDULE_STATUS_CANCELLED.getCode().equals(scheduleStatus) ||
                                        LoanStatus.REPAY_SCHEDULE_STATUS_LATE_REPAIED.getCode().equals(scheduleStatus)) {
                                    throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "账单[" + repayId + "]已存在还款或已废除");
                                }

                                //是否已存在重复还款记录
                                repeatRepayJudge(lnRepaySchedule.getId(), repayId);
                            } else {
                                throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "账单[" + repayId + "]不存在");
                            }
                            lnRepay.setStatus(LoanStatus.REPAY_STATUS_PAYING.getCode());
                            lnRepay.setUpdateTime(new Date());
                            lnRepay.setBgwOrderNo(bgwOrderNo);
                            lnRepay.setCreateTime(new Date());
                            lnRepay.setDoneTotal(total != null ? MoneyUtil.divide(total, 100).doubleValue() : 0d);
                            lnRepay.setLnUserId(lnUser.getId());
                            lnRepay.setPayOrderNo(orderNo);
                            lnRepay.setLoanId(lnLoan.getId());
                            lnRepay.setPartnerOrderNo(req.getOrderNo());
                            lnRepay.setInformStatus(LoanStatus.NOTICE_STATUS_INIT.getCode());
                            lnRepayMapper.insertSelective(lnRepay);

                            LnRepayDetail lnRepayDetail = new LnRepayDetail();
                            lnRepayDetail.setUpdateTime(new Date());
                            lnRepayDetail.setCreateTime(new Date());
                            lnRepayDetail.setRepayId(lnRepay.getId());
                            lnRepayDetail.setDoneAmount(principal != null ? MoneyUtil.divide(principal, 100).doubleValue() : 0d);
                            lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_PRINCIPAL.getCode());
                            lnRepayDetailMapper.insertSelective(lnRepayDetail);

                            Double interestDoneAmount = repayment.getInterest() != null ?
                                    MoneyUtil.divide(repayment.getInterest().doubleValue(), 100).doubleValue() : 0d;
                            lnRepayDetail.setDoneAmount(interestDoneAmount);
                            lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_INTEREST.getCode());
                            lnRepayDetailMapper.insertSelective(lnRepayDetail);
                            // 滞纳金
                            Double lateFeeDoneAmount = repayment.getLateFee() != null ?
                                    MoneyUtil.divide(repayment.getLateFee().doubleValue(), 100).doubleValue() : 0d;
                            lnRepayDetail.setDoneAmount(lateFeeDoneAmount);
                            lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_LATE_FEE.getCode());
                            lnRepayDetailMapper.insertSelective(lnRepayDetail);
                            // 手续费
                            Double serviceFeeDoneAmount = repayment.getServiceFee() != null ?
                                    MoneyUtil.divide(repayment.getServiceFee().doubleValue(), 100).doubleValue() : 0d;
                            lnRepayDetail.setDoneAmount(serviceFeeDoneAmount);
                            lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_SERVICE_FEE.getCode());
                            lnRepayDetailMapper.insertSelective(lnRepayDetail);
                            // 平台服务费
                            Double pServiceFeeDoneAmount = repayment.getPlatformServiceFee() != null ?
                                    MoneyUtil.divide(repayment.getPlatformServiceFee().doubleValue(), 100).doubleValue() : 0d;
                            lnRepayDetail.setDoneAmount(pServiceFeeDoneAmount);
                            lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_PLATFORM_SERVICE_FEE.getCode());
                            lnRepayDetailMapper.insertSelective(lnRepayDetail);
                            // 信息认证费
                            Double infoCertifiedFeeDoneAmount = repayment.getInfoCertifiedFee() != null ?
                                    MoneyUtil.divide(repayment.getInfoCertifiedFee().doubleValue(), 100).doubleValue() : 0d;
                            lnRepayDetail.setDoneAmount(infoCertifiedFeeDoneAmount);
                            lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_INFO_CERTIFIED_FEE.getCode());
                            lnRepayDetailMapper.insertSelective(lnRepayDetail);
                            // 风控服务费
                            Double riskManageServiceFeeDoneAmount = repayment.getRiskManageServiceFee() != null ?
                                    MoneyUtil.divide(repayment.getRiskManageServiceFee().doubleValue(), 100).doubleValue() : 0d;
                            lnRepayDetail.setDoneAmount(riskManageServiceFeeDoneAmount);
                            lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_RISK_MANAGE_SERVICE_FEE.getCode());
                            lnRepayDetailMapper.insertSelective(lnRepayDetail);
                            // 代收通道费
                            Double collectionChannelFeeDoneAmount = repayment.getCollectionChannelFee() != null ?
                                    MoneyUtil.divide(repayment.getCollectionChannelFee().doubleValue(), 100).doubleValue() : 0d;
                            lnRepayDetail.setDoneAmount(collectionChannelFeeDoneAmount);
                            lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_COLLECTION_CHANNEL_FEE.getCode());
                            lnRepayDetailMapper.insertSelective(lnRepayDetail);
                            // 其他费用
                            Double otherDoneAmount = repayment.getOther() != null ?
                                    MoneyUtil.divide(repayment.getOther().doubleValue(), 100).doubleValue() : 0d;
                            lnRepayDetail.setDoneAmount(otherDoneAmount);
                            lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_OTHER.getCode());
                            lnRepayDetailMapper.insertSelective(lnRepayDetail);

                        }
                    }
                });
                //记录ln_pay_orders,ln_pay_orders_jnl表
                LnPayOrders lnPayOrders = new LnPayOrders();
                lnPayOrders.setCreateTime(new Date());
                lnPayOrders.setAccountType(1);   // 1是用户
                lnPayOrders.setAmount(MoneyUtil.divide(req.getTotalAmount(), "100").doubleValue());
                lnPayOrders.setBankCardNo(req.getBankCard());
                lnPayOrders.setBankId(pay19BankVOs.get(0).getBankId());
                lnPayOrders.setBankName(pay19BankVOs.get(0).getBankName());
                lnPayOrders.setChannel(Constants.CHANNEL_BAOFOO);
                lnPayOrders.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DK.getCode());
                lnPayOrders.setIdCard(req.getIdCard());
                lnPayOrders.setLnUserId(lnUser.getId());
                lnPayOrders.setMobile(req.getMobile());
                lnPayOrders.setMoneyType(0);   //0是人民币
                lnPayOrders.setOrderNo(orderNo);
                lnPayOrders.setPartnerCode(PartnerEnum.ZSD.getCode());
                lnPayOrders.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_INIT.getCode()));
                lnPayOrders.setSubAccountId(null);
                lnPayOrders.setTransType(LoanStatus.TRANS_TYPE_REPAY.getCode());
                lnPayOrders.setUpdateTime(new Date());
                lnPayOrders.setUserName(req.getCardHolder());
                lnPayOrdersMapper.insertSelective(lnPayOrders);

                LnPayOrdersJnl lnPayOrdersJnl = new LnPayOrdersJnl();
                lnPayOrdersJnl.setSubAccountId(null);
                lnPayOrdersJnl.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DK.getCode());
                lnPayOrdersJnl.setCreateTime(new Date());
                lnPayOrdersJnl.setOrderId(lnPayOrders.getId());
                lnPayOrdersJnl.setOrderNo(lnPayOrders.getOrderNo());
                lnPayOrdersJnl.setTransAmount(lnPayOrders.getAmount());
                lnPayOrdersJnl.setTransCode(LoanStatus.ORDERS_JNL_STATUS_INIT.getCode());
                lnPayOrdersJnl.setUserId(lnUser.getId());
                lnPayOrdersJnl.setSysTime(new Date());
                lnPayOrdersJnlMapper.insertSelective(lnPayOrdersJnl);

                //入还款队列
                LnBindCard lnBindCard = new LnBindCard();
                lnBindCard.setLnUserId(lnUser.getId());
                lnBindCard.setBankCard(req.getBankCard());
                lnBindCard.setIdCard(req.getIdCard());
                lnBindCard.setUserName(req.getCardHolder());
                lnBindCard.setBankCode(req.getBankCode());
                lnBindCard.setMobile(req.getMobile());

                //入redis
                try {
                    RepayQueueDTO repayQueueDTO = new RepayQueueDTO();
                    repayQueueDTO.setLnBindCard(lnBindCard);
                    repayQueueDTO.setLnPayOrder(lnPayOrders);
                    repayQueueDTO.setChannel(PartnerEnum.ZSD.getCode());
                    jsClientDaoSupport.rpush("repay_queue", JSON.toJSONString(repayQueueDTO));
                    logger.info(">>>入还款队列数据:" + JSON.toJSONString(repayQueueDTO) + "<<<");
                }catch (Exception e){
                    logger.error("入还款队列异常", e);
                }
            }
        } finally {
        }
    }

    /**
     * 发起代扣还款请求,结果处理
     * @param lnPayOrder
     * @param lnBindCard
     */
    @Override
    public void withholdingRepayZsdAsync(LnPayOrders lnPayOrder, LnBindCard lnBindCard) {
        try {
            jsClientDaoSupport.lock(RedisLockEnum.LOCK_ZSD_REPAY.getKey()+lnBindCard.getIdCard());
            logger.info("=========赞时贷代扣还款请求，加锁："+RedisLockEnum.LOCK_ZSD_REPAY.getKey()+lnBindCard.getIdCard()+"=======");
            LnRepayExample lnRepayExample = new LnRepayExample();
            lnRepayExample.createCriteria().andPayOrderNoEqualTo(lnPayOrder.getOrderNo());
            List<LnRepay> repayList = lnRepayMapper.selectByExample(lnRepayExample);
            // 前置校验
            if (CollectionUtils.isNotEmpty(repayList)) {
                LnPayOrders newestOrder = lnPayOrdersMapper.selectByPrimaryKey(lnPayOrder.getId());
                if(null == newestOrder){
                    throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "无订单记录");
                }
                if(newestOrder.getStatus() == Integer.parseInt(LoanStatus.ORDERS_STATUS_SUCCESS.getCode()) ||
                        newestOrder.getStatus() == Integer.parseInt(LoanStatus.ORDERS_STATUS_PAYING.getCode())){
                    //订单已成功或处理中时
                    throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "订单"+newestOrder.getOrderNo()+"正在处理中或者已经成功");
                } else if(newestOrder.getStatus() == Integer.parseInt(LoanStatus.ORDERS_STATUS_FAIL.getCode()) ||
                        newestOrder.getStatus() == Integer.parseInt(LoanStatus.ORDERS_STATUS_PRE_FAIL.getCode())) {
                    //订单非成功非处理中时，返回错误信息
                    String errorMsg = newestOrder.getReturnMsg();
                    throw new PTMessageException(PTMessageEnum.TRANS_ERROR, "：" + errorMsg);
                }
            } else {
                throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "无还款记录");
            }
            //代扣发起
            B2GReqMsg_BaoFooCutpayment_Cutpayment cutpayment = new B2GReqMsg_BaoFooCutpayment_Cutpayment();
            cutpayment.setTrans_serial_no(Util.generateUserOrderNo4Pay19(lnBindCard.getLnUserId()));
            cutpayment.setTrans_id(lnPayOrder.getOrderNo());
            //云贷宝付代扣交易金额入参需要转成分，赞分期就是以分为单位，赞分期无需改动
            // 赞时贷还款总额以分为单位
            cutpayment.setTxnAmt(MoneyUtil.multiply(lnPayOrder.getAmount(), 100).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            cutpayment.setAcc_no(lnBindCard.getBankCard());
            cutpayment.setId_card(lnBindCard.getIdCard());
            cutpayment.setId_holder(lnBindCard.getUserName());
            cutpayment.setMobile(lnBindCard.getMobile());
            cutpayment.setPay_code(lnBindCard.getBankCode());
            cutpayment.setAdditional_info(PartnerEnum.ZSD.getName() + "用户代扣还款");
            //查询支付渠道代扣优先商户号信息
            BsPaymentChannel channelInfo = paymentChannelMapper.selectFisrtDK();
            if(channelInfo != null){
            	cutpayment.setMerchantNo(channelInfo.getMerchantNo());
            	cutpayment.setIsMain(channelInfo.getIsMain());
            	//修改订单表payment_channel_id
            	payOrdersService.updatePaymentChannelId(lnPayOrder.getId(), channelInfo.getId(), null);
            }
            B2GResMsg_BaoFooCutpayment_Cutpayment res = null;
            try {
                res = baoFooTransportService.withholding(cutpayment);
            } catch (Exception e) {
                res.setResCode(LoanStatus.BAOFOO_PAY_STATUS_PROCESS.getCode());
                res.setResMsg("通讯失败，置为处理中");
            }
            if (!res.getResCode().equals(ConstantUtil.BSRESCODE_SUCCESS)) {
                if (res.getResCode().equals(LoanStatus.BAOFOO_PAY_STATUS_PROCESS.getCode())) {

                    //更新订单表为处理中
                    payOrdersService.modifyLnOrderStatus4Safe(lnPayOrder.getId(), Integer.parseInt(LoanStatus.ORDERS_STATUS_PAYING.getCode()),
                            null, res.getResCode(), res.getResMsg());

                    //放到redis中
                    LoanNoticeVO vo = new LoanNoticeVO();
                    vo.setPayOrderNo(lnPayOrder.getOrderNo());
                    vo.setChannel(lnPayOrder.getChannel());
                    vo.setChannelTransType(lnPayOrder.getChannelTransType());
                    vo.setTransType(lnPayOrder.getTransType());
                    vo.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_PAYING.getCode()));
                    vo.setAmount(MoneyUtil.defaultRound(lnPayOrder.getAmount()).toString());
                    redisUtil.rpushRedis(vo);

                    //并插入到消息队列表中
                    BsPayResultQueue queue = new BsPayResultQueue();
                    queue.setChannel(Constants.ORDER_CHANNEL_BAOFOO);
                    queue.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DK.getCode());
                    queue.setCreateTime(new Date());
                    queue.setDealNum(0);
                    queue.setOrderNo(lnPayOrder.getOrderNo());
                    queue.setStatus(LoanStatus.NOTICE_STATUS_INIT.getCode());
                    queue.setTransType(lnPayOrder.getTransType());
                    queue.setUpdateTime(new Date());
                    queueMapper.insertSelective(queue);

                } else {
                    //更新订单表为处理中
                    payOrdersService.modifyLnOrderStatus4Safe(lnPayOrder.getId(), Integer.parseInt(LoanStatus.ORDERS_STATUS_PAYING.getCode()),
                            null, res.getResCode(), res.getResMsg());

                    //还款失败,还款支付结果处理
                    DepFixedRepayPayResultInfo payResult = new DepFixedRepayPayResultInfo();
                    payResult.setSuccess(false);
                    payResult.setAmount(lnPayOrder.getAmount());	// 订单表的金额已经是元单位了，不需要再除以100
                    payResult.setOrderNo(lnPayOrder.getOrderNo());
                    payResult.setReturnCode(res.getResCode());
                    payResult.setReturnMsg(res.getResMsg());
                    doRepayResultPayProcess(payResult);
                }
            } else {
            	if(channelInfo != null && channelInfo.getIsMain() != null && channelInfo.getIsMain() !=1 ){
            		//使用的是辅助通道
            		if(StringUtil.isBlank(res.getPay4OnlineOrderNo())){
            			//钱包转账支付订单号为空，告警
            			specialJnlService.warn4FailNoSMS(lnPayOrder.getAmount(),"代扣商户间钱包转账失败，代扣订单号："+lnPayOrder.getOrderNo(),lnPayOrder.getOrderNo(),"【代扣商户间转账】");
            		}else{
	            		//钱包转账支付订单号不为空，记钱包转账支付的订单及流水
	            		LnPayOrders lnPayOrders = new LnPayOrders();
	                    lnPayOrders.setCreateTime(new Date());
	                    lnPayOrders.setAccountType(2);//转账算系统，记2
	                    lnPayOrders.setAmount(lnPayOrder.getAmount());
	                    lnPayOrders.setChannel(Constants.CHANNEL_BAOFOO);
	                    lnPayOrders.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_TRANSFER.getCode());
	                    lnPayOrders.setMoneyType(0);
	                    lnPayOrders.setOrderNo(res.getPay4OnlineOrderNo());
	                    lnPayOrders.setPartnerCode(PartnerEnum.ZSD.getCode());
	                    lnPayOrders.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_SUCCESS.getCode()));
	                    lnPayOrders.setSubAccountId(lnPayOrder.getSubAccountId());
	                    lnPayOrders.setTransType(LoanStatus.TRANS_TYPE_REPAY.getCode());
	                    lnPayOrders.setUpdateTime(new Date());
	                    lnPayOrders.setPaymentChannelId(channelInfo.getId());
	                    lnPayOrdersMapper.insertSelective(lnPayOrders);
	                    //记录订单流水表
	            		LnPayOrdersJnl lnPayOrdersJnl = new LnPayOrdersJnl();
	                    lnPayOrdersJnl.setSubAccountId(lnPayOrder.getSubAccountId());
	                    lnPayOrdersJnl.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_TRANSFER.getCode());
	                    lnPayOrdersJnl.setCreateTime(new Date());
	                    lnPayOrdersJnl.setOrderId(lnPayOrders.getId());
	                    lnPayOrdersJnl.setOrderNo(lnPayOrders.getOrderNo());
	                    lnPayOrdersJnl.setTransAmount(lnPayOrders.getAmount());
	                    lnPayOrdersJnl.setTransCode(Constants.ORDER_TRANS_CODE_SUCCESS);
	                    lnPayOrdersJnl.setUserId(lnPayOrders.getLnUserId());
	                    lnPayOrdersJnl.setSysTime(new Date());
	                    lnPayOrdersJnlMapper.insertSelective(lnPayOrdersJnl);
            		}
            	}
                //更新订单表为处理中
                payOrdersService.modifyLnOrderStatus4Safe(lnPayOrder.getId(), Integer.parseInt(LoanStatus.ORDERS_STATUS_PAYING.getCode()),
                        null, res.getResCode(), res.getResMsg());

                //还款成功,还款支付结果处理
                DepFixedRepayPayResultInfo payResult = new DepFixedRepayPayResultInfo();
                payResult.setSuccess(true);
                payResult.setAmount(lnPayOrder.getAmount());
                payResult.setOrderNo(lnPayOrder.getOrderNo());
                payResult.setReturnCode(res.getResCode());
                payResult.setReturnMsg(res.getResMsg());
                doRepayResultPayProcess(payResult);
            }
        }finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_ZSD_REPAY.getKey()+lnBindCard.getIdCard());
            logger.info("=========赞时贷代扣还款请求，解锁："+RedisLockEnum.LOCK_ZSD_REPAY.getKey()+lnBindCard.getIdCard()+"=======");
        }
    }

    /**
     * 发起代扣线下还款（逻辑同代扣还款，但不发起宝付请求，另根据产品要求抛出前记录所有还款信息）
     *
     * 1.加锁,数据校验（还款订单重复、借款用户存在、借款存在、该笔借款是否有还款正在进行中、
     *   传入账单为空、还款处理中、已还款、已逾期还款、校验还款账单明细本金和还款明细本金是否一致）
     * 2.记录ln_repay(pay_order_no 记录币港湾订单号 -> 记录宝付支付订单号),ln_repay_detail,
     *   ln_pay_orders(order_no 记录币港湾订单号 -> 记录宝付支付订单号，note 备注'赞时贷代扣线下还款'),ln_pay_orders_jnl
     * 3.默认支付成功，调用还款支付结果处理（成功）
     *
     * @param req
     */
    @Override
    public void repayOfflineZsd(final G2BReqMsg_ZsdRepay_CutpaymentRepay req){
        try{

            jsClientDaoSupport.lock(RedisLockEnum.LOCK_ZSD_REPAY.getKey() + req.getIdCard());
            logger.info("=========赞时贷代扣还款（线下还款），加锁："+RedisLockEnum.LOCK_ZSD_REPAY.getKey()+req.getIdCard()+"=======");

            final String bgwOrderNo = Util.generateSysOrderNo(Constants.BGW_ORDER_NUMBER_GENERATE_OFFLINE_REPAY);
            //根据产品需求,所有发送过来的还款信息都要记录
            final LnRepay lnRepay = new LnRepay();
            lnRepay.setStatus(LoanStatus.REPAY_STATUS_FAIL.getCode());
            lnRepay.setBgwOrderNo(bgwOrderNo);
            lnRepay.setRepayType(Constants.IS_OFFLINE_REPAY);
            lnRepay.setDoneTotal(req.getTotalAmount() != null ? MoneyUtil.divide(req.getTotalAmount(), "100").doubleValue() : 0d);
            //记录传入的线下还款宝付订单号，用于对账
            lnRepay.setPayOrderNo(req.getOffPayOrderNo());
            lnRepay.setPartnerOrderNo(req.getOrderNo());
            lnRepay.setInformStatus(LoanStatus.NOTICE_STATUS_INIT.getCode());

            //合作方订单号是否重复
            LnRepayExample repayExample = new LnRepayExample();
            repayExample.createCriteria().andPartnerOrderNoEqualTo(req.getOrderNo());
            List<LnRepay> repayList = lnRepayMapper.selectByExample(repayExample);
            if (CollectionUtils.isNotEmpty(repayList)) {
                throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "订单号重复");
            }
            if (CollectionUtils.isNotEmpty(req.getRepayments())) {
                //借款用户是否存在
                LnUserExample lnUserExample = new LnUserExample();
                lnUserExample.createCriteria().andPartnerCodeEqualTo(PartnerEnum.ZSD.getCode()).andPartnerUserIdEqualTo(req.getUserId());
                List<LnUser> lnUsers = lnUserMapper.selectByExample(lnUserExample);
                if (CollectionUtils.isEmpty(lnUsers)) {
                    lnRepay.setUpdateTime(new Date());
                    lnRepay.setCreateTime(new Date());
                    lnRepayMapper.insertSelective(lnRepay);
                    throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND, "找不到借款用户信息");
                }
                final LnUser lnUser = lnUsers.get(0);

                lnRepay.setLnUserId(lnUser.getId());
                String loanId = req.getLoanId();
                //借款是否存在
                LnLoanExample loanExample = new LnLoanExample();
                loanExample.createCriteria().andPartnerLoanIdEqualTo(loanId)
                        .andLnUserIdEqualTo(lnUser.getId()).andStatusEqualTo(LoanStatus.LOAN_STATUS_PAIED.getCode());
                List<LnLoan> loanList = lnLoanMapper.selectByExample(loanExample);
                if (CollectionUtils.isEmpty(loanList)) {
                    lnRepay.setUpdateTime(new Date());
                    lnRepay.setCreateTime(new Date());
                    lnRepayMapper.insertSelective(lnRepay);
                    throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND, "找不到借款[" + loanId + "]信息");
                }

                final LnLoan lnLoan = loanList.get(0);
                lnRepay.setLoanId(lnLoan.getId());
                //该笔借款是否有还款正在进行中
                if (lnLoan.getIsRepaying().equals(Constants.DEP_REPAY_REPAYING)) {
                    lnRepay.setUpdateTime(new Date());
                    lnRepay.setCreateTime(new Date());
                    lnRepayMapper.insertSelective(lnRepay);
                    throw new PTMessageException(PTMessageEnum.DEP_REPAY_REPAYING);
                }
                transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                    @Override
                    protected void doInTransactionWithoutResult(TransactionStatus status) {
                        List<Repayment> repayments = req.getRepayments();
                        if (CollectionUtils.isEmpty(repayments)) {
                            throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND, "代扣还款赞时贷传入账单为空");
                        }
                        for (Repayment repayment : repayments) {
                            String repayId = repayment.getRepayId();
                            // 总额
                            Double total = repayment.getTotal().doubleValue();
                            // 本金
                            Double principal = repayment.getPrincipal().doubleValue();
                            // 利息
                            Double interestDoneAmount = repayment.getInterest() != null ?
                                    MoneyUtil.divide(repayment.getInterest().doubleValue(), 100).doubleValue() : 0d;
                            // 滞纳金
                            Double lateFeeDoneAmount = repayment.getLateFee() != null ?
                                    MoneyUtil.divide(repayment.getLateFee().doubleValue(), 100).doubleValue() : 0d;
                            // 手续费
                            Double serviceFeeDoneAmount = repayment.getServiceFee() != null ?
                                    MoneyUtil.divide(repayment.getServiceFee().doubleValue(), 100).doubleValue() : 0d;
                            // 平台服务费
                            Double pServiceFeeDoneAmount = repayment.getPlatformServiceFee() != null ?
                                    MoneyUtil.divide(repayment.getPlatformServiceFee().doubleValue(), 100).doubleValue() : 0d;
                            // 信息认证费
                            Double infoCertifiedFeeDoneAmount = repayment.getInfoCertifiedFee() != null ?
                                    MoneyUtil.divide(repayment.getInfoCertifiedFee().doubleValue(), 100).doubleValue() : 0d;
                            // 风控服务费
                            Double riskManageServiceFeeDoneAmount = repayment.getRiskManageServiceFee() != null ?
                                    MoneyUtil.divide(repayment.getRiskManageServiceFee().doubleValue(), 100).doubleValue() : 0d;
                            // 代收通道费
                            Double collectionChannelFeeDoneAmount = repayment.getCollectionChannelFee() != null ?
                                    MoneyUtil.divide(repayment.getCollectionChannelFee().doubleValue(), 100).doubleValue() : 0d;
                            // 其他费用
                            Double otherDoneAmount = repayment.getOther() != null ?
                                    MoneyUtil.divide(repayment.getOther().doubleValue(), 100).doubleValue() : 0d;

                            LnRepayScheduleExample lnRepayScheduleExample = new LnRepayScheduleExample();
                            lnRepayScheduleExample.createCriteria().andPartnerRepayIdEqualTo(repayId).andLoanIdEqualTo(lnLoan.getId());
                            List<LnRepaySchedule> lnRepaySchedules = lnRepayScheduleMapper.selectByExample(lnRepayScheduleExample);

                            //还款账单表存在时校验
                            if (CollectionUtils.isNotEmpty(lnRepaySchedules)) {
                                LnRepaySchedule lnRepaySchedule = lnRepaySchedules.get(0);

                                //线下还款订单号相同，账单编号不同直接throw
                                LnRepayExample repayOffExample = new LnRepayExample();
                                repayOffExample.createCriteria().andPayOrderNoEqualTo(req.getOffPayOrderNo())
                                        .andStatusNotEqualTo(Constants.LN_REPAY_PAY_STATUS_REPAY_FAIL);
                                List<LnRepay> repayList = lnRepayMapper.selectByExample(repayOffExample);
                                if (CollectionUtils.isNotEmpty(repayList)) {
                                    LnRepay lnRepay1 = repayList.get(0);
                                    if(lnRepay1.getRepayPlanId() != lnRepaySchedule.getId()){
                                        throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, ",线下还款订单号相同，账单编号不同");
                                    }
                                }

                                //对应账单有还款进行中抛出
                                Boolean isRepaying = lnRepayMapper.isBillRepaying(lnRepaySchedule.getId());
                                if(isRepaying){
                                    throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "账单[" + lnRepaySchedule.getPartnerRepayId() + "]正在处理中");
                                }
                                if (Constants.LN_REPAY_RETURN_FLAG_PENDING.equals(lnRepaySchedule.getReturnFlag()) ||
                                        Constants.LN_REPAY_RETURN_FLAG_PROCESS.equals(lnRepaySchedule.getReturnFlag())) {
                                    throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "账单[" + lnRepaySchedule.getPartnerRepayId() + "]正在处理中");
                                }
                                lnRepay.setRepayPlanId(lnRepaySchedule.getId());
                                String scheduleStatus = lnRepaySchedule.getStatus();
                                if (LoanStatus.REPAY_SCHEDULE_STATUS_REPAIED.getCode().equals(scheduleStatus) ||
                                        LoanStatus.REPAY_SCHEDULE_STATUS_CANCELLED.getCode().equals(scheduleStatus) ||
                                        LoanStatus.REPAY_SCHEDULE_STATUS_LATE_REPAIED.getCode().equals(scheduleStatus)) {
                                    throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "账单[" + repayId + "]已存在还款或已废除");
                                }
                                //校验还款账单明细本金和还款明细本金是否一致，不一致抛出
                                if(LoanStatus.REPAY_SCHEDULE_STATUS_INIT.getCode().equals(scheduleStatus)){
                                    LnRepayScheduleDetailExample exampleDetail = new LnRepayScheduleDetailExample();
                                    exampleDetail.createCriteria().andPlanIdEqualTo(lnRepaySchedule.getId()).andSubjectCodeEqualTo(Constants.SUBJECT_PRINCIPAL);
                                    List<LnRepayScheduleDetail> list = lnRepayScheduleDetailMapper.selectByExample(exampleDetail);
                                    if(CollectionUtils.isEmpty(list)){
                                        throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "账单本金明细不存在");
                                    }
                                    Double principalYuan = principal != null ? MoneyUtil.divide(principal, 100).doubleValue() : 0d;
                                    LnRepayScheduleDetail item = list.get(0);
                                    if(principalYuan.compareTo(item.getPlanAmount()) != 0){
                                        throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "线下还款未按计划本金还款");
                                    }
                                }
                                //是否已存在重复还款记录
                                repeatRepayJudge(lnRepaySchedule.getId(), repayId);
                            } else {
                                throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "账单[" + repayId + "]不存在");
                            }

                            //记录还款信息
                            lnRepay.setStatus(LoanStatus.REPAY_STATUS_PAYING.getCode());
                            lnRepay.setUpdateTime(new Date());
                            lnRepay.setCreateTime(new Date());
                            lnRepay.setDoneTotal(total != null ? MoneyUtil.divide(total, 100).doubleValue() : 0d);
                            lnRepayMapper.insertSelective(lnRepay);

                            //记录还款信息
                            saveRepayDetail(lnRepay.getId(), principal, interestDoneAmount, lateFeeDoneAmount,
                                    serviceFeeDoneAmount, pServiceFeeDoneAmount, infoCertifiedFeeDoneAmount,
                                    riskManageServiceFeeDoneAmount, collectionChannelFeeDoneAmount, otherDoneAmount);
                        }
                    }
                });
                //记录ln_pay_orders,ln_pay_orders_jnl表
                LnPayOrders lnPayOrders = new LnPayOrders();
                lnPayOrders.setCreateTime(new Date());
                lnPayOrders.setAccountType(1);   // 1是用户
                lnPayOrders.setAmount(MoneyUtil.divide(req.getTotalAmount(), "100").doubleValue());
                if(StringUtil.isNotEmpty(req.getBankCard())){
                    lnPayOrders.setBankCardNo(req.getBankCard());
                }
                lnPayOrders.setChannel(Constants.CHANNEL_BAOFOO);
                lnPayOrders.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DK.getCode());
                lnPayOrders.setIdCard(req.getIdCard());
                lnPayOrders.setLnUserId(lnUser.getId());
                lnPayOrders.setMobile(req.getMobile());
                lnPayOrders.setMoneyType(0);   //0是人民币
                lnPayOrders.setOrderNo(req.getOffPayOrderNo());
                lnPayOrders.setPartnerCode(PartnerEnum.ZSD.getCode());
                lnPayOrders.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_INIT.getCode()));
                lnPayOrders.setSubAccountId(null);
                lnPayOrders.setTransType(LoanStatus.TRANS_TYPE_REPAY.getCode());
                lnPayOrders.setUpdateTime(new Date());
                lnPayOrders.setUserName(req.getCardHolder());
                lnPayOrders.setNote(Constants.OFFLINE_ZSD_ORDER_NOTE);
                
                // 资产方线下还款现在走的主通道，设置为默认值
                lnPayOrders.setPaymentChannelId(1);
                lnPayOrdersMapper.insertSelective(lnPayOrders);

                LnPayOrdersJnl lnPayOrdersJnl = new LnPayOrdersJnl();
                lnPayOrdersJnl.setSubAccountId(null);
                lnPayOrdersJnl.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DK.getCode());
                lnPayOrdersJnl.setCreateTime(new Date());
                lnPayOrdersJnl.setOrderId(lnPayOrders.getId());
                lnPayOrdersJnl.setOrderNo(lnPayOrders.getOrderNo());
                lnPayOrdersJnl.setTransAmount(lnPayOrders.getAmount());
                lnPayOrdersJnl.setTransCode(LoanStatus.ORDERS_JNL_STATUS_INIT.getCode());
                lnPayOrdersJnl.setUserId(lnUser.getId());
                lnPayOrdersJnl.setSysTime(new Date());
                lnPayOrdersJnlMapper.insertSelective(lnPayOrdersJnl);

                //默认代扣成功，更新订单表为处理中
                payOrdersService.modifyLnOrderStatus4Safe(lnPayOrders.getId(), Integer.parseInt(LoanStatus.ORDERS_STATUS_PAYING.getCode()),
                        null, LoanStatus.BAOFOO_PAY_STATUS_SUCCESS.getCode(), "线下还款成功");

                //还款成功,还款支付结果处理
                DepFixedRepayPayResultInfo payResult = new DepFixedRepayPayResultInfo();
                payResult.setSuccess(true);
                payResult.setAmount(lnPayOrders.getAmount());
                payResult.setOrderNo(lnPayOrders.getOrderNo());
                payResult.setReturnCode(lnPayOrders.getReturnCode());
                payResult.setReturnMsg(lnPayOrders.getReturnMsg());
                doRepayResultPayProcess(payResult);
            }else{
                throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND,",（线下还款代扣）入参还款信息为空");
            }
        }finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_ZSD_REPAY.getKey() + req.getIdCard());
            logger.info("=========赞时贷代扣还款（线下还款），解锁："+RedisLockEnum.LOCK_ZSD_REPAY.getKey()+req.getIdCard()+"=======");
        }

    }

    @Override
    public String doZsdPreRepayRepeat(String partnerOrderNo) {
        //订单号不存在
        LnRepayExample repayExample = new LnRepayExample();
        repayExample.createCriteria().andPartnerOrderNoEqualTo(partnerOrderNo);
        final List<LnRepay> repayList = lnRepayMapper.selectByExample(repayExample);
        if (CollectionUtils.isEmpty(repayList)) {
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "订单号不存在");
        }
        LnRepay repay = repayList.get(0);
        LnPayOrdersExample ordersExample = new LnPayOrdersExample();
        ordersExample.createCriteria().andOrderNoEqualTo(repay.getPayOrderNo()).andPartnerCodeEqualTo(PartnerEnum.ZSD.getCode())
                .andStatusEqualTo(Integer.parseInt(LoanStatus.ORDERS_STATUS_PRE_SUCCESS.getCode()));
        List<LnPayOrders> repayOrdersList = lnPayOrdersMapper.selectByExample(ordersExample);
        if(CollectionUtils.isEmpty(repayOrdersList)) {
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "订单号不存在");
        }
        LnPayOrders repayOrder = repayOrdersList.get(0);

        //借款用户是否存在
        final LnUser lnUser = lnUserMapper.selectByPrimaryKey(repayList.get(0).getLnUserId());
        if (null == lnUser) {
            throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND, "找不到借款用户信息");
        }
        //借款是否存在
        final LnLoan lnLoan = lnLoanMapper.selectByPrimaryKey(repay.getLoanId());
        if (null == lnLoan) {
            throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND, "找不到借款["+repay.getLoanId()+"]信息");
        }
        //该笔借款是否有还款正在进行中
        if(lnLoan.getIsRepaying().equals(Constants.DEP_REPAY_REPAYING)){
            throw new PTMessageException(PTMessageEnum.DEP_REPAY_REPAYING);
        }

        final String payOrderNo = Util.generateOrderNo4BaoFoo(8);
        String bgwOrderNo = repay.getBgwOrderNo();
        for (LnRepay lnRepay: repayList) {
            LnRepay updateRepay = new LnRepay();
            updateRepay.setId(lnRepay.getId());
            updateRepay.setPayOrderNo(payOrderNo);
            updateRepay.setUpdateTime(new Date());
            lnRepayMapper.updateByPrimaryKeySelective(updateRepay);
        }

        //根据借款支付订单号，查询子账户id
        LnPayOrdersExample loanOrdersExample = new LnPayOrdersExample();
        loanOrdersExample.createCriteria().andOrderNoEqualTo(lnLoan.getPayOrderNo()).andPartnerCodeEqualTo(PartnerEnum.ZSD.getCode());
        List<LnPayOrders> loanOrders = lnPayOrdersMapper.selectByExample(loanOrdersExample);

        LnPayOrders newRepayOrder = new LnPayOrders();
        newRepayOrder.setCreateTime(new Date());
        newRepayOrder.setAccountType(1);     // 1是用户
        newRepayOrder.setAmount(repayOrder.getAmount());
        newRepayOrder.setBankCardNo(repayOrder.getBankCardNo());
        newRepayOrder.setBankId(repayOrder.getBankId());
        newRepayOrder.setBankName(repayOrder.getBankName());
        newRepayOrder.setChannel(Constants.CHANNEL_BAOFOO);
        newRepayOrder.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DK.getCode());
        newRepayOrder.setIdCard(repayOrder.getIdCard());
        newRepayOrder.setLnUserId(lnUser.getId());
        newRepayOrder.setMobile(repayOrder.getMobile());
        newRepayOrder.setMoneyType(0);
        newRepayOrder.setOrderNo(payOrderNo);
        newRepayOrder.setPartnerCode(PartnerEnum.ZSD.getCode());
        newRepayOrder.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_INIT.getCode()));
        newRepayOrder.setSubAccountId(loanOrders.get(0).getSubAccountId());
        newRepayOrder.setTransType(LoanStatus.TRANS_TYPE_REPAY.getCode());
        newRepayOrder.setUpdateTime(new Date());
        newRepayOrder.setUserName(repayOrder.getUserName());
        lnPayOrdersMapper.insertSelective(newRepayOrder);

        //记录ln_pay_orders_jnl表
        LnPayOrdersJnl lnPayOrdersJnl = new LnPayOrdersJnl();
        lnPayOrdersJnl.setSubAccountId(loanOrders.get(0).getSubAccountId());
        lnPayOrdersJnl.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DK.getCode());
        lnPayOrdersJnl.setCreateTime(new Date());
        lnPayOrdersJnl.setOrderId(repayOrder.getId());
        lnPayOrdersJnl.setOrderNo(repayOrder.getOrderNo());
        lnPayOrdersJnl.setTransAmount(repayOrder.getAmount());
        lnPayOrdersJnl.setTransCode(LoanStatus.ORDERS_JNL_STATUS_INIT.getCode());
        lnPayOrdersJnl.setUserId(lnUser.getId());
        lnPayOrdersJnl.setSysTime(new Date());
        lnPayOrdersJnl.setNote("赞时贷还款重发短信验证码");
        lnPayOrdersJnlMapper.insertSelective(lnPayOrdersJnl);
        //发送短信
        String sendCode = null;
        String returnMsg = null;
        try {
            sendCode = smsService.sendRepayPreToken(repayOrder.getMobile(), PartnerEnum.ZSD.getCode());
        }catch (Exception e){
            e.printStackTrace();
            sendCode = Constants.SEND_CODE_ERROR;
            if(e instanceof PTMessageException){
                returnMsg = ((PTMessageException) e).getErrMessage();
            }
        }
        if(Constants.SEND_CODE_ERROR.equals(sendCode)){
            LnPayOrders ordersTemp = new LnPayOrders();
            ordersTemp.setId(newRepayOrder.getId());
            ordersTemp.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_PRE_FAIL.getCode()));
            ordersTemp.setReturnMsg(returnMsg == null ? "短信发送失败" : returnMsg);
            ordersTemp.setUpdateTime(new Date());
            lnPayOrdersMapper.updateByPrimaryKeySelective(ordersTemp);
            //还款表置失败
            LnRepay repayTemp = new LnRepay();
            repayTemp.setUpdateTime(new Date());
            repayTemp.setStatus(LoanStatus.REPAY_STATUS_FAIL.getCode());
            LnRepayExample lnRepayExample = new LnRepayExample();
            lnRepayExample.createCriteria().andPayOrderNoEqualTo(payOrderNo).andLnUserIdEqualTo(lnUser.getId());
            lnRepayMapper.updateByExampleSelective(repayTemp, lnRepayExample);
            throw new PTMessageException(PTMessageEnum.USER_ORDER_PRE_FAIL, ordersTemp.getReturnMsg());
        }else {
            LnPayOrders ordersTemp = new LnPayOrders();
            ordersTemp.setId(newRepayOrder.getId());
            ordersTemp.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_PRE_SUCCESS.getCode()));
            ordersTemp.setUpdateTime(new Date());
            lnPayOrdersMapper.updateByPrimaryKeySelective(ordersTemp);
            //还款表置处理中
            LnRepay repayTemp = new LnRepay();
            repayTemp.setUpdateTime(new Date());
            repayTemp.setStatus(LoanStatus.REPAY_STATUS_PAYING.getCode());
            LnRepayExample lnRepayExample = new LnRepayExample();
            lnRepayExample.createCriteria().andPayOrderNoEqualTo(payOrderNo).andLnUserIdEqualTo(lnUser.getId());
            lnRepayMapper.updateByExampleSelective(repayTemp, lnRepayExample);
            return bgwOrderNo;
        }
    }

    @Override
    public void lateRepayZsdNotice(final G2BReqMsg_ZsdRepay_LateRepayNotice req) {
        /**
         * 1、数据校验
         * 2、代偿通知及明细记录ln_compensate、ln_compensate_detail
         * 3、循环调用还款结果处理
         * 4、3协议生成及通知赞时贷ln_compensate_agreement_address
         */
        try {
        	//校验数据
            LnCompensateExample compensateExample = new LnCompensateExample();
            compensateExample.createCriteria().andOrderNoEqualTo(req.getOrderNo())
                    .andPartnerCodeEqualTo(Constants.PROPERTY_SYMBOL_ZSD);
            //单号重复
            List<LnCompensate> list = lnCompensateMapper.selectByExample(compensateExample);
            if (CollectionUtils.isNotEmpty(list)){
            	LnCompensateDetailExample compensateDetailExample = new LnCompensateDetailExample();
            	compensateDetailExample.createCriteria().andCompensateIdEqualTo(list.get(0).getId());
            	LnCompensateDetail detailRecord = lnCompensateDetailMapper.selectByExample(compensateDetailExample).get(0);
            	if (Constants.COMPENSATE_REPAYS_STATUS_SUCC.equals(detailRecord.getStatus()) ||
            			Constants.COMPENSATE_REPAYS_STATUS_REPEAT.equals(detailRecord.getStatus())) {
            		return;
            	} else if(Constants.COMPENSATE_REPAYS_STATUS_INIT.equals(detailRecord.getStatus())) {
                    throw new PTMessageException(PTMessageEnum.COMPENSATE_NOTICE_SAME);
            	} else if(Constants.COMPENSATE_REPAYS_STATUS_FAIL.equals(detailRecord.getStatus())) {
                    throw new PTMessageException(PTMessageEnum.COMPENSATE_NOTICE_FAIL);
            	}
            }
            LnCompensateExample compensateExample4Pay = new LnCompensateExample();
            compensateExample4Pay.createCriteria()
                    .andPayOrderNoEqualTo(req.getPayOrderNo())
                    .andPartnerCodeEqualTo(Constants.PROPERTY_SYMBOL_ZSD);
            //支付单号重复
            List<LnCompensate> list4Pay = lnCompensateMapper.selectByExample(compensateExample4Pay);
            if (CollectionUtils.isNotEmpty(list4Pay)){
            	LnCompensateDetailExample compensateDetailExample = new LnCompensateDetailExample();
            	compensateDetailExample.createCriteria().andCompensateIdEqualTo(list.get(0).getId());
            	LnCompensateDetail detailRecord = lnCompensateDetailMapper.selectByExample(compensateDetailExample).get(0);
            	if (Constants.COMPENSATE_REPAYS_STATUS_SUCC.equals(detailRecord.getStatus()) ||
            			Constants.COMPENSATE_REPAYS_STATUS_REPEAT.equals(detailRecord.getStatus())) {
            		return;
            	} else if(Constants.COMPENSATE_REPAYS_STATUS_INIT.equals(detailRecord.getStatus())) {
                    throw new PTMessageException(PTMessageEnum.COMPENSATE_NOTICE_SAME);
            	} else if(Constants.COMPENSATE_REPAYS_STATUS_FAIL.equals(detailRecord.getStatus())) {
                    throw new PTMessageException(PTMessageEnum.COMPENSATE_NOTICE_FAIL);
            	}
            }

            final List<LnCompensateDetail> detailList = new ArrayList<LnCompensateDetail>();
            final LnCompensate lnCompensate = new LnCompensate();
            jsClientDaoSupport.lock(RedisLockEnum.LOCK_DEP_FIXED_LATE_REPAY_NOTICE.getKey()+Constants.PROPERTY_SYMBOL_ZSD);
            transactionTemplate.execute(new TransactionCallbackWithoutResult(){
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {

                    lnCompensate.setApplyTime(req.getApplyTime() == null ? null : DateUtil.parseDate(req.getApplyTime()));
                    lnCompensate.setCreateTime(new Date());
                    lnCompensate.setFinishTime(req.getFinishTime() == null ? null : DateUtil.parseDate(req.getFinishTime()));
                    lnCompensate.setOrderNo(req.getOrderNo());
                    lnCompensate.setPartnerCode(Constants.PROPERTY_SYMBOL_ZSD);
                    lnCompensate.setPayOrderNo(req.getPayOrderNo());
                    lnCompensate.setTotalMount(req.getTotalAmount() == null ? null : Double.valueOf(req.getTotalAmount()));
                    lnCompensate.setUpdateTime(new Date());
                    lnCompensateMapper.insertSelective(lnCompensate);

                    ArrayList<HashMap<String, Object>> detailMapList = req.getRepayments();
                    for (HashMap<String, Object> detailMap : detailMapList) {
                        LnCompensateDetail detailRecord = new LnCompensateDetail();
                        detailRecord.setCompensateId(lnCompensate.getId());
                        detailRecord.setCreateTime(new Date());
                        detailRecord.setInterest((Double)detailMap.get("interest"));
                        detailRecord.setInterestOverdue((Double)detailMap.get("interestOverdue"));
                        detailRecord.setPartnerLoanId((String)detailMap.get("loanId"));
                        detailRecord.setPartnerRepayId((String)detailMap.get("repayId"));
                        detailRecord.setPartnerUserId((String)detailMap.get("userId"));
                        detailRecord.setPrincipal((Double)detailMap.get("principal"));
                        detailRecord.setPrincipalOverdue((Double)detailMap.get("principalOverdue"));
                        detailRecord.setRepaySerial((Integer)detailMap.get("repaySerial"));
                        detailRecord.setRepayType((String)detailMap.get("repayType"));
                        //detailRecord.setReservedfield1((String)detailMap.get("reservedField1"));
                        detailRecord.setStatus(Constants.COMPENSATE_REPAYS_STATUS_INIT);
                        detailRecord.setTotal((Double)detailMap.get("total"));
                        detailRecord.setUpdateTime(new Date());
                        lnCompensateDetailMapper.insertSelective(detailRecord);

                        detailList.add(detailRecord);
                    }

                }
            });
            //List<LnCompensateDetail> agreementList = new ArrayList<LnCompensateDetail>();
            for (LnCompensateDetail lnCompensateDetail : detailList) {
                try {
                    /**
                     * 循环判断Ln_repay_schedule状态是否为REPAID，
                     * 			是则做重复还款处理，return；
                     * 		否则需要把对应记录状态改为LATE_NOT，
                     * 			调用“B还款结果处理”；（仅代偿SUCC）
                     */
                	LnUserExample lnUserExample = new LnUserExample();
                    lnUserExample.createCriteria().andPartnerUserIdEqualTo(lnCompensateDetail.getPartnerUserId())
                    .andPartnerCodeEqualTo(lnCompensate.getPartnerCode());
                    List<LnUser> lnUserList = lnUserMapper.selectByExample(lnUserExample);
                    if(CollectionUtils.isEmpty(lnUserList)){
                        logger.info("===========赞时贷【代偿还款】借款用户数据未获得================");
                        throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
                    }
                    LnLoanExample loanExample = new LnLoanExample();
                    loanExample.createCriteria().andPartnerLoanIdEqualTo(lnCompensateDetail.getPartnerLoanId())
                    	.andLnUserIdEqualTo(lnUserList.get(0).getId());
                    List<LnLoan>  lnLoanList= lnLoanMapper.selectByExample(loanExample);
                    if(CollectionUtils.isEmpty(lnLoanList)){
                    	logger.info("===========赞时贷【代偿还款】lnCompensateDetail.id="+lnCompensateDetail.getId()+"未查询到借款信息=============");
                        continue;
                    }
                    LnLoan lnLoan = lnLoanList.get(0);

                    //前置校验：2、还款计划表中是否存在，状态是否已还
                    LnRepayScheduleExample repayScheduleExample = new LnRepayScheduleExample();
                    repayScheduleExample.createCriteria().andPartnerRepayIdEqualTo(lnCompensateDetail.getPartnerRepayId())
                            .andLoanIdEqualTo(lnLoan.getId()).andStatusNotEqualTo(Constants.LN_REPAY_CANCELLED);
                    List<LnRepaySchedule> repaySchedulList = lnRepayScheduleMapper.selectByExample(repayScheduleExample);

                    if(CollectionUtils.isEmpty(repaySchedulList)){
                        logger.info("===========【代偿还款】还款计划表数据未获得================");
                        throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
                    }
                    Date today = DateUtil.parseDate(DateUtil.formatYYYYMMDD(new Date()));
                    LnRepaySchedule lnRepaySchedule = repaySchedulList.get(0);
                    //判断是否已还款，若已还款，ln_repeat_repay_record记库，调用“重复还款入营收账记账”
                    if(Constants.LN_REPAY_REPAIED.equals(lnRepaySchedule.getStatus()) ||
                            Constants.LN_REPAY_LATE_REPAIED.equals(lnRepaySchedule.getStatus())){
                        //调 重复还款处理
                        LnRepeatRepayRecord lnRepeatRepayRecord = new LnRepeatRepayRecord();
                        lnRepeatRepayRecord.setPartnerCode(Constants.PROPERTY_SYMBOL_ZSD);
                        lnRepeatRepayRecord.setRepayAmount(lnCompensateDetail.getTotal());
                        lnRepeatRepayRecord.setRepayOrderNo(req.getOrderNo());
                        lnRepeatRepayRecord.setRepayPlanId(lnRepaySchedule.getId());
                        lnRepeatRepayRecord.setRepayType(Constants.REPAY_TYPE_COMPENSATE);
                        lnRepeatRepayRecord.setReturnAmount(lnCompensateDetail.getTotal());
                        lnRepeatRepayRecord.setSettleDate(today); //结算日期
                        lnRepeatRepayRecord.setStatus(Constants.REPEAT_REPAY_STATUS_INIT);
                        lnRepeatRepayRecord.setCreateTime(new Date());
                        lnRepeatRepayRecord.setUpdateTime(new Date());
                        repeatRepayProcess(lnRepeatRepayRecord);

                        //修改代偿明细
                        LnCompensateDetail detailTemp = new LnCompensateDetail();
                        detailTemp.setId(lnCompensateDetail.getId());
                        detailTemp.setStatus(Constants.COMPENSATE_REPAYS_STATUS_REPEAT);
                        detailTemp.setUpdateTime(new Date());
                        lnCompensateDetailMapper.updateByPrimaryKeySelective(detailTemp);

                    } else if(Constants.LN_REPAY_LATE_NOT.equals(lnRepaySchedule.getStatus())){
                        //已代偿的账单不进行处理，做告警处理
                        logger.info(">>>代偿单号["+req.getOrderNo()+"]对账单编号["+lnRepaySchedule.getId()+"]代偿重复！<<<");
                        specialJnlService.warn4Fail(0d,"代偿单号["+req.getOrderNo()+"]对账单编号["+lnRepaySchedule.getId()+"]代偿重复",
                                req.getOrderNo(), "代偿重复",false);
                    } else if(Constants.LN_REPAY_CANCELLED.equals(lnRepaySchedule.getStatus())){
                        //已废除的账单不进行处理，做告警处理
                        logger.info(">>>代偿单号["+req.getOrderNo()+"]对账单编号["+lnRepaySchedule.getId()+"]lnRepaySchedule已废除！<<<");
                        specialJnlService.warn4Fail(0d,"代偿单号["+req.getOrderNo()+"]对账单编号["+lnRepaySchedule.getId()+"]还款计划已废除",
                                req.getOrderNo(), "还款计划已废除",false);
                    } else{
                        //循环调用代偿系统分账处理
                        CompensateRepaySysSplitInfo info = new CompensateRepaySysSplitInfo();
                        info.setLoanId(lnLoan.getId());
                        info.setPartnerRepayId(lnCompensateDetail.getPartnerRepayId());
                        info.setPartnerLoanId(lnLoan.getPartnerLoanId());
                        info.setOrderNo(req.getOrderNo());
                        info.setRepayAmount(lnCompensateDetail.getTotal());
                        info.setLnCompensateDetailId(lnCompensateDetail.getId());
                        compensateRepaySysSplit(info);

						/*if(lnCompensateDetail.getPrincipal() > 0){
							//本金代偿，生成协议
							agreementList.add(lnCompensateDetail);
						}*/
                    }

                } catch (Exception e) {
                    logger.error("代偿明细编号["+lnCompensateDetail.getId()+"]处理异常", e);
                    specialJnlService.warn4Fail(lnCompensateDetail.getTotal(),"代偿明细编号["+lnCompensateDetail.getId()+"]处理异常",
                            req.getOrderNo(), "代偿明细处理",false);
                }

            }

        }finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_DEP_FIXED_LATE_REPAY_NOTICE.getKey()+Constants.PROPERTY_SYMBOL_ZSD);
        }


    }

	@Override
	public void overdueRepaySplit4ZSD(final LnLoan lnLoan, final LnRepaySchedule repaySchedule,
			final Double repayAmount, final List<LnLoanRelation> relationList) {
		/**
		 * 计算
		 * 0、每笔债权协议利率之和，LnDepositionRepaySchedule获取
		 * 1、保证金=借款总本金*3%/12
		 * 2、赞时贷营收 = 还款总金额 - 还款本金-每笔债权协议利率之和  -保证金
		 * 3、ln_repay_schedule更新状态为逾期已还 ；
		 * 4、新增存管还款计划表及明细
		 * 5、调用系统记账
		 */
		transactionTemplate.execute(new TransactionCallbackWithoutResult(){
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
            	//还款资金：债权协议利率之和（包括本金）
				LnDepositionRepayScheduleExample depSchedulExample = new LnDepositionRepayScheduleExample();
				depSchedulExample.createCriteria().andLoanIdEqualTo(lnLoan.getId()).andPartnerRepayIdEqualTo(repaySchedule.getPartnerRepayId());
				List<LnDepositionRepaySchedule> depSchedulList = lnDepositionRepayScheduleMapper.selectByExample(depSchedulExample);
				Double agreementAmount = depSchedulList.get(0).getPlanTotal();

				//还款本金
				Double repayPrincipal = getRepayDetailAmount(repaySchedule.getId(), LoanSubjects.SUBJECT_CODE_PRINCIPAL);

				//赞时贷保证金 = 借款本金*3%/12
				Double marginRate = sysConfigService.findRatePercentByKey(Constants.ZSD_SYS_MARGIN_RATE);
				Double mRate = marginRate == null ? 3 : marginRate;
				Double M_amount = MoneyUtil.divide(MoneyUtil.multiply(lnLoan.getApproveAmount(),mRate).doubleValue(),1200).doubleValue();

				//赞时贷营收 = （计划还款总金额 ） - 债权协议利率之和（包括本金）-赞时贷保证金 (可能为负数)
				Double ZSDRevenue = MoneyUtil.subtract(MoneyUtil.subtract(repayAmount, agreementAmount).doubleValue(),M_amount).doubleValue();

				//bs_revenue_trans_detail记录合作方还款营收收入
        		if(ZSDRevenue.compareTo(0d) != 0){
					BsRevenueTransDetail bsRevenueTransDetail = new BsRevenueTransDetail();
	        		bsRevenueTransDetail.setPartnerCode(PartnerEnum.ZSD.getCode());
	        		bsRevenueTransDetail.setTransType(Constants.REVENUE_TYPE_REPAY_INCOME);
	        		bsRevenueTransDetail.setLoanId(lnLoan.getId());
	        		bsRevenueTransDetail.setRepaySerial(repaySchedule.getSerialId());
	        		bsRevenueTransDetail.setRepayScheduleId(repaySchedule.getId());
	        		bsRevenueTransDetail.setRepayAmount(repayAmount);
	        		bsRevenueTransDetail.setRevenueAmount(ZSDRevenue);
	        		bsRevenueTransDetail.setRepayScheduleId(repaySchedule.getId());
	        		bsRevenueTransDetail.setCreateTime(new Date());
	        		bsRevenueTransDetail.setUpdateTime(new Date());
	        		bsRevenueTransDetailMapper.insertSelective(bsRevenueTransDetail);
	        	}


				//ln_repay_schedule更新状态为已还款
				LnRepaySchedule lnRepayScheduleTemp = new LnRepaySchedule();
				lnRepayScheduleTemp.setId(repaySchedule.getId());
				lnRepayScheduleTemp.setFinishTime(new Date());
				lnRepayScheduleTemp.setUpdateTime(new Date());
				lnRepayScheduleTemp.setStatus(Constants.LN_REPAY_LATE_REPAIED);
				lnRepayScheduleMapper.updateByPrimaryKeySelective(lnRepayScheduleTemp);

		        //获取最大的SerialId
				Integer maxSerialId = repaySchedule.getSerialId();
				//存管还款计划表
				LnDepositionRepaySchedule schedul = new LnDepositionRepaySchedule();
				schedul.setLnUserId(lnLoan.getLnUserId());
				schedul.setLoanId(lnLoan.getId());
				schedul.setPartnerRepayId(repaySchedule.getPartnerRepayId());
				schedul.setSerialId(maxSerialId);
				schedul.setPlanDate(repaySchedule.getPlanDate());
				schedul.setPlanTotal(agreementAmount);
				schedul.setStatus(Constants.LN_REPAY_STATUS_INIT);
				schedul.setReturnFlag(Constants.DEP_REPAY_RETURN_FLAG_DF_PENDING);
				schedul.setRepayType(Constants.LN_DEP_REPAY_SCHEDULE_REPAY_TYPE_LATE_REPAY);
				schedul.setCreateTime(new Date());
				schedul.setUpdateTime(new Date());
				lnDepositionRepayScheduleMapper.insertSelective(schedul);

				//存管还款计划表明细
				LnDepositionRepayScheduleDetail detailPrincipal = new LnDepositionRepayScheduleDetail();
				detailPrincipal.setPlanId(schedul.getId());
				detailPrincipal.setPlanAmount(repayPrincipal);
				detailPrincipal.setSubjectCode(LoanSubjects.SUBJECT_CODE_PRINCIPAL.getCode());
				detailPrincipal.setCreateTime(new Date());
				detailPrincipal.setUpdateTime(new Date());
				lnDepositionRepayScheduleDetailMapper.insertSelective(detailPrincipal);

				LnDepositionRepayScheduleDetail detailInterest = new LnDepositionRepayScheduleDetail();
				detailInterest.setPlanId(schedul.getId());
				detailInterest.setPlanAmount(MoneyUtil.subtract(agreementAmount, repayPrincipal).doubleValue());
				detailInterest.setSubjectCode(LoanSubjects.SUBJECT_CODE_INTEREST.getCode());
				detailInterest.setCreateTime(new Date());
				detailInterest.setUpdateTime(new Date());
				lnDepositionRepayScheduleDetailMapper.insertSelective(detailInterest);

				//调用系统记账
				RepayInfo repayInfo = new RepayInfo();
				repayInfo.setPartner(PartnerEnum.ZSD);
		        repayInfo.setAmount(repayAmount);
		       // repayInfo.setLoanActId(relationList.get(0).getLnSubAccountId());
		        repayInfo.setPrincipal(getRepayDetailAmount(repaySchedule.getId(), LoanSubjects.SUBJECT_CODE_PRINCIPAL));
		        repayInfo.setInterest(MoneyUtil.subtract(agreementAmount, repayInfo.getPrincipal()).doubleValue());
		        repayInfo.setFee(0d);
		        repayInfo.setBailAmount(M_amount);
		        //repayInfo.setChargeRuleId(lnLoan.getChargeRuleId());
		        repayInfo.setThdRepayAmount(agreementAmount);
		        repayInfo.setRevenueZanAmount(ZSDRevenue);

		        repayAccountService.chargeOverdueRepay(repayInfo);


			}
		});

	}

    @Override
	public void depSevenRepayApply(final G2BReqMsg_DepLoan7_CutRepayConfirm req) {
        try{
            //订单是否重复
            LnRepayExample repayExample = new LnRepayExample();
            repayExample.createCriteria().andPartnerOrderNoEqualTo(req.getOrderNo());
            List<LnRepay> repayList = lnRepayMapper.selectByExample(repayExample);
            if (CollectionUtils.isNotEmpty(repayList) && repayList.size() > 0) {
                throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "订单号重复");
            }
            if (CollectionUtils.isNotEmpty(req.getLoans())) {
                //借款用户是否存在
                LnUserExample lnUserExample = new LnUserExample();
                lnUserExample.createCriteria().andPartnerCodeEqualTo(PartnerEnum.SEVEN_DAI_SELF.getCode())
                        .andPartnerUserIdEqualTo(req.getUserId());
                List<LnUser> lnUsers = lnUserMapper.selectByExample(lnUserExample);
                if (CollectionUtils.isEmpty(lnUsers)) {
                    throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND, "找不到借款用户信息");
                }
                //该银行是否支持代扣
                List<Pay19BankVO> pay19BankVOs = bs19payBankMapper.selectByChanelPayTypeBankCode(Constants.CHANNEL_BAOFOO, Constants.PAY_TYPE_HOLD, req.getBankCode());
                if (CollectionUtils.isEmpty(pay19BankVOs)) {
                    throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND, "银行编码[" + req.getBankCode() + "]不存在或该银行不支持代扣");
                }
                final LnUser lnUser = lnUsers.get(0);
                final String orderNo = Util.generateOrderNo4BaoFoo(8);
                final String bgwOrderNo = Util.generateSysOrderNo(Constants.BGW_ORDER_NUMBER_GENERATE_WITHHOLD);
                transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                    @Override
                    protected void doInTransactionWithoutResult(TransactionStatus status) {
                        ArrayList<HashMap<String, Object>> loans = req.getLoans();
                        if (loans.size() > 1) {
                        	throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND, "同一个还款报文，不能存在多笔借款的还款信息");
                        }
                        for (HashMap<String, Object> loan : loans) {
                            String loanId = (String) loan.get("loanId");
                            ArrayList<HashMap<String, Object>> repayments = (ArrayList<HashMap<String, Object>>) loan.get("repayments");
                            //每笔借款是否存在
                            LnLoanExample loanExample = new LnLoanExample();
                            loanExample.createCriteria().andPartnerLoanIdEqualTo(loanId)
                                    .andLnUserIdEqualTo(lnUser.getId()).andStatusEqualTo(LoanStatus.LOAN_STATUS_PAIED.getCode());
                            List<LnLoan> loanList = lnLoanMapper.selectByExample(loanExample);
                            if (CollectionUtils.isEmpty(loanList)) {
                                throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND, "找不到借款[" + loanId + "]信息");
                            }
                            final LnLoan lnLoan = loanList.get(0);
                            //该笔借款是否有还款正在进行中
                            if (lnLoan.getIsRepaying().equals(Constants.DEP_REPAY_REPAYING)) {
                                throw new PTMessageException(PTMessageEnum.DEP_REPAY_REPAYING);
                            }

                            if (CollectionUtils.isEmpty(repayments)) {
                                throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND, "代扣还款七贷传入账单为空");
                            }
                            
                            //最后一期账单编号
                            String lastPeriodPartnerRepayId = lnRepayScheduleMapper.selectLastPeriodPartnerRepayId(lnLoan.getId());
                            boolean isLastPeriodRepay = false;
                            // 还款总金额
                            Double repayPrincipal = 0d;
                            
                            // 多期账单，如果存在已还款的账单则移除，并循环下一期账单
                            // 如果账单不存在且为0期账单，则生成新账单
                            ArrayList<HashMap<String, Object>> newRepayments = new ArrayList<>();
                            for (HashMap<String, Object> repay : repayments) {
                            	String repayId = (String) repay.get("repayId");
                                Double principal = (Double) repay.get("principal");
                                Long repaySerial = (Long) repay.get("repaySerial");
                                Double total = (Double) repay.get("total");
                                Double interest = (Double) repay.get("interest");
                                Double principalOverdue = (Double) repay.get("principalOverdue");
                                Double interestOverdue = (Double) repay.get("interestOverdue");
                                Double penalty = (Double) repay.get("penalty");

                            	LnRepayScheduleExample lnRepayScheduleExample = new LnRepayScheduleExample();
                                lnRepayScheduleExample.createCriteria().andPartnerRepayIdEqualTo(repayId).andLoanIdEqualTo(lnLoan.getId());
                                List<LnRepaySchedule> lnRepaySchedules = lnRepayScheduleMapper.selectByExample(lnRepayScheduleExample);
                                if (CollectionUtils.isEmpty(lnRepaySchedules)) {
                                    if (Constants.PARTNER_BUSINESS_FLAG_REPAY_ANY_TIME.equals(lnLoan.getPartnerBusinessFlag()) && repaySerial == 0) {
                                        // 随借随还产品：提前还款，生成0期账单
                                        generateRepayBill(lnLoan.getId(), repayId, principal, repaySerial, total, interest, principalOverdue, interestOverdue);

                                        repayPrincipal = MoneyUtil.add(repayPrincipal, principal).doubleValue();
                                        newRepayments.add(repay);
                                    } else {
                                        // 账单不存在，告警
                                        String detail = PartnerEnum.SEVEN_DAI_SELF.getName() + "（代扣还款）账单不存在partnerRepayId=:" + repayId;
                                        specialJnlService.warnAppoint4Async(0d, detail, repayId, PartnerEnum.SEVEN_DAI_SELF.getName() + "代扣还款处理",false, Constants.EMERGENCY_MOBILE);

                                        // 非提前还款（非0期账单），账单必须存在
                                        throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND, "账单为空");
                                    }
                                } else {
                               	 	LnRepaySchedule lnRepaySchedule = lnRepaySchedules.get(0);
                                    String scheduleStatus = lnRepaySchedule.getStatus();
                                    if (LoanStatus.REPAY_SCHEDULE_STATUS_REPAIED.getCode().equals(scheduleStatus) ||
                                            LoanStatus.REPAY_SCHEDULE_STATUS_CANCELLED.getCode().equals(scheduleStatus) ||
                                            LoanStatus.REPAY_SCHEDULE_STATUS_LATE_REPAIED.getCode().equals(scheduleStatus) ||
                                            LoanStatus.REPAY_SCHEDULE_STATUS_LATE_NOT.getCode().equals(scheduleStatus)) {                                    
                                    } else {
                                        repayPrincipal = MoneyUtil.add(repayPrincipal, principal).doubleValue();
                                    	newRepayments.add(repay);
                                    }
                                }
                            }    
                            if (CollectionUtils.isEmpty(newRepayments)) {
                                throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "账单[" + (String)repayments.get(0).get("repayId") + "]已存在还款或已废除");
                            }

                            // 随借随还产品：本金还款金额不能超过剩余未还本金金额
                            if (Constants.PARTNER_BUSINESS_FLAG_REPAY_ANY_TIME.equals(lnLoan.getPartnerBusinessFlag())) {
//                                Double prePrincipalTotal = MoneyUtil.subtract(lnLoan.getApproveAmount(), lnRepayScheduleMapper.sumRepaiedByLoanId(lnLoan.getId())).doubleValue();
                                Double sumLeftPrincipal = lnLoanRelationMapper.sumLeftAmountByLoanId(lnLoan.getId()) == null ? 0 : lnLoanRelationMapper.sumLeftAmountByLoanId(lnLoan.getId());
                                if (sumLeftPrincipal.compareTo(repayPrincipal) < 0) {
                                    throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "账单[" + (String)repayments.get(0).get("repayId") + "]本金还款金额不能超过剩余未还本金金额");
                                }
                            }
                            
                            for (HashMap<String, Object> repay : newRepayments) {
                                //是否已还款/是否需要账单同步
                                String repayId = (String) repay.get("repayId");
                                Double total = (Double) repay.get("total");
                                Double principal = (Double) repay.get("principal");
                                Double interest = (Double) repay.get("interest");
                                Double principalOverdue = (Double) repay.get("principalOverdue");
                                Double interestOverdue = (Double) repay.get("interestOverdue");
                                Double penalty = (Double) repay.get("penalty");
                                Long repaySerial = (Long) repay.get("repaySerial");
                                //预留字段
                                //String reservedField1 = (String) repay.get("reservedField1");

                                LnRepayScheduleExample lnRepayScheduleExample = new LnRepayScheduleExample();
                                lnRepayScheduleExample.createCriteria().andPartnerRepayIdEqualTo(repayId).andLoanIdEqualTo(lnLoan.getId());
                                List<LnRepaySchedule> lnRepaySchedules = lnRepayScheduleMapper.selectByExample(lnRepayScheduleExample);

                                //记录还款信息表和还款信息明细表
                                LnRepay lnRepay = new LnRepay();
                                if (CollectionUtils.isEmpty(lnRepaySchedules)) {
                                	 throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND, "账单为空");
                                } else {
                                	 LnRepaySchedule lnRepaySchedule = lnRepaySchedules.get(0);
                                     if (lnRepaySchedule.getReturnFlag().equals(Constants.LN_REPAY_RETURN_FLAG_PENDING) ||
                                             lnRepaySchedule.getReturnFlag().equals(Constants.LN_REPAY_RETURN_FLAG_PROCESS)) {
                                         throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "账单[" + lnRepaySchedule.getPartnerRepayId() + "]正在处理中");
                                     }
                                     lnRepay.setRepayPlanId(lnRepaySchedule.getId());
                                     String scheduleStatus = lnRepaySchedule.getStatus();
                                     if (LoanStatus.REPAY_SCHEDULE_STATUS_REPAIED.getCode().equals(scheduleStatus) ||
                                             LoanStatus.REPAY_SCHEDULE_STATUS_CANCELLED.getCode().equals(scheduleStatus) ||
                                             LoanStatus.REPAY_SCHEDULE_STATUS_LATE_REPAIED.getCode().equals(scheduleStatus) ||
                                             LoanStatus.REPAY_SCHEDULE_STATUS_LATE_NOT.getCode().equals(scheduleStatus)) {
                                    	 throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "账单[" + repayId + "]已存在还款或已废除");
                                     }

                                     //是否已存在重复还款记录
                                     repeatRepayJudge(lnRepaySchedule.getId(), repayId);

                                    // 非随借随还产品：借款本金不支持部分还款
                                    if (!Constants.PARTNER_BUSINESS_FLAG_REPAY_ANY_TIME.equals(lnLoan.getPartnerBusinessFlag())) {
                                        // 账单不能还部分本金
                                        RepayScheduleVO repayScheduleVO = lnRepayScheduleMapper.selectRepaySchedulePrincipal(lnLoan.getId(), repayId);
                                        if (repayScheduleVO != null && repayScheduleVO.getPlanAmount().compareTo(principal) != 0) {
                                            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "账单[" + repayId + "]不能还部分本金");
                                        }
                                    }
                                }
                                lnRepay.setStatus(LoanStatus.REPAY_STATUS_PAYING.getCode());
                                lnRepay.setUpdateTime(new Date());
                                lnRepay.setBgwOrderNo(bgwOrderNo);
                                lnRepay.setCreateTime(new Date());
                                lnRepay.setDoneTotal(total);
                                lnRepay.setLnUserId(lnUser.getId());
                                lnRepay.setPayOrderNo(orderNo);
                                lnRepay.setLoanId(lnLoan.getId());
                                lnRepay.setPartnerOrderNo(req.getOrderNo());
                                lnRepay.setInformStatus(LoanStatus.NOTICE_STATUS_INIT.getCode());
                                lnRepay.setApplyTime(req.getApplyTime());
                                lnRepayMapper.insertSelective(lnRepay);

                                LnRepayDetail lnRepayDetail = new LnRepayDetail();
                                lnRepayDetail.setUpdateTime(new Date());
                                lnRepayDetail.setCreateTime(new Date());
                                lnRepayDetail.setRepayId(lnRepay.getId());
                                lnRepayDetail.setDoneAmount(principal != null ? principal : 0d);
                                lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_PRINCIPAL.getCode());
                                lnRepayDetailMapper.insertSelective(lnRepayDetail);

                                lnRepayDetail.setDoneAmount(interest != null ? interest : 0d);
                                lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_INTEREST.getCode());
                                lnRepayDetailMapper.insertSelective(lnRepayDetail);

                                lnRepayDetail.setDoneAmount(principalOverdue != null ? principalOverdue : 0d);
                                lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_PRINCIPAL_OVERDUE.getCode());
                                lnRepayDetailMapper.insertSelective(lnRepayDetail);

                                lnRepayDetail.setDoneAmount(interestOverdue != null ? interestOverdue : 0d);
                                lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_INTEREST_OVERDUE.getCode());
                                lnRepayDetailMapper.insertSelective(lnRepayDetail);

                                lnRepayDetail.setDoneAmount(penalty != null ? penalty : 0d);
                                lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_PENALTY.getCode());
                                lnRepayDetailMapper.insertSelective(lnRepayDetail);
                            }

                        }
                    }
                });
                //记录ln_pay_orders,ln_pay_orders_jnl表
                LnPayOrders lnPayOrders = new LnPayOrders();
                lnPayOrders.setCreateTime(new Date());
                lnPayOrders.setAccountType(1);
                lnPayOrders.setAmount(req.getTotalAmount());
                lnPayOrders.setBankCardNo(req.getBankCard());
                lnPayOrders.setBankId(pay19BankVOs.get(0).getBankId());
                lnPayOrders.setBankName(pay19BankVOs.get(0).getBankName());
                lnPayOrders.setChannel(Constants.CHANNEL_BAOFOO);
                lnPayOrders.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DK.getCode());
                lnPayOrders.setIdCard(req.getIdCard());
                lnPayOrders.setLnUserId(lnUser.getId());
                lnPayOrders.setMobile(req.getMobile());
                lnPayOrders.setMoneyType(0);
                lnPayOrders.setOrderNo(orderNo);
                lnPayOrders.setPartnerCode(PartnerEnum.SEVEN_DAI_SELF.getCode());
                lnPayOrders.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_INIT.getCode()));
                lnPayOrders.setSubAccountId(null);
                lnPayOrders.setTransType(LoanStatus.TRANS_TYPE_REPAY.getCode());
                lnPayOrders.setUpdateTime(new Date());
                lnPayOrders.setUserName(req.getName());
                lnPayOrdersMapper.insertSelective(lnPayOrders);

                LnPayOrdersJnl lnPayOrdersJnl = new LnPayOrdersJnl();
                lnPayOrdersJnl.setSubAccountId(null);
                lnPayOrdersJnl.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DK.getCode());
                lnPayOrdersJnl.setCreateTime(new Date());
                lnPayOrdersJnl.setOrderId(lnPayOrders.getId());
                lnPayOrdersJnl.setOrderNo(lnPayOrders.getOrderNo());
                lnPayOrdersJnl.setTransAmount(lnPayOrders.getAmount());
                lnPayOrdersJnl.setTransCode(LoanStatus.ORDERS_JNL_STATUS_INIT.getCode());
                lnPayOrdersJnl.setUserId(lnUser.getId());
                lnPayOrdersJnl.setSysTime(new Date());
                lnPayOrdersJnlMapper.insertSelective(lnPayOrdersJnl);

                //入还款队列
                LnBindCard lnBindCard = new LnBindCard();
                lnBindCard.setLnUserId(lnUser.getId());
                lnBindCard.setBankCard(req.getBankCard());
                lnBindCard.setIdCard(req.getIdCard());
                lnBindCard.setUserName(req.getName());
                lnBindCard.setBankCode(req.getBankCode());
                lnBindCard.setMobile(req.getMobile());

                //入redis
                try {
                    RepayQueueDTO repayQueueDTO = new RepayQueueDTO();
                    repayQueueDTO.setLnBindCard(lnBindCard);
                    repayQueueDTO.setLnPayOrder(lnPayOrders);
                    repayQueueDTO.setChannel(PartnerEnum.SEVEN_DAI_SELF.getCode());
                    if (!StringUtil.isEmpty(req.getUserSignNo())) {
                    	repayQueueDTO.setUserSignNo(req.getUserSignNo());
					}
                    if (!StringUtil.isEmpty(req.getPayIP())) {
                    	repayQueueDTO.setPayIP(req.getPayIP());
					}
                    jsClientDaoSupport.rpush("repay_queue", JSON.toJSONString(repayQueueDTO));
                    logger.info(">>>入还款队列数据:" + JSON.toJSONString(repayQueueDTO) + "<<<");
                }catch (Exception e){
                    logger.error("入还款队列异常", e);
                }
            }
        }finally{

        }
	}

    /**
     * 七贷代扣线下还款（逻辑同代扣还款，但不发起宝付请求，另根据产品要求抛出前记录所有还款信息）
     *
     * 1.加锁,数据校验（还款订单重复、借款用户是否存在、借款是否存在、该笔借款是否有还款正在进行中、
     *   传入账单为空、还款处理中、已还款、已逾期还款、校验还款账单明细本金和还款明细本金是否一致）
     * 2.记录ln_repay(pay_order_no  记录宝付支付订单号),ln_repay_detail,
     *   ln_pay_orders(order_no 记录宝付支付订单号，note 备注'七贷代扣线下还款'),ln_pay_orders_jnl
     * 3.默认支付成功，调用还款支付结果处理（成功）
     *
     * @param req
     */
    @Override
    public void repayOfflineDepSeven(final G2BReqMsg_DepLoan7_CutRepayConfirm req) {
        try{
            jsClientDaoSupport.lock(RedisLockEnum.LOCK_REPAY_SELF.getKey() + PartnerEnum.SEVEN_DAI_SELF.getCode() + req.getIdCard());
            logger.info("=========七贷代扣还款（线下还款），加锁："+ RedisLockEnum.LOCK_REPAY_SELF.getKey() + PartnerEnum.SEVEN_DAI_SELF.getCode() + req.getIdCard() +"=======");

            final String bgwOrderNo = Util.generateSysOrderNo(Constants.BGW_ORDER_NUMBER_GENERATE_OFFLINE_REPAY);
            //根据产品需求,所有发送过来的还款信息都要记录
            LnRepay lnRepay = new LnRepay();
            lnRepay.setStatus(LoanStatus.REPAY_STATUS_FAIL.getCode());
            lnRepay.setBgwOrderNo(bgwOrderNo);
            lnRepay.setRepayType(Constants.IS_OFFLINE_REPAY);
            lnRepay.setDoneTotal(req.getTotalAmount() != null ? req.getTotalAmount() : 0d);
            //记录传入的线下还款宝付订单号，用于对账
            lnRepay.setPayOrderNo(req.getOffPayOrderNo());
            lnRepay.setPartnerOrderNo(req.getOrderNo());
            lnRepay.setInformStatus(LoanStatus.NOTICE_STATUS_INIT.getCode());
            lnRepay.setApplyTime(req.getApplyTime());

            //合作方订单号是否重复
            LnRepayExample repayRepeat = new LnRepayExample();
            repayRepeat.createCriteria().andPartnerOrderNoEqualTo(req.getOrderNo());
            List<LnRepay> repayList = lnRepayMapper.selectByExample(repayRepeat);
            if (CollectionUtils.isNotEmpty(repayList)) {
                throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "订单号重复");
            }
            //借款用户是否存在
            LnUserExample lnUserExample = new LnUserExample();
            lnUserExample.createCriteria().andPartnerCodeEqualTo(PartnerEnum.SEVEN_DAI_SELF.getCode()).andPartnerUserIdEqualTo(req.getUserId());
            List<LnUser> lnUsers = lnUserMapper.selectByExample(lnUserExample);
            if (CollectionUtils.isEmpty(lnUsers)) {
                lnRepay.setUpdateTime(new Date());
                lnRepay.setCreateTime(new Date());
                lnRepayMapper.insertSelective(lnRepay);
                throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND, "找不到借款用户信息");
            }
            final LnUser lnUser = lnUsers.get(0);
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {
                    ArrayList<HashMap<String, Object>> loanInfos = req.getLoans();
                    if (loanInfos.size() > 1) {
                    	throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND, "同一个还款报文，不能存在多笔借款的还款信息");
                    }
                    for(HashMap<String, Object> loanInfo :loanInfos){
                        String loanId = (String) loanInfo.get("loanId");
                        ArrayList<HashMap<String, Object>> repayments = (ArrayList<HashMap<String, Object>>) loanInfo.get("repayments");

                        //借款是否存在
                        LnLoanExample loanExample = new LnLoanExample();
                        loanExample.createCriteria().andPartnerLoanIdEqualTo(loanId)
                                .andLnUserIdEqualTo(lnUser.getId()).andStatusEqualTo(LoanStatus.LOAN_STATUS_PAIED.getCode());
                        List<LnLoan> loanList = lnLoanMapper.selectByExample(loanExample);
                        if (CollectionUtils.isEmpty(loanList)) {
                            throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND, "找不到借款[" + loanId + "]信息");
                        }

                        final LnLoan lnLoan = loanList.get(0);
                        //该笔借款是否有还款正在进行中
                        if (lnLoan.getIsRepaying().equals(Constants.DEP_REPAY_REPAYING)) {
                            throw new PTMessageException(PTMessageEnum.DEP_REPAY_REPAYING);
                        }

                        // 还款总金额
                        Double repayPrincipal = 0d;
                        
                        // 多期账单，如果存在已还款的账单则移除，并循环下一期账单
                        // 如果账单不存在且为0期账单，则生成新账单
                        ArrayList<HashMap<String, Object>> newRepayments = new ArrayList<>();
                        for (HashMap<String, Object> repay : repayments) {
                            String repayId = (String) repay.get("repayId");
                            Double principal = (Double) repay.get("principal");
                            Long repaySerial = (Long) repay.get("repaySerial");
                            Double total = (Double) repay.get("total");
                            Double interest = (Double) repay.get("interest");
                            Double principalOverdue = (Double) repay.get("principalOverdue");
                            Double interestOverdue = (Double) repay.get("interestOverdue");
                            Double penalty = (Double) repay.get("penalty");
                        	LnRepayScheduleExample lnRepayScheduleExample = new LnRepayScheduleExample();
                            lnRepayScheduleExample.createCriteria().andPartnerRepayIdEqualTo(repayId).andLoanIdEqualTo(lnLoan.getId());
                            List<LnRepaySchedule> lnRepaySchedules = lnRepayScheduleMapper.selectByExample(lnRepayScheduleExample);
                            if (CollectionUtils.isEmpty(lnRepaySchedules)) {
                                if (Constants.PARTNER_BUSINESS_FLAG_REPAY_ANY_TIME.equals(lnLoan.getPartnerBusinessFlag()) && repaySerial == 0) {
                                    // 随借随还产品：提前还款，生成0期账单
                                    generateRepayBill(lnLoan.getId(), repayId, principal, repaySerial, total, interest, principalOverdue, interestOverdue);

                                    repayPrincipal = MoneyUtil.add(repayPrincipal, principal).doubleValue();
                                    newRepayments.add(repay);
                                } else {
                                    // 账单不存在，告警
                                    String detail = PartnerEnum.SEVEN_DAI_SELF.getName() + "（线下还款）账单不存在partnerRepayId=:" + repayId;
                                    specialJnlService.warnAppoint4Async(0d, detail, repayId, PartnerEnum.SEVEN_DAI_SELF.getName() + "线下还款处理",false, Constants.EMERGENCY_MOBILE);

                                    // 非提前还款(非0期账单)，账单必须存在
                                    throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND, "账单为空");
                                }
                            } else {
                           	 	LnRepaySchedule lnRepaySchedule = lnRepaySchedules.get(0);
                                String scheduleStatus = lnRepaySchedule.getStatus();
                                if (LoanStatus.REPAY_SCHEDULE_STATUS_REPAIED.getCode().equals(scheduleStatus) ||
                                        LoanStatus.REPAY_SCHEDULE_STATUS_CANCELLED.getCode().equals(scheduleStatus) ||
                                        LoanStatus.REPAY_SCHEDULE_STATUS_LATE_REPAIED.getCode().equals(scheduleStatus) ||
                                        LoanStatus.REPAY_SCHEDULE_STATUS_LATE_NOT.getCode().equals(scheduleStatus)) {                                    
                                } else {
                                    repayPrincipal = MoneyUtil.add(repayPrincipal, principal).doubleValue();
                                	newRepayments.add(repay);
                                }
                            }
                        }    
                        if (CollectionUtils.isEmpty(newRepayments)) {
                            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "账单[" + (String)repayments.get(0).get("repayId") + "]已存在还款或已废除");
                        }

                        // 随借随还产品：本金还款金额不能超过剩余未还本金金额
                        if (Constants.PARTNER_BUSINESS_FLAG_REPAY_ANY_TIME.equals(lnLoan.getPartnerBusinessFlag())) {
                        	//Double prePrincipalTotal = MoneyUtil.subtract(lnLoan.getApproveAmount(), lnRepayScheduleMapper.sumRepaiedByLoanId(lnLoan.getId())).doubleValue();
                            Double sumLeftPrincipal = lnLoanRelationMapper.sumLeftAmountByLoanId(lnLoan.getId()) == null ? 0 : lnLoanRelationMapper.sumLeftAmountByLoanId(lnLoan.getId());
                            if (sumLeftPrincipal.compareTo(repayPrincipal) < 0) {
                                throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "账单[" + (String)repayments.get(0).get("repayId") + "]本金还款金额不能超过剩余未还本金金额");
                            }
                        }
                        
                        for (HashMap<String, Object> repayment : newRepayments) {
                            //合作方账单编号
                            String repayId = (String) repayment.get("repayId");
                            // 总额
                            Double total = (Double) repayment.get("total");
                            // 本金
                            Double principal = (Double) repayment.get("principal");
                            // 利息
                            Double interest = (Double) repayment.get("interest");
                            // 本金滞纳金
                            Double principalOverdue = (Double) repayment.get("principalOverdue");
                            // 利息滞纳金
                            Double interestOverdue = (Double) repayment.get("interestOverdue");
                            // 违约金
                            Double penalty = (Double) repayment.get("penalty");

                            LnRepay lnRepayIn = new LnRepay();
                            lnRepayIn.setBgwOrderNo(bgwOrderNo);
                            lnRepayIn.setRepayType(Constants.IS_OFFLINE_REPAY);
                            //记录传入的线下还款宝付订单号，用于对账
                            lnRepayIn.setPayOrderNo(req.getOffPayOrderNo());
                            lnRepayIn.setPartnerOrderNo(req.getOrderNo());
                            lnRepayIn.setInformStatus(LoanStatus.NOTICE_STATUS_INIT.getCode());
                            lnRepayIn.setLoanId(lnLoan.getId());
                            lnRepayIn.setLnUserId(lnUser.getId());
                            lnRepayIn.setApplyTime(req.getApplyTime());

                            LnRepayScheduleExample lnRepayScheduleExample = new LnRepayScheduleExample();
                            lnRepayScheduleExample.createCriteria().andPartnerRepayIdEqualTo(repayId).andLoanIdEqualTo(lnLoan.getId());
                            List<LnRepaySchedule> lnRepaySchedules = lnRepayScheduleMapper.selectByExample(lnRepayScheduleExample);

                            //还款账单表存在时校验
                            if (CollectionUtils.isNotEmpty(lnRepaySchedules)) {
                                LnRepaySchedule lnRepaySchedule = lnRepaySchedules.get(0);

                                //对应账单有还款进行中抛出
                                Boolean isRepaying = lnRepayMapper.isBillRepaying(lnRepaySchedule.getId());
                                if(isRepaying){
                                    throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "账单[" + lnRepaySchedule.getPartnerRepayId() + "]正在处理中");
                                }
                                if (Constants.LN_REPAY_RETURN_FLAG_PENDING.equals(lnRepaySchedule.getReturnFlag()) ||
                                        Constants.LN_REPAY_RETURN_FLAG_PROCESS.equals(lnRepaySchedule.getReturnFlag())) {
                                    throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "账单[" + lnRepaySchedule.getPartnerRepayId() + "]正在处理中");
                                }
                                lnRepayIn.setRepayPlanId(lnRepaySchedule.getId());
                                String scheduleStatus = lnRepaySchedule.getStatus();
                                if (LoanStatus.REPAY_SCHEDULE_STATUS_REPAIED.getCode().equals(scheduleStatus) ||
                                        LoanStatus.REPAY_SCHEDULE_STATUS_CANCELLED.getCode().equals(scheduleStatus) ||
                                        LoanStatus.REPAY_SCHEDULE_STATUS_LATE_NOT.getCode().equals(scheduleStatus) ||
                                        LoanStatus.REPAY_SCHEDULE_STATUS_LATE_REPAIED.getCode().equals(scheduleStatus)) {
                                    throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "账单[" + repayId + "]已存在还款或已废除");
                                }

                                //是否已存在重复还款记录
                                repeatRepayJudge(lnRepaySchedule.getId(), repayId);

                                // 非随借随还产品：借款本金不能部分还款
                                if (!Constants.PARTNER_BUSINESS_FLAG_REPAY_ANY_TIME.equals(lnLoan.getPartnerBusinessFlag())) {
                                    //还款本金不为0或借款本金 抛出
                                    if(LoanStatus.REPAY_SCHEDULE_STATUS_INIT.getCode().equals(scheduleStatus)) {
                                        if(principal > 0 && lnLoan.getApproveAmount().compareTo(principal) != 0) {
                                            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "线下还款传入本金有误");
                                        }
                                    }
                                }
                            } else {
                                throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "账单[" + repayId + "]不存在");
                            }

                            //记录还款信息
                            lnRepayIn.setStatus(LoanStatus.REPAY_STATUS_PAYING.getCode());
                            lnRepayIn.setUpdateTime(new Date());
                            lnRepayIn.setCreateTime(new Date());
                            lnRepayIn.setDoneTotal(total != null ? total : 0d);
                            lnRepayMapper.insertSelective(lnRepayIn);

                            //记录还款信息
                            LnRepayDetail lnRepayDetail = new LnRepayDetail();
                            lnRepayDetail.setUpdateTime(new Date());
                            lnRepayDetail.setCreateTime(new Date());
                            lnRepayDetail.setRepayId(lnRepayIn.getId());
                            // 本金
                            lnRepayDetail.setDoneAmount(principal != null ? principal : 0d);
                            lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_PRINCIPAL.getCode());
                            lnRepayDetailMapper.insertSelective(lnRepayDetail);
                            // 利息
                            lnRepayDetail.setDoneAmount(interest != null ? interest : 0d);
                            lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_INTEREST.getCode());
                            lnRepayDetailMapper.insertSelective(lnRepayDetail);
                            // 本金滞纳金
                            lnRepayDetail.setDoneAmount(principalOverdue != null ? principalOverdue : 0d);
                            lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_PRINCIPAL_OVERDUE.getCode());
                            lnRepayDetailMapper.insertSelective(lnRepayDetail);
                            // 利息滞纳金
                            lnRepayDetail.setDoneAmount(interestOverdue != null ? interestOverdue : 0d);
                            lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_INTEREST_OVERDUE.getCode());
                            lnRepayDetailMapper.insertSelective(lnRepayDetail);
                            // 违约金
                            lnRepayDetail.setDoneAmount(penalty != null ? penalty : 0d);
                            lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_PENALTY.getCode());
                            lnRepayDetailMapper.insertSelective(lnRepayDetail);
                        }
                    }
                }
            });
            //记录ln_pay_orders,ln_pay_orders_jnl表
            LnPayOrders lnPayOrders = new LnPayOrders();
            lnPayOrders.setCreateTime(new Date());
            lnPayOrders.setAccountType(Constants.ORDER_ACCOUNT_TYPE_USER);
            lnPayOrders.setAmount(req.getTotalAmount());
            if(StringUtil.isNotEmpty(req.getBankCard())){
                lnPayOrders.setBankCardNo(req.getBankCard());
            }
            lnPayOrders.setChannel(Constants.CHANNEL_BAOFOO);
            lnPayOrders.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DK.getCode());
            lnPayOrders.setIdCard(req.getIdCard());
            lnPayOrders.setLnUserId(lnUser.getId());
            lnPayOrders.setMobile(req.getMobile());
            lnPayOrders.setMoneyType(0);   //0是人民币
            lnPayOrders.setOrderNo(req.getOffPayOrderNo());
            lnPayOrders.setPartnerCode(PartnerEnum.SEVEN_DAI_SELF.getCode());
            lnPayOrders.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_INIT.getCode()));
            lnPayOrders.setSubAccountId(null);
            lnPayOrders.setTransType(LoanStatus.TRANS_TYPE_REPAY.getCode());
            lnPayOrders.setUpdateTime(new Date());
            lnPayOrders.setUserName(req.getName());
            lnPayOrders.setNote(Constants.OFFLINE_7_SELF_ORDER_NOTE);
            
            // 资产方线下还款现在走的主通道，设置为默认值
            lnPayOrders.setPaymentChannelId(1);
            lnPayOrdersMapper.insertSelective(lnPayOrders);

            LnPayOrdersJnl lnPayOrdersJnl = new LnPayOrdersJnl();
            lnPayOrdersJnl.setSubAccountId(null);
            lnPayOrdersJnl.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DK.getCode());
            lnPayOrdersJnl.setCreateTime(new Date());
            lnPayOrdersJnl.setOrderId(lnPayOrders.getId());
            lnPayOrdersJnl.setOrderNo(lnPayOrders.getOrderNo());
            lnPayOrdersJnl.setTransAmount(lnPayOrders.getAmount());
            lnPayOrdersJnl.setTransCode(LoanStatus.ORDERS_JNL_STATUS_INIT.getCode());
            lnPayOrdersJnl.setUserId(lnUser.getId());
            lnPayOrdersJnl.setSysTime(new Date());
            lnPayOrdersJnlMapper.insertSelective(lnPayOrdersJnl);

            //默认代扣成功，更新订单表为处理中
            payOrdersService.modifyLnOrderStatus4Safe(lnPayOrders.getId(), Integer.parseInt(LoanStatus.ORDERS_STATUS_PAYING.getCode()),
                    null, LoanStatus.BAOFOO_PAY_STATUS_PROCESS.getCode(), "线下还款处理中");

            //还款成功,还款支付结果处理
            DepFixedRepayPayResultInfo payResult = new DepFixedRepayPayResultInfo();
            payResult.setSuccess(true);
            payResult.setAmount(lnPayOrders.getAmount());
            payResult.setOrderNo(lnPayOrders.getOrderNo());
            payResult.setReturnCode(LoanStatus.BAOFOO_PAY_STATUS_SUCCESS.getCode());
            payResult.setReturnMsg("线下还款成功");
            doRepayResultPayProcess(payResult);
        }finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_REPAY_SELF.getKey() + PartnerEnum.SEVEN_DAI_SELF.getCode() + req.getIdCard());
            logger.info("=========七贷代扣还款（线下还款），解锁：" + RedisLockEnum.LOCK_REPAY_SELF.getKey() + PartnerEnum.SEVEN_DAI_SELF.getCode() + req.getIdCard() + "=======");
        }

    }

    @Override
    public void noticePartner2Seven(final LnPayOrders lnPayOrder, final String repayResultMsg) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                logger.info(">>>通知7贷还款通知：[入参]" + JSON.toJSONString(lnPayOrder) + "|errorMsg=" + repayResultMsg + "<<<");

                B2GReqMsg_DepLoan7Notice_RepayResultNotice sevenNoticeRepay = new B2GReqMsg_DepLoan7Notice_RepayResultNotice();

                //通过订单号在还款信息列表其中一条记录中 获取合作方订单号
                LnRepayExample example = new LnRepayExample();
                example.createCriteria().andPayOrderNoEqualTo(lnPayOrder.getOrderNo());
                List<LnRepay> repayList = lnRepayMapper.selectByExample(example);
                if(CollectionUtils.isEmpty(repayList)) {
                    logger.error("通知7贷时未找到BgwOrderNo=" + lnPayOrder.getOrderNo() + "的还款信息记录");
                }
                LnRepay repay = repayList.get(0);
                sevenNoticeRepay.setOrderNo(repay.getPartnerOrderNo());
                sevenNoticeRepay.setChannelLoan(lnPayOrder.getChannel());

                if(repay.getLoanId() == null){
                    logger.error("还款通知7贷时找不到的借款信息id为："+JSONObject.fromObject(repay.getLoanId()).toString());
                }

                if(lnPayOrder.getStatus() == Constants.ORDER_STATUS_SUCCESS){
                    sevenNoticeRepay.setResultCode("SUCCESS");
                    sevenNoticeRepay.setResultMsg(LoanStatus.REPAY_STATUS_REPAIED.getDescription());
                    sevenNoticeRepay.setFinishTime(lnPayOrder.getUpdateTime());
                }else if(lnPayOrder.getStatus() == Constants.ORDER_STATUS_PAYING){
                    sevenNoticeRepay.setResultCode("PROCESS");
                    sevenNoticeRepay.setResultMsg(LoanStatus.REPAY_STATUS_PAYING.getDescription());
                }else{
                    sevenNoticeRepay.setResultCode("FAIL");
                    sevenNoticeRepay.setResultMsg(StringUtil.isEmpty(lnPayOrder.getReturnMsg())?LoanStatus.REPAY_STATUS_FAIL.getDescription():lnPayOrder.getReturnMsg());
                }

                B2GResMsg_DepLoan7Notice_RepayResultNotice res = null;
                LnRepay repayTemp = new LnRepay();
                try {
                    res = depLoan7NoticeService.noticeRepay(sevenNoticeRepay);

                    if (res != null && ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
                        repayTemp.setInformStatus(LoanStatus.NOTICE_STATUS_SUCCESS.getCode());
                    } else {
                        repayTemp.setInformStatus(LoanStatus.NOTICE_STATUS_FAIL.getCode());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    repayTemp.setInformStatus(LoanStatus.NOTICE_STATUS_FAIL.getCode());
                }
                LnRepayExample lnRepayExample = new LnRepayExample();
                lnRepayExample.createCriteria().andPayOrderNoEqualTo(lnPayOrder.getOrderNo());
                repayTemp.setUpdateTime(new Date());
                lnRepayMapper.updateByExampleSelective(repayTemp, lnRepayExample);
            }
        }).start();
    }

    @Override
    public void sevenRepayResultQuery(G2BReqMsg_DepLoan7_QueryRepayResult req, G2BResMsg_DepLoan7_QueryRepayResult res) {
        List<LnRepay> lnRepayList = lnRepayMapper.selectByParOrderNoAndFlag(req.getOrderNo(),PartnerEnum.SEVEN_DAI_SELF.getCode());
        if(CollectionUtils.isEmpty(lnRepayList)) {
            res.setOrderNo(req.getOrderNo());
            res.setChannel(Constants.CHANNEL_BAOFOO);
            res.setResultCode(Constants.ORDER_TRANS_CODE_FAIL);
            res.setResultMsg("7贷还款结果查询未找到还款信息");
            return;
        }
        LnRepay lnRepay = lnRepayList.get(0);
        res.setOrderNo(req.getOrderNo());
        res.setBgwOrderNo(lnRepay.getBgwOrderNo());
        res.setChannel(Constants.CHANNEL_BAOFOO);

        //根据还款订单状态 返回支付状态
        LnPayOrdersExample example = new LnPayOrdersExample();
        example.createCriteria().andPartnerCodeEqualTo(PartnerEnum.SEVEN_DAI_SELF.getCode()).andOrderNoEqualTo(lnRepay.getPayOrderNo());
        List<LnPayOrders> lnPayOrderList = lnPayOrdersMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(lnPayOrderList)){
            res.setResultCode(Constants.ORDER_TRANS_CODE_FAIL);
            res.setResultMsg("7贷还款结果查询未找到orderNo="+req.getOrderNo()+"的还款订单信息");
            return;
        }
        LnPayOrders lnPayOrder = lnPayOrderList.get(0);
        if(lnPayOrder.getStatus() == Constants.ORDER_STATUS_SUCCESS){
            res.setResultCode("SUCCESS");
            res.setResultMsg(LoanStatus.REPAY_STATUS_REPAIED.getDescription());
            res.setFinishTime(new Date());
        }else if(lnPayOrder.getStatus() == Constants.ORDER_STATUS_PAYING
                || lnPayOrder.getStatus() == Constants.ORDER_STATUS_CREATE){
            res.setResultCode("PROCESS");
            res.setResultMsg(LoanStatus.REPAY_STATUS_PAYING.getDescription());
        }else if(lnPayOrder.getStatus() == Constants.ORDER_STATUS_PRE_SUCC){
            if (DateUtil.getDiffeMinute(lnPayOrder.getUpdateTime()) <= 15) {
                res.setResultCode("PROCESS");
                res.setResultMsg(LoanStatus.REPAY_STATUS_PAYING.getDescription());
            }else {
                res.setResultCode("FAIL");
                res.setResultMsg("该订单已失效");
            }
        }else{
            res.setResultCode("FAIL");
            res.setResultMsg(StringUtil.isEmpty(lnPayOrder.getReturnMsg())?LoanStatus.REPAY_STATUS_FAIL.getDescription():lnPayOrder.getReturnMsg());
        }
    }

    @Override
    public void yunDaiBillSync(com.alibaba.fastjson.JSONObject json, LnPayOrders order) {
        Integer lnPayOrderId = json.getInteger("lnPayOrderId");
        String partnerUserId = json.getString("partnerUserId");
        String partnerLoanId = json.getString("partnerLoanId");
        Integer loanId = json.getInteger("loanId");
        JSONArray repayBills = null;
        if(StringUtil.isNotEmpty(json.getString("repayBills"))){
            repayBills = json.getJSONArray("repayBills");
        }
        if(lnPayOrderId == null || loanId == null || StringUtil.isEmpty(partnerUserId)
                || StringUtil.isEmpty(partnerLoanId)){
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR,",redis中数据校验失败");
        }

        // 云贷
        BillInfo billInfo = null;
        // 获取云贷账单
        try{
            billInfo = depFixedBillSyncService.getNewestBill(partnerUserId, partnerLoanId);
        }catch(Exception e){
            logger.warn("还款处理轮询定时获取云贷账单异常");
            jsClientDaoSupport.rpush("repayBill", json.toString());
            return;
        }
        logger.info(">>>云贷同步账单数据" + JSON.toJSONString(billInfo) + "<<<");
        if(billInfo == null || CollectionUtils.isEmpty(billInfo.getRepayments())){
            logger.warn("还款处理轮询定时获取云贷账单为空");
            //重新放入redis中
            jsClientDaoSupport.rpush("repayBill", json.toString());
            return;
        }
        List<QueryBillRepayment> yunBills = billInfo.getRepayments();
        for(QueryBillRepayment billRepayment: yunBills){
            //获取云贷账单为paying,重新放入redis中
            if(billRepayment.getStatus().equals(Constants.YUN_BILL_STATUS_REPAYING)){
                jsClientDaoSupport.rpush("repayBill", json.toString());
                return;
            }
        }

        if(order == null){
            logger.error("还款处理轮询未找到lnPayOrderId=" + lnPayOrderId + "的订单信息");
            //重新放入redis中
            jsClientDaoSupport.rpush("repayBill", json.toString());
            return;
        }
        List<RepayBillVO> repayBillVOList = null;
        if(repayBills != null){
            repayBillVOList = (List<RepayBillVO>) JSONUtil.jsonString2BeanList(repayBills.toJSONString(), RepayBillVO.class);
        }
        if(CollectionUtils.isNotEmpty(repayBillVOList)){
            /**
             * 无记录--REPAIED：插入一条REPAIED记录，调用“B还款结果处理”还款成功处理；
             * 无记录--INIT/REPAYING/LATE_NOT：插入一条INIT数据；
             * 无记录--CANCELLED：插入一条CANCELLED数据；
             * INIT--REPAIED：调用“B还款结果处理”还款成功处理；
             * INIT--CANCELLED：更新状态为CANCELLED；
             * LATE_NOT(代偿完成时会改成此状态)--REPAIED：判断是否已有相关代偿，是则做一笔重复还款处理；
             * REPAIED--REPAIED：判断是否已有相关还款，是则做一笔重复还款处理
             */
            //redis账单不为空时对照redis中账单
            List<QueryBillRepayment> matchBillRepayments = new ArrayList<QueryBillRepayment>();
            for(RepayBillVO repayBill :repayBillVOList){
                boolean isMatch = false;
                for(QueryBillRepayment yunBill :yunBills){
                    if(repayBill.getPartnerRepayId().equals(yunBill.getRepayId())){
                        matchBillRepayments.add(yunBill);
                        if(repayBill.getScheStatus().equals(Constants.LN_REPAY_STATUS_INIT) &&
                                yunBill.getStatus().equals(Constants.YUN_BILL_STATUS_REPAIED)){
                            logger.info(">>>还款处理轮询INIT--REPAIED条件START<<<");
                            NormalRepaySysSplitInfo result = new NormalRepaySysSplitInfo();
                            result.setLnPayOrdersId(order.getId());
                            result.setOrderNo(order.getOrderNo());
                            result.setLoanId(repayBill.getLoanId());
                            result.setRepayAmount(yunBill.getTotal());
                            result.setPartnerRepayId(yunBill.getRepayId());
                            try{
                                logger.info(">>>还款处理轮询INIT--REPAIED条件调用还款结果处理开始<<<");
                                this.normalRepaySysSplit(result);
                                logger.info(">>>还款处理轮询INIT--REPAIED条件调用还款结果处理结束<<<");
                            }catch(Exception e){
                                logger.info(">>>还款处理轮询redis中数据处理错误<<<",e);
                                specialJnlService.warn4Fail(0d,"还款处理轮询时处理partnerRepayId=:" + repayBill.getPartnerRepayId() + "对应redis数据错误",
                                        repayBill.getPartnerRepayId(), "对应redis数据错误",false);
                            }
                            //置return_flag为finish
                            logger.info(">>>还款处理轮询INIT--REPAIED条件置return_flag为finish<<<");
                            LnRepaySchedule lnRepaySchedule = new LnRepaySchedule();
                            lnRepaySchedule.setId(repayBill.getRepayPlanId());
                            lnRepaySchedule.setReturnFlag(Constants.LN_REPAY_RETURN_FLAG_FINISH);
                            lnRepaySchedule.setPayOrderNo(yunBill.getBgwOrderNo());
                            lnRepayScheduleMapper.updateByPrimaryKeySelective(lnRepaySchedule);
                            logger.info(">>>还款处理轮询INIT--REPAIED条件END<<<");
                        }else if(repayBill.getScheStatus().equals(Constants.LN_REPAY_STATUS_INIT) &&
                                yunBill.getStatus().equals(Constants.YUN_BILL_STATUS_CANCELLED)){
                            logger.info(">>>还款处理轮询INIT--CANCELLED条件START,更新状态为CANCELLED,置return_flag为finish<<<");
                            LnRepaySchedule lnRepaySchedule = new LnRepaySchedule();
                            lnRepaySchedule.setId(repayBill.getRepayPlanId());
                            lnRepaySchedule.setStatus(Constants.LN_REPAY_CANCELLED);
                            lnRepaySchedule.setReturnFlag(Constants.LN_REPAY_RETURN_FLAG_FINISH);
                            lnRepaySchedule.setPayOrderNo(yunBill.getBgwOrderNo());
                            lnRepayScheduleMapper.updateByPrimaryKeySelective(lnRepaySchedule);
                            logger.info(">>>还款处理轮询INIT--CANCELLED条件END<<<");
                        }else if(repayBill.getScheStatus().equals(Constants.LN_REPAY_LATE_NOT) &&
                                yunBill.getStatus().equals(Constants.YUN_BILL_STATUS_REPAIED)){
                            //判断是否已有相关代偿，是则做一笔重复还款处理
                            logger.info(">>>还款处理轮询LATE_NOT--REPAIED条件START,调用重复还款处理<<<");
                            LnCompensateDetailExample example = new LnCompensateDetailExample();
                            example.createCriteria().andPartnerLoanIdEqualTo(repayBill.getPartnerLoanId()).andPartnerRepayIdEqualTo(repayBill.getPartnerRepayId());
                            List<LnCompensateDetail> compensateDetails = lnCompensateDetailMapper.selectByExample(example);
                            if(CollectionUtils.isNotEmpty(compensateDetails)){
                                LnRepeatRepayRecord repeatRepay = new LnRepeatRepayRecord();
                                repeatRepay.setPartnerCode(Constants.PRODUCT_PROPERTY_SYMBOL_YUN_DAI_SELF);
                                repeatRepay.setRepayAmount(yunBill.getTotal());
                                repeatRepay.setRepayOrderNo(order.getOrderNo());
                                repeatRepay.setRepayPlanId(repayBill.getRepayPlanId());
                                repeatRepay.setRepayType(Constants.REPAY_TYPE_USER_REPAY);
                                repeatRepay.setReturnAmount(yunBill.getTotal());
                                repeatRepay.setSettleDate(new Date()); //结算日期
                                repeatRepay.setStatus(Constants.REPEAT_REPAY_STATUS_INIT);
                                repeatRepay.setCreateTime(new Date());
                                repeatRepay.setUpdateTime(new Date());
                                try{
                                    repeatRepayProcess(repeatRepay);
                                }catch(Exception e){
                                    logger.info(">>>还款处理轮询LATE_NOT--REPAIED条件调用还款结果处理错误<<<",e);
                                    specialJnlService.warn4Fail(0d,"还款处理轮询LATE_NOT--REPAIED条件调用还款结果处理错误partnerRepayId=" + repayBill.getPartnerRepayId(),
                                            repayBill.getPartnerRepayId(), "对应redis数据错误",false);
                                }
                            }
                            //更新return_flag为finish
                            logger.info(">>>还款处理轮询LATE_NOT--REPAIED条件置return_flag为finish<<<");
                            LnRepaySchedule lnRepaySchedule = new LnRepaySchedule();
                            lnRepaySchedule.setId(repayBill.getRepayPlanId());
                            lnRepaySchedule.setReturnFlag(Constants.LN_REPAY_RETURN_FLAG_FINISH);
                            lnRepaySchedule.setPayOrderNo(yunBill.getBgwOrderNo());
                            lnRepayScheduleMapper.updateByPrimaryKeySelective(lnRepaySchedule);
                            logger.info(">>>还款处理轮询LATE_NOT--REPAIED条件END<<<");
                        }else if(repayBill.getScheStatus().equals(Constants.LN_REPAY_REPAIED) &&
                                yunBill.getStatus().equals(Constants.YUN_BILL_STATUS_REPAIED)){
                            //同一笔账单是否存在除当前订单号的还款信息,是则做一笔重复还款
                            logger.info(">>>还款处理轮询REPAIED--REPAIED条件START,调用重复还款处理<<<");
                            Integer isRepeat = lnRepayMapper.isRepeatRepay(repayBill.getRepayPlanId(),lnPayOrderId);
                            if(isRepeat > 0){
                                LnRepeatRepayRecord repeatRepay = new LnRepeatRepayRecord();
                                repeatRepay.setPartnerCode(Constants.PRODUCT_PROPERTY_SYMBOL_YUN_DAI_SELF);
                                repeatRepay.setRepayAmount(yunBill.getTotal());
                                repeatRepay.setRepayOrderNo(order.getOrderNo());
                                repeatRepay.setRepayPlanId(repayBill.getRepayPlanId());
                                repeatRepay.setRepayType(Constants.REPAY_TYPE_USER_REPAY);
                                repeatRepay.setReturnAmount(yunBill.getTotal());
                                repeatRepay.setSettleDate(new Date()); //结算日期
                                repeatRepay.setStatus(Constants.REPEAT_REPAY_STATUS_INIT);
                                repeatRepay.setCreateTime(new Date());
                                repeatRepay.setUpdateTime(new Date());
                                try{
                                    repeatRepayProcess(repeatRepay);
                                }catch(Exception e){
                                    logger.info(">>>还款处理轮询REPAIED--REPAIED条件调用还款结果处理错误<<<",e);
                                    specialJnlService.warn4Fail(0d,"还款处理轮询REPAIED--REPAIED条件调用还款结果处理错误partnerRepayId=" + repayBill.getPartnerRepayId(),
                                            repayBill.getPartnerRepayId(), "对应redis数据错误",false);
                                }
                            }
                            //更新return_flag为finish
                            logger.info(">>>还款处理轮询REPAIED--REPAIED条件置return_flag为finish<<<");
                            LnRepaySchedule lnRepaySchedule = new LnRepaySchedule();
                            lnRepaySchedule.setId(repayBill.getRepayPlanId());
                            lnRepaySchedule.setReturnFlag(Constants.LN_REPAY_RETURN_FLAG_FINISH);
                            lnRepaySchedule.setPayOrderNo(yunBill.getBgwOrderNo());
                            lnRepayScheduleMapper.updateByPrimaryKeySelective(lnRepaySchedule);
                            logger.info(">>>还款处理轮询REPAIED--REPAIED条件END<<<");
                        }else{
                            logger.info(">>>还款处理轮询有PENDING状态未处理，请检查" + JSON.toJSONString(repayBill) + "<<<");
                            specialJnlService.warn4Fail(0d,"还款处理轮询有PENDING状态未处理，请检查partnerRepayId=" + repayBill.getPartnerRepayId(),
                                    repayBill.getPartnerRepayId(), "云贷还款账单有未处理，请检查",false);
                            continue;
                        }
                        isMatch = true;
                        break;
                    }
                }
                if(!isMatch){
                    logger.info(">>>还款处理轮询有PENDING状态未处理，请检查" + JSON.toJSONString(repayBill) + "<<<");
                    specialJnlService.warn4Fail(0d,"还款处理轮询PENDING账单在云贷列表未找到，请检查partnerRepayId=" + repayBill.getPartnerRepayId(),
                            repayBill.getPartnerRepayId(), "云贷还款账单有未处理，请检查",false);
                }
            }
            yunBills.removeAll(matchBillRepayments);
        }
        //根据还款日对云贷还款账单排序(repay_date ASC)
        logger.info("排序前云贷还款账单:" + yunBills + "<<<");
        Collections.sort(yunBills, new Comparator<QueryBillRepayment>() {
            @Override
            public int compare(QueryBillRepayment o1, QueryBillRepayment o2) {
                return o1.getRepayDate().compareTo(o2.getRepayDate());
            }
        });
        logger.info("排序后云贷还款账单:" + yunBills + "<<<");
        //云贷账单除去redis中的剩余账单
        for(QueryBillRepayment yunRetainBill :yunBills){
            LnRepayScheduleExample example = new LnRepayScheduleExample();
            example.createCriteria().andLoanIdEqualTo(loanId).andPartnerRepayIdEqualTo(yunRetainBill.getRepayId());
            List<LnRepaySchedule> repaySches = lnRepayScheduleMapper.selectByExample(example);
            if(CollectionUtils.isEmpty(repaySches)){
                if(yunRetainBill.getStatus().equals(Constants.YUN_BILL_STATUS_REPAIED)){
                    logger.info(">>>还款处理轮询无记录--REPAIED条件START<<<");
                    //插入一条REPAIED记录
                    logger.info(">>>还款处理轮询无记录--REPAIED条件插入一条REPAIED记录<<<");
                    LnRepaySchedule repaySchedule = new LnRepaySchedule();
                    repaySchedule.setLoanId(loanId);
                    repaySchedule.setPartnerRepayId(yunRetainBill.getRepayId());
                    repaySchedule.setSerialId(yunRetainBill.getRepaySerial());
                    repaySchedule.setPlanDate(yunRetainBill.getRepayDate());
                    repaySchedule.setPlanTotal(yunRetainBill.getTotal());
                    repaySchedule.setStatus(Constants.LN_REPAY_REPAIED);
                    repaySchedule.setCreateTime(new Date());
                    repaySchedule.setUpdateTime(new Date());
                    repaySchedule.setPayOrderNo(yunRetainBill.getBgwOrderNo());
                    lnRepayScheduleMapper.insertSelective(repaySchedule);

                    LnRepayScheduleDetail lnRepayScheduleDetail = new LnRepayScheduleDetail();
                    lnRepayScheduleDetail.setUpdateTime(new Date());
                    lnRepayScheduleDetail.setCreateTime(new Date());
                    lnRepayScheduleDetail.setPlanId(repaySchedule.getId());
                    lnRepayScheduleDetail.setPlanAmount(yunRetainBill.getPrincipal() != null ? yunRetainBill.getPrincipal() : 0d);
                    lnRepayScheduleDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_PRINCIPAL.getCode());
                    lnRepayScheduleDetailMapper.insertSelective(lnRepayScheduleDetail);

                    lnRepayScheduleDetail.setPlanAmount(yunRetainBill.getInterest() != null ? yunRetainBill.getInterest() : 0d);
                    lnRepayScheduleDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_INTEREST.getCode());
                    lnRepayScheduleDetailMapper.insertSelective(lnRepayScheduleDetail);

                    lnRepayScheduleDetail.setPlanAmount(yunRetainBill.getPrincipalOverdue() != null ? yunRetainBill.getPrincipalOverdue() : 0d);
                    lnRepayScheduleDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_PRINCIPAL_OVERDUE.getCode());
                    lnRepayScheduleDetailMapper.insertSelective(lnRepayScheduleDetail);

                    lnRepayScheduleDetail.setPlanAmount(yunRetainBill.getInterestOverdue() != null ? yunRetainBill.getInterestOverdue() : 0d);
                    lnRepayScheduleDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_INTEREST_OVERDUE.getCode());
                    lnRepayScheduleDetailMapper.insertSelective(lnRepayScheduleDetail);

                    //调用“B还款结果处理”还款成功处理
                    logger.info(">>>还款处理轮询无记录--REPAIED条件调用“B还款结果处理”还款成功处理<<<");
                    NormalRepaySysSplitInfo result = new NormalRepaySysSplitInfo();
                    result.setLnPayOrdersId(order.getId());
                    result.setOrderNo(order.getOrderNo());
                    result.setLoanId(loanId);
                    result.setRepayAmount(yunRetainBill.getTotal());
                    result.setPartnerRepayId(yunRetainBill.getRepayId());
                    try{
                        logger.info(">>>还款处理轮询无记录--REPAIED条件调用还款结果处理开始<<<");
                        normalRepaySysSplit(result);
                        logger.info(">>>还款处理轮询无记录--REPAIED条件调用还款结果处理结束<<<");
                    }catch(Exception e){
                        logger.info(">>>还款处理轮询无记录--REPAIED条件调用还款结果处理错误<<<",e);
                        specialJnlService.warn4Fail(0d,"还款处理轮询无记录--REPAIED条件调用还款结果处理错误partnerRepayId=" + yunRetainBill.getRepayId(),
                                yunRetainBill.getRepayId(),"对应redis数据错误",false);
                    }
                    //置return_flag为finish
                    logger.info(">>>还款处理轮询无记录--REPAIED条件置return_flag为finish<<<");
                    LnRepaySchedule newRepaySchedule = new LnRepaySchedule();
                    newRepaySchedule.setId(repaySchedule.getId());
                    newRepaySchedule.setReturnFlag(Constants.LN_REPAY_RETURN_FLAG_FINISH);
                    newRepaySchedule.setPayOrderNo(yunRetainBill.getBgwOrderNo());
                    lnRepayScheduleMapper.updateByPrimaryKeySelective(newRepaySchedule);
                    logger.info(">>>还款处理轮询无记录--REPAIED条件END<<<");
                }else if(yunRetainBill.getStatus().equals(Constants.YUN_BILL_STATUS_INIT) ||
                        yunRetainBill.getStatus().equals(Constants.YUN_BILL_STATUS_REPAYING) ||
                        yunRetainBill.getStatus().equals(Constants.YUN_BILL_STATUS_LATE_NOT)){
                    logger.info(">>>还款处理轮询无记录--INIT/REPAYING/LATE_NOT条件START,插入一条INIT数据,置return_flag为finish<<<");
                    LnRepaySchedule repaySchedule = new LnRepaySchedule();
                    repaySchedule.setLoanId(loanId);
                    repaySchedule.setPartnerRepayId(yunRetainBill.getRepayId());
                    repaySchedule.setSerialId(yunRetainBill.getRepaySerial());
                    repaySchedule.setPlanDate(yunRetainBill.getRepayDate());
                    repaySchedule.setPlanTotal(yunRetainBill.getTotal());
                    repaySchedule.setStatus(Constants.LN_REPAY_STATUS_INIT);
                    repaySchedule.setReturnFlag(Constants.LN_REPAY_RETURN_FLAG_FINISH);
                    repaySchedule.setCreateTime(new Date());
                    repaySchedule.setUpdateTime(new Date());
                    repaySchedule.setPayOrderNo(yunRetainBill.getBgwOrderNo());
                    lnRepayScheduleMapper.insertSelective(repaySchedule);

                    LnRepayScheduleDetail lnRepayScheduleDetail = new LnRepayScheduleDetail();
                    lnRepayScheduleDetail.setUpdateTime(new Date());
                    lnRepayScheduleDetail.setCreateTime(new Date());
                    lnRepayScheduleDetail.setPlanId(repaySchedule.getId());
                    lnRepayScheduleDetail.setPlanAmount(yunRetainBill.getPrincipal() != null ? yunRetainBill.getPrincipal() : 0d);
                    lnRepayScheduleDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_PRINCIPAL.getCode());
                    lnRepayScheduleDetailMapper.insertSelective(lnRepayScheduleDetail);

                    lnRepayScheduleDetail.setPlanAmount(yunRetainBill.getInterest() != null ? yunRetainBill.getInterest() : 0d);
                    lnRepayScheduleDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_INTEREST.getCode());
                    lnRepayScheduleDetailMapper.insertSelective(lnRepayScheduleDetail);

                    lnRepayScheduleDetail.setPlanAmount(yunRetainBill.getPrincipalOverdue() != null ? yunRetainBill.getPrincipalOverdue() : 0d);
                    lnRepayScheduleDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_PRINCIPAL_OVERDUE.getCode());
                    lnRepayScheduleDetailMapper.insertSelective(lnRepayScheduleDetail);

                    lnRepayScheduleDetail.setPlanAmount(yunRetainBill.getInterestOverdue() != null ? yunRetainBill.getInterestOverdue() : 0d);
                    lnRepayScheduleDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_INTEREST_OVERDUE.getCode());
                    lnRepayScheduleDetailMapper.insertSelective(lnRepayScheduleDetail);
                    logger.info(">>>还款处理轮询无记录--INIT/REPAYING/LATE_NOT条件END<<<");
                }else if(yunRetainBill.getStatus().equals(Constants.YUN_BILL_STATUS_CANCELLED)){
                    logger.info(">>>还款处理轮询无记录--CANCELLED条件START,插入一条CANCELLED数据,置return_flag为finish<<<");
                    LnRepaySchedule repaySchedule = new LnRepaySchedule();
                    repaySchedule.setLoanId(loanId);
                    repaySchedule.setPartnerRepayId(yunRetainBill.getRepayId());
                    repaySchedule.setSerialId(yunRetainBill.getRepaySerial());
                    repaySchedule.setPlanDate(yunRetainBill.getRepayDate());
                    repaySchedule.setPlanTotal(yunRetainBill.getTotal());
                    repaySchedule.setStatus(Constants.LN_REPAY_CANCELLED);
                    repaySchedule.setReturnFlag(Constants.LN_REPAY_RETURN_FLAG_FINISH);
                    repaySchedule.setCreateTime(new Date());
                    repaySchedule.setUpdateTime(new Date());
                    repaySchedule.setPayOrderNo(yunRetainBill.getBgwOrderNo());
                    lnRepayScheduleMapper.insertSelective(repaySchedule);

                    LnRepayScheduleDetail lnRepayScheduleDetail = new LnRepayScheduleDetail();
                    lnRepayScheduleDetail.setUpdateTime(new Date());
                    lnRepayScheduleDetail.setCreateTime(new Date());
                    lnRepayScheduleDetail.setPlanId(repaySchedule.getId());
                    lnRepayScheduleDetail.setPlanAmount(yunRetainBill.getPrincipal() != null ? yunRetainBill.getPrincipal() : 0d);
                    lnRepayScheduleDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_PRINCIPAL.getCode());
                    lnRepayScheduleDetailMapper.insertSelective(lnRepayScheduleDetail);

                    lnRepayScheduleDetail.setPlanAmount(yunRetainBill.getInterest() != null ? yunRetainBill.getInterest() : 0d);
                    lnRepayScheduleDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_INTEREST.getCode());
                    lnRepayScheduleDetailMapper.insertSelective(lnRepayScheduleDetail);

                    lnRepayScheduleDetail.setPlanAmount(yunRetainBill.getPrincipalOverdue() != null ? yunRetainBill.getPrincipalOverdue() : 0d);
                    lnRepayScheduleDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_PRINCIPAL_OVERDUE.getCode());
                    lnRepayScheduleDetailMapper.insertSelective(lnRepayScheduleDetail);

                    lnRepayScheduleDetail.setPlanAmount(yunRetainBill.getInterestOverdue() != null ? yunRetainBill.getInterestOverdue() : 0d);
                    lnRepayScheduleDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_INTEREST_OVERDUE.getCode());
                    lnRepayScheduleDetailMapper.insertSelective(lnRepayScheduleDetail);
                    logger.info(">>>还款处理轮询无记录--CANCELLED条件END<<<");
                }
            }
        }

        //处理结束时，借款记录设为可还标识
        if(loanId != null){
            LnLoan lnLoanUpdate = new LnLoan();
            lnLoanUpdate.setId(loanId);
            lnLoanUpdate.setIsRepaying(Constants.DEP_REPAY_AVAILABLE);
            lnLoanUpdate.setUpdateTime(new Date());
            lnLoanMapper.updateByPrimaryKeySelective(lnLoanUpdate);
        }
    }

    @Override
    public void zsdBillSync(com.alibaba.fastjson.JSONObject json, LnPayOrders order) {
        // 赞时贷
        Integer loanId = json.getInteger("loanId");
        if(order == null || loanId == null){
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR,",redis中数据校验失败");
        }

        JSONArray repayBills = null;
        if(StringUtil.isNotEmpty(json.getString("repayBills"))){
            repayBills = json.getJSONArray("repayBills");
        }
        List<RepayBillVO> repayBillVOList = null;
        if(repayBills != null){
            repayBillVOList = (List<RepayBillVO>) JSONUtil.jsonString2BeanList(repayBills.toJSONString(), RepayBillVO.class);
        }

        if(CollectionUtils.isNotEmpty(repayBillVOList)){
            /**
             * INIT：调用“3.2.26还款结果处理”还款成功处理；
             * LATE_NOT(代偿完成时会改成此状态)：调用逾期还款分账；
             * REPAIED、LATE_REPAIED：做一笔重复还款处理
             */
            //redis账单不为空时对照redis中账单
            for(RepayBillVO repayBill :repayBillVOList){
                if(repayBill.getScheStatus().equals(Constants.LN_REPAY_STATUS_INIT)){
                    logger.info(">>>赞时贷还款处理轮询INIT条件START<<<");
                    NormalRepaySysSplitInfo result = new NormalRepaySysSplitInfo();
                    result.setLnPayOrdersId(order.getId());
                    result.setOrderNo(order.getOrderNo());
                    result.setLoanId(repayBill.getLoanId());
                    result.setRepayAmount(order.getAmount());
                    result.setPartnerRepayId(repayBill.getPartnerRepayId());
                    try{
                        logger.info(">>>赞时贷还款处理轮询INIT条件调用还款结果处理开始<<<");
                        normalRepaySysSplit(result);
                        logger.info(">>>赞时贷还款处理轮询INIT条件调用还款结果处理结束<<<");
                    }catch(Exception e){
                        logger.info(">>>还款处理轮询redis中数据处理错误<<<",e);
                        specialJnlService.warn4Fail(0d,"还款处理轮询时处理partnerRepayId=:" + repayBill.getPartnerRepayId() + "对应redis数据错误",
                                repayBill.getPartnerRepayId(), "对应redis数据错误",false);
                    }
                    //置return_flag为finish
                    logger.info(">>>赞时贷还款处理轮询INIT条件置return_flag为finish<<<");
                    LnRepaySchedule lnRepaySchedule = new LnRepaySchedule();
                    lnRepaySchedule.setId(repayBill.getRepayPlanId());
                    lnRepaySchedule.setReturnFlag(Constants.LN_REPAY_RETURN_FLAG_FINISH);
                    lnRepaySchedule.setPayOrderNo(order.getOrderNo());
                    lnRepayScheduleMapper.updateByPrimaryKeySelective(lnRepaySchedule);
                    logger.info(">>>赞时贷还款处理轮询INIT条件END<<<");
                }else if(repayBill.getScheStatus().equals(Constants.LN_REPAY_LATE_NOT)){
                    logger.info(">>>赞时贷还款处理轮询LATE_NOT条件START,调用逾期还款分账<<<");
                    LnLoan loan = lnLoanMapper.selectByPrimaryKey(repayBill.getLoanId());
                    if (loan == null) {
                        logger.info("=================【ZSD逾期还款】借贷借款数据未获取=====================");
                        throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
                    }
                    LnRepaySchedule lnRepaySchedule = lnRepayScheduleMapper.selectByPrimaryKey(repayBill.getRepayPlanId());
                    if (lnRepaySchedule == null) {
                        logger.info("=================【ZSD逾期还款】账单数据未获取=====================");
                        throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
                    }

                    //借贷关系列表数据校验
                    LnLoanRelationExample relationExample = new LnLoanRelationExample();
                    relationExample.createCriteria().andLoanIdEqualTo(repayBill.getLoanId());
                    List<LnLoanRelation> relationList = lnLoanRelationMapper.selectByExample(relationExample);
                    if (org.springframework.util.CollectionUtils.isEmpty(relationList)) {
                        logger.info("=================【ZSD逾期还款】借贷关系列表为空=====================");
                        throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
                    }
                    try{
                        logger.info(">>>赞时贷还款处理轮询LATE_NOT条件调用逾期还款分账开始<<<");
                        overdueRepaySplit4ZSD(loan,lnRepaySchedule,lnRepaySchedule.getPlanTotal(),relationList);
                        logger.info(">>>赞时贷还款处理轮询LATE_NOT条件调用逾期还款分账结束<<<");
                    }catch (Exception e){
                        logger.info(">>>还款处理轮询redis中数据处理错误<<<",e);
                        specialJnlService.warn4Fail(0d,"还款处理轮询时处理partnerRepayId=:" + repayBill.getPartnerRepayId() + "对应redis数据错误",
                                repayBill.getPartnerRepayId(), "对应redis数据错误",false);
                    }
                    //置return_flag为finish
                    logger.info(">>>赞时贷还款处理轮询INIT条件置return_flag为finish<<<");
                    LnRepaySchedule lnRepayScheduleUp = new LnRepaySchedule();
                    lnRepayScheduleUp.setId(repayBill.getRepayPlanId());
                    lnRepayScheduleUp.setReturnFlag(Constants.LN_REPAY_RETURN_FLAG_FINISH);
                    lnRepayScheduleUp.setPayOrderNo(order.getOrderNo());
                    lnRepayScheduleMapper.updateByPrimaryKeySelective(lnRepayScheduleUp);
                    logger.info(">>>赞时贷还款处理轮询LATE_NOT条件END<<<");
                }else if(repayBill.getScheStatus().equals(Constants.LN_REPAY_REPAIED)
                        || repayBill.getScheStatus().equals(Constants.LN_REPAY_LATE_REPAIED)){
                    //同一笔账单是否存在除当前订单号的还款信息,是则做一笔重复还款
                    logger.info(">>>赞时贷还款处理轮询REPAIED、LATE_REPAIED条件START,调用重复还款处理<<<");
                    LnRepeatRepayRecord repeatRepay = new LnRepeatRepayRecord();
                    repeatRepay.setPartnerCode(Constants.PROPERTY_SYMBOL_ZSD);
                    repeatRepay.setRepayAmount(order.getAmount());
                    repeatRepay.setRepayOrderNo(order.getOrderNo());
                    repeatRepay.setRepayPlanId(repayBill.getRepayPlanId());
                    repeatRepay.setRepayType(Constants.REPAY_TYPE_USER_REPAY);
                    repeatRepay.setReturnAmount(order.getAmount());
                    repeatRepay.setSettleDate(new Date()); //结算日期
                    repeatRepay.setStatus(Constants.REPEAT_REPAY_STATUS_INIT);
                    repeatRepay.setCreateTime(new Date());
                    repeatRepay.setUpdateTime(new Date());
                    try{
                        repeatRepayProcess(repeatRepay);
                    }catch(Exception e){
                        logger.info(">>>赞时贷还款处理轮询REPAIED、LATE_REPAIED条件调用还款结果处理错误<<<",e);
                        specialJnlService.warn4Fail(0d,"赞时贷还款处理轮询REPAIED、LATE_REPAIED条件调用还款结果处理错误partnerRepayId=" + repayBill.getPartnerRepayId(),
                                repayBill.getPartnerRepayId(), "对应redis数据错误",false);
                    }

                    //更新return_flag为finish
                    logger.info(">>>赞时贷还款处理轮询REPAIED、LATE_REPAIED条件置return_flag为finish<<<");
                    LnRepaySchedule lnRepaySchedule = new LnRepaySchedule();
                    lnRepaySchedule.setId(repayBill.getRepayPlanId());
                    lnRepaySchedule.setReturnFlag(Constants.LN_REPAY_RETURN_FLAG_FINISH);
                    lnRepaySchedule.setPayOrderNo(order.getOrderNo());
                    lnRepayScheduleMapper.updateByPrimaryKeySelective(lnRepaySchedule);
                    logger.info(">>>赞时贷还款处理轮询REPAIED、LATE_REPAIED条件END<<<");
                }else{
                    logger.info(">>>还款处理轮询有PENDING状态未处理，请检查" + JSON.toJSONString(repayBill) + "<<<");
                    specialJnlService.warn4Fail(0d,"还款处理轮询有PENDING状态未处理，请检查partnerRepayId=" + repayBill.getPartnerRepayId(),
                            repayBill.getPartnerRepayId(), "赞时贷还款账单有未处理，请检查",false);
                    continue;
                }
                break;
            }
        }

        //处理结束时，借款记录设为可还标识
        if(loanId != null){
            LnLoan lnLoanUpdate = new LnLoan();
            lnLoanUpdate.setId(loanId);
            lnLoanUpdate.setIsRepaying(Constants.DEP_REPAY_AVAILABLE);
            lnLoanUpdate.setUpdateTime(new Date());
            lnLoanMapper.updateByPrimaryKeySelective(lnLoanUpdate);
        }
    }

    @Override
    public void sevenDaiBillSync(com.alibaba.fastjson.JSONObject json, LnPayOrders order) {
        // 获取7贷账单
        Integer loanId = null;
        Integer lnPayOrderId = null;
        JSONArray repayBills = null;
        if(StringUtil.isNotEmpty(json.getString("repayBills"))){
            repayBills = json.getJSONArray("repayBills");
        }
        //重新放入redis中
        if(order == null) {
            logger.error("还款处理轮询未找到lnPayOrderId=" + lnPayOrderId + "的订单信息");
            jsClientDaoSupport.rpush("repayBill", json.toString());
            return;
        }
        List<RepayBillVO> repayBillVOList = null;
        if( repayBills != null ) {
            repayBillVOList = (List<RepayBillVO>) JSONUtil.jsonString2BeanList(repayBills.toJSONString(), RepayBillVO.class);
        }
        try {
            loanId = json.getInteger("loanId");
            lnPayOrderId = json.getInteger("lnPayOrderId");
        } catch(Exception e){
            logger.warn("7贷还款处理轮询定时获取7贷账单异常");
            jsClientDaoSupport.rpush("repayBill", json.toString());
            return;
        }

        /**
         * INIT：调用正常还款成功处理；
         * LATE_NOT(代偿完成时会改成此状态)、REPAIED、LATE_REPAIED：
         做一笔重复还款处理
         */
        if(CollectionUtils.isNotEmpty(repayBillVOList)) {
            //redis账单不为空时对照redis中账单
            for(RepayBillVO repayBill : repayBillVOList) {
                if(repayBill.getScheStatus().equals(Constants.LN_REPAY_STATUS_INIT)){
                    NormalRepaySysSplitInfo result = new NormalRepaySysSplitInfo();
                    result.setLnPayOrdersId(order.getId());
                    result.setOrderNo(order.getOrderNo());
                    result.setLoanId(repayBill.getLoanId());
                    result.setRepayAmount(order.getAmount());
                    result.setPartnerRepayId(repayBill.getPartnerRepayId());
                    result.setBillBizInfoId(repayBill.getBillId());
                    try {
                        logger.info(">>>还款处理轮询账单"+repayBill.getPartnerRepayId()+"INIT--REPAIED条件START；bill_biz_info_id = " + repayBill.getBillId() + "<<<");
                        //代扣还款分账
                        if(null != repayBill.getBillId() && repayBill.getBillId() > 0) {
                            normalRepaySysSplit4Seven(result);
                            addRedis2Sync(repayBill.getLoanId(),repayBill.getRepayPlanId());
                        } else {
                            logger.info(">>>还款处理轮询账单"+repayBill.getPartnerRepayId()+"7贷提前还款，利息账单状态更新为REPAIED<<<");
                            LnRepaySchedule lnRepaySchedule = new LnRepaySchedule();
                            lnRepaySchedule.setId(repayBill.getRepayPlanId());
                            lnRepaySchedule.setStatus(Constants.LN_REPAY_PAY_STATUS_REPAIED);
                            lnRepaySchedule.setUpdateTime(new Date());
                            lnRepayScheduleMapper.updateByPrimaryKeySelective(lnRepaySchedule);
                        }
                        logger.info(">>>还款处理轮询账单"+repayBill.getPartnerRepayId()+"INIT--REPAIED条件END<<<");
                    } catch(Exception e) {
                        logger.info(">>>还款处理轮询redis中数据处理错误<<<", e);
                        specialJnlService.warn4Fail(0d,"还款处理轮询时处理账单partnerRepayId=:" + repayBill.getPartnerRepayId() + "对应redis数据错误",
                                repayBill.getPartnerRepayId(), "对应redis数据错误",false);
                    }
                    bgwBillUpdate(repayBill, order, json, Constants.SYSCONFIG_UPDATEFLAG_UPDATE);
                } else if(repayBill.getScheStatus().equals(Constants.LN_REPAY_REPAIED)
                        || repayBill.getScheStatus().equals(Constants.LN_REPAY_LATE_REPAIED)
                        || repayBill.getScheStatus().equals(Constants.LN_REPAY_LATE_NOT)) {
                    // 同一笔账单是否存在除当前订单号的还款信息 , 是则做一笔重复还款
                    logger.info(">>>还款处理轮询REPAIED、LATE_REPAIED、LATE_NOT条件START,调用重复还款处理<<<");
                    Integer isRepeat = lnRepayMapper.isRepeatRepay( repayBill.getRepayPlanId() , order.getId() );
                    if( isRepeat > 0 ){
                        LnRepeatRepayRecord repeatRepay = new LnRepeatRepayRecord();
                        repeatRepay.setPartnerCode(Constants.PROPERTY_SYMBOL_7_DAI_SELF);
                        repeatRepay.setRepayAmount(order.getAmount());
                        repeatRepay.setRepayOrderNo(order.getOrderNo());
                        repeatRepay.setRepayPlanId(repayBill.getRepayPlanId());
                        repeatRepay.setRepayType(Constants.REPAY_TYPE_USER_REPAY);
                        repeatRepay.setReturnAmount(order.getAmount());
                        repeatRepay.setSettleDate(new Date()); //结算日期
                        repeatRepay.setStatus(Constants.REPEAT_REPAY_STATUS_INIT);
                        repeatRepay.setCreateTime(new Date());
                        repeatRepay.setUpdateTime(new Date());
                        try{
                            repeatRepayProcess(repeatRepay);
                        }catch(Exception e){
                            logger.info(">>>还款处理轮询REPAIED、LATE_REPAIED、LATE_NOT条件调用还款结果处理错误<<<",e);
                            specialJnlService.warn4Fail(0d,"还款处理轮询REPAIED、LATE_REPAIED、LATE_NOT条件调用还款结果处理错误partnerRepayId=" + repayBill.getPartnerRepayId(),
                                    repayBill.getPartnerRepayId(), "对应redis数据错误",false);
                        }
                    }
                    bgwBillUpdate(repayBill, order, json, Constants.SYSCONFIG_UPDATEFLAG_UPDATE);
                } else {
                    logger.info(">>>还款处理轮询有PENDING状态未处理，请检查" + JSON.toJSONString(repayBill) + "<<<");
                    specialJnlService.warn4Fail(0d, "还款处理轮询有PENDING状态未处理，请检查partnerRepayId=" + repayBill.getPartnerRepayId(),
                            repayBill.getPartnerRepayId(), "七贷还款账单有未处理，请检查",false);
                    continue;
                }
            }
        }
        //处理结束时，借款记录设为可还标识
        if(loanId != null){
            LnLoan lnLoanUpdate = new LnLoan();
            lnLoanUpdate.setId(loanId);
            lnLoanUpdate.setIsRepaying(Constants.DEP_REPAY_AVAILABLE);
            lnLoanUpdate.setUpdateTime(new Date());
            lnLoanMapper.updateByPrimaryKeySelective(lnLoanUpdate);
        }
    }

    /**
     * 七贷-随借随还-还款成功后入redis同步账单
     * @author bianyatian
     * @param loanId
     * @param repayPlanId
     */
    private void addRedis2Sync(Integer loanId, Integer repayPlanId) {
		//查询借款表
    	LnLoan lnLoan = lnLoanMapper.selectByPrimaryKey(loanId);
    	if(Constants.PARTNER_BUSINESS_FLAG_REPAY_ANY_TIME.equals(lnLoan.getPartnerBusinessFlag())){
    		//查询该笔账单还款本金是否为0，是则不入redis;
    		LnRepayScheduleDetailExample detailExample = new LnRepayScheduleDetailExample();
    		detailExample.createCriteria().andPlanIdEqualTo(repayPlanId).andSubjectCodeEqualTo(Constants.SUBJECT_PRINCIPAL);
    		List<LnRepayScheduleDetail> detailList = lnRepayScheduleDetailMapper.selectByExample(detailExample);
    		if(CollectionUtils.isEmpty(detailList) || detailList.get(0).getPlanAmount() == 0){
    			return;
    		}
    		try {
				//已还本金小于借款本金，说明未全部还款，入redis，同步账单
                JSONObject billObject = new JSONObject();
                billObject.put("partnerLoanId", lnLoan.getPartnerLoanId());
                billObject.put("userId", lnLoan.getLnUserId());
                billObject.put("loanId", loanId);
                billObject.put("loanAmount", lnLoan.getApproveAmount());
                billObject.put("time", DateUtil.format(new Date()));
                jsClientDaoSupport.rpush("sevenFreePayBill", billObject.toString());
                logger.info(">>>七贷-随借随还入队列(账单同步)数据:" + JSON.toJSONString(billObject) + "<<<");
			} catch (Exception e) {
				logger.info(">>>七贷-随借随还入队列(账单同步)异常，借款id:" + loanId + "<<<");
				e.printStackTrace();
			}
    	}
	}

	/**
     * @param repayBill 本地账单
     * @param order
     * @param jsonObject
     * @return int 账单表Id
     * */
    private void bgwBillUpdate(final RepayBillVO repayBill ,final LnPayOrders order,
    			final com.alibaba.fastjson.JSONObject jsonObject, final String operate_type) {

        logger.info("账单lnRepayScheduleId=["+repayBill.getRepayPlanId()+"]币港湾账单状态=["+repayBill.getScheStatus()+"]return_flag置为finish<<<");
//        transactionTemplate.execute(new TransactionCallbackWithoutResult(){
//			@Override
//			protected void doInTransactionWithoutResult(TransactionStatus status) {
//				if( Constants.SYSCONFIG_UPDATEFLAG_UPDATE.equals(operate_type) ) {

		        	logger.info("账单lnRepayScheduleId=["+repayBill.getRepayPlanId()+"]信息同步.....<<<");
		        	LnRepaySchedule lnRepaySchedule = new LnRepaySchedule();
		            lnRepaySchedule.setId(repayBill.getRepayPlanId());
		            lnRepaySchedule.setReturnFlag(Constants.LN_REPAY_RETURN_FLAG_FINISH);
                    lnRepaySchedule.setFinishTime(new Date());
		            lnRepaySchedule.setPayOrderNo(order.getOrderNo());
                    lnRepayScheduleMapper.updateByPrimaryKeySelective(lnRepaySchedule);

//		            // 更新账单detail-principal表的记录
//		            LnRepayScheduleDetailExample repaySchDetailPrincipalExample = new LnRepayScheduleDetailExample();
//		            repaySchDetailPrincipalExample.createCriteria().andSubjectCodeEqualTo(LoanSubjects.SUBJECT_CODE_PRINCIPAL.getCode())
//		            	.andPlanIdEqualTo(repayBill.getRepayPlanId());
//		            List<LnRepayScheduleDetail> lnRepaySchDetailPrincipal = lnRepayScheduleDetailMapper.selectByExample(repaySchDetailPrincipalExample);
//		            LnRepayScheduleDetail lnRepayScheduleDetail = new LnRepayScheduleDetail();
//		            lnRepayScheduleDetail.setId(lnRepaySchDetailPrincipal.get(0).getId());
//		            lnRepayScheduleDetail.setUpdateTime(new Date());
//		            lnRepayScheduleDetail.setPlanAmount(sevenBill.getPrincipal() != null ? sevenBill.getPrincipal() : 0d);
//		            lnRepayScheduleDetailMapper.updateByPrimaryKeySelective(lnRepayScheduleDetail);
//
//		            // 更新账单detail—interest表的记录
//		            LnRepayScheduleDetailExample repaySchDetailInterestExample = new LnRepayScheduleDetailExample();
//		            repaySchDetailInterestExample.createCriteria().andSubjectCodeEqualTo(LoanSubjects.SUBJECT_CODE_INTEREST.getCode())
//		            	.andPlanIdEqualTo(repayBill.getRepayPlanId());
//		            List<LnRepayScheduleDetail> lnRepaySchDetailInterest = lnRepayScheduleDetailMapper.selectByExample(repaySchDetailInterestExample);
//		            LnRepayScheduleDetail lnRepayScheduleDetailInterest = new LnRepayScheduleDetail();
//		            lnRepayScheduleDetailInterest.setId(lnRepaySchDetailInterest.get(0).getId());
//		            lnRepayScheduleDetailInterest.setUpdateTime(new Date());
//		            lnRepayScheduleDetailInterest.setPlanAmount(sevenBill.getInterest() != null ? sevenBill.getInterest() : 0d);
//		            lnRepayScheduleDetailMapper.updateByPrimaryKeySelective(lnRepayScheduleDetailInterest);
//
//		            // 更新账单detail—principal-overdue表的记录
//		            LnRepayScheduleDetailExample repaySchDetailPrincipalOverdueExample = new LnRepayScheduleDetailExample();
//		            repaySchDetailPrincipalOverdueExample.createCriteria().andSubjectCodeEqualTo(LoanSubjects.SUBJECT_CODE_PRINCIPAL_OVERDUE.getCode())
//		            	.andPlanIdEqualTo(repayBill.getRepayPlanId());
//		            List<LnRepayScheduleDetail> lnRepaySchDetailOverdue = lnRepayScheduleDetailMapper.selectByExample(repaySchDetailPrincipalOverdueExample);
//		            LnRepayScheduleDetail lnRepayScheduleDetailOverdue = new LnRepayScheduleDetail();
//		            lnRepayScheduleDetailOverdue.setId(lnRepaySchDetailOverdue.get(0).getId());
//		            lnRepayScheduleDetailOverdue.setUpdateTime(new Date());
//		            lnRepayScheduleDetailOverdue.setPlanAmount(sevenBill.getPrincipalOverdue() != null ? sevenBill.getPrincipalOverdue() : 0d);
//		            lnRepayScheduleDetailMapper.updateByPrimaryKeySelective(lnRepayScheduleDetailOverdue);
//
//		            //更新账单detail—interest-overdue表的记录
//		            LnRepayScheduleDetailExample repaySchDetailInterestOverdueExample = new LnRepayScheduleDetailExample();
//		            repaySchDetailInterestOverdueExample.createCriteria().andSubjectCodeEqualTo(LoanSubjects.SUBJECT_CODE_INTEREST_OVERDUE.getCode())
//		            	.andPlanIdEqualTo(repayBill.getRepayPlanId());
//		            List<LnRepayScheduleDetail> lnRepaySchDetailInterestOverdue = lnRepayScheduleDetailMapper.selectByExample(repaySchDetailInterestOverdueExample);
//		            LnRepayScheduleDetail lnRepayScheduleDetailInterestOverdue = new LnRepayScheduleDetail();
//		            lnRepayScheduleDetailInterestOverdue.setId(lnRepaySchDetailInterestOverdue.get(0).getId());
//		            lnRepayScheduleDetailInterestOverdue.setUpdateTime(new Date());
//		            lnRepayScheduleDetailInterestOverdue.setPlanAmount(sevenBill.getInterestOverdue() != null ? sevenBill.getInterestOverdue() : 0d);
//		            lnRepayScheduleDetailMapper.updateByPrimaryKeySelective(lnRepayScheduleDetailInterestOverdue);
//		        } else if( Constants.SYSCONFIG_UPDATEFLAG_CREATE.equals(operate_type) ) {
//		            LnRepaySchedule repaySchedule = new LnRepaySchedule();
//		            repaySchedule.setLoanId(jsonObject.getInteger("loanId"));
//		            repaySchedule.setPartnerRepayId(sevenBill.getRepayId());
//		            repaySchedule.setSerialId(sevenBill.getRepaySerial());
//		            repaySchedule.setPlanDate(sevenBill.getRepayDate());
//		            repaySchedule.setPlanTotal(sevenBill.getTotal());
//
//		            if( sevenBill.getStatus().equals(Constants.YUN_BILL_STATUS_REPAIED) ) {
//		                repaySchedule.setStatus(Constants.LN_REPAY_REPAIED);
//		            } else if( sevenBill.getStatus().equals(Constants.YUN_BILL_STATUS_INIT) ) {
//		                repaySchedule.setStatus(Constants.LN_REPAY_STATUS_INIT);
//		            } else if( sevenBill.getStatus().equals(Constants.YUN_BILL_STATUS_LATE_NOT) ) {
//		            	repaySchedule.setStatus(Constants.LN_REPAY_LATE_NOT);
//		            }
//
//		            repaySchedule.setReturnFlag(Constants.LN_REPAY_RETURN_FLAG_FINISH);
//
//		            repaySchedule.setCreateTime(new Date());
//		            repaySchedule.setUpdateTime(new Date());
//		            repaySchedule.setPayOrderNo(sevenBill.getBgwOrderNo());
//		            lnRepayScheduleMapper.insertSelective(repaySchedule);
//
//		            LnRepayScheduleDetail lnRepayScheduleDetail = new LnRepayScheduleDetail();
//		            lnRepayScheduleDetail.setUpdateTime(new Date());
//		            lnRepayScheduleDetail.setCreateTime(new Date());
//		            lnRepayScheduleDetail.setPlanId(repaySchedule.getId());
//		            lnRepayScheduleDetail.setPlanAmount(sevenBill.getPrincipal() != null ? sevenBill.getPrincipal() : 0d);
//		            lnRepayScheduleDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_PRINCIPAL.getCode());
//		            lnRepayScheduleDetailMapper.insertSelective(lnRepayScheduleDetail);
//
//		            lnRepayScheduleDetail.setPlanAmount(sevenBill.getInterest() != null ? sevenBill.getInterest() : 0d);
//		            lnRepayScheduleDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_INTEREST.getCode());
//		            lnRepayScheduleDetailMapper.insertSelective(lnRepayScheduleDetail);
//
//		            lnRepayScheduleDetail.setPlanAmount(sevenBill.getPrincipalOverdue() != null ? sevenBill.getPrincipalOverdue() : 0d);
//		            lnRepayScheduleDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_PRINCIPAL_OVERDUE.getCode());
//		            lnRepayScheduleDetailMapper.insertSelective(lnRepayScheduleDetail);
//
//		            lnRepayScheduleDetail.setPlanAmount(sevenBill.getInterestOverdue() != null ? sevenBill.getInterestOverdue() : 0d);
//		            lnRepayScheduleDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_INTEREST_OVERDUE.getCode());
//		            lnRepayScheduleDetailMapper.insertSelective(lnRepayScheduleDetail);
//		            logger.info("账单lnRepayScheduleId=["+repaySchedule.getId()+"]更新END.....<<<");
//		        }
//			}
//        });
    }

    /**
     * 记录还款明细表信息（根据产品需求，记录所有请求过来的还款信息）
     * @param repayId
     * @param principal
     * @param interestDoneAmount
     * @param lateFeeDoneAmount
     * @param serviceFeeDoneAmount
     * @param pServiceFeeDoneAmount
     * @param infoCertifiedFeeDoneAmount
     * @param riskManageServiceFeeDoneAmount
     * @param collectionChannelFeeDoneAmount
     * @param otherDoneAmount
     */
    private void saveRepayDetail(Integer repayId, Double principal, Double interestDoneAmount, Double lateFeeDoneAmount,
                                 Double serviceFeeDoneAmount, Double pServiceFeeDoneAmount, Double infoCertifiedFeeDoneAmount,
                                 Double riskManageServiceFeeDoneAmount, Double collectionChannelFeeDoneAmount, Double otherDoneAmount) {

        LnRepayDetail lnRepayDetail = new LnRepayDetail();
        lnRepayDetail.setUpdateTime(new Date());
        lnRepayDetail.setCreateTime(new Date());
        lnRepayDetail.setRepayId(repayId);
        // 本金
        lnRepayDetail.setDoneAmount(principal != null ? MoneyUtil.divide(principal, 100).doubleValue() : 0d);
        lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_PRINCIPAL.getCode());
        lnRepayDetailMapper.insertSelective(lnRepayDetail);
        // 利息
        lnRepayDetail.setDoneAmount(interestDoneAmount);
        lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_INTEREST.getCode());
        lnRepayDetailMapper.insertSelective(lnRepayDetail);
        // 滞纳金
        lnRepayDetail.setDoneAmount(lateFeeDoneAmount);
        lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_LATE_FEE.getCode());
        lnRepayDetailMapper.insertSelective(lnRepayDetail);
        // 手续费
        lnRepayDetail.setDoneAmount(serviceFeeDoneAmount);
        lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_SERVICE_FEE.getCode());
        lnRepayDetailMapper.insertSelective(lnRepayDetail);
        // 平台服务费
        lnRepayDetail.setDoneAmount(pServiceFeeDoneAmount);
        lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_PLATFORM_SERVICE_FEE.getCode());
        lnRepayDetailMapper.insertSelective(lnRepayDetail);
        // 信息认证费
        lnRepayDetail.setDoneAmount(infoCertifiedFeeDoneAmount);
        lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_INFO_CERTIFIED_FEE.getCode());
        lnRepayDetailMapper.insertSelective(lnRepayDetail);
        // 风控服务费
        lnRepayDetail.setDoneAmount(riskManageServiceFeeDoneAmount);
        lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_RISK_MANAGE_SERVICE_FEE.getCode());
        lnRepayDetailMapper.insertSelective(lnRepayDetail);
        // 代收通道费
        lnRepayDetail.setDoneAmount(collectionChannelFeeDoneAmount);
        lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_COLLECTION_CHANNEL_FEE.getCode());
        lnRepayDetailMapper.insertSelective(lnRepayDetail);
        // 其他费用
        lnRepayDetail.setDoneAmount(otherDoneAmount);
        lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_OTHER.getCode());
        lnRepayDetailMapper.insertSelective(lnRepayDetail);
    }

	@Override
	public void compRepaySysSplit4Seven(final CompensateRepaySysSplitInfo info) {
		 /**
		  * 七贷代偿分账
         * 前置校验：借款表是否存在；借款表存在，查询借款用户，确定合作方；校验是否存在初始状态的代偿明细；
         * 校验还款计划表中是否存在；计划表还款本金校验
         * 加锁、事务
         * 分账计算系统还款户金额（THD_REPAY）、系统营收户金额（THD_BGW_REVENUE_YUN）
         * 修改代偿明细
         * ln_repay_schedule更新还款标识为处理完成
         * 新增存管还款计划表（ln_deposition_repay_schedule）及明细
         * 调用系统记账
         */
		//必传入参校验
        if(info.getLoanId() == null || StringUtils.isBlank(info.getPartnerLoanId())
                || StringUtils.isBlank(info.getPartnerRepayId())
                || info.getRepayAmount() == null
                || info.getLnCompensateDetailId() == null ){
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }

        //前置校验：1、借款表是否存在
        final LnLoan lnLoan = lnLoanMapper.selectByPrimaryKey(info.getLoanId());
        if(lnLoan == null){
            logger.info("===========【代偿还款系统分账】：校验，借款表数据未获得================");
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }
        //借款表存在，查询借款用户，确定合作方
        final LnUser lnUser = lnUserMapper.selectByPrimaryKey(lnLoan.getLnUserId());
        PartnerEnum partnerEnum = PartnerEnum.getEnumByCode(lnUser.getPartnerCode());
        if(PartnerEnum.SEVEN_DAI.getCode().equals(lnUser.getPartnerCode())){
        	 logger.info("===========【代偿还款系统分账】：合作方校验失败================");
             throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }
        final String partnerName = partnerEnum.getName();

        //前置校验：2、校验是否存在初始状态的代偿明细
        LnCompensateDetailExample example = new LnCompensateDetailExample();
        example.createCriteria().andIdEqualTo(info.getLnCompensateDetailId())
                .andStatusEqualTo(Constants.COMPENSATE_REPAYS_STATUS_INIT);
        List<LnCompensateDetail> list = lnCompensateDetailMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(list)){
            logger.info("===========【"+partnerName+"代偿还款系统分账】：代偿明细数据未获得================");
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }
        final LnCompensateDetail lnCompensateDetail = list.get(0);
        //获得实际代偿的利息本金
        final Double repayPrincipal = lnCompensateDetail.getPrincipal();
        final String compensateTypeString = repayPrincipal>0 ? "本金": "利息";


        //查询ln_repay_schedule
        LnRepayScheduleExample repayScheduleExample = new LnRepayScheduleExample();
        repayScheduleExample.createCriteria().andPartnerRepayIdEqualTo(info.getPartnerRepayId())
                .andLoanIdEqualTo(info.getLoanId()).andStatusNotEqualTo(Constants.LN_REPAY_CANCELLED);
        List<LnRepaySchedule> repaySchedulList = lnRepayScheduleMapper.selectByExample(repayScheduleExample);
        //前置校验：3、还款计划表中是否存在
        if(CollectionUtils.isEmpty(repaySchedulList)){
            logger.info("===========【"+partnerName+compensateTypeString+"代偿还款系统分账】：还款计划表数据未获得================");
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }
        final LnRepaySchedule lnRepaySchedule = repaySchedulList.get(0);

        //计划表还款本金校验
        final Double repayPrincipalSchedul = getRepayDetailAmount(lnRepaySchedule.getId(), LoanSubjects.SUBJECT_CODE_PRINCIPAL);
        if(repayPrincipalSchedul.compareTo(repayPrincipal) != 0){
            logger.info("===========【"+partnerName+compensateTypeString+"代偿还款系统分账】：代偿本金："+repayPrincipal+"，计划表应还本金："+repayPrincipalSchedul+"================");
            //告警表添加数据
            specialJnlService.warn4FailNoSMS(MoneyUtil.subtract(repayPrincipalSchedul,repayPrincipal).doubleValue(),
                    "【"+partnerName+compensateTypeString+"代偿还款系统分账】：代偿本金："+repayPrincipal+"，计划表应还本金："+repayPrincipalSchedul,
                    "代偿明细记录编号："+info.getLnCompensateDetailId().toString(),
                    "【"+partnerName+compensateTypeString+"代偿还款系统分账】");

            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }
        try {
        	if(repayPrincipal > 0){
                jsClientDaoSupport.unlock(RedisLockEnum.LOCK_DEP_LOAN_RELATION.getKey());
            }
            //查询有债权关系的债权数据
            final List<LoanRelation4TransferVO> relationList = lnLoanRelationMapper.getSevenRelationList(null,lnRepaySchedule.getLoanId());
            if(CollectionUtils.isEmpty(relationList)){
                logger.info("===========还款结果处理：无对应的债权================");
                throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
            }
            try {
                transactionTemplate.execute(new TransactionCallbackWithoutResult(){
                    @Override
                    protected void doInTransactionWithoutResult(TransactionStatus status) {
                    	logger.info("===========【"+partnerName+","+compensateTypeString+"代偿还款系统分账】开始：代偿本金："+repayPrincipal+"，代偿利息："+lnCompensateDetail.getInterest()+"================");
                        //查询上次还款时间
                    	LnBillBizInfo billBizInfo = billBizInfoMapper.selectLastByLoanId(lnRepaySchedule.getLoanId());
                        //借款计息天数
                        Integer inDays = 0;
                        Date lastSettleTime = null;
                        if(billBizInfo == null || billBizInfo.getRepayTime() == null){
                        	//未发生过还款，则计息天数为借款日到计划还款日，前后都计息
                            inDays = DateUtil.getDiffeDay(lnRepaySchedule.getPlanDate(), DateUtil.parseDate(DateUtil.formatYYYYMMDD(lnLoan.getLoanTime())))+1;
                            lastSettleTime = lnLoan.getLoanTime();
                        }else{
                        	//已发生过还款，则计息天数从上次还款日到计划还款日，前不计息(上次还款日在上次还款已计算日期)，后计息
                            inDays = DateUtil.getDiffeDay(lnRepaySchedule.getPlanDate(), DateUtil.parseDate(DateUtil.formatYYYYMMDD(billBizInfo.getRepayTime())));
                            lastSettleTime = DateUtil.addDays(billBizInfo.getRepayTime(), 1);
                        }
                        inDays = inDays < 0 ? 0 : inDays;
                        //补息的利率(七贷自主和币港湾的结算利率)---22%
                        Double initRate = null;
                        initRate = sysConfigService.findRatePercentByKey(Constants.SEVEN_DAI_SELF_SYS_SETTLE_RATE);
                        Double rate = initRate == null ? 22 : initRate;

                        //获得实际代偿的利息
                        Double C_amount = MoneyUtil.subtract(lnCompensateDetail.getTotal(),lnCompensateDetail.getPrincipal()).doubleValue();

                        //计息本金
                        Double P_amount = lnLoanRelationMapper.getRelationAmountNotRepayingByLoanId(lnLoan.getId());

                        //获得应获币港湾22%总利息F_amount=P_amount计息本金*计天数息(inDays)*22%/365
                        Double F_amount = depFixedLoanRelationshipService.calInterest(inDays, rate, P_amount, 2);
                        logger.info("=============【"+partnerName+","+compensateTypeString+"代偿还款系统分账】应还币港湾"+rate+"%总利息F="+F_amount+"===============");

                        //七贷营收 = 代偿利息- 币港湾22%总利息
                        Double K_amount = MoneyUtil.subtract(C_amount, F_amount).doubleValue();
                        /*if(K_amount < 0){
                            //代偿利息< 22%总利息，记补息
                            LnAccountFillDetail lnAccountFillDetail = new LnAccountFillDetail();
                            lnAccountFillDetail.setPartnerCode(PartnerEnum.SEVEN_DAI_SELF.getCode());
                            lnAccountFillDetail.setFillType(Constants.FILL_DETAIL_FILL_TYPE_INTEREST);
                            lnAccountFillDetail.setFillDate(new Date());
                            lnAccountFillDetail.setAmount(MoneyUtil.subtract(F_amount, C_amount).doubleValue());
                            lnAccountFillDetail.setRelativeNo(lnRepaySchedule.getId().toString());
                            lnAccountFillDetail.setStatus(Constants.FILL_DETAIL_FILL_STATUS_SUCCESS);
                            lnAccountFillDetail.setCreateTime(new Date());
                            lnAccountFillDetail.setUpdateTime(new Date());
                            lnAccountFillDetailMapper.insertSelective(lnAccountFillDetail);

                        }*/

                        if(K_amount.compareTo(0d) != 0){
                            //bs_revenue_trans_detail记录合作方还款营收收入
                    		BsRevenueTransDetail bsRevenueTransDetail = new BsRevenueTransDetail();
                    		bsRevenueTransDetail.setPartnerCode(PartnerEnum.SEVEN_DAI_SELF.getCode());
                    		bsRevenueTransDetail.setTransType(Constants.REVENUE_TYPE_REPAY_INCOME);
                    		bsRevenueTransDetail.setLoanId(lnLoan.getId());
                    		bsRevenueTransDetail.setRepaySerial(lnRepaySchedule.getSerialId());
                    		bsRevenueTransDetail.setRepayScheduleId(lnRepaySchedule.getId());
                    		bsRevenueTransDetail.setRepayAmount(lnCompensateDetail.getTotal());
                    		bsRevenueTransDetail.setRevenueAmount(K_amount);
                    		bsRevenueTransDetail.setRepayScheduleId(lnRepaySchedule.getId());
                    		bsRevenueTransDetail.setCreateTime(new Date());
                    		bsRevenueTransDetail.setUpdateTime(new Date());
                    		bsRevenueTransDetailMapper.insertSelective(bsRevenueTransDetail);
                    	}
                        Double agreementRate = lnLoan.getAgreementRate();

                        //根据债权列表，计算每笔债权的（13%*计息本金*计息总天数/365），记录ln_finance_repay_schedule表，并求和，返回
                        FinanceRepayCalVO vo = new FinanceRepayCalVO();
                        //获取最大的SerialId
                        Integer maxSerialId = lnDepositionRepayScheduleMapper.selectMaxSerialIdByLoanId(lnLoan.getId());
                        if(repayPrincipal > 0){
                            //本金代偿
                            vo = do4FinanceRepaySeven(relationList, repayPrincipal, lnRepaySchedule, agreementRate, inDays, true, maxSerialId, lnRepaySchedule.getPlanDate(),lastSettleTime);
                        }else{
                            vo = do4FinanceRepaySeven(relationList, 0d, lnRepaySchedule, agreementRate, inDays, true, maxSerialId, lnRepaySchedule.getPlanDate(),lastSettleTime);
                        }
                        //记账单业务处理信息表ln_bill_biz_info
                        LnBillBizInfo billBizInfoTemp = new LnBillBizInfo();
                        billBizInfoTemp.setLastSettleTime(lastSettleTime);
                        billBizInfoTemp.setInterestDays(inDays);
                        billBizInfoTemp.setCreateTime(new Date());
                        billBizInfoTemp.setLoanId(lnLoan.getId());
                        billBizInfoTemp.setRepayScheduleId(lnRepaySchedule.getId());
                        billBizInfoTemp.setRepayTime(lnRepaySchedule.getPlanDate());
                        billBizInfoTemp.setRepayType(Constants.LN_REPAY_LATE_NOT);
                        billBizInfoTemp.setUpdateTime(new Date());
                        billBizInfoMapper.insertSelective(billBizInfoTemp);

                        Double agreement_amount = vo.getAgreementSumAmount();

                        //币港湾应收借款人服务费 P_amount计息本金*计天数息(inDays)*借款服务费利率/365
						Double loanServiceAmount = lnLoan.getLoanServiceRate() == null 
								? 0d :depFixedLoanRelationshipService.calInterest(inDays, lnLoan.getLoanServiceRate(), P_amount, 2);
                        //系统营收户金额（THD_BGW_REVENUE_7）= 币港湾22%总利息 - 13%协议利率的利息-2%借款服务费
                        Double bgw_revenue_amount = MoneyUtil.subtract(MoneyUtil.subtract(F_amount,agreement_amount).doubleValue(),loanServiceAmount).doubleValue();

                        //系统还款户金额(THD_REPAY) = P_amount计息本金*计天数息(inDays)*13%协议利率/365 + 本金+2%借款服务费
						Double thd_repay_amount = MoneyUtil.add(MoneyUtil.add(agreement_amount, repayPrincipal).doubleValue(),loanServiceAmount).doubleValue();
                        //修改代偿明细
                        LnCompensateDetail detailTemp = new LnCompensateDetail();
                        detailTemp.setId(lnCompensateDetail.getId());
                        detailTemp.setStatus(Constants.COMPENSATE_REPAYS_STATUS_SUCC);
                        detailTemp.setUpdateTime(new Date());
                        lnCompensateDetailMapper.updateByPrimaryKeySelective(detailTemp);

                        //ln_repay_schedule更新
                        LnRepaySchedule lnRepayScheduleTemp = new LnRepaySchedule();
                        lnRepayScheduleTemp.setId(lnRepaySchedule.getId());
                        lnRepayScheduleTemp.setFinishTime(new Date());
                        lnRepayScheduleTemp.setUpdateTime(new Date());
                        lnRepayScheduleTemp.setStatus(Constants.LN_REPAY_LATE_NOT);
                        lnRepayScheduleMapper.updateByPrimaryKeySelective(lnRepayScheduleTemp);

                        if(thd_repay_amount > 0d){
                        	//存管还款计划表
                            LnDepositionRepaySchedule schedul = new LnDepositionRepaySchedule();
                            schedul.setLnUserId(lnLoan.getLnUserId());
                            schedul.setLoanId(lnLoan.getId());
                            schedul.setPartnerRepayId(lnRepaySchedule.getPartnerRepayId());
                            schedul.setSerialId(maxSerialId+1);
                            schedul.setPlanDate(lnRepaySchedule.getPlanDate());
                            schedul.setPlanTotal(thd_repay_amount);
                            schedul.setStatus(Constants.LN_REPAY_STATUS_INIT);
                            schedul.setReturnFlag(Constants.DEP_REPAY_RETURN_FLAG_DF_PENDING);
                            if(repayPrincipal > 0){
                            	//本金代偿
                            	schedul.setRepayType(Constants.LN_DEP_REPAY_SCHEDULE_REPAY_TYPE_LATE_NOT);
                            }else{
                            	schedul.setRepayType(Constants.LN_DEP_REPAY_SCHEDULE_REPAY_TYPE_NORMAL_REPAY);
                            }

                            schedul.setCreateTime(new Date());
                            schedul.setUpdateTime(new Date());
                            lnDepositionRepayScheduleMapper.insertSelective(schedul);

                            //存管还款计划表明细
                            LnDepositionRepayScheduleDetail detailInterest = new LnDepositionRepayScheduleDetail();
                            detailInterest.setPlanId(schedul.getId());
                            detailInterest.setPlanAmount(agreement_amount);
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
                            if(loanServiceAmount != null && loanServiceAmount > 0){
                            	//存管还款计划表明细-借款服务费
                				LnDepositionRepayScheduleDetail detailLoanService = new LnDepositionRepayScheduleDetail();
                				detailLoanService.setPlanId(schedul.getId());
                				detailLoanService.setPlanAmount(loanServiceAmount);
                				detailLoanService.setSubjectCode(LoanSubjects.SUBJECT_CODE_LOAN_SERVICE_FEE.getCode());
                				detailLoanService.setCreateTime(new Date());
                				detailLoanService.setUpdateTime(new Date());
                				lnDepositionRepayScheduleDetailMapper.insertSelective(detailLoanService);
                            }
                            if(repayPrincipal > 0){
                                //本金代偿，记录ln_compensate_relation
                                List<LnCompensateRelation> list = vo.getList();
                                if(CollectionUtils.isNotEmpty(list)){
                                    for (LnCompensateRelation compensateRelation : list) {
                                        compensateRelation.setDepPlanId(schedul.getId());
                                        compensateRelation.setInterestDay(inDays);
                                        lnCompensateRelationMapper.insertSelective(compensateRelation);
                                    }
                                }
                            }
                        }
                        
                        

                        //系统记账
                        FixedRepaySysSplitInfo repaySysSplitInfo = new FixedRepaySysSplitInfo();
                        repaySysSplitInfo.setThdRepayAmount(thd_repay_amount);
                        repaySysSplitInfo.setThdRevenueAmount(K_amount);
                        repaySysSplitInfo.setThdBGWRevenueAmount(bgw_revenue_amount);
                        repaySysSplitInfo.setPartner(PartnerEnum.getEnumByCode(lnUser.getPartnerCode()));
                        repaySysSplitInfo.setThdMarginAmount(0d);
                        repaySysSplitInfo.setFee(0d);
                        repaySysSplitInfo.setLnRepayScheduleId(lnRepaySchedule.getId());
                        depFixedRepayAccountService.repaySysSplit(repaySysSplitInfo);

                    }
                });
            }catch (Exception e) {
                e.printStackTrace();
            }
        } finally {
            if(repayPrincipal > 0){
                jsClientDaoSupport.unlock(RedisLockEnum.LOCK_DEP_LOAN_RELATION.getKey());
            }
        }

	}


	/**
     * 根据债权列表，计算每笔债权的（13%*计息本金*计息总天数/365），记录ln_finance_repay_schedule表，并求和，返回
     * @param relationList 债权关系列表
     * @param repayPrincipal 借款人还款本金
     * @param lnRepaySchedule 还款计划
     * @param agreementRate 借款协议利率
     * @param inDays 借款人计息天数
     * @param compensateFlag 代偿标识
     * @param maxSerial 已还的最大期数
     * @param lastDay 此次结息日
	 * @param lastSettleTime 上次还款结息日+1(起息日)
     * @return
     */
    protected FinanceRepayCalVO do4FinanceRepaySeven(
            List<LoanRelation4TransferVO> relationList, Double repayPrincipal,
            LnRepaySchedule lnRepaySchedule, Double agreementRate,
            Integer inDays, boolean compensateFlag, Integer maxSerial, Date lastDay, Date lastSettleTime) {
    	FinanceRepayCalVO financeRepayCalVO = new FinanceRepayCalVO();
    	//本金代偿需生成代偿关系数据
    	List<LnCompensateRelation> compensateList = new ArrayList<LnCompensateRelation>();
    	//借款协议利息总和
        Double D_amount = 0d;
        if(repayPrincipal > 0){
            /**
             * 有本金还款：
             * 1、比较匹配关系的剩余金额和本金部分大小，确定某笔债权的还款金额
             * 2、生成ln_finance_repay_schedule记录，还款利息为0
             * 3、记账：判断还款类型，准备记账数据，调用“B非代偿还款记账”或“B代偿还款记账”
             */
        	
        	LnLoan lnLoan = lnLoanMapper.selectByPrimaryKey(lnRepaySchedule.getLoanId());
        	
            Double repayPrincipalTemp = repayPrincipal; //还款金额初始化
            for (LoanRelation4TransferVO record : relationList) {
            	if(record.getLeftAmount() == 0d){
            		continue;
            	}
                Double thisRepayAmount = 0d;//本金
                //确定还款到该笔债权的本金
                if(repayPrincipalTemp < record.getLeftAmount()){
                    thisRepayAmount = repayPrincipalTemp;
                }else{
                    thisRepayAmount = record.getLeftAmount();
                }
                //初始化
                LnFinanceRepaySchedule scheduleTemp = new LnFinanceRepaySchedule();
                scheduleTemp.setPlanInterest(0d);
                scheduleTemp.setPlanTotal(0d);
                scheduleTemp.setPlanTransInterest(0d);
                scheduleTemp.setPlanFee(0d);
                if(compensateFlag){
                	logger.info("=============本金代偿还款========================");
                }else{
                	logger.info("=============本金正常/提前还款========================");
                }
            	
                //协议利率利息=计息本金*计天数息(inDays)*13%/365
                Double agreementAmount = depFixedLoanRelationshipService.calInterest(inDays, agreementRate, record.getLeftAmount(), 2);
                D_amount = MoneyUtil.add(agreementAmount, D_amount).doubleValue();
            	//判断是否是本金代偿,是则计算理财人还款计划表的利息和手续费，且则生成ln_compensate_relation相关信息并返回
            	scheduleTemp = depFixedLoanRelationshipService.getFinanceRepaySchedule4SevenRepay(record, inDays, lastDay, agreementAmount, lastSettleTime);

               
                if(compensateFlag){
                	 logger.info("=============本金代偿,是则生成ln_compensate_relation========================");
                     LnCompensateRelation lnCompensateRelation = new LnCompensateRelation();
//                     BsSysConfig config = sysConfigService.findConfigByKey(Constants.SEVEN_DAI_COMPENSATE_USER_ID);
//                     Integer compUserId = config == null ? 0 : Integer.valueOf(config.getConfValue());
//                     BsHfbankUserExtExample extExample = new BsHfbankUserExtExample();
//                     extExample.createCriteria().andUserIdEqualTo(compUserId);
//                     List<BsHfbankUserExt> ext = bsHfbankUserExtMapper.selectByExample(extExample);
//                     if (CollectionUtils.isEmpty(ext)) {
//                         throw new PTMessageException(PTMessageEnum.HF_NOT_BIND);
//                     }
                     
                     BsUserCompensateVO vo = this.compensaterInfo(lnLoan.getLoanTime(), Constants.PROPERTY_SYMBOL_7_DAI_SELF);
                     if (vo==null ) {
                         throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND, Constants.PROPERTY_SYMBOL_YUN_DAI_SELF+"本金代偿代偿人信息未找到");
                     }
                     
                     lnCompensateRelation.setLoanRelationId(record.getId());
                     lnCompensateRelation.setCompUserId(vo.getId());
                     lnCompensateRelation.setPartnerCode(Constants.PROPERTY_SYMBOL_7_DAI_SELF);
                     lnCompensateRelation.setCompHfUserId(vo.getHfUserId());
                     lnCompensateRelation.setAmount(MoneyUtil.add(thisRepayAmount, agreementAmount).doubleValue());
                     lnCompensateRelation.setPrincipal(thisRepayAmount);
                     lnCompensateRelation.setInterest(agreementAmount);
                     lnCompensateRelation.setCreateTime(new Date());
                     lnCompensateRelation.setUpdateTime(new Date());
                     compensateList.add(lnCompensateRelation);
                }
                

                scheduleTemp.setPlanPrincipal(thisRepayAmount);
                scheduleTemp.setRepaySerial(maxSerial+1);
                //当天时间
                Date today = DateUtil.parseDate(DateUtil.formatYYYYMMDD(new Date()));
                scheduleTemp.setCreateTime(new Date());
                scheduleTemp.setPlanDate(today);
                scheduleTemp.setRelationId(record.getId());
                scheduleTemp.setStatus(Constants.FINANCE_REPAY_SATAE_REPAYING);
                scheduleTemp.setUpdateTime(new Date());
                Double planTotal = MoneyUtil.add(scheduleTemp.getPlanPrincipal(),
                			MoneyUtil.add(scheduleTemp.getPlanTransInterest(),MoneyUtil.add(scheduleTemp.getPlanInterest(),scheduleTemp.getPlanFee()).doubleValue()).doubleValue()
                		).doubleValue();
                scheduleTemp.setPlanTotal(planTotal);
                lnFinanceRepayScheduleMapper.insertSelective(scheduleTemp);
                repayPrincipalTemp = MoneyUtil.subtract(repayPrincipalTemp, thisRepayAmount).doubleValue();
                /*if(MoneyUtil.subtract(repayPrincipalTemp, 0).doubleValue() <= 0){
                    break;
                }*/
            }
        }else{
        	//纯利息的回款
            for (LoanRelation4TransferVO record : relationList) {
                //协议利率利息=计息本金*计天数息(inDays)*13%/365
                Double agreementAmount = depFixedLoanRelationshipService.calInterest(inDays, agreementRate, record.getLeftAmount(), 2);
                D_amount = MoneyUtil.add(agreementAmount, D_amount).doubleValue();
                
                if( inDays > 0 ) {
                	//生成理财人还款计划数据
                    LnFinanceRepaySchedule scheduleTemp = depFixedLoanRelationshipService.getFinanceRepaySchedule4SevenRepay(record, inDays, lastDay, agreementAmount, lastSettleTime);
                    scheduleTemp.setRepaySerial(maxSerial+1);
                    //当天时间
                    Date today = DateUtil.parseDate(DateUtil.formatYYYYMMDD(new Date()));
                    scheduleTemp.setCreateTime(new Date());
                    scheduleTemp.setPlanDate(today);
                    scheduleTemp.setRelationId(record.getId());
                    scheduleTemp.setStatus(Constants.FINANCE_REPAY_SATAE_REPAYING);
                    scheduleTemp.setUpdateTime(new Date());
                    Double total = MoneyUtil.add(scheduleTemp.getPlanTransInterest(),MoneyUtil.add(scheduleTemp.getPlanInterest(),scheduleTemp.getPlanFee()).doubleValue()).doubleValue();
                    scheduleTemp.setPlanTotal(total);
                    scheduleTemp.setPlanPrincipal(0d);
                    lnFinanceRepayScheduleMapper.insertSelective(scheduleTemp);
                }
                
            }
        }
        financeRepayCalVO.setList(compensateList);
        financeRepayCalVO.setAgreementSumAmount(D_amount);
    	return financeRepayCalVO;
    }

    // 生成账单
    private void generateRepayBill(Integer lnLoanId, String repayId, Double principal, Long repaySerial, Double total, Double interest, Double principalOverdue, Double interestOverdue) {

        // 新增ln_Repay_Schedule，生成提现还款0期账单
        LnRepaySchedule lnRepaySchedule = new LnRepaySchedule();
        lnRepaySchedule.setUpdateTime(new Date());
        lnRepaySchedule.setCreateTime(new Date());
        lnRepaySchedule.setLoanId(lnLoanId);
        lnRepaySchedule.setPartnerRepayId(repayId);
        lnRepaySchedule.setPlanDate(new Date());
        lnRepaySchedule.setPlanTotal(total != null ? total : 0d);
        lnRepaySchedule.setStatus(LoanStatus.REPAY_SCHEDULE_STATUS_INIT.getCode());
        lnRepaySchedule.setSerialId(repaySerial.intValue());
        lnRepayScheduleMapper.insertSelective(lnRepaySchedule);

        //记录还款计划明细表ln_repay_schedule_detail
        LnRepayScheduleDetail lnRepayScheduleDetail = new LnRepayScheduleDetail();
        lnRepayScheduleDetail.setUpdateTime(new Date());
        lnRepayScheduleDetail.setCreateTime(new Date());
        lnRepayScheduleDetail.setPlanId(lnRepaySchedule.getId());
        lnRepayScheduleDetail.setPlanAmount(principal != null ? principal : 0d);
        lnRepayScheduleDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_PRINCIPAL.getCode());
        lnRepayScheduleDetailMapper.insertSelective(lnRepayScheduleDetail);
        //利息
        lnRepayScheduleDetail.setPlanAmount(interest != null ? interest : 0d);
        lnRepayScheduleDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_INTEREST.getCode());
        lnRepayScheduleDetailMapper.insertSelective(lnRepayScheduleDetail);
        // 本金逾期费
        lnRepayScheduleDetail.setPlanAmount(principalOverdue != null ? principalOverdue : 0d);
        lnRepayScheduleDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_PRINCIPAL_OVERDUE.getCode());
        lnRepayScheduleDetailMapper.insertSelective(lnRepayScheduleDetail);
        // 利息逾期费
        lnRepayScheduleDetail.setPlanAmount(interestOverdue != null ? interestOverdue : 0d);
        lnRepayScheduleDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_INTEREST_OVERDUE.getCode());
        lnRepayScheduleDetailMapper.insertSelective(lnRepayScheduleDetail);
    }

    /**
     * 通过还款信息更新还款计划账单
     * @param lnRepay
     */
    private void modifyRepayBill(LnRepay lnRepay, String returnFlag) {

        // 查询还款明细信息
        LnRepayDetailExample lnRepayDetailExample = new LnRepayDetailExample();
        lnRepayDetailExample.createCriteria().andRepayIdEqualTo(lnRepay.getId()).andSubjectCodeEqualTo(LoanSubjects.SUBJECT_CODE_PRINCIPAL.getCode());
        LnRepayDetail principalDetail = lnRepayDetailMapper.selectByExample(lnRepayDetailExample).get(0);
        Double principal = principalDetail.getDoneAmount();

        lnRepayDetailExample.clear();
        lnRepayDetailExample.createCriteria().andRepayIdEqualTo(lnRepay.getId()).andSubjectCodeEqualTo(LoanSubjects.SUBJECT_CODE_INTEREST.getCode());
        LnRepayDetail interestDetail = lnRepayDetailMapper.selectByExample(lnRepayDetailExample).get(0);
        Double interest = interestDetail.getDoneAmount();

        lnRepayDetailExample.clear();
        lnRepayDetailExample.createCriteria().andRepayIdEqualTo(lnRepay.getId()).andSubjectCodeEqualTo(LoanSubjects.SUBJECT_CODE_PRINCIPAL_OVERDUE.getCode());
        LnRepayDetail principalOverdueDetail = lnRepayDetailMapper.selectByExample(lnRepayDetailExample).get(0);
        Double principalOverdue = principalOverdueDetail.getDoneAmount();

        lnRepayDetailExample.clear();
        lnRepayDetailExample.createCriteria().andRepayIdEqualTo(lnRepay.getId()).andSubjectCodeEqualTo(LoanSubjects.SUBJECT_CODE_INTEREST_OVERDUE.getCode());
        LnRepayDetail interestOverdueDetail = lnRepayDetailMapper.selectByExample(lnRepayDetailExample).get(0);
        Double interestOverdue = interestOverdueDetail.getDoneAmount();

        LnRepaySchedule repaySchedule = new LnRepaySchedule();
        repaySchedule.setId(lnRepay.getRepayPlanId());
        repaySchedule.setReturnFlag(returnFlag);
        repaySchedule.setPlanTotal(lnRepay.getDoneTotal());
        repaySchedule.setUpdateTime(new Date());
        lnRepayScheduleMapper.updateByPrimaryKeySelective(repaySchedule);

        //记录还款计划明细表
        LnRepayScheduleDetailExample example = new LnRepayScheduleDetailExample();
        example.createCriteria().andPlanIdEqualTo(lnRepay.getRepayPlanId()).andSubjectCodeEqualTo(LoanSubjects.SUBJECT_CODE_PRINCIPAL.getCode());
        LnRepayScheduleDetail lnRepayScheduleDetailTemp = new LnRepayScheduleDetail();
        lnRepayScheduleDetailTemp.setUpdateTime(new Date());
        lnRepayScheduleDetailTemp.setPlanAmount(principal != null ? principal : 0d);
        lnRepayScheduleDetailMapper.updateByExampleSelective(lnRepayScheduleDetailTemp, example);
        //利息
        example.clear();
        example.createCriteria().andPlanIdEqualTo(lnRepay.getRepayPlanId()).andSubjectCodeEqualTo(LoanSubjects.SUBJECT_CODE_INTEREST.getCode());
        lnRepayScheduleDetailTemp.setPlanAmount(interest != null ? interest : 0d);
        lnRepayScheduleDetailMapper.updateByExampleSelective(lnRepayScheduleDetailTemp, example);
        // 本金逾期费
        example.clear();
        example.createCriteria().andPlanIdEqualTo(lnRepay.getRepayPlanId()).andSubjectCodeEqualTo(LoanSubjects.SUBJECT_CODE_PRINCIPAL_OVERDUE.getCode());
        lnRepayScheduleDetailTemp.setPlanAmount(principalOverdue != null ? principalOverdue : 0d);
        lnRepayScheduleDetailMapper.updateByExampleSelective(lnRepayScheduleDetailTemp, example);
        // 利息逾期费
        example.clear();
        example.createCriteria().andPlanIdEqualTo(lnRepay.getRepayPlanId()).andSubjectCodeEqualTo(LoanSubjects.SUBJECT_CODE_INTEREST_OVERDUE.getCode());
        lnRepayScheduleDetailTemp.setPlanAmount(interestOverdue != null ? interestOverdue : 0d);
        lnRepayScheduleDetailMapper.updateByExampleSelective(lnRepayScheduleDetailTemp, example);
    }

    /**
     * 云贷-等本等息-代扣还款分账
     * */
    @Override
    public void repaySysSplit4YunFixedPrincipalInterest(NormalRepaySysSplitInfo info) {
    	//必传入参校验
		if(info.getLnPayOrdersId() == null || info.getLoanId() == null
                || info.getRepayAmount() == null || StringUtils.isBlank(info.getOrderNo())
                || StringUtils.isBlank(info.getPartnerRepayId())){
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }
        //1、业务开始前数据校验
		RepaySplitVerifyReturnVO repaySplitVerifyReturnVO = verifyBusinessBefore(info);
        LnLoan lnLoan = repaySplitVerifyReturnVO.getLnLoan();
        LnRepaySchedule repaySchedule = repaySplitVerifyReturnVO.getRepaySchedule();
		try {
			//加锁
	        jsClientDaoSupport.lock(RedisLockEnum.LOCK_DEP_LOAN_RELATION.getKey());
	        //2、查询有债权关系的债权数据
			List<LoanRelation4TransferVO> relationList = getRelationList(info);
			//3、分账和记账
			splitAccounted4FixedPrincipalInterest(info, lnLoan, relationList, repaySchedule);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jsClientDaoSupport.unlock(RedisLockEnum.LOCK_DEP_LOAN_RELATION.getKey());
		}
    }
    
    /**
     * 云贷-等额本息-代扣还款分账记账
     */
	@Override
	public void repaySysSplit4YunFixedInstallment(NormalRepaySysSplitInfo info) {
		//必传入参校验
		if(info.getLnPayOrdersId() == null || info.getLoanId() == null
                || info.getRepayAmount() == null || StringUtils.isBlank(info.getOrderNo())
                || StringUtils.isBlank(info.getPartnerRepayId())){
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }
        //1、业务开始前数据校验
		RepaySplitVerifyReturnVO repaySplitVerifyReturnVO = verifyBusinessBefore(info);
        LnLoan lnLoan = repaySplitVerifyReturnVO.getLnLoan();
        LnRepaySchedule repaySchedule = repaySplitVerifyReturnVO.getRepaySchedule();
        
		try {
			//加锁
	        jsClientDaoSupport.lock(RedisLockEnum.LOCK_DEP_LOAN_RELATION.getKey());
	        //2、查询有债权关系的债权数据
			List<LoanRelation4TransferVO> relationList = getRelationList(info);
			//3、分账和记账
			splitAccounted4FixedInstallment(info, lnLoan, relationList, repaySchedule);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			jsClientDaoSupport.unlock(RedisLockEnum.LOCK_DEP_LOAN_RELATION.getKey());
			
		}
		
	}
	/**
	 * 云贷-等本等息-还款分账记账
	 * @author gemma
	 * @param info
	 * @param lnLoan
	 * @param relationList
	 * */
	private void splitAccounted4FixedPrincipalInterest(final NormalRepaySysSplitInfo info, final LnLoan lnLoan,
			final List<LoanRelation4TransferVO> relationList, final LnRepaySchedule repaySchedule) {
		try {
			
					/**
					 * 0、判断是否是全部提前还款的非当期账单,若是,则除本金外都为合作方营收
					 * 1、每月结算利息=调用等额本息方法,等额本息方式计算某期本息和;
					 * 2、合作方营收=还款总额-每月结算本息
					 * 3、借款服务费=调用等额本息方法,等额本息方式计算某期利息;入参本金为借款总本金;入参利率为借款服务费利率;
					 */
					LnRepayExample repayExample = new LnRepayExample();
					repayExample.createCriteria().andLoanIdEqualTo(info.getLoanId()).andRepayPlanIdEqualTo(repaySchedule.getId())
		                        .andStatusEqualTo(Constants.LN_REPAY_PAY_STATUS_REPAIED);
					List<LnRepay> repayList = lnRepayMapper.selectByExample(repayExample);
					if(CollectionUtils.isEmpty(repayList)){
						logger.info("==========="+info.getPartnerEnum().getName()+"_"+info.getBusinessTypeEnum().getName()
			            		+"【代扣还款系统分账】：无对应的LnRepay信息================");
			            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
					}
					//借款人>每期还款金额
					final Double serialRepayAmount = repayList.get(0).getDoneTotal();
					//还款本金
					final Double serialRepayPrincipal = getRepayDetailAmount(repaySchedule.getId(), LoanSubjects.SUBJECT_CODE_PRINCIPAL);
					
					//云贷自主和币港湾的结算利率 --18%
					Double settleRateInit = lnLoan.getBgwSettleRate() != null?lnLoan.getBgwSettleRate() :sysConfigService.findRatePercentByKey(Constants.YUN_FIXED_PRINCIPAL_INTEREST_SYS_SETTLE_RATE);
					final Double settleRate = settleRateInit == null ? 20 : settleRateInit;
					//云贷自主和币港湾的借款服务费 -- 2%
					Double loanServerRateInit = lnLoan.getLoanServiceRate() != null ? lnLoan.getLoanServiceRate() : sysConfigService.findRatePercentByKey(Constants.LOAN_SERVICE_RATE_FIXED_PRINCIPAL_INTEREST);
					Double loanServerRate = loanServerRateInit == null ? 2 : loanServerRateInit;
					
					//每月结算本息=调用等本等息方法计算某期本息和;入参本金为借款总本金,入参利率为结算利率,总期数,当前还款期数
					logger.info("结算利息计算入参:借款本金{"+lnLoan.getApproveAmount()+"},借款期数{"+lnLoan.getPeriod()+"},结算利率{"+settleRate+"}还款期次{"+repaySchedule.getSerialId()+"}");
					final AverageCapitalPlusInterestVO settleVO = algorithmService.calEqualPrincipalInterestPlan4Serial(lnLoan.getApproveAmount(), 
							lnLoan.getPeriod(), MoneyUtil.divide(settleRate, 100, 2).doubleValue(), 
							repaySchedule.getSerialId());
					final Double settleAmount = settleVO.getPlanTotal();
					//资产合作方营收
					final Double partnerRevenueAmount = MoneyUtil.subtract(serialRepayAmount, settleAmount).doubleValue();

					//借款服务费=(借款服务费*结算利息)/结算利率;
					final Double loanServiceAmount = MoneyUtil.divide( MoneyUtil.multiply(settleVO.getPlanInterest(), loanServerRate).doubleValue(),
							settleRate).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() ;
					logger.info("币港湾结算利息{"+settleVO.getPlanInterest()+"}借款服务费{"+loanServiceAmount+"}");
					//计算当前借款剩余总本金
					final Double leftTotalPrincipal = lnLoanRelationMapper.sumLeftAmountByLoanId(lnLoan.getId());
					
					logger.info("当期还款本金={"+serialRepayPrincipal+"}借款剩余总本金={"+leftTotalPrincipal+"}" +
									"计算结息利息对象数据="+com.alibaba.fastjson.JSONObject.toJSONString(settleVO));
					transactionTemplate.execute(new TransactionCallbackWithoutResult(){
						@Override
						protected void doInTransactionWithoutResult(TransactionStatus status) {
							//理财人分账记lnFinanceRepaySchedule表
							FinanceRepayCalVO financeVO = do4FinanceRepay4YunFixedPrincipalInterest(serialRepayPrincipal, relationList, 
									MoneyUtil.divide(lnLoan.getAgreementRate(),100,2).doubleValue(), 
									lnLoan.getPeriod(), repaySchedule.getSerialId(),  repaySchedule.getPlanDate(), 
									DateUtil.parseDate(DateUtil.formatYYYYMMDD(lnLoan.getLoanTime())), 
									MoneyUtil.divide(settleRate,100,2).doubleValue(), leftTotalPrincipal,
									settleVO.getPlanInterest());
							logger.info("理财人分账记FinanceRepayCalVO:"+com.alibaba.fastjson.JSONObject.toJSONString(financeVO));
							//币港湾营收(宝付)=每月结算本息利息 -借款服务费-借款协议本息(出借人协议利息之和)
							Double bgwRevenueAmount = CalculatorUtil.calculate("a-a-a", settleAmount, loanServiceAmount, financeVO.getAgreementSumAmount());
							Double repay_amount = MoneyUtil.add(financeVO.getAgreementSumAmount(), loanServiceAmount).doubleValue();
							Double repayScheduleTotal = MoneyUtil.add(financeVO.getAgreementSumAmount(), loanServiceAmount).doubleValue();
							logger.info("币港湾营收(宝付)" + bgwRevenueAmount);
							//1、记录云贷还款营收收入 2、ln_repay_schedule更新状态为已还  3、新增存管还款计划表及明细
							repayPaymentService.doNormalRepayDetail(lnLoan, repaySchedule, serialRepayAmount, info.getOrderNo(), partnerRevenueAmount,
									relationList.get(0).getLnSubAccountId(), repayScheduleTotal, PartnerEnum.YUN_DAI_SELF, repaySchedule.getSerialId()-1, loanServiceAmount);
							
							//系统记账
							FixedRepaySysSplitInfo repaySysSplitInfo = new FixedRepaySysSplitInfo();
							repaySysSplitInfo.setThdRepayAmount(repay_amount);
							repaySysSplitInfo.setThdRevenueAmount(partnerRevenueAmount);
							repaySysSplitInfo.setThdBGWRevenueAmount(bgwRevenueAmount);
							repaySysSplitInfo.setPartner(info.getPartnerEnum());
							repaySysSplitInfo.setThdMarginAmount(0d);
							repaySysSplitInfo.setFee(0d);
							repaySysSplitInfo.setLnRepayScheduleId(repaySchedule.getId());
							depFixedRepayAccountService.repaySysSplit(repaySysSplitInfo);
						}
					});
		} catch (Exception e) {
			
		} finally {
			
		}
	}
	/**
	 * 云贷-等额本息-还款分账记账
	 * @author bianyatian
	 * @param info
	 * @param lnLoan
	 * @param relationList
	 */
	private void splitAccounted4FixedInstallment(final NormalRepaySysSplitInfo info, final LnLoan lnLoan,
			final List<LoanRelation4TransferVO> relationList, final LnRepaySchedule repaySchedule) {
		try {
			/**
			 * 0、判断是否是全部提前还款的非当期账单，若是，则除本金外都为合作方营收
			 * 1、每月结算本息=调用等额本息方法，等额本息方式计算某期本息和；入参本金为借款总本金；入参利率为结算利率；
			 * 2、合作方营收=还款总额-每月结算本息
			 * 3、借款服务费=调用等额本息方法，等额本息方式计算某期利息；入参本金为借款总本金；入参利率为借款服务费利率；
			 */
			LnRepayExample repayExample = new LnRepayExample();
			repayExample.createCriteria().andRepayPlanIdEqualTo(repaySchedule.getId()).andLoanIdEqualTo(info.getLoanId())
                        .andStatusEqualTo(Constants.LN_REPAY_PAY_STATUS_REPAIED);
			List<LnRepay> repayList = lnRepayMapper.selectByExample(repayExample);
			if(CollectionUtils.isEmpty(repayList)){
				logger.info("==========="+info.getPartnerEnum().getName()+"_"+info.getBusinessTypeEnum().getName()
	            		+"【代扣还款系统分账】：无对应的LnRepay信息================");
	            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
			}
			Date repayDate = DateUtil.parseDate(DateUtil.formatYYYYMMDD(repayList.get(0).getDoneTime()));
			//还款金额
			final Double repayTotal = repayList.get(0).getDoneTotal();
			
			//还款本金
			final Double repayPrincipal = getRepayDetailAmount(repaySchedule.getId(), LoanSubjects.SUBJECT_CODE_PRINCIPAL);
			
			if(relationList.get(0).getLastRepayPlanDate() != null && repayDate.compareTo(relationList.get(0).getLastRepayPlanDate()) <= 0){
				//还款日期 <= 账单的前一期还款计划还款日期，说明是提前非当期的还款
				logger.info("==========="+info.getPartnerEnum().getName()+"_"+info.getBusinessTypeEnum().getName()
	            		+"【代扣还款系统分账】：还款时间："+DateUtil.formatYYYYMMDD(repayDate)
	            		+"，上期账单计划还款时间："+DateUtil.formatYYYYMMDD(relationList.get(0).getLastRepayPlanDate())+"，为非当期的提前还款，还款除本金外都为合作方营收================");
				
				transactionTemplate.execute(new TransactionCallbackWithoutResult(){
					@Override
					protected void doInTransactionWithoutResult(TransactionStatus status) {
					//理财人还款本金
					Double repayFinancePrincipal = do4FinanceRepayYUNPrincipal(repayPrincipal, relationList, lnLoan.getAgreementRate(), 
							lnLoan.getPeriod(), repaySchedule.getSerialId());
					
					//币港湾营收 = 还款本金 - 理财人还款本金
					Double bgwRevenueAmount = MoneyUtil.subtract(repayPrincipal, repayFinancePrincipal).doubleValue();
				
					//合作方营收 = 还款总金额-还款本金
					Double partnerRevenueAmount = MoneyUtil.subtract(repayTotal, repayPrincipal).doubleValue();
				
					//1、记录云贷还款营收收入 2、ln_repay_schedule更新状态为已还 3、新增存管还款计划表及明细
					repayPaymentService.doNormalRepayDetail(lnLoan, repaySchedule, repayTotal,info.getOrderNo(), partnerRevenueAmount,
							relationList.get(0).getLnSubAccountId(), repayFinancePrincipal, PartnerEnum.YUN_DAI_SELF, repaySchedule.getSerialId()-1, 0d);
					
					//系统记账，只记合作方的营收，和理财人的本金
					FixedRepaySysSplitInfo repaySysSplitInfo = new FixedRepaySysSplitInfo();
					repaySysSplitInfo.setThdRepayAmount(repayFinancePrincipal);
					repaySysSplitInfo.setThdRevenueAmount(partnerRevenueAmount);
					repaySysSplitInfo.setThdBGWRevenueAmount(bgwRevenueAmount);
					repaySysSplitInfo.setPartner(info.getPartnerEnum());
					repaySysSplitInfo.setThdMarginAmount(0d);
					repaySysSplitInfo.setFee(0d);
					repaySysSplitInfo.setLnRepayScheduleId(repaySchedule.getId());
					depFixedRepayAccountService.repaySysSplit(repaySysSplitInfo);
					}
					});
			}else{
				//正常还款
				//云贷自主和币港湾的结算利率---20%
				Double settleRateInit = lnLoan.getBgwSettleRate() != null?lnLoan.getBgwSettleRate() :sysConfigService.findRatePercentByKey(Constants.YUN_FIXED_INSTALLMENT_SYS_SETTLE_RATE);
				Double settleRate = settleRateInit == null ? 20 : settleRateInit;
				//每月结算本息=调用等额本息方法，等额本息方式计算某期本息和；入参本金为借款总本金；入参利率为结算利率；
				AverageCapitalPlusInterestVO settleVO = algorithmService.calAverageCapitalPlusInterestPlan4Serial(lnLoan.getApproveAmount(), 
						lnLoan.getPeriod(), MoneyUtil.divide(settleRate, 100, 4).doubleValue(), repaySchedule.getSerialId());
				final Double settleAmount = settleVO.getPlanTotal();
				//合作方营收
				final Double partnerRevenueAmount = MoneyUtil.subtract(repayTotal, settleAmount).doubleValue();
				
				//借款服务费=调用等额本息方法，等额本息方式计算某期利息；入参本金为借款总本金；入参利率为借款服务费利率；
				AverageCapitalPlusInterestVO loanServiceVO = algorithmService.calAverageCapitalPlusInterestPlan4Serial(lnLoan.getApproveAmount(), 
						lnLoan.getPeriod(), MoneyUtil.divide(lnLoan.getLoanServiceRate(), 100, 4).doubleValue(), repaySchedule.getSerialId());
				final Double loanServiceAmount = loanServiceVO.getPlanInterest();
				transactionTemplate.execute(new TransactionCallbackWithoutResult(){
					@Override
					protected void doInTransactionWithoutResult(TransactionStatus status) {
						//理财人分账记lnFinanceRepaySchedule表
						FinanceRepayCalVO financeVO = do4FinanceRepayYUNFixedInstallment(repayPrincipal, relationList, lnLoan.getAgreementRate(), 
								lnLoan.getPeriod(), repaySchedule.getSerialId(), repaySchedule.getPlanDate(), DateUtil.parseDate(DateUtil.formatYYYYMMDD(lnLoan.getLoanTime())));
						
						//币港湾营收(宝付) = 每月结算本息 - 借款服务费-借款协议本息（理财人和）
						Double bgwRevenueAmount = CalculatorUtil.calculate("a-a-a", settleAmount, loanServiceAmount, financeVO.getAgreementSumAmount());
						
						//系统还款户金额（THD_REPAY）= 借款协议本息（理财人和）+2%借款服务费
						Double repay_amount = MoneyUtil.add(financeVO.getAgreementSumAmount(),loanServiceAmount).doubleValue();
						
						//1、记录云贷还款营收收入 2、ln_repay_schedule更新状态为已还 3、新增存管还款计划表及明细
						repayPaymentService.doNormalRepayDetail(lnLoan, repaySchedule, repayTotal,info.getOrderNo(), partnerRevenueAmount,
								relationList.get(0).getLnSubAccountId(), repay_amount, PartnerEnum.YUN_DAI_SELF, repaySchedule.getSerialId()-1, loanServiceAmount);
						
						//系统记账
						FixedRepaySysSplitInfo repaySysSplitInfo = new FixedRepaySysSplitInfo();
						repaySysSplitInfo.setThdRepayAmount(repay_amount);
						repaySysSplitInfo.setThdRevenueAmount(partnerRevenueAmount);
						repaySysSplitInfo.setThdBGWRevenueAmount(bgwRevenueAmount);
						repaySysSplitInfo.setPartner(info.getPartnerEnum());
						repaySysSplitInfo.setThdMarginAmount(0d);
						repaySysSplitInfo.setFee(0d);
						repaySysSplitInfo.setLnRepayScheduleId(repaySchedule.getId());
						depFixedRepayAccountService.repaySysSplit(repaySysSplitInfo);
					}
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * 计算每个理财人对应的借款协议本息、应回本金、应回利息
	 * @author bianyatian
	 * @param repayPrincipal
	 * @param relationList
	 * @param agreementRate
	 * @param period
	 * @param serialId
	 * @param repayPlanDate
	 * @param loanDate
	 * @return
	 */
	protected FinanceRepayCalVO do4FinanceRepayYUNFixedInstallment(
			Double repayPrincipal, List<LoanRelation4TransferVO> relationList, Double agreementRate,
			Integer loanPeriod, Integer repayScheduleSerialId, Date repayPlanDate, Date loanDate) {
		FinanceRepayCalVO financeRepayCalVO = new FinanceRepayCalVO();
		Double sumRepayPrincipal = 0d; //累计还到用户的本金
		Double sumRepayAgreement = 0d; //累计协议利率本息
		Double agreeRate = MoneyUtil.divide(agreementRate, 100, 4).doubleValue();
		for (LoanRelation4TransferVO loanRelation4TransferVO : relationList) {
			/**
			 * 借款协议本息=调用方法2，等额本息方式计算某期本息和；入参本金为债权初始本金（承接后）；入参利率为借款协议利率13%；
			 * 入参总期数债转后期数（借款期数-匹配表的first_term+1）；为入参当前期数为债转后的还款期数（借款人还款期数-匹配表的first_term+1）；
			 */
			AverageCapitalPlusInterestVO agreementVo = algorithmService.calAverageCapitalPlusInterestPlan4Serial(
					loanRelation4TransferVO.getInitAmount(),(loanPeriod-loanRelation4TransferVO.getFirstTerm()+1), agreeRate ,	
					(repayScheduleSerialId-loanRelation4TransferVO.getFirstTerm()+1));
			//借款协议本息
			Double repayAgreement = agreementVo.getPlanTotal();
			sumRepayAgreement = MoneyUtil.add(sumRepayAgreement, repayAgreement).doubleValue();
			/**
			 * 投资人本金=调用等额本息方法，等额本息方式计算某期本金；入参本金为债权初始本金（承接后）；入参利率为借款协议利率13%；
			 * 入参总期数债转后期数（借款期数-匹配表的first_term+1）；为入参当前期数为债转后的还款期数（借款人还款期数-匹配表的first_term+1）；
			 * 
			 */
			/*AverageCapitalPlusInterestVO principalVo = algorithmService.calAverageCapitalPlusInterestPlan4Serial(
					loanRelation4TransferVO.getInitAmount(), (loanPeriod-loanRelation4TransferVO.getFirstTerm()+1), 
					agreeRate, (repayScheduleSerialId-loanRelation4TransferVO.getFirstTerm()+1));*/
			//应回理财人本金
			Double repay2UserPrincipal = agreementVo.getPlanPrincipal();
			sumRepayPrincipal = MoneyUtil.add(sumRepayPrincipal, repay2UserPrincipal).doubleValue();
			
			/**
			 * 产品利息=调用等额本息方法，等额本息方式计算某期利息；
			 * 入参本金为--债权初始本金（承接后）；入参利率为--产品利率8%；
			 * 入参总期数为--债转后期数（借款期数-匹配表的first_term+1）；
			 * 入参当前期数为--债转后的还款期数（借款人还款期数-匹配表的first_term+1）；
			 * 若为转让后的第一期，需要乘以(当期总天数-转让前持有天数)/当期总天数  （债转当日记为承接人的计息开始日期），
			 * 再加上承接时的债转付息
			 */
			AverageCapitalPlusInterestVO interestVo = algorithmService.calAverageCapitalPlusInterestPlan4Serial(
					loanRelation4TransferVO.getInitAmount(), (loanPeriod-loanRelation4TransferVO.getFirstTerm()+1), 
					MoneyUtil.divide(loanRelation4TransferVO.getBaseRate(), 100, 4).doubleValue(), 
					(repayScheduleSerialId-loanRelation4TransferVO.getFirstTerm()+1));
			//应回理财人利息
			Double repay2UserInterest = interestVo.getPlanInterest();
			//应回理财人债转付息
			Double repay2UserTransInterest = 0d;
			
			//最后计息日 = 理财人回款日 != null && 理财人回款日-1 <当期账单日 ？ (理财人回款日-1) : 当期账单日
			Date lastCalDate = loanRelation4TransferVO.getLastFinishInterestDate()!= null && DateUtil.addDays(loanRelation4TransferVO.getLastFinishInterestDate(), -1).compareTo(repayPlanDate) < 0
					? DateUtil.addDays(loanRelation4TransferVO.getLastFinishInterestDate(), -1) : repayPlanDate;
			//当期总天数
			Integer termDays = 0;
			if(loanRelation4TransferVO.getLastRepayPlanDate() == null){
				//上次还款计划还款日期为空，说明未发生还款，则该期期数为借款日到该期还款日（前后包括）
				termDays = DateUtil.getDiffeDay(repayPlanDate,loanDate)+1;
				logger.info("===========【代扣还款系统分账】债权id="+loanRelation4TransferVO.getId()+"，借款日期："+DateUtil.formatYYYYMMDD(loanDate)+"================");
			}else{
				//上次还款计划还款日期不为空，说明已发生还款，则该期期数为上次还款日到该期还款日（前不包括）
				termDays = DateUtil.getDiffeDay(repayPlanDate,loanRelation4TransferVO.getLastRepayPlanDate());
				logger.info("===========【代扣还款系统分账】债权id="+loanRelation4TransferVO.getId()+"，上次还款日期："+DateUtil.formatYYYYMMDD(loanRelation4TransferVO.getLastRepayPlanDate())+"================");
			}
			//转让后当期持有债权天数（前后包括）
			Integer diffDays = 0;
			if(loanRelation4TransferVO.getLastRepayPlanDate() == null 
					|| loanRelation4TransferVO.getLastRepayPlanDate().compareTo(loanRelation4TransferVO.getRelationBeginDate()) <0){
				//上次还款计划还款日期为空或小于债权起始日，说明未发生还款或还款发生在获得债权前，则转让后当期持有债权天数为债权起息日到结束日
				diffDays = DateUtil.getDiffeDay(lastCalDate, loanRelation4TransferVO.getRelationBeginDate())+1;
				logger.info("===========【代扣还款系统分账】债权id="+loanRelation4TransferVO.getId()+"，债权起息日："+DateUtil.formatYYYYMMDD(loanRelation4TransferVO.getRelationBeginDate())+"================");
			}else{
				//上次还款计划还款日期不为空，说明已发生还款，则该期期数为上次还款日到结束日（前不包括）
				diffDays = DateUtil.getDiffeDay(lastCalDate,loanRelation4TransferVO.getLastRepayPlanDate());
				logger.info("===========【代扣还款系统分账】债权id="+loanRelation4TransferVO.getId()+"，理财人从上次还款日："+DateUtil.formatYYYYMMDD(loanRelation4TransferVO.getLastRepayPlanDate())+"================");
			}
					
			//应回理财人利息 = 期满利息*占有天数/当期总天数 
			repay2UserInterest = CalculatorUtil.calculate("a*a/a", repay2UserInterest, diffDays.doubleValue(), termDays.doubleValue());
			logger.info("===========【代扣还款系统分账】债权id="+loanRelation4TransferVO.getId()+"，此次还款，占有天数="
					+diffDays+"，当期总天数="+termDays+"================");
			
			if(Constants.TRANS_MARK_TRANS_IN.equals(loanRelation4TransferVO.getTransMark()) && 
					loanRelation4TransferVO.getFirstTerm() == repayScheduleSerialId){
				//应回理财人债转付息
				repay2UserTransInterest = loanRelation4TransferVO.getLastPayInterest();
			}
			
			//币港湾营收（恒丰）=协议本息-本金-利息-债转付息
			Double bgwFee = CalculatorUtil.calculate("a-a-a-a",repayAgreement,repay2UserPrincipal, repay2UserInterest, repay2UserTransInterest );
			//生成理财人还款计划数据
            LnFinanceRepaySchedule scheduleTemp = new LnFinanceRepaySchedule();
            scheduleTemp.setRepaySerial(repayScheduleSerialId);
            //当天日期
            Date today = DateUtil.parseDate(DateUtil.formatYYYYMMDD(new Date()));
            scheduleTemp.setPlanTotal(repayAgreement);
            scheduleTemp.setPlanPrincipal(repay2UserPrincipal);
            scheduleTemp.setPlanFee(bgwFee);
            scheduleTemp.setPlanInterest(repay2UserInterest);
            scheduleTemp.setPlanTransInterest(repay2UserTransInterest);
            scheduleTemp.setCreateTime(new Date());
            scheduleTemp.setPlanDate(today);
            scheduleTemp.setRelationId(loanRelation4TransferVO.getId());
            scheduleTemp.setStatus(Constants.FINANCE_REPAY_SATAE_REPAYING);
            scheduleTemp.setUpdateTime(new Date());
            lnFinanceRepayScheduleMapper.insertSelective(scheduleTemp);
		}
		financeRepayCalVO.setAgreementSumAmount(sumRepayAgreement);
		financeRepayCalVO.setFinanceSumPrincipal(sumRepayPrincipal);
		return financeRepayCalVO;
	}

	/**
	 * 理财人仅回本金，无利息
	 * 投资人本金=调用等额本息方法，等额本息方式计算某期本金；入参本金为债权初始本金（承接后）；入参利率为借款协议利率13%；
	 * 入参总期数债转后期数（借款期数-匹配表的first_term+1）；为入参当前期数为债转后的还款期数（借款人还款期数-匹配表的first_term+1）；
	 * @author bianyatian
	 * @param repayPrincipal 还款总本金
	 * @param relationList 债权列表数据
	 * @param agreementRate 协议利率
	 * @param loanPeriod 借款总期数
	 * @param repayScheduleSerialId 借款人还款期数
	 * @return 
	 */
	protected Double do4FinanceRepayYUNPrincipal(Double repayPrincipal,
			List<LoanRelation4TransferVO> relationList, Double agreementRate, Integer loanPeriod, Integer repayScheduleSerialId) {
		Double sumRepayPrincipal = 0d; //累计还到用户的金额
		Double agreeRate = MoneyUtil.divide(agreementRate, 100, 4).doubleValue();
		for (LoanRelation4TransferVO loanRelation4TransferVO : relationList) {
			AverageCapitalPlusInterestVO plusVo = algorithmService.calAverageCapitalPlusInterestPlan4Serial(
					loanRelation4TransferVO.getInitAmount(), (loanPeriod-loanRelation4TransferVO.getFirstTerm()+1), 
					agreeRate, (repayScheduleSerialId-loanRelation4TransferVO.getFirstTerm()+1));
			//应回理财人本金
			Double repay2UserPrincipal = plusVo.getPlanPrincipal();
			
			logger.info("===========【代扣还款系统分账】理财人分账—等额本息，计算本金，债权关系id="+loanRelation4TransferVO.getId()+"，债权初始金额="+loanRelation4TransferVO.getInitAmount()
            		+"，债权期数="+(loanPeriod-loanRelation4TransferVO.getFirstTerm()+1)
            		+"，计算利率（协议利率）="+agreeRate
            		+"，还款期数="+(repayScheduleSerialId-loanRelation4TransferVO.getFirstTerm()+1)
            		+"，应回本金="+repay2UserPrincipal+"================");
			sumRepayPrincipal = MoneyUtil.add(sumRepayPrincipal, repay2UserPrincipal).doubleValue();
			
			//生成理财人还款计划数据
            LnFinanceRepaySchedule scheduleTemp = new LnFinanceRepaySchedule();
            scheduleTemp.setRepaySerial(repayScheduleSerialId);
            //当天日期
            Date today = DateUtil.parseDate(DateUtil.formatYYYYMMDD(new Date()));
            scheduleTemp.setPlanTotal(repay2UserPrincipal);
            scheduleTemp.setPlanPrincipal(repay2UserPrincipal);
            scheduleTemp.setPlanFee(0d);
            scheduleTemp.setPlanInterest(0d);
            scheduleTemp.setPlanTransInterest(0d);
            scheduleTemp.setCreateTime(new Date());
            scheduleTemp.setPlanDate(today);
            scheduleTemp.setRelationId(loanRelation4TransferVO.getId());
            scheduleTemp.setStatus(Constants.FINANCE_REPAY_SATAE_REPAYING);
            scheduleTemp.setUpdateTime(new Date());
            lnFinanceRepayScheduleMapper.insertSelective(scheduleTemp);
		}
		return sumRepayPrincipal;
	}

	/**
	 * 代扣还款分账-云贷-等额本息-获取债权列
	 * @author bianyatian
	 * @param info
	 * @return
	 */
	private List<LoanRelation4TransferVO> getRelationList(
			NormalRepaySysSplitInfo info) {
		List<LoanRelation4TransferVO> relationList = lnLoanRelationMapper.getYunFixedInstallmentRelationList(info.getLoanId());
		if(CollectionUtils.isEmpty(relationList)){
			logger.info("==========="+info.getPartnerEnum().getName()+"_"+info.getBusinessTypeEnum().getName()
            		+"【代扣还款系统分账】：无对应的债权列表================");
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
		}
		return relationList;
	}

	/**
	 * 代扣还分账-业务开始前数据校验
	 * @author bianyatian
	 * @param info
	 * @return
	 */
	private RepaySplitVerifyReturnVO verifyBusinessBefore(NormalRepaySysSplitInfo info) {
		RepaySplitVerifyReturnVO repaySplitVerifyReturnVO = new RepaySplitVerifyReturnVO();
		//1、前置校验：借款表是否存在
        LnLoan lnLoan = lnLoanMapper.selectByPrimaryKey(info.getLoanId());
        if(lnLoan == null){
            logger.info("==========="+info.getPartnerEnum().getName()+"_"+info.getBusinessTypeEnum().getName()
            		+"【代扣还款系统分账】：校验，借款表数据未获得================");
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }
        //2、前置校验：借款表存在，查询借款用户，确定用户是否存在，合作方是否一致
        LnUser lnUser = lnUserMapper.selectByPrimaryKey(lnLoan.getLnUserId());
        if(lnUser == null){
        	logger.info("==========="+info.getPartnerEnum().getName()+"_"+info.getBusinessTypeEnum().getName()
        			+"【代扣还款系统分账】：校验，借款用户数据不存在================");
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }
        if(!info.getPartnerEnum().getCode().equals(lnUser.getPartnerCode())){
        	logger.info("==========="+info.getPartnerEnum().getName()+"_"+info.getBusinessTypeEnum().getName()
        			+"【代扣还款系统分账】：校验，入参合作方和借款用户合作方不一致================");
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }
        
        //3、前置校验：判断订单是否处理中，还款信息是否存在等
        LnPayOrdersExample payOrdersExample = new LnPayOrdersExample();
        payOrdersExample.createCriteria().andIdEqualTo(info.getLnPayOrdersId())
                .andStatusEqualTo(Constants.ORDER_STATUS_SUCCESS);
        List<LnPayOrders> ordersList = lnPayOrdersMapper.selectByExample(payOrdersExample);
        if(CollectionUtils.isEmpty(ordersList)){
            logger.info("==========="+info.getPartnerEnum().getName()+"_"+info.getBusinessTypeEnum().getName()
            		+"【主动、代扣还款系统分账】：校验，订单数据未获得================");
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }

        //4、前置校验：查询ln_repay_schedule，判断还款计划表中是否存在
        LnRepayScheduleExample repayScheduleExample = new LnRepayScheduleExample();
        repayScheduleExample.createCriteria().andPartnerRepayIdEqualTo(info.getPartnerRepayId())
                .andLoanIdEqualTo(info.getLoanId()).andStatusNotEqualTo(Constants.LN_REPAY_CANCELLED);
        List<LnRepaySchedule> repaySchedulList = lnRepayScheduleMapper.selectByExample(repayScheduleExample);
        if(CollectionUtils.isEmpty(repaySchedulList)){
            logger.info("==========="+info.getPartnerEnum().getName()+"_"+info.getBusinessTypeEnum().getName()
            		+"【主动、代扣还款系统分账】：还款计划表数据未获得================");
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }
        
        if(Constants.LN_REPAY_REPAIED.equals(repaySchedulList.get(0).getStatus()) 
        		|| Constants.LN_REPAY_LATE_REPAIED.equals(repaySchedulList.get(0).getStatus()) ){
        	logger.info("==========="+info.getPartnerEnum().getName()+"_"+info.getBusinessTypeEnum().getName()
        			+"【主动、代扣还款系统分账】：还款计划表数据状态为："+repaySchedulList.get(0).getStatus()+"================");
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }
        logger.info("==========="+info.getPartnerEnum().getName()+"_"+info.getBusinessTypeEnum().getName()
        		+"【主动、代扣还款系统分账】：LnRepaySchedule.id="+repaySchedulList.get(0).getId()+"================");
        repaySplitVerifyReturnVO.setLnLoan(lnLoan);
        repaySplitVerifyReturnVO.setLnUser(lnUser);
        repaySplitVerifyReturnVO.setRepaySchedule(repaySchedulList.get(0));
        
        return repaySplitVerifyReturnVO;
	}
	
	
	/**
	 * 计算每个理财人对应的出借人借款协议本息、出借人应回本金、出借人应回利息(产品利息)
	 * @author gemma
	 * @param repayPrincipal -- 还款本金
	 * @param relationList -- 债权关系列表
	 * @param agreementRate -- 借款协议利率 13% 传0.13
	 * @param period -- 总期数
	 * @param serialId -- 期次
	 * @param repayPlanDate -- 本期次账单日
	 * @param loanDate -- 放款日
	 * @param leftTotalPrincipal -- 借款剩余总本金
	 * @param payingPrincipal -- 借款还款中本金
	 * @param jsInterest -- 本期结算利息  20% 传0.2
	 * @param loanServiceFee -- 借款服务费 
	 * @return 
	 */
	protected FinanceRepayCalVO do4FinanceRepay4YunFixedPrincipalInterest(
			Double repayPrincipal, List<LoanRelation4TransferVO> relationList, Double agreementRate,
			Integer loanPeriod, Integer repayScheduleSerialId, Date repayPlanDate, Date loanDate, 
			Double settleRate, Double leftTotalPrincipal, Double jsInterest) {
		
		FinanceRepayCalVO financeRepayCalVO = new FinanceRepayCalVO();
		Double sumRepayPrincipal = 0d; //累计还到用户的本金
		Double sumRepayAgreement = 0d; //累计协议利率本息
		
		for (LoanRelation4TransferVO loanRelation4TransferVO : relationList) {
			logger.info("理财人loanRelation4TransferVO={"+com.alibaba.fastjson.JSONObject.toJSONString(loanRelation4TransferVO)+"}");
			/**
			 * 出借人借款协议本息=(出借人剩余本金*借款协议利率)/(剩余总本金*结算利率)*结算利息;
			 */
			Double repayAgreement = MoneyUtil.divide( 
					MoneyUtil.multiply(
							MoneyUtil.multiply(
									loanRelation4TransferVO.getLeftAmount(), 
									agreementRate).doubleValue(), 
							jsInterest).doubleValue() ,  
					MoneyUtil.multiply(
							leftTotalPrincipal, settleRate).doubleValue()
			).doubleValue();
			
			/**
			 * 出借人应回产品本金=(出借人初始本金/借款期数;(最后一期应回本金=初始总本金-前几期本金之和)
			 * 如果是债转,见TRANS_IN逻辑分支{债转后初始本金}
			 * 出借人应回本金 = 承接债权总本金/承接债权总期数，最后一期为=承接债权总本金-前N期的承接债权本金之和 left_amount
			 * FLOOR求精度
			 */
			Double repay2UserPrincipal = 0d;
			if( !loanPeriod.equals(repayScheduleSerialId) ) {
				repay2UserPrincipal = CalculatorUtil.formatCash(loanRelation4TransferVO.getInitAmount(), 
						loanPeriod-loanRelation4TransferVO.getFirstTerm()+1, 
						2);
			} else {
				repay2UserPrincipal = loanRelation4TransferVO.getLeftAmount();
			}
			
			/**
			 * 出借人应回产品利息=(出借人剩余本金*产品利率)/(剩余总本金*结算利率)*结算利息;
			 * 若为转让后的第一期,需要乘以(当期总天数-转让前持有天数)/当期总天数 (债转当日记为承接人的计息开始日期),
			 * 再加上承接时的债转付息
			 */
			logger.info("债权id{"+loanRelation4TransferVO.getId()+"}出借人剩余本金{"+loanRelation4TransferVO.getLeftAmount()+"}产品利率{"+loanRelation4TransferVO.getBaseRate()+"}" +
					"结算利息{"+jsInterest+"}剩余借款总本金{"+leftTotalPrincipal+"}结算利率{"+MoneyUtil.multiply(settleRate, 100).doubleValue()+"}");
			Double repay2UserInterest = MoneyUtil.divide( 
					MoneyUtil.multiply(
							MoneyUtil.multiply(
									loanRelation4TransferVO.getLeftAmount(), 
									loanRelation4TransferVO.getBaseRate()).doubleValue(), 
							jsInterest).doubleValue() ,  
					MoneyUtil.multiply(
							leftTotalPrincipal,
								MoneyUtil.multiply(settleRate, 100).doubleValue()).doubleValue()
			).doubleValue();
			
			//出借人应回理财人债转付息
			Double repay2UserTransInterest = 0d;
			//如果发生过债转-需要计算债转付息,及持有天数
			if( Constants.TRANS_MARK_TRANS_IN.equals(loanRelation4TransferVO.getTransMark()) ) {
				//最后计息日=理财人回款日!= null && 理财人回款日-1<当期账单日?(理财人回款日-1):当期账单日
				Date lastCalDate = loanRelation4TransferVO.getLastFinishInterestDate() != null && DateUtil.addDays(loanRelation4TransferVO.getLastFinishInterestDate(), -1).compareTo(repayPlanDate) < 0
						? DateUtil.addDays(loanRelation4TransferVO.getLastFinishInterestDate(), -1) : repayPlanDate;
				//当期总天数
				Integer termDays = 0;
				if( loanRelation4TransferVO.getLastRepayPlanDate() == null ) {
					//上次还款日为空，说明未发生还款，则该期期数为借款日到该期还款日(前后包括)
					termDays = DateUtil.getDiffeDay(repayPlanDate,loanDate)+1;
					logger.info("债权转让后,债权id{"+loanRelation4TransferVO.getId()+"}当期账单日{"+DateUtil.formatYYYYMMDD(repayPlanDate)+"}借款日期{"+DateUtil.formatYYYYMMDD(loanDate)+"}");
				} else {
					//上次还款日不为空，说明已发生还款，则该期期数为上次还款日到该期还款日(前不包括)
					termDays = DateUtil.getDiffeDay(repayPlanDate,loanRelation4TransferVO.getLastRepayPlanDate());
					logger.info("债权转让后,债权id{"+loanRelation4TransferVO.getId()+"}当期账单日{"+DateUtil.formatYYYYMMDD(repayPlanDate)+"}上次还款日期{"+DateUtil.formatYYYYMMDD(loanRelation4TransferVO.getLastRepayPlanDate())+"}");
				}
				
				Integer diffDays = 0;//转让后当期持有债权天数(前后包括)
				if( loanRelation4TransferVO.getLastRepayPlanDate() == null 
						|| loanRelation4TransferVO.getLastRepayPlanDate().compareTo(loanRelation4TransferVO.getRelationBeginDate()) < 0 ) {
					//上次还款计划还款日期为空或小于债权起始日，说明未发生还款或还款发生在获得债权前，则转让后当期持有债权天数为债权起息日到结束日
					diffDays = DateUtil.getDiffeDay(lastCalDate, loanRelation4TransferVO.getRelationBeginDate())+1;
					logger.info("债权转让后,债权id{"+loanRelation4TransferVO.getId()+"},上次还款计划还款日期为空或小于债权起始日,未发生还款或还款发生在获得债权前," +
							"最后计息日{"+DateUtil.formatYYYYMMDD(lastCalDate)+"}债权起息日{"+DateUtil.formatYYYYMMDD(loanRelation4TransferVO.getRelationBeginDate())+"}");
				} else {
					//上次还款计划还款日期不为空，说明已发生还款，则该期期数为上次还款日到结束日(前不包括)
					diffDays = DateUtil.getDiffeDay(lastCalDate,loanRelation4TransferVO.getLastRepayPlanDate());
					diffDays = diffDays < 0 ? 0: diffDays;
					logger.info("债权转让后,债权id{"+loanRelation4TransferVO.getId()+"},上次还款计划还款日期不为空,已发生还款," +
							"最后计息日{"+DateUtil.formatYYYYMMDD(lastCalDate)+"}理财人从上次还款日{"+DateUtil.formatYYYYMMDD(loanRelation4TransferVO.getLastRepayPlanDate())+"}");
				}
				
				//出借人应回利息 = 期满利息*占有天数/当期总天数 
				repay2UserInterest = CalculatorUtil.calculate("a*a/a", repay2UserInterest, diffDays.doubleValue(), termDays.doubleValue());
				logger.info("债权转让后,债权id{"+loanRelation4TransferVO.getId()+"}占有天数="+diffDays+",当期总天数="+termDays+"}");
				//出借人应回债转付息
				repay2UserTransInterest = loanRelation4TransferVO.getLastPayInterest();
			}
			//出借人应回总本金
			sumRepayPrincipal = MoneyUtil.add(sumRepayPrincipal, repay2UserPrincipal).doubleValue();
			//出借人借款协议总本息
			sumRepayAgreement = CalculatorUtil.calculate("a+a+a", sumRepayAgreement , repayAgreement , repay2UserPrincipal);
			
			//币港湾营收(恒丰)= 协议利息-利息-债转付息
			Double bgwFee = CalculatorUtil.calculate( "a-a-a", repayAgreement, repay2UserInterest, repay2UserTransInterest );
			//协议利息+应回本金+借款服务费
			Double planTotal = CalculatorUtil.calculate( "a+a", repayAgreement, repay2UserPrincipal);
			
			logger.info("出借人借款协议本息{"+repayAgreement+"}出借人应回产品本金{"+repay2UserPrincipal+"}出借人应回产品利息{"+repay2UserInterest
					+"出借人应回理财人债转付息}"+repay2UserTransInterest+"}币港湾营收(恒丰){"+bgwFee+"}");
			
			//生成理财人还款计划数据
            LnFinanceRepaySchedule scheduleTemp = new LnFinanceRepaySchedule();
            scheduleTemp.setRepaySerial(repayScheduleSerialId);
            //当天日期
            Date today = DateUtil.parseDate(DateUtil.formatYYYYMMDD(new Date()));
            scheduleTemp.setRelationId(loanRelation4TransferVO.getId());
            scheduleTemp.setPlanDate(today);
            scheduleTemp.setPlanTotal(planTotal);
            scheduleTemp.setPlanPrincipal(repay2UserPrincipal);
            scheduleTemp.setPlanInterest(repay2UserInterest);
            scheduleTemp.setPlanTransInterest(repay2UserTransInterest);
            scheduleTemp.setPlanFee(bgwFee);
            scheduleTemp.setStatus(Constants.FINANCE_REPAY_SATAE_REPAYING);
            scheduleTemp.setCreateTime(new Date());
            scheduleTemp.setUpdateTime(new Date());
            lnFinanceRepayScheduleMapper.insertSelective(scheduleTemp);
		}
		logger.info("出借人借款协议总本息="+sumRepayAgreement+"出借人总本金="+sumRepayPrincipal);
		financeRepayCalVO.setAgreementSumAmount(sumRepayAgreement);
		financeRepayCalVO.setFinanceSumPrincipal(sumRepayPrincipal);
		return financeRepayCalVO;
	}
}

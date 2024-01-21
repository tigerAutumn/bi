package com.pinting.business.accounting.loan.service.impl;

import com.alibaba.fastjson.JSON;
import com.pinting.business.accounting.loan.enums.LoanStatus;
import com.pinting.business.accounting.loan.enums.LoanSubjects;
import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.loan.model.FinanceRepayCalVO;
import com.pinting.business.accounting.loan.model.RepayInfo;
import com.pinting.business.accounting.loan.model.RepayResultInfo;
import com.pinting.business.accounting.loan.service.FinanceReceiveMoneyService;
import com.pinting.business.accounting.loan.service.RepayAccountService;
import com.pinting.business.accounting.loan.service.RepayPaymentService;
import com.pinting.business.accounting.loan.service.impl.process.RepayProcess;
import com.pinting.business.accounting.loan.util.redisUtil;
import com.pinting.business.accounting.service.PayOrdersService;
import com.pinting.business.dao.*;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.enums.PayPlatformEnum;
import com.pinting.business.enums.RedisLockEnum;
import com.pinting.business.enums.ThirdSysChannelEnum;
import com.pinting.business.enums.TransTypeEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.*;
import com.pinting.business.model.dto.OrderResultInfo;
import com.pinting.business.model.dto.RepayQueueDTO;
import com.pinting.business.model.vo.*;
import com.pinting.business.service.common.AlgorithmService;
import com.pinting.business.service.common.CommissionService;
import com.pinting.business.service.common.OrderBusinessService;
import com.pinting.business.service.loan.LoanUserMobileWhiteListCheckService;
import com.pinting.business.service.loan.LoanUserService;
import com.pinting.business.service.manage.BsSysConfigService;
import com.pinting.business.service.site.BankCardService;
import com.pinting.business.service.site.SMSService;
import com.pinting.business.service.site.SpecialJnlService;
import com.pinting.business.service.site.SysConfigService;
import com.pinting.business.util.Constants;
import com.pinting.business.util.Util;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.hessian.message.baofoo.*;
import com.pinting.gateway.hessian.message.hfbank.B2GReqMsg_HFBank_RepayCompensate;
import com.pinting.gateway.hessian.message.hfbank.B2GResMsg_HFBank_RepayCompensate;
import com.pinting.gateway.hessian.message.loan.*;
import com.pinting.gateway.hessian.message.loan.model.Repayment;
import com.pinting.gateway.hessian.message.pay19.enums.OrderStatus;
import com.pinting.gateway.out.service.BaoFooTransportService;
import com.pinting.gateway.out.service.HfbankTransportService;
import com.pinting.gateway.out.service.loan.NoticeService;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 剑钊 on 2016/8/19.
 */
@Service
public class RepayPaymentServiceImpl implements RepayPaymentService {
    private final Logger log = LoggerFactory.getLogger(RepayPaymentServiceImpl.class);
    private JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);

    @Autowired
    private BaoFooTransportService baoFooTransportService;
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private LnRepayMapper repayMapper;
    @Autowired
    private LnPayOrdersMapper payOrdersMapper;
    @Autowired
    private LnPayOrdersJnlMapper payOrdersJnlMapper;
    @Autowired
    private LnLoanMapper loanMapper;
    @Autowired
    private LnRepayScheduleMapper scheduleMapper;
    @Autowired
    private LnRepayDetailMapper detailMapper;
    @Autowired
    private BankCardService bankCardService;
    @Autowired
    private LnBindCardMapper bindCardMapper;
    @Autowired
    private BsPayResultQueueMapper queueMapper;
    @Autowired
    private RepayAccountService repayAccountService;
    @Autowired
    private LoanUserService loanUserService;
    @Autowired
    private LnLoanRelationMapper relationMapper;
    @Autowired
    private AlgorithmService algorithmService;
    @Autowired
    private LnRepayScheduleDetailMapper scheduleDetailMapper;
    @Autowired
    private FinanceReceiveMoneyService financeReceiveMoneyService;
    @Autowired
    private SpecialJnlService specialJnlService;
    @Autowired
    private CommissionService commissionService;
    @Autowired
    private BsServiceFeeMapper serviceFeeMapper;
    @Autowired
    private LnFinanceRepayScheduleMapper financeRepayScheduleMapper;
    @Autowired
    private OrderBusinessService orderBusinessService;
    @Autowired
    private PayOrdersService payOrdersService;
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private LnBadMapper lnBadMapper;
    @Autowired
    private LnBadDetailMapper lnBadDetailMapper;
    @Autowired
    private LnUserMapper lnUserMapper;
    @Autowired
    private LnSubjectMapper lnSubjectMapper;
    @Autowired
    private BsRevenueTransDetailMapper bsRevenueTransDetailMapper;
    @Autowired
    private LnDepositionRepayScheduleMapper lnDepositionRepayScheduleMapper;
    @Autowired
    private LnRepayScheduleDetailMapper lnRepayScheduleDetailMapper;
    @Autowired
    private LnDepositionRepayScheduleDetailMapper lnDepositionRepayScheduleDetailMapper;
    @Autowired
    private SysConfigService sysConfigService;
    @Autowired
    private BsHfbankUserExtMapper bsHfbankUserExtMapper;
    @Autowired
    private LnDepositionTargetMapper lnDepositionTargetMapper;
    @Autowired
    private HfbankTransportService hfbankTransportService;
    @Autowired
    private LnCompensateRelationMapper lnCompensateRelationMapper;
    @Autowired
    private SMSService smsService;
    @Autowired
    private Bs19payBankMapper bs19payBankMapper;
    @Autowired
    private LoanUserMobileWhiteListCheckService loanUserMobileWhiteListCheckService;
    @Autowired
    private BsPaymentChannelMapper paymentChannelMapper;
    @Autowired
    private BsSysConfigService bsSysConfigService;

    @Override
    //@Transactional
    public String preRepay(G2BReqMsg_Repay_PreRepay req) throws Exception {
        LnUser lnUser = loanUserService.queryLoanUserExist(req.getUserId(), PartnerEnum.ZAN.getCode());
        if(lnUser == null){
            throw new PTMessageException(PTMessageEnum.ZAN_ADDLOANCIF_USER_NOT_EXIST);
        }else{
            //白名单
            if (!loanUserMobileWhiteListCheckService.lnMobileWhiteListCheck(lnUser.getMobile())) {
                throw new PTMessageException(PTMessageEnum.ZAN_LOAN_USER_NOT_FOUND_IN_WHITE);
            }
        }

        //检查绑卡表，如果客户对应的卡未绑定，则返回相应错误
        LnBindCard lnBindCard = loanUserService.queryLoanBindCardExist(req.getUserId(), req.getBindId(), req.getChannel());

        LnRepayExample repayExample = new LnRepayExample();
        repayExample.createCriteria().andPartnerOrderNoEqualTo(req.getOrderNo());
        List<LnRepay> repayList = repayMapper.selectByExample(repayExample);
        if (CollectionUtils.isNotEmpty(repayList) && repayList.size() > 0) {
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "订单号重复");
        }

        if (lnBindCard != null) {

            if (CollectionUtils.isNotEmpty(req.getRepayments()) && req.getRepayments().size() > 0) {

                //查询借款信息
                LnLoanExample loanExample = new LnLoanExample();
                loanExample.createCriteria().andPartnerLoanIdEqualTo(req.getLoanId()).andStatusEqualTo(LoanStatus.LOAN_STATUS_PAIED.getCode());
                List<LnLoan> loanList = loanMapper.selectByExample(loanExample);
                if (CollectionUtils.isEmpty(loanList) || loanList.size() == 0) {
                    throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND, "找不到借款信息");
                }


                //支付订单号
                String payOrderNo = Util.generateOrderNo4BaoFoo(8);
                //记录还款信息表id
                List<Integer> repayIds = new ArrayList<>();

                for (Repayment repayment : req.getRepayments()) {
                    log.info("==========================还款明细:" + JSONObject.fromObject(repayment).toString());
                    //查询对应的还款计划表
                    LnRepayScheduleExample scheduleExample = new LnRepayScheduleExample();
                    List<String> status = new ArrayList<>();
                    status.add(LoanStatus.REPAY_SCHEDULE_STATUS_REPAIED.getCode());
                    status.add(LoanStatus.REPAY_SCHEDULE_STATUS_LATE_REPAIED.getCode());
                    scheduleExample.createCriteria().andLoanIdEqualTo(loanList.get(0).getId()).andPartnerRepayIdEqualTo(repayment.getRepayId()).andStatusNotIn(status);
                    List<LnRepaySchedule> repayScheduleList = scheduleMapper.selectByExample(scheduleExample);

                    //判断是否已还过款
                    if (CollectionUtils.isEmpty(repayScheduleList)) {
                        throw new PTMessageException(PTMessageEnum.ZAN_REPAY_PAYMENT_ORDER_DUPLICATE, "[" + repayment.getRepayId() + "]");
                    }

                    //查询还款计划表对应的还款计划明细
                    LnRepayScheduleDetailExample detailExample = new LnRepayScheduleDetailExample();
                    detailExample.createCriteria().andPlanIdEqualTo(repayScheduleList.get(0).getId());
                    List<LnRepayScheduleDetail> detailList = scheduleDetailMapper.selectByExample(detailExample);

                    log.info("=====================================实际还款与还款计划详情比较===============================");
                    //判断还款的每一项是否与还款计划详情一致
                    for (LnRepayScheduleDetail scheduleDetail : detailList) {
                        if (scheduleDetail.getSubjectCode().equals(LoanSubjects.SUBJECT_CODE_PRINCIPAL.getCode())
                                && MoneyUtil.multiply(scheduleDetail.getPlanAmount(), 100).longValue() != repayment.getPrincipal().longValue()) {
                            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "本金应还金额错误");
                        }

                        if (scheduleDetail.getSubjectCode().equals(LoanSubjects.SUBJECT_CODE_INTEREST.getCode())
                                && MoneyUtil.multiply(scheduleDetail.getPlanAmount(), 100).longValue() != repayment.getInterest().longValue()) {
                            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "利息应还金额错误");
                        }

                        //手续费暂不需要校验
//                        if (scheduleDetail.getSubjectCode().equals(LoanSubjects.SUBJECT_CODE_SERVICE_FEE.getCode())
//                                && MoneyUtil.multiply(scheduleDetail.getPlanAmount(), 100).longValue() != repayment.getServiceFee().longValue()) {
//                            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "手续费应还金额错误");
//                        }

                        if (scheduleDetail.getSubjectCode().equals(LoanSubjects.SUBJECT_CODE_SUPERVISE_FEE.getCode())
                                && MoneyUtil.multiply(scheduleDetail.getPlanAmount(), 100).longValue() != repayment.getSuperviseFee().longValue()) {
                            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "监管费应还金额错误");
                        }

                        if (scheduleDetail.getSubjectCode().equals(LoanSubjects.SUBJECT_CODE_INFO_SERVICE_FEE.getCode())
                                && MoneyUtil.multiply(scheduleDetail.getPlanAmount(), 100).longValue() != repayment.getInfoServiceFee().longValue()) {
                            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "信息服务费应还金额错误");
                        }

                        if (scheduleDetail.getSubjectCode().equals(LoanSubjects.SUBJECT_CODE_ACCOUNT_MANAGE_FEE.getCode())
                                && MoneyUtil.multiply(scheduleDetail.getPlanAmount(), 100).longValue() != repayment.getAccountManageFee().longValue()) {
                            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "账户管理费应还金额错误");
                        }

                        if (scheduleDetail.getSubjectCode().equals(LoanSubjects.SUBJECT_CODE_PREMIUM.getCode())
                                && MoneyUtil.multiply(scheduleDetail.getPlanAmount(), 100).longValue() != repayment.getPremium().longValue()) {
                            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "保费应还金额错误");
                        }
                        //TODO 改为告警
//                        if (MoneyUtil.multiply(loanUserService.calLateFee(repayScheduleList.get(0).getLoanId(), repayScheduleList.get(0).getSerialId()), 100).longValue() != repayment.getLateFee().longValue()) {
//                            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "滞纳金应还金额错误");
//                        }
                    }

                    //记录ln_repay表，状态为还款中
                    LnRepay repay = new LnRepay();
                    repay.setRepayPlanId(repayScheduleList.get(0).getId());
                    repay.setStatus(LoanStatus.REPAY_STATUS_PAYING.getCode());
                    repay.setUpdateTime(new Date());
                    //在修改状态的方法中会置
                    repay.setBgwOrderNo(Util.generateSysOrderNo(Constants.BGW_ORDER_NUMBER_GENERATE_WITHHOLD));
                    repay.setCreateTime(new Date());
                    repay.setBgwBindId(req.getBindId());
                    repay.setDoneTotal(MoneyUtil.divide(repayment.getTotal().toString(), "100").doubleValue());
                    repay.setLnUserId(lnBindCard.getLnUserId());
                    repay.setPayOrderNo(payOrderNo);
                    repay.setLoanId(loanList.get(0).getId());
                    repay.setPartnerOrderNo(req.getOrderNo());
                    repay.setInformStatus(LoanStatus.NOTICE_STATUS_INIT.getCode());

                    repayMapper.insertSelective(repay);
                    log.info("==========================还款信息表:" + JSONObject.fromObject(repay).toString());

                    repayIds.add(repay.getId());
                    //记录还款计划明细表ln_repay_detail
                    LnRepayDetail lnRepayDetail = new LnRepayDetail();
                    lnRepayDetail.setUpdateTime(new Date());
                    lnRepayDetail.setCreateTime(new Date());
                    lnRepayDetail.setRepayId(repay.getId());
                    lnRepayDetail.setDoneAmount(MoneyUtil.divide(repayment.getPrincipal(), 100).doubleValue());
                    lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_PRINCIPAL.getCode());
                    detailMapper.insertSelective(lnRepayDetail);
                    //利息
                    lnRepayDetail.setDoneAmount(MoneyUtil.divide(repayment.getInterest(), 100).doubleValue());
                    lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_INTEREST.getCode());
                    detailMapper.insertSelective(lnRepayDetail);
                    //监管费
                    lnRepayDetail.setDoneAmount(repayment.getSuperviseFee() != null ? MoneyUtil.divide(repayment.getSuperviseFee(), 100).doubleValue() : 0);
                    lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_SUPERVISE_FEE.getCode());
                    detailMapper.insertSelective(lnRepayDetail);
                    //信息服务费
                    lnRepayDetail.setDoneAmount(MoneyUtil.divide(repayment.getInfoServiceFee(), 100).doubleValue());
                    lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_INFO_SERVICE_FEE.getCode());
                    detailMapper.insertSelective(lnRepayDetail);
                    //账户管理费
                    lnRepayDetail.setDoneAmount(MoneyUtil.divide(repayment.getAccountManageFee(), 100).doubleValue());
                    lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_ACCOUNT_MANAGE_FEE.getCode());
                    detailMapper.insertSelective(lnRepayDetail);
                    //保费
                    lnRepayDetail.setDoneAmount(MoneyUtil.divide(repayment.getPremium(), 100).doubleValue());
                    lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_PREMIUM.getCode());
                    detailMapper.insertSelective(lnRepayDetail);
                    //其他
                    lnRepayDetail.setDoneAmount(MoneyUtil.divide(repayment.getOther(), 100).doubleValue());
                    lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_OTHER.getCode());
                    detailMapper.insertSelective(lnRepayDetail);

                    //滞纳金
                    lnRepayDetail.setDoneAmount(MoneyUtil.divide(repayment.getLateFee(), 100).doubleValue());
                    lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_LATE_FEE.getCode());
                    detailMapper.insertSelective(lnRepayDetail);

                }

                //根据借款支付订单号，查询子账户id
                LnPayOrdersExample ordersExample = new LnPayOrdersExample();
                ordersExample.createCriteria().andOrderNoEqualTo(loanList.get(0).getPayOrderNo());
                List<LnPayOrders> ordersList = payOrdersMapper.selectByExample(ordersExample);

                //记录ln_pay_orders,ln_pay_orders_jnl表
                LnPayOrders lnPayOrders = new LnPayOrders();
                lnPayOrders.setCreateTime(new Date());
                lnPayOrders.setAccountType(1);
                lnPayOrders.setAmount(MoneyUtil.divide(req.getTotalAmount(), "100").doubleValue());
                lnPayOrders.setBankCardNo(lnBindCard.getBankCard());
                BsCardBin bsCardBin = bankCardService.queryBankBin(lnBindCard.getBankCard());
                lnPayOrders.setBankId(bsCardBin.getBankId());
                lnPayOrders.setBankName(lnBindCard.getBankName());
                lnPayOrders.setChannel(Constants.CHANNEL_BAOFOO);
                lnPayOrders.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DK.getCode());
                lnPayOrders.setIdCard(lnBindCard.getIdCard());
                lnPayOrders.setLnUserId(lnBindCard.getLnUserId());
                lnPayOrders.setMobile(lnBindCard.getMobile());
                lnPayOrders.setMoneyType(0);
                lnPayOrders.setOrderNo(payOrderNo);
                lnPayOrders.setPartnerCode(req.getChannel());
                lnPayOrders.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_INIT.getCode()));
                lnPayOrders.setSubAccountId(ordersList.get(0).getSubAccountId());
                lnPayOrders.setTransType(LoanStatus.TRANS_TYPE_REPAY.getCode());
                lnPayOrders.setUpdateTime(new Date());
                lnPayOrders.setUserName(lnBindCard.getUserName());
                //查询支付渠道代扣优先商户号信息
            	BsPaymentChannel channelInfo = paymentChannelMapper.selectFisrtDK();
            	if(channelInfo != null){
            		//修改订单表payment_channel_id
            		lnPayOrders.setPaymentChannelId(channelInfo.getId());
            	}
                payOrdersMapper.insertSelective(lnPayOrders);
                log.info("===========================还款订单表插入记录：" + JSONObject.fromObject(lnPayOrders).toString());

                LnPayOrdersJnl lnPayOrdersJnl = new LnPayOrdersJnl();
                lnPayOrdersJnl.setSubAccountId(ordersList.get(0).getSubAccountId());
                lnPayOrdersJnl.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DK.getCode());
                lnPayOrdersJnl.setCreateTime(new Date());
                lnPayOrdersJnl.setOrderId(lnPayOrders.getId());
                lnPayOrdersJnl.setOrderNo(lnPayOrders.getOrderNo());
                lnPayOrdersJnl.setTransAmount(lnPayOrders.getAmount());
                lnPayOrdersJnl.setTransCode(LoanStatus.ORDERS_JNL_STATUS_INIT.getCode());
                lnPayOrdersJnl.setUserId(lnBindCard.getLnUserId());
                lnPayOrdersJnl.setSysTime(new Date());

                payOrdersJnlMapper.insertSelective(lnPayOrdersJnl);

                //发送短信
                String sendCode = null;
                String returnMsg = null;
                try {
                    sendCode = smsService.sendZanRepayPreToken(lnBindCard.getMobile());
                }catch (Exception e){
                    log.error("赞分期预还款发送短信异常",e);
                    sendCode = Constants.SEND_CODE_ERROR;
                    if(e instanceof PTMessageException){
                        returnMsg = ((PTMessageException) e).getErrMessage();
                    }

                    LnPayOrders ordersTemp = new LnPayOrders();
                    ordersTemp.setId(lnPayOrders.getId());
                    ordersTemp.setUpdateTime(new Date());
                    ordersTemp.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_PRE_FAIL.getCode()));
                    ordersTemp.setReturnMsg("通讯失败");
                    payOrdersMapper.updateByPrimaryKeySelective(ordersTemp);
                    //记录订单流水
                    lnPayOrdersJnl = new LnPayOrdersJnl();
                    lnPayOrdersJnl.setSubAccountId(ordersList.get(0).getSubAccountId());
                    lnPayOrdersJnl.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DK.getCode());
                    lnPayOrdersJnl.setCreateTime(new Date());
                    lnPayOrdersJnl.setOrderId(lnPayOrders.getId());
                    lnPayOrdersJnl.setOrderNo(lnPayOrders.getOrderNo());
                    lnPayOrdersJnl.setTransAmount(MoneyUtil.divide(req.getTotalAmount(), "100").doubleValue());
                    lnPayOrdersJnl.setTransCode(LoanStatus.ORDERS_JNL_STATUS_PRE_FAIL.getCode());
                    lnPayOrdersJnl.setUserId(lnBindCard.getLnUserId());
                    lnPayOrdersJnl.setSysTime(new Date());
                    payOrdersJnlMapper.insertSelective(lnPayOrdersJnl);

                    modifyRepayStatus(repayIds, false);
                    throw new PTMessageException(PTMessageEnum.CONNECTION_ERROR);
                }

                LnPayOrders ordersTemp = new LnPayOrders();
                ordersTemp.setId(lnPayOrders.getId());
                ordersTemp.setUpdateTime(new Date());
                if(Constants.SEND_CODE_ERROR.equals(sendCode)){
                    //更新订单状态
                    ordersTemp.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_PRE_FAIL.getCode()));
                    ordersTemp.setReturnMsg(returnMsg == null ? "短信发送失败" : returnMsg);
                    payOrdersMapper.updateByPrimaryKeySelective(ordersTemp);
                    //记录订单流水
                    lnPayOrdersJnl = new LnPayOrdersJnl();
                    lnPayOrdersJnl.setSubAccountId(ordersList.get(0).getSubAccountId());
                    lnPayOrdersJnl.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DK.getCode());
                    lnPayOrdersJnl.setCreateTime(new Date());
                    lnPayOrdersJnl.setOrderId(lnPayOrders.getId());
                    lnPayOrdersJnl.setOrderNo(lnPayOrders.getOrderNo());
                    lnPayOrdersJnl.setTransAmount(MoneyUtil.divide(req.getTotalAmount(), "100").doubleValue());
                    lnPayOrdersJnl.setTransCode(LoanStatus.ORDERS_JNL_STATUS_PRE_FAIL.getCode());
                    lnPayOrdersJnl.setUserId(lnBindCard.getLnUserId());
                    lnPayOrdersJnl.setSysTime(new Date());

                    payOrdersJnlMapper.insertSelective(lnPayOrdersJnl);
                    //修改还款信息表状态
                    modifyRepayStatus(repayIds, false);
                    throw new PTMessageException(PTMessageEnum.USER_ORDER_PRE_FAIL, ordersTemp.getReturnMsg());
                }else {
                    //更新订单状态
                    ordersTemp.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_PRE_SUCCESS.getCode()));
                    payOrdersMapper.updateByPrimaryKeySelective(ordersTemp);
                    //记录订单流水
                    lnPayOrdersJnl = new LnPayOrdersJnl();
                    lnPayOrdersJnl.setSubAccountId(ordersList.get(0).getSubAccountId());
                    lnPayOrdersJnl.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DK.getCode());
                    lnPayOrdersJnl.setCreateTime(new Date());
                    lnPayOrdersJnl.setOrderId(lnPayOrders.getId());
                    lnPayOrdersJnl.setOrderNo(lnPayOrders.getOrderNo());
                    lnPayOrdersJnl.setTransAmount(MoneyUtil.divide(req.getTotalAmount(), "100").doubleValue());
                    lnPayOrdersJnl.setTransCode(LoanStatus.ORDERS_JNL_STATUS_PRE_SUCCESS.getCode());
                    lnPayOrdersJnl.setUserId(lnBindCard.getLnUserId());
                    lnPayOrdersJnl.setSysTime(new Date());

                    payOrdersJnlMapper.insertSelective(lnPayOrdersJnl);
                    //修改还款信息表状态，并返回币赞订单号
                    return modifyRepayStatus(repayIds, true);
                }
            } else {
                throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "还款数据列表为空");
            }
        } else {
            throw new PTMessageException(PTMessageEnum.ZAN_LOAN_USER_CARD_UNBIND);
        }

    }

    /**
     * 修改还款信息表状态
     *
     * @param repayIds
     * @param success
     */
    private String modifyRepayStatus(List<Integer> repayIds, boolean success) {

        LnRepay repayTemp = new LnRepay();
        if (success) {
            repayTemp.setStatus(LoanStatus.REPAY_STATUS_PAYING.getCode());
            repayTemp.setBgwOrderNo(Util.generateSysOrderNo(Constants.BGW_ORDER_NUMBER_GENERATE_WITHHOLD));
        } else {
            repayTemp.setStatus(LoanStatus.REPAY_STATUS_FAIL.getCode());
        }

        for (Integer id : repayIds) {
            repayTemp.setId(id);
            repayTemp.setUpdateTime(new Date());
            repayMapper.updateByPrimaryKeySelective(repayTemp);
        }

        return repayTemp.getBgwOrderNo();
    }

    @Override
    public void repayConfirm(G2BReqMsg_Repay_RepayConfirm req) throws Exception {

    	String idCardNo = "";
    	LnRepayExample repayExample = new LnRepayExample();
    	repayExample.createCriteria().andBgwOrderNoEqualTo(req.getBgwOrderNo()).andStatusEqualTo(LoanStatus.REPAY_STATUS_PAYING.getCode());
        List<LnRepay> repayList = repayMapper.selectByExample(repayExample);
        List<LnPayOrders> ordersList = null;
        if(CollectionUtils.isNotEmpty(repayList)){
        	LnPayOrdersExample ordersExample = new LnPayOrdersExample();
            ordersExample.createCriteria().andOrderNoEqualTo(repayList.get(0).getPayOrderNo());
            ordersList = payOrdersMapper.selectByExample(ordersExample);
            idCardNo = CollectionUtils.isNotEmpty(ordersList)?ordersList.get(0).getIdCard() : "";
        }
        
        try {
            jsClientDaoSupport.lock(RedisLockEnum.LOCK_REPAY.getKey()+idCardNo);
            log.info("=========赞分期确认还款，加锁："+RedisLockEnum.LOCK_REPAY.getKey()+idCardNo+"=======");
            if (CollectionUtils.isEmpty(repayList)) {
                throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "无预下单成功的还款记录");
            }
            if(CollectionUtils.isEmpty(ordersList)){
                throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "无预下单的订单记录");
            }
            LnPayOrders order = ordersList.get(0);
            if(order.getStatus() == Integer.parseInt(LoanStatus.ORDERS_STATUS_SUCCESS.getCode()) ||
                    order.getStatus() == Integer.parseInt(LoanStatus.ORDERS_STATUS_PAYING.getCode())){
                //订单已成功或处理中时，直接返回
                return;
            }else if(order.getStatus() != Integer.parseInt(LoanStatus.ORDERS_STATUS_PRE_SUCCESS.getCode())){
                //订单非成功非处理中，非预下单成功时，返回错误信息
                String errorMsg = order.getReturnMsg();
                throw new PTMessageException(PTMessageEnum.TRANS_ERROR, "：" + errorMsg);
            }

            for (LnRepay repay : repayList) {
                LnRepayExample repayedExample = new LnRepayExample();
                repayedExample.createCriteria().andStatusEqualTo(LoanStatus.REPAY_STATUS_REPAIED.getCode()).andRepayPlanIdEqualTo(repay.getRepayPlanId());
                //查询还款记录，判断此笔下单是否包含已还款的记录
                List<LnRepay> repayedList = repayMapper.selectByExample(repayedExample);
                if (!CollectionUtils.isEmpty(repayedList)) {
                    throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "此笔还款下单包含已还款的记录");
                }
            }
            //查询用户绑卡id
            LnBindCardExample bindCardExample = new LnBindCardExample();
            bindCardExample.createCriteria().andBgwBindIdEqualTo(repayList.get(0).getBgwBindId());
            List<LnBindCard> bindCardList = bindCardMapper.selectByExample(bindCardExample);

            //预下单短信验证码校验
            Boolean isSuc = smsService.validateIdentity(order.getMobile(), req.getSmsCode());
            if(!isSuc){
                throw new PTMessageException(PTMessageEnum.MOBILE_CODE_WRONG_ERROR);
            }
            B2GReqMsg_BaoFooCutpayment_Cutpayment cutpayment = new B2GReqMsg_BaoFooCutpayment_Cutpayment();
            cutpayment.setTrans_serial_no(Util.generateUserOrderNo4Pay19(order.getLnUserId()));
            cutpayment.setTrans_id(order.getOrderNo());
            //云贷宝付代扣交易金额入参需要转成分，赞分期就是以分为单位，赞分期无需改动
            cutpayment.setTxnAmt(MoneyUtil.multiply(order.getAmount(), 100).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            cutpayment.setAcc_no(order.getBankCardNo());
            cutpayment.setId_card(order.getIdCard());
            cutpayment.setId_holder(order.getUserName());
            cutpayment.setMobile(order.getMobile());
            //通过银行bank_id得到银行编码
            Bs19payBankExample example = new Bs19payBankExample();
            example.createCriteria().andBankIdEqualTo(order.getBankId()).andChannelEqualTo(Constants.ORDER_CHANNEL_BAOFOO);
            List<Bs19payBank> bs19payBank = bs19payBankMapper.selectByExample(example);

            cutpayment.setPay_code(bs19payBank.get(0).getPay19BankCode());
            cutpayment.setAdditional_info(PartnerEnum.getEnumByCode(order.getPartnerCode()).getName() + "用户代扣还款");
            //查询支付渠道代扣优先商户号信息
            BsPaymentChannel channelInfo = paymentChannelMapper.selectFisrtDK();
            if(channelInfo != null){
            	cutpayment.setMerchantNo(channelInfo.getMerchantNo());
            	cutpayment.setIsMain(channelInfo.getIsMain());
            	//修改订单表payment_channel_id
            	payOrdersService.updatePaymentChannelId(order.getId(), channelInfo.getId(),null);
            }
            B2GResMsg_BaoFooCutpayment_Cutpayment res = null;
            try {
                res = baoFooTransportService.withholding(cutpayment);
            } catch (Exception e) {
                e.printStackTrace();
                throw new PTMessageException(PTMessageEnum.CONNECTION_ERROR);
            }

            LnRepay repayTemp = new LnRepay();
            repayTemp.setUpdateTime(new Date());

            if (!res.getResCode().equals(ConstantUtil.BSRESCODE_SUCCESS)) {

                if (res.getResCode().equals(LoanStatus.BAOFOO_PAY_STATUS_PROCESS.getCode())) {
                    //更新还款信息表状态 预下单时已置为还款中状态
                        /*for (LnRepay repay : repayList) {
                            repayTemp.setId(repay.getId());
                            repayTemp.setStatus(LoanStatus.REPAY_STATUS_PAYING.getCode());
                            repayMapper.updateByPrimaryKeySelective(repayTemp);
                        }*/
                    //更新订单表状态
                    payOrdersService.modifyLnOrderStatus4Safe(order.getId(), Integer.parseInt(LoanStatus.ORDERS_STATUS_PAYING.getCode()),
                            null, res.getResCode(), res.getResMsg());

                    //放到redis中
                    LoanNoticeVO vo = new LoanNoticeVO();
                    vo.setPayOrderNo(order.getOrderNo());
                    vo.setChannel(order.getChannel());
                    vo.setChannelTransType(order.getChannelTransType());
                    vo.setTransType(order.getTransType());
                    vo.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_PAYING.getCode()));
                    vo.setAmount(MoneyUtil.defaultRound(order.getAmount()).toString());
                    redisUtil.rpushRedis(vo);

                    //并插入到消息队列表中
                    BsPayResultQueue queue = new BsPayResultQueue();
                    queue.setChannel(Constants.ORDER_CHANNEL_BAOFOO);
                    queue.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DK.getCode());
                    queue.setCreateTime(new Date());
                    queue.setDealNum(0);
                    queue.setOrderNo(order.getOrderNo());
                    queue.setStatus(LoanStatus.NOTICE_STATUS_INIT.getCode());
                    queue.setTransType(order.getTransType());
                    queue.setUpdateTime(new Date());
                    queueMapper.insertSelective(queue);

                    LnPayOrdersJnl lnPayOrdersJnl = new LnPayOrdersJnl();
                    //记录订单明细流水表
                    lnPayOrdersJnl.setSubAccountId(order.getSubAccountId());
                    lnPayOrdersJnl.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DK.getCode());
                    lnPayOrdersJnl.setCreateTime(new Date());
                    lnPayOrdersJnl.setOrderId(order.getId());
                    lnPayOrdersJnl.setOrderNo(order.getOrderNo());
                    lnPayOrdersJnl.setTransAmount(order.getAmount());
                    lnPayOrdersJnl.setTransCode(Constants.ORDER_TRANS_CODE_PROCESS);
                    lnPayOrdersJnl.setUserId(order.getLnUserId());
                    lnPayOrdersJnl.setSysTime(new Date());

                    payOrdersJnlMapper.insertSelective(lnPayOrdersJnl);

                } else {
                	
                    //还款失败
                    //更新订单表状态
                    payOrdersService.modifyLnOrderStatus4Safe(order.getId(), Integer.parseInt(LoanStatus.ORDERS_STATUS_PAYING.getCode()),
                            null, res.getResCode(), res.getResMsg());
                    try {
                        OrderResultInfo resultInfo = new OrderResultInfo();
                        resultInfo.setSuccess(false);
                        resultInfo.setReturnCode(res.getResCode());
                        resultInfo.setOrderNo(order.getOrderNo());
                        resultInfo.setAmount(order.getAmount());
                        resultInfo.setReturnMsg(res.getResMsg());
                        HashMap<String, Object> extendMap = new HashMap<String, Object>();
                        extendMap.put("throwFlag", true);//失败时，是否同步抛错标识
                        resultInfo.setExtendMap(extendMap);
                        orderBusinessService.loanerRepay(resultInfo);
                    } catch (Exception e) {
                        e.printStackTrace();
                        if(e instanceof PTMessageException){
                            String errorCode = ((PTMessageException) e).getErrCode();
                            String errorMsg = ((PTMessageException) e).getErrMessage();
                            if(PTMessageEnum.ZAN_REPAY_FAIL.getCode().equals(errorCode)){
                                throw new PTMessageException(PTMessageEnum.ZAN_REPAY_FAIL, StringUtil.substring(errorMsg, 5));
                            }
                        }
                    }

                }

            } else {
                //还款成功
            	if(channelInfo != null && channelInfo.getIsMain() != null && channelInfo.getIsMain() !=1 ){
            		//使用的是辅助通道
            		if(StringUtil.isBlank(res.getPay4OnlineOrderNo())){
            			//钱包转账支付订单号为空，告警
            			specialJnlService.warn4FailNoSMS(order.getAmount(),"代扣商户间钱包转账失败，代扣订单号："+order.getOrderNo(),order.getOrderNo(),"【代扣商户间转账】");
            		}else{
	            		//钱包转账支付订单号不为空，记钱包转账支付的订单及流水
                		LnPayOrders lnPayOrders = new LnPayOrders();
                        lnPayOrders.setCreateTime(new Date());
                        lnPayOrders.setAccountType(2);//转账算系统，记2
                        lnPayOrders.setAmount(order.getAmount());
                        lnPayOrders.setChannel(Constants.CHANNEL_BAOFOO);
                        lnPayOrders.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_TRANSFER.getCode());
                        lnPayOrders.setMoneyType(0);
                        lnPayOrders.setOrderNo(res.getPay4OnlineOrderNo());
                        lnPayOrders.setPartnerCode(PartnerEnum.ZAN.getCode());
                        lnPayOrders.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_SUCCESS.getCode()));
                        lnPayOrders.setSubAccountId(order.getSubAccountId());
                        lnPayOrders.setTransType(LoanStatus.TRANS_TYPE_REPAY.getCode());
                        lnPayOrders.setUpdateTime(new Date());
                        lnPayOrders.setPaymentChannelId(channelInfo.getId());
                        payOrdersMapper.insertSelective(lnPayOrders);
                        //记录订单流水表
                		LnPayOrdersJnl lnPayOrdersJnl = new LnPayOrdersJnl();
                        lnPayOrdersJnl.setSubAccountId(order.getSubAccountId());
                        lnPayOrdersJnl.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_TRANSFER.getCode());
                        lnPayOrdersJnl.setCreateTime(new Date());
                        lnPayOrdersJnl.setOrderId(lnPayOrders.getId());
                        lnPayOrdersJnl.setOrderNo(lnPayOrders.getOrderNo());
                        lnPayOrdersJnl.setTransAmount(lnPayOrders.getAmount());
                        lnPayOrdersJnl.setTransCode(Constants.ORDER_TRANS_CODE_SUCCESS);
                        lnPayOrdersJnl.setUserId(lnPayOrders.getLnUserId());
                        lnPayOrdersJnl.setSysTime(new Date());
                        payOrdersJnlMapper.insertSelective(lnPayOrdersJnl);
            		}
            	}
                //更新订单表状态
                payOrdersService.modifyLnOrderStatus4Safe(order.getId(), Integer.parseInt(LoanStatus.ORDERS_STATUS_PAYING.getCode()),
                        null, res.getResCode(), res.getResMsg());

                //放到redis中
                LoanNoticeVO vo = new LoanNoticeVO();
                vo.setPayOrderNo(order.getOrderNo());
                vo.setChannel(order.getChannel());
                vo.setChannelTransType(order.getChannelTransType());
                vo.setTransType(order.getTransType());
                vo.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_SUCCESS.getCode()));
                vo.setAmount(MoneyUtil.defaultRound(order.getAmount()).toString());
                redisUtil.rpushRedis(vo);

                //并插入到消息队列表中
                BsPayResultQueue queue = new BsPayResultQueue();
                queue.setChannel(Constants.ORDER_CHANNEL_BAOFOO);
                queue.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DK.getCode());
                queue.setCreateTime(new Date());
                queue.setDealNum(0);
                queue.setOrderNo(order.getOrderNo());
                queue.setStatus(LoanStatus.NOTICE_STATUS_INIT.getCode());
                queue.setTransType(order.getTransType());
                queue.setUpdateTime(new Date());
                queueMapper.insertSelective(queue);
            }
        } finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_REPAY.getKey()+idCardNo);
            log.info("=========赞分期确认还款，解锁："+RedisLockEnum.LOCK_REPAY.getKey()+idCardNo+"=======");
        }
    }

    @Override
    public String withholdingRepay(G2BReqMsg_Repay_WithholdingRepay req) throws Exception {

        try {
            LnUser lnUser = loanUserService.queryLoanUserExist(req.getUserId(), PartnerEnum.ZAN.getCode());
            if(lnUser == null){
                throw new PTMessageException(PTMessageEnum.ZAN_ADDLOANCIF_USER_NOT_EXIST);
            }else{
                //白名单
                if (!loanUserMobileWhiteListCheckService.lnMobileWhiteListCheck(lnUser.getMobile())) {
                    throw new PTMessageException(PTMessageEnum.ZAN_LOAN_USER_NOT_FOUND_IN_WHITE);
                }
            }
            LnRepayExample repayExample = new LnRepayExample();
            repayExample.createCriteria().andPartnerOrderNoEqualTo(req.getOrderNo());
            List<LnRepay> repayList = repayMapper.selectByExample(repayExample);
            if (CollectionUtils.isNotEmpty(repayList) && repayList.size() > 0) {
                LnRepay repay = repayList.get(0);
                LnPayOrdersExample ordersExample = new LnPayOrdersExample();
                ordersExample.createCriteria().andOrderNoEqualTo(repay.getPayOrderNo());
                List<LnPayOrders> orders = payOrdersMapper.selectByExample(ordersExample);
                if(CollectionUtils.isEmpty(orders)){
                    throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "无订单记录");
                }
                LnPayOrders order = orders.get(0);
                if(order.getStatus() == Integer.parseInt(LoanStatus.ORDERS_STATUS_SUCCESS.getCode()) ||
                        order.getStatus() == Integer.parseInt(LoanStatus.ORDERS_STATUS_PAYING.getCode())){
                    //订单已成功或处理中时，直接返回
                    return repay.getBgwOrderNo();
                }else{
                    //订单非成功非处理中时，返回错误信息
                    String errorMsg = order.getReturnMsg();
                    throw new PTMessageException(PTMessageEnum.TRANS_ERROR, "：" + errorMsg);
                }
            }

            //检查绑卡表，如果客户对应的卡未绑定，则返回相应错误
            LnBindCard lnBindCard = loanUserService.queryLoanBindCardExist(req.getUserId(), req.getBindId(), req.getChannel());
            if (lnBindCard == null) {
                throw new PTMessageException(PTMessageEnum.ZAN_LOAN_USER_CARD_UNBIND);
            }

            if (CollectionUtils.isNotEmpty(req.getRepayments()) && req.getRepayments().size() > 0) {

                //查询借款信息
                LnLoanExample loanExample = new LnLoanExample();
                loanExample.createCriteria().andPartnerLoanIdEqualTo(req.getLoanId()).andStatusEqualTo(LoanStatus.LOAN_STATUS_PAIED.getCode());
                List<LnLoan> loanList = loanMapper.selectByExample(loanExample);
                if (CollectionUtils.isEmpty(loanList) || loanList.size() == 0) {
                    throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND, "找不到借款信息");
                }


                //支付订单号
                String payOrderNo = Util.generateOrderNo4BaoFoo(8);
                //记录还款信息表id
                List<Integer> repayIds = new ArrayList<>();

                String bgwOrderNo = null;
                RepayQueueDTO repayQueueDTO = new RepayQueueDTO();
                for (Repayment repayment : req.getRepayments()) {
                    log.info("==========================还款明细:" + JSONObject.fromObject(repayment).toString());
                    //查询对应的还款计划表
                    LnRepayScheduleExample scheduleExample = new LnRepayScheduleExample();
                    List<String> status = new ArrayList<>();
                    status.add(LoanStatus.REPAY_SCHEDULE_STATUS_REPAIED.getCode());
                    status.add(LoanStatus.REPAY_SCHEDULE_STATUS_LATE_REPAIED.getCode());
                    scheduleExample.createCriteria().andLoanIdEqualTo(loanList.get(0).getId()).andPartnerRepayIdEqualTo(repayment.getRepayId()).andStatusNotIn(status);
                    List<LnRepaySchedule> repayScheduleList = scheduleMapper.selectByExample(scheduleExample);

                    //判断是否已还过款
                    if (CollectionUtils.isEmpty(repayScheduleList)) {
                        throw new PTMessageException(PTMessageEnum.ZAN_REPAY_PAYMENT_ORDER_DUPLICATE, "[" + repayment.getRepayId() + "]");
                    }

                    //查询还款计划表对应的还款计划明细
                    LnRepayScheduleDetailExample detailExample = new LnRepayScheduleDetailExample();
                    detailExample.createCriteria().andPlanIdEqualTo(repayScheduleList.get(0).getId());
                    List<LnRepayScheduleDetail> detailList = scheduleDetailMapper.selectByExample(detailExample);

                    log.info("=====================================实际还款与还款计划详情比较===============================");
                    //判断还款的每一项是否与还款计划详情一致
                    for (LnRepayScheduleDetail scheduleDetail : detailList) {
                        if (scheduleDetail.getSubjectCode().equals(LoanSubjects.SUBJECT_CODE_PRINCIPAL.getCode())
                                && MoneyUtil.multiply(scheduleDetail.getPlanAmount(), 100).longValue() != repayment.getPrincipal().longValue()) {
                            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "本金应还金额错误");
                        }

                        if (scheduleDetail.getSubjectCode().equals(LoanSubjects.SUBJECT_CODE_INTEREST.getCode())
                                && MoneyUtil.multiply(scheduleDetail.getPlanAmount(), 100).longValue() != repayment.getInterest().longValue()) {
                            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "利息应还金额错误");
                        }

                        if (scheduleDetail.getSubjectCode().equals(LoanSubjects.SUBJECT_CODE_SUPERVISE_FEE.getCode())
                                && MoneyUtil.multiply(scheduleDetail.getPlanAmount(), 100).longValue() != repayment.getSuperviseFee().longValue()) {
                            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "监管费应还金额错误");
                        }

                        if (scheduleDetail.getSubjectCode().equals(LoanSubjects.SUBJECT_CODE_INFO_SERVICE_FEE.getCode())
                                && MoneyUtil.multiply(scheduleDetail.getPlanAmount(), 100).longValue() != repayment.getInfoServiceFee().longValue()) {
                            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "信息服务费应还金额错误");
                        }

                        if (scheduleDetail.getSubjectCode().equals(LoanSubjects.SUBJECT_CODE_ACCOUNT_MANAGE_FEE.getCode())
                                && MoneyUtil.multiply(scheduleDetail.getPlanAmount(), 100).longValue() != repayment.getAccountManageFee().longValue()) {
                            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "账户管理费应还金额错误");
                        }

                        if (scheduleDetail.getSubjectCode().equals(LoanSubjects.SUBJECT_CODE_PREMIUM.getCode())
                                && MoneyUtil.multiply(scheduleDetail.getPlanAmount(), 100).longValue() != repayment.getPremium().longValue()) {
                            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "保费应还金额错误");
                        }
                    }

                    bgwOrderNo = Util.generateSysOrderNo("BDK");
                    //记录ln_repay表，状态为还款中
                    LnRepay repay = new LnRepay();
                    repay.setBgwOrderNo(bgwOrderNo);
                    repay.setRepayPlanId(repayScheduleList.get(0).getId());
                    repay.setStatus(LoanStatus.REPAY_STATUS_PAYING.getCode());
                    repay.setUpdateTime(new Date());
                    repay.setCreateTime(new Date());
                    repay.setBgwBindId(req.getBindId());
                    repay.setDoneTotal(MoneyUtil.divide(repayment.getTotal().toString(), "100").doubleValue());
                    repay.setLnUserId(lnBindCard.getLnUserId());
                    repay.setPayOrderNo(payOrderNo);
                    repay.setLoanId(loanList.get(0).getId());
                    repay.setPartnerOrderNo(req.getOrderNo());
                    repay.setInformStatus(LoanStatus.NOTICE_STATUS_INIT.getCode());
                    repayMapper.insertSelective(repay);
                    log.info("==========================还款信息表:" + JSONObject.fromObject(repay).toString());

                    repayIds.add(repay.getId());
                    //记录还款计划明细表ln_repay_detail
                    LnRepayDetail lnRepayDetail = new LnRepayDetail();
                    lnRepayDetail.setUpdateTime(new Date());
                    lnRepayDetail.setCreateTime(new Date());
                    lnRepayDetail.setRepayId(repay.getId());
                    lnRepayDetail.setDoneAmount(MoneyUtil.divide(repayment.getPrincipal(), 100).doubleValue());
                    lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_PRINCIPAL.getCode());
                    detailMapper.insertSelective(lnRepayDetail);
                    //利息
                    lnRepayDetail.setDoneAmount(MoneyUtil.divide(repayment.getInterest(), 100).doubleValue());
                    lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_INTEREST.getCode());
                    detailMapper.insertSelective(lnRepayDetail);
                    //监管费
                    lnRepayDetail.setDoneAmount(repayment.getSuperviseFee() != null ? MoneyUtil.divide(repayment.getSuperviseFee(), 100).doubleValue() : 0);
                    lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_SUPERVISE_FEE.getCode());
                    detailMapper.insertSelective(lnRepayDetail);
                    //信息服务费
                    lnRepayDetail.setDoneAmount(MoneyUtil.divide(repayment.getInfoServiceFee(), 100).doubleValue());
                    lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_INFO_SERVICE_FEE.getCode());
                    detailMapper.insertSelective(lnRepayDetail);
                    //账户管理费
                    lnRepayDetail.setDoneAmount(MoneyUtil.divide(repayment.getAccountManageFee(), 100).doubleValue());
                    lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_ACCOUNT_MANAGE_FEE.getCode());
                    detailMapper.insertSelective(lnRepayDetail);
                    //保费
                    lnRepayDetail.setDoneAmount(MoneyUtil.divide(repayment.getPremium(), 100).doubleValue());
                    lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_PREMIUM.getCode());
                    detailMapper.insertSelective(lnRepayDetail);
                    //其他
                    lnRepayDetail.setDoneAmount(MoneyUtil.divide(repayment.getOther(), 100).doubleValue());
                    lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_OTHER.getCode());
                    detailMapper.insertSelective(lnRepayDetail);

                    //滞纳金
                    lnRepayDetail.setDoneAmount(MoneyUtil.divide(repayment.getLateFee(), 100).doubleValue());
                    lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_LATE_FEE.getCode());
                    detailMapper.insertSelective(lnRepayDetail);
                }

                //根据借款支付订单号，查询子账户id
                LnPayOrdersExample ordersExample = new LnPayOrdersExample();
                ordersExample.createCriteria().andOrderNoEqualTo(loanList.get(0).getPayOrderNo());
                List<LnPayOrders> ordersList = payOrdersMapper.selectByExample(ordersExample);

                //记录ln_pay_orders,ln_pay_orders_jnl表
                LnPayOrders lnPayOrders = new LnPayOrders();
                lnPayOrders.setCreateTime(new Date());
                lnPayOrders.setAccountType(1);
                lnPayOrders.setAmount(MoneyUtil.divide(req.getTotalAmount(), "100").doubleValue());

                lnPayOrders.setBankCardNo(lnBindCard.getBankCard());
                BsCardBin bsCardBin = bankCardService.queryBankBin(lnBindCard.getBankCard());
                lnPayOrders.setBankName(lnBindCard.getBankName());
                lnPayOrders.setIdCard(lnBindCard.getIdCard());
                lnPayOrders.setLnUserId(lnBindCard.getLnUserId());
                lnPayOrders.setMobile(lnBindCard.getMobile());
                lnPayOrders.setUserName(lnBindCard.getUserName());
                lnPayOrders.setBankId(bsCardBin.getBankId());
                lnPayOrders.setChannel(Constants.CHANNEL_BAOFOO);
                //渠道交易类型为代扣
                lnPayOrders.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DK.getCode());
                lnPayOrders.setMoneyType(0);
                lnPayOrders.setOrderNo(payOrderNo);
                lnPayOrders.setPartnerCode(req.getChannel());
                lnPayOrders.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_INIT.getCode()));
                lnPayOrders.setSubAccountId(ordersList.get(0).getSubAccountId());
                lnPayOrders.setTransType(LoanStatus.TRANS_TYPE_REPAY.getCode());
                lnPayOrders.setUpdateTime(new Date());

                payOrdersMapper.insertSelective(lnPayOrders);
                log.info("===========================还款订单表插入记录：" + JSONObject.fromObject(lnPayOrders).toString());

                LnPayOrdersJnl lnPayOrdersJnl = new LnPayOrdersJnl();
                lnPayOrdersJnl.setSubAccountId(ordersList.get(0).getSubAccountId());
                lnPayOrdersJnl.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DK.getCode());
                lnPayOrdersJnl.setCreateTime(new Date());
                lnPayOrdersJnl.setOrderId(lnPayOrders.getId());
                lnPayOrdersJnl.setOrderNo(lnPayOrders.getOrderNo());
                lnPayOrdersJnl.setTransAmount(lnPayOrders.getAmount());
                lnPayOrdersJnl.setTransCode(LoanStatus.ORDERS_JNL_STATUS_INIT.getCode());
                lnPayOrdersJnl.setUserId(lnBindCard.getLnUserId());
                lnPayOrdersJnl.setSysTime(new Date());

                payOrdersJnlMapper.insertSelective(lnPayOrdersJnl);

                // 存入redis
                try {
                    repayQueueDTO.setLnBindCard(lnBindCard);
                    repayQueueDTO.setLnPayOrder(lnPayOrders);
                    repayQueueDTO.setChannel(PartnerEnum.ZAN.getCode());
                    jsClientDaoSupport.rpush("repay_queue", JSON.toJSONString(repayQueueDTO));
                    log.info(">>>入还款队列数据:" + JSON.toJSONString(repayQueueDTO) + "<<<");
                }catch (Exception e){
                    log.error("入还款队列异常", e);
                }
                return bgwOrderNo;

            } else {
                throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "还款数据列表为空");
            }

        } finally {

        }
    }

    @Override
    public void withholdingRepaySendBaoFoo(LnPayOrders order, LnBindCard lnBindCard) {
        try {
            jsClientDaoSupport.lock(RedisLockEnum.LOCK_REPAY.getKey()+lnBindCard.getIdCard());
            log.info("=========赞分期发起代扣，加锁："+RedisLockEnum.LOCK_REPAY.getKey()+lnBindCard.getIdCard()+"=======");
            LnRepayExample lnRepayExample = new LnRepayExample();
            lnRepayExample.createCriteria().andPayOrderNoEqualTo(order.getOrderNo());
            List<LnRepay> repayList = repayMapper.selectByExample(lnRepayExample);
            // 前置校验
            if (CollectionUtils.isNotEmpty(repayList)) {
                LnPayOrders newestOrder = payOrdersMapper.selectByPrimaryKey(order.getId());
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

            LnUser user = lnUserMapper.selectByPrimaryKey(order.getLnUserId());

            //检查绑卡表，如果客户对应的卡未绑定，则返回相应错误
            lnBindCard = loanUserService.queryLoanBindCardExist(user.getPartnerUserId(), lnBindCard.getBgwBindId(), PartnerEnum.ZAN.getCode());
            if (lnBindCard == null) {
                throw new PTMessageException(PTMessageEnum.ZAN_LOAN_USER_CARD_UNBIND);
            }

            // 发起还款请求
            B2GReqMsg_BaoFooCutpayment_Cutpayment cutpayment = new B2GReqMsg_BaoFooCutpayment_Cutpayment();
            cutpayment.setTrans_serial_no(Util.generateUserOrderNo4Pay19(order.getLnUserId()));
            cutpayment.setTrans_id(order.getOrderNo());
            cutpayment.setTxnAmt(MoneyUtil.multiply(order.getAmount(), 100).toString()); // 单位是分
            cutpayment.setAcc_no(lnBindCard.getBankCard());
            cutpayment.setId_card(lnBindCard.getIdCard());
            cutpayment.setId_holder(lnBindCard.getUserName());
            cutpayment.setMobile(lnBindCard.getMobile());
            cutpayment.setPay_code(lnBindCard.getBankCode());
            cutpayment.setAdditional_info(PartnerEnum.getEnumByCode(user.getPartnerCode()).getName() + "用户代扣还款");
            //查询支付渠道代扣优先商户号信息
            BsPaymentChannel channelInfo = paymentChannelMapper.selectFisrtDK();
            if(channelInfo != null){
            	cutpayment.setMerchantNo(channelInfo.getMerchantNo());
            	cutpayment.setIsMain(channelInfo.getIsMain());
            	//修改订单表payment_channel_id
            	payOrdersService.updatePaymentChannelId(order.getId(), channelInfo.getId(), null);
            }
            B2GResMsg_BaoFooCutpayment_Cutpayment res = new B2GResMsg_BaoFooCutpayment_Cutpayment();
            try {
                res = baoFooTransportService.withholding(cutpayment);
            } catch (Exception e) {
                e.printStackTrace();
                res.setResCode(LoanStatus.BAOFOO_PAY_STATUS_PROCESS.getCode());
                res.setResMsg("通讯失败，置为处理中");
            }

            if (!res.getResCode().equals(ConstantUtil.BSRESCODE_SUCCESS)) {
                if (res.getResCode().equals(LoanStatus.BAOFOO_PAY_STATUS_PROCESS.getCode())) {
                    //更新订单表为处理中
                    payOrdersService.modifyLnOrderStatus4Safe(order.getId(), Integer.parseInt(LoanStatus.ORDERS_STATUS_PAYING.getCode()),
                            null, res.getResCode(), res.getResMsg());

                    LnPayOrdersJnl lnPayOrdersJnl = new LnPayOrdersJnl();
                    //记录订单明细流水表
                    lnPayOrdersJnl.setSubAccountId(order.getSubAccountId());
                    lnPayOrdersJnl.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DK.getCode());
                    lnPayOrdersJnl.setCreateTime(new Date());
                    lnPayOrdersJnl.setOrderId(order.getId());
                    lnPayOrdersJnl.setOrderNo(order.getOrderNo());
                    lnPayOrdersJnl.setTransAmount(order.getAmount());
                    lnPayOrdersJnl.setTransCode(LoanStatus.ORDERS_JNL_STATUS_PAYING.getCode());
                    lnPayOrdersJnl.setUserId(order.getLnUserId());
                    lnPayOrdersJnl.setSysTime(new Date());
                    payOrdersJnlMapper.insertSelective(lnPayOrdersJnl);

                    //放到redis中
                    LoanNoticeVO vo = new LoanNoticeVO();
                    vo.setPayOrderNo(order.getOrderNo());
                    vo.setChannel(order.getChannel());
                    vo.setChannelTransType(order.getChannelTransType());
                    vo.setTransType(order.getTransType());
                    vo.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_PAYING.getCode()));
                    vo.setAmount(MoneyUtil.defaultRound(order.getAmount()).toString());
                    redisUtil.rpushRedis(vo);

                    //并插入到消息队列表中
                    BsPayResultQueue queue = new BsPayResultQueue();
                    queue.setChannel(Constants.ORDER_CHANNEL_BAOFOO);
                    queue.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DK.getCode());
                    queue.setCreateTime(new Date());
                    queue.setDealNum(0);
                    queue.setOrderNo(order.getOrderNo());
                    queue.setStatus(LoanStatus.NOTICE_STATUS_INIT.getCode());
                    queue.setTransType(order.getTransType());
                    queue.setUpdateTime(new Date());
                    queueMapper.insertSelective(queue);
                } else {
                    //还款失败，更新订单表为处理中
                    payOrdersService.modifyLnOrderStatus4Safe(order.getId(), Integer.parseInt(LoanStatus.ORDERS_STATUS_PAYING.getCode()),
                            null, res.getResCode(), res.getResMsg());

                    try {
                        OrderResultInfo resultInfo = new OrderResultInfo();
                        resultInfo.setSuccess(false);
                        resultInfo.setReturnCode(res.getResCode());
                        resultInfo.setOrderNo(order.getOrderNo());
                        resultInfo.setAmount(order.getAmount());
                        resultInfo.setReturnMsg(res.getResMsg());
                        orderBusinessService.loanerRepay(resultInfo);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            } else {
            	if(channelInfo != null && channelInfo.getIsMain() != null && channelInfo.getIsMain() !=1 ){
            		//使用的是辅助通道
            		if(StringUtil.isBlank(res.getPay4OnlineOrderNo())){
            			//钱包转账支付订单号为空，告警
            			specialJnlService.warn4FailNoSMS(order.getAmount(),"代扣商户间钱包转账失败，代扣订单号："+order.getOrderNo(),order.getOrderNo(),"【代扣商户间转账】");
            		}else{
	            		LnPayOrders lnPayOrders = new LnPayOrders();
	                    lnPayOrders.setCreateTime(new Date());
	                    lnPayOrders.setAccountType(2);//转账算系统，记2
	                    lnPayOrders.setAmount(order.getAmount());
	                    lnPayOrders.setChannel(Constants.CHANNEL_BAOFOO);
	                    lnPayOrders.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_TRANSFER.getCode());
	                    lnPayOrders.setMoneyType(0);
	                    lnPayOrders.setOrderNo(res.getPay4OnlineOrderNo());
	                    lnPayOrders.setPartnerCode(PartnerEnum.ZAN.getCode());
	                    lnPayOrders.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_SUCCESS.getCode()));
	                    lnPayOrders.setSubAccountId(order.getSubAccountId());
	                    lnPayOrders.setTransType(LoanStatus.TRANS_TYPE_REPAY.getCode());
	                    lnPayOrders.setUpdateTime(new Date());
	                    lnPayOrders.setPaymentChannelId(channelInfo.getId());
	                    payOrdersMapper.insertSelective(lnPayOrders);
	                    //记录订单流水表
	            		LnPayOrdersJnl lnPayOrdersJnl = new LnPayOrdersJnl();
	                    lnPayOrdersJnl.setSubAccountId(order.getSubAccountId());
	                    lnPayOrdersJnl.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_TRANSFER.getCode());
	                    lnPayOrdersJnl.setCreateTime(new Date());
	                    lnPayOrdersJnl.setOrderId(lnPayOrders.getId());
	                    lnPayOrdersJnl.setOrderNo(lnPayOrders.getOrderNo());
	                    lnPayOrdersJnl.setTransAmount(lnPayOrders.getAmount());
	                    lnPayOrdersJnl.setTransCode(Constants.ORDER_TRANS_CODE_SUCCESS);
	                    lnPayOrdersJnl.setUserId(lnPayOrders.getLnUserId());
	                    lnPayOrdersJnl.setSysTime(new Date());
	                    payOrdersJnlMapper.insertSelective(lnPayOrdersJnl);
            		}
            	}
                //还款成功
                payOrdersService.modifyLnOrderStatus4Safe(order.getId(), Integer.parseInt(LoanStatus.ORDERS_STATUS_PAYING.getCode()),
                        null, res.getResCode(), res.getResMsg());

                //放到redis中
                LoanNoticeVO vo = new LoanNoticeVO();
                vo.setPayOrderNo(order.getOrderNo());
                vo.setChannel(order.getChannel());
                vo.setChannelTransType(order.getChannelTransType());
                vo.setTransType(order.getTransType());
                vo.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_SUCCESS.getCode()));
                vo.setAmount(MoneyUtil.defaultRound(order.getAmount()).toString());
                redisUtil.rpushRedis(vo);

                //并插入到消息队列表中
                BsPayResultQueue queue = new BsPayResultQueue();
                queue.setChannel(Constants.ORDER_CHANNEL_BAOFOO);
                queue.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DK.getCode());
                queue.setCreateTime(new Date());
                queue.setDealNum(0);
                queue.setOrderNo(order.getOrderNo());
                queue.setStatus(LoanStatus.NOTICE_STATUS_INIT.getCode());
                queue.setTransType(order.getTransType());
                queue.setUpdateTime(new Date());
                queueMapper.insertSelective(queue);
            }
        }finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_REPAY.getKey()+lnBindCard.getIdCard());
            log.info("=========赞分期发起代扣，解锁："+RedisLockEnum.LOCK_REPAY.getKey()+lnBindCard.getIdCard()+"=======");
        }
    }

    @Override
    @Transactional(noRollbackFor = PTMessageException.class)
	public void notifyRepay(final RepayResultInfo req) {

        //查询相关订单表
        LnPayOrdersExample lnPayOrdersExample = new LnPayOrdersExample();
        lnPayOrdersExample.createCriteria().andOrderNoEqualTo(req.getOrderId());
        List<LnPayOrders> orderList = payOrdersMapper.selectByExample(lnPayOrdersExample);
        if (CollectionUtils.isEmpty(orderList)) {
            specialJnlService.warn4Fail(null, "还款通知失败：订单号 " + req.getOrderId() + " 的订单不存在，需要您的确认，请检查", req.getOrderId(), "还款订单不存在", true);
            throw new PTMessageException(PTMessageEnum.PAYMENT_ORDER_NOT_EXIST);
        }
        LnPayOrders order = payOrdersMapper.selectByPKForLock(orderList.get(0).getId());

        //根据支付方订单号查询还款信息表
        LnRepayExample example = new LnRepayExample();
        example.createCriteria().andPayOrderNoEqualTo(req.getOrderId());
        List<LnRepay> repayList = repayMapper.selectByExample(example);

        // 根据订单号查询每一期计划利息和计划本金
        List<RepayInfoVO> repayInfoVOs = repayMapper.selectRepayInfoGroup(req.getOrderId());

        if (CollectionUtils.isEmpty(repayList)) {
            specialJnlService.warn4Fail(null, "还款通知失败：找不到订单号 " + req.getOrderId() + " 的还款信息，需要您的确认，请检查", req.getOrderId(), "还款信息不存在", true);
            throw new PTMessageException(PTMessageEnum.REPAY_DATA_NOT_FOUND, "无还款记录");
        }
        LnRepay lnRepay = repayList.get(0);
        try {
            //判断还款信息表的状态是否需要更新
            if (order.getStatus() == Constants.ORDER_STATUS_PAYING) {
                //查询借款信息表
                LnLoan lnLoan = loanMapper.selectByPrimaryKey(lnRepay.getLoanId());

                //TODO 判断是否是部分还款（暂无这种情况）

                LnPayOrdersJnl lnPayOrdersJnl = new LnPayOrdersJnl();
                //修改ln_pay_orders状态
                LnPayOrders payOrdersTemp = new LnPayOrders();
                payOrdersTemp.setId(order.getId());
                payOrdersTemp.setUpdateTime(new Date());
                if (OrderStatus.SUCCESS.getCode().equals(req.getStatus())) {
                    payOrdersTemp.setReturnCode(ConstantUtil.BSRESCODE_SUCCESS);
                    payOrdersTemp.setReturnMsg(ConstantUtil.DEFAULT_SUCESSMSG);
                    payOrdersTemp.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_SUCCESS.getCode()));
                    lnPayOrdersJnl.setTransCode(LoanStatus.ORDERS_JNL_STATUS_SUCCESS.getCode());
                } else {
                    payOrdersTemp.setReturnCode(req.getErrorCode());
                    payOrdersTemp.setReturnMsg(req.getErrorMsg());
                    payOrdersTemp.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_FAIL.getCode()));
                    lnPayOrdersJnl.setTransCode(LoanStatus.ORDERS_JNL_STATUS_FAIL.getCode());
                }
                payOrdersMapper.updateByPrimaryKeySelective(payOrdersTemp);
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

                payOrdersJnlMapper.insertSelective(lnPayOrdersJnl);

                //修改支付结果表状态
                BsPayResultQueueExample queueExample = new BsPayResultQueueExample();
                queueExample.createCriteria().andOrderNoEqualTo(order.getOrderNo());
                List<BsPayResultQueue> queueList = queueMapper.selectByExample(queueExample);

                if (CollectionUtils.isNotEmpty(queueList) && queueList.size() > 0) {
                    BsPayResultQueue queueTemp = new BsPayResultQueue();
                    queueTemp.setId(queueList.get(0).getId());
                    queueTemp.setUpdateTime(new Date());
                    if (OrderStatus.SUCCESS.getCode().equals(req.getStatus())) {
                        queueTemp.setStatus("SUCC");
                    } else {
                        queueTemp.setStatus("FAIL");
                    }
                    queueMapper.updateByPrimaryKeySelective(queueTemp);
                }

                for (final LnRepay repay : repayList) {

                    //查询借贷关系表
                    LnLoanRelationExample relationExample = new LnLoanRelationExample();
                    relationExample.createCriteria().andLoanIdEqualTo(lnRepay.getLoanId()).andLnUserIdEqualTo(lnRepay.getLnUserId());
                    List<LnLoanRelation> relationList = relationMapper.selectByExample(relationExample);

                    //修改还款信息表状态
                    LnRepay repayTemp = new LnRepay();
                    repayTemp.setId(repay.getId());
                    repayTemp.setUpdateTime(new Date());
                    if (OrderStatus.SUCCESS.getCode().equals(req.getStatus())) {
                        repayTemp.setStatus(LoanStatus.REPAY_STATUS_REPAIED.getCode());
                        repayTemp.setDoneTime(new Date());
                    } else {
                        repayTemp.setStatus(LoanStatus.REPAY_STATUS_FAIL.getCode());
                    }
                    repayMapper.updateByPrimaryKeySelective(repayTemp);
                    lnRepay.setStatus(repayTemp.getStatus());

                    if (OrderStatus.SUCCESS.getCode().equals(req.getStatus())) {
                        LnRepayDetailExample detailExample = new LnRepayDetailExample();
                        detailExample.createCriteria().andRepayIdEqualTo(repay.getId());
                        List<LnRepayDetail> detailList = detailMapper.selectByExample(detailExample);

                        //查询还款计划表
                        final LnRepaySchedule repaySchedule = scheduleMapper.selectByPrimaryKey(repay.getRepayPlanId());

                        RepayInfo repayInfo = new RepayInfo();
                        repayInfo.setPartner(PartnerEnum.getEnumByCode(order.getPartnerCode()));
                        repayInfo.setChargeRuleId(lnLoan.getChargeRuleId());
                        //借款用户子账户编号
                        repayInfo.setLoanActId(relationList.get(0).getLnSubAccountId());
                        //发生金额
                        repayInfo.setAmount(repay.getDoneTotal());

                        //理财人利息
                        Double interest = 0d;
                        //理财人本金
                        Double principal = 0d;

                        for (LnRepayDetail detail : detailList) {

                            //逾期费
                            if (detail.getSubjectCode().equals(LoanSubjects.SUBJECT_CODE_LATE_FEE.getCode())) {
                                repayInfo.setOverdueAmount(detail.getDoneAmount());
                            }

                        }

                        //币港湾服务费(不是信息服务费)
                        repayInfo.setServiceFee(algorithmService.calBGWServiceFee(lnLoan.getId(), repaySchedule.getSerialId()));

                        //保证金 (不是保费)
                        repayInfo.setBailAmount(algorithmService.calZANDeposit(lnLoan.getId(), repaySchedule.getSerialId()));

                        CommissionVO commissionVO = new CommissionVO();
                        //手续费
                        if(LoanStatus.CHANNEL_TRANS_TYPE_AUTH.getCode().equals(order.getChannelTransType())) {
                            // 快捷还款
                            commissionVO = commissionService.calServiceFee(loanUserService.calTotalRepay(lnLoan.getId(), repaySchedule.getSerialId()), TransTypeEnum.LOAN_USER_REPAY_QUICK_PAY, PayPlatformEnum.BAOFOO);
                        } else if(LoanStatus.CHANNEL_TRANS_TYPE_NET_BANK.getCode().equals(order.getChannelTransType())) {
                            // 网银还款
                            commissionVO = commissionService.calServiceFee(loanUserService.calTotalRepay(lnLoan.getId(), repaySchedule.getSerialId()), TransTypeEnum.LOAN_USER_REPAY_E_BANK, PayPlatformEnum.BAOFOO);
                        } else if(LoanStatus.CHANNEL_TRANS_TYPE_DK.getCode().equals(order.getChannelTransType())) {
                            // 代扣还款
                            commissionVO = commissionService.calServiceFee(loanUserService.calTotalRepay(lnLoan.getId(), repaySchedule.getSerialId()), TransTypeEnum.ZAN_REPAY_DK, PayPlatformEnum.BAOFOO);
                        }

                        repayInfo.setFee(commissionVO.getActPayAmount() == null ? 0d : commissionVO.getActPayAmount());

                        //记录手续费
                        BsServiceFee bsServiceFee = new BsServiceFee();
                        bsServiceFee.setPlanFee(commissionVO.getNeedPayAmount());
                        bsServiceFee.setDoneFee(commissionVO.getActPayAmount());
                        bsServiceFee.setTransAmount(repay.getDoneTotal());
                        bsServiceFee.setFeeType(Constants.FEE_TYPE_REPAY);
                        bsServiceFee.setStatus(Constants.FEE_STATUS_COLLECT);
                        bsServiceFee.setCreateTime(new Date());
                        bsServiceFee.setOrderNo(repay.getPayOrderNo());
                        bsServiceFee.setSubAccountId(relationList.get(0).getLnSubAccountId());
                        bsServiceFee.setUpdateTime(new Date());
                        bsServiceFee.setNote("应扣" + commissionVO.getNeedPayAmount() + "，实扣" + commissionVO.getActPayAmount());
                        bsServiceFee.setPaymentPlatformFee(commissionVO.getThreePartyPaymentServiceFee());
                        serviceFeeMapper.insertSelective(bsServiceFee);

//                        for (LnLoanRelation relation : relationList) {
//
//                            LnFinanceRepayScheduleExample financeRepayScheduleExample = new LnFinanceRepayScheduleExample();
//                            financeRepayScheduleExample.createCriteria().andRelationIdEqualTo(relation.getId()).andRepaySerialEqualTo(repaySchedule.getSerialId());
//                            List<LnFinanceRepaySchedule> list = financeRepayScheduleMapper.selectByExample(financeRepayScheduleExample);
//                            if (CollectionUtils.isNotEmpty(list)) {
//                                interest = MoneyUtil.add(interest, list.get(0).getPlanInterest()).doubleValue();
//                                principal = MoneyUtil.add(principal, list.get(0).getPlanPrincipal()).doubleValue();
//                            }
//                        }
                        repayInfo.setInterest(interest);
                        repayInfo.setPrincipal(principal);
                        if(!CollectionUtils.isEmpty(repayInfoVOs)) {
                            for(RepayInfoVO vo : repayInfoVOs) {
                                if(vo.getSerialId().equals(repaySchedule.getSerialId())) {
                                    repayInfo.setInterest(vo.getPlanInterest());
                                    repayInfo.setPrincipal(vo.getPlanPrincipal());
                                    break;
                                }
                            }
                        }

                        //判断是否逾期
                        boolean late = loanUserService.isLoanUserLoanTermLate(repay.getLnUserId(), repay.getLoanId(), repaySchedule.getSerialId());
                        //逾期
                        if (late) {
                            /**
                             * 调用逾期还款处理：宝付体系赞分期营收户+，宝付体系保证金户+，还款资金户+
                             * 生成存管账单return_flag=DF_PENDING记录
                             * 更新还款计划表状态为逾期已还款
                             */
                        	overdueRepay(lnLoan, repaySchedule, repay.getDoneTotal(), repay.getPayOrderNo(), relationList, false);

                            /*repayAccountService.chargeOverdueRepay(repayInfo);
                            //更新还款计划表状态
                            repaySchedule.setStatus(LoanStatus.REPAY_SCHEDULE_STATUS_LATE_REPAIED.getCode());
                            repaySchedule.setUpdateTime(new Date());
                            scheduleMapper.updateByPrimaryKeySelective(repaySchedule);*/
                            //如果未逾期：更新还款计划表状态为已还款，存管还款账单表（等待定时轮询进行存管线下还款）、账单明细表记录生成
                        } else {
                        	/**
                        	 * 非逾期，正常还款分账：更新还款计划表状态为已还款，存管还款账单表（等待定时轮询进行存管线下还款）、账单明细表记录生成
                        	 * 系统记账
                        	 */
                        	//判断账单时间是否在设定日期及之后，之后则调用新方法
                        	Date finishDate = DateUtil.parseDate("2018-03-15");
                            BsSysConfig config = bsSysConfigService.findKey( Constants.ZAN_CALLED_AWAY_DATE );
                            if(config != null){
                            	finishDate = DateUtil.parseDate(config.getConfValue());
                            }
                            if(repaySchedule.getPlanDate().compareTo(finishDate) <= 0){
                            	//小于等于该时间及之前，调用原来的还款分账
                            	normalRepaySysSplit(lnLoan, repaySchedule, repay.getDoneTotal(), repay.getPayOrderNo(), relationList);
                            }else{
                            	normalRepaySysSplit4ZANNew(lnLoan, repaySchedule, repay.getDoneTotal(), repay.getPayOrderNo(), relationList);
                            }
                        	

                           /* //非逾期记账 更新借贷关系由鲨鱼那边调用
                        	repayInfo.setThdRepayAmount(0.0);
                        	repayInfo.setRevenueZanAmount(0.0);
                            repayAccountService.chargeNormalRepay(repayInfo);
                            //更新还款计划表状态
                            repaySchedule.setStatus(LoanStatus.REPAY_SCHEDULE_STATUS_REPAIED.getCode());
                            repaySchedule.setUpdateTime(new Date());
                            scheduleMapper.updateByPrimaryKeySelective(repaySchedule);*/
                            //生成正常还款的回款计划
                            /*new Thread(new Runnable() {
                                @Override
                                public void run() {

                                    try {
                                        FinanceRepayInfo financeRepayInfo = new FinanceRepayInfo();
                                        financeRepayInfo.setLoanId(repay.getLoanId());
                                        financeRepayInfo.setRepayScheduleId(repaySchedule.getId());
                                        financeRepayInfo.setRepaySerial(repaySchedule.getSerialId());
                                        financeReceiveMoneyService.generateNormalRepayPlan(financeRepayInfo);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        specialJnlService.warn4Fail(null, "还款时[" + req.getOrderId() + "]回款计划生成失败，请检查并重新生成", req.getOrderId(), "还款时回款计划生成失败", true);
                                    }
                                }
                            }).start();*/
                        }
                    }
                }

            }
        } catch (Exception e) {
            log.error("通知处理异常", e);
            //放到redis中
            LoanNoticeVO vo = new LoanNoticeVO();
            vo.setPayOrderNo(order.getOrderNo());
            vo.setChannel(order.getChannel());
            vo.setChannelTransType(order.getChannelTransType());
            vo.setTransType(order.getTransType());
            vo.setStatus(Constants.ORDER_STATUS_PAYING);
            vo.setAmount(order.getAmount().toString());
            redisUtil.rpushRedis(vo);
            throw e;
        }
        //若主动还款同步失败，处理完结果业务后，直接同步抛错！
        HashMap<String, Object> extendMap = req.getExtendMap();
        if(extendMap != null){//失败时，是否同步抛错标识
            Object throwFlag = extendMap.get("throwFlag");
            if(throwFlag != null && (boolean)throwFlag){
                throw new PTMessageException(PTMessageEnum.ZAN_REPAY_FAIL, req.getErrorMsg());
            }
        }

        //起线程通知
        RepayProcess process = new RepayProcess();
        process.setRepay(lnRepay);
        process.setRepayPaymentService(this);
        process.setErrMsg(req.getErrorMsg());
        new Thread(process).start();
    }
    

	@Override
    public void notifyPartner(LnRepay repay, String errorMsg) {
        log.info(">>>支付通知合作方开始：[入参]" + repay + "|errorMsg=" + errorMsg + "<<<");
        B2GReqMsg_RepayNotice_NoticeRepay noticeRepay = new B2GReqMsg_RepayNotice_NoticeRepay();
        noticeRepay.setOrderNo(repay.getPartnerOrderNo());

        LnPayOrdersExample ordersExample = new LnPayOrdersExample();
        ordersExample.createCriteria().andOrderNoEqualTo(repay.getPayOrderNo());
        List<LnPayOrders> ordersList = payOrdersMapper.selectByExample(ordersExample);
        if (CollectionUtils.isNotEmpty(ordersList)) {
            noticeRepay.setChannel(ordersList.get(0).getChannel());
        }
        //根据借款编号 查询借款信息
        LnLoan lnLoan = loanMapper.selectByPrimaryKey(repay.getLoanId());

        if (lnLoan != null) {

            noticeRepay.setLoanId(lnLoan.getPartnerLoanId());
            noticeRepay.setLoanResultCode(repay.getStatus().equals(LoanStatus.REPAY_STATUS_REPAIED.getCode()) ? "SUCCESS" : "FAIL");
            noticeRepay.setLoanResultMsg(errorMsg);
        } else {
            noticeRepay.setLoanResultCode("FAIL");
            noticeRepay.setLoanResultMsg("找不到借款信息");
        }

        B2GResMsg_RepayNotice_NoticeRepay res = null;
        LnRepay repayTemp = new LnRepay();
        try {
            res = noticeService.noticeRepay(noticeRepay);

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
        lnRepayExample.createCriteria().andPayOrderNoEqualTo(repay.getPayOrderNo());
        repayTemp.setUpdateTime(new Date());
        repayMapper.updateByExampleSelective(repayTemp, lnRepayExample);
    }

	@Override
	public void badDebt(final G2BReqMsg_Repay_BadDebt req) {
		//检查该笔订单是否存在
		LnBadExample lnBadExample = new LnBadExample();
		lnBadExample.createCriteria().andPartnerOrderNoEqualTo(req.getOrderNo());
		List<LnBad> lnBadList = lnBadMapper.selectByExample(lnBadExample);
		if (CollectionUtils.isNotEmpty(lnBadList)) {
			throw new PTMessageException(PTMessageEnum.ZAN_ORDER_NO_EXIST);
		}
		//检查该笔借款是否存在
		LnLoanExample lnLoanExample = new LnLoanExample();
		lnLoanExample.createCriteria().andPartnerLoanIdEqualTo(req.getLoanId());
		final List<LnLoan> lnLoanList = loanMapper.selectByExample(lnLoanExample);
		if (CollectionUtils.isEmpty(lnLoanList)) {
			throw new PTMessageException(PTMessageEnum.ZAN_REPAY_LOAN_ID_NOT_EXIST);
		}

		//校验用户是否对应
		LnUser lnUser = lnUserMapper.selectByPrimaryKey(lnLoanList.get(0).getLnUserId());
		if (lnUser == null || !lnUser.getPartnerUserId().equals(req.getUserId()) ) {
			throw new PTMessageException(PTMessageEnum.ZAN_REPAY_BAD_USER_ERROR);
		}


        transactionTemplate.execute(new TransactionCallbackWithoutResult(){
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {

                for (Repayment repayment : req.getRepayments()) {
                    log.info("==========================还款明细:" + JSONObject.fromObject(repayment).toString());




                    //查询对应的还款计划表
                    LnRepayScheduleExample scheduleExample = new LnRepayScheduleExample();
                    List<String> status = new ArrayList<>();
                    status.add(LoanStatus.REPAY_SCHEDULE_STATUS_LATE_NOT.getCode());
                    scheduleExample.createCriteria().andLoanIdEqualTo(lnLoanList.get(0).getId()).andPartnerRepayIdEqualTo(repayment.getRepayId()).andStatusIn(status);
                    List<LnRepaySchedule> repayScheduleList = scheduleMapper.selectByExample(scheduleExample);

                    //判断是否已还过款
                    if (CollectionUtils.isEmpty(repayScheduleList)) {
                        throw new PTMessageException(PTMessageEnum.ZAN_REPAY_PAYMENT_ORDER_DUPLICATE, "[" + repayment.getRepayId() + "]");
                    }


                    //判断该笔还款是否已经存库
                    LnBadExample lnBadExample  = new LnBadExample();
                    lnBadExample.createCriteria().andRepayPlanIdEqualTo(repayScheduleList.get(0).getId());
                    List<LnBad> lnBadList = lnBadMapper.selectByExample(lnBadExample);
                    if (CollectionUtils.isNotEmpty(lnBadList)) {
                    	continue;
					}

                    //查询还款计划表对应的还款计划明细
                    LnRepayScheduleDetailExample detailExample = new LnRepayScheduleDetailExample();
                    detailExample.createCriteria().andPlanIdEqualTo(repayScheduleList.get(0).getId());
                    List<LnRepayScheduleDetail> detailList = scheduleDetailMapper.selectByExample(detailExample);

                    log.info("=====================================实际还款与还款计划详情比较===============================");


                    //判断入参总金额是否是各项金额总和
                	Double totalAmountTemp = MoneyUtil.add(repayment.getPrincipal(), repayment.getInterest()).doubleValue();
                	totalAmountTemp = MoneyUtil.add(totalAmountTemp, repayment.getLateFee()).doubleValue();
                	totalAmountTemp = MoneyUtil.add(totalAmountTemp, repayment.getServiceFee()).doubleValue();
                	totalAmountTemp = MoneyUtil.add(totalAmountTemp, repayment.getSuperviseFee()).doubleValue();
                	totalAmountTemp = MoneyUtil.add(totalAmountTemp, repayment.getInfoServiceFee()).doubleValue();
                	totalAmountTemp = MoneyUtil.add(totalAmountTemp, repayment.getAccountManageFee()).doubleValue();
                	totalAmountTemp = MoneyUtil.add(totalAmountTemp, repayment.getPremium()).doubleValue();

                	Long totalAmount = MoneyUtil.add(totalAmountTemp, repayment.getOther()).longValue();
                			/*repayment.getPrincipal() + repayment.getInterest() + repayment.getLateFee() + repayment.getServiceFee() +
                			repayment.getSuperviseFee()+ repayment.getInfoServiceFee() + repayment.getAccountManageFee() + repayment.getPremium() +
                			repayment.getOther();*/

                	if (!totalAmount.equals(repayment.getTotal())) {
                		throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "入参总金额校验错误");
					}


                    //判断还款的每一项是否与还款计划详情一致
                    for (LnRepayScheduleDetail scheduleDetail : detailList) {


                    	if (scheduleDetail.getSubjectCode().equals(LoanSubjects.SUBJECT_CODE_PRINCIPAL.getCode())
                                && MoneyUtil.multiply(scheduleDetail.getPlanAmount(), 100).longValue() != repayment.getPrincipal().longValue()) {
                            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "坏账总金额错误");
                        }

                        if (scheduleDetail.getSubjectCode().equals(LoanSubjects.SUBJECT_CODE_INTEREST.getCode())
                                && MoneyUtil.multiply(scheduleDetail.getPlanAmount(), 100).longValue() != repayment.getInterest().longValue()) {
                            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "坏账利息金额错误");
                        }


                        if (scheduleDetail.getSubjectCode().equals(LoanSubjects.SUBJECT_CODE_SUPERVISE_FEE.getCode())
                                && MoneyUtil.multiply(scheduleDetail.getPlanAmount(), 100).longValue() != repayment.getSuperviseFee().longValue()) {
                            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "坏账监管费金额错误");
                        }

                        if (scheduleDetail.getSubjectCode().equals(LoanSubjects.SUBJECT_CODE_INFO_SERVICE_FEE.getCode())
                                && MoneyUtil.multiply(scheduleDetail.getPlanAmount(), 100).longValue() != repayment.getInfoServiceFee().longValue()) {
                            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "坏账信息服务费金额错误");
                        }

                        if (scheduleDetail.getSubjectCode().equals(LoanSubjects.SUBJECT_CODE_ACCOUNT_MANAGE_FEE.getCode())
                                && MoneyUtil.multiply(scheduleDetail.getPlanAmount(), 100).longValue() != repayment.getAccountManageFee().longValue()) {
                            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "坏账账户管理费金额错误");
                        }

                        if (scheduleDetail.getSubjectCode().equals(LoanSubjects.SUBJECT_CODE_PREMIUM.getCode())
                                && MoneyUtil.multiply(scheduleDetail.getPlanAmount(), 100).longValue() != repayment.getPremium().longValue()) {
                            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "坏账保费金额错误");
                        }

                        if (scheduleDetail.getSubjectCode().equals(LoanSubjects.SUBJECT_CODE_OTHER.getCode())
                                && MoneyUtil.multiply(scheduleDetail.getPlanAmount(), 100).longValue() != repayment.getOther().longValue()) {
                            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "坏账其他费用金额错误");
                        }

                    }

                    //记录ln_repay表，状态为还款中
                    LnBad lnBad = new LnBad();
                    lnBad.setLnUserId(lnLoanList.get(0).getLnUserId());
                    lnBad.setLoanId(lnLoanList.get(0).getId());
                    lnBad.setRepayPlanId(repayScheduleList.get(0).getId());
                    lnBad.setPartnerOrderNo(req.getOrderNo());
                    lnBad.setCreateTime(new Date());
                    lnBad.setUpdateTime(new Date());
                    lnBadMapper.insertSelective(lnBad);
                    log.info("==========================还款信息表:" + JSONObject.fromObject(lnBad).toString());

                    //记录还款计划明细表ln_repay_detail
                    LnBadDetail lnBadDetail = new LnBadDetail();
                    lnBadDetail.setUpdateTime(new Date());
                    lnBadDetail.setCreateTime(new Date());
                    lnBadDetail.setBadId(lnBad.getId());
                    lnBadDetail.setDoneAmount(MoneyUtil.divide(repayment.getPrincipal(), 100).doubleValue());
                    lnBadDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_PRINCIPAL.getCode());
                    lnBadDetailMapper.insertSelective(lnBadDetail);
                    //利息
                    lnBadDetail.setDoneAmount(MoneyUtil.divide(repayment.getInterest(), 100).doubleValue());
                    lnBadDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_INTEREST.getCode());
                    lnBadDetailMapper.insertSelective(lnBadDetail);
                    //监管费
                    lnBadDetail.setDoneAmount(repayment.getSuperviseFee() != null ? MoneyUtil.divide(repayment.getSuperviseFee(), 100).doubleValue() : 0);
                    lnBadDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_SUPERVISE_FEE.getCode());
                    lnBadDetailMapper.insertSelective(lnBadDetail);
                    //信息服务费
                    lnBadDetail.setDoneAmount(MoneyUtil.divide(repayment.getInfoServiceFee(), 100).doubleValue());
                    lnBadDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_INFO_SERVICE_FEE.getCode());
                    lnBadDetailMapper.insertSelective(lnBadDetail);
                    //账户管理费
                    lnBadDetail.setDoneAmount(MoneyUtil.divide(repayment.getAccountManageFee(), 100).doubleValue());
                    lnBadDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_ACCOUNT_MANAGE_FEE.getCode());
                    lnBadDetailMapper.insertSelective(lnBadDetail);
                    //保费
                    lnBadDetail.setDoneAmount(MoneyUtil.divide(repayment.getPremium(), 100).doubleValue());
                    lnBadDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_PREMIUM.getCode());
                    lnBadDetailMapper.insertSelective(lnBadDetail);
                    //其他
                    lnBadDetail.setDoneAmount(MoneyUtil.divide(repayment.getOther(), 100).doubleValue());
                    lnBadDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_OTHER.getCode());
                    lnBadDetailMapper.insertSelective(lnBadDetail);

                    //滞纳金
                    lnBadDetail.setDoneAmount(MoneyUtil.divide(repayment.getLateFee(), 100).doubleValue());
                    lnBadDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_LATE_FEE.getCode());
                    lnBadDetailMapper.insertSelective(lnBadDetail);
                    //坏账手续费
                    lnBadDetail.setDoneAmount(MoneyUtil.divide(repayment.getLateFee(), 100).doubleValue());
                    lnBadDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_SERVICE_FEE.getCode());
                    lnBadDetailMapper.insertSelective(lnBadDetail);

                }


            }
        });

	}

	/**
	 * 正常还款、提前还款系统分账
	 */
	@Override
	public void normalRepaySysSplit(LnLoan lnLoan, LnRepaySchedule repaySchedule,
			Double repayAmount, String payOrderNo, List<LnLoanRelation> relationList) {
		/**
		 * 计算1、币港湾结算=借款总本金/期数+借款总本金*名义月利率；名义月利率=15%*（期数+1）/（期数*24）
		 * 2、保证金=借款总本金*3%/12
		 * 3、赞分期营收 = 还款总金额 - 币港湾结算 -保证金
		 * 4、币港湾营收=币港湾结算-每笔债权协议利率之和
		 * 5、每笔债权协议利率之和，并更新lnFinanceRepaySchedule表的PlanTotal和PlanFee
		 * 			1、记录ZAN的还款营收收入 2、ln_repay_schedule更新状态为已还 3、新增存管还款计划表及明细
		 * 6、调用系统记账
		 */

		//币港湾结算
		Double BGWSettlementFee = algorithmService.calBGWSettlementFee(lnLoan.getId(), repaySchedule.getSerialId());
		//保证金
		Double ZANDeposit = algorithmService.calZANDeposit(lnLoan.getId(), repaySchedule.getSerialId());
		//赞分期营收
		Double ZANRevenue = MoneyUtil.subtract(
				MoneyUtil.subtract(repayAmount, BGWSettlementFee).doubleValue(),ZANDeposit).doubleValue();
		//债权协议利率之和（包括本金）
		FinanceRepayCalVO financeRepayCalVO = do4FinanceRepay(lnLoan, repaySchedule.getSerialId(),relationList,false);
		Double agreementAmount = financeRepayCalVO.getAgreementSumAmount();
		Double sumFinancePrincipal = financeRepayCalVO.getFinanceSumPrincipal(); //应还至理财人的本金和
		//币港湾营收
		Double BGWRevenue = MoneyUtil.subtract(BGWSettlementFee, agreementAmount).doubleValue();
		/*CommissionVO commissionVO = new CommissionVO();
        //手续费
        if(LoanStatus.CHANNEL_TRANS_TYPE_AUTH.getCode().equals(channelTransType)) {
            // 快捷还款
            commissionVO = commissionService.calServiceFee(lnRepay.getDoneTotal(), TransTypeEnum.LOAN_USER_REPAY_QUICK_PAY, PayPlatformEnum.BAOFOO);
        } else if(LoanStatus.CHANNEL_TRANS_TYPE_NET_BANK.getCode().equals(channelTransType)) {
            // 网银还款
            commissionVO = commissionService.calServiceFee(lnRepay.getDoneTotal(), TransTypeEnum.LOAN_USER_REPAY_E_BANK, PayPlatformEnum.BAOFOO);
        } else if(LoanStatus.CHANNEL_TRANS_TYPE_DK.getCode().equals(channelTransType)) {
            // 代扣还款
            commissionVO = commissionService.calServiceFee(lnRepay.getDoneTotal(), TransTypeEnum.ZAN_REPAY_DK, PayPlatformEnum.BAOFOO);
        }
        */
        //1、记录BGW对ZAN还款营收收入 2、ln_repay_schedule更新状态为已还 3、新增存管还款计划表及明细
        //获取最大的SerialId 赞分期账单固定，此处直接取自账单表序号
        Integer maxSerialId = repaySchedule.getSerialId()-1;
        doNormalRepayDetail(lnLoan, repaySchedule, repayAmount, payOrderNo, ZANRevenue,
        		relationList.get(0).getLnSubAccountId(), agreementAmount, PartnerEnum.ZAN, maxSerialId, null);

		//系统记账
        RepayInfo repayInfo = new RepayInfo();
        repayInfo.setPartner(PartnerEnum.ZAN);
        repayInfo.setAmount(repayAmount);
        repayInfo.setLoanActId(relationList.get(0).getLnSubAccountId());
        repayInfo.setPrincipal(getRepayDetailAmount(repaySchedule.getId(), LoanSubjects.SUBJECT_CODE_PRINCIPAL));
        repayInfo.setInterest(MoneyUtil.subtract(agreementAmount, repayInfo.getPrincipal()).doubleValue());
        repayInfo.setFee(0d);
        repayInfo.setBailAmount(ZANDeposit);
        repayInfo.setChargeRuleId(lnLoan.getChargeRuleId());
        repayInfo.setThdRepayAmount(agreementAmount);
        repayInfo.setRevenueZanAmount(ZANRevenue);
        repayInfo.setBgwRevenueAmount(BGWRevenue);
        repayInfo.setFinancePrincipal(sumFinancePrincipal);
        repayInfo.setLnRepayScheduleId(repaySchedule.getId());
        repayAccountService.chargeNormalRepay(repayInfo);

	}

	@Override
	public void overdueSysSplit(LnLoan lnLoan, RepayScheduleVO repaySchedule,
			List<LnLoanRelation> relationList) {
		/**
		 * 1、代偿人垫付资金=债权协议利率之和
         * 币港湾结算=借款总本金/期数+借款总本金*名义月利率；名义月利率=15%*（期数+1）/（期数*24）
		 * 2、赞分期营收 = 币港湾结算-每笔债权协议利率之和  >0 不处理,< 0 赞分期营收+
		 * 3、币港湾营收 = 币港湾结算-每笔债权协议利率之和 >0 币港湾营收+,<0 币港湾营收-
		 * 4、每笔债权协议利率之和，并更新lnFinanceRepaySchedule表的PlanTotal和PlanFee
		 * 5、生成存管账单
		 * 6、调用系统记账
		 * 7、ln_repay_schedule更新状态为已还款
		 */

		//币港湾结算
		Double BGWSettlementFee = algorithmService.calBGWSettlementFee(lnLoan.getId(), repaySchedule.getSerialId());

		//债权协议利率之和（包括本金）
		FinanceRepayCalVO financeRepayCalVO = do4FinanceRepay(lnLoan, repaySchedule.getSerialId(),relationList,true);
		Double agreementAmount = financeRepayCalVO.getAgreementSumAmount();
		Double sumFinancePrincipal = financeRepayCalVO.getFinanceSumPrincipal(); //应还至理财人的本金和
		//营收差额
		Double BGWRevenue = MoneyUtil.subtract(BGWSettlementFee, agreementAmount).doubleValue();

        //记录还款营收收入（逾期垫付时营收扣除）
		BsRevenueTransDetail bsRevenueTransDetail = new BsRevenueTransDetail();
		bsRevenueTransDetail.setPartnerCode(PartnerEnum.ZAN.getCode());
		bsRevenueTransDetail.setTransType(Constants.REVENUE_TYPE_OVERDUE_DEDUCT);
		bsRevenueTransDetail.setLoanId(lnLoan.getId());
		bsRevenueTransDetail.setRepaySerial(repaySchedule.getSerialId());
		bsRevenueTransDetail.setRepayScheduleId(repaySchedule.getId());
		bsRevenueTransDetail.setRepayAmount(repaySchedule.getPlanTotal());
		bsRevenueTransDetail.setRevenueAmount(BGWRevenue);
		bsRevenueTransDetail.setRepayScheduleId(repaySchedule.getId());
		bsRevenueTransDetail.setCreateTime(new Date());
		bsRevenueTransDetail.setUpdateTime(new Date());
		bsRevenueTransDetailMapper.insertSelective(bsRevenueTransDetail);

		//生成存管账单
		//获取SerialId
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
		schedul.setReturnFlag(Constants.DEP_REPAY_RETURN_FLAG_DK_SUCC);
		schedul.setRepayType(Constants.LN_DEP_REPAY_SCHEDULE_REPAY_TYPE_LATE_NOT);
		schedul.setCreateTime(new Date());
		schedul.setUpdateTime(new Date());
		lnDepositionRepayScheduleMapper.insertSelective(schedul);

		//还款本金
		Double repayPrincipal = getRepayDetailAmount(repaySchedule.getId(), LoanSubjects.SUBJECT_CODE_PRINCIPAL);
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

        List<LnCompensateRelation> list = financeRepayCalVO.getList();
        if(CollectionUtils.isNotEmpty(list)){
            Integer inDays = 30;
            for (LnCompensateRelation compensateRelation : list) {
                compensateRelation.setDepPlanId(schedul.getId());
                compensateRelation.setInterestDay(inDays);
                lnCompensateRelationMapper.insertSelective(compensateRelation);
            }
        }
		//调用系统记账
		RepayInfo info = new RepayInfo();
		info.setAmount(agreementAmount);
		info.setServiceFee(BGWRevenue);
		info.setPartner(PartnerEnum.ZAN);
		info.setLoanActId(relationList.get(0).getLnSubAccountId());
		info.setPrincipal(repaySchedule.getPrincipal());
		info.setFinancePrincipal(sumFinancePrincipal);
		info.setLnFinancePlanId(repaySchedule.getId());
		repayAccountService.chargeOverdueAdvance(info);

		//ln_repay_schedule更新状态为已还款
		LnRepaySchedule lnRepayScheduleTemp = new LnRepaySchedule();
		lnRepayScheduleTemp.setId(repaySchedule.getId());
		lnRepayScheduleTemp.setFinishTime(new Date());
		lnRepayScheduleTemp.setUpdateTime(new Date());
		lnRepayScheduleTemp.setStatus(Constants.LN_REPAY_LATE_NOT);
		scheduleMapper.updateByPrimaryKeySelective(lnRepayScheduleTemp);

	}

	@Override
	public void doNormalRepayDetail(LnLoan lnLoan, LnRepaySchedule repaySchedule, Double lnRepayAmount,String payOrderNo, Double partnerRevenue,
			Integer lnSubAccountId, Double depRepayScheduleTotal, PartnerEnum partnerEnum, Integer maxSerialId, Double loanServiceAmount) {
		//bs_revenue_trans_detail记录合作方还款营收收入
		BsRevenueTransDetail bsRevenueTransDetail = new BsRevenueTransDetail();
		bsRevenueTransDetail.setPartnerCode(partnerEnum.getCode());
		bsRevenueTransDetail.setTransType(Constants.REVENUE_TYPE_REPAY_INCOME);
		bsRevenueTransDetail.setLoanId(lnLoan.getId());
		bsRevenueTransDetail.setRepaySerial(repaySchedule.getSerialId());
		bsRevenueTransDetail.setRepayScheduleId(repaySchedule.getId());
		bsRevenueTransDetail.setRepayAmount(lnRepayAmount);
		bsRevenueTransDetail.setRevenueAmount(partnerRevenue);
		bsRevenueTransDetail.setRepayScheduleId(repaySchedule.getId());
		bsRevenueTransDetail.setCreateTime(new Date());
		bsRevenueTransDetail.setUpdateTime(new Date());
		bsRevenueTransDetailMapper.insertSelective(bsRevenueTransDetail);

		//ln_repay_schedule更新状态为已还款
		LnRepaySchedule lnRepayScheduleTemp = new LnRepaySchedule();
		lnRepayScheduleTemp.setId(repaySchedule.getId());
		lnRepayScheduleTemp.setFinishTime(new Date());
		lnRepayScheduleTemp.setUpdateTime(new Date());
		lnRepayScheduleTemp.setStatus(Constants.LN_REPAY_REPAIED);
		scheduleMapper.updateByPrimaryKeySelective(lnRepayScheduleTemp);

		//存管还款计划表
		LnDepositionRepaySchedule schedul = new LnDepositionRepaySchedule();
		schedul.setLnUserId(lnLoan.getLnUserId());
		schedul.setLoanId(lnLoan.getId());
		schedul.setPartnerRepayId(repaySchedule.getPartnerRepayId());
		schedul.setSerialId(maxSerialId+1);
		schedul.setPlanDate(repaySchedule.getPlanDate());
		schedul.setPlanTotal(depRepayScheduleTotal);
		schedul.setStatus(Constants.LN_REPAY_STATUS_INIT);
		schedul.setReturnFlag(Constants.DEP_REPAY_RETURN_FLAG_DF_PENDING);
		schedul.setRepayType(Constants.LN_DEP_REPAY_SCHEDULE_REPAY_TYPE_NORMAL_REPAY);
		schedul.setCreateTime(new Date());
		schedul.setUpdateTime(new Date());
		lnDepositionRepayScheduleMapper.insertSelective(schedul);

		//还款本金
		Double repayPrincipal = getRepayDetailAmount(repaySchedule.getId(), LoanSubjects.SUBJECT_CODE_PRINCIPAL);
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
		Double interest = MoneyUtil.subtract(MoneyUtil.subtract(depRepayScheduleTotal, repayPrincipal).doubleValue(),
				loanServiceAmount==null?0:loanServiceAmount).doubleValue();
		detailInterest.setPlanAmount(interest);
		detailInterest.setSubjectCode(LoanSubjects.SUBJECT_CODE_INTEREST.getCode());
		detailInterest.setCreateTime(new Date());
		detailInterest.setUpdateTime(new Date());
		lnDepositionRepayScheduleDetailMapper.insertSelective(detailInterest);
		
		if(loanServiceAmount!= null && loanServiceAmount > 0){
			//存管还款计划表明细-借款服务费
			LnDepositionRepayScheduleDetail detailLoanService = new LnDepositionRepayScheduleDetail();
			detailLoanService.setPlanId(schedul.getId());
			detailLoanService.setPlanAmount(loanServiceAmount);
			detailLoanService.setSubjectCode(LoanSubjects.SUBJECT_CODE_LOAN_SERVICE_FEE.getCode());
			detailLoanService.setCreateTime(new Date());
			detailLoanService.setUpdateTime(new Date());
			lnDepositionRepayScheduleDetailMapper.insertSelective(detailLoanService);
		}

	}
	@Override
	public void doNormalRepayDetail4Seven(LnLoan lnLoan, LnRepaySchedule repaySchedule, Double lnRepayAmount,String payOrderNo, Double partnerRevenue,
			Integer lnSubAccountId, Double depRepayScheduleTotal, PartnerEnum partnerEnum, Integer maxSerialId, 
			Double loanServiceAmount, boolean isLastPeriodRepay , Double realRepayPrincipal, Double diffRepayPrincipal) {
		//bs_revenue_trans_detail记录合作方还款营收收入
		BsRevenueTransDetail bsRevenueTransDetail = new BsRevenueTransDetail();
		bsRevenueTransDetail.setPartnerCode(partnerEnum.getCode());
		bsRevenueTransDetail.setTransType(Constants.REVENUE_TYPE_REPAY_INCOME);
		bsRevenueTransDetail.setLoanId(lnLoan.getId());
		bsRevenueTransDetail.setRepaySerial(repaySchedule.getSerialId());
		bsRevenueTransDetail.setRepayScheduleId(repaySchedule.getId());
		bsRevenueTransDetail.setRepayAmount(lnRepayAmount);
		bsRevenueTransDetail.setRevenueAmount(partnerRevenue);
		bsRevenueTransDetail.setOtherFee(-diffRepayPrincipal);
		bsRevenueTransDetail.setRepayScheduleId(repaySchedule.getId());
		bsRevenueTransDetail.setCreateTime(new Date());
		bsRevenueTransDetail.setUpdateTime(new Date());
		bsRevenueTransDetailMapper.insertSelective(bsRevenueTransDetail);

		//ln_repay_schedule更新状态为已还款
		LnRepaySchedule lnRepayScheduleTemp = new LnRepaySchedule();
		lnRepayScheduleTemp.setId(repaySchedule.getId());
		lnRepayScheduleTemp.setFinishTime(new Date());
		lnRepayScheduleTemp.setUpdateTime(new Date());
		lnRepayScheduleTemp.setStatus(Constants.LN_REPAY_REPAIED);
		
		if( isLastPeriodRepay && diffRepayPrincipal > 0 ) {
			//如果是最后一期 , 存在补差本金, 则更新账单表plan_total 
			lnRepayScheduleTemp.setPlanTotal( MoneyUtil.add( repaySchedule.getPlanTotal() , diffRepayPrincipal ).doubleValue() );

			LnRepayScheduleDetailExample lnRepSchDetailExample = new LnRepayScheduleDetailExample();
			lnRepSchDetailExample.createCriteria().andPlanIdEqualTo(repaySchedule.getId()).andSubjectCodeEqualTo(LoanSubjects.SUBJECT_CODE_PRINCIPAL.getCode());
			LnRepayScheduleDetail lnRepayDetail = new LnRepayScheduleDetail();
			lnRepayDetail.setPlanAmount(realRepayPrincipal);
			lnRepayDetail.setUpdateTime(new Date());
			scheduleDetailMapper.updateByExampleSelective(lnRepayDetail, lnRepSchDetailExample);
		}
		scheduleMapper.updateByPrimaryKeySelective(lnRepayScheduleTemp);
		
		
		
		
		if( depRepayScheduleTotal > 0 ) {
			//存管还款计划表
			LnDepositionRepaySchedule schedul = new LnDepositionRepaySchedule();
			schedul.setLnUserId(lnLoan.getLnUserId());
			schedul.setLoanId(lnLoan.getId());
			schedul.setPartnerRepayId(repaySchedule.getPartnerRepayId());
			schedul.setSerialId(maxSerialId+1);
			schedul.setPlanDate(repaySchedule.getPlanDate());
			schedul.setPlanTotal(depRepayScheduleTotal);
			schedul.setStatus(Constants.LN_REPAY_STATUS_INIT);
			schedul.setReturnFlag(Constants.DEP_REPAY_RETURN_FLAG_DF_PENDING);
			schedul.setRepayType(Constants.LN_DEP_REPAY_SCHEDULE_REPAY_TYPE_NORMAL_REPAY);
			schedul.setCreateTime(new Date());
			schedul.setUpdateTime(new Date());
			lnDepositionRepayScheduleMapper.insertSelective(schedul);

			//还款本金(七贷最后一期本金还款取真实还款本金)
			Double repayPrincipal = isLastPeriodRepay ? realRepayPrincipal : getRepayDetailAmount(repaySchedule.getId(), LoanSubjects.SUBJECT_CODE_PRINCIPAL);
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
			Double interest = MoneyUtil.subtract(MoneyUtil.subtract(depRepayScheduleTotal, repayPrincipal).doubleValue(),
					loanServiceAmount==null?0:loanServiceAmount).doubleValue();
			detailInterest.setPlanAmount(interest);
			detailInterest.setSubjectCode(LoanSubjects.SUBJECT_CODE_INTEREST.getCode());
			detailInterest.setCreateTime(new Date());
			detailInterest.setUpdateTime(new Date());
			lnDepositionRepayScheduleDetailMapper.insertSelective(detailInterest);
			
			if(loanServiceAmount!= null && loanServiceAmount > 0){
				//存管还款计划表明细-借款服务费
				LnDepositionRepayScheduleDetail detailLoanService = new LnDepositionRepayScheduleDetail();
				detailLoanService.setPlanId(schedul.getId());
				detailLoanService.setPlanAmount(loanServiceAmount);
				detailLoanService.setSubjectCode(LoanSubjects.SUBJECT_CODE_LOAN_SERVICE_FEE.getCode());
				detailLoanService.setCreateTime(new Date());
				detailLoanService.setUpdateTime(new Date());
				lnDepositionRepayScheduleDetailMapper.insertSelective(detailLoanService);
			}
		}
		
	}
	private FinanceRepayCalVO do4FinanceRepay(LnLoan lnLoan, Integer serialId,
			List<LnLoanRelation> relationList, boolean compensateFlag) {
		FinanceRepayCalVO financeRepayCalVO = new FinanceRepayCalVO();
		List<LnCompensateRelation> compensateList = new ArrayList<LnCompensateRelation>();
		Double sumAmout = 0d; //协议利率算得本息和
		Double sumPrincipal = 0d;
		LnSubjectExample lnSubjectExample = new LnSubjectExample();
		lnSubjectExample.createCriteria().andChargeRuleIdEqualTo(lnLoan.getChargeRuleId());
		List<LnSubject> lnSubjectList = lnSubjectMapper.selectByExample(lnSubjectExample);

		Double interestRate = 0d;
		for (LnSubject lnSubject : lnSubjectList) {
			if (Constants.SUBJECT_INTEREST.equals(lnSubject.getSubjectCode())) {
				interestRate = lnSubject.getNumValue();
			}
		}
		interestRate = MoneyUtil.divide(interestRate, 10000, 4).doubleValue();
		interestRate = MoneyUtil.multiply(interestRate,12).doubleValue();

		for (LnLoanRelation lnLoanRelation : relationList) {
            if(lnLoanRelation.getTotalAmount() > 0){// 过滤已债转的债权关系
                Double initAmount = lnLoanRelation.getInitAmount() != null ? lnLoanRelation.getInitAmount():lnLoanRelation.getTotalAmount();
                AverageCapitalPlusInterestVO plusInterestVO = algorithmService.calAverageCapitalPlusInterestPlan4Serial(initAmount,
                        lnLoan.getPeriod(), interestRate, serialId);
                LnFinanceRepayScheduleExample financeRepayScheduleExample = new LnFinanceRepayScheduleExample();
                financeRepayScheduleExample.createCriteria().andRelationIdEqualTo(lnLoanRelation.getId()).andRepaySerialEqualTo(serialId);
                List<LnFinanceRepaySchedule> list = financeRepayScheduleMapper.selectByExample(financeRepayScheduleExample);
                if (CollectionUtils.isNotEmpty(list)) {
                    LnFinanceRepaySchedule lnFinanceRepaySchedule = list.get(0);
                    LnFinanceRepaySchedule lnFinanceRepayScheduleTemp = new LnFinanceRepaySchedule();
                    lnFinanceRepayScheduleTemp.setId(lnFinanceRepaySchedule.getId());
                    lnFinanceRepayScheduleTemp.setPlanFee(MoneyUtil.subtract(plusInterestVO.getPlanTotal(), lnFinanceRepaySchedule.getPlanTotal()).doubleValue());
                    lnFinanceRepayScheduleTemp.setPlanTotal(plusInterestVO.getPlanTotal());
                    lnFinanceRepayScheduleTemp.setStatus(Constants.FINANCE_REPAY_SATAE_REPAYING);
                    lnFinanceRepayScheduleTemp.setUpdateTime(new Date());
                    financeRepayScheduleMapper.updateByPrimaryKeySelective(lnFinanceRepayScheduleTemp);
                    sumPrincipal = MoneyUtil.add(lnFinanceRepaySchedule.getPlanPrincipal(), sumPrincipal).doubleValue();
                }else{
                    throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
                }
                sumAmout =MoneyUtil.add(plusInterestVO.getPlanTotal(), sumAmout).doubleValue();

                if(compensateFlag){
                    //生成ln_compensate_relation相关信息并返回
                    LnCompensateRelation lnCompensateRelation = new LnCompensateRelation();
                    BsSysConfig config = sysConfigService.findConfigByKey(Constants.ZAN_COMPENSATE_USER_ID);
                    Integer compUserId = config == null ? 0 : Integer.valueOf(config.getConfValue());
                    BsHfbankUserExtExample extExample = new BsHfbankUserExtExample();
                    extExample.createCriteria().andUserIdEqualTo(compUserId);
                    List<BsHfbankUserExt> ext = bsHfbankUserExtMapper.selectByExample(extExample);
                    if (CollectionUtils.isEmpty(ext)) {
                        throw new PTMessageException(PTMessageEnum.HF_NOT_BIND);
                    }
                    //计算计息天数
                    Integer interestDay = 30;
                    if(serialId > 1){
                        LnFinanceRepayScheduleExample financeRepayScheduleBeforeExample = new LnFinanceRepayScheduleExample();
                        financeRepayScheduleBeforeExample.createCriteria().andRelationIdEqualTo(lnLoanRelation.getId()).andRepaySerialEqualTo(serialId-1);
                        List<LnFinanceRepaySchedule> schedulList = financeRepayScheduleMapper.selectByExample(financeRepayScheduleExample);
                        if (CollectionUtils.isEmpty(schedulList)) {
                            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
                        }
                        interestDay = com.pinting.core.util.DateUtil.getDiffeDay(list.get(0).getPlanDate(), schedulList.get(0).getPlanDate());
                    }else{
                        LnLoan loan = loanMapper.selectByPrimaryKey(lnLoanRelation.getLoanId());
                        interestDay = com.pinting.core.util.DateUtil.getDiffeDay(list.get(0).getPlanDate(), loan.getLoanTime());
                    }


                    lnCompensateRelation.setLoanRelationId(lnLoanRelation.getId());
                    lnCompensateRelation.setCompUserId(compUserId);
                    lnCompensateRelation.setPartnerCode(Constants.PROPERTY_SYMBOL_ZAN);
                    lnCompensateRelation.setCompHfUserId(ext.get(0).getHfUserId());
                    lnCompensateRelation.setAmount(plusInterestVO.getPlanTotal());
                    lnCompensateRelation.setPrincipal(plusInterestVO.getPlanPrincipal());
                    lnCompensateRelation.setInterest(plusInterestVO.getPlanInterest());
                    lnCompensateRelation.setInterestDay(interestDay);
                    lnCompensateRelation.setCreateTime(new Date());
                    lnCompensateRelation.setUpdateTime(new Date());
                    compensateList.add(lnCompensateRelation);
                }
            }
		}
        financeRepayCalVO.setList(compensateList);
		financeRepayCalVO.setAgreementSumAmount(sumAmout);
		financeRepayCalVO.setFinanceSumPrincipal(sumPrincipal);
		return financeRepayCalVO;
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
	public void overdueRepay(LnLoan lnLoan, LnRepaySchedule repaySchedule,
			Double repayAmount, String payOrderNo, List<LnLoanRelation> relationList,
			boolean isOffLine) {
		/**
		 * 计算
		 * 1、保证金=借款总本金*3%/12
		 * 2、赞分期营收 = 还款总金额 - 还款本金-每笔债权协议利率之和  -保证金
		 * 3、ln_repay_schedule更新状态为逾期已还 ；
		 * 4、新增存管还款计划表及明细
		 * 5、调用系统记账
		 */

		//保证金
		Double ZANDeposit = algorithmService.calZANDeposit(lnLoan.getId(), repaySchedule.getSerialId());
		
		//债权协议利率之和（包括本金）
		LnDepositionRepayScheduleExample depSchedulExample = new LnDepositionRepayScheduleExample();
		depSchedulExample.createCriteria().andLoanIdEqualTo(lnLoan.getId()).andPartnerRepayIdEqualTo(repaySchedule.getPartnerRepayId());
		List<LnDepositionRepaySchedule> depSchedulList = lnDepositionRepayScheduleMapper.selectByExample(depSchedulExample);

		Double agreementAmount = depSchedulList.get(0).getPlanTotal();

		//赞分期营收
		Double ZANRevenue = MoneyUtil.subtract(
				MoneyUtil.subtract(repayAmount, agreementAmount).doubleValue(),ZANDeposit).doubleValue();
		if(isOffLine){
			//线下还款，逾期还款营收记账
			BsRevenueTransDetail bsRevenueTransDetail = new BsRevenueTransDetail();
    		bsRevenueTransDetail.setPartnerCode(PartnerEnum.ZAN.getCode());
    		bsRevenueTransDetail.setTransType(Constants.REVENUE_TYPE_OVERDUE_REPAY_INCOME);
    		bsRevenueTransDetail.setLoanId(lnLoan.getId());
    		bsRevenueTransDetail.setRepaySerial(repaySchedule.getSerialId());
    		bsRevenueTransDetail.setRepayScheduleId(repaySchedule.getId());
    		bsRevenueTransDetail.setRepayAmount(repayAmount);
    		bsRevenueTransDetail.setRevenueAmount(ZANRevenue);
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
		scheduleMapper.updateByPrimaryKeySelective(lnRepayScheduleTemp);

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
		//判断是否在存管上线前已代偿
		if(Constants.LN_REPAY_LATE_NOT.equals(repaySchedule.getStatus()) &&
				repaySchedule.getPlanDate().compareTo(DateUtil.parseDate(Constants.CUNGUAN_UP_DATE)) <= 0){
			schedul.setReturnFlag(Constants.DEP_REPAY_RETURN_FLAG_DF_PENDING + "_1");
		}else{
			schedul.setReturnFlag(Constants.DEP_REPAY_RETURN_FLAG_DF_PENDING);
		}
		schedul.setRepayType(Constants.LN_DEP_REPAY_SCHEDULE_REPAY_TYPE_LATE_REPAY);
		schedul.setCreateTime(new Date());
		schedul.setUpdateTime(new Date());
		lnDepositionRepayScheduleMapper.insertSelective(schedul);

		//还款本金
		Double repayPrincipal = getRepayDetailAmount(repaySchedule.getId(), LoanSubjects.SUBJECT_CODE_PRINCIPAL);
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
		repayInfo.setPartner(PartnerEnum.ZAN);
        repayInfo.setAmount(repayAmount);
        repayInfo.setLoanActId(relationList.get(0).getLnSubAccountId());
        repayInfo.setPrincipal(getRepayDetailAmount(repaySchedule.getId(), LoanSubjects.SUBJECT_CODE_PRINCIPAL));
        repayInfo.setInterest(MoneyUtil.subtract(agreementAmount, repayInfo.getPrincipal()).doubleValue());
        repayInfo.setFee(0d);
        repayInfo.setBailAmount(ZANDeposit);
        repayInfo.setChargeRuleId(lnLoan.getChargeRuleId());
        repayInfo.setThdRepayAmount(agreementAmount);
        repayInfo.setRevenueZanAmount(ZANRevenue);

        repayAccountService.chargeOverdueRepay(repayInfo);
	}

	@Override
	public void overdueRepay2Compensate(
			LnDepositionRepaySchedule depRepaySchedule,String lnHfUserId, String compensateHfUserId,
			Integer lnDEPJSHId, Integer compensateDEPJSHId) {
		String trsOrderNo = Util.generateOrderNo4BaoFoo(8);
		/**
		 * ln_deposition_repay_schedule对应账单更新return_order_no订单号、return_flag更新为RETURN_PROCESS
		 * 发起借款人还款到代偿人请求
		 * 借款人还款到代偿人通讯异常：错误日志，return；
		 * 借款人还款到代偿人成功部分：更新存管账单表return_flag状态为RETURN_SUCC，借款人DEP_JSH-，代偿人DEP_JSH+，系统用户余额户+
		 * 借款人还款到代偿人失败部分：更新存管账单表return_flag状态为REPAY_FAIL
		 */
		LnDepositionRepaySchedule depRepayScheduleTemp = new LnDepositionRepaySchedule();
		depRepayScheduleTemp.setId(depRepaySchedule.getId());
		depRepayScheduleTemp.setReturnOrderNo(trsOrderNo);
		depRepayScheduleTemp.setReturnFlag(Constants.DEP_REPAY_RETURN_FLAG_RETURN_PROCESS);
		depRepayScheduleTemp.setUpdateTime(new Date());
		lnDepositionRepayScheduleMapper.updateByPrimaryKeySelective(depRepayScheduleTemp);


		LnDepositionTarget depositionTarget = lnDepositionTargetMapper.selectByLoanId(depRepaySchedule.getLoanId());

		depRepayScheduleTemp = lnDepositionRepayScheduleMapper.selectByPrimaryKey(depRepaySchedule.getId());

		B2GReqMsg_HFBank_RepayCompensate repayCompensateReq = new B2GReqMsg_HFBank_RepayCompensate();
		repayCompensateReq.setOrder_no(trsOrderNo);
		repayCompensateReq.setPartner_trans_date(new Date());
		repayCompensateReq.setPartner_trans_time(new Date());
		repayCompensateReq.setProd_id(depositionTarget.getId().toString());
		repayCompensateReq.setRepay_amt(depRepayScheduleTemp.getPlanTotal());
		repayCompensateReq.setPlatcust(lnHfUserId);
		repayCompensateReq.setCompensation_platcust(compensateHfUserId);
		repayCompensateReq.setRemark("借款人还款到代偿人");
		B2GResMsg_HFBank_RepayCompensate repayCompensateRes = null;
        try{
            repayCompensateRes = hfbankTransportService.repayCompensate(repayCompensateReq);
        }catch (Exception e){
            log.error("【借款人还款到代偿人】通讯异常：", e);
            return;
        }
        if (ConstantUtil.BSRESCODE_SUCCESS.equals(repayCompensateRes.getResCode())) {
			//成功:更新存管账单表return_flag状态为RETURN_SUCC，借款人DEP_JSH-，代偿人DEP_JSH+，系统用户余额户+
			LnDepositionRepaySchedule depRepayScheduleSuccTemp = new LnDepositionRepaySchedule();
			depRepayScheduleSuccTemp.setId(depRepaySchedule.getId());
            depRepayScheduleSuccTemp.setStatus(Constants.LN_REPAY_LATE_REPAIED);
			depRepayScheduleSuccTemp.setReturnFlag(Constants.DEP_REPAY_RETURN_FLAG_RETURN_SUCC);
            depRepayScheduleSuccTemp.setFinishTime(new Date());
			depRepayScheduleSuccTemp.setUpdateTime(new Date());
			lnDepositionRepayScheduleMapper.updateByPrimaryKeySelective(depRepayScheduleSuccTemp);
			//记账
			repayAccountService.chargeRepay2CcompensateSuccAct(lnDEPJSHId,compensateDEPJSHId,depRepaySchedule.getPlanTotal());
		}else if(LoanStatus.BAOFOO_PAY_STATUS_PROCESS.getCode().equals(repayCompensateRes.getResCode())) {
            //超时告警
            specialJnlService.warn4FailNoSMS(null, "借款人还款到代偿人超时", "标的编号：[" + depositionTarget.getId() + "]", "【借款人还款到代偿人】");
            log.info("借款人还款到代偿人超时:" + "标的编号：[" + depositionTarget.getId() + "]");
            return;
        }else{
			//失败：更新存管账单表return_flag状态为REPAY_FAIL （由于逾期还款没有return步骤，所以失败时flag需要改成repay_fail）
			LnDepositionRepaySchedule depRepayScheduleFailTemp = new LnDepositionRepaySchedule();
			depRepayScheduleFailTemp.setId(depRepaySchedule.getId());
			depRepayScheduleFailTemp.setReturnFlag(Constants.DEP_REPAY_RETURN_FLAG_REPAY_FAIL);
			depRepayScheduleFailTemp.setUpdateTime(new Date());
			lnDepositionRepayScheduleMapper.updateByPrimaryKeySelective(depRepayScheduleFailTemp);
		}
	}

	@Override
	public String withholdingRepayOffLine(G2BReqMsg_Repay_WithholdingRepay req){

        try {
            LnUser lnUser = loanUserService.queryLoanUserExist(req.getUserId(), PartnerEnum.ZAN.getCode());
            if(lnUser == null){
                throw new PTMessageException(PTMessageEnum.ZAN_ADDLOANCIF_USER_NOT_EXIST);
            
            }else{
                //白名单
                if (!loanUserMobileWhiteListCheckService.lnMobileWhiteListCheck(lnUser.getMobile())) {
                    throw new PTMessageException(PTMessageEnum.ZAN_LOAN_USER_NOT_FOUND_IN_WHITE);
                }
            }
            LnRepayExample repayExample = new LnRepayExample();
            repayExample.createCriteria().andPartnerOrderNoEqualTo(req.getOrderNo());
            List<LnRepay> repayList = repayMapper.selectByExample(repayExample);
            
            if (CollectionUtils.isNotEmpty(repayList) && repayList.size() > 0) {
                LnRepay repay = repayList.get(0);
                
                LnPayOrdersExample ordersExample = new LnPayOrdersExample();
                ordersExample.createCriteria().andOrderNoEqualTo(repay.getPayOrderNo());
                List<LnPayOrders> orders = payOrdersMapper.selectByExample(ordersExample);
                
                if(!CollectionUtils.isEmpty(orders)){
                	 LnPayOrders order = orders.get(0);
                     if(order.getStatus() == Integer.parseInt(LoanStatus.ORDERS_STATUS_SUCCESS.getCode()) ||
                             order.getStatus() == Integer.parseInt(LoanStatus.ORDERS_STATUS_PAYING.getCode())){
                         //订单已成功或处理中时，直接返回
                    	 throw new PTMessageException(PTMessageEnum.TRANS_ERROR, "：订单已成功或处理中"+repay.getBgwOrderNo());
                    	 //return repay.getBgwOrderNo();
                     }else{
                         //订单非成功非处理中时，返回错误信息
                         String errorMsg = order.getReturnMsg();
                         throw new PTMessageException(PTMessageEnum.TRANS_ERROR, "：" + errorMsg);
                     }
                }else {
                	throw new PTMessageException(PTMessageEnum.TRANS_ERROR, "：重复的线下还款请求" );
				}
               
            }

            //检查绑卡表，如果客户对应的卡未绑定，则返回相应错误
            LnBindCard lnBindCard = loanUserService.queryLoanBindCardExist(req.getUserId(), req.getBindId(), req.getChannel());
            if (lnBindCard == null) {
                throw new PTMessageException(PTMessageEnum.ZAN_LOAN_USER_CARD_UNBIND);
            }

            if (CollectionUtils.isNotEmpty(req.getRepayments()) && req.getRepayments().size() > 0) {

                //查询借款信息
                LnLoanExample loanExample = new LnLoanExample();
                loanExample.createCriteria().andPartnerLoanIdEqualTo(req.getLoanId()).andStatusEqualTo(LoanStatus.LOAN_STATUS_PAIED.getCode());
                List<LnLoan> loanList = loanMapper.selectByExample(loanExample);
                if (CollectionUtils.isEmpty(loanList) || loanList.size() == 0) {
                    throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND, "找不到借款信息");
                }


                //支付订单号
                String payOrderNo = Util.generateSysOrderNo(Constants.BGW_ORDER_NUMBER_GENERATE_OFFLINE_REPAY);
                //记录还款信息表id
                List<Integer> repayIds = new ArrayList<>();

                String bgwOrderNo = null;
                
                boolean  checkFlag = true;
                String  checkErrorMsg = "";
                
                for (Repayment repayment : req.getRepayments()) {
                    log.info("==========================还款明细:" + JSONObject.fromObject(repayment).toString());
                    //查询对应的还款计划表      
                    LnRepayScheduleExample scheduleExample = new LnRepayScheduleExample();
                    scheduleExample.createCriteria().andLoanIdEqualTo(loanList.get(0).getId()).andPartnerRepayIdEqualTo(repayment.getRepayId());
                    List<LnRepaySchedule> repayScheduleList = scheduleMapper.selectByExample(scheduleExample);
                    
                    Integer repayPlanId = null;
                    if (!CollectionUtils.isEmpty(repayScheduleList)) {
                    	repayPlanId = repayScheduleList.get(0).getId();
					}
                    log.info("=====================================线下还款不进行金额比较===============================");
                    //判断还款的每一项是否与还款计划详情一致(线下还款不进行金额比较)


                    bgwOrderNo = Util.generateSysOrderNo(Constants.BGW_ORDER_NUMBER_GENERATE_OFFLINE_REPAY);
                    //记录ln_repay表，状态为还款中
                    LnRepay repay = new LnRepay();
                    repay.setBgwOrderNo(bgwOrderNo);
                    repay.setRepayPlanId(repayPlanId);
                    repay.setStatus(LoanStatus.REPAY_STATUS_PAYING.getCode());
                    repay.setUpdateTime(new Date());
                    repay.setCreateTime(new Date());
                    repay.setBgwBindId(req.getBindId());
                    repay.setDoneTotal(MoneyUtil.divide(repayment.getTotal().toString(), "100").doubleValue());
                    repay.setLnUserId(lnBindCard.getLnUserId());
                    repay.setPayOrderNo(payOrderNo);
                    repay.setLoanId(loanList.get(0).getId());
                    repay.setPartnerOrderNo(req.getOrderNo());
                    repay.setInformStatus(LoanStatus.NOTICE_STATUS_INIT.getCode());
                    repay.setRepayType(Constants.IS_OFFLINE_REPAY);
                    repayMapper.insertSelective(repay);
                    log.info("==========================还款信息表:" + JSONObject.fromObject(repay).toString());

                    repayIds.add(repay.getId());
                    //记录还款计划明细表ln_repay_detail
                    LnRepayDetail lnRepayDetail = new LnRepayDetail();
                    lnRepayDetail.setUpdateTime(new Date());
                    lnRepayDetail.setCreateTime(new Date());
                    lnRepayDetail.setRepayId(repay.getId());
                    lnRepayDetail.setDoneAmount(MoneyUtil.divide(repayment.getPrincipal(), 100).doubleValue());
                    lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_PRINCIPAL.getCode());
                    detailMapper.insertSelective(lnRepayDetail);
                    //利息
                    lnRepayDetail.setDoneAmount(MoneyUtil.divide(repayment.getInterest(), 100).doubleValue());
                    lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_INTEREST.getCode());
                    detailMapper.insertSelective(lnRepayDetail);
                    //监管费
                    lnRepayDetail.setDoneAmount(repayment.getSuperviseFee() != null ? MoneyUtil.divide(repayment.getSuperviseFee(), 100).doubleValue() : 0);
                    lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_SUPERVISE_FEE.getCode());
                    detailMapper.insertSelective(lnRepayDetail);
                    //信息服务费
                    lnRepayDetail.setDoneAmount(MoneyUtil.divide(repayment.getInfoServiceFee(), 100).doubleValue());
                    lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_INFO_SERVICE_FEE.getCode());
                    detailMapper.insertSelective(lnRepayDetail);
                    //账户管理费
                    lnRepayDetail.setDoneAmount(MoneyUtil.divide(repayment.getAccountManageFee(), 100).doubleValue());
                    lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_ACCOUNT_MANAGE_FEE.getCode());
                    detailMapper.insertSelective(lnRepayDetail);
                    //保费
                    lnRepayDetail.setDoneAmount(MoneyUtil.divide(repayment.getPremium(), 100).doubleValue());
                    lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_PREMIUM.getCode());
                    detailMapper.insertSelective(lnRepayDetail);
                    //其他
                    lnRepayDetail.setDoneAmount(MoneyUtil.divide(repayment.getOther(), 100).doubleValue());
                    lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_OTHER.getCode());
                    detailMapper.insertSelective(lnRepayDetail);

                    //滞纳金
                    lnRepayDetail.setDoneAmount(MoneyUtil.divide(repayment.getLateFee(), 100).doubleValue());
                    lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_LATE_FEE.getCode());
                    detailMapper.insertSelective(lnRepayDetail);
                    
                    if (CollectionUtils.isEmpty(repayScheduleList)) {
                    	LnRepay repayRepaied = new LnRepay();
                    	repayRepaied.setId(repay.getId());
                    	repayRepaied.setStatus(LoanStatus.REPAY_STATUS_FAIL.getCode());
                    	repayRepaied.setUpdateTime(new Date());
                    	repayMapper.updateByPrimaryKeySelective(repayRepaied);
                    	
                    	String tmp = "找不到对应的还款账单[" + repayment.getRepayId() + "]";
                    	checkFlag = false;
                    	checkErrorMsg = checkErrorMsg+"|"+tmp;
                        //throw new PTMessageException(PTMessageEnum.ZAN_OFFLINE_REPAY_PLAN_ID_EMPTY_ERROR, "找不到对应的还款账单[" + repayment.getRepayId() + "]");
					}
                    
                    //判断是否已还过款
                    if (LoanStatus.REPAY_SCHEDULE_STATUS_REPAIED.getCode().equals(repayScheduleList.get(0).getStatus()) ||
                    		LoanStatus.REPAY_SCHEDULE_STATUS_LATE_REPAIED.getCode().equals(repayScheduleList.get(0).getStatus())) {
                    	LnRepay repayRepaied = new LnRepay();
                    	repayRepaied.setId(repay.getId());
                    	repayRepaied.setStatus(LoanStatus.REPAY_STATUS_FAIL.getCode());
                    	repayRepaied.setUpdateTime(new Date());
                    	repayMapper.updateByPrimaryKeySelective(repayRepaied);
                    	
                    	String tmp =  "账单已还款[" + repayment.getRepayId() + "]";
                    	checkFlag = false;
                    	checkErrorMsg = checkErrorMsg+"|"+tmp;
                        //throw new PTMessageException(PTMessageEnum.ZAN_REPAY_PAYMENT_ORDER_DUPLICATE, "[" + repayment.getRepayId() + "]");
                    }
                    
                    if (LoanStatus.REPAY_SCHEDULE_STATUS_INIT.getCode().equals(repayScheduleList.get(0).getStatus())) {
                    	LnRepay repayRepaied = new LnRepay();
                    	repayRepaied.setId(repay.getId());
                    	repayRepaied.setStatus(LoanStatus.REPAY_STATUS_FAIL.getCode());
                    	repayRepaied.setUpdateTime(new Date());
                    	repayMapper.updateByPrimaryKeySelective(repayRepaied);
                    	String tmp =  "账单状态为INIT [" + repayment.getRepayId() + "]";
                    	checkFlag = false;
                    	checkErrorMsg = checkErrorMsg+"|"+tmp;
                        //throw new PTMessageException(PTMessageEnum.ZAN_OFFLINE_REPAY_STATUS_ERROR,"账单状态为INIT [" + repayment.getRepayId() + "]");
                    }
                    
                    if (!LoanStatus.REPAY_SCHEDULE_STATUS_LATE_NOT.getCode().equals(repayScheduleList.get(0).getStatus())) {
                    	LnRepay repayRepaied = new LnRepay();
                    	repayRepaied.setId(repay.getId());
                    	repayRepaied.setStatus(LoanStatus.REPAY_STATUS_FAIL.getCode());
                    	repayRepaied.setUpdateTime(new Date());
                    	repayMapper.updateByPrimaryKeySelective(repayRepaied);
                    	
                    	String tmp =  "账单状态不为LATE_NOT[" + repayment.getRepayId() + "]";
                    	checkFlag = false;
                    	checkErrorMsg = checkErrorMsg+"|"+tmp;
                        //throw new PTMessageException(PTMessageEnum.ZAN_OFFLINE_REPAY_STATUS_ERROR,"账单状态不为LATE_NOT [" + repayment.getRepayId() + "]");
                    }

                }
                
                if (!checkFlag) {
                	throw new PTMessageException(PTMessageEnum.ZAN_OFFLINE_REPAY_SCEDULE_CHECK_ERROR,checkErrorMsg);
				}

                //根据借款支付订单号，查询子账户id
                LnPayOrdersExample ordersExample = new LnPayOrdersExample();
                ordersExample.createCriteria().andOrderNoEqualTo(loanList.get(0).getPayOrderNo());
                List<LnPayOrders> ordersList = payOrdersMapper.selectByExample(ordersExample);

                //记录ln_pay_orders,ln_pay_orders_jnl表
                LnPayOrders lnPayOrders = new LnPayOrders();
                lnPayOrders.setCreateTime(new Date());
                lnPayOrders.setAccountType(1);
                lnPayOrders.setAmount(MoneyUtil.divide(req.getTotalAmount(), "100").doubleValue());

                lnPayOrders.setBankCardNo(lnBindCard.getBankCard());
                BsCardBin bsCardBin = bankCardService.queryBankBin(lnBindCard.getBankCard());
                lnPayOrders.setBankName(lnBindCard.getBankName());
                lnPayOrders.setIdCard(lnBindCard.getIdCard());
                lnPayOrders.setLnUserId(lnBindCard.getLnUserId());
                lnPayOrders.setMobile(lnBindCard.getMobile());
                lnPayOrders.setUserName(lnBindCard.getUserName());
                lnPayOrders.setBankId(bsCardBin.getBankId());
                lnPayOrders.setChannel(Constants.CHANNEL_BAOFOO);
                //渠道交易类型为代扣
                lnPayOrders.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DK.getCode());
                lnPayOrders.setMoneyType(0);
                lnPayOrders.setOrderNo(payOrderNo);
                lnPayOrders.setPartnerCode(req.getChannel());
                lnPayOrders.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_SUCCESS.getCode()));
                lnPayOrders.setSubAccountId(ordersList.get(0).getSubAccountId());
                lnPayOrders.setTransType(LoanStatus.TRANS_TYPE_REPAY.getCode());
                lnPayOrders.setUpdateTime(new Date());
                lnPayOrders.setReturnCode(ConstantUtil.BSRESCODE_SUCCESS);
                lnPayOrders.setReturnMsg(ConstantUtil.DEFAULT_SUCESSMSG);
                lnPayOrders.setNote(Constants.OFFLINE_ZAN_ORDER_NOTE);
                
                // 资产方线下还款现在走的主通道，设置为默认值
                lnPayOrders.setPaymentChannelId(1);
                payOrdersMapper.insertSelective(lnPayOrders);
                log.info("===========================还款订单表插入记录：" + JSONObject.fromObject(lnPayOrders).toString());

                LnPayOrdersJnl lnPayOrdersJnl = new LnPayOrdersJnl();
                lnPayOrdersJnl.setSubAccountId(ordersList.get(0).getSubAccountId());
                lnPayOrdersJnl.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DK.getCode());
                lnPayOrdersJnl.setCreateTime(new Date());
                lnPayOrdersJnl.setOrderId(lnPayOrders.getId());
                lnPayOrdersJnl.setOrderNo(lnPayOrders.getOrderNo());
                lnPayOrdersJnl.setTransAmount(lnPayOrders.getAmount());
                lnPayOrdersJnl.setTransCode(LoanStatus.ORDERS_JNL_STATUS_SUCCESS.getCode());
                lnPayOrdersJnl.setUserId(lnBindCard.getLnUserId());
                lnPayOrdersJnl.setSysTime(new Date());
                lnPayOrdersJnl.setReturnCode("000000");
                lnPayOrdersJnl.setReturnMsg("线下还款默认成功");
                payOrdersJnlMapper.insertSelective(lnPayOrdersJnl);
                
                
                //----------------------------------------------------------------


                //根据支付方订单号查询还款信息表
                LnRepayExample example = new LnRepayExample();
                example.createCriteria().andPayOrderNoEqualTo(lnPayOrders.getOrderNo());
                List<LnRepay> repayLists = repayMapper.selectByExample(example);

                // 根据订单号查询每一期计划利息和计划本金
                List<RepayInfoVO> repayInfoVOs = repayMapper.selectRepayInfoGroup(lnPayOrders.getOrderNo());

                if (CollectionUtils.isEmpty(repayLists)) {
                    throw new PTMessageException(PTMessageEnum.REPAY_DATA_NOT_FOUND, "无还款记录");
                }
                LnRepay lnRepay = repayLists.get(0);

                //查询借款信息表
                LnLoan lnLoan = loanMapper.selectByPrimaryKey(lnRepay.getLoanId());

                for (final LnRepay repay : repayLists) {

                    //查询借贷关系表
                    LnLoanRelationExample relationExample = new LnLoanRelationExample();
                    relationExample.createCriteria().andLoanIdEqualTo(lnRepay.getLoanId()).andLnUserIdEqualTo(lnRepay.getLnUserId());
                    List<LnLoanRelation> relationList = relationMapper.selectByExample(relationExample);

                    //修改还款信息表状态
                    LnRepay repayTemp = new LnRepay();
                    repayTemp.setId(repay.getId());
                    repayTemp.setUpdateTime(new Date());
                    repayTemp.setStatus(LoanStatus.REPAY_STATUS_REPAIED.getCode());
                    repayTemp.setDoneTime(new Date());
                    repayMapper.updateByPrimaryKeySelective(repayTemp);
                    lnRepay.setStatus(repayTemp.getStatus());


                    LnRepayDetailExample detailExample = new LnRepayDetailExample();
                    detailExample.createCriteria().andRepayIdEqualTo(repay.getId());
                    List<LnRepayDetail> detailList = detailMapper.selectByExample(detailExample);

                    //查询还款计划表
                    final LnRepaySchedule repaySchedule = scheduleMapper.selectByPrimaryKey(repay.getRepayPlanId());

                    RepayInfo repayInfo = new RepayInfo();
                    repayInfo.setPartner(PartnerEnum.getEnumByCode(lnPayOrders.getPartnerCode()));
                    repayInfo.setChargeRuleId(lnLoan.getChargeRuleId());
                    //借款用户子账户编号
                    repayInfo.setLoanActId(relationList.get(0).getLnSubAccountId());
                    //发生金额
                    repayInfo.setAmount(repay.getDoneTotal());

                    //理财人利息
                    Double interest = 0d;
                    //理财人本金
                    Double principal = 0d;

                    for (LnRepayDetail detail : detailList) {

                        //逾期费
                        if (detail.getSubjectCode().equals(LoanSubjects.SUBJECT_CODE_LATE_FEE.getCode())) {
                            repayInfo.setOverdueAmount(detail.getDoneAmount());
                        }

                    }

                    //币港湾服务费(不是信息服务费)
                    repayInfo.setServiceFee(algorithmService.calBGWServiceFee(lnLoan.getId(), repaySchedule.getSerialId()));

                    //保证金 (不是保费)
                    repayInfo.setBailAmount(algorithmService.calZANDeposit(lnLoan.getId(), repaySchedule.getSerialId()));

                    CommissionVO commissionVO = new CommissionVO();
                    //手续费- 代扣还款
                    commissionVO = commissionService.calServiceFee(loanUserService.calTotalRepay(lnLoan.getId(), repaySchedule.getSerialId()), TransTypeEnum.ZAN_REPAY_DK, PayPlatformEnum.BAOFOO);
                    repayInfo.setFee(commissionVO.getActPayAmount() == null ? 0d : commissionVO.getActPayAmount());

                    //记录手续费
                    BsServiceFee bsServiceFee = new BsServiceFee();
                    bsServiceFee.setPlanFee(commissionVO.getNeedPayAmount());
                    bsServiceFee.setDoneFee(commissionVO.getActPayAmount());
                    bsServiceFee.setTransAmount(repay.getDoneTotal());
                    bsServiceFee.setFeeType(Constants.FEE_TYPE_REPAY);
                    bsServiceFee.setStatus(Constants.FEE_STATUS_COLLECT);
                    bsServiceFee.setCreateTime(new Date());
                    bsServiceFee.setOrderNo(repay.getPayOrderNo());
                    bsServiceFee.setSubAccountId(relationList.get(0).getLnSubAccountId());
                    bsServiceFee.setUpdateTime(new Date());
                    bsServiceFee.setNote("应扣" + commissionVO.getNeedPayAmount() + "，实扣" + commissionVO.getActPayAmount());
                    bsServiceFee.setPaymentPlatformFee(commissionVO.getThreePartyPaymentServiceFee());
                    serviceFeeMapper.insertSelective(bsServiceFee);


                    repayInfo.setInterest(interest);
                    repayInfo.setPrincipal(principal);
                    if(!CollectionUtils.isEmpty(repayInfoVOs)) {
                        for(RepayInfoVO vos : repayInfoVOs) {
                            if(vos.getSerialId().equals(repaySchedule.getSerialId())) {
                                repayInfo.setInterest(vos.getPlanInterest());
                                repayInfo.setPrincipal(vos.getPlanPrincipal());
                                break;
                            }
                        }
                    }

                	overdueRepay(lnLoan, repaySchedule, repay.getDoneTotal(), repay.getPayOrderNo(), relationList ,true);

                
                }

                //起线程通知
                RepayProcess process = new RepayProcess();
                process.setRepay(lnRepay);
                process.setRepayPaymentService(this);
                process.setErrMsg("");
                new Thread(process).start();
            
                //----------------------------------------------------------------
                
                return bgwOrderNo;

            } else {
                throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "还款数据列表为空");
            }

        } finally {

        }
    }

	@Override
	public void normalRepaySysSplit4ZANNew(LnLoan lnLoan,
			LnRepaySchedule repaySchedule, Double repayAmount,
			String payOrderNo, List<LnLoanRelation> relationList) {
		/**
		 * 计算1、币港湾结算=借款总本金/期数+借款总本金*名义月利率；名义月利率=15%*（期数+1）/（期数*24）
		 * 2、保证金=借款总本金*3%/12
		 * 3、赞分期营收 = 还款总金额 - 币港湾结算 -保证金
		 * 4、币港湾营收=币港湾结算-每笔债权协议利率之和
		 * 5、记录ZAN还款营收收入 、ln_repay_schedule更新状态为已还
		 * 6、调用系统记账
		 */

		//币港湾结算
		Double BGWSettlementFee = algorithmService.calBGWSettlementFee(lnLoan.getId(), repaySchedule.getSerialId());
		//保证金
		Double ZANDeposit = algorithmService.calZANDeposit(lnLoan.getId(), repaySchedule.getSerialId());
		//赞分期营收
		Double ZANRevenue = MoneyUtil.subtract(
				MoneyUtil.subtract(repayAmount, BGWSettlementFee).doubleValue(),ZANDeposit).doubleValue();
		
		//币港湾营收
		Double BGWRevenue = BGWSettlementFee;
		
		
        //1、记录ZAN的还款营收收入 2、ln_repay_schedule更新状态为已还
        //bs_revenue_trans_detail记录合作方还款营收收入
        BsRevenueTransDetail bsRevenueTransDetail = new BsRevenueTransDetail();
        bsRevenueTransDetail.setPartnerCode(PartnerEnum.ZAN.getCode());
        bsRevenueTransDetail.setTransType(Constants.REVENUE_TYPE_REPAY_INCOME);
        bsRevenueTransDetail.setLoanId(lnLoan.getId());
        bsRevenueTransDetail.setRepaySerial(repaySchedule.getSerialId());
        bsRevenueTransDetail.setRepayScheduleId(repaySchedule.getId());
        bsRevenueTransDetail.setRepayAmount(repayAmount);
        bsRevenueTransDetail.setRevenueAmount(ZANRevenue);
        bsRevenueTransDetail.setRepayScheduleId(repaySchedule.getId());
        bsRevenueTransDetail.setCreateTime(new Date());
        bsRevenueTransDetail.setUpdateTime(new Date());
        bsRevenueTransDetailMapper.insertSelective(bsRevenueTransDetail);

        //ln_repay_schedule更新状态为已还款
        LnRepaySchedule lnRepayScheduleTemp = new LnRepaySchedule();
        lnRepayScheduleTemp.setId(repaySchedule.getId());
        lnRepayScheduleTemp.setFinishTime(new Date());
        lnRepayScheduleTemp.setUpdateTime(new Date());
        lnRepayScheduleTemp.setStatus(Constants.LN_REPAY_REPAIED);
        scheduleMapper.updateByPrimaryKeySelective(lnRepayScheduleTemp);

		//系统记账
        RepayInfo repayInfo = new RepayInfo();
        repayInfo.setPartner(PartnerEnum.ZAN);
        repayInfo.setAmount(repayAmount);
        repayInfo.setLoanActId(relationList.get(0).getLnSubAccountId());
        repayInfo.setPrincipal(getRepayDetailAmount(repaySchedule.getId(), LoanSubjects.SUBJECT_CODE_PRINCIPAL));
        repayInfo.setFee(0d);
        repayInfo.setBailAmount(ZANDeposit);
        repayInfo.setRevenueZanAmount(ZANRevenue);
        repayInfo.setBgwRevenueAmount(BGWRevenue);
        repayInfo.setLnRepayScheduleId(repaySchedule.getId());
        repayAccountService.chargeNormalRepay4ZANNew(repayInfo);
		
	}

	@Override
	@Transactional(noRollbackFor = PTMessageException.class)
	public void normalRepaySysSplit4ZANHF(LnLoan lnLoan,
			LnLoanRepayScheduleVO loanRepaySchedule,
			List<LnLoanRelation> relationList) {
		
		//债权协议利率之和（包括本金）
		FinanceRepayCalVO financeRepayCalVO = do4FinanceRepay(lnLoan, loanRepaySchedule.getSerialId(),relationList,false);
		Double agreementAmount = financeRepayCalVO.getAgreementSumAmount(); //线下还款本息和
		Double sumFinancePrincipal = financeRepayCalVO.getFinanceSumPrincipal(); //应还至理财人的本金和
		
		
        Integer maxSerialId = loanRepaySchedule.getSerialId()-1;
        //存管还款计划表
        LnDepositionRepaySchedule schedul = new LnDepositionRepaySchedule();
  		schedul.setLnUserId(lnLoan.getLnUserId());
  		schedul.setLoanId(lnLoan.getId());
  		schedul.setPartnerRepayId(loanRepaySchedule.getPartnerRepayId());
  		schedul.setSerialId(maxSerialId+1);
  		schedul.setPlanDate(loanRepaySchedule.getPlanDate());
  		schedul.setPlanTotal(agreementAmount);
  		schedul.setStatus(Constants.LN_REPAY_STATUS_INIT);
  		schedul.setReturnFlag(Constants.DEP_REPAY_RETURN_FLAG_DF_PENDING);
  		schedul.setRepayType(Constants.LN_DEP_REPAY_SCHEDULE_REPAY_TYPE_NORMAL_REPAY);
  		schedul.setCreateTime(new Date());
  		schedul.setUpdateTime(new Date());
  		lnDepositionRepayScheduleMapper.insertSelective(schedul);

  		//还款本金
  		Double repayPrincipal = getRepayDetailAmount(loanRepaySchedule.getRepayScheduleId(), LoanSubjects.SUBJECT_CODE_PRINCIPAL);
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

  		//系统记账
        RepayInfo repayInfo = new RepayInfo();
        repayInfo.setPartner(PartnerEnum.ZAN);
        repayInfo.setLoanActId(relationList.get(0).getLnSubAccountId());
        repayInfo.setPrincipal(getRepayDetailAmount(loanRepaySchedule.getRepayScheduleId(), LoanSubjects.SUBJECT_CODE_PRINCIPAL));
        repayInfo.setInterest(MoneyUtil.subtract(agreementAmount, repayInfo.getPrincipal()).doubleValue());
        repayInfo.setChargeRuleId(lnLoan.getChargeRuleId());
        repayInfo.setThdRepayAmount(agreementAmount);
        repayInfo.setFinancePrincipal(sumFinancePrincipal);
        repayAccountService.chargeNormalRepay4ZANHF(repayInfo);
	}

	@Override
	public String withholdingRepayOffLineNew(
			G2BReqMsg_Repay_WithholdingRepay req) throws Exception {
		try {
            LnUser lnUser = loanUserService.queryLoanUserExist(req.getUserId(), PartnerEnum.ZAN.getCode());
            if(lnUser == null){
                throw new PTMessageException(PTMessageEnum.ZAN_ADDLOANCIF_USER_NOT_EXIST);
            
            }else{
                //白名单
                if (!loanUserMobileWhiteListCheckService.lnMobileWhiteListCheck(lnUser.getMobile())) {
                    throw new PTMessageException(PTMessageEnum.ZAN_LOAN_USER_NOT_FOUND_IN_WHITE);
                }
            }
            LnRepayExample repayExample = new LnRepayExample();
            repayExample.createCriteria().andPartnerOrderNoEqualTo(req.getOrderNo());
            List<LnRepay> repayList = repayMapper.selectByExample(repayExample);
            
            if (CollectionUtils.isNotEmpty(repayList) && repayList.size() > 0) {
                LnRepay repay = repayList.get(0);
                
                LnPayOrdersExample ordersExample = new LnPayOrdersExample();
                ordersExample.createCriteria().andOrderNoEqualTo(repay.getPayOrderNo());
                List<LnPayOrders> orders = payOrdersMapper.selectByExample(ordersExample);
                
                if(!CollectionUtils.isEmpty(orders)){
                	 LnPayOrders order = orders.get(0);
                     if(order.getStatus() == Integer.parseInt(LoanStatus.ORDERS_STATUS_SUCCESS.getCode()) ||
                             order.getStatus() == Integer.parseInt(LoanStatus.ORDERS_STATUS_PAYING.getCode())){
                         //订单已成功或处理中时，直接返回
                    	 throw new PTMessageException(PTMessageEnum.TRANS_ERROR, "：订单已成功或处理中"+repay.getBgwOrderNo());
                    	 //return repay.getBgwOrderNo();
                     }else{
                         //订单非成功非处理中时，返回错误信息
                         String errorMsg = order.getReturnMsg();
                         throw new PTMessageException(PTMessageEnum.TRANS_ERROR, "：" + errorMsg);
                     }
                }else {
                	throw new PTMessageException(PTMessageEnum.TRANS_ERROR, "：重复的线下还款请求" );
				}
               
            }
            //赞分期提前赎回时间
            Date finishDate = DateUtil.parseDate("2018-03-15");
            BsSysConfig config = bsSysConfigService.findKey( Constants.ZAN_CALLED_AWAY_DATE );
            if(config != null){
            	finishDate = DateUtil.parseDate(config.getConfValue());
            }
            
            //检查绑卡表，如果客户对应的卡未绑定，则返回相应错误
            LnBindCard lnBindCard = loanUserService.queryLoanBindCardExist(req.getUserId(), req.getBindId(), req.getChannel());
            if (lnBindCard == null) {
                throw new PTMessageException(PTMessageEnum.ZAN_LOAN_USER_CARD_UNBIND);
            }

            if (CollectionUtils.isNotEmpty(req.getRepayments()) && req.getRepayments().size() > 0) {

                //查询借款信息
                LnLoanExample loanExample = new LnLoanExample();
                loanExample.createCriteria().andPartnerLoanIdEqualTo(req.getLoanId()).andStatusEqualTo(LoanStatus.LOAN_STATUS_PAIED.getCode());
                List<LnLoan> loanList = loanMapper.selectByExample(loanExample);
                if (CollectionUtils.isEmpty(loanList) || loanList.size() == 0) {
                    throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND, "找不到借款信息");
                }


                //支付订单号
                String payOrderNo = Util.generateSysOrderNo(Constants.BGW_ORDER_NUMBER_GENERATE_OFFLINE_REPAY);
                //记录还款信息表id
                List<Integer> repayIds = new ArrayList<>();

                String bgwOrderNo = null;
                
                boolean  checkFlag = true;
                String  checkErrorMsg = "";
                
                for (Repayment repayment : req.getRepayments()) {
                    log.info("==========================还款明细:" + JSONObject.fromObject(repayment).toString());
                    //查询对应的还款计划表      
                    LnRepayScheduleExample scheduleExample = new LnRepayScheduleExample();
                    scheduleExample.createCriteria().andLoanIdEqualTo(loanList.get(0).getId()).andPartnerRepayIdEqualTo(repayment.getRepayId());
                    List<LnRepaySchedule> repayScheduleList = scheduleMapper.selectByExample(scheduleExample);
                    
                    Integer repayPlanId = null;
                    if (!CollectionUtils.isEmpty(repayScheduleList)) {
                    	repayPlanId = repayScheduleList.get(0).getId();
					}
                    log.info("=====================================线下还款不进行金额比较===============================");
                    //判断还款的每一项是否与还款计划详情一致(线下还款不进行金额比较)


                    bgwOrderNo = Util.generateSysOrderNo(Constants.BGW_ORDER_NUMBER_GENERATE_OFFLINE_REPAY);
                    //记录ln_repay表，状态为还款中
                    LnRepay repay = new LnRepay();
                    repay.setBgwOrderNo(bgwOrderNo);
                    repay.setRepayPlanId(repayPlanId);
                    repay.setStatus(LoanStatus.REPAY_STATUS_PAYING.getCode());
                    repay.setUpdateTime(new Date());
                    repay.setCreateTime(new Date());
                    repay.setBgwBindId(req.getBindId());
                    repay.setDoneTotal(MoneyUtil.divide(repayment.getTotal().toString(), "100").doubleValue());
                    repay.setLnUserId(lnBindCard.getLnUserId());
                    repay.setPayOrderNo(payOrderNo);
                    repay.setLoanId(loanList.get(0).getId());
                    repay.setPartnerOrderNo(req.getOrderNo());
                    repay.setInformStatus(LoanStatus.NOTICE_STATUS_INIT.getCode());
                    repay.setRepayType(Constants.IS_OFFLINE_REPAY);
                    repayMapper.insertSelective(repay);
                    log.info("==========================还款信息表:" + JSONObject.fromObject(repay).toString());

                    repayIds.add(repay.getId());
                    //记录还款计划明细表ln_repay_detail
                    LnRepayDetail lnRepayDetail = new LnRepayDetail();
                    lnRepayDetail.setUpdateTime(new Date());
                    lnRepayDetail.setCreateTime(new Date());
                    lnRepayDetail.setRepayId(repay.getId());
                    lnRepayDetail.setDoneAmount(MoneyUtil.divide(repayment.getPrincipal(), 100).doubleValue());
                    lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_PRINCIPAL.getCode());
                    detailMapper.insertSelective(lnRepayDetail);
                    //利息
                    lnRepayDetail.setDoneAmount(MoneyUtil.divide(repayment.getInterest(), 100).doubleValue());
                    lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_INTEREST.getCode());
                    detailMapper.insertSelective(lnRepayDetail);
                    //监管费
                    lnRepayDetail.setDoneAmount(repayment.getSuperviseFee() != null ? MoneyUtil.divide(repayment.getSuperviseFee(), 100).doubleValue() : 0);
                    lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_SUPERVISE_FEE.getCode());
                    detailMapper.insertSelective(lnRepayDetail);
                    //信息服务费
                    lnRepayDetail.setDoneAmount(MoneyUtil.divide(repayment.getInfoServiceFee(), 100).doubleValue());
                    lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_INFO_SERVICE_FEE.getCode());
                    detailMapper.insertSelective(lnRepayDetail);
                    //账户管理费
                    lnRepayDetail.setDoneAmount(MoneyUtil.divide(repayment.getAccountManageFee(), 100).doubleValue());
                    lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_ACCOUNT_MANAGE_FEE.getCode());
                    detailMapper.insertSelective(lnRepayDetail);
                    //保费
                    lnRepayDetail.setDoneAmount(MoneyUtil.divide(repayment.getPremium(), 100).doubleValue());
                    lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_PREMIUM.getCode());
                    detailMapper.insertSelective(lnRepayDetail);
                    //其他
                    lnRepayDetail.setDoneAmount(MoneyUtil.divide(repayment.getOther(), 100).doubleValue());
                    lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_OTHER.getCode());
                    detailMapper.insertSelective(lnRepayDetail);

                    //滞纳金
                    lnRepayDetail.setDoneAmount(MoneyUtil.divide(repayment.getLateFee(), 100).doubleValue());
                    lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_LATE_FEE.getCode());
                    detailMapper.insertSelective(lnRepayDetail);
                    
                    if (CollectionUtils.isEmpty(repayScheduleList)) {
                    	LnRepay repayRepaied = new LnRepay();
                    	repayRepaied.setId(repay.getId());
                    	repayRepaied.setStatus(LoanStatus.REPAY_STATUS_FAIL.getCode());
                    	repayRepaied.setUpdateTime(new Date());
                    	repayMapper.updateByPrimaryKeySelective(repayRepaied);
                    	
                    	String tmp = "找不到对应的还款账单[" + repayment.getRepayId() + "]";
                    	checkFlag = false;
                    	checkErrorMsg = checkErrorMsg+"|"+tmp;
                        //throw new PTMessageException(PTMessageEnum.ZAN_OFFLINE_REPAY_PLAN_ID_EMPTY_ERROR, "找不到对应的还款账单[" + repayment.getRepayId() + "]");
					}
                    
                    //判断是否已还过款
                    if (LoanStatus.REPAY_SCHEDULE_STATUS_REPAIED.getCode().equals(repayScheduleList.get(0).getStatus()) ||
                    		LoanStatus.REPAY_SCHEDULE_STATUS_LATE_REPAIED.getCode().equals(repayScheduleList.get(0).getStatus())) {
                    	LnRepay repayRepaied = new LnRepay();
                    	repayRepaied.setId(repay.getId());
                    	repayRepaied.setStatus(LoanStatus.REPAY_STATUS_FAIL.getCode());
                    	repayRepaied.setUpdateTime(new Date());
                    	repayMapper.updateByPrimaryKeySelective(repayRepaied);
                    	
                    	String tmp =  "账单已还款[" + repayment.getRepayId() + "]";
                    	checkFlag = false;
                    	checkErrorMsg = checkErrorMsg+"|"+tmp;
                        //throw new PTMessageException(PTMessageEnum.ZAN_REPAY_PAYMENT_ORDER_DUPLICATE, "[" + repayment.getRepayId() + "]");
                    }
                    
                    if (!(LoanStatus.REPAY_SCHEDULE_STATUS_INIT.getCode().equals(repayScheduleList.get(0).getStatus())
                    		&& repayScheduleList.get(0).getPlanDate().compareTo(finishDate)>0)) {
                    	LnRepay repayRepaied = new LnRepay();
                    	repayRepaied.setId(repay.getId());
                    	repayRepaied.setStatus(LoanStatus.REPAY_STATUS_FAIL.getCode());
                    	repayRepaied.setUpdateTime(new Date());
                    	repayMapper.updateByPrimaryKeySelective(repayRepaied);
                    	String tmp =  "账单状态为"+repayScheduleList.get(0).getStatus()+"[" + repayment.getRepayId() + "],核实账单日期";
                    	checkFlag = false;
                    	checkErrorMsg = checkErrorMsg+"|"+tmp;
                        //throw new PTMessageException(PTMessageEnum.ZAN_OFFLINE_REPAY_STATUS_ERROR,"账单状态为INIT [" + repayment.getRepayId() + "]");
                    }

                }
                
                if (!checkFlag) {
                	throw new PTMessageException(PTMessageEnum.ZAN_OFFLINE_REPAY_SCEDULE_CHECK_ERROR,checkErrorMsg);
				}

                //根据借款支付订单号，查询子账户id
                LnPayOrdersExample ordersExample = new LnPayOrdersExample();
                ordersExample.createCriteria().andOrderNoEqualTo(loanList.get(0).getPayOrderNo());
                List<LnPayOrders> ordersList = payOrdersMapper.selectByExample(ordersExample);

                //记录ln_pay_orders,ln_pay_orders_jnl表
                LnPayOrders lnPayOrders = new LnPayOrders();
                lnPayOrders.setCreateTime(new Date());
                lnPayOrders.setAccountType(1);
                lnPayOrders.setAmount(MoneyUtil.divide(req.getTotalAmount(), "100").doubleValue());

                lnPayOrders.setBankCardNo(lnBindCard.getBankCard());
                BsCardBin bsCardBin = bankCardService.queryBankBin(lnBindCard.getBankCard());
                lnPayOrders.setBankName(lnBindCard.getBankName());
                lnPayOrders.setIdCard(lnBindCard.getIdCard());
                lnPayOrders.setLnUserId(lnBindCard.getLnUserId());
                lnPayOrders.setMobile(lnBindCard.getMobile());
                lnPayOrders.setUserName(lnBindCard.getUserName());
                lnPayOrders.setBankId(bsCardBin.getBankId());
                lnPayOrders.setChannel(Constants.CHANNEL_BAOFOO);
                //渠道交易类型为代扣
                lnPayOrders.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DK.getCode());
                lnPayOrders.setMoneyType(0);
                lnPayOrders.setOrderNo(payOrderNo);
                lnPayOrders.setPartnerCode(req.getChannel());
                lnPayOrders.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_SUCCESS.getCode()));
                lnPayOrders.setSubAccountId(ordersList.get(0).getSubAccountId());
                lnPayOrders.setTransType(LoanStatus.TRANS_TYPE_REPAY.getCode());
                lnPayOrders.setUpdateTime(new Date());
                lnPayOrders.setReturnCode(ConstantUtil.BSRESCODE_SUCCESS);
                lnPayOrders.setReturnMsg(ConstantUtil.DEFAULT_SUCESSMSG);
                lnPayOrders.setNote(Constants.OFFLINE_ZAN_ORDER_NOTE);
                
                // 资产方线下还款现在走的主通道，设置为默认值
                lnPayOrders.setPaymentChannelId(1);
                payOrdersMapper.insertSelective(lnPayOrders);
                log.info("===========================还款订单表插入记录：" + JSONObject.fromObject(lnPayOrders).toString());

                LnPayOrdersJnl lnPayOrdersJnl = new LnPayOrdersJnl();
                lnPayOrdersJnl.setSubAccountId(ordersList.get(0).getSubAccountId());
                lnPayOrdersJnl.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DK.getCode());
                lnPayOrdersJnl.setCreateTime(new Date());
                lnPayOrdersJnl.setOrderId(lnPayOrders.getId());
                lnPayOrdersJnl.setOrderNo(lnPayOrders.getOrderNo());
                lnPayOrdersJnl.setTransAmount(lnPayOrders.getAmount());
                lnPayOrdersJnl.setTransCode(LoanStatus.ORDERS_JNL_STATUS_SUCCESS.getCode());
                lnPayOrdersJnl.setUserId(lnBindCard.getLnUserId());
                lnPayOrdersJnl.setSysTime(new Date());
                lnPayOrdersJnl.setReturnCode("000000");
                lnPayOrdersJnl.setReturnMsg("线下还款默认成功");
                payOrdersJnlMapper.insertSelective(lnPayOrdersJnl);
                
                
                //----------------------------------------------------------------


                //根据支付方订单号查询还款信息表
                LnRepayExample example = new LnRepayExample();
                example.createCriteria().andPayOrderNoEqualTo(lnPayOrders.getOrderNo());
                List<LnRepay> repayLists = repayMapper.selectByExample(example);

                // 根据订单号查询每一期计划利息和计划本金
                List<RepayInfoVO> repayInfoVOs = repayMapper.selectRepayInfoGroup(lnPayOrders.getOrderNo());

                if (CollectionUtils.isEmpty(repayLists)) {
                    throw new PTMessageException(PTMessageEnum.REPAY_DATA_NOT_FOUND, "无还款记录");
                }
                LnRepay lnRepay = repayLists.get(0);

                //查询借款信息表
                LnLoan lnLoan = loanMapper.selectByPrimaryKey(lnRepay.getLoanId());

                for (final LnRepay repay : repayLists) {

                    //查询借贷关系表
                    LnLoanRelationExample relationExample = new LnLoanRelationExample();
                    relationExample.createCriteria().andLoanIdEqualTo(lnRepay.getLoanId()).andLnUserIdEqualTo(lnRepay.getLnUserId());
                    List<LnLoanRelation> relationList = relationMapper.selectByExample(relationExample);

                    //修改还款信息表状态
                    LnRepay repayTemp = new LnRepay();
                    repayTemp.setId(repay.getId());
                    repayTemp.setUpdateTime(new Date());
                    repayTemp.setStatus(LoanStatus.REPAY_STATUS_REPAIED.getCode());
                    repayTemp.setDoneTime(new Date());
                    repayMapper.updateByPrimaryKeySelective(repayTemp);
                    lnRepay.setStatus(repayTemp.getStatus());


                    LnRepayDetailExample detailExample = new LnRepayDetailExample();
                    detailExample.createCriteria().andRepayIdEqualTo(repay.getId());
                    List<LnRepayDetail> detailList = detailMapper.selectByExample(detailExample);

                    //查询还款计划表
                    final LnRepaySchedule repaySchedule = scheduleMapper.selectByPrimaryKey(repay.getRepayPlanId());

                    RepayInfo repayInfo = new RepayInfo();
                    repayInfo.setPartner(PartnerEnum.getEnumByCode(lnPayOrders.getPartnerCode()));
                    repayInfo.setChargeRuleId(lnLoan.getChargeRuleId());
                    //借款用户子账户编号
                    repayInfo.setLoanActId(relationList.get(0).getLnSubAccountId());
                    //发生金额
                    repayInfo.setAmount(repay.getDoneTotal());

                    //理财人利息
                    Double interest = 0d;
                    //理财人本金
                    Double principal = 0d;

                    for (LnRepayDetail detail : detailList) {

                        //逾期费
                        if (detail.getSubjectCode().equals(LoanSubjects.SUBJECT_CODE_LATE_FEE.getCode())) {
                            repayInfo.setOverdueAmount(detail.getDoneAmount());
                        }

                    }

                    //币港湾服务费(不是信息服务费)
                    repayInfo.setServiceFee(algorithmService.calBGWServiceFee(lnLoan.getId(), repaySchedule.getSerialId()));

                    //保证金 (不是保费)
                    repayInfo.setBailAmount(algorithmService.calZANDeposit(lnLoan.getId(), repaySchedule.getSerialId()));

                    CommissionVO commissionVO = new CommissionVO();
                    //手续费- 代扣还款
                    commissionVO = commissionService.calServiceFee(loanUserService.calTotalRepay(lnLoan.getId(), repaySchedule.getSerialId()), TransTypeEnum.ZAN_REPAY_DK, PayPlatformEnum.BAOFOO);
                    repayInfo.setFee(commissionVO.getActPayAmount() == null ? 0d : commissionVO.getActPayAmount());

                    //记录手续费
                    BsServiceFee bsServiceFee = new BsServiceFee();
                    bsServiceFee.setPlanFee(commissionVO.getNeedPayAmount());
                    bsServiceFee.setDoneFee(commissionVO.getActPayAmount());
                    bsServiceFee.setTransAmount(repay.getDoneTotal());
                    bsServiceFee.setFeeType(Constants.FEE_TYPE_REPAY);
                    bsServiceFee.setStatus(Constants.FEE_STATUS_COLLECT);
                    bsServiceFee.setCreateTime(new Date());
                    bsServiceFee.setOrderNo(repay.getPayOrderNo());
                    bsServiceFee.setSubAccountId(relationList.get(0).getLnSubAccountId());
                    bsServiceFee.setUpdateTime(new Date());
                    bsServiceFee.setNote("应扣" + commissionVO.getNeedPayAmount() + "，实扣" + commissionVO.getActPayAmount());
                    bsServiceFee.setPaymentPlatformFee(commissionVO.getThreePartyPaymentServiceFee());
                    serviceFeeMapper.insertSelective(bsServiceFee);


                    repayInfo.setInterest(interest);
                    repayInfo.setPrincipal(principal);
                    if(!CollectionUtils.isEmpty(repayInfoVOs)) {
                        for(RepayInfoVO vos : repayInfoVOs) {
                            if(vos.getSerialId().equals(repaySchedule.getSerialId())) {
                                repayInfo.setInterest(vos.getPlanInterest());
                                repayInfo.setPrincipal(vos.getPlanPrincipal());
                                break;
                            }
                        }
                    }

                	//overdueRepay(lnLoan, repaySchedule, repay.getDoneTotal(), repay.getPayOrderNo(), relationList ,true);
                	normalRepaySysSplit4ZANNew(lnLoan, repaySchedule, repay.getDoneTotal(), repay.getPayOrderNo(), relationList);
                
                }

                //起线程通知
                RepayProcess process = new RepayProcess();
                process.setRepay(lnRepay);
                process.setRepayPaymentService(this);
                process.setErrMsg("");
                new Thread(process).start();
            
                //----------------------------------------------------------------
                
                return bgwOrderNo;

            } else {
                throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "还款数据列表为空");
            }

        } finally {

        }
	}

}


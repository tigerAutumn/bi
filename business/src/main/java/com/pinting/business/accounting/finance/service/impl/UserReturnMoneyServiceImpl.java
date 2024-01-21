package com.pinting.business.accounting.finance.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.pinting.business.accounting.finance.model.DFResultInfo;
import com.pinting.business.accounting.finance.service.UserReturnMoneyService;
import com.pinting.business.accounting.loan.enums.LoanStatus;
import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.loan.model.BaseAccount;
import com.pinting.business.accounting.loan.model.PartnerSysActCode;
import com.pinting.business.accounting.loan.util.redisUtil;
import com.pinting.business.accounting.service.BsSysSubAccountService;
import com.pinting.business.accounting.service.CustomerReceiveMoneyService;
import com.pinting.business.accounting.service.PayOrdersService;
import com.pinting.business.dao.*;
import com.pinting.business.enums.BaoFooEnum;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.enums.RedisLockEnum;
import com.pinting.business.enums.ThirdSysChannelEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.*;
import com.pinting.business.model.vo.BsSubAccountVO;
import com.pinting.business.model.vo.LoanNoticeVO;
import com.pinting.business.service.site.*;
import com.pinting.business.util.CalculatorUtil;
import com.pinting.business.util.Constants;
import com.pinting.business.util.Util;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.hessian.message.baofoo.B2GReqMsg_BaoFooQuickPay_Pay4Trans;
import com.pinting.gateway.hessian.message.baofoo.B2GResMsg_BaoFooQuickPay_Pay4Trans;
import com.pinting.gateway.hessian.message.pay19.enums.OrderStatus;
import com.pinting.gateway.out.service.BaoFooTransportService;
import com.pinting.gateway.out.service.SMSServiceClient;
import com.pinting.gateway.smsEnums.TemplateKey;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by babyshark on 2016/9/9.
 */
@Service
public class UserReturnMoneyServiceImpl implements UserReturnMoneyService {
    private Logger log = LoggerFactory.getLogger(UserReturnMoneyServiceImpl.class);
    private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);

    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private UserService userService;
    @Autowired
    private BankCardService bankCardService;
    @Autowired
    private SubAccountService subAccountService;
    @Autowired
    private SpecialJnlService specialJnlService;
    @Autowired
    private PayOrdersService payOrdersService;
    @Autowired
    private CustomerReceiveMoneyService customerReceiveMoneyService;
    @Autowired
    private BsBatchBuyDetailMapper bsBatchBuyDetailMapper;
    @Autowired
    private BsPayOrdersMapper bsPayOrdersMapper;
    @Autowired
    private BsPayOrdersJnlMapper bsPayOrdersJnlMapper;
    @Autowired
    private BsSysSubAccountMapper sysAccountMapper;
    @Autowired
    private BsSysAccountJnlMapper sysAccountJnlMapper;
    @Autowired
    private BsUserMapper bsUserMapper;
    @Autowired
    private BsUserTransDetailMapper bsUserTransDetailMapper;
    @Autowired
    private BsAccountJnlMapper bsAccountJnlMapper;
    @Autowired
    private SMSServiceClient smsServiceClient;
    @Autowired
    private SendWechatService sendWechatService;
    @Autowired
    private SysConfigService sysConfigService;
    @Autowired
    private BsBatchBuyMapper bsBatchBuyMapper;
    @Autowired
    private BsBatchReturnDetailMapper bsBatchReturnDetailMapper;
    @Autowired
    private BaoFooTransportService baoFooTransportService;
    @Autowired
    private BsPayResultQueueMapper queueMapper;
    @Autowired
    private BsSysSubAccountService bsSysSubAccountService;
    @Autowired
    private	BsDepositionReturnMapper bsDepositionReturnMapper;
    @Autowired
    private ProductService productService;
    @Autowired
    private BsDepositionQuitApplyMapper bsDepositionQuitApplyMapper;
    @Autowired
    private BsSubAccountMapper bsSubAccountMapper;
    @Autowired
    private BsDailyBonusMapper bsDailyBonusMapper;
    @Autowired
    private BsSubAccountPairMapper bsSubAccountPairMapper;
    @Autowired
    private BsInterestTicketInfoMapper bsInterestTicketInfoMapper;
    @Autowired
    private BsBonusGrantPlanMapper bsBonusGrantPlanMapper;
    @Autowired
    private SendWechatByRabbitService sendWechatByRabbitService;

    /**
     * 用户批量回款
     *
     * @param receiveBatchs
     */
    @Override
    public void returnBatch(List<BsBatchBuy> receiveBatchs) {
        //前置校验：暂无
        log.info("========={批量客户回款}开始=========");
        if(receiveBatchs!=null && receiveBatchs.size()>0){
            log.info("========={批量客户回款}有["+receiveBatchs.size()+"]笔系统批次待回款=========");
            //遍历待回款批次
            for (BsBatchBuy bsBatchBuy : receiveBatchs) {
                try {
                    //查询该批次待回款用户产品明细
                    BsBatchBuyDetailExample detailExample = new BsBatchBuyDetailExample();
                    detailExample.createCriteria().andBatchIdEqualTo(bsBatchBuy.getId()).andReturnStatusEqualTo(Constants.RETURN_STATUS_NOT);
                    List<BsBatchBuyDetail> details = bsBatchBuyDetailMapper.selectByExample(detailExample);
                    if(details!=null && details.size()>0){
                        log.info("========={批量客户回款}批次号["+bsBatchBuy.getSendBatchId()+"]找到["+details.size()+"]笔理财产品待回款=========");
                        //待回款的理财明细数据同步到回款结算表
                        generatePlans(details);
                        //根据回款计划表，进行单笔客户回款
                        BsBatchReturnDetailExample returnDetailExample = new BsBatchReturnDetailExample();
                        returnDetailExample.createCriteria().andBatchIdEqualTo(bsBatchBuy.getId()).andReturnStatusEqualTo(Constants.RETURN_STATUS_NOT);
                        List<BsBatchReturnDetail> returnDetails = bsBatchReturnDetailMapper.selectByExample(returnDetailExample);
                        if(returnDetails!=null && returnDetails.size()>0){
                            for (BsBatchReturnDetail batchReturnDetail : returnDetails) {
                                BsUser fnUser = userService.findUserById(batchReturnDetail.getUserId());
                                Integer returnPath = fnUser.getReturnPath();
                                if(Constants.RETURN_PATH_BALANCE == returnPath){
                                    return2Balance(batchReturnDetail);
                                }else if(Constants.RETURN_PATH_BANKCARD == returnPath){
                                    return2Card(batchReturnDetail);
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    log.info("========={批量客户回款}批次号["+bsBatchBuy.getSendBatchId()+"]回款产生异常=========", e);
                    //该批次对应的所有待回款产品 设为  回款失败
                    BsBatchBuyDetailExample updateDetailExample = new BsBatchBuyDetailExample();
                    updateDetailExample.createCriteria().andBatchIdEqualTo(bsBatchBuy.getId()).andReturnStatusEqualTo(Constants.RETURN_STATUS_NOT);
                    BsBatchBuyDetail updateDetail = new BsBatchBuyDetail();
                    updateDetail.setReturnStatus(Constants.RETURN_STATUS_FAIL);
                    updateDetail.setUpdateTime(new Date());
                    bsBatchBuyDetailMapper.updateByExampleSelective(updateDetail, updateDetailExample);
                    //告警
                    specialJnlService.warn4Fail(bsBatchBuy.getAmount(), "{批量客户回款}批次号["+bsBatchBuy.getSendBatchId()+"]回款产生异常",
                            bsBatchBuy.getSendBatchId(),"批量客户回款",false);
                }

            }
        }
    }

    /**
     * 回款结算计划生成
     *
     * @param batchBuyDetails
     * @return
     */
    @Override
    public void generatePlans(final List<BsBatchBuyDetail> batchBuyDetails) {
        try {
            jsClientDaoSupport.lock(RedisLockEnum.LOCK_USER_RECEIVEMONEY.getKey());
            BsSysConfig limitConfig = sysConfigService.findConfigByKey(Constants.RETURN_MAX_AMOUNT_LIMIT);
            final Double maxAmountLimit = Double.valueOf(limitConfig.getConfValue());

            transactionTemplate.execute(new TransactionCallbackWithoutResult(){
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {

                    for (BsBatchBuyDetail bsBatchBuyDetail : batchBuyDetails) {
                        Integer userId = bsBatchBuyDetail.getUserId();
                        Integer subAccountId = bsBatchBuyDetail.getSubAccountId();
                        //产品户信息
                        BsSubAccountVO productAccount = subAccountService.findMyInvestByUserIdAndId(userId, subAccountId);
                        //产品本金
                        BigDecimal principal = new BigDecimal(String.valueOf(productAccount.getBalance()));
                        Integer days = productAccount.getTerm4Day();
                        //产品收益(四舍五入)
                        Double interest = CalculatorUtil.calculate("(a*a*a)/a", principal.doubleValue(), productAccount.getProductRate(), days.doubleValue(), 36500d);
                        		/* new BigDecimal(principal.multiply(
                                new BigDecimal(productAccount.getProductRate()*days/36500d)).doubleValue()).setScale(2, BigDecimal.ROUND_HALF_UP);*/
                        BigDecimal totalReturn = MoneyUtil.add(principal.doubleValue(), interest.doubleValue());
                        //回款根据金额拆分，并插入回款结算表
                        List<String> returnMoneyList = getSingleReturnMoneyList(totalReturn.doubleValue(), maxAmountLimit);
                        for (int i = 0; i < returnMoneyList.size(); i++) {
                            String singleReturnMoney = returnMoneyList.get(i);
                            BsBatchReturnDetail returnDetail = new BsBatchReturnDetail();
                            returnDetail.setBatchId(bsBatchBuyDetail.getBatchId());
                            returnDetail.setRate(bsBatchBuyDetail.getRate());
                            returnDetail.setReturnStatus(bsBatchBuyDetail.getReturnStatus());
                            returnDetail.setReturnAmount(Double.valueOf(singleReturnMoney));
                            returnDetail.setUserId(userId);
                            returnDetail.setSubAccountId(subAccountId);
                            returnDetail.setPrincipal(principal.doubleValue());
                            returnDetail.setInterest(interest.doubleValue());
                            returnDetail.setCreateTime(new Date());
                            returnDetail.setUpdateTime(new Date());
                            //判断是否允许回款计划插入
                            Double returnedAmount = bsBatchReturnDetailMapper.sumReturnedAmountBySubActId(subAccountId);
                            Double currReturnAmount = MoneyUtil.add(returnedAmount==null?0d:returnedAmount, returnDetail.getReturnAmount()).doubleValue();
                            Double totalReturnAmount = MoneyUtil.add(principal.doubleValue(), interest.doubleValue()).doubleValue();
                            if(MoneyUtil.subtract(totalReturnAmount, currReturnAmount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() >= 0){
                                bsBatchReturnDetailMapper.insertSelective(returnDetail);
                                if(i == returnMoneyList.size() - 1){
                                    BsBatchBuyDetail buyDetail = new BsBatchBuyDetail();
                                    buyDetail.setId(bsBatchBuyDetail.getId());
                                    buyDetail.setReturnStatus(Constants.RETURN_STATUS_SUCCESS);
                                    buyDetail.setUpdateTime(new Date());
                                    bsBatchBuyDetailMapper.updateByPrimaryKeySelective(buyDetail);
                                }
                            }else{
                                throw new PTMessageException(PTMessageEnum.RETURN_AMOUNT_ERROR);
                            }
                        }
                    }
                }
            });
        } finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_USER_RECEIVEMONEY.getKey());
        }
    }

    /**
     * 根据回款结算表进行单笔回款
     *
     * @param batchReturnDetail
     */
    @Override
    public void return2Card(BsBatchReturnDetail batchReturnDetail) {
        try {
            jsClientDaoSupport.lock(RedisLockEnum.LOCK_USER_RECEIVEMONEY.getKey());
            log.info("========={单笔客户回款}开始=========");
            if(isRepeatReturnDetail(batchReturnDetail.getId()))
                return;
            BsPayOrders order = new BsPayOrders();
            BsPayOrdersJnl orderJnl = new BsPayOrdersJnl();
            try {
                //查询用户信息
                final Integer userId = batchReturnDetail.getUserId();
                //用户信息
                //final BsUser user = userService.findUserById(userId);
                final Integer subAccountId = batchReturnDetail.getSubAccountId();
                //产品户信息
                final BsSubAccountVO productAccount = subAccountService.findMyInvestByUserIdAndId(userId, subAccountId);
                //回款路径为银行卡时
                log.info("========={单笔客户回款}回款到卡开始=========");
                //查询回款卡
                BsBankCard receiveCard = bankCardService.findFirstBankCardByUserId(userId);
                //订单表插入
                String orderNo = Util.generateOrderNo4Pay19(productAccount.getId());
                Date now = new Date();
                order.setAccountType(Constants.ORDER_ACCOUNT_TYPE_USER);
                order.setAmount(batchReturnDetail.getReturnAmount());//回款金额为本金+收益
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
                order.setTransType(Constants.TRANS_RETURN_2_USER_BANK_CARD);
                order.setUpdateTime(now);
                order.setUserId(userId);
                order.setUserName(receiveCard.getCardOwner());
                bsPayOrdersMapper.insertSelective(order);
                //订单明细表插入
                orderJnl.setCreateTime(now);
                orderJnl.setOrderId(order.getId());
                orderJnl.setOrderNo(order.getOrderNo());
                orderJnl.setSubAccountCode(productAccount.getCode());
                orderJnl.setSubAccountId(subAccountId);
                orderJnl.setSysTime(now);
                orderJnl.setTransAmount(batchReturnDetail.getReturnAmount());
                orderJnl.setTransCode(Constants.TRANS_RETURN_2_USER_BANK_CARD);
                orderJnl.setUserId(userId);
                bsPayOrdersJnlMapper.insertSelective(orderJnl);
                /**
                 * 宝付代付回款
                 */
                B2GReqMsg_BaoFooQuickPay_Pay4Trans req = new B2GReqMsg_BaoFooQuickPay_Pay4Trans();
                req.setTrans_no(order.getOrderNo());
                req.setTrans_money(String.valueOf(batchReturnDetail.getReturnAmount()));
                req.setTo_acc_no(receiveCard.getCardNo());
                req.setTo_acc_name(receiveCard.getCardOwner());
                req.setTo_bank_name(BaoFooEnum.pay4BankMap.get(String.valueOf(receiveCard.getBankId())));
                req.setTrans_card_id(receiveCard.getIdCard());
                req.setTrans_mobile(receiveCard.getMobile());
                req.setTrans_summary("普通产品回款");
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

                    String thirdReturnCode = Util.getThirdReturnCode(res);
                    updateOrder.setReturnCode(thirdReturnCode);
                    updateOrder.setReturnMsg(res.getRes_Msg());
                    updateOrder.setUpdateTime(new Date());
                    bsPayOrdersMapper.updateByPrimaryKeySelective(updateOrder);
                    //待回款产品明细表状态设为处理中
                    BsBatchReturnDetail updateDetail = new BsBatchReturnDetail();
                    updateDetail.setId(batchReturnDetail.getId());
                    updateDetail.setReturnOrderNo(order.getOrderNo());
                    updateDetail.setReturnStatus(Constants.RETURN_STATUS_PROCCESS);
                    updateDetail.setUpdateTime(new Date());
                    bsBatchReturnDetailMapper.updateByPrimaryKeySelective(updateDetail);

                    DFResultInfo succReq = new DFResultInfo();
                    succReq.setAmount(Double.valueOf(res.getTrans_money()));
                    succReq.setFinishTime(new Date());//宝付代付接口同步成功时 无完成时间，此处只能用本地服务时间
                    succReq.setMxOrderId(order.getOrderNo());
                    succReq.setOrderStatus(OrderStatus.SUCCESS.getCode());
                    succReq.setRetCode(res.getRes_Code());
                    notifyReturn2CardResult(succReq);
                }
                //代付下单申请成功，等待异步处理
                else if("920003".equals(res.getResCode())){
                    //更新订单表
                    BsPayOrders updateOrder = new BsPayOrders();
                    updateOrder.setId(order.getId());
                    updateOrder.setStatus(Constants.ORDER_STATUS_PAYING);

                    String thirdReturnCode = Util.getThirdReturnCode(res);
                    updateOrder.setReturnCode(thirdReturnCode);
                    updateOrder.setReturnMsg(res.getRes_Msg());
                    updateOrder.setUpdateTime(new Date());
                    bsPayOrdersMapper.updateByPrimaryKeySelective(updateOrder);
                    //待回款产品明细表状态设为处理中
                    BsBatchReturnDetail updateDetail = new BsBatchReturnDetail();
                    updateDetail.setId(batchReturnDetail.getId());
                    updateDetail.setReturnOrderNo(order.getOrderNo());
                    updateDetail.setReturnStatus(Constants.RETURN_STATUS_PROCCESS);
                    updateDetail.setUpdateTime(new Date());
                    bsBatchReturnDetailMapper.updateByPrimaryKeySelective(updateDetail);

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
                    String thirdReturnCode = Util.getThirdReturnCode(res);
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
                    insertOrderJnl.setTransAmount(batchReturnDetail.getReturnAmount());
                    insertOrderJnl.setTransCode(Constants.TRANS_RETURN_2_USER_BANK_CARD);
                    insertOrderJnl.setUserId(userId);
                    insertOrderJnl.setReturnCode(thirdReturnCode);
                    insertOrderJnl.setReturnMsg(res.getResMsg());
                    bsPayOrdersJnlMapper.insertSelective(insertOrderJnl);
                    //待回款产品明细表状态设为失败
                    BsBatchReturnDetail updateDetail = new BsBatchReturnDetail();
                    updateDetail.setId(batchReturnDetail.getId());
                    updateDetail.setReturnOrderNo(order.getOrderNo());
                    updateDetail.setReturnStatus(Constants.RETURN_STATUS_FAIL);
                    updateDetail.setUpdateTime(new Date());
                    bsBatchReturnDetailMapper.updateByPrimaryKeySelective(updateDetail);
                    //告警
                    specialJnlService.warn4Fail(order.getAmount(), "{单笔客户回款}产品户编号["+batchReturnDetail.getSubAccountId()+"]回款产生异常",
                            order.getOrderNo(),"单笔客户回款",false);
                }
                log.info("========={单笔客户回款}结束=========");
            } catch (Exception e) {
                log.info("========={单笔客户回款}产品户编号["+batchReturnDetail.getSubAccountId()+"]回款产生异常=========", e);
                if(e instanceof PTMessageException){
                    //待回款产品明细表状态设为回款失败
                    BsBatchReturnDetail updateDetail = new BsBatchReturnDetail();
                    updateDetail.setId(batchReturnDetail.getId());
                    updateDetail.setReturnOrderNo(order.getOrderNo());
                    updateDetail.setReturnStatus(Constants.RETURN_STATUS_FAIL);
                    updateDetail.setUpdateTime(new Date());
                    bsBatchReturnDetailMapper.updateByPrimaryKeySelective(updateDetail);

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
                    insertOrderJnl.setTransCode(Constants.TRANS_RETURN_2_USER_BANK_CARD);
                    insertOrderJnl.setUserId(orderJnl.getUserId());
                    insertOrderJnl.setReturnCode(pte.getErrCode());
                    insertOrderJnl.setReturnMsg(pte.getErrMessage()+":通讯失败");
                    bsPayOrdersJnlMapper.insertSelective(insertOrderJnl);
                }

                //告警
                specialJnlService.warn4Fail(order.getAmount(), "{单笔客户回款}产品户编号["+batchReturnDetail.getSubAccountId()+"]回款产生异常",
                        order.getOrderNo(),"单笔客户回款",false);
            }
        } finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_USER_RECEIVEMONEY.getKey());
        }
    }

    /**
     * 根据回款结算表进行单笔回款
     *
     * @param batchReturnDetail
     */
    @Override
    public void return2Balance(final BsBatchReturnDetail batchReturnDetail) {
        try {
            jsClientDaoSupport.lock(RedisLockEnum.LOCK_USER_RECEIVEMONEY.getKey());
            log.info("========={单笔客户回款}开始=========");
            if(isRepeatReturnDetail(batchReturnDetail.getId()))
                return;
            try {
                //查询用户信息
                final Integer userId = batchReturnDetail.getUserId();
                //用户信息
                final BsUser user = userService.findUserById(userId);
                final Integer subAccountId = batchReturnDetail.getSubAccountId();
                //产品户信息
                final BsSubAccountVO productAccount = subAccountService.findMyInvestByUserIdAndId(userId, subAccountId);
                //回款路径为余额
                log.info("========={单笔客户回款}回款到余额开始=========");
                final Integer detailId = batchReturnDetail.getId();
                final Integer batchBuyId = batchReturnDetail.getBatchId();
                final Double amount = batchReturnDetail.getReturnAmount();
                final Double productPrincipal = batchReturnDetail.getPrincipal();
                final Double productInterest = batchReturnDetail.getInterest();
                //判断该笔理财是否是最后次回款
                final boolean isLastReturn = isLastReturnMoney(subAccountId, amount, MoneyUtil.add(productPrincipal, productInterest).doubleValue());
                transactionTemplate.execute(new TransactionCallbackWithoutResult(){
                    @Override
                    protected void doInTransactionWithoutResult(TransactionStatus status) {
                        //待回款产品明细表状态设为回款成功
                        Date now = new Date();
                        BsBatchReturnDetail updateDetail = new BsBatchReturnDetail();
                        updateDetail.setId(detailId);
                        updateDetail.setReturnStatus(Constants.RETURN_STATUS_SUCCESS);
                        updateDetail.setUpdateTime(now);
                        bsBatchReturnDetailMapper.updateByPrimaryKeySelective(updateDetail);
                        //用户此产品户状态改为已结算
                        if(isLastReturn){
                            BsSubAccount reg = new BsSubAccount();
                            reg.setId(subAccountId);
                            reg.setStatus(Constants.SUBACCOUNT_STATUS_SETTLE);
                            subAccountService.modifySubAccountById(reg);
                        }

                        //记录用户交易明细
                        BsUserTransDetail transdetail = new BsUserTransDetail();
                        transdetail.setUserId(userId);
                        transdetail.setCardNo(null);
                        transdetail.setTransType(Constants.Trans_TYPE_RETURN);
                        transdetail.setTransStatus(Constants.Trans_STATUS_SUCCESS);
                        transdetail.setOrderNo(null);
                        transdetail.setCreateTime(now);
                        transdetail.setAmount(amount);
                        transdetail.setUpdateTime(now);
                        bsUserTransDetailMapper.insertSelective(transdetail);
                        //用户结算户金额增加
                        BsSubAccount jshSubAccount = subAccountService.findJSHSubAccountByUserId(userId);
                        BsSubAccount jsh = new BsSubAccount();
                        jsh.setId(jshSubAccount.getId());
                        jsh.setBalance(amount);
                        jsh.setAvailableBalance(amount);
                        jsh.setCanWithdraw(amount);
                        jsh.setLastTransDate(now);
                        subAccountService.modifyBalancesByIncrement(jsh);
                        //用户表当前投资收益减少，可提现金额增加
                        BsUser userUpdate = new BsUser();
                        userUpdate.setId(userId);
                        userUpdate.setCanWithdraw(amount);
                        if(isLastReturn){
                            Double currInterest = user.getCurrentInterest();
                            if (productInterest > 0) {
                                if(MoneyUtil.subtract(productInterest, currInterest).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() > 0){
                                    userUpdate.setCurrentInterest(-currInterest);
                                    //告警
                                    //specialJnlService.warn4Fail(currInterest, "{用户回款到卡通知}产品户编号["+subAccountId+"]当前收益减去回款利息为负数|"+currInterest+"-"+productInterest, null,"用户回款到卡通知当前收益为负",false);
                                }else{
                                    userUpdate.setCurrentInterest(-productInterest);
                                }
                            }
                        }
                        bsUserMapper.updateUserAmountInfoById(userUpdate);
                        //系统子账户表RETURN户减少
                        BsSysSubAccount userSubAccountLock = bsSysSubAccountService.findSysSubAccount4Lock(Constants.SYS_ACCOUNT_USER);
                        BsBatchBuy bsBatchBuy = bsBatchBuyMapper.selectByPrimaryKey(batchBuyId);
                        String actCode = Util.getSysReturnSubActCode(ThirdSysChannelEnum.getEnumByCode(bsBatchBuy.getPropertySymbol()));
                        BsSysSubAccount sysSubAccountLock = bsSysSubAccountService.findSysSubAccount4Lock(actCode);
                        BsSysSubAccount readyUpdate = new BsSysSubAccount();
                        readyUpdate.setId(sysSubAccountLock.getId());
                        readyUpdate.setBalance(MoneyUtil.subtract(sysSubAccountLock.getBalance(), amount).doubleValue());
                        readyUpdate.setAvailableBalance(MoneyUtil.subtract(sysSubAccountLock.getAvailableBalance(), amount).doubleValue());
                        readyUpdate.setCanWithdraw(MoneyUtil.subtract(sysSubAccountLock.getCanWithdraw(), amount).doubleValue());
                        sysAccountMapper.updateByPrimaryKeySelective(readyUpdate);
                        //系统子账户表USER户增加
                        BsSysSubAccount userReadyUpdate = new BsSysSubAccount();
                        userReadyUpdate.setId(userSubAccountLock.getId());
                        userReadyUpdate.setBalance(MoneyUtil.add(userSubAccountLock.getBalance(), amount).doubleValue());
                        userReadyUpdate.setAvailableBalance(MoneyUtil.add(userSubAccountLock.getAvailableBalance(), amount).doubleValue());
                        userReadyUpdate.setCanWithdraw(MoneyUtil.add(userSubAccountLock.getCanWithdraw(), amount).doubleValue());
                        sysAccountMapper.updateByPrimaryKeySelective(userReadyUpdate);
                        //系统记账流水表
                        BsSysAccountJnl sysAccountJnl = new BsSysAccountJnl();
                        sysAccountJnl.setTransTime(now);
                        sysAccountJnl.setTransCode(Constants.SYS_USER_RETURN_2_BALANCE);
                        sysAccountJnl.setTransName("用户回款到余额（本金+利息）");
                        sysAccountJnl.setTransAmount(amount);
                        sysAccountJnl.setSysTime(now);
                        sysAccountJnl.setCdFlag1(Constants.CD_FLAG_C);
                        sysAccountJnl.setSubAccountCode1(sysSubAccountLock.getCode());
                        sysAccountJnl.setBeforeBalance1(sysSubAccountLock.getBalance());
                        sysAccountJnl.setAfterBalance1(readyUpdate.getBalance());
                        sysAccountJnl.setBeforeAvialableBalance1(sysSubAccountLock.getAvailableBalance());
                        sysAccountJnl.setAfterAvialableBalance1(readyUpdate.getAvailableBalance());
                        sysAccountJnl.setBeforeFreezeBalance1(sysSubAccountLock.getFreezeBalance());
                        sysAccountJnl.setAfterFreezeBalance1(sysSubAccountLock.getFreezeBalance());
                        sysAccountJnl.setCdFlag2(Constants.CD_FLAG_D);
                        sysAccountJnl.setSubAccountCode2(userSubAccountLock.getCode());
                        sysAccountJnl.setBeforeBalance2(userSubAccountLock.getBalance());
                        sysAccountJnl.setAfterBalance2(userReadyUpdate.getBalance());
                        sysAccountJnl.setBeforeAvialableBalance2(userSubAccountLock.getAvailableBalance());
                        sysAccountJnl.setAfterAvialableBalance2(userReadyUpdate.getAvailableBalance());
                        sysAccountJnl.setBeforeFreezeBalance2(userSubAccountLock.getFreezeBalance());
                        sysAccountJnl.setAfterFreezeBalance2(userSubAccountLock.getFreezeBalance());
                        sysAccountJnl.setFee(0.0);
                        sysAccountJnl.setStatus(Constants.SYS_ACCOUNT_STATUS_SUCCESS);
                        sysAccountJnlMapper.insertSelective(sysAccountJnl);
                        //用户记账流水表
                        BsAccountJnl bsAccountJnl = new BsAccountJnl();
                        bsAccountJnl.setTransTime(now);
                        bsAccountJnl.setTransCode(Constants.USER_RETURN_2_BALANCE);
                        bsAccountJnl.setTransName("回款到余额");
                        bsAccountJnl.setTransAmount(amount);
                        bsAccountJnl.setSysTime(now);
                        bsAccountJnl.setCdFlag1(Constants.CD_FLAG_D);
                        bsAccountJnl.setUserId1(userId);
                        bsAccountJnl.setAccountId1(jshSubAccount.getAccountId());
                        bsAccountJnl.setSubAccountId1(jshSubAccount.getId());
                        bsAccountJnl.setSubAccountCode1(jshSubAccount.getCode());
                        bsAccountJnl.setBeforeBalance1(jshSubAccount.getBalance());
                        bsAccountJnl.setBeforeAvialableBalance1(jshSubAccount.getAvailableBalance());
                        bsAccountJnl.setBeforeFreezeBalance1(jshSubAccount.getFreezeBalance());
                        bsAccountJnl.setAfterBalance1(MoneyUtil.add(jshSubAccount.getBalance(), amount).doubleValue());
                        bsAccountJnl.setAfterAvialableBalance1(MoneyUtil.add(jshSubAccount.getAvailableBalance(), amount).doubleValue());
                        bsAccountJnl.setAfterFreezeBalance1(jshSubAccount.getFreezeBalance());
                        bsAccountJnl.setCdFlag2(Constants.CD_FLAG_C);
                        bsAccountJnl.setUserId2(userId);
                        bsAccountJnl.setSubAccountId2(subAccountId);
                        bsAccountJnl.setFee(0d);
                        bsAccountJnl.setStatus(Constants.ACCOUNT_JNL_STATUS_SUCCESS);
                        bsAccountJnlMapper.insertSelective(bsAccountJnl);

                        //登记该条回款记录
                        if(isLastReturn){
                            BsCustomerReceiveMoney receiveMoney = new BsCustomerReceiveMoney();
                            receiveMoney.setAmountBase(productPrincipal);
                            receiveMoney.setAmountInterest(productInterest);
                            receiveMoney.setBankNo(null);
                            receiveMoney.setCardNo(user.getIdCard());
                            receiveMoney.setCreateTime(now);
                            receiveMoney.setCustomerName(user.getUserName());
                            receiveMoney.setOrderNo(null);
                            receiveMoney.setProductCode(String.valueOf(productAccount.getProductId()));
                            receiveMoney.setStatus(0);
                            receiveMoney.setSuccessTime(now);
                            customerReceiveMoneyService.addCustomerReceiveMoney(receiveMoney);
                        }

                    }
                });
                if(isLastReturn){
                	try {
                        smsServiceClient.sendTemplateMessage(user.getMobile(), TemplateKey.RETURN_SUCCESS2BALANCE,
                                String.valueOf(MoneyUtil.add(productPrincipal, productInterest).doubleValue()), productPrincipal.toString(), productInterest.toString());
                        //发送微信模板消息
                        sendWechatService.paymentMgs2Balance(userId,"",
                                String.valueOf(MoneyUtil.add(productPrincipal, productInterest).doubleValue()), productPrincipal.toString(), productInterest.toString());
					} catch (Exception e) {
						 log.info("========={单笔客户回款}微信模板异常=========", e);
					}
 
                }
                log.info("========={单笔客户回款}结束=========");
            } catch (Exception e) {
                log.info("========={单笔客户回款}产品户编号["+batchReturnDetail.getSubAccountId()+"]回款产生异常=========", e);
                //告警
                specialJnlService.warn4Fail(batchReturnDetail.getReturnAmount(), "{单笔客户回款}产品户编号["+batchReturnDetail.getSubAccountId()+"]回款产生异常",
                        batchReturnDetail.getReturnOrderNo(),"单笔客户回款",false);
            }
        } finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_USER_RECEIVEMONEY.getKey());
        }
    }

    /**
     * 用户回款到卡结果通知处理
     *
     * @param req
     */
    @Override
    public void notifyReturn2CardResult(final DFResultInfo req) {
        try {
            //jsClientDaoSupport.lock(RedisLockEnum.LOCK_USER_RECEIVEMONEY.getKey());
            log.info("========={用户回款到卡通知}用户回款到卡通知处理开始=========");
            //检查是否是否重复通知（根据订单信息是否是处理中，如果不是，则已经处理过，拒绝再次处理）
            String orderNo = req.getMxOrderId();
            final BsPayOrders order = payOrdersService.findOrderByOrderNo(orderNo);
            if(order.getStatus() != Constants.ORDER_STATUS_PAYING){//订单状态已经被修改，此次通知认为是重复通知，不做处理
                //告警
                //specialJnlService.warn4Fail(order.getAmount(), "{用户回款到卡通知}订单号["+orderNo+"]19付重复通知",order.getOrderNo(),"用户回款到卡通知",false);
                log.warn("========={用户回款到卡通知}订单号["+orderNo+"]重复通知=========");
                return;
            }

            //产品户信息
            final BsSubAccountVO productAccount = subAccountService.findMyInvestByUserIdAndId(order.getUserId(), order.getSubAccountId());
            //产品本金
            final BigDecimal productPrincipal = new BigDecimal(String.valueOf(productAccount.getBalance()));
            Integer days = productAccount.getTerm4Day();
            //产品收益(四舍五入)
            final Double productInterest = CalculatorUtil.calculate("(a*a*a)/a", productPrincipal.doubleValue(),productAccount.getProductRate(),days.doubleValue(),36500d);
            		/*new BigDecimal(productPrincipal.multiply(
                    new BigDecimal(productAccount.getProductRate()*days/36500d)).doubleValue()).setScale(2, BigDecimal.ROUND_HALF_UP);*/
            //回款金额
            final Double amount = order.getAmount();
            //判断该笔理财是否是最后次回款
            final boolean isLastReturn = isLastReturnMoney(order.getSubAccountId(),
                    amount, MoneyUtil.add(productPrincipal.doubleValue(), productInterest.doubleValue()).doubleValue());

            final BsUser user = userService.findUserById(order.getUserId());
            String mobile = user.getMobile();
            final BsSubAccount proSubAccount = subAccountService.findSubAccountById(order.getSubAccountId());
            //登记该条回款通知
            if(isLastReturn){
            	log.info("========={用户回款到卡通知}登记用户回款通知=========");
                BsCustomerReceiveMoney receiveMoney = new BsCustomerReceiveMoney();
                receiveMoney.setAmountBase(proSubAccount.getBalance());
                receiveMoney.setAmountInterest(productInterest.doubleValue());
                receiveMoney.setBankNo(order.getBankCardNo());
                receiveMoney.setCardNo(order.getIdCard());
                receiveMoney.setCreateTime(new Date());
                receiveMoney.setCustomerName(order.getUserName());
                receiveMoney.setOrderNo(order.getOrderNo());
                receiveMoney.setProductCode(String.valueOf(proSubAccount.getProductId()));
                receiveMoney.setStatus(OrderStatus.SUCCESS.getCode().equals(req.getOrderStatus())?0:1);
                receiveMoney.setSuccessTime(req.getFinishTime());
                customerReceiveMoneyService.addCustomerReceiveMoney(receiveMoney);
            }

            //回款到卡成功
            if(OrderStatus.SUCCESS.getCode().equals(req.getOrderStatus())) {
                log.info("========={用户回款到卡通知}用户回款到卡通知结果成功！=========");
                transactionTemplate.execute(new TransactionCallbackWithoutResult(){
                    @Override
                    protected void doInTransactionWithoutResult(TransactionStatus status) {
                        //待回款产品明细表状态设为成功
                        BsBatchReturnDetailExample batchReturnDetailExample = new BsBatchReturnDetailExample();
                        batchReturnDetailExample.createCriteria().andSubAccountIdEqualTo(order.getSubAccountId()).andReturnOrderNoEqualTo(order.getOrderNo());
                        BsBatchReturnDetail updateDetail = new BsBatchReturnDetail();
                        updateDetail.setReturnStatus(Constants.RETURN_STATUS_SUCCESS);
                        updateDetail.setUpdateTime(new Date());
                        updateDetail.setReturnOrderNo(req.getMxOrderId());
                        bsBatchReturnDetailMapper.updateByExampleSelective(updateDetail, batchReturnDetailExample);
                        //更新订单表，记录订单流水表
                        BsPayOrders updateOrder = new BsPayOrders();
                        updateOrder.setId(order.getId());
                        updateOrder.setStatus(Constants.ORDER_STATUS_SUCCESS);
                        updateOrder.setReturnCode(req.getRetCode());
                        updateOrder.setReturnMsg(req.getErrorMsg());
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
                        insertOrderJnl.setTransCode(Constants.TRANS_RETURN_2_USER_BANK_CARD);
                        insertOrderJnl.setUserId(order.getUserId());
                        insertOrderJnl.setReturnCode(req.getRetCode());
                        insertOrderJnl.setReturnMsg(req.getErrorMsg());
                        bsPayOrdersJnlMapper.insertSelective(insertOrderJnl);
                        //用户此产品户状态改为已结算
                        if(isLastReturn){
                            BsSubAccount reg = new BsSubAccount();
                            reg.setId(order.getSubAccountId());
                            reg.setStatus(Constants.SUBACCOUNT_STATUS_SETTLE);
                            subAccountService.modifySubAccountById(reg);

                            //用户账记账
                            BsSubAccount subAccount = subAccountService.findSubAccountById(order.getSubAccountId());
                            BsAccountJnl accountJnl = new BsAccountJnl();
                            accountJnl.setTransTime(new Date());
                            accountJnl.setTransCode(Constants.USER_RETURN_2_BANK_CARD);
                            accountJnl.setTransName("回款到卡");
                            accountJnl.setTransAmount(order.getAmount());
                            accountJnl.setSysTime(new Date());
                            accountJnl.setChannelTime(null);
                            accountJnl.setChannelJnlNo(null);
                            accountJnl.setCdFlag1(Constants.CD_FLAG_C);
                            accountJnl.setUserId1(order.getUserId());
                            accountJnl.setAccountId1(subAccount.getAccountId());
                            accountJnl.setSubAccountId1(order.getSubAccountId());
                            accountJnl.setSubAccountCode1(subAccount.getCode());
                            accountJnl.setBeforeBalance1(subAccount.getBalance());
                            accountJnl.setAfterBalance1(0d);
                            accountJnl.setBeforeAvialableBalance1(subAccount.getAvailableBalance());
                            accountJnl.setAfterAvialableBalance1(0d);
                            accountJnl.setBeforeFreezeBalance1(subAccount.getFreezeBalance());
                            accountJnl.setAfterFreezeBalance1(0d);
                            bsAccountJnlMapper.insertSelective(accountJnl);
                        }
                        //记录用户交易明细
                        BsUserTransDetail transdetail = new BsUserTransDetail();
                        transdetail.setUserId(order.getUserId());
                        transdetail.setCardNo(order.getBankCardNo());
                        transdetail.setTransType(Constants.Trans_TYPE_RETURN);
                        transdetail.setTransStatus(Constants.Trans_STATUS_SUCCESS);
                        transdetail.setOrderNo(order.getOrderNo());
                        transdetail.setCreateTime(new Date());
                        transdetail.setAmount(order.getAmount());
                        transdetail.setUpdateTime(new Date());
                        bsUserTransDetailMapper.insertSelective(transdetail);
                        BsUserTransDetail transdetail2 = new BsUserTransDetail();
                        transdetail2.setUserId(order.getUserId());
                        transdetail2.setCardNo(order.getBankCardNo());
                        transdetail2.setTransType(Constants.Trans_TYPE_WITHDRAW);
                        transdetail2.setTransStatus(Constants.Trans_STATUS_SUCCESS);
                        transdetail2.setOrderNo(order.getOrderNo());
                        transdetail2.setCreateTime(new Date());
                        transdetail2.setAmount(-order.getAmount());
                        transdetail2.setUpdateTime(new Date());
                        bsUserTransDetailMapper.insertSelective(transdetail2);
                        //用户表当前投资收益减少
                        if(isLastReturn){
                            BsUser userUpdate = new BsUser();
                            userUpdate.setId(order.getUserId());
                            Double currInterest = user.getCurrentInterest();
                            if (productInterest.doubleValue() > 0) {
                                if(MoneyUtil.subtract(productInterest.doubleValue(), currInterest).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() > 0){
                                    userUpdate.setCurrentInterest(-currInterest);
                                    //告警
                                    //specialJnlService.warn4Fail(currInterest, "{用户回款到卡通知}产品户编号["+order.getSubAccountId()+"]当前收益减去回款利息为负数|"+currInterest+"-"+productInterest.doubleValue(), null,"用户回款到卡通知当前收益为负",false);
                                }else{
                                    userUpdate.setCurrentInterest(-productInterest.doubleValue());
                                }
                            }
                            bsUserMapper.updateUserAmountInfoById(userUpdate);
                        }
                        //系统子账户表RETURN户减少
                        List<BsBatchReturnDetail> batchReturnDetails = bsBatchReturnDetailMapper.selectByExample(batchReturnDetailExample);
                        BsBatchBuy bsBatchBuy = bsBatchBuyMapper.selectByPrimaryKey(batchReturnDetails.get(0).getBatchId());
                        String actCode = Util.getSysReturnSubActCode(ThirdSysChannelEnum.getEnumByCode(bsBatchBuy.getPropertySymbol()));
                        BsSysSubAccount sysSubAccountLock = bsSysSubAccountService.findSysSubAccount4Lock(actCode);
                        BsSysSubAccount readyUpdate = new BsSysSubAccount();
                        readyUpdate.setId(sysSubAccountLock.getId());
                        readyUpdate.setBalance(MoneyUtil.subtract(sysSubAccountLock.getBalance(), order.getAmount()).doubleValue());
                        readyUpdate.setAvailableBalance(MoneyUtil.subtract(sysSubAccountLock.getAvailableBalance(), order.getAmount()).doubleValue());
                        readyUpdate.setCanWithdraw(MoneyUtil.subtract(sysSubAccountLock.getCanWithdraw(), order.getAmount()).doubleValue());
                        sysAccountMapper.updateByPrimaryKeySelective(readyUpdate);
                        //系统记账流水表
                        BsSysAccountJnl sysAccountJnl = new BsSysAccountJnl();
                        sysAccountJnl.setTransTime(new Date());
                        sysAccountJnl.setTransCode(Constants.SYS_USER_RETURN_2_CARD);
                        sysAccountJnl.setTransName("用户回款卡（本金+利息）");
                        sysAccountJnl.setTransAmount(order.getAmount());
                        sysAccountJnl.setSysTime(new Date());
                        sysAccountJnl.setCdFlag1(Constants.CD_FLAG_C);
                        sysAccountJnl.setSubAccountCode1(sysSubAccountLock.getCode());
                        sysAccountJnl.setBeforeBalance1(sysSubAccountLock.getBalance());
                        sysAccountJnl.setAfterBalance1(readyUpdate.getBalance());
                        sysAccountJnl.setBeforeAvialableBalance1(sysSubAccountLock.getAvailableBalance());
                        sysAccountJnl.setAfterAvialableBalance1(readyUpdate.getAvailableBalance());
                        sysAccountJnl.setBeforeFreezeBalance1(sysSubAccountLock.getFreezeBalance());
                        sysAccountJnl.setAfterFreezeBalance1(sysSubAccountLock.getFreezeBalance());
                        sysAccountJnl.setFee(0.0);
                        sysAccountJnl.setStatus(Constants.SYS_ACCOUNT_STATUS_SUCCESS);
                        sysAccountJnlMapper.insertSelective(sysAccountJnl);
                    }
                });

                //短信通知
                if(isLastReturn){
                    try {
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
					} catch (Exception e) {
						log.info("========={用户回款到卡通知}发送消息异常！========="+e);
					}
                    
                }

            }
            //回款到卡失败
            else{
                log.info("========={用户回款到卡通知}用户回款到卡通知结果失败！=========");
                //待回款产品明细表状态设为失败
                BsBatchReturnDetailExample example = new BsBatchReturnDetailExample();
                example.createCriteria().andSubAccountIdEqualTo(order.getSubAccountId()).andReturnOrderNoEqualTo(order.getOrderNo());
                BsBatchReturnDetail updateDetail = new BsBatchReturnDetail();
                updateDetail.setReturnStatus(Constants.RETURN_STATUS_FAIL);
                updateDetail.setUpdateTime(new Date());
                bsBatchReturnDetailMapper.updateByExampleSelective(updateDetail, example);
                //更新订单表，记录订单流水表
                BsPayOrders updateOrder = new BsPayOrders();
                updateOrder.setId(order.getId());
                updateOrder.setStatus(Constants.ORDER_STATUS_FAIL);
                updateOrder.setReturnCode(req.getRetCode());
                updateOrder.setReturnMsg(req.getErrorMsg());
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
                insertOrderJnl.setTransCode(Constants.TRANS_RETURN_2_USER_BANK_CARD);
                insertOrderJnl.setUserId(order.getUserId());
                insertOrderJnl.setReturnCode(req.getRetCode());
                insertOrderJnl.setReturnMsg(req.getErrorMsg());
                bsPayOrdersJnlMapper.insertSelective(insertOrderJnl);
                //告警
                specialJnlService.warn4Fail(order.getAmount(), "{用户回款到卡通知}产品户编号["+order.getSubAccountId()+"]回款通知结果为失败",
                        order.getOrderNo(),"用户回款到卡通知",false);
            }

            //修改支付结果表状态
            BsPayResultQueueExample queueExample = new BsPayResultQueueExample();
            queueExample.createCriteria().andOrderNoEqualTo(order.getOrderNo());
            List<BsPayResultQueue> queueList = queueMapper.selectByExample(queueExample);

            if (CollectionUtils.isNotEmpty(queueList) && queueList.size() > 0) {
                BsPayResultQueue queueTemp = new BsPayResultQueue();
                queueTemp.setId(queueList.get(0).getId());
                queueTemp.setUpdateTime(new Date());
                if (OrderStatus.SUCCESS.getCode().equals(req.getOrderStatus())) {
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

    /**
     * 获得单笔回款金额列表
     * @param totalReturn
     * @param maxAmountLimit
     * @return 列表至少有一条数据
     */
    private List<String> getSingleReturnMoneyList(Double totalReturn, Double maxAmountLimit){
        List<String> moneyList = new ArrayList<String>();

        if(MoneyUtil.subtract(totalReturn, maxAmountLimit).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() > 0){
            Double singleReturnAmount = MoneyUtil.multiply(maxAmountLimit, 0.8).doubleValue();
            Double lastReturn = totalReturn%singleReturnAmount;
            if("0.00".equals(String.format("%.2f", lastReturn))){
                int returnCount = (int) (totalReturn/singleReturnAmount);
                for (int i = 0; i < returnCount; i++) {
                    moneyList.add(String.format("%.2f", singleReturnAmount));
                }
            }else{
                int returnCount = (int) (totalReturn/singleReturnAmount) + 1;
                for (int i = 0; i < returnCount; i++) {
                    if(i + 1 == returnCount){
                        moneyList.add(String.format("%.2f", lastReturn));
                    }else{
                        moneyList.add(String.format("%.2f", singleReturnAmount));
                    }
                }
            }
        }else{
            moneyList.add(String.format("%.2f", totalReturn));
        }
        return moneyList;
    }

    /**
     * 判断某笔理财是否是最后次用户回款
     * @param subAccountId 理财编号
     * @param returnAmount 回款金额
     * @param totalReturnAmount 应回款金额
     * @return true：是最后次；false：非最后次
     */
    private boolean isLastReturnMoney(Integer subAccountId, Double returnAmount, Double totalReturnAmount){
        if(MoneyUtil.subtract(returnAmount, totalReturnAmount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() < 0){
            BsBatchReturnDetailExample unReturneExample = new BsBatchReturnDetailExample();
            unReturneExample.createCriteria().andSubAccountIdEqualTo(subAccountId)
                    .andReturnStatusNotEqualTo(Constants.RETURN_STATUS_SUCCESS);
            int unReturnCount = bsBatchReturnDetailMapper.countByExample(unReturneExample);
            if(unReturnCount == 1){
                return true;
            }else if(unReturnCount > 1){
                return false;
            }else{
                throw new PTMessageException(PTMessageEnum.RETURN_AMOUNT_ERROR);
            }
        }else if(MoneyUtil.subtract(returnAmount, totalReturnAmount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() == 0){
            return true;
        }else{
            throw new PTMessageException(PTMessageEnum.RETURN_AMOUNT_ERROR);
        }
    }

    /**
     * 判断是否是重复的回款
     * @param returnDetailId
     * @return
     */
    private boolean isRepeatReturnDetail(Integer returnDetailId){
        BsBatchReturnDetail returnCheck = bsBatchReturnDetailMapper.selectByPrimaryKey(returnDetailId);
        if(Constants.RETURN_STATUS_PROCCESS.equals(returnCheck.getReturnStatus()) ||
                Constants.RETURN_STATUS_SUCCESS.equals(returnCheck.getReturnStatus())){
            log.warn("========={单笔客户回款}回款编号["+returnDetailId+"]已回款或处理中，不需重复处理=========");
            return true;
        }
        return false;
    }

	@Override
	public void depGeneratePlans(Integer subAccountId, Integer userId,
			Double principal, Double interest, Double penalty,Double bonus,Double overflowInterest) {
  	  log.info("========={存管系统固定期限产品退出服务}生成回款计划开始=========");
  	  try {
            jsClientDaoSupport.lock(RedisLockEnum.LOCK_DEP_QUIT_GENERATE_PLANS.getKey());
  	        
            BsDepositionReturnExample example = new BsDepositionReturnExample();
            example.createCriteria().andUserIdEqualTo(userId).andSubAccountIdEqualTo(subAccountId);
            List<BsDepositionReturn>  depositionReturnList = bsDepositionReturnMapper.selectByExample(example);
            if (CollectionUtils.isEmpty(depositionReturnList)) {
            	log.info("========={存管系统固定期限产品退出服务}回款表不存在对应回款记录，插入回款表=========");
		    	BsDepositionReturn bsDepositionReturn = new BsDepositionReturn();
		    	bsDepositionReturn.setUserId(userId);
		    	bsDepositionReturn.setPrincipal(principal);
		    	bsDepositionReturn.setInterest(interest);
		    	bsDepositionReturn.setPenalty(penalty);
		    	bsDepositionReturn.setBonus(bonus);
		    	bsDepositionReturn.setOverflowInterest(overflowInterest);
		    	bsDepositionReturn.setExpectDate(new Date());
		    	bsDepositionReturn.setSubAccountId(subAccountId);
		    	bsDepositionReturn.setReturnOrderNo(null);
		    	bsDepositionReturn.setReturnAmount(null);
		    	bsDepositionReturn.setFinishTime(null);
		    	bsDepositionReturn.setStatus(Constants.DEP_RETURN_STATUS_INIT);
		    	bsDepositionReturn.setUpdateTime(new Date());
		    	bsDepositionReturn.setCreateTime(new Date());
		    	bsDepositionReturnMapper.insertSelective(bsDepositionReturn);
			}
            log.info("========={存管系统固定期限产品退出服务}生成回款计划结束=========");
  	  }  finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_DEP_QUIT_GENERATE_PLANS.getKey());
      }
  }

	@Override
	public void depReturn2Balance(final BsDepositionReturn bsDepositionReturn) {
        /**
         *  
			加锁
			判断是否重复回款
			事务start
			1、bs_increase_return状态设为回款成功，设置回款金额
			2、bs_increase_quit_apply状态设为已结算
			3、交易明细表bs_user_trans_detail记录（
			2017年3月12日：用户交易明细只记一条回款记录 （本息合计-手续费），不再先记本息回款再扣手续费  ）
			4、bs_user当前收益减少，可提现金额增加
			
			5、调用“理财人回款记账”
					5.1、bs_sub_account用户产品户AUTH_INCR状态设为已结算
					5.2、bs_sub_account用户JSH金额增加（回款到余额），bs_account_jnl
					5.3、系统RETURN_YUN_INCR户金额减少（本息合计）>系统USER户金额增加，bs_sys_account_jnl
					5.4、若锁定期内回款：
					用户JSH余额减少
					系统USER户金额减少（违约金）>系统BGW_REVENUE_4_YUN_INCR增加金额（退出违约金扣除），bs_sys_account_jnl
			6、登记回款bs_customer_receive_money

            7、查看是否存在加息券
                 如果使用了加息券，查询加息券收益（bs_interest_ticket_info.interest_amount）
                 有加息券收益，发放加息券利息：
                 bs_user.current_bonus bs_user.total_bonus 用户表奖励金账户（+加息券利息），更新奖励金子账户bs_sub_account.product_type（'JLJ'），
                 记录用户账户流水bs_account_jnl
                 bs_daily_bonus增加用户奖励金明细记录(bs_daily_bonus.sub_account_id)（bs_daily_bonus.type(INTEREST_TICKET：加息券收益)）
                 bs_bonus_grant_plan.grant_type(INTEREST_TICKET：加息券收益)增加奖励金发放计划SUCC
			事务end
			7、短信、微信发送（若锁定期内回款则调用有手续费的信息模板）
         */
        try {
            jsClientDaoSupport.lock(RedisLockEnum.LOCK_DEP_FIXED_USER_RECEIVEMONEY.getKey());
            log.info("========={存管定期产品退出回款到余额}开始=========");
            final BsDepositionReturn returnCheck = bsDepositionReturnMapper.selectByPrimaryKey(bsDepositionReturn.getId());
            
            if(Constants.DEP_RETURN_STATUS_PROCESS.equals(returnCheck.getStatus()) ||
                    Constants.DEP_RETURN_STATUS_SUCCESS.equals(returnCheck.getStatus())){
                log.warn("========={存管定期产品退出回款到余额}回款编号["+returnCheck.getId()+"]已回款或处理中，不需重复处理=========");
                return;
            }
            
            try {
                //查询用户信息
                final Integer userId = bsDepositionReturn.getUserId();
                final BsUser bsUser = userService.findUserById(userId);
                final Double productPrincipal = returnCheck.getPrincipal();
                final Double productInterest = returnCheck.getInterest();
                final Double overflowInterest = returnCheck.getOverflowInterest();
                //应回款总额
                final Double amount = MoneyUtil.add(MoneyUtil.add(productInterest, overflowInterest).doubleValue(), productPrincipal).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                //实际回款到余额 金额
                final Double realReturnAmount = MoneyUtil.subtract(amount, returnCheck.getBonus()).doubleValue();
                //通过奖励金回款 金额
                final Double bonus = returnCheck.getBonus();
                //产品户信息
                final BsSubAccount productAccount = subAccountService.findSubAccountById(returnCheck.getSubAccountId());
                final BsPropertyInfo productInfo = productService.queryPropertyInfoByProductId(productAccount.getProductId());
                BsDepositionQuitApplyExample bsDepositionQuitApplyExample = new BsDepositionQuitApplyExample();
                bsDepositionQuitApplyExample.createCriteria().andUserIdEqualTo(userId).andSubAccountIdEqualTo(productAccount.getId());
                final List<BsDepositionQuitApply> bsDepositionQuitApplies = bsDepositionQuitApplyMapper.selectByExample(bsDepositionQuitApplyExample);
                
                transactionTemplate.execute(new TransactionCallbackWithoutResult(){
                    @Override
                    protected void doInTransactionWithoutResult(TransactionStatus status) {
                    	Date now = new Date();
                    	
                    	//1、bs_deposition_return状态设为回款成功，设置回款金额
                    	BsDepositionReturn bReturn = new BsDepositionReturn();
                    	bReturn.setId(bsDepositionReturn.getId());
                    	bReturn.setStatus(Constants.DEP_RETURN_STATUS_SUCCESS);
                    	bReturn.setReturnAmount(realReturnAmount);
                    	bReturn.setFinishTime(new Date());
                    	bReturn.setUpdateTime(new Date());
                    	bsDepositionReturnMapper.updateByPrimaryKeySelective(bReturn);
                    	//2、bs_deposition_quit_apply状态设为已结算
                    	BsDepositionQuitApply bsDepositionQuitApply = new BsDepositionQuitApply();
                    	bsDepositionQuitApply.setFinalInterest(productInterest);
                    	bsDepositionQuitApply.setId(bsDepositionQuitApplies.get(0).getId());
                    	bsDepositionQuitApply.setStatus(Constants.INCREASE_QUIT_APPLY_RETURNED);
                    	bsDepositionQuitApply.setUpdateTime(new Date());
                    	bsDepositionQuitApplyMapper.updateByPrimaryKeySelective(bsDepositionQuitApply);
                       
                        //3、记录用户交易明细
                    	//2017年3月12日：用户交易明细只记一条回款记录 （本息合计-手续费），不再先记本息回款再扣手续费  
                        BsUserTransDetail transDetail = new BsUserTransDetail();
                        transDetail.setUserId(returnCheck.getUserId());
                        transDetail.setCardNo(null);
                        transDetail.setTransType(Constants.Trans_TYPE_RETURN);
                        if(productInfo != null && productInfo.getPropertySymbol().equals(PartnerEnum.ZSD)){
        					transDetail.setTransType(Constants.Trans_TYPE_ZSD_RETURN);
        				}
                        transDetail.setTransStatus(Constants.Trans_STATUS_SUCCESS);
                        transDetail.setOrderNo(null);
                        transDetail.setCreateTime(new Date());
                        transDetail.setAmount(realReturnAmount);
                        transDetail.setUpdateTime(new Date());
                        bsUserTransDetailMapper.insertSelective(transDetail);

                        //4、用户表当前收益减少（减少金额为产品利息），可提现金额增加（增加实际回款金额）
                        BsUser userUpdate = new BsUser();
                        userUpdate.setId(userId);
                        userUpdate.setCanWithdraw(realReturnAmount);
                        Double currInterest = bsUser.getCurrentInterest();
                        if (productInterest > 0) {
                            if(MoneyUtil.subtract(productInterest, currInterest).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() > 0){
                                userUpdate.setCurrentInterest(-currInterest);
                            }else{
                                userUpdate.setCurrentInterest(-productInterest);
                            }
                        }
                        bsUserMapper.updateUserAmountInfoById(userUpdate);

                        //5.1、用户产品户AUTH_YUN、RED、DIFF状态设为已结算
                        BsSubAccount bsSubAccount = new BsSubAccount();
                        bsSubAccount.setId(productAccount.getId());
                        bsSubAccount.setStatus(Constants.SUBACCOUNT_STATUS_SETTLE);
                        bsSubAccountMapper.updateByPrimaryKeySelective(bsSubAccount);

                        BsSubAccountPairExample pairExample = new BsSubAccountPairExample();
                        pairExample.createCriteria().andAuthAccountIdEqualTo(bsSubAccount.getId());
                        List<BsSubAccountPair> pairs = bsSubAccountPairMapper.selectByExample(pairExample);
                        if(CollectionUtils.isNotEmpty(pairs) && pairs.get(0).getDiffAccountId() != null){
                            bsSubAccount.setId(pairs.get(0).getDiffAccountId());
                            bsSubAccount.setStatus(Constants.SUBACCOUNT_STATUS_SETTLE);
                            bsSubAccountMapper.updateByPrimaryKeySelective(bsSubAccount);
                        }
                        if(CollectionUtils.isNotEmpty(pairs) && pairs.get(0).getRedAccountId() != null){
                            bsSubAccount.setId(pairs.get(0).getRedAccountId());
                            bsSubAccount.setStatus(Constants.SUBACCOUNT_STATUS_SETTLE);
                            bsSubAccountMapper.updateByPrimaryKeySelective(bsSubAccount);
                        }
                        
                        //5.2、F：bs_sub_account用户JSH金额增加（回款到余额），bs_account_jnl
                        BsSubAccount tempUserJsh = bsSubAccountMapper.selectSingleSubActByUserAndType(userId, Constants.PRODUCT_TYPE_DEP_JSH);
                        BsSubAccount jshSubAccount = bsSubAccountMapper.selectSubAccountByIdForLock(tempUserJsh.getId());
                        BsSubAccount jsh = new BsSubAccount();
                        jsh.setId(jshSubAccount.getId());
                        jsh.setBalance(MoneyUtil.add(jshSubAccount.getBalance(), realReturnAmount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                        jsh.setAvailableBalance(MoneyUtil.add(jshSubAccount.getAvailableBalance(), realReturnAmount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                        jsh.setCanWithdraw(MoneyUtil.add(jshSubAccount.getCanWithdraw(), realReturnAmount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                        jsh.setLastTransDate(new Date());
                        bsSubAccountMapper.updateByPrimaryKeySelective(jsh);
                        //用户记账流水表   --回款到余额
                        BsAccountJnl bsAccountJnl = new BsAccountJnl();
                        bsAccountJnl.setTransTime(now);
                        bsAccountJnl.setTransCode(Constants.USER_RETURN_2_BALANCE);
                        bsAccountJnl.setTransName("回款到余额");
                        bsAccountJnl.setTransAmount(realReturnAmount);
                        bsAccountJnl.setSysTime(now);
                        bsAccountJnl.setCdFlag1(Constants.CD_FLAG_D);
                        bsAccountJnl.setUserId1(userId);
                        bsAccountJnl.setAccountId1(jshSubAccount.getAccountId());
                        bsAccountJnl.setSubAccountId1(jshSubAccount.getId());
                        bsAccountJnl.setSubAccountCode1(jshSubAccount.getCode());
                        bsAccountJnl.setBeforeBalance1(jshSubAccount.getBalance());
                        bsAccountJnl.setBeforeAvialableBalance1(jshSubAccount.getAvailableBalance());
                        bsAccountJnl.setBeforeFreezeBalance1(jshSubAccount.getFreezeBalance());
                        bsAccountJnl.setAfterBalance1(MoneyUtil.add(jshSubAccount.getBalance() , realReturnAmount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                        bsAccountJnl.setAfterAvialableBalance1(MoneyUtil.add(jshSubAccount.getAvailableBalance(), realReturnAmount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                        bsAccountJnl.setAfterFreezeBalance1(jshSubAccount.getFreezeBalance());
                        bsAccountJnl.setFee(0d);
                        bsAccountJnl.setStatus(Constants.ACCOUNT_JNL_STATUS_SUCCESS);
                        bsAccountJnlMapper.insertSelective(bsAccountJnl);

                        //通过奖励金发放回款
                        if(bonus > 0){
                            //修改用户信息表
                            BsUser updateUser = new BsUser();
                            updateUser.setId(bsUser.getId());
                            updateUser.setCurrentBonus(MoneyUtil.add(bsUser.getCurrentBonus()==null?0.0:bsUser.getCurrentBonus(), bonus).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                            updateUser.setTotalBonus(MoneyUtil.add(bsUser.getTotalBonus()==null?0.0:bsUser.getTotalBonus(), bonus).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                            bsUserMapper.updateByPrimaryKeySelective(updateUser);
                            //修改用户子账户表
                            BsSubAccount tempJlj = bsSubAccountMapper.selectSingleSubActByUserAndType(bsUser.getId(),Constants.PRODUCT_TYPE_JLJ);
                            BsSubAccount subJljAccountLock = bsSubAccountMapper.selectSubAccountByIdForLock(tempJlj.getId());
                            BsSubAccount readySubJljAccount = new BsSubAccount();
                            readySubJljAccount.setId(subJljAccountLock.getId());
                            readySubJljAccount.setBalance(MoneyUtil.add(subJljAccountLock.getBalance(), bonus).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                            readySubJljAccount.setAvailableBalance(MoneyUtil.add(subJljAccountLock.getAvailableBalance(), bonus).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                            readySubJljAccount.setCanWithdraw(MoneyUtil.add(subJljAccountLock.getCanWithdraw(), bonus).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                            bsSubAccountMapper.updateByPrimaryKeySelective(readySubJljAccount);
                            //新增用户记账流水表
                            BsAccountJnl accountJljJnl = new BsAccountJnl();
                            accountJljJnl.setTransTime(new Date());
                            accountJljJnl.setTransCode(Constants.USER_FILL_INTEREST_BONUS);
                            accountJljJnl.setTransName("奖励金补息");
                            accountJljJnl.setTransAmount(bonus);
                            accountJljJnl.setSysTime(new Date());
                            accountJljJnl.setCdFlag1(Constants.CD_FLAG_C);
                            accountJljJnl.setUserId1(bsUser.getId());
                            accountJljJnl.setCdFlag2(Constants.CD_FLAG_D);
                            accountJljJnl.setUserId2(bsUser.getId());
                            accountJljJnl.setAccountId2(subJljAccountLock.getAccountId());
                            accountJljJnl.setSubAccountId2(subJljAccountLock.getId());
                            accountJljJnl.setSubAccountCode2(subJljAccountLock.getCode());
                            accountJljJnl.setBeforeBalance2(subJljAccountLock.getBalance());
                            accountJljJnl.setAfterBalance2(readySubJljAccount.getBalance());
                            accountJljJnl.setBeforeAvialableBalance2(subJljAccountLock.getAvailableBalance());
                            accountJljJnl.setAfterAvialableBalance2(readySubJljAccount.getAvailableBalance());
                            accountJljJnl.setBeforeFreezeBalance2(subJljAccountLock.getFreezeBalance());
                            accountJljJnl.setAfterFreezeBalance2(subJljAccountLock.getFreezeBalance());
                            accountJljJnl.setFee(0.0);
                            bsAccountJnlMapper.insertSelective(accountJljJnl);
                            //新增用户奖励金明细表
                            BsDailyBonus daily = new BsDailyBonus();
                            daily.setUserId(bsUser.getId());
                            daily.setBeRecommendUserId(null);
                            daily.setSubAccountId(bsSubAccount.getId());
                            daily.setBonus(bonus);
                            daily.setTime(new Date());
                            daily.setType(Constants.Daily_BONUS_TYPE_DEP_FILL_INTEREST);
                            bsDailyBonusMapper.insertSelective(daily);
                        }
                        
                        PartnerSysActCode partnerActCode = BaseAccount.partnerActCodeMap.get(PartnerEnum.YUN_DAI_SELF);
                        //获取渠道系统账户代码
                        if( productInfo != null ) {
                        	partnerActCode = BaseAccount.partnerActCodeMap.get(PartnerEnum.getEnumByCode(productInfo.getPropertySymbol()));
                        	if(PartnerEnum.FREE.getCode().equals(productInfo.getPropertySymbol()))  {
                        		partnerActCode = BaseAccount.getFreeActLoanPartner(PartnerEnum.getEnumByCode(productInfo.getPropertySymbol()));
                        	}
                        }
                        log.info("partnerActCode={"+JSONObject.toJSONString(partnerActCode)+"");
                        //5.3、存管用户余额户BGW_USER户金额增加(本息合计)，bs_sys_account_jnl
                        BsSysSubAccount userSubAccountLock = bsSysSubAccountService.findSysSubAccount4Lock(Constants.SYS_ACCOUNT_BGW_USER);
                        //存管用户余额户BGW_USER户增加
                        BsSysSubAccount userReadyUpdate = new BsSysSubAccount();
                        userReadyUpdate.setId(userSubAccountLock.getId());
                        userReadyUpdate.setBalance(MoneyUtil.add(userSubAccountLock.getBalance(), realReturnAmount).doubleValue());
                        userReadyUpdate.setAvailableBalance(MoneyUtil.add(userSubAccountLock.getAvailableBalance(), realReturnAmount).doubleValue());
                        userReadyUpdate.setCanWithdraw(MoneyUtil.add(userSubAccountLock.getCanWithdraw(), realReturnAmount).doubleValue());
                        sysAccountMapper.updateByPrimaryKeySelective(userReadyUpdate);
                        //系统记账流水表BGW_USER(本息)+
                        BsSysAccountJnl sysBgwUserAccountJnl = new BsSysAccountJnl();
                        sysBgwUserAccountJnl.setTransTime(now);
                        sysBgwUserAccountJnl.setTransCode(Constants.SYS_USER_RETURN_2_BALANCE);
                        sysBgwUserAccountJnl.setTransName("用户回款到余额（本金+利息）");
                        sysBgwUserAccountJnl.setTransAmount(amount);
                        sysBgwUserAccountJnl.setSysTime(now);
                        sysBgwUserAccountJnl.setCdFlag2(Constants.CD_FLAG_D);
                        sysBgwUserAccountJnl.setSubAccountCode2(userSubAccountLock.getCode());
                        sysBgwUserAccountJnl.setBeforeBalance2(userSubAccountLock.getBalance());
                        sysBgwUserAccountJnl.setAfterBalance2(userReadyUpdate.getBalance());
                        sysBgwUserAccountJnl.setBeforeAvialableBalance2(userSubAccountLock.getAvailableBalance());
                        sysBgwUserAccountJnl.setAfterAvialableBalance2(userReadyUpdate.getAvailableBalance());
                        sysBgwUserAccountJnl.setBeforeFreezeBalance2(userSubAccountLock.getFreezeBalance());
                        sysBgwUserAccountJnl.setAfterFreezeBalance2(userSubAccountLock.getFreezeBalance());
                        sysBgwUserAccountJnl.setFee(0.0);
                        sysBgwUserAccountJnl.setStatus(Constants.SYS_ACCOUNT_STATUS_SUCCESS);
                        sysBgwUserAccountJnl.setNote("回款到余额："+realReturnAmount+";回款到奖励金："+bonus); //从回款户出来有两个部分，一是奖励金，二是用户余额；系统没有奖励金户，这里记录流水不分开记，只做备注
                        sysAccountJnlMapper.insertSelective(sysBgwUserAccountJnl);
                        //5、登记回款bs_customer_receive_money
                        BsCustomerReceiveMoney receiveMoney = new BsCustomerReceiveMoney();
                        receiveMoney.setAmountBase(returnCheck.getPrincipal());
                        receiveMoney.setAmountInterest(returnCheck.getInterest());
                        receiveMoney.setBankNo(null);
                        receiveMoney.setCardNo(bsUser.getIdCard());
                        receiveMoney.setCreateTime(new Date());
                        receiveMoney.setCustomerName(bsUser.getUserName());
                        receiveMoney.setOrderNo(null);
                        receiveMoney.setProductCode(String.valueOf(productAccount.getProductId()));
                        receiveMoney.setStatus(0);
                        receiveMoney.setSuccessTime(new Date());
                        customerReceiveMoneyService.addCustomerReceiveMoney(receiveMoney);



                        // bs_user.current_bonus bs_user.total_bonus 用户表奖励金账户（+加息券利息），更新奖励金子账户bs_sub_account.product_type（'JLJ'），
                        // 记录用户账户流水bs_account_jnl
                        // bs_daily_bonus增加用户奖励金明细记录(bs_daily_bonus.sub_account_id)（bs_daily_bonus.type(INTEREST_TICKET：加息券收益)）
                        // bs_bonus_grant_plan.grant_type(INTEREST_TICKET：加息券收益)增加奖励金发放计划SUCC
                        BsInterestTicketInfoExample example = new BsInterestTicketInfoExample();
                        example.createCriteria().andUserIdEqualTo(userId).andAuthAccountIdEqualTo(productAccount.getId()).andStatusEqualTo(Constants.TICKET_INTEREST_STATUS_USED);
                        List<BsInterestTicketInfo> ticketList = bsInterestTicketInfoMapper.selectByExample(example);
                        if(CollectionUtils.isNotEmpty(ticketList)) {
                            // 存在加息券
                            BsInterestTicketInfo info = ticketList.get(0);
                            BsUser updateUser = new BsUser();
                            updateUser.setId(bsUser.getId());
                            updateUser.setCurrentBonus(info.getInterestAmount());
                            updateUser.setTotalBonus(info.getInterestAmount());
                            bsUserMapper.updateBonusById(updateUser);

                            //修改用户子账户表-JLJ
                            BsSubAccount tempJlj = bsSubAccountMapper.selectSingleSubActByUserAndType(bsUser.getId(), Constants.PRODUCT_TYPE_JLJ);
                            BsSubAccount subJljAccountLock = bsSubAccountMapper.selectSubAccountByIdForLock(tempJlj.getId());
                            BsSubAccount readySubJljAccount = new BsSubAccount();
                            readySubJljAccount.setId(subJljAccountLock.getId());
                            readySubJljAccount.setBalance(MoneyUtil.add(subJljAccountLock.getBalance(), info.getInterestAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                            readySubJljAccount.setAvailableBalance(MoneyUtil.add(subJljAccountLock.getAvailableBalance(), info.getInterestAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                            readySubJljAccount.setCanWithdraw(MoneyUtil.add(subJljAccountLock.getCanWithdraw(), info.getInterestAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                            bsSubAccountMapper.updateByPrimaryKeySelective(readySubJljAccount);

                            //新增用户记账流水表
                            BsAccountJnl accountJljJnl = new BsAccountJnl();
                            accountJljJnl.setTransTime(new Date());
                            accountJljJnl.setTransCode(Constants.TICKET_INTEREST_TYPE_INTEREST_TICKET);
                            accountJljJnl.setTransName("加息券收益");
                            accountJljJnl.setTransAmount(info.getInterestAmount());
                            accountJljJnl.setSysTime(new Date());
                            accountJljJnl.setCdFlag1(Constants.CD_FLAG_C);
                            accountJljJnl.setUserId1(bsUser.getId());
                            accountJljJnl.setCdFlag2(Constants.CD_FLAG_D);
                            accountJljJnl.setUserId2(bsUser.getId());
                            accountJljJnl.setAccountId2(subJljAccountLock.getAccountId());
                            accountJljJnl.setSubAccountId2(subJljAccountLock.getId());
                            accountJljJnl.setSubAccountCode2(subJljAccountLock.getCode());
                            accountJljJnl.setBeforeBalance2(subJljAccountLock.getBalance());
                            accountJljJnl.setAfterBalance2(readySubJljAccount.getBalance());
                            accountJljJnl.setBeforeAvialableBalance2(subJljAccountLock.getAvailableBalance());
                            accountJljJnl.setAfterAvialableBalance2(readySubJljAccount.getAvailableBalance());
                            accountJljJnl.setBeforeFreezeBalance2(subJljAccountLock.getFreezeBalance());
                            accountJljJnl.setAfterFreezeBalance2(subJljAccountLock.getFreezeBalance());
                            accountJljJnl.setFee(0.0);
                            bsAccountJnlMapper.insertSelective(accountJljJnl);
                            //新增用户奖励金明细表
                            BsDailyBonus daily = new BsDailyBonus();
                            daily.setUserId(bsUser.getId());
                            daily.setSubAccountId(productAccount.getId());
                            daily.setBonus(info.getInterestAmount());
                            daily.setTime(new Date());
                            daily.setType(Constants.TICKET_INTEREST_TYPE_INTEREST_TICKET);
                            bsDailyBonusMapper.insertSelective(daily);

                            BsBonusGrantPlan plan = new BsBonusGrantPlan();
                            plan.setAmount(info.getInterestAmount());
                            plan.setCreateTime(new Date());
                            plan.setFinishDate(new Date());
                            plan.setGrantDate(new Date());
                            plan.setGrantType(Constants.TICKET_INTEREST_TYPE_INTEREST_TICKET);
                            plan.setSerialNo(1);
                            plan.setStatus(Constants.BONUS_GRANT_STATUS_SUCC);
                            plan.setSubAccountId(productAccount.getId());
                            plan.setUpdateTime(new Date());
                            plan.setUserId(bsUser.getId());
                            bsBonusGrantPlanMapper.insertSelective(plan);
                            try {
                            	sendWechatByRabbitService.bonusArrive(bsUser.getId(), plan.getAmount().toString(), DateUtil.format(new Date()));
                            } catch (Exception e) {
                                e.printStackTrace();
                                log.info("========={存管定期产品退出回款到余额}发送加息券消息异常{}=========", e.getMessage());
                            }
                        }
                    }
                });
                try {

                		 log.info("========={存管定期产品退出回款到余额}发送信息=========");
                		 smsServiceClient.sendTemplateMessage(bsUser.getMobile(), TemplateKey.RETURN_SUCCESS2BALANCE,
                                 String.valueOf(amount), productPrincipal.toString(), MoneyUtil.add(productInterest, overflowInterest).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                         //发送微信模板消息
                         sendWechatService.paymentMgs2Balance(userId,"",
                                 String.valueOf(amount), productPrincipal.toString(), MoneyUtil.add(productInterest, overflowInterest).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
				
                   
				} catch (Exception e) {
					log.info("========={存管定期产品退出回款到余额}发送消息异常=========", e);
				}
                
                log.info("========={存管定期产品退出回款到余额}结束=========");
            } catch (Exception e) {
            	log.info("========={存管定期产品退出回款到余额}产品户编号["+returnCheck.getSubAccountId()+"]回款产生异常=========", e);
                //告警
                specialJnlService.warn4Fail(returnCheck.getReturnAmount(), "{存管定期产品退出回款到余额}产品户编号["+returnCheck.getSubAccountId()+"]回款产生异常",
                        null,"存管定期产品退出回款到余额",false);
            }
        } finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_DEP_FIXED_USER_RECEIVEMONEY.getKey());
        }
    
    }

	@Override
	public void return2Balance4Before(BsBatchReturnDetail batchReturnDetail) {
		/**
		 * 1、待回款产品明细表状态设为回款成功；2、记录用户交易明细；3、DEP_JSH增加回款；
		 * 4、系统回款户减少；5、系统子账户表BGW_USER户增加
		 * 6、用户表当前投资收益减少，可提现金额增加
		 * 7、登记该条回款记录BsCustomerReceiveMoney
		 */
		try {
            jsClientDaoSupport.lock(RedisLockEnum.LOCK_USER_RECEIVEMONEY.getKey());
            log.info("========={单笔客户回款}开始 3.10之前=========");
            if(isRepeatReturnDetail(batchReturnDetail.getId()))
                return;
            try {
                //查询用户信息
                final Integer userId = batchReturnDetail.getUserId();
                //用户信息
                final BsUser user = userService.findUserById(userId);
                final Integer subAccountId = batchReturnDetail.getSubAccountId();
                //产品户信息
                final BsSubAccountVO productAccount = subAccountService.findMyInvestByUserIdAndId(userId, subAccountId);
                //回款路径为余额
                log.info("========={单笔客户回款}回款到余额开始=========");
                final Integer detailId = batchReturnDetail.getId();
                final Integer batchBuyId = batchReturnDetail.getBatchId();
                final Double amount = batchReturnDetail.getReturnAmount();
                final Double productPrincipal = batchReturnDetail.getPrincipal();
                final Double productInterest = batchReturnDetail.getInterest();
                //该笔理财是否是最后次回款
                final boolean isLastReturn = true;
                transactionTemplate.execute(new TransactionCallbackWithoutResult(){
                    @Override
                    protected void doInTransactionWithoutResult(TransactionStatus status) {
                        //待回款产品明细表状态设为回款成功
                        Date now = new Date();
                        BsBatchReturnDetail updateDetail = new BsBatchReturnDetail();
                        updateDetail.setId(detailId);
                        updateDetail.setReturnStatus(Constants.RETURN_STATUS_SUCCESS);
                        updateDetail.setUpdateTime(now);
                        bsBatchReturnDetailMapper.updateByPrimaryKeySelective(updateDetail);
                        //用户此产品户状态改为已结算
                        if(isLastReturn){
                            BsSubAccount reg = new BsSubAccount();
                            reg.setId(subAccountId);
                            reg.setStatus(Constants.SUBACCOUNT_STATUS_SETTLE);
                            subAccountService.modifySubAccountById(reg);
                        }

                        //记录用户交易明细
                        BsUserTransDetail transdetail = new BsUserTransDetail();
                        transdetail.setUserId(userId);
                        transdetail.setCardNo(null);
                        transdetail.setTransType(Constants.Trans_TYPE_RETURN);
                        transdetail.setTransStatus(Constants.Trans_STATUS_SUCCESS);
                        transdetail.setOrderNo(null);
                        transdetail.setCreateTime(now);
                        transdetail.setAmount(amount);
                        transdetail.setUpdateTime(now);
                        bsUserTransDetailMapper.insertSelective(transdetail);
                        BsSubAccount tempUserJsh = bsSubAccountMapper.selectSingleSubActByUserAndType(userId, Constants.PRODUCT_TYPE_DEP_JSH);
                        BsSubAccount jshSubAccount = bsSubAccountMapper.selectSubAccountByIdForLock(tempUserJsh.getId());
                        BsSubAccount jsh = new BsSubAccount();
                        jsh.setId(jshSubAccount.getId());
                        jsh.setBalance(MoneyUtil.add(jshSubAccount.getBalance(), amount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                        jsh.setAvailableBalance(MoneyUtil.add(jshSubAccount.getAvailableBalance(), amount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                        jsh.setCanWithdraw(MoneyUtil.add(jshSubAccount.getCanWithdraw(), amount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                        jsh.setLastTransDate(new Date());
                        bsSubAccountMapper.updateByPrimaryKeySelective(jsh);
                        //用户记账流水表   --回款到余额
                        BsAccountJnl bsAccountJnl = new BsAccountJnl();
                        bsAccountJnl.setTransTime(now);
                        bsAccountJnl.setTransCode(Constants.USER_RETURN_2_BALANCE);
                        bsAccountJnl.setTransName("回款到余额");
                        bsAccountJnl.setTransAmount(amount);
                        bsAccountJnl.setSysTime(now);
                        bsAccountJnl.setCdFlag1(Constants.CD_FLAG_D);
                        bsAccountJnl.setUserId1(userId);
                        bsAccountJnl.setAccountId1(jshSubAccount.getAccountId());
                        bsAccountJnl.setSubAccountId1(jshSubAccount.getId());
                        bsAccountJnl.setSubAccountCode1(jshSubAccount.getCode());
                        bsAccountJnl.setBeforeBalance1(jshSubAccount.getBalance());
                        bsAccountJnl.setBeforeAvialableBalance1(jshSubAccount.getAvailableBalance());
                        bsAccountJnl.setBeforeFreezeBalance1(jshSubAccount.getFreezeBalance());
                        bsAccountJnl.setAfterBalance1(MoneyUtil.add(jshSubAccount.getBalance() , amount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                        bsAccountJnl.setAfterAvialableBalance1(MoneyUtil.add(jshSubAccount.getAvailableBalance(), amount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                        bsAccountJnl.setAfterFreezeBalance1(jshSubAccount.getFreezeBalance());
                        bsAccountJnl.setFee(0d);
                        bsAccountJnl.setStatus(Constants.ACCOUNT_JNL_STATUS_SUCCESS);
                        bsAccountJnlMapper.insertSelective(bsAccountJnl);
                        //用户表当前投资收益减少，可提现金额增加
                        BsUser userUpdate = new BsUser();
                        userUpdate.setId(userId);
                        userUpdate.setCanWithdraw(amount);
                        if(isLastReturn){
                            Double currInterest = user.getCurrentInterest();
                            if (productInterest > 0) {
                                if(MoneyUtil.subtract(productInterest, currInterest).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() > 0){
                                    userUpdate.setCurrentInterest(-currInterest);
                                    //告警
                                    //specialJnlService.warn4Fail(currInterest, "{用户回款到卡通知}产品户编号["+subAccountId+"]当前收益减去回款利息为负数|"+currInterest+"-"+productInterest, null,"用户回款到卡通知当前收益为负",false);
                                }else{
                                    userUpdate.setCurrentInterest(-productInterest);
                                }
                            }
                        }
                        bsUserMapper.updateUserAmountInfoById(userUpdate);
                        //系统子账户表RETURN户减少
                        BsSysSubAccount userSubAccountLock = bsSysSubAccountService.findSysSubAccount4Lock(Constants.SYS_ACCOUNT_BGW_USER);
                        BsBatchBuy bsBatchBuy = bsBatchBuyMapper.selectByPrimaryKey(batchBuyId);
                        String actCode = Util.getSysReturnSubActCode(ThirdSysChannelEnum.getEnumByCode(bsBatchBuy.getPropertySymbol()));
                        BsSysSubAccount sysSubAccountLock = bsSysSubAccountService.findSysSubAccount4Lock(actCode);
                        BsSysSubAccount readyUpdate = new BsSysSubAccount();
                        readyUpdate.setId(sysSubAccountLock.getId());
                        readyUpdate.setBalance(MoneyUtil.subtract(sysSubAccountLock.getBalance(), amount).doubleValue());
                        readyUpdate.setAvailableBalance(MoneyUtil.subtract(sysSubAccountLock.getAvailableBalance(), amount).doubleValue());
                        readyUpdate.setCanWithdraw(MoneyUtil.subtract(sysSubAccountLock.getCanWithdraw(), amount).doubleValue());
                        sysAccountMapper.updateByPrimaryKeySelective(readyUpdate);
                        //系统子账户表USER户增加
                        BsSysSubAccount userReadyUpdate = new BsSysSubAccount();
                        userReadyUpdate.setId(userSubAccountLock.getId());
                        userReadyUpdate.setBalance(MoneyUtil.add(userSubAccountLock.getBalance(), amount).doubleValue());
                        userReadyUpdate.setAvailableBalance(MoneyUtil.add(userSubAccountLock.getAvailableBalance(), amount).doubleValue());
                        userReadyUpdate.setCanWithdraw(MoneyUtil.add(userSubAccountLock.getCanWithdraw(), amount).doubleValue());
                        sysAccountMapper.updateByPrimaryKeySelective(userReadyUpdate);
                        //系统记账流水表
                        BsSysAccountJnl sysAccountJnl = new BsSysAccountJnl();
                        sysAccountJnl.setTransTime(now);
                        sysAccountJnl.setTransCode(Constants.SYS_USER_RETURN_2_BALANCE);
                        sysAccountJnl.setTransName("用户回款到余额（本金+利息）");
                        sysAccountJnl.setTransAmount(amount);
                        sysAccountJnl.setSysTime(now);
                        sysAccountJnl.setCdFlag1(Constants.CD_FLAG_C);
                        sysAccountJnl.setSubAccountCode1(sysSubAccountLock.getCode());
                        sysAccountJnl.setBeforeBalance1(sysSubAccountLock.getBalance());
                        sysAccountJnl.setAfterBalance1(readyUpdate.getBalance());
                        sysAccountJnl.setBeforeAvialableBalance1(sysSubAccountLock.getAvailableBalance());
                        sysAccountJnl.setAfterAvialableBalance1(readyUpdate.getAvailableBalance());
                        sysAccountJnl.setBeforeFreezeBalance1(sysSubAccountLock.getFreezeBalance());
                        sysAccountJnl.setAfterFreezeBalance1(sysSubAccountLock.getFreezeBalance());
                        sysAccountJnl.setCdFlag2(Constants.CD_FLAG_D);
                        sysAccountJnl.setSubAccountCode2(userSubAccountLock.getCode());
                        sysAccountJnl.setBeforeBalance2(userSubAccountLock.getBalance());
                        sysAccountJnl.setAfterBalance2(userReadyUpdate.getBalance());
                        sysAccountJnl.setBeforeAvialableBalance2(userSubAccountLock.getAvailableBalance());
                        sysAccountJnl.setAfterAvialableBalance2(userReadyUpdate.getAvailableBalance());
                        sysAccountJnl.setBeforeFreezeBalance2(userSubAccountLock.getFreezeBalance());
                        sysAccountJnl.setAfterFreezeBalance2(userSubAccountLock.getFreezeBalance());
                        sysAccountJnl.setFee(0.0);
                        sysAccountJnl.setStatus(Constants.SYS_ACCOUNT_STATUS_SUCCESS);
                        sysAccountJnlMapper.insertSelective(sysAccountJnl);

                        //登记该条回款记录BsCustomerReceiveMoney
                        if(isLastReturn){
                            BsCustomerReceiveMoney receiveMoney = new BsCustomerReceiveMoney();
                            receiveMoney.setAmountBase(productPrincipal);
                            receiveMoney.setAmountInterest(productInterest);
                            receiveMoney.setBankNo(null);
                            receiveMoney.setCardNo(user.getIdCard());
                            receiveMoney.setCreateTime(now);
                            receiveMoney.setCustomerName(user.getUserName());
                            receiveMoney.setOrderNo(null);
                            receiveMoney.setProductCode(String.valueOf(productAccount.getProductId()));
                            receiveMoney.setStatus(0);
                            receiveMoney.setSuccessTime(now);
                            customerReceiveMoneyService.addCustomerReceiveMoney(receiveMoney);
                        }

                    }
                });
                if(isLastReturn){
                	try {
                        smsServiceClient.sendTemplateMessage(user.getMobile(), TemplateKey.RETURN_SUCCESS2BALANCE,
                                String.valueOf(MoneyUtil.add(productPrincipal, productInterest).doubleValue()), productPrincipal.toString(), productInterest.toString());
                        //发送微信模板消息
                        sendWechatService.paymentMgs2Balance(userId,"",
                                String.valueOf(MoneyUtil.add(productPrincipal, productInterest).doubleValue()), productPrincipal.toString(), productInterest.toString());
					} catch (Exception e) {
						 log.info("========={单笔客户回款}微信模板异常=========", e);
					}
 
                }
                log.info("========={单笔客户回款}结束=========");
            } catch (Exception e) {
                log.info("========={单笔客户回款}产品户编号["+batchReturnDetail.getSubAccountId()+"]回款产生异常=========", e);
                //告警
                specialJnlService.warn4Fail(batchReturnDetail.getReturnAmount(), "{单笔客户回款}产品户编号["+batchReturnDetail.getSubAccountId()+"]回款产生异常",
                        batchReturnDetail.getReturnOrderNo(),"单笔客户回款",false);
            }
        } finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_USER_RECEIVEMONEY.getKey());
        }
		
	}
}

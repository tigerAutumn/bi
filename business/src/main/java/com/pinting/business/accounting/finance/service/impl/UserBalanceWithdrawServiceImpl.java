package com.pinting.business.accounting.finance.service.impl;

import com.pinting.business.accounting.finance.model.DFResultInfo;
import com.pinting.business.accounting.finance.service.DepUserBalanceWithdrawService;
import com.pinting.business.accounting.finance.service.DepUserBonusWithdrawService;
import com.pinting.business.accounting.finance.service.UserBalanceWithdrawService;
import com.pinting.business.accounting.loan.enums.LoanStatus;
import com.pinting.business.accounting.loan.enums.VIPId4PartnerEnum;
import com.pinting.business.accounting.loan.service.LoanRelationshipService;
import com.pinting.business.accounting.loan.util.redisUtil;
import com.pinting.business.accounting.service.BsSysSubAccountService;
import com.pinting.business.accounting.service.PayOrdersService;
import com.pinting.business.dao.*;
import com.pinting.business.enums.*;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.hessian.manage.ReqMsg_UserBalance_WithdrawFall;
import com.pinting.business.hessian.manage.ReqMsg_UserBalance_WithdrawPass;
import com.pinting.business.hessian.manage.ResMsg_UserBalance_WithdrawFall;
import com.pinting.business.hessian.manage.ResMsg_UserBalance_WithdrawPass;
import com.pinting.business.hessian.site.message.ReqMsg_UserBalance_Withdraw;
import com.pinting.business.hessian.site.message.ResMsg_UserBalance_Withdraw;
import com.pinting.business.model.*;
import com.pinting.business.model.vo.BalanceWithdrawCheckVO;
import com.pinting.business.model.vo.CommissionVO;
import com.pinting.business.model.vo.LoanNoticeVO;
import com.pinting.business.service.common.CommissionService;
import com.pinting.business.service.manage.BsSysConfigService;
import com.pinting.business.service.site.*;
import com.pinting.business.util.Constants;
import com.pinting.business.util.Util;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.core.util.encrypt.MD5Util;
import com.pinting.gateway.hessian.message.baofoo.B2GReqMsg_BaoFooQuickPay_Pay4Trans;
import com.pinting.gateway.hessian.message.baofoo.B2GResMsg_BaoFooQuickPay_Pay4Trans;
import com.pinting.gateway.hessian.message.pay19.enums.OrderStatus;
import com.pinting.gateway.in.util.MethodRole;
import com.pinting.gateway.out.service.BaoFooTransportService;
import com.pinting.gateway.out.service.SMSServiceClient;
import com.pinting.gateway.smsEnums.TemplateKey;
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
import java.util.List;

/**
 * Created by babyshark on 2016/9/10.
 */
@Service
public class UserBalanceWithdrawServiceImpl implements UserBalanceWithdrawService {
    private Logger logger = LoggerFactory.getLogger(UserBalanceWithdrawServiceImpl.class);
    private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);
    @Autowired
    private UserService userService;
    @Autowired
    private BsUserMapper bsUserMapper;
    @Autowired
    private BankCardService bankCardService;
    @Autowired
    private BsPayOrdersMapper bsPayOrdersMapper;
    @Autowired
    private BsPayOrdersJnlMapper bsPayOrdersJnlMapper;
    @Autowired
    private SpecialJnlService specialJnlService;
    @Autowired
    private SubAccountService subAccountService;
    @Autowired
    private BsAccountJnlMapper bsAccountJnlMapper;
    @Autowired
    private BsUserTransDetailMapper bsUserTransDetailMapper;
    @Autowired
    private PayOrdersService payOrdersService;
    @Autowired
    private BsSysSubAccountMapper bsSysSubAccountMapper;
    @Autowired
    private BsSysAccountJnlMapper bsSysAccountJnlMapper;
    @Autowired
    private SMSServiceClient smsServiceClient;
    @Autowired
    private SendWechatService sendWechatService;
    @Autowired
    private BsWithdrawCheckService bsWithdrawCheckService;
    @Autowired
    private BaoFooTransportService baoFooTransportService;
    @Autowired
    private BsServiceFeeMapper bsServiceFeeMapper;
    @Autowired
    private BsSysConfigMapper bsSysConfigMapper;
    @Autowired
    private CommissionService commissionService;
    @Autowired
    private BsPayResultQueueMapper queueMapper;
    @Autowired
    private BsSubAccountMapper subAccountMapper;
    @Autowired
    private LoanRelationshipService loanRelationshipService;
    @Autowired
    private BsSysConfigService bsSysConfigService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private BsSysSubAccountService bsSysSubAccountService;
    @Autowired
    private DepUserBalanceWithdrawService depUserBalanceWithdrawService;
    @Autowired
    private DepUserBonusWithdrawService depUserBonusWithdrawService;
    @Autowired
    private BsHfbankUserExtMapper bsHfbankUserExtMapper;

    @Override
    //@Transactional
    @MethodRole("APP")
    public void apply(final ReqMsg_UserBalance_Withdraw req,
                      final ResMsg_UserBalance_Withdraw res) {
        // 零、公共校验
        // 1、用户是否存在
        // 2、是否有回款卡
        // 一、用户提现
        // 1、校验用户支付密码是否正确
        // 2、可用余额是否足够
        // 3、用户信息表可用余额减少
        // 4、用户结算户，冻结金额增加，可用余额减少
        // 5、订单信息表中添加一条记录
        // 6、添加一条订单明细信息
        // 7、同时记录记账流水表
        // 8、记录用户交易明细表(状态为处理中)
        // 二、管理台审核提现
        // 1、检验是否存在该提现审核记录
        // 2、查询JSH冻结余额是否足够
        // 3、订单信息表中添加一条记录
        // 4、订单明细表中添加一条记录
        // 5、修改记录提现审核表
        // 6、修改交易明细表，添加订单号，修改状态为处理中
        // 三、公共处理
        // 1.发起代付请求
        logger.info("提现请求：{}", JSONObject.fromObject(req));

        BalanceWithdrawCheckVO check = new BalanceWithdrawCheckVO();
        check.setUserId(req.getUserId());
        depUserBalanceWithdrawService.withdrawCheck(check);

        final BsPayOrders order = new BsPayOrders();
        BsPayOrdersJnl insertOrderJnl = new BsPayOrdersJnl();
        BsUser user;
        BsUser tempUser;
        BsSubAccount jshDB;
        BsSubAccount jsh;
        BsAccountJnl bsAccountJnl;
        BsBankCard receiveCard;
        Date applyTime;
        Double planFee = 0d;
        Double fee = 0d;
        try {
            jsClientDaoSupport.lock(RedisLockEnum.LOCK_WITHDRAW_SERVICE.getKey());
            // 零、公共校验
            // 1.检验用户是否存在
            user = userService.findUserById(req.getUserId());
            if (user == null) {
                throw new PTMessageException(PTMessageEnum.USER_NO_EXIT);
            }
            // 代偿人且不是安卓
            // 赞分期代偿人不允许前端提现
            if(isCompensatoryUserZAN(req.getUserId()) && Constants.TERMINAL_TYPE_MANAGE != req.getTerminalType()) {
                throw new PTMessageException(PTMessageEnum.WITHDRAW_NOT_ALLOWED);
            }
//            if(isSuperUser(req.getUserId())){
//                throw new PTMessageException(PTMessageEnum.WITHDRAW_NOT_ALLOWED);
//            }
            // 2、是否有回款卡
            receiveCard = bankCardService.findFirstBankCardByUserId(req
                    .getUserId());
            if (receiveCard == null) {
                throw new PTMessageException(PTMessageEnum.BANKCARD_NOT_BIND);
            }
            // 3、是否恒丰开户
            BsHfbankUserExtExample extExample = new BsHfbankUserExtExample();
            extExample.createCriteria().andUserIdEqualTo(user.getId()).andStatusEqualTo(Constants.HFBANK_USER_EXT_STATUS_OPEN);
            List<BsHfbankUserExt> ext = bsHfbankUserExtMapper.selectByExample(extExample);
            if (CollectionUtils.isEmpty(ext)) {
                throw new PTMessageException(PTMessageEnum.HF_NOT_BIND);
            }

            if (StringUtil.isBlank(req.getCheckId())) {
                logger.info("用户提现开始：{}", JSONObject.fromObject(req));
                // 用户提现
                applyTime = new Date();
                // 1、校验用户支付密码是否正确
                if (!user.getPayPassword()
                        .equals(MD5Util.encryptMD5(req.getPassword()
                                + MD5Util.getEncryptkey()))) {
                    throw new PTMessageException(PTMessageEnum.USER_PAY_PASS_ERROR);
                }
                BsSysConfig eachMonthWithdrawTimesConfig = bsSysConfigMapper.selectByPrimaryKey(Constants.EACH_MONTH_WITHDRAW_TIMES);
                int each_month_withdraw_times = Integer.parseInt(eachMonthWithdrawTimesConfig.getConfValue());
                CommissionVO commission = commissionService.calServiceFee(req.getAmount(), TransTypeEnum.USER_WITHDRAW, PayPlatformEnum.BAOFOO);
                Double withdraw_counter_fee = commission.getActPayAmount();
                // 1.查询当月提现成功订单，判断是否超过三次
                String applyTimeYYYYMM = DateUtil.formatDateTime(applyTime, "yyyy-MM");
                String startTime = applyTimeYYYYMM + "-01 00:00:00";
                Date startDate = DateUtil.parse(startTime, "yyyy-MM-dd HH:mm:ss");
                Date endDate = new Date();
                int historyWithdrawOrderCount = countHistoryWithdraw(startDate, endDate, req);

                // 最小限额校验
                BsSysConfig withdrawLimitConfig = bsSysConfigMapper.selectByPrimaryKey(Constants.WITHDRAW_LIMIT);
                Double withdrawLimit = Double.valueOf(withdrawLimitConfig.getConfValue());
                if (each_month_withdraw_times - historyWithdrawOrderCount > 0) {
                    if (req.getAmount().compareTo(withdrawLimit) < 0) {
                        throw new PTMessageException(PTMessageEnum.WITHDRAW_AMOUNT_SURPASS);
                    }
                } else {
                    Double limit = withdrawLimit.compareTo(withdraw_counter_fee) > 0 ? withdrawLimit : withdraw_counter_fee;
                    if (req.getAmount().compareTo(limit) <= 0) {
                        throw new PTMessageException(PTMessageEnum.WITHDRAW_AMOUNT_SURPASS);
                    }
                }
                // 2、可用余额是否足够
                BsSubAccount tempJsh = subAccountMapper.selectSingleSubActByUserAndType(req.getUserId(), Constants.PRODUCT_TYPE_JSH);
                jshDB = subAccountMapper.selectSubAccountByIdForLock(tempJsh.getId());
                if (MoneyUtil.subtract(req.getAmount(), jshDB.getCanWithdraw()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() > 0) {
                    throw new PTMessageException(PTMessageEnum.WITHDRAW_AMOUNT_SURPASS);
                }
                if (historyWithdrawOrderCount >= each_month_withdraw_times) {
                    fee = withdraw_counter_fee;
                    planFee = commission.getNeedPayAmount();
                    // 用户提现金额不够手续费，则判断余额是否足够
                    Double leftAmount = MoneyUtil.subtract(jshDB.getCanWithdraw(), withdraw_counter_fee).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    if (leftAmount.compareTo(0d) <= 0) {
                        throw new PTMessageException(PTMessageEnum.WITHDRAW_MORE_THAN_3_TIMES_AMOUNT_LESS);
                    }
                    // 1.查询当月提现成功订单超过三次，扣除手续费
                    String realAmount = MoneyUtil.subtract(req.getAmount(), withdraw_counter_fee).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
                    if (Double.valueOf(realAmount).compareTo(0d) < 0) {
                        throw new PTMessageException(PTMessageEnum.WITHDRAW_MORE_THAN_3_TIMES_AMOUNT_LESS);
                    }
                    order.setAmount(Double.valueOf(realAmount));
                } else {
                    order.setAmount(req.getAmount());
                }

                // 4、用户结算户，冻结金额增加，可用余额，可提现余额减少
                jsh = new BsSubAccount();
                jsh.setId(jshDB.getId());
                jsh.setAvailableBalance(-req.getAmount());
                jsh.setCanWithdraw(-req.getAmount());
                jsh.setFreezeBalance(req.getAmount());
                jsh.setLastTransDate(new Date());
                subAccountService.modifyBalancesByIncrement(jsh);

                // 5、订单信息表中添加一条记录
                Date now = new Date();
                String orderNo = Util.generateOrderNo4BaoFoo(8);
                order.setTerminalType(req.getTerminalType());
                order.setAccountType(Constants.ORDER_ACCOUNT_TYPE_USER);
                order.setBankCardNo(receiveCard.getCardNo());
                order.setBankId(receiveCard.getBankId());
                order.setBankName(BaoFooEnum.pay4BankMap.get(String.valueOf(receiveCard.getBankId())));
                order.setChannel(Constants.CHANNEL_BAOFOO);    // 提现渠道修改为宝付
                order.setChannelTransType(Constants.CHANNEL_TRS_DF);
                order.setCreateTime(now);
                order.setIdCard(receiveCard.getIdCard());
                order.setMobile(receiveCard.getMobile());
                order.setMoneyType(Constants.ORDER_CURRENCY_CNY);
                order.setOrderNo(orderNo);
                order.setStatus(Constants.ORDER_STATUS_CREATE);
                order.setSubAccountId(null);
                order.setTransType(Constants.TRANS_BALANCE_WITHDRAW);
                order.setUpdateTime(now);
                order.setUserId(req.getUserId());
                order.setUserName(receiveCard.getCardOwner());
                bsPayOrdersMapper.insertSelective(order);
                res.setWithdrawTime(now);
                res.setOrderNo(orderNo);

                // 6、添加一条订单明细信息
                insertOrderJnl.setCreateTime(new Date());
                insertOrderJnl.setOrderId(order.getId());
                insertOrderJnl.setOrderNo(order.getOrderNo());
                insertOrderJnl.setSubAccountCode(null);
                insertOrderJnl.setSubAccountId(null);
                insertOrderJnl.setSysTime(new Date());
                insertOrderJnl.setTransAmount(order.getAmount());
                insertOrderJnl.setTransCode(Constants.ORDER_TRANS_CODE_INIT);
                insertOrderJnl.setUserId(req.getUserId());
                insertOrderJnl.setReturnCode(null);
                insertOrderJnl.setReturnMsg(null);
                bsPayOrdersJnlMapper.insertSelective(insertOrderJnl);

                // 10、收取2元手续费，记录手续费登记表
                BsServiceFee bsServiceFee = new BsServiceFee();
                bsServiceFee.setPlanFee(planFee);
                bsServiceFee.setDoneFee(fee);
                bsServiceFee.setTransAmount(order.getAmount());
                bsServiceFee.setFeeType(Constants.FEE_TYPE_FINANCE_WITHDRAW);
                bsServiceFee.setStatus(Constants.FEE_STATUS_COLLECT);
                bsServiceFee.setCreateTime(new Date());
                bsServiceFee.setOrderNo(order.getOrderNo());
                bsServiceFee.setSubAccountId(order.getSubAccountId());
                bsServiceFee.setUpdateTime(new Date());
                bsServiceFee.setNote("应扣"+ planFee +"，实扣"+fee);
                bsServiceFee.setPaymentPlatformFee(commission.getThreePartyPaymentServiceFee());
                bsServiceFeeMapper.insertSelective(bsServiceFee);

                // 7、同时记录记账流水表
                bsAccountJnl = new BsAccountJnl();
                bsAccountJnl.setTransTime(new Date());
                bsAccountJnl.setTransCode(Constants.USER_BALANCE_WITHDRAW);
                bsAccountJnl.setTransName("用户提现，冻结余额");// 冻结
                bsAccountJnl.setTransAmount(req.getAmount());
                bsAccountJnl.setSysTime(new Date());
                bsAccountJnl.setCdFlag1(Constants.CD_FLAG_C);
                bsAccountJnl.setUserId1(req.getUserId());
                bsAccountJnl.setAccountId1(jshDB.getAccountId());
                bsAccountJnl.setSubAccountId1(jshDB.getId());
                bsAccountJnl.setSubAccountCode1(jshDB.getCode());
                bsAccountJnl.setBeforeBalance1(jshDB.getBalance());
                bsAccountJnl.setBeforeAvialableBalance1(jshDB.getAvailableBalance());
                bsAccountJnl.setBeforeFreezeBalance1(jshDB.getFreezeBalance());
                bsAccountJnl.setAfterBalance1(jshDB.getBalance());
                bsAccountJnl.setAfterAvialableBalance1(MoneyUtil.subtract(jshDB.getAvailableBalance()
                        , req.getAmount()).doubleValue());
                bsAccountJnl.setAfterFreezeBalance1(MoneyUtil.add(jshDB.getFreezeBalance()
                        , req.getAmount()).doubleValue());
                bsAccountJnl.setCdFlag2(Constants.CD_FLAG_D);
                bsAccountJnl.setUserId2(req.getUserId());
                bsAccountJnl.setSubAccountCode2(jshDB.getCode());
                bsAccountJnl.setFee(fee);
                bsAccountJnl.setStatus(Constants.ACCOUNT_JNL_STATUS_SUCCESS);
                bsAccountJnlMapper.insertSelective(bsAccountJnl);

                // 8、记录用户交易明细表(状态为处理中)
                BsUserTransDetail transDetail = new BsUserTransDetail();
                transDetail.setUserId(req.getUserId());
                transDetail.setCardNo(receiveCard.getCardNo());
                transDetail.setTransType(Constants.Trans_TYPE_WITHDRAW);
                transDetail.setTransStatus(Constants.Trans_STATUS_DEAL);
                transDetail.setOrderNo(order.getOrderNo());
                transDetail.setCreateTime(now);
                transDetail.setAmount(-order.getAmount());
                transDetail.setUpdateTime(now);
                bsUserTransDetailMapper.insertSelective(transDetail);
                if(fee.compareTo(0d) > 0){
                    BsUserTransDetail feeTransDetail = new BsUserTransDetail();
                    feeTransDetail.setUserId(req.getUserId());
                    feeTransDetail.setCardNo(receiveCard.getCardNo());
                    feeTransDetail.setTransType(Constants.Trans_TYPE_WITHDRAW_FEE);
                    feeTransDetail.setTransStatus(Constants.Trans_STATUS_DEAL);
                    feeTransDetail.setOrderNo(order.getOrderNo());
                    feeTransDetail.setCreateTime(now);
                    feeTransDetail.setAmount(-fee);
                    feeTransDetail.setUpdateTime(now);
                    bsUserTransDetailMapper.insertSelective(feeTransDetail);
                }

                // 3、用户信息表可用余额减少
                tempUser = new BsUser();
                tempUser.setCanWithdraw(-req.getAmount());
                tempUser.setId(req.getUserId());
                bsUserMapper.updateBonusById(tempUser);

            } else {
                logger.info("管理台审核提现开始：{}", JSONObject.fromObject(req));
                // 管理台审核提现
                // 1.检验是否存在该提现审核记录
                BsWithdrawCheck bsWithdrawCheck = bsWithdrawCheckService
                        .selectWithdrawCheck(Integer.parseInt(req.getCheckId()));
                applyTime = bsWithdrawCheck.getCreateTime();
                if (bsWithdrawCheck == null
                        || (!bsWithdrawCheck.getStatus().equals(
                        Constants.WITHDRAW_TO_CHECK))) {
                    throw new PTMessageException(PTMessageEnum.WITHDRAW_CHECK_NO_EXIT);
                }
                req.setAmount(bsWithdrawCheck.getAmount());

                // 2.查询JSH冻结余额是否足够
                jshDB = subAccountService
                        .findJSHSubAccountByUserId(bsWithdrawCheck.getUserId());
                if (bsWithdrawCheck.getAmount() > jshDB.getFreezeBalance()) {
                    throw new PTMessageException(PTMessageEnum.WITHDRAW_AMOUNT_SURPASS);
                }
                // 3、订单信息表中添加一条记录
                Date now = new Date();
                String orderNo = Util.generateOrderNo4BaoFoo(8);

                BsSysConfig eachMonthWithdrawTimesConfig = bsSysConfigMapper.selectByPrimaryKey(Constants.EACH_MONTH_WITHDRAW_TIMES);
                int each_month_withdraw_times = Integer.parseInt(eachMonthWithdrawTimesConfig.getConfValue());
                CommissionVO commission = commissionService.calServiceFee(req.getAmount(), TransTypeEnum.USER_WITHDRAW, PayPlatformEnum.BAOFOO);
                Double withdraw_counter_fee = commission.getActPayAmount();
                // 1.查询当月提现成功订单，判断是否超过三次
                String applyTimeYYYYMM = DateUtil.formatDateTime(applyTime, "yyyy-MM");
                String startTime = applyTimeYYYYMM + "-01 00:00:00";
                Date startDate = DateUtil.parse(startTime, "yyyy-MM-dd HH:mm:ss");
                Date endDate = bsWithdrawCheck.getCreateTime();
                int historyWithdrawOrderCount = countHistoryWithdraw(startDate, endDate, req);

                // 最小限额校验
                BsSysConfig withdrawLimitConfig = bsSysConfigMapper.selectByPrimaryKey(Constants.WITHDRAW_LIMIT);
                Double withdrawLimit = Double.valueOf(withdrawLimitConfig.getConfValue());
                if (each_month_withdraw_times - historyWithdrawOrderCount > 0) {
                    if (req.getAmount().compareTo(withdrawLimit) < 0) {
                        throw new PTMessageException(PTMessageEnum.WITHDRAW_AMOUNT_SURPASS);
                    }
                } else {
                    Double limit = withdrawLimit.compareTo(withdraw_counter_fee) > 0 ? withdrawLimit : withdraw_counter_fee;
                    if (req.getAmount().compareTo(limit) <= 0) {
                        throw new PTMessageException(PTMessageEnum.WITHDRAW_AMOUNT_SURPASS);
                    }
                }

                if (historyWithdrawOrderCount >= each_month_withdraw_times) {
                    // 1.查询当月提现成功订单超过三次，扣除手续费
                    logger.info("bsWithdrawCheck.getAmount() " + bsWithdrawCheck.getAmount());
                    logger.info("withdraw_counter_fee " + withdraw_counter_fee);
                    String realAmount = MoneyUtil.subtract(bsWithdrawCheck.getAmount(), withdraw_counter_fee).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
                    if (Double.valueOf(realAmount).compareTo(0d) < 0) {
                        throw new PTMessageException(PTMessageEnum.WITHDRAW_MORE_THAN_3_TIMES_AMOUNT_LESS);
                    }
                    order.setAmount(Double.valueOf(realAmount));
                    fee = withdraw_counter_fee;
                    planFee = commission.getNeedPayAmount();
                } else {
                    order.setAmount(bsWithdrawCheck.getAmount());
                }
                order.setTerminalType(bsWithdrawCheck.getTerminalType());
                order.setAccountType(Constants.ORDER_ACCOUNT_TYPE_USER);
                order.setBankCardNo(receiveCard.getCardNo());
                order.setBankId(receiveCard.getBankId());
                order.setBankName(BaoFooEnum.pay4BankMap.get(String.valueOf(receiveCard.getBankId())));
                order.setChannel(Constants.CHANNEL_BAOFOO);
                order.setChannelTransType(Constants.CHANNEL_TRS_DF);
                order.setCreateTime(now);
                order.setIdCard(receiveCard.getIdCard());
                order.setMobile(receiveCard.getMobile());
                order.setMoneyType(Constants.ORDER_CURRENCY_CNY);
                order.setOrderNo(orderNo);
                order.setStatus(Constants.ORDER_STATUS_CREATE);
                order.setSubAccountId(null);
                order.setTransType(Constants.TRANS_BALANCE_WITHDRAW);
                order.setUpdateTime(now);
                order.setUserId(bsWithdrawCheck.getUserId());
                order.setUserName(receiveCard.getCardOwner());
                bsPayOrdersMapper.insertSelective(order);
                // 4、订单明细表中添加一条记录
                insertOrderJnl.setCreateTime(new Date());
                insertOrderJnl.setOrderId(order.getId());
                insertOrderJnl.setOrderNo(order.getOrderNo());
                insertOrderJnl.setSubAccountCode(null);
                insertOrderJnl.setSubAccountId(null);
                insertOrderJnl.setSysTime(new Date());
                insertOrderJnl.setTransAmount(order.getAmount());
                insertOrderJnl.setTransCode(Constants.ORDER_TRANS_CODE_INIT);
                insertOrderJnl.setUserId(bsWithdrawCheck.getUserId());
                insertOrderJnl.setReturnCode(null);
                insertOrderJnl.setReturnMsg(null);
                bsPayOrdersJnlMapper.insertSelective(insertOrderJnl);
                // 10、收取2元手续费，记录手续费登记表
                BsServiceFee bsServiceFee = new BsServiceFee();
                bsServiceFee.setPlanFee(planFee);
                bsServiceFee.setDoneFee(fee);
                bsServiceFee.setTransAmount(order.getAmount());
                bsServiceFee.setFeeType(Constants.FEE_TYPE_FINANCE_WITHDRAW);
                bsServiceFee.setStatus(Constants.FEE_STATUS_COLLECT);
                bsServiceFee.setCreateTime(new Date());
                bsServiceFee.setOrderNo(order.getOrderNo());
                bsServiceFee.setSubAccountId(order.getSubAccountId());
                bsServiceFee.setUpdateTime(new Date());
                bsServiceFee.setNote("应扣"+ planFee +"，实扣"+fee);
                bsServiceFee.setPaymentPlatformFee(commission.getThreePartyPaymentServiceFee());
                bsServiceFeeMapper.insertSelective(bsServiceFee);
                // 5、修改记录提现审核表
                bsWithdrawCheck.setStatus(Constants.WITHDRAW_PASS);
                bsWithdrawCheck.setId(Integer.parseInt(req.getCheckId()));
                bsWithdrawCheck.setOrderNo(order.getOrderNo());
                bsWithdrawCheck.setmUserId(req.getManageId());
                bsWithdrawCheckService.updateWithdrawCheck(bsWithdrawCheck);
                // 6、修改交易明细表，添加订单号，修改状态为处理中
                BsUserTransDetail tmpTransDetail = new BsUserTransDetail();
                tmpTransDetail.setId(bsWithdrawCheck.getTransDetailId());
                tmpTransDetail.setOrderNo(orderNo);
                tmpTransDetail.setTransStatus(Constants.Trans_STATUS_DEAL);
                tmpTransDetail.setUpdateTime(new Date());
                bsUserTransDetailMapper.updateByPrimaryKeySelective(tmpTransDetail);
                if(bsWithdrawCheck.getSubTransDetailId() != null){
                    BsUserTransDetail tmpFeeTransDetail = new BsUserTransDetail();
                    tmpFeeTransDetail.setId(bsWithdrawCheck.getSubTransDetailId());
                    tmpFeeTransDetail.setOrderNo(orderNo);
                    tmpFeeTransDetail.setTransStatus(Constants.Trans_STATUS_DEAL);
                    tmpFeeTransDetail.setUpdateTime(new Date());
                    bsUserTransDetailMapper.updateByPrimaryKeySelective(tmpFeeTransDetail);
                }
            }
        } finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_WITHDRAW_SERVICE.getKey());
        }
        // 三、公共处理
        // 1、发起代付请求
        logger.info("提现发起代付请求：{}", JSONObject.fromObject(order));
        B2GResMsg_BaoFooQuickPay_Pay4Trans resMsg;
        B2GReqMsg_BaoFooQuickPay_Pay4Trans b2gReq = new B2GReqMsg_BaoFooQuickPay_Pay4Trans();
        b2gReq.setTo_acc_name(order.getUserName());
        b2gReq.setTo_acc_no(order.getBankCardNo());
        b2gReq.setTo_bank_name(order.getBankName());
        b2gReq.setTrans_money(MoneyUtil.multiply(order.getAmount(), 1).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        b2gReq.setTrans_no(order.getOrderNo());
        b2gReq.setTrans_card_id(receiveCard.getIdCard());
        b2gReq.setTrans_mobile(receiveCard.getMobile());
        b2gReq.setTrans_summary("提现");
        try {
            resMsg = baoFooTransportService.pay4Trans(b2gReq);
        } catch (Exception e){
            logger.error("提现申请通讯失败：{}", e);
            resMsg = new B2GResMsg_BaoFooQuickPay_Pay4Trans();
            resMsg.setResCode(Constants.GATEWAY_PAYING_RES_CODE);
            resMsg.setResMsg("通讯失败，置为处理中");
        }

        // 代付下单申请成功，等待通知. 此时更新订单表状态为处理中
        if (ConstantUtil.BSRESCODE_SUCCESS.equals(resMsg.getResCode())) {
            logger.info("提现宝付代付下单申请成功：{}", JSONObject.fromObject(resMsg));
            // 更新订单表
            payOrdersService.modifyOrderStatus4Safe(order.getId(), Constants.ORDER_STATUS_PAYING,
                    null, resMsg.getResCode(), resMsg.getResMsg());
            DFResultInfo resultInfo = new DFResultInfo();
            resultInfo.setAmount(order.getAmount());
            resultInfo.setFinishTime(new Date());
            resultInfo.setMxOrderId(order.getOrderNo());
            resultInfo.setSysOrderId(resMsg.getTrans_orderid());
            resultInfo.setOrderStatus(OrderStatus.SUCCESS.getCode());
            this.notify(resultInfo);

        } else if(Constants.GATEWAY_PAYING_RES_CODE.equals(resMsg.getResCode())) {
            logger.info("提现宝付代付下单申请处理中：{}", JSONObject.fromObject(resMsg));
            // 更新订单表
            payOrdersService.modifyOrderStatus4Safe(order.getId(), Constants.ORDER_STATUS_PAYING,
                    null, resMsg.getResCode(), resMsg.getResMsg());
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
        } else {
            // 代付下单申请失败
            // 1.用户信息表可用余额增加回来(还原)
            // 2.更新订单表，状态为失败
            // 3.添加一条订单明细信息
            // 4.用户结算户，做反向操作（冻结金额减少，可用余额增加）
            // 5.添加一条解冻记账流水
            // 6.更新用户交易明细表，状态为失败
            // 7.特殊交易流水表中，添加一条记录
            //手续费状态改为回退

            logger.error("提现宝付代付下单申请失败：{}", JSONObject.fromObject(resMsg));
            final String errorMsg = resMsg.getResMsg();
            final String thirdReturnCode = Util.getThirdReturnCode(res);
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {
                    BsPayOrders lockOrder = bsPayOrdersMapper.selectByPKForLock(order.getId());
                    // 2.更新订单表，状态为失败
                    payOrdersService.modifyOrderStatus4Safe(order.getId(), Constants.ORDER_STATUS_FAIL,
                            null, thirdReturnCode, errorMsg);
                    // 3.添加一条订单明细信息
                    BsPayOrdersJnl insertOrderJnl = new BsPayOrdersJnl();
                    insertOrderJnl.setCreateTime(new Date());
                    insertOrderJnl.setOrderId(order.getId());
                    insertOrderJnl.setOrderNo(order.getOrderNo());
                    insertOrderJnl.setSubAccountCode(null);
                    insertOrderJnl.setSubAccountId(null);
                    insertOrderJnl.setSysTime(new Date());
                    insertOrderJnl.setTransAmount(order.getAmount());
                    insertOrderJnl.setTransCode(Constants.ORDER_TRANS_CODE_FAIL);
                    insertOrderJnl.setUserId(req.getUserId());
                    insertOrderJnl.setReturnCode(thirdReturnCode);
                    insertOrderJnl.setReturnMsg(errorMsg);
                    bsPayOrdersJnlMapper.insertSelective(insertOrderJnl);
                }
            });
            // 1.用户信息表可用余额增加回来(还原)
            tempUser = new BsUser();
            tempUser.setCanWithdraw(req.getAmount());
            tempUser.setId(req.getUserId());
            bsUserMapper.updateBonusById(tempUser);
            //修改手续费状态为回退
            BsServiceFee bsServiceFee = new BsServiceFee();
            bsServiceFee.setStatus(Constants.FEE_STATUS_RETURN);
            bsServiceFee.setUpdateTime(new Date());
            BsServiceFeeExample bsServiceFeeExample = new BsServiceFeeExample();
            bsServiceFeeExample.createCriteria().andOrderNoEqualTo(order.getOrderNo()).andFeeTypeEqualTo(Constants.FEE_TYPE_FINANCE_WITHDRAW);
            bsServiceFeeMapper.updateByExampleSelective(bsServiceFee, bsServiceFeeExample);

            // 4.用户结算户，做反向操作（冻结金额减少，可用余额增加,可提现余额增加）
            jshDB = subAccountService
                    .findJSHSubAccountByUserId(req.getUserId());
            jsh = new BsSubAccount();
            jsh.setId(jshDB.getId());
            jsh.setAvailableBalance(req.getAmount());
            jsh.setCanWithdraw(req.getAmount());
            jsh.setFreezeBalance(-req.getAmount());
            jsh.setLastTransDate(new Date());
            subAccountService.modifyBalancesByIncrement(jsh);

            // 5.添加一条解冻记账流水
            bsAccountJnl = new BsAccountJnl();
            bsAccountJnl.setTransTime(new Date());
            bsAccountJnl.setTransCode(Constants.USER_UNFREEZE);
            bsAccountJnl.setTransName("用户提现发送失败，解冻余额");// 解冻
            bsAccountJnl.setTransAmount(req.getAmount());
            bsAccountJnl.setSysTime(new Date());
            bsAccountJnl.setCdFlag1(Constants.CD_FLAG_D);
            bsAccountJnl.setUserId1(req.getUserId());
            bsAccountJnl.setAccountId1(jshDB.getAccountId());
            bsAccountJnl.setSubAccountId1(jshDB.getId());
            bsAccountJnl.setSubAccountCode1(jshDB.getCode());
            bsAccountJnl.setBeforeBalance1(jshDB.getBalance());
            bsAccountJnl
                    .setBeforeAvialableBalance1(jshDB.getAvailableBalance());
            bsAccountJnl.setBeforeFreezeBalance1(jshDB.getFreezeBalance());
            bsAccountJnl.setAfterBalance1(jshDB.getBalance());
            bsAccountJnl.setAfterAvialableBalance1(MoneyUtil.add(jshDB.getAvailableBalance()
                    , req.getAmount()).doubleValue());
            bsAccountJnl.setAfterFreezeBalance1(MoneyUtil.subtract(jshDB.getFreezeBalance()
                    , req.getAmount()).doubleValue());
            bsAccountJnl.setCdFlag2(Constants.CD_FLAG_C);
            bsAccountJnl.setUserId2(req.getUserId());
            bsAccountJnl.setSubAccountCode2(jshDB.getCode());
            bsAccountJnl.setFee(fee);
            bsAccountJnl.setStatus(Constants.ACCOUNT_JNL_STATUS_FAIL);
            bsAccountJnlMapper.insertSelective(bsAccountJnl);

            // 6.更新用户交易明细表，状态为失败
            BsUserTransDetailExample example = new BsUserTransDetailExample();
            example.createCriteria().andOrderNoEqualTo(order.getOrderNo());
            BsUserTransDetail tmpTransDetail = new BsUserTransDetail();
            tmpTransDetail.setTransStatus(Constants.Trans_STATUS_FAIL);
            tmpTransDetail.setReturnCode(thirdReturnCode);
            tmpTransDetail.setReturnMsg(res.getResMsg());
            tmpTransDetail.setUpdateTime(new Date());
            bsUserTransDetailMapper.updateByExampleSelective(tmpTransDetail, example);
            // 7.特殊交易流水表中，添加一条记录
            try {
                specialJnlService.warn4Fail(req.getAmount(), "【客户余额提现】用户编号["
                                + req.getUserId() + "]回款产生异常", order.getOrderNo(),
                        "客户余额提现", false);
                smsServiceClient.sendTemplateMessage(user.getMobile(),
                        TemplateKey.WITHDRAW_FALL, String.valueOf(req.getAmount()),
                        "通讯失败请重试");
                // 微信模板消息
                String bankCard = receiveCard.getCardNo();
                sendWechatService.withdrawMgs(user.getId(), "", bankCard.substring(
                        bankCard.length() - 4, bankCard.length()), String
                        .valueOf(req.getAmount()), "fall", "通讯失败请重试", null, null);
            }catch (Exception e){
                logger.error("提现失败通知到用户异常", e);
            }
        }
        // 6.后置处理(无数据库更新处理)
    }

    @Override
    public int countHistoryWithdraw(Date startDate, Date endDate, ReqMsg_UserBalance_Withdraw req) {
        String startTime = DateUtil.formatDateTime(startDate, "yyyy-MM-dd HH:mm:ss");
        String endTime = DateUtil.formatDateTime(endDate, "yyyy-MM-dd HH:mm:ss");
        int count = bsUserTransDetailMapper.countWithdrawExcludeReturn(req.getUserId(), startTime, endTime);
        return count;
    }

    @Override
    public void notify(DFResultInfo req) {
        try {
            logger.info("提现通知开始", JSONObject.fromObject(req));
            // 检查是否是否重复通知（根据订单信息是否是处理中，如果不是，则已经处理过，拒绝再次处理）
            String orderNo = req.getMxOrderId();
            BsPayOrdersExample example = new BsPayOrdersExample();
            example.createCriteria().andOrderNoEqualTo(orderNo)
                    .andStatusEqualTo(Constants.ORDER_STATUS_PAYING);
            List<BsPayOrders> orderList = bsPayOrdersMapper
                    .selectByExample(example);

            if (orderList == null || orderList.isEmpty()) {// 没有查到处理中的交易
                // 继续去掉状态查询，如果是已完成的订单，则为重复通知
                example = new BsPayOrdersExample();
                example.createCriteria().andOrderNoEqualTo(orderNo);
                orderList = bsPayOrdersMapper.selectByExample(example);
                if (orderList != null && !orderList.isEmpty()) {
                    // 记录特殊交易流水表
                    BsPayOrders order = orderList.get(0);
                    logger.warn("【用户提现通知异常】用户编号[" + order.getUserId() + "]宝付重复通知");
                    /*specialJnlService.warn4FailNoSMS(order.getAmount(),
                            "【用户提现通知异常】用户编号[" + order.getUserId() + "]宝付重复通知",
                            order.getOrderNo(), "用户提现通知异常");*/
                    return;
                } else {// 没有发生过这个交易，记录特殊流水表
                    specialJnlService.warn4FailNoSMS(-1.0,
                            "【用户提现通知异常】用户编号[未知]宝付通知了一个未知的订单号：" + orderNo, orderNo,
                            "用户提现通知异常");
                    return;
                }
            }

            // 查到了处理中的交易，为一个有效的通知
            BsPayOrders order = orderList.get(0);
            if (order.getStatus() != Constants.ORDER_STATUS_PAYING) {// 订单状态已经被修改，此次通知认为是重复通知，不做处理
                // 记录特殊交易流水表
                logger.warn("【用户提现通知异常】用户编号[" + order.getUserId() + "]宝付重复通知");
                /*specialJnlService.warn4FailNoSMS(order.getAmount(), "【用户提现通知异常】用户编号["
                                + order.getUserId() + "]宝付重复通知", order.getOrderNo(),
                        "用户提现通知异常");*/
                return;
            }

            if(Constants.TRANS_BONUS_WITHDRAW.equals(order.getTransType())) {
                // 奖励金提现
                depUserBonusWithdrawService.notifyBonusWithdraw(req);
                return;
            }

            String mobile = userService.findUserById(order.getUserId()).getMobile();

            BsServiceFeeExample bsServiceFeeExample = new BsServiceFeeExample();
            bsServiceFeeExample.createCriteria().andOrderNoEqualTo(order.getOrderNo())
                    .andFeeTypeEqualTo(Constants.FEE_TYPE_FINANCE_WITHDRAW);
            List<BsServiceFee> bsServiceFeeList = bsServiceFeeMapper.selectByExample(bsServiceFeeExample);
            Double fee = 0d;
            if(CollectionUtils.isNotEmpty(bsServiceFeeList)) {
                fee = bsServiceFeeList.get(0).getDoneFee();
            }
            Double totalAmount = MoneyUtil.defaultRound(MoneyUtil.add(order.getAmount(), fee)).doubleValue();

            if (OrderStatus.SUCCESS.getCode().equals(req.getOrderStatus())) {
                logger.info("通知提现成功：{}", JSONObject.fromObject(req));
                // 提现成功
                withdrawSuccess(req);
                try {
                    /*
				 * smsService.sendMessage(mobile, "您有一笔金额为：￥" + order.getAmount() +
				 * "元的提现已完成，如有疑问请拨打400-806-1230。");
				 */
                    smsServiceClient.sendTemplateMessage(mobile,
                            TemplateKey.WITHDRAW_SUC, totalAmount.toString(), fee.toString(), order.getAmount().toString());
                    // 微信模板消息
                    logger.info("提现成功：提现微信模板消息请求信息：{}", JSONObject.fromObject(order));
                    String bankCard = order.getBankCardNo();
                    sendWechatService.withdrawMgs(order.getUserId(), "", bankCard.substring(bankCard.length() - 4, bankCard.length()),
                            totalAmount.toString(), "suc", null, fee.toString(), order.getAmount().toString());
                }catch (Exception e){
                    logger.error("提现成功通知到用户异常", e);
                }

            } else {// 提现失败
                logger.info("通知提现失败：{}", JSONObject.fromObject(req));

                withdrawFail(req);
                try {
                /*
				 * smsService.sendMessage(mobile, "您有一笔金额为：￥" + order.getAmount() +
				 * "元的提现交易失败，失败原因："+ req.getErrorMsg() + "，如有疑问请拨打400-806-1230。");
				 */
                    smsServiceClient.sendTemplateMessage(mobile,
                            TemplateKey.WITHDRAW_FALL, totalAmount.toString(),
                            req.getErrorMsg());
                    // 微信模板消息
                    logger.info("提现失败：提现微信模板消息请求信息：{}",JSONObject.fromObject(order));
                    String bankCard = order.getBankCardNo();
                    sendWechatService.withdrawMgs(order.getUserId(), "", bankCard
                            .substring(bankCard.length() - 4, bankCard.length()), totalAmount.toString(), "fall", req.getErrorMsg(), null, null);
                }catch (Exception e){
                    logger.error("提现失败通知到用户异常", e);
                }
            }
        } finally {
        }

    }

    // 通知提现成功处理逻辑
    //@Transactional（！！！直接调用方法时 注解将不会生效！！！
    // 方法内事务建议使用编程式事务或通过Service调用注解式事务的方法）
    private void withdrawSuccess(final DFResultInfo req) {
        transactionTemplate.execute(new TransactionCallbackWithoutResult(){
            @Override
            public void doInTransactionWithoutResult(TransactionStatus status) {
                logger.info("通知提现成功，请求参数：{}", JSONObject.fromObject(req));
                String orderNo = req.getMxOrderId();
                BsPayOrdersExample ordersExample = new BsPayOrdersExample();
                ordersExample.createCriteria().andOrderNoEqualTo(orderNo);
                List<BsPayOrders> orderList = bsPayOrdersMapper.selectByExample(ordersExample);
                BsPayOrders order = bsPayOrdersMapper.selectByPKForLock(orderList.get(0).getId());
                int userId = order.getUserId();

                // 1.修改提现的用户交易明细表,状态为成功
                BsUserTransDetailExample example = new BsUserTransDetailExample();
                example.createCriteria().andOrderNoEqualTo(orderNo);
                BsUserTransDetail tmpTransDetail = new BsUserTransDetail();
                tmpTransDetail.setTransStatus(Constants.Trans_STATUS_SUCCESS);
                tmpTransDetail.setReturnCode(req.getRetCode());
                tmpTransDetail.setReturnMsg(req.getErrorMsg());
                tmpTransDetail.setUpdateTime(new Date());
                bsUserTransDetailMapper.updateByExampleSelective(tmpTransDetail, example);

                // 2.更新订单信息表状态为成功
                BsPayOrders updateOrder = new BsPayOrders();
                updateOrder.setId(order.getId());
                updateOrder.setStatus(Constants.ORDER_STATUS_SUCCESS);
                updateOrder.setReturnCode(ConstantUtil.BSRESCODE_SUCCESS);
                updateOrder.setReturnMsg("用户提现成功");
                updateOrder.setUpdateTime(new Date());
                bsPayOrdersMapper.updateByPrimaryKeySelective(updateOrder);

                // 3.订单明细信息表添加一条记录
                BsPayOrdersJnl insertOrderJnl = new BsPayOrdersJnl();
                insertOrderJnl.setCreateTime(new Date());
                insertOrderJnl.setOrderId(order.getId());
                insertOrderJnl.setOrderNo(order.getOrderNo());
                insertOrderJnl.setSubAccountCode(null);
                insertOrderJnl.setSubAccountId(null);
                insertOrderJnl.setSysTime(new Date());
                insertOrderJnl.setTransAmount(order.getAmount());
                insertOrderJnl.setTransCode(Constants.ORDER_TRANS_CODE_SUCCESS);
                insertOrderJnl.setUserId(userId);
                insertOrderJnl.setReturnCode(ConstantUtil.BSRESCODE_SUCCESS);
                insertOrderJnl.setReturnMsg("用户提现成功");
                bsPayOrdersJnlMapper.insertSelective(insertOrderJnl);

                // 4.用户信息表余额减少(此处不能再改变，因为发送提现请求的时候已经减少，在此记录只做提醒作用)

                // 5.用户子账户表中冻结金额减少，余额减少。其他不动
                BsServiceFeeExample bsServiceFeeExample = new BsServiceFeeExample();
                bsServiceFeeExample.createCriteria().andOrderNoEqualTo(order.getOrderNo())
                        .andFeeTypeEqualTo(Constants.FEE_TYPE_FINANCE_WITHDRAW);
                List<BsServiceFee> bsServiceFeeList = bsServiceFeeMapper.selectByExample(bsServiceFeeExample);
                Double fee = 0d;
                if(CollectionUtils.isNotEmpty(bsServiceFeeList)) {
                    fee = bsServiceFeeList.get(0).getDoneFee();
                }
                Double realBalance = MoneyUtil.add(order.getAmount(), fee).doubleValue();

                BsSubAccount jshDB = subAccountService
                        .findJSHSubAccountByUserId(userId);
                BsSubAccount jsh = new BsSubAccount();
                jsh.setId(jshDB.getId());
                jsh.setBalance(-realBalance);
                jsh.setFreezeBalance(-realBalance);
                jsh.setLastTransDate(new Date());
                subAccountService.modifyBalancesByIncrement(jsh);

                // 6.用户记账流水表记录两条交易流水（实际提现金额和手续费）
                BsAccountJnl bsAccountJnl = new BsAccountJnl();
                bsAccountJnl.setTransTime(new Date());
                bsAccountJnl.setTransCode(Constants.USER_BALANCE_WITHDRAW);
                bsAccountJnl.setTransName("用户提现成功");// 提现成功
                bsAccountJnl.setTransAmount(order.getAmount());
                bsAccountJnl.setSysTime(new Date());
                bsAccountJnl.setCdFlag1(Constants.CD_FLAG_C);
                bsAccountJnl.setUserId1(userId);
                bsAccountJnl.setAccountId1(jshDB.getAccountId());
                bsAccountJnl.setSubAccountId1(jshDB.getId());
                bsAccountJnl.setSubAccountCode1(jshDB.getCode());
                bsAccountJnl.setBeforeBalance1(jshDB.getBalance());
                bsAccountJnl.setBeforeAvialableBalance1(jshDB.getAvailableBalance());
                bsAccountJnl.setBeforeFreezeBalance1(jshDB.getFreezeBalance());
                bsAccountJnl.setAfterBalance1(MoneyUtil.subtract(jshDB.getBalance(),order.getAmount()).doubleValue());
                bsAccountJnl.setAfterAvialableBalance1(jshDB.getAvailableBalance());
                bsAccountJnl.setAfterFreezeBalance1(MoneyUtil.subtract(jshDB.getFreezeBalance(), order.getAmount()).doubleValue());
                bsAccountJnl.setCdFlag2(Constants.CD_FLAG_D);
                bsAccountJnl.setUserId2(userId);
                bsAccountJnl.setSubAccountCode2(jshDB.getCode());
                bsAccountJnl.setFee(fee);
                bsAccountJnl.setStatus(Constants.ACCOUNT_JNL_STATUS_SUCCESS);
                bsAccountJnl.setChannelTime(req.getFinishTime());
                bsAccountJnl.setChannelJnlNo(req.getSysOrderId());
                bsAccountJnlMapper.insertSelective(bsAccountJnl);


                // 7.系统子账户表中USER账户减少
                BsSysSubAccount sysUserAccount = bsSysSubAccountService.findSysSubAccount4Lock(Constants.SYS_ACCOUNT_USER);
                BsSysSubAccount readyUserUpdate = new BsSysSubAccount();
                readyUserUpdate.setId(sysUserAccount.getId());
                readyUserUpdate.setBalance(MoneyUtil.subtract(sysUserAccount.getBalance(), realBalance).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                readyUserUpdate.setAvailableBalance(MoneyUtil.subtract(sysUserAccount.getAvailableBalance(), realBalance).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                readyUserUpdate.setCanWithdraw(MoneyUtil.subtract(sysUserAccount.getCanWithdraw(), realBalance).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                bsSysSubAccountMapper.updateByPrimaryKeySelective(readyUserUpdate);


                // 8.系统记账流水表记账
                // 实际提现到卡记账
                BsSysAccountJnl sysAccountJnl = new BsSysAccountJnl();
                sysAccountJnl.setTransTime(new Date());
                sysAccountJnl.setTransCode(Constants.SYS_USER_WITHDRAW);
                sysAccountJnl.setTransName("用户提现（实际到卡）");
                sysAccountJnl.setTransAmount(order.getAmount());
                sysAccountJnl.setSysTime(new Date());
                sysAccountJnl.setCdFlag1(Constants.CD_FLAG_C);
                sysAccountJnl.setSubAccountCode1(sysUserAccount.getCode());
                sysAccountJnl.setBeforeBalance1(sysUserAccount.getBalance());
                sysAccountJnl.setAfterBalance1(MoneyUtil.add(readyUserUpdate.getBalance(), fee).doubleValue());
                sysAccountJnl.setBeforeAvialableBalance1(sysUserAccount.getAvailableBalance());
                sysAccountJnl.setAfterAvialableBalance1(MoneyUtil.add(readyUserUpdate.getAvailableBalance(), fee).doubleValue());
                sysAccountJnl.setBeforeFreezeBalance1(sysUserAccount.getFreezeBalance());
                sysAccountJnl.setAfterFreezeBalance1(sysUserAccount.getFreezeBalance());
                sysAccountJnl.setFee(fee);
                sysAccountJnl.setStatus(Constants.SYS_ACCOUNT_STATUS_SUCCESS);
                sysAccountJnl.setChannelTime(req.getFinishTime());
                bsSysAccountJnlMapper.insertSelective(sysAccountJnl);

                if(null != fee && fee > 0) {
                    BsAccountJnl feeAccountJnl = new BsAccountJnl();
                    feeAccountJnl.setTransTime(new Date());
                    feeAccountJnl.setTransCode(Constants.USER_WITHDRAW_FEE);
                    feeAccountJnl.setTransName("提现手续费扣除");// 提现成功
                    feeAccountJnl.setTransAmount(fee);
                    feeAccountJnl.setSysTime(new Date());
                    feeAccountJnl.setCdFlag1(Constants.CD_FLAG_C);
                    feeAccountJnl.setUserId1(userId);
                    feeAccountJnl.setAccountId1(jshDB.getAccountId());
                    feeAccountJnl.setSubAccountId1(jshDB.getId());
                    feeAccountJnl.setSubAccountCode1(jshDB.getCode());
                    feeAccountJnl.setBeforeBalance1(jshDB.getBalance() - order.getAmount());
                    feeAccountJnl.setBeforeAvialableBalance1(jshDB.getAvailableBalance());
                    feeAccountJnl.setBeforeFreezeBalance1(MoneyUtil.subtract(jshDB.getFreezeBalance(), order.getAmount()).doubleValue());
                    feeAccountJnl.setAfterBalance1(MoneyUtil.subtract(jshDB.getBalance(), realBalance).doubleValue());
                    feeAccountJnl.setAfterAvialableBalance1(jshDB.getAvailableBalance());
                    feeAccountJnl.setAfterFreezeBalance1(MoneyUtil.subtract(jshDB.getFreezeBalance(), realBalance).doubleValue());
                    feeAccountJnl.setCdFlag2(Constants.CD_FLAG_D);
                    feeAccountJnl.setUserId2(userId);
                    feeAccountJnl.setSubAccountCode2(jshDB.getCode());
                    feeAccountJnl.setFee(0d);
                    feeAccountJnl.setStatus(Constants.ACCOUNT_JNL_STATUS_SUCCESS);
                    bsAccountJnlMapper.insertSelective(feeAccountJnl);

                    // 9、系统手续费记账
                    BsSysSubAccount sysAccountFee = bsSysSubAccountService.findSysSubAccount4Lock(Constants.SYS_ACCOUNT_FEE);
                    BsSysSubAccount sysAccountFeeTemp = new BsSysSubAccount();
                    sysAccountFeeTemp.setId(sysAccountFee.getId());
                    sysAccountFeeTemp.setAvailableBalance(MoneyUtil.add(sysAccountFee.getAvailableBalance(), fee).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    sysAccountFeeTemp.setBalance(MoneyUtil.add(sysAccountFee.getBalance(), fee).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    sysAccountFeeTemp.setCanWithdraw(MoneyUtil.add(sysAccountFee.getCanWithdraw(), fee).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    bsSysSubAccountMapper.updateByPrimaryKeySelective(sysAccountFeeTemp);

                    //手续费划转记账
                    BsSysAccountJnl sysFeeAccountJnl = new BsSysAccountJnl();
                    sysFeeAccountJnl.setTransTime(new Date());
                    sysFeeAccountJnl.setTransCode(Constants.SYS_FEE_INCOME);
                    sysFeeAccountJnl.setTransName("用户提现手续费划转");
                    sysFeeAccountJnl.setTransAmount(fee);
                    sysFeeAccountJnl.setSysTime(new Date());
                    sysFeeAccountJnl.setCdFlag1(Constants.CD_FLAG_D);
                    sysFeeAccountJnl.setSubAccountCode1(sysAccountFee.getCode());
                    sysFeeAccountJnl.setBeforeBalance1(sysAccountFee.getBalance());
                    sysFeeAccountJnl.setAfterBalance1(sysAccountFeeTemp.getBalance());
                    sysFeeAccountJnl.setBeforeAvialableBalance1(sysAccountFee.getAvailableBalance());
                    sysFeeAccountJnl.setAfterAvialableBalance1(sysAccountFeeTemp.getAvailableBalance());
                    sysFeeAccountJnl.setBeforeFreezeBalance1(sysAccountFee.getFreezeBalance());
                    sysFeeAccountJnl.setAfterFreezeBalance1(sysAccountFee.getFreezeBalance());
                    sysFeeAccountJnl.setCdFlag2(Constants.CD_FLAG_C);
                    sysFeeAccountJnl.setSubAccountCode2(sysUserAccount.getCode());
                    sysFeeAccountJnl.setBeforeBalance2(MoneyUtil.add(readyUserUpdate.getBalance(), fee).doubleValue());
                    sysFeeAccountJnl.setAfterBalance2(readyUserUpdate.getBalance());
                    sysFeeAccountJnl.setBeforeAvialableBalance2(MoneyUtil.add(readyUserUpdate.getAvailableBalance(), fee).doubleValue());
                    sysFeeAccountJnl.setAfterAvialableBalance2(readyUserUpdate.getAvailableBalance());
                    sysFeeAccountJnl.setBeforeFreezeBalance2(sysUserAccount.getFreezeBalance());
                    sysFeeAccountJnl.setAfterFreezeBalance2(sysUserAccount.getFreezeBalance());
                    sysFeeAccountJnl.setFee(0d);
                    sysFeeAccountJnl.setStatus(Constants.SYS_ACCOUNT_STATUS_SUCCESS);
                    bsSysAccountJnlMapper.insertSelective(sysFeeAccountJnl);
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
            }
        });
    }

    // 通知提现失败处理逻辑
    //@Transactional
    private void withdrawFail(final DFResultInfo req) {
        transactionTemplate.execute(new TransactionCallbackWithoutResult(){
            @Override
            public void doInTransactionWithoutResult(TransactionStatus status) {
                logger.info("通知提现失败，请求参数：{}", JSONObject.fromObject(req));
                String orderNo = req.getMxOrderId();
                BsPayOrdersExample ordersExample = new BsPayOrdersExample();
                ordersExample.createCriteria().andOrderNoEqualTo(orderNo);
                List<BsPayOrders> orderList = bsPayOrdersMapper.selectByExample(ordersExample);
                BsPayOrders order = bsPayOrdersMapper.selectByPKForLock(orderList.get(0).getId());
                int userId = order.getUserId();

                // 0.计算实际扣除金额（包含手续费）
                // 1.用户信息表可用余额增加回来(还原)
                // 2.更新订单表，状态为失败
                // 3.添加一条订单明细信息
                // 4.用户结算子账户，做反向操作（冻结金额减少，可用余额增加）
                // 5.添加一条解冻记账流水
                // 6.更新用户交易明细表，状态为失败
                // 7.系统手续费账户BsSysSubAccount做反向操作

                // 0.计算实际扣除金额（包含手续费）
                BsServiceFeeExample bsServiceFeeExample = new BsServiceFeeExample();
                bsServiceFeeExample.createCriteria().andOrderNoEqualTo(order.getOrderNo()).andFeeTypeEqualTo(Constants.FEE_TYPE_FINANCE_WITHDRAW);
                List<BsServiceFee> bsServiceFeeList = bsServiceFeeMapper.selectByExample(bsServiceFeeExample);
                Double fee = 0d;
                if(CollectionUtils.isNotEmpty(bsServiceFeeList)) {
                    fee = bsServiceFeeList.get(0).getDoneFee();
                }
                Double realBalance = MoneyUtil.add(order.getAmount(), fee).doubleValue();

                // 1.用户信息表可用余额增加回来(还原)
                BsUser tempUser = new BsUser();
                tempUser.setCanWithdraw(realBalance);
                tempUser.setId(userId);
                bsUserMapper.updateBonusById(tempUser);

                // 2.更新订单表，状态为失败
                BsPayOrders updateOrder = new BsPayOrders();
                updateOrder.setId(order.getId());
                updateOrder.setStatus(Constants.ORDER_STATUS_FAIL);
                updateOrder.setReturnCode(req.getRetCode());
                updateOrder.setReturnMsg(req.getErrorMsg());
                updateOrder.setUpdateTime(new Date());
                bsPayOrdersMapper.updateByPrimaryKeySelective(updateOrder);

                // 3.添加一条订单明细信息
                BsPayOrdersJnl insertOrderJnl = new BsPayOrdersJnl();
                insertOrderJnl.setCreateTime(new Date());
                insertOrderJnl.setOrderId(order.getId());
                insertOrderJnl.setOrderNo(order.getOrderNo());
                insertOrderJnl.setSubAccountCode(null);
                insertOrderJnl.setSubAccountId(null);
                insertOrderJnl.setSysTime(new Date());
                insertOrderJnl.setTransAmount(order.getAmount());
                insertOrderJnl.setTransCode(Constants.ORDER_TRANS_CODE_FAIL);
                insertOrderJnl.setUserId(userId);
                insertOrderJnl.setReturnCode(req.getRetCode());
                insertOrderJnl.setReturnMsg(req.getErrorMsg());
                bsPayOrdersJnlMapper.insertSelective(insertOrderJnl);

                //修改手续费状态为回退
                BsServiceFee bsServiceFee = new BsServiceFee();
                bsServiceFee.setStatus(Constants.FEE_STATUS_RETURN);
                bsServiceFee.setUpdateTime(new Date());
                bsServiceFeeMapper.updateByExampleSelective(bsServiceFee, bsServiceFeeExample);

                // 4.用户结算户，做反向操作（冻结金额减少，可用余额增加,可提现余额增加）
                BsSubAccount jshDB = subAccountService
                        .findJSHSubAccountByUserId(userId);
                BsSubAccount jsh = new BsSubAccount();
                jsh.setId(jshDB.getId());
                jsh.setAvailableBalance(realBalance);
                jsh.setCanWithdraw(realBalance);
                jsh.setFreezeBalance(-realBalance);
                jsh.setLastTransDate(new Date());
                subAccountService.modifyBalancesByIncrement(jsh);

                // 5.添加一条解冻记账流水
                BsAccountJnl bsAccountJnl = new BsAccountJnl();
                bsAccountJnl.setTransTime(new Date());
                bsAccountJnl.setTransCode(Constants.USER_UNFREEZE);
                bsAccountJnl.setTransName("用户提现失败，解冻余额");// 解冻
                bsAccountJnl.setTransAmount(realBalance);
                bsAccountJnl.setSysTime(new Date());
                bsAccountJnl.setCdFlag1(Constants.CD_FLAG_D);
                bsAccountJnl.setUserId1(userId);
                bsAccountJnl.setAccountId1(jshDB.getAccountId());
                bsAccountJnl.setSubAccountId1(jshDB.getId());
                bsAccountJnl.setSubAccountCode1(jshDB.getCode());
                bsAccountJnl.setBeforeBalance1(jshDB.getBalance());
                bsAccountJnl.setBeforeAvialableBalance1(jshDB.getAvailableBalance());
                bsAccountJnl.setBeforeFreezeBalance1(jshDB.getFreezeBalance());
                bsAccountJnl.setAfterBalance1(jshDB.getBalance());
                bsAccountJnl.setAfterAvialableBalance1(MoneyUtil.add(jshDB.getAvailableBalance()
                        , realBalance).doubleValue());
                bsAccountJnl.setAfterFreezeBalance1(MoneyUtil.subtract(jshDB.getFreezeBalance()
                        , realBalance).doubleValue());
                bsAccountJnl.setCdFlag2(Constants.CD_FLAG_C);
                bsAccountJnl.setUserId2(userId);
                bsAccountJnl.setSubAccountCode2(jshDB.getCode());
                bsAccountJnl.setFee(fee);
                bsAccountJnl.setStatus(Constants.ACCOUNT_JNL_STATUS_FAIL);
                bsAccountJnlMapper.insertSelective(bsAccountJnl);

                // 6.更新用户交易明细表，状态为失败
                BsUserTransDetailExample example = new BsUserTransDetailExample();
                example.createCriteria().andOrderNoEqualTo(orderNo);
                BsUserTransDetail tmpTransDetail = new BsUserTransDetail();
                tmpTransDetail.setTransStatus(Constants.Trans_STATUS_FAIL);
                tmpTransDetail.setReturnCode(req.getRetCode());
                tmpTransDetail.setReturnMsg(req.getErrorMsg());
                tmpTransDetail.setUpdateTime(new Date());
                bsUserTransDetailMapper.updateByExampleSelective(tmpTransDetail, example);

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
            }
        });
    }

    /**
     * 发送用户提现请求(需审核)
     */
    @Override
    //@Transactional
    @MethodRole("APP")
    public void check(final ReqMsg_UserBalance_Withdraw req,
                      final ResMsg_UserBalance_Withdraw res) {
        // 0.检验用户是否存在
        // 1.校验用户支付密码是否正确
        // 2.查询可用余额是否足够
        // 3.是否有回款卡
        // 4.前置处理
        // 4.1 用户信息表可用余额减少
        // 4.2 用户结算户，冻结金额增加，可用余额，可提现余额减少
        // 4.3记录用户交易明细表(状态为审核中)
        // 4.4新增用户记账流水表记录
        // 5.记录提现审核表bs_withdraw_check
        BalanceWithdrawCheckVO check = new BalanceWithdrawCheckVO();
        check.setUserId(req.getUserId());
        depUserBalanceWithdrawService.withdrawCheck(check);

        try {
            jsClientDaoSupport.lock(RedisLockEnum.LOCK_WITHDRAW_SERVICE.getKey());
            transactionTemplate.execute(new TransactionCallbackWithoutResult(){
                @Override
                public void doInTransactionWithoutResult(TransactionStatus status) {
                    // 0.检验用户是否存在
                    BsUser user = userService.findUserById(req.getUserId());
                    if (user == null) {
                        throw new PTMessageException(PTMessageEnum.USER_NO_EXIT);
                    }
                    // 赞分期代偿人不允许前端提现
                    if(isCompensatoryUserZAN(req.getUserId()) && Constants.TERMINAL_TYPE_MANAGE != req.getTerminalType()) {
                        throw new PTMessageException(PTMessageEnum.WITHDRAW_NOT_ALLOWED);
                    }
//                    if(isSuperUser(req.getUserId())){
//                        throw new PTMessageException(PTMessageEnum.WITHDRAW_NOT_ALLOWED);
//                    }

                    // 1.校验用户支付密码是否正确
                    if (!user.getPayPassword()
                            .equals(MD5Util.encryptMD5(req.getPassword()
                                    + MD5Util.getEncryptkey()))) {
                        throw new PTMessageException(PTMessageEnum.USER_PAY_PASS_ERROR);
                    }

                    // 3.是否有回款卡
                    BsBankCard receiveCard = bankCardService.findFirstBankCardByUserId(req
                            .getUserId());
                    if (receiveCard == null) {
                        throw new PTMessageException(PTMessageEnum.BANKCARD_NOT_BIND);
                    }

                    BsSysConfig eachMonthWithdrawTimesConfig = bsSysConfigMapper.selectByPrimaryKey(Constants.EACH_MONTH_WITHDRAW_TIMES);
                    int each_month_withdraw_times = Integer.parseInt(eachMonthWithdrawTimesConfig.getConfValue());
                    CommissionVO commission = commissionService.calServiceFee(req.getAmount(), TransTypeEnum.USER_WITHDRAW, null);
                    Double withdraw_counter_fee = commission.getActPayAmount();
                    // 1.查询当月提现成功订单，判断是否超过三次
                    String applyTimeYYYYMM = DateUtil.formatDateTime(new Date(), "yyyy-MM");
                    String startTime = applyTimeYYYYMM + "-01 00:00:00";
                    Date startDate = DateUtil.parse(startTime, "yyyy-MM-dd HH:mm:ss");
                    Date endDate = new Date();
                    int historyWithdrawOrderCount = countHistoryWithdraw(startDate, endDate, req);

                    // 最小限额校验
                    BsSysConfig withdrawLimitConfig = bsSysConfigMapper.selectByPrimaryKey(Constants.WITHDRAW_LIMIT);
                    Double withdrawLimit = Double.valueOf(withdrawLimitConfig.getConfValue());
                    if (each_month_withdraw_times - historyWithdrawOrderCount > 0) {
                        if (req.getAmount().compareTo(withdrawLimit) < 0) {
                            throw new PTMessageException(PTMessageEnum.WITHDRAW_AMOUNT_SURPASS);
                        }
                    } else {
                        Double limit = withdrawLimit.compareTo(withdraw_counter_fee) > 0 ? withdrawLimit : withdraw_counter_fee;
                        if (req.getAmount().compareTo(limit) <= 0) {
                            throw new PTMessageException(PTMessageEnum.WITHDRAW_AMOUNT_SURPASS);
                        }
                    }
                    // 2、可用余额是否足够
                    BsSubAccount tempJsh = subAccountMapper.selectSingleSubActByUserAndType(req.getUserId(), Constants.PRODUCT_TYPE_JSH);
                    BsSubAccount jshDB = subAccountMapper.selectSubAccountByIdForLock(tempJsh.getId());
                    if (MoneyUtil.subtract(req.getAmount(), jshDB.getCanWithdraw()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() > 0) {
                        throw new PTMessageException(PTMessageEnum.WITHDRAW_AMOUNT_SURPASS);
                    }
                    Double fee = 0d;
                    Double planFee = 0d;
                    Double realAmount = req.getAmount();
                    if (historyWithdrawOrderCount >= each_month_withdraw_times) {
                        fee = withdraw_counter_fee;
                        planFee = commission.getNeedPayAmount();
                        // 用户提现金额不够手续费，则判断余额是否足够
                        Double leftAmount = MoneyUtil.subtract(jshDB.getCanWithdraw(), withdraw_counter_fee).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        if (leftAmount.compareTo(0d) <= 0) {
                            throw new PTMessageException(PTMessageEnum.WITHDRAW_MORE_THAN_3_TIMES_AMOUNT_LESS);
                        }
                        // 1.查询当月提现成功订单超过三次，扣除手续费
                        realAmount = MoneyUtil.subtract(req.getAmount(), withdraw_counter_fee).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        if (realAmount.compareTo(0d) < 0) {
                            throw new PTMessageException(PTMessageEnum.WITHDRAW_MORE_THAN_3_TIMES_AMOUNT_LESS);
                        }
                    }
                    // 4.前置处理
                    // 4.2 用户结算户，冻结金额增加，可用余额，可提现余额减少
                    BsSubAccount jsh = new BsSubAccount();
                    jsh.setId(jshDB.getId());
                    jsh.setAvailableBalance(-req.getAmount());
                    jsh.setCanWithdraw(-req.getAmount());
                    jsh.setFreezeBalance(req.getAmount());
                    jsh.setLastTransDate(new Date());
                    subAccountService.modifyBalancesByIncrement(jsh);
                    // 4.1 用户信息表可用余额减少
                    BsUser tempUser = new BsUser();
                    tempUser.setCanWithdraw(-req.getAmount());
                    tempUser.setId(req.getUserId());
                    bsUserMapper.updateBonusById(tempUser);

                    // 4.3记录用户交易明细表(状态为审核中)
                    Date now = new Date();
                    BsUserTransDetail transDetail = new BsUserTransDetail();
                    transDetail.setUserId(req.getUserId());
                    transDetail.setCardNo(receiveCard.getCardNo());
                    transDetail.setTransType(Constants.Trans_TYPE_WITHDRAW);
                    transDetail.setTransStatus(Constants.Trans_STATUS_CHECK);
                    transDetail.setOrderNo("");
                    transDetail.setCreateTime(now);
                    transDetail.setAmount(-realAmount);
                    transDetail.setUpdateTime(now);
                    bsUserTransDetailMapper.insertSelective(transDetail);
                    BsUserTransDetail feeTransDetail = null;
                    if(fee.compareTo(0d) > 0){
                        feeTransDetail = new BsUserTransDetail();
                        feeTransDetail.setUserId(req.getUserId());
                        feeTransDetail.setCardNo(receiveCard.getCardNo());
                        feeTransDetail.setTransType(Constants.Trans_TYPE_WITHDRAW_FEE);
                        feeTransDetail.setTransStatus(Constants.Trans_STATUS_CHECK);
                        feeTransDetail.setOrderNo("");
                        feeTransDetail.setCreateTime(now);
                        feeTransDetail.setAmount(-fee);
                        feeTransDetail.setUpdateTime(now);
                        bsUserTransDetailMapper.insertSelective(feeTransDetail);
                    }
                    // 4.4新增用户记账流水表记录
                    BsAccountJnl bsAccountJnl = new BsAccountJnl();
                    bsAccountJnl.setTransTime(new Date());
                    bsAccountJnl.setTransCode(Constants.USER_BALANCE_WITHDRAW);
                    bsAccountJnl.setTransName("用户提现，冻结余额");//冻结金额增加，可用余额，可提现余额减少
                    bsAccountJnl.setTransAmount(req.getAmount());
                    bsAccountJnl.setSysTime(new Date());
                    bsAccountJnl.setCdFlag1(Constants.CD_FLAG_C);
                    bsAccountJnl.setUserId1(req.getUserId());
                    bsAccountJnl.setAccountId1(jshDB.getAccountId());
                    bsAccountJnl.setSubAccountId1(jshDB.getId());
                    bsAccountJnl.setSubAccountCode1(jshDB.getCode());
                    bsAccountJnl.setBeforeBalance1(jshDB.getBalance());
                    bsAccountJnl.setBeforeAvialableBalance1(jshDB.getAvailableBalance());
                    bsAccountJnl.setBeforeFreezeBalance1(jshDB.getFreezeBalance());
                    bsAccountJnl.setAfterBalance1(jshDB.getBalance());
                    bsAccountJnl.setAfterAvialableBalance1(MoneyUtil.subtract(jshDB.getAvailableBalance()
                            , req.getAmount()).doubleValue());
                    bsAccountJnl.setAfterFreezeBalance1(MoneyUtil.add(jshDB.getFreezeBalance()
                            , req.getAmount()).doubleValue());
                    bsAccountJnl.setCdFlag2(Constants.CD_FLAG_D);
                    bsAccountJnl.setUserId2(req.getUserId());
                    bsAccountJnl.setSubAccountCode2(jshDB.getCode());
                    bsAccountJnl.setFee(0d);
                    bsAccountJnl.setStatus(Constants.ACCOUNT_JNL_STATUS_SUCCESS);
                    bsAccountJnlMapper.insertSelective(bsAccountJnl);

                    // 5.记录提现审核表bs_withdraw_check
                    BsWithdrawCheck bsWithdrawCheck = new BsWithdrawCheck();
                    bsWithdrawCheck.setAmount(req.getAmount());
                    bsWithdrawCheck.setUserId(req.getUserId());
                    bsWithdrawCheck.setStatus(Constants.WITHDRAW_TO_CHECK);
                    bsWithdrawCheck.setTransDetailId(transDetail.getId());
                    bsWithdrawCheck.setTerminalType(req.getTerminalType());
                    bsWithdrawCheck.setSubTransDetailId(feeTransDetail != null ? feeTransDetail.getId() : null);
                    bsWithdrawCheck.setCreateTime(now);
                    bsWithdrawCheckService.addWithdrawCheck(bsWithdrawCheck);
                    res.setWithdrawTime(now);
                    res.setOrderNo("");
                }
            });
        } finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_WITHDRAW_SERVICE.getKey());
        }
    }

    /**
     * 后台管理审核通过
     */
    @Override
    public void pass(
            ReqMsg_UserBalance_WithdrawPass req,
            ResMsg_UserBalance_WithdrawPass res) {
        // 0.检验是否存在该提现审核记录
        // 1.检验用户是否存在
        // 2.查询JSH冻结余额是否足够
        // 3.是否有回款卡
        // 4.前置处理
        // 4.1订单信息表中添加一条记录
        // 4.2 订单明细表中添加一条记录
        // 4.3修改记录提现审核表
        // 4.4修改交易明细表，添加订单号，修改状态为处理中
        // 5.发起代付请求
        // 6.后置处理(无数据库更新处理)

        // 0.检验是否存在该提现审核记录
        BsWithdrawCheck bsWithdrawCheck = bsWithdrawCheckService
                .selectWithdrawCheck(req.getCheckId());
        if (bsWithdrawCheck == null
                || (!bsWithdrawCheck.getStatus().equals(
                Constants.WITHDRAW_TO_CHECK))) {
            throw new PTMessageException(PTMessageEnum.WITHDRAW_CHECK_NO_EXIT);
        }

        BalanceWithdrawCheckVO check = new BalanceWithdrawCheckVO();
        check.setUserId(bsWithdrawCheck.getUserId());
        depUserBalanceWithdrawService.withdrawCheck(check);

        // 1.检验用户是否存在
        BsUser user = userService.findUserById(bsWithdrawCheck.getUserId());
        if (user == null) {
            throw new PTMessageException(PTMessageEnum.USER_NO_EXIT);
        }
        // 1.2 校验用户状态是否冻结
        if (BsUserStatus.USER_STATUS_FREEZE.getIntValue().intValue() == user.getStatus().intValue()) {
        	throw new PTMessageException(PTMessageEnum.USER_STATUS_FROST_MANAGE_ERROR);
        }

        // 2.查询JSH冻结余额是否足够
        BsUserTransDetail bsUserTransDetail =bsUserTransDetailMapper.selectByPrimaryKey(bsWithdrawCheck.getTransDetailId());
        if(Constants.Trans_TYPE_DEP_WITHDRAW.equals(bsUserTransDetail.getTransType())) {
            BsSubAccount jshDB = subAccountService
                    .findDEPJSHSubAccountByUserId(bsWithdrawCheck.getUserId());
            if (bsWithdrawCheck.getAmount() > jshDB.getFreezeBalance()) {
                throw new PTMessageException(PTMessageEnum.WITHDRAW_AMOUNT_SURPASS);
            }
        } else {
            BsSubAccount jshDB = subAccountService
                    .findJSHSubAccountByUserId(bsWithdrawCheck.getUserId());
            if (bsWithdrawCheck.getAmount() > jshDB.getFreezeBalance()) {
                throw new PTMessageException(PTMessageEnum.WITHDRAW_AMOUNT_SURPASS);
            }
        }

        // 3.是否有回款卡
        BsBankCard receiveCard = bankCardService
                .findFirstBankCardByUserId(bsWithdrawCheck.getUserId());
        if (receiveCard == null) {
            throw new PTMessageException(PTMessageEnum.BANKCARD_NOT_BIND);
        }

        // 币港湾理财人提现申请金额限制（用于判断是否审核提现） 50000
        BsSysConfig withdrawApplyLimitConfig = bsSysConfigService.findKey(Constants.WITHDRAW_APPLY_LIMIT);
        Double withdrawApplyLimit = withdrawApplyLimitConfig == null ? 50000d : Double.valueOf(withdrawApplyLimitConfig.getConfValue());

        ReqMsg_UserBalance_Withdraw reqMsg = new ReqMsg_UserBalance_Withdraw();
        ResMsg_UserBalance_Withdraw resMsg = new ResMsg_UserBalance_Withdraw();
        reqMsg.setUserId(user.getId());
        reqMsg.setCheckId(String.valueOf(req.getCheckId()));
        reqMsg.setManageId(req.getManageId());
        bsWithdrawCheck.setCheckTime(new Date());
        bsWithdrawCheck.setmUserId(req.getManageId());

        Double applyAmount = bsWithdrawCheck.getAmount();

        if(Constants.Trans_TYPE_DEP_WITHDRAW.equals(bsUserTransDetail.getTransType())) {
            logger.info("【存管审核提现】提现审核ID：{}", bsWithdrawCheck.getId());

            BsSysConfig startCongig = bsSysConfigMapper.selectByPrimaryKey(Constants.HF_BANK_CUT_DAY_START_TIME);
            BsSysConfig endCongig = bsSysConfigMapper.selectByPrimaryKey(Constants.HF_BANK_CUT_DAY_END_TIME);
            String hfStartTime = null == startCongig ? null : (StringUtil.isBlank(startCongig.getConfValue()) ? null : startCongig.getConfValue());
            String hfEndTime = null == endCongig ? null : (StringUtil.isBlank(endCongig.getConfValue()) ? null : endCongig.getConfValue());
            Date today = new Date();
            logger.info("恒丰日切开始时间：{}，结束时间：{}。当前时间：{}", hfStartTime, hfEndTime, com.pinting.business.util.DateUtil.format(today, "yyyy-MM-dd HH:mm:ss"));
            if(hfStartTime != null && hfEndTime != null) {
                if(com.pinting.business.util.DateUtil.betweenHHMM(hfStartTime, hfEndTime, today, true)) {
                    throw new PTMessageException(PTMessageEnum.HF_IN_MAINTENANCE.getCode(), hfStartTime + "--" + hfEndTime + "为银行维护时间，请稍后再试");
                }
            }

            if(Constants.WITHDRAW_CLEAR_MARK_NO.equals(bsWithdrawCheck.getClearingMark())) {
                // 1.1 无需清算
//                if(applyAmount.compareTo(withdrawApplyLimit) <= 0) {
//                    // 1.1.1 申请金额 <= 5万 审核提现 -> 需要清算，立即存入队列
//                    logger.info("提现审核ID：{}。申请金额 <= 5万，需要清算，立即存入队列", bsWithdrawCheck.getId());
//                    bsWithdrawCheck.setStatus(Constants.WITHDRAW_PASS_QUE);
//                    this.passWithdrawCheck(bsWithdrawCheck);
//                } else {
//                    // 1.1.2 申请金额 > 5万 审核提现 -> 进入发送队列工作日发送
//
//                }
                logger.info("提现审核ID：{}。进入发送队列工作日发送", bsWithdrawCheck.getId());
                bsWithdrawCheck.setStatus(Constants.WITHDRAW_PASS_QUE);
                this.passWithdrawCheck(bsWithdrawCheck);
            } else {
                // 1.2 需要清算
                logger.info("提现审核ID：{}。需要清算，立即存入队列", bsWithdrawCheck.getId());
                bsWithdrawCheck.setStatus(Constants.WITHDRAW_PASS_QUE);
                this.passWithdrawCheck(bsWithdrawCheck);
            }
        } else {
            logger.info("【普通审核提现】提现审核ID：{}", bsWithdrawCheck.getId());
            this.apply(reqMsg, resMsg);
        }
    }

    private void passWithdrawCheck(BsWithdrawCheck check) {
        // 修改提现审核表
        bsWithdrawCheckService.updateWithdrawCheck(check);

        // 修改交易明细表，添加订单号，修改状态为处理中
        BsUserTransDetail tmpTransDetail = new BsUserTransDetail();
        tmpTransDetail.setId(check.getTransDetailId());
        tmpTransDetail.setTransStatus(Constants.Trans_STATUS_DEAL);
        tmpTransDetail.setUpdateTime(new Date());
        bsUserTransDetailMapper.updateByPrimaryKeySelective(tmpTransDetail);
        if(check.getSubTransDetailId() != null){
            BsUserTransDetail tmpFeeTransDetail = new BsUserTransDetail();
            tmpFeeTransDetail.setId(check.getSubTransDetailId());
            tmpFeeTransDetail.setTransStatus(Constants.Trans_STATUS_DEAL);
            tmpFeeTransDetail.setUpdateTime(new Date());
            bsUserTransDetailMapper.updateByPrimaryKeySelective(tmpFeeTransDetail);
        }
    }

    /**
     * 后台管理审核不通过（置为失败）
     */
    @Override
    @Transactional
    public void refuse(
            ReqMsg_UserBalance_WithdrawFall req,
            ResMsg_UserBalance_WithdrawFall res) {
        // 0.检验是否存在该提现审核记录
        // 1.用户信息表可用余额增加回来(还原)
        // 2.用户结算户，做反向操作（冻结金额减少，可用余额增加）
        // 3.更新用户交易明细表，状态为失败
        // 4.修改记录提现审核表
        try {
            jsClientDaoSupport.lock(RedisLockEnum.LOCK_WITHDRAW_SERVICE.getKey());

            // 0.检验是否存在该提现审核记录
            BsWithdrawCheck bsWithdrawCheck = bsWithdrawCheckService
                    .selectWithdrawCheck(req.getCheckId());
            if (bsWithdrawCheck == null
                    || (!bsWithdrawCheck.getStatus().equals(
                    Constants.WITHDRAW_TO_CHECK))) {
                throw new PTMessageException(PTMessageEnum.WITHDRAW_CHECK_NO_EXIT);
            }

            // 1.用户信息表可用余额增加回来(还原)
            BsUser tempUser = new BsUser();
            tempUser.setCanWithdraw(bsWithdrawCheck.getAmount());
            tempUser.setId(bsWithdrawCheck.getUserId());
            bsUserMapper.updateBonusById(tempUser);
            // 2.用户结算户，做反向操作（冻结金额减少，可用余额增加,可提现余额增加）
            BsSubAccount jshDB = subAccountService
                    .findJSHSubAccountByUserId(bsWithdrawCheck.getUserId());
            BsSubAccount jsh = new BsSubAccount();
            jsh.setId(jshDB.getId());
            jsh.setAvailableBalance(bsWithdrawCheck.getAmount());
            jsh.setCanWithdraw(bsWithdrawCheck.getAmount());
            jsh.setFreezeBalance(-bsWithdrawCheck.getAmount());
            jsh.setLastTransDate(new Date());
            subAccountService.modifyBalancesByIncrement(jsh);

            // 3.更新用户交易明细表，状态为失败
            BsUserTransDetail tmpTransDetail = new BsUserTransDetail();
            tmpTransDetail.setId(bsWithdrawCheck.getTransDetailId());
            tmpTransDetail.setTransStatus(Constants.Trans_STATUS_FAIL);
            tmpTransDetail.setReturnCode(res.getResCode());
            tmpTransDetail.setReturnMsg(res.getResMsg());
            tmpTransDetail.setUpdateTime(new Date());
            bsUserTransDetailMapper.updateByPrimaryKeySelective(tmpTransDetail);
            if(bsWithdrawCheck.getSubTransDetailId() != null){
                BsUserTransDetail tmpFeeTransDetail = new BsUserTransDetail();
                tmpFeeTransDetail.setId(bsWithdrawCheck.getSubTransDetailId());
                tmpFeeTransDetail.setTransStatus(Constants.Trans_STATUS_FAIL);
                tmpFeeTransDetail.setReturnCode(res.getResCode());
                tmpFeeTransDetail.setReturnMsg(res.getResMsg());
                tmpFeeTransDetail.setUpdateTime(new Date());
                bsUserTransDetailMapper.updateByPrimaryKeySelective(tmpFeeTransDetail);
            }
            // 4.修改记录提现审核表
            bsWithdrawCheck.setStatus(Constants.WITHDRAW_NOT_PASS);
            bsWithdrawCheck.setId(req.getCheckId());
            bsWithdrawCheck.setmUserId(req.getManageId());
            bsWithdrawCheckService.updateWithdrawCheck(bsWithdrawCheck);
        } finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_WITHDRAW_SERVICE.getKey());
        }
    }

    /**
     * 获得最小限额
     * @param leftTimes     剩余免费次数
     * @param withdrawLimit 提现限额
     * @param fee           手续费
     * @return
     */
    private Double getRealWithdrawLimit(Integer leftTimes, Double withdrawLimit, Double fee) {
        if(leftTimes > 0) {
            return withdrawLimit;
        } else {
            return withdrawLimit.compareTo(fee) > 0 ? withdrawLimit : fee;
        }
    }

    /**
     * 是否赞分期代偿人
     * @param userId
     * @return
     */
    private boolean isCompensatoryUserZAN(Integer userId) {
        boolean isCompensatoryUser = false;
        BsSysConfig supers = bsSysConfigMapper.selectByPrimaryKey(Constants.ZAN_COMPENSATE_USER_ID);
        List<Integer> superUserIds = new ArrayList<>();
        if (supers != null) {
            String[] userStr = supers.getConfValue().split(",");
            for (String string : userStr) {
                superUserIds.add(Integer.parseInt(string));
            }
        }
        if(!CollectionUtils.isEmpty(superUserIds)) {
            for (Integer superUserId : superUserIds) {
                if(superUserId.equals(userId)) {
                    isCompensatoryUser = true;
                    break;
                }
            }
        }
        return isCompensatoryUser;
    }

    /**
     * 根据用户编号判断是否是VIP理财人
     * @param userId
     * @return true 是VIP理财人，false 普通理财人
     */
    public boolean isSuperUser(Integer userId){
        boolean isSuperUser = false;
        List<Integer> superUsers = loanRelationshipService.getSuperUserList();
        if(!CollectionUtils.isEmpty(superUsers)){
            for (Integer superUser: superUsers) {
                if(userId.equals(superUser)){
                    isSuperUser = true;
                    break;
                }
            }
        }
        if(!isSuperUser) {
            BsSysConfig supers = bsSysConfigMapper.selectByPrimaryKey(VIPId4PartnerEnum.YUN_DAI_SELF.getVipIdKey());
            List<Integer> superUserIds = new ArrayList<>();
            if (supers != null) {
                String[] userStr = supers.getConfValue().split(",");
                for (String string : userStr) {
                    superUserIds.add(Integer.parseInt(string));
                }
            }
            if(!CollectionUtils.isEmpty(superUserIds)) {
                for (Integer superUserId : superUserIds) {
                    if(superUserId.equals(userId)) {
                        isSuperUser = true;
                        break;
                    }
                }
            }
        }
        return isSuperUser;
    }

    /**
     * 1. < 5万，不包含当日充值金额，T日清算，自动审核
     * 2. < 5万，包含当日充值金额，T+1日清算（），人工审核
     * 3. > 5万，不包含当日充值金额，T日（工作日）清算，自动审核
     * 4. > 5万，包含当日充值金额，T+1日（工作日）清算，人工审核
     * @param req
     * @param res
     */
    public void preWithdraw(ReqMsg_UserBalance_Withdraw req,
                            ResMsg_UserBalance_Withdraw res) {

        try {
            logger.info("提现请求预校验开始：{}", JSONObject.fromObject(req));
            jsClientDaoSupport.lock(RedisLockEnum.LOCK_WITHDRAW_FACADE.getKey());
            
            if(req.getAmount() <= 0){
                throw new PTMessageException(PTMessageEnum.WITHDRAW_AMOUNT_SURPASS.getCode(), "提现金额须大于0元");
            }
            // 普通余额提现
            if(!Constants.WITHDRAW_ACCOUNT_TYPE_DEP.equals(req.getAccountType())) {
                logger.info("普通余额提现直接提现");
                apply(req, res);// 存管上线之后宝付提现不再走审核
                res.setNeedCheck(false);
                res.setTime(new Date());
                return;
            }

            // 累计投资金额
            // Double hisBuyAmount = subAccountMapper.sumAmount4HisBuy(req.getUserId());
            // 用户的历史购买总金额下限（10000）
            // BsSysConfig hisBuyConfig = bsSysConfigService.findKey(Constants.HISTORY_BUY_AMOUNT_LIMIT);
            // Double hisBuyAmontLimit = hisBuyConfig == null ? 10000d : Double.valueOf(hisBuyConfig.getConfValue());
            // 币港湾理财人提现申请金额限制（用于判断是否审核提现） 50000
            // BsSysConfig withdrawApplyLimitConfig = bsSysConfigService.findKey(Constants.WITHDRAW_APPLY_LIMIT);
            // Double withdrawApplyLimit = withdrawApplyLimitConfig == null ? 50000d : Double.valueOf(withdrawApplyLimitConfig.getConfValue());
            // 今日充值未购买金额（今天充值成功-今天余额购买）
            Double todayTopUpLeftBalance = orderService.topUpSubBuyBalanceToday(req.getUserId());
            // 用户可用余额
            Double availableBalance = subAccountMapper.selectAvailableBalanceByUserId(req.getUserId());
            Double applyAmount = req.getAmount();

            // 存管余额提现
            if(applyAmount.compareTo(MoneyUtil.subtract(availableBalance, todayTopUpLeftBalance).doubleValue()) <= 0) {
                logger.info("【存管提现进入自动审核流程】用户：{} 可用余额：{}，充值未购买金额：{}", req.getUserId(), availableBalance, todayTopUpLeftBalance);
                // 1.1 (D<=A) 申请金额 <= 用户可用余额 - 今日充值未购买剩余金额（提现金额不包含今日充值金额，自动审核，T日清算完）
                req.setClearingMark(Constants.WITHDRAW_CLEAR_MARK_YES_T);
                Integer checkId = depUserBalanceWithdrawService.check(req, res);
                BsWithdrawCheck check = bsWithdrawCheckService.selectWithdrawCheck(checkId);
                check.setStatus(Constants.WITHDRAW_PASS_QUE);
                check.setNote("自动审核");
                this.passWithdrawCheck(check);
                res.setNeedCheck(false);
                res.setTime(new Date());
            } else {
                logger.info("【存管提现进入人工审核流程】用户：{} 的可用余额：{}，充值未购买金额：{}", req.getUserId(), availableBalance, todayTopUpLeftBalance);
                // 1.2 (D<=A) 申请金额 > 用户可用余额 - 今日充值未购买剩余金额（提现金额包含今日充值金额，人工审核，T+1日清算完）
                req.setClearingMark(Constants.WITHDRAW_CLEAR_MARK_YES_T_ADD_1);
                depUserBalanceWithdrawService.check(req, res);
                res.setNeedCheck(true);
                res.setTime(new Date());
            }
        } finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_WITHDRAW_FACADE.getKey());
        }

    }

}

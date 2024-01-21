package com.pinting.business.accounting.finance.service.impl;

import com.pinting.business.accounting.finance.model.DFResultInfo;
import com.pinting.business.accounting.finance.service.DepUserBalanceWithdrawService;
import com.pinting.business.accounting.loan.enums.DepTargetEnum;
import com.pinting.business.accounting.loan.enums.LoanStatus;
import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.loan.enums.VIPId4PartnerEnum;
import com.pinting.business.accounting.loan.service.DepFixedLoanPaymentService;
import com.pinting.business.accounting.loan.service.LoanPaymentService;
import com.pinting.business.accounting.loan.service.LoanRelationshipService;
import com.pinting.business.accounting.service.BsSysSubAccountService;
import com.pinting.business.accounting.service.PayOrdersService;
import com.pinting.business.dao.*;
import com.pinting.business.enums.*;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.hessian.site.message.ReqMsg_UserBalance_Withdraw;
import com.pinting.business.hessian.site.message.ResMsg_UserBalance_Withdraw;
import com.pinting.business.model.*;
import com.pinting.business.model.vo.BalanceWithdrawCheckVO;
import com.pinting.business.model.vo.CommissionVO;
import com.pinting.business.service.common.CommissionService;
import com.pinting.business.service.site.*;
import com.pinting.business.util.Constants;
import com.pinting.business.util.Util;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.core.util.encrypt.MD5Util;
import com.pinting.gateway.hessian.message.hfbank.B2GReqMsg_HFBank_UserBatchWithdraw;
import com.pinting.gateway.hessian.message.hfbank.B2GResMsg_HFBank_UserBatchWithdraw;
import com.pinting.gateway.hessian.message.hfbank.G2BReqMsg_HFBank_OutOfAccount;
import com.pinting.gateway.hessian.message.hfbank.model.BatchWithdrawExtData;
import com.pinting.gateway.hessian.message.pay19.enums.OrderStatus;
import com.pinting.gateway.out.service.HfbankTransportService;
import com.pinting.gateway.out.service.SMSServiceClient;
import com.pinting.gateway.smsEnums.TemplateKey;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
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
 * Author:      cyb
 * Date:        2017/4/12
 * Description: 存管体系用户余额提现
 */
@Service
public class DepUserBalanceWithdrawServiceImpl implements DepUserBalanceWithdrawService {

    private Logger logger = LoggerFactory.getLogger(DepUserBalanceWithdrawServiceImpl.class);
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
    private TransactionTemplate transactionTemplate;
    @Autowired
    private BsSysSubAccountService bsSysSubAccountService;
    @Autowired
    private HfbankTransportService hfbankTransportService;
    @Autowired
    private BsHfbankUserExtMapper bsHfbankUserExtMapper;
    @Autowired
    private BsWithdrawCheckMapper bsWithdrawCheckMapper;
    @Autowired
    private BsBankMapper bsBankMapper;
    @Autowired
    private LnPayOrdersMapper payOrdersMapper;
    @Autowired
    private LnUserMapper lnUserMapper;
    @Autowired
    private LoanPaymentService loanPaymentService;
    @Autowired
    private DepFixedLoanPaymentService depFixedLoanPaymentService;
    @Autowired
    private LnPayOrdersJnlMapper lnPayOrdersJnlMapper;
    @Autowired
    private AssetsService assetsService;
    
    @Override
    public void apply(final ReqMsg_UserBalance_Withdraw req, final ResMsg_UserBalance_Withdraw res) {
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
        this.withdrawCheck(check);

        // 1.0. 恒丰日切时间，禁止充值提现操作
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
        BsHfbankUserExt userExt;
        BsBank bank;
        boolean isCompensatoryUserZAN = isCompensatoryUserZAN(req.getUserId());
        try {
            jsClientDaoSupport.lock(RedisLockEnum.LOCK_WITHDRAW_SERVICE.getKey());
            // 零、公共校验
            // 1.检验用户是否存在
            user = userService.findUserById(req.getUserId());
            if (user == null) {
                throw new PTMessageException(PTMessageEnum.USER_NO_EXIT);
            }
            // 赞分期代偿人不允许前端提现
            if(isCompensatoryUserZAN) {
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
            bank = bsBankMapper.selectByPrimaryKey(receiveCard.getBankId());
            // 3、是否恒丰开户
            BsHfbankUserExtExample extExample = new BsHfbankUserExtExample();
            extExample.createCriteria().andUserIdEqualTo(user.getId()).andStatusEqualTo(Constants.HFBANK_USER_EXT_STATUS_OPEN);
            List<BsHfbankUserExt> ext = bsHfbankUserExtMapper.selectByExample(extExample);
            if (CollectionUtils.isEmpty(ext)) {
                throw new PTMessageException(PTMessageEnum.HF_NOT_BIND);
            }
            userExt = ext.get(0);
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
                logger.info("申请金额：{}，提现最小限额：{}，是否相等：{}", req.getAmount(), withdrawLimit, req.getAmount().compareTo(withdrawLimit));
                if (req.getAmount().compareTo(withdrawLimit) < 0) {
                    throw new PTMessageException(PTMessageEnum.WITHDRAW_AMOUNT_SURPASS);
                }
            } else {
                if(withdrawLimit.compareTo(withdraw_counter_fee) > 0) {
                    logger.info("申请金额：{}，提现最小限额：{}，是否相等：{}", req.getAmount(), withdrawLimit, req.getAmount().compareTo(withdrawLimit));
                    if (req.getAmount().compareTo(withdrawLimit) < 0) {
                        throw new PTMessageException(PTMessageEnum.WITHDRAW_AMOUNT_SURPASS);
                    }
                } else {
                    if (req.getAmount().compareTo(withdraw_counter_fee) <= 0) {
                        throw new PTMessageException(PTMessageEnum.WITHDRAW_AMOUNT_SURPASS);
                    }
                }
            }
            // 2、可用余额是否足够
            BsSubAccount tempJsh = subAccountMapper.selectSingleSubActByUserAndType(req.getUserId(), Constants.PRODUCT_TYPE_DEP_JSH);
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
                order.setAmount(req.getAmount());
            } else {
                order.setAmount(req.getAmount());
            }

            // 5、订单信息表中添加一条记录
            Date now = new Date();
            String orderNo = Util.generateOrderNo4BaoFoo(8);
            order.setTerminalType(req.getTerminalType());
            order.setAccountType(Constants.ORDER_ACCOUNT_TYPE_USER);
            order.setBankCardNo(receiveCard.getCardNo());
            order.setBankId(receiveCard.getBankId());
            order.setBankName(BaoFooEnum.pay4BankMap.get(String.valueOf(receiveCard.getBankId())));
            order.setChannel(Constants.CHANNEL_HFBANK);
            order.setChannelTransType(Constants.CHANNEL_TRS_DF);
            order.setCreateTime(now);
            order.setIdCard(receiveCard.getIdCard());
            order.setMobile(receiveCard.getMobile());
            order.setMoneyType(Constants.ORDER_CURRENCY_CNY);
            order.setOrderNo(orderNo);
            order.setStatus(Constants.ORDER_STATUS_CREATE);
            order.setSubAccountId(tempJsh.getId());
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

            jsh = new BsSubAccount();
            bsAccountJnl = new BsAccountJnl();
            // 普通用户提现初始化记账
            userInitAccount(jsh, jshDB, bsAccountJnl, req, fee);

            // 10、收取2元手续费，记录手续费登记表
            BsServiceFee bsServiceFee = new BsServiceFee();
            bsServiceFee.setPlanFee(planFee);
            bsServiceFee.setDoneFee(fee);
            bsServiceFee.setTransAmount(order.getAmount());
            bsServiceFee.setFeeType(Constants.FEE_TYPE_HF_FINANCE_WITHDRAW);
            bsServiceFee.setStatus(Constants.FEE_STATUS_COLLECT);
            bsServiceFee.setCreateTime(new Date());
            bsServiceFee.setOrderNo(order.getOrderNo());
            bsServiceFee.setSubAccountId(order.getSubAccountId());
            bsServiceFee.setUpdateTime(new Date());
            bsServiceFee.setNote("应扣"+ planFee +"，实扣"+fee);
            bsServiceFee.setPaymentPlatformFee(commission.getThreePartyPaymentServiceFee());
            bsServiceFeeMapper.insertSelective(bsServiceFee);

            // 8、记录用户交易明细表(状态为处理中)
            BsUserTransDetail transDetail = new BsUserTransDetail();
            transDetail.setUserId(req.getUserId());
            transDetail.setCardNo(receiveCard.getCardNo());
            transDetail.setTransType(Constants.Trans_TYPE_DEP_WITHDRAW);
            transDetail.setTransStatus(Constants.Trans_STATUS_DEAL);
            transDetail.setOrderNo(order.getOrderNo());
            transDetail.setCreateTime(now);
            transDetail.setAmount(-(MoneyUtil.subtract(req.getAmount(), fee).doubleValue()));
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
        } finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_WITHDRAW_SERVICE.getKey());
        }
        // 三、公共处理
        // 1、发起代付请求
        logger.info("存管体系提现发起代付请求：{}", JSONObject.fromObject(order));

        B2GResMsg_HFBank_UserBatchWithdraw resMsg = new B2GResMsg_HFBank_UserBatchWithdraw();
        B2GReqMsg_HFBank_UserBatchWithdraw b2gReq = new B2GReqMsg_HFBank_UserBatchWithdraw();
        b2gReq.setClient_property(Constants.HF_CLIENT_PROPERTY_PERSON);
        b2gReq.setOrder_no("withDraw"+order.getOrderNo());
        b2gReq.setPartner_trans_date(order.getCreateTime());
        b2gReq.setPartner_trans_time(order.getCreateTime());
        List<BatchWithdrawExtData> data = new ArrayList<>();
        BatchWithdrawExtData withdrawExtData = new BatchWithdrawExtData();
        withdrawExtData.setDetail_no(order.getOrderNo());
        withdrawExtData.setPlatcust(userExt.getHfUserId());
        withdrawExtData.setAmt(order.getAmount());
        withdrawExtData.setIs_advance(Constants.HF_WITHDRAW_IS_ADVANCE_NO);
        withdrawExtData.setPay_code(Constants.HF_PAY_CODE_HFBANK_OUT);//行内通道出金编码
        withdrawExtData.setFee_amt(fee);
        //withdrawExtData.setType("0"); //文档2.0无此字段
        withdrawExtData.setWithdraw_type(null == req.getWithdrawType() ? Constants.HFBANK_WITHDRAW_TYPE_INVEST : req.getWithdrawType());
        withdrawExtData.setRemark("用户提现");
        withdrawExtData.setTran_type(Constants.HF_WITHDRAW_TRAN_TYPE_PAY_REAL); // 跨行
        withdrawExtData.setBank_code(bank.getUnionBankId());
        withdrawExtData.setBank_name(order.getBankName());
        data.add(withdrawExtData);
        b2gReq.setData(data);
        b2gReq.setTotal_num(1);//批量提现接口用于会员提现
        try {
            resMsg = hfbankTransportService.userBatchWithdraw(b2gReq);
        } catch (Exception e){
            logger.error("提现申请通讯失败：{}", e.getMessage());
            resMsg.setResCode(Constants.GATEWAY_PAYING_RES_CODE);
            resMsg.setResMsg("提现通讯失败");
        }

        // 代付下单申请成功并且成功条数为1，等待通知. 此时更新订单表状态为处理中
        if (ConstantUtil.BSRESCODE_SUCCESS.equals(resMsg.getResCode()) && "1".equals(resMsg.getSuccess_num())) {
            // 恒丰银行提现接口不存在立即成功的情况，需等待异步通知。此成功表示受理成功。
            logger.info("提现存管代付下单申请处理中：{}", JSONObject.fromObject(resMsg));
            // 更新订单表
            payOrdersService.modifyOrderStatus4Safe(order.getId(), Constants.ORDER_STATUS_PAYING,
                    null, resMsg.getResCode(), resMsg.getResMsg());
        } else if(Constants.GATEWAY_PAYING_RES_CODE.equals(resMsg.getResCode())) {
        	// 恒丰银行提现接口不存在立即成功的情况，需等待异步通知。此异常表示通讯超时。
            logger.info("提现存管代付下单(超时)申请处理中：{}", JSONObject.fromObject(resMsg));
            // 更新订单表
            payOrdersService.modifyOrderStatus4Safe(order.getId(), Constants.ORDER_STATUS_PAYING,
                    null, resMsg.getResCode(), resMsg.getResMsg());
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

            logger.error("提现存管代付下单申请失败：{}", JSONObject.fromObject(resMsg));
            final String thirdReturnCode;
            final String errorMsg;
            if(CollectionUtils.isNotEmpty(resMsg.getError_data())) {
                thirdReturnCode = resMsg.getError_data().get(0).getError_no();
                errorMsg = resMsg.getError_data().get(0).getError_info();
            } else {
                thirdReturnCode = resMsg.getRecode();
                errorMsg = resMsg.getResMsg();
            }
            final Double finalFee = fee;
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

                    // 1.用户信息表可用余额增加回来(还原)
                    BsUser failUser = new BsUser();
                    failUser.setCanWithdraw(req.getAmount());
                    failUser.setId(req.getUserId());
                    bsUserMapper.updateBonusById(failUser);
                    //修改手续费状态为回退
                    BsServiceFee bsServiceFee = new BsServiceFee();
                    bsServiceFee.setStatus(Constants.FEE_STATUS_RETURN);
                    bsServiceFee.setUpdateTime(new Date());
                    BsServiceFeeExample bsServiceFeeExample = new BsServiceFeeExample();
                    bsServiceFeeExample.createCriteria().andOrderNoEqualTo(order.getOrderNo()).andFeeTypeEqualTo(Constants.FEE_TYPE_HF_FINANCE_WITHDRAW);
                    bsServiceFeeMapper.updateByExampleSelective(bsServiceFee, bsServiceFeeExample);

                    // 4.用户结算户，做反向操作（冻结金额减少，可用余额增加,可提现余额增加）
                    BsSubAccount failJshDB = subAccountService
                            .findDEPJSHSubAccountByUserId(req.getUserId());
                    BsSubAccount failJsh = new BsSubAccount();
                    failJsh.setId(failJshDB.getId());
                    failJsh.setAvailableBalance(req.getAmount());
                    failJsh.setCanWithdraw(req.getAmount());
                    failJsh.setFreezeBalance(-req.getAmount());
                    failJsh.setLastTransDate(new Date());
                    subAccountService.modifyBalancesByIncrement(failJsh);

                    // 5.添加一条解冻记账流水
                    BsAccountJnl bsAccountJnl = new BsAccountJnl();
                    bsAccountJnl.setTransTime(new Date());
                    bsAccountJnl.setTransCode(Constants.USER_UNFREEZE);
                    bsAccountJnl.setTransName("用户存管提现发送失败，解冻余额");// 解冻
                    bsAccountJnl.setTransAmount(req.getAmount());
                    bsAccountJnl.setSysTime(new Date());
                    bsAccountJnl.setCdFlag1(Constants.CD_FLAG_D);
                    bsAccountJnl.setUserId1(req.getUserId());
                    bsAccountJnl.setAccountId1(failJshDB.getAccountId());
                    bsAccountJnl.setSubAccountId1(failJshDB.getId());
                    bsAccountJnl.setSubAccountCode1(failJshDB.getCode());
                    bsAccountJnl.setBeforeBalance1(failJshDB.getBalance());
                    bsAccountJnl
                            .setBeforeAvialableBalance1(failJshDB.getAvailableBalance());
                    bsAccountJnl.setBeforeFreezeBalance1(failJshDB.getFreezeBalance());
                    bsAccountJnl.setAfterBalance1(failJshDB.getBalance());
                    bsAccountJnl.setAfterAvialableBalance1(MoneyUtil.add(failJshDB.getAvailableBalance()
                            , req.getAmount()).doubleValue());
                    bsAccountJnl.setAfterFreezeBalance1(MoneyUtil.subtract(failJshDB.getFreezeBalance()
                            , req.getAmount()).doubleValue());
                    bsAccountJnl.setCdFlag2(Constants.CD_FLAG_C);
                    bsAccountJnl.setUserId2(req.getUserId());
                    bsAccountJnl.setSubAccountCode2(failJshDB.getCode());
                    bsAccountJnl.setFee(finalFee);
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
                }
            });

            // 7.特殊交易流水表中，添加一条记录
            try {
                specialJnlService.warn4Fail(req.getAmount(), "【客户余额提现】用户编号["
                                + req.getUserId() + "]回款产生异常", order.getOrderNo(),
                        "客户余额提现", false);
                smsServiceClient.sendTemplateMessage(user.getMobile(),
                        TemplateKey.WITHDRAW_FALL, String.valueOf(req.getAmount()),
                        "交易失败");
                // 微信模板消息
                String bankCard = receiveCard.getCardNo();
                sendWechatService.withdrawMgs(user.getId(), "", bankCard.substring(
                        bankCard.length() - 4, bankCard.length()), String
                        .valueOf(req.getAmount()), "fall", "交易失败", null, null);
            }catch (Exception e){
                logger.error("提现失败通知到用户异常", e);
            }
            throw new PTMessageException(PTMessageEnum.TRANS_ERROR.getCode(), "提现失败，请重试");
        }
        // 6.后置处理(无数据库更新处理)
    }

    @Override
    public void checkPass(final ReqMsg_UserBalance_Withdraw req, final ResMsg_UserBalance_Withdraw res, String checkStatus) {
        if(Constants.WITHDRAW_PASS.equals(checkStatus)) {
            logger.info("管理台审核提现开始：{}，审核状态：{}", JSONObject.fromObject(req), checkStatus);
        } else {
            logger.info("提现队列定时发起提现请求开始：{}，审核状态：{}", JSONObject.fromObject(req), checkStatus);
        }

        BalanceWithdrawCheckVO check = new BalanceWithdrawCheckVO();
        check.setUserId(req.getUserId());
        this.withdrawCheck(check);

        final BsPayOrders order = new BsPayOrders();
        BsPayOrdersJnl insertOrderJnl = new BsPayOrdersJnl();
        BsUser user;
        BsSubAccount jshDB;
        BsBankCard receiveCard;
        Date applyTime;
        Double planFee = 0d;
        Double fee = 0d;
        BsHfbankUserExt userExt;
        BsBank bank;
        boolean isCompensatoryUserZAN = isCompensatoryUserZAN(req.getUserId());
        final BsWithdrawCheck bsWithdrawCheck = bsWithdrawCheckService.selectWithdrawCheck(Integer.parseInt(req.getCheckId()));
        try {
            jsClientDaoSupport.lock(RedisLockEnum.LOCK_WITHDRAW_CHECK_PASS_SERVICE.getKey());
            // 零、公共校验
            // 1.0. 恒丰日切时间，禁止充值提现操作
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

            // 1.检验用户是否存在
            user = userService.findUserById(req.getUserId());
            if (user == null) {
                throw new PTMessageException(PTMessageEnum.USER_NO_EXIT);
            }
            // 赞分期代偿人不允许前端提现
            if(isCompensatoryUserZAN) {
                throw new PTMessageException(PTMessageEnum.WITHDRAW_NOT_ALLOWED);
            }
            // 2、是否有回款卡
            receiveCard = bankCardService.findFirstBankCardByUserId(req
                    .getUserId());
            if (receiveCard == null) {
                throw new PTMessageException(PTMessageEnum.BANKCARD_NOT_BIND);
            }
            bank = bsBankMapper.selectByPrimaryKey(receiveCard.getBankId());
            // 3、是否恒丰开户
            BsHfbankUserExtExample extExample = new BsHfbankUserExtExample();
            extExample.createCriteria().andUserIdEqualTo(user.getId()).andStatusEqualTo(Constants.HFBANK_USER_EXT_STATUS_OPEN);
            List<BsHfbankUserExt> ext = bsHfbankUserExtMapper.selectByExample(extExample);
            if (CollectionUtils.isEmpty(ext)) {
                throw new PTMessageException(PTMessageEnum.HF_NOT_BIND);
            }
            userExt = ext.get(0);
            // 管理台审核提现
            // 1.检验是否存在该提现审核记录
            applyTime = bsWithdrawCheck.getCreateTime();
            if (bsWithdrawCheck == null || (!bsWithdrawCheck.getStatus().equals(Constants.WITHDRAW_TO_CHECK) && !bsWithdrawCheck.getStatus().equals(Constants.WITHDRAW_PASS_PROCESS)) ) {
                throw new PTMessageException(PTMessageEnum.WITHDRAW_CHECK_NO_EXIT);
            }
            req.setAmount(bsWithdrawCheck.getAmount());

            // 2.查询JSH冻结余额是否足够
            jshDB = subAccountService
                    .findDEPJSHSubAccountByUserId(bsWithdrawCheck.getUserId());
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
                logger.info("申请金额：{}，提现最小限额：{}，是否相等：{}", req.getAmount(), withdrawLimit, req.getAmount().compareTo(withdrawLimit));
                if (req.getAmount().compareTo(withdrawLimit) < 0) {
                    throw new PTMessageException(PTMessageEnum.WITHDRAW_AMOUNT_SURPASS);
                }
            } else {
                if(withdrawLimit.compareTo(withdraw_counter_fee) > 0) {
                    logger.info("申请金额：{}，提现最小限额：{}，是否相等：{}", req.getAmount(), withdrawLimit, req.getAmount().compareTo(withdrawLimit));
                    if (req.getAmount().compareTo(withdrawLimit) < 0) {
                        throw new PTMessageException(PTMessageEnum.WITHDRAW_AMOUNT_SURPASS);
                    }
                } else {
                    if (req.getAmount().compareTo(withdraw_counter_fee) <= 0) {
                        throw new PTMessageException(PTMessageEnum.WITHDRAW_AMOUNT_SURPASS);
                    }
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
                order.setAmount(bsWithdrawCheck.getAmount());
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
            order.setChannel(Constants.CHANNEL_HFBANK);
            order.setChannelTransType(Constants.CHANNEL_TRS_DF);
            order.setCreateTime(now);
            order.setIdCard(receiveCard.getIdCard());
            order.setMobile(receiveCard.getMobile());
            order.setMoneyType(Constants.ORDER_CURRENCY_CNY);
            order.setOrderNo(orderNo);
            order.setStatus(Constants.ORDER_STATUS_CREATE);
            order.setSubAccountId(jshDB.getId());
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
            bsServiceFee.setFeeType(Constants.FEE_TYPE_HF_FINANCE_WITHDRAW);
            bsServiceFee.setStatus(Constants.FEE_STATUS_COLLECT);
            bsServiceFee.setCreateTime(new Date());
            bsServiceFee.setOrderNo(order.getOrderNo());
            bsServiceFee.setSubAccountId(order.getSubAccountId());
            bsServiceFee.setUpdateTime(new Date());
            bsServiceFee.setNote("应扣"+ planFee +"，实扣"+fee);
            bsServiceFee.setPaymentPlatformFee(commission.getThreePartyPaymentServiceFee());
            bsServiceFeeMapper.insertSelective(bsServiceFee);
            // 5、修改记录提现审核表
            bsWithdrawCheck.setStatus(checkStatus); // Constants.WITHDRAW_PASS
            bsWithdrawCheck.setId(Integer.parseInt(req.getCheckId()));
            bsWithdrawCheck.setOrderNo(order.getOrderNo());
            bsWithdrawCheck.setmUserId(req.getManageId());
            bsWithdrawCheck.setExecutionTime(new Date());
            bsWithdrawCheckMapper.updateByPrimaryKeySelective(bsWithdrawCheck);
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
        } finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_WITHDRAW_CHECK_PASS_SERVICE.getKey());
        }

        // 1、发起代付请求
        logger.info("存管体系提现发起代付请求：{}", JSONObject.fromObject(order));
        B2GResMsg_HFBank_UserBatchWithdraw resMsg = new B2GResMsg_HFBank_UserBatchWithdraw();
        B2GReqMsg_HFBank_UserBatchWithdraw b2gReq = new B2GReqMsg_HFBank_UserBatchWithdraw();
        b2gReq.setClient_property(Constants.HF_CLIENT_PROPERTY_PERSON);
        b2gReq.setOrder_no("withDraw"+order.getOrderNo());
        b2gReq.setPartner_trans_date(order.getCreateTime());
        b2gReq.setPartner_trans_time(order.getCreateTime());
        List<BatchWithdrawExtData> data = new ArrayList<>();
        BatchWithdrawExtData withdrawExtData = new BatchWithdrawExtData();
        withdrawExtData.setDetail_no(order.getOrderNo());
        withdrawExtData.setPlatcust(userExt.getHfUserId());
        withdrawExtData.setAmt(order.getAmount());
        withdrawExtData.setIs_advance(Constants.HF_WITHDRAW_IS_ADVANCE_NO);
        withdrawExtData.setPay_code(Constants.HF_PAY_CODE_HFBANK_OUT);//行内通道出金编码
        withdrawExtData.setFee_amt(fee);
        //withdrawExtData.setType("0"); //文档2.0无此字段
        withdrawExtData.setWithdraw_type(null == req.getWithdrawType() ? Constants.HFBANK_WITHDRAW_TYPE_INVEST : req.getWithdrawType());
        withdrawExtData.setRemark("用户提现");
        withdrawExtData.setTran_type(Constants.HF_WITHDRAW_TRAN_TYPE_PAY_REAL); // 跨行
        withdrawExtData.setBank_code(bank.getUnionBankId());
        withdrawExtData.setBank_name(order.getBankName());
        data.add(withdrawExtData);
        b2gReq.setData(data);
        b2gReq.setTotal_num(1);//批量提现接口用于会员提现
        try {
            resMsg = hfbankTransportService.userBatchWithdraw(b2gReq);
        } catch (Exception e){
            logger.error("提现申请通讯失败：{}", e.getMessage());
            resMsg.setResCode(Constants.GATEWAY_PAYING_RES_CODE);
            resMsg.setResMsg("提现通讯失败");
        }

        // 代付下单申请成功并且成功条数为1，等待通知. 此时更新订单表状态为处理中
        if (ConstantUtil.BSRESCODE_SUCCESS.equals(resMsg.getResCode()) && "1".equals(resMsg.getSuccess_num())) {
            // 恒丰银行提现接口不存在立即成功的情况，需等待异步通知。此成功表示受理成功。
            logger.info("提现存管代付下单申请处理中：{}", JSONObject.fromObject(resMsg));
            // 更新订单表
            payOrdersService.modifyOrderStatus4Safe(order.getId(), Constants.ORDER_STATUS_PAYING,
                    null, resMsg.getResCode(), resMsg.getResMsg());
        } else if(Constants.GATEWAY_PAYING_RES_CODE.equals(resMsg.getResCode())) {
        	// 恒丰银行提现接口不存在立即成功的情况，需等待异步通知。此异常表示通讯超时。
            logger.info("提现存管代付下单(超时)申请处理中：{}", JSONObject.fromObject(resMsg));
            // 更新订单表
            payOrdersService.modifyOrderStatus4Safe(order.getId(), Constants.ORDER_STATUS_PAYING,
                    null, resMsg.getResCode(), resMsg.getResMsg());
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

            logger.error("提现存管代付下单申请失败：{}", JSONObject.fromObject(resMsg));
            final String thirdReturnCode;
            final String errorMsg;
            if(CollectionUtils.isNotEmpty(resMsg.getError_data())) {
                thirdReturnCode = resMsg.getError_data().get(0).getError_no();
                errorMsg = resMsg.getError_data().get(0).getError_info();
            } else {
                thirdReturnCode = resMsg.getRecode();
                errorMsg = resMsg.getResMsg();
            }
            final Double finalFee = fee;
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

                    // 1.用户信息表可用余额增加回来(还原)
                    BsUser failUser = new BsUser();
                    failUser.setCanWithdraw(req.getAmount());
                    failUser.setId(req.getUserId());
                    bsUserMapper.updateBonusById(failUser);
                    //修改手续费状态为回退
                    BsServiceFee bsServiceFee = new BsServiceFee();
                    bsServiceFee.setStatus(Constants.FEE_STATUS_RETURN);
                    bsServiceFee.setUpdateTime(new Date());
                    BsServiceFeeExample bsServiceFeeExample = new BsServiceFeeExample();
                    bsServiceFeeExample.createCriteria().andOrderNoEqualTo(order.getOrderNo()).andFeeTypeEqualTo(Constants.FEE_TYPE_HF_FINANCE_WITHDRAW);
                    bsServiceFeeMapper.updateByExampleSelective(bsServiceFee, bsServiceFeeExample);

                    // 4.用户结算户，做反向操作（冻结金额减少，可用余额增加,可提现余额增加）
                    BsSubAccount failJshDB = subAccountService
                            .findDEPJSHSubAccountByUserId(req.getUserId());
                    BsSubAccount failJsh = new BsSubAccount();
                    failJsh.setId(failJshDB.getId());
                    failJsh.setAvailableBalance(req.getAmount());
                    failJsh.setCanWithdraw(req.getAmount());
                    failJsh.setFreezeBalance(-req.getAmount());
                    failJsh.setLastTransDate(new Date());
                    subAccountService.modifyBalancesByIncrement(failJsh);

                    // 5.添加一条解冻记账流水
                    BsAccountJnl bsAccountJnl = new BsAccountJnl();
                    bsAccountJnl.setTransTime(new Date());
                    bsAccountJnl.setTransCode(Constants.USER_UNFREEZE);
                    bsAccountJnl.setTransName("用户存管提现发送失败，解冻余额");// 解冻
                    bsAccountJnl.setTransAmount(req.getAmount());
                    bsAccountJnl.setSysTime(new Date());
                    bsAccountJnl.setCdFlag1(Constants.CD_FLAG_D);
                    bsAccountJnl.setUserId1(req.getUserId());
                    bsAccountJnl.setAccountId1(failJshDB.getAccountId());
                    bsAccountJnl.setSubAccountId1(failJshDB.getId());
                    bsAccountJnl.setSubAccountCode1(failJshDB.getCode());
                    bsAccountJnl.setBeforeBalance1(failJshDB.getBalance());
                    bsAccountJnl
                            .setBeforeAvialableBalance1(failJshDB.getAvailableBalance());
                    bsAccountJnl.setBeforeFreezeBalance1(failJshDB.getFreezeBalance());
                    bsAccountJnl.setAfterBalance1(failJshDB.getBalance());
                    bsAccountJnl.setAfterAvialableBalance1(MoneyUtil.add(failJshDB.getAvailableBalance()
                            , req.getAmount()).doubleValue());
                    bsAccountJnl.setAfterFreezeBalance1(MoneyUtil.subtract(failJshDB.getFreezeBalance()
                            , req.getAmount()).doubleValue());
                    bsAccountJnl.setCdFlag2(Constants.CD_FLAG_C);
                    bsAccountJnl.setUserId2(req.getUserId());
                    bsAccountJnl.setSubAccountCode2(failJshDB.getCode());
                    bsAccountJnl.setFee(finalFee);
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

                    bsWithdrawCheck.setExecutionTime(new Date());
                    bsWithdrawCheck.setNote(res.getResMsg());
                    bsWithdrawCheckMapper.updateByPrimaryKeySelective(bsWithdrawCheck);
                }
            });


            // 7.特殊交易流水表中，添加一条记录
            try {
                // 客户余额提现失败,告警发送客服，技术
                specialJnlService.warnAppoint4Fail(req.getAmount(), "["+ user.getMobile() +"]手机号客户，提现订单号["+ order.getOrderNo() +"]受理失败，请联系恒丰技术确认失败原因",
                        order.getOrderNo(),"客户余额提现", false, Constants.CUSTOMER_SERVICE_MOBILE, Constants.EMERGENCY_MOBILE);
                smsServiceClient.sendTemplateMessage(user.getMobile(),
                        TemplateKey.WITHDRAW_FALL, String.valueOf(req.getAmount()),
                        "交易失败");
                // 微信模板消息
                String bankCard = receiveCard.getCardNo();
                sendWechatService.withdrawMgs(user.getId(), "", bankCard.substring(
                        bankCard.length() - 4, bankCard.length()), String
                        .valueOf(req.getAmount()), "fall", "交易失败", null, null);
            }catch (Exception e){
                logger.error("提现失败通知到用户异常", e);
            }
        }
        // 6.后置处理(无数据库更新处理)
    }

    @Override
    public void preCompensatorApply(ReqMsg_UserBalance_Withdraw req, ResMsg_UserBalance_Withdraw res) {
        // 1.0. 恒丰日切时间，禁止充值提现操作
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

        // 币港湾理财人提现申请金额限制（用于判断是否审核提现） 50000
        BsSysConfig withdrawApplyLimitConfig = bsSysConfigMapper.selectByPrimaryKey(Constants.WITHDRAW_APPLY_LIMIT);
        Double withdrawApplyLimit = withdrawApplyLimitConfig == null ? 50000d : Double.valueOf(withdrawApplyLimitConfig.getConfValue());
        Double applyAmount = req.getAmount();
        if(applyAmount.compareTo(withdrawApplyLimit) > 0) {
            // 申请金额 > 5万 进入发送队列工作日发送
            Date now = new Date();
            BsUserTransDetail transDetail = new BsUserTransDetail();
            transDetail.setUserId(req.getUserId());
            transDetail.setTransType(Constants.Trans_TYPE_DEP_WITHDRAW);
            transDetail.setTransStatus(Constants.Trans_STATUS_DEAL);
            transDetail.setOrderNo("");
            transDetail.setCreateTime(now);
            transDetail.setAmount(-applyAmount);
            transDetail.setUpdateTime(now);
            bsUserTransDetailMapper.insertSelective(transDetail);

            BsWithdrawCheck bsWithdrawCheck = new BsWithdrawCheck();
            bsWithdrawCheck.setAmount(req.getAmount());
            bsWithdrawCheck.setUserId(req.getUserId());
            bsWithdrawCheck.setStatus(Constants.WITHDRAW_PASS_QUE);
            bsWithdrawCheck.setTransDetailId(transDetail.getId());
            bsWithdrawCheck.setTerminalType(req.getTerminalType());
            bsWithdrawCheck.setmUserId(req.getManageId());
            bsWithdrawCheck.setCreateTime(now);
            bsWithdrawCheck.setCheckTime(now);
            bsWithdrawCheck.setClearingMark(Constants.WITHDRAW_CLEAR_MARK_NO);
            bsWithdrawCheckService.addWithdrawCheck(bsWithdrawCheck);
        } else {
            this.compensatorApply(req, res);
        }
    }

    @Override
    public void compensatorApply(final ReqMsg_UserBalance_Withdraw req, final ResMsg_UserBalance_Withdraw res) {
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

        final BsPayOrders order = new BsPayOrders();
        BsPayOrdersJnl insertOrderJnl = new BsPayOrdersJnl();
        BsUser user;
        BsUser tempUser;
        BsSubAccount jshDB;
        BsBankCard receiveCard;
        Date applyTime;
        Double planFee = 0d;
        Double fee = 0d;
        BsHfbankUserExt userExt;
        BsBank bank;
        boolean isCompensatoryUserZAN = isCompensatoryUserZAN(req.getUserId());
        try {
            jsClientDaoSupport.lock(RedisLockEnum.LOCK_WITHDRAW_SERVICE.getKey());
            // 零、公共校验
            // 1.0. 恒丰日切时间，禁止充值提现操作
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

            // 1.检验用户是否存在
            user = userService.findUserById(req.getUserId());
            if (user == null) {
                check2SuccWithdraw2Fail(req.getCheckId());
                throw new PTMessageException(PTMessageEnum.USER_NO_EXIT);
            }
            // 赞分期代偿人不允许前端提现
            if(!isCompensatoryUserZAN) {
                check2SuccWithdraw2Fail(req.getCheckId());
                throw new PTMessageException(PTMessageEnum.WITHDRAW_NOT_ALLOWED.getCode(), "当前用户不是代偿人");
            }
            // 2、是否有回款卡
            receiveCard = bankCardService.findFirstBankCardByUserId(req
                    .getUserId());
            if (receiveCard == null) {
                check2SuccWithdraw2Fail(req.getCheckId());
                throw new PTMessageException(PTMessageEnum.BANKCARD_NOT_BIND);
            }
            bank = bsBankMapper.selectByPrimaryKey(receiveCard.getBankId());
            // 3、是否恒丰开户
            BsHfbankUserExtExample extExample = new BsHfbankUserExtExample();
            extExample.createCriteria().andUserIdEqualTo(user.getId()).andStatusEqualTo(Constants.HFBANK_USER_EXT_STATUS_OPEN);
            List<BsHfbankUserExt> ext = bsHfbankUserExtMapper.selectByExample(extExample);
            if (CollectionUtils.isEmpty(ext)) {
                check2SuccWithdraw2Fail(req.getCheckId());
                throw new PTMessageException(PTMessageEnum.HF_NOT_BIND);
            }
            userExt = ext.get(0);
            // 用户提现
            applyTime = new Date();

            BsSysConfig eachMonthWithdrawTimesConfig = bsSysConfigMapper.selectByPrimaryKey(Constants.EACH_MONTH_WITHDRAW_TIMES);
            int each_month_withdraw_times = Integer.parseInt(eachMonthWithdrawTimesConfig.getConfValue());
            CommissionVO commission = commissionService.calServiceFee(req.getAmount(), TransTypeEnum.USER_WITHDRAW, PayPlatformEnum.BAOFOO);
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
                    check2SuccWithdraw2Fail(req.getCheckId());
                    throw new PTMessageException(PTMessageEnum.WITHDRAW_AMOUNT_SURPASS);
                }
            }
            // 2、余额是否足够
            BsSubAccount tempJsh = subAccountMapper.selectSingleSubActByUserAndType(req.getUserId(), Constants.PRODUCT_TYPE_DEP_JSH);
            jshDB = subAccountMapper.selectSubAccountByIdForLock(tempJsh.getId());
            if (MoneyUtil.subtract(req.getAmount(), jshDB.getBalance()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() > 0) {
                check2SuccWithdraw2Fail(req.getCheckId());
                throw new PTMessageException(PTMessageEnum.WITHDRAW_AMOUNT_SURPASS);
            }
            if (historyWithdrawOrderCount >= each_month_withdraw_times) {
                planFee = commission.getNeedPayAmount();
                order.setAmount(req.getAmount());
            } else {
                order.setAmount(req.getAmount());
            }
            BsPayOrdersExample ordersExample = new BsPayOrdersExample();
            ordersExample.createCriteria().andUserIdEqualTo(user.getId()).andChannelTransTypeEqualTo(Constants.CHANNEL_TRS_DF)
                    .andTransTypeEqualTo(Constants.TRANS_BALANCE_WITHDRAW).andStatusEqualTo(Constants.ORDER_STATUS_PAYING);
            List<BsPayOrders> orders = bsPayOrdersMapper.selectByExample(ordersExample);
            Double payingAmount = 0d;
            for (BsPayOrders paying: orders) {
                payingAmount = MoneyUtil.add(payingAmount, paying.getAmount()).doubleValue();
            }
            if(jshDB.getBalance().compareTo(MoneyUtil.add(payingAmount, req.getAmount()).doubleValue()) <= 0) {
                check2SuccWithdraw2Fail(req.getCheckId());
                throw new PTMessageException(PTMessageEnum.WITHDRAW_AMOUNT_SURPASS);
            }

            // 5、订单信息表中添加一条记录
            Date now = new Date();
            String orderNo = Util.generateOrderNo4BaoFoo(8);
            order.setTerminalType(Constants.TERMINAL_TYPE_MANAGE);
            order.setAccountType(Constants.ORDER_ACCOUNT_TYPE_USER);
            order.setBankCardNo(receiveCard.getCardNo());
            order.setBankId(receiveCard.getBankId());
            order.setBankName(BaoFooEnum.pay4BankMap.get(String.valueOf(receiveCard.getBankId())));
            order.setChannel(Constants.CHANNEL_HFBANK);
            order.setChannelTransType(Constants.CHANNEL_TRS_DF);
            order.setCreateTime(now);
            order.setIdCard(receiveCard.getIdCard());
            order.setMobile(receiveCard.getMobile());
            order.setMoneyType(Constants.ORDER_CURRENCY_CNY);
            order.setOrderNo(orderNo);
            order.setStatus(Constants.ORDER_STATUS_CREATE);
            order.setTransType(Constants.TRANS_BALANCE_WITHDRAW);
            order.setUpdateTime(now);
            order.setUserId(req.getUserId());
            order.setUserName(receiveCard.getCardOwner());
            order.setSubAccountId(tempJsh.getId());
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
            bsServiceFee.setFeeType(Constants.FEE_TYPE_HF_FINANCE_WITHDRAW);
            bsServiceFee.setStatus(Constants.FEE_STATUS_COLLECT);
            bsServiceFee.setCreateTime(new Date());
            bsServiceFee.setOrderNo(order.getOrderNo());
            bsServiceFee.setSubAccountId(order.getSubAccountId());
            bsServiceFee.setUpdateTime(new Date());
            bsServiceFee.setNote("应扣"+ planFee +"，实扣"+fee);
            bsServiceFee.setPaymentPlatformFee(commission.getThreePartyPaymentServiceFee());
            bsServiceFeeMapper.insertSelective(bsServiceFee);

            // 8、记录用户交易明细表(状态为处理中)
            if(StringUtil.isNotBlank(req.getCheckId())) {
                BsWithdrawCheck check = bsWithdrawCheckMapper.selectByPrimaryKey(Integer.parseInt(req.getCheckId()));
                check.setStatus(Constants.WITHDRAW_PASS_QUE_SUCC);
                check.setExecutionTime(new Date());
                check.setOrderNo(order.getOrderNo());
                bsWithdrawCheckMapper.updateByPrimaryKeySelective(check);

                BsUserTransDetail transDetail = new BsUserTransDetail();
                transDetail.setId(check.getTransDetailId());
                transDetail.setOrderNo(order.getOrderNo());
                transDetail.setCardNo(receiveCard.getCardNo());
                transDetail.setUpdateTime(now);
                bsUserTransDetailMapper.updateByPrimaryKeySelective(transDetail);
            } else {
                BsUserTransDetail transDetail = new BsUserTransDetail();
                transDetail.setUserId(req.getUserId());
                transDetail.setCardNo(receiveCard.getCardNo());
                transDetail.setTransType(Constants.Trans_TYPE_DEP_WITHDRAW);
                transDetail.setTransStatus(Constants.Trans_STATUS_DEAL);
                transDetail.setOrderNo(order.getOrderNo());
                transDetail.setCreateTime(now);
                transDetail.setAmount(-(MoneyUtil.subtract(req.getAmount(), fee).doubleValue()));
                transDetail.setUpdateTime(now);
                bsUserTransDetailMapper.insertSelective(transDetail);
            }
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

        } finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_WITHDRAW_SERVICE.getKey());
        }
        // 三、公共处理
        // 1、发起代付请求
        logger.info("存管体系提现发起代付请求：{}", JSONObject.fromObject(order));

        B2GResMsg_HFBank_UserBatchWithdraw resMsg = new B2GResMsg_HFBank_UserBatchWithdraw();
        B2GReqMsg_HFBank_UserBatchWithdraw b2gReq = new B2GReqMsg_HFBank_UserBatchWithdraw();
        b2gReq.setClient_property(Constants.HF_CLIENT_PROPERTY_PERSON);
        b2gReq.setOrder_no("withDraw"+order.getOrderNo());
        b2gReq.setPartner_trans_date(order.getCreateTime());
        b2gReq.setPartner_trans_time(order.getCreateTime());
        List<BatchWithdrawExtData> data = new ArrayList<>();
        BatchWithdrawExtData withdrawExtData = new BatchWithdrawExtData();
        withdrawExtData.setDetail_no(order.getOrderNo());
        withdrawExtData.setPlatcust(userExt.getHfUserId());
        withdrawExtData.setAmt(order.getAmount());
        withdrawExtData.setIs_advance(Constants.HF_WITHDRAW_IS_ADVANCE_NO);
        withdrawExtData.setPay_code(Constants.HF_PAY_CODE_HFBANK_OUT);//行内通道出金编码
        withdrawExtData.setFee_amt(fee);
        //withdrawExtData.setType("0"); //文档2.0无此字段
        withdrawExtData.setWithdraw_type(null == req.getWithdrawType() ? Constants.HFBANK_WITHDRAW_TYPE_INVEST : req.getWithdrawType());
        withdrawExtData.setRemark("用户提现");
        withdrawExtData.setTran_type(Constants.HF_WITHDRAW_TRAN_TYPE_PAY_REAL); // 跨行
        withdrawExtData.setBank_code(bank.getUnionBankId());
        withdrawExtData.setBank_name(order.getBankName());
        data.add(withdrawExtData);
        b2gReq.setData(data);
        b2gReq.setTotal_num(1);//批量提现接口用于会员提现
        try {
            resMsg = hfbankTransportService.userBatchWithdraw(b2gReq);
        } catch (Exception e){
            logger.error("提现申请通讯失败：{}", e.getMessage());
            resMsg.setResCode(Constants.GATEWAY_PAYING_RES_CODE);
            resMsg.setResMsg("提现通讯失败");
        }

        // 代付下单申请成功并且成功条数为1，等待通知. 此时更新订单表状态为处理中
        if (ConstantUtil.BSRESCODE_SUCCESS.equals(resMsg.getResCode()) && "1".equals(resMsg.getSuccess_num())) {
            // 恒丰银行提现接口不存在立即成功的情况，需等待异步通知。此成功表示受理成功。
            logger.info("提现存管代付下单申请处理中：{}", JSONObject.fromObject(resMsg));
            // 更新订单表
            payOrdersService.modifyOrderStatus4Safe(order.getId(), Constants.ORDER_STATUS_PAYING,
                    null, resMsg.getResCode(), resMsg.getResMsg());
        } else if(Constants.GATEWAY_PAYING_RES_CODE.equals(resMsg.getResCode())) {
        	// 恒丰银行提现接口不存在立即成功的情况，需等待异步通知。此异常表示通讯超时。
            logger.info("提现存管代付下单(超时)申请处理中：{}", JSONObject.fromObject(resMsg));
            // 更新订单表
            payOrdersService.modifyOrderStatus4Safe(order.getId(), Constants.ORDER_STATUS_PAYING,
                    null, resMsg.getResCode(), resMsg.getResMsg());
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

            logger.error("提现存管代付下单申请失败：{}", JSONObject.fromObject(resMsg));
            final String thirdReturnCode;
            final String errorMsg;
            if(CollectionUtils.isNotEmpty(resMsg.getError_data())) {
                thirdReturnCode = resMsg.getError_data().get(0).getError_no();
                errorMsg = resMsg.getError_data().get(0).getError_info();
            } else {
                thirdReturnCode = resMsg.getRecode();
                errorMsg = resMsg.getResMsg();
            }
            final Double finalFee = fee;
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

                    // 1.用户信息表可用余额增加回来(还原)
                    BsUser failUser = new BsUser();
                    failUser.setCanWithdraw(req.getAmount());
                    failUser.setId(req.getUserId());
                    bsUserMapper.updateBonusById(failUser);
                    //修改手续费状态为回退
                    BsServiceFee bsServiceFee = new BsServiceFee();
                    bsServiceFee.setStatus(Constants.FEE_STATUS_RETURN);
                    bsServiceFee.setUpdateTime(new Date());
                    BsServiceFeeExample bsServiceFeeExample = new BsServiceFeeExample();
                    bsServiceFeeExample.createCriteria().andOrderNoEqualTo(order.getOrderNo()).andFeeTypeEqualTo(Constants.FEE_TYPE_HF_FINANCE_WITHDRAW);
                    bsServiceFeeMapper.updateByExampleSelective(bsServiceFee, bsServiceFeeExample);

                    // 6.更新用户交易明细表，状态为失败
                    BsUserTransDetailExample example = new BsUserTransDetailExample();
                    example.createCriteria().andOrderNoEqualTo(order.getOrderNo());
                    BsUserTransDetail tmpTransDetail = new BsUserTransDetail();
                    tmpTransDetail.setTransStatus(Constants.Trans_STATUS_FAIL);
                    tmpTransDetail.setReturnCode(thirdReturnCode);
                    tmpTransDetail.setReturnMsg(res.getResMsg());
                    tmpTransDetail.setUpdateTime(new Date());
                    bsUserTransDetailMapper.updateByExampleSelective(tmpTransDetail, example);

                }
            });

            // 7.特殊交易流水表中，添加一条记录
            try {
                specialJnlService.warn4Fail(req.getAmount(), "【客户余额提现】用户编号["
                                + req.getUserId() + "]回款产生异常", order.getOrderNo(),
                        "客户余额提现", false);
                smsServiceClient.sendTemplateMessage(user.getMobile(),
                        TemplateKey.WITHDRAW_FALL, String.valueOf(req.getAmount()),
                        "交易失败");
                // 微信模板消息
                String bankCard = receiveCard.getCardNo();
                sendWechatService.withdrawMgs(user.getId(), "", bankCard.substring(
                        bankCard.length() - 4, bankCard.length()), String
                        .valueOf(req.getAmount()), "fall", "交易失败", null, null);
            }catch (Exception e){
                logger.error("提现失败通知到用户异常", e);
            }
        }
        // 6.后置处理(无数据库更新处理)
    }

    private void check2SuccWithdraw2Fail(String checkId) {
        if(!StringUtil.isBlank(checkId)) {
            BsWithdrawCheck check = bsWithdrawCheckMapper.selectByPrimaryKey(Integer.parseInt(checkId));
            check.setStatus(Constants.WITHDRAW_PASS_QUE_SUCC);
            bsWithdrawCheckMapper.updateByPrimaryKeySelective(check);

            BsUserTransDetail transDetail = new BsUserTransDetail();
            transDetail.setId(check.getTransDetailId());
            transDetail.setUpdateTime(new Date());
            transDetail.setTransStatus(Constants.Trans_STATUS_FAIL);
            bsUserTransDetailMapper.updateByPrimaryKeySelective(transDetail);
        }
    }

    @Override
    public void notify(DFResultInfo req) {
        try {
            logger.info("提现通知开始：{}", JSONObject.fromObject(req));
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
                    logger.warn("【用户提现通知异常】用户编号[" + order.getUserId() + "]恒丰重复通知");
                    /*specialJnlService.warn4FailNoSMS(order.getAmount(),
                            "【用户提现通知异常】用户编号[" + order.getUserId() + "]存管重复通知",
                            order.getOrderNo(), "用户提现通知异常");*/
                    return;
                } else {
                	//查询LnPayOrders表是否存在提现记录
                	LnPayOrdersExample lnPayOrdersExample = new LnPayOrdersExample();
                	lnPayOrdersExample.createCriteria().andOrderNoEqualTo(orderNo).andStatusEqualTo(Integer.parseInt(LoanStatus.ORDERS_STATUS_PAYING.getCode()));
                	List<LnPayOrders> lnPayOrders = payOrdersMapper.selectByExample(lnPayOrdersExample);
                	if(CollectionUtils.isEmpty(lnPayOrders)){
                		// 没有发生过这个交易，记录特殊流水表
                        specialJnlService.warn4FailNoSMS(-1.0,
                                "【用户提现通知异常】用户编号[未知]存管通知了一个未知的订单号：" + orderNo, orderNo,
                                "用户提现通知异常");
                        return;
                    }
                    PartnerEnum partnerEnum = PartnerEnum.getEnumByCode(lnPayOrders.get(0).getPartnerCode());
                    if(partnerEnum == null) {
                        throw new PTMessageException(PTMessageEnum.TRANS_ERROR.getCode(), "未找到合作方编码");
                    }
                	//发生过借款人代付提现，做处理
                	 if (OrderStatus.SUCCESS.getCode().equals(req.getOrderStatus())) {
                		 G2BReqMsg_HFBank_OutOfAccount succReq = new G2BReqMsg_HFBank_OutOfAccount();
                		 succReq.setOrder_no(orderNo);
                		 succReq.setOrder_status(Constants.HF_OUT_OF_ACCOUNT_SUCCESS);
                		 if(Constants.PROPERTY_SYMBOL_ZAN.equals(lnPayOrders.get(0).getPartnerCode())) {//赞分期
                			 logger.info("赞分期标的出账回调重发订单号=["+orderNo+"]通知更新");
                			 loanPaymentService.outOfAccountResultAcceptZan(succReq, DepTargetEnum.DEP_TARGET_OPERATE_CHARGE_OFF);
                		 } else {//云贷/7贷==
                			 logger.info("标的出账回调重发订单号=["+orderNo+"]通知更新");
                			 depFixedLoanPaymentService.outOfAccountResultAccept(succReq, DepTargetEnum.DEP_TARGET_OPERATE_CHARGE_OFF, partnerEnum);
                        }
                	 }else{
                		 G2BReqMsg_HFBank_OutOfAccount failReq = new G2BReqMsg_HFBank_OutOfAccount();
                         failReq.setOrder_no(orderNo);
                         failReq.setOrder_status(Constants.HF_OUT_OF_ACCOUNT_FAIL);
                         failReq.setError_info("代付提现失败");
                         if(PartnerEnum.ZAN.getCode().equals(lnPayOrders.get(0).getPartnerCode())) {
                             loanPaymentService.outOfAccountResultAcceptZan(failReq, DepTargetEnum.DEP_TARGET_OPERATE_CHARGE_OFF);
                         } else {
                             depFixedLoanPaymentService.outOfAccountResultAccept(failReq, DepTargetEnum.DEP_TARGET_OPERATE_CHARGE_OFF, partnerEnum);
                         }
                	 }
                    return;
                }
            }

            // 查到了处理中的交易，为一个有效的通知
            BsPayOrders order = bsPayOrdersMapper.selectByPrimaryKey(orderList.get(0).getId());
            if (order.getStatus() != Constants.ORDER_STATUS_PAYING) {// 订单状态已经被修改，此次通知认为是重复通知，不做处理
                // 记录特殊交易流水表
                logger.warn("【用户提现通知异常】用户编号[" + order.getUserId() + "]恒丰重复通知");
                /*specialJnlService.warn4FailNoSMS(order.getAmount(), "【用户提现通知异常】用户编号["
                                + order.getUserId() + "]存管重复通知", order.getOrderNo(),
                        "用户提现通知异常");*/
                return;
            }

            String mobile = userService.findUserById(order.getUserId()).getMobile();

            BsServiceFeeExample bsServiceFeeExample = new BsServiceFeeExample();
            bsServiceFeeExample.createCriteria().andOrderNoEqualTo(order.getOrderNo())
                    .andFeeTypeEqualTo(Constants.FEE_TYPE_HF_FINANCE_WITHDRAW);
            List<BsServiceFee> bsServiceFeeList = bsServiceFeeMapper.selectByExample(bsServiceFeeExample);
            Double fee = 0d;
            if(CollectionUtils.isNotEmpty(bsServiceFeeList)) {
                fee = bsServiceFeeList.get(0).getDoneFee();
            }

            if(req.getHfUserId() != null) {
                // 提现通知入参platcust与实际提现用户不一致，校验通过。（本地轮询，hfUserId是null，调用无需校验）
                if(Constants.HF_NO_PLATCUST.equals(req.getHfUserId())) {
                    throw new PTMessageException(PTMessageEnum.TRANS_ERROR.getCode(), "提现通知入参platcust为空");
                }
                BsHfbankUserExtExample extExample = new BsHfbankUserExtExample();
                extExample.createCriteria().andUserIdEqualTo(order.getUserId()).andStatusEqualTo(Constants.HFBANK_USER_EXT_STATUS_OPEN).andHfUserIdEqualTo(req.getHfUserId());
                List<BsHfbankUserExt> exts = bsHfbankUserExtMapper.selectByExample(extExample);
                if(CollectionUtils.isEmpty(exts)) {
                    throw new PTMessageException(PTMessageEnum.TRANS_ERROR.getCode(), "提现通知入参platcust与实际提现用户不一致");
                }
            }

            if (OrderStatus.SUCCESS.getCode().equals(req.getOrderStatus())) {
                logger.info("通知提现成功：{}", JSONObject.fromObject(req));

                // 提现成功
                if(!isCompensatoryUserZAN(order.getUserId())) {
                    // 普通理财人提现
                    withdrawSuccess(req);
                } else {
                    // 代偿人提现
                    compensatoryWithdrawSuccess(req);
                }

                try {
                    /*
				 * smsService.sendMessage(mobile, "您有一笔金额为：￥" + order.getAmount() +
				 * "元的提现已完成，如有疑问请拨打400-806-1230。");
				 */
                    smsServiceClient.sendTemplateMessage(mobile,
                            TemplateKey.WITHDRAW_SUC, order.getAmount().toString(), fee.toString(), MoneyUtil.subtract(order.getAmount(), fee).toString());
                    // 微信模板消息
                    logger.info("提现成功：提现微信模板消息请求信息：{}", JSONObject.fromObject(order));
                    String bankCard = order.getBankCardNo();
                    sendWechatService.withdrawMgs(order.getUserId(), "", bankCard.substring(bankCard.length() - 4, bankCard.length()),
                            order.getAmount().toString(), "suc", null, fee.toString(), MoneyUtil.subtract(order.getAmount(), fee).toString());
                }catch (Exception e){
                    logger.error("提现成功通知到用户异常", e);
                }

            } else {// 提现失败
                logger.info("通知提现失败：{}", JSONObject.fromObject(req));

                if(!isCompensatoryUserZAN(order.getUserId())) {
                    // 普通理财人提现
                    withdrawFail(req);
                } else {
                    // 代偿人提现
                    compensatoryWithdrawFail(req);
                }
                try {
                /*
				 * smsService.sendMessage(mobile, "您有一笔金额为：￥" + order.getAmount() +
				 * "元的提现交易失败，失败原因："+ req.getErrorMsg() + "，如有疑问请拨打400-806-1230。");
				 */
                    if(StringUtil.isBlank(req.getErrorMsg())) {
                        req.setErrorMsg(PTMessageEnum.TRANS_ERROR.getMessage());
                    }
                    smsServiceClient.sendTemplateMessage(mobile,
                            TemplateKey.WITHDRAW_FALL, order.getAmount().toString(),
                            req.getErrorMsg());
                    // 微信模板消息
                    logger.info("提现失败：提现微信模板消息请求信息：{}",JSONObject.fromObject(order));
                    String bankCard = order.getBankCardNo();
                    sendWechatService.withdrawMgs(order.getUserId(), "", bankCard
                            .substring(bankCard.length() - 4, bankCard.length()), order.getAmount().toString(), "fall", req.getErrorMsg(), null, null);
                    // 客户余额提现失败,告警发送客服，技术
                    specialJnlService.warnAppoint4Fail(req.getAmount(), "["+ mobile +"]手机号客户，提现订单号["+ order.getOrderNo() +"]回调失败，请联系恒丰技术确认失败原因",
                            order.getOrderNo(),"客户余额提现", false, Constants.CUSTOMER_SERVICE_MOBILE, Constants.EMERGENCY_MOBILE);
                }catch (Exception e){
                    logger.error("提现失败通知到用户异常", e);
                }
            }
        } finally {
        }
    }

    @Override
    public Integer check(final ReqMsg_UserBalance_Withdraw req, final ResMsg_UserBalance_Withdraw res) {
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
        // 1.0. 恒丰日切时间，禁止充值提现操作
        BalanceWithdrawCheckVO check = new BalanceWithdrawCheckVO();
        check.setUserId(req.getUserId());
        this.withdrawCheck(check);

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

        final Integer[] checkId = new Integer[1];
        try {
            jsClientDaoSupport.lock(RedisLockEnum.LOCK_WITHDRAW_SERVICE.getKey());
            transactionTemplate.execute(new TransactionCallbackWithoutResult(){
                @Override
                public void doInTransactionWithoutResult(TransactionStatus status) {
                    // 0.检验用户是否存在
                    BsUser user = userService.findUserById(req.getUserId());
                    boolean isCompensatoryUserZAN = isCompensatoryUserZAN(req.getUserId());
                    if (user == null) {
                        throw new PTMessageException(PTMessageEnum.USER_NO_EXIT);
                    }
                    // 赞分期代偿人不允许前端提现
                    if(isCompensatoryUserZAN) {
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
                        logger.info("申请金额：{}，提现最小限额：{}，是否相等：{}", req.getAmount(), withdrawLimit, req.getAmount().compareTo(withdrawLimit));
                        if (req.getAmount().compareTo(withdrawLimit) < 0) {
                            throw new PTMessageException(PTMessageEnum.WITHDRAW_AMOUNT_SURPASS);
                        }
                    } else {
                        if(withdrawLimit.compareTo(withdraw_counter_fee) > 0) {
                            logger.info("申请金额：{}，提现最小限额：{}，是否相等：{}", req.getAmount(), withdrawLimit, req.getAmount().compareTo(withdrawLimit));
                            if (req.getAmount().compareTo(withdrawLimit) < 0) {
                                throw new PTMessageException(PTMessageEnum.WITHDRAW_AMOUNT_SURPASS);
                            }
                        } else {
                            if (req.getAmount().compareTo(withdraw_counter_fee) <= 0) {
                                throw new PTMessageException(PTMessageEnum.WITHDRAW_AMOUNT_SURPASS);
                            }
                        }
                    }
                    // 2、可用余额是否足够
                    BsSubAccount tempJsh = subAccountMapper.selectSingleSubActByUserAndType(req.getUserId(), Constants.PRODUCT_TYPE_DEP_JSH);
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
                    BsSubAccount jsh = new BsSubAccount();
                    BsAccountJnl bsAccountJnl = new BsAccountJnl();
                    // 普通用户提现初始化记账
                    userInitAccount(jsh, jshDB, bsAccountJnl, req, fee);

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
                    transDetail.setTransType(Constants.Trans_TYPE_DEP_WITHDRAW);
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

                    // 5.记录提现审核表bs_withdraw_check
                    BsWithdrawCheck bsWithdrawCheck = new BsWithdrawCheck();
                    bsWithdrawCheck.setAmount(req.getAmount());
                    bsWithdrawCheck.setUserId(req.getUserId());
                    bsWithdrawCheck.setStatus(Constants.WITHDRAW_TO_CHECK);
                    bsWithdrawCheck.setTransDetailId(transDetail.getId());
                    bsWithdrawCheck.setTerminalType(req.getTerminalType());
                    bsWithdrawCheck.setSubTransDetailId(feeTransDetail != null ? feeTransDetail.getId() : null);
                    bsWithdrawCheck.setClearingMark(req.getClearingMark());
                    bsWithdrawCheck.setCreateTime(now);
                    bsWithdrawCheckMapper.insertSelective(bsWithdrawCheck);

                    Integer withdrawCheckId = bsWithdrawCheck.getId();
                    checkId[0] = withdrawCheckId;
                    res.setWithdrawTime(now);
                    res.setOrderNo("");
                }
            });
        } finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_WITHDRAW_SERVICE.getKey());
        }
        return checkId[0];
    }

    /**
     * 根据用户编号判断是否是VIP理财人
     * @param userId
     * @return true 是VIP理财人，false 普通理财人
     */
    private boolean isSuperUser(Integer userId){
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
     * 普通用户提现申请初始化记账
     */
    private void userInitAccount(BsSubAccount jsh, BsSubAccount jshDB, BsAccountJnl bsAccountJnl, ReqMsg_UserBalance_Withdraw req, Double fee) {
        // 4、用户结算户，冻结金额增加，可用余额，可提现余额减少
        jsh.setId(jshDB.getId());
        jsh.setAvailableBalance(-req.getAmount());
        jsh.setCanWithdraw(-req.getAmount());
        jsh.setFreezeBalance(req.getAmount());
        jsh.setLastTransDate(new Date());
        subAccountService.modifyBalancesByIncrement(jsh);

        // 7、同时记录记账流水表
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
        bsAccountJnl.setAfterAvialableBalance1(MoneyUtil.subtract(jshDB.getAvailableBalance(), req.getAmount()).doubleValue());
        bsAccountJnl.setAfterFreezeBalance1(MoneyUtil.add(jshDB.getFreezeBalance(),req.getAmount()).doubleValue());
        bsAccountJnl.setCdFlag2(Constants.CD_FLAG_D);
        bsAccountJnl.setUserId2(req.getUserId());
        bsAccountJnl.setSubAccountCode2(jshDB.getCode());
        bsAccountJnl.setFee(fee);
        bsAccountJnl.setStatus(Constants.ACCOUNT_JNL_STATUS_SUCCESS);
        bsAccountJnlMapper.insertSelective(bsAccountJnl);
    }

    /**
     * 是否赞分期代偿人
     * @param userId
     * @return
     */
    @Override
    public boolean isCompensatoryUserZAN(Integer userId) {
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

    private int countHistoryWithdraw(Date startDate, Date endDate, ReqMsg_UserBalance_Withdraw req) {
        String startTime = DateUtil.formatDateTime(startDate, "yyyy-MM-dd HH:mm:ss");
        String endTime = DateUtil.formatDateTime(endDate, "yyyy-MM-dd HH:mm:ss");
        int count = bsUserTransDetailMapper.countWithdrawExcludeReturn(req.getUserId(), startTime, endTime);
        return count;
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
                updateOrder.setPayPath(PayPathEnum.find(req.getPayPath())==null?
                		null:PayPathEnum.find(req.getPayPath()).getPayPathVal());
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
                        .andFeeTypeEqualTo(Constants.FEE_TYPE_HF_FINANCE_WITHDRAW);
                List<BsServiceFee> bsServiceFeeList = bsServiceFeeMapper.selectByExample(bsServiceFeeExample);
                Double fee = 0d;
                if(CollectionUtils.isNotEmpty(bsServiceFeeList)) {
                    fee = bsServiceFeeList.get(0).getDoneFee();
                }
                // 用户实际到账金额
                Double returnBalance = MoneyUtil.subtract(order.getAmount(), fee).doubleValue();

                BsSubAccount jshDB = subAccountService
                        .findDEPJSHSubAccountByUserId(userId);
                BsSubAccount jsh = new BsSubAccount();
                jsh.setId(jshDB.getId());
                jsh.setBalance(-order.getAmount());
                jsh.setFreezeBalance(-order.getAmount());
                jsh.setLastTransDate(new Date());
                subAccountService.modifyBalancesByIncrement(jsh);

                // 6.用户记账流水表记录两条交易流水（实际提现金额和手续费）
                BsAccountJnl bsAccountJnl = new BsAccountJnl();
                bsAccountJnl.setTransTime(new Date());
                bsAccountJnl.setTransCode(Constants.USER_BALANCE_WITHDRAW);
                bsAccountJnl.setTransName("用户存管提现成功");// 提现成功
                bsAccountJnl.setTransAmount(returnBalance);
                bsAccountJnl.setSysTime(new Date());
                bsAccountJnl.setCdFlag1(Constants.CD_FLAG_C);
                bsAccountJnl.setUserId1(userId);
                bsAccountJnl.setAccountId1(jshDB.getAccountId());
                bsAccountJnl.setSubAccountId1(jshDB.getId());
                bsAccountJnl.setSubAccountCode1(jshDB.getCode());
                bsAccountJnl.setBeforeBalance1(jshDB.getBalance());
                bsAccountJnl.setBeforeAvialableBalance1(jshDB.getAvailableBalance());
                bsAccountJnl.setBeforeFreezeBalance1(jshDB.getFreezeBalance());
                bsAccountJnl.setAfterBalance1(MoneyUtil.subtract(jshDB.getBalance(), returnBalance).doubleValue());
                bsAccountJnl.setAfterAvialableBalance1(jshDB.getAvailableBalance());
                bsAccountJnl.setAfterFreezeBalance1(MoneyUtil.subtract(jshDB.getFreezeBalance(), returnBalance).doubleValue());
                bsAccountJnl.setCdFlag2(Constants.CD_FLAG_D);
                bsAccountJnl.setUserId2(userId);
                bsAccountJnl.setSubAccountCode2(jshDB.getCode());
                bsAccountJnl.setFee(fee);
                bsAccountJnl.setStatus(Constants.ACCOUNT_JNL_STATUS_SUCCESS);
                bsAccountJnlMapper.insertSelective(bsAccountJnl);

                // 7.系统存管用户余额户表中BGW_USER账户减少
                BsSysSubAccount sysUserAccount = bsSysSubAccountService.findSysSubAccount4Lock(Constants.SYS_ACCOUNT_BGW_USER);
                BsSysSubAccount readyUserUpdate = new BsSysSubAccount();
                readyUserUpdate.setId(sysUserAccount.getId());
                readyUserUpdate.setBalance(MoneyUtil.subtract(sysUserAccount.getBalance(), order.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                readyUserUpdate.setAvailableBalance(MoneyUtil.subtract(sysUserAccount.getAvailableBalance(), order.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                readyUserUpdate.setCanWithdraw(MoneyUtil.subtract(sysUserAccount.getCanWithdraw(), order.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                bsSysSubAccountMapper.updateByPrimaryKeySelective(readyUserUpdate);

                // 8.系统记账流水表记账
                // 实际提现到卡记账
                BsSysAccountJnl sysAccountJnl = new BsSysAccountJnl();
                sysAccountJnl.setTransTime(new Date());
                sysAccountJnl.setTransCode(Constants.SYS_USER_WITHDRAW);
                sysAccountJnl.setTransName("用户提现（实际到卡）");
                sysAccountJnl.setTransAmount(MoneyUtil.subtract(order.getAmount(), fee).doubleValue());
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
                bsSysAccountJnlMapper.insertSelective(sysAccountJnl);

                if(null != fee && fee > 0) {

                    // 9、存管 -- 系统手续费记账
                    BsSysSubAccount sysAccountFee = bsSysSubAccountService.findSysSubAccount4Lock(Constants.SYS_ACCOUNT_DEP_OTHER_FEE);
                    BsSysSubAccount sysAccountFeeTemp = new BsSysSubAccount();
                    sysAccountFeeTemp.setId(sysAccountFee.getId());
                    sysAccountFeeTemp.setAvailableBalance(MoneyUtil.add(sysAccountFee.getAvailableBalance(), fee).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    sysAccountFeeTemp.setBalance(MoneyUtil.add(sysAccountFee.getBalance(), fee).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    sysAccountFeeTemp.setCanWithdraw(MoneyUtil.add(sysAccountFee.getCanWithdraw(), fee).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    bsSysSubAccountMapper.updateByPrimaryKeySelective(sysAccountFeeTemp);

                    BsAccountJnl feeAccountJnl = new BsAccountJnl();
                    feeAccountJnl.setTransTime(new Date());
                    feeAccountJnl.setTransCode(Constants.USER_WITHDRAW_FEE);
                    feeAccountJnl.setTransName("存管提现手续费扣除");// 提现成功
                    feeAccountJnl.setTransAmount(fee);
                    feeAccountJnl.setSysTime(new Date());
                    feeAccountJnl.setCdFlag1(Constants.CD_FLAG_C);
                    feeAccountJnl.setUserId1(userId);
                    feeAccountJnl.setAccountId1(jshDB.getAccountId());
                    feeAccountJnl.setSubAccountId1(jshDB.getId());
                    feeAccountJnl.setSubAccountCode1(jshDB.getCode());
                    feeAccountJnl.setBeforeBalance1(MoneyUtil.subtract(jshDB.getBalance(), returnBalance).doubleValue());
                    feeAccountJnl.setBeforeAvialableBalance1(jshDB.getAvailableBalance());
                    feeAccountJnl.setBeforeFreezeBalance1(MoneyUtil.subtract(jshDB.getFreezeBalance(), returnBalance).doubleValue());
                    feeAccountJnl.setAfterBalance1(jshDB.getBalance() - order.getAmount());
                    feeAccountJnl.setAfterAvialableBalance1(jshDB.getAvailableBalance());
                    feeAccountJnl.setAfterFreezeBalance1(jshDB.getFreezeBalance() - order.getAmount());
                    feeAccountJnl.setCdFlag2(Constants.CD_FLAG_D);
                    feeAccountJnl.setUserId2(userId);
                    feeAccountJnl.setSubAccountCode2(jshDB.getCode());
                    feeAccountJnl.setFee(0d);
                    feeAccountJnl.setStatus(Constants.ACCOUNT_JNL_STATUS_SUCCESS);
                    bsAccountJnlMapper.insertSelective(feeAccountJnl);

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

    /**
     * 代偿人提现成功记账
     * @param req
     */
    private void compensatoryWithdrawSuccess(final DFResultInfo req) {
        transactionTemplate.execute(new TransactionCallbackWithoutResult(){
            @Override
            public void doInTransactionWithoutResult(TransactionStatus status) {
                logger.info("赞分期代偿人通知提现成功，请求参数：{}", JSONObject.fromObject(req));
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
                updateOrder.setReturnMsg("代偿人提现成功");
                updateOrder.setUpdateTime(new Date());
                updateOrder.setPayPath(PayPathEnum.find(req.getPayPath())==null?
                		null:PayPathEnum.find(req.getPayPath()).getPayPathVal());
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
                insertOrderJnl.setReturnMsg("代偿人提现成功");
                bsPayOrdersJnlMapper.insertSelective(insertOrderJnl);

                // 4.用户信息表余额减少(此处不能再改变，因为发送提现请求的时候已经减少，在此记录只做提醒作用)

                // 5.用户子账户表中冻结金额减少，余额减少。其他不动
                // 用户实际到账金额
                Double returnBalance = order.getAmount();

                BsSubAccount jshDB = subAccountService
                        .findDEPJSHSubAccountByUserId(userId);
                BsSubAccount jsh = new BsSubAccount();
                jsh.setId(jshDB.getId());
                jsh.setBalance(-order.getAmount());
                jsh.setFreezeBalance(-order.getAmount());
                jsh.setLastTransDate(new Date());
                subAccountService.modifyBalancesByIncrement(jsh);

                // 6.用户记账流水表记录两条交易流水（实际提现金额和手续费）
                BsAccountJnl bsAccountJnl = new BsAccountJnl();
                bsAccountJnl.setTransTime(new Date());
                bsAccountJnl.setTransCode(Constants.USER_BALANCE_WITHDRAW);
                bsAccountJnl.setTransName("代偿人存管提现成功");// 提现成功
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
                bsAccountJnl.setAfterBalance1(MoneyUtil.subtract(jshDB.getBalance(), returnBalance).doubleValue());
                bsAccountJnl.setAfterAvialableBalance1(jshDB.getAvailableBalance());
                bsAccountJnl.setAfterFreezeBalance1(MoneyUtil.subtract(jshDB.getFreezeBalance(), returnBalance).doubleValue());
                bsAccountJnl.setCdFlag2(Constants.CD_FLAG_D);
                bsAccountJnl.setUserId2(userId);
                bsAccountJnl.setSubAccountCode2(jshDB.getCode());
                bsAccountJnl.setFee(0d);
                bsAccountJnl.setStatus(Constants.ACCOUNT_JNL_STATUS_SUCCESS);
                bsAccountJnlMapper.insertSelective(bsAccountJnl);

                // 7.系统存管用户余额户表中BGW_USER账户减少
                BsSysSubAccount sysUserAccount = bsSysSubAccountService.findSysSubAccount4Lock(Constants.SYS_ACCOUNT_BGW_USER);
                BsSysSubAccount readyUserUpdate = new BsSysSubAccount();
                readyUserUpdate.setId(sysUserAccount.getId());
                readyUserUpdate.setBalance(MoneyUtil.subtract(sysUserAccount.getBalance(), order.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                readyUserUpdate.setFreezeBalance(MoneyUtil.subtract(sysUserAccount.getFreezeBalance(), order.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                bsSysSubAccountMapper.updateByPrimaryKeySelective(readyUserUpdate);

                // 8.系统记账流水表记账
                // 实际提现到卡记账
                BsSysAccountJnl sysAccountJnl = new BsSysAccountJnl();
                sysAccountJnl.setTransTime(new Date());
                sysAccountJnl.setTransCode(Constants.SYS_USER_WITHDRAW);
                sysAccountJnl.setTransName("代偿人提现（实际到卡）");
                sysAccountJnl.setTransAmount(order.getAmount());
                sysAccountJnl.setSysTime(new Date());
                sysAccountJnl.setCdFlag1(Constants.CD_FLAG_C);
                sysAccountJnl.setSubAccountCode1(sysUserAccount.getCode());
                sysAccountJnl.setBeforeBalance1(sysUserAccount.getBalance());
                sysAccountJnl.setAfterBalance1(readyUserUpdate.getBalance());
                sysAccountJnl.setBeforeAvialableBalance1(sysUserAccount.getAvailableBalance());
                sysAccountJnl.setAfterAvialableBalance1(sysUserAccount.getAvailableBalance());
                sysAccountJnl.setBeforeFreezeBalance1(sysUserAccount.getFreezeBalance());
                sysAccountJnl.setAfterFreezeBalance1(readyUserUpdate.getFreezeBalance());
                sysAccountJnl.setFee(0d);
                sysAccountJnl.setStatus(Constants.SYS_ACCOUNT_STATUS_SUCCESS);
                bsSysAccountJnlMapper.insertSelective(sysAccountJnl);

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
                bsServiceFeeExample.createCriteria().andOrderNoEqualTo(order.getOrderNo()).andFeeTypeEqualTo(Constants.FEE_TYPE_HF_FINANCE_WITHDRAW);
                List<BsServiceFee> bsServiceFeeList = bsServiceFeeMapper.selectByExample(bsServiceFeeExample);
                Double fee = 0d;
                if(CollectionUtils.isNotEmpty(bsServiceFeeList)) {
                    fee = bsServiceFeeList.get(0).getDoneFee();
                }
//                Double realBalance = MoneyUtil.add(order.getAmount(), fee).doubleValue();
                // 用户实际到账金额
                Double returnBalance = MoneyUtil.subtract(order.getAmount(), fee).doubleValue();

                // 1.用户信息表可用余额增加回来(还原)
                BsUser tempUser = new BsUser();
                tempUser.setCanWithdraw(order.getAmount());
                tempUser.setId(userId);
                bsUserMapper.updateBonusById(tempUser);

                // 2.更新订单表，状态为失败
                BsPayOrders updateOrder = new BsPayOrders();
                updateOrder.setId(order.getId());
                updateOrder.setStatus(Constants.ORDER_STATUS_FAIL);
                updateOrder.setReturnCode(req.getRetCode());
                updateOrder.setReturnMsg(req.getErrorMsg());
                updateOrder.setUpdateTime(new Date());
                updateOrder.setPayPath(PayPathEnum.find(req.getPayPath())==null?
                		null:PayPathEnum.find(req.getPayPath()).getPayPathVal());
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
                        .findDEPJSHSubAccountByUserId(userId);
                BsSubAccount jsh = new BsSubAccount();
                jsh.setId(jshDB.getId());
                jsh.setAvailableBalance(order.getAmount());
                jsh.setCanWithdraw(order.getAmount());
                jsh.setFreezeBalance(-order.getAmount());
                jsh.setLastTransDate(new Date());
                subAccountService.modifyBalancesByIncrement(jsh);

                // 5.添加一条解冻记账流水
                BsAccountJnl bsAccountJnl = new BsAccountJnl();
                bsAccountJnl.setTransTime(new Date());
                bsAccountJnl.setTransCode(Constants.USER_UNFREEZE);
                bsAccountJnl.setTransName("用户提现失败，解冻余额");// 解冻
                bsAccountJnl.setTransAmount(order.getAmount());
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
                        , order.getAmount()).doubleValue());
                bsAccountJnl.setAfterFreezeBalance1(MoneyUtil.subtract(jshDB.getFreezeBalance()
                        , order.getAmount()).doubleValue());
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
     * 代偿人失败记账
     * @param req
     */
    private void compensatoryWithdrawFail(final DFResultInfo req) {
        transactionTemplate.execute(new TransactionCallbackWithoutResult(){
            @Override
            public void doInTransactionWithoutResult(TransactionStatus status) {
                logger.info("通知代偿人提现失败，请求参数：{}", JSONObject.fromObject(req));
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
                bsServiceFeeExample.createCriteria().andOrderNoEqualTo(order.getOrderNo()).andFeeTypeEqualTo(Constants.FEE_TYPE_HF_FINANCE_WITHDRAW);
                List<BsServiceFee> bsServiceFeeList = bsServiceFeeMapper.selectByExample(bsServiceFeeExample);
                Double fee = 0d;
                if(CollectionUtils.isNotEmpty(bsServiceFeeList)) {
                    fee = bsServiceFeeList.get(0).getDoneFee();
                }
//                Double realBalance = MoneyUtil.add(order.getAmount(), fee).doubleValue();
                // 用户实际到账金额
                Double returnBalance = MoneyUtil.subtract(order.getAmount(), fee).doubleValue();

                // 1.用户信息表可用余额增加回来(还原)
                BsUser tempUser = new BsUser();
                tempUser.setCanWithdraw(order.getAmount());
                tempUser.setId(userId);
                bsUserMapper.updateBonusById(tempUser);

                // 2.更新订单表，状态为失败
                BsPayOrders updateOrder = new BsPayOrders();
                updateOrder.setId(order.getId());
                updateOrder.setStatus(Constants.ORDER_STATUS_FAIL);
                updateOrder.setReturnCode(req.getRetCode());
                updateOrder.setReturnMsg(req.getErrorMsg());
                updateOrder.setUpdateTime(new Date());
                updateOrder.setPayPath(PayPathEnum.find(req.getPayPath())==null?
                		null:PayPathEnum.find(req.getPayPath()).getPayPathVal());
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

                // 4.用户结算户，提现申请未修改DEP_JSH

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
     * 借款人提现成功不记账
     * @param req
     */
    private void loanerWithdrawSuccessNoCharge(final DFResultInfo req) {
        transactionTemplate.execute(new TransactionCallbackWithoutResult(){
            @Override
            public void doInTransactionWithoutResult(TransactionStatus status) {
                logger.info("借款人通知提现成功，请求参数：{}", JSONObject.fromObject(req));
                String orderNo = req.getMxOrderId();
                LnPayOrdersExample ordersExample = new LnPayOrdersExample();
                ordersExample.createCriteria().andOrderNoEqualTo(orderNo);
                List<LnPayOrders> orderList = payOrdersMapper.selectByExample(ordersExample);
                LnPayOrders order = payOrdersMapper.selectByPKForLock(orderList.get(0).getId());
                int userId = order.getLnUserId();

                // 1.更新订单信息表状态为成功
                LnPayOrders updateOrder = new LnPayOrders();
                updateOrder.setId(order.getId());
                updateOrder.setStatus(Constants.ORDER_STATUS_SUCCESS);
                updateOrder.setReturnCode(ConstantUtil.BSRESCODE_SUCCESS);
                updateOrder.setReturnMsg("借款人提现成功");
                updateOrder.setUpdateTime(new Date());
                updateOrder.setPayPath(PayPathEnum.find(req.getPayPath())==null?
                		null:PayPathEnum.find(req.getPayPath()).getPayPathVal());
                payOrdersMapper.updateByPrimaryKeySelective(updateOrder);

                // 2.订单明细信息表添加一条记录
                LnPayOrdersJnl insertOrderJnl = new LnPayOrdersJnl();
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
                insertOrderJnl.setReturnMsg("借款人提现成功");
                lnPayOrdersJnlMapper.insertSelective(insertOrderJnl);
            }
        });
    }
    
    /**
     * 借款人提现失败不记账
     * @param req
     */
    private void loanerWithdrawFailNoCharge(final DFResultInfo req) {
        transactionTemplate.execute(new TransactionCallbackWithoutResult(){
            @Override
            public void doInTransactionWithoutResult(TransactionStatus status) {
                logger.info("通知借款人提现失败，请求参数：{}", JSONObject.fromObject(req));
                String orderNo = req.getMxOrderId();
                LnPayOrdersExample ordersExample = new LnPayOrdersExample();
                ordersExample.createCriteria().andOrderNoEqualTo(orderNo);
                List<LnPayOrders> orderList = payOrdersMapper.selectByExample(ordersExample);
                LnPayOrders order = payOrdersMapper.selectByPKForLock(orderList.get(0).getId());
                int userId = order.getLnUserId();

                // 1.更新订单表，状态为失败
                LnPayOrders updateOrder = new LnPayOrders();
                updateOrder.setId(order.getId());
                updateOrder.setStatus(Constants.ORDER_STATUS_FAIL);
                updateOrder.setReturnCode(req.getRetCode());
                updateOrder.setReturnMsg(req.getErrorMsg());
                updateOrder.setUpdateTime(new Date());
                updateOrder.setPayPath(PayPathEnum.find(req.getPayPath())==null?
                		null:PayPathEnum.find(req.getPayPath()).getPayPathVal());
                payOrdersMapper.updateByPrimaryKeySelective(updateOrder);

                // 2.添加一条订单明细信息
                LnPayOrdersJnl insertOrderJnl = new LnPayOrdersJnl();
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
                lnPayOrdersJnlMapper.insertSelective(insertOrderJnl);
            }
        });
    }

    @Override
    public void withdrawCheck(BalanceWithdrawCheckVO req) {
        BsSysConfig blackUserConfig = bsSysConfigMapper.selectByPrimaryKey(Constants.WITHDRAW_BLACK_USER);
        logger.info("存管余额提现黑名单：{}", JSONObject.fromObject(blackUserConfig));
        if(blackUserConfig != null) {
            if(StringUtil.isNotBlank(blackUserConfig.getConfValue())) {
                String[] blackUserList = blackUserConfig.getConfValue().split(",");
                if(ArrayUtils.isNotEmpty(blackUserList)) {
                    for (String blackUser: blackUserList) {
                        if(blackUser.equals(String.valueOf(req.getUserId()))) {
                            throw new PTMessageException(PTMessageEnum.WITHDRAW_NOT_ALLOWED);
                        }
                    }
                }
            }
        }

        String riskStatus = assetsService.riskStatus(req.getUserId());
        if(Constants.IS_NO.equals(riskStatus)) {
            throw new PTMessageException(PTMessageEnum.NO_ASSESSMENT);
        } else if(Constants.IS_EXPIRE.equals(riskStatus)) {
            throw new PTMessageException(PTMessageEnum.ASSESSMENT_EXPIRE);
        }
    }
}

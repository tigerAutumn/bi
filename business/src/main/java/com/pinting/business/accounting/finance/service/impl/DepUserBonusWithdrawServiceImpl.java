package com.pinting.business.accounting.finance.service.impl;

import com.pinting.business.accounting.finance.model.DFResultInfo;
import com.pinting.business.accounting.finance.service.DepUserBalanceWithdrawService;
import com.pinting.business.accounting.finance.service.DepUserBonusWithdrawService;
import com.pinting.business.accounting.loan.enums.LoanStatus;
import com.pinting.business.accounting.loan.enums.VIPId4PartnerEnum;
import com.pinting.business.accounting.loan.service.LoanRelationshipService;
import com.pinting.business.accounting.loan.util.redisUtil;
import com.pinting.business.accounting.service.BsSysSubAccountService;
import com.pinting.business.accounting.service.PayOrdersService;
import com.pinting.business.dao.*;
import com.pinting.business.enums.BaoFooEnum;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.enums.RedisLockEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.*;
import com.pinting.business.model.vo.BalanceWithdrawCheckVO;
import com.pinting.business.model.vo.BonusWithdrawVO;
import com.pinting.business.model.vo.LoanNoticeVO;
import com.pinting.business.service.common.CommissionService;
import com.pinting.business.service.manage.BsSpecialJnlService;
import com.pinting.business.service.manage.BsSysConfigService;
import com.pinting.business.service.site.*;
import com.pinting.business.util.Constants;
import com.pinting.business.util.DateUtil;
import com.pinting.business.util.Util;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.core.util.encrypt.MD5Util;
import com.pinting.gateway.hessian.message.baofoo.B2GReqMsg_BaoFooQuickPay_Pay4Trans;
import com.pinting.gateway.hessian.message.baofoo.B2GResMsg_BaoFooQuickPay_Pay4Trans;
import com.pinting.gateway.hessian.message.pay19.enums.OrderStatus;
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
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.util.*;

/**
 * Author:      cyb
 * Date:        2017/4/10
 * Description: 存管体系奖励金提现
 */
@Service
public class DepUserBonusWithdrawServiceImpl implements DepUserBonusWithdrawService {

    private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);

    private Logger logger = LoggerFactory.getLogger(DepUserBonusWithdrawServiceImpl.class);

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
    private BaoFooTransportService baoFooTransportService;
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
    private BsSpecialJnlMapper bsSpecialJnlMapper;
    @Autowired
    private SMSService smsService;
    @Autowired
    private BonusService bonusService;
    @Autowired
    private BsSpecialJnlService bsSpecialJnlService;
    @Autowired
    private BsHfbankUserExtMapper bsHfbankUserExtMapper;
    @Autowired
    private DepUserBalanceWithdrawService depUserBalanceWithdrawService;
    @Autowired
    private AssetsService assetsService;

    @Override
    public BonusWithdrawVO userBonusWithdraw(final Integer userId, final Double bonusAmount, String payPassword, Integer terminalType) {
        // 0. 校验
        // 0.1. 用户是否存在
        // 0.2. 校验用户支付密码是否正确
        // 0.3. 是否有回款卡
        // 0.4. 最小提现限额校验
        // 0.5. JLJ可用余额是否足够
        // 0.5.1. 不足够-bs_special_jnl中新增一条记录
        // 0.5.2. 不足够 记录bs_user_trans_detail(状态为失败）
        // 0.6. 判断系统子账户JSH的Balance金额是否足够
        // 0.6.1. 不足够-向管理运营者发布告警短信，提醒运营管理者及时充值到币港湾账户中
        // 1. 更新奖励金户（奖励金冻结。可用余额、可提现余额-；冻结金额+）。
        // 2. 新增奖励金提现订单
        // 3. 新增奖励金提现订单流水
        // 4. 修改bs_user减去current_bonus的值
        // 5. 新增用户交易明细表，状态为处理中状态
        // 5. 发起代付请求
        // 6. 代付成功
        // 6.1. bs_daily_bonus插入一条奖励金提现的奖励记录
        // 6.2. 用户子账户表，用户奖励金JLJ冻结金额-，余额-
        // 6.3. 系统子账户-结算户JSH金额扣除
        // 6.4. 记录系统往来记账流水表
        // 6.5. 记录bs_user_trans_detail(状态为成功)
        // 6.6. 修改订单状态为成功
        // 6.7. 新增订单流水表
        // 7. 代付处理中
        // 8. 代付失败
        // 8.1. 更新奖励金户还原（奖励金冻结解除。可用余额、可提现余额+；冻结金额-）。
        // 8.2. 更新奖励金提现订单状态为失败
        // 8.3. 新增奖励金提现订单流水
        // 8.4. 用户信息表当前奖励金增加回来(还原)
        // 8.5. 添加一条解冻记账流水
        // 8.6. 更新用户交易明细表，状态为失败
        // 8.7. 特殊交易流水表中，添加一条记录并通知用户

        try {
            jsClientDaoSupport.lock(RedisLockEnum.LOCK_BONUS2JSH.getKey());
            logger.info("用户 {} 进行奖励金提现,金额：{}，", userId, bonusAmount);
            // 0. 校验
            Map<String, Object> preWithdrawMap = this.checkBonusWithdraw(userId, bonusAmount, payPassword);
            final BsUser user = (BsUser) preWithdrawMap.get("user");
            BsBankCard receiveCard = (BsBankCard) preWithdrawMap.get("receiveCard");
            // 0.5. JLJ可用余额是否足够
            BsSubAccount jljSubAccount = subAccountMapper.selectJLJSubAccountByUserId(userId);
            Double jljCanWithdraw = jljSubAccount.getCanWithdraw() == null ? 0d : jljSubAccount.getCanWithdraw();
            if(bonusAmount.compareTo(jljCanWithdraw) > 0) {
                throw new PTMessageException(PTMessageEnum.USER_JLJ_NOT_ENOUGH);
            }
            // 0.6. 判断系统子账户JSH的Balance金额是否足够
            final BsSysSubAccount jshAccount = bsSysSubAccountMapper.selectByCode(Constants.SYS_ACCOUNT_JSH);
            if (jshAccount.getBalance() < bonusAmount) {
                // 0.6.1. 不足够-向管理运营者发布告警短信，提醒运营管理者及时充值到币港湾账户中
                smsService.sendSysManagerMobiles("奖励金提现失败充值提醒", false);
                logger.info("【奖励金提现】：系统子账户JSH可用资金不足，对应操作结束");
                throw new PTMessageException(PTMessageEnum.SYS_JSH_BALANCE_NOT_ENOUGH);
            }
//            if(isSuperUser(userId)) {
//                throw new PTMessageException(PTMessageEnum.WITHDRAW_NOT_ALLOWED);
//            }

            // 1. 更新奖励金户 奖励金冻结。
            BsSubAccount tempJLJ = subAccountMapper.selectSingleSubActByUserAndType(userId, Constants.PRODUCT_TYPE_JLJ);
            final BsSubAccount jljLock = subAccountMapper.selectSubAccountByIdForLock(tempJLJ.getId());
            final BsSubAccount jlj = new BsSubAccount();
            jlj.setId(jljLock.getId());
            jlj.setAvailableBalance(-bonusAmount);
            jlj.setCanWithdraw(-bonusAmount);
            jlj.setFreezeBalance(bonusAmount);
            jlj.setLastTransDate(new Date());
            subAccountService.modifyBalancesByIncrement(jlj);

            // 2. 新增奖励金提现订单
            Date now = new Date();
            final BsPayOrders order = new BsPayOrders();
            String orderNo = Util.generateOrderNo4BaoFoo(8);
            order.setTerminalType(terminalType);
            order.setAmount(bonusAmount);
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
            order.setTransType(Constants.TRANS_BONUS_WITHDRAW);
            order.setUpdateTime(now);
            order.setUserId(userId);
            order.setUserName(receiveCard.getCardOwner());
            bsPayOrdersMapper.insertSelective(order);

            // 3. 新增奖励金提现订单流水
            BsPayOrdersJnl jnl = new BsPayOrdersJnl();
            jnl.setCreateTime(new Date());
            jnl.setOrderId(order.getId());
            jnl.setOrderNo(order.getOrderNo());
            jnl.setSubAccountCode(null);
            jnl.setSubAccountId(null);
            jnl.setSysTime(new Date());
            jnl.setTransAmount(order.getAmount());
            jnl.setTransCode(Constants.ORDER_TRANS_CODE_INIT);
            jnl.setUserId(userId);
            jnl.setReturnCode(null);
            jnl.setReturnMsg(null);
            bsPayOrdersJnlMapper.insertSelective(jnl);

            BsAccountJnl bsAccountJnl = new BsAccountJnl();
            bsAccountJnl.setTransTime(new Date());
            bsAccountJnl.setTransCode(Constants.USER_BONUS_WITHDRAW);
            bsAccountJnl.setTransName("用户提现，冻结余额");// 冻结
            bsAccountJnl.setTransAmount(bonusAmount);
            bsAccountJnl.setSysTime(new Date());
            bsAccountJnl.setCdFlag1(Constants.CD_FLAG_C);
            bsAccountJnl.setUserId1(userId);
            bsAccountJnl.setAccountId1(jljLock.getAccountId());
            bsAccountJnl.setSubAccountId1(jljLock.getId());
            bsAccountJnl.setSubAccountCode1(jljLock.getCode());
            bsAccountJnl.setBeforeBalance1(jljLock.getBalance());
            bsAccountJnl.setBeforeAvialableBalance1(jljLock.getAvailableBalance());
            bsAccountJnl.setBeforeFreezeBalance1(jljLock.getFreezeBalance());
            bsAccountJnl.setAfterBalance1(jljLock.getBalance());
            bsAccountJnl.setAfterAvialableBalance1(MoneyUtil.subtract(jljLock.getAvailableBalance(), bonusAmount).doubleValue());
            bsAccountJnl.setAfterFreezeBalance1(MoneyUtil.add(jljLock.getFreezeBalance(), bonusAmount).doubleValue());
            bsAccountJnl.setCdFlag2(Constants.CD_FLAG_D);
            bsAccountJnl.setUserId2(userId);
            bsAccountJnl.setSubAccountCode2(jljLock.getCode());
            bsAccountJnl.setFee(0d);
            bsAccountJnl.setStatus(Constants.ACCOUNT_JNL_STATUS_SUCCESS);
            bsAccountJnlMapper.insertSelective(bsAccountJnl);

            // 4. 修改bs_user减去current_bonus的值
            logger.info("【奖励金提现】：修改bs_user减去current_bonus的值");
            BsUser bsUser = new BsUser();
            bsUser.setId(userId);
            bsUser.setCurrentBonus(bonusAmount * -1);
            bsUserMapper.updateBonusById(bsUser);

            // 5. 新增用户交易明细表，状态为处理中状态
            BsUserTransDetail initTransDetail = new BsUserTransDetail();
            initTransDetail.setOrderNo(order.getOrderNo());
            initTransDetail.setAmount(-bonusAmount);
            initTransDetail.setUserId(userId);
            initTransDetail.setTransStatus(Constants.Trans_STATUS_DEAL);
            initTransDetail.setReturnCode(null);
            initTransDetail.setReturnMsg(null);
            initTransDetail.setCreateTime(new Date());
            initTransDetail.setUpdateTime(new Date());
            initTransDetail.setTransType(Constants.Trans_TYPE_USER_BONUS_WITHDRAW);
            bsUserTransDetailMapper.insertSelective(initTransDetail);

            // 5. 发起代付请求
            B2GResMsg_BaoFooQuickPay_Pay4Trans resMsg;
            B2GReqMsg_BaoFooQuickPay_Pay4Trans b2gReq = new B2GReqMsg_BaoFooQuickPay_Pay4Trans();
            b2gReq.setTo_acc_name(order.getUserName());
            b2gReq.setTo_acc_no(order.getBankCardNo());
            b2gReq.setTo_bank_name(order.getBankName());
            b2gReq.setTrans_money(MoneyUtil.multiply(order.getAmount(), 1).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            b2gReq.setTrans_no(order.getOrderNo());
            b2gReq.setTrans_card_id(receiveCard.getIdCard());
            b2gReq.setTrans_mobile(receiveCard.getMobile());
            b2gReq.setTrans_summary("奖励金提现");
            logger.info("奖励金提现发起代付请求：{}", JSONObject.fromObject(b2gReq));
            try {
                resMsg = baoFooTransportService.pay4Trans(b2gReq);
            } catch (Exception e) {
                logger.error("奖励金提现申请通讯失败：{}", e.getMessage());
                resMsg = new B2GResMsg_BaoFooQuickPay_Pay4Trans();
                resMsg.setResCode(ConstantUtil.BSRESCODE_FAIL);
                resMsg.setResMsg("通讯失败，置为失败");
            }
            if (ConstantUtil.BSRESCODE_SUCCESS.equals(resMsg.getResCode())) {
                // 修改订单状态为成功
                payOrdersService.modifyOrderStatus4Safe(order.getId(), Constants.ORDER_STATUS_PAYING,
                        jlj.getId(), null, null);

                DFResultInfo resultInfo = new DFResultInfo();
                resultInfo.setAmount(bonusAmount);
                resultInfo.setFinishTime(new Date());
                resultInfo.setMxOrderId(order.getOrderNo());
                resultInfo.setOrderStatus(OrderStatus.SUCCESS.getCode());
                resultInfo.setRetCode(resMsg.getRes_Code());
                resultInfo.setErrorMsg(resMsg.getResMsg());
                this.notifyBonusWithdraw(resultInfo);
            } else if(Constants.GATEWAY_PAYING_RES_CODE.equals(resMsg.getResCode())) {
                // 7. 代付处理中
                // 7.1. bs_special_jnl中新增一条记录 奖励金转提现代付处理中
                logger.info("【奖励金提现处理中】");
                final B2GResMsg_BaoFooQuickPay_Pay4Trans finalResMsg1 = resMsg;
                transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                    @Override
                    protected void doInTransactionWithoutResult(TransactionStatus status) {

                        // 7.2. 修改订单状态为处理中
                        payOrdersService.modifyOrderStatus4Safe(order.getId(), Constants.ORDER_STATUS_PAYING,
                                jlj.getId(), null, null);

                        // 7.3. 新增订单流水表
                        BsPayOrdersJnl orderJnl = new BsPayOrdersJnl();
                        orderJnl.setOrderId(order.getId());
                        orderJnl.setOrderNo(order.getOrderNo());
                        orderJnl.setTransCode(Constants.ORDER_TRANS_CODE_PROCESS);
                        orderJnl.setChannelTransType(order.getChannelTransType());
                        orderJnl.setTransAmount(order.getAmount());
                        orderJnl.setSysTime(new Date());
                        orderJnl.setChannelTime(order.getCreateTime());
                        orderJnl.setChannelJnlNo(finalResMsg1.getTrans_orderid());
                        orderJnl.setSubAccountId(order.getSubAccountId());
                        orderJnl.setSubAccountCode(jlj.getCode());
                        orderJnl.setUserId(order.getUserId());
                        orderJnl.setReturnCode(finalResMsg1.getResCode());
                        orderJnl.setReturnMsg(finalResMsg1.getResMsg());
                        orderJnl.setCreateTime(new Date());
                        bsPayOrdersJnlMapper.insertSelective(orderJnl);

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
                });
            } else {
                // 8. 代付失败
                // 8.1. 更新奖励金户还原（奖励金冻结解除。可用余额、可提现余额+；冻结金额-）。
                // 8.2. 更新奖励金提现订单状态为失败
                // 8.3. 新增奖励金提现订单流水
                // 8.4. 用户信息表当前奖励金增加回来(还原)
                // 8.5. 添加一条解冻记账流水
                // 8.6. 更新用户交易明细表，状态为失败
                // 8.7. 特殊交易流水表中，添加一条记录并通知用户
                logger.info("奖励金提现宝付代付下单申请失败：{}", JSONObject.fromObject(resMsg));
                final String errorMsg = resMsg.getResMsg();
                final String thirdReturnCode = Util.getThirdReturnCode(resMsg);
                transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                    @Override
                    protected void doInTransactionWithoutResult(TransactionStatus status) {
                        BsPayOrders lockOrder = bsPayOrdersMapper.selectByPKForLock(order.getId());

                        // 8.1. 更新奖励金户还原（奖励金冻结解除。可用余额、可提现余额+；冻结金额-）。
                        BsSubAccount jljDB = subAccountService.findJLJSubAccountByUserId(userId);
                        BsSubAccount jljAccount = new BsSubAccount();
                        jljAccount.setId(jljDB.getId());
                        jljAccount.setAvailableBalance(bonusAmount);
                        jljAccount.setCanWithdraw(bonusAmount);
                        jljAccount.setFreezeBalance(-bonusAmount);
                        jljAccount.setLastTransDate(new Date());
                        subAccountService.modifyBalancesByIncrement(jljAccount);

                        // 8.2. 更新奖励金提现订单状态为失败
                        payOrdersService.modifyOrderStatus4Safe(order.getId(), Constants.ORDER_STATUS_FAIL,
                                null, thirdReturnCode, errorMsg);

                        // 8.3. 新增奖励金提现订单流水
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
                        insertOrderJnl.setReturnCode(thirdReturnCode);
                        insertOrderJnl.setReturnMsg(errorMsg);
                        bsPayOrdersJnlMapper.insertSelective(insertOrderJnl);

                        // 8.4. 用户信息表当前奖励金增加回来(还原)
                        BsUser tempUser = new BsUser();
                        tempUser.setCurrentBonus(bonusAmount);
                        tempUser.setId(userId);
                        bsUserMapper.updateBonusById(tempUser);

                        // 8.5. 添加一条解冻记账流水
                        BsAccountJnl bsAccountJnl = new BsAccountJnl();
                        bsAccountJnl.setTransTime(new Date());
                        bsAccountJnl.setTransCode(Constants.USER_UNFREEZE);
                        bsAccountJnl.setTransName("用户奖励金提现发送失败，解冻余额");// 解冻
                        bsAccountJnl.setTransAmount(bonusAmount);
                        bsAccountJnl.setSysTime(new Date());
                        bsAccountJnl.setCdFlag1(Constants.CD_FLAG_D);
                        bsAccountJnl.setUserId1(userId);
                        bsAccountJnl.setAccountId1(jljDB.getAccountId());
                        bsAccountJnl.setSubAccountId1(jljDB.getId());
                        bsAccountJnl.setSubAccountCode1(jljDB.getCode());
                        bsAccountJnl.setBeforeBalance1(jljDB.getBalance());
                        bsAccountJnl.setBeforeAvialableBalance1(jljDB.getAvailableBalance());
                        bsAccountJnl.setBeforeFreezeBalance1(jljDB.getFreezeBalance());
                        bsAccountJnl.setAfterBalance1(jljDB.getBalance());
                        bsAccountJnl.setAfterAvialableBalance1(MoneyUtil.add(jljDB.getAvailableBalance(), bonusAmount).doubleValue());
                        bsAccountJnl.setAfterFreezeBalance1(MoneyUtil.subtract(jljDB.getFreezeBalance(), bonusAmount).doubleValue());
                        bsAccountJnl.setCdFlag2(Constants.CD_FLAG_C);
                        bsAccountJnl.setUserId2(userId);
                        bsAccountJnl.setSubAccountCode2(jljDB.getCode());
                        bsAccountJnl.setStatus(Constants.ACCOUNT_JNL_STATUS_FAIL);
                        bsAccountJnlMapper.insertSelective(bsAccountJnl);

                        // 8.6. 更新用户交易明细表，状态为失败
                        BsUserTransDetailExample example = new BsUserTransDetailExample();
                        example.createCriteria().andOrderNoEqualTo(order.getOrderNo());
                        BsUserTransDetail tmpTransDetail = new BsUserTransDetail();
                        tmpTransDetail.setTransStatus(Constants.Trans_STATUS_FAIL);
                        tmpTransDetail.setReturnCode(thirdReturnCode);
                        tmpTransDetail.setReturnMsg(errorMsg);
                        tmpTransDetail.setUpdateTime(new Date());
                        bsUserTransDetailMapper.updateByExampleSelective(tmpTransDetail, example);
                    }
                });

                // 8.7. 特殊交易流水表中，添加一条记录并通知用户
                try {
                    specialJnlService.warn4Fail(bonusAmount, "【客户奖励金提现】用户编号[" + userId + "]回款产生异常", order.getOrderNo(), "客户奖励金提现", false);
                    smsServiceClient.sendTemplateMessage(user.getMobile(), TemplateKey.BONUS_WITHDRAW_FALL, String.valueOf(bonusAmount), "通讯失败请重试");
                    // 微信模板消息
                    String bankCard = receiveCard.getCardNo();
                    sendWechatService.bonusWithdrawMgs(user.getId(), "", bankCard.substring(bankCard.length() - 4, bankCard.length()), String.valueOf(bonusAmount), "fall", "通讯失败请重试");
                } catch (Exception e) {
                    logger.error("用户奖励金提现失败通知到用户异常", e.getMessage());
                }
            }
            
            BonusWithdrawVO  bonusWithdrawVO = new BonusWithdrawVO();
            bonusWithdrawVO.setOrderNo(orderNo);
            bonusWithdrawVO.setTime(DateUtil.format(order.getCreateTime(), "YYYY年MM月dd日 HH:mm:ss"));
            return bonusWithdrawVO;
            
            
        } finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_BONUS2JSH.getKey());
        }
    }

    @Override
    public void notifyBonusWithdraw(DFResultInfo req) {
        try {
            logger.info("奖励金提现通知开始：{}", JSONObject.fromObject(req));
            // 检查是否是否重复通知（根据订单信息是否是处理中，如果不是，则已经处理过，拒绝再次处理）
            String orderNo = req.getMxOrderId();
            BsPayOrdersExample example = new BsPayOrdersExample();
            example.createCriteria().andOrderNoEqualTo(orderNo).andStatusEqualTo(Constants.ORDER_STATUS_PAYING);
            List<BsPayOrders> orderList = bsPayOrdersMapper.selectByExample(example);

            if (orderList == null || orderList.isEmpty()) {// 没有查到处理中的交易
                // 继续去掉状态查询，如果是已完成的订单，则为重复通知
                example = new BsPayOrdersExample();
                example.createCriteria().andOrderNoEqualTo(orderNo);
                orderList = bsPayOrdersMapper.selectByExample(example);
                if (orderList != null && !orderList.isEmpty()) {
                    // 记录特殊交易流水表
                    BsPayOrders order = orderList.get(0);
                    logger.warn("【用户奖励金提现通知异常】用户编号[" + order.getUserId() + "]宝付重复通知");
                    /*specialJnlService.warn4FailNoSMS(order.getAmount(),
                            "【用户提现通知异常】用户编号[" + order.getUserId() + "]宝付重复通知",
                            order.getOrderNo(), "用户提现通知异常");*/
                    return;
                } else {// 没有发生过这个交易，记录特殊流水表
                    specialJnlService.warn4FailNoSMS(-1.0,
                            "【用户奖励金提现通知异常】用户编号[未知]宝付通知了一个未知的订单号：" + orderNo, orderNo,
                            "用户奖励金提现通知异常");
                    return;
                }
            }

            // 查到了处理中的交易，为一个有效的通知
            BsPayOrders order = orderList.get(0);
            if (order.getStatus() != Constants.ORDER_STATUS_PAYING) {// 订单状态已经被修改，此次通知认为是重复通知，不做处理
                // 记录特殊交易流水表
                logger.warn("【用户奖励金提现通知异常】用户编号[" + order.getUserId() + "]宝付重复通知");
                /*specialJnlService.warn4FailNoSMS(order.getAmount(), "【用户提现通知异常】用户编号["
                                + order.getUserId() + "]宝付重复通知", order.getOrderNo(),
                        "用户提现通知异常");*/
                return;
            }

            String mobile = userService.findUserById(order.getUserId()).getMobile();

            Double totalAmount = MoneyUtil.defaultRound(order.getAmount()).doubleValue();

            if (OrderStatus.SUCCESS.getCode().equals(req.getOrderStatus())) {
                logger.info("通知奖励金提现成功：{}", JSONObject.fromObject(req));
                // 奖励金提现成功
                withdrawSuccess(req);
                try {
                    /*
				 * smsService.sendMessage(mobile, "您有一笔金额为：￥" + order.getAmount() +
				 * "元的提现已完成，如有疑问请拨打400-806-1230。");
				 */
                    smsServiceClient.sendTemplateMessage(mobile, TemplateKey.BONUS_WITHDRAW_SUC, totalAmount.toString(), order.getAmount().toString());
                    // 微信模板消息
                    logger.info("奖励金提现成功：提现微信模板消息请求信息：{}", JSONObject.fromObject(order));
                    String bankCard = order.getBankCardNo();
                    sendWechatService.bonusWithdrawMgs(order.getUserId(), "", bankCard.substring(bankCard.length() - 4, bankCard.length()),totalAmount.toString(), "suc", order.getAmount().toString());
                }catch (Exception e){
                    logger.error("奖励金提现成功通知到用户异常", e);
                }

            } else {// 奖励金提现失败
                logger.info("通知提现失败：{}", JSONObject.fromObject(req));
                withdrawFail(req);
                try {
                /*
				 * smsService.sendMessage(mobile, "您有一笔金额为：￥" + order.getAmount() +
				 * "元的提现交易失败，失败原因："+ req.getErrorMsg() + "，如有疑问请拨打400-806-1230。");
				 */
                    smsServiceClient.sendTemplateMessage(mobile, TemplateKey.BONUS_WITHDRAW_FALL, totalAmount.toString(), req.getErrorMsg());
                    logger.info("提现失败：提现微信模板消息请求信息：{}",JSONObject.fromObject(order));
                    String bankCard = order.getBankCardNo();
                    // 微信模板消息
                    sendWechatService.bonusWithdrawMgs(order.getUserId(), "", bankCard
                            .substring(bankCard.length() - 4, bankCard.length()), totalAmount.toString(), "fall", req.getErrorMsg());
                }catch (Exception e){
                    logger.error("提现失败通知到用户异常", e);
                }
            }
        } finally {
        }
    }


    private void withdrawSuccess(final DFResultInfo req) {
        transactionTemplate.execute(new TransactionCallbackWithoutResult(){
            @Override
            public void doInTransactionWithoutResult(TransactionStatus status) {
                logger.info("通知奖励金提现成功，请求参数：{}", JSONObject.fromObject(req));
                String orderNo = req.getMxOrderId();
                BsPayOrdersExample ordersExample = new BsPayOrdersExample();
                ordersExample.createCriteria().andOrderNoEqualTo(orderNo);
                List<BsPayOrders> orderList = bsPayOrdersMapper.selectByExample(ordersExample);
                BsPayOrders order = bsPayOrdersMapper.selectByPKForLock(orderList.get(0).getId());
                int userId = order.getUserId();

                BsSubAccount jljDB = subAccountService.findJLJSubAccountByUserId(userId);

                // 6.1. bs_daily_bonus插入一条奖励金提现的奖励记录
                BsDailyBonus bsDailyBonus = new BsDailyBonus();
                bsDailyBonus.setBeRecommendUserId(userId);
                bsDailyBonus.setBonus(MoneyUtil.multiply(order.getAmount(), -1).doubleValue());
                bsDailyBonus.setTime(new Date());
                bsDailyBonus.setUserId(userId);
                bsDailyBonus.setType(Constants.Daily_BONUS_TYPE_WITHDRAW);
                bonusService.addDailyBonus(bsDailyBonus);

                // 6.2. 用户子账户表，用户奖励金JLJ冻结金额-，余额-
                BsSubAccount JLJ = new BsSubAccount();
                JLJ.setId(jljDB.getId());
                JLJ.setBalance(MoneyUtil.multiply(order.getAmount(), -1).doubleValue());
                JLJ.setFreezeBalance(MoneyUtil.multiply(order.getAmount(), -1).doubleValue());
                JLJ.setLastTransDate(new Date());
                subAccountMapper.updateBalancesByIncrement(JLJ);

                // 6.2.0 用户记账流水表记录两条交易流水（实际提现金额和手续费）
                BsAccountJnl bsAccountJnl = new BsAccountJnl();
                bsAccountJnl.setTransTime(new Date());
                bsAccountJnl.setTransCode(Constants.USER_BONUS_WITHDRAW);
                bsAccountJnl.setTransName("用户奖励金提现成功");// 提现成功
                bsAccountJnl.setTransAmount(order.getAmount());
                bsAccountJnl.setSysTime(new Date());
                bsAccountJnl.setCdFlag1(Constants.CD_FLAG_C);
                bsAccountJnl.setUserId1(userId);
                bsAccountJnl.setAccountId1(jljDB.getAccountId());
                bsAccountJnl.setSubAccountId1(jljDB.getId());
                bsAccountJnl.setSubAccountCode1(jljDB.getCode());
                bsAccountJnl.setBeforeBalance1(jljDB.getBalance());
                bsAccountJnl.setBeforeAvialableBalance1(jljDB.getAvailableBalance());
                bsAccountJnl.setBeforeFreezeBalance1(jljDB.getFreezeBalance());
                bsAccountJnl.setAfterBalance1(MoneyUtil.subtract(jljDB.getBalance(), order.getAmount()).doubleValue());
                bsAccountJnl.setAfterAvialableBalance1(jljDB.getAvailableBalance());
                bsAccountJnl.setAfterFreezeBalance1(MoneyUtil.subtract(jljDB.getFreezeBalance(),order.getAmount()).doubleValue());
                bsAccountJnl.setCdFlag2(Constants.CD_FLAG_D);
                bsAccountJnl.setUserId2(userId);
                bsAccountJnl.setSubAccountCode2(jljDB.getCode());
                bsAccountJnl.setFee(0d);
                bsAccountJnl.setStatus(Constants.ACCOUNT_JNL_STATUS_SUCCESS);
                bsAccountJnlMapper.insertSelective(bsAccountJnl);

                // 6.3. 系统子账户-结算户JSH金额扣除
                BsSysSubAccount jshSysAccount = bsSysSubAccountMapper.selectByCode(Constants.SYS_ACCOUNT_JSH);
                BsSysSubAccount jshAccount = new BsSysSubAccount();
                jshAccount.setId(jshSysAccount.getId());
                jshAccount.setBalance(MoneyUtil.multiply(order.getAmount(), -1).doubleValue());
                jshAccount.setAvailableBalance(MoneyUtil.multiply(order.getAmount(), -1).doubleValue());
                jshAccount.setCanWithdraw(MoneyUtil.multiply(order.getAmount(), -1).doubleValue());
                jshAccount.setLastTransDate(new Date());
                logger.info("【奖励金提现】：系统子账户-结算户JSH，余额修改");
                bsSysSubAccountMapper.updateById(jshAccount);

                // 6.4. 记录系统往来记账流水表
                BsSysAccountJnl sysJnl = new BsSysAccountJnl();
                sysJnl.setTransTime(new Date());
                sysJnl.setTransCode(Constants.SYS_USER_BONUS_WITHDRAW);
                sysJnl.setTransAmount(order.getAmount());
                sysJnl.setStatus(Constants.ACCOUNT_JNL_STATUS_SUCCESS);
                sysJnl.setTransName("奖励金提现");
                sysJnl.setSysTime(new Date());
                sysJnl.setCdFlag1(Constants.CD_FLAG_C); // 对JLJ而言是转出，为贷
                sysJnl.setSubAccountCode1(jshSysAccount.getCode());
                sysJnl.setBeforeBalance1(jshSysAccount.getBalance());
                sysJnl.setBeforeAvialableBalance1(jshSysAccount.getAvailableBalance());
                sysJnl.setBeforeFreezeBalance1(jshSysAccount.getFreezeBalance());
                sysJnl.setAfterBalance1(MoneyUtil.subtract(jshSysAccount.getBalance(), order.getAmount()).doubleValue());
                sysJnl.setAfterAvialableBalance1(MoneyUtil.subtract(jshSysAccount.getAvailableBalance(), order.getAmount()).doubleValue());
                sysJnl.setAfterFreezeBalance1(jshSysAccount.getFreezeBalance());
//                sysJnl.setCdFlag2(Constants.CD_FLAG_D); // 对JSH而言是转入，为借
//                sysJnl.setSubAccountCode2(jshSysAccount.getCode());
//                sysJnl.setBeforeBalance2(jshSysAccount.getBalance());
//                sysJnl.setBeforeAvialableBalance2(jshSysAccount.getAvailableBalance());
//                sysJnl.setBeforeFreezeBalance2(jshSysAccount.getFreezeBalance());
//                sysJnl.setAfterBalance2(MoneyUtil.subtract(jshSysAccount.getBalance(), order.getAmount()).doubleValue());
//                sysJnl.setAfterAvialableBalance2(MoneyUtil.subtract(jshSysAccount.getAvailableBalance(), order.getAmount()).doubleValue());
//                sysJnl.setAfterFreezeBalance2(jshSysAccount.getFreezeBalance());
                sysJnl.setFee(0d);
                bsSysAccountJnlMapper.insertSelective(sysJnl);

                // 6.5. 记录bs_user_trans_detail(状态为成功)
                BsUserTransDetailExample example = new BsUserTransDetailExample();
                example.createCriteria().andOrderNoEqualTo(orderNo);
                BsUserTransDetail tmpTransDetail = new BsUserTransDetail();
                tmpTransDetail.setTransStatus(Constants.Trans_STATUS_SUCCESS);
                tmpTransDetail.setReturnCode(req.getRetCode());
                tmpTransDetail.setReturnMsg(req.getErrorMsg());
                tmpTransDetail.setUpdateTime(new Date());
                tmpTransDetail.setTransDesc("【奖励金提现】成功");
                bsUserTransDetailMapper.updateByExampleSelective(tmpTransDetail, example);

                // 6.6. 修改订单状态为成功
                payOrdersService.modifyOrderStatus4Safe(order.getId(), Constants.ORDER_STATUS_SUCCESS,
                        jljDB.getId(), null, null);

                // 6.7. 新增订单流水表
                BsPayOrdersJnl orderJnl = new BsPayOrdersJnl();
                orderJnl.setOrderId(order.getId());
                orderJnl.setOrderNo(order.getOrderNo());
                orderJnl.setTransCode(Constants.ORDER_TRANS_CODE_SUCCESS);
                orderJnl.setChannelTransType(order.getChannelTransType());
                orderJnl.setTransAmount(order.getAmount());
                orderJnl.setSysTime(new Date());
                orderJnl.setChannelTime(order.getCreateTime());
                orderJnl.setChannelJnlNo(req.getMxOrderId());
                orderJnl.setSubAccountId(order.getSubAccountId());
                orderJnl.setSubAccountCode(jljDB.getCode());
                orderJnl.setUserId(order.getUserId());
                orderJnl.setReturnCode(req.getRetCode());
                orderJnl.setReturnMsg(req.getErrorMsg());
                orderJnl.setCreateTime(new Date());
                bsPayOrdersJnlMapper.insertSelective(orderJnl);

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

                // 8.1. 更新奖励金户还原（奖励金冻结解除。可用余额、可提现余额+；冻结金额-）。
                BsSubAccount jljDB = subAccountService.findJLJSubAccountByUserId(userId);
                BsSubAccount jljAccount = new BsSubAccount();
                jljAccount.setId(jljDB.getId());
                jljAccount.setAvailableBalance(order.getAmount());
                jljAccount.setCanWithdraw(order.getAmount());
                jljAccount.setFreezeBalance(-order.getAmount());
                jljAccount.setLastTransDate(new Date());
                subAccountService.modifyBalancesByIncrement(jljAccount);

                // 8.2. 更新奖励金提现订单状态为失败
                payOrdersService.modifyOrderStatus4Safe(order.getId(), Constants.ORDER_STATUS_FAIL,
                        null, req.getRetCode(), req.getErrorMsg());

                // 8.3. 新增奖励金提现订单流水
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

                // 8.4. 用户信息表当前奖励金增加回来(还原)
                BsUser tempUser = new BsUser();
                tempUser.setCurrentBonus(order.getAmount());
                tempUser.setId(userId);
                bsUserMapper.updateBonusById(tempUser);

                // 8.5. 添加一条解冻记账流水
                BsAccountJnl bsAccountJnl = new BsAccountJnl();
                bsAccountJnl.setTransTime(new Date());
                bsAccountJnl.setTransCode(Constants.USER_UNFREEZE);
                bsAccountJnl.setTransName("用户奖励金提现发送失败，解冻余额");// 解冻
                bsAccountJnl.setTransAmount(order.getAmount());
                bsAccountJnl.setSysTime(new Date());
                bsAccountJnl.setCdFlag1(Constants.CD_FLAG_D);
                bsAccountJnl.setUserId1(userId);
                bsAccountJnl.setAccountId1(jljDB.getAccountId());
                bsAccountJnl.setSubAccountId1(jljDB.getId());
                bsAccountJnl.setSubAccountCode1(jljDB.getCode());
                bsAccountJnl.setBeforeBalance1(jljDB.getBalance());
                bsAccountJnl.setBeforeAvialableBalance1(jljDB.getAvailableBalance());
                bsAccountJnl.setBeforeFreezeBalance1(jljDB.getFreezeBalance());
                bsAccountJnl.setAfterBalance1(jljDB.getBalance());
                bsAccountJnl.setAfterAvialableBalance1(MoneyUtil.add(jljDB.getAvailableBalance(), order.getAmount()).doubleValue());
                bsAccountJnl.setAfterFreezeBalance1(MoneyUtil.subtract(jljDB.getFreezeBalance(), order.getAmount()).doubleValue());
                bsAccountJnl.setCdFlag2(Constants.CD_FLAG_C);
                bsAccountJnl.setUserId2(userId);
                bsAccountJnl.setSubAccountCode2(jljDB.getCode());
                bsAccountJnl.setStatus(Constants.ACCOUNT_JNL_STATUS_FAIL);
                bsAccountJnlMapper.insertSelective(bsAccountJnl);

                // 8.6. 更新用户交易明细表，状态为失败
                BsUserTransDetailExample example = new BsUserTransDetailExample();
                example.createCriteria().andOrderNoEqualTo(order.getOrderNo());
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

    private Map<String, Object> checkBonusWithdraw(Integer userId, Double bonusAmount, String payPassword) {
        Map<String, Object> result = new HashMap<>();
        // 0.1. 用户是否存在
        // 0.2. 校验用户支付密码是否正确
        // 0.3. 是否有回款卡
        // 0.4. 最小提现限额校验

        BalanceWithdrawCheckVO check = new BalanceWithdrawCheckVO();
        check.setUserId(userId);
        depUserBalanceWithdrawService.withdrawCheck(check);

        if(bonusAmount == null || 0d == bonusAmount) {
            throw new PTMessageException(PTMessageEnum.BONUS_BALANCE_IS_NULL);
        }
        // 0.1. 用户是否存在
        BsUser user = userService.findUserById(userId);
        if (user == null) {
            throw new PTMessageException(PTMessageEnum.USER_NO_EXIT);
        }
        // 0.2. 超级理财人（VIP理财人）不允许奖励金提现（经确认，VIP理财人不会产生奖励金）
//        if(isSuperUser(userId)) {
//            throw new PTMessageException(PTMessageEnum.WITHDRAW_NOT_ALLOWED);
//        }
        // 0.2. 校验用户支付密码是否正确
        if (!user.getPayPassword().equals(MD5Util.encryptMD5(payPassword + MD5Util.getEncryptkey()))) {
            throw new PTMessageException(PTMessageEnum.USER_PAY_PASS_ERROR);
        }
        // 0.3. 是否有回款卡
        BsBankCard receiveCard = bankCardService.findFirstBankCardByUserId(userId);
        if (receiveCard == null) {
            throw new PTMessageException(PTMessageEnum.BANKCARD_NOT_BIND);
        }
        // 0.4. JLJ可用余额是否足够（如果存在处理中的订单，奖励金可用余额不为零，则依然可以提现剩余奖励金？？）
//        BsSubAccount tempJsh = subAccountMapper.selectSingleSubActByUserAndType(userId, Constants.PRODUCT_TYPE_JLJ);
//        BsSubAccount jljDB = subAccountMapper.selectSubAccountByIdForLock(tempJsh.getId());
//        if (MoneyUtil.subtract(bonusAmount, jljDB.getCanWithdraw()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() > 0) {
//            throw new PTMessageException(PTMessageEnum.WITHDRAW_AMOUNT_SURPASS);
//        }
        // 0.4. 最小提现限额校验
        BsSysConfig config = bsSysConfigMapper.selectByPrimaryKey(Constants.BONUS_WITHDRAW_LIMIT);
        Double bonusWithdrawLimit = Double.valueOf(config.getConfValue());
        if (bonusAmount.compareTo(bonusWithdrawLimit) < 0) {
            throw new PTMessageException(PTMessageEnum.WITHDRAW_AMOUNT_SURPASS.getCode(), "奖励金"+MoneyUtil.format(bonusWithdrawLimit)+"元起提，请重新输入。");
        }

        // 是否恒丰开户
        BsHfbankUserExtExample extExample = new BsHfbankUserExtExample();
        extExample.createCriteria().andUserIdEqualTo(userId).andStatusEqualTo(Constants.HFBANK_USER_EXT_STATUS_OPEN);
        List<BsHfbankUserExt> ext = bsHfbankUserExtMapper.selectByExample(extExample);
        if (org.apache.commons.collections.CollectionUtils.isEmpty(ext)) {
            throw new PTMessageException(PTMessageEnum.HF_NOT_BIND);
        }

        String riskStatus = assetsService.riskStatus(userId);
        if(Constants.IS_NO.equals(riskStatus)) {
            throw new PTMessageException(PTMessageEnum.NO_ASSESSMENT);
        } else if(Constants.IS_EXPIRE.equals(riskStatus)) {
            throw new PTMessageException(PTMessageEnum.ASSESSMENT_EXPIRE);
        }

        result.put("user", user);
        result.put("receiveCard", receiveCard);
        return result;
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

}

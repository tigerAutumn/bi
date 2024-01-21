package com.pinting.business.accounting.finance.service.impl;

import com.pinting.business.accounting.finance.model.QuickPayResultInfo;
import com.pinting.business.accounting.finance.service.DepUserTopUpService;
import com.pinting.business.accounting.loan.enums.LoanStatus;
import com.pinting.business.accounting.loan.util.redisUtil;
import com.pinting.business.accounting.service.BsSysSubAccountService;
import com.pinting.business.accounting.service.PayOrdersService;
import com.pinting.business.dao.*;
import com.pinting.business.enums.*;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.hessian.site.message.ReqMsg_RegularBuy_EBankBuy;
import com.pinting.business.hessian.site.message.ReqMsg_RegularBuy_Order;
import com.pinting.business.hessian.site.message.ResMsg_RegularBuy_EBankBuy;
import com.pinting.business.hessian.site.message.ResMsg_RegularBuy_Order;
import com.pinting.business.model.*;
import com.pinting.business.model.dto.OrderResultInfo;
import com.pinting.business.model.vo.CommissionVO;
import com.pinting.business.model.vo.LoanNoticeVO;
import com.pinting.business.service.common.CommissionService;
import com.pinting.business.service.common.OrderBusinessService;
import com.pinting.business.service.manage.Bs19PayBankService;
import com.pinting.business.service.manage.BsUserService;
import com.pinting.business.service.site.AssetsService;
import com.pinting.business.service.site.BankCardService;
import com.pinting.business.service.site.SendWechatService;
import com.pinting.business.util.Constants;
import com.pinting.business.util.DateUtil;
import com.pinting.business.util.IdcardUtils;
import com.pinting.business.util.Util;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.hessian.message.hfbank.*;
import com.pinting.gateway.hessian.message.pay19.enums.*;
import com.pinting.gateway.out.service.*;
import com.pinting.gateway.smsEnums.TemplateKey;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * Author:      cyb
 * Date:        2017/4/4
 * Description: 存管充值服务
 */
@Service
public class DepUserTopUpServiceImpl implements DepUserTopUpService {
    private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);

    private final Logger logger = LoggerFactory.getLogger(DepUserTopUpServiceImpl.class);

    @Autowired
    private HfbankTransportService hfbankTransportService;
    @Autowired
    private Bs19payBankMapper payBankMapper;
    @Autowired
    private BsPayOrdersMapper payOrderMapper;
    @Autowired
    private BsSysConfigMapper sysMapper;
    @Autowired
    private BsBankCardMapper bankCardMapper;
    @Autowired
    private BsSubAccountMapper subAccountMapper;
    @Autowired
    private BsPayOrdersJnlMapper payOrderJnlMapper;
    @Autowired
    private BsAccountMapper accountMapper;
    @Autowired
    private BsUserTransDetailMapper userTransMapper;
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private BsUserMapper userMapper;
    @Autowired
    private BsAccountJnlMapper accountJnlMapper;
    @Autowired
    private BsSysSubAccountMapper sysAccountMapper;
    @Autowired
    private BsSysAccountJnlMapper sysAccountJnlMapper;
    @Autowired
    private SMSServiceClient smsServiceClient;
    @Autowired
    private BsBankMapper  bsBankMapper;
    @Autowired
    private SendWechatService sendWechatService;
    @Autowired
    private BankCardService bankCardService;
    @Autowired
    private BsServiceFeeMapper bsServiceFeeMapper;
    @Autowired
    private BsPayResultQueueMapper queueMapper;
    @Autowired
    private CommissionService commissionService;
    @Autowired
    private BsUserService bsUserService;
    @Autowired
    private PayOrdersService payOrdersService;
    @Autowired
    private BsSysSubAccountService bsSysSubAccountService;
    @Autowired
    private BsPayReceiptMapper bsPayReceiptMapper;
    @Autowired
    private BsHfbankUserExtMapper bsHfbankUserExtMapper;
    @Autowired
    private OrderBusinessService orderBusinessService;
    @Autowired
    private AssetsService assetsService;

    /**
     *	1. 校验请求数据正确性
     *	    1. 银行信息bankId非空
     *	    2. 校验该银行是否存在可用渠道
     *	    4. 校验用户是否存在且用户状态是可用
     *	    5. 校验用户是否成年
     *	    6. 校验用户是否在70岁以下
     *	       1. 超过70岁，校验是否存在投资中的产品（所有产品）。如果存在，则允许继续充值
     *	    7. 校验该银行渠道是否可用——此处和 2 是否重复，有待校验。
     *	    8. 银行单笔限额校验
     *	    9. 银行单日限额校验
     *	    10. 银行单月限额校验
     *	    11. 最低充值金额校验
     *	    12. 用户是否绑卡校验（可放到3操作中）
     *	    13. 已绑卡用户校验bs_user中的绑卡信息和bs_bank中的信息一致（可放到3操作中）
     *	    14. 校验充值金额是否低于手续费
     *	2. 新增订单bs_pay_orders
     *	    1. amout 即 充值金额
     *	    2. status 是 INIT
     *	3. 新增订单流水bs_pay_orders_jnl
     *	4. 调用恒丰用户充值预下单接口
     *	5. 充值预下单成功
     *	    1. 更新订单状态为预下单成功
     *	    2. 插入订单流水表
     *	6. 充值预下单失败
     *	    1. 更新订单状态为失败
     *	    2. 插入订单流水表
     * @param req 用户ID、充值金额、银行卡号、预留手机号、身份证、持卡人姓名、银行名称、银行ID、终端类型@1手机端,2PC端
     * @param res 用于结果返回
     */
    @Override
    public void hfPre(ReqMsg_RegularBuy_Order req, ResMsg_RegularBuy_Order res) {
        logger.info("【恒丰快捷充值预下单】请求参数：{}", JSONObject.fromObject(req));

        // 1. 校验请求数据正确性
        Map<String, Object> returnMap = quickPayTopUpPreCheck(req);
        Bs19payBank bank = (Bs19payBank) returnMap.get("bank");
        BsHfbankUserExt ext = (BsHfbankUserExt) returnMap.get("ext");
        Date now = new Date();

        //==========================================预下单=============================================
        // 2. 新增订单bs_pay_orders
        final BsPayOrders order = new BsPayOrders();
        order.setOrderNo(Util.generateOrderNo(req.getUserId()));
        order.setAmount(req.getAmount());
        order.setUserId(req.getUserId());
        order.setChannel(bank.getChannel());
        order.setStatus(Constants.ORDER_STATUS_CREATE);
        order.setBankName(req.getBankName());
        order.setMoneyType(Constants.ORDER_CURRENCY_CNY);
        order.setTerminalType(req.getTerminalType());
        order.setCreateTime(now);
        order.setUpdateTime(now);
        order.setBankId(req.getBankId());
        order.setBankCardNo(req.getCardNo());
        order.setAccountType(Constants.ORDER_ACCOUNT_TYPE_USER);
        order.setTransType(Constants.TRANS_TOP_UP);
        order.setChannelTransType(Constants.CHANNEL_TRS_QUICKPAY);
        order.setMobile(req.getMobile());
        order.setIdCard(req.getIdCard());
        order.setUserName(req.getUserName());
        payOrderMapper.insertSelective(order);

        // 3. 新增订单流水bs_pay_orders_jnl
        BsPayOrdersJnl jnl = new BsPayOrdersJnl();
        jnl.setOrderId(order.getId());
        jnl.setOrderNo(order.getOrderNo());
        jnl.setTransCode(Constants.ORDER_TRANS_CODE_INIT);
        jnl.setChannelTransType(Constants.CHANNEL_TRS_QUICKPAY);
        jnl.setTransAmount(order.getAmount());
        jnl.setSysTime(now);
        jnl.setUserId(req.getUserId());
        jnl.setCreateTime(now);
        payOrderJnlMapper.insertSelective(jnl);

        // 4. 调用恒丰用户充值预下单接口
        B2GReqMsg_HFBank_UserQuickPayPreRecharge b2gReq = new B2GReqMsg_HFBank_UserQuickPayPreRecharge();
        b2gReq.setOrder_no(order.getOrderNo());
        b2gReq.setPartner_trans_time(order.getCreateTime());
        b2gReq.setPartner_trans_date(order.getCreateTime());
        b2gReq.setPlatcust(ext.getHfUserId());
        b2gReq.setName(order.getUserName());
        b2gReq.setCard_no(order.getBankCardNo());;
        b2gReq.setId_code(order.getIdCard());
        b2gReq.setMobile(order.getMobile());
        b2gReq.setAmt(order.getAmount());
        b2gReq.setPay_code(Constants.HF_PAY_CODE_BAOFOO);
        b2gReq.setCard_type(Constants.HF_CARD_TYPE_DEBIT);
        b2gReq.setId_type(Constants.HF_ID_TYPE_ID_CARD); // 1-身份证
        b2gReq.setRemark("用户充值预下单");
        b2gReq.setAccount_type(req.getAccountType());
        b2gReq.setAccount_type(null == req.getAccountType() ? Constants.HFBANK_ACCOUNT_TYPE_INVEST : req.getAccountType());


        final B2GResMsg_HFBank_UserQuickPayPreRecharge b2gRes;
        try {
            logger.info("【恒丰用户充值预下单接口】请求参数：{}", JSONObject.fromObject(b2gReq));
            b2gRes = hfbankTransportService.userQuickPayPreRecharge(b2gReq);
        } catch (Exception e) {
            logger.error("【恒丰用户充值预下单接口】失败：网络通信异常。{}", e.getMessage());
            transactionTemplate.execute(new TransactionCallback<Boolean>() {
                @Override
                public Boolean doInTransaction(TransactionStatus status) {
                    // 6. 充值预下单失败
                    // 6.1. 更新订单状态为失败
                    BsPayOrders failOrder = new BsPayOrders();
                    failOrder.setId(order.getId());
                    failOrder.setStatus(Constants.ORDER_STATUS_CON_FAIL);
                    failOrder.setUpdateTime(new Date());
                    failOrder.setReturnMsg("恒丰用户充值预下单失败：网络通信异常。");
                    payOrderMapper.updateByPrimaryKeySelective(failOrder);
                    // 6.2. 插入订单流水表
                    BsPayOrdersJnl failJnl = new BsPayOrdersJnl();
                    failJnl.setOrderId(order.getId());
                    failJnl.setOrderNo(order.getOrderNo());
                    failJnl.setTransCode(Constants.ORDER_TRANS_CODE_COMM_FAIL);
                    failJnl.setChannelTransType(Constants.CHANNEL_TRS_QUICKPAY);
                    failJnl.setTransAmount(order.getAmount());
                    failJnl.setSysTime(new Date());
                    failJnl.setUserId(order.getUserId());
                    failJnl.setCreateTime(new Date());
                    failJnl.setReturnMsg("恒丰用户充值预下单失败：网络通信异常。");
                    payOrderJnlMapper.insertSelective(failJnl);
                    return true;
                }
            });

            throw new PTMessageException(PTMessageEnum.PAY19_CONNECTION_ERROR);
        }
        if(ConstantUtil.BSRESCODE_SUCCESS.equals(b2gRes.getResCode())) {
            // 5. 充值预下单成功
            logger.info("【恒丰用户充值预下单接口】成功：{}", JSONObject.fromObject(b2gRes));
            transactionTemplate.execute(new TransactionCallback<Boolean>() {
                @Override
                public Boolean doInTransaction(TransactionStatus status) {
                    // 5.1. 更新订单状态为预下单成功
                    BsPayOrders succOrder = new BsPayOrders();
                    succOrder.setId(order.getId());
                    succOrder.setStatus(Constants.ORDER_STATUS_PRE_SUCC);
                    succOrder.setUpdateTime(new Date());
                    succOrder.setReturnMsg(b2gRes.getResMsg());
                    payOrderMapper.updateByPrimaryKeySelective(succOrder);

                    // 5.2. 插入订单流水表
                    BsPayOrdersJnl succJnl = new BsPayOrdersJnl();
                    succJnl.setOrderId(order.getId());
                    succJnl.setOrderNo(order.getOrderNo());
                    succJnl.setTransCode(Constants.ORDER_TRANS_CODE_PRE_SUCC);
                    succJnl.setChannelTransType(Constants.CHANNEL_TRS_QUICKPAY);
                    succJnl.setTransAmount(order.getAmount());
                    succJnl.setSysTime(new Date());
                    succJnl.setUserId(order.getUserId());
                    succJnl.setCreateTime(new Date());
                    succJnl.setReturnCode(b2gRes.getResCode());
                    succJnl.setReturnMsg(b2gRes.getResMsg());
                    payOrderJnlMapper.insertSelective(succJnl);
                    return true;
                }
            });
        } else {
            logger.error("【恒丰用户充值预下单接口】失败：{}", JSONObject.fromObject(b2gRes));
            transactionTemplate.execute(new TransactionCallback<Boolean>() {
                @Override
                public Boolean doInTransaction(TransactionStatus status) {
                    // 6. 充值预下单失败
                    // 6.1. 更新订单状态为失败
                    BsPayOrders failOrder = new BsPayOrders();
                    failOrder.setId(order.getId());
                    failOrder.setUpdateTime(new Date());
                    failOrder.setStatus(Constants.ORDER_STATUS_PRE_FAIL);
                    failOrder.setReturnCode(b2gRes.getResCode());
                    failOrder.setReturnMsg(b2gRes.getResMsg());
                    payOrderMapper.updateByPrimaryKeySelective(failOrder);

                    // 6.2. 插入订单流水表
                    BsPayOrdersJnl preJnl = new BsPayOrdersJnl();
                    preJnl.setOrderId(order.getId());
                    preJnl.setOrderNo(order.getOrderNo());
                    preJnl.setTransCode(Constants.ORDER_TRANS_CODE_PRE_FAIL);
                    preJnl.setChannelTransType(Constants.CHANNEL_TRS_QUICKPAY);
                    preJnl.setTransAmount(order.getAmount());
                    preJnl.setSysTime(new Date());
                    preJnl.setUserId(order.getUserId());
                    preJnl.setCreateTime(new Date());
                    preJnl.setReturnCode(b2gRes.getResCode());
                    preJnl.setReturnMsg(b2gRes.getResMsg());
                    payOrderJnlMapper.insertSelective(preJnl);
                    return true;
                }
            });
            throw new PTMessageException(PTMessageEnum.USER_ORDER_PRE_FAIL,b2gRes.getResMsg());
        }
        //设置返回数据
        res.setOrderNo(order.getOrderNo());
        res.setUserId(order.getUserId());
    }

    /**
     *  充值正式下单（快捷）
     * 	1. 校验请求数据正确性
     * 		1. 银行信息bankId非空
     * 		2. 校验该银行是否存在可用渠道
     * 		4. 校验用户是否存在且用户状态是可用
     * 		5. 校验用户是否成年
     * 		6. 校验用户是否在70岁以下
     * 			1. 超过70岁，校验是否存在投资中的产品（所有产品）。如果存在，则允许继续充值
     * 		7. 校验该银行渠道是否可用——此处和 2 是否重复，有待校验。
     * 		8. 银行单笔限额校验
     * 		9. 银行单日限额校验
     * 		10. 银行单月限额校验
     * 		11. 最低充值金额校验
     * 		12. 用户是否绑卡校验（可放到3操作中）
     * 		13. 已绑卡用户校验bs_user中的绑卡信息和bs_bank中的信息一致（可放到3操作中）
     * 		14. 校验充值金额是否低于手续费
     * 		15. 校验订单是否是预下单成功状态的订单
     * 		16. 校验用户余额户是否存在
     * 		17. 校验确认下单金额和预下单金额是否一致
     * 	2. 调用恒丰用户充值正式下单接口
     * 	3. 充值成功
     * 		1. 修改订单状态为成功
     * 		2. 新增订单流水
     * 		3. 修改用户交易明细表状态为成功（没有，则新增）
     * 		4. 修改用户余额户金额+   PRODUCT_TYPE_DEP_JSH
     * 		5. 新增用户记账流水表
     * 		6. 修改平台用户余额户+    SYS_ACCOUNT_BGW_USER
     * 		7. 新增系统记账流水表
     * 		8. 修改用户信息表（可用余额+）
     * 		9. 修改支付结果表状态（BsPayResultQueue）为成功
     * 		10. 用户充值手续费记账
     * 			1. 新增手续费登记表
     * 			2. 存在手续费
     * 				1. 新增用户交易明细表
     * 				2. 修改用户余额户金额（-手续费）
     * 				3. 新增用户记账流水表
     * 				4. 修改用户bs_user可提现余额（-手续费）
     * 	4. 充值失败
     * 		1. 修改订单状态是失败
     * 		2. 新增订单流水
     * 	5. 充值处理中
     * 		1. 修改订单状态是处理中
     * 		2. 新增订单流水表
     * 		3. 新增用户交易明细表（处理中）
     * 		4. 将处理中订单信息存入redis中（认证支付）
     * 		5. 插入消息队列表BsPayResultQueue
     * 	6. 发送短信、微信通知
     *  @param req 请求信息
     *  @param res 用于结果返回
     */
    @Override
    public void hfConfirm(final ReqMsg_RegularBuy_Order req, final ResMsg_RegularBuy_Order res) {
        logger.info("【恒丰用户充值正式下单】请求参数：{}", JSONObject.fromObject(req));

        // 1. 校验请求数据正确性
        Map<String, Object> returnMap = quickPayTopUpPreCheck(req);
        Bs19payBank bank = (Bs19payBank) returnMap.get("bank");

        // 1.15. 校验订单是否是预下单成功状态的订单
        BsPayOrdersExample reguOrderExample = new BsPayOrdersExample();
        reguOrderExample.createCriteria().andOrderNoEqualTo(req.getOrderNo()).andStatusEqualTo(Constants.ORDER_STATUS_PRE_SUCC).andUserIdEqualTo(req.getUserId());
        List<BsPayOrders> reguOrderList = payOrderMapper.selectByExample(reguOrderExample);
        if(CollectionUtils.isEmpty(reguOrderList)) {
            throw new PTMessageException(PTMessageEnum.PAYMENT_ORDER_NOT_EXIST);
        }
        final BsPayOrders reguOrder = reguOrderList.get(0);
        // 1.16. 校验用户余额户是否存在
        List<BsSubAccount> subList = subAccountMapper.selectSubAccount(req.getUserId(), Constants.PRODUCT_TYPE_DEP_JSH, Constants.SUBACCOUNT_STATUS_OPEN);
        if(CollectionUtils.isEmpty(subList)) {
            throw new PTMessageException(PTMessageEnum.USER_JSH_NO_EXIT);
        }
        final BsSubAccount bsSubAccount = subList.get(0);

        // 1.17. 校验确认下单金额和预下单金额是否一致
        if(reguOrder.getAmount().compareTo(req.getAmount()) != 0){
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "金额非法");
        }

        // 2. 绑卡/充值短信验证码 必须6位，否则提示客户“请输入6位验证码”
        if(req.getVerifyCode().length() != 6) {
            throw new PTMessageException(PTMessageEnum.MOBILE_CODE_MUST_SIX);
        }
        if(!StringUtil.isNumeric(req.getVerifyCode())) {
            throw new PTMessageException(PTMessageEnum.MOBILE_CODE_MUST_NUMBER);
        }
        //==========================================验证结束============================================

        // 2. 调用恒丰用户充值正式下单接口
        B2GReqMsg_HFBank_UserQuickPayConfirmRecharge b2gReq = new B2GReqMsg_HFBank_UserQuickPayConfirmRecharge();
        b2gReq.setPartner_trans_date(new Date());
        b2gReq.setPartner_trans_time(new Date());
        b2gReq.setIdentifying_code(req.getVerifyCode());
        b2gReq.setOrigin_order_no(reguOrder.getOrderNo());
        b2gReq.setPay_code(Constants.HF_PAY_CODE_BAOFOO);
        b2gReq.setRemark("用户充值正式下单");
        final B2GResMsg_HFBank_UserQuickPayConfirmRecharge b2gRes;
        try {
            logger.info("【恒丰用户充值正式下单】请求参数：{}", JSONObject.fromObject(b2gReq));
            b2gRes = hfbankTransportService.userQuickPayConfirmRecharge(b2gReq);
        } catch (Exception e) {
            logger.error("【恒丰用户充值正式下单】失败：{}", e.getMessage());
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {
                    BsPayOrders order = payOrderMapper.selectByPKForLock(reguOrder.getId());

                    // 4.1. 修改订单状态是失败-7
                    payOrdersService.modifyOrderStatus4Safe(reguOrder.getId(), Constants.ORDER_STATUS_FAIL,
                            bsSubAccount.getId(), null, "正式下单失败：网络通信异常。");

                    // 4.2. 新增订单流水
                    BsPayOrdersJnl reguJnl = new BsPayOrdersJnl();
                    reguJnl.setOrderId(reguOrder.getId());
                    reguJnl.setOrderNo(reguOrder.getOrderNo());
                    reguJnl.setChannelTransType(Constants.CHANNEL_TRS_QUICKPAY);
                    reguJnl.setTransAmount(reguOrder.getAmount());
                    reguJnl.setSysTime(new Date());
                    reguJnl.setSubAccountId(bsSubAccount.getId());
                    reguJnl.setSubAccountCode(bsSubAccount.getCode());
                    reguJnl.setUserId(reguOrder.getUserId());
                    reguJnl.setCreateTime(new Date());
                    reguJnl.setTransCode(Constants.ORDER_TRANS_CODE_FAIL);
                    reguJnl.setSysTime(new Date());
                    reguJnl.setCreateTime(new Date());
                    reguJnl.setReturnMsg("正式下单失败：网络通信异常。");
                    payOrderJnlMapper.insertSelective(reguJnl);

                    // 4.3. 新增用户交易明细表（失败）
                    BsUserTransDetail detail = new BsUserTransDetail();
                    detail.setUserId(reguOrder.getUserId());
                    detail.setCardNo(reguOrder.getBankCardNo());
                    detail.setTransType(Constants.Trans_TYPE_TOP_UP);
                    detail.setTransStatus(Constants.Trans_STATUS_FAIL);
                    detail.setOrderNo(reguOrder.getOrderNo());
                    detail.setCreateTime(new Date());
                    detail.setAmount(req.getAmount());
                    detail.setUpdateTime(new Date());
                    userTransMapper.insertSelective(detail);
                }
            });
            throw new PTMessageException(PTMessageEnum.PAY19_CONNECTION_ERROR);
        }
        if(ConstantUtil.BSRESCODE_SUCCESS.equals(b2gRes.getResCode())) {
            // 3. 充值成功
            logger.info("【恒丰用户充值正式下单】成功：{}", JSONObject.fromObject(b2gRes));
            res.setResCode(ConstantUtil.RESCODE_SUCCESS);
            // 3.1. 修改订单状态为成功
            payOrdersService.modifyOrderStatus4Safe(reguOrder.getId(), Constants.ORDER_STATUS_PAYING,
                    bsSubAccount.getId(), null, null);

            OrderResultInfo orderResultInfo = new OrderResultInfo();
            orderResultInfo.setOrderNo(reguOrder.getOrderNo());
            orderResultInfo.setAmount(reguOrder.getAmount());
            orderResultInfo.setPayOrderNo(b2gRes.getData().getQuery_id());
            orderResultInfo.setResMsg(b2gRes.getResMsg());
            orderResultInfo.setResCode(StringUtil.isBlank(b2gRes.getRecode()) ? b2gRes.getResCode() : b2gRes.getRecode());
            orderResultInfo.setFinishTime(new Date());
            orderResultInfo.setSuccess(true);
            orderBusinessService.financeHFTopUp(orderResultInfo);
        } else if(Constants.GATEWAY_PAYING_RES_CODE.equals(b2gRes.getResCode())) {
            // 5. 充值处理中
            logger.info("【恒丰用户充值正式下单】处理中：{}", JSONObject.fromObject(b2gRes));
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {
                    // 5.1. 修改订单状态是处理中-5
                    payOrdersService.modifyOrderStatus4Safe(reguOrder.getId(), Constants.ORDER_STATUS_PAYING,
                            bsSubAccount.getId(), null, null);
                    // 5.2. 新增订单流水表
                    BsPayOrdersJnl reguJnl = new BsPayOrdersJnl();
                    reguJnl.setOrderId(reguOrder.getId());
                    reguJnl.setOrderNo(reguOrder.getOrderNo());
                    reguJnl.setTransCode(Constants.ORDER_TRANS_CODE_PROCCESS);
                    reguJnl.setChannelTransType(Constants.CHANNEL_TRS_QUICKPAY);
                    reguJnl.setTransAmount(reguOrder.getAmount());
                    reguJnl.setSysTime(new Date());
                    reguJnl.setSubAccountId(bsSubAccount.getId());
                    reguJnl.setSubAccountCode(bsSubAccount.getCode());
                    reguJnl.setUserId(reguOrder.getUserId());
                    reguJnl.setCreateTime(new Date());
                    reguJnl.setChannelTime(reguOrder.getCreateTime());
                    reguJnl.setReturnCode(StringUtil.isBlank(b2gRes.getRecode()) ? b2gRes.getResCode() : b2gRes.getRecode());
                    reguJnl.setReturnMsg(b2gRes.getResMsg());
                    payOrderJnlMapper.insertSelective(reguJnl);

                    // 5.3. 新增用户交易明细表（处理中）
                    BsUserTransDetail detail = new BsUserTransDetail();
                    detail.setUserId(reguOrder.getUserId());
                    detail.setCardNo(reguOrder.getBankCardNo());
                    detail.setTransType(Constants.Trans_TYPE_TOP_UP);
                    detail.setTransStatus(Constants.Trans_STATUS_DEAL);
                    detail.setOrderNo(reguOrder.getOrderNo());
                    detail.setCreateTime(new Date());
                    detail.setAmount(req.getAmount());
                    detail.setUpdateTime(new Date());
                    userTransMapper.insertSelective(detail);
                    res.setResCode(Constants.GATEWAY_PAYING_RES_CODE);

                    // 5.4. 将处理中订单信息存入redis中（认证支付）
                    LoanNoticeVO vo = new LoanNoticeVO();
                    vo.setPayOrderNo(reguOrder.getOrderNo());
                    vo.setAmount(reguOrder.getAmount().toString());
                    vo.setChannel(reguOrder.getChannel());
                    vo.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_AUTH.getCode());
                    vo.setTransType(reguOrder.getTransType());
                    vo.setStatus(Constants.ORDER_STATUS_PAYING);
                    redisUtil.rpushRedis(vo);

                    // 5.5. 插入消息队列表BsPayResultQueue
                    BsPayResultQueue queue = new BsPayResultQueue();
                    queue.setChannel(Constants.CHANNEL_HFBANK);
                    queue.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_AUTH.getCode());
                    queue.setCreateTime(new Date());
                    queue.setDealNum(0);
                    queue.setOrderNo(reguOrder.getOrderNo());
                    queue.setStatus(LoanStatus.NOTICE_STATUS_INIT.getCode());
                    queue.setTransType(reguOrder.getTransType());
                    queue.setUpdateTime(new Date());
                    queueMapper.insertSelective(queue);
                }
            });
        } else {
            // 四、恒丰正式下单失败
            logger.error("【恒丰用户充值正式下单】失败：{}", JSONObject.fromObject(b2gRes));
            final String errorMsg =  b2gRes.getResMsg();
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {
                    BsPayOrders order = payOrderMapper.selectByPKForLock(reguOrder.getId());
                    // 4.1. 修改订单状态是失败-7
                    String thirdReturnCode = StringUtil.isBlank(b2gRes.getRecode()) ? b2gRes.getResCode() : b2gRes.getResCode();
                    payOrdersService.modifyOrderStatus4Safe(reguOrder.getId(), Constants.ORDER_STATUS_FAIL,
                            bsSubAccount.getId(), thirdReturnCode, errorMsg);

                    // 4.2. 新增订单流水
                    BsPayOrdersJnl reguJnl = new BsPayOrdersJnl();
                    reguJnl.setOrderId(reguOrder.getId());
                    reguJnl.setOrderNo(reguOrder.getOrderNo());
                    reguJnl.setChannelTransType(Constants.CHANNEL_TRS_QUICKPAY);
                    reguJnl.setTransAmount(reguOrder.getAmount());
                    reguJnl.setSysTime(new Date());
                    reguJnl.setSubAccountId(bsSubAccount.getId());
                    reguJnl.setSubAccountCode(bsSubAccount.getCode());
                    reguJnl.setUserId(reguOrder.getUserId());
                    reguJnl.setCreateTime(new Date());
                    reguJnl.setTransCode(Constants.ORDER_TRANS_CODE_FAIL);
                    reguJnl.setSysTime(new Date());
                    reguJnl.setReturnCode(thirdReturnCode);
                    reguJnl.setReturnMsg(errorMsg);
                    payOrderJnlMapper.insertSelective(reguJnl);

                    // 4.3. 新增用户交易明细表（失败）
                    BsUserTransDetail detail = new BsUserTransDetail();
                    detail.setUserId(reguOrder.getUserId());
                    detail.setCardNo(reguOrder.getBankCardNo());
                    detail.setTransType(Constants.Trans_TYPE_TOP_UP);
                    detail.setTransStatus(Constants.Trans_STATUS_FAIL);
                    detail.setOrderNo(reguOrder.getOrderNo());
                    detail.setCreateTime(new Date());
                    detail.setAmount(req.getAmount());
                    detail.setUpdateTime(new Date());
                    userTransMapper.insertSelective(detail);

                }
            });
            res.setResCode(b2gRes.getResCode());
            res.setResMsg(b2gRes.getResMsg());
        }
        res.setUserId(req.getUserId());
        res.setOrderDate(new Date());
        res.setOrderNo(reguOrder.getOrderNo());

//        // 五、发送微信+短信
        if(ConstantUtil.RESCODE_SUCCESS.equals(res.getResCode())) {
//            //message = "您有一笔充值已成功，充值金额："+order.getAmount()+"。如有疑问请拨打400-806-1230。";
//            smsServiceClient.sendTemplateMessage(reguOrder.getMobile(), TemplateKey.TOP_UP_SUC, reguOrder.getAmount().toString());
//            //发送微信模板消息
//            sendWechatService.topUpMgs(reguOrder.getUserId(),"", reguOrder.getMobile(), reguOrder.getAmount().toString(), "suc", null);
        } else if(Constants.GATEWAY_PAYING_RES_CODE.equals(res.getResCode())) {
//            // 仍是处理中不做任何处理
        } else {
            //获取手机号，发送短信，微信消息
//            String mobile = getMobile(reguOrder.getUserId());
            //message = "您有一笔充值未成功，失败原因："+req.getErrorMsg()+"，请核实，如有疑问请拨打400-806-1230。";
//            smsServiceClient.sendTemplateMessage(mobile, TemplateKey.TOP_UP_FALL, BaoFooResCode.getEnumByCode(res.getResCode()).getDescription());
//            smsServiceClient.sendTemplateMessage(mobile, TemplateKey.TOP_UP_FALL, StringUtil.isBlank(b2gRes.getResMsg()) ? PTMessageEnum.USER_ORDER_REGULAR_FAIL.getMessage() : b2gRes.getResMsg());
            //发送微信模板消息
//            sendWechatService.topUpMgs(reguOrder.getUserId(),"", mobile, reguOrder.getAmount().toString(), "fall", StringUtil.isBlank(b2gRes.getResMsg()) ? PTMessageEnum.USER_ORDER_REGULAR_FAIL.getMessage() : b2gRes.getResMsg());
//            sendWechatService.topUpMgs(reguOrder.getUserId(),"", mobile, reguOrder.getAmount().toString(), "fall", BaoFooResCode.getEnumByCode(res.getResCode()).getDescription());
            throw new PTMessageException(PTMessageEnum.USER_ORDER_REGULAR_FAIL.getCode(), StringUtil.isBlank(b2gRes.getResMsg()) ? PTMessageEnum.USER_ORDER_REGULAR_FAIL.getMessage() : b2gRes.getResMsg());
        }
    }

    /**
     * 充值通知
     * 0. 数据校验
     *  1. 判断订单是否存在
     * 	    1. 快捷支付处理中的订单
     * 		2. 网银支付初始状态的订单
     *  2. 判断用户是否存在
     *  3. 判断用户主账户是否存在
     *  4. 判断订单对应的用户子账户DEP_JSH是否存在
     * 1. 充值成功
     * 	1. 修改订单表状态为成功-6
     * 	2. 新增订单流水表
     * 	3. 修改用户交易明细表状态为成功（没有，则新增）
     * 	4. 修改用户余额户金额+   PRODUCT_TYPE_DEP_JSH
     * 	5. 新增用户记账流水表
     * 	6. 修改平台用户余额户+    SYS_ACCOUNT_BGW_USER
     * 	7. 新增系统记账流水表
     * 	8. 修改用户信息表（可用余额+）
     * 	9. 判断是否为快捷充值，若为快捷充值，则需要修改支付结果表状态（BsPayResultQueue）为成功
     * 	10. 用户充值手续费记账
     * 		1. 新增手续费登记表
     * 		2. 存在手续费
     * 			1. 新增用户交易明细表
     * 			2. 修改用户余额户金额（-手续费）
     * 			3. 新增用户记账流水表
     * 			4. 修改用户bs_user可提现余额（-手续费）
     *
     * 2. 充值失败
     * 	1. 修改订单表状态为失败-7
     * 	2. 新增订单流水表
     * 	3. 修改用户交易明细表状态为 失败（没有，则新增）
     * 	4. 判断是否为快捷充值，若为快捷充值，则需要修改支付结果表状态（BsPayResultQueue）为失败
     * @param req 请求信息
     */
    @Override
    public void hfNotify(final QuickPayResultInfo req) {
        logger.info("【恒丰用户充值通知】请求参数：{}", JSONObject.fromObject(req));
        // 0. 数据校验
        Map<String, Object> returnMap = notifyPreCheck(req);
        try {
            jsClientDaoSupport.lock(RedisLockEnum.LOCK_USER_TOPUP.getKey());
            final BsPayOrders order = (BsPayOrders) returnMap.get("order");
            final BsUser user = (BsUser) returnMap.get("user");
            final BsAccount account = (BsAccount) returnMap.get("account");
            final BsSubAccount subAccount = (BsSubAccount) returnMap.get("subAccount");
            final boolean isCompensatoryUserZAN = isCompensatoryUserZAN(user.getId()); // 是否赞分期代偿人
            transactionTemplate.execute(new TransactionCallbackWithoutResult(){
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {
                    BsPayOrders lockOrder = payOrderMapper.selectByPKForLock(order.getId());
                    // 1. 充值成功
                    if(OrderStatus.SUCCESS.getCode().equals(req.getStatus())) {
                        logger.info("【恒丰用户充值通知】充值交易成功，币港湾订单号：{}", order.getOrderNo());

                        // 1.1. 修改订单表状态为成功-6
                        order.setStatus(Constants.ORDER_STATUS_SUCCESS);
                        order.setUpdateTime(new Date());
                        order.setReturnCode(req.getErrorCode());
                        order.setReturnMsg(req.getErrorMsg());
                        payOrderMapper.updateByPrimaryKeySelective(order);

                        // 1.2. 新增订单流水表
                        BsPayOrdersJnl orderJnl = new BsPayOrdersJnl();
                        orderJnl.setOrderId(order.getId());
                        orderJnl.setOrderNo(order.getOrderNo());
                        orderJnl.setTransCode(Constants.ORDER_TRANS_CODE_SUCCESS);
                        orderJnl.setChannelTransType(order.getChannelTransType());
                        orderJnl.setTransAmount(order.getAmount());
                        orderJnl.setSysTime(new Date());
                        orderJnl.setChannelTime(req.getOrderFinTime());
                        orderJnl.setChannelJnlNo(req.getMpOrderId());
                        orderJnl.setSubAccountId(order.getSubAccountId());
                        orderJnl.setSubAccountCode(subAccount.getCode());
                        orderJnl.setUserId(order.getUserId());
                        orderJnl.setReturnCode(req.getErrorCode());
                        orderJnl.setReturnMsg(req.getErrorMsg());
                        orderJnl.setCreateTime(new Date());
                        payOrderJnlMapper.insertSelective(orderJnl);

                        // 1.3. 修改用户交易明细表状态为成功（没有，则新增）
                        BsUserTransDetailExample userTransExample = new BsUserTransDetailExample();
                        userTransExample.createCriteria().andUserIdEqualTo(order.getUserId()).andOrderNoEqualTo(order.getOrderNo());
                        int count = userTransMapper.countByExample(userTransExample);
                        if(count <= 0) {
                            BsUserTransDetail userTrans = new BsUserTransDetail();
                            // 新增用户交易明细表(充值)
                            userTrans.setUserId(order.getUserId());
                            userTrans.setCardNo(order.getBankCardNo());
                            userTrans.setTransType(Constants.Trans_TYPE_TOP_UP);
                            userTrans.setTransStatus(Constants.Trans_STATUS_SUCCESS);
                            userTrans.setOrderNo(order.getOrderNo());
                            userTrans.setCreateTime(new Date());
                            userTrans.setAmount(order.getAmount());
                            userTrans.setUpdateTime(new Date());
                            userTransMapper.insertSelective(userTrans);
                        } else {
                            BsUserTransDetail userTrans = new BsUserTransDetail();
                            userTrans.setTransStatus(Constants.Trans_STATUS_SUCCESS);
                            userTrans.setUpdateTime(new Date());
                            userTransMapper.updateByExampleSelective(userTrans, userTransExample);
                        }

                        //充值
                        if(!isCompensatoryUserZAN) {
                            // 普通理财人充值记账
                            userAccount(req, order, account);
                        } else {
                            // 代偿人充值记账
                            compensatoryAccount(req, order, account);
                        }

                        // 1.8. 修改用户信息表（可用余额+）
                        BsUser userLock = userMapper.selectByPKForLock(user.getId());
                        BsUser updateUser = new BsUser();
                        updateUser.setId(userLock.getId());
                        updateUser.setCanWithdraw(MoneyUtil.add(userLock.getCanWithdraw(), order.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                        userMapper.updateByPrimaryKeySelective(updateUser);

                        // 1.9. 判断是否为快捷充值，若为快捷充值，则需要修改支付结果表状态（BsPayResultQueue）为成功
                        BsPayOrdersExample quickOrderExample = new BsPayOrdersExample();
                        quickOrderExample.createCriteria().andOrderNoEqualTo(req.getOrderId())
                                .andStatusEqualTo(Constants.ORDER_STATUS_PAYING).andChannelTransTypeEqualTo(Constants.CHANNEL_TRS_QUICKPAY);
                        int quickOrderCount = payOrderMapper.countByExample(quickOrderExample);
                        if(quickOrderCount > 0) {
                            BsPayResultQueueExample queueExample = new BsPayResultQueueExample();
                            queueExample.createCriteria().andOrderNoEqualTo(order.getOrderNo());
                            List<BsPayResultQueue> queueList = queueMapper.selectByExample(queueExample);
                            if (org.apache.commons.collections.CollectionUtils.isNotEmpty(queueList) && queueList.size() > 0) {
                                BsPayResultQueue queueTemp = new BsPayResultQueue();
                                queueTemp.setId(queueList.get(0).getId());
                                queueTemp.setUpdateTime(new Date());
                                if (OrderStatus.SUCCESS.getCode().equals(req.getStatus())) {
                                    queueTemp.setStatus("SUCC");
                                }else {
                                    queueTemp.setStatus("FAIL");
                                }
                                queueMapper.updateByPrimaryKeySelective(queueTemp);
                            }
                        }

                        chargeTopUpCommission(order.getOrderNo(), TransTypeEnum.USER_TOP_UP_QUICK_PAY, PayPlatformEnum.HF_BAOFOO);
                    }
                    //失败
                    else {
                        logger.error("【恒丰用户充值通知】充值交易失败，币港湾订单号：{}，错误码：{}，原因：{}", order.getOrderNo(), req.getErrorCode(), req.getErrorMsg());
                        // 1. 修改订单表状态为失败-7
                        order.setStatus(Constants.ORDER_STATUS_FAIL);
                        order.setUpdateTime(new Date());
                        order.setReturnCode(req.getErrorCode());
                        order.setReturnMsg(req.getErrorMsg());
                        payOrderMapper.updateByPrimaryKeySelective(order);

                        // 2. 新增订单流水表
                        BsPayOrdersJnl orderJnl = new BsPayOrdersJnl();
                        orderJnl.setOrderId(order.getId());
                        orderJnl.setOrderNo(order.getOrderNo());
                        orderJnl.setTransCode(Constants.ORDER_TRANS_CODE_FAIL);
                        orderJnl.setChannelTransType(order.getChannelTransType());
                        orderJnl.setTransAmount(order.getAmount());
                        orderJnl.setSysTime(new Date());
                        orderJnl.setChannelTime(req.getOrderFinTime());
                        orderJnl.setChannelJnlNo(req.getMpOrderId());
                        orderJnl.setSubAccountId(order.getSubAccountId());
                        orderJnl.setSubAccountCode(subAccount.getCode());
                        orderJnl.setUserId(order.getUserId());
                        orderJnl.setReturnCode(req.getErrorCode());
                        orderJnl.setReturnMsg(req.getErrorMsg());
                        orderJnl.setCreateTime(new Date());
                        payOrderJnlMapper.insertSelective(orderJnl);

                        // 3. 修改用户交易明细表状态为 失败（没有，则新增）
                        BsUserTransDetailExample userTransExample = new BsUserTransDetailExample();
                        userTransExample.createCriteria().andUserIdEqualTo(order.getUserId()).andOrderNoEqualTo(order.getOrderNo());
                        int count = userTransMapper.countByExample(userTransExample);
                        if(count <= 0) {
                            BsUserTransDetail userTrans = new BsUserTransDetail();
                            // 新增用户交易明细表(充值)
                            userTrans.setUserId(order.getUserId());
                            userTrans.setCardNo(order.getBankCardNo());
                            userTrans.setTransType(Constants.Trans_TYPE_TOP_UP);
                            userTrans.setTransStatus(Constants.Trans_STATUS_FAIL);
                            userTrans.setOrderNo(order.getOrderNo());
                            userTrans.setCreateTime(new Date());
                            userTrans.setAmount(order.getAmount());
                            userTrans.setUpdateTime(new Date());
                            userTransMapper.insertSelective(userTrans);
                        } else {
                            BsUserTransDetail userTrans = new BsUserTransDetail();
                            userTrans.setTransStatus(Constants.Trans_STATUS_FAIL);
                            userTrans.setUpdateTime(new Date());
                            userTransMapper.updateByExampleSelective(userTrans, userTransExample);
                        }


                        // 4. 判断是否为快捷充值，若为快捷充值，则需要修改支付结果表状态（BsPayResultQueue）为失败
                        BsPayOrdersExample quickOrderExample = new BsPayOrdersExample();
                        quickOrderExample.createCriteria().andOrderNoEqualTo(req.getOrderId())
                                .andStatusEqualTo(Constants.ORDER_STATUS_PAYING).andChannelTransTypeEqualTo(Constants.CHANNEL_TRS_QUICKPAY);
                        int quickOrderCount = payOrderMapper.countByExample(quickOrderExample);
                        if(quickOrderCount > 0) {
                            BsPayResultQueueExample queueExample = new BsPayResultQueueExample();
                            queueExample.createCriteria().andOrderNoEqualTo(order.getOrderNo());
                            List<BsPayResultQueue> queueList = queueMapper.selectByExample(queueExample);
                            if (org.apache.commons.collections.CollectionUtils.isNotEmpty(queueList) && queueList.size() > 0) {
                                BsPayResultQueue queueTemp = new BsPayResultQueue();
                                queueTemp.setId(queueList.get(0).getId());
                                queueTemp.setUpdateTime(new Date());
                                if (OrderStatus.SUCCESS.getCode().equals(req.getStatus())) {
                                    queueTemp.setStatus("SUCC");
                                }else {
                                    queueTemp.setStatus("FAIL");
                                }
                                queueMapper.updateByPrimaryKeySelective(queueTemp);
                            }
                        }
                    }
                }

            });

            //获取手机号，发送短信，微信消息
            String mobile = getMobile(order.getUserId());
            //充值
            try {
                if(OrderStatus.SUCCESS.getCode().equals(req.getStatus())) {
                    //message = "您有一笔充值已成功，充值金额："+order.getAmount()+"。如有疑问请拨打400-806-1230。";
                    smsServiceClient.sendTemplateMessage(mobile, TemplateKey.TOP_UP_SUC, order.getAmount().toString());
                    //发送微信模板消息
                    sendWechatService.topUpMgs(order.getUserId(),"", mobile, order.getAmount().toString(), "suc", null);
                }
            } catch (Exception e) {
                logger.error("恒丰充值发送通知异常：{}", e.getMessage());
            }
        } finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_USER_TOPUP.getKey());
        }

    }

    /**
     * 网银充值
     * 1. 前置校验
     *  1. 银行信息bankId非空
     *  2. 校验用户是否存在且用户状态是可用
     *  3. 校验用户是否成年
     *  4. 校验用户是否在70岁以下
     *      4.1. 超过70岁，校验是否存在投资中的产品（所有产品）。如果存在，则允许继续充值
     *  5. 用户是否绑卡校验（可放到3操作中）
     *  6. 最低充值金额校验
     *  7. 判断订单对应的用户子账户DEP_JSH是否存在
     *  8. 判断是否存在该银行通道
     *  9. 是否恒丰开户
     * 2. 新增订单bs_pay_orders
     * 3. 新增订单流水bs_pay_orders_jnl
     * 4. 调用恒丰网关充值接口
     * @param req 请求信息
     * @param res 用于结果返回
     */
    @Override
    public void hfEBank(ReqMsg_RegularBuy_EBankBuy req, ResMsg_RegularBuy_EBankBuy res) {
        logger.info("【用户网银充值下单】请求参数：{}", JSONObject.fromObject(req));
        // 1. 前置校验
        Map<String, Object> returnMap = eBankTopUpPreCheck(req);
        BsSubAccount subAccount = (BsSubAccount) returnMap.get("subAccount");
        BsUser user = (BsUser) returnMap.get("user");
        BsBank bsBank = (BsBank) returnMap.get("bsBank");
        BsHfbankUserExt ext = (BsHfbankUserExt) returnMap.get("ext");
        Bs19payBank bs19payBank = (Bs19payBank) returnMap.get("bs19payBank");

        //==========================================验证结束============================================

        // 2. 新增订单bs_pay_orders
        BsPayOrders order = new BsPayOrders();
        order.setOrderNo(Util.generateOrderNo(req.getUserId()));
        order.setAmount(req.getAmount());
        order.setUserId(req.getUserId());
        order.setSubAccountId(subAccount.getId());
        order.setChannel(Constants.CHANNEL_HFBANK);
        order.setStatus(Constants.ORDER_STATUS_CREATE);
        order.setMoneyType(Constants.ORDER_CURRENCY_CNY);
        order.setTerminalType(Constants.TERMINAL_TYPE_PC);
        order.setCreateTime(new Date());
        order.setUpdateTime(new Date());
        order.setAccountType(Constants.ORDER_ACCOUNT_TYPE_USER);
        order.setTransType(Constants.TRANS_TOP_UP);
        order.setChannelTransType(Constants.CHANNEL_TRS_NETBANK);
        order.setMobile(user.getMobile());
        order.setIdCard(user.getIdCard());
        order.setUserName(user.getUserName());
        if (bsBank != null) {
            order.setBankId(bsBank.getId());
            order.setBankName(bsBank.getName());
        }
        payOrderMapper.insertSelective(order);

        // 3. 新增订单流水bs_pay_orders_jnl
        BsPayOrdersJnl jnl = new BsPayOrdersJnl();
        jnl.setOrderId(order.getId());
        jnl.setOrderNo(order.getOrderNo());
        jnl.setTransCode(Constants.ORDER_TRANS_CODE_INIT);
        jnl.setChannelTransType(Constants.CHANNEL_TRS_NETBANK);
        jnl.setTransAmount(order.getAmount());
        jnl.setSysTime(new Date());
        jnl.setUserId(req.getUserId());
        jnl.setSubAccountId(subAccount.getId());
        jnl.setSubAccountCode(subAccount.getCode());
        jnl.setCreateTime(new Date());
        payOrderJnlMapper.insertSelective(jnl);
        
        //Bs19payBank bank = bs19PayBankService.selectByPrimaryKey(Integer.parseInt(req.getBankCode()));
        

        // 4. 调用恒丰网关充值接口
        B2GReqMsg_HFBank_UserGatewayRechargeRequest eBankReq = new B2GReqMsg_HFBank_UserGatewayRechargeRequest();
        eBankReq.setOrder_no(order.getOrderNo());
        eBankReq.setPartner_trans_date(order.getCreateTime());
        eBankReq.setPartner_trans_time(order.getCreateTime());
        eBankReq.setPlatcust(ext.getHfUserId());
        eBankReq.setType(Constants.HF_TYPE_USER_RECHARGE);  // 充值类型（1-用户充值）
        eBankReq.setCharge_type(Constants.HF_CHARGE_TYPE_INVITE); // 1-投资账户 2-融资账户
        eBankReq.setBankcode(BaoFooEnum.ebankPayMap.get(String.valueOf(bs19payBank.getBankId())));
        eBankReq.setPay_code(Constants.HF_PAY_CODE_BAOFOO);
        eBankReq.setAmt(req.getAmount());
        if (StringUtil.isNotEmpty(req.getWebFlag()) && "qianbao178NetBankBuy".equals(req.getWebFlag())) {
            eBankReq.setReturn_url(GlobEnvUtil.get("ebankbuy.return.url.gen178"));
        } else {
            eBankReq.setReturn_url(GlobEnvUtil.get("ebankbuy.return.url.gen"));
        }
        eBankReq.setCard_no("");//卡号，无卡号填空:””
        try {
            logger.info("【用户网银充值下单】请求参数：{}", JSONObject.fromObject(eBankReq));
            B2GResMsg_HFBank_UserGatewayRechargeRequest ebankRes = hfbankTransportService.userGatewayRechargeRequest(eBankReq);
            if(StringUtil.isEmpty(ebankRes.getHtml())) {
                logger.error("【用户网银充值下单】失败，错误码：{}，失败原因：{}",res.getResCode(), res.getResMsg());
                throw new PTMessageException(PTMessageEnum.DAFY_BUY_PRODUCT_ERROR.getCode(), res.getResMsg());
            } else {
                res.setResHtml(ebankRes.getHtml());
            }
        } catch (Exception e) {
            logger.error("【用户网银充值下单】失败，失败原因：{}", e.getMessage());
            e.printStackTrace();
            throw new PTMessageException(PTMessageEnum.DAFY_BUY_PRODUCT_ERROR.getCode(), "充值异常!");
        }
        //设置返回数据
        res.setOrderDate(order.getCreateTime());
        res.setOrderNo(order.getOrderNo());
        logger.info("【用户网银充值下单】成功，币港湾订单号：{}", order.getOrderNo());
    }

    /**
     *  用户充值手续费记账
     * 		1. 新增手续费登记表
     * 		2. 存在手续费
     * 			1. 新增用户交易明细表
     * 			2. 修改用户余额户金额（-手续费）
     * 			3. 新增用户记账流水表
     * 			4. 修改用户bs_user可提现余额（-手续费）
     * 充值手续费扣除并记账
     */
    public void chargeTopUpCommission(String orderNo, TransTypeEnum transTypeEnum, PayPlatformEnum payPlatformEnum){
        // 用户充值手续费记账
        logger.info("=========【充值交易成功扣除手续费开始】币港湾订单号："+orderNo+"=========");
        BsPayOrdersExample ordersExample = new BsPayOrdersExample();
        ordersExample.createCriteria().andOrderNoEqualTo(orderNo);
        List<BsPayOrders> reguOrderList = payOrderMapper.selectByExample(ordersExample);
        BsPayOrders reguOrder = reguOrderList.get(0);

        CommissionVO commissionVO = commissionService.calServiceFee(reguOrder.getAmount(), transTypeEnum, payPlatformEnum);
        logger.info("=========【充值交易成功扣除手续费】应扣手续费："+commissionVO.getNeedPayAmount()+"；实际扣除手续费："+commissionVO.getActPayAmount()+"===================");

        // 1. 新增手续费登记表
        BsServiceFee bsServiceFee = new BsServiceFee();
        bsServiceFee.setPlanFee(commissionVO.getNeedPayAmount());
        bsServiceFee.setDoneFee(commissionVO.getActPayAmount());
        bsServiceFee.setTransAmount(reguOrder.getAmount());
        bsServiceFee.setFeeType(Constants.FEE_TYPE_FINANCE_TOPUP);
        bsServiceFee.setStatus(Constants.FEE_STATUS_COLLECT);
        bsServiceFee.setCreateTime(new Date());
        bsServiceFee.setOrderNo(reguOrder.getOrderNo());
        bsServiceFee.setSubAccountId(reguOrder.getSubAccountId());
        bsServiceFee.setUpdateTime(new Date());
        bsServiceFee.setNote("应扣"+commissionVO.getNeedPayAmount()+"，实扣"+commissionVO.getActPayAmount());
        bsServiceFee.setPaymentPlatformFee(commissionVO.getThreePartyPaymentServiceFee());
        bsServiceFeeMapper.insertSelective(bsServiceFee);

        // 2. 存在手续费
        if (commissionVO.getActPayAmount()>0) {
            // 2.1. 新增用户交易明细表
            BsUserTransDetail detailFee = new BsUserTransDetail();
            detailFee.setUserId(reguOrder.getUserId());
            detailFee.setCardNo(reguOrder.getBankCardNo());
            detailFee.setTransType(Constants.Trans_TYPE_TOP_UP_FEE);
            detailFee.setTransStatus(Constants.Trans_STATUS_SUCCESS);
            detailFee.setOrderNo(reguOrder.getOrderNo());
            detailFee.setCreateTime(new Date());
            detailFee.setAmount(-commissionVO.getActPayAmount());
            detailFee.setUpdateTime(new Date());
            userTransMapper.insertSelective(detailFee);

            // 2.2. 修改用户余额户金额（-手续费）
            BsSubAccount tempJsh = subAccountMapper.selectSingleSubActByUserAndType(reguOrder.getUserId(), Constants.PRODUCT_TYPE_DEP_JSH);
            BsSubAccount subAccountLockFee = subAccountMapper.selectSubAccountByIdForLock(tempJsh.getId());
            BsSubAccount readySubAccountFee = new BsSubAccount();
            readySubAccountFee.setId(subAccountLockFee.getId());
            readySubAccountFee.setBalance(MoneyUtil.subtract(subAccountLockFee.getBalance(), commissionVO.getActPayAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            readySubAccountFee.setAvailableBalance(MoneyUtil.subtract(subAccountLockFee.getAvailableBalance(), commissionVO.getActPayAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            readySubAccountFee.setCanWithdraw(MoneyUtil.subtract(subAccountLockFee.getCanWithdraw(), commissionVO.getActPayAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            subAccountMapper.updateByPrimaryKeySelective(readySubAccountFee);

            // 2.3. 新增用户记账流水表
            BsAccountJnl accountJnlFee = new BsAccountJnl();
            accountJnlFee.setTransTime(new Date());
            accountJnlFee.setTransCode(Constants.TRANS_TOP_UP_FEE);
            accountJnlFee.setTransName("充值手续费扣除");
            accountJnlFee.setTransAmount(commissionVO.getActPayAmount());
            accountJnlFee.setSysTime(new Date());
            accountJnlFee.setChannelTime(reguOrder.getCreateTime());
            accountJnlFee.setCdFlag1(Constants.CD_FLAG_D);
            accountJnlFee.setUserId1(reguOrder.getUserId());
            accountJnlFee.setAccountId1(subAccountLockFee.getAccountId());
            accountJnlFee.setSubAccountId1(subAccountLockFee.getId());
            accountJnlFee.setSubAccountCode1(subAccountLockFee.getCode());
            accountJnlFee.setBeforeBalance1(subAccountLockFee.getBalance());
            accountJnlFee.setAfterBalance1(readySubAccountFee.getBalance());
            accountJnlFee.setBeforeAvialableBalance1(subAccountLockFee.getAvailableBalance());
            accountJnlFee.setAfterAvialableBalance1(readySubAccountFee.getAvailableBalance());
            accountJnlFee.setBeforeFreezeBalance1(subAccountLockFee.getFreezeBalance());
            accountJnlFee.setAfterFreezeBalance1(subAccountLockFee.getFreezeBalance());
            accountJnlFee.setCdFlag2(Constants.CD_FLAG_C);
            accountJnlFee.setUserId2(reguOrder.getUserId());
            accountJnlFee.setFee(0.0);
            accountJnlMapper.insertSelective(accountJnlFee);

            // 2.4. 修改用户bs_user可提现余额（-手续费）
            BsUser userLockFee = userMapper.selectByPKForLock(reguOrder.getUserId());
            BsUser updateUserFee = new BsUser();
            updateUserFee.setId(userLockFee.getId());
            updateUserFee.setCanWithdraw(MoneyUtil.subtract(userLockFee.getCanWithdraw(), commissionVO.getActPayAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            userMapper.updateByPrimaryKeySelective(updateUserFee);
        }
        logger.info("=========【充值交易成功扣除手续费成功】币港湾订单号："+orderNo+"=========");
    }

    /**
     * 快捷充值预下单、确认下单前置校验，及数据装备
     *  1. 银行信息bankId非空
     *  2. 校验该银行是否存在可用渠道
     *  3. 校验用户是否存在且用户状态是可用
     *  4. 校验该用户是否绑卡（支付签约回执表）
     *  5. 校验用户是否成年
     *  6. 校验用户是否在70岁以下
     *     1. 超过70岁，校验是否存在投资中的产品（所有产品）。如果存在，则允许继续充值
     *  7. 校验该银行渠道是否可用——此处和 2 是否重复，有待校验。
     *  8. 银行单笔限额校验
     *  9. 银行单日限额校验
     *  10. 银行单月限额校验
     *  11. 最低充值金额校验
     *  12. 用户是否绑卡校验（可放到3操作中）
     *  13. 已绑卡用户校验bs_user中的绑卡信息和bs_bank中的信息一致（可放到3操作中）
     *  14. 校验充值金额是否低于手续费
     * @param req
     * @return 充值下单后续业务需要的数据对象，校验不通过则throws异常
     */
    private Map<String, Object> quickPayTopUpPreCheck(ReqMsg_RegularBuy_Order req) {
        // 1. 校验请求数据正确性
        // 1.0. 恒丰日切时间，禁止充值提现操作
        BsSysConfig startCongig = sysMapper.selectByPrimaryKey(Constants.HF_BANK_CUT_DAY_START_TIME);
        BsSysConfig endCongig = sysMapper.selectByPrimaryKey(Constants.HF_BANK_CUT_DAY_END_TIME);
        String hfStartTime = null == startCongig ? null : (StringUtil.isBlank(startCongig.getConfValue()) ? null : startCongig.getConfValue());
        String hfEndTime = null == endCongig ? null : (StringUtil.isBlank(endCongig.getConfValue()) ? null : endCongig.getConfValue());
        Date today = new Date();
        logger.info("恒丰日切开始时间：{}，结束时间：{}。当前时间：{}", hfStartTime, hfEndTime, com.pinting.business.util.DateUtil.format(today, "yyyy-MM-dd HH:mm:ss"));
        if(hfStartTime != null && hfEndTime != null) {
            if(com.pinting.business.util.DateUtil.betweenHHMM(hfStartTime, hfEndTime, today, true)) {
                throw new PTMessageException(PTMessageEnum.HF_IN_MAINTENANCE.getCode(), hfStartTime + "--" + hfEndTime + "为银行维护时间，请稍后再试");
            }
        }

        // 1.1. 银行信息bankId非空
        if(req.getBankId() == null) {
            throw new PTMessageException(PTMessageEnum.BANK_IS_NOT_EXIST);
        }
        // 1.2. 校验该银行是否存在可用渠道
        Bs19payBank bank = bankCardService.selectChannelBankInfo(req.getUserId(), req.getBankId());
        if(bank.getId() == null) {
            throw new PTMessageException(PTMessageEnum.BANK_IS_NOT_EXIST);
        }

        // 1.3. 校验用户是否存在且用户状态是可用
        BsUser bsUser = userMapper.selectByPrimaryKey(req.getUserId());
        if(!(bsUser != null && bsUser.getStatus() == Constants.USER_STATUS_1)) {
            throw new PTMessageException(PTMessageEnum.USER_NO_EXIT);
        }

        // 1.4. 校验该用户是否绑卡（支付签约回执表）
        BsPayReceiptExample bsPayReceiptExample = new BsPayReceiptExample();
        bsPayReceiptExample.createCriteria().andChannelEqualTo(Constants.CHANNEL_HFBANK).andUserIdEqualTo(req.getUserId()).andIsBindCardEqualTo(Constants.BAOFOO_BIND_YES);
        int count = bsPayReceiptMapper.countByExample(bsPayReceiptExample);
        if(count <= 0) {
            throw new PTMessageException(PTMessageEnum.BIND_BANK_CARD_ERROR);
        }

        // 1.5. 校验用户是否成年
        if(!IdcardUtils.isAdultAge(bsUser.getIdCard())){
            throw new PTMessageException(PTMessageEnum.USER_NOT_ADULT);
        }
        // 1.6. 校验用户是否在70岁以下
        // 1.6.1 超过70岁，校验是否存在投资中的产品（所有产品）。如果存在，则允许继续充值
        if(IdcardUtils.isOldAge(bsUser.getIdCard(), Constants.OLD_MAN_AGE_LIMIT)) {
            BsAccountExample accountExample = new BsAccountExample();
            accountExample.createCriteria().andUserIdEqualTo(bsUser.getId());
            List<BsAccount> bsAccounts = accountMapper.selectByExample(accountExample);
            BsSubAccountExample subActExample = new BsSubAccountExample();
            List<Integer> statusList = new ArrayList<>();
            statusList.add(Constants.SUBACCOUNT_STATUS_OPEN);
            statusList.add(Constants.SUBACCOUNT_STATUS_SETTLE);
            statusList.add(Constants.SUBACCOUNT_STATUS_CLOSE);
            List<String> productTypeList = new ArrayList<>();
            productTypeList.add(Constants.PRODUCT_TYPE_REG);
            productTypeList.add(Constants.PRODUCT_TYPE_AUTH);
            productTypeList.add(Constants.PRODUCT_TYPE_AUTH_YUN);
            productTypeList.add(Constants.PRODUCT_TYPE_AUTH_7);
            productTypeList.add(Constants.PRODUCT_TYPE_AUTH_ZSD);
            productTypeList.add(Constants.PRODUCT_TYPE_AUTH_FREE);
            subActExample.createCriteria().andProductTypeIn(productTypeList).andStatusNotIn(statusList).andAccountIdEqualTo(bsAccounts.get(0).getId());
            int buyCount = subAccountMapper.countByExample(subActExample);
            if(buyCount <= 0) {
                throw new PTMessageException(PTMessageEnum.USER_IS_OLD_MAN);
            }
        }

        // 1.7. 校验该银行渠道是否可用——此处和 2 是否重复，有待校验。
        Double amount = req.getAmount();
        Date now = new Date();
        Date start = bank.getForbiddenStart();
        Date end = bank.getForbiddenEnd();
        if(null != start && null != end) {
            int before = com.pinting.business.util.DateUtil.compareTo(now, start);
            int after = com.pinting.business.util.DateUtil.compareTo(now, end);
            if(!(before < 0 || after > 0) || Constants.IS_AVAILABLE_DISABLE == bank.getIsAvailable()) {
                throw new PTMessageException(PTMessageEnum.BANK_CHANNEL_LOSE);
            }
        } else if(null != start && null == end) {
            int before = com.pinting.business.util.DateUtil.compareTo(now, start);
            if(before >= 0 || Constants.IS_AVAILABLE_DISABLE == bank.getIsAvailable()) {
                throw new PTMessageException(PTMessageEnum.BANK_CHANNEL_LOSE);
            }
        } else if(null == start && null != end) {
            int after = com.pinting.business.util.DateUtil.compareTo(now, end);
            if(after <= 0 || Constants.IS_AVAILABLE_DISABLE == bank.getIsAvailable()) {
                throw new PTMessageException(PTMessageEnum.BANK_CHANNEL_LOSE);
            }
        } else if(null == start && null == end) {
            if(Constants.IS_AVAILABLE_DISABLE == bank.getIsAvailable()) {
                throw new PTMessageException(PTMessageEnum.BANK_CHANNEL_LOSE);
            }
        }

        // 1.8. 银行单笔限额校验
        if(amount > bank.getOneTop()) {
            throw new PTMessageException(PTMessageEnum.PASS_ONETOP_LIMIT);
        }
        // 1.9. 银行单日限额校验
        String startDay = DateUtil.format(now, "yyyy-MM-dd")+" 00:00:00";
        String endDay = DateUtil.format(now, "yyyy-MM-dd")+" 23:59:59";
        Double totalDay = payOrderMapper.calculateTotal(Constants.CHANNEL_TRS_QUICKPAY, startDay, endDay, req.getBankId(), req.getUserId());
        if(totalDay == null) {
            totalDay = 0.0;
        }
        logger.info("充值金额：" + amount + "；当前用户当日充值总金额：" + totalDay + "；银行单日限额：" + bank.getDayTop());
        int compare = MoneyUtil.add(amount, totalDay).compareTo(new BigDecimal(String.valueOf(bank.getDayTop())));
        logger.info(compare < 1 ? "没有超过单日限额" : "超过单日限额");
        if(MoneyUtil.add(amount, totalDay).compareTo(new BigDecimal(String.valueOf(bank.getDayTop()))) == 1) {
            throw new PTMessageException(PTMessageEnum.PASS_DAYTOP_LIMIT);
        }

        // 1.10. 银行单月限额校验
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        String startMonth = DateUtil.format(calendar.getTime(), "yyyy-MM-dd")+" 00:00:00";
        calendar.roll(Calendar.DAY_OF_MONTH, -1);
        String endMonth = DateUtil.format(calendar.getTime(), "yyyy-MM-dd")+" 23:59:59";
        Double totalMonth = payOrderMapper.calculateTotal(Constants.CHANNEL_TRS_QUICKPAY, startMonth, endMonth, req.getBankId(), req.getUserId());
        if(totalMonth == null) {
            totalMonth = 0.0;
        }
        if(MoneyUtil.add(amount, totalMonth).compareTo(new BigDecimal(String.valueOf(bank.getMonthTop()))) == 1) {
            throw new PTMessageException(PTMessageEnum.PASS_MONTHTOP_LIMIT);
        }

        // 1.11. 最低充值金额校验
        BsSysConfig sys = sysMapper.selectByPrimaryKey(Constants.RECHANGE_LIMIT);
        if(Double.parseDouble(sys.getConfValue()) > req.getAmount()) {
            throw new PTMessageException(PTMessageEnum.RECHANGE_AMOUNT_IS_NOT_PASS.getCode(), "充值金额不能低于" + sys.getConfValue() + "元");
        }

        if(bsUser.getIsBindBank() == Constants.IS_BIND_BANK_NO || bsUser.getIsBindName() == Constants.IS_BIND_NAME_NO) {
            // 1.12. 用户是否绑卡校验（可放到3操作中）
            throw new PTMessageException(PTMessageEnum.BIND_BANK_CARD_ERROR);
        } else {
            // 1.13. 已绑卡用户校验bs_user中的绑卡信息和bs_bank中的信息一致（可放到3操作中）
            BsBankCardExample bbcExample = new BsBankCardExample();
            bbcExample.createCriteria().andUserIdEqualTo(req.getUserId()).andCardNoEqualTo(req.getCardNo()).andStatusEqualTo(Constants.BANK_CARD_NORMAL).andIsFirstEqualTo(Constants.BANK_CARD_USERED);
            List<BsBankCard> bbcList = bankCardMapper.selectByExample(bbcExample);
            if(CollectionUtils.isEmpty(bbcList)) {
                throw new PTMessageException(PTMessageEnum.USER_BINDCARD_IS_ERROR);
            }
        }
        // 1.14. 校验充值金额是否低于手续费
        CommissionVO commissionVO = commissionService.calServiceFee(req.getAmount(), TransTypeEnum.USER_TOP_UP_QUICK_PAY, null);
        if(req.getAmount().compareTo(commissionVO.getActPayAmount()) <= 0) {
            throw new PTMessageException(PTMessageEnum.TOP_UP_LESS_THAN_FEE);
        }

        // 是否恒丰开户
        BsHfbankUserExtExample extExample = new BsHfbankUserExtExample();
        extExample.createCriteria().andUserIdEqualTo(req.getUserId()).andStatusEqualTo(Constants.HFBANK_USER_EXT_STATUS_OPEN);
        List<BsHfbankUserExt> ext = bsHfbankUserExtMapper.selectByExample(extExample);
        if (org.apache.commons.collections.CollectionUtils.isEmpty(ext)) {
            throw new PTMessageException(PTMessageEnum.HF_NOT_BIND);
        }

        String riskStatus = assetsService.riskStatus(req.getUserId());
        if(Constants.IS_NO.equals(riskStatus)) {
            throw new PTMessageException(PTMessageEnum.NO_ASSESSMENT);
        } else if(Constants.IS_EXPIRE.equals(riskStatus)) {
            throw new PTMessageException(PTMessageEnum.ASSESSMENT_EXPIRE);
        }

        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("bank", bank);
        returnMap.put("ext", ext.get(0));
        return returnMap;
    }

    /**
     * 用户网银充值前置校验
     * 1. 银行信息bankId非空
     * 2. 校验用户是否存在且用户状态是可用
     * 3. 校验用户是否成年
     * 4. 校验用户是否在70岁以下
     *  4.1. 超过70岁，校验是否存在投资中的产品（所有产品）。如果存在，则允许继续充值
     * 5. 用户是否绑卡校验（可放到3操作中）
     * 6. 最低充值金额校验
     * 7. 判断订单对应的用户子账户DEP_JSH是否存在
     * 8. 判断是否存在该银行通道
     * 9. 是否恒丰开户
     * @param req
     * @return
     */
    private Map<String, Object> eBankTopUpPreCheck(ReqMsg_RegularBuy_EBankBuy req) {
        // 1.0. 恒丰日切时间，禁止充值提现操作
        BsSysConfig startCongig = sysMapper.selectByPrimaryKey(Constants.HF_BANK_CUT_DAY_START_TIME);
        BsSysConfig endCongig = sysMapper.selectByPrimaryKey(Constants.HF_BANK_CUT_DAY_END_TIME);
        String hfStartTime = null == startCongig ? null : (StringUtil.isBlank(startCongig.getConfValue()) ? null : startCongig.getConfValue());
        String hfEndTime = null == endCongig ? null : (StringUtil.isBlank(endCongig.getConfValue()) ? null : endCongig.getConfValue());
        Date today = new Date();
        logger.info("恒丰日切开始时间：{}，结束时间：{}。当前时间：{}", hfStartTime, hfEndTime, com.pinting.business.util.DateUtil.format(today, "yyyy-MM-dd HH:mm:ss"));
        if(hfStartTime != null && hfEndTime != null) {
            if(com.pinting.business.util.DateUtil.betweenHHMM(hfStartTime, hfEndTime, today, true)) {
                throw new PTMessageException(PTMessageEnum.HF_IN_MAINTENANCE.getCode(), hfStartTime + "--" + hfEndTime + "为银行维护时间，请稍后再试");
            }
        }

        // 1. 银行信息bankId非空
        if(StringUtil.isBlank(req.getBankCode())) {
            throw new PTMessageException(PTMessageEnum.BANK_IS_NOT_EXIST);
        }

        // 2. 校验用户是否存在且用户状态是可用
        BsUser user = userMapper.selectByPrimaryKey(req.getUserId());
        if(!(user != null && user.getStatus() == Constants.USER_STATUS_1)) {
            throw new PTMessageException(PTMessageEnum.USER_NO_EXIT);
        }

        // 3. 校验用户是否成年
        if(!IdcardUtils.isAdultAge(user.getIdCard())){
            throw new PTMessageException(PTMessageEnum.USER_NOT_ADULT);
        }

        // 4. 校验用户是否在70岁以下
        //     4.1. 超过70岁，校验是否存在投资中的产品（所有产品）。如果存在，则允许继续充值
        if(IdcardUtils.isOldAge(user.getIdCard(), Constants.OLD_MAN_AGE_LIMIT)) {
            BsAccountExample accountExample = new BsAccountExample();
            accountExample.createCriteria().andUserIdEqualTo(user.getId());
            List<BsAccount> bsAccounts = accountMapper.selectByExample(accountExample);
            BsSubAccountExample subActExample = new BsSubAccountExample();
            List<Integer> statusList = new ArrayList<>();
            statusList.add(Constants.SUBACCOUNT_STATUS_OPEN);
            statusList.add(Constants.SUBACCOUNT_STATUS_SETTLE);
            statusList.add(Constants.SUBACCOUNT_STATUS_CLOSE);
            List<String> productTypeList = new ArrayList<>();
            productTypeList.add(Constants.PRODUCT_TYPE_REG);
            productTypeList.add(Constants.PRODUCT_TYPE_AUTH);
            productTypeList.add(Constants.PRODUCT_TYPE_AUTH_YUN);
            productTypeList.add(Constants.PRODUCT_TYPE_AUTH_7);
            productTypeList.add(Constants.PRODUCT_TYPE_AUTH_ZSD);
            subActExample.createCriteria().andProductTypeIn(productTypeList).andStatusNotIn(statusList).andAccountIdEqualTo(bsAccounts.get(0).getId());
            int buyCount = subAccountMapper.countByExample(subActExample);
            if(buyCount <= 0) {
                throw new PTMessageException(PTMessageEnum.USER_IS_OLD_MAN);
            }
        }

        // 5. 用户是否绑卡校验（可放到3操作中）
        if(user.getIsBindBank() == Constants.IS_BIND_BANK_NO || user.getIsBindName() == Constants.IS_BIND_NAME_NO) {
            throw new PTMessageException(PTMessageEnum.BANKCARD_NOT_BIND);
        }

        // 6. 最低充值金额校验
        BsSysConfig sys = sysMapper.selectByPrimaryKey(Constants.RECHANGE_LIMIT);
        if(Double.parseDouble(sys.getConfValue()) > req.getAmount()) {
            throw new PTMessageException(PTMessageEnum.RECHANGE_AMOUNT_IS_NOT_PASS.getCode(), "充值金额不能低于" + sys.getConfValue() + "元");
        }

        // 7. 判断订单对应的用户子账户DEP_JSH是否存在
        final List<BsSubAccount> subList = subAccountMapper.selectSubAccount(req.getUserId(), Constants.SYS_ACCOUNT_DEP_JSH, Constants.SUBACCOUNT_STATUS_OPEN);
        if(CollectionUtils.isEmpty(subList)) {
            throw new PTMessageException(PTMessageEnum.USER_JSH_NO_EXIT);
        }

        // 8. 判断是否存在该银行通道
        Bs19payBank bs19payBank = payBankMapper.selectByPrimaryKey(Integer.parseInt(req.getBankCode()));
        BsBank bsBank = null;
        if (null != bs19payBank) {
            bsBank = bsBankMapper.selectByPrimaryKey(bs19payBank.getBankId());
        }
        if(null == bsBank) {
            throw new PTMessageException(PTMessageEnum.BANK_IS_NOT_EXIST);
        }

        // 9. 是否恒丰开户
        BsHfbankUserExtExample extExample = new BsHfbankUserExtExample();
        extExample.createCriteria().andUserIdEqualTo(req.getUserId()).andStatusEqualTo(Constants.HFBANK_USER_EXT_STATUS_OPEN);
        List<BsHfbankUserExt> ext = bsHfbankUserExtMapper.selectByExample(extExample);
        if (org.apache.commons.collections.CollectionUtils.isEmpty(ext)) {
            throw new PTMessageException(PTMessageEnum.HF_NOT_BIND);
        }

        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("subAccount", subList.get(0));
        returnMap.put("user", user);
        returnMap.put("bsBank", bsBank);
        returnMap.put("bs19payBank", bs19payBank);
        returnMap.put("ext", ext.get(0));
        return returnMap;
    }

    /**
     * 充值通知前置校验，及数据装备
     * 1. 判断订单是否存在
     * 		1. 快捷支付处理中的订单
     * 		2. 网银支付初始状态的订单
     * 2. 判断用户是否存在
     * 3. 判断用户主账户是否存在
     * 4. 判断订单对应的用户子账户DEP_JSH是否存在
     * @param req
     * @return 充值通知后续业务需要的数据对象，校验不通过则throws异常
     */
    private Map<String, Object> notifyPreCheck(QuickPayResultInfo req) {
        // 1. 判断订单是否存在
        // 1.1. 快捷支付处理中的订单
        BsPayOrdersExample quickOrderExample = new BsPayOrdersExample();
        quickOrderExample.createCriteria().andOrderNoEqualTo(req.getOrderId())
                .andStatusEqualTo(Constants.ORDER_STATUS_PAYING).andChannelTransTypeEqualTo(Constants.CHANNEL_TRS_QUICKPAY);
        List<BsPayOrders> quickOrderList = payOrderMapper.selectByExample(quickOrderExample);
        // 1.2. 网银支付初始状态的订单
        BsPayOrdersExample eBankOrderExample = new BsPayOrdersExample();
        eBankOrderExample.createCriteria().andOrderNoEqualTo(req.getOrderId())
                .andStatusEqualTo(Constants.ORDER_STATUS_CREATE).andChannelTransTypeEqualTo(Constants.CHANNEL_TRS_NETBANK);
        List<BsPayOrders> ebankOrderList = payOrderMapper.selectByExample(eBankOrderExample);
        List<BsPayOrders> orderList = new ArrayList<>();
        orderList.addAll(quickOrderList);
        orderList.addAll(ebankOrderList);
        if(CollectionUtils.isEmpty(orderList)) {
            throw new PTMessageException(PTMessageEnum.PAYMENT_ORDER_NOT_EXIST);
        }
        final BsPayOrders order = orderList.get(0);
        Integer userId = order.getUserId();
        // 2. 判断用户是否存在
        final BsUser user = userMapper.selectByPrimaryKey(userId);
        if(!(user != null && user.getStatus() == Constants.USER_STATUS_1)) {
            throw new PTMessageException(PTMessageEnum.USER_NO_EXIT);
        }

        // 3. 判断用户主账户是否存在
        BsAccountExample accExample = new BsAccountExample();
        accExample.createCriteria().andUserIdEqualTo(order.getUserId());
        List<BsAccount> accList = accountMapper.selectByExample(accExample);
        if(CollectionUtils.isEmpty(accList)) {
            throw new PTMessageException(PTMessageEnum.ACCOUNT_NO_EXIT);
        }
        final BsAccount account = accList.get(0);

        // 4. 判断订单对应的用户子账户DEP_JSH是否存在
        final BsSubAccount subAccount = subAccountMapper.selectByPrimaryKey(order.getSubAccountId());
        if(subAccount == null) {
            throw new PTMessageException(PTMessageEnum.SUB_ACCOUNT_NO_EXIT);
        }
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("order", order);
        returnMap.put("user", user);
        returnMap.put("account", account);
        returnMap.put("subAccount", subAccount);
        return returnMap;
    }

    /**
     * 充值成功普通用户记账
     */
    private void userAccount(QuickPayResultInfo req, BsPayOrders order, BsAccount account) {
        // 1.4. 修改用户余额户金额+   PRODUCT_TYPE_DEP_JSH
        BsSubAccount tempJsh = subAccountMapper.selectSingleSubActByUserAndType(order.getUserId(),Constants.PRODUCT_TYPE_DEP_JSH);
        BsSubAccount subAccountLock = subAccountMapper.selectSubAccountByIdForLock(tempJsh.getId());
        BsSubAccount readySubAccount = new BsSubAccount();
        readySubAccount.setId(subAccountLock.getId());
        readySubAccount.setBalance(MoneyUtil.add(subAccountLock.getBalance(), order.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        readySubAccount.setAvailableBalance(MoneyUtil.add(subAccountLock.getAvailableBalance(), order.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        readySubAccount.setCanWithdraw(MoneyUtil.add(subAccountLock.getCanWithdraw(), order.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        subAccountMapper.updateByPrimaryKeySelective(readySubAccount);

        // 1.5. 新增用户记账流水表
        BsAccountJnl accountJnl = new BsAccountJnl();
        accountJnl.setTransTime(new Date());
        accountJnl.setTransCode(Constants.TRANS_TOP_UP);
        accountJnl.setTransName("充值");
        accountJnl.setTransAmount(order.getAmount());
        accountJnl.setSysTime(new Date());
        accountJnl.setChannelTime(req.getOrderFinTime());
        accountJnl.setChannelJnlNo(req.getMpOrderId()); //支付平台订单号
        accountJnl.setCdFlag1(Constants.CD_FLAG_D);
        accountJnl.setUserId1(order.getUserId());
        accountJnl.setAccountId1(account.getId());
        accountJnl.setSubAccountId1(subAccountLock.getId());
        accountJnl.setSubAccountCode1(subAccountLock.getCode());
        accountJnl.setBeforeBalance1(subAccountLock.getBalance());
        accountJnl.setAfterBalance1(readySubAccount.getBalance());
        accountJnl.setBeforeAvialableBalance1(subAccountLock.getAvailableBalance());
        accountJnl.setAfterAvialableBalance1(readySubAccount.getAvailableBalance());
        accountJnl.setBeforeFreezeBalance1(subAccountLock.getFreezeBalance());
        accountJnl.setAfterFreezeBalance1(subAccountLock.getFreezeBalance());
        accountJnl.setCdFlag2(Constants.CD_FLAG_C);
        accountJnl.setUserId2(order.getUserId());
        accountJnl.setFee(0.0);
        accountJnlMapper.insertSelective(accountJnl);

        // 1.6. 修改平台用户余额户+    SYS_ACCOUNT_BGW_USER
        BsSysSubAccount sysSubAccountLock = bsSysSubAccountService.findSysSubAccount4Lock(Constants.SYS_ACCOUNT_BGW_USER);
        BsSysSubAccount readyUpdate = new BsSysSubAccount();
        readyUpdate.setId(sysSubAccountLock.getId());
        readyUpdate.setBalance(MoneyUtil.add(sysSubAccountLock.getBalance(), order.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        readyUpdate.setAvailableBalance(MoneyUtil.add(sysSubAccountLock.getAvailableBalance(), order.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        readyUpdate.setCanWithdraw(MoneyUtil.add(sysSubAccountLock.getCanWithdraw(), order.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        sysAccountMapper.updateByPrimaryKeySelective(readyUpdate);

        // 1.7. 新增系统记账流水表
        BsSysAccountJnl sysAccountJnl = new BsSysAccountJnl();
        sysAccountJnl.setTransTime(new Date());
        sysAccountJnl.setTransCode(Constants.SYS_USER_TOP_UP);
        sysAccountJnl.setTransName("用户充值");
        sysAccountJnl.setTransAmount(order.getAmount());
        sysAccountJnl.setSysTime(new Date());
        sysAccountJnl.setChannelTime(req.getOrderFinTime());
        sysAccountJnl.setChannelJnlNo(req.getMpOrderId());
        sysAccountJnl.setCdFlag2(Constants.CD_FLAG_D);
        sysAccountJnl.setSubAccountCode2(sysSubAccountLock.getCode());
        sysAccountJnl.setBeforeBalance2(sysSubAccountLock.getBalance());
        sysAccountJnl.setAfterBalance2(readyUpdate.getBalance());
        sysAccountJnl.setBeforeAvialableBalance2(sysSubAccountLock.getAvailableBalance());
        sysAccountJnl.setAfterAvialableBalance2(readyUpdate.getAvailableBalance());
        sysAccountJnl.setBeforeFreezeBalance2(sysSubAccountLock.getFreezeBalance());
        sysAccountJnl.setAfterFreezeBalance2(sysSubAccountLock.getFreezeBalance());
        sysAccountJnl.setFee(0.0);
        sysAccountJnl.setStatus(Constants.SYS_ACCOUNT_STATUS_SUCCESS);
        sysAccountJnlMapper.insertSelective(sysAccountJnl);
    }

    /**
     * 充值成功赞分期代偿人记账
     */
    private void compensatoryAccount(QuickPayResultInfo req, BsPayOrders order, BsAccount account) {
        // 1.4. 修改用户余额户金额+   PRODUCT_TYPE_DEP_JSH
        BsSubAccount tempJsh = subAccountMapper.selectSingleSubActByUserAndType(order.getUserId(),Constants.PRODUCT_TYPE_DEP_JSH);
        BsSubAccount subAccountLock = subAccountMapper.selectSubAccountByIdForLock(tempJsh.getId());
        BsSubAccount readySubAccount = new BsSubAccount();
        readySubAccount.setId(subAccountLock.getId());
        readySubAccount.setBalance(MoneyUtil.add(subAccountLock.getBalance(), order.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        readySubAccount.setFreezeBalance(MoneyUtil.add(subAccountLock.getFreezeBalance(), order.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        subAccountMapper.updateByPrimaryKeySelective(readySubAccount);

        // 1.5. 新增用户记账流水表
        BsAccountJnl accountJnl = new BsAccountJnl();
        accountJnl.setTransTime(new Date());
        accountJnl.setTransCode(Constants.TRANS_TOP_UP);
        accountJnl.setTransName("赞分期代偿人充值");
        accountJnl.setTransAmount(order.getAmount());
        accountJnl.setSysTime(new Date());
        accountJnl.setChannelTime(req.getOrderFinTime());
        accountJnl.setChannelJnlNo(req.getMpOrderId()); //支付平台订单号
        accountJnl.setCdFlag1(Constants.CD_FLAG_D);
        accountJnl.setUserId1(order.getUserId());
        accountJnl.setAccountId1(account.getId());
        accountJnl.setSubAccountId1(subAccountLock.getId());
        accountJnl.setSubAccountCode1(subAccountLock.getCode());
        accountJnl.setBeforeBalance1(subAccountLock.getBalance());
        accountJnl.setAfterBalance1(readySubAccount.getBalance());
        accountJnl.setBeforeAvialableBalance1(subAccountLock.getAvailableBalance());
        accountJnl.setAfterAvialableBalance1(subAccountLock.getAvailableBalance());
        accountJnl.setBeforeFreezeBalance1(subAccountLock.getFreezeBalance());
        accountJnl.setAfterFreezeBalance1(readySubAccount.getFreezeBalance());
        accountJnl.setCdFlag2(Constants.CD_FLAG_C);
        accountJnl.setUserId2(order.getUserId());
        accountJnl.setFee(0.0);
        accountJnlMapper.insertSelective(accountJnl);

        // 1.6. 修改平台用户余额户+    SYS_ACCOUNT_BGW_USER
        BsSysSubAccount sysSubAccountLock = bsSysSubAccountService.findSysSubAccount4Lock(Constants.SYS_ACCOUNT_BGW_USER);
        BsSysSubAccount readyUpdate = new BsSysSubAccount();
        readyUpdate.setId(sysSubAccountLock.getId());
        readyUpdate.setBalance(MoneyUtil.add(sysSubAccountLock.getBalance(), order.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        readyUpdate.setFreezeBalance(MoneyUtil.add(sysSubAccountLock.getFreezeBalance(), order.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        sysAccountMapper.updateByPrimaryKeySelective(readyUpdate);

        // 1.7. 新增系统记账流水表
        BsSysAccountJnl sysAccountJnl = new BsSysAccountJnl();
        sysAccountJnl.setTransTime(new Date());
        sysAccountJnl.setTransCode(Constants.SYS_USER_TOP_UP);
        sysAccountJnl.setTransName("赞分期代偿人充值");
        sysAccountJnl.setTransAmount(order.getAmount());
        sysAccountJnl.setSysTime(new Date());
        sysAccountJnl.setChannelTime(req.getOrderFinTime());
        sysAccountJnl.setChannelJnlNo(req.getMpOrderId());
        sysAccountJnl.setCdFlag2(Constants.CD_FLAG_D);
        sysAccountJnl.setSubAccountCode2(sysSubAccountLock.getCode());
        sysAccountJnl.setBeforeBalance2(sysSubAccountLock.getBalance());
        sysAccountJnl.setAfterBalance2(readyUpdate.getBalance());
        sysAccountJnl.setBeforeAvialableBalance2(sysSubAccountLock.getAvailableBalance());
        sysAccountJnl.setAfterAvialableBalance2(sysSubAccountLock.getAvailableBalance());
        sysAccountJnl.setBeforeFreezeBalance2(sysSubAccountLock.getFreezeBalance());
        sysAccountJnl.setAfterFreezeBalance2(readyUpdate.getFreezeBalance());
        sysAccountJnl.setFee(0.0);
        sysAccountJnl.setStatus(Constants.SYS_ACCOUNT_STATUS_SUCCESS);
        sysAccountJnlMapper.insertSelective(sysAccountJnl);
    }

    /**
     * 查询用户手机号
     * @param userId
     * @return
     */
    private String getMobile(Integer userId) {
        BsUser user = bsUserService.findUserByUserId(userId);
        if(user != null){
            return user.getMobile();
        }else{
            return null;
        }

    }

    /**
     * 是否赞分期代偿人
     * @param userId
     * @return
     */
    private boolean isCompensatoryUserZAN(Integer userId) {
        boolean isCompensatoryUser = false;
        BsSysConfig supers = sysMapper.selectByPrimaryKey(Constants.ZAN_COMPENSATE_USER_ID);
        List<Integer> superUserIds = new ArrayList<>();
        if (supers != null) {
            String[] userStr = supers.getConfValue().split(",");
            for (String string : userStr) {
                superUserIds.add(Integer.parseInt(string));
            }
        }
        if(!org.apache.commons.collections.CollectionUtils.isEmpty(superUserIds)) {
            for (Integer superUserId : superUserIds) {
                if(superUserId.equals(userId)) {
                    isCompensatoryUser = true;
                    break;
                }
            }
        }
        return isCompensatoryUser;
    }


}

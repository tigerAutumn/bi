package com.pinting.business.accounting.finance.service.impl;

import com.pinting.business.accounting.finance.model.EBankResultInfo;
import com.pinting.business.accounting.finance.model.QuickPayResultInfo;
import com.pinting.business.accounting.finance.service.UserTopUpService;
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
import com.pinting.gateway.hessian.message.baofoo.*;
import com.pinting.gateway.hessian.message.pay19.B2GReqMsg_QuickPay_ConfirmOrder;
import com.pinting.gateway.hessian.message.pay19.B2GReqMsg_QuickPay_PreOrder;
import com.pinting.gateway.hessian.message.pay19.B2GResMsg_QuickPay_ConfirmOrder;
import com.pinting.gateway.hessian.message.pay19.B2GResMsg_QuickPay_PreOrder;
import com.pinting.gateway.hessian.message.pay19.enums.*;
import com.pinting.gateway.hessian.message.reapal.*;
import com.pinting.gateway.in.util.MethodRole;
import com.pinting.gateway.out.service.*;
import com.pinting.gateway.smsEnums.TemplateKey;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by babyshark on 2016/9/10.
 */
@Service
public class UserTopUpServiceImpl implements UserTopUpService {
    private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);

    private final Logger logger = LoggerFactory.getLogger(UserTopUpServiceImpl.class);

    @Autowired
    private Pay19TransportService pay19TransportService;
    @Autowired
    private BaoFooTransportService baoFooTransportService;
    @Autowired
    private NetBankService netBankService;
    @Autowired
    private Bs19payBankMapper payBankMapper;
    @Autowired
    private BsPayOrdersMapper payOrderMapper;
    @Autowired
    private BsSysConfigMapper sysMapper;
    @Autowired
    private BsProductMapper proMapper;
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
    private Bs19payBankMapper pay19Mapper;
    @Autowired
    private SMSServiceClient smsServiceClient;
    @Autowired
    private BsBankMapper  bsBankMapper;
    @Autowired
    private SendWechatService sendWechatService;
    @Autowired
    private BankCardService bankCardService;
    @Autowired
    private BsPayReceiptMapper receiptMapper;
    @Autowired
    private ReapalTransportService reapalService;
    @Autowired
    private BsHelpChannelAccountMapper helpChannelMapper;
    @Autowired
    private BsPayReceiptMapper bsPayReceiptMapper;
    @Autowired
    private BsServiceFeeMapper bsServiceFeeMapper;
    @Autowired
    private Bs19PayBankService bs19PayBankService;
    @Autowired
    private BsPayResultQueueMapper queueMapper;
    @Autowired
    private CommissionService commissionService;
    @Autowired
    private OrderBusinessService orderBusinessService;
    @Autowired
    private BsUserService bsUserService;
    @Autowired
    private PayOrdersService payOrdersService;
    @Autowired
    private BsSysSubAccountService bsSysSubAccountService;

    /**
     * 充值预下单（快捷）
     *
     * @param req 用户ID、充值金额、银行卡号、预留手机号、身份证、持卡人姓名、银行名称、银行ID、终端类型@1手机端,2PC端
     * @param res 用于结果返回
     */
    @Override
    @MethodRole("APP")
    public void pre(ReqMsg_RegularBuy_Order req, ResMsg_RegularBuy_Order res) {
        Map<String, Object> returnMap = topUpPreCheck(req);
        Bs19payBank bank = (Bs19payBank) returnMap.get("bank");
        Date now = new Date();
        //==========================================验证结束============================================
        //==========================================预下单=============================================
        //新增订单表
        BsPayOrders order = new BsPayOrders();
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
        //新增订单流水表
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

        //宝付预下单
        if(Constants.CHANNEL_BAOFOO.equals(bank.getChannel())) {
            baofooPreTopUp(order, res);
        }
        //19付预下单
        else if(Constants.CHANNEL_PAY19.equals(bank.getChannel())) {
            pay19PreOrder(req,res,bank,order);
        }
        //融宝预下单
        else if(Constants.CHANNEL_REAPAL.equals(bank.getChannel())) {
            BsPayReceiptExample receiptExample = new BsPayReceiptExample();
            receiptExample.createCriteria().andUserIdEqualTo(req.getUserId()).andBankCardNoEqualTo(req.getCardNo())
                    .andChannelEqualTo(Constants.CHANNEL_REAPAL).andIsBindCardEqualTo(Constants.REAPAL_BIND_YES);
            List<BsPayReceipt> receiptList = receiptMapper.selectByExample(receiptExample);
            //调用绑卡签约接口
            if(!CollectionUtils.isEmpty(receiptList)) {
                reapalBindPreOrder(req,res,receiptList.get(0).getReceiptNo(),order);
            }
            //调用储蓄卡签约接口
            else {
                reapalMemoryPreOrder(req,res,bank,order);
            }
        }
    }
    /**
     * 充值正式下单（快捷）
     *
     * @param req 请求信息
     * @param res 用于结果返回
     */
    @Override
    @MethodRole("APP")
    public void confirm(ReqMsg_RegularBuy_Order req, ResMsg_RegularBuy_Order res) {
        Map<String, Object> returnMap = topUpPreCheck(req);
        Bs19payBank bank = (Bs19payBank) returnMap.get("bank");
        //==========================================验证结束============================================
        //==========================================预下单=============================================
        if(Constants.CHANNEL_PAY19.equals(bank.getChannel())) {
            //19付正式下单
            pay19ConfirmOrder(req);
        } else if(Constants.CHANNEL_REAPAL.equals(bank.getChannel())) {
            //融宝正式下单
            reapalConfirmOrder(req);
        } else if(Constants.CHANNEL_BAOFOO.equals(bank.getChannel())) {
            // 宝付充值正式下单
            baofooConfirmTopUp(req, res);
        }
    }
    /**
     * 充值通知（快捷）
     *
     * @param req 请求信息
     */
    @Override
    public void notify(QuickPayResultInfo req) {
        try {
            logger.info("快捷充值通知开始，请求信息：{}", JSONObject.fromObject(req));
            jsClientDaoSupport.lock(RedisLockEnum.LOCK_USER_TOPUP.getKey());
            Map<String, Object> returnMap = notifyPreCheck(req);
            BsPayOrders order = (BsPayOrders) returnMap.get("order");
            if(Constants.CHANNEL_BAOFOO.equals(order.getChannel())){
                //主通道快捷充值通知
                logger.info("主通道快捷充值通知开始，请求信息：{}", JSONObject.fromObject(req));
                baofooNotify(req, returnMap);
            }else{
                //辅助通道快捷充值通知
                logger.info("辅助通道快捷充值通知开始，请求信息：{}", JSONObject.fromObject(req));
                G2BReqMsg_ReapalQuickPay_ReapalNotify assistReq = new G2BReqMsg_ReapalQuickPay_ReapalNotify();
                assistReq.setTradeNo(req.getMpOrderId());
                assistReq.setResultCode(req.getErrorCode());
                assistReq.setResultMsg(req.getErrorMsg());
                assistReq.setStatus(req.getStatus());
                assistNotify(assistReq, returnMap);
            }
        }finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_USER_TOPUP.getKey());
        }
    }

    /**
     * 充值预下单、确认下单前置校验，及数据装备
     * @param req
     * @return 充值下单后续业务需要的数据对象，校验不通过则throws异常
     */
    private Map<String, Object> topUpPreCheck(ReqMsg_RegularBuy_Order req){
        logger.info("充值前置校验开始", JSONObject.fromObject(req));

        //0、判断快捷通道是否开启
        if(req.getBankId() == null) {
            throw new PTMessageException(PTMessageEnum.BANK_IS_NOT_EXIST);
        }
        Bs19payBank bank = bankCardService.selectChannelBankInfo(req.getUserId(), req.getBankId());
        if(bank.getId() == null) {
            throw new PTMessageException(PTMessageEnum.BANK_IS_NOT_EXIST);
        }

        //1、判断是否绑卡
        BsPayReceiptExample bsPayReceiptExample = new BsPayReceiptExample();
        bsPayReceiptExample.createCriteria().andUserIdEqualTo(req.getUserId()).andBankCardNoEqualTo(req.getCardNo()).andChannelEqualTo(Constants.CHANNEL_BAOFOO);
        List<BsPayReceipt> bsPayReceiptList = bsPayReceiptMapper.selectByExample(bsPayReceiptExample);
        if (CollectionUtils.isEmpty(bsPayReceiptList)) {
            throw new PTMessageException(PTMessageEnum.USER_NOT_BIND_CARD);
        }

        //校验(充值)
        //2、判断用户是否存在，判断用户是否未成年
        BsUser bsUser = userMapper.selectByPrimaryKey(req.getUserId());
        if(!(bsUser != null && bsUser.getStatus() == Constants.USER_STATUS_1)) {
            throw new PTMessageException(PTMessageEnum.USER_NO_EXIT);
        }
        if(!IdcardUtils.isAdultAge(req.getIdCard())){
            throw new PTMessageException(PTMessageEnum.USER_NOT_ADULT);
        }
        // 2.1、不存在投资中的产品，则不允许充值和购买
        if(IdcardUtils.isOldAge(bsUser.getIdCard(), Constants.OLD_MAN_AGE_LIMIT)) {
            BsAccountExample accountExample = new BsAccountExample();
            accountExample.createCriteria().andUserIdEqualTo(bsUser.getId());
            List<BsAccount> bsAccounts = accountMapper.selectByExample(accountExample);
            BsSubAccountExample regExample = new BsSubAccountExample();
            List<Integer> statusList = new ArrayList<>();
            statusList.add(Constants.SUBACCOUNT_STATUS_OPEN);
            statusList.add(Constants.SUBACCOUNT_STATUS_SETTLE);
            statusList.add(Constants.SUBACCOUNT_STATUS_CLOSE);
            regExample.createCriteria().andProductTypeEqualTo(Constants.PRODUCT_TYPE_REG).andStatusNotIn(statusList).andAccountIdEqualTo(bsAccounts.get(0).getId());
            int regCount = subAccountMapper.countByExample(regExample);
            BsSubAccountExample authExample = new BsSubAccountExample();
            authExample.createCriteria().andProductTypeEqualTo(Constants.PRODUCT_TYPE_AUTH).andStatusNotIn(statusList).andAccountIdEqualTo(bsAccounts.get(0).getId());
            int authCount = subAccountMapper.countByExample(authExample);
            if(regCount + authCount <= 0) {
                throw new PTMessageException(PTMessageEnum.USER_IS_OLD_MAN);
            }
        }

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

        //3、判断单笔购买金额(银行限额)
        if(amount > bank.getOneTop()) {
            throw new PTMessageException(PTMessageEnum.PASS_ONETOP_LIMIT);
        }
        //4、判断单日充值金额
        //查询当天购买产品的总金额
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
        //如果是融宝渠道需要验证单日总额是否超限
        if(Constants.CHANNEL_REAPAL.equals(bank.getChannel())) {
            BsSysConfig reapalSys = sysMapper.selectByPrimaryKey(Constants.REAPAL_DAY_LIMIT);
            Double channelTotal = payOrderMapper.calculateChannelTotal(null, startDay, endDay, Constants.CHANNEL_REAPAL);
            if(channelTotal == null) {
                channelTotal = 0.0;
            }
            if(MoneyUtil.add(amount, channelTotal).compareTo(new BigDecimal(String.valueOf(reapalSys.getConfValue()))) == 1) {
                throw new PTMessageException(PTMessageEnum.PASS_REAPAL_DAYTOP_LIMIT);
            }
        }

        //5、判断单月购买金额
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

        //17、判断充值金额是否达到最低充值金额
        BsSysConfig sys = sysMapper.selectByPrimaryKey(Constants.RECHANGE_LIMIT);
        if(Double.parseDouble(sys.getConfValue()) > req.getAmount()) {
            throw new PTMessageException(PTMessageEnum.RECHANGE_AMOUNT_IS_NOT_PASS.getCode(), "充值金额不能低于" + sys.getConfValue() + "元");
        }
        //18、未绑卡的用户需要验证是否已经绑卡
        if(bsUser.getIsBindBank() == Constants.IS_BIND_BANK_NO) {
            throw new PTMessageException(PTMessageEnum.BIND_BANK_CARD_ERROR);
        }
        //19、绑卡用户需要验证用户ID和银行卡号是否匹配
        else {
            BsBankCardExample bbcExample = new BsBankCardExample();
            bbcExample.createCriteria().andUserIdEqualTo(req.getUserId()).andCardNoEqualTo(req.getCardNo()).andStatusEqualTo(Constants.BANK_CARD_NORMAL).andIsFirstEqualTo(Constants.BANK_CARD_USERED);
            List<BsBankCard> bbcList = bankCardMapper.selectByExample(bbcExample);
            if(CollectionUtils.isEmpty(bbcList)) {
                throw new PTMessageException(PTMessageEnum.USER_BINDCARD_IS_ERROR);
            }
        }
        //20、充值金额低于手续费
        CommissionVO commissionVO = commissionService.calServiceFee(req.getAmount(), TransTypeEnum.USER_TOP_UP_QUICK_PAY, null);
        if(req.getAmount().compareTo(commissionVO.getActPayAmount()) <= 0) {
            throw new PTMessageException(PTMessageEnum.TOP_UP_LESS_THAN_FEE);
        }

        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("bank", bank);
        return returnMap;
    }

    private void baofooPreTopUp(BsPayOrders order, ResMsg_RegularBuy_Order res) {
        // 一、校验
        // 1、查询支付签约回执表，判断是否已绑卡
        // 2、充值金额不得低于10元
        // 插入订单数据
        // 新增订单流水表
        // 二、调用宝付预下单接口成功
        // 1、更新订单表，状态-3
        // 2、插入流水表
        // 三、调用宝付预下单接口失败
        // 1、更新订单表，状态-2||4
        // 2、插入流水表
        // 一、校验
        // 1、查询支付签约回执表，判断是否已绑卡
        BsPayReceiptExample bsPayReceiptExample = new BsPayReceiptExample();
        bsPayReceiptExample.createCriteria().andUserIdEqualTo(order.getUserId()).andBankCardNoEqualTo(order.getBankCardNo()).andChannelEqualTo(Constants.CHANNEL_BAOFOO);
        List<BsPayReceipt> bsPayReceiptList = bsPayReceiptMapper.selectByExample(bsPayReceiptExample);
        BsPayReceipt bsPayReceipt;
        if (CollectionUtils.isEmpty(bsPayReceiptList)) {
            throw new PTMessageException(PTMessageEnum.USER_NOT_BIND_CARD);
        } else {
            bsPayReceipt = bsPayReceiptList.get(0);
        }
        // 调用宝付预下单接口
        B2GReqMsg_BaoFooQuickPay_QuickPay b2gReq = new B2GReqMsg_BaoFooQuickPay_QuickPay();
        b2gReq.setTransId(order.getOrderNo());
        b2gReq.setBindId(bsPayReceipt.getReceiptNo());
        b2gReq.setTxnAmt(MoneyUtil.multiply(order.getAmount(), 100d).setScale(0, BigDecimal.ROUND_HALF_UP).toString());
        b2gReq.setTrans_serial_no(Util.generateUserOrderNo4Pay19(order.getUserId()));
        B2GResMsg_BaoFooQuickPay_QuickPay b2gRes;
        try {
            logger.info("宝付充值预下单请求信息：{}", JSONObject.fromObject(b2gReq));
            b2gRes = baoFooTransportService.quickPay(b2gReq);
        } catch (Exception e) {
            logger.error("宝付充值预下单失败：网络通信异常。", e.getMessage());
            //改订单表
            BsPayOrders failOrder = new BsPayOrders();
            failOrder.setId(order.getId());
            failOrder.setStatus(Constants.ORDER_STATUS_CON_FAIL);
            failOrder.setUpdateTime(new Date());
            failOrder.setReturnMsg("宝付充值预下单失败：网络通信异常。");
            payOrderMapper.updateByPrimaryKeySelective(failOrder);
            //新增订单流水表
            BsPayOrdersJnl failJnl = new BsPayOrdersJnl();
            failJnl.setOrderId(order.getId());
            failJnl.setOrderNo(order.getOrderNo());
            failJnl.setTransCode(Constants.ORDER_TRANS_CODE_COMM_FAIL);
            failJnl.setChannelTransType(Constants.CHANNEL_TRS_QUICKPAY);
            failJnl.setTransAmount(order.getAmount());
            failJnl.setSysTime(new Date());
            failJnl.setUserId(order.getUserId());
            failJnl.setCreateTime(new Date());
            failJnl.setReturnMsg("宝付充值预下单失败：网络通信异常。");
            payOrderJnlMapper.insertSelective(failJnl);
            throw new PTMessageException(PTMessageEnum.PAY19_CONNECTION_ERROR);
        }
        if(ConstantUtil.BSRESCODE_SUCCESS.equals(b2gRes.getResCode())) {
            // 二、调用宝付预下单接口成功
            logger.info("宝付充值预下单成功：{}", JSONObject.fromObject(b2gRes));
            // 1、更新订单表，状态-3
            BsPayOrders succOrder = new BsPayOrders();
            succOrder.setId(order.getId());
            succOrder.setStatus(Constants.ORDER_STATUS_PRE_SUCC);
            succOrder.setUpdateTime(new Date());
            succOrder.setReturnMsg(b2gRes.getResMsg());
            payOrderMapper.updateByPrimaryKeySelective(succOrder);
            // 2、插入流水表
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
        } else {
            logger.error("宝付充值预下单失败：网络通信异常。{}", JSONObject.fromObject(b2gRes));
            //修改订单表
            BsPayOrders failOrder = new BsPayOrders();
            failOrder.setId(order.getId());
            failOrder.setUpdateTime(new Date());
            failOrder.setStatus(Constants.ORDER_STATUS_PRE_FAIL);

            String thirdReturnCode = Util.getThirdReturnCode(res);
            failOrder.setReturnCode(thirdReturnCode);

            failOrder.setReturnMsg(b2gRes.getResMsg());
            payOrderMapper.updateByPrimaryKeySelective(failOrder);
            //新增订单流水表
            BsPayOrdersJnl preJnl = new BsPayOrdersJnl();
            preJnl.setOrderId(order.getId());
            preJnl.setOrderNo(order.getOrderNo());
            preJnl.setTransCode(Constants.ORDER_TRANS_CODE_PRE_FAIL);
            preJnl.setChannelTransType(Constants.CHANNEL_TRS_QUICKPAY);
            preJnl.setTransAmount(order.getAmount());
            preJnl.setSysTime(new Date());
            preJnl.setUserId(order.getUserId());
            preJnl.setCreateTime(new Date());
            preJnl.setReturnCode(thirdReturnCode);
            preJnl.setReturnMsg(b2gRes.getResMsg());
            payOrderJnlMapper.insertSelective(preJnl);
            throw new PTMessageException(PTMessageEnum.USER_ORDER_PRE_FAIL, b2gRes.getResMsg());
        }
        //设置返回数据
        res.setOrderNo(order.getOrderNo());
        res.setUserId(order.getUserId());
    }

    //融宝绑卡签约接口
    private void reapalBindPreOrder(ReqMsg_RegularBuy_Order req, ResMsg_RegularBuy_Order res, String bindId, BsPayOrders order) {
        //调用融宝绑卡签约接口
        B2GReqMsg_ReapalQuickPay_BindCardSign bindReq = new B2GReqMsg_ReapalQuickPay_BindCardSign();
        bindReq.setAmount(order.getAmount());
        bindReq.setBindId(bindId);
        bindReq.setOrderNo(order.getOrderNo());
        bindReq.setUserId(String.valueOf(req.getUserId()));
        bindReq.setUserMacAddr(Constants.REAPAL_MAC_ADDR);
        bindReq.setUserIpAddr(Constants.REAPAL_IP_ADDR);
        B2GResMsg_ReapalQuickPay_BindCardSign bindRes = null;
        try {
            logger.info("=========【融宝绑卡接口预下单】币港湾订单号："+order.getOrderNo()+"=========");
            bindRes = reapalService.bindCardSign(bindReq);
        }catch(Exception e) {
            logger.error("=========【融宝绑卡接口预下单失败】原因：网络通信异常=========");
            //改订单表
            BsPayOrders failOrder = new BsPayOrders();
            failOrder.setId(order.getId());
            failOrder.setStatus(Constants.ORDER_STATUS_CON_FAIL);
            failOrder.setUpdateTime(new Date());
            failOrder.setReturnMsg("预下单失败:网络通信异常");
            payOrderMapper.updateByPrimaryKeySelective(failOrder);
            //新增订单流水表
            BsPayOrdersJnl failJnl = new BsPayOrdersJnl();
            failJnl.setOrderId(order.getId());
            failJnl.setOrderNo(order.getOrderNo());
            failJnl.setTransCode(Constants.ORDER_TRANS_CODE_COMM_FAIL);
            failJnl.setChannelTransType(Constants.CHANNEL_TRS_QUICKPAY);
            failJnl.setTransAmount(order.getAmount());
            failJnl.setSysTime(new Date());
            failJnl.setUserId(req.getUserId());
            failJnl.setCreateTime(new Date());
            failJnl.setReturnMsg("预下单失败:网络通信异常");
            payOrderJnlMapper.insertSelective(failJnl);
            throw new PTMessageException(PTMessageEnum.PAY19_CONNECTION_ERROR);
        }

        //预下单成功
        if (ConstantUtil.BSRESCODE_SUCCESS.equals(bindRes.getResCode())) {
            logger.info("=========【融宝储蓄卡接口预下单成功】=========");
            //修改订单表
            BsPayOrders preOrder = new BsPayOrders();
            preOrder.setId(order.getId());
            preOrder.setUpdateTime(new Date());
            preOrder.setStatus(Constants.ORDER_STATUS_PRE_SUCC);
            preOrder.setReturnCode(bindRes.getResCode());
            preOrder.setReturnMsg(bindRes.getResMsg());
            payOrderMapper.updateByPrimaryKeySelective(preOrder);
            //新增订单流水表
            BsPayOrdersJnl preJnl = new BsPayOrdersJnl();
            preJnl.setOrderId(order.getId());
            preJnl.setOrderNo(order.getOrderNo());
            preJnl.setTransCode(Constants.ORDER_TRANS_CODE_PRE_SUCC);
            preJnl.setChannelTransType(Constants.CHANNEL_TRS_QUICKPAY);
            preJnl.setTransAmount(order.getAmount());
            preJnl.setSysTime(new Date());
            //preJnl.setChannelTime(orderDate);
            //preJnl.setChannelJnlNo(mpOrderNo);
            preJnl.setUserId(req.getUserId());
            preJnl.setCreateTime(new Date());
            preJnl.setReturnCode(bindRes.getResCode());
            preJnl.setReturnMsg(bindRes.getResMsg());
            payOrderJnlMapper.insertSelective(preJnl);
            //设置返回数据
            res.setOrderNo(order.getOrderNo());
            //res.setMpOrderNo(mpOrderNo);
            res.setUserId(req.getUserId());

        }
        //预下单失败
        else {
            logger.error("=========【融宝储蓄卡接口预下单失败】错误码："+bindRes.getResCode()+"，原因："+bindRes.getResMsg()+"=========");
            //修改订单表
            BsPayOrders preOrder = new BsPayOrders();
            preOrder.setId(order.getId());
            preOrder.setUpdateTime(new Date());
            preOrder.setStatus(Constants.ORDER_STATUS_PRE_FAIL);
            preOrder.setReturnCode(bindRes.getResCode());
            preOrder.setReturnMsg(bindRes.getResMsg());
            payOrderMapper.updateByPrimaryKeySelective(preOrder);
            //新增订单流水表
            BsPayOrdersJnl preJnl = new BsPayOrdersJnl();
            preJnl.setOrderId(order.getId());
            preJnl.setOrderNo(order.getOrderNo());
            preJnl.setTransCode(Constants.ORDER_TRANS_CODE_PRE_FAIL);
            preJnl.setChannelTransType(Constants.CHANNEL_TRS_QUICKPAY);
            preJnl.setTransAmount(order.getAmount());
            preJnl.setSysTime(new Date());
            preJnl.setUserId(req.getUserId());
            preJnl.setCreateTime(new Date());
            preJnl.setReturnCode(bindRes.getResCode());
            preJnl.setReturnMsg(bindRes.getResMsg());
            payOrderJnlMapper.insertSelective(preJnl);
            throw new PTMessageException(PTMessageEnum.USER_ORDER_PRE_FAIL, bindRes.getResMsg());
        }
    }

    //融宝储蓄卡签约接口
    private void reapalMemoryPreOrder(ReqMsg_RegularBuy_Order req, ResMsg_RegularBuy_Order res, Bs19payBank bank, BsPayOrders order) {
        //调用融宝储蓄卡签约接口
        B2GReqMsg_ReapalQuickPay_MemoryCardSign memoryReq = new B2GReqMsg_ReapalQuickPay_MemoryCardSign();
        memoryReq.setCardNo(req.getCardNo());
        memoryReq.setOwner(req.getUserName());
        memoryReq.setCertNo(req.getIdCard());
        memoryReq.setPhone(req.getMobile());
        memoryReq.setOrderNo(order.getOrderNo());
        memoryReq.setAmount(order.getAmount());
        memoryReq.setUserId(String.valueOf(req.getUserId()));
        memoryReq.setUserMacAddr(Constants.REAPAL_MAC_ADDR);
        memoryReq.setUserIpAddr(Constants.REAPAL_IP_ADDR);
        B2GResMsg_ReapalQuickPay_MemoryCardSign memoryRes = null;
        try {
            logger.info("=========【融宝储蓄卡接口预下单】币港湾订单号："+order.getOrderNo()+"=========");
            memoryRes = reapalService.memoryCardSign(memoryReq);
        }catch(Exception ex) {
            logger.error("=========【融宝储蓄卡接口预下单失败】原因：网络通信异常=========");
            //改订单表
            BsPayOrders failOrder = new BsPayOrders();
            failOrder.setId(order.getId());
            failOrder.setStatus(Constants.ORDER_STATUS_CON_FAIL);
            failOrder.setUpdateTime(new Date());
            failOrder.setReturnMsg("预下单失败:网络通信异常");
            payOrderMapper.updateByPrimaryKeySelective(failOrder);
            //新增订单流水表
            BsPayOrdersJnl failJnl = new BsPayOrdersJnl();
            failJnl.setOrderId(order.getId());
            failJnl.setOrderNo(order.getOrderNo());
            failJnl.setTransCode(Constants.ORDER_TRANS_CODE_COMM_FAIL);
            failJnl.setChannelTransType(Constants.CHANNEL_TRS_QUICKPAY);
            failJnl.setTransAmount(order.getAmount());
            failJnl.setSysTime(new Date());
            failJnl.setUserId(req.getUserId());
            failJnl.setCreateTime(new Date());
            failJnl.setReturnMsg("预下单失败:网络通信异常");
            payOrderJnlMapper.insertSelective(failJnl);
            throw new PTMessageException(PTMessageEnum.PAY19_CONNECTION_ERROR);
        }

        //预下单成功
        if (ConstantUtil.BSRESCODE_SUCCESS.equals(memoryRes.getResCode())) {
            logger.info("=========【融宝储蓄卡接口预下单成功】=========");
            //修改订单表
            BsPayOrders preOrder = new BsPayOrders();
            preOrder.setId(order.getId());
            preOrder.setUpdateTime(new Date());
            preOrder.setStatus(Constants.ORDER_STATUS_PRE_SUCC);
            preOrder.setReturnCode(memoryRes.getResCode());
            preOrder.setReturnMsg(memoryRes.getResMsg());
            payOrderMapper.updateByPrimaryKeySelective(preOrder);
            //新增订单流水表
            BsPayOrdersJnl preJnl = new BsPayOrdersJnl();
            preJnl.setOrderId(order.getId());
            preJnl.setOrderNo(order.getOrderNo());
            preJnl.setTransCode(Constants.ORDER_TRANS_CODE_PRE_SUCC);
            preJnl.setChannelTransType(Constants.CHANNEL_TRS_QUICKPAY);
            preJnl.setTransAmount(order.getAmount());
            preJnl.setSysTime(new Date());
            //preJnl.setChannelTime(orderDate);
            //preJnl.setChannelJnlNo(mpOrderNo);
            preJnl.setUserId(req.getUserId());
            preJnl.setCreateTime(new Date());
            preJnl.setReturnCode(memoryRes.getResCode());
            preJnl.setReturnMsg(memoryRes.getResMsg());
            payOrderJnlMapper.insertSelective(preJnl);
            //新增支付签约回执表
            BsPayReceiptExample receiptExample = new BsPayReceiptExample();
            receiptExample.createCriteria().andUserIdEqualTo(order.getUserId()).andBankCardNoEqualTo(order.getBankCardNo()).andChannelEqualTo(Constants.CHANNEL_REAPAL);
            List<BsPayReceipt> receiptList = receiptMapper.selectByExample(receiptExample);
            if(CollectionUtils.isEmpty(receiptList)) {
                BsPayReceipt re = new BsPayReceipt();
                re.setBankCardNo(req.getCardNo());
                re.setUserId(req.getUserId());
                re.setUserName(req.getUserName());
                re.setBankName(req.getBankName());
                re.setIdCard(req.getIdCard());
                re.setMobile(req.getMobile());
                re.setReceiptNo(memoryRes.getBindId());
                re.setIsBindCard(Constants.REAPAL_BIND_NO);
                re.setChannel(Constants.CHANNEL_REAPAL);
                re.setCreateTime(new Date());
                re.setUpdateTime(new Date());
                receiptMapper.insertSelective(re);
            }
            //设置返回数据
            res.setOrderNo(order.getOrderNo());
            //res.setMpOrderNo(mpOrderNo);
            res.setUserId(req.getUserId());

            //招行需要调用卡密接口
            if(Constants.BANK_CMB_CODE.equalsIgnoreCase(bank.getPay19BankCode()) && Constants.REAPAL_CERTIFICATE_NO.equals(memoryRes.getCertificate())) {
                B2GReqMsg_ReapalQuickPay_Certify certifyReq = new B2GReqMsg_ReapalQuickPay_Certify();
                certifyReq.setBindId(memoryRes.getBindId());
                certifyReq.setOrderNo(order.getOrderNo());
                certifyReq.setUserId(req.getUserId());
                certifyReq.setSource(req.getTerminalType());
                if(req.getTransType() == Constants.USER_TRANS_TYPE_CARD) {
                    certifyReq.setPid(req.getProductId());
                }
                B2GResMsg_ReapalQuickPay_Certify certifyResp = reapalService.certify(certifyReq);
                if(ConstantUtil.BSRESCODE_SUCCESS.equals(certifyResp.getResCode())) {
                    res.setHtml(certifyResp.getHtml());
                }
                else {
                    res.setHtml("fail");
                }
            }

        }
        //预下单失败
        else {
            logger.error("=========【融宝储蓄卡接口预下单失败】错误码："+memoryRes.getResCode()+"，原因："+memoryRes.getResMsg()+"=========");
            //修改订单表
            BsPayOrders preOrder = new BsPayOrders();
            preOrder.setId(order.getId());
            preOrder.setUpdateTime(new Date());
            preOrder.setStatus(Constants.ORDER_STATUS_PRE_FAIL);
            preOrder.setReturnCode(memoryRes.getResCode());
            preOrder.setReturnMsg(memoryRes.getResMsg());
            payOrderMapper.updateByPrimaryKeySelective(preOrder);
            //新增订单流水表
            BsPayOrdersJnl preJnl = new BsPayOrdersJnl();
            preJnl.setOrderId(order.getId());
            preJnl.setOrderNo(order.getOrderNo());
            preJnl.setTransCode(Constants.ORDER_TRANS_CODE_PRE_FAIL);
            preJnl.setChannelTransType(Constants.CHANNEL_TRS_QUICKPAY);
            preJnl.setTransAmount(order.getAmount());
            preJnl.setSysTime(new Date());
            preJnl.setUserId(req.getUserId());
            preJnl.setCreateTime(new Date());
            preJnl.setReturnCode(memoryRes.getResCode());
            preJnl.setReturnMsg(memoryRes.getResMsg());
            payOrderJnlMapper.insertSelective(preJnl);
            throw new PTMessageException(PTMessageEnum.USER_ORDER_PRE_FAIL, memoryRes.getResMsg());
        }
    }
    //19付预下单
    private void pay19PreOrder(ReqMsg_RegularBuy_Order req, ResMsg_RegularBuy_Order res, Bs19payBank bank, BsPayOrders order) {
        //调用19付预下单接口
        B2GReqMsg_QuickPay_PreOrder reqMsg = new B2GReqMsg_QuickPay_PreOrder();
        reqMsg.setAcctAttr(AcctAttr.PRIVATE); // 账户属性
        reqMsg.setAcctType(AcctType.DEBITCARD); // 卡类型
        reqMsg.setAmount(order.getAmount());
        reqMsg.setBankCardNum(req.getCardNo()); // 银行卡号
        reqMsg.setBindSNo(null); //  绑定流水号
        reqMsg.setCardHolder(req.getUserName()); // 持卡人姓名
        reqMsg.setCurrency(com.pinting.gateway.hessian.message.pay19.enums.Currency.RMB); // 币种
        reqMsg.setCvv2(null); // cvv2码
        reqMsg.setIdCardnum(req.getIdCard()); // 身份证号
        reqMsg.setIdType(IdType.IDENTITY_CARD); // 证件类型
        reqMsg.setIsBind(IsSaveBind.SAVE_BIND); // 是否保存快捷支付绑定信息
        reqMsg.setIsFixBindInfo(null); // 绑定流水的不全标识
        reqMsg.setMobile(req.getMobile());
        reqMsg.setMxGoodsType(GoodsType.VIRTUAL_GOODS); // 商品类型
        reqMsg.setOrderDate(order.getCreateTime());
        reqMsg.setOrderDesc("快捷下单");
        reqMsg.setOrderId(order.getOrderNo());
        reqMsg.setOrderPDesc("充值");
        reqMsg.setOrderPName("充值");
        reqMsg.setOrderRemarkDesc(null);
        reqMsg.setPcId(bank.getPay19BankCode()); // 银行通道
        reqMsg.setTradeDesc("充值");
        reqMsg.setTradeType(TradeType.QUICKPAY);
        reqMsg.setUserId(String.valueOf(req.getUserId()));
        reqMsg.setValidDate(null);
        B2GResMsg_QuickPay_PreOrder resMsg = null;
        try {
            logger.info("=========【19付预下单】币港湾订单号："+order.getOrderNo()+"=========");
            resMsg = pay19TransportService.preOrder(reqMsg);
        }catch(Exception ex) {
            logger.error("=========【19付预下单失败】原因：网络通信异常=========");
            //改订单表
            BsPayOrders failOrder = new BsPayOrders();
            failOrder.setId(order.getId());
            failOrder.setStatus(Constants.ORDER_STATUS_CON_FAIL);
            failOrder.setUpdateTime(new Date());
            failOrder.setReturnMsg("预下单失败:网络通信异常");
            payOrderMapper.updateByPrimaryKeySelective(failOrder);
            //新增订单流水表
            BsPayOrdersJnl failJnl = new BsPayOrdersJnl();
            failJnl.setOrderId(order.getId());
            failJnl.setOrderNo(order.getOrderNo());
            failJnl.setTransCode(Constants.ORDER_TRANS_CODE_COMM_FAIL);
            failJnl.setChannelTransType(Constants.CHANNEL_TRS_QUICKPAY);
            failJnl.setTransAmount(order.getAmount());
            failJnl.setSysTime(new Date());
            failJnl.setUserId(req.getUserId());
            failJnl.setCreateTime(new Date());
            failJnl.setReturnMsg("预下单失败:网络通信异常");
            payOrderJnlMapper.insertSelective(failJnl);
            throw new PTMessageException(PTMessageEnum.PAY19_CONNECTION_ERROR);
        }

        String orderNo = resMsg.getOrderId(); //币港湾订单号
        String mpOrderNo = resMsg.getMpOrderId(); //19付订单号
        Date orderDate = resMsg.getOrderDate();
        String errorCode = resMsg.getErrorCode(); //19付返回的错误码,00000表示成功
        String returnMsg = resMsg.getResMsg();

        //预下单成功
        if (StringUtil.isNotBlank(orderNo)) {
            logger.info("=========【19付预下单成功】返回19付订单号："+mpOrderNo+"=========");
            //修改订单表
            BsPayOrders preOrder = new BsPayOrders();
            preOrder.setId(order.getId());
            preOrder.setUpdateTime(new Date());
            preOrder.setStatus(Constants.ORDER_STATUS_PRE_SUCC);
            preOrder.setReturnCode(errorCode);
            preOrder.setReturnMsg(returnMsg);
            payOrderMapper.updateByPrimaryKeySelective(preOrder);
            //新增订单流水表
            BsPayOrdersJnl preJnl = new BsPayOrdersJnl();
            preJnl.setOrderId(order.getId());
            preJnl.setOrderNo(order.getOrderNo());
            preJnl.setTransCode(Constants.ORDER_TRANS_CODE_PRE_SUCC);
            preJnl.setChannelTransType(Constants.CHANNEL_TRS_QUICKPAY);
            preJnl.setTransAmount(order.getAmount());
            preJnl.setSysTime(new Date());
            preJnl.setChannelTime(orderDate);
            preJnl.setChannelJnlNo(mpOrderNo);
            preJnl.setUserId(req.getUserId());
            preJnl.setCreateTime(new Date());
            preJnl.setReturnCode(errorCode);
            preJnl.setReturnMsg(returnMsg);
            payOrderJnlMapper.insertSelective(preJnl);
            //设置返回数据
            res.setOrderNo(order.getOrderNo());
            res.setMpOrderNo(mpOrderNo);
            res.setUserId(req.getUserId());
        }
        //预下单失败
        else {
            if(StringUtil.isBlank(errorCode) && StringUtil.isNotBlank(returnMsg)) {
                String[] temp = returnMsg.split(",");
                errorCode = temp[temp.length-1].trim();
            }
            logger.error("=========【19付预下单失败】错误码："+errorCode+"，原因："+returnMsg+"=========");
            //修改订单表
            BsPayOrders preOrder = new BsPayOrders();
            preOrder.setId(order.getId());
            preOrder.setUpdateTime(new Date());
            preOrder.setStatus(Constants.ORDER_STATUS_PRE_FAIL);
            preOrder.setReturnCode(errorCode);
            preOrder.setReturnMsg(returnMsg);
            payOrderMapper.updateByPrimaryKeySelective(preOrder);
            //新增订单流水表
            BsPayOrdersJnl preJnl = new BsPayOrdersJnl();
            preJnl.setOrderId(order.getId());
            preJnl.setOrderNo(order.getOrderNo());
            preJnl.setTransCode(Constants.ORDER_TRANS_CODE_PRE_FAIL);
            preJnl.setChannelTransType(Constants.CHANNEL_TRS_QUICKPAY);
            preJnl.setTransAmount(order.getAmount());
            preJnl.setSysTime(new Date());
            preJnl.setUserId(req.getUserId());
            preJnl.setCreateTime(new Date());
            preJnl.setReturnCode(errorCode);
            preJnl.setReturnMsg(returnMsg);
            payOrderJnlMapper.insertSelective(preJnl);
            throw new PTMessageException(PTMessageEnum.USER_ORDER_PRE_FAIL, returnMsg==null?"":returnMsg.split(",")[0]);
        }
    }
    /**
     * 宝付充值确认下单
     * @param req
     * @param res
     */
    private void baofooConfirmTopUp(ReqMsg_RegularBuy_Order req, final ResMsg_RegularBuy_Order res) {
        // 一、数据校验
        // 1、判断订单状态是否为预下单成功
        // 2、校验结算户是否存在
        // 二、宝付正式下单成功
        // 1、将处理中状态改为成功 bs_pay_orders
        // 2、新增订单流水表 成功
        // 3、新增用户交易明细表状态为成功
        // 4、修改用户子账户结算户表
        // 5、新增用户记账流水表 BsAccountJnl
        // 6、修改系统子账户表
        // 7、新增系统记账流水表
        // 8、修改用户信息表 最近使用银行ID 可提现余额
        // 3、用户交易明细表 BsUserTransDetail
        // 三、宝付正式下单失败
        // 1、修改订单表-7
        // 2、流水表
        // 四、宝付正式下单处理中
        // 1、修改订单表-5
        // 2、新增流水表
        // 3、新增用户交易明细表状态为处理中
        // 五、发送微信+短信

        // 一、数据校验
        // 1、判断订单状态是否为预下单成功
        logger.info("宝付充值确认下单开始：{}" , JSONObject.fromObject(req));
        BsPayOrdersExample reguOrderExample = new BsPayOrdersExample();
        reguOrderExample.createCriteria().andOrderNoEqualTo(req.getOrderNo()).andStatusEqualTo(Constants.ORDER_STATUS_PRE_SUCC).andUserIdEqualTo(req.getUserId());
        List<BsPayOrders> reguOrderList = payOrderMapper.selectByExample(reguOrderExample);
        if(CollectionUtils.isEmpty(reguOrderList)) {
            throw new PTMessageException(PTMessageEnum.PAYMENT_ORDER_NOT_EXIST);
        }
        // 2. 绑卡/充值短信验证码 必须6位，否则提示客户“请输入6位验证码”
        if(req.getVerifyCode().length() != 6) {
            throw new PTMessageException(PTMessageEnum.MOBILE_CODE_MUST_SIX);
        }
        if(!StringUtil.isNumeric(req.getVerifyCode())) {
            throw new PTMessageException(PTMessageEnum.MOBILE_CODE_MUST_NUMBER);
        }
        final BsPayOrders reguOrder = reguOrderList.get(0);
        // 2、校验结算户是否存在
        List<BsSubAccount> subList = subAccountMapper.selectSubAccount(req.getUserId(), Constants.SYS_ACCOUNT_JSH, Constants.SUBACCOUNT_STATUS_OPEN);
        if(CollectionUtils.isEmpty(subList)) {
            throw new PTMessageException(PTMessageEnum.USER_JSH_NO_EXIT);
        }
        final BsSubAccount bsSubAccount = subList.get(0);
        // 3、支付签约回执表
        BsPayReceiptExample receiptExample = new BsPayReceiptExample();
        receiptExample.createCriteria().andUserIdEqualTo(reguOrder.getUserId()).andBankCardNoEqualTo(reguOrder.getBankCardNo()).andChannelEqualTo(Constants.CHANNEL_BAOFOO).andIsBindCardEqualTo(Constants.IS_BIND_BANK_YES);
        List<BsPayReceipt> receiptList = bsPayReceiptMapper.selectByExample(receiptExample);
        BsPayReceipt bsPayReceipt = receiptList.get(0);
        // 判断确认下单金额是否与预下单金额一致
        if(reguOrder.getAmount().compareTo(req.getAmount()) != 0){
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "金额非法");
        }

        //调用宝付正式下单接口
        B2GReqMsg_BaoFooQuickPay_QuickPayConfirm b2gReq = new B2GReqMsg_BaoFooQuickPay_QuickPayConfirm();
        b2gReq.setTxn_amt(MoneyUtil.multiply(req.getAmount(), 100d).setScale(0, BigDecimal.ROUND_HALF_UP).toString());
        b2gReq.setBind_id(bsPayReceipt.getReceiptNo());
        b2gReq.setSms_code(req.getVerifyCode());
        b2gReq.setTrans_id(reguOrder.getOrderNo());
        b2gReq.setTrans_serial_no(Util.generateUserOrderNo4Pay19(req.getUserId()));
        b2gReq.setAdditional_info("充值");
        B2GResMsg_BaoFooQuickPay_QuickPayConfirm b2gRes;
        try {
            logger.info("宝付充值正式下单请求信息：{}" , JSONObject.fromObject(b2gReq));
            b2gRes = baoFooTransportService.quickPayConfirm(b2gReq);
        } catch (Exception e) {
            logger.error("宝付充值正式下单失败：{}", e.getMessage());
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {
                    BsPayOrders order = payOrderMapper.selectByPKForLock(reguOrder.getId());
                    // 1、修改订单表-7
                    payOrdersService.modifyOrderStatus4Safe(reguOrder.getId(), Constants.ORDER_STATUS_FAIL,
                            bsSubAccount.getId(), null, "正式下单失败：网络通信异常。");
                    // 2、新增订单流水表
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
                }
            });
            throw new PTMessageException(PTMessageEnum.PAY19_CONNECTION_ERROR);
        }
        if(ConstantUtil.BSRESCODE_SUCCESS.equals(b2gRes.getResCode())) {
            // 二、宝付正式下单成功
            logger.info("宝付充值正式下单成功：{}", JSONObject.fromObject(b2gRes));
            res.setResCode(ConstantUtil.RESCODE_SUCCESS);
            payOrdersService.modifyOrderStatus4Safe(reguOrder.getId(), Constants.ORDER_STATUS_PAYING,
                    bsSubAccount.getId(), null, null);

            OrderResultInfo resultInfo = new OrderResultInfo();
            resultInfo.setPayOrderNo(b2gRes.getTrans_no());
            resultInfo.setAmount(reguOrder.getAmount());
            resultInfo.setOrderNo(reguOrder.getOrderNo());
            resultInfo.setSuccess(true);
            resultInfo.setFinishTime(new Date());
            orderBusinessService.financerQuickPayTopUp(resultInfo);

        } else if(Constants.GATEWAY_PAYING_RES_CODE.equals(b2gRes.getResCode())) {
            // 三、宝付正式下单正在处理中
            logger.info("宝付充值正式下单处理中：{}", JSONObject.fromObject(b2gRes));
            // 1、修改订单表-5
            payOrdersService.modifyOrderStatus4Safe(reguOrder.getId(), Constants.ORDER_STATUS_PAYING,
                    bsSubAccount.getId(), null, null);
            // 2、新增流水表
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
            reguJnl.setReturnCode(b2gRes.getResCode());
            reguJnl.setReturnMsg(b2gRes.getResMsg());
            payOrderJnlMapper.insertSelective(reguJnl);

            // 3、新增用户交易明细表(充值)
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

            //放到redis中
            LoanNoticeVO vo = new LoanNoticeVO();
            vo.setPayOrderNo(reguOrder.getOrderNo());
            vo.setAmount(reguOrder.getAmount().toString());
            vo.setChannel(reguOrder.getChannel());
            vo.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_AUTH.getCode());
            vo.setTransType(reguOrder.getTransType());
            vo.setStatus(Constants.ORDER_STATUS_PAYING);
            redisUtil.rpushRedis(vo);

            //并插入到消息队列表中
            BsPayResultQueue queue = new BsPayResultQueue();
            queue.setChannel(Constants.ORDER_CHANNEL_BAOFOO);
            queue.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_AUTH.getCode());
            queue.setCreateTime(new Date());
            queue.setDealNum(0);
            queue.setOrderNo(reguOrder.getOrderNo());
            queue.setStatus(LoanStatus.NOTICE_STATUS_INIT.getCode());
            queue.setTransType(reguOrder.getTransType());
            queue.setUpdateTime(new Date());
            queueMapper.insertSelective(queue);
        } else {
            // 四、宝付正式下单失败
            logger.error("宝付充值正式下单失败：{}", JSONObject.fromObject(b2gRes));
            final String errorMsg =  b2gRes.getResMsg();
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {
                    BsPayOrders order = payOrderMapper.selectByPKForLock(reguOrder.getId());
                    // 1、修改订单表-7
                    String thirdReturnCode = "";
                    if (res.getExtendMap()!= null) {
                        thirdReturnCode =  (String)res.getExtendMap().get("thirdReturnCode");
                    }else {
                        thirdReturnCode = res.getResCode();
                    }
                    payOrdersService.modifyOrderStatus4Safe(reguOrder.getId(), Constants.ORDER_STATUS_FAIL,
                            bsSubAccount.getId(), thirdReturnCode, errorMsg);
                    // 2、流水表
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
                    reguJnl.setTransCode(Constants.ORDER_TRANS_CODE_FAIL);
                    reguJnl.setReturnCode(thirdReturnCode);
                    reguJnl.setReturnMsg(errorMsg);
                    payOrderJnlMapper.insertSelective(reguJnl);

                }
            });
            res.setResCode(b2gRes.getResCode());
            res.setResMsg(b2gRes.getResMsg());
            throw new PTMessageException(PTMessageEnum.USER_ORDER_REGULAR_FAIL,b2gRes.getResMsg());
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
            String mobile = getMobile(reguOrder.getUserId());
        	
            //message = "您有一笔充值未成功，失败原因："+req.getErrorMsg()+"，请核实，如有疑问请拨打400-806-1230。";
            smsServiceClient.sendTemplateMessage(mobile, TemplateKey.TOP_UP_FALL, BaoFooResCode.getEnumByCode(res.getResCode()).getDescription());
            //发送微信模板消息
            sendWechatService.topUpMgs(reguOrder.getUserId(),"", mobile, reguOrder.getAmount().toString(), "fall", BaoFooResCode.getEnumByCode(res.getResCode()).getDescription());
        }
    }

    //融宝确认支付接口
    private void reapalConfirmOrder(ReqMsg_RegularBuy_Order req) {
        Date now = new Date();
        Integer subAccountId = null;
        String subAccountCode = null;
        //判断订单状态是否为预下单成功
        BsPayOrdersExample reguOrderExample = new BsPayOrdersExample();
        reguOrderExample.createCriteria().andOrderNoEqualTo(req.getOrderNo()).andStatusEqualTo(Constants.ORDER_STATUS_PRE_SUCC).andUserIdEqualTo(req.getUserId());
        List<BsPayOrders> reguOrderList = payOrderMapper.selectByExample(reguOrderExample);
        if(CollectionUtils.isEmpty(reguOrderList)) {
            throw new PTMessageException(PTMessageEnum.PAYMENT_ORDER_NOT_EXIST);
        }
        BsPayOrders reguOrder = reguOrderList.get(0);

        //充值
        //查询子账户表中状态为"JSH"的记录
        List<BsSubAccount> subList = subAccountMapper.selectSubAccount(req.getUserId(), Constants.SYS_ACCOUNT_JSH, Constants.SUBACCOUNT_STATUS_OPEN);
        if(!CollectionUtils.isEmpty(subList) && subList.size() > 0) {
            subAccountId = subList.get(0).getId();
            subAccountCode = subList.get(0).getCode();
        }
        else {
            throw new PTMessageException(PTMessageEnum.USER_JSH_NO_EXIT);
        }

        //设置子账户id
        reguOrder.setSubAccountId(subAccountId);

        BsPayOrdersJnl reguJnl = new BsPayOrdersJnl();
        reguJnl.setOrderId(reguOrder.getId());
        reguJnl.setOrderNo(reguOrder.getOrderNo());
        reguJnl.setTransCode(Constants.ORDER_TRANS_CODE_PROCCESS);
        reguJnl.setChannelTransType(Constants.CHANNEL_TRS_QUICKPAY);
        reguJnl.setTransAmount(reguOrder.getAmount());
        reguJnl.setSysTime(now);
        reguJnl.setSubAccountId(subAccountId);
        reguJnl.setSubAccountCode(subAccountCode);
        reguJnl.setUserId(reguOrder.getUserId());
        reguJnl.setCreateTime(now);

        //调用融宝正式下单接口
        B2GReqMsg_ReapalQuickPay_SubmitPay reqMsg = new B2GReqMsg_ReapalQuickPay_SubmitPay();
        reqMsg.setCheckCode(req.getVerifyCode());
        reqMsg.setOrderNo(reguOrder.getOrderNo());
        B2GResMsg_ReapalQuickPay_SubmitPay resMsg = null;
        try {
            logger.info("=========【融宝正式下单】=========");
            resMsg = reapalService.submitPay(reqMsg);
        }catch(Exception e) {
            logger.error("=========【融宝正式下单失败】原因：网络通信异常=========");
            //修改订单表
            reguOrder.setStatus(Constants.ORDER_STATUS_FAIL);
            reguOrder.setUpdateTime(new Date());
            reguOrder.setReturnMsg("正式下单失败:网络通信异常");
            payOrderMapper.updateByPrimaryKeySelective(reguOrder);
            //新增订单流水表
            reguJnl.setTransCode(Constants.ORDER_TRANS_CODE_FAIL);
            reguJnl.setSysTime(new Date());
            reguJnl.setCreateTime(new Date());
            reguJnl.setReturnMsg("正式下单失败:网络通信异常");
            payOrderJnlMapper.insertSelective(reguJnl);
            throw new PTMessageException(PTMessageEnum.PAY19_CONNECTION_ERROR);
        }

        //正式下单成功
        if(ConstantUtil.BSRESCODE_SUCCESS.equals(resMsg.getResCode())) {
            logger.info("=========【融宝正式下单成功】融宝订单号："+resMsg.getTradeNo()+"=========");
            //新增用户交易明细表(充值)
            BsUserTransDetail detail1 = new BsUserTransDetail();
            detail1.setUserId(reguOrder.getUserId());
            detail1.setCardNo(reguOrder.getBankCardNo());
            detail1.setTransType(Constants.Trans_TYPE_TOP_UP);
            detail1.setTransStatus(Constants.Trans_STATUS_DEAL);
            detail1.setOrderNo(reguOrder.getOrderNo());
            detail1.setCreateTime(new Date());
            detail1.setAmount(req.getAmount());
            detail1.setUpdateTime(new Date());
            userTransMapper.insertSelective(detail1);
            //修改订单状态
            reguOrder.setSubAccountId(subAccountId);
            reguOrder.setStatus(Constants.ORDER_STATUS_PAYING);
            reguOrder.setUpdateTime(now);
            payOrderMapper.updateByPrimaryKeySelective(reguOrder);
            //新增订单流水
            reguJnl.setChannelTime(reguOrder.getCreateTime());
            reguJnl.setChannelJnlNo(resMsg.getTradeNo());
            reguJnl.setReturnCode(resMsg.getResCode());
            reguJnl.setReturnMsg(resMsg.getResMsg());
            payOrderJnlMapper.insertSelective(reguJnl);

        
        }
        //正式下单失败
        else {
            logger.error("=========【融宝正式下单失败】错误码："+resMsg.getResCode()+"，原因："+resMsg.getResMsg()+"=========");
            //修改订单表
            reguOrder.setStatus(Constants.ORDER_STATUS_FAIL);
            reguOrder.setUpdateTime(new Date());
            reguOrder.setReturnCode(resMsg.getResCode());
            reguOrder.setReturnMsg(resMsg.getResMsg());
            payOrderMapper.updateByPrimaryKeySelective(reguOrder);
            //新增订单流水表
            reguJnl.setTransCode(Constants.ORDER_TRANS_CODE_FAIL);
            reguJnl.setSysTime(new Date());
            reguJnl.setCreateTime(new Date());
            reguJnl.setReturnCode(resMsg.getResCode());
            reguJnl.setReturnMsg(resMsg.getResMsg());
            payOrderJnlMapper.insertSelective(reguJnl);
            throw new PTMessageException(PTMessageEnum.USER_ORDER_REGULAR_FAIL,resMsg.getResMsg());
        }
    }

    //19付正式下单
    private void pay19ConfirmOrder(ReqMsg_RegularBuy_Order req) {
        Date now = new Date();
        Integer subAccountId = null;
        String subAccountCode = null;
        //判断订单状态是否为预下单成功
        BsPayOrdersExample reguOrderExample = new BsPayOrdersExample();
        reguOrderExample.createCriteria().andOrderNoEqualTo(req.getOrderNo()).andStatusEqualTo(Constants.ORDER_STATUS_PRE_SUCC).andUserIdEqualTo(req.getUserId());
        List<BsPayOrders> reguOrderList = payOrderMapper.selectByExample(reguOrderExample);
        if(CollectionUtils.isEmpty(reguOrderList)) {
            throw new PTMessageException(PTMessageEnum.PAYMENT_ORDER_NOT_EXIST);
        }
        BsPayOrders reguOrder = reguOrderList.get(0);

        //充值
        //查询子账户表中状态为"JSH"的记录
        List<BsSubAccount> subList = subAccountMapper.selectSubAccount(req.getUserId(), Constants.SYS_ACCOUNT_JSH, Constants.SUBACCOUNT_STATUS_OPEN);
        if(!CollectionUtils.isEmpty(subList) && subList.size() > 0) {
            subAccountId = subList.get(0).getId();
            subAccountCode = subList.get(0).getCode();
        }
        else {
            throw new PTMessageException(PTMessageEnum.USER_JSH_NO_EXIT);
        }

        //设置子账户id
        reguOrder.setSubAccountId(subAccountId);

        //查询预下单成功的订单流水记录
        BsPayOrdersJnlExample reguJnlExample = new BsPayOrdersJnlExample();
        reguJnlExample.createCriteria().andOrderIdEqualTo(reguOrder.getId()).andTransCodeEqualTo(Constants.ORDER_TRANS_CODE_PRE_SUCC);
        List<BsPayOrdersJnl> reguJnlList = payOrderJnlMapper.selectByExample(reguJnlExample);
        if(CollectionUtils.isEmpty(reguJnlList)) {
            throw new PTMessageException(PTMessageEnum.PAYMENT_ORDER_JNL_NOT_EXIST);
        }
        BsPayOrdersJnl reguJnl = new BsPayOrdersJnl();
        reguJnl.setOrderId(reguOrder.getId());
        reguJnl.setOrderNo(reguOrder.getOrderNo());
        reguJnl.setTransCode(Constants.ORDER_TRANS_CODE_PROCCESS);
        reguJnl.setChannelTransType(Constants.CHANNEL_TRS_QUICKPAY);
        reguJnl.setTransAmount(reguOrder.getAmount());
        reguJnl.setSysTime(now);
        reguJnl.setChannelTime(reguJnlList.get(0).getChannelTime());
        reguJnl.setChannelJnlNo(reguJnlList.get(0).getChannelJnlNo());
        reguJnl.setSubAccountId(subAccountId);
        reguJnl.setSubAccountCode(subAccountCode);
        reguJnl.setUserId(reguOrder.getUserId());
        reguJnl.setCreateTime(now);

        //调用19付正式下单接口
        B2GReqMsg_QuickPay_ConfirmOrder reqMsg = new B2GReqMsg_QuickPay_ConfirmOrder();
        reqMsg.setAmount(reguOrder.getAmount());
        reqMsg.setMpOrderId(reguJnlList.get(0).getChannelJnlNo());
        reqMsg.setOrderDate(reguJnlList.get(0).getChannelTime());
        reqMsg.setOrderId(reguOrder.getOrderNo());
        reqMsg.setUserId(String.valueOf(reguOrder.getUserId()));
        reqMsg.setVerifyCode(req.getVerifyCode());
        B2GResMsg_QuickPay_ConfirmOrder resMsg = null;
        try {
            logger.info("=========【19付正式下单】19付订单号："+reguJnlList.get(0).getChannelJnlNo()+"=========");
            resMsg = pay19TransportService.confirmOrder(reqMsg);
        }catch(Exception ex) {
            logger.error("=========【19付正式下单失败】原因：网络通信异常=========");
            //修改订单表
            reguOrder.setStatus(Constants.ORDER_STATUS_FAIL);
            reguOrder.setUpdateTime(new Date());
            reguOrder.setReturnMsg("正式下单失败:网络通信异常");
            payOrderMapper.updateByPrimaryKeySelective(reguOrder);
            //新增订单流水表
            reguJnl.setTransCode(Constants.ORDER_TRANS_CODE_FAIL);
            reguJnl.setSysTime(new Date());
            reguJnl.setCreateTime(new Date());
            reguJnl.setReturnMsg("正式下单失败:网络通信异常");
            payOrderJnlMapper.insertSelective(reguJnl);
            throw new PTMessageException(PTMessageEnum.PAY19_CONNECTION_ERROR);
        }

        //正式下单请求成功
        if(ConstantUtil.BSRESCODE_SUCCESS.equals(resMsg.getResCode())) {
            //正式下单成功
            if(TradeResult.SUCCESS.getCode().equals(resMsg.getTradeResult().getCode())||TradeResult.PROCESS.getCode().equals(resMsg.getTradeResult().getCode())) {
                logger.info("=========【19付正式下单成功】19付订单号："+reguJnlList.get(0).getChannelJnlNo()+"=========");

                //新增用户交易明细表(充值)
                BsUserTransDetail detail1 = new BsUserTransDetail();
                detail1.setUserId(reguOrder.getUserId());
                detail1.setCardNo(reguOrder.getBankCardNo());
                detail1.setTransType(Constants.Trans_TYPE_TOP_UP);
                detail1.setTransStatus(Constants.Trans_STATUS_DEAL);
                detail1.setOrderNo(reguOrder.getOrderNo());
                detail1.setCreateTime(new Date());
                detail1.setAmount(req.getAmount());
                detail1.setUpdateTime(new Date());
                userTransMapper.insertSelective(detail1);
                //修改订单状态
                reguOrder.setSubAccountId(subAccountId);
                reguOrder.setStatus(Constants.ORDER_STATUS_PAYING);
                reguOrder.setUpdateTime(now);
                payOrderMapper.updateByPrimaryKeySelective(reguOrder);
                //新增订单流水
                reguJnl.setReturnCode(resMsg.getErrorCode());
                reguJnl.setReturnMsg(resMsg.getErrorMsg());
                payOrderJnlMapper.insertSelective(reguJnl);
                

            }
            //正式下单失败
            else {
                logger.error("=========【19付正式下单失败】错误码："+resMsg.getErrorCode()+"，原因："+resMsg.getErrorMsg()+"=========");
                //修改订单表
                reguOrder.setStatus(Constants.ORDER_STATUS_FAIL);
                reguOrder.setUpdateTime(new Date());
                reguOrder.setReturnCode(resMsg.getErrorCode());
                reguOrder.setReturnMsg(resMsg.getErrorMsg());
                payOrderMapper.updateByPrimaryKeySelective(reguOrder);
                //新增订单流水表
                reguJnl.setTransCode(Constants.ORDER_TRANS_CODE_FAIL);
                reguJnl.setSysTime(new Date());
                reguJnl.setCreateTime(new Date());
                reguJnl.setReturnCode(resMsg.getErrorCode());
                reguJnl.setReturnMsg(resMsg.getErrorMsg());
                payOrderJnlMapper.insertSelective(reguJnl);
                throw new PTMessageException(PTMessageEnum.USER_ORDER_REGULAR_FAIL,resMsg.getErrorMsg()==null?"":resMsg.getErrorMsg());
            }
        }
        //正式下单请求失败
        else {
            String code = resMsg.getResCode();
            if(StringUtil.isBlank(code) && StringUtil.isNotBlank(resMsg.getResMsg())) {
                String[] temp = resMsg.getResMsg().split(",");
                code = temp[temp.length-1].trim();
            }
            logger.error("=========【19付正式下单请求失败】错误码："+code+"，原因："+resMsg.getResMsg()+"=========");
            //修改订单表
            reguOrder.setStatus(Constants.ORDER_STATUS_FAIL);
            reguOrder.setUpdateTime(new Date());
            reguOrder.setReturnCode(code);
            reguOrder.setReturnMsg(resMsg.getResMsg());
            payOrderMapper.updateByPrimaryKeySelective(reguOrder);
            //新增订单流水表
            reguJnl.setTransCode(Constants.ORDER_TRANS_CODE_FAIL);
            reguJnl.setSysTime(new Date());
            reguJnl.setCreateTime(new Date());
            reguJnl.setReturnCode(code);
            reguJnl.setReturnMsg(resMsg.getResMsg());
            payOrderJnlMapper.insertSelective(reguJnl);
            throw new PTMessageException(PTMessageEnum.USER_ORDER_REGULAR_FAIL,resMsg.getResMsg()==null?"":resMsg.getResMsg());
        }
    }

    /**
     * 充值通知前置校验，及数据装备
     * @param req
     * @return 充值通知后续业务需要的数据对象，校验不通过则throws异常
     */
    private Map<String, Object> notifyPreCheck(QuickPayResultInfo req){
        //判断订单是否存在
        BsPayOrdersExample orderExample = new BsPayOrdersExample();
        orderExample.createCriteria().andOrderNoEqualTo(req.getOrderId()).andStatusEqualTo(Constants.ORDER_STATUS_PAYING);
        List<BsPayOrders> orderList = payOrderMapper.selectByExample(orderExample);
        if(CollectionUtils.isEmpty(orderList)) {
            throw new PTMessageException(PTMessageEnum.PAYMENT_ORDER_NOT_EXIST);
        }
        final BsPayOrders order = orderList.get(0);
        Integer userId = order.getUserId();
        //判断用户是否存在
        final BsUser user = userMapper.selectByPrimaryKey(userId);
        if(!(user != null && user.getStatus() == Constants.USER_STATUS_1)) {
            throw new PTMessageException(PTMessageEnum.USER_NO_EXIT);
        }

        //判断用户主账户是否存在
        BsAccountExample accExample = new BsAccountExample();
        accExample.createCriteria().andUserIdEqualTo(order.getUserId());
        List<BsAccount> accList = accountMapper.selectByExample(accExample);
        if(CollectionUtils.isEmpty(accList)) {
            throw new PTMessageException(PTMessageEnum.ACCOUNT_NO_EXIT);
        }
        final BsAccount account = accList.get(0);

        //判断用户子账户是否存在
        final BsSubAccount subAccount = subAccountMapper.selectByPrimaryKey(order.getSubAccountId());
        if(subAccount == null) {
            throw new PTMessageException(PTMessageEnum.SUB_ACCOUNT_NO_EXIT);
        }
        //查询产品
        //final BsProduct product = proMapper.selectByPrimaryKey(subAccount.getProductId());
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("order", order);
        returnMap.put("user", user);
        returnMap.put("account", account);
        returnMap.put("subAccount", subAccount);
        //returnMap.put("product", product);
        return returnMap;
    }

    /**
     * baofoo充值异步返回通知
     * @param req
     * @throws
     */
    private void baofooNotify(final QuickPayResultInfo req, Map<String, Object> checkMap){
        final BsPayOrders order = (BsPayOrders) checkMap.get("order");
        final BsUser user = (BsUser) checkMap.get("user");
        final BsAccount account = (BsAccount) checkMap.get("account");
        final BsSubAccount subAccount = (BsSubAccount) checkMap.get("subAccount");
        //final BsProduct product = (BsProduct) checkMap.get("product");
        //事务操作
        transactionTemplate.execute(new TransactionCallbackWithoutResult(){

            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                BsPayOrders lockOrder = payOrderMapper.selectByPKForLock(order.getId());
                //成功
                if(OrderStatus.SUCCESS.getCode().equals(req.getStatus())) {
                    logger.info("=========【卡充值交易成功】币港湾订单号："+order.getOrderNo()+"=========");
                    //修改订单表
                    order.setStatus(Constants.ORDER_STATUS_SUCCESS);
                    order.setUpdateTime(new Date());
                    order.setReturnCode(req.getErrorCode());
                    order.setReturnMsg(req.getErrorMsg());
                    payOrderMapper.updateByPrimaryKeySelective(order);
                    //新增订单流水表
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

                    //修改用户交易明细表


                    BsUserTransDetailExample userTransExample = new BsUserTransDetailExample();
                    userTransExample.createCriteria().andUserIdEqualTo(order.getUserId()).andOrderNoEqualTo(order.getOrderNo());
                    int count = userTransMapper.countByExample(userTransExample);
                    if(count <= 0) {
                        BsUserTransDetail userTrans = new BsUserTransDetail();
                        // 新增用户交易明细表(充值)
                        BsUserTransDetail detail = new BsUserTransDetail();
                        userTrans.setUserId(order.getUserId());
                        userTrans.setCardNo(order.getBankCardNo());
                        userTrans.setTransType(Constants.Trans_TYPE_TOP_UP);
                        userTrans.setTransStatus(Constants.Trans_STATUS_SUCCESS);
                        userTrans.setOrderNo(order.getOrderNo());
                        userTrans.setCreateTime(new Date());
                        userTrans.setAmount(req.getAmount());
                        userTrans.setUpdateTime(new Date());
                        userTransMapper.insertSelective(userTrans);
                    } else {
                        BsUserTransDetail userTrans = new BsUserTransDetail();
                        userTrans.setTransStatus(Constants.Trans_STATUS_SUCCESS);
                        userTrans.setUpdateTime(new Date());
                        userTransMapper.updateByExampleSelective(userTrans, userTransExample);
                    }

                    //充值
                    //修改用户子账户表
                    BsSubAccount tempJsh = subAccountMapper.selectSingleSubActByUserAndType(order.getUserId(),Constants.PRODUCT_TYPE_JSH);
                    BsSubAccount subAccountLock = subAccountMapper.selectSubAccountByIdForLock(tempJsh.getId());
                    BsSubAccount readySubAccount = new BsSubAccount();
                    readySubAccount.setId(subAccountLock.getId());
                    readySubAccount.setBalance(MoneyUtil.add(subAccountLock.getBalance(), order.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    readySubAccount.setAvailableBalance(MoneyUtil.add(subAccountLock.getAvailableBalance(), order.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    readySubAccount.setCanWithdraw(MoneyUtil.add(subAccountLock.getCanWithdraw(), order.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    subAccountMapper.updateByPrimaryKeySelective(readySubAccount);
                    //新增用户记账流水表
                    BsAccountJnl accountJnl = new BsAccountJnl();
                    accountJnl.setTransTime(new Date());
                    accountJnl.setTransCode(Constants.TRANS_TOP_UP);
                    accountJnl.setTransName("充值");
                    accountJnl.setTransAmount(order.getAmount());
                    accountJnl.setSysTime(new Date());
                    accountJnl.setChannelTime(req.getOrderFinTime());
                    accountJnl.setChannelJnlNo(req.getMpOrderId()); //19付订单号
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
                    //修改系统子账户表
                    BsSysSubAccount sysSubAccountLock = bsSysSubAccountService.findSysSubAccount4Lock(Constants.SYS_ACCOUNT_USER);
                    BsSysSubAccount readyUpdate = new BsSysSubAccount();
                    readyUpdate.setId(sysSubAccountLock.getId());
                    readyUpdate.setBalance(MoneyUtil.add(sysSubAccountLock.getBalance(), order.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    readyUpdate.setAvailableBalance(MoneyUtil.add(sysSubAccountLock.getAvailableBalance(), order.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    readyUpdate.setCanWithdraw(MoneyUtil.add(sysSubAccountLock.getCanWithdraw(), order.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    sysAccountMapper.updateByPrimaryKeySelective(readyUpdate);
                    //新增系统记账流水表
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
                    //修改用户信息表
                    BsUser userLock = userMapper.selectByPKForLock(user.getId());
                    BsUser updateUser = new BsUser();
                    updateUser.setId(userLock.getId());
                    updateUser.setCanWithdraw(MoneyUtil.add(userLock.getCanWithdraw(), order.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    userMapper.updateByPrimaryKeySelective(updateUser);

                    //修改支付结果表状态
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
                    
                    
                    //对用户收取手续费并记账
                    chargeTopUpCommission(order.getOrderNo(), TransTypeEnum.USER_TOP_UP_QUICK_PAY, PayPlatformEnum.BAOFOO);
                }
                //失败
                else {
                    logger.error("=========【卡充值交易失败】币港湾订单号："+order.getOrderNo()+"，错误码："+req.getErrorCode()+"，原因："+req.getErrorMsg()+"=========");
                    //修改订单表
                    order.setStatus(Constants.ORDER_STATUS_FAIL);
                    order.setUpdateTime(new Date());
                    order.setReturnCode(req.getErrorCode());
                    order.setReturnMsg(req.getErrorMsg());
                    payOrderMapper.updateByPrimaryKeySelective(order);
                    //新增订单流水表
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

                    //修改用户交易明细表
                    BsUserTransDetail userTrans = new BsUserTransDetail();
                    userTrans.setTransStatus(Constants.Trans_STATUS_FAIL);
                    userTrans.setUpdateTime(new Date());
                    BsUserTransDetailExample userTransExample = new BsUserTransDetailExample();
                    userTransExample.createCriteria().andUserIdEqualTo(order.getUserId()).andOrderNoEqualTo(order.getOrderNo());
                    userTransMapper.updateByExampleSelective(userTrans, userTransExample);

                    //修改支付结果表状态
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

        });

        //获取手机号，发送短信，微信消息
        String mobile = getMobile(order.getUserId());
        //充值
        if(OrderStatus.SUCCESS.getCode().equals(req.getStatus())) {
            //message = "您有一笔充值已成功，充值金额："+order.getAmount()+"。如有疑问请拨打400-806-1230。";
            smsServiceClient.sendTemplateMessage(mobile, TemplateKey.TOP_UP_SUC, order.getAmount().toString());
            //发送微信模板消息
            sendWechatService.topUpMgs(order.getUserId(),"", mobile, order.getAmount().toString(), "suc", null);
        }
        else {
            //message = "您有一笔充值未成功，失败原因："+req.getErrorMsg()+"，请核实，如有疑问请拨打400-806-1230。";
            smsServiceClient.sendTemplateMessage(mobile, TemplateKey.TOP_UP_FALL, req.getErrorMsg());
            //发送微信模板消息
            sendWechatService.topUpMgs(order.getUserId(),"", mobile, order.getAmount().toString(), "fall", req.getErrorMsg());
        }
    }

    /**
     * 辅助渠道充值异步返回通知
     * @param req
     * @throws
     */
    private void assistNotify(final G2BReqMsg_ReapalQuickPay_ReapalNotify req, Map<String, Object> checkMap){
        final BsPayOrders order = (BsPayOrders) checkMap.get("order");
        final BsUser user = (BsUser) checkMap.get("user");
        final BsAccount account = (BsAccount) checkMap.get("account");
        final BsSubAccount subAccount = (BsSubAccount) checkMap.get("subAccount");
        //final BsProduct product = (BsProduct) checkMap.get("product");
        //查询辅助渠道是否存在
        BsHelpChannelAccountExample helpExample = new BsHelpChannelAccountExample();
        helpExample.createCriteria().andChannelEqualTo(order.getChannel());
        List<BsHelpChannelAccount> helpList = helpChannelMapper.selectByExample(helpExample);
        if(CollectionUtils.isEmpty(helpList)) {
            throw new PTMessageException(PTMessageEnum.HELP_CHANNEL_ERROR);
        }

        //事务操作
        transactionTemplate.execute(new TransactionCallbackWithoutResult(){

            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                //成功
                if(OrderStatus.SUCCESS.getCode().equals(req.getStatus())) {
                    logger.info("=========【充值辅助渠道交易成功】币港湾订单号："+order.getOrderNo()+"=========");
                    //修改订单表
                    order.setStatus(Constants.ORDER_STATUS_SUCCESS);
                    order.setUpdateTime(new Date());
                    order.setReturnCode(req.getResultCode());
                    order.setReturnMsg("交易成功");
                    payOrderMapper.updateByPrimaryKeySelective(order);
                    //新增订单流水表
                    BsPayOrdersJnl orderJnl = new BsPayOrdersJnl();
                    orderJnl.setOrderId(order.getId());
                    orderJnl.setOrderNo(order.getOrderNo());
                    orderJnl.setTransCode(Constants.ORDER_TRANS_CODE_SUCCESS);
                    orderJnl.setChannelTransType(order.getChannelTransType());
                    orderJnl.setTransAmount(order.getAmount());
                    orderJnl.setSysTime(new Date());
                    orderJnl.setChannelTime(new Date());
                    orderJnl.setChannelJnlNo(req.getTradeNo());
                    orderJnl.setSubAccountId(order.getSubAccountId());
                    orderJnl.setSubAccountCode(subAccount.getCode());
                    orderJnl.setUserId(order.getUserId());
                    orderJnl.setReturnCode(req.getResultCode());
                    orderJnl.setReturnMsg(req.getResultMsg());
                    orderJnl.setCreateTime(new Date());
                    payOrderJnlMapper.insertSelective(orderJnl);
                    //修改用户交易明细表
                    BsUserTransDetail userTrans = new BsUserTransDetail();
                    userTrans.setTransStatus(Constants.Trans_STATUS_SUCCESS);
                    userTrans.setUpdateTime(new Date());
                    BsUserTransDetailExample userTransExample = new BsUserTransDetailExample();
                    userTransExample.createCriteria().andUserIdEqualTo(order.getUserId()).andOrderNoEqualTo(order.getOrderNo());
                    userTransMapper.updateByExampleSelective(userTrans, userTransExample);
                    //修改支付签约回执表
                    receiptMapper.updateBindStatus(order.getUserId(), order.getBankCardNo(), order.getChannel());
                    //充值
                    // 修改用户子账户表
                    BsSubAccount tempJsh = subAccountMapper.selectSingleSubActByUserAndType(order.getUserId(),Constants.PRODUCT_TYPE_JSH);
                    BsSubAccount subAccountLock = subAccountMapper.selectSubAccountByIdForLock(tempJsh.getId());
                    BsSubAccount readySubAccount = new BsSubAccount();
                    readySubAccount.setId(subAccountLock.getId());
                    readySubAccount.setBalance(MoneyUtil.add(subAccountLock.getBalance(), order.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    readySubAccount.setAvailableBalance(MoneyUtil.add(subAccountLock.getAvailableBalance(), order.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    readySubAccount.setCanWithdraw(MoneyUtil.add(subAccountLock.getCanWithdraw(), order.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    subAccountMapper.updateByPrimaryKeySelective(readySubAccount);
                    //新增用户记账流水表
                    BsAccountJnl accountJnl = new BsAccountJnl();
                    accountJnl.setTransTime(new Date());
                    accountJnl.setTransCode(Constants.TRANS_TOP_UP);
                    accountJnl.setTransName("充值");
                    accountJnl.setTransAmount(order.getAmount());
                    accountJnl.setSysTime(new Date());
                    accountJnl.setChannelTime(new Date());
                    accountJnl.setChannelJnlNo(req.getTradeNo()); //辅助渠道订单号
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
                    //修改系统子账户表
                    BsSysSubAccount sysSubAccountLock = bsSysSubAccountService.findSysSubAccount4Lock(Constants.SYS_ACCOUNT_USER);
                    BsSysSubAccount readyUpdate = new BsSysSubAccount();
                    readyUpdate.setId(sysSubAccountLock.getId());
                    readyUpdate.setBalance(MoneyUtil.add(sysSubAccountLock.getBalance(), order.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    readyUpdate.setAvailableBalance(MoneyUtil.add(sysSubAccountLock.getAvailableBalance(), order.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    readyUpdate.setCanWithdraw(MoneyUtil.add(sysSubAccountLock.getCanWithdraw(), order.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    sysAccountMapper.updateByPrimaryKeySelective(readyUpdate);
                    //新增系统记账流水表
                    BsSysAccountJnl sysAccountJnl = new BsSysAccountJnl();
                    sysAccountJnl.setTransTime(new Date());
                    sysAccountJnl.setTransCode(Constants.SYS_USER_TOP_UP);
                    sysAccountJnl.setTransName("用户充值");
                    sysAccountJnl.setTransAmount(order.getAmount());
                    sysAccountJnl.setSysTime(new Date());
                    sysAccountJnl.setChannelTime(new Date());
                    sysAccountJnl.setChannelJnlNo(req.getTradeNo());
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
                    //修改辅助渠道系统账户表
                    BsHelpChannelAccount helpChannel = helpChannelMapper.selectChannelForLock(order.getChannel());
                    BsHelpChannelAccount updateChannel = new BsHelpChannelAccount();
                    updateChannel.setId(helpChannel.getId());
                    updateChannel.setBalance(MoneyUtil.add(helpChannel.getBalance(), order.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    updateChannel.setAvailableBalance(MoneyUtil.add(helpChannel.getAvailableBalance(), order.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    updateChannel.setCanWithdraw(MoneyUtil.add(helpChannel.getCanWithdraw(), order.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    helpChannelMapper.updateByPrimaryKeySelective(updateChannel);
                    //修改用户信息表
                    BsUser userLock = userMapper.selectByPKForLock(user.getId());
                    BsUser updateUser = new BsUser();
                    updateUser.setId(userLock.getId());
                    updateUser.setCanWithdraw(MoneyUtil.add(userLock.getCanWithdraw(), order.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    userMapper.updateByPrimaryKeySelective(updateUser);
               
                    //对用户收取手续费并记账
                    chargeTopUpCommission(order.getOrderNo(), TransTypeEnum.USER_TOP_UP_QUICK_PAY, PayPlatformEnum.OTHER);
                    
                }
                //失败
                else {
                    logger.error("=========【充值辅助渠道交易失败】币港湾订单号："+order.getOrderNo()+"，错误码："+req.getResultCode()+"，原因："+req.getResultMsg()+"=========");
                    //修改订单表
                    order.setStatus(Constants.ORDER_STATUS_FAIL);
                    order.setUpdateTime(new Date());
                    order.setReturnCode(req.getResultCode());
                    order.setReturnMsg(req.getResultMsg());
                    payOrderMapper.updateByPrimaryKeySelective(order);
                    //新增订单流水表
                    BsPayOrdersJnl orderJnl = new BsPayOrdersJnl();
                    orderJnl.setOrderId(order.getId());
                    orderJnl.setOrderNo(order.getOrderNo());
                    orderJnl.setTransCode(Constants.ORDER_TRANS_CODE_FAIL);
                    orderJnl.setChannelTransType(order.getChannelTransType());
                    orderJnl.setTransAmount(order.getAmount());
                    orderJnl.setSysTime(new Date());
                    orderJnl.setChannelTime(new Date());
                    orderJnl.setChannelJnlNo(req.getTradeNo());
                    orderJnl.setSubAccountId(order.getSubAccountId());
                    orderJnl.setSubAccountCode(subAccount.getCode());
                    orderJnl.setUserId(order.getUserId());
                    orderJnl.setReturnCode(req.getResultCode());
                    orderJnl.setReturnMsg(req.getResultMsg());
                    orderJnl.setCreateTime(new Date());
                    payOrderJnlMapper.insertSelective(orderJnl);

                    //修改用户交易明细表
                    BsUserTransDetail userTrans = new BsUserTransDetail();
                    userTrans.setTransStatus(Constants.Trans_STATUS_FAIL);
                    userTrans.setUpdateTime(new Date());
                    BsUserTransDetailExample userTransExample = new BsUserTransDetailExample();
                    userTransExample.createCriteria().andUserIdEqualTo(order.getUserId()).andOrderNoEqualTo(order.getOrderNo());
                    userTransMapper.updateByExampleSelective(userTrans, userTransExample);
                }
            }

        });

        //获取手机号，发送短信，微信消息
        String mobile = getMobile(order.getUserId());
        //充值
        if(OrderStatus.SUCCESS.getCode().equals(req.getStatus())) {
            //message = "您有一笔充值已成功，充值金额："+order.getAmount()+"。如有疑问请拨打400-806-1230。";
            smsServiceClient.sendTemplateMessage(mobile, TemplateKey.TOP_UP_SUC, order.getAmount().toString());
            //发送微信模板消息
            sendWechatService.topUpMgs(order.getUserId(),"", mobile, order.getAmount().toString(), "suc", null);
        }
        else {
            //message = "您有一笔充值未成功，失败原因："+req.getErrorMsg()+"，请核实，如有疑问请拨打400-806-1230。";
            smsServiceClient.sendTemplateMessage(mobile, TemplateKey.TOP_UP_FALL, req.getResultMsg());
            //发送微信模板消息
            sendWechatService.topUpMgs(order.getUserId(),"", mobile, order.getAmount().toString(), "fall", req.getResultMsg());
        }
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
     * 网银充值
     *
     * @param req 请求信息
     * @param res 用于结果返回
     */
    @Override
    public void eBank(ReqMsg_RegularBuy_EBankBuy req, ResMsg_RegularBuy_EBankBuy res) {
        Date now = new Date();
        BsProduct product = null;
        //1、判断用户是否存在，判断是否成年
        BsUser user = userMapper.selectByPrimaryKey(req.getUserId());
        if(!(user != null && user.getStatus() == Constants.USER_STATUS_1)) {
            throw new PTMessageException(PTMessageEnum.USER_NO_EXIT);
        }
        if(!IdcardUtils.isAdultAge(user.getIdCard())){
            throw new PTMessageException(PTMessageEnum.USER_NOT_ADULT);
        }
        
        
        // 1.1、不存在投资中的产品，则不再允许充值和购买
        if(IdcardUtils.isOldAge(user.getIdCard(), Constants.OLD_MAN_AGE_LIMIT)) {
            BsAccountExample accountExample = new BsAccountExample();
            accountExample.createCriteria().andUserIdEqualTo(user.getId());
            List<BsAccount> bsAccounts = accountMapper.selectByExample(accountExample);
            BsSubAccountExample regExample = new BsSubAccountExample();
            List<Integer> statusList = new ArrayList<>();
            statusList.add(Constants.SUBACCOUNT_STATUS_OPEN);
            statusList.add(Constants.SUBACCOUNT_STATUS_SETTLE);
            statusList.add(Constants.SUBACCOUNT_STATUS_CLOSE);
            regExample.createCriteria().andProductTypeEqualTo(Constants.PRODUCT_TYPE_REG).andStatusNotIn(statusList).andAccountIdEqualTo(bsAccounts.get(0).getId());
            int regCount = subAccountMapper.countByExample(regExample);
            BsSubAccountExample regdExample = new BsSubAccountExample();
            regdExample.createCriteria().andProductTypeEqualTo(Constants.PRODUCT_TYPE_AUTH).andStatusNotIn(statusList).andAccountIdEqualTo(bsAccounts.get(0).getId());
            int authCount = subAccountMapper.countByExample(regdExample);
            if(regCount + authCount <= 0) {
                throw new PTMessageException(PTMessageEnum.USER_IS_OLD_MAN);
            }
        }
        
        
        if(user.getIsBindBank() == Constants.IS_BIND_BANK_NO) {
            throw new PTMessageException(PTMessageEnum.BANKCARD_NOT_BIND);
        }
        //12、判断充值金额是否达到最低充值金额
        BsSysConfig sys = sysMapper.selectByPrimaryKey(Constants.RECHANGE_LIMIT);
        if(Double.parseDouble(sys.getConfValue()) > req.getAmount()) {
            throw new PTMessageException(PTMessageEnum.RECHANGE_AMOUNT_IS_NOT_PASS.getCode(), "充值金额不能低于" + sys.getConfValue() + "元");
        }

        Bs19payBankExample bs19payBankExample = new Bs19payBankExample();
        bs19payBankExample.createCriteria().andPay19BankCodeEqualTo(req.getBankCode());
        List<Bs19payBank >  bs19payBankList = payBankMapper.selectByExample(bs19payBankExample);
        BsBank bsBank = new BsBank();
        if (!CollectionUtils.isEmpty(bs19payBankList)) {
            bsBank = bsBankMapper.selectByPrimaryKey(bs19payBankList.get(0).getBankId());
        }

        //==========================================验证结束============================================

        Integer subAccountId = null;
        String subAccountCode = null;
        //充值
        //查询子账户表中状态为"JSH"的记录
        List<BsSubAccount> subList = subAccountMapper.selectSubAccount(req.getUserId(), Constants.SYS_ACCOUNT_JSH, Constants.SUBACCOUNT_STATUS_OPEN);
        if(!CollectionUtils.isEmpty(subList) && subList.size() > 0) {
            subAccountId = subList.get(0).getId();
            subAccountCode = subList.get(0).getCode();
        }
        else {
            throw new PTMessageException(PTMessageEnum.USER_JSH_NO_EXIT);
        }

        //新增订单表
        BsPayOrders order = new BsPayOrders();
        order.setOrderNo(Util.generateOrderNo(req.getUserId()));
        order.setAmount(req.getAmount());
        order.setUserId(req.getUserId());
        order.setSubAccountId(subAccountId);
        order.setChannel(Constants.CHANNEL_BAOFOO);
        order.setStatus(Constants.ORDER_STATUS_CREATE);
        order.setMoneyType(Constants.ORDER_CURRENCY_CNY);
        order.setTerminalType(Constants.TERMINAL_TYPE_PC);
        order.setCreateTime(now);
        order.setUpdateTime(now);
        order.setAccountType(Constants.ORDER_ACCOUNT_TYPE_USER);
        order.setTransType(Constants.TRANS_TOP_UP);
        order.setChannelTransType(Constants.CHANNEL_TRS_NETBANK);
        order.setMobile(user.getMobile());
        order.setIdCard(user.getIdCard());
        order.setUserName(user.getUserName());
        if (bsBank != null ) {
            order.setBankId(bsBank.getId());
            order.setBankName(BaoFooEnum.cardBinMap.get(String.valueOf(bsBank.getId())));
        }
        payOrderMapper.insertSelective(order);

        //新增订单流水表
        BsPayOrdersJnl jnl = new BsPayOrdersJnl();
        jnl.setOrderId(order.getId());
        jnl.setOrderNo(order.getOrderNo());
        jnl.setTransCode(Constants.ORDER_TRANS_CODE_INIT);
        jnl.setChannelTransType(Constants.CHANNEL_TRS_NETBANK);
        jnl.setTransAmount(order.getAmount());
        jnl.setSysTime(now);
        jnl.setUserId(req.getUserId());
        jnl.setSubAccountId(subAccountId);
        jnl.setSubAccountCode(subAccountCode);
        jnl.setCreateTime(now);
        payOrderJnlMapper.insertSelective(jnl);

        //设置返回数据
        res.setOrderDate(order.getCreateTime());
        res.setOrderNo(order.getOrderNo());


       Bs19payBank bank = bs19PayBankService.selectByPrimaryKey(Integer.parseInt(req.getBankCode()));
        
        //充值前置操作
        B2GReqMsg_BaoFooQuickPay_EBank eBankReq = new B2GReqMsg_BaoFooQuickPay_EBank();
        eBankReq.setPayId(BaoFooEnum.ebankPayMap.get(String.valueOf(bank.getBankId())));
        eBankReq.setTradeDate(com.pinting.core.util.DateUtil.formatDateTime(res.getOrderDate(),"yyyyMMddHHmmss"));
        eBankReq.setTransId(res.getOrderNo());
        Integer amount = MoneyUtil.multiply(req.getAmount(),100d).intValue();
        eBankReq.setOrderMoney(String.valueOf(amount));
        eBankReq.setProductName(user.getUserName()+"充值"+req.getAmount());
        eBankReq.setUserName(user.getUserName());
        eBankReq.setAdditionalInfo(null);
        //需要根据不同的用户调用不同的页面
        if (StringUtil.isNotEmpty(req.getWebFlag()) && "qianbao178NetBankBuy".equals(req.getWebFlag())) {
            eBankReq.setPageUrl(GlobEnvUtil.get("ebankbuy.return.url.gen178"));
        }else {
            eBankReq.setPageUrl(GlobEnvUtil.get("ebankbuy.return.url.gen"));
        }


        B2GResMsg_BaoFooQuickPay_EBank eBankRes = null;
        try {
            eBankRes = netBankService.netBankBuyProductBaofoo(eBankReq);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PTMessageException(PTMessageEnum.DAFY_BUY_PRODUCT_ERROR.getCode(), "充值异常!");
        }

        if(StringUtil.isEmpty(eBankRes.getHtmlStr())){

            throw new PTMessageException(PTMessageEnum.DAFY_BUY_PRODUCT_ERROR.getCode(), res.getResMsg());
        }else{
            //返回页
            res.setResHtml(eBankRes.getHtmlStr());
        }
        logger.info("=========【网银充值下单成功】币港湾订单号："+order.getOrderNo()+"=========");
    }

    /**
     * 网银充值通知
     *
     * @param req 请求信息
     */
    @Override
    public void notifyEBank(final EBankResultInfo req) {
        try {
            jsClientDaoSupport.lock(RedisLockEnum.LOCK_USER_TOPUP.getKey());
            //判断订单是否存在
            BsPayOrdersExample orderExample = new BsPayOrdersExample();
            orderExample.createCriteria().andOrderNoEqualTo(req.getMxOrderId()).andStatusEqualTo(Constants.ORDER_STATUS_CREATE);
            List<BsPayOrders> orderList = payOrderMapper.selectByExample(orderExample);
            if(CollectionUtils.isEmpty(orderList)) {
                throw new PTMessageException(PTMessageEnum.PAYMENT_ORDER_NOT_EXIST);
            }
            final BsPayOrders order = orderList.get(0);
            final Integer bankId = order.getBankId();

            //判断用户是否存在
            final BsUser user = userMapper.selectByPrimaryKey(order.getUserId());
            if(!(user != null && user.getStatus() == Constants.USER_STATUS_1)) {
                throw new PTMessageException(PTMessageEnum.USER_NO_EXIT);
            }

            //判断用户主账户是否存在
            BsAccountExample accExample = new BsAccountExample();
            accExample.createCriteria().andUserIdEqualTo(order.getUserId());
            List<BsAccount> accList = accountMapper.selectByExample(accExample);
            if(CollectionUtils.isEmpty(accList)) {
                throw new PTMessageException(PTMessageEnum.ACCOUNT_NO_EXIT);
            }
            final BsAccount account = accList.get(0);

            //判断用户子账户是否存在
            final BsSubAccount subAccount = subAccountMapper.selectByPrimaryKey(order.getSubAccountId());
            if(subAccount == null) {
                throw new PTMessageException(PTMessageEnum.SUB_ACCOUNT_NO_EXIT);
            }

            //银行名称
            String temp = null;
            /*if(StringUtil.isNotBlank(req.getBankId())) {
                List<Pay19BankVO> pay19BankList = pay19Mapper.select19PayBankList(null, null, req.getBankId());
                if(!CollectionUtils.isEmpty(pay19BankList)) {
                    temp = BaoFooEnum.cardBinMap.get(String.valueOf(pay19BankList.get(0).getId()));
                }
            }*/
            temp = BaoFooEnum.cardBinMap.get(String.valueOf(bankId));

            final String bankName = temp;

            //查询产品
            final BsProduct product = proMapper.selectByPrimaryKey(subAccount.getProductId());

            //==========================================验证结束============================================

            transactionTemplate.execute(new TransactionCallbackWithoutResult(){

                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {

                    if(OrderStatus.SUCCESS.getCode().equals(req.getResult())) {
                        logger.info("=========【网银充值交易成功】币港湾订单号："+order.getOrderNo()+"=========");
                        //修改订单表
                        order.setBankName(bankName);
                        order.setStatus(Constants.ORDER_STATUS_SUCCESS);
                        order.setUpdateTime(new Date());
                        payOrderMapper.updateByPrimaryKeySelective(order);
                        //新增订单流水表
                        BsPayOrdersJnl orderJnl = new BsPayOrdersJnl();
                        orderJnl.setOrderId(order.getId());
                        orderJnl.setOrderNo(order.getOrderNo());
                        orderJnl.setTransCode(Constants.ORDER_TRANS_CODE_SUCCESS);
                        orderJnl.setChannelTransType(order.getChannelTransType());
                        orderJnl.setTransAmount(order.getAmount());
                        orderJnl.setSysTime(new Date());
                        orderJnl.setChannelTime(req.getPayDate());
                        orderJnl.setChannelJnlNo(req.getMpOrderId());
                        orderJnl.setSubAccountId(order.getSubAccountId());
                        orderJnl.setSubAccountCode(subAccount.getCode());
                        orderJnl.setUserId(order.getUserId());
                        orderJnl.setCreateTime(new Date());
                        payOrderJnlMapper.insertSelective(orderJnl);
                        //新增用户交易明细表(充值)
                        BsUserTransDetail detail1 = new BsUserTransDetail();
                        detail1.setUserId(order.getUserId());
                        detail1.setTransType(Constants.Trans_TYPE_TOP_UP);
                        detail1.setTransStatus(Constants.Trans_STATUS_SUCCESS);
                        detail1.setOrderNo(order.getOrderNo());
                        detail1.setCreateTime(new Date());
                        detail1.setAmount(order.getAmount());
                        detail1.setUpdateTime(new Date());
                        userTransMapper.insertSelective(detail1);
                        //修改用户子账户表
                        BsSubAccount tempJsh = subAccountMapper.selectSingleSubActByUserAndType(order.getUserId(),Constants.PRODUCT_TYPE_JSH);
                        BsSubAccount subAccountLock = subAccountMapper.selectSubAccountByIdForLock(tempJsh.getId());
                        BsSubAccount readySubAccount = new BsSubAccount();
                        readySubAccount.setId(subAccountLock.getId());
                        readySubAccount.setBalance(MoneyUtil.add(subAccountLock.getBalance(), order.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                        readySubAccount.setAvailableBalance(MoneyUtil.add(subAccountLock.getAvailableBalance(), order.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                        readySubAccount.setCanWithdraw(MoneyUtil.add(subAccountLock.getCanWithdraw(), order.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                        subAccountMapper.updateByPrimaryKeySelective(readySubAccount);
                        //新增用户记账流水表
                        BsAccountJnl accountJnl = new BsAccountJnl();
                        accountJnl.setTransTime(new Date());
                        accountJnl.setTransCode(Constants.TRANS_TOP_UP);
                        accountJnl.setTransName("充值");
                        accountJnl.setTransAmount(order.getAmount());
                        accountJnl.setSysTime(new Date());
                        accountJnl.setChannelTime(req.getPayDate());
                        accountJnl.setChannelJnlNo(req.getMpOrderId()); //19付订单号
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
                        //修改系统子账户表
                        BsSysSubAccount sysSubAccountLock = bsSysSubAccountService.findSysSubAccount4Lock(Constants.SYS_ACCOUNT_USER);
                        BsSysSubAccount readyUpdate = new BsSysSubAccount();
                        readyUpdate.setId(sysSubAccountLock.getId());
                        readyUpdate.setBalance(MoneyUtil.add(sysSubAccountLock.getBalance(), order.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                        readyUpdate.setAvailableBalance(MoneyUtil.add(sysSubAccountLock.getAvailableBalance(), order.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                        readyUpdate.setCanWithdraw(MoneyUtil.add(sysSubAccountLock.getCanWithdraw(), order.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                        sysAccountMapper.updateByPrimaryKeySelective(readyUpdate);
                        //新增系统记账流水表
                        BsSysAccountJnl sysAccountJnl = new BsSysAccountJnl();
                        sysAccountJnl.setTransTime(new Date());
                        sysAccountJnl.setTransCode(Constants.SYS_USER_TOP_UP);
                        sysAccountJnl.setTransName("用户充值");
                        sysAccountJnl.setTransAmount(order.getAmount());
                        sysAccountJnl.setSysTime(new Date());
                        sysAccountJnl.setChannelTime(req.getPayDate());
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
                        //修改用户信息表
                        BsUser userLock = userMapper.selectByPKForLock(user.getId());
                        BsUser updateUser = new BsUser();
                        updateUser.setId(userLock.getId());
                        updateUser.setCanWithdraw(MoneyUtil.add(userLock.getCanWithdraw(), order.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                        userMapper.updateByPrimaryKeySelective(updateUser);

                        
                        //对用户收取手续费并记账
                        chargeTopUpCommission(order.getOrderNo(), TransTypeEnum.USER_TOP_UP_E_BANK, PayPlatformEnum.BAOFOO);
                        
                    }
                    else {
                        logger.error("=========【网银充值交易失败】币港湾订单号："+order.getOrderNo()+"=========");
                        //修改订单表
                        order.setBankName(bankName);
                        order.setStatus(Constants.ORDER_STATUS_FAIL);
                        order.setUpdateTime(new Date());
                        order.setReturnCode(req.getRetCode());
                        order.setReturnMsg(req.getErrorMsg());
                        payOrderMapper.updateByPrimaryKeySelective(order);
                        //新增订单流水表
                        BsPayOrdersJnl orderJnl = new BsPayOrdersJnl();
                        orderJnl.setOrderId(order.getId());
                        orderJnl.setOrderNo(order.getOrderNo());
                        orderJnl.setTransCode(Constants.ORDER_TRANS_CODE_FAIL);
                        orderJnl.setChannelTransType(order.getChannelTransType());
                        orderJnl.setTransAmount(order.getAmount());
                        orderJnl.setSysTime(new Date());
                        orderJnl.setChannelTime(req.getPayDate());
                        orderJnl.setChannelJnlNo(req.getMpOrderId());
                        orderJnl.setUserId(order.getUserId());
                        orderJnl.setCreateTime(new Date());
                        payOrderJnlMapper.insertSelective(orderJnl);
                        //新增用户交易明细表(充值)
                        BsUserTransDetail detail1 = new BsUserTransDetail();
                        detail1.setUserId(order.getUserId());
                        detail1.setTransType(Constants.Trans_TYPE_TOP_UP);
                        detail1.setTransStatus(Constants.Trans_STATUS_FAIL);
                        detail1.setOrderNo(order.getOrderNo());
                        detail1.setCreateTime(new Date());
                        detail1.setAmount(order.getAmount());
                        detail1.setUpdateTime(new Date());
                        userTransMapper.insertSelective(detail1);
                    }
                }

            });
            //获取手机号，发送短信，微信消息
            String mobile = getMobile(order.getUserId());
            //充值
            if(OrderStatus.SUCCESS.getCode().equals(req.getResult())) {
                //message = "您有一笔充值已成功，充值金额："+order.getAmount()+"。如有疑问请拨打400-806-1230。";
                smsServiceClient.sendTemplateMessage(mobile, TemplateKey.TOP_UP_SUC, order.getAmount().toString());
                //发送微信模板消息
                sendWechatService.topUpMgs(order.getUserId(),"", mobile, order.getAmount().toString(), "suc", null);
            }
            else {
                //message = "您有一笔充值未成功，失败原因：网银通道失败，请核实，如有疑问请拨打400-806-1230。";
                smsServiceClient.sendTemplateMessage(mobile, TemplateKey.TOP_UP_FALL, "网银通道失败");
                //发送微信模板消息
                sendWechatService.topUpMgs(order.getUserId(),"", mobile, order.getAmount().toString(), "fall", "网银通道失败");
            }
        } finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_USER_TOPUP.getKey());
        }

    }
    
    /**
     * 充值手续费扣除并记账
     */
    public void chargeTopUpCommission(String orderNo, TransTypeEnum transTypeEnum, PayPlatformEnum payPlatformEnum){

    	logger.info("=========【充值交易成功扣除手续费开始】币港湾订单号："+orderNo+"=========");
    	BsPayOrdersExample ordersExample = new BsPayOrdersExample();
    	ordersExample.createCriteria().andOrderNoEqualTo(orderNo);
    	List<BsPayOrders> reguOrderList = payOrderMapper.selectByExample(ordersExample);
    	BsPayOrders reguOrder = reguOrderList.get(0);
    	
    	CommissionVO commissionVO = commissionService.calServiceFee(reguOrder.getAmount(), transTypeEnum, payPlatformEnum);
    	logger.info("=========【充值交易成功扣除手续费】应扣手续费："+commissionVO.getNeedPayAmount()+"；实际扣除手续费："+commissionVO.getActPayAmount()+"===================");
    	//1、记录手续费登记表
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

        // 2、新增用户交易明细表(手续费)
        if (commissionVO.getActPayAmount()>0) {
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

            //3、修改用户子账户
            BsSubAccount tempJsh = subAccountMapper.selectSingleSubActByUserAndType(reguOrder.getUserId(), Constants.PRODUCT_TYPE_JSH);
            BsSubAccount subAccountLockFee = subAccountMapper.selectSubAccountByIdForLock(tempJsh.getId());
            BsSubAccount readySubAccountFee = new BsSubAccount();
            readySubAccountFee.setId(subAccountLockFee.getId());
            readySubAccountFee.setBalance(MoneyUtil.subtract(subAccountLockFee.getBalance(), commissionVO.getActPayAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            readySubAccountFee.setAvailableBalance(MoneyUtil.subtract(subAccountLockFee.getAvailableBalance(), commissionVO.getActPayAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            readySubAccountFee.setCanWithdraw(MoneyUtil.subtract(subAccountLockFee.getCanWithdraw(), commissionVO.getActPayAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            subAccountMapper.updateByPrimaryKeySelective(readySubAccountFee);

            //4、用户记账流水BsAccountJnl
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

            //5、修改用户可提现余额
            BsUser userLockFee = userMapper.selectByPKForLock(reguOrder.getUserId());
            BsUser updateUserFee = new BsUser();
            updateUserFee.setId(userLockFee.getId());
            updateUserFee.setCanWithdraw(MoneyUtil.subtract(userLockFee.getCanWithdraw(), commissionVO.getActPayAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            userMapper.updateByPrimaryKeySelective(updateUserFee);
		}
        logger.info("=========【充值交易成功扣除手续费成功】币港湾订单号："+orderNo+"=========");
    }
    
    
}

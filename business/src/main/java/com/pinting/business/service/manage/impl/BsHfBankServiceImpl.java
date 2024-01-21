package com.pinting.business.service.manage.impl;

import com.pinting.business.accounting.finance.model.SysRechargeResultInfo;
import com.pinting.business.accounting.finance.model.SysWithdrawResultInfo;
import com.pinting.business.accounting.service.BsSysSubAccountService;
import com.pinting.business.accounting.service.PayOrdersService;
import com.pinting.business.dao.*;
import com.pinting.business.enums.HFBankAccountType;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.*;
import com.pinting.business.model.vo.*;
import com.pinting.business.service.common.CommissionService;
import com.pinting.business.service.manage.BsHfBankService;
import com.pinting.business.service.manage.BsSubAccountService;
import com.pinting.business.service.site.BankCardService;
import com.pinting.business.service.site.SysConfigService;
import com.pinting.business.util.Constants;
import com.pinting.business.util.Util;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.hessian.message.hfbank.*;
import com.pinting.gateway.hessian.message.hfbank.model.BatchWithdrawExtData;
import com.pinting.gateway.out.service.HfbankTransportService;
import com.pinting.gateway.smsEnums.TemplateKey;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.util.*;

/**
 *
 * @author SHENGP
 * @date  2017年4月19日 下午4:14:25
 */
@Service
public class BsHfBankServiceImpl implements BsHfBankService {

	private Logger LOG = LoggerFactory.getLogger(BsHfBankServiceImpl.class);
	
	@Autowired
	private HfbankTransportService hfbankTransportService;
    @Autowired
    private BsSysSubAccountService bsSysSubAccountService;
    @Autowired
    private CommissionService commissionService;
    @Autowired
    private BankCardService bankCardService;
	@Autowired
	private BsPayOrdersMapper bsPayOrdersMapper;
	@Autowired
	private BsSysSubAccountMapper bsSysSubAccountMapper;
	@Autowired
	private BsSysAccountJnlMapper bsSysAccountJnlMapper;
	@Autowired
	private BsPayOrdersJnlMapper bsPayOrdersJnlMapper;
    @Autowired
    private TransactionTemplate transactionTemplate;
	@Autowired
	private BsUserMapper bsUserMapper;
    @Autowired
    private BsHfbankUserExtMapper bsHfbankUserExtMapper;
    @Autowired
    private BsBankCardMapper bsBankCardMapper;
    @Autowired
    private BsPayReceiptMapper bsPayReceiptMapper;
    @Autowired
    private BsSysConfigMapper sysMapper;
    @Autowired
    private BsBankMapper bsBankMapper;
    @Autowired
    private BsSubAccountService bsSubAccountService;
    
    @Autowired
    private PayOrdersService payOrdersService;
    @Autowired
    private LnPayOrdersMapper lnPayOrdersMapper;
    @Autowired
    private LnPayOrdersJnlMapper lnPayOrdersJnlMapper;
    @Autowired 
    private LnUserMapper lnUserMapper;
	@Autowired
	private BsSubAccountMapper bsSubAccountMapper;
    @Autowired
	private BsAccountJnlMapper bsAccountJnlMapper;
	@Autowired
	private LnBindCardMapper lnBindCardMapper;
	@Autowired
    private SysConfigService sysConfigService;
	@Autowired
	private BsUserTransDetailMapper bsUserTransDetailMapper;
	
	@Override
	public String sysTopUp(BsHfBankSysTopUpVo bsHfBankSysTopUpVo) {
		LOG.info("[sysTopUp]入参：" + bsHfBankSysTopUpVo.toString());
		Double amount = bsHfBankSysTopUpVo.getAmount();
		if(StringUtil.isBlank(bsHfBankSysTopUpVo.getNote()) || amount == null){
			throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
		}
		String orderNo = Util.generateSysOrderNo("HFRS");
		
		// 封装恒丰平台充值请求体
		Date transTime = new Date();
		B2GReqMsg_HFBank_PlatCharge req = new B2GReqMsg_HFBank_PlatCharge();
		req.setOrder_no(orderNo);
		req.setPartner_trans_date(transTime);
		req.setPartner_trans_time(transTime);
		req.setAmount(bsHfBankSysTopUpVo.getAmount());

		// 新增订单信息 
		final BsPayOrders orders = new BsPayOrders();
		BeanUtils.copyProperties(bsHfBankSysTopUpVo, orders);
		orders.setOrderNo(orderNo);
		orders.setMoneyType(Constants.ORDER_CURRENCY_CNY); 
		orders.setAccountType(Constants.ORDER_ACCOUNT_TYPE_SYS);
		orders.setTransType(Constants.TRANS_HFBANK_SYS_TOP_UP);
		orders.setChannel(Constants.CHANNEL_HFBANK);
		orders.setStatus(Constants.ORDER_STATUS_CREATE);
		orders.setCreateTime(transTime);
		bsPayOrdersMapper.insertSelective(orders);
		//新增订单流水表
        BsPayOrdersJnl jnl = fillBsPayOrdersJnl(orders, transTime);
        jnl.setTransCode(Constants.ORDER_TRANS_CODE_INIT);
        jnl.setChannelTransType(Constants.TRANS_HFBANK_SYS_TOP_UP);
//        jnl.setNote(bsHfBankSysTopUpVo.getNote());
        bsPayOrdersJnlMapper.insertSelective(jnl);
        
     	// 调用平台充值接口
 		B2GResMsg_HFBank_PlatCharge res = null;
 		try {
 			res = hfbankTransportService.platCharge(req);
 		} catch (Exception e) {
 			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {                	
                	BsPayOrders ordersFail = new BsPayOrders();
                	ordersFail.setId(orders.getId());
                	ordersFail.setStatus(Constants.ORDER_STATUS_CON_FAIL);
                	ordersFail.setUpdateTime(new Date());
                	bsPayOrdersMapper.updateByPrimaryKeySelective(ordersFail);
                	BsPayOrdersJnl jnlFail = fillBsPayOrdersJnl(ordersFail, new Date());
                	jnlFail.setOrderNo(orders.getOrderNo());
                	jnlFail.setTransAmount(orders.getAmount());
                	jnlFail.setChannelTransType(Constants.TRANS_HFBANK_SYS_TOP_UP);
                	jnlFail.setTransCode(Constants.ORDER_TRANS_CODE_COMM_FAIL);
                	bsPayOrdersJnlMapper.insertSelective(jnlFail);
                }
 			});
        	throw new PTMessageException(PTMessageEnum.TRANS_ERROR);
 		}
 		transTime = new Date();
 		if (Constants.DEP_RECODE_SUCCESS.equals(res.getRecode())) {
			// 订单和订单流水更新为成功
			BsPayOrders ordersSucc = new BsPayOrders();
            ordersSucc.setId(orders.getId());
            ordersSucc.setStatus(Constants.ORDER_STATUS_PAYING);
            ordersSucc.setUpdateTime(transTime);
            ordersSucc.setReturnCode(res.getRecode());
            ordersSucc.setReturnMsg(res.getRemsg());
            bsPayOrdersMapper.updateByPrimaryKeySelective(ordersSucc);
            BsPayOrdersJnl jnlSucc = fillBsPayOrdersJnl(ordersSucc, transTime);
            jnlSucc.setOrderNo(orderNo);
            jnlSucc.setTransAmount(amount);
            jnlSucc.setChannelTransType(Constants.TRANS_HFBANK_SYS_TOP_UP);
//            jnlSucc.setNote(bsHfBankSysTopUpVo.getNote());
            jnlSucc.setTransCode(Constants.ORDER_TRANS_CODE_PROCESS);
            jnlSucc.setReturnCode(res.getRecode());
            jnlSucc.setReturnMsg(res.getRemsg());
            bsPayOrdersJnlMapper.insertSelective(jnlSucc);
			return PTMessageEnum.TRANS_SUCCESS.getCode(); // 只是通讯成功，是否入账成功需等待异步通知结果
		} else {
			// 订单和订单流水更新为失败
			BsPayOrders ordersFail = new BsPayOrders();
            ordersFail.setId(orders.getId());
            ordersFail.setStatus(Constants.ORDER_STATUS_FAIL);
            ordersFail.setReturnCode(res.getRecode());
            ordersFail.setReturnMsg(res.getRemsg());
            ordersFail.setUpdateTime(transTime);
            bsPayOrdersMapper.updateByPrimaryKeySelective(ordersFail);
            BsPayOrdersJnl jnlFail = fillBsPayOrdersJnl(ordersFail, transTime);
            jnlFail.setOrderNo(orderNo);
            jnlFail.setTransAmount(amount);
            jnlFail.setChannelTransType(Constants.TRANS_HFBANK_SYS_TOP_UP);
//            jnlFail.setNote(bsHfBankSysTopUpVo.getNote());
            jnlFail.setTransCode(Constants.ORDER_TRANS_CODE_FAIL);
            jnlFail.setReturnCode(res.getRecode());
            jnlFail.setReturnMsg(res.getRemsg());
            bsPayOrdersJnlMapper.insertSelective(jnlFail);
			return PTMessageEnum.TRANS_ERROR.getCode();
		}
	}

	@Override
	public String sysWithdraw(BsHfBankSysWithdrawVo bsHfBankSysWithdrawVo) {
		LOG.info("[sysWithdraw]入参：" + bsHfBankSysWithdrawVo.toString());
		Double amount = bsHfBankSysWithdrawVo.getAmount();
		if(StringUtil.isBlank(bsHfBankSysWithdrawVo.getNote()) || amount == null){
			throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
		}
		String orderNo = Util.generateSysOrderNo("HFWS");
		BsSysSubAccount sysSubAccount = bsSysSubAccountService.findSysSubAccount4Lock(Constants.SYS_ACCOUNT_DEP_JSH);
		// 存管自有子账户可用余额和提现金额比较
		if (MoneyUtil.subtract(sysSubAccount.getAvailableBalance(), amount).doubleValue() < 0) {
			throw new PTMessageException(PTMessageEnum.SYS_BALANCE_NOT_ENOUGH);
		}

		// 封装恒丰平台提现请求体
		Date transTime = new Date();
		B2GReqMsg_HFBank_PlatWithDraw req = new B2GReqMsg_HFBank_PlatWithDraw();
		req.setOrder_no(orderNo);
		req.setPartner_trans_date(transTime);
		req.setPartner_trans_time(transTime);
		req.setAmount(bsHfBankSysWithdrawVo.getAmount());
		req.setNotify_url(null);		
		
		// 新增订单信息
		final BsPayOrders orders = new BsPayOrders();
		BeanUtils.copyProperties(bsHfBankSysWithdrawVo, orders);
		orders.setOrderNo(orderNo);
		orders.setMoneyType(Constants.ORDER_CURRENCY_CNY);
		orders.setAccountType(Constants.ORDER_ACCOUNT_TYPE_SYS);
		orders.setTransType(Constants.TRANS_HFBANK_SYS_WITHDRAW);
		orders.setChannel(Constants.CHANNEL_HFBANK);
		orders.setStatus(Constants.ORDER_STATUS_CREATE);
		orders.setCreateTime(transTime);
		bsPayOrdersMapper.insertSelective(orders);
		//新增订单流水表
        BsPayOrdersJnl jnl = fillBsPayOrdersJnl(orders, transTime);
        jnl.setChannelTransType(Constants.TRANS_HFBANK_SYS_WITHDRAW);
        jnl.setTransCode(Constants.ORDER_TRANS_CODE_INIT);
        jnl.setNote(bsHfBankSysWithdrawVo.getNote());
        bsPayOrdersJnlMapper.insertSelective(jnl);

		// 调用平台提现接口
		B2GResMsg_HFBank_PlatWithDraw res = null;
		try {
			res = hfbankTransportService.platWithDraw(req);
		} catch (Exception e) {
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {                 	
                	BsPayOrders ordersFail = new BsPayOrders();
                	ordersFail.setId(orders.getId());
                	ordersFail.setStatus(Constants.ORDER_STATUS_CON_FAIL);
                	ordersFail.setUpdateTime(new Date());
                	bsPayOrdersMapper.updateByPrimaryKeySelective(ordersFail);
                	BsPayOrdersJnl jnlFail = fillBsPayOrdersJnl(ordersFail, new Date());
                	jnlFail.setOrderNo(orders.getOrderNo());
                	jnlFail.setTransAmount(orders.getAmount());
                	jnlFail.setChannelTransType(Constants.TRANS_HFBANK_SYS_WITHDRAW);
                	jnlFail.setTransCode(Constants.ORDER_TRANS_CODE_PROCESS);
                	bsPayOrdersJnlMapper.insertSelective(jnlFail);
                }
			});   
			throw new PTMessageException(PTMessageEnum.TRANS_ERROR);
		}
		transTime = new Date();
		if (Constants.DEP_RECODE_SUCCESS.equals(res.getRecode())) {
			// 订单和订单流水更新为成功
			BsPayOrders ordersSucc = new BsPayOrders();
            ordersSucc.setId(orders.getId());
            ordersSucc.setStatus(Constants.ORDER_STATUS_PAYING);
            ordersSucc.setUpdateTime(transTime);
            ordersSucc.setReturnCode(res.getRecode());
            ordersSucc.setReturnMsg(res.getRemsg());
            bsPayOrdersMapper.updateByPrimaryKeySelective(ordersSucc);
            BsPayOrdersJnl jnlSucc = fillBsPayOrdersJnl(ordersSucc, transTime);
            jnlSucc.setOrderNo(orderNo);
            jnlSucc.setTransAmount(amount);
            jnlSucc.setChannelTransType(Constants.TRANS_HFBANK_SYS_WITHDRAW);
            jnlSucc.setNote(bsHfBankSysWithdrawVo.getNote());
            jnlSucc.setTransCode(Constants.ORDER_TRANS_CODE_PRE_SUCC);
            jnlSucc.setReturnCode(res.getRecode());
            jnlSucc.setReturnMsg(res.getRemsg());
            bsPayOrdersJnlMapper.insertSelective(jnlSucc);
			return PTMessageEnum.TRANS_SUCCESS.getCode(); // 只是通讯成功，是否入账成功需等待异步通知结果
		} else {
			// 订单和订单流水更新为失败
			BsPayOrders ordersFail = new BsPayOrders();
            ordersFail.setId(orders.getId());
            ordersFail.setStatus(Constants.ORDER_STATUS_FAIL);
            ordersFail.setReturnCode(res.getRecode());
            ordersFail.setReturnMsg(res.getRemsg());
            ordersFail.setUpdateTime(transTime);
            bsPayOrdersMapper.updateByPrimaryKeySelective(ordersFail);
            BsPayOrdersJnl jnlFail = fillBsPayOrdersJnl(ordersFail, transTime);
            jnlFail.setOrderNo(orderNo);
            jnlFail.setTransAmount(amount);
            jnlFail.setChannelTransType(Constants.TRANS_HFBANK_SYS_WITHDRAW);
            jnlFail.setNote(bsHfBankSysWithdrawVo.getNote());
            jnlFail.setTransCode(Constants.ORDER_TRANS_CODE_FAIL);
            jnlFail.setReturnCode(res.getRecode());
            jnlFail.setReturnMsg(res.getRemsg());
            bsPayOrdersJnlMapper.insertSelective(jnlFail);
			return PTMessageEnum.TRANS_ERROR.getCode();
		}
	}

	@Override
	public String sysAccountTransfer(BsHfBankSysAccountTransferVo bsHfBankSysAccountTransferVo) {
		LOG.info("[sysAccountTransfer]入参：" + bsHfBankSysAccountTransferVo.toString());
		LOG.info("[sysAccountTransfer]入参：{}", JSONObject.fromObject(bsHfBankSysAccountTransferVo));
		Double amount = bsHfBankSysAccountTransferVo.getAmount();
		String sourceAccountCode = bsHfBankSysAccountTransferVo.getSourceAccount();
		String destAccountCode = bsHfBankSysAccountTransferVo.getDestAccount();
		if(StringUtil.isBlank(bsHfBankSysAccountTransferVo.getNote()) || amount == null ||
				StringUtil.isBlank(sourceAccountCode) || StringUtil.isBlank(destAccountCode)){
			throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
		}
		// 转入账户限定和余额校验
		final BsSysSubAccount sysSourceSubAccount = bsSysSubAccountService.findSysSubAccount4Lock(sourceAccountCode);
		final BsSysSubAccount sysDestSubAccount = bsSysSubAccountService.findSysSubAccount4Lock(destAccountCode);
		if (MoneyUtil.subtract(sysSourceSubAccount.getAvailableBalance(), amount).doubleValue() < 0) {
			throw new PTMessageException(PTMessageEnum.SYS_BALANCE_NOT_ENOUGH);
		}
		if (!isValidDestAccount(sourceAccountCode, destAccountCode)) {
			throw new PTMessageException(PTMessageEnum.DEP_PLATFORM_TRANS_ACCOUNT_ERROR);
		}

		String orderNo = Util.generateSysOrderNo("HFTS");
		Date transTime = new Date();
		// 封装恒丰平台不同子账户转账请求体
		B2GReqMsg_HFBank_PlatformAccountConverse req = new B2GReqMsg_HFBank_PlatformAccountConverse();
		req.setSource_account(getHfAccountCode(sourceAccountCode));
		req.setAmt(amount);
		req.setDest_account(getHfAccountCode(destAccountCode));
		req.setPartner_trans_date(transTime);
		req.setPartner_trans_time(transTime);
		req.setRemark("平台不同子账户转账");
		req.setOrder_no(orderNo);

		// 新增订单信息
		final BsPayOrders orders = new BsPayOrders();
		BeanUtils.copyProperties(bsHfBankSysAccountTransferVo, orders);
		orders.setOrderNo(orderNo);
		orders.setMoneyType(Constants.ORDER_CURRENCY_CNY);
		orders.setAccountType(Constants.ORDER_ACCOUNT_TYPE_SYS);
		orders.setTransType(Constants.TRANS_HFBANK_SYS_ACCOUNT_TRANSFER);
		orders.setChannel(Constants.CHANNEL_HFBANK);
		orders.setStatus(Constants.ORDER_STATUS_CREATE);
		orders.setCreateTime(transTime);
		bsPayOrdersMapper.insertSelective(orders);
		//新增订单流水表
        BsPayOrdersJnl jnl = fillBsPayOrdersJnl(orders, transTime);
        jnl.setChannelTransType(Constants.TRANS_HFBANK_SYS_ACCOUNT_TRANSFER);
        jnl.setTransCode(Constants.ORDER_TRANS_CODE_INIT);
        jnl.setNote(bsHfBankSysAccountTransferVo.getNote());
        bsPayOrdersJnlMapper.insertSelective(jnl);

		// 调用平台不同子账户转账接口
		final B2GResMsg_HFBank_PlatformAccountConverse res;
		try {
			res = hfbankTransportService.platformAccountConverse(req);
		} catch (Exception e) {
			// 订单和订单流水更新为失败
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {                     	
                	BsPayOrders ordersFail = new BsPayOrders();
                	ordersFail.setId(orders.getId());
                	ordersFail.setStatus(Constants.ORDER_STATUS_CON_FAIL);
                	ordersFail.setUpdateTime(new Date());
                	bsPayOrdersMapper.updateByPrimaryKeySelective(ordersFail);
                	BsPayOrdersJnl jnlFail = fillBsPayOrdersJnl(ordersFail, new Date());
                	jnlFail.setTransCode(Constants.ORDER_TRANS_CODE_COMM_FAIL);
                	jnlFail.setOrderNo(orders.getOrderNo());
                	jnlFail.setTransAmount(orders.getAmount());
                	jnlFail.setChannelTransType(Constants.TRANS_HFBANK_SYS_ACCOUNT_TRANSFER);  
                	bsPayOrdersJnlMapper.insertSelective(jnlFail);
                }
			});     
			throw new PTMessageException(PTMessageEnum.TRANS_ERROR);
		}
		transTime = new Date();
		if (Constants.DEP_RECODE_SUCCESS.equals(res.getRecode())) {
			// 订单和订单流水更新为成功
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {
                	BsPayOrders ordersSucc = new BsPayOrders();
                	ordersSucc.setId(orders.getId());
                	ordersSucc.setStatus(Constants.ORDER_STATUS_SUCCESS);
                	ordersSucc.setUpdateTime(new Date());
                	ordersSucc.setReturnCode(res.getRecode());
                	ordersSucc.setReturnMsg(res.getRemsg());
                	bsPayOrdersMapper.updateByPrimaryKeySelective(ordersSucc);
                	BsPayOrdersJnl jnlSucc = fillBsPayOrdersJnl(ordersSucc, new Date());
                	jnlSucc.setTransCode(Constants.ORDER_TRANS_CODE_SUCCESS);
                	jnlSucc.setOrderNo(orders.getOrderNo());
                	jnlSucc.setTransAmount(orders.getAmount());
                	jnlSucc.setChannelTransType(Constants.TRANS_HFBANK_SYS_ACCOUNT_TRANSFER);  
                	jnlSucc.setReturnCode(res.getRecode());
                	jnlSucc.setReturnMsg(res.getRemsg());
                	bsPayOrdersJnlMapper.insertSelective(jnlSucc);
                	
                	// 更新存管转入转出账户
                	BsSysSubAccount tempSourceSysSubAccount = new BsSysSubAccount();
                	tempSourceSysSubAccount.setId(sysSourceSubAccount.getId());
                	tempSourceSysSubAccount.setBalance(MoneyUtil.subtract(sysSourceSubAccount.getBalance(), orders.getAmount()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
                	tempSourceSysSubAccount.setAvailableBalance(MoneyUtil.subtract(sysSourceSubAccount.getAvailableBalance(), orders.getAmount()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
                	tempSourceSysSubAccount.setCanWithdraw(MoneyUtil.subtract(sysSourceSubAccount.getCanWithdraw(), orders.getAmount()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
                	bsSysSubAccountMapper.updateByPrimaryKeySelective(tempSourceSysSubAccount); 
                	BsSysSubAccount tempDestSysSubAccount = new BsSysSubAccount();
                	tempDestSysSubAccount.setId(sysDestSubAccount.getId());
                	tempDestSysSubAccount.setBalance(MoneyUtil.add(sysDestSubAccount.getBalance(), orders.getAmount()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
                	tempDestSysSubAccount.setAvailableBalance(MoneyUtil.add(sysDestSubAccount.getAvailableBalance(), orders.getAmount()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
                	tempDestSysSubAccount.setCanWithdraw(MoneyUtil.add(sysDestSubAccount.getCanWithdraw(), orders.getAmount()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
                	bsSysSubAccountMapper.updateByPrimaryKeySelective(tempDestSysSubAccount);
                	// 系统账户流水
                	BsSysAccountJnl sysAccountJnl = new BsSysAccountJnl();
                	sysAccountJnl.setTransTime(new Date());
                	sysAccountJnl.setTransCode(Constants.SYS_DEP_TRANS_ORDER);
                	sysAccountJnl.setTransName("恒丰系统账户划账");
                	sysAccountJnl.setTransAmount(orders.getAmount());
                	sysAccountJnl.setSysTime(new Date());
                	sysAccountJnl.setChannelTime(null);
                	sysAccountJnl.setChannelJnlNo(null);
                	sysAccountJnl.setCdFlag1(Constants.CD_FLAG_C);
                	sysAccountJnl.setSubAccountCode1(sysSourceSubAccount.getCode());
                	sysAccountJnl.setBeforeBalance1(sysSourceSubAccount.getBalance());
                	sysAccountJnl.setAfterBalance1(tempSourceSysSubAccount.getBalance());
                	sysAccountJnl.setBeforeAvialableBalance1(sysSourceSubAccount.getAvailableBalance());
                	sysAccountJnl.setAfterAvialableBalance1(tempSourceSysSubAccount.getAvailableBalance());
                	sysAccountJnl.setBeforeFreezeBalance1(sysSourceSubAccount.getFreezeBalance());
                	sysAccountJnl.setAfterFreezeBalance1(sysSourceSubAccount.getFreezeBalance());
                	sysAccountJnl.setCdFlag2(Constants.CD_FLAG_D);
                	sysAccountJnl.setSubAccountCode2(sysDestSubAccount.getCode());
                	sysAccountJnl.setBeforeBalance2(sysDestSubAccount.getBalance());
                	sysAccountJnl.setAfterBalance2(tempDestSysSubAccount.getBalance());
                	sysAccountJnl.setBeforeAvialableBalance2(sysDestSubAccount.getAvailableBalance());
                	sysAccountJnl.setAfterAvialableBalance2(tempDestSysSubAccount.getAvailableBalance());
                	sysAccountJnl.setBeforeFreezeBalance2(sysDestSubAccount.getFreezeBalance());
                	sysAccountJnl.setAfterFreezeBalance2(sysDestSubAccount.getFreezeBalance());
                	sysAccountJnl.setFee(0.0);
                	sysAccountJnl.setStatus(Constants.ACCOUNT_JNL_STATUS_SUCCESS);
                	bsSysAccountJnlMapper.insertSelective(sysAccountJnl);
                }
			});     
			return PTMessageEnum.TRANS_SUCCESS.getCode();
		} else {
			// 订单和订单流水更新为失败
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {    
                	BsPayOrders ordersFail = new BsPayOrders();
                    ordersFail.setId(orders.getId());
                    ordersFail.setStatus(Constants.ORDER_STATUS_FAIL);
                    ordersFail.setReturnCode(res.getRecode());
                    ordersFail.setReturnMsg(res.getRemsg());
                    ordersFail.setUpdateTime(new Date());
                    bsPayOrdersMapper.updateByPrimaryKeySelective(ordersFail);
                    BsPayOrdersJnl jnlFail = fillBsPayOrdersJnl(ordersFail, new Date());
                    jnlFail.setOrderNo(orders.getOrderNo());
                    jnlFail.setTransAmount(orders.getAmount());
                    jnlFail.setChannelTransType(Constants.TRANS_HFBANK_SYS_ACCOUNT_TRANSFER);  
                    jnlFail.setTransCode(Constants.ORDER_TRANS_CODE_FAIL);
                    jnlFail.setReturnCode(res.getRecode());
                    jnlFail.setReturnMsg(res.getRemsg());
                    bsPayOrdersJnlMapper.insertSelective(jnlFail);
                }
			});  
			return PTMessageEnum.TRANS_ERROR.getCode();
		}
	}

	@Override
	public void sysTopUpNotify(final SysRechargeResultInfo req) {
		LOG.info("【恒丰平台充值通知】请求参数：{}", JSONObject.fromObject(req));
		// 数据校验
		Map<String, Object> returnMap = sysRechargeNotifyCheck(req);
		final BsPayOrders orders = (BsPayOrders) returnMap.get("order");
		Double amount = orders.getAmount();
		if (MoneyUtil.subtract(amount, req.getAmount()).doubleValue() != 0) {
			throw new PTMessageException(PTMessageEnum.PAYMENT_SYSRECHARGE_AMOUNT_NOT_SAME);
		}
		transactionTemplate.execute(new TransactionCallbackWithoutResult(){
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				
				BsSysSubAccount sysSubAccount = bsSysSubAccountService.findSysSubAccount4Lock(Constants.SYS_ACCOUNT_DEP_JSH);
				Date transTime = new Date();
				// 订单和订单流水更新为成功
				if("1".equals(req.getCode())) {
					BsPayOrders ordersSucc = new BsPayOrders();
					ordersSucc.setId(orders.getId());
					ordersSucc.setStatus(Constants.ORDER_STATUS_SUCCESS);
					ordersSucc.setUpdateTime(transTime);
					bsPayOrdersMapper.updateByPrimaryKeySelective(ordersSucc);
					BsPayOrdersJnl jnlSucc = fillBsPayOrdersJnl(ordersSucc, transTime);
					jnlSucc.setOrderNo(orders.getOrderNo());
					jnlSucc.setTransAmount(orders.getAmount());
					jnlSucc.setChannelTransType(Constants.TRANS_HFBANK_SYS_TOP_UP);
					jnlSucc.setTransCode(Constants.ORDER_TRANS_CODE_SUCCESS);
					bsPayOrdersJnlMapper.insertSelective(jnlSucc);
					
					// 更新存管自有子账户
					BsSysSubAccount tempSysSubAccount = new BsSysSubAccount();
					tempSysSubAccount.setId(sysSubAccount.getId());
					tempSysSubAccount.setBalance(MoneyUtil.add(sysSubAccount.getBalance(), orders.getAmount()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
					tempSysSubAccount.setAvailableBalance(MoneyUtil.add(sysSubAccount.getAvailableBalance(), orders.getAmount()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
					tempSysSubAccount.setCanWithdraw(MoneyUtil.add(sysSubAccount.getCanWithdraw(), orders.getAmount()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
					bsSysSubAccountMapper.updateByPrimaryKeySelective(tempSysSubAccount); 
					// 系统账户流水
					BsSysAccountJnl sysAccountJnl = new BsSysAccountJnl();
					sysAccountJnl.setTransTime(transTime);
					sysAccountJnl.setTransCode(Constants.SYS_DEP_TOP_UP);
					sysAccountJnl.setTransName("恒丰系统充值");
					sysAccountJnl.setTransAmount(orders.getAmount());
					sysAccountJnl.setSysTime(transTime);
					sysAccountJnl.setChannelTime(null);
					sysAccountJnl.setChannelJnlNo(null);
					sysAccountJnl.setCdFlag1(Constants.CD_FLAG_D);
					sysAccountJnl.setSubAccountCode1(sysSubAccount.getCode());
					sysAccountJnl.setBeforeBalance1(sysSubAccount.getBalance());
					sysAccountJnl.setAfterBalance1(tempSysSubAccount.getBalance());
					sysAccountJnl.setBeforeAvialableBalance1(sysSubAccount.getAvailableBalance());
					sysAccountJnl.setAfterAvialableBalance1(tempSysSubAccount.getAvailableBalance());
					sysAccountJnl.setBeforeFreezeBalance1(sysSubAccount.getFreezeBalance());
					sysAccountJnl.setAfterFreezeBalance1(sysSubAccount.getFreezeBalance());
					sysAccountJnl.setFee(0.0);
					sysAccountJnl.setStatus(Constants.ACCOUNT_JNL_STATUS_SUCCESS);
					bsSysAccountJnlMapper.insertSelective(sysAccountJnl);
				} else if("2".equals(req.getCode())) {
					// 订单和订单流水更新为失败
					BsPayOrders ordersFail = new BsPayOrders();
					ordersFail.setId(orders.getId());
					ordersFail.setStatus(Constants.ORDER_STATUS_FAIL);
					ordersFail.setUpdateTime(transTime);
					bsPayOrdersMapper.updateByPrimaryKeySelective(ordersFail);
					BsPayOrdersJnl jnlFail = fillBsPayOrdersJnl(ordersFail, transTime);
					jnlFail.setOrderNo(orders.getOrderNo());
					jnlFail.setTransAmount(orders.getAmount());
					jnlFail.setChannelTransType(Constants.TRANS_HFBANK_SYS_TOP_UP);
					jnlFail.setTransCode(Constants.ORDER_TRANS_CODE_FAIL);
					bsPayOrdersJnlMapper.insertSelective(jnlFail);
				}
			}	
		});
	}
	
	@Override
	public void sysWithdrawNotify(final SysWithdrawResultInfo req) {
		LOG.info("【恒丰平台提现通知】请求参数：{}", JSONObject.fromObject(req));
		// 数据校验
		Map<String, Object> returnMap = sysWithdrawNotifyCheck(req);
		final BsPayOrders orders = (BsPayOrders) returnMap.get("order");
		Double amount = orders.getAmount();
		if (MoneyUtil.subtract(amount, req.getAmount()).doubleValue() != 0) {
			throw new PTMessageException(PTMessageEnum.PAYMENT_SYSWITHDRAW_AMOUNT_NOT_SAME);
		}
		transactionTemplate.execute(new TransactionCallbackWithoutResult(){
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				
				BsSysSubAccount sysSubAccount = bsSysSubAccountService.findSysSubAccount4Lock(Constants.SYS_ACCOUNT_DEP_JSH);
				Date transTime = new Date();
				// 订单和订单流水更新为成功
				if("1".equals(req.getCode())) {
					BsPayOrders ordersSucc = new BsPayOrders();
					ordersSucc.setId(orders.getId());
					ordersSucc.setStatus(Constants.ORDER_STATUS_SUCCESS);
					ordersSucc.setUpdateTime(transTime);
					bsPayOrdersMapper.updateByPrimaryKeySelective(ordersSucc);
					BsPayOrdersJnl jnlSucc = fillBsPayOrdersJnl(ordersSucc, transTime);
					jnlSucc.setOrderNo(orders.getOrderNo());
					jnlSucc.setTransAmount(orders.getAmount());
					jnlSucc.setChannelTransType(Constants.TRANS_HFBANK_SYS_WITHDRAW);
					jnlSucc.setTransCode(Constants.ORDER_TRANS_CODE_SUCCESS);
					bsPayOrdersJnlMapper.insertSelective(jnlSucc);
					
					// 更新存管自有子账户
					BsSysSubAccount tempSysSubAccount = new BsSysSubAccount();
					tempSysSubAccount.setId(sysSubAccount.getId());
					tempSysSubAccount.setBalance(MoneyUtil.subtract(sysSubAccount.getBalance(), orders.getAmount()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
					tempSysSubAccount.setAvailableBalance(MoneyUtil.subtract(sysSubAccount.getAvailableBalance(), orders.getAmount()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
					tempSysSubAccount.setCanWithdraw(MoneyUtil.subtract(sysSubAccount.getCanWithdraw(), orders.getAmount()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
					bsSysSubAccountMapper.updateByPrimaryKeySelective(tempSysSubAccount); 
					// 系统账户流水
					BsSysAccountJnl sysAccountJnl = new BsSysAccountJnl();
					sysAccountJnl.setTransTime(transTime);
					sysAccountJnl.setTransCode(Constants.SYS_DEP_WITHDRAW);
					sysAccountJnl.setTransName("恒丰系统提现");
					sysAccountJnl.setTransAmount(orders.getAmount());
					sysAccountJnl.setSysTime(transTime);
					sysAccountJnl.setChannelTime(null);
					sysAccountJnl.setChannelJnlNo(null);
					sysAccountJnl.setCdFlag1(Constants.CD_FLAG_C);
					sysAccountJnl.setSubAccountCode1(sysSubAccount.getCode());
					sysAccountJnl.setBeforeBalance1(sysSubAccount.getBalance());
					sysAccountJnl.setAfterBalance1(tempSysSubAccount.getBalance());
					sysAccountJnl.setBeforeAvialableBalance1(sysSubAccount.getAvailableBalance());
					sysAccountJnl.setAfterAvialableBalance1(tempSysSubAccount.getAvailableBalance());
					sysAccountJnl.setBeforeFreezeBalance1(sysSubAccount.getFreezeBalance());
					sysAccountJnl.setAfterFreezeBalance1(sysSubAccount.getFreezeBalance());
					sysAccountJnl.setFee(0.0);
					sysAccountJnl.setStatus(Constants.ACCOUNT_JNL_STATUS_SUCCESS);
					bsSysAccountJnlMapper.insertSelective(sysAccountJnl);
				} else if("2".equals(req.getCode())) {
					// 订单和订单流水更新为失败
					BsPayOrders ordersFail = new BsPayOrders();
					ordersFail.setId(orders.getId());
					ordersFail.setStatus(Constants.ORDER_STATUS_FAIL);
					ordersFail.setUpdateTime(transTime);
					bsPayOrdersMapper.updateByPrimaryKeySelective(ordersFail);
					BsPayOrdersJnl jnlFail = fillBsPayOrdersJnl(ordersFail, transTime);
					jnlFail.setTransCode(Constants.ORDER_TRANS_CODE_FAIL);
					jnlFail.setOrderNo(orders.getOrderNo());
					jnlFail.setTransAmount(orders.getAmount());
					jnlFail.setChannelTransType(Constants.TRANS_HFBANK_SYS_WITHDRAW);
					bsPayOrdersJnlMapper.insertSelective(jnlFail);
				}
			}
		});	
	}
	
	/**
	 * 填充订单流水信息
	 * @param orders
	 * @param transTime
	 * @return
	 */
	private BsPayOrdersJnl fillBsPayOrdersJnl(BsPayOrders orders, Date transTime) {
		BsPayOrdersJnl jnl = new BsPayOrdersJnl();
		jnl.setOrderId(orders.getId());
        jnl.setOrderNo(orders.getOrderNo());
        jnl.setTransAmount(orders.getAmount());
        jnl.setSysTime(transTime);
        jnl.setCreateTime(transTime);
		return jnl;
	}
	
	/**
	 * 填充订单流水信息
	 * @param orders
	 * @param transTime
	 * @return
	 */
	private LnPayOrdersJnl fillLnPayOrdersJnl(LnPayOrders orders, Date transTime) {
		LnPayOrdersJnl jnl = new LnPayOrdersJnl();
		jnl.setOrderId(orders.getId());
        jnl.setOrderNo(orders.getOrderNo());
        jnl.setTransAmount(orders.getAmount());
        jnl.setSysTime(transTime);
        jnl.setCreateTime(transTime);  
		return jnl;
	}
    
	/**
	 * 系统转入转出账户转化为恒丰账户编码
	 * 恒丰账户编码查看恒丰对应接口
	 * @param accountCode
	 * @return
	 */
	private String getHfAccountCode(String accountCode) {
		String hfAccount = "";
		if (Constants.SYS_ACCOUNT_DEP_JSH.equals(accountCode)) {
			hfAccount = HFBankAccountType.DEP_JSH.getCode();  
		} else if (Constants.SYS_ACCOUNT_DEP_BGW_REVENUE_ZAN.equals(accountCode)
				|| Constants.SYS_ACCOUNT_DEP_BGW_REVENUE_YUN.equals(accountCode)
				|| Constants.SYS_ACCOUNT_DEP_HEADFEE_YUN.equals(accountCode)
				|| Constants.SYS_ACCOUNT_DEP_HEADFEE_ZSD.equals(accountCode)
				|| Constants.SYS_ACCOUNT_DEP_BGW_REVENUE_ZSD.equals(accountCode)
				|| Constants.SYS_ACCOUNT_DEP_OTHER_FEE.equals(accountCode)
				|| Constants.SYS_ACCOUNT_DEP_BGW_REVENUE_7.equals(accountCode)){
			hfAccount = HFBankAccountType.DEP_FEE.getCode();
		} else if (Constants.SYS_ACCOUNT_DEP_REDPACKET.equals(accountCode)) {
			hfAccount = HFBankAccountType.DEP_RED.getCode();
		} else if (Constants.SYS_ACCOUNT_DEP_ADVANCE.equals(accountCode)) {
			hfAccount = HFBankAccountType.DEP_ADVANCE.getCode();
		}
		return hfAccount;
	}
	
	/**
	 * 是否合法的转入账户
	 * @param sourceAccountCode
	 * @param destAccountCode
	 * @return
	 */
	private boolean isValidDestAccount(String sourceAccountCode, String destAccountCode) {
		boolean isValidDestAccount = true;
		String[] array = {Constants.SYS_ACCOUNT_DEP_JSH, Constants.SYS_ACCOUNT_DEP_REDPACKET, Constants.SYS_ACCOUNT_DEP_BGW_REVENUE_ZAN,
				Constants.SYS_ACCOUNT_DEP_BGW_REVENUE_YUN, Constants.SYS_ACCOUNT_DEP_HEADFEE_YUN, Constants.SYS_ACCOUNT_DEP_OTHER_FEE,
				Constants.SYS_ACCOUNT_DEP_ADVANCE, Constants.SYS_ACCOUNT_DEP_HEADFEE_ZSD, Constants.SYS_ACCOUNT_DEP_BGW_REVENUE_ZSD,
				Constants.SYS_ACCOUNT_DEP_BGW_REVENUE_7};
		String[] subArray = {Constants.SYS_ACCOUNT_DEP_REDPACKET, Constants.SYS_ACCOUNT_DEP_BGW_REVENUE_ZAN, 
				Constants.SYS_ACCOUNT_DEP_BGW_REVENUE_YUN, Constants.SYS_ACCOUNT_DEP_HEADFEE_YUN, Constants.SYS_ACCOUNT_DEP_OTHER_FEE,
				Constants.SYS_ACCOUNT_DEP_ADVANCE, Constants.SYS_ACCOUNT_DEP_HEADFEE_ZSD, Constants.SYS_ACCOUNT_DEP_BGW_REVENUE_ZSD,
				Constants.SYS_ACCOUNT_DEP_BGW_REVENUE_7};
		if(!(Arrays.asList(array).contains(sourceAccountCode) && Arrays.asList(array).contains(destAccountCode))) {
			isValidDestAccount = false;
		}
		if(Arrays.asList(subArray).contains(sourceAccountCode) && (!Constants.SYS_ACCOUNT_DEP_JSH.equals(destAccountCode))) {
			isValidDestAccount = false;
		}
		return isValidDestAccount;
	}

	/**
	 * 平台充值通知校验
	 * @param req
	 * @return
	 */
	private Map<String, Object> sysRechargeNotifyCheck(SysRechargeResultInfo req) {
		BsPayOrdersExample orderExample = new BsPayOrdersExample();
		orderExample.createCriteria().andOrderNoEqualTo(req.getOrder_no())
                .andStatusEqualTo(Constants.ORDER_STATUS_PAYING).andTransTypeEqualTo(Constants.TRANS_HFBANK_SYS_TOP_UP);
        List<BsPayOrders> orderList = bsPayOrdersMapper.selectByExample(orderExample);
        if (orderList == null || orderList.isEmpty()) {
        	throw new PTMessageException(PTMessageEnum.PAYMENT_ORDER_NOT_EXIST);
        }
        BsPayOrders order = orderList.get(0);
        if (order.getStatus() != Constants.ORDER_STATUS_PAYING) {
        	LOG.info("【平台充值通知异常】订单编号[" + order.getOrderNo() + "]恒丰重复通知");
        	throw new PTMessageException(PTMessageEnum.PAYMENT_ORDER_DUPLICATE);
        }
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("order", order);
        return returnMap;
	}
	
	/**
	 * 平台提现通知校验
	 * @param req
	 * @return
	 */
	private Map<String, Object> sysWithdrawNotifyCheck(SysWithdrawResultInfo req) {
		BsPayOrdersExample orderExample = new BsPayOrdersExample();
		orderExample.createCriteria().andOrderNoEqualTo(req.getOrder_no())
                .andStatusEqualTo(Constants.ORDER_STATUS_PAYING).andTransTypeEqualTo(Constants.TRANS_HFBANK_SYS_WITHDRAW);
        List<BsPayOrders> orderList = bsPayOrdersMapper.selectByExample(orderExample);
        if (orderList == null || orderList.isEmpty()) {
        	throw new PTMessageException(PTMessageEnum.PAYMENT_ORDER_NOT_EXIST);
        }
        BsPayOrders order = orderList.get(0);
        if (order.getStatus() != Constants.ORDER_STATUS_PAYING) {
        	LOG.info("【平台提现通知异常】订单编号[" + order.getOrderNo() + "]恒丰重复通知");
        	throw new PTMessageException(PTMessageEnum.PAYMENT_ORDER_DUPLICATE);
        }
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("order", order);
        return returnMap;
	}

	@Override
	public String sysAccountTransferNoCharge(BsHfBankSysAccountTransferVo bsHfBankSysAccountTransferVo) {
		LOG.info("[sysAccountTransferNoCharge]入参：" + bsHfBankSysAccountTransferVo.toString());
		LOG.info("[sysAccountTransferNoCharge]入参：{}", JSONObject.fromObject(bsHfBankSysAccountTransferVo));
		Double amount = bsHfBankSysAccountTransferVo.getAmount();
		String sourceAccountCode = bsHfBankSysAccountTransferVo.getSourceAccount();
		String destAccountCode = bsHfBankSysAccountTransferVo.getDestAccount();
		if(StringUtil.isBlank(bsHfBankSysAccountTransferVo.getNote()) || amount == null ||
				StringUtil.isBlank(sourceAccountCode) || StringUtil.isBlank(destAccountCode)){
			throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
		}
		// 转入账户限定和余额校验
		final BsSysSubAccount sysSourceSubAccount = bsSysSubAccountService.findSysSubAccount4Lock(sourceAccountCode);
		final BsSysSubAccount sysDestSubAccount = bsSysSubAccountService.findSysSubAccount4Lock(destAccountCode);
		if (!isValidDestAccount(sourceAccountCode, destAccountCode)) {
			throw new PTMessageException(PTMessageEnum.DEP_PLATFORM_TRANS_ACCOUNT_ERROR);
		}

		String orderNo = Util.generateSysOrderNo("HFTS");
		Date transTime = new Date();
		// 封装恒丰平台不同子账户转账请求体
		B2GReqMsg_HFBank_PlatformAccountConverse req = new B2GReqMsg_HFBank_PlatformAccountConverse();
		req.setSource_account(getHfAccountCode(sourceAccountCode));
		req.setAmt(amount);
		req.setDest_account(getHfAccountCode(destAccountCode));
		req.setPartner_trans_date(transTime);
		req.setPartner_trans_time(transTime);
		req.setRemark("平台不同子账户转账");
		req.setOrder_no(orderNo);

		// 新增订单信息
		final BsPayOrders orders = new BsPayOrders();
		BeanUtils.copyProperties(bsHfBankSysAccountTransferVo, orders);
		orders.setOrderNo(orderNo);
		orders.setMoneyType(Constants.ORDER_CURRENCY_CNY);
		orders.setAccountType(Constants.ORDER_ACCOUNT_TYPE_SYS);
		orders.setTransType(Constants.TRANS_HFBANK_ADVANCE_TRANSFER);
		orders.setChannel(Constants.CHANNEL_HFBANK);
		orders.setStatus(Constants.ORDER_STATUS_CREATE);
		orders.setCreateTime(transTime);
		bsPayOrdersMapper.insertSelective(orders);
		//新增订单流水表
        BsPayOrdersJnl jnl = fillBsPayOrdersJnl(orders, transTime);
        jnl.setChannelTransType(Constants.TRANS_HFBANK_SYS_ACCOUNT_TRANSFER);
        jnl.setTransCode(Constants.ORDER_TRANS_CODE_INIT);
        jnl.setNote(bsHfBankSysAccountTransferVo.getNote());
        bsPayOrdersJnlMapper.insertSelective(jnl);

		// 调用平台不同子账户转账接口
		final B2GResMsg_HFBank_PlatformAccountConverse res;
		try {
			res = hfbankTransportService.platformAccountConverse(req);
		} catch (Exception e) {
			LOG.error("调用平台不同子账户转账接口失败：", e);
			// 订单和订单流水更新为失败
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {                     	
                	BsPayOrders ordersFail = new BsPayOrders();
                	ordersFail.setId(orders.getId());
                	ordersFail.setStatus(Constants.ORDER_STATUS_CON_FAIL);
                	ordersFail.setUpdateTime(new Date());
                	bsPayOrdersMapper.updateByPrimaryKeySelective(ordersFail);
                	BsPayOrdersJnl jnlFail = fillBsPayOrdersJnl(ordersFail, new Date());
                	jnlFail.setTransCode(Constants.ORDER_TRANS_CODE_COMM_FAIL);
                	jnlFail.setOrderNo(orders.getOrderNo());
                	jnlFail.setTransAmount(orders.getAmount());
                	jnlFail.setChannelTransType(Constants.TRANS_HFBANK_SYS_ACCOUNT_TRANSFER);  
                	bsPayOrdersJnlMapper.insertSelective(jnlFail);
                }
			});     
			throw new PTMessageException(PTMessageEnum.TRANS_ERROR);
		}
		transTime = new Date();
		if (Constants.DEP_RECODE_SUCCESS.equals(res.getRecode())) {
			// 订单和订单流水更新为成功
			LOG.info("调用平台不同子账户转账接口响应成功：recode{},remsg{}", res.getRecode(), res.getRemsg());
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {
                	BsPayOrders ordersSucc = new BsPayOrders();
                	ordersSucc.setId(orders.getId());
                	ordersSucc.setStatus(Constants.ORDER_STATUS_SUCCESS);
                	ordersSucc.setUpdateTime(new Date());
                	ordersSucc.setReturnCode(res.getRecode());
                	ordersSucc.setReturnMsg(res.getRemsg());
                	bsPayOrdersMapper.updateByPrimaryKeySelective(ordersSucc);
                	BsPayOrdersJnl jnlSucc = fillBsPayOrdersJnl(ordersSucc, new Date());
                	jnlSucc.setTransCode(Constants.ORDER_TRANS_CODE_SUCCESS);
                	jnlSucc.setOrderNo(orders.getOrderNo());
                	jnlSucc.setTransAmount(orders.getAmount());
                	jnlSucc.setChannelTransType(Constants.TRANS_HFBANK_SYS_ACCOUNT_TRANSFER);  
                	jnlSucc.setReturnCode(res.getRecode());
                	jnlSucc.setReturnMsg(res.getRemsg());
                	bsPayOrdersJnlMapper.insertSelective(jnlSucc);
                	
                }
			});     
			return PTMessageEnum.TRANS_SUCCESS.getCode();
		} else {
			LOG.info("调用平台不同子账户转账接口响应失败：recode{},remsg{}", res.getRecode(), res.getRemsg());
			// 订单和订单流水更新为失败
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {    
                	BsPayOrders ordersFail = new BsPayOrders();
                    ordersFail.setId(orders.getId());
                    ordersFail.setStatus(Constants.ORDER_STATUS_FAIL);
                    ordersFail.setReturnCode(res.getRecode());
                    ordersFail.setReturnMsg(res.getRemsg());
                    ordersFail.setUpdateTime(new Date());
                    bsPayOrdersMapper.updateByPrimaryKeySelective(ordersFail);
                    BsPayOrdersJnl jnlFail = fillBsPayOrdersJnl(ordersFail, new Date());
                    jnlFail.setOrderNo(orders.getOrderNo());
                    jnlFail.setTransAmount(orders.getAmount());
                    jnlFail.setChannelTransType(Constants.TRANS_HFBANK_SYS_ACCOUNT_TRANSFER);  
                    jnlFail.setTransCode(Constants.ORDER_TRANS_CODE_FAIL);
                    jnlFail.setReturnCode(res.getRecode());
                    jnlFail.setReturnMsg(res.getRemsg());
                    bsPayOrdersJnlMapper.insertSelective(jnlFail);
                }
			});  
			return PTMessageEnum.TRANS_ERROR.getCode();
		}
	}

	@Override
	public String sysLoanerWithdrawNoCharge(BsHfBankSysLoanerWithdrawVo bsHfBankSysLoanerWithdrawVo) {
		LOG.info("[sysLoanerWithdrawNoCharge]入参：{}", JSONObject.fromObject(bsHfBankSysLoanerWithdrawVo));
		Double amount = bsHfBankSysLoanerWithdrawVo.getAmount();
		final LnUser lnUser = lnUserMapper.selectByPrimaryKey(bsHfBankSysLoanerWithdrawVo.getLoanUserId());
		LnBindCardExample bindCardExample = new LnBindCardExample();
		bindCardExample.createCriteria().andLnUserIdEqualTo(lnUser.getId()).andBankCardEqualTo(bsHfBankSysLoanerWithdrawVo.getBankCard());
		List<LnBindCard> bindCardList = lnBindCardMapper.selectByExample(bindCardExample);
		if(amount == null || lnUser == null || CollectionUtils.isEmpty(bindCardList)){
			throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
		}
		LnBindCard bindCard = bindCardList.get(0);
		// 封装存管体系提现发起代付请求
		String orderNo = Util.generateSysOrderNo("HFLWS");
		B2GResMsg_HFBank_UserBatchWithdraw resMsg = new B2GResMsg_HFBank_UserBatchWithdraw();
        B2GReqMsg_HFBank_UserBatchWithdraw b2gReq = new B2GReqMsg_HFBank_UserBatchWithdraw();
        b2gReq.setClient_property(Constants.HF_CLIENT_PROPERTY_PERSON);
        b2gReq.setOrder_no("withDraw"+orderNo);
        b2gReq.setPartner_trans_date(new Date());
        b2gReq.setPartner_trans_time(new Date());
        List<BatchWithdrawExtData> data = new ArrayList<>();
        BatchWithdrawExtData withdrawExtData = new BatchWithdrawExtData();
        withdrawExtData.setDetail_no(orderNo);
        withdrawExtData.setPlatcust(bsHfBankSysLoanerWithdrawVo.getHfUserId());
        withdrawExtData.setAmt(amount);
        withdrawExtData.setIs_advance(Constants.HF_WITHDRAW_IS_ADVANCE_NO);
        withdrawExtData.setPay_code(Constants.HF_PAY_CODE_HFBANK_OUT);//行内通道出金编码
        withdrawExtData.setFee_amt(0d);  //手续费
        //withdrawExtData.setType("0"); //文档2.0无此字段
        withdrawExtData.setWithdraw_type(Constants.HFBANK_WITHDRAW_TYPE_FINANCE);
        withdrawExtData.setRemark("借款人提现");
        withdrawExtData.setTran_type(Constants.HF_WITHDRAW_TRAN_TYPE_PAY_REAL); // 跨行
        //withdrawExtData.setBank_code(bank.getUnionBankId());
        withdrawExtData.setBank_name(bindCard.getBankName());
        data.add(withdrawExtData);
        b2gReq.setData(data);
        b2gReq.setTotal_num(1);//批量提现接口用于会员提现
        
        // 新增订单信息
        Date transTime = new Date();
 		final LnPayOrders orders = new LnPayOrders();
 		orders.setOrderNo(orderNo);
 		orders.setAmount(amount);
 		orders.setLnUserId(lnUser.getId());
 		orders.setChannel(Constants.CHANNEL_HFBANK);
 		orders.setStatus(Constants.ORDER_STATUS_CREATE);
 		orders.setBankName(bindCard.getBankName());
 		orders.setMoneyType(Constants.ORDER_CURRENCY_CNY);
 		orders.setTerminalType(Constants.TERMINAL_TYPE_PC);
 		//orders.setBankId(bankId);
 		orders.setBankCardNo(bindCard.getBankCard());
 		orders.setAccountType(Constants.ORDER_ACCOUNT_TYPE_SYS);
 		orders.setTransType(Constants.TRANS_LOANER_SYS_WITHDRAW);
 		orders.setChannelTransType(Constants.CHANNEL_TRS_DF);     //代付
 		orders.setMobile(lnUser.getMobile());
 		orders.setIdCard(lnUser.getIdCard());
 		orders.setUserName(lnUser.getUserName());
 		orders.setNote("借款人提现");
 		orders.setCreateTime(transTime);
 		lnPayOrdersMapper.insertSelective(orders);

        try {
            resMsg = hfbankTransportService.userBatchWithdraw(b2gReq);
        } catch (Exception e){
            LOG.error("提现申请通讯失败：{}", e.getMessage());
            resMsg.setResCode(Constants.GATEWAY_PAYING_RES_CODE);
            resMsg.setResMsg("提现通讯失败");
        }

        // 代付下单申请成功并且成功条数为1，等待通知. 此时更新订单表状态为处理中
        if (ConstantUtil.BSRESCODE_SUCCESS.equals(resMsg.getResCode()) && "1".equals(resMsg.getSuccess_num())) {
            // 恒丰银行提现接口不存在立即成功的情况，需等待异步通知。此成功表示受理成功。
        	LOG.info("提现存管代付下单申请处理中：{}", JSONObject.fromObject(resMsg));
            // 更新订单表
        	payOrdersService.modifyLnOrderStatus4Safe(orders.getId(), Constants.ORDER_STATUS_PAYING,
                    null, resMsg.getResCode(), resMsg.getResMsg());
        	return PTMessageEnum.TRANS_SUCCESS.getCode();
        } else if(Constants.GATEWAY_PAYING_RES_CODE.equals(resMsg.getResCode())) {
        	// 恒丰银行提现接口不存在立即成功的情况，需等待异步通知。此异常表示通讯超时。
        	LOG.info("提现存管代付下单(超时)申请处理中：{}", JSONObject.fromObject(resMsg));
            // 更新订单表
        	payOrdersService.modifyLnOrderStatus4Safe(orders.getId(), Constants.ORDER_STATUS_PAYING,
                    null, resMsg.getResCode(), resMsg.getResMsg());
        	return PTMessageEnum.TRANS_SUCCESS.getCode();
        } else {
            // 代付下单申请失败
            // 1.更新订单表，状态为失败
            // 2.添加一条订单明细信息

        	LOG.error("提现存管代付下单申请失败：{}", JSONObject.fromObject(resMsg));
            final String thirdReturnCode;
            final String errorMsg;
            if(CollectionUtils.isNotEmpty(resMsg.getError_data())) {
                thirdReturnCode = resMsg.getError_data().get(0).getError_no();
                errorMsg = resMsg.getError_data().get(0).getError_info();
            } else {
                thirdReturnCode = resMsg.getRecode();
                errorMsg = resMsg.getResMsg();
            }           
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {
                    LnPayOrders lockOrder = lnPayOrdersMapper.selectByPKForLock(orders.getId());
                    // 1.更新订单表，状态为失败
                    payOrdersService.modifyLnOrderStatus4Safe(orders.getId(), Constants.ORDER_STATUS_FAIL,
                            null, thirdReturnCode, errorMsg);
                    // 2.添加一条订单明细信息                    
                    LnPayOrdersJnl jnl = fillLnPayOrdersJnl(orders, new Date());
                    jnl.setChannelTransType(Constants.CHANNEL_TRS_DF);
                    jnl.setTransCode(Constants.ORDER_TRANS_CODE_INIT);
                    jnl.setUserId(lnUser.getId());
                    jnl.setNote("借款人提现失败:"+errorMsg);
                    jnl.setSubAccountCode(null);
                    jnl.setSubAccountId(null);
                    jnl.setReturnCode(thirdReturnCode);
                    jnl.setReturnMsg(errorMsg);
                    lnPayOrdersJnlMapper.insertSelective(jnl);
                }
            });
            return PTMessageEnum.TRANS_ERROR.getCode();
        }
	}

	@Override
	public String sysAccountTransferToPerson(BsHfBankSysAccountTransferVo bsHfBankSysAccountTransferVo) {
		LOG.info("[sysAccountTransferToPerson]入参：{}", JSONObject.fromObject(bsHfBankSysAccountTransferVo));
		Double amount = bsHfBankSysAccountTransferVo.getAmount();
		BsSysConfig configUser = null;
		if (Constants.YUN_HEAD_FEE_2_USER.equals(bsHfBankSysAccountTransferVo.getDestAccount())) {
			configUser = sysConfigService.findConfigByKey(Constants.YUN_HEAD_FEE_2_USER);
		} else if (Constants.ZSD_HEAD_FEE_2_USER.equals(bsHfBankSysAccountTransferVo.getDestAccount())) {
			configUser = sysConfigService.findConfigByKey(Constants.ZSD_HEAD_FEE_2_USER);
		} 
        LOG.info("method sysAccountTransferToPerson configUser:{}", configUser.getConfValue());
		BsUserExample example = new BsUserExample();
		example.createCriteria().andIdEqualTo(Integer.parseInt(configUser.getConfValue())).andStatusEqualTo(Constants.USER_STATUS_1);
		List<BsUser> userList = bsUserMapper.selectByExample(example);
		if(StringUtil.isBlank(bsHfBankSysAccountTransferVo.getNote()) || amount == null || CollectionUtils.isEmpty(userList)){
			throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
		}
		final BsUser user = userList.get(0);
		
		// 转入用户恒丰信息
		BsHfbankUserExtExample userExtExample = new BsHfbankUserExtExample();
		userExtExample.createCriteria().andUserIdEqualTo(user.getId());
		List<BsHfbankUserExt> userExts = bsHfbankUserExtMapper.selectByExample(userExtExample);
		if(CollectionUtils.isEmpty(userExts)){
			throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
		}
		
		// 自有子账户余额校验
		final BsSysSubAccount sysSourceSubAccount = bsSysSubAccountService.findSysSubAccount4Lock(Constants.SYS_ACCOUNT_DEP_JSH);
		if (MoneyUtil.subtract(sysSourceSubAccount.getAvailableBalance(), amount).doubleValue() < 0) {
			throw new PTMessageException(PTMessageEnum.SYS_BALANCE_NOT_ENOUGH_FOR_MANAGE);
		}
		String orderNo = Util.generateSysOrderNo("HFTPS");
		Date transTime = new Date();
		
		// 封装恒丰平台不同子账户转账请求体
		B2GReqMsg_HFBank_PlatformTransfer req = new B2GReqMsg_HFBank_PlatformTransfer();
		req.setAmount(String.valueOf(amount));
		req.setPartner_trans_date(transTime);
		req.setPartner_trans_time(transTime);
		req.setRemark("平台转账个人");
		req.setPlat_account("01");  // 01平台账户
		req.setOrder_no(orderNo);
		req.setPlatcust(userExts.get(0).getHfUserId());
		req.setCause_type(Constants.SYS_ACCOUNT_CASETYPE_RETURN_COMMISSIONFEE);  // 手续费返还-返还手续费

		final BsSysSubAccount sysBgwSubAccount = bsSysSubAccountService.findSysSubAccount4Lock(Constants.SYS_ACCOUNT_BGW_USER);
		final BsSubAccount inSubAccount = bsSubAccountService.findSubAccount4Lock(user.getId(), Constants.PRODUCT_TYPE_DEP_JSH);
			
		// 新增订单信息
		final BsPayOrders orders = new BsPayOrders();
		orders.setAmount(amount);
		orders.setUserName(user.getUserName());
		orders.setOrderNo(orderNo);
		orders.setUserId(user.getId());
		orders.setIdCard(user.getIdCard());
		orders.setMobile(user.getMobile());
		orders.setChannelTransType(Constants.CHANNEL_TRS_TRANSFER);
		orders.setSubAccountId(inSubAccount.getId());		
		orders.setMoneyType(Constants.ORDER_CURRENCY_CNY);
		orders.setAccountType(Constants.ORDER_ACCOUNT_TYPE_SYS);
		if (Constants.YUN_HEAD_FEE_2_USER.equals(bsHfBankSysAccountTransferVo.getDestAccount())) {			
			orders.setTransType(Constants.TRANS_HFBANK_YUN_HEAD_FEE_TRANSFER);
		} else if (Constants.ZSD_HEAD_FEE_2_USER.equals(bsHfBankSysAccountTransferVo.getDestAccount())) {
			orders.setTransType(Constants.TRANS_HFBANK_ZSD_HEAD_FEE_TRANSFER);
		}
		orders.setChannel(Constants.CHANNEL_HFBANK);
		orders.setStatus(Constants.ORDER_STATUS_CREATE);
		orders.setTerminalType(Constants.TERMINAL_TYPE_MANAGE);
		orders.setNote(bsHfBankSysAccountTransferVo.getNote());
		orders.setCreateTime(transTime);
		bsPayOrdersMapper.insertSelective(orders);
		//新增订单流水表
        BsPayOrdersJnl jnl = fillBsPayOrdersJnl(orders, transTime);
        jnl.setUserId(user.getId());
        jnl.setSubAccountId(inSubAccount.getId());
        jnl.setSubAccountCode(inSubAccount.getCode());
        jnl.setChannelTransType(Constants.CHANNEL_TRS_TRANSFER);
        jnl.setTransCode(Constants.ORDER_TRANS_CODE_INIT);
        jnl.setNote(bsHfBankSysAccountTransferVo.getNote());
        bsPayOrdersJnlMapper.insertSelective(jnl);
      
		// 调用平台不同子账户转账接口
		final B2GResMsg_HFBank_PlatformTransfer res;
		try {
			res = hfbankTransportService.platformTransfer(req);
		} catch (Exception e) {
			LOG.error("云贷砍头息划转通讯失败：{}", e.getMessage());
			// 订单和订单流水更新为失败
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {                     	
                	BsPayOrders ordersFail = new BsPayOrders();
                	ordersFail.setId(orders.getId());
                	ordersFail.setStatus(Constants.ORDER_STATUS_CON_FAIL);
                	ordersFail.setUpdateTime(new Date());
                	bsPayOrdersMapper.updateByPrimaryKeySelective(ordersFail);
                	BsPayOrdersJnl jnlFail = fillBsPayOrdersJnl(ordersFail, new Date());
                	jnlFail.setUserId(user.getId());
                	jnlFail.setSubAccountId(inSubAccount.getId());
                	jnlFail.setSubAccountCode(inSubAccount.getCode());
                	jnlFail.setTransCode(Constants.ORDER_TRANS_CODE_COMM_FAIL);
                	jnlFail.setOrderNo(orders.getOrderNo());
                	jnlFail.setTransAmount(orders.getAmount());
                	jnlFail.setChannelTransType(Constants.CHANNEL_TRS_TRANSFER);  
                	bsPayOrdersJnlMapper.insertSelective(jnlFail);
                }
			});     
			return PTMessageEnum.TRANS_ERROR.getCode();
		}
		transTime = new Date();
		if (Constants.DEP_RECODE_SUCCESS.equals(res.getRecode())) {
			// 订单和订单流水更新为成功
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {
                	BsPayOrders ordersSucc = new BsPayOrders();
                	ordersSucc.setId(orders.getId());
                	ordersSucc.setStatus(Constants.ORDER_STATUS_SUCCESS);
                	ordersSucc.setUpdateTime(new Date());
                	ordersSucc.setReturnCode(res.getRecode());
                	ordersSucc.setReturnMsg(res.getRemsg());
                	bsPayOrdersMapper.updateByPrimaryKeySelective(ordersSucc);
                	BsPayOrdersJnl jnlSucc = fillBsPayOrdersJnl(ordersSucc, new Date());
                	jnlSucc.setUserId(user.getId());
                	jnlSucc.setSubAccountId(inSubAccount.getId());
                	jnlSucc.setSubAccountCode(inSubAccount.getCode());
                	jnlSucc.setTransCode(Constants.ORDER_TRANS_CODE_SUCCESS);
                	jnlSucc.setOrderNo(orders.getOrderNo());
                	jnlSucc.setTransAmount(orders.getAmount());
                	jnlSucc.setChannelTransType(Constants.CHANNEL_TRS_TRANSFER);  
                	jnlSucc.setReturnCode(res.getRecode());
                	jnlSucc.setReturnMsg(res.getRemsg());
                	bsPayOrdersJnlMapper.insertSelective(jnlSucc);
                	
                	// 更新转入转出账户 用户DEP_JSH和系统的BGW_USER+ 系统DEP_JSH-
                	BsSysSubAccount tempSourceSysSubAccount = new BsSysSubAccount();
                	tempSourceSysSubAccount.setId(sysSourceSubAccount.getId());
                	tempSourceSysSubAccount.setBalance(MoneyUtil.subtract(sysSourceSubAccount.getBalance(), orders.getAmount()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
                	tempSourceSysSubAccount.setAvailableBalance(MoneyUtil.subtract(sysSourceSubAccount.getAvailableBalance(), orders.getAmount()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
                	tempSourceSysSubAccount.setCanWithdraw(MoneyUtil.subtract(sysSourceSubAccount.getCanWithdraw(), orders.getAmount()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
                	bsSysSubAccountMapper.updateByPrimaryKeySelective(tempSourceSysSubAccount); 
                	BsSysSubAccount tempSysBgwSubAccount = new BsSysSubAccount();
                	tempSysBgwSubAccount.setId(sysBgwSubAccount.getId());
                	tempSysBgwSubAccount.setBalance(MoneyUtil.add(sysBgwSubAccount.getBalance(), orders.getAmount()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
                	tempSysBgwSubAccount.setAvailableBalance(MoneyUtil.add(sysBgwSubAccount.getAvailableBalance(), orders.getAmount()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
                	tempSysBgwSubAccount.setCanWithdraw(MoneyUtil.add(sysBgwSubAccount.getCanWithdraw(), orders.getAmount()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
                	bsSysSubAccountMapper.updateByPrimaryKeySelective(tempSysBgwSubAccount); 
                	
                	BsSubAccount tempInSubAccount = new BsSubAccount();
                	tempInSubAccount.setId(inSubAccount.getId());
                	tempInSubAccount.setBalance(MoneyUtil.add(inSubAccount.getBalance(), orders.getAmount()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
                	tempInSubAccount.setAvailableBalance(MoneyUtil.add(inSubAccount.getAvailableBalance(), orders.getAmount()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
                	tempInSubAccount.setCanWithdraw(MoneyUtil.add(inSubAccount.getCanWithdraw(), orders.getAmount()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
                	bsSubAccountMapper.updateByPrimaryKeySelective(tempInSubAccount);
                	
                	// 系统账户流水
                	BsSysAccountJnl sysAccountJnl = new BsSysAccountJnl();
                	sysAccountJnl.setTransTime(new Date());
                	sysAccountJnl.setTransCode(Constants.SYS_DEP_TRANS_2_PERSON);
                	sysAccountJnl.setTransName("手续费返还");
                	sysAccountJnl.setTransAmount(orders.getAmount());
                	sysAccountJnl.setSysTime(new Date());
                	sysAccountJnl.setChannelTime(null);
                	sysAccountJnl.setChannelJnlNo(null);
                	sysAccountJnl.setCdFlag1(Constants.CD_FLAG_C);
                	sysAccountJnl.setSubAccountCode1(sysSourceSubAccount.getCode());
                	sysAccountJnl.setBeforeBalance1(sysSourceSubAccount.getBalance());
                	sysAccountJnl.setAfterBalance1(tempSourceSysSubAccount.getBalance());
                	sysAccountJnl.setBeforeAvialableBalance1(sysSourceSubAccount.getAvailableBalance());
                	sysAccountJnl.setAfterAvialableBalance1(tempSourceSysSubAccount.getAvailableBalance());
                	sysAccountJnl.setBeforeFreezeBalance1(sysSourceSubAccount.getFreezeBalance());
                	sysAccountJnl.setAfterFreezeBalance1(sysSourceSubAccount.getFreezeBalance());
                	sysAccountJnl.setCdFlag2(Constants.CD_FLAG_D);
                	sysAccountJnl.setSubAccountCode2(sysBgwSubAccount.getCode());
                	sysAccountJnl.setBeforeBalance2(sysBgwSubAccount.getBalance());
                	sysAccountJnl.setAfterBalance2(tempSysBgwSubAccount.getBalance());
                	sysAccountJnl.setBeforeAvialableBalance2(sysBgwSubAccount.getAvailableBalance());
                	sysAccountJnl.setAfterAvialableBalance2(tempSysBgwSubAccount.getAvailableBalance());
                	sysAccountJnl.setBeforeFreezeBalance2(sysBgwSubAccount.getFreezeBalance());
                	sysAccountJnl.setAfterFreezeBalance2(sysBgwSubAccount.getFreezeBalance());
                	sysAccountJnl.setStatus(Constants.ACCOUNT_JNL_STATUS_SUCCESS);
                	sysAccountJnl.setFee(0.0);
                	bsSysAccountJnlMapper.insertSelective(sysAccountJnl);
                	
                	// 用户账户流水
                	BsAccountJnl bsAccountJnl = new BsAccountJnl();
                	bsAccountJnl.setTransTime(new Date());
                	bsAccountJnl.setTransCode(Constants.USER_DEP_2_PERSON);
                	bsAccountJnl.setTransName("手续费返还");
                	bsAccountJnl.setTransAmount(orders.getAmount());
                 	bsAccountJnl.setSysTime(new Date());
                 	bsAccountJnl.setCdFlag1(Constants.CD_FLAG_D);
                 	bsAccountJnl.setUserId1(user.getId());
                 	bsAccountJnl.setAccountId1(inSubAccount.getAccountId());
                 	bsAccountJnl.setSubAccountId1(inSubAccount.getId());
                 	bsAccountJnl.setSubAccountCode1(inSubAccount.getCode());
                 	bsAccountJnl.setBeforeBalance1(inSubAccount.getBalance());
                 	bsAccountJnl.setBeforeAvialableBalance1(inSubAccount.getAvailableBalance());
                 	bsAccountJnl.setBeforeFreezeBalance1(inSubAccount.getFreezeBalance());
                 	bsAccountJnl.setAfterBalance1(tempInSubAccount.getBalance());
                 	bsAccountJnl.setAfterAvialableBalance1(tempInSubAccount.getAvailableBalance());
                 	bsAccountJnl.setAfterFreezeBalance1(inSubAccount.getFreezeBalance());
                 	bsAccountJnl.setStatus(Constants.ACCOUNT_JNL_STATUS_SUCCESS);
                 	bsAccountJnl.setFee(0.0);
                 	bsAccountJnlMapper.insertSelective(bsAccountJnl);
                 	
                 	// 更新bs_user 可提现金额
                 	BsUser tempUser = new BsUser();
                 	tempUser.setId(user.getId());
                 	tempUser.setCanWithdraw(MoneyUtil.add(user.getCanWithdraw(), orders.getAmount()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
                 	bsUserMapper.updateByPrimaryKeySelective(tempUser);
                 	
                 	// 插入用户交易明细表
                 	Date now = new Date();
                 	BsUserTransDetail transDetail = new BsUserTransDetail();
                    transDetail.setUserId(user.getId());
                    transDetail.setTransType(Constants.Trans_TYPE_HEAD_FEE_RETURN);
                    transDetail.setTransStatus(Constants.Trans_STATUS_SUCCESS);
                    transDetail.setOrderNo(Util.generateOrderNo4BaoFoo(8));
                    transDetail.setCreateTime(now);
                    transDetail.setAmount(orders.getAmount());
                    transDetail.setUpdateTime(now);
                    bsUserTransDetailMapper.insertSelective(transDetail);
                 	
                }
			});     
			return PTMessageEnum.TRANS_SUCCESS.getCode();
		} else {
			// 订单和订单流水更新为失败
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {    
                	BsPayOrders ordersFail = new BsPayOrders();
                    ordersFail.setId(orders.getId());
                    ordersFail.setStatus(Constants.ORDER_STATUS_FAIL);
                    ordersFail.setReturnCode(res.getRecode());
                    ordersFail.setReturnMsg(res.getRemsg());
                    ordersFail.setUpdateTime(new Date());
                    bsPayOrdersMapper.updateByPrimaryKeySelective(ordersFail);
                    BsPayOrdersJnl jnlFail = fillBsPayOrdersJnl(ordersFail, new Date());
                    jnlFail.setUserId(user.getId());
                	jnlFail.setSubAccountId(inSubAccount.getId());
                	jnlFail.setSubAccountCode(inSubAccount.getCode());
                    jnlFail.setOrderNo(orders.getOrderNo());
                    jnlFail.setTransAmount(orders.getAmount());
                    jnlFail.setChannelTransType(Constants.CHANNEL_TRS_TRANSFER);  
                    jnlFail.setTransCode(Constants.ORDER_TRANS_CODE_FAIL);
                    jnlFail.setReturnCode(res.getRecode());
                    jnlFail.setReturnMsg(res.getRemsg());
                    bsPayOrdersJnlMapper.insertSelective(jnlFail);
                }
			});  
			return PTMessageEnum.TRANS_ERROR.getCode();
		}
	}
	
}

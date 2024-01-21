package com.pinting.business.facade.site;

import com.pinting.business.accounting.finance.service.DepUserBalanceBuyService;
import com.pinting.business.accounting.finance.service.DepUserTopUpService;
import com.pinting.business.accounting.finance.service.UserBalanceBuyService;
import com.pinting.business.accounting.finance.service.UserTopUpService;
import com.pinting.business.accounting.service.PayOrdersService;
import com.pinting.business.enums.BaoFooEnum;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.hessian.site.message.*;
import com.pinting.business.model.*;
import com.pinting.business.model.vo.UserInfoVO;
import com.pinting.business.service.site.*;
import com.pinting.business.util.Constants;
import com.pinting.core.util.*;
import com.pinting.gateway.hessian.message.baofoo.B2GReqMsg_BaoFooQuickPay_EBank;
import com.pinting.gateway.hessian.message.baofoo.B2GResMsg_BaoFooQuickPay_EBank;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Payment_BuyProduct;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Payment_BuyProduct;
import com.pinting.gateway.hessian.message.reapal.B2GReqMsg_ReapalQuickPay_ResendCode;
import com.pinting.gateway.in.service.DafyUserService;
import com.pinting.gateway.out.service.DafyTransportService;
import com.pinting.gateway.out.service.NetBankService;
import com.pinting.gateway.out.service.ReapalTransportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Project: business
 * @Title: RegularBuyFacade.java
 * @author Huang MengJian
 * @date 2015-1-21 上午11:15:49
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Component("RegularBuy")
public class RegularBuyFacade {

	@Autowired
	private UserService userService;
	@Autowired
	private SubAccountService subAccountService;
	@Autowired
	private ProductService productService;
	@Autowired
	private DafyTransportService dafyTransportService;
	@Autowired
	private DafyUserService dafyUserService;
	@Autowired
	private PayOrdersService payOrdersService;
	@Autowired
	private DafyWapBankService dafyWapBankService;
	@Autowired
	private NetBankService  netBankService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private ReapalTransportService reapalTransportService;
	@Autowired
	private DepUserTopUpService depUserTopUpService;
	@Autowired
	private DepUserBalanceBuyService depUserBalanceBuyService;

	
	private Logger log = LoggerFactory.getLogger(RegularBuyFacade.class);
	
	public void infoQuery (ReqMsg_RegularBuy_InfoQuery reqMsg, ResMsg_RegularBuy_InfoQuery resMsg) {
		
		BsProduct product = productService.findRegularById(reqMsg.getId());
		
		if(product == null){
			throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
		}else{
			resMsg.setId(product.getId());
			resMsg.setInvestNum(product.getInvestNum());
			resMsg.setRate(product.getBaseRate());
			resMsg.setTrem(product.getTerm());
			resMsg.setPriceCeiling(product.getMaxSingleInvestAmount() == null?-1:product.getMaxSingleInvestAmount()); //当上限和下限金额没有设置时，默认金额不受限制
			resMsg.setPriceLimit(product.getMinInvestAmount() == null?-1:product.getMinInvestAmount());
			resMsg.setMaxInvestTimes(product.getMaxInvestTimes());  //能购买的次数
			int buyNum = product.getMaxInvestTimes() - subAccountService.countProductBuyNumByProductId(reqMsg.getId(),reqMsg.getUserId());
			resMsg.setBuyNum(buyNum<0?0:buyNum);  //剩余购买的次数：判断是否小于0，是因为存在早上设置了3次，有人也购买了3次，但是下午却改成了2次，造成变成负数。
			
			double surplusAmount = subAccountService.countProductAmountByProductId(reqMsg.getId(),reqMsg.getUserId());
			resMsg.setSurplusAmount(surplusAmount<0?product.getMaxInvestAmount():surplusAmount);  //小于的情况是还没有购买产品时，那么剩余金额为最初的金额
		}
		
	}
	
	
	@Deprecated
	public void buy(ReqMsg_RegularBuy_Buy reqMsg, ResMsg_RegularBuy_Buy resMsg) {
		
		/**
		 * 1.判断是否已经实名认证，绑定银行卡
		 * 2.判断购买次数是否充足，判断购买金额是否低于下限，高于上限
		 * 3.插入产品子账户，当前状态为银行处理中
		 * 4.购买流水前置登记
		 * 5.新增订单
		 * ==============以上过程全部封装到 【产品前置操作】 方法当中 ==========
		 * 6.向达飞发起购买申请
		 */
		BsProduct product = productService.findRegularById(reqMsg.getProductId());
		//购买前准备工作
		log.info("【用户发起购买信息：】 userId = " + reqMsg.getUserId() + ", amount=" + reqMsg.getMoney() + ", productCode：" + product.getCode() + ", BankId:" + reqMsg.getBankId() );
		
		String orderNo = payOrdersService.preBuyProduct(reqMsg.getBankId()==null||"".equals(reqMsg.getBankId())?-1:Integer.parseInt(reqMsg.getBankId()),reqMsg.getUserId(), reqMsg.getMoney(), product);
		//向达飞发起购买申请
		B2GReqMsg_Payment_BuyProduct req = new B2GReqMsg_Payment_BuyProduct();
		DafyUserExt dafyUser = dafyUserService.findDafyUserByUserId(reqMsg.getUserId());
		req.setAmount(reqMsg.getMoney());
		req.setCustomerId(dafyUser.getDafyUserId());
		req.setProductCode(product.getCode());
		req.setOrderNo(orderNo);
		if(reqMsg.getBankId() != null && !"".equals(reqMsg.getBankId())){  //当为空时，说明此时由pc端发起购买支付
			DafyWapBankExt dafyBank = dafyWapBankService.findDafyBankCodeByBankId(reqMsg.getBankId());
			req.setBankCode(dafyBank.getDafyBankId() + "");
		}
		req.setChannel(reqMsg.getChannel());
		
		B2GResMsg_Payment_BuyProduct res = dafyTransportService.buyProduct(req);
		if(!ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())){
			//修改订单状态为 失败
			BsPayOrders order4Error = new BsPayOrders();
			order4Error.setOrderNo(orderNo);
			order4Error.setUpdateTime(new Date());
			order4Error.setStatus(Constants.ORDER_STATUS_FAIL);
			payOrdersService.modifyPayOrderByOrderNo(order4Error);
			throw new PTMessageException(PTMessageEnum.DAFY_BUY_PRODUCT_ERROR, res.getResMsg());
		}else{
			//返回页
			resMsg.setRetHtml(res.getRetHtml());
			//修改订单状态为 支付处理中
			BsPayOrders order4Accept = new BsPayOrders();
			order4Accept.setOrderNo(orderNo);
			order4Accept.setUpdateTime(new Date());
			order4Accept.setStatus(Constants.ORDER_STATUS_PAYING);
			payOrdersService.modifyPayOrderByOrderNo(order4Accept);
		}
	}
	
	
	public void netBankbuy(ReqMsg_RegularBuy_NetBankbuy reqMsg,ResMsg_RegularBuy_NetBankbuy resMsg){

		
		/**
		 * 1.判断是否已经实名认证，绑定银行卡
		 * 2.判断购买次数是否充足，判断购买金额是否低于下限，高于上限
		 * 3.插入产品子账户，当前状态为银行处理中
		 * 4.购买流水前置登记
		 * 5.新增订单
		 * ==============以上过程全部封装到 【产品前置操作】 方法当中 ==========
		 * 6.向宝付发起购买申请
		 */
	
		BsProduct product = productService.findRegularById(reqMsg.getProductId());
		//购买前置操作
		BsUser bsUser = userService.findUserById(reqMsg.getUserId());
		ReqMsg_RegularBuy_EBankBuy reqBuy = new  ReqMsg_RegularBuy_EBankBuy();
		ResMsg_RegularBuy_EBankBuy resBuy = new  ResMsg_RegularBuy_EBankBuy();
		reqBuy.setUserId(reqMsg.getUserId());
		reqBuy.setProductId(reqMsg.getProductId());
		reqBuy.setAmount(reqMsg.getMoney());
		reqBuy.setTransType(1);
		reqBuy.setBankCode(reqMsg.getBankId());
		//userBuyProductService.eBankBuy(reqBuy, resBuy);
		
		if(resBuy.getOrderNo() == null){
			throw new PTMessageException(PTMessageEnum.DAFY_BUY_EBANK_ERROR);
		}
		
		//购买前准备工作
		log.info("【用户发起购买信息：】 userId = " + reqMsg.getUserId() + ", amount=" + reqMsg.getMoney() + ", productCode：" + product.getCode() + ", BankId:" + reqMsg.getBankId() );
		
       
        B2GReqMsg_BaoFooQuickPay_EBank req=new B2GReqMsg_BaoFooQuickPay_EBank();
        req.setPayId(BaoFooEnum.ebankPayMap.get(reqMsg.getBankId()));
        req.setTradeDate(DateUtil.formatDateTime(resBuy.getOrderDate(),"yyyyMMddHHmmss"));
        req.setTransId(resBuy.getOrderNo());
        req.setOrderMoney(MoneyUtil.format(MoneyUtil.multiply(reqMsg.getMoney(),100d)));
        req.setProductName(product.getName());
        req.setUserName(bsUser.getUserName());
        req.setAdditionalInfo(null);
        //需要根据不同的产品调用不同的页面
        
	    if (reqMsg.getFlag() != null && "qianbao178NetBankBuy".equals(reqMsg.getFlag())) {
	    	req.setPageUrl(GlobEnvUtil.get("ebankbuy.return.url.gen178"));
		}else {
			req.setPageUrl(GlobEnvUtil.get("ebankbuy.return.url.gen"));
		}
       
        
        B2GResMsg_BaoFooQuickPay_EBank res = null;
		try {
			 res = netBankService.netBankBuyProductBaofoo(req);
		} catch (Exception e) {
			e.printStackTrace();
			throw new PTMessageException(PTMessageEnum.DAFY_BUY_PRODUCT_ERROR);
		}
		
		if(StringUtil.isEmpty(res.getHtmlStr())){
			
			throw new PTMessageException(PTMessageEnum.DAFY_BUY_PRODUCT_ERROR, res.getResMsg());
		}else{
			//返回页
			resMsg.setRetHtml(res.getHtmlStr());
		}
		
		
		/*
		 * 
		 * 19付网银支付，暂时不使用，改为宝付网银支付
		 * 以后如果可以根据用户优先支付渠道设置选取网银通道
		 * 
		B2GReqMsg_NetBank_EBank req = new B2GReqMsg_NetBank_EBank();
		req.setOrderDate(resBuy.getOrderDate());
		req.setOrderId(resBuy.getOrderNo());
		req.setAmount(reqMsg.getMoney());
		req.setMxUserIp(GlobEnvUtil.get("19pay.netbank.mxuserIP"));
		req.setOrderPName("币港湾理财");
		req.setOrderPDesc(product.getTerm4Day()+"天"+product.getBaseRate());
		req.setUserMobile(bsUser.getMobile());
		req.setOrderDesc("D_BANK");
		req.setTradeDesc("新网银交易");
		req.setFlag(reqMsg.getFlag());
		req.setBankId(reqMsg.getBankId());
		B2GResMsg_NetBank_EBank res = null;
		try {
			 res = netBankService.netBankBuyProduct(req);
		} catch (Exception e) {
			e.printStackTrace();
			throw new PTMessageException(PTMessageEnum.DAFY_BUY_PRODUCT_ERROR);
		}
		
		if(StringUtil.isEmpty(res.getHtmlStr())){
			
			throw new PTMessageException(PTMessageEnum.DAFY_BUY_PRODUCT_ERROR, res.getResMsg());
		}else{
			//返回页
			resMsg.setRetHtml(res.getHtmlStr());
		}*/
	
	}
	
	/**
	 * 
	 * @Title: preOrder 
	 * @Description: 充值预下单||正式下单
	 * @param req
	 * @param res
	 * @throws
	 */
	public void order(ReqMsg_RegularBuy_Order req, ResMsg_RegularBuy_Order res) {
		if(req.getPlaceOrder() == Constants.READY_PLANCE_ORDER) {
			depUserTopUpService.hfPre(req, res);
		} else {
			depUserTopUpService.hfConfirm(req, res);
		}
	}
	
	/**
	 * 
	 * @Title: balanceBuy
	 * @Description: 通过余额购买理财产品
	 * @param req
	 * @param res
	 * @throws
	 */
	public void balanceBuy(ReqMsg_RegularBuy_BalanceBuy req, ResMsg_RegularBuy_BalanceBuy res) {
		BsPropertyInfo propertyInfo = productService.queryPropertyInfoByProductId(req.getProductId());
		if(propertyInfo == null ){
			throw new PTMessageException(PTMessageEnum.CHECK_PRODUCT_OFF_ERROR);
		}
		if(Constants.PRODUCT_PROPERTY_SYMBOL_ZAN.equals(propertyInfo.getPropertySymbol())) {
			depUserBalanceBuyService.buyStage(req, res);
//			userBalanceBuyService.auth(req, res);
		}else {
			depUserBalanceBuyService.buyFixed(req, res);
//			userBalanceBuyService.buy(req, res);
		}
	}
	
	/**
	 * 
	 * @Title: eBankBuy 
	 * @Description: 通过网银购买理财产品
	 * @param req
	 * @param res
	 * @throws
	 */
	public void eBankBuy(ReqMsg_RegularBuy_EBankBuy req, ResMsg_RegularBuy_EBankBuy res) {
		//userBuyProductService.eBankBuy(req, res);
	}
	
	/**
	 * 
	 * @Title: reapalQuickCMB 
	 * @Description: 融宝招行快捷支付相关
	 * @param req
	 * @param res
	 * @throws
	 */
	public void reapalQuickCMB(ReqMsg_RegularBuy_ReapalQuickCMB req, ResMsg_RegularBuy_ReapalQuickCMB
			res) {
		UserInfoVO userInfoVO =  orderService.reapalQuickCMB(req.getOrderNo(),req.getProductId());
		
		res.setUserId(userInfoVO.getUserId());
		res.setAmount(userInfoVO.getAmount());
		res.setCardNo(userInfoVO.getCardNo());
		res.setUserName(userInfoVO.getUserName());
		res.setIdCard(userInfoVO.getIdCard());
		res.setMobile(userInfoVO.getMobile());
		res.setProductId(userInfoVO.getProductId());
		res.setProductName(userInfoVO.getProductName());
		res.setBankId(userInfoVO.getBankId());
		res.setBankName(userInfoVO.getBankName());
		res.setIsBind(userInfoVO.getIsBind());
		res.setTransType(userInfoVO.getTransType());
		res.setTerminalType(userInfoVO.getTerminalType());
	}
	
	
	public void resendCode(ReqMsg_RegularBuy_ResendCode req, ResMsg_RegularBuy_ResendCode
			res){
		B2GReqMsg_ReapalQuickPay_ResendCode b2gReqMsg_ReapalQuickPay_ResendCode = new B2GReqMsg_ReapalQuickPay_ResendCode();
		b2gReqMsg_ReapalQuickPay_ResendCode.setOrderNo(req.getOrderNo());
		reapalTransportService.resendCode(b2gReqMsg_ReapalQuickPay_ResendCode);
	}
}

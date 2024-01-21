package com.pinting.business.aspect;

import com.pinting.business.dao.BsPayOrdersMapper;
import com.pinting.business.dao.BsProductMapper;
import com.pinting.business.dao.BsSubAccountMapper;
import com.pinting.business.dao.BsUserMapper;
import com.pinting.business.hessian.site.message.ReqMsg_RegularBuy_BalanceBuy;
import com.pinting.business.hessian.site.message.ResMsg_RegularBuy_BalanceBuy;
import com.pinting.business.model.BsPayOrders;
import com.pinting.business.model.BsPayOrdersExample;
import com.pinting.business.model.BsProduct;
import com.pinting.business.model.BsSubAccount;
import com.pinting.business.model.BsUser;
import com.pinting.business.util.Constants;
import com.pinting.core.util.MoneyUtil;
import com.pinting.gateway.hessian.message.xicai.B2GReqMsg_Xicai_InvestCallBack;
import com.pinting.gateway.out.service.XicaiTransportService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * 
 * @ClassName: XicaiAspect 
 * @Description: 希财投资回调
 * @author lenovo
 * @date 2016年3月25日 上午11:29:58 
 *
 */
@Aspect
@Component
@Order(1)
public class XicaiAspect {
	
	@Autowired
	private BsPayOrdersMapper payOrderMapper;
	@Autowired
	private XicaiTransportService xicaiService;
	@Autowired
	private BsUserMapper userMapper;
	@Autowired
	private BsProductMapper productMapper;
	@Autowired
	private BsSubAccountMapper subAccountMapper;
	

	/*@Pointcut("execution(public * com.pinting.business.accounting.buyproduct.service.impl.UserBuyProductServiceImpl.notify(..))")
	public void pay19Pointcut(){}
	*/
	/*@Pointcut("execution(public * com.pinting.business.accounting.buyproduct.service.impl.UserBuyProductServiceImpl.channelNotify(..))")
	public void reapalPointcut(){}*/
	
	@Pointcut("execution(public * com.pinting.business.accounting.finance.service.impl.UserBalanceBuyServiceImpl.buy(..))")
	public void balanceBuyPointcut(){}
	
	/*@Pointcut("execution(public * com.pinting.business.accounting.buyproduct.service.impl.UserBuyProductServiceImpl.notifyEBank(..))")
	public void eBankPointcut(){}*/
	
	/*@AfterReturning("pay19Pointcut()")
	public void pay19AfterXicai(JoinPoint call) {
		G2BReqMsg_Pay19QuickPay_PayResultNotice req = (G2BReqMsg_Pay19QuickPay_PayResultNotice)call.getArgs()[0];
		
		
		BsPayOrdersExample orderExample = new BsPayOrdersExample();
		orderExample.createCriteria().andUserIdEqualTo(Integer.parseInt(req.getUserId())).andOrderNoEqualTo(req.getOrderId()).andStatusEqualTo(Constants.ORDER_STATUS_SUCCESS);
		List<BsPayOrders> orderList = payOrderMapper.selectByExample(orderExample);

		if(!CollectionUtils.isEmpty(orderList)) {
			BsUser user = userMapper.selectByPrimaryKey(Integer.parseInt(req.getUserId()));
			//判断用户子账户是否存在
			BsSubAccount subAccount = subAccountMapper.selectByPrimaryKey(orderList.get(0).getSubAccountId());
			if(subAccount != null && user != null && Constants.XICAI_AGENT_ID == user.getAgentId()) {
				//查询产品
				BsProduct product = productMapper.selectByPrimaryKey(subAccount.getProductId());
				if (product!=null ) {
					//希财投资回调
					xicaiInvestCallBack(user,orderList.get(0),product);
				}
			}
		}
	}
	*/
	/*@AfterReturning("reapalPointcut()")
	public void reapalAfterXicai(JoinPoint call) {
		G2BReqMsg_ReapalQuickPay_ReapalNotify req = (G2BReqMsg_ReapalQuickPay_ReapalNotify)call.getArgs()[0];
		
		
		
		BsPayOrdersExample orderExample = new BsPayOrdersExample();
		orderExample.createCriteria().andOrderNoEqualTo(req.getOrderNo()).andStatusEqualTo(Constants.ORDER_STATUS_SUCCESS);
		List<BsPayOrders> orderList = payOrderMapper.selectByExample(orderExample);

		if(!CollectionUtils.isEmpty(orderList)) {
			BsUser user = userMapper.selectByPrimaryKey(orderList.get(0).getUserId());
			//判断用户子账户是否存在
			BsSubAccount subAccount = subAccountMapper.selectByPrimaryKey(orderList.get(0).getSubAccountId());
			if(subAccount != null && user != null && Constants.XICAI_AGENT_ID == user.getAgentId()) {
				//查询产品
				BsProduct product = productMapper.selectByPrimaryKey(subAccount.getProductId());
				if (product!=null ) {
					//希财投资回调
					xicaiInvestCallBack(user,orderList.get(0),product);
				}
			}
		}
	}*/
	
	@AfterReturning("balanceBuyPointcut()")
	public void balanceBuyAfterXicai(JoinPoint call) {
		ReqMsg_RegularBuy_BalanceBuy req = (ReqMsg_RegularBuy_BalanceBuy)call.getArgs()[0];
		BsUser user = userMapper.selectByPrimaryKey(req.getUserId());
		BsProduct product = productMapper.selectProByUser(req.getProductId(), req.getUserId());
		ResMsg_RegularBuy_BalanceBuy res = (ResMsg_RegularBuy_BalanceBuy)call.getArgs()[1];
		BsPayOrdersExample csaiCallBackExample = new BsPayOrdersExample();
		csaiCallBackExample.createCriteria().andSubAccountIdEqualTo((Integer)res.getExtendMap().get("subAccountId")).andTransTypeEqualTo(Constants.USER_BALANCE_BUY_PRODUCT).andStatusEqualTo(Constants.ORDER_STATUS_SUCCESS);
		List<BsPayOrders> csaiCallBackOrder = payOrderMapper.selectByExample(csaiCallBackExample);
		
		if (!CollectionUtils.isEmpty(csaiCallBackOrder) && product != null && user != null && Constants.XICAI_AGENT_ID == user.getAgentId()) {
			
			BsSubAccount subAccount = subAccountMapper.selectByPrimaryKey(csaiCallBackOrder.get(0).getSubAccountId());
			if (Constants.PRODUCT_TYPE_REG.equals(subAccount.getProductType()) ) {
				//希财投资回调
				xicaiInvestCallBack(user,csaiCallBackOrder.get(0),product);
			}

		}
	}
	
	/*@AfterReturning("eBankPointcut()")
	public void eBankAfterXicai(JoinPoint call) {
		G2BReqMsg_Pay19NewCounter_NewCounterResultNotice req = (G2BReqMsg_Pay19NewCounter_NewCounterResultNotice)call.getArgs()[0];
	

		BsPayOrdersExample orderExample = new BsPayOrdersExample();
		orderExample.createCriteria().andOrderNoEqualTo(req.getMxOrderId()).andStatusEqualTo(Constants.ORDER_STATUS_SUCCESS);
		List<BsPayOrders> orderList = payOrderMapper.selectByExample(orderExample);

		if(!CollectionUtils.isEmpty(orderList)) {
			BsUser user = userMapper.selectByPrimaryKey(orderList.get(0).getUserId());
			//判断用户子账户是否存在
			BsSubAccount subAccount = subAccountMapper.selectByPrimaryKey(orderList.get(0).getSubAccountId());
			if(subAccount != null && user != null && Constants.XICAI_AGENT_ID == user.getAgentId()) {
				//查询产品
				BsProduct product = productMapper.selectByPrimaryKey(subAccount.getProductId());
				if (product!=null ) {
					//希财投资回调
					xicaiInvestCallBack(user,orderList.get(0),product);
				}
			}
		}
	}*/
	
	
	/**
	 * 
	 * @Title: xicaiInvestCallBack 
	 * @Description: 向希财发送投资回调信息
	 * @throws
	 */
	private void xicaiInvestCallBack(BsUser user, BsPayOrders order, BsProduct product) {
		if(Constants.XICAI_AGENT_ID.equals(user.getAgentId())) {
			B2GReqMsg_Xicai_InvestCallBack req = new B2GReqMsg_Xicai_InvestCallBack();
			req.setPhone(user.getMobile());
			req.setPid(product.getId());
			req.setSubAccountId(order.getSubAccountId());
			req.setInvestamount(order.getAmount());
			req.setCommision(calculate(product.getTerm4Day(),2.0,order.getAmount()));
			req.setEarnings(calculate(product.getTerm4Day(),product.getBaseRate(),order.getAmount()));
			xicaiService.investCallBack(req);
		}
	}
	
	private Double calculate(Integer term, Double rate, Double amount) {
		BigDecimal v1 = MoneyUtil.divide(String.valueOf(term), "365", 5);
		BigDecimal v2 = MoneyUtil.divide(rate, 100.0, 5);
		BigDecimal v3 = MoneyUtil.multiply(v1.doubleValue(), v2.doubleValue());
		Double result = MoneyUtil.multiply(amount, v3.doubleValue()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return result;
	}
}

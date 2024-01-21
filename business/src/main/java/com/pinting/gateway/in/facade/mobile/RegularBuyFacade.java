package com.pinting.gateway.in.facade.mobile;

import com.pinting.business.accounting.finance.service.DepUserBalanceBuyService;
import com.pinting.business.accounting.finance.service.DepUserTopUpService;
import com.pinting.business.accounting.finance.service.UserBalanceBuyService;
import com.pinting.business.accounting.finance.service.UserTopUpService;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.hessian.site.message.*;
import com.pinting.business.model.BsPropertyInfo;
import com.pinting.business.model.vo.UserInfoVO;
import com.pinting.business.service.site.OrderService;
import com.pinting.business.service.site.ProductService;
import com.pinting.business.util.Constants;
import com.pinting.gateway.in.util.InterfaceVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Project: business
 * @Title: RegularBuyFacade.java
 * @author Huang MengJian
 * @date 2015-1-21 上午11:15:49
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Component("appRegularBuy")
public class RegularBuyFacade {

	@Autowired
	private OrderService orderService;
	@Autowired
	private UserTopUpService userTopUpService;
	@Autowired
	private UserBalanceBuyService userBalanceBuyService;
	@Autowired
	private ProductService productService;
	@Autowired
	private DepUserTopUpService depUserTopUpService;
	@Autowired
	private DepUserBalanceBuyService depUserBalanceBuyService;

	/**
	 * 
	 * @Title: preOrder 
	 * @Description: 购买理财产品（包含预下单，正式下单和充值）
	 * @param req
	 * @param res
	 * @throws
	 */
	@InterfaceVersion("Order/1.0.0")
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
	@InterfaceVersion("BalanceBuy/1.0.0")
	public void balanceBuy(ReqMsg_RegularBuy_BalanceBuy req, ResMsg_RegularBuy_BalanceBuy res) {
		BsPropertyInfo propertyInfo = productService.queryPropertyInfoByProductId(req.getProductId());
		if(propertyInfo == null ){
			throw new PTMessageException(PTMessageEnum.CHECK_PRODUCT_OFF_ERROR);
		}
		if(Constants.PRODUCT_PROPERTY_SYMBOL_ZAN.equals(propertyInfo.getPropertySymbol())) {
			depUserBalanceBuyService.buyStage(req, res);
		}else {
			depUserBalanceBuyService.buyFixed(req, res);
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
	@InterfaceVersion("ReapalQuickCMB/1.0.0")
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
	
}

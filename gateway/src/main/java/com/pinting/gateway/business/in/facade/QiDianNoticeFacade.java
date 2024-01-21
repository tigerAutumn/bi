package com.pinting.gateway.business.in.facade;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.gateway.hessian.message.qidian.B2GReqMsg_QiDianNotice_CustomerInfoSync;
import com.pinting.gateway.hessian.message.qidian.B2GReqMsg_QiDianNotice_OrderInfoSync;
import com.pinting.gateway.hessian.message.qidian.B2GResMsg_QiDianNotice_CustomerInfoSync;
import com.pinting.gateway.hessian.message.qidian.B2GResMsg_QiDianNotice_OrderInfoSync;
import com.pinting.gateway.hessian.message.qidian.model.Customers;
import com.pinting.gateway.hessian.message.qidian.model.OrderInfos;
import com.pinting.gateway.qidian.out.model.CustomerInfoSyncReqModel;
import com.pinting.gateway.qidian.out.model.OrderInfoSyncReqModel;
import com.pinting.gateway.qidian.out.service.SendQiDianService;

/**
 * 
 * @project gateway
 * @title QiDianNoticeFacade.java
 * @author Dragon & cat
 * @date 2018-3-21
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
@Component("QiDianNotice")
public class QiDianNoticeFacade {

	@Autowired
    private SendQiDianService sendQiDianService;
	
	/**
	 * 用户信息同步通知
	 * 
	 * @param req
	 * @param res
	 * @throws Exception
	 */
	public void customerInfoSync(B2GReqMsg_QiDianNotice_CustomerInfoSync req,
			B2GResMsg_QiDianNotice_CustomerInfoSync res) throws Exception {

		CustomerInfoSyncReqModel customerInfoSyncReqModel = new CustomerInfoSyncReqModel();

		List<com.pinting.gateway.qidian.out.model.Customers> customerListReq = new ArrayList<com.pinting.gateway.qidian.out.model.Customers>();
		
		if (CollectionUtils.isNotEmpty(req.getCustomers())) {			
			for (Customers customers : req.getCustomers()) {
				com.pinting.gateway.qidian.out.model.Customers customer = new com.pinting.gateway.qidian.out.model.Customers();
				customer.setFranchiseeId(customers.getFranchiseeId());
				customer.setCustomerId(customers.getCustomerId());
				customer.setCustomerName(customers.getCustomerName());
				customer.setCustomerMobile(customers.getCustomerMobile());
				customer.setCustomerLevel(customers.getCustomerLevel());
				customer.setRealNameStatus(customers.getRealNameStatus());
				customer.setIdCardNo(customers.getIdCardNo());
				customer.setRemainInvest(customers.getRemainInvest() != null?MoneyUtil.multiply(customers.getRemainInvest(), 100).longValue():0);
				customer.setTotalInvest(customers.getTotalInvest() != null? MoneyUtil.multiply(customers.getTotalInvest(), 100).longValue():0);
				customer.setRegisterTime(customers.getRegisterTime() !=null ?DateUtil.format(customers.getRegisterTime()):null);
				customer.setLoginTime(customers.getLoginTime()!=null ?DateUtil.format(customers.getLoginTime()):null);
				customerListReq.add(customer);
			}
		}
		customerInfoSyncReqModel.setType(req.getType());
		customerInfoSyncReqModel.setCustomers(customerListReq);
		
		sendQiDianService.customerInfoSync(customerInfoSyncReqModel);
	}
	
	
	/**
	 * 订单信息同步通知
	 * 
	 * @param req
	 * @param res
	 * @throws Exception
	 */
	public void orderInfoSync(B2GReqMsg_QiDianNotice_OrderInfoSync req,
			B2GResMsg_QiDianNotice_OrderInfoSync res) throws Exception {

		OrderInfoSyncReqModel orderInfoSyncReqModel = new OrderInfoSyncReqModel();

		List<com.pinting.gateway.qidian.out.model.OrderInfos> orderInfoListReq = new ArrayList<com.pinting.gateway.qidian.out.model.OrderInfos>();
		
		if (CollectionUtils.isNotEmpty(req.getOrderInfos())) {			
			for (OrderInfos orderInfos : req.getOrderInfos()) {
				com.pinting.gateway.qidian.out.model.OrderInfos orderInfo = new com.pinting.gateway.qidian.out.model.OrderInfos();
				orderInfo.setOrderNo(orderInfos.getOrderNo());
				orderInfo.setCustomerId(orderInfos.getCustomerId());
				orderInfo.setPartnerName(orderInfos.getPartnerName());
				orderInfo.setProductId(orderInfos.getProductId());
				orderInfo.setProductName(orderInfos.getProductName());
				orderInfo.setProductTerm(orderInfos.getProductTerm());
				orderInfo.setInvestStatus(orderInfos.getInvestStatus());
				orderInfo.setInvestStatusDesc(orderInfos.getInvestStatusDesc());
				orderInfo.setReturnType(orderInfos.getReturnType());
				orderInfo.setBaseRate(orderInfos.getBaseRate());
				orderInfo.setIsRaise(orderInfos.getIsRaise());
				orderInfo.setRaiseRate(orderInfos.getRaiseRate());
				orderInfo.setOpenBalance(orderInfos.getOpenBalance()!= null?MoneyUtil.multiply(orderInfos.getOpenBalance(), 100).longValue():0);
				orderInfo.setBalance(orderInfos.getBalance()!=null ?MoneyUtil.multiply(orderInfos.getBalance(), 100).longValue():0);
				orderInfo.setExpectYield(orderInfos.getExpectYield() != null ?MoneyUtil.multiply(orderInfos.getExpectYield(), 100).longValue():0);
				orderInfo.setOpenTime(orderInfos.getOpenTime() != null ?DateUtil.format(orderInfos.getOpenTime()):null);
				orderInfo.setInterestBeginDate(orderInfos.getInterestBeginDate() != null ? DateUtil.formatDateTime(orderInfos.getInterestBeginDate(),DateUtil.DATE_FORMAT+" 00:00:00"):null);
				orderInfo.setUpdateTime(orderInfos.getUpdateTime() != null ? DateUtil.format(orderInfos.getUpdateTime()): null);
				orderInfo.setPlanDate(orderInfos.getPlanDate() != null ? DateUtil.formatDateTime(orderInfos.getPlanDate(),DateUtil.DATE_FORMAT+" 00:00:00"):null );
				orderInfo.setYield(orderInfos.getYield() != null ? MoneyUtil.multiply(orderInfos.getYield(), 100).longValue():0);
				orderInfo.setReturnDate(orderInfos.getReturnDate() != null ? DateUtil.formatDateTime(orderInfos.getReturnDate(),DateUtil.DATE_FORMAT+" 00:00:00"): null);
				orderInfo.setIsDiscount(orderInfos.getIsDiscount());
				orderInfo.setDiscountAmount(orderInfos.getDiscountAmount() != null ? MoneyUtil.multiply(orderInfos.getDiscountAmount(), 100).longValue():0);
				orderInfoListReq.add(orderInfo);
			}
		}
		orderInfoSyncReqModel.setType(req.getType());
		orderInfoSyncReqModel.setOrderInfos(orderInfoListReq);
		
		sendQiDianService.orderInfoSync(orderInfoSyncReqModel);
	}



}

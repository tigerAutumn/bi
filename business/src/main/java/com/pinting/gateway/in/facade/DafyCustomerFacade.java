package com.pinting.gateway.in.facade;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.accounting.service.CustomerReceiveMoneyService;
import com.pinting.business.accounting.service.PayOrdersService;
import com.pinting.business.model.BsCustomerReceiveMoney;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_Customer_ReceiveMoney;
import com.pinting.gateway.hessian.message.dafy.G2BResMsg_Customer_ReceiveMoney;
import com.pinting.gateway.hessian.message.dafy.model.ReceiveMoneyDetail;

/**
 * 客户相关处理类
 * @Project: business
 * @Title: DafyCustomerFacade.java
 * @author dingpf
 * @date 2015-4-3 下午1:40:10
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Component("Customer")
public class DafyCustomerFacade {
	
	@Autowired
	private PayOrdersService orderService;
	@Autowired
	private CustomerReceiveMoneyService customerReceiveMoneyService;
	
	private Logger log = LoggerFactory.getLogger(DafyCustomerFacade.class);
	
	public void receiveMoney(G2BReqMsg_Customer_ReceiveMoney req,
			G2BResMsg_Customer_ReceiveMoney res){
		List<ReceiveMoneyDetail> list = req.getDataList();
		if(list != null && list.size() > 0){
			log.info("====================>【理财回款通知】Business平台已收到理财回款通知，共【"+list.size()+"】条理财产品需处理");
			for (ReceiveMoneyDetail receiveMoneyDetail : list) {
				log.info("====================>【理财回款通知】订单号【"+receiveMoneyDetail.getOrderNo()+"】开始进行回款处理");
				//登记该条回款通知
				BsCustomerReceiveMoney receiveMoney = new BsCustomerReceiveMoney();
				receiveMoney.setAmountBase(receiveMoneyDetail.getAmountBj());
				receiveMoney.setAmountInterest(receiveMoneyDetail.getAmountLx());
				receiveMoney.setBankNo(receiveMoneyDetail.getBankNo());
				receiveMoney.setCardNo(receiveMoneyDetail.getCardNo());
				receiveMoney.setCreateTime(new Date());
				receiveMoney.setCustomerName(receiveMoneyDetail.getName());
				receiveMoney.setOrderNo(receiveMoneyDetail.getOrderNo());
				receiveMoney.setProductCode(receiveMoneyDetail.getProductId());
				receiveMoney.setStatus(receiveMoneyDetail.getStatus());
				receiveMoney.setSuccessTime(receiveMoneyDetail.getSuccessTime());
				customerReceiveMoneyService.addCustomerReceiveMoney(receiveMoney);
				//处理回款
				orderService.receiveMoney(receiveMoneyDetail);
			}
		}
	}
}

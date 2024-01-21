package com.pinting.business.accounting.service.impl.process;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pinting.gateway.hessian.message.qb178.B2GReqMsg_APP178_OrderNotice;
import com.pinting.gateway.hessian.message.qb178.B2GResMsg_APP178_OrderNotice;
import com.pinting.gateway.out.service.App178TransportService;

/**
 * 钱报178平台订单通知线程
 * @project business
 * @title OrderNoticeProcess.java
 * @author Dragon & cat
 * @date 2017-8-18
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
public class OrderNoticeProcess implements Runnable{
	private Logger log = LoggerFactory.getLogger(OrderNoticeProcess.class);
	
	private B2GReqMsg_APP178_OrderNotice    orderNotice;
	 
	private App178TransportService app178TransportService;

	public void setOrderNotice(B2GReqMsg_APP178_OrderNotice orderNotice) {
		this.orderNotice = orderNotice;
	}

	public void setApp178TransportService(
			App178TransportService app178TransportService) {
		this.app178TransportService = app178TransportService;
	}



	@Override
	public void run() {
		log.info("钱报178平台订单通知线程开始");
		B2GResMsg_APP178_OrderNotice res = app178TransportService.orderNotice(orderNotice);
		log.info("钱报178平台订单通知线程结束:"+res.getResCode()+"["+res.getResMsg()+"]");
	}
	
}

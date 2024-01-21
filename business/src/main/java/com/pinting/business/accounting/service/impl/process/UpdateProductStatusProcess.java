package com.pinting.business.accounting.service.impl.process;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pinting.gateway.hessian.message.qb178.B2GReqMsg_APP178_UpdateProductInfo;
import com.pinting.gateway.hessian.message.qb178.B2GResMsg_APP178_UpdateProductInfo;
import com.pinting.gateway.out.service.App178TransportService;
/**
 * 更新产品状态
 * @project business
 * @title UpdateProductStatusProcess.java
 * @author Dragon & cat
 * @date 2017-8-18
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
public class UpdateProductStatusProcess implements Runnable{
	private Logger log = LoggerFactory.getLogger(UpdateProductStatusProcess.class);
	
	private B2GReqMsg_APP178_UpdateProductInfo    updateProductInfo;
	 
	private App178TransportService  app178TransportService;

	

	public void setUpdateProductInfo(
			B2GReqMsg_APP178_UpdateProductInfo updateProductInfo) {
		this.updateProductInfo = updateProductInfo;
	}



	public void setApp178TransportService(
			App178TransportService app178TransportService) {
		this.app178TransportService = app178TransportService;
	}



	@Override
	public void run() {
		log.info("钱报178更新产品状态通知线程开始");
		B2GResMsg_APP178_UpdateProductInfo res = app178TransportService.updateProductInfo(updateProductInfo);
		log.info("钱报178更新产品状态通知线程结束:"+res.getResCode()+"["+res.getResMsg()+"]");
	}
	
}

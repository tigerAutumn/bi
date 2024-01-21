package com.pinting.business.accounting.service.impl.process;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pinting.gateway.hessian.message.qb178.B2GReqMsg_APP178_UpdateUserInfo;
import com.pinting.gateway.hessian.message.qb178.B2GResMsg_APP178_UpdateUserInfo;
import com.pinting.gateway.out.service.App178TransportService;
/**
 * 更新用户信息
 * @project business
 * @title UpdateUserInfoProcess.java
 * @author Dragon & cat
 * @date 2017-8-18
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
public class UpdateUserInfoProcess  implements Runnable {
	private Logger log = LoggerFactory.getLogger(UpdateUserInfoProcess.class);
	
	private B2GReqMsg_APP178_UpdateUserInfo  updateUserInfo;
	 
	private App178TransportService  app178TransportService;



	public void setUpdateUserInfo(B2GReqMsg_APP178_UpdateUserInfo updateUserInfo) {
		this.updateUserInfo = updateUserInfo;
	}



	public void setApp178TransportService(
			App178TransportService app178TransportService) {
		this.app178TransportService = app178TransportService;
	}



	@Override
	public void run() {
		log.info("钱报178更新用户信息通知线程开始");
		B2GResMsg_APP178_UpdateUserInfo res = app178TransportService.UpdateUserInfo(updateUserInfo);
		log.info("钱报178更新用户信息通知线程结束:"+res.getResCode()+"["+res.getResMsg()+"]");
	}
	
}

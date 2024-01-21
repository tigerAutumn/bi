package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;
/**
 * 
 * @project gateway
 * @title ReqMsg_User_ActivatePageInfo.java
 * @author Dragon & cat
 * @date 2017-4-12
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
public class ReqMsg_User_ActivatePageInfo extends ReqMsg {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2265019719155242233L;
	/*用户ID*/
	private		Integer 	userId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	
}

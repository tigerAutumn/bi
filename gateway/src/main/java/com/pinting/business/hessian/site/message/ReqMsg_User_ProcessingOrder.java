package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_User_ProcessingOrder extends ReqMsg {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3149675335668762417L;
	private Integer userId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	
	
}

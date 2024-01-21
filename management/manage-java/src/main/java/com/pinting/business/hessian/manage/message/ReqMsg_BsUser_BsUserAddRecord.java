package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_BsUser_BsUserAddRecord extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8029237860485492269L;

	private Integer userId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
}

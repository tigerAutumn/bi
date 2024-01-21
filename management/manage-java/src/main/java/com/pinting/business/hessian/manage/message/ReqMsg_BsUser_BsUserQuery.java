package com.pinting.business.hessian.manage.message;


import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_BsUser_BsUserQuery extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3215992899070780554L;

	private Integer userId;
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
}

package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_WeekHayActivity_CountUserRemind extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6992233218451718010L;
	
	private Integer userId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}

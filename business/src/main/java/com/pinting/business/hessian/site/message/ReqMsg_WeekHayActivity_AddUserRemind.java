package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_WeekHayActivity_AddUserRemind extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8067905042781354748L;

	private Integer userId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}

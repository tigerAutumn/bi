package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_User_CheckNewUser extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 203948751849686240L;
	
	private int userId;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}

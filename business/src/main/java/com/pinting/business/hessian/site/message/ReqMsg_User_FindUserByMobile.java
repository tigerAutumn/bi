package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_User_FindUserByMobile extends ReqMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8836914034208538701L;
	private String mobile;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
}

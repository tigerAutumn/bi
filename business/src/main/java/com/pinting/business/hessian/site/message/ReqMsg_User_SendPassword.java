package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_User_SendPassword extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 478885483808789704L;

	private String password;
	
	private String mobile;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}

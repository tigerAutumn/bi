package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_SMS_Interval extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1379019328898584905L;
	private String mobile;
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
}

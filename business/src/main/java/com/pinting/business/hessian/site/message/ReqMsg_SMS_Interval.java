package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_SMS_Interval extends ReqMsg {


	/**
	 * 
	 */
	private static final long serialVersionUID = 4904772905721888664L;
	private String mobile;
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
}

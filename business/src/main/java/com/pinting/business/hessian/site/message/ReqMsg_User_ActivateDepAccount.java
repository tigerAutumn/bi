package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_User_ActivateDepAccount extends ReqMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5793040993936590617L;
	/*用户ID*/
	private		Integer  	userId;
	/*手机验证码*/
	private		String  	mobileCode;
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getMobileCode() {
		return mobileCode;
	}
	public void setMobileCode(String mobileCode) {
		this.mobileCode = mobileCode;
	}
	
	
}

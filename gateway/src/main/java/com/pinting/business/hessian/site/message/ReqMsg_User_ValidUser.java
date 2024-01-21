package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_User_ValidUser extends ReqMsg {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8695951917523889381L;
	private String mobile;//	手机号		
	private String mobileCode;//手机验证码
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getMobileCode() {
		return mobileCode;
	}
	public void setMobileCode(String mobileCode) {
		this.mobileCode = mobileCode;
	}
	
	
}

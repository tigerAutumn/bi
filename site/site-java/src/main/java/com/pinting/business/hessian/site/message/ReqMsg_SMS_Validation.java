package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;
/**
 * @Project: site-java
 * @Title: ReqMsg_SMS_Validation.java
 * @author Dong Wenkai
 * @date 2015-1-21 下午6:43:11
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class ReqMsg_SMS_Validation extends ReqMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3332563882845996474L;
	private String mobile;//手机	 可选
	private String mobileCode;//用户输入验证码 	选填

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

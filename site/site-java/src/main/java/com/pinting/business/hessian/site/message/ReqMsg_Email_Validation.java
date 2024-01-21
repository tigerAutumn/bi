package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;
/**
 * @Project: site-java
 * @Title: ReqMsg_Email_Validation.java
 * @author Dong Wenkai
 * @date 2015-1-21 下午6:40:55
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class ReqMsg_Email_Validation extends ReqMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8281147900557955552L;
	private String emailCode;//用户输入验证码 	选填
	private String email;//手机	 可选

	public String getEmailCode() {
		return emailCode;
	}
	public void setEmailCode(String emailCode) {
		this.emailCode = emailCode;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}

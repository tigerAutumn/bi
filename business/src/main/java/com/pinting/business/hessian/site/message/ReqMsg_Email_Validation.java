package com.pinting.business.hessian.site.message;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;

import com.pinting.core.hessian.msg.ReqMsg;
/**
 * @Project: business
 * @Title: ReqMsg_Email_Validation.java
 * @author Dong Wenkai
 * @date 2015-1-21 下午6:45:33
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class ReqMsg_Email_Validation extends ReqMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8281147900557955552L;
	@NotNull(message="验证码不能为空")
	private String emailCode;//用户输入验证码 	选填
	@NotNull(message="邮箱不能为空")
	@Email(message="邮箱格式错误")
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

package com.pinting.business.hessian.site.message;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;

import com.pinting.core.hessian.msg.ReqMsg;
/**
 * @Project: business
 * @Title: ReqMsg_Email_Generate.java
 * @author Dong Wenkai
 * @date 2015-1-21 下午6:45:30
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class ReqMsg_Email_Generate extends ReqMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = -887795972151480150L;
	@NotNull(message="邮箱不能为空")
	@Email(message="邮箱格式错误")
	private String email;//手机	 可选

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}

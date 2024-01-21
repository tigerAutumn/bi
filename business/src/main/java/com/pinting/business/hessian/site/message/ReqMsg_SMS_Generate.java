package com.pinting.business.hessian.site.message;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.pinting.core.hessian.msg.ReqMsg;
/**
 * @Project: business
 * @Title: ReqMsg_SMS_Generate.java
 * @author Dong Wenkai
 * @date 2015-1-21 下午6:46:09
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class ReqMsg_SMS_Generate extends ReqMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = -887795972151480150L;
	@NotNull(message="手机号不能为空")
	@Pattern(regexp="^[1]\\d{10}$", message="手机格式错误")
	private String mobile;//手机	 可选

	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
}

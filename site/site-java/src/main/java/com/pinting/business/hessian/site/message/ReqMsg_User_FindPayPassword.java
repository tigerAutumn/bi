package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;
/**
 * @Project: site-java
 * @Title: ReqMsg_User_FindPayPassword.java
 * @author Dong Wenkai
 * @date 2015-1-21 下午6:41:52
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class ReqMsg_User_FindPayPassword extends ReqMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7135059085921919880L;
	private String mobile;//手机	 可选
	private String mobileCode;//用户输入验证码 	选填
	private String password;
	private Integer userId;

	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
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
	public String getMobileCode() {
		return mobileCode;
	}
	public void setMobileCode(String mobileCode) {
		this.mobileCode = mobileCode;
	}
	
}

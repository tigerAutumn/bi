package com.pinting.business.hessian.site.message;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.pinting.core.hessian.msg.ReqMsg;
/**
 * @Project: business
 * @Title: ReqMsg_User_FindPayPassword.java
 * @author Dong Wenkai
 * @date 2015-1-21 下午6:46:18
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class ReqMsg_User_FindPayPassword extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1102745596402466115L;
	private String mobile;//手机	 可选
	private String mobileCode;//用户输入验证码 	选填
	@NotNull(message="交易密码不能为空")
    @Pattern(regexp="^[0-9a-zA-Z_]{6,16}$",message="交易密码格式错误")
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

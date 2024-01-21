package com.pinting.business.hessian.site.message;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_User_FindPassword extends ReqMsg {


	

	/**
	 * 
	 */
	private static final long serialVersionUID = 4666363447149578997L;


	private Integer userId;//	用户编号	必填		
	private String nick;//	用户名	必填			
	private String mobile;//	手机号		
	private String email;//	邮箱		
	@NotNull(message="密码不能为空")
    @Pattern(regexp="^[0-9a-zA-Z_]{6,16}$",message="密码格式错误")
	private String password ;//密码
	private String mobileCode;//手机验证码
	
	public String getMobileCode() {
		return mobileCode;
	}
	public void setMobileCode(String mobileCode) {
		this.mobileCode = mobileCode;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	
	
	
}

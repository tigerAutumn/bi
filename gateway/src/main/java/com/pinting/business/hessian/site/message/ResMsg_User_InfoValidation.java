package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_User_InfoValidation extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6323138753450213474L;



	
	private Integer userId;//	用户编号		
	private String nick;//	用户名		
	private String mobile;//	手机号		
	private String email;//	邮箱		
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

	
}

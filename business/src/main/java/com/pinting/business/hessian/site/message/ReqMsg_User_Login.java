package com.pinting.business.hessian.site.message;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_User_Login extends ReqMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6392738155106542291L;
	//用户名或手机号都以nick字段传送过来
	@NotNull(message="帐号不能为空")
	@Pattern(regexp="^([\\u4e00-\\u9fa5]+|([0-9a-zA-Z_]+\\s?)+){1,16}$",message="帐号格式错误")
	private String nick;
	@NotNull(message="密码不能为空")
	@Pattern(regexp="^[0-9a-zA-Z_]{6,16}$",message="密码格式错误")
	private String password;
	private String mobile;
	
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}

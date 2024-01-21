package com.pinting.business.hessian.manage.message;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_BsUser_BsUserSave extends ReqMsg {


	/**
	 * 
	 */
	private static final long serialVersionUID = -8730823187771159729L;
	private Integer userId;
	@NotNull(message="帐号不能为空")
	@Pattern(regexp="^(?![1][34587]\\d{9})(([\\u4e00-\\u9fa5]+|([0-9a-zA-Z_]+\\s?)+){1,16})",message="帐号格式错误")
	private String nick;
	private String userName;
	@NotNull(message="手机号不能为空")
	@Pattern(regexp="^[1][34587]\\d{9}$",message="手机格式错误")
	private String mobile;
	private String email;
	private String idCard;
	private Integer status;
	private String controlName;
	
	public String getControlName() {
		return controlName;
	}

	public void setControlName(String controlName) {
		this.controlName = controlName;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
}

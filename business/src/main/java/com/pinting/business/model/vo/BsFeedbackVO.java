package com.pinting.business.model.vo;

import com.pinting.business.model.BsFeedback;

public class BsFeedbackVO extends BsFeedback {
	private String nick;
	private String userName;
	private String userPhone;
	
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
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
}

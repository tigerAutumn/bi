package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_User_BankListQuery extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3730732503779178959L;

	private Integer userId;
	private String nick;
	
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
}

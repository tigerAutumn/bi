package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

@Deprecated
public class ReqMsg_Bonus_WithdrawInfoQuery extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5909150514661415226L;
	
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

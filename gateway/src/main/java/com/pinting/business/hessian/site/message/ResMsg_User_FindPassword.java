package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_User_FindPassword extends ResMsg {


	
	private static final long serialVersionUID = 9140823934295062777L;
	/**
	 * 
	 */




	
	private String nick;//	用户名	必填			
	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	
}

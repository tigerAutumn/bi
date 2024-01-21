package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_BsUser_SmsUser extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7079547768628487035L;
	
    public String msg;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}

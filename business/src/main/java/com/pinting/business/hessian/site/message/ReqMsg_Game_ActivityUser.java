package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_Game_ActivityUser extends ReqMsg {


	/**
	 * 
	 */
	private static final long serialVersionUID = -2155626185014981104L;
	private String openId;
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
	
	
}

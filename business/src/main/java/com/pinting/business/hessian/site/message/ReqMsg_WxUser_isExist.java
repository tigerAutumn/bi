package com.pinting.business.hessian.site.message;

import java.util.Date;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_WxUser_isExist extends ReqMsg {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 6142681615415528087L;

	private String openId;

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
	
	
}

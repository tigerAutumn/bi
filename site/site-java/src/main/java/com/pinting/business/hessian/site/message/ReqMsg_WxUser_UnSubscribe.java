package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_WxUser_UnSubscribe extends ReqMsg {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1956557109120413067L;
	private String openId;
	private String userId;
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
}

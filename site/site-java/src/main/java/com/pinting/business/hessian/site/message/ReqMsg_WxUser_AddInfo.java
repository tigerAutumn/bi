package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_WxUser_AddInfo extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2397166666665770062L;
	
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

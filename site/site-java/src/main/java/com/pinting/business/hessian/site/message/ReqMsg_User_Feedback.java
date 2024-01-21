package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_User_Feedback extends ReqMsg {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3575515420261286534L;
	
	private Integer userId;
	private String info;
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
}

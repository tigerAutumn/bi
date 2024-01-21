package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_Game_Start extends ReqMsg {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 57005468589309362L;
	private String mobile;
	private Integer activityUser;
	
	
	public Integer getActivityUser() {
		return activityUser;
	}
	public void setActivityUser(Integer activityUser) {
		this.activityUser = activityUser;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
}

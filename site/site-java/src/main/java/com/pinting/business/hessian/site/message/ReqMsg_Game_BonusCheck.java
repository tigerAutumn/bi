package com.pinting.business.hessian.site.message;

import java.util.Date;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_Game_BonusCheck extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3331778718845419027L;
	private String mobile;
	private String openId;
	private String activityUserId;
	
	public String getActivityUserId() {
		return activityUserId;
	}
	public void setActivityUserId(String activityUserId) {
		this.activityUserId = activityUserId;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	


}

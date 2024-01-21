package com.pinting.business.hessian.site.message;

import java.util.Date;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_Game_Activity extends ReqMsg {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6253333981084939592L;
	private String mobile;
	private Integer activityUser;
	private Integer helperWxUserId;
	private String wxUserId;
	
	
	public String getWxUserId() {
		return wxUserId;
	}
	public void setWxUserId(String wxUserId) {
		this.wxUserId = wxUserId;
	}
	public Integer getHelperWxUserId() {
		return helperWxUserId;
	}
	public void setHelperWxUserId(Integer helperWxUserId) {
		this.helperWxUserId = helperWxUserId;
	}
	
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

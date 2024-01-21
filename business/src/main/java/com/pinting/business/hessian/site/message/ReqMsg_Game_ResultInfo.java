package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_Game_ResultInfo extends ReqMsg {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6896319039896297445L;

	private Integer activityId;

	private String mobile;
	private Integer gold;
	private Integer activityUserId;
	private Integer helperWxUserId;
	public Integer getActivityId() {
		return activityId;
	}
	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Integer getGold() {
		return gold;
	}
	public void setGold(Integer gold) {
		this.gold = gold;
	}
	public Integer getActivityUserId() {
		return activityUserId;
	}
	public void setActivityUserId(Integer activityUserId) {
		this.activityUserId = activityUserId;
	}
	public Integer getHelperWxUserId() {
		return helperWxUserId;
	}
	public void setHelperWxUserId(Integer helperWxUserId) {
		this.helperWxUserId = helperWxUserId;
	}
	


	
}

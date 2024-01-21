package com.pinting.business.hessian.site.message;

import java.util.Date;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_Game_Bonus extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 567538606104841624L;
	private Integer activityId;
	private Integer activityUserId;
	private Integer gold;
	private Integer helperWxUserId;

	private Date startTime;

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public Integer getActivityUserId() {
		return activityUserId;
	}

	public void setActivityUserId(Integer activityUserId) {
		this.activityUserId = activityUserId;
	}

	public Integer getGold() {
		return gold;
	}

	public void setGold(Integer gold) {
		this.gold = gold;
	}

	public Integer getHelperWxUserId() {
		return helperWxUserId;
	}

	public void setHelperWxUserId(Integer helperWxUserId) {
		this.helperWxUserId = helperWxUserId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	

}

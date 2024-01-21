package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_ActivityLuckyDraw_GetActiveAward extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6630166437013941950L;

	/*活动表主键id*/
	private Integer activityId;
	/*用户id*/
	private Integer userId;

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}

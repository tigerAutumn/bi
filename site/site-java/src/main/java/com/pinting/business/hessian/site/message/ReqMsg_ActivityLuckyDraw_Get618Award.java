package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 618活动用户抽奖入参
 * @author bianyatian
 * @2016-6-6 下午3:27:15
 */
public class ReqMsg_ActivityLuckyDraw_Get618Award extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8516435520425926848L;

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

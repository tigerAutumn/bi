package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 活动主页入参
 * @author bianyatian
 * @2016-10-29 下午3:53:30
 */
public class ReqMsg_ActivityLuckyDraw_ActivityIndex extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6934406944382489593L;
	
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

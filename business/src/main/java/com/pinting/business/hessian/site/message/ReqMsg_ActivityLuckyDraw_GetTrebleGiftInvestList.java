package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_ActivityLuckyDraw_GetTrebleGiftInvestList extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 36282630691548520L;
	
	/*活动表主键id*/
	private Integer activityId;
	
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

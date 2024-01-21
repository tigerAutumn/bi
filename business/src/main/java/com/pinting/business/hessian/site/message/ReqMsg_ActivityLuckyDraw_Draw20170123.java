package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 20170123全民抽奖入参
 * @author bianyatian
 * @2017-1-11 下午7:57:17
 */
public class ReqMsg_ActivityLuckyDraw_Draw20170123 extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4772585019440744897L;
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

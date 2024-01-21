package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 
 * 查询618活动主页入参
 * @author bianyatian
 * @2016-6-6 下午3:27:24
 */
public class ReqMsg_ActivityLuckyDraw_Activity618Index extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1324225870183637754L;
	
	/*活动表主键id*/
	private Integer activityId;
	/*用户id*/
	private Integer userId;
	/*中奖条数，null则表示查询所有*/
	private Integer luckyLimitNum; 
	

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

	public Integer getLuckyLimitNum() {
		return luckyLimitNum;
	}

	public void setLuckyLimitNum(Integer luckyLimitNum) {
		this.luckyLimitNum = luckyLimitNum;
	}

}

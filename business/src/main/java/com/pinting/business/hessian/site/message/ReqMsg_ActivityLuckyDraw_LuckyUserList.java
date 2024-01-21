package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 查询某次活动中奖列表
 * @author bianyatian
 * @2017-1-10 下午7:31:22
 */
public class ReqMsg_ActivityLuckyDraw_LuckyUserList extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7591476363743803762L;
	
	/*活动表主键id*/
	private Integer activityId;
	
	/*中奖条数，null则表示查询所有*/
	private Integer luckyLimitNum;

	public Integer getLuckyLimitNum() {
		return luckyLimitNum;
	}

	public void setLuckyLimitNum(Integer luckyLimitNum) {
		this.luckyLimitNum = luckyLimitNum;
	}

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}
}

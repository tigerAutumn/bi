package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 618活动用户中奖列表入参
 * @author bianyatian
 * @2016-6-7 下午5:19:31
 */
public class ReqMsg_ActivityLuckyDraw_Get618UserLuckyList extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1504670084024516903L;
	
	/*活动id*/
	private Integer activityId;
	/*用户id*/
	private Integer userId;
	/*开始页数*/
	private Integer startPage;
	/*每页条数*/
	private Integer pageSize;
	
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

	public Integer getStartPage() {
		return startPage;
	}

	public void setStartPage(Integer startPage) {
		this.startPage = startPage;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

}

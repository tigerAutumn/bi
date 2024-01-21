package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 
 * @author zhangpeng
 * @date 2018/05/08
 */
public class ResMsg_WeekHayActivity_CheckWeekhayStatus extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7932152926783051270L;

	private String displayTime; //活动时间阶段，用来区分跳转展示页和活动页的时间
	private Integer activityStartTime; //活动开始时间，用来区分活动开始与否
	
	public String getDisplayTime() {
		return displayTime;
	}
	public void setDisplayTime(String displayTime) {
		this.displayTime = displayTime;
	}
	public Integer getActivityStartTime() {
		return activityStartTime;
	}
	public void setActivityStartTime(Integer activityStartTime) {
		this.activityStartTime = activityStartTime;
	}
	
}

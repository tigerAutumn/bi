package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 
 * @author zhangpeng
 * @date 2018/05/08
 */
public class ReqMsg_WeekHayActivity_CheckWeekhayStatus extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -886315499417837300L;

	private String activityType;

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}
	
}

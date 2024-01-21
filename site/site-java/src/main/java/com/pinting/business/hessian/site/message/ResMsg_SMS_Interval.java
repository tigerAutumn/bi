package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_SMS_Interval extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5537513931863025997L;
	private Integer interval;
	public Integer getInterval() {
		return interval;
	}
	public void setInterval(Integer interval) {
		this.interval = interval;
	}
}

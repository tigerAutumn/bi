package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_ActivityLuckyDraw_ActivityTime extends ReqMsg {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -672567913529989014L;
	/*活动表主键id*/
	private Integer activityId;
	/*时间样式，例：yyyy-MM-dd/yyyy-MM-dd HH:mm:ss*/
	private String format;
	
	public Integer getActivityId() {
		return activityId;
	}
	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
}

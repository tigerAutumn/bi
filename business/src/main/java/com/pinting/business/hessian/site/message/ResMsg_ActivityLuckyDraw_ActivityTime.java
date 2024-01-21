package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_ActivityLuckyDraw_ActivityTime extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8043989143112050302L;
	
	/*活动开始时间 yyyy-MM-dd HH:mm:ss*/
	private String startTime;
	/*活动结束时间 yyyy-MM-dd HH:mm:ss*/
	private String endTime;
	/*是否开始，noStart-未开始，end-已结束，ing进行中*/
	private String isStart;
	
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getIsStart() {
		return isStart;
	}
	public void setIsStart(String isStart) {
		this.isStart = isStart;
	}
	

}

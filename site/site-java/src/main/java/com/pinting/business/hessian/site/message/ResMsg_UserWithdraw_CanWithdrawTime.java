package com.pinting.business.hessian.site.message;


import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_UserWithdraw_CanWithdrawTime extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2949982323247873619L;
	
	private String begin;
	private String end;
	private String beginTime;
	private String endTime;
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getBegin() {
		return begin;
	}
	public void setBegin(String begin) {
		this.begin = begin;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	

}

package com.pinting.business.model.vo;

import java.util.Date;

import com.pinting.business.model.BsCashSchedule30;

public class BsCashSchedule30VO extends BsCashSchedule30{
	
	private Date beginTime;
	
	private Date overTime;

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getOverTime() {
		return overTime;
	}

	public void setOverTime(Date overTime) {
		this.overTime = overTime;
	}
	
	
}

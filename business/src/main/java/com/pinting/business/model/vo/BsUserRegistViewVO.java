package com.pinting.business.model.vo;

import java.util.Date;

import com.pinting.business.model.BsUserRegistView;

public class BsUserRegistViewVO extends BsUserRegistView{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6571775979883815932L;

	private Date startTime;
	
	private Date endTime;

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
}

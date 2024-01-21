package com.pinting.business.model.vo;

import java.util.Date;

import com.pinting.business.model.BsUserInvestView;

public class BsUserInvestViewVO extends BsUserInvestView{

	/**
	 * 序列化编号
	 */
	private static final long serialVersionUID = 1524210087220282929L;
	
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

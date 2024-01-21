package com.pinting.business.model.vo;

import java.util.Date;

import com.pinting.business.model.BsProfitLoss;

public class BsProfitLossVO extends BsProfitLoss{
	
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

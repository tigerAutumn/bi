package com.pinting.business.model.vo;

import java.util.Date;

import com.pinting.business.model.BsSmsRecordJnl;

public class BsSmsRecordJnlVO extends BsSmsRecordJnl{

	private String platformsCode;
	
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

	public String getPlatformsCode() {
		return platformsCode;
	}

	public void setPlatformsCode(String platformsCode) {
		this.platformsCode = platformsCode;
	}
}

package com.pinting.business.model.vo;

import java.util.Date;

import com.pinting.business.model.BsAppActive;

public class AppActiveVO extends BsAppActive{
	/**
	 * 序列化编号
	 */
	private static final long serialVersionUID = 524500632689892631L;
	
	private String activeStatus;
	
	private String sActiveStatus;
	
	private Date sStartTime;
	
	private Date eEndTime;
	
	public String getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}

	public String getsActiveStatus() {
		return sActiveStatus;
	}

	public void setsActiveStatus(String sActiveStatus) {
		this.sActiveStatus = sActiveStatus;
	}

	public Date getsStartTime() {
		return sStartTime;
	}

	public void setsStartTime(Date sStartTime) {
		this.sStartTime = sStartTime;
	}

	public Date geteEndTime() {
		return eEndTime;
	}

	public void seteEndTime(Date eEndTime) {
		this.eEndTime = eEndTime;
	}
}

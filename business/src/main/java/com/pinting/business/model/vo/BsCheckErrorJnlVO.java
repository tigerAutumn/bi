package com.pinting.business.model.vo;

import java.util.Date;

import com.pinting.business.model.BsCheckErrorJnl;

public class BsCheckErrorJnlVO extends BsCheckErrorJnl {
	
	private String mUserName; //管理台处理用户，姓名
	
	private String bsUserName; //注册用户姓名

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

	public String getmUserName() {
		return mUserName;
	}

	public void setmUserName(String mUserName) {
		this.mUserName = mUserName;
	}

	public String getBsUserName() {
		return bsUserName;
	}

	public void setBsUserName(String bsUserName) {
		this.bsUserName = bsUserName;
	}
	
	
	
}

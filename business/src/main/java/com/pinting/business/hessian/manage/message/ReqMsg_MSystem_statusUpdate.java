package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_MSystem_statusUpdate extends ReqMsg {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6735263649916946983L;
	private Integer statusValue;
	private Integer mUserId;
	private String statusKey;
	public Integer getStatusValue() {
		return statusValue;
	}
	public void setStatusValue(Integer statusValue) {
		this.statusValue = statusValue;
	}
	public Integer getmUserId() {
		return mUserId;
	}
	public void setmUserId(Integer mUserId) {
		this.mUserId = mUserId;
	}
	public String getStatusKey() {
		return statusKey;
	}
	public void setStatusKey(String statusKey) {
		this.statusKey = statusKey;
	}


	
}

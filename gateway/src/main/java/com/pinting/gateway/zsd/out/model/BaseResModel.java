package com.pinting.gateway.zsd.out.model;

import java.util.Date;

/**
 * 
 * @project gateway
 * @title BaseResModel.java
 * @author Dragon & cat
 * @date 2017-9-1
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
public class BaseResModel {
	
	private String errorCode;
	private String errorMsg;
	private Date responseTime;
	
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public Date getResponseTime() {
		return responseTime;
	}
	public void setResponseTime(Date responseTime) {
		this.responseTime = responseTime;
	}
	@Override
	public String toString() {
		return "BaseResModel [errorCode=" + errorCode + ", errorMsg="
				+ errorMsg + ", responseTime=" + responseTime + "]";
	}
	
}

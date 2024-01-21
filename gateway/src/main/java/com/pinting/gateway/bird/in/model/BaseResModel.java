package com.pinting.gateway.bird.in.model;

import java.util.Date;

/**
 * @Project: gateway
 * @Title: BaseResModel.java
 * @author Zhou Changzai
 * @date 2015-2-10 下午8:05:30
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class BaseResModel {
	
	private String errorCode;
	private String errorMsg;
	private String responseTime;
	
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

	public String getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(String responseTime) {
		this.responseTime = responseTime;
	}

	@Override
	public String toString() {
		return "BaseResModel [errorCode=" + errorCode + ", errorMsg="
				+ errorMsg + ", responseTime=" + responseTime + "]";
	}
	
}

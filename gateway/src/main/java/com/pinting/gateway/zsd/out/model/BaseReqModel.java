package com.pinting.gateway.zsd.out.model;

/**
 * 
 * @project gateway
 * @title BaseReqModel.java
 * @author Dragon & cat
 * @date 2017-9-1
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
public class BaseReqModel {
	private String orgCode;
	private String channal;
	private String token;
	private String requestTime;
	private String clientKey;
	
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getChannal() {
		return channal;
	}
	public void setChannal(String channal) {
		this.channal = channal;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}

	public String getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(String requestTime) {
		this.requestTime = requestTime;
	}

	public String getClientKey() {
		return clientKey;
	}
	public void setClientKey(String clientKey) {
		this.clientKey = clientKey;
	}
	@Override
	public String toString() {
		return "BaseReqModel [token=" + token + ", requestTime=" + requestTime
				+ ", clientKey=" + clientKey + "]";
	}
	
}

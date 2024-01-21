package com.pinting.gateway.zsd.in.model;

import org.hibernate.validator.constraints.NotEmpty;

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
	@NotEmpty(message = "token不能为空")
	private String token;
	@NotEmpty(message = "请求时间不能为空")
	private String requestTime;
	@NotEmpty(message = "客户端编码错误")
	private String clientKey;
	
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

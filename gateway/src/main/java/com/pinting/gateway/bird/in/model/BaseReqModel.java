package com.pinting.gateway.bird.in.model;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 
 * @Project: gateway
 * @Title: BaseReqModel.java
 * @author dingpf
 * @date 2016-8-10 上午10:16:08
 * @Copyright: 2016 BiGangWan.com Inc. All rights reserved.
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

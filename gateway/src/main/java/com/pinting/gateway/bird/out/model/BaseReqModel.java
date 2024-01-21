package com.pinting.gateway.bird.out.model;

import java.util.Date;

/**
 * 
 * @Project: gateway
 * @Title: BaseReqModel.java
 * @author dingpf
 * @date 2016-8-10 上午10:16:08
 * @Copyright: 2016 BiGangWan.com Inc. All rights reserved.
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

package com.pinting.manage.model;

import java.util.Date;

/**
 * @Project: gateway
 * @Title: BaseModel.java
 * @author Zhou Changzai
 * @date 2015-2-10 下午8:03:27
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class BaseReqModel {
	private String token;
	private String transCode;
	private Date requestTime;
	private String hash;
	private String jsonList;//用作保存 json数组字符串
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getTransCode() {
		return transCode;
	}
	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}
	public Date getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}
	public String getJsonList() {
		return jsonList;
	}
	public void setJsonList(String jsonList) {
		this.jsonList = jsonList;
	}
	@Override
	public String toString() {
		return "BaseReqModel [token=" + token + ", transCode=" + transCode
				+ ", requestTime=" + requestTime + ", hash=" + hash
				+ ", jsonList=" + jsonList + "]";
	}
	
}

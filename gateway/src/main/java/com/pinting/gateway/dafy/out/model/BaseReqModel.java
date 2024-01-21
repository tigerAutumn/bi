package com.pinting.gateway.dafy.out.model;

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
	/**
	 * 加解密key
	 */
	private String outDESKey;
	private String clientKey;
	private String clientSecret;
	
	/**
	 * 购买产品地址
	 */
	private String url;

	
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
	public String getOutDESKey() {
		return outDESKey;
	}
	public void setOutDESKey(String outDESKey) {
		this.outDESKey = outDESKey;
	}
	public String getClientKey() {
		return clientKey;
	}
	public void setClientKey(String clientKey) {
		this.clientKey = clientKey;
	}
	public String getClientSecret() {
		return clientSecret;
	}
	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}

package com.pinting.gateway.qidian.in.model;

/**
 * @Project: gateway
 * @Title: LoginReqModel.java
 * @author Zhou Changzai
 * @date 2015-2-10 下午8:17:09
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class LoginReqModel extends BaseReqModel {
	private String clientKey;
	private String clientSecret;
	
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
}

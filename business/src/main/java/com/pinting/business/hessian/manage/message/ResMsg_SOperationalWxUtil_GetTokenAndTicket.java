package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 *
 * @author SHENGUOPING
 * @date  2018年6月19日 下午4:01:03
 */
public class ResMsg_SOperationalWxUtil_GetTokenAndTicket extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6245416108023759111L;
	
	private String accessToken;
	
	private String jsapiTicket;
	
	private String appid;
	
	private String secret;
	
	private String token;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getJsapiTicket() {
		return jsapiTicket;
	}

	public void setJsapiTicket(String jsapiTicket) {
		this.jsapiTicket = jsapiTicket;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
}

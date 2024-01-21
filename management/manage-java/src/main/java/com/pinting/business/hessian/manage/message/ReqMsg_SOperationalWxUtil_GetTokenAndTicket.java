package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 *
 * @author SHENGUOPING
 * @date  2018年6月19日 下午3:56:42
 */
public class ReqMsg_SOperationalWxUtil_GetTokenAndTicket extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3478780437666709496L;

	private String requestFlag;
	
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

	public String getRequestFlag() {
		return requestFlag;
	}

	public void setRequestFlag(String requestFlag) {
		this.requestFlag = requestFlag;
	}
	
}

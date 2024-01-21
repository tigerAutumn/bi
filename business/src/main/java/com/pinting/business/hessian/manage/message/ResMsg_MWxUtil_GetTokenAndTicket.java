package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_MWxUtil_GetTokenAndTicket extends ResMsg {

	/**
	 * 序列化编号
	 */
	private static final long serialVersionUID = -8905790670566613499L;
	
	private String accessToken;
	
	private String jsapiTicket;

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
}

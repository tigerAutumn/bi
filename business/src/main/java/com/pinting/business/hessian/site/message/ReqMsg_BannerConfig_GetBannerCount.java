package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_BannerConfig_GetBannerCount extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6614622922977725752L;

	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}

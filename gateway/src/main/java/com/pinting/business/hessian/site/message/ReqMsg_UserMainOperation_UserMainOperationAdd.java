/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 *
 */
public class ReqMsg_UserMainOperation_UserMainOperationAdd extends ReqMsg {

    private static final long serialVersionUID = 5251287360520113969L;

    private Integer userId;
    
    private String url;
    
    private String srcIp;
    
    private String srcAgent;
    
    private String referer;

	private String mobile;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSrcIp() {
		return srcIp;
	}

	public void setSrcIp(String srcIp) {
		this.srcIp = srcIp;
	}

	public String getSrcAgent() {
		return srcAgent;
	}

	public void setSrcAgent(String srcAgent) {
		this.srcAgent = srcAgent;
	}

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}
}

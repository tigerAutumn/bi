/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 
 * @author yanwl
 * @version $Id: ReqMsg_UserMainOperation_UserMainOperationAdd.java, v 0.1 2016-3-8 下午14:50:42 yanwl Exp $
 */
public class ReqMsg_UserMainOperation_UserMainOperationAdd extends ReqMsg {

    /**  
     * 序列化编号
     */
    private static final long serialVersionUID = 5251287360520113969L;

    private Integer userId;
    
    private String url;
    
    private String srcIp;
    
    private String srcAgent;
    
    private String referer;

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

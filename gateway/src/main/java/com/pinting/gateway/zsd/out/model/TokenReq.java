package com.pinting.gateway.zsd.out.model;

/**
 * 
 * @project gateway
 * @title TokenReq.java
 * @author Dragon & cat
 * @date 2017-9-1
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
public class TokenReq extends BaseReqModel{

    private String org_code;
    
    private String org_secret;
    
	public String getOrg_code() {
		return org_code;
	}
	public void setOrg_code(String org_code) {
		this.org_code = org_code;
	}
	public String getOrg_secret() {
		return org_secret;
	}
	public void setOrg_secret(String org_secret) {
		this.org_secret = org_secret;
	}
}

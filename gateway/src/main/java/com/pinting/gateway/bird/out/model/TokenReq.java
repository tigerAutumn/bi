package com.pinting.gateway.bird.out.model;

/**
 * Created by 剑钊 on 2016/8/10.
 * 获取token
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

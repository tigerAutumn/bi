package com.pinting.gateway.zsd.out.model;

/**
 * 
 * @project gateway
 * @title TokenRes.java
 * @author Dragon & cat
 * @date 2017-9-1
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
public class TokenRes  extends BaseResModel {

    private String 	access_token;
    /*过期秒数*/
    private Integer expire_in ;
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public Integer getExpire_in() {
		return expire_in;
	}
	public void setExpire_in(Integer expire_in) {
		this.expire_in = expire_in;
	}
		
}

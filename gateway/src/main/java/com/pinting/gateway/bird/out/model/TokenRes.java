package com.pinting.gateway.bird.out.model;

/**
 * Created by 剑钊 on 2016/8/10.
 * 获取token
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

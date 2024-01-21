package com.pinting.open.pojo.request.asset;

import com.pinting.open.base.request.Request;
import com.pinting.open.base.util.Constants;
/**
 * 
 * @project open-base
 * @title ActivateDepAccountRequest.java
 * @author Dragon & cat
 * @date 2017-4-8
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description 存管业务改造   激活存管账户（ 我的资产）
 */
public class ActivateDepAccountRequest extends Request {
	/*用户ID*/
	private		Integer  	userId;
	/*手机验证码*/
	private		String  	mobileCode;
	
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getMobileCode() {
		return mobileCode;
	}

	public void setMobileCode(String mobileCode) {
		this.mobileCode = mobileCode;
	}

	@Override
	public String restApiUrl() {
		return Constants.BASE_REST_URL + "/mobile/asset/activate_dep_account";
	}

	@Override
	public String testApiUrl() {
		return Constants.BASE_TEST_URL + "/mobile/asset/activate_dep_account";
	}

}

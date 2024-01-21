package com.pinting.open.pojo.request.asset;

import com.pinting.open.base.request.Request;
import com.pinting.open.base.util.Constants;
/**
 * 
 * @project open-base
 * @title ActivatePageInfoRequest.java
 * @author Dragon & cat
 * @date 2017-4-8
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description 存管业务改造  获取激活页面数据（ 我的资产）
 * 
 */
public class ActivatePageInfoRequest extends Request {
	
	/*用户ID*/
	private		Integer 	userId;
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Override
	public String restApiUrl() {
		return Constants.BASE_REST_URL + "/mobile/asset/activate_page_info";
	}

	@Override
	public String testApiUrl() {
		return Constants.BASE_TEST_URL + "/mobile/asset/activate_page_info";
	}

}

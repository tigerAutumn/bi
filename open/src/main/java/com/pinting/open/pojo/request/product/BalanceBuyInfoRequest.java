package com.pinting.open.pojo.request.product;

import com.pinting.open.base.request.Request;
import com.pinting.open.base.util.Constants;
/**
 * 
 * @project open-base
 * @title BalanceBuyInfoRequest.java
 * @author Dragon & cat
 * @date 2017-4-14
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description  余额购买页面信息
 */
public class BalanceBuyInfoRequest extends Request {
	/*用户ID*/
	private		Integer		productId;
	/*用户ID*/
	private		Integer		userId;
	
	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Override
	public String restApiUrl() {
		return Constants.BASE_REST_URL + "/mobile/product/balanceBuyInfo";
	}

	@Override
	public String testApiUrl() {
		return Constants.BASE_TEST_URL + "/mobile/product/balanceBuyInfo";
	}

}

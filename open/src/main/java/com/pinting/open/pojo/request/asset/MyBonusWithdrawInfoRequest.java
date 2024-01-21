package com.pinting.open.pojo.request.asset;

import com.pinting.open.base.request.Request;
import com.pinting.open.base.util.Constants;
/**
 * 
 * @project open-base
 * @title MyBonusWithdrawInfoRequest.java
 * @author Dragon & cat
 * @date 2017-4-13
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
public class MyBonusWithdrawInfoRequest extends Request {
	/*用户ID*/
	private		Integer		userId;
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Override
	public String restApiUrl() {
		return Constants.BASE_REST_URL + "/mobile/asset/my_bonus_withdraw_info";
	}

	@Override
	public String testApiUrl() {
		return Constants.BASE_TEST_URL + "/mobile/asset/my_bonus_withdraw_info";
	}

}

package com.pinting.open.pojo.request.asset;

import com.pinting.open.base.request.Request;
import com.pinting.open.base.util.Constants;

public class BalanceRechargeRequest extends Request {

	
	private Integer userId;
	
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	
	@Override
	public String restApiUrl() {
		return Constants.BASE_REST_URL + "/mobile/asset/balance_recharge";
	}

	@Override
	public String testApiUrl() {
		return Constants.BASE_TEST_URL + "/mobile/asset/balance_recharge";
	}

}

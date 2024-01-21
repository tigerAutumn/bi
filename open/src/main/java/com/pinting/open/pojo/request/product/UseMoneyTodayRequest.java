package com.pinting.open.pojo.request.product;

import com.pinting.open.base.request.Request;
import com.pinting.open.base.util.Constants;

public class UseMoneyTodayRequest extends Request {

	private Integer bankId;
	
	private Integer userId;
	
	public Integer getBankId() {
		return bankId;
	}

	public void setBankId(Integer bankId) {
		this.bankId = bankId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Override
	public String restApiUrl() {
		return Constants.BASE_REST_URL + "/mobile/product/useMoneyToday";
	}

	@Override
	public String testApiUrl() {
		return Constants.BASE_TEST_URL + "/mobile/product/useMoneyToday";
	}

}

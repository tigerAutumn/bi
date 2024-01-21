package com.pinting.open.pojo.request.index;

import com.pinting.open.base.request.Request;
import com.pinting.open.base.util.Constants;

public class SendCodeByIDRequest extends Request {

	private Integer userId;
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Override
	public String restApiUrl() {
		return Constants.BASE_REST_URL + "/mobile/index/sendCodeByID";
	}

	@Override
	public String testApiUrl() {
		return Constants.BASE_TEST_URL + "/mobile/index/sendCodeByID";
	}

}

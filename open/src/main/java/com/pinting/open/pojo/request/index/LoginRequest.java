package com.pinting.open.pojo.request.index;

import com.pinting.open.base.request.Request;
import com.pinting.open.base.util.Constants;

public class LoginRequest extends Request {
	
	private String mobile;
	
	private String password;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String restApiUrl() {
		return Constants.BASE_REST_URL + "/mobile/index/login";
	}

	public String testApiUrl() {
		return Constants.BASE_TEST_URL + "/mobile/index/login";
	}

}

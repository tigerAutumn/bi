package com.pinting.open.pojo.request.asset;

import com.pinting.open.base.request.Request;
import com.pinting.open.base.util.Constants;

public class NewVersionRequest extends Request {
	
	private String appType;
	
//	private String appVersion;

//	public String getAppVersion() {
//		return appVersion;
//	}
//
//	public void setAppVersion(String appVersion) {
//		this.appVersion = appVersion;
//	}

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	@Override
	public String restApiUrl() {
		return Constants.BASE_REST_URL + "/mobile/more/newVersion";
	}

	@Override
	public String testApiUrl() {
		return Constants.BASE_TEST_URL + "/mobile/more/newVersion";
	}

}

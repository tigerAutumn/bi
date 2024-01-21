package com.pinting.open.pojo.request.index;

import com.pinting.open.base.request.Request;
import com.pinting.open.base.util.Constants;

public class ApplicationRequest extends Request {
	
	private Integer userId;
	
	private String applications;
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public String getApplications() {
		return applications;
	}

	public void setApplications(String applications) {
		this.applications = applications;
	}

	public String restApiUrl() {
        return Constants.BASE_REST_URL + "/mobile/index/application";
    }

    public String testApiUrl() {
        return Constants.BASE_TEST_URL + "/mobile/index/application";
    }
}

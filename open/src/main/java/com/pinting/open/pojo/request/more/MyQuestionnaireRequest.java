package com.pinting.open.pojo.request.more;

import com.pinting.open.base.request.Request;
import com.pinting.open.base.util.Constants;

public class MyQuestionnaireRequest extends Request {
	
	/**
	 * 用户ID
	 */
	private Integer userId;
	
	@Override
	public String restApiUrl() {
		return Constants.BASE_REST_URL + "/mobile/more/myQuestionnaire";
	}

	@Override
	public String testApiUrl() {
		return Constants.BASE_TEST_URL + "/mobile/more/myQuestionnaire";
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
}

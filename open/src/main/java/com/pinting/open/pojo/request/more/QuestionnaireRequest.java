package com.pinting.open.pojo.request.more;

import com.pinting.open.base.request.Request;
import com.pinting.open.base.util.Constants;

public class QuestionnaireRequest extends Request {
	
	/**
	 * 用户ID
	 */
	private Integer userId;
	/** 分数 */
	private Integer score;
	
	@Override
	public String restApiUrl() {
		return Constants.BASE_REST_URL + "/mobile/more/questionnaire";
	}

	@Override
	public String testApiUrl() {
		return Constants.BASE_TEST_URL + "/mobile/more/questionnaire";
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}
	
}

package com.pinting.open.pojo.request.more;

import com.pinting.open.base.request.Request;
import com.pinting.open.base.util.Constants;

/**
 *
 * @author SHENGP
 * @date  2017年7月5日 下午3:49:18
 */
public class QuestionnaireMoreRequest extends Request {

	/**
	 * 用户ID
	 */
	private Integer userId;
	
	@Override
	public String restApiUrl() {
		return Constants.BASE_REST_URL + "/mobile/more/questionnaireMore";
	}

	@Override
	public String testApiUrl() {
		return Constants.BASE_TEST_URL + "/mobile/more/questionnaireMore";
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
}

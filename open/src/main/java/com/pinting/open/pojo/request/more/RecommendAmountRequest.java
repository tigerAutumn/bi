package com.pinting.open.pojo.request.more;

import com.pinting.open.base.request.Request;
import com.pinting.open.base.util.Constants;

public class RecommendAmountRequest  extends Request {

	private Integer userId;
	
	@Override
    public String restApiUrl() {
        return Constants.BASE_REST_URL + "/mobile/more/recommendAmount";
    }

    @Override
    public String testApiUrl() {
        return Constants.BASE_TEST_URL + "/mobile/more/recommendAmount";
    }

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}

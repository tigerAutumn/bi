package com.pinting.open.pojo.request.asset;

import com.pinting.open.base.request.Request;
import com.pinting.open.base.util.Constants;

public class LoadMatchTimeRequest extends Request {

	@Override
	public String restApiUrl() {
		return Constants.BASE_REST_URL + "/mobile/asset/loadMatchTime";
	}

	@Override
	public String testApiUrl() {
		return Constants.BASE_TEST_URL + "/mobile/asset/loadMatchTime";
	}

}

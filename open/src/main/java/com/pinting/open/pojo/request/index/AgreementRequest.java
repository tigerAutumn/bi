package com.pinting.open.pojo.request.index;

import com.pinting.open.base.request.Request;
import com.pinting.open.base.util.Constants;

public class AgreementRequest extends Request {

	@Override
	public String restApiUrl() {
		return Constants.BASE_REST_URL + "/mobile/index/agreement";
	}

	@Override
	public String testApiUrl() {
		return Constants.BASE_TEST_URL + "/mobile/index/agreement";
	}

}

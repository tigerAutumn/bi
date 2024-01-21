package com.pinting.open.pojo.request.product;

import com.pinting.open.base.request.Request;
import com.pinting.open.base.util.Constants;

public class BaofooBankListRequest extends Request {

	@Override
	public String restApiUrl() {
		return Constants.BASE_REST_URL + "/mobile/product/baofooBankList";
	}

	@Override
	public String testApiUrl() {
		return Constants.BASE_TEST_URL + "/mobile/product/baofooBankList";
	}

}

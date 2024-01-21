package com.pinting.open.pojo.request.index;

import com.pinting.open.base.request.Request;
import com.pinting.open.base.util.Constants;
/**
 * APP启动页Request
 * @author Dragon & cat
 * @date 2017-3-16
 */
public class AppStartRequest extends Request {
	
	
	
	@Override
	public String restApiUrl() {
		return Constants.BASE_REST_URL + "/mobile/index/appStart";
	}

	@Override
	public String testApiUrl() {
		return Constants.BASE_TEST_URL + "/mobile/index/appStart";
	}

}

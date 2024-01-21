package com.pinting.open.pojo.request.asset;

import com.pinting.open.base.request.Request;
import com.pinting.open.base.util.Constants;

public class TradingDetailRequest extends Request {
	
	private Integer userId;
	private Integer pageIndex;  //第*页
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	@Override
	public String restApiUrl() {
		return Constants.BASE_REST_URL + "/mobile/asset/trading_detail";
	}

	@Override
	public String testApiUrl() {
		return Constants.BASE_TEST_URL + "/mobile/asset/trading_detail";
	}

}

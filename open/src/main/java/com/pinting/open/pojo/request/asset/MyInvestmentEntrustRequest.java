package com.pinting.open.pojo.request.asset;

import com.pinting.open.base.request.Request;
import com.pinting.open.base.util.Constants;

/**
 * 我的投资_明细
 * @author Dragon & cat
 * @date 2016-8-30
 */
public class MyInvestmentEntrustRequest extends Request {
	
	private Integer userId; //用户ID
	
	private Integer pageIndexEntrust; //委托计划页码
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}


	public Integer getPageIndexEntrust() {
		return pageIndexEntrust;
	}

	public void setPageIndexEntrust(Integer pageIndexEntrust) {
		this.pageIndexEntrust = pageIndexEntrust;
	}

	@Override
	public String restApiUrl() {
		return Constants.BASE_REST_URL + "/mobile/asset/my_investment_entrust";
	}

	@Override
	public String testApiUrl() {
		return Constants.BASE_TEST_URL + "/mobile/asset/my_investment_entrust";
	}

}

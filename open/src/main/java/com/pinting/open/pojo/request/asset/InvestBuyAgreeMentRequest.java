package com.pinting.open.pojo.request.asset;

import com.pinting.open.base.request.Request;
import com.pinting.open.base.util.Constants;

public class InvestBuyAgreeMentRequest extends Request {
	
	private Integer userId;//用户编号	必填
	
	private Integer subAccountId;  //子账户ID
	
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	
	

	public Integer getSubAccountId() {
		return subAccountId;
	}

	public void setSubAccountId(Integer subAccountId) {
		this.subAccountId = subAccountId;
	}

	@Override
	public String restApiUrl() {
		return Constants.BASE_REST_URL + "/mobile/asset/invest_buy_agree_ment";
	}

	@Override
	public String testApiUrl() {
		return Constants.BASE_TEST_URL + "/mobile/asset/invest_buy_agree_ment";
	}


}

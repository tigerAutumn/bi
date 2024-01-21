package com.pinting.open.pojo.request.asset;

import com.pinting.open.base.request.Request;
import com.pinting.open.base.util.Constants;

/**
 * 解绑前校验Request
 * @project open
 * @author bianyatian
 * @2018-5-23 下午4:34:09
 */
public class BankCardUnbindCheckRequest extends Request {

	//用户ID
	private Integer userId;
	
	//银行卡id
	private Integer bankCardId;
	
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getBankCardId() {
		return bankCardId;
	}

	public void setBankCardId(Integer bankCardId) {
		this.bankCardId = bankCardId;
	}

	@Override
	public String restApiUrl() {
		return Constants.BASE_REST_URL + "/mobile/asset/unbindCheck";
	}

	@Override
	public String testApiUrl() {
		return Constants.BASE_TEST_URL + "/mobile/asset/unbindCheck";
	}

}

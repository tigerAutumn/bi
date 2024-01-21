package com.pinting.open.pojo.request.asset;

import com.pinting.open.base.request.Request;
import com.pinting.open.base.util.Constants;

public class SafePayPasswordChangeRequest extends Request {

	private Integer userId;   //用户ID
	private String oldPayPassWord;   //原支付密码
	private String newPayPassWord;   //新支付密码
	
	
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getOldPayPassWord() {
		return oldPayPassWord;
	}

	public void setOldPayPassWord(String oldPayPassWord) {
		this.oldPayPassWord = oldPayPassWord;
	}

	public String getNewPayPassWord() {
		return newPayPassWord;
	}

	public void setNewPayPassWord(String newPayPassWord) {
		this.newPayPassWord = newPayPassWord;
	}

	@Override
	public String restApiUrl() {
		return Constants.BASE_REST_URL + "/mobile/asset/safe_pay_password_change";
	}

	@Override
	public String testApiUrl() {
		return Constants.BASE_TEST_URL + "/mobile/asset/safe_pay_password_change";
	}


}

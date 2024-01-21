package com.pinting.open.pojo.request.asset;

import com.pinting.open.base.request.Request;
import com.pinting.open.base.util.Constants;
/**
 * 正式绑卡Request
 * @author Dragon & cat
 * @date 2016-8-29
 */
public class BankCardBindRequest extends Request {
	/**
	 * 预绑卡订单号
	 */
	private String orderNo;
	/**
	 * 手机验证码
	 */
	private String smsCode;
	/**
	 * 用户ID
	 */
	private Integer userId;
	
	
	
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Override
	public String restApiUrl() {
		return Constants.BASE_REST_URL + "/mobile/asset/bindCard";
	}

	@Override
	public String testApiUrl() {
		return Constants.BASE_TEST_URL + "/mobile/asset/bindCard";
	}

}

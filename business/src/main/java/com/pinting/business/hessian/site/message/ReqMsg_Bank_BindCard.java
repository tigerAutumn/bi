package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;
/**
 * 正式绑卡ReqMsg
 * @author Dragon & cat
 * @date 2016-8-29
 */
public class ReqMsg_Bank_BindCard extends ReqMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8167190495767678305L;
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
	private String userId;
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
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}

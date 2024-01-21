package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 解绑前校验
 * @project gateway
 * @author bianyatian
 * @2018-5-23 下午4:47:47
 */
public class ReqMsg_Bank_UnbindCheck extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5836450267158203532L;

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
}

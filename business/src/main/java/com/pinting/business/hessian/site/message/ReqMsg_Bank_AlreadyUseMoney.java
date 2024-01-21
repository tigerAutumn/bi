package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 计算用户当日已使用的银行额度入参
 * @author shencheng
 * @2015-11-24 上午10:44:48
 */
public class ReqMsg_Bank_AlreadyUseMoney extends ReqMsg {

	private static final long serialVersionUID = -3099209519540198037L;

	/*用户id*/
	private Integer userId;
	/*银行id*/
	private Integer bankId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getBankId() {
		return bankId;
	}

	public void setBankId(Integer bankId) {
		this.bankId = bankId;
	}
}

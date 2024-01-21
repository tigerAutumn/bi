package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 查询用户信息和支持快捷支付的19付银行 入参
 * @author shencheng
 * @2015-11-19 上午11:52:39
 */
public class ReqMsg_Bank_UserBankInfo extends ReqMsg {

	private static final long serialVersionUID = 1559554282979787530L;
	/*用户id*/
	private Integer userId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}

package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 查询用户绑定的银行卡入参
 * @author shencheng
 * @2015-11-16 下午7:49:54
 */
public class ReqMsg_Bank_bindBankList extends ReqMsg {

	private static final long serialVersionUID = -8008844462257787060L;

	/*用户id*/
	private Integer userId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}

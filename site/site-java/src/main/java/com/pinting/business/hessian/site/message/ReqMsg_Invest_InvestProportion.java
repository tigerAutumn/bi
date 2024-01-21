package com.pinting.business.hessian.site.message;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_Invest_InvestProportion extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 699333435599609012L;

	/**
	 * 用户ID
	 */
	private Integer userId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}

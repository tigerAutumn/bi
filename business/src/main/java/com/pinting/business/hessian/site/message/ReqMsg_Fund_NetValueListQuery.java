package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_Fund_NetValueListQuery extends ReqMsg{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5051971522976136198L;

	private int fundId;

	public int getFundId() {
		return fundId;
	}

	public void setFundId(int fundId) {
		this.fundId = fundId;
	}
	
	
}

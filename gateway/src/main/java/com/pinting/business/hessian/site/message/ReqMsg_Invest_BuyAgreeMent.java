package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_Invest_BuyAgreeMent extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4212059377550667414L;
	
	private Integer userId;//用户编号	必填
	
	private Integer subAccountId;  //子账户ID

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getSubAccountId() {
		return subAccountId;
	}

	public void setSubAccountId(Integer subAccountId) {
		this.subAccountId = subAccountId;
	}

	
	
}

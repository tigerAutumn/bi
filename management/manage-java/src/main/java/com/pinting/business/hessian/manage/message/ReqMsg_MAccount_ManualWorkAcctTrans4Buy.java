package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_MAccount_ManualWorkAcctTrans4Buy extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9008942912194909317L;

	private Integer subAccountId;

	public Integer getSubAccountId() {
		return subAccountId;
	}

	public void setSubAccountId(Integer subAccountId) {
		this.subAccountId = subAccountId;
	}
	

}

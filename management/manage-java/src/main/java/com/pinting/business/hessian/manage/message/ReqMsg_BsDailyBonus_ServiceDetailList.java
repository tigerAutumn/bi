package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_BsDailyBonus_ServiceDetailList extends ReqMsg{

	/**
	 * 
	 */
	private static final long serialVersionUID = -61255485748073405L;

	private Integer subAccountId;

	public Integer getSubAccountId() {
		return subAccountId;
	}

	public void setSubAccountId(Integer subAccountId) {
		this.subAccountId = subAccountId;
	}
	
}

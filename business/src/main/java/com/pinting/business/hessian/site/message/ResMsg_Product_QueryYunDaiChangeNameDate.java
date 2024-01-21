package com.pinting.business.hessian.site.message;

import java.util.Date;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_Product_QueryYunDaiChangeNameDate extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2729574008975421400L;
	
	private		Date 	changeNameDate;

	public Date getChangeNameDate() {
		return changeNameDate;
	}

	public void setChangeNameDate(Date changeNameDate) {
		this.changeNameDate = changeNameDate;
	}
	
}

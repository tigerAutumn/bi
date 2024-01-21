package com.pinting.business.hessian.manage.message;

import java.util.Date;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_Reconciliation_CheckAccount2Dafy extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3117900293576563451L;
	
	private Date queryDate;//对账日期(理财产品购买的日期)

	public Date getQueryDate() {
		return queryDate;
	}

	public void setQueryDate(Date queryDate) {
		this.queryDate = queryDate;
	}
	

}

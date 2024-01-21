package com.pinting.gateway.hessian.message.dafy;

import java.util.Date;

import com.pinting.core.hessian.msg.ReqMsg;

public class B2GReqMsg_Account_CheckAccount extends ReqMsg{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8589493072802961507L;

	private Date queryDate;

	public Date getQueryDate() {
		return queryDate;
	}

	public void setQueryDate(Date queryDate) {
		this.queryDate = queryDate;
	}
	

}

package com.pinting.gateway.hessian.message.reapal;


import java.util.Date;

import com.pinting.core.hessian.msg.ReqMsg;

public class B2GReqMsg_ReapalAgentPay_AgentPayQuery extends ReqMsg {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5488675838644415246L;
	
	private String batchCurrnum;
	private Date batchDate;
	
	public String getBatchCurrnum() {
		return batchCurrnum;
	}
	public void setBatchCurrnum(String batchCurrnum) {
		this.batchCurrnum = batchCurrnum;
	}
	public Date getBatchDate() {
		return batchDate;
	}
	public void setBatchDate(Date batchDate) {
		this.batchDate = batchDate;
	}
	
	

}

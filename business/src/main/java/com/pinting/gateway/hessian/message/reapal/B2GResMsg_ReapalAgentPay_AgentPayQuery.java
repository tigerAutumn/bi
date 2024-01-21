package com.pinting.gateway.hessian.message.reapal;

import java.util.Date;
import java.util.List;

import com.pinting.core.hessian.msg.ResMsg;
import com.pinting.gateway.hessian.message.reapal.model.AgentPayQueryDetail;

public class B2GResMsg_ReapalAgentPay_AgentPayQuery extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2254228871257641060L;
	
	private Date batchDate;
	private String batchCurrnum;
	
	private List<AgentPayQueryDetail> details;

	public Date getBatchDate() {
		return batchDate;
	}

	public void setBatchDate(Date batchDate) {
		this.batchDate = batchDate;
	}

	public String getBatchCurrnum() {
		return batchCurrnum;
	}

	public void setBatchCurrnum(String batchCurrnum) {
		this.batchCurrnum = batchCurrnum;
	}

	public List<AgentPayQueryDetail> getDetails() {
		return details;
	}

	public void setDetails(List<AgentPayQueryDetail> details) {
		this.details = details;
	}

}

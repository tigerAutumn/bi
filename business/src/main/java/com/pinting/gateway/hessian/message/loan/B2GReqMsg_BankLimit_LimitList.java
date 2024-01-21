package com.pinting.gateway.hessian.message.loan;

import java.util.List;

import com.pinting.core.hessian.msg.ReqMsg;
import com.pinting.gateway.hessian.message.loan.model.BankLimit;

public class B2GReqMsg_BankLimit_LimitList extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6839688480175303378L;

	private List<BankLimit> bankLimits;

	public List<BankLimit> getBankLimits() {
		return bankLimits;
	}

	public void setBankLimits(List<BankLimit> bankLimits) {
		this.bankLimits = bankLimits;
	}
    
    
}

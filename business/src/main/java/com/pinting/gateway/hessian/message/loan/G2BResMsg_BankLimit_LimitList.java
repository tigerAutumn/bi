package com.pinting.gateway.hessian.message.loan;

import java.util.List;

import com.pinting.core.hessian.msg.ResMsg;
import com.pinting.gateway.hessian.message.loan.model.BankLimit;

public class G2BResMsg_BankLimit_LimitList extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6209459951556659300L;
	private List<BankLimit> bankLimits;

	public List<BankLimit> getBankLimits() {
		return bankLimits;
	}

	public void setBankLimits(List<BankLimit> bankLimits) {
		this.bankLimits = bankLimits;
	}
}

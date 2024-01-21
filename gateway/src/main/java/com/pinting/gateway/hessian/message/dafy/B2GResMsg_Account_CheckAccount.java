package com.pinting.gateway.hessian.message.dafy;

import java.util.List;

import com.pinting.core.hessian.msg.ResMsg;
import com.pinting.gateway.hessian.message.dafy.model.InvestmentAmounts;

public class B2GResMsg_Account_CheckAccount extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5084090467207923894L;
	
	private List<InvestmentAmounts> investmentAmounts;
	public List<InvestmentAmounts> getInvestmentAmounts() {
		return investmentAmounts;
	}
	public void setInvestmentAmounts(List<InvestmentAmounts> investmentAmounts) {
		this.investmentAmounts = investmentAmounts;
	}

}

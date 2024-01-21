package com.pinting.gateway.hessian.message.dafy;

import java.util.List;

import com.pinting.core.hessian.msg.ResMsg;
import com.pinting.gateway.hessian.message.dafy.model.BorrowerRepayData;

public class B2GResMsg_Payment_QueryRepayJnl extends ResMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7458940225479331712L;
	private List<BorrowerRepayData> borrowerRepays;
	public List<BorrowerRepayData> getBorrowerRepays() {
		return borrowerRepays;
	}
	public void setBorrowerRepays(List<BorrowerRepayData> borrowerRepays) {
		this.borrowerRepays = borrowerRepays;
	}

}

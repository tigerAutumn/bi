package com.pinting.gateway.loan7.out.model;

import java.util.List;

import com.pinting.gateway.hessian.message.dafy.model.BorrowerRepayData;

public class QueryRepayJnlResModel extends BaseResModel {
	
	private List<BorrowerRepayData> borrowerRepayData;

	public List<BorrowerRepayData> getBorrowerRepayData() {
		return borrowerRepayData;
	}

	public void setBorrowerRepayData(List<BorrowerRepayData> borrowerRepayData) {
		this.borrowerRepayData = borrowerRepayData;
	}

	@Override
	public String toString() {
		return "QueryRepayJnlResModel [borrowerRepayData=" + borrowerRepayData
				+ "]";
	}
	
	
}

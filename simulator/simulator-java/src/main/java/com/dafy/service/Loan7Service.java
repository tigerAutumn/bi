package com.dafy.service;

import com.dafy.model.vo.loan7.QueryBillResModel;


public interface Loan7Service {

	/**
	 * @param loanId 借款编号
	 * @return
	 */
	QueryBillResModel queryBill(String loanId);
	
}

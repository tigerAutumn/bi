package com.dafy.service;

import com.dafy.model.vo.QueryBillResModel;

public interface YunSelfLoanService {
	
	QueryBillResModel queryBill(String userId , String loanId);
}

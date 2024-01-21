package com.pinting.gateway.in.facade.loan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.service.loan.LoanQueryService;
import com.pinting.gateway.hessian.message.loan.G2BReqMsg_BankLimit_LimitList;
import com.pinting.gateway.hessian.message.loan.G2BResMsg_BankLimit_LimitList;

@Component("BankLimit")
public class BankLimitFacade {
	@Autowired
	private LoanQueryService loanQueryService;
	
	public void limitList(G2BReqMsg_BankLimit_LimitList req,
			G2BResMsg_BankLimit_LimitList res){
		res.setBankLimits(loanQueryService.getBankLimitList(req, res));
	}
}

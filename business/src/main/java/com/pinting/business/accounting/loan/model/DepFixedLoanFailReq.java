package com.pinting.business.accounting.loan.model;

import java.util.List;

import com.pinting.business.model.LnDepositionTarget;
import com.pinting.business.model.LnLoan;
import com.pinting.business.model.vo.LoanRelation4DepVO;

/**
 * 存管定期借款失败
 * @author bianyatian
 * @2017-4-11 下午4:53:52
 */
public class DepFixedLoanFailReq {

	private LnLoan lnLoan;
	
	private LnDepositionTarget depositionTarget;

	private List<LoanRelation4DepVO> loanRelationList;
	
	private String orderNo;
	
	private Exception error;
	
	private String propertySymbol; //资产方
	
	public Exception getError() {
		return error;
	}

	public void setError(Exception error) {
		this.error = error;
	}

	public LnLoan getLnLoan() {
		return lnLoan;
	}

	public void setLnLoan(LnLoan lnLoan) {
		this.lnLoan = lnLoan;
	}

	public LnDepositionTarget getDepositionTarget() {
		return depositionTarget;
	}

	public void setDepositionTarget(LnDepositionTarget depositionTarget) {
		this.depositionTarget = depositionTarget;
	}

	public List<LoanRelation4DepVO> getLoanRelationList() {
		return loanRelationList;
	}

	public void setLoanRelationList(List<LoanRelation4DepVO> loanRelationList) {
		this.loanRelationList = loanRelationList;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getPropertySymbol() {
		return propertySymbol;
	}

	public void setPropertySymbol(String propertySymbol) {
		this.propertySymbol = propertySymbol;
	}

}

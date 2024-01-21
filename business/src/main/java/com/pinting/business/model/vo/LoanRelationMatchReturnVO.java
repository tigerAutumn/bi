package com.pinting.business.model.vo;

import java.util.List;

import com.pinting.business.model.LnLoanRelation;

public class LoanRelationMatchReturnVO {

	private List<LnLoanRelation> list; //旧关系匹配列表
	
	private List<LoanRelation4DepVO> depRelationList; //存管匹配列表
	
	private Double borrowAmount; //借款金额
	
	private Double normalAUTHAmount; //普通AUTH总资金
	
	private Double superAUTHAmount; //超级理财人AUTH总资金

	public List<LnLoanRelation> getList() {
		return list;
	}

	public void setList(List<LnLoanRelation> list) {
		this.list = list;
	}

	public Double getBorrowAmount() {
		return borrowAmount;
	}

	public void setBorrowAmount(Double borrowAmount) {
		this.borrowAmount = borrowAmount;
	}

	public Double getNormalAUTHAmount() {
		return normalAUTHAmount;
	}

	public void setNormalAUTHAmount(Double normalAUTHAmount) {
		this.normalAUTHAmount = normalAUTHAmount;
	}

	public Double getSuperAUTHAmount() {
		return superAUTHAmount;
	}

	public void setSuperAUTHAmount(Double superAUTHAmount) {
		this.superAUTHAmount = superAUTHAmount;
	}

	public List<LoanRelation4DepVO> getDepRelationList() {
		return depRelationList;
	}

	public void setDepRelationList(List<LoanRelation4DepVO> depRelationList) {
		this.depRelationList = depRelationList;
	}
}

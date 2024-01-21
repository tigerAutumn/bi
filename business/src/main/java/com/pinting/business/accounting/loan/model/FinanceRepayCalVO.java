package com.pinting.business.accounting.loan.model;

import java.util.List;
import com.pinting.business.model.LnCompensateRelation;

public class FinanceRepayCalVO {
	
	private Double agreementSumAmount; //根据协议利率计算总金额
	
	private Double financeSumPrincipal; //理财人应收总本金
	
	private Double financeSumInterest;  //理财人应收总利息
	
	private List<LnCompensateRelation> list; //本金代偿时需返回的列表

	public Double getAgreementSumAmount() {
		return agreementSumAmount;
	}

	public void setAgreementSumAmount(Double agreementSumAmount) {
		this.agreementSumAmount = agreementSumAmount;
	}

	public List<LnCompensateRelation> getList() {
		return list;
	}

	public void setList(List<LnCompensateRelation> list) {
		this.list = list;
	}

	public Double getFinanceSumPrincipal() {
		return financeSumPrincipal;
	}

	public void setFinanceSumPrincipal(Double financeSumPrincipal) {
		this.financeSumPrincipal = financeSumPrincipal;
	}

	public Double getFinanceSumInterest() {
		return financeSumInterest;
	}

	public void setFinanceSumInterest(Double financeSumInterest) {
		this.financeSumInterest = financeSumInterest;
	}

}

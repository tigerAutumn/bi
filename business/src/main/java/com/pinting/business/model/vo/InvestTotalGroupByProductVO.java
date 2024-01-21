package com.pinting.business.model.vo;

public class InvestTotalGroupByProductVO {
	
	
	private Double investTotalGroupByProductAmount; //产品总投资金额
	private String productName; //产品名称
	private String investTotalGroupByProductAmountString; //产品总投资金额字符串类型
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Double getInvestTotalGroupByProductAmount() {
		return investTotalGroupByProductAmount;
	}
	public void setInvestTotalGroupByProductAmount(
			Double investTotalGroupByProductAmount) {
		this.investTotalGroupByProductAmount = investTotalGroupByProductAmount;
	}
	public String getInvestTotalGroupByProductAmountString() {
		return investTotalGroupByProductAmountString;
	}
	public void setInvestTotalGroupByProductAmountString(
			String investTotalGroupByProductAmountString) {
		this.investTotalGroupByProductAmountString = investTotalGroupByProductAmountString;
	}
	
}

package com.pinting.gateway.hessian.message.dafy.model;

import java.io.Serializable;

public class InvestmentAmounts implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2866018974042249836L;
	private String productCode = null;
	private String productName = null;
	private Double amount = 0d;
	
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	@Override
	public String toString() {
		return "理财产品汇总 [productCode=" + productCode
				+ ", productName=" + productName + ", amount=" + amount + "]";
	}
	
}

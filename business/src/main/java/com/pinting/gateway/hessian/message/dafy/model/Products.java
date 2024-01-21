package com.pinting.gateway.hessian.message.dafy.model;

import java.io.Serializable;

public class Products implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2367307811540385418L;
	private Double productAmount;
	private String productCode;
	private String productOrderNo;
	public Double getProductAmount() {
		return productAmount;
	}
	public void setProductAmount(Double productAmount) {
		this.productAmount = productAmount;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProductOrderNo() {
		return productOrderNo;
	}
	public void setProductOrderNo(String productOrderNo) {
		this.productOrderNo = productOrderNo;
	}

}

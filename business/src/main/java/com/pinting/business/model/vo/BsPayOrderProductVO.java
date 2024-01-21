package com.pinting.business.model.vo;

import com.pinting.business.model.BsPayOrders;

public class BsPayOrderProductVO extends BsPayOrders{
	
	private String orderNo;
	private String subAccountCode;
	private Integer productId;
	private Double balance;
	private Double maxTotalAmount;
	private Double currTotalAmount;
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getSubAccountCode() {
		return subAccountCode;
	}
	public void setSubAccountCode(String subAccountCode) {
		this.subAccountCode = subAccountCode;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public Double getMaxTotalAmount() {
		return maxTotalAmount;
	}
	public void setMaxTotalAmount(Double maxTotalAmount) {
		this.maxTotalAmount = maxTotalAmount;
	}
	public Double getCurrTotalAmount() {
		return currTotalAmount;
	}
	public void setCurrTotalAmount(Double currTotalAmount) {
		this.currTotalAmount = currTotalAmount;
	}
	@Override
	public String toString() {
		return "BsPayOrderProductVO [orderNo=" + orderNo + ", subAccountCode="
				+ subAccountCode + ", productId=" + productId + ", balance="
				+ balance + ", maxTotalAmount=" + maxTotalAmount
				+ ", currTotalAmount=" + currTotalAmount + "]";
	}
	
}

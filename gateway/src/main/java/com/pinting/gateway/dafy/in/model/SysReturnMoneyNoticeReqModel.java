package com.pinting.gateway.dafy.in.model;

import java.util.Date;
/**
 * 
 * @Project: gateway
 * @Title: SysReturnMoneyNoticeReqModel.java
 * @author dingpf
 * @date 2015-11-21 下午1:57:16
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class SysReturnMoneyNoticeReqModel extends BaseReqModel {
	private String clientKey;
	private String payPlatform;
	private String merchantId;
	private String payOrderNo;
	private Date payReqTime;
	private Date payFinshTime;
	private double amount;
	private String productOrderNo;
	private String productCode;
	private double productAmount;
	private double productInterest;
	private String productReturnTerm;
	
	public String getClientKey() {
		return clientKey;
	}
	public void setClientKey(String clientKey) {
		this.clientKey = clientKey;
	}
	public String getPayPlatform() {
		return payPlatform;
	}
	public void setPayPlatform(String payPlatform) {
		this.payPlatform = payPlatform;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getPayOrderNo() {
		return payOrderNo;
	}
	public void setPayOrderNo(String payOrderNo) {
		this.payOrderNo = payOrderNo;
	}
	public Date getPayReqTime() {
		return payReqTime;
	}
	public void setPayReqTime(Date payReqTime) {
		this.payReqTime = payReqTime;
	}
	public Date getPayFinshTime() {
		return payFinshTime;
	}
	public void setPayFinshTime(Date payFinshTime) {
		this.payFinshTime = payFinshTime;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getProductOrderNo() {
		return productOrderNo;
	}
	public void setProductOrderNo(String productOrderNo) {
		this.productOrderNo = productOrderNo;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public double getProductAmount() {
		return productAmount;
	}
	public void setProductAmount(double productAmount) {
		this.productAmount = productAmount;
	}
	public double getProductInterest() {
		return productInterest;
	}
	public void setProductInterest(double productInterest) {
		this.productInterest = productInterest;
	}
	public String getProductReturnTerm() {
		return productReturnTerm;
	}
	public void setProductReturnTerm(String productReturnTerm) {
		this.productReturnTerm = productReturnTerm;
	}
	@Override
	public String toString() {
		return "SysReturnMoneyNoticeReqModel [clientKey=" + clientKey
				+ ", payPlatform=" + payPlatform + ", merchantId=" + merchantId
				+ ", payOrderNo=" + payOrderNo + ", payReqTime=" + payReqTime
				+ ", payFinshTime=" + payFinshTime + ", amount=" + amount
				+ ", productOrderNo=" + productOrderNo + ", productCode="
				+ productCode + ", productAmount=" + productAmount
				+ ", productInterest=" + productInterest
				+ ", productReturnTerm=" + productReturnTerm + "]";
	}
	
}

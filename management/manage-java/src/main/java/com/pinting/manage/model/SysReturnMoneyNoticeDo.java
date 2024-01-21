package com.pinting.manage.model;

import java.util.Date;

public class SysReturnMoneyNoticeDo extends BaseReqModel {
	private String clientKey;
	private String payPlatform;
	private String merchantId;
	private String payOrderNo;
	private Date payReqTime;
	private Date payFinshTime;
	private double amount;
	private String productOrderNo;
	private String productCode;
	private double productPrincipal;
	private double productAmount;
	private double productInterest;
	private String productReturnTerm;
	private String propertySymbol;
	private String noticeUrl;

	public String getNoticeUrl() {
		return noticeUrl;
	}

	public void setNoticeUrl(String noticeUrl) {
		this.noticeUrl = noticeUrl;
	}

	public double getProductPrincipal() {
		return productPrincipal;
	}

	public void setProductPrincipal(double productPrincipal) {
		this.productPrincipal = productPrincipal;
	}

	public String getPropertySymbol() {
		return propertySymbol;
	}

	public void setPropertySymbol(String propertySymbol) {
		this.propertySymbol = propertySymbol;
	}

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

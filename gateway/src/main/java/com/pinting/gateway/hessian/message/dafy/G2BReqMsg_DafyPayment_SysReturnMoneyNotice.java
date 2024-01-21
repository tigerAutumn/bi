package com.pinting.gateway.hessian.message.dafy;

import java.util.Date;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 
 * @Project: gateway
 * @Title: G2BReqMsg_DafyPayment_SysReturnMoneyNotice.java
 * @author dingpf
 * @date 2015-11-21 下午12:37:22
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class G2BReqMsg_DafyPayment_SysReturnMoneyNotice extends ReqMsg{

	/**
	 * 
	 */
	private static final long serialVersionUID = 79638127139272169L;
	
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

}

package com.pinting.gateway.dafy.in.model;

import java.util.Date;

/**
 * 
 * @Project: gateway
 * @Title: SysBuyProductNoticeReqModel.java
 * @author dingpf
 * @date 2015-11-21 下午1:57:24
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class SysBuyProductNoticeReqModel extends BaseReqModel {
	private String payPlatform;
	private Date finshTime;
	private String productOrderNo;
	private String productCode;
	private double productAmount;
	private int result;
	private String errorMsg;
	private String clientKey;
	
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getPayPlatform() {
		return payPlatform;
	}
	public void setPayPlatform(String payPlatform) {
		this.payPlatform = payPlatform;
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
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public Date getFinshTime() {
		return finshTime;
	}
	public void setFinshTime(Date finshTime) {
		this.finshTime = finshTime;
	}
	public String getClientKey() {
		return clientKey;
	}
	public void setClientKey(String clientKey) {
		this.clientKey = clientKey;
	}
	@Override
	public String toString() {
		return "SysBuyProductNoticeReqModel [payPlatform=" + payPlatform
				+ ", finshTime=" + finshTime + ", productOrderNo="
				+ productOrderNo + ", productCode=" + productCode
				+ ", productAmount=" + productAmount + ", result=" + result
				+ ", errorMsg=" + errorMsg + ", clientKey=" + clientKey + "]";
	}
	
}

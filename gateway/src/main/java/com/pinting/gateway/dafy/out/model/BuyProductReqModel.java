package com.pinting.gateway.dafy.out.model;


/**
 * @Project: gateway
 * @Title: BuyProductReqModel.java
 * @author Zhou Changzai
 * @date 2015-2-11 下午3:41:50
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class BuyProductReqModel extends BaseReqModel {
	public static final String TRANSFER_TYPE_PC = "0";//PC浏览器
	public static final String TRANSFER_TYPE_WAP = "1";//手机浏览器
	
	private String customerId;
	private String productCode;
	private String amount;
	private String orderId;
	private String urlJsp;
	private String transferType;//渠道类型，0-PC， 1-WAP
	private String bankCode;//银行编码，在渠道类型为1的时候才需要
	
	public String getTransferType() {
		return transferType;
	}
	public void setTransferType(String transferType) {
		this.transferType = transferType;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getUrlJsp() {
		return urlJsp;
	}
	public void setUrlJsp(String urlJsp) {
		this.urlJsp = urlJsp;
	}
}

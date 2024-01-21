package com.pinting.gateway.loan7.out.model;

import java.util.List;

import com.pinting.gateway.hessian.message.dafy.model.Products;

public class SysBatchBuyProductReqModel extends BaseReqModel {

	private String clientKey;
	private String customerId;
	private String payPlatform;
	private String merchantId;
	private String payOrderNo;
	private String payReqTime;
	private String payFinishTime;
	private Double amount;
	private List<Products> products;
	public String getClientKey() {
		return clientKey;
	}
	public void setClientKey(String clientKey) {
		this.clientKey = clientKey;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
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
	public String getPayReqTime() {
		return payReqTime;
	}
	public void setPayReqTime(String payReqTime) {
		this.payReqTime = payReqTime;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public List<Products> getProducts() {
		return products;
	}
	public void setProducts(List<Products> products) {
		this.products = products;
	}
	public String getPayFinishTime() {
		return payFinishTime;
	}
	public void setPayFinishTime(String payFinishTime) {
		this.payFinishTime = payFinishTime;
	}
}

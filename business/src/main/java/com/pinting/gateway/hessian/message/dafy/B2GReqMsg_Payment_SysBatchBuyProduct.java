package com.pinting.gateway.hessian.message.dafy;

import java.util.Date;
import java.util.List;

import com.pinting.core.hessian.msg.ReqMsg;
import com.pinting.gateway.hessian.message.dafy.model.Products;

/**
 * 
 * @Project: business
 * @Title: B2GReqMsg_Payment_SysBatchBuyProduct.java
 * @author dingpf
 * @date 2015-11-20 下午7:06:22
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class B2GReqMsg_Payment_SysBatchBuyProduct extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4994282601269209057L;
	
	private String customerId;
	private String payPlatform = "BAOFOO";
	private String payOrderNo;
	private Date payReqTime;
	private Date payFinshTime;
	private Double amount;
	private List<Products> products;
	//产品方
	private String propertySymbol;
	
	private String pay19MpOrderNo;
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

	public String getPropertySymbol() {
		return propertySymbol;
	}

	public void setPropertySymbol(String propertySymbol) {
		this.propertySymbol = propertySymbol;
	}
	public String getPay19MpOrderNo() {
		return pay19MpOrderNo;
	}
	public void setPay19MpOrderNo(String pay19MpOrderNo) {
		this.pay19MpOrderNo = pay19MpOrderNo;
	}
	
}

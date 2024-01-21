package com.pinting.gateway.hessian.message.dafy;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * @Project: business
 * @Title: B2GReqMsg_Payment_BuyProduct.java
 * @author Zhou Changzai
 * @date 2015-2-11 下午8:02:39
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class B2GReqMsg_Payment_BuyProduct extends ReqMsg {
	private static final long serialVersionUID = -4928033850676326332L;
	
	private String customerId;//达飞系统客户ID
	private String productCode;//理财产品编码
	private Double amount;//购买金额
	private String orderNo;//订单号
	private String bankCode;//银行编码，在渠道类型为1的时候才需要
	
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}

}

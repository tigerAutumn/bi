/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.pay19.out.model.resp;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: QueryRecvAcctTransResp.java, v 0.1 2015-11-3 下午4:45:01 BabyShark Exp $
 */
public class QueryRecvAcctTransResp extends AcctTransBaseResp {

    
    /**
	 * 
	 */
	private static final long serialVersionUID = -7235718824355957655L;
	private String            merchantId;
    private String            oriOutMxId;
    private String            oriOutOrderId;
    private String            mpOrderId;
    private String            orderAmount;
    private String            tradeResult;
    private String            fee;
    private String            finTime;
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getOriOutMxId() {
		return oriOutMxId;
	}
	public void setOriOutMxId(String oriOutMxId) {
		this.oriOutMxId = oriOutMxId;
	}
	public String getOriOutOrderId() {
		return oriOutOrderId;
	}
	public void setOriOutOrderId(String oriOutOrderId) {
		this.oriOutOrderId = oriOutOrderId;
	}
	public String getMpOrderId() {
		return mpOrderId;
	}
	public void setMpOrderId(String mpOrderId) {
		this.mpOrderId = mpOrderId;
	}
	public String getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}
	public String getTradeResult() {
		return tradeResult;
	}
	public void setTradeResult(String tradeResult) {
		this.tradeResult = tradeResult;
	}
	public String getFee() {
		return fee;
	}
	public void setFee(String fee) {
		this.fee = fee;
	}
	public String getFinTime() {
		return finTime;
	}
	public void setFinTime(String finTime) {
		this.finTime = finTime;
	}

}

/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.pay19.out.model.resp;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: QueryAcctTransResp.java, v 0.1 2015-11-4 下午2:08:25 BabyShark Exp $
 */
public class QueryAcctTransResp extends AcctTransBaseResp {

    /**  */
    private static final long serialVersionUID = -6815214856037840493L;

    private String            merchantId;
    private String            orderId;
    private String            mpOrderId;
    private String            orderAmount;
    private String            fee;
    private String            tradeResult;
    private String            finTime;

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getTradeResult() {
        return tradeResult;
    }

    public void setTradeResult(String tradeResult) {
        this.tradeResult = tradeResult;
    }

    public String getFinTime() {
        return finTime;
    }

    public void setFinTime(String finTime) {
        this.finTime = finTime;
    }

}

/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.pay19.out.model.resp;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: MerchantDfQueryResp.java, v 0.1 2015-8-11 下午4:38:49 BabyShark Exp $
 */
public class MerchantDfQueryResp extends Pay4AnotherBaseResp {

    /**  */
    private static final long serialVersionUID = 1444755982425483682L;
    private String            mxOrderId;
    private String            sysOrderId;
    private String            orderStatus;
    private String            finishTime;
    private String            amount;

    public String getMxOrderId() {
        return mxOrderId;
    }

    public void setMxOrderId(String mxOrderId) {
        this.mxOrderId = mxOrderId;
    }

    public String getSysOrderId() {
        return sysOrderId;
    }

    public void setSysOrderId(String sysOrderId) {
        this.sysOrderId = sysOrderId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

}

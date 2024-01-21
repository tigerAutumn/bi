/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.pay19.in.model.req;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: NewMerchantDFReq.java, v 0.1 2015-8-12 上午11:57:03 BabyShark Exp $
 */
public class NewMerchantDFReq extends Pay4AnotherBaseReq {

    /**  */
    private static final long serialVersionUID = 3421017954439905934L;

    private String            mxOrderId;
    private String            sysOrderId;
    private String            orderStatus;
    private String            finishTime;
    private String            amount;
    private String            extend1;
    private String            extend2;
    private String            extend3;

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

    public String getExtend1() {
        return extend1;
    }

    public void setExtend1(String extend1) {
        this.extend1 = extend1;
    }

    public String getExtend2() {
        return extend2;
    }

    public void setExtend2(String extend2) {
        this.extend2 = extend2;
    }

    public String getExtend3() {
        return extend3;
    }

    public void setExtend3(String extend3) {
        this.extend3 = extend3;
    }

    @Override
    public String toString() {
        return "19付代付通知请求数据： 【 mxOrderId=" + mxOrderId + ", sysOrderId=" + sysOrderId
               + ", orderStatus=" + orderStatus + ", finishTime=" + finishTime + ", amount="
               + amount + ", extend1=" + extend1 + ", extend2=" + extend2 + ", extend3=" + extend3
               + ", version=" + getVersion() + ", hmac=" + getHmac() + ", retCode=" + getRetCode()
               + "】";
    }

}

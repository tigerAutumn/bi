/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.pay19.out.model.resp;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: NewMerchantDfResp.java, v 0.1 2015-8-11 下午4:35:31 BabyShark Exp $
 */
public class NewMerchantDfResp extends Pay4AnotherBaseResp {

    /**  */
    private static final long serialVersionUID = -6136786792209261156L;
    
    private String            mxOrderId;
    private String            preOrderTime;
    private String            sysOrderId;

    public String getMxOrderId() {
        return mxOrderId;
    }

    public void setMxOrderId(String mxOrderId) {
        this.mxOrderId = mxOrderId;
    }

    public String getPreOrderTime() {
        return preOrderTime;
    }

    public void setPreOrderTime(String preOrderTime) {
        this.preOrderTime = preOrderTime;
    }

    public String getSysOrderId() {
        return sysOrderId;
    }

    public void setSysOrderId(String sysOrderId) {
        this.sysOrderId = sysOrderId;
    }

}

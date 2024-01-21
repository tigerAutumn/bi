/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.pay19.out.model.req;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: Pay4AnotherBaseReq.java, v 0.1 2015-8-11 下午3:23:58 BabyShark Exp $
 */
public class Pay4AnotherBaseReq extends BaseReq {

    /**  */
    private static final long serialVersionUID = 7772146178233868125L;
    private String            version          = "1.0";
    private String            reqTime;
    private String            mxId;
    private String            hmac;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getReqTime() {
        return reqTime;
    }

    public void setReqTime(String reqTime) {
        this.reqTime = reqTime;
    }

    public String getMxId() {
        return mxId;
    }

    public void setMxId(String mxId) {
        this.mxId = mxId;
    }

    public String getHmac() {
        return hmac;
    }

    public void setHmac(String hmac) {
        this.hmac = hmac;
    }

}

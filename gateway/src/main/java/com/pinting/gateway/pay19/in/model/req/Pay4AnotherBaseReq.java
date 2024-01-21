/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.pay19.in.model.req;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: Pay4AnotherBaseReq.java, v 0.1 2015-8-12 上午11:54:19 BabyShark Exp $
 */
public class Pay4AnotherBaseReq extends BaseReq {

    /**  */
    private static final long serialVersionUID = 5132094602628942508L;
    private String            version          = "1.0";
    private String            hmac;
    private String            mxId;
    private String            retCode;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getHmac() {
        return hmac;
    }

    public void setHmac(String hmac) {
        this.hmac = hmac;
    }

    public String getMxId() {
        return mxId;
    }

    public void setMxId(String mxId) {
        this.mxId = mxId;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

}

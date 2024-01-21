/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.pay19.out.model.resp;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: Pay4AnotherBaseResp.java, v 0.1 2015-8-11 下午3:25:55 BabyShark Exp $
 */
public class Pay4AnotherBaseResp extends BaseResp {

    /**  */
    private static final long serialVersionUID = -52233672695049114L;
    private String            mxId;
    private String            retCode;
    private String            status;
    private String            hmac;

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
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHmac() {
        return hmac;
    }

    public void setHmac(String hmac) {
        this.hmac = hmac;
    }

}

/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.pay19.out.model.req;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: RealNameBaseReq.java, v 0.1 2015-8-17 下午6:31:29 BabyShark Exp $
 */
public class RealNameBaseReq extends BaseReq {

    /**  */
    private static final long serialVersionUID = 5386893050917698753L;

    private String            version          = "1.00";
    private String            verifyString;
    private String            merchantId;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVerifyString() {
        return verifyString;
    }

    public void setVerifyString(String verifyString) {
        this.verifyString = verifyString;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

}

/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.pay19.in.model.req;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: RealNameBaseReq.java, v 0.1 2015-8-18 下午3:08:23 BabyShark Exp $
 */
public class RealNameBaseReq extends BaseReq {

    /**  */
    private static final long serialVersionUID = 7603882232456666644L;
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

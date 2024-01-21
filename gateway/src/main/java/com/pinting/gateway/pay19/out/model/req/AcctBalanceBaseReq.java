/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.pay19.out.model.req;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: AcctBalanceBaseReq.java, v 0.1 2015-11-3 下午3:39:27 BabyShark Exp $
 */
public class AcctBalanceBaseReq extends BaseReq {

    /**  */
    private static final long serialVersionUID = -8368807961967812673L;
    private String            versionId        = "1.00";
    private String            verifyString;
    private String            merchantId;

    public String getVersionId() {
        return versionId;
    }

    public void setVersionId(String versionId) {
        this.versionId = versionId;
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

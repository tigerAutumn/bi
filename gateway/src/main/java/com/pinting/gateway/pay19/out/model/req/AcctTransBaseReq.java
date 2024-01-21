/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.pay19.out.model.req;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: AcctTransBaseReq.java, v 0.1 2015-11-3 下午4:47:26 BabyShark Exp $
 */
public class AcctTransBaseReq extends BaseReq {

    /**  */
    private static final long serialVersionUID = 7031129410426016621L;
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

/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.pay19.in.model.req;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: QuickPayBaseReq.java, v 0.1 2015-8-12 上午11:54:19 BabyShark Exp $
 */
public class QuickPayBaseReq extends BaseReq {

    /**  */
    private static final long serialVersionUID = 5639784495274487812L;

    private String            version_id       = "3.00";
    private String            verifystring;
    private String            merchant_id;
    private String            status;
    private String            error_code;

    public String getVersion_id() {
        return version_id;
    }

    public void setVersion_id(String version_id) {
        this.version_id = version_id;
    }

    public String getVerifystring() {
        return verifystring;
    }

    public void setVerifystring(String verifystring) {
        this.verifystring = verifystring;
    }

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

}

/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.pay19.out.model.resp;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: RealNameBaseResp.java, v 0.1 2015-8-17 下午6:45:33 BabyShark Exp $
 */
public class RealNameBaseResp extends BaseResp {

    /**  */
    private static final long serialVersionUID = -6161908798342885920L;
    private String            version          = "1.00";
    private String            verifyString;
    private String            reqStatus;
    private String            retCode;

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

    public String getReqStatus() {
        return reqStatus;
    }

    public void setReqStatus(String reqStatus) {
        this.reqStatus = reqStatus;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

}

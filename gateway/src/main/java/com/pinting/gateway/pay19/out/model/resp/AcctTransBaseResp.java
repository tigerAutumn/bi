/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.pay19.out.model.resp;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: AcctTransBaseResp.java, v 0.1 2015-11-3 下午4:41:33 BabyShark Exp $
 */
public class AcctTransBaseResp extends BaseResp {

    /**  */
    private static final long serialVersionUID = -5760664434290415682L;
    private String            versionId        = "1.00";
    private String            verifyString;
    private String            reqStatus;
    private String            retCode;

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

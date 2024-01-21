/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.model.vo;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: UserSealResult.java, v 0.1 2015-9-17 下午1:47:52 BabyShark Exp $
 */
public class UserSealResultVO {
    private boolean isSucc;      //是否有章：isSucc为true时，以下4项不为空
    private String  sealCode;
    private String  sealPassword;
    private String  certDN;
    private String  certSN;

    public boolean isSucc() {
        return isSucc;
    }

    public void setSucc(boolean isSucc) {
        this.isSucc = isSucc;
    }

    public String getSealCode() {
        return sealCode;
    }

    public void setSealCode(String sealCode) {
        this.sealCode = sealCode;
    }

    public String getSealPassword() {
        return sealPassword;
    }

    public void setSealPassword(String sealPassword) {
        this.sealPassword = sealPassword;
    }

    public String getCertDN() {
        return certDN;
    }

    public void setCertDN(String certDN) {
        this.certDN = certDN;
    }

    public String getCertSN() {
        return certSN;
    }

    public void setCertSN(String certSN) {
        this.certSN = certSN;
    }

}

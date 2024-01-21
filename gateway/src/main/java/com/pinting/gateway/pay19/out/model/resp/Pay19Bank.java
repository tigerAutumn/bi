/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.pay19.out.model.resp;

import java.io.Serializable;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: Pay19Bank.java, v 0.1 2015-8-6 下午2:54:58 BabyShark Exp $
 */
public class Pay19Bank implements Serializable {
    /**  */
    private static final long serialVersionUID = -7702271798661388692L;
    private String            bankName;
    private String            bankType;
    private String            pcId;

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankType() {
        return bankType;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    public String getPcId() {
        return pcId;
    }

    public void setPcId(String pcId) {
        this.pcId = pcId;
    }

}

/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.cfcatransfer.bigangwan.model.cfca;

import java.util.Date;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: KTBaseResp.java, v 0.1 2015-9-14 下午3:39:33 BabyShark Exp $
 */
public class KTBaseResp {
    private String txType;
    private Date   txTime;
    private String resultCode;
    private String resultMessage;

    public String getTxType() {
        return txType;
    }

    public void setTxType(String txType) {
        this.txType = txType;
    }

    public Date getTxTime() {
        return txTime;
    }

    public void setTxTime(Date txTime) {
        this.txTime = txTime;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

}

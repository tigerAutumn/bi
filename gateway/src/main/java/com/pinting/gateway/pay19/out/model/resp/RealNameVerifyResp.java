/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.pay19.out.model.resp;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: RealNameAuthResp.java, v 0.1 2015-8-17 下午6:47:49 BabyShark Exp $
 */
public class RealNameVerifyResp extends RealNameBaseResp {

    /**  */
    private static final long serialVersionUID = 1869905345749998408L;

    private String            tradeStatus;
    private String            merchantId;
    private String            mxReqSno;
    private String            mxReqDate;
    private String            mpServSno;

    public String getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMxReqSno() {
        return mxReqSno;
    }

    public void setMxReqSno(String mxReqSno) {
        this.mxReqSno = mxReqSno;
    }

    public String getMxReqDate() {
        return mxReqDate;
    }

    public void setMxReqDate(String mxReqDate) {
        this.mxReqDate = mxReqDate;
    }

    public String getMpServSno() {
        return mpServSno;
    }

    public void setMpServSno(String mpServSno) {
        this.mpServSno = mpServSno;
    }

}

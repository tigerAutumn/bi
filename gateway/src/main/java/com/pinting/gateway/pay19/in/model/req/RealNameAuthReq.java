/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.pay19.in.model.req;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: RealNameAuthReq.java, v 0.1 2015-8-18 下午3:09:56 BabyShark Exp $
 */
public class RealNameAuthReq extends RealNameBaseReq {

    /**  */
    private static final long serialVersionUID = 5933123343880213899L;
    private String            mxReqSno;
    private String            mxReqDate;
    private String            mpServSno;
    private String            tradeStatus;

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

    public String getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    @Override
    public String toString() {
        return "19付实名认证通知请求数据： 【mxReqSno=" + mxReqSno + ", mxReqDate=" + mxReqDate + ", mpServSno="
               + mpServSno + ", tradeStatus=" + tradeStatus + ", version=" + getVersion()
               + ", verifyString=" + getVerifyString() + "】";
    }

}

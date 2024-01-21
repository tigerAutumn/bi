/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.pay19.out.model.req;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: QueryRealNameResultReq.java, v 0.1 2015-8-17 下午6:50:31 BabyShark Exp $
 */
public class QueryRealNameVerifyReq extends RealNameBaseReq {

    /**  */
    private static final long serialVersionUID = -5926995233187195424L;
    private String            mxReqSno;
    private String            mxReqDate;

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

}

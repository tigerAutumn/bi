/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.pay19.out.model.req;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: RSendSms.java, v 0.1 2015-8-6 下午4:09:01 BabyShark Exp $
 */
public class RSendSmsReq extends QuickPayBaseReq {
    /**  */
    private static final long serialVersionUID = -2068478866238500715L;
    private String            mx_userid;
    private String            order_id;
    private String            order_date;
    private String            mp_orderid;
    private String            verifycode_sendflag;

    public String getMx_userid() {
        return mx_userid;
    }

    public void setMx_userid(String mx_userid) {
        this.mx_userid = mx_userid;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getMp_orderid() {
        return mp_orderid;
    }

    public void setMp_orderid(String mp_orderid) {
        this.mp_orderid = mp_orderid;
    }

    public String getVerifycode_sendflag() {
        return verifycode_sendflag;
    }

    public void setVerifycode_sendflag(String verifycode_sendflag) {
        this.verifycode_sendflag = verifycode_sendflag;
    }

}

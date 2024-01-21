/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.pay19.out.model.req;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: ConfirmOrderReq.java, v 0.1 2015-8-7 上午10:03:51 BabyShark Exp $
 */
public class ConfirmOrderReq extends QuickPayBaseReq {

    /**  */
    private static final long serialVersionUID = -7777612618246294241L;
    private String            mx_userid;
    private String            order_id;
    private String            order_date;
    private String            amount;
    private String            mp_orderid;
    private String            verifyCode;

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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getMp_orderid() {
        return mp_orderid;
    }

    public void setMp_orderid(String mp_orderid) {
        this.mp_orderid = mp_orderid;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

}

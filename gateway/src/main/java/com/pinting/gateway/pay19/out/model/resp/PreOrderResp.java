/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.pay19.out.model.resp;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: PreOrderResp.java, v 0.1 2015-8-6 下午4:52:08 BabyShark Exp $
 */
public class PreOrderResp extends QuickPayBaseResp {

    /**  */
    private static final long serialVersionUID = 4527220489085866461L;
    private String            mx_userid;
    private String            order_id;
    private String            order_date;
    private String            mp_orderid;
    private String            signUrl;

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

    public String getSignUrl() {
        return signUrl;
    }

    public void setSignUrl(String signUrl) {
        this.signUrl = signUrl;
    }

}

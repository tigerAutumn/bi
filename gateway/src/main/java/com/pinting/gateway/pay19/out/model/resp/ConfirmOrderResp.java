/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.pay19.out.model.resp;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: ConfirmOrderResp.java, v 0.1 2015-8-7 上午10:06:36 BabyShark Exp $
 */
public class ConfirmOrderResp extends QuickPayBaseResp {

    /**  */
    private static final long serialVersionUID = -580320362578238246L;
    private String            req_status;
    private String            trade_result;
    private String            mx_userid;
    private String            order_id;
    private String            order_date;
    private String            mp_orderid;
    private String            fin_time;

    public String getReq_status() {
        return req_status;
    }

    public void setReq_status(String req_status) {
        this.req_status = req_status;
    }

    public String getTrade_result() {
        return trade_result;
    }

    public void setTrade_result(String trade_result) {
        this.trade_result = trade_result;
    }

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

    public String getFin_time() {
        return fin_time;
    }

    public void setFin_time(String fin_time) {
        this.fin_time = fin_time;
    }

}

/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.pay19.in.model.req;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: PayNoticeReq.java, v 0.1 2015-8-7 下午1:37:11 BabyShark Exp $
 */
public class PayNoticeReq extends QuickPayBaseReq {

    /**  */
    private static final long serialVersionUID = -1359810714583586228L;
    private String            mx_userid;
    private String            mx_account_id;
    private String            order_id;
    private String            order_date;
    private String            order_sub_time;
    private String            order_fin_time;
    private String            amount;
    private String            currency;
    private String            mp_orderid;
    private String            trade_type;
    private String            order_pname;
    private String            order_remark_desc;
    private String            common_retrieve_param;

    public String getMx_userid() {
        return mx_userid;
    }

    public void setMx_userid(String mx_userid) {
        this.mx_userid = mx_userid;
    }

    public String getMx_account_id() {
        return mx_account_id;
    }

    public void setMx_account_id(String mx_account_id) {
        this.mx_account_id = mx_account_id;
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

    public String getOrder_sub_time() {
        return order_sub_time;
    }

    public void setOrder_sub_time(String order_sub_time) {
        this.order_sub_time = order_sub_time;
    }

    public String getOrder_fin_time() {
        return order_fin_time;
    }

    public void setOrder_fin_time(String order_fin_time) {
        this.order_fin_time = order_fin_time;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getMp_orderid() {
        return mp_orderid;
    }

    public void setMp_orderid(String mp_orderid) {
        this.mp_orderid = mp_orderid;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getOrder_pname() {
        return order_pname;
    }

    public void setOrder_pname(String order_pname) {
        this.order_pname = order_pname;
    }

    public String getOrder_remark_desc() {
        return order_remark_desc;
    }

    public void setOrder_remark_desc(String order_remark_desc) {
        this.order_remark_desc = order_remark_desc;
    }

    public String getCommon_retrieve_param() {
        return common_retrieve_param;
    }

    public void setCommon_retrieve_param(String common_retrieve_param) {
        this.common_retrieve_param = common_retrieve_param;
    }

    @Override
    public String toString() {
        return "19付支付通知请求数据： 【mx_userid=" + mx_userid + ", mx_account_id=" + mx_account_id
               + ", order_id=" + order_id + ", order_date=" + order_date + ", order_sub_time="
               + order_sub_time + ", order_fin_time=" + order_fin_time + ", amount=" + amount
               + ", currency=" + currency + ", mp_orderid=" + mp_orderid + ", trade_type="
               + trade_type + ", order_pname=" + order_pname + ", order_remark_desc="
               + order_remark_desc + ", common_retrieve_param=" + common_retrieve_param
               + ", status=" + getStatus() + ", error_code=" + getError_code() + ", verifystring="
               + getVerifystring() + "】";
    }
}

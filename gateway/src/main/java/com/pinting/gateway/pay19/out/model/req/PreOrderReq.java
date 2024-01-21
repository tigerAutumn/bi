/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.pay19.out.model.req;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: PreOrder.java, v 0.1 2015-8-6 下午4:44:43 BabyShark Exp $
 */
public class PreOrderReq extends QuickPayBaseReq {

    /**  */
    private static final long serialVersionUID = -8660584556233842500L;
    private String            mx_userid;
    private String            mx_account_id;
    private String            user_account_id;
    private String            bind_sno;
    private String            order_id;
    private String            order_date;
    private String            amount;
    private String            order_pname;
    private String            order_pdesc;
    private String            pc_id;
    private String            bank_card_num;
    private String            cvv2;
    private String            valid_date;
    private String            card_holder;
    private String            id_type;
    private String            id_cardnum;
    private String            mobile;
    private String            currency;
    private String            acct_type;
    private String            acct_attr;
    private String            trade_type;
    private String            trade_desc;
    private String            page_notify_url;
    private String            notify_url;
    private String            is_bind;
    private String            mx_goods_display_url;
    private String            common_retrieve_param;
    private String            common_busi_expend_param;
    private String            overtime_interval;
    private String            order_desc;
    private String            order_remark_desc;
    private String            isFixBindInfo;
    private String            mx_goods_type;
    private String            reserved;

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

    public String getUser_account_id() {
        return user_account_id;
    }

    public void setUser_account_id(String user_account_id) {
        this.user_account_id = user_account_id;
    }

    public String getBind_sno() {
        return bind_sno;
    }

    public void setBind_sno(String bind_sno) {
        this.bind_sno = bind_sno;
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

    public String getOrder_pname() {
        return order_pname;
    }

    public void setOrder_pname(String order_pname) {
        this.order_pname = order_pname;
    }

    public String getOrder_pdesc() {
        return order_pdesc;
    }

    public void setOrder_pdesc(String order_pdesc) {
        this.order_pdesc = order_pdesc;
    }

    public String getPc_id() {
        return pc_id;
    }

    public void setPc_id(String pc_id) {
        this.pc_id = pc_id;
    }

    public String getBank_card_num() {
        return bank_card_num;
    }

    public void setBank_card_num(String bank_card_num) {
        this.bank_card_num = bank_card_num;
    }

    public String getCvv2() {
        return cvv2;
    }

    public void setCvv2(String cvv2) {
        this.cvv2 = cvv2;
    }

    public String getValid_date() {
        return valid_date;
    }

    public void setValid_date(String valid_date) {
        this.valid_date = valid_date;
    }

    public String getCard_holder() {
        return card_holder;
    }

    public void setCard_holder(String card_holder) {
        this.card_holder = card_holder;
    }

    public String getId_type() {
        return id_type;
    }

    public void setId_type(String id_type) {
        this.id_type = id_type;
    }

    public String getId_cardnum() {
        return id_cardnum;
    }

    public void setId_cardnum(String id_cardnum) {
        this.id_cardnum = id_cardnum;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAcct_type() {
        return acct_type;
    }

    public void setAcct_type(String acct_type) {
        this.acct_type = acct_type;
    }

    public String getAcct_attr() {
        return acct_attr;
    }

    public void setAcct_attr(String acct_attr) {
        this.acct_attr = acct_attr;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getTrade_desc() {
        return trade_desc;
    }

    public void setTrade_desc(String trade_desc) {
        this.trade_desc = trade_desc;
    }

    public String getPage_notify_url() {
        return page_notify_url;
    }

    public void setPage_notify_url(String page_notify_url) {
        this.page_notify_url = page_notify_url;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getIs_bind() {
        return is_bind;
    }

    public void setIs_bind(String is_bind) {
        this.is_bind = is_bind;
    }

    public String getMx_goods_display_url() {
        return mx_goods_display_url;
    }

    public void setMx_goods_display_url(String mx_goods_display_url) {
        this.mx_goods_display_url = mx_goods_display_url;
    }

    public String getCommon_retrieve_param() {
        return common_retrieve_param;
    }

    public void setCommon_retrieve_param(String common_retrieve_param) {
        this.common_retrieve_param = common_retrieve_param;
    }

    public String getCommon_busi_expend_param() {
        return common_busi_expend_param;
    }

    public void setCommon_busi_expend_param(String common_busi_expend_param) {
        this.common_busi_expend_param = common_busi_expend_param;
    }

    public String getOvertime_interval() {
        return overtime_interval;
    }

    public void setOvertime_interval(String overtime_interval) {
        this.overtime_interval = overtime_interval;
    }

    public String getOrder_desc() {
        return order_desc;
    }

    public void setOrder_desc(String order_desc) {
        this.order_desc = order_desc;
    }

    public String getOrder_remark_desc() {
        return order_remark_desc;
    }

    public void setOrder_remark_desc(String order_remark_desc) {
        this.order_remark_desc = order_remark_desc;
    }

    public String getIsFixBindInfo() {
        return isFixBindInfo;
    }

    public void setIsFixBindInfo(String isFixBindInfo) {
        this.isFixBindInfo = isFixBindInfo;
    }

    public String getMx_goods_type() {
        return mx_goods_type;
    }

    public void setMx_goods_type(String mx_goods_type) {
        this.mx_goods_type = mx_goods_type;
    }

    public String getReserved() {
        return reserved;
    }

    public void setReserved(String reserved) {
        this.reserved = reserved;
    }

}

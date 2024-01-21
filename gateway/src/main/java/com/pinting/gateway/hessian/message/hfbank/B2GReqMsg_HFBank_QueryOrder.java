package com.pinting.gateway.hessian.message.hfbank;

import com.pinting.core.hessian.msg.ReqMsg;

import java.util.Date;

/**
 * 订单状态查询请求信息
 * Created by shh on 2017/4/3.
 */
public class B2GReqMsg_HFBank_QueryOrder extends ReqMsg {

    private static final long serialVersionUID = -342978284453211374L;

    /* 查询的订单号（针对批量接口，对应detail_no） */
    private String query_order_no;
    /* 订单号 */
    private String order_no;
    /* 商户交易日期 YYYYMMdd */
    private Date partner_trans_date;
    /* 商户交易时间 HHmmss */
    private Date partner_trans_time;

    public String getQuery_order_no() {
        return query_order_no;
    }

    public void setQuery_order_no(String query_order_no) {
        this.query_order_no = query_order_no;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public Date getPartner_trans_date() {
        return partner_trans_date;
    }

    public void setPartner_trans_date(Date partner_trans_date) {
        this.partner_trans_date = partner_trans_date;
    }

    public Date getPartner_trans_time() {
        return partner_trans_time;
    }

    public void setPartner_trans_time(Date partner_trans_time) {
        this.partner_trans_time = partner_trans_time;
    }
}

package com.pinting.gateway.hessian.message.hfbank;

import com.pinting.core.hessian.msg.ReqMsg;

import java.util.Date;

/**
 * 标的账户余额查询请求信息
 * Created by shh on 2017/4/3.
 */
public class B2GReqMsg_HFBank_QueryProductBalance extends ReqMsg {

    private static final long serialVersionUID = 3977100029379553284L;

    /* 账户编号 */
    private String prod_id;
    /* 现金01   在途02 */
    private String type;

    /* 订单号 */
    private String order_no;

    /* 商户交易日期 YYYYMMdd */
    private Date partner_trans_date;

    /* 商户交易时间 HHmmss */
    private Date partner_trans_time;

    public String getProd_id() {
        return prod_id;
    }

    public void setProd_id(String prod_id) {
        this.prod_id = prod_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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


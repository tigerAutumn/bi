package com.pinting.gateway.hessian.message.hfbank;

import com.pinting.core.hessian.msg.ReqMsg;

import java.util.Date;

/**
 * Author:      shh
 * Date:        2017/5/9
 * Description:
 */
public class B2GReqMsg_HFBank_BalanceInfo extends ReqMsg {

    private static final long serialVersionUID = 6548826146116086130L;

    /* 订单号 */
    private String order_no;

    /* 商户交易日期 YYYYMMdd */
    private Date partner_trans_date;

    /* 商户交易时间 HHmmss */
    private Date partner_trans_time;

    /* 对账日期(yyyyMMdd) */
    private Date paycheck_date;

    /* 是否预对账0-不是，1-是 */
    private String precheck_flag;

    /* 开始时间(预对账必输：yyyyMMddHHmmss) */
    private Date begin_time;

    /* 结束时间(预对账必输：yyyyMMddHHmmss) */
    private Date end_time;

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

    public Date getPaycheck_date() {
        return paycheck_date;
    }

    public void setPaycheck_date(Date paycheck_date) {
        this.paycheck_date = paycheck_date;
    }

    public String getPrecheck_flag() {
        return precheck_flag;
    }

    public void setPrecheck_flag(String precheck_flag) {
        this.precheck_flag = precheck_flag;
    }

    public Date getBegin_time() {
        return begin_time;
    }

    public void setBegin_time(Date begin_time) {
        this.begin_time = begin_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }
}

package com.pinting.gateway.hessian.message.hfbank;

import com.pinting.core.hessian.msg.ReqMsg;

import java.util.Date;

/**
 * 账户余额明细查询请求信息
 * Created by shh on 2017/4/3.
 */
public class B2GReqMsg_HFBank_QueryAccountLeftAmountInfo extends ReqMsg {

    private static final long serialVersionUID = -6726094681124880616L;

    /* 账户编号 */
    private String account;
    /* 平台（1--自有， 11--在途垫付）  个人（投资账户 12、融资 13） */
    private String acct_type;
    /* 针对个人（现金01、在途02） */
    private String fund_type;
    /* 订单号 */
    private String order_no;
    /* 商户交易日期 YYYYMMdd */
    private Date partner_trans_date;
    /* 商户交易时间 HHmmss */
    private Date partner_trans_time;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAcct_type() {
        return acct_type;
    }

    public void setAcct_type(String acct_type) {
        this.acct_type = acct_type;
    }

    public String getFund_type() {
        return fund_type;
    }

    public void setFund_type(String fund_type) {
        this.fund_type = fund_type;
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

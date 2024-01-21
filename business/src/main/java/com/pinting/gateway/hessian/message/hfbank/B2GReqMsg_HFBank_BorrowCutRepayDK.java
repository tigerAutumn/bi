package com.pinting.gateway.hessian.message.hfbank;

import com.pinting.core.hessian.msg.ReqMsg;
import com.pinting.gateway.hessian.message.hfbank.model.BorrowCutRepayPlatcust;

import java.util.Date;
import java.util.List;

/**
 * Author:      cyb
 * Date:        2017/4/3
 * Description: 借款人扣款还款请求信息
 */
public class B2GReqMsg_HFBank_BorrowCutRepayDK extends ReqMsg {

    private static final long serialVersionUID = -1193257439522308331L;
    /* 总金额 */
    private Double amt;
    /* 异步通知地址 */
    private String notify_url;
    /* 备注 */
    private String remark;
    /* 平台客户入账列表 */
    private List<BorrowCutRepayPlatcust> platcustList;

    /* 订单号 */
    private String order_no;

    /* 商户交易日期 YYYYMMdd */
    private Date partner_trans_date;

    /* 商户交易时间 HHmmss */
    private Date partner_trans_time;

    public Double getAmt() {
        return amt;
    }

    public void setAmt(Double amt) {
        this.amt = amt;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<BorrowCutRepayPlatcust> getPlatcustList() {
        return platcustList;
    }

    public void setPlatcustList(List<BorrowCutRepayPlatcust> platcustList) {
        this.platcustList = platcustList;
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

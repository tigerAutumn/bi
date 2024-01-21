package com.pinting.gateway.hessian.message.hfbank;

import com.pinting.core.hessian.msg.ReqMsg;
import com.pinting.gateway.hessian.message.hfbank.model.BatchRegistExt3Detail;

import java.util.Date;
import java.util.List;

/**
 * Author:      cyb
 * Date:        2017/4/3
 * Description: 批量开户（实名认证）针对借款人请求信息
 */
public class B2GReqMsg_HFBank_BatchBindCardRealNameAuth extends ReqMsg {

    private static final long serialVersionUID = -6236004750729650762L;

    /* 总数量 */
    private Integer total_num;
    /* JsonArray，批量数据 */
    private List<BatchRegistExt3Detail> data;

    /* 订单号 */
    private String order_no;

    /* 商户交易日期 YYYYMMdd */
    private Date partner_trans_date;

    /* 商户交易时间 HHmmss */
    private Date partner_trans_time;

    public Integer getTotal_num() {
        return total_num;
    }

    public void setTotal_num(Integer total_num) {
        this.total_num = total_num;
    }

    public List<BatchRegistExt3Detail> getData() {
        return data;
    }

    public void setData(List<BatchRegistExt3Detail> data) {
        this.data = data;
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

package com.pinting.gateway.hessian.message.hfbank;

import com.pinting.core.hessian.msg.ReqMsg;
import com.pinting.gateway.hessian.message.hfbank.model.BatchRegistExtDetail;

import java.util.Date;
import java.util.List;

/**
 * Author:      cyb
 * Date:        2017/4/1
 * Description: 批量开户(四要素绑卡)请求数据
 */
public class B2GReqMsg_HFBank_BatchBindCard4Elements extends ReqMsg {

    private static final long serialVersionUID = -5229445278834277056L;

    /* 总数量 */
    private Integer total_num;

    /* JsonArray，批量明细数据 */
    private List<BatchRegistExtDetail> data;

    /* 订单号 */
    private String order_no;

    /* 商户交易日期 YYYYMMdd */
    private Date partner_trans_date;

    /* 商户交易时间 HHmmss */
    private Date partner_trans_time;


    public List<BatchRegistExtDetail> getData() {
        return data;
    }

    public void setData(List<BatchRegistExtDetail> data) {
        this.data = data;
    }

    public Integer getTotal_num() {
        return total_num;
    }

    public void setTotal_num(Integer total_num) {
        this.total_num = total_num;
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

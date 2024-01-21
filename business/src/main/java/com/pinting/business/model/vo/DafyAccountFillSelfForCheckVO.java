package com.pinting.business.model.vo;

import java.util.Date;

/**
 * Author:      cyb
 * Date:        2017/2/9
 * Description: 补账对账VO
 */
public class DafyAccountFillSelfForCheckVO {

    private String partner_code;
    private String trans_type;
    private Double amount;
    private String pay_order_no;
    private Date over_time;
    private Date apply_time;
    private Date finish_time;
    private String channel;
    private String pay_type;

    public String getPartner_code() {
        return partner_code;
    }

    public void setPartner_code(String partner_code) {
        this.partner_code = partner_code;
    }

    public String getTrans_type() {
        return trans_type;
    }

    public void setTrans_type(String trans_type) {
        this.trans_type = trans_type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getPay_order_no() {
        return pay_order_no;
    }

    public void setPay_order_no(String pay_order_no) {
        this.pay_order_no = pay_order_no;
    }

    public Date getOver_time() {
        return over_time;
    }

    public void setOver_time(Date over_time) {
        this.over_time = over_time;
    }

    public Date getApply_time() {
        return apply_time;
    }

    public void setApply_time(Date apply_time) {
        this.apply_time = apply_time;
    }

    public Date getFinish_time() {
        return finish_time;
    }

    public void setFinish_time(Date finish_time) {
        this.finish_time = finish_time;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }
}

package com.pinting.business.model.vo;

import java.util.Date;

/**
 * Author:      cyb
 * Date:        2017/2/9
 * Description: 成功云贷营收对账单VO
 */
public class DafyRevenueSelfForCheckVO {

    private String partner_code;
    private String trans_type;
    private String partner_business_flag;
    private Double amount;
    private String order_no;
    private Date settle_date;
    private Date create_time;
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

    public String getPartner_business_flag() {
		return partner_business_flag;
	}

	public void setPartner_business_flag(String partner_business_flag) {
		this.partner_business_flag = partner_business_flag;
	}

	public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public Date getSettle_date() {
        return settle_date;
    }

    public void setSettle_date(Date settle_date) {
        this.settle_date = settle_date;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
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

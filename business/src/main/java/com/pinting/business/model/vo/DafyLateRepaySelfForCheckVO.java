package com.pinting.business.model.vo;

import java.util.Date;

/**
 * Author:      cyb
 * Date:        2017/2/9
 * Description: 成功代偿对账单VO
 */
public class DafyLateRepaySelfForCheckVO {

    private String partner_user_id;
    private String partner_loan_id;
    private String partner_repay_id;
    private String partner_code;
    private String trans_type;
    private String partner_business_flag;
    private Double total;
    private Double principal;
    private Double interest;
    private Double principal_overdue;
    private Double interest_overdue;
    private String reservedField1;
    private String pay_order_no;
    private String order_no;
    private Date apply_time;
    private Date finish_time;
    private String channel;
    private String pay_type;

    public String getPartner_user_id() {
        return partner_user_id;
    }

    public void setPartner_user_id(String partner_user_id) {
        this.partner_user_id = partner_user_id;
    }

    public String getPartner_loan_id() {
        return partner_loan_id;
    }

    public void setPartner_loan_id(String partner_loan_id) {
        this.partner_loan_id = partner_loan_id;
    }

    public String getPartner_repay_id() {
        return partner_repay_id;
    }

    public void setPartner_repay_id(String partner_repay_id) {
        this.partner_repay_id = partner_repay_id;
    }

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

	public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getPrincipal() {
        return principal;
    }

    public void setPrincipal(Double principal) {
        this.principal = principal;
    }

    public Double getInterest() {
        return interest;
    }

    public void setInterest(Double interest) {
        this.interest = interest;
    }

    public Double getPrincipal_overdue() {
        return principal_overdue;
    }

    public void setPrincipal_overdue(Double principal_overdue) {
        this.principal_overdue = principal_overdue;
    }

    public Double getInterest_overdue() {
        return interest_overdue;
    }

    public void setInterest_overdue(Double interest_overdue) {
        this.interest_overdue = interest_overdue;
    }

    public String getReservedField1() {
        return reservedField1;
    }

    public void setReservedField1(String reservedField1) {
        this.reservedField1 = reservedField1;
    }

    public String getPay_order_no() {
        return pay_order_no;
    }

    public void setPay_order_no(String pay_order_no) {
        this.pay_order_no = pay_order_no;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
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

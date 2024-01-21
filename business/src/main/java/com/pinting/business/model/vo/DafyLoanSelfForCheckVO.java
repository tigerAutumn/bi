package com.pinting.business.model.vo;

import java.util.Date;

/**
 * Author:      cyb
 * Date:        2017/2/9
 * Description: 生成借款+借款手续费对账单VO
 */
public class DafyLoanSelfForCheckVO {

    private String ln_user_id;
    private String partner_loan_id;
    private String partner_code;
    private String trans_type;
    private String partner_business_flag;
    private Double apply_amount;
    private Double approve_amount;
    private Double head_fee;
    private Integer period;
    private String partner_order_no;
    private String pay_order_no;
    private Date apply_time;
    private Date loan_time;
    private String channel;
    private String status;
    private String return_msg;
    
    private Date loan_launch_time;
    private Double service_fee;
    private String partner_repay_id;
    
    public String getLn_user_id() {
        return ln_user_id;
    }

    public void setLn_user_id(String ln_user_id) {
        this.ln_user_id = ln_user_id;
    }

    public String getPartner_loan_id() {
        return partner_loan_id;
    }

    public void setPartner_loan_id(String partner_loan_id) {
        this.partner_loan_id = partner_loan_id;
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

	public Double getApprove_amount() {
        return approve_amount;
    }

    public void setApprove_amount(Double approve_amount) {
        this.approve_amount = approve_amount;
    }

    public Double getApply_amount() {
        return apply_amount;
    }

    public void setApply_amount(Double apply_amount) {
        this.apply_amount = apply_amount;
    }

    public Double getHead_fee() {
        return head_fee;
    }

    public void setHead_fee(Double head_fee) {
        this.head_fee = head_fee;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public String getPartner_order_no() {
        return partner_order_no;
    }

    public void setPartner_order_no(String partner_order_no) {
        this.partner_order_no = partner_order_no;
    }

    public String getPay_order_no() {
        return pay_order_no;
    }

    public void setPay_order_no(String pay_order_no) {
        this.pay_order_no = pay_order_no;
    }

    public Date getApply_time() {
        return apply_time;
    }

    public void setApply_time(Date apply_time) {
        this.apply_time = apply_time;
    }

    public Date getLoan_time() {
        return loan_time;
    }

    public void setLoan_time(Date loan_time) {
        this.loan_time = loan_time;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReturn_msg() {
        return return_msg;
    }

    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }

	public Date getLoan_launch_time() {
		return loan_launch_time;
	}

	public void setLoan_launch_time(Date loan_launch_time) {
		this.loan_launch_time = loan_launch_time;
	}

	public Double getService_fee() {
		return service_fee;
	}

	public void setService_fee(Double service_fee) {
		this.service_fee = service_fee;
	}

	public String getPartner_repay_id() {
		return partner_repay_id;
	}

	public void setPartner_repay_id(String partner_repay_id) {
		this.partner_repay_id = partner_repay_id;
	}
    
}

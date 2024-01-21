package com.pinting.gateway.hessian.message.hfbank;

import com.pinting.core.hessian.msg.ReqMsg;

import java.util.Date;

public class B2GReqMsg_HFBank_PlatformTransfer extends ReqMsg {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1791899281919027416L;
	/**平台账户(01 为平台账户，从平台自有资金转账)*/
	private 	String 		plat_account;
	/**金额*/
	private 	String 		amount;
	/**电子账户*/
	private 	String 		platcust;
	/**原因*/
	private 	String 		remark;
	/**原因类型*/
	private 	String		cause_type;
    /* 订单号 */
    private String order_no;

    /* 商户交易日期 YYYYMMdd */
    private Date partner_trans_date;

    /* 商户交易时间 HHmmss */
    private Date partner_trans_time;

	public String getCause_type() {
		return cause_type;
	}
	public void setCause_type(String cause_type) {
		this.cause_type = cause_type;
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

	public String getPlat_account() {
		return plat_account;
	}
	public void setPlat_account(String plat_account) {
		this.plat_account = plat_account;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getPlatcust() {
		return platcust;
	}
	public void setPlatcust(String platcust) {
		this.platcust = platcust;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}

package com.pinting.gateway.hessian.message.hfbank;

import com.pinting.core.hessian.msg.ReqMsg;

import java.util.Date;

public class B2GReqMsg_HFBank_ModifyPayOutAcct extends ReqMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1472353585795507574L;
	/**标的编号*/
	private 	String 		prod_id;
	/**产品收款人开户行（八位编号）*/
	private 	String 		open_branch;
	/**产品收款人银行卡号*/
	private 	String 		withdraw_account;
	/**卡类型(1-个人 2-企业)*/
	private 	String 		account_type;
	/**产品收款人姓名*/
	private 	String 		payee_name;
	/**备注*/
	private 	String 		remark;
	
    /* 订单号 */
    private String order_no;

    /* 商户交易日期 YYYYMMdd */
    private Date partner_trans_date;

    /* 商户交易时间 HHmmss */
    private Date partner_trans_time;

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

	public String getProd_id() {
		return prod_id;
	}
	public void setProd_id(String prod_id) {
		this.prod_id = prod_id;
	}
	public String getOpen_branch() {
		return open_branch;
	}
	public void setOpen_branch(String open_branch) {
		this.open_branch = open_branch;
	}
	public String getWithdraw_account() {
		return withdraw_account;
	}
	public void setWithdraw_account(String withdraw_account) {
		this.withdraw_account = withdraw_account;
	}
	public String getAccount_type() {
		return account_type;
	}
	public void setAccount_type(String account_type) {
		this.account_type = account_type;
	}
	public String getPayee_name() {
		return payee_name;
	}
	public void setPayee_name(String payee_name) {
		this.payee_name = payee_name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}

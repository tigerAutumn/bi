package com.pinting.gateway.hessian.message.hfbank.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @project gateway
 * @title PublishFinancingInfo.java
 * @author Dragon & cat
 * @date 2017-4-1
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description  恒丰银行存管 ，标的发布请求融资信息
 */ 
public class PublishFinancingInfo  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3303598595801313941L;

	/*融资人的平台客户编号*/
	private		String 		cust_no;
	
	/*申请日期（YYYY-MM-DD）*/
	private		Date 		reg_date;
	/*申请时间（HH:mm:ss）*/
	private		Date 		reg_time;
	/*融资利息（eg：0.12）*/
	private		String 		financ_int;
	/*借款用途[限制100字符以内]*/
	private		String 		financ_purpose;
	/*用款日期（YYYY-MM-DD*/
	private		Date 		use_date;
	/*产品收款人开户行*/
	private		String 		open_branch;
	/*产品收款人银行卡号*/
	private		String 		withdraw_account;
	/*卡类型(1-个人 2-企业)*/
	private		String 		account_type;
	/*产品收款人姓名*/
	private		String 		payee_name;
	/*融资金额*/
	private		Double 		financ_amt;
	/*手续费*/
	private		Double 		fee_int;
	public String getCust_no() {
		return cust_no;
	}
	public void setCust_no(String cust_no) {
		this.cust_no = cust_no;
	}

	public String getFinanc_int() {
		return financ_int;
	}
	public void setFinanc_int(String financ_int) {
		this.financ_int = financ_int;
	}
	public String getFinanc_purpose() {
		return financ_purpose;
	}
	public void setFinanc_purpose(String financ_purpose) {
		this.financ_purpose = financ_purpose;
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
	public Date getReg_date() {
		return reg_date;
	}
	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}
	public Date getReg_time() {
		return reg_time;
	}
	public void setReg_time(Date reg_time) {
		this.reg_time = reg_time;
	}
	public Date getUse_date() {
		return use_date;
	}
	public void setUse_date(Date use_date) {
		this.use_date = use_date;
	}
	public Double getFinanc_amt() {
		return financ_amt;
	}
	public void setFinanc_amt(Double financ_amt) {
		this.financ_amt = financ_amt;
	}
	public Double getFee_int() {
		return fee_int;
	}
	public void setFee_int(Double fee_int) {
		this.fee_int = fee_int;
	}
}

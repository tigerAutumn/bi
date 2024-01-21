package com.pinting.gateway.hfbank.out.model;

import java.io.Serializable;

/**
 * 
 * @project gateway
 * @title ChargeOffReqDetail.java
 * @author Dragon & cat
 * @date 2017-4-1
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description 恒丰银行存管，标的出账请求-资金出账明细（一个标的对应多个融资人）
 */ 
public class ChargeOffReqDetail implements  Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8733317993955619133L;
	/**2:垫付，1：不垫付*/
	private  	String 		is_advance ;
	/**融资人的平台客户号*/
	private  	String 		platcust ;
	/**真实融资金额（出账金额）*/
	private  	String 		out_amt ;
	/**产品收款人开户行*/
	private  	String 		open_branch;
	/**产品收款人银行卡号*/
	private  	String 		withdraw_account;
	/**产品收款人户名*/
	private  	String 		payee_name  ;
	/**银行编号 对公出账必填*/
	private  	String 		bank_id ;
	/**公私标示(0-个人 1-公司)*/
	private  	String 		client_property;
	/**城市编码（富友必填）*/
	private  	String 		city_code ;
	/* 交易类型（代表 pay行内，payReal 代表跨行） */
	private String tran_type;
	/* 银行行号（行内支付需要填）*/
	private String bank_code;
	/* 银行名称（行内支付需要填） */
	private String bank_name;

	public String getTran_type() {
		return tran_type;
	}

	public void setTran_type(String tran_type) {
		this.tran_type = tran_type;
	}

	public String getBank_code() {
		return bank_code;
	}

	public void setBank_code(String bank_code) {
		this.bank_code = bank_code;
	}

	public String getBank_name() {
		return bank_name;
	}

	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}
	public String getIs_advance() {
		return is_advance;
	}
	public void setIs_advance(String is_advance) {
		this.is_advance = is_advance;
	}
	public String getPlatcust() {
		return platcust;
	}
	public void setPlatcust(String platcust) {
		this.platcust = platcust;
	}
	public String getOut_amt() {
		return out_amt;
	}
	public void setOut_amt(String out_amt) {
		this.out_amt = out_amt;
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
	public String getPayee_name() {
		return payee_name;
	}
	public void setPayee_name(String payee_name) {
		this.payee_name = payee_name;
	}
	public String getBank_id() {
		return bank_id;
	}
	public void setBank_id(String bank_id) {
		this.bank_id = bank_id;
	}
	public String getClient_property() {
		return client_property;
	}
	public void setClient_property(String client_property) {
		this.client_property = client_property;
	}
	public String getCity_code() {
		return city_code;
	}
	public void setCity_code(String city_code) {
		this.city_code = city_code;
	}
	
	
}

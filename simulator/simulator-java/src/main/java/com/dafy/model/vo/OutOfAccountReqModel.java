package com.dafy.model.vo;

import java.util.Date;

/**
 * 
 * @project gateway
 * @title OutOfAccountReqModel.java
 * @author Dragon & cat
 * @date 2017-4-1
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description 恒丰存管-成标出账通知请求
 */
public class OutOfAccountReqModel extends BaseReqModel {
	/**出账金额*/
	private  	String 		out_amt;
	/**融资人平台客户编号*/
	private  	String 		platcust;
	/**开户行*/
	private  	String 		open_branch;
	/**收款人银行卡号*/
	private  	String 		withdraw_account;
	/**收款人姓名*/
	private  	String 		payee_name;
	/**支付完成日期*/
	private  	String 		pay_finish_date;
	/**支付完成时间*/
	private  	String 		pay_finish_time;
	/**交易状态 1-出账成功 2-失败*/
	private  	String 		order_status;
	/**失败原因*/
	private  	String 		error_info;
	/**失败编码*/
	private  	String 		error_no;
	/** 支付通道流水号 */
	private     String  	host_req_serial_no;
	/** 行内支付才有（03小额 06超网 09大额） */
	private     String  	pay_path;
	
	public String getOut_amt() {
		return out_amt;
	}
	public void setOut_amt(String out_amt) {
		this.out_amt = out_amt;
	}
	public String getPlatcust() {
		return platcust;
	}
	public void setPlatcust(String platcust) {
		this.platcust = platcust;
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
	public String getPay_finish_date() {
		return pay_finish_date;
	}
	public void setPay_finish_date(String pay_finish_date) {
		this.pay_finish_date = pay_finish_date;
	}
	public String getPay_finish_time() {
		return pay_finish_time;
	}
	public void setPay_finish_time(String pay_finish_time) {
		this.pay_finish_time = pay_finish_time;
	}
	public String getOrder_status() {
		return order_status;
	}
	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}
	public String getError_info() {
		return error_info;
	}
	public void setError_info(String error_info) {
		this.error_info = error_info;
	}
	public String getError_no() {
		return error_no;
	}
	public void setError_no(String error_no) {
		this.error_no = error_no;
	}
	public String getHost_req_serial_no() {
		return host_req_serial_no;
	}
	public void setHost_req_serial_no(String host_req_serial_no) {
		this.host_req_serial_no = host_req_serial_no;
	}
	public String getPay_path() {
		return pay_path;
	}
	public void setPay_path(String pay_path) {
		this.pay_path = pay_path;
	}
	
}

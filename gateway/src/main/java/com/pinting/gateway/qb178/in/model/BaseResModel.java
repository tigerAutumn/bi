package com.pinting.gateway.qb178.in.model;
/**
 * 钱报178APP平台公共请求类回参
 * @project gateway
 * @title BaseResModel.java
 * @author Dragon & cat
 * @date 2017-7-28
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
public class BaseResModel {

	/*总记录数*/
	private	Integer	total_num;
	/*当前页*/
	private	Integer	current_page;
	/*签名串*/
	private	String	cert_sign;
	/*错误码*/
	private	String	error_no;
	/*错误信息*/
	private	String	error_info;
	
	
	public Integer getTotal_num() {
		return total_num;
	}
	public void setTotal_num(Integer total_num) {
		this.total_num = total_num;
	}
	public Integer getCurrent_page() {
		return current_page;
	}
	public void setCurrent_page(Integer current_page) {
		this.current_page = current_page;
	}
	public String getCert_sign() {
		return cert_sign;
	}
	public void setCert_sign(String cert_sign) {
		this.cert_sign = cert_sign;
	}
	public String getError_no() {
		return error_no;
	}
	public void setError_no(String error_no) {
		this.error_no = error_no;
	}
	public String getError_info() {
		return error_info;
	}
	public void setError_info(String error_info) {
		this.error_info = error_info;
	}
}

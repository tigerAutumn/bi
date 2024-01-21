package com.pinting.gateway.qb178.in.model;
/**
 * 钱报178APP平台公共请求类入参
 * @project gateway
 * @title BaseReqModel.java
 * @author Dragon & cat
 * @date 2017-7-28
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
public class BaseReqModel {
	/*签名串*/
	private String cert_sign;
	/*页数，起始值为1*/
	private	Integer	page_num;
	/*每页记录数*/
	private	Integer page_size;
	public String getCert_sign() {
		return cert_sign;
	}
	public void setCert_sign(String cert_sign) {
		this.cert_sign = cert_sign;
	}
	public Integer getPage_num() {
		return page_num;
	}
	public void setPage_num(Integer page_num) {
		this.page_num = page_num;
	}
	public Integer getPage_size() {
		return page_size;
	}
	public void setPage_size(Integer page_size) {
		this.page_size = page_size;
	}
	
	
}

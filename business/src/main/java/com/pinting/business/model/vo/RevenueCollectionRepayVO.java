package com.pinting.business.model.vo;

import java.util.Date;
/**
 * 财务--赞分期营销代收代付
 * @author Dragon & cat
 * @date 2016-12-7
 */
public class RevenueCollectionRepayVO {

	/*序号*/
	private		Integer		rowno;
	/*用户名*/
	private		String		userName;
	/*手机号*/
	private		String		mobile;
	/*类型*/
	private		String		type;
	/*代收金额*/
	private		Double		revenueCollection;
	/*代付金额*/
	private		Double		revenuePay;
	/*产生时间*/
	private		Date		time;
	public Integer getRowno() {
		return rowno;
	}
	public void setRowno(Integer rowno) {
		this.rowno = rowno;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Double getRevenueCollection() {
		return revenueCollection;
	}
	public void setRevenueCollection(Double revenueCollection) {
		this.revenueCollection = revenueCollection;
	}
	public Double getRevenuePay() {
		return revenuePay;
	}
	public void setRevenuePay(Double revenuePay) {
		this.revenuePay = revenuePay;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	
}

package com.pinting.business.model.vo;

import java.util.Date;

/**
 *
 * @author SHENGP
 * @date  2017年6月16日 下午3:39:12
 */
public class InfluxCollectionRepayVO {
	
	/*序号*/
	private		Integer		rowno;
	/*用户名*/
	private		String		userName;
	/*手机号*/
	private		String		mobile;
	/*类型*/
	private		String		type;
	/*代收金额*/
	private		Double		influxCollection;
	/*代付金额*/
	private		Double		influxRepay;
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
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public Double getInfluxCollection() {
		return influxCollection;
	}
	public void setInfluxCollection(Double influxCollection) {
		this.influxCollection = influxCollection;
	}
	public Double getInfluxRepay() {
		return influxRepay;
	}
	public void setInfluxRepay(Double influxRepay) {
		this.influxRepay = influxRepay;
	}
	
}

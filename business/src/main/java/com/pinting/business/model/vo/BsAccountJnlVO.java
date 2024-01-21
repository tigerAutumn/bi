package com.pinting.business.model.vo;

import java.util.Date;

import com.pinting.business.model.BsAccountJnl;

public class BsAccountJnlVO extends BsAccountJnl{
	
	/**
	 * 序列化编号
	 */
	private static final long serialVersionUID = -7543196827265619895L;

	private Date beginTime;
	
	private Date overTime;
	
	private String orderNo;
	
	private String mobile;
	
	private String userName;
	
	private String bankName;
	
	private String bankCardNo;
	
	private String transCodes;
	
	/** 账务开始时间 */
	private Date startTransTime;
	
	/** 账务结束时间 */
	private Date endTransTime;
	
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getOverTime() {
		return overTime;
	}

	public void setOverTime(Date overTime) {
		this.overTime = overTime;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	public String getTransCodes() {
		return transCodes;
	}

	public void setTransCodes(String transCodes) {
		this.transCodes = transCodes;
	}

	public Date getStartTransTime() {
		return startTransTime;
	}

	public void setStartTransTime(Date startTransTime) {
		this.startTransTime = startTransTime;
	}

	public Date getEndTransTime() {
		return endTransTime;
	}

	public void setEndTransTime(Date endTransTime) {
		this.endTransTime = endTransTime;
	}
}

package com.pinting.business.model.vo;

import java.io.Serializable;

public class PlayerKillingVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 733177604129459909L;
	/*排名*/
	private		Integer		rowno;
	/*用户名*/
	private		String		userName;
	/*购买金额*/
	private		Double		buyAmount;
	/*对应奖励金额*/
	private 	Double		awardAmount;
	/*用户手机号*/
	private 	String 		mobile;
	/*用户id*/
	private		Integer 	userId;
	
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
	public Double getBuyAmount() {
		return buyAmount;
	}
	public void setBuyAmount(Double buyAmount) {
		this.buyAmount = buyAmount;
	}
	public Double getAwardAmount() {
		return awardAmount;
	}
	public void setAwardAmount(Double awardAmount) {
		this.awardAmount = awardAmount;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	
}

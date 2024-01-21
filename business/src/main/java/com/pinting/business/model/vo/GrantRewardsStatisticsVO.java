package com.pinting.business.model.vo;

import java.util.Date;

public class GrantRewardsStatisticsVO {
	
	private Integer rowno; //查询结果序号
	
	private String billNo;

	private String userName; //用户姓名
	
	private String mobile;
	
	private Date time; //购买时间
	
	private String customerCode; //客户代码
	
	private Double rewardsAmount;  //奖励金金额

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


	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public Double getRewardsAmount() {
		return rewardsAmount;
	}

	public void setRewardsAmount(Double rewardsAmount) {
		this.rewardsAmount = rewardsAmount;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	
}

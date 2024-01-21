package com.pinting.business.model.vo;

import java.util.Date;

public class FinancialRechargeRecordVO {

	private Integer rowno; //查询结果序号
    private String mobile;
    private String userName;
    private Integer rechargeTimes;
    private Integer rechargeTotalTimes;
    private String channelTransType;
    private Double transAmount;
    private String transCode;
    private Date createTime;
    private Date updateTime;
	public Integer getRowno() {
		return rowno;
	}
	public void setRowno(Integer rowno) {
		this.rowno = rowno;
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
	public Integer getRechargeTimes() {
		return rechargeTimes;
	}
	public void setRechargeTimes(Integer rechargeTimes) {
		this.rechargeTimes = rechargeTimes;
	}
	public Integer getRechargeTotalTimes() {
		return rechargeTotalTimes;
	}
	public void setRechargeTotalTimes(Integer rechargeTotalTimes) {
		this.rechargeTotalTimes = rechargeTotalTimes;
	}
	public String getChannelTransType() {
		return channelTransType;
	}
	public void setChannelTransType(String channelTransType) {
		this.channelTransType = channelTransType;
	}
	public Double getTransAmount() {
		return transAmount;
	}
	public void setTransAmount(Double transAmount) {
		this.transAmount = transAmount;
	}
	public String getTransCode() {
		return transCode;
	}
	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
    
    
}

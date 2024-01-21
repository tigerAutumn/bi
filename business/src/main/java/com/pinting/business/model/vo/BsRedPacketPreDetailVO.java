package com.pinting.business.model.vo;

import java.util.Date;

import com.pinting.business.model.BsRedPacketPreDetail;

public class BsRedPacketPreDetailVO extends BsRedPacketPreDetail {

    private String mobile;
    
    private String bankName;
    
    private Double recentTotalBuy;
    
    private Double totalBuy;
	
    private Integer countBuy;

    private String serialNo;

    private Date registerTime;

    private Date firstBuyDate;
    
    private Date lastBuyDate;
    
    private String agentName;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public Double getRecentTotalBuy() {
		return recentTotalBuy;
	}

	public void setRecentTotalBuy(Double recentTotalBuy) {
		this.recentTotalBuy = recentTotalBuy;
	}

	public Double getTotalBuy() {
		return totalBuy;
	}

	public void setTotalBuy(Double totalBuy) {
		this.totalBuy = totalBuy;
	}

	public Integer getCountBuy() {
		return countBuy;
	}

	public void setCountBuy(Integer countBuy) {
		this.countBuy = countBuy;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public Date getFirstBuyDate() {
		return firstBuyDate;
	}

	public void setFirstBuyDate(Date firstBuyDate) {
		this.firstBuyDate = firstBuyDate;
	}

	public Date getLastBuyDate() {
		return lastBuyDate;
	}

	public void setLastBuyDate(Date lastBuyDate) {
		this.lastBuyDate = lastBuyDate;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
    
    
}

package com.pinting.business.model.vo;

import java.util.Date;

/**
 * 渠道用户查询（秦皇岛）
 * @author SHENGUOPING
 * @date  2018年7月21日 下午3:53:28
 */
public class BsQhdUserAgentVO extends PageInfoObject {

	private static final long serialVersionUID = -7830601252431165892L;

	private String mobile;
	
	private String userName;
	
	private Double totalBalance;
	
	private Double canWithdraw;
	
	private Double currentInterest;
	
	private Double currentBanlace;
	
	private Double totalInterest;
	
	private Date registerTime;
	
	private Integer regTerminal; //注册终端
	
	private Date startRegisterTime; //注册开始时间
	
	private Date endRegisterTime; //注册结束时间

	private String distributionChannel;
	
	private Double jshBalance;

	private Double jljBalance;

	private Double authBalance;
	
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

	public Double getTotalBalance() {
		return totalBalance;
	}

	public void setTotalBalance(Double totalBalance) {
		this.totalBalance = totalBalance;
	}

	public Double getCanWithdraw() {
		return canWithdraw;
	}

	public void setCanWithdraw(Double canWithdraw) {
		this.canWithdraw = canWithdraw;
	}

	public Double getCurrentInterest() {
		return currentInterest;
	}

	public void setCurrentInterest(Double currentInterest) {
		this.currentInterest = currentInterest;
	}

	public Double getCurrentBanlace() {
		return currentBanlace;
	}

	public void setCurrentBanlace(Double currentBanlace) {
		this.currentBanlace = currentBanlace;
	}

	public Double getTotalInterest() {
		return totalInterest;
	}

	public void setTotalInterest(Double totalInterest) {
		this.totalInterest = totalInterest;
	}

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public Integer getRegTerminal() {
		return regTerminal;
	}

	public void setRegTerminal(Integer regTerminal) {
		this.regTerminal = regTerminal;
	}

	public Date getStartRegisterTime() {
		return startRegisterTime;
	}

	public void setStartRegisterTime(Date startRegisterTime) {
		this.startRegisterTime = startRegisterTime;
	}

	public Date getEndRegisterTime() {
		return endRegisterTime;
	}

	public void setEndRegisterTime(Date endRegisterTime) {
		this.endRegisterTime = endRegisterTime;
	}

	public String getDistributionChannel() {
		return distributionChannel;
	}

	public void setDistributionChannel(String distributionChannel) {
		this.distributionChannel = distributionChannel;
	}

	public Double getJshBalance() {
		return jshBalance;
	}

	public void setJshBalance(Double jshBalance) {
		this.jshBalance = jshBalance;
	}

	public Double getJljBalance() {
		return jljBalance;
	}

	public void setJljBalance(Double jljBalance) {
		this.jljBalance = jljBalance;
	}

	public Double getAuthBalance() {
		return authBalance;
	}

	public void setAuthBalance(Double authBalance) {
		this.authBalance = authBalance;
	}
	
}

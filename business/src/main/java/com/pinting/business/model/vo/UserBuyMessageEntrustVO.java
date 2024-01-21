package com.pinting.business.model.vo;

import java.util.Date;
import java.util.List;

public class UserBuyMessageEntrustVO extends PageInfoObject{

	/*用户ID*/
	private		Integer		userId;
	/*用户手机号*/
	private		String		mobile;
	/*用户姓名*/
	private		String		userName;
	/*资金方标记*/
	private		String		propertySymbol;
	/*订单号*/
	private		String		orderNo;
	/*期限*/
	private		Integer		term;
	/*利率*/
	private		Double		baseRate;
	/*利率区间开始*/
	private		Double		startRate;
	/*利率区间结束*/
	private		Double		endRate;
	
	/*金额*/
	private		Double		amount;
	
	/*金额开始*/
	private		Double		beginAmount;
	/*金额结束*/
	private		Double		endAmount;
	
	/*状态*/
	private		String		status;
	/*借款时间*/
	private		Date		loanTime;
	/*借款时间开始*/
	private		Date		loanBeginTime;
	/*借款时间结束*/
	private		Date		loanEndTime;
	
	/*结算时间*/
	private		Date 		repayTime;
	/*结算时间开始*/
	private		Date		repayBeginTime;
	/*结算时间结束*/
	private		Date		repayEndTime;
	
	/*渠道名称*/
	private		String		agentName;
	
	
	private 	String 		agentIds;
	/*非渠道编号*/
	private 	String 		nonAgentId;

	private 	List<Object> 		agentIdsObj;
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
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
	public String getPropertySymbol() {
		return propertySymbol;
	}
	public void setPropertySymbol(String propertySymbol) {
		this.propertySymbol = propertySymbol;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public Integer getTerm() {
		return term;
	}
	public void setTerm(Integer term) {
		this.term = term;
	}
	public Double getBaseRate() {
		return baseRate;
	}
	public void setBaseRate(Double baseRate) {
		this.baseRate = baseRate;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getLoanTime() {
		return loanTime;
	}
	public void setLoanTime(Date loanTime) {
		this.loanTime = loanTime;
	}
	public Date getRepayTime() {
		return repayTime;
	}
	public void setRepayTime(Date repayTime) {
		this.repayTime = repayTime;
	}
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	public Double getStartRate() {
		return startRate;
	}
	public void setStartRate(Double startRate) {
		this.startRate = startRate;
	}
	public Double getEndRate() {
		return endRate;
	}
	public void setEndRate(Double endRate) {
		this.endRate = endRate;
	}
	public Date getLoanBeginTime() {
		return loanBeginTime;
	}
	public void setLoanBeginTime(Date loanBeginTime) {
		this.loanBeginTime = loanBeginTime;
	}
	public Date getLoanEndTime() {
		return loanEndTime;
	}
	public void setLoanEndTime(Date loanEndTime) {
		this.loanEndTime = loanEndTime;
	}
	public Date getRepayBeginTime() {
		return repayBeginTime;
	}
	public void setRepayBeginTime(Date repayBeginTime) {
		this.repayBeginTime = repayBeginTime;
	}
	public Date getRepayEndTime() {
		return repayEndTime;
	}
	public void setRepayEndTime(Date repayEndTime) {
		this.repayEndTime = repayEndTime;
	}
	public Double getBeginAmount() {
		return beginAmount;
	}
	public void setBeginAmount(Double beginAmount) {
		this.beginAmount = beginAmount;
	}
	public Double getEndAmount() {
		return endAmount;
	}
	public void setEndAmount(Double endAmount) {
		this.endAmount = endAmount;
	}
	
	public String getAgentIds() {
		return agentIds;
	}
	public void setAgentIds(String agentIds) {
		this.agentIds = agentIds;
	}
	public List<Object> getAgentIdsObj() {
		return agentIdsObj;
	}
	public void setAgentIdsObj(List<Object> agentIdsObj) {
		this.agentIdsObj = agentIdsObj;
	}
	public String getNonAgentId() {
		return nonAgentId;
	}
	public void setNonAgentId(String nonAgentId) {
		this.nonAgentId = nonAgentId;
	}
	
}

package com.pinting.open.pojo.model.asset;

import java.util.Date;

public class InvestAccount {
	
	//子账户id
	private Integer investId;
	//投资金额
    private Double balance; 
    //投资开始日期
    private String interestBeginDate;
	//投资结束日期
	private String investEndTime;
	//投资剩余天数
	private Integer investDay;
	//现利息基数
    private Double productRate;
    //投资状态
    private Integer status;
    //到期收益
    private Double yield;
    //红包抵扣
    private Double redAmount;
    //产品开始时间
    private String startTime;
    //产品ID
    private Integer productId;
    //产品名称
    private String productName;
    //投资协议类型
    private String propertyType;
    //服务器当前时间
    private String now;
    
    private String term; //委托意向期限
    
    private String openTime; //委托成功日期
    
    private Double entrustBalance; //委托金额,开户金额
    
    private String returnType;//回款方式
    
    private Double returnBalance; //委托结束退回金额
    
    private String entrustStatus; //委托计划状态
    
	private Double returnAmount;  //已收金额即已收本息
	
	private Double receiveAmount;   //待收金额即待收本息
	
	private Double receivePrincipalAmount;  // 待收本金
	
	private Double receiveInterestAmount;  // 待收利息

	//计划管理页面《出借服务协议》（no）与《授权委托书》（yes）协议名字显示判断 powerAttorneyFlag（no、yes）
	private String powerAttorneyFlag;
	
	private Double ticketApr; 	//加息幅度
	
	private Double interestAmount;  //加息收益

	public String getNow() {
		return now;
	}
	public void setNow(String now) {
		this.now = now;
	}
	public String getPropertyType() {
		return propertyType;
	}
	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public Double getRedAmount() {
		return redAmount;
	}
	public void setRedAmount(Double redAmount) {
		this.redAmount = redAmount;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public Double getYield() {
        return yield;
    }
    public void setYield(Double yield) {
        this.yield = yield;
    }
    public Integer getInvestId() {
		return investId;
	}
	public void setInvestId(Integer investId) {
		this.investId = investId;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	
	
	public String getInterestBeginDate() {
		return interestBeginDate;
	}
	public void setInterestBeginDate(String interestBeginDate) {
		this.interestBeginDate = interestBeginDate;
	}

	public String getInvestEndTime() {
		return investEndTime;
	}
	public void setInvestEndTime(String investEndTime) {
		this.investEndTime = investEndTime;
	}
	public Integer getInvestDay() {
		return investDay;
	}
	public void setInvestDay(Integer investDay) {
		this.investDay = investDay;
	}
	public Double getProductRate() {
		return productRate;
	}
	public void setProductRate(Double productRate) {
		this.productRate = productRate;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public Double getEntrustBalance() {
		return entrustBalance;
	}
	public void setEntrustBalance(Double entrustBalance) {
		this.entrustBalance = entrustBalance;
	}
	public String getReturnType() {
		return returnType;
	}
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
	public Double getReturnBalance() {
		return returnBalance;
	}
	public void setReturnBalance(Double returnBalance) {
		this.returnBalance = returnBalance;
	}
	public String getOpenTime() {
		return openTime;
	}
	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}
	public String getEntrustStatus() {
		return entrustStatus;
	}
	public void setEntrustStatus(String entrustStatus) {
		this.entrustStatus = entrustStatus;
	}
	public Double getReturnAmount() {
		return returnAmount;
	}
	public void setReturnAmount(Double returnAmount) {
		this.returnAmount = returnAmount;
	}
	public Double getReceiveAmount() {
		return receiveAmount;
	}
	public void setReceiveAmount(Double receiveAmount) {
		this.receiveAmount = receiveAmount;
	}
	public Double getReceivePrincipalAmount() {
		return receivePrincipalAmount;
	}
	public void setReceivePrincipalAmount(Double receivePrincipalAmount) {
		this.receivePrincipalAmount = receivePrincipalAmount;
	}
	public Double getReceiveInterestAmount() {
		return receiveInterestAmount;
	}
	public void setReceiveInterestAmount(Double receiveInterestAmount) {
		this.receiveInterestAmount = receiveInterestAmount;
	}

	public String getPowerAttorneyFlag() {
		return powerAttorneyFlag;
	}

	public void setPowerAttorneyFlag(String powerAttorneyFlag) {
		this.powerAttorneyFlag = powerAttorneyFlag;
	}
	
	public Double getTicketApr() {
		return ticketApr;
	}
	public void setTicketApr(Double ticketApr) {
		this.ticketApr = ticketApr;
	}
	
	public Double getInterestAmount() {
		return interestAmount;
	}
	public void setInterestAmount(Double interestAmount) {
		this.interestAmount = interestAmount;
	}
}

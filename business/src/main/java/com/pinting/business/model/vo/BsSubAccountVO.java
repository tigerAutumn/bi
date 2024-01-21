package com.pinting.business.model.vo;

import java.util.Date;

import com.pinting.business.model.BsSubAccount;

public class BsSubAccountVO extends BsSubAccount {

	//投资期限
	private int term;  
	
	private Integer term4Day;
	
	//投资天数
	private Integer investDay;
	
	//投资结束日期
	private Date investEndTime;

	//投资总人数
	private Integer productNum;
	
	//起始时间（用于统计）
	private Date startTime;
	//结束时间（用于统计）
	private Date endTime;
	
	//用户id
	private Integer userId;
	
	//投资总金额
	private Double totalAmount;
	
	//投资笔数
	private Integer investCount;
	
	//红包金额
	private Double redAmount;
	
	//产品ID
	private Integer productId;
	
	//产品名称
	private String productName;
	
	//目标债权类别
	private String propertyType;

	//站岗户回退金额
	private Double returnBalance;
	
	private String entrustStatus;
	
	private String returnType;
	
	private Double returnAmount;  //已收金额即已收本息（PC端我的投资中已完成的投资用此字段表示已获得收益）
	
	private Double receiveAmount;   //待收金额即待收本息（）
	
	private Double receivePrincipalAmount;  // 待收本金
	
	private Double receiveInterestAmount;  // 待收利息

	private String productType;   //产品类型

	private Integer propertyId; //接收资金方编号
	
	private Double ticketApr;	//基于本金的加息率
	
	private Double interestAmount;	//购买成功后收益金额
	
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

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Integer getInvestCount() {
		return investCount;
	}

	public void setInvestCount(Integer investCount) {
		this.investCount = investCount;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getProductNum() {
		return productNum;
	}

	public void setProductNum(Integer productNum) {
		this.productNum = productNum;
	}

	public int getTerm() {
		return term;
	}

	public void setTerm(int term) {
		this.term = term;
	}

	public Integer getInvestDay() {
		return investDay;
	}

	public void setInvestDay(Integer investDay) {
		this.investDay = investDay;
	}

	public Date getInvestEndTime() {
		return investEndTime;
	}

	public void setInvestEndTime(Date investEndTime) {
		this.investEndTime = investEndTime;
	}

	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	public Double getReturnBalance() {
		return returnBalance;
	}

	public void setReturnBalance(Double returnBalance) {
		this.returnBalance = returnBalance;
	}

	public Integer getTerm4Day() {
		if (term == 0) {
			return null;
		}
		
		if(term < 0){
			term4Day = Math.abs(this.term);
		}else if(term == 12){
			term4Day = 365;
		}else{
			term4Day = term*30;
		}
		return term4Day;
	}

	public String getEntrustStatus() {
		return entrustStatus;
	}

	public void setEntrustStatus(String entrustStatus) {
		this.entrustStatus = entrustStatus;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
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

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public Integer getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(Integer propertyId) {
		this.propertyId = propertyId;
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

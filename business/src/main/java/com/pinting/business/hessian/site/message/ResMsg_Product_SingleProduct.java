package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_Product_SingleProduct extends ResMsg {

	private static final long serialVersionUID = 6456498553697981630L;

	private Integer id;
	
	private String rate;
	
	private Integer term;
	
	private String name;
	
	private Double minInvestAmount;
	
	private String currTime; //当前时间
	
	private String startTime; //产品开始时间
	
	private String endTime; //产品结束时间
	
	private String finishTime; //终止时间
	
	private Double leftAmount; //产品剩余额度
	
	private String propertyType; //投资协议类型
	
	private String activityType;//产品活动类型
	
	private Double maxSingleInvestAmount; //个人单次最高投资金额
	
	private String propertySymbol;//资金接收方标记
	
    private Double maxTotalAmount;  //限额
    
    private Double currTotalAmount;  // 累计投资额
    
    private Integer status;    // 状态
    
    private String isSupportRedPacket; //是否支持红包
    
    private String  returnType;

	private String remindTagContent; // 提醒标签内容

	private String interestRatesTagContent; // 加息标签内容

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public Integer getTerm() {
		return term;
	}

	public void setTerm(Integer term) {
		this.term = term;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getMinInvestAmount() {
		return minInvestAmount;
	}

	public void setMinInvestAmount(Double minInvestAmount) {
		this.minInvestAmount = minInvestAmount;
	}

	public String getCurrTime() {
		return currTime;
	}

	public void setCurrTime(String currTime) {
		this.currTime = currTime;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}

	public Double getLeftAmount() {
		return leftAmount;
	}

	public void setLeftAmount(Double leftAmount) {
		this.leftAmount = leftAmount;
	}

	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	public Double getMaxSingleInvestAmount() {
		return maxSingleInvestAmount;
	}

	public void setMaxSingleInvestAmount(Double maxSingleInvestAmount) {
		this.maxSingleInvestAmount = maxSingleInvestAmount;
	}

	public String getPropertySymbol() {
		return propertySymbol;
	}

	public void setPropertySymbol(String propertySymbol) {
		this.propertySymbol = propertySymbol;
	}

	public Double getMaxTotalAmount() {
		return maxTotalAmount;
	}

	public void setMaxTotalAmount(Double maxTotalAmount) {
		this.maxTotalAmount = maxTotalAmount;
	}

	public Double getCurrTotalAmount() {
		return currTotalAmount;
	}

	public void setCurrTotalAmount(Double currTotalAmount) {
		this.currTotalAmount = currTotalAmount;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getIsSupportRedPacket() {
		return isSupportRedPacket;
	}

	public void setIsSupportRedPacket(String isSupportRedPacket) {
		this.isSupportRedPacket = isSupportRedPacket;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public String getRemindTagContent() {
		return remindTagContent;
	}

	public void setRemindTagContent(String remindTagContent) {
		this.remindTagContent = remindTagContent;
	}

	public String getInterestRatesTagContent() {
		return interestRatesTagContent;
	}

	public void setInterestRatesTagContent(String interestRatesTagContent) {
		this.interestRatesTagContent = interestRatesTagContent;
	}
}

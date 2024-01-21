package com.pinting.open.pojo.model.product;

public class Product {

	private Integer id;
	
	private String term;
	
	private String name;
	
	private String rate;
	
	private Double minInvestAmount;
	
	private String currTime;
	
	private String startTime;
	
	private String endTime;
	
	private String finishTime;
	
	private Double leftAmount;
	
	private String propertyType;
	
	private String activityType; //产品类型
	
	private Double maxSingleInvestAmount; //个人单次最高投资金额
	
	private String propertySymbol;//资金接收方标记
	
    private String isSupportRedPacket; //是否支持红包
    
    private Double maxTotalAmount;  //限额
    
    private Double currTotalAmount;  // 累计投资额
    
    private Integer status;    // 状态
    
    private String returnType;  //回款类型
    
    private String isSuggest;  //是否推荐
    
    private String flag;  // 标记位
    
    private Double progress;  // 进度

	private String remindTagContent; // 提醒标签内容

	private String interestRatesTagContent; // 加息标签内容
    
	public Double getMaxSingleInvestAmount() {
		return maxSingleInvestAmount;
	}

	public void setMaxSingleInvestAmount(Double maxSingleInvestAmount) {
		this.maxSingleInvestAmount = maxSingleInvestAmount;
	}

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	public Double getLeftAmount() {
		return leftAmount;
	}

	public void setLeftAmount(Double leftAmount) {
		this.leftAmount = leftAmount;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public Double getMinInvestAmount() {
		return minInvestAmount;
	}

	public void setMinInvestAmount(Double minInvestAmount) {
		this.minInvestAmount = minInvestAmount;
	}

	public String getPropertySymbol() {
		return propertySymbol;
	}

	public void setPropertySymbol(String propertySymbol) {
		this.propertySymbol = propertySymbol;
	}

	public String getIsSupportRedPacket() {
		return isSupportRedPacket;
	}

	public void setIsSupportRedPacket(String isSupportRedPacket) {
		this.isSupportRedPacket = isSupportRedPacket;
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

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public String getIsSuggest() {
		return isSuggest;
	}

	public void setIsSuggest(String isSuggest) {
		this.isSuggest = isSuggest;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Double getProgress() {
		return progress;
	}

	public void setProgress(Double progress) {
		this.progress = progress;
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

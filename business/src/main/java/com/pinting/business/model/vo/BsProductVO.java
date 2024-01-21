package com.pinting.business.model.vo;

public class BsProductVO {

	private Integer id;
	
	private String rate;
	
	private String term;

	private String dayTerm;
	
	private String name;
	
	private Double minInvestAmount;
	
	private String currTime; //当前时间
	
	private String startTime; //产品开始时间
	
	private String endTime; //产品结束时间
	
	private String finishTime; //终止时间
	
	private Double leftAmount; //产品剩余额度
	
	private Integer status;    // 状态
	
	private Double currTotalAmount;  // 累计投资额
	
	private Double maxTotalAmount;   // 总额度
	
	private String propertyType; //投资协议类型
	
	private String unfinishedProject;   // 是否有未完成计划。YES：有；NO：无
	
	private String activityType;//产品活动类型
	
	private Double maxSingleInvestAmount; //个人单次最高投资金额
	
	private String propertySymbol;
	
    private String isSupportRedPacket;
    
    private String returnType;

	private Integer investCount;
	
	private String isSuggest;  //是否推荐

	// 期数
	private Integer serialOrder;

	private String serialName;

	private String remindTagContent; // 提醒标签内容

	private String interestRatesTagContent; // 加息标签内容

	public String getSerialName() {
		return serialName;
	}

	public void setSerialName(String serialName) {
		this.serialName = serialName;
	}

	public Integer getSerialOrder() {
		return serialOrder;
	}

	public void setSerialOrder(Integer serialOrder) {
		this.serialOrder = serialOrder;
	}

	public Integer getInvestCount() {
		return investCount;
	}

	public void setInvestCount(Integer investCount) {
		this.investCount = investCount;
	}

	public String getDayTerm() {
		return dayTerm;
	}

	public void setDayTerm(String dayTerm) {
		this.dayTerm = dayTerm;
	}

	public Double getMaxSingleInvestAmount() {
		return maxSingleInvestAmount;
	}

	public void setMaxSingleInvestAmount(Double maxSingleInvestAmount) {
		this.maxSingleInvestAmount = maxSingleInvestAmount;
	}

	public String getUnfinishedProject() {
        return unfinishedProject;
    }

    public void setUnfinishedProject(String unfinishedProject) {
        this.unfinishedProject = unfinishedProject;
    }

    public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

	public Double getMinInvestAmount() {
		return minInvestAmount;
	}

	public void setMinInvestAmount(Double minInvestAmount) {
		this.minInvestAmount = minInvestAmount;
	}

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

    public Double getCurrTotalAmount() {
        return currTotalAmount;
    }

    public void setCurrTotalAmount(Double currTotalAmount) {
        this.currTotalAmount = currTotalAmount;
    }

    public Double getMaxTotalAmount() {
        return maxTotalAmount;
    }

    public void setMaxTotalAmount(Double maxTotalAmount) {
        this.maxTotalAmount = maxTotalAmount;
    }

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
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

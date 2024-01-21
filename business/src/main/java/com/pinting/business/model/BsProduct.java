package com.pinting.business.model;

import java.util.Date;

import com.pinting.business.model.vo.PageInfoObject;

public class BsProduct extends PageInfoObject{
    /** 
	 * serialVersionUID:序列化编号
	 * @since JDK 1.7
	 */  
	private static final long serialVersionUID = -3482413710010017953L;

	private Integer id;

    private String name;

    private String type;

    private String activityType;

    private String code;

    private Integer interestType;

    private Double baseRate;

    private Double maxRate;

    private Integer term;
    
    private Integer term4Day;

    private Double maxTotalAmount;

    private Double minInvestAmount;

    private Double maxSingleInvestAmount;

    private Double maxInvestAmount;

    private Integer maxInvestTimes;

    private Date startTime;

    private Date endTime;

    private Double currTotalAmount;

    private Integer status;

    private String note;

    private Integer investNum;

    private Double sysReturnRate;

    private Integer serialId;

    private Integer serialOrder;

    private String serialName;

    private String propertyType;

    private String beginInterestDays;

    private String returnType;

    private String interestDeal;

    private String isSupportTransfer;

    private Double manageFee;

    private String showTerminal;

    private Integer propertyId;

    private String isSuggest;

    private Integer terminator;

    private Date finishTime;

    private Integer checker;

    private Date checkTime;

    private Integer distributor;

    private Date distributeTime;

    private String isSupportRedPacket;

    private Date createTime;

    private Date updateTime;

    private String isSupportIncrInterest;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getInterestType() {
        return interestType;
    }

    public void setInterestType(Integer interestType) {
        this.interestType = interestType;
    }

    public Double getBaseRate() {
        return baseRate;
    }

    public void setBaseRate(Double baseRate) {
        this.baseRate = baseRate;
    }

    public Double getMaxRate() {
        return maxRate;
    }

    public void setMaxRate(Double maxRate) {
        this.maxRate = maxRate;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public Double getMaxTotalAmount() {
        return maxTotalAmount;
    }

    public void setMaxTotalAmount(Double maxTotalAmount) {
        this.maxTotalAmount = maxTotalAmount;
    }

    public Double getMinInvestAmount() {
        return minInvestAmount;
    }

    public void setMinInvestAmount(Double minInvestAmount) {
        this.minInvestAmount = minInvestAmount;
    }

    public Double getMaxSingleInvestAmount() {
        return maxSingleInvestAmount;
    }

    public void setMaxSingleInvestAmount(Double maxSingleInvestAmount) {
        this.maxSingleInvestAmount = maxSingleInvestAmount;
    }

    public Double getMaxInvestAmount() {
        return maxInvestAmount;
    }

    public void setMaxInvestAmount(Double maxInvestAmount) {
        this.maxInvestAmount = maxInvestAmount;
    }

    public Integer getMaxInvestTimes() {
        return maxInvestTimes;
    }

    public void setMaxInvestTimes(Integer maxInvestTimes) {
        this.maxInvestTimes = maxInvestTimes;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getInvestNum() {
        return investNum;
    }

    public void setInvestNum(Integer investNum) {
        this.investNum = investNum;
    }

    public Double getSysReturnRate() {
        return sysReturnRate;
    }

    public void setSysReturnRate(Double sysReturnRate) {
        this.sysReturnRate = sysReturnRate;
    }

    public Integer getSerialId() {
        return serialId;
    }

    public void setSerialId(Integer serialId) {
        this.serialId = serialId;
    }

    public Integer getSerialOrder() {
        return serialOrder;
    }

    public void setSerialOrder(Integer serialOrder) {
        this.serialOrder = serialOrder;
    }

    public String getSerialName() {
        return serialName;
    }

    public void setSerialName(String serialName) {
        this.serialName = serialName;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public String getBeginInterestDays() {
        return beginInterestDays;
    }

    public void setBeginInterestDays(String beginInterestDays) {
        this.beginInterestDays = beginInterestDays;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public String getInterestDeal() {
        return interestDeal;
    }

    public void setInterestDeal(String interestDeal) {
        this.interestDeal = interestDeal;
    }

    public String getIsSupportTransfer() {
        return isSupportTransfer;
    }

    public void setIsSupportTransfer(String isSupportTransfer) {
        this.isSupportTransfer = isSupportTransfer;
    }

    public Double getManageFee() {
        return manageFee;
    }

    public void setManageFee(Double manageFee) {
        this.manageFee = manageFee;
    }

    public String getShowTerminal() {
        return showTerminal;
    }

    public void setShowTerminal(String showTerminal) {
        this.showTerminal = showTerminal;
    }

    public Integer getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Integer propertyId) {
        this.propertyId = propertyId;
    }

    public String getIsSuggest() {
        return isSuggest;
    }

    public void setIsSuggest(String isSuggest) {
        this.isSuggest = isSuggest;
    }

    public Integer getTerminator() {
        return terminator;
    }

    public void setTerminator(Integer terminator) {
        this.terminator = terminator;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Integer getChecker() {
        return checker;
    }

    public void setChecker(Integer checker) {
        this.checker = checker;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public Integer getDistributor() {
        return distributor;
    }

    public void setDistributor(Integer distributor) {
        this.distributor = distributor;
    }

    public Date getDistributeTime() {
        return distributeTime;
    }

    public void setDistributeTime(Date distributeTime) {
        this.distributeTime = distributeTime;
    }

    public String getIsSupportRedPacket() {
        return isSupportRedPacket;
    }

    public void setIsSupportRedPacket(String isSupportRedPacket) {
        this.isSupportRedPacket = isSupportRedPacket;
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

    public String getIsSupportIncrInterest() {
        return isSupportIncrInterest;
    }

    public void setIsSupportIncrInterest(String isSupportIncrInterest) {
        this.isSupportIncrInterest = isSupportIncrInterest == null ? null : isSupportIncrInterest.trim();
    }

	public Integer getTerm4Day() {
		if (term == null || "".equals(term)) {
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

}
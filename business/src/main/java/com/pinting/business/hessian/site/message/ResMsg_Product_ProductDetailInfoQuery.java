package com.pinting.business.hessian.site.message;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_Product_ProductDetailInfoQuery extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3214055719693884142L;
	
	private Integer id;

    private String name;

    private String type;

    private String code;

    private Integer interestType;

    private Double baseRate;

    private Double maxRate;

    private Integer term;

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

    private Date createTime;

    private Date updateTime;
    
	private String propertyName;
	
    private String propertySummary; //资产合作方简介

    private String returnSource; //还款来源,还款保障

    private String fundSecurity; //资金安全

    private String orgnizeCheck; //合作机构审查

    private String coopProtocolPics;  //合作协议示例图

    private String ratingGrade; //三方担保合同

    private String loanProtocolPics; //借款合同示例图
    
    private Double surplusAmount; //剩余可投金额
    
    private String informMinute;//
    
    
    private String ratingGradePics; //三方担保合同示例图
    
    private String orgnizeCheckPics; //合作机构审查示例图
    
    private String activityType; //产品活动类型
    
    private String propertySymbol;//资金接收方标识
    
    private Integer termMonth; //产品投资期限（月为单位）
    
    private String entrustLockPeriod; //匹配债权委托锁定期

	/**
	 * 投资记录总记录数
	 */
	private int totalRows;

	private ArrayList<HashMap<String, Object>> investRecordList;

	private String remindTagContent; //提醒标签内容

	private String interestRatesTagContent; //加息标签内容
	
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

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getPropertySummary() {
		return propertySummary;
	}

	public void setPropertySummary(String propertySummary) {
		this.propertySummary = propertySummary;
	}

	public String getReturnSource() {
		return returnSource;
	}

	public void setReturnSource(String returnSource) {
		this.returnSource = returnSource;
	}

	public String getFundSecurity() {
		return fundSecurity;
	}

	public void setFundSecurity(String fundSecurity) {
		this.fundSecurity = fundSecurity;
	}

	public String getOrgnizeCheck() {
		return orgnizeCheck;
	}

	public void setOrgnizeCheck(String orgnizeCheck) {
		this.orgnizeCheck = orgnizeCheck;
	}

	public String getCoopProtocolPics() {
		return coopProtocolPics;
	}

	public void setCoopProtocolPics(String coopProtocolPics) {
		this.coopProtocolPics = coopProtocolPics;
	}

	public String getRatingGrade() {
		return ratingGrade;
	}

	public void setRatingGrade(String ratingGrade) {
		this.ratingGrade = ratingGrade;
	}

	public String getLoanProtocolPics() {
		return loanProtocolPics;
	}

	public void setLoanProtocolPics(String loanProtocolPics) {
		this.loanProtocolPics = loanProtocolPics;
	}

	public Double getSurplusAmount() {
		return surplusAmount;
	}

	public void setSurplusAmount(Double surplusAmount) {
		this.surplusAmount = surplusAmount;
	}


	public ArrayList<HashMap<String, Object>> getInvestRecordList() {
		return investRecordList;
	}

	public void setInvestRecordList(
			ArrayList<HashMap<String, Object>> investRecordList) {
		this.investRecordList = investRecordList;
	}

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public String getInformMinute() {
		return informMinute;
	}

	public void setInformMinute(String informMinute) {
		this.informMinute = informMinute;
	}

	public String getRatingGradePics() {
		return ratingGradePics;
	}

	public void setRatingGradePics(String ratingGradePics) {
		this.ratingGradePics = ratingGradePics;
	}

	public String getOrgnizeCheckPics() {
		return orgnizeCheckPics;
	}

	public void setOrgnizeCheckPics(String orgnizeCheckPics) {
		this.orgnizeCheckPics = orgnizeCheckPics;
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

	public Integer getTermMonth() {
		return termMonth;
	}

	public void setTermMonth(Integer termMonth) {
		this.termMonth = termMonth;
	}

	public String getEntrustLockPeriod() {
		return entrustLockPeriod;
	}

	public void setEntrustLockPeriod(String entrustLockPeriod) {
		this.entrustLockPeriod = entrustLockPeriod;
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

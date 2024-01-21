package com.pinting.business.model.vo;

import java.util.Date;
import java.util.List;

import com.pinting.business.model.BsProduct;

public class MProductVO extends BsProduct{

	/** 
	 * serialVersionUID:序列化编号
	 * @since JDK 1.7
	 */  
	private static final long serialVersionUID = -977900140929750971L;

	private String sName;
	
	private Integer sSerialId;
	
	private Date sDistributeTime;
	
	private Date eDistributeTime;
	
	private Date sStartTime;
	
	private Date eStartTime;
	
	private Date sEndTime;
	
	private Date eEndTime;
	
	private Integer sTerm;
	
	private Double sBaseRate;
	
	private Integer sStatus;
	
	private String sIsSuggest;
	
	private String sShowTerminal;
	
	private String proSerialName;
	
	private String termStr;
	
	private String checkerName;
	
	private String distributorName;
	
	private String propertyName;
	
	private List<Object>  showTerminalList;
	
	private String queryType;
	
	private String propertySummary; //资产合作方简介

    private String returnSource; //还款来源,还款保障

    private String fundSecurity; //资金安全

    private String orgnizeCheck; //合作机构审查

    private String coopProtocolPics;  //合作协议示例图

    private String ratingGrade; //三方担保合同

    private String loanProtocolPics; //借款合同示例图
    
    private Double surplusAmount; //剩余可投金额
    
    private String ratingGradePics; //三方担保合同示例图
    
    private String orgnizeCheckPics; //合作机构审查示例图
    
    private int numPerPage = 100;

	public String getsName() {
		return sName;
	}

	public void setsName(String sName) {
		this.sName = sName;
	}

	public Integer getsSerialId() {
		return sSerialId;
	}

	public void setsSerialId(Integer sSerialId) {
		this.sSerialId = sSerialId;
	}

	public Date getsDistributeTime() {
		return sDistributeTime;
	}

	public void setsDistributeTime(Date sDistributeTime) {
		this.sDistributeTime = sDistributeTime;
	}

	public Date geteDistributeTime() {
		return eDistributeTime;
	}

	public void seteDistributeTime(Date eDistributeTime) {
		this.eDistributeTime = eDistributeTime;
	}

	public Date getsStartTime() {
		return sStartTime;
	}

	public void setsStartTime(Date sStartTime) {
		this.sStartTime = sStartTime;
	}

	public Date geteStartTime() {
		return eStartTime;
	}

	public void seteStartTime(Date eStartTime) {
		this.eStartTime = eStartTime;
	}

	public Date getsEndTime() {
		return sEndTime;
	}

	public void setsEndTime(Date sEndTime) {
		this.sEndTime = sEndTime;
	}

	public Date geteEndTime() {
		return eEndTime;
	}

	public void seteEndTime(Date eEndTime) {
		this.eEndTime = eEndTime;
	}

	public Integer getsTerm() {
		return sTerm;
	}

	public void setsTerm(Integer sTerm) {
		this.sTerm = sTerm;
	}

	public Double getsBaseRate() {
		return sBaseRate;
	}

	public void setsBaseRate(Double sBaseRate) {
		this.sBaseRate = sBaseRate;
	}

	public Integer getsStatus() {
		return sStatus;
	}

	public void setsStatus(Integer sStatus) {
		this.sStatus = sStatus;
	}

	public String getsIsSuggest() {
		return sIsSuggest;
	}

	public void setsIsSuggest(String sIsSuggest) {
		this.sIsSuggest = sIsSuggest;
	}

	public String getProSerialName() {
		return proSerialName;
	}

	public void setProSerialName(String proSerialName) {
		this.proSerialName = proSerialName;
	}

	public String getTermStr() {
		return termStr;
	}

	public void setTermStr(String termStr) {
		this.termStr = termStr;
	}

	public String getCheckerName() {
		return checkerName;
	}

	public void setCheckerName(String checkerName) {
		this.checkerName = checkerName;
	}

	public String getDistributorName() {
		return distributorName;
	}

	public void setDistributorName(String distributorName) {
		this.distributorName = distributorName;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getsShowTerminal() {
		return sShowTerminal;
	}

	public void setsShowTerminal(String sShowTerminal) {
		this.sShowTerminal = sShowTerminal;
	}

	public List<Object> getShowTerminalList() {
		return showTerminalList;
	}

	public void setShowTerminalList(List<Object> showTerminalList) {
		this.showTerminalList = showTerminalList;
	}

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
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

	public int getNumPerPage() {
		return numPerPage;
	}

	public void setNumPerPage(int numPerPage) {
		this.numPerPage = numPerPage;
	}
}

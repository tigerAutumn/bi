package com.pinting.business.model.vo;

import java.util.List;

import com.pinting.business.model.BsProduct;
/**
 * 理财计划产品详情
 * @author Dargon&Cat
 * @version $Id: ProductDetailVO.java, v 0.1 2016-4-21 上午10:49:47 Dargon&Cat Exp $
 */
public class ProductDetailVO extends BsProduct  {
	

	private String propertyName;
	
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
    
    private String propertySymbol;//资金接收方标识
    
    private Integer termMonth; //产品投资期限（月为单位）
    
    private List<InvestRecordVO> investRecordVO;

	private Integer investCount; // 投资人次

	private String remindTagContent; // 提醒标签内容

	private String interestRatesTagContent; // 加息标签内容

	public Integer getInvestCount() {
		return investCount;
	}

	public void setInvestCount(Integer investCount) {
		this.investCount = investCount;
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

	

	public List<InvestRecordVO> getInvestRecordVO() {
		return investRecordVO;
	}

	public void setInvestRecordVO(List<InvestRecordVO> investRecordVO) {
		this.investRecordVO = investRecordVO;
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

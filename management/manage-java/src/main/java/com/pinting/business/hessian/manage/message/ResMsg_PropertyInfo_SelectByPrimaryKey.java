package com.pinting.business.hessian.manage.message;

import java.util.Date;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_PropertyInfo_SelectByPrimaryKey extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6870587580556639445L;
	
	private Integer id;

    private String propertyName;

    private String propertySummary;

    private String returnSource;

    private String fundSecurity;

    private String orgnizeCheck;

    private String coopProtocolPics;

    private String ratingGrade;

    private String loanProtocolPics;

    private Date createTime;

    private Date updateTime;
    
    private String orgnizeCheckPics;
    
    private String ratingGradePics;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getOrgnizeCheckPics() {
		return orgnizeCheckPics;
	}

	public void setOrgnizeCheckPics(String orgnizeCheckPics) {
		this.orgnizeCheckPics = orgnizeCheckPics;
	}

	public String getRatingGradePics() {
		return ratingGradePics;
	}

	public void setRatingGradePics(String ratingGradePics) {
		this.ratingGradePics = ratingGradePics;
	}

}

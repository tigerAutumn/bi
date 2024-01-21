package com.pinting.business.model;

import java.util.Date;

import com.pinting.business.model.vo.PageInfoObject;

public class BsPropertyInfo extends PageInfoObject {
    /**
	 * 
	 */
	private static final long serialVersionUID = -2517092879384165773L;

	private Integer id;

    private String propertyName;

    private String propertySymbol;

    private String propertySummary;

    private String returnSource;

    private String fundSecurity;

    private String orgnizeCheck;

    private String orgnizeCheckPics;

    private String coopProtocolPics;

    private String ratingGrade;

    private String ratingGradePics;

    private String loanProtocolPics;

    private Date createTime;

    private Date updateTime;

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

    public String getPropertySymbol() {
        return propertySymbol;
    }

    public void setPropertySymbol(String propertySymbol) {
        this.propertySymbol = propertySymbol;
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

    public String getOrgnizeCheckPics() {
        return orgnizeCheckPics;
    }

    public void setOrgnizeCheckPics(String orgnizeCheckPics) {
        this.orgnizeCheckPics = orgnizeCheckPics;
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

    public String getRatingGradePics() {
        return ratingGradePics;
    }

    public void setRatingGradePics(String ratingGradePics) {
        this.ratingGradePics = ratingGradePics;
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
}
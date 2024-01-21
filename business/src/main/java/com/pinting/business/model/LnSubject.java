package com.pinting.business.model;

import java.util.Date;

public class LnSubject {
    private Integer id;

    private Integer chargeRuleId;

    private String subjectCode;

    private String subjectName;

    private Integer repayOrder;

    private String reserveRule;

    private String calRule;

    private String leftRule;

    private String ratetype;

    private Double numValue;

    private String customizedClass;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getChargeRuleId() {
        return chargeRuleId;
    }

    public void setChargeRuleId(Integer chargeRuleId) {
        this.chargeRuleId = chargeRuleId;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Integer getRepayOrder() {
        return repayOrder;
    }

    public void setRepayOrder(Integer repayOrder) {
        this.repayOrder = repayOrder;
    }

    public String getReserveRule() {
        return reserveRule;
    }

    public void setReserveRule(String reserveRule) {
        this.reserveRule = reserveRule;
    }

    public String getCalRule() {
        return calRule;
    }

    public void setCalRule(String calRule) {
        this.calRule = calRule;
    }

    public String getLeftRule() {
        return leftRule;
    }

    public void setLeftRule(String leftRule) {
        this.leftRule = leftRule;
    }

    public String getRatetype() {
        return ratetype;
    }

    public void setRatetype(String ratetype) {
        this.ratetype = ratetype;
    }

    public Double getNumValue() {
        return numValue;
    }

    public void setNumValue(Double numValue) {
        this.numValue = numValue;
    }

    public String getCustomizedClass() {
        return customizedClass;
    }

    public void setCustomizedClass(String customizedClass) {
        this.customizedClass = customizedClass;
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
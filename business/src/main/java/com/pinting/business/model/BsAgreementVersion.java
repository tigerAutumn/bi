package com.pinting.business.model;

import java.util.Date;

public class BsAgreementVersion {
    private Integer id;

    private String partnerCode;

    private String agreementName;

    private String agreementType;

    private String agreementVersion;

    private String agreementUrl;

    private Date agreementEffectiveStartTime;

    private Date agreementEffectiveEndTime;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode == null ? null : partnerCode.trim();
    }

    public String getAgreementName() {
        return agreementName;
    }

    public void setAgreementName(String agreementName) {
        this.agreementName = agreementName == null ? null : agreementName.trim();
    }

    public String getAgreementType() {
        return agreementType;
    }

    public void setAgreementType(String agreementType) {
        this.agreementType = agreementType == null ? null : agreementType.trim();
    }

    public String getAgreementVersion() {
        return agreementVersion;
    }

    public void setAgreementVersion(String agreementVersion) {
        this.agreementVersion = agreementVersion == null ? null : agreementVersion.trim();
    }

    public String getAgreementUrl() {
        return agreementUrl;
    }

    public void setAgreementUrl(String agreementUrl) {
        this.agreementUrl = agreementUrl == null ? null : agreementUrl.trim();
    }

    public Date getAgreementEffectiveStartTime() {
        return agreementEffectiveStartTime;
    }

    public void setAgreementEffectiveStartTime(Date agreementEffectiveStartTime) {
        this.agreementEffectiveStartTime = agreementEffectiveStartTime;
    }

    public Date getAgreementEffectiveEndTime() {
        return agreementEffectiveEndTime;
    }

    public void setAgreementEffectiveEndTime(Date agreementEffectiveEndTime) {
        this.agreementEffectiveEndTime = agreementEffectiveEndTime;
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
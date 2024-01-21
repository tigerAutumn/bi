package com.pinting.business.model;

import java.util.Date;

public class LnLoanApplyRecord {
    private Integer id;

    private String partnerUserId;

    private String partnerLoanId;

    private Double applyAmount;

    private Double headFee;

    private Double agreementRate;

    private Double loanServiceRate;

    private Double bgwSettleRate;

    private Integer period;

    private String partnerOrderNo;

    private String partnerBusinessFlag;

    private String subjectName;

    private String purpose;

    private Date applyTime;

    private Double creditAmount;

    private Double loanedAmount;

    private String bgwBindId;

    private String creditLevel;

    private Integer creditScore;

    private Integer loanTimes;

    private Integer breakTimes;

    private Integer breakMaxDays;

    private Date interestTime;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPartnerUserId() {
        return partnerUserId;
    }

    public void setPartnerUserId(String partnerUserId) {
        this.partnerUserId = partnerUserId == null ? null : partnerUserId.trim();
    }

    public String getPartnerLoanId() {
        return partnerLoanId;
    }

    public void setPartnerLoanId(String partnerLoanId) {
        this.partnerLoanId = partnerLoanId == null ? null : partnerLoanId.trim();
    }

    public Double getApplyAmount() {
        return applyAmount;
    }

    public void setApplyAmount(Double applyAmount) {
        this.applyAmount = applyAmount;
    }

    public Double getHeadFee() {
        return headFee;
    }

    public void setHeadFee(Double headFee) {
        this.headFee = headFee;
    }

    public Double getAgreementRate() {
        return agreementRate;
    }

    public void setAgreementRate(Double agreementRate) {
        this.agreementRate = agreementRate;
    }

    public Double getLoanServiceRate() {
        return loanServiceRate;
    }

    public void setLoanServiceRate(Double loanServiceRate) {
        this.loanServiceRate = loanServiceRate;
    }

    public Double getBgwSettleRate() {
        return bgwSettleRate;
    }

    public void setBgwSettleRate(Double bgwSettleRate) {
        this.bgwSettleRate = bgwSettleRate;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public String getPartnerOrderNo() {
        return partnerOrderNo;
    }

    public void setPartnerOrderNo(String partnerOrderNo) {
        this.partnerOrderNo = partnerOrderNo == null ? null : partnerOrderNo.trim();
    }

    public String getPartnerBusinessFlag() {
        return partnerBusinessFlag;
    }

    public void setPartnerBusinessFlag(String partnerBusinessFlag) {
        this.partnerBusinessFlag = partnerBusinessFlag == null ? null : partnerBusinessFlag.trim();
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName == null ? null : subjectName.trim();
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose == null ? null : purpose.trim();
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Double getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(Double creditAmount) {
        this.creditAmount = creditAmount;
    }

    public Double getLoanedAmount() {
        return loanedAmount;
    }

    public void setLoanedAmount(Double loanedAmount) {
        this.loanedAmount = loanedAmount;
    }

    public String getBgwBindId() {
        return bgwBindId;
    }

    public void setBgwBindId(String bgwBindId) {
        this.bgwBindId = bgwBindId == null ? null : bgwBindId.trim();
    }

    public String getCreditLevel() {
        return creditLevel;
    }

    public void setCreditLevel(String creditLevel) {
        this.creditLevel = creditLevel == null ? null : creditLevel.trim();
    }

    public Integer getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(Integer creditScore) {
        this.creditScore = creditScore;
    }

    public Integer getLoanTimes() {
        return loanTimes;
    }

    public void setLoanTimes(Integer loanTimes) {
        this.loanTimes = loanTimes;
    }

    public Integer getBreakTimes() {
        return breakTimes;
    }

    public void setBreakTimes(Integer breakTimes) {
        this.breakTimes = breakTimes;
    }

    public Integer getBreakMaxDays() {
        return breakMaxDays;
    }

    public void setBreakMaxDays(Integer breakMaxDays) {
        this.breakMaxDays = breakMaxDays;
    }

    public Date getInterestTime() {
        return interestTime;
    }

    public void setInterestTime(Date interestTime) {
        this.interestTime = interestTime;
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
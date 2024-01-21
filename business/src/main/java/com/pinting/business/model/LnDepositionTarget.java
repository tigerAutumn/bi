package com.pinting.business.model;

import java.util.Date;

public class LnDepositionTarget {
    private Integer id;

    private String prodName;

    private String prodType;

    private Double totalLimit;

    private String interestType;

    private String setupType;

    private Date sellDate;

    private Date beginDate;

    private Date expireDate;

    private Integer cycle;

    private String cycleUnit;

    private Double istYear;

    private String repayType;

    private Integer loanId;

    private String chargeOffAuto;

    private String overLimit;

    private Double overTotalLimit;

    private String riskLvl;

    private Integer buyerNumLimit;

    private String status;

    private String remark;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getProdType() {
        return prodType;
    }

    public void setProdType(String prodType) {
        this.prodType = prodType;
    }

    public Double getTotalLimit() {
        return totalLimit;
    }

    public void setTotalLimit(Double totalLimit) {
        this.totalLimit = totalLimit;
    }

    public String getInterestType() {
        return interestType;
    }

    public void setInterestType(String interestType) {
        this.interestType = interestType;
    }

    public String getSetupType() {
        return setupType;
    }

    public void setSetupType(String setupType) {
        this.setupType = setupType;
    }

    public Date getSellDate() {
        return sellDate;
    }

    public void setSellDate(Date sellDate) {
        this.sellDate = sellDate;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public Integer getCycle() {
        return cycle;
    }

    public void setCycle(Integer cycle) {
        this.cycle = cycle;
    }

    public String getCycleUnit() {
        return cycleUnit;
    }

    public void setCycleUnit(String cycleUnit) {
        this.cycleUnit = cycleUnit;
    }

    public Double getIstYear() {
        return istYear;
    }

    public void setIstYear(Double istYear) {
        this.istYear = istYear;
    }

    public String getRepayType() {
        return repayType;
    }

    public void setRepayType(String repayType) {
        this.repayType = repayType;
    }

    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
    }

    public String getChargeOffAuto() {
        return chargeOffAuto;
    }

    public void setChargeOffAuto(String chargeOffAuto) {
        this.chargeOffAuto = chargeOffAuto;
    }

    public String getOverLimit() {
        return overLimit;
    }

    public void setOverLimit(String overLimit) {
        this.overLimit = overLimit;
    }

    public Double getOverTotalLimit() {
        return overTotalLimit;
    }

    public void setOverTotalLimit(Double overTotalLimit) {
        this.overTotalLimit = overTotalLimit;
    }

    public String getRiskLvl() {
        return riskLvl;
    }

    public void setRiskLvl(String riskLvl) {
        this.riskLvl = riskLvl;
    }

    public Integer getBuyerNumLimit() {
        return buyerNumLimit;
    }

    public void setBuyerNumLimit(Integer buyerNumLimit) {
        this.buyerNumLimit = buyerNumLimit;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
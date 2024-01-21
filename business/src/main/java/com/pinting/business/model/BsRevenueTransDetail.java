package com.pinting.business.model;

import java.util.Date;

public class BsRevenueTransDetail {
    private Integer id;

    private String partnerCode;

    private String transType;

    private Integer loanId;

    private Integer repaySerial;

    private Integer revenueTransId;

    private Integer repayScheduleId;

    private Integer repayId;

    private Double repayAmount;

    private Double deposit;

    private Double bgwServiceFee;

    private Double commissionFee;

    private Double otherFee;

    private Double revenueAmount;

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
        this.partnerCode = partnerCode;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
    }

    public Integer getRepaySerial() {
        return repaySerial;
    }

    public void setRepaySerial(Integer repaySerial) {
        this.repaySerial = repaySerial;
    }

    public Integer getRevenueTransId() {
        return revenueTransId;
    }

    public void setRevenueTransId(Integer revenueTransId) {
        this.revenueTransId = revenueTransId;
    }

    public Integer getRepayScheduleId() {
        return repayScheduleId;
    }

    public void setRepayScheduleId(Integer repayScheduleId) {
        this.repayScheduleId = repayScheduleId;
    }

    public Integer getRepayId() {
        return repayId;
    }

    public void setRepayId(Integer repayId) {
        this.repayId = repayId;
    }

    public Double getRepayAmount() {
        return repayAmount;
    }

    public void setRepayAmount(Double repayAmount) {
        this.repayAmount = repayAmount;
    }

    public Double getDeposit() {
        return deposit;
    }

    public void setDeposit(Double deposit) {
        this.deposit = deposit;
    }

    public Double getBgwServiceFee() {
        return bgwServiceFee;
    }

    public void setBgwServiceFee(Double bgwServiceFee) {
        this.bgwServiceFee = bgwServiceFee;
    }

    public Double getCommissionFee() {
        return commissionFee;
    }

    public void setCommissionFee(Double commissionFee) {
        this.commissionFee = commissionFee;
    }

    public Double getOtherFee() {
        return otherFee;
    }

    public void setOtherFee(Double otherFee) {
        this.otherFee = otherFee;
    }

    public Double getRevenueAmount() {
        return revenueAmount;
    }

    public void setRevenueAmount(Double revenueAmount) {
        this.revenueAmount = revenueAmount;
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
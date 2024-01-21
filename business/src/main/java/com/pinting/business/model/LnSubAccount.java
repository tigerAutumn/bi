package com.pinting.business.model;

import java.util.Date;

public class LnSubAccount {
    private Integer id;

    private Integer lnUserId;

    private Integer accountId;

    private String code;

    private String accountType;

    private String productCode;

    private String productName;

    private Double productRate;

    private Double openBalance;

    private Double balance;

    private Double availableBalance;

    private Double canWithdraw;

    private Double freezeBalance;

    private String status;

    private String checkStatus;

    private Date interestBeginDate;

    private Date lastTransDate;

    private Date lastCalInterestDate;

    private Date lastFinishInterestDate;

    private Double accumulationInerest;

    private Date openTime;

    private Date closeTime;

    private String note;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLnUserId() {
        return lnUserId;
    }

    public void setLnUserId(Integer lnUserId) {
        this.lnUserId = lnUserId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getProductRate() {
        return productRate;
    }

    public void setProductRate(Double productRate) {
        this.productRate = productRate;
    }

    public Double getOpenBalance() {
        return openBalance;
    }

    public void setOpenBalance(Double openBalance) {
        this.openBalance = openBalance;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(Double availableBalance) {
        this.availableBalance = availableBalance;
    }

    public Double getCanWithdraw() {
        return canWithdraw;
    }

    public void setCanWithdraw(Double canWithdraw) {
        this.canWithdraw = canWithdraw;
    }

    public Double getFreezeBalance() {
        return freezeBalance;
    }

    public void setFreezeBalance(Double freezeBalance) {
        this.freezeBalance = freezeBalance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
    }

    public Date getInterestBeginDate() {
        return interestBeginDate;
    }

    public void setInterestBeginDate(Date interestBeginDate) {
        this.interestBeginDate = interestBeginDate;
    }

    public Date getLastTransDate() {
        return lastTransDate;
    }

    public void setLastTransDate(Date lastTransDate) {
        this.lastTransDate = lastTransDate;
    }

    public Date getLastCalInterestDate() {
        return lastCalInterestDate;
    }

    public void setLastCalInterestDate(Date lastCalInterestDate) {
        this.lastCalInterestDate = lastCalInterestDate;
    }

    public Date getLastFinishInterestDate() {
        return lastFinishInterestDate;
    }

    public void setLastFinishInterestDate(Date lastFinishInterestDate) {
        this.lastFinishInterestDate = lastFinishInterestDate;
    }

    public Double getAccumulationInerest() {
        return accumulationInerest;
    }

    public void setAccumulationInerest(Double accumulationInerest) {
        this.accumulationInerest = accumulationInerest;
    }

    public Date getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Date openTime) {
        this.openTime = openTime;
    }

    public Date getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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
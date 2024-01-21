package com.pinting.business.model;

import java.util.Date;

public class BsHfbankBalanceCheckRecord {
    private Integer id;

    private String hfUserId;

    private Date paycheckDate;

    private Double balance;

    private Double availableBalance;

    private Double freezeBalance;

    private String platNo;

    private String note;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHfUserId() {
        return hfUserId;
    }

    public void setHfUserId(String hfUserId) {
        this.hfUserId = hfUserId;
    }

    public Date getPaycheckDate() {
        return paycheckDate;
    }

    public void setPaycheckDate(Date paycheckDate) {
        this.paycheckDate = paycheckDate;
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

    public Double getFreezeBalance() {
        return freezeBalance;
    }

    public void setFreezeBalance(Double freezeBalance) {
        this.freezeBalance = freezeBalance;
    }

    public String getPlatNo() {
        return platNo;
    }

    public void setPlatNo(String platNo) {
        this.platNo = platNo;
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
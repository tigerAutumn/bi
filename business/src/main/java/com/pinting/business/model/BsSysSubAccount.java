package com.pinting.business.model;

import java.util.Date;

public class BsSysSubAccount {
    private Integer id;

    private String code;

    private Double openBalance;

    private Double balance;

    private Double availableBalance;

    private Double canWithdraw;

    private Double freezeBalance;

    private Date lastTransDate;

    private String note;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public Date getLastTransDate() {
        return lastTransDate;
    }

    public void setLastTransDate(Date lastTransDate) {
        this.lastTransDate = lastTransDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
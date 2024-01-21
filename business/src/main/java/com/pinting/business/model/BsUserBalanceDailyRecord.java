package com.pinting.business.model;

import java.util.Date;

public class BsUserBalanceDailyRecord {
    private Integer id;

    private Integer userId;

    private String hfUserId;

    private Date paycheckDate;

    private Double balance;

    private Double depJshBalance;

    private Double zanAuthBalance;

    private Double yunAuthBalance;

    private String note;

    private Date updateTime;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public Double getDepJshBalance() {
        return depJshBalance;
    }

    public void setDepJshBalance(Double depJshBalance) {
        this.depJshBalance = depJshBalance;
    }

    public Double getZanAuthBalance() {
        return zanAuthBalance;
    }

    public void setZanAuthBalance(Double zanAuthBalance) {
        this.zanAuthBalance = zanAuthBalance;
    }

    public Double getYunAuthBalance() {
        return yunAuthBalance;
    }

    public void setYunAuthBalance(Double yunAuthBalance) {
        this.yunAuthBalance = yunAuthBalance;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
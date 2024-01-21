package com.pinting.business.model;

import java.util.Date;

public class DafyUserExt {
    private Integer id;

    private Integer userId;

    private String dafyUserId;

    private Date dafyRegisterTime;

    private Date dafyBindCardTime;

    private String bankCard;

    private Integer status;

    private Date createTime;

    private String bindFailReson;

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

    public String getDafyUserId() {
        return dafyUserId;
    }

    public void setDafyUserId(String dafyUserId) {
        this.dafyUserId = dafyUserId;
    }

    public Date getDafyRegisterTime() {
        return dafyRegisterTime;
    }

    public void setDafyRegisterTime(Date dafyRegisterTime) {
        this.dafyRegisterTime = dafyRegisterTime;
    }

    public Date getDafyBindCardTime() {
        return dafyBindCardTime;
    }

    public void setDafyBindCardTime(Date dafyBindCardTime) {
        this.dafyBindCardTime = dafyBindCardTime;
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getBindFailReson() {
        return bindFailReson;
    }

    public void setBindFailReson(String bindFailReson) {
        this.bindFailReson = bindFailReson;
    }
}
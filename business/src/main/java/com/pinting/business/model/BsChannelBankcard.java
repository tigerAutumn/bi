package com.pinting.business.model;

import java.util.Date;

public class BsChannelBankcard {
    private Integer id;

    private String cardNo;

    private String cardOwner;

    private String idCard;

    private String mobile;

    private Integer bankId;

    private Date bindTime;

    private Date unbindTime;

    private String bankName;

    private String subBranchName;

    private Integer openProvince;

    private Integer openCity;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCardOwner() {
        return cardOwner;
    }

    public void setCardOwner(String cardOwner) {
        this.cardOwner = cardOwner;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public Date getBindTime() {
        return bindTime;
    }

    public void setBindTime(Date bindTime) {
        this.bindTime = bindTime;
    }

    public Date getUnbindTime() {
        return unbindTime;
    }

    public void setUnbindTime(Date unbindTime) {
        this.unbindTime = unbindTime;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getSubBranchName() {
        return subBranchName;
    }

    public void setSubBranchName(String subBranchName) {
        this.subBranchName = subBranchName;
    }

    public Integer getOpenProvince() {
        return openProvince;
    }

    public void setOpenProvince(Integer openProvince) {
        this.openProvince = openProvince;
    }

    public Integer getOpenCity() {
        return openCity;
    }

    public void setOpenCity(Integer openCity) {
        this.openCity = openCity;
    }
}
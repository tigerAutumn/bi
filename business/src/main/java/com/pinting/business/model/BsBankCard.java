package com.pinting.business.model;

import java.util.Date;

import com.pinting.business.model.vo.PageInfoObject;

public class BsBankCard extends PageInfoObject{
    private Integer id;

    private Integer userId;

    private String cardNo;

    private String cardOwner;

    private String idCard;

    private String mobile;

    private Integer bankId;

    private Integer status;

    private Integer isFirst;

    private Date bindTime;

    private Date unbindTime;

    private String bankName;

    private String subBranchName;

    private Integer openProvinceId;

    private Integer openCityId;

    private String note;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsFirst() {
        return isFirst;
    }

    public void setIsFirst(Integer isFirst) {
        this.isFirst = isFirst;
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

    public Integer getOpenProvinceId() {
        return openProvinceId;
    }

    public void setOpenProvinceId(Integer openProvinceId) {
        this.openProvinceId = openProvinceId;
    }

    public Integer getOpenCityId() {
        return openCityId;
    }

    public void setOpenCityId(Integer openCityId) {
        this.openCityId = openCityId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
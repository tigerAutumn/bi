package com.pinting.business.model;

import java.util.Date;

public class LnAccountFillDetail {
    private Integer id;

    private String partnerCode;

    private String fillType;

    private Date fillDate;

    private Double amount;

    private Integer outAccountId;

    private Integer inAccountId;

    private String relativeNo;

    private String status;

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

    public String getFillType() {
        return fillType;
    }

    public void setFillType(String fillType) {
        this.fillType = fillType;
    }

    public Date getFillDate() {
        return fillDate;
    }

    public void setFillDate(Date fillDate) {
        this.fillDate = fillDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getOutAccountId() {
        return outAccountId;
    }

    public void setOutAccountId(Integer outAccountId) {
        this.outAccountId = outAccountId;
    }

    public Integer getInAccountId() {
        return inAccountId;
    }

    public void setInAccountId(Integer inAccountId) {
        this.inAccountId = inAccountId;
    }

    public String getRelativeNo() {
        return relativeNo;
    }

    public void setRelativeNo(String relativeNo) {
        this.relativeNo = relativeNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
package com.pinting.business.model;

import java.util.Date;

public class BsLiquidationRecord {
    private Integer id;

    private Date liquidationTime;

    private String status;

    private Double hfJshBalance;

    private Double usedRedAmount;

    private String note;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getLiquidationTime() {
        return liquidationTime;
    }

    public void setLiquidationTime(Date liquidationTime) {
        this.liquidationTime = liquidationTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getHfJshBalance() {
        return hfJshBalance;
    }

    public void setHfJshBalance(Double hfJshBalance) {
        this.hfJshBalance = hfJshBalance;
    }

    public Double getUsedRedAmount() {
        return usedRedAmount;
    }

    public void setUsedRedAmount(Double usedRedAmount) {
        this.usedRedAmount = usedRedAmount;
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
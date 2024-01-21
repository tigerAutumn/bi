package com.pinting.mall.model;

import java.util.Date;

public class MallPointsIncome {
    private Integer id;

    private Integer userId;

    private String transType;

    private Long points;

    private String transOrderNo;

    private Date transTime;

    private String status;

    private Date finishTime;

    private String firstInvestFlag;

    private Double investInterest;

    private Double sumInvestAmout;

    private String note;

    private Date createTime;

    private Date updateTime;

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

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public Long getPoints() {
        return points;
    }

    public void setPoints(Long points) {
        this.points = points;
    }

    public String getTransOrderNo() {
        return transOrderNo;
    }

    public void setTransOrderNo(String transOrderNo) {
        this.transOrderNo = transOrderNo;
    }

    public Date getTransTime() {
        return transTime;
    }

    public void setTransTime(Date transTime) {
        this.transTime = transTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public String getFirstInvestFlag() {
        return firstInvestFlag;
    }

    public void setFirstInvestFlag(String firstInvestFlag) {
        this.firstInvestFlag = firstInvestFlag;
    }

    public Double getInvestInterest() {
        return investInterest;
    }

    public void setInvestInterest(Double investInterest) {
        this.investInterest = investInterest;
    }

    public Double getSumInvestAmout() {
        return sumInvestAmout;
    }

    public void setSumInvestAmout(Double sumInvestAmout) {
        this.sumInvestAmout = sumInvestAmout;
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
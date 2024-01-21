package com.pinting.business.model;

import java.util.Date;

public class BsWithdrawCheck {
    private Integer id;

    private Integer userId;

    private Integer terminalType;

    private Integer transDetailId;

    private Integer subTransDetailId;

    private Double amount;

    private String status;

    private String clearingMark;

    private Date createTime;

    private Date checkTime;

    private Date executionTime;

    private String orderNo;

    private Integer mUserId;

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

    public Integer getTerminalType() {
        return terminalType;
    }

    public void setTerminalType(Integer terminalType) {
        this.terminalType = terminalType;
    }

    public Integer getTransDetailId() {
        return transDetailId;
    }

    public void setTransDetailId(Integer transDetailId) {
        this.transDetailId = transDetailId;
    }

    public Integer getSubTransDetailId() {
        return subTransDetailId;
    }

    public void setSubTransDetailId(Integer subTransDetailId) {
        this.subTransDetailId = subTransDetailId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getClearingMark() {
        return clearingMark;
    }

    public void setClearingMark(String clearingMark) {
        this.clearingMark = clearingMark == null ? null : clearingMark.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public Date getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(Date executionTime) {
        this.executionTime = executionTime;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public Integer getmUserId() {
        return mUserId;
    }

    public void setmUserId(Integer mUserId) {
        this.mUserId = mUserId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }
}
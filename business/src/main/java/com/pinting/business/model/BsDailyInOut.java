package com.pinting.business.model;

import java.util.Date;

public class BsDailyInOut {
    private Integer id;

    private Date date;

    private Integer inOut;

    private Double amount;

    private Date expectDate;

    private Integer isExecuted;

    private Integer productId;

    private String note;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getInOut() {
        return inOut;
    }

    public void setInOut(Integer inOut) {
        this.inOut = inOut;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getExpectDate() {
        return expectDate;
    }

    public void setExpectDate(Date expectDate) {
        this.expectDate = expectDate;
    }

    public Integer getIsExecuted() {
        return isExecuted;
    }

    public void setIsExecuted(Integer isExecuted) {
        this.isExecuted = isExecuted;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
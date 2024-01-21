package com.pinting.business.model;

import java.util.Date;

public class Bs19payCheck {
    private Integer id;

    private String orderNo;

    private Date checkTime;

    private Double sysAmount;

    private Double doneAmount;

    private String result;

    private String info;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public Double getSysAmount() {
        return sysAmount;
    }

    public void setSysAmount(Double sysAmount) {
        this.sysAmount = sysAmount;
    }

    public Double getDoneAmount() {
        return doneAmount;
    }

    public void setDoneAmount(Double doneAmount) {
        this.doneAmount = doneAmount;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
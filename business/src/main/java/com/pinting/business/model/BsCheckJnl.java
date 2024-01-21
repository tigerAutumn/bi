package com.pinting.business.model;

import java.util.Date;

public class BsCheckJnl {
    private Integer id;

    private Integer transJnlId;

    private Date time;

    private Double sysAmount;

    private Double doneAmount;

    private Integer result;

    private String info;

    private Integer userId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTransJnlId() {
        return transJnlId;
    }

    public void setTransJnlId(Integer transJnlId) {
        this.transJnlId = transJnlId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
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

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
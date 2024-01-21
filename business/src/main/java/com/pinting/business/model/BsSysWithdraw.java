package com.pinting.business.model;

import java.util.Date;

import com.pinting.business.model.vo.PageInfoObject;

public class BsSysWithdraw extends PageInfoObject{
    private Integer id;

    private String applyNo;

    private Double amount;

    private Date withdrawTime;

    private Date finishTime;

    private Integer status;

    private Integer startJnlNo;

    private Integer endJnlNo;

    private String failReason;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getApplyNo() {
        return applyNo;
    }

    public void setApplyNo(String applyNo) {
        this.applyNo = applyNo;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getWithdrawTime() {
        return withdrawTime;
    }

    public void setWithdrawTime(Date withdrawTime) {
        this.withdrawTime = withdrawTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStartJnlNo() {
        return startJnlNo;
    }

    public void setStartJnlNo(Integer startJnlNo) {
        this.startJnlNo = startJnlNo;
    }

    public Integer getEndJnlNo() {
        return endJnlNo;
    }

    public void setEndJnlNo(Integer endJnlNo) {
        this.endJnlNo = endJnlNo;
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }
}
package com.pinting.business.model;

import java.util.Date;

public class BsMatchRepayDetail {
    private Integer id;

    private Integer matchId;

    private Double repayAmount;

    private Date repayTime;

    private Double leftPrincipal;

    private String note;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMatchId() {
        return matchId;
    }

    public void setMatchId(Integer matchId) {
        this.matchId = matchId;
    }

    public Double getRepayAmount() {
        return repayAmount;
    }

    public void setRepayAmount(Double repayAmount) {
        this.repayAmount = repayAmount;
    }

    public Date getRepayTime() {
        return repayTime;
    }

    public void setRepayTime(Date repayTime) {
        this.repayTime = repayTime;
    }

    public Double getLeftPrincipal() {
        return leftPrincipal;
    }

    public void setLeftPrincipal(Double leftPrincipal) {
        this.leftPrincipal = leftPrincipal;
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
}
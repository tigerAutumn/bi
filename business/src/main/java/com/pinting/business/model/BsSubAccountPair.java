package com.pinting.business.model;

import java.util.Date;

public class BsSubAccountPair {
    private Integer id;

    private Integer authAccountId;

    private Integer regDAccountId;

    private Integer redAccountId;

    private Integer diffAccountId;

    private Integer term;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAuthAccountId() {
        return authAccountId;
    }

    public void setAuthAccountId(Integer authAccountId) {
        this.authAccountId = authAccountId;
    }

    public Integer getRegDAccountId() {
        return regDAccountId;
    }

    public void setRegDAccountId(Integer regDAccountId) {
        this.regDAccountId = regDAccountId;
    }

    public Integer getRedAccountId() {
        return redAccountId;
    }

    public void setRedAccountId(Integer redAccountId) {
        this.redAccountId = redAccountId;
    }

    public Integer getDiffAccountId() {
        return diffAccountId;
    }

    public void setDiffAccountId(Integer diffAccountId) {
        this.diffAccountId = diffAccountId;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
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
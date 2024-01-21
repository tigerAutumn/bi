package com.pinting.mall.model;

import java.util.Date;

public class MallUserSign {
    private Integer id;

    private Integer userId;

    private Date signTime;

    private Date latestTime;

    private Integer signDays;

    private Long signPoints;

    private Long latestPoints;

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

    public Date getSignTime() {
        return signTime;
    }

    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }

    public Date getLatestTime() {
        return latestTime;
    }

    public void setLatestTime(Date latestTime) {
        this.latestTime = latestTime;
    }

    public Integer getSignDays() {
        return signDays;
    }

    public void setSignDays(Integer signDays) {
        this.signDays = signDays;
    }

    public Long getSignPoints() {
        return signPoints;
    }

    public void setSignPoints(Long signPoints) {
        this.signPoints = signPoints;
    }

    public Long getLatestPoints() {
        return latestPoints;
    }

    public void setLatestPoints(Long latestPoints) {
        this.latestPoints = latestPoints;
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
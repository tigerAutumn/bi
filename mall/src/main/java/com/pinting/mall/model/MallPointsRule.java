package com.pinting.mall.model;

import java.util.Date;

public class MallPointsRule {
    private Integer id;

    private String getScene;

    private String getTimes;

    private String getTimeType;

    private Long points;

    private String status;

    private Date triggerTimeStart;

    private Date triggerTimeEnd;

    private Integer creator;

    private Integer lastOperator;

    private String note;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGetScene() {
        return getScene;
    }

    public void setGetScene(String getScene) {
        this.getScene = getScene;
    }

    public String getGetTimes() {
        return getTimes;
    }

    public void setGetTimes(String getTimes) {
        this.getTimes = getTimes;
    }

    public String getGetTimeType() {
        return getTimeType;
    }

    public void setGetTimeType(String getTimeType) {
        this.getTimeType = getTimeType;
    }

    public Long getPoints() {
        return points;
    }

    public void setPoints(Long points) {
        this.points = points;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getTriggerTimeStart() {
        return triggerTimeStart;
    }

    public void setTriggerTimeStart(Date triggerTimeStart) {
        this.triggerTimeStart = triggerTimeStart;
    }

    public Date getTriggerTimeEnd() {
        return triggerTimeEnd;
    }

    public void setTriggerTimeEnd(Date triggerTimeEnd) {
        this.triggerTimeEnd = triggerTimeEnd;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public Integer getLastOperator() {
        return lastOperator;
    }

    public void setLastOperator(Integer lastOperator) {
        this.lastOperator = lastOperator;
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
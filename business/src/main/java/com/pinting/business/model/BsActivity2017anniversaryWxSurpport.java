package com.pinting.business.model;

import java.util.Date;

public class BsActivity2017anniversaryWxSurpport {
    private Integer id;

    private Integer partnerId;

    private Integer wxInfoId;

    private Integer luckyDrawId;

    private Integer activityId;

    private String content;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Integer partnerId) {
        this.partnerId = partnerId;
    }

    public Integer getWxInfoId() {
        return wxInfoId;
    }

    public void setWxInfoId(Integer wxInfoId) {
        this.wxInfoId = wxInfoId;
    }

    public Integer getLuckyDrawId() {
        return luckyDrawId;
    }

    public void setLuckyDrawId(Integer luckyDrawId) {
        this.luckyDrawId = luckyDrawId;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
package com.pinting.business.model;

import java.util.Date;

public class BsEcup2016ActivityUser {
    private Integer id;

    private Integer userId;

    private String champion;

    private String silver;

    private Integer supportNum;

    private String isLucky;

    private Date supportMilestoneTime;

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

    public String getChampion() {
        return champion;
    }

    public void setChampion(String champion) {
        this.champion = champion;
    }

    public String getSilver() {
        return silver;
    }

    public void setSilver(String silver) {
        this.silver = silver;
    }

    public Integer getSupportNum() {
        return supportNum;
    }

    public void setSupportNum(Integer supportNum) {
        this.supportNum = supportNum;
    }

    public String getIsLucky() {
        return isLucky;
    }

    public void setIsLucky(String isLucky) {
        this.isLucky = isLucky;
    }

    public Date getSupportMilestoneTime() {
        return supportMilestoneTime;
    }

    public void setSupportMilestoneTime(Date supportMilestoneTime) {
        this.supportMilestoneTime = supportMilestoneTime;
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
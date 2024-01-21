package com.pinting.business.model;

import java.util.Date;

public class BsWaterConservationSignUp {
    private Integer id;

    private Integer serialNo;

    private Integer userId;

    private Integer activityId;

    private String userName;

    private String mobile;

    private Integer familyNum;

    private Double monthWaterRate;

    private String content;

    private String waterSavePhoto;

    private String waterRatePhoto;

    private Integer voteNum;

    private String checkStatus;

    private Integer checkUserId;

    private Date checkTime;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(Integer serialNo) {
        this.serialNo = serialNo;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public Integer getFamilyNum() {
        return familyNum;
    }

    public void setFamilyNum(Integer familyNum) {
        this.familyNum = familyNum;
    }

    public Double getMonthWaterRate() {
        return monthWaterRate;
    }

    public void setMonthWaterRate(Double monthWaterRate) {
        this.monthWaterRate = monthWaterRate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getWaterSavePhoto() {
        return waterSavePhoto;
    }

    public void setWaterSavePhoto(String waterSavePhoto) {
        this.waterSavePhoto = waterSavePhoto == null ? null : waterSavePhoto.trim();
    }

    public String getWaterRatePhoto() {
        return waterRatePhoto;
    }

    public void setWaterRatePhoto(String waterRatePhoto) {
        this.waterRatePhoto = waterRatePhoto == null ? null : waterRatePhoto.trim();
    }

    public Integer getVoteNum() {
        return voteNum;
    }

    public void setVoteNum(Integer voteNum) {
        this.voteNum = voteNum;
    }

    public String getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus == null ? null : checkStatus.trim();
    }

    public Integer getCheckUserId() {
        return checkUserId;
    }

    public void setCheckUserId(Integer checkUserId) {
        this.checkUserId = checkUserId;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
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
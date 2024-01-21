package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Author:      cyb
 * Date:        2017/3/23
 * Description:
 */
public class ReqMsg_AgentActivity_WaterSignUp extends ReqMsg {

    private static final long serialVersionUID = 1825747392707725499L;

    private Integer userId;

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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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
        this.content = content;
    }

    public String getWaterSavePhoto() {
        return waterSavePhoto;
    }

    public void setWaterSavePhoto(String waterSavePhoto) {
        this.waterSavePhoto = waterSavePhoto;
    }

    public String getWaterRatePhoto() {
        return waterRatePhoto;
    }

    public void setWaterRatePhoto(String waterRatePhoto) {
        this.waterRatePhoto = waterRatePhoto;
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
        this.checkStatus = checkStatus;
    }

    public Integer getCheckUserId() {
        return checkUserId;
    }

    public void setCheckUserId(Integer checkUserId) {
        this.checkUserId = checkUserId;
    }
}


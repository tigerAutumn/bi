package com.pinting.business.model;

import java.util.Date;

public class BsAutoRedPacketRule {
    private Integer id;

    private String serialNo;

    private String agentIds;

    private String triggerType;

    private String validTermType;

    private String status;

    private Double triggerAmountStart;

    private Double triggerAmountEnd;

    private Integer triggerInviteNum;

    private Date distributeTimeStart;

    private Date distributeTimeEnd;

    private Integer availableDays;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getAgentIds() {
        return agentIds;
    }

    public void setAgentIds(String agentIds) {
        this.agentIds = agentIds;
    }

    public String getTriggerType() {
        return triggerType;
    }

    public void setTriggerType(String triggerType) {
        this.triggerType = triggerType;
    }

    public String getValidTermType() {
        return validTermType;
    }

    public void setValidTermType(String validTermType) {
        this.validTermType = validTermType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getTriggerAmountStart() {
        return triggerAmountStart;
    }

    public void setTriggerAmountStart(Double triggerAmountStart) {
        this.triggerAmountStart = triggerAmountStart;
    }

    public Double getTriggerAmountEnd() {
        return triggerAmountEnd;
    }

    public void setTriggerAmountEnd(Double triggerAmountEnd) {
        this.triggerAmountEnd = triggerAmountEnd;
    }

    public Integer getTriggerInviteNum() {
        return triggerInviteNum;
    }

    public void setTriggerInviteNum(Integer triggerInviteNum) {
        this.triggerInviteNum = triggerInviteNum;
    }

    public Date getDistributeTimeStart() {
        return distributeTimeStart;
    }

    public void setDistributeTimeStart(Date distributeTimeStart) {
        this.distributeTimeStart = distributeTimeStart;
    }

    public Date getDistributeTimeEnd() {
        return distributeTimeEnd;
    }

    public void setDistributeTimeEnd(Date distributeTimeEnd) {
        this.distributeTimeEnd = distributeTimeEnd;
    }

    public Integer getAvailableDays() {
        return availableDays;
    }

    public void setAvailableDays(Integer availableDays) {
        this.availableDays = availableDays;
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
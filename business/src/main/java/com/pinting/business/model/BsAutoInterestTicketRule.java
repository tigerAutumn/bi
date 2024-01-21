package com.pinting.business.model;

import java.util.Date;

public class BsAutoInterestTicketRule {
    private Integer id; // 自动加息券规则表

    private String serialNo; // 批次号

    private String agentIds; // 支持多个，用逗号隔开 -1，全部渠道 0，普通用户

    private String triggerType; // 触发条件类型：HAPPY_BIRTHDAY（生日福利）

    private Date triggerTimeStart; // HAPPY_BIRTHDAY：触发开始时间

    private Date triggerTimeEnd; // HAPPY_BIRTHDAY：触发结束时间

    private Date createTime; // 创建时间

    private Date updateTime; // 更新时间

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
        this.serialNo = serialNo == null ? null : serialNo.trim();
    }

    public String getAgentIds() {
        return agentIds;
    }

    public void setAgentIds(String agentIds) {
        this.agentIds = agentIds == null ? null : agentIds.trim();
    }

    public String getTriggerType() {
        return triggerType;
    }

    public void setTriggerType(String triggerType) {
        this.triggerType = triggerType == null ? null : triggerType.trim();
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
package com.pinting.business.model.vo;

import java.util.Date;

/**
 * Created by cyb on 2018/4/4.
 */
public class TicketGrantPlanCheckVO {

    private Integer checkId;

    private String serialNo; // 发放计划批次号

    private String checkStatus; // UNCHECKED 待审核 PASS 已通过 REFUSE 不通过

    private String grantStatus; // 发放状态: INIT 未发放 PROCESS 发放中 FINISH 发放结束 CLOSE 已停用

    // 加息券发放规则
    private Integer ruleId; // 触发规则ID

    private String triggerType; // 触发条件类型：HAPPY_BIRTHDAY（生日福利）

    private Date triggerTimeStart; // HAPPY_BIRTHDAY：触发开始时间

    private Date triggerTimeEnd; // HAPPY_BIRTHDAY：触发结束时间

    private String agentIds; // 支持多个，用逗号隔开 -1，全部渠道 0，普通用户

    // 加息券信息
    private Integer attrId; // 属性ID

    private String ticketName; // 加息券名称

    private Double ticketApr; // 加息幅度(基于本金的加息率%)

    private Integer grantTotal; // 发放加息券总数

    private Integer grantNum; // 已发放加息券数量

    private String validTermType; // 加息券发放有效期类型:FIXED 固定时间段有效 AFTER_RECEIVE 发放后有效天数

    private Date useTimeStart; // FIXED 固定时间段：使用有效期开始

    private Date useTimeEnd; // FIXED 固定时间段：使用有效期结束

    private Integer availableDays; // AFTER_RECEIVE 发放后有效天数：使用

    private String notifyChannel; // WECHAT 微信 SMS 短信 APP app通知 可以多个，以逗号隔开

    private Double investLimit; // 达到投资金额加息券才能使用(单笔投资满多少元)

    private String productLimit; // BIGANGWAN_SERIAL（港湾系列） YONGJIN_SERIAL（涌金系列） KUAHONG_SERIAL（跨虹系列） BAOXIN_SERIAL（保信系列） 多个产品限制用逗号隔开

    private String termLimit; // 30,90,180,365（天） 多个产品期限限制用逗号隔开

    private String note; // 备注说明


    public Integer getCheckId() {
        return checkId;
    }

    public void setCheckId(Integer checkId) {
        this.checkId = checkId;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getGrantStatus() {
        return grantStatus;
    }

    public void setGrantStatus(String grantStatus) {
        this.grantStatus = grantStatus;
    }

    public Integer getRuleId() {
        return ruleId;
    }

    public void setRuleId(Integer ruleId) {
        this.ruleId = ruleId;
    }

    public String getTriggerType() {
        return triggerType;
    }

    public void setTriggerType(String triggerType) {
        this.triggerType = triggerType;
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

    public String getAgentIds() {
        return agentIds;
    }

    public void setAgentIds(String agentIds) {
        this.agentIds = agentIds;
    }

    public Integer getAttrId() {
        return attrId;
    }

    public void setAttrId(Integer attrId) {
        this.attrId = attrId;
    }

    public String getTicketName() {
        return ticketName;
    }

    public void setTicketName(String ticketName) {
        this.ticketName = ticketName;
    }

    public Double getTicketApr() {
        return ticketApr;
    }

    public void setTicketApr(Double ticketApr) {
        this.ticketApr = ticketApr;
    }

    public Integer getGrantTotal() {
        return grantTotal;
    }

    public void setGrantTotal(Integer grantTotal) {
        this.grantTotal = grantTotal;
    }

    public Integer getGrantNum() {
        return grantNum;
    }

    public void setGrantNum(Integer grantNum) {
        this.grantNum = grantNum;
    }

    public String getValidTermType() {
        return validTermType;
    }

    public void setValidTermType(String validTermType) {
        this.validTermType = validTermType;
    }

    public Date getUseTimeStart() {
        return useTimeStart;
    }

    public void setUseTimeStart(Date useTimeStart) {
        this.useTimeStart = useTimeStart;
    }

    public Date getUseTimeEnd() {
        return useTimeEnd;
    }

    public void setUseTimeEnd(Date useTimeEnd) {
        this.useTimeEnd = useTimeEnd;
    }

    public Integer getAvailableDays() {
        return availableDays;
    }

    public void setAvailableDays(Integer availableDays) {
        this.availableDays = availableDays;
    }

    public String getNotifyChannel() {
        return notifyChannel;
    }

    public void setNotifyChannel(String notifyChannel) {
        this.notifyChannel = notifyChannel;
    }

    public Double getInvestLimit() {
        return investLimit;
    }

    public void setInvestLimit(Double investLimit) {
        this.investLimit = investLimit;
    }

    public String getProductLimit() {
        return productLimit;
    }

    public void setProductLimit(String productLimit) {
        this.productLimit = productLimit;
    }

    public String getTermLimit() {
        return termLimit;
    }

    public void setTermLimit(String termLimit) {
        this.termLimit = termLimit;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}

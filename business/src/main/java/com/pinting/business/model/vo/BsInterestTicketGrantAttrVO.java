package com.pinting.business.model.vo;

import java.util.Date;

/**
 * 加息券bean对象
 */
public class BsInterestTicketGrantAttrVO {

    // 查询条件
    private Integer checkId; // 卡券发放批次计划审核表

    private String serialNo; // 发放计划批次号

    private String serialName; // 发放计划名称

    private String distributeType; // 发放模式：AUTO 自动 MANUAL 手动

    private Integer applicant; // 申请人

    private String applicantName; // 申请人名称

    private Date applyTime; // 申请时间

    private String checkStatus; // UNCHECKED 待审核 PASS 已通过 REFUSE 不通过

    private String grantStatus; // 发放状态: INIT 未发放 PROCESS 发放中 FINISH 发放结束 CLOSE 已停用

    private Integer checker; // 审核人

    private String checkerName; // 审核人名称

    private Date checkTime; // 审核时间

    private Integer attrId; // 加息券发放批次属性表

    private Double ticketApr; // 加息幅度(基于本金的加息率%)

    private Integer grantTotal; // 发放加息券总数

    private Integer grantNum; // 已发放加息券数量

    private String validTermType; // 加息券发放有效期类型:FIXED 固定时间段有效 AFTER_RECEIVE 发放后有效天数

    private String useTimeStart; // FIXED 固定时间段：使用有效期开始

    private String useTimeEnd; // FIXED 固定时间段：使用有效期结束

    private Integer availableDays; // AFTER_RECEIVE 发放后有效天数：使用

    private String notifyChannel; // WECHAT 微信 SMS 短信 APP app通知 可以多个，以逗号隔开

    private Double investLimit; // 达到投资金额加息券才能使用(单笔投资满多少元)

    private String productLimit; // BIGANGWAN_SERIAL（港湾系列） YONGJIN_SERIAL（涌金系列） KUAHONG_SERIAL（跨虹系列） BAOXIN_SERIAL（保信系列） 多个产品限制用逗号隔开

    private String termLimit; // 30,90,180,365（天） 多个产品期限限制用逗号隔开

    private Integer ruleId; // 自动加息券规则表

    private String agentIds; // 支持多个，用逗号隔开 -1，全部渠道 0，普通用户

    private String agentIdsDesc; // 渠道描述

    private String triggerType; // 触发条件类型：HAPPY_BIRTHDAY（生日福利）

    private String triggerTimeStart; // HAPPY_BIRTHDAY：触发开始时间

    private String triggerTimeEnd; // HAPPY_BIRTHDAY：触发结束时间

    private String userIds; // 理财用户ID集合字符串，由,拼接

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

    public String getSerialName() {
        return serialName;
    }

    public void setSerialName(String serialName) {
        this.serialName = serialName;
    }

    public String getDistributeType() {
        return distributeType;
    }

    public void setDistributeType(String distributeType) {
        this.distributeType = distributeType;
    }

    public Integer getAttrId() {
        return attrId;
    }

    public void setAttrId(Integer attrId) {
        this.attrId = attrId;
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

    public Integer getRuleId() {
        return ruleId;
    }

    public void setRuleId(Integer ruleId) {
        this.ruleId = ruleId;
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

    public Integer getApplicant() {
        return applicant;
    }

    public void setApplicant(Integer applicant) {
        this.applicant = applicant;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
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

    public Integer getChecker() {
        return checker;
    }

    public void setChecker(Integer checker) {
        this.checker = checker;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public String getUserIds() {
        return userIds;
    }

    public void setUserIds(String userIds) {
        this.userIds = userIds;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getCheckerName() {
        return checkerName;
    }

    public void setCheckerName(String checkerName) {
        this.checkerName = checkerName;
    }

    public String getAgentIdsDesc() {
        return agentIdsDesc;
    }

    public void setAgentIdsDesc(String agentIdsDesc) {
        this.agentIdsDesc = agentIdsDesc;
    }

    public String getUseTimeStart() {
        return useTimeStart;
    }

    public void setUseTimeStart(String useTimeStart) {
        this.useTimeStart = useTimeStart;
    }

    public String getUseTimeEnd() {
        return useTimeEnd;
    }

    public void setUseTimeEnd(String useTimeEnd) {
        this.useTimeEnd = useTimeEnd;
    }

    public String getTriggerTimeStart() {
        return triggerTimeStart;
    }

    public void setTriggerTimeStart(String triggerTimeStart) {
        this.triggerTimeStart = triggerTimeStart;
    }

    public String getTriggerTimeEnd() {
        return triggerTimeEnd;
    }

    public void setTriggerTimeEnd(String triggerTimeEnd) {
        this.triggerTimeEnd = triggerTimeEnd;
    }
}
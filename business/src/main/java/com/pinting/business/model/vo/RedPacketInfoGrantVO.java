package com.pinting.business.model.vo;

import java.util.Date;

/**
 * 生日福利红包发放
 * @author SHENGUOPING
 * @date  2018年8月9日 下午2:38:52
 */
public class RedPacketInfoGrantVO {

	private Integer checkId;
	
	private String serialNo; // 发放计划批次号
	
	private String applyNo;
	
	private String serialName;
	
	private String distributeType;
	
	private String policyType; // 红包策略
	
	private String checkStatus; // UNCHECKED 待审核 PASS 已通过 REFUSE 不通过
	
	private String validTermType;
	
	private Integer availableDays; // AFTER_RECEIVE 发放后有效天数：使用

	private String notifyChannel; // WECHAT 微信 SMS 短信 APP app通知 可以多个，以逗号隔开
	
	private Integer total;
	
	private String termLimit; // 30,90,180,365（天） 多个产品期限限制用逗号隔开

    private String note; // 备注说明
	
    // 加息券发放规则
    private Integer ruleId; // 触发规则ID

    private String triggerType; // 触发条件类型：HAPPY_BIRTHDAY（生日福利）

    private Date distributeTimeStart; // HAPPY_BIRTHDAY：触发开始时间

    private Date distributeTimeEnd; // HAPPY_BIRTHDAY：触发结束时间
    
    private Date useTimeStart; // HAPPY_BIRTHDAY：有效开始时间

    private Date useTimeEnd; // HAPPY_BIRTHDAY：有效结束时间

    private String agentIds; // 支持多个，用逗号隔开 -1，全部渠道 0，普通用户
    
    private Double amount; // 红包金额

    private Integer sendCount;  // 已发数量
    
    private Integer leftCount; // 剩余可发数量
    
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

	public String getPolicyType() {
		return policyType;
	}

	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}

	public String getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
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

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
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

	public String getAgentIds() {
		return agentIds;
	}

	public void setAgentIds(String agentIds) {
		this.agentIds = agentIds;
	}

	public String getValidTermType() {
		return validTermType;
	}

	public void setValidTermType(String validTermType) {
		this.validTermType = validTermType;
	}

	public Integer getSendCount() {
		return sendCount;
	}

	public void setSendCount(Integer sendCount) {
		this.sendCount = sendCount;
	}

	public Integer getLeftCount() {
		return leftCount;
	}

	public void setLeftCount(Integer leftCount) {
		this.leftCount = leftCount;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
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

	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}
    
}

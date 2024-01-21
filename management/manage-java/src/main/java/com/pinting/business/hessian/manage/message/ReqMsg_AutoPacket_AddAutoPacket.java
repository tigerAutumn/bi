package com.pinting.business.hessian.manage.message;

import java.util.Date;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_AutoPacket_AddAutoPacket extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5798225331692150994L;

	private String applyNo;
	
	private String serialName;
	
	private String policyType;
	
	private String distributeType;
	
	private Integer total;

	private Double amount;

	private String useCondition;

	private Double full;

	private Double subtract;

	private Date useTimeStart;

	private Date useTimeEnd;
	
	private String notifyChannel;
	
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
    
    private Integer applicant;
    
    private String termLimit;

    private String note;

    public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
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

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getUseCondition() {
		return useCondition;
	}

	public void setUseCondition(String useCondition) {
		this.useCondition = useCondition;
	}

	public Double getFull() {
		return full;
	}

	public void setFull(Double full) {
		this.full = full;
	}

	public Double getSubtract() {
		return subtract;
	}

	public void setSubtract(Double subtract) {
		this.subtract = subtract;
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

	public String getNotifyChannel() {
		return notifyChannel;
	}

	public void setNotifyChannel(String notifyChannel) {
		this.notifyChannel = notifyChannel;
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

	public Integer getApplicant() {
		return applicant;
	}

	public void setApplicant(Integer applicant) {
		this.applicant = applicant;
	}

	public String getPolicyType() {
		return policyType;
	}

	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}

	
}

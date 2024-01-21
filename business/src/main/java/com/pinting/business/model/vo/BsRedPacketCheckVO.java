package com.pinting.business.model.vo;

import java.util.Date;

import com.pinting.business.model.BsRedPacketCheck;

public class BsRedPacketCheckVO extends BsRedPacketCheck {
	private String triggerType;  //自动红包触发条件类型
	private String validTermType; //红包有效期类型
	private Integer availableDays; //发放固定期限的红包有效天数
	private String status; //自动红包是否停用
    private Date distributeTimeStart;  //自动红包发放时间开始
    private Date distributeTimeEnd;   //自动红包发放时间结束
    private String rpName;
    private String applicantName;
    private String checkerName;
    private Integer channelNum;  //渠道个数
    private String agentIds;
	
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
	public Integer getAvailableDays() {
		return availableDays;
	}
	public void setAvailableDays(Integer availableDays) {
		this.availableDays = availableDays;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getRpName() {
		return rpName;
	}
	public void setRpName(String rpName) {
		this.rpName = rpName;
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
	public Integer getChannelNum() {
		return channelNum;
	}
	public void setChannelNum(Integer channelNum) {
		this.channelNum = channelNum;
	}
	public String getAgentIds() {
		return agentIds;
	}
	public void setAgentIds(String agentIds) {
		this.agentIds = agentIds;
	}
	
}

package com.pinting.business.model.vo;

import java.util.Date;

import com.pinting.business.model.BsAutoRedPacketRule;

public class AutoRedPocketReviewVO extends BsAutoRedPacketRule {

    private String serialName;
    
    private String applyNo;
    
    private Double full;

    private Double subtract;
    
    private Date useTimeStart;

    private Date useTimeEnd;

    private Integer total;

    private String notifyChannel;
    
    private String rpName;
    
    private String applicantName;
    
    private Double amount;

    private String useCondition;

    private String termLimit;

    private String note;
    
    private String policyType;

	public String getSerialName() {
		return serialName;
	}

	public void setSerialName(String serialName) {
		this.serialName = serialName;
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

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public String getNotifyChannel() {
		return notifyChannel;
	}

	public void setNotifyChannel(String notifyChannel) {
		this.notifyChannel = notifyChannel;
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

	public String getPolicyType() {
		return policyType;
	}

	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}

	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}
    
	
}

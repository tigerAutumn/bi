package com.pinting.business.hessian.manage.message;

import java.util.List;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_ManualPacket_ManualPacketAdd extends ReqMsg {

	/**
	 * 序列化编号
	 */
	private static final long serialVersionUID = 5798225331692150994L;

	private String applyNo;
	
	private String serialName;
	
	private Integer total;

	private Double amount;

	private String useCondition;

	private Double full;

	private Double subtract;

	private String useTimeStart;

	private String useTimeEnd;
	
	private String notifyChannel;
	
	private String note;
	
	private Integer applicant;
	
	private String policyType;
	
	 /**
     * 所有推送的用户Id
     */
    private List<Integer> userIdList;
    
    private String manualConditions;

    private String termLimit;

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

	public String getNotifyChannel() {
		return notifyChannel;
	}

	public void setNotifyChannel(String notifyChannel) {
		this.notifyChannel = notifyChannel;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public List<Integer> getUserIdList() {
		return userIdList;
	}

	public void setUserIdList(List<Integer> userIdList) {
		this.userIdList = userIdList;
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

	public Integer getApplicant() {
		return applicant;
	}

	public void setApplicant(Integer applicant) {
		this.applicant = applicant;
	}

	public String getManualConditions() {
		return manualConditions;
	}

	public void setManualConditions(String manualConditions) {
		this.manualConditions = manualConditions;
	}

	public String getTermLimit() {
		return termLimit;
	}

	public void setTermLimit(String termLimit) {
		this.termLimit = termLimit;
	}

	public String getPolicyType() {
		return policyType;
	}

	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}
}

package com.pinting.gateway.hessian.message.qb178;

import java.util.Date;

import com.pinting.core.hessian.msg.ReqMsg;

public class B2GReqMsg_APP178_UpdateRepayPlan extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5469570511705251356L;
	/* 用户账号 */
	private String userAccount;
	/* 产品编号 */
	private Integer productCode;
	/* 期数 */
	private Integer period;
	/* 计划状态未到期（WAITING）;已完成（FINISH） */
	private String jetPlanStatus;
	/* 实际还款日期，计划状态更新为已完成时必填 */
	private Date realDate;
	/* 计划还款日期  */
	private Date planDate;
	/* 合作商流水号，全局唯一，建议时间戳 */
	private String serialNo;
	
	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	public Integer getProductCode() {
		return productCode;
	}
	public void setProductCode(Integer productCode) {
		this.productCode = productCode;
	}
	public Integer getPeriod() {
		return period;
	}
	public void setPeriod(Integer period) {
		this.period = period;
	}
	public String getJetPlanStatus() {
		return jetPlanStatus;
	}
	public void setJetPlanStatus(String jetPlanStatus) {
		this.jetPlanStatus = jetPlanStatus;
	}
	public Date getRealDate() {
		return realDate;
	}
	public void setRealDate(Date realDate) {
		this.realDate = realDate;
	}
	public Date getPlanDate() {
		return planDate;
	}
	public void setPlanDate(Date planDate) {
		this.planDate = planDate;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	
	
}

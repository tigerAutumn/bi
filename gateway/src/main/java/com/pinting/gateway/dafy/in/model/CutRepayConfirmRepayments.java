package com.pinting.gateway.dafy.in.model;

/**
 * 自主放款-代扣还款-二级循环
 * @author bianyatian
 * @2016-11-29 上午10:14:17
 */
public class CutRepayConfirmRepayments {
	
	private String repayId; //账单编号
	
	private String status; //账单状态
	
	private String repayType; //还款类型
	
	private Long repaySerial; //账单序号
	
	private Long total; //总金额,单位分
	
	private Long principal; //本金,单位分
	
	private Long interest; //利息,单位分
	
	private Long principalOverdue; //本金滞纳金,单位分,非必填
	
	private Long interestOverdue; //利息滞纳金,单位分,非必填
	
	private String reservedField1; //预留字段1

	public String getRepayId() {
		return repayId;
	}

	public void setRepayId(String repayId) {
		this.repayId = repayId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRepayType() {
		return repayType;
	}

	public void setRepayType(String repayType) {
		this.repayType = repayType;
	}

	public Long getRepaySerial() {
		return repaySerial;
	}

	public void setRepaySerial(Long repaySerial) {
		this.repaySerial = repaySerial;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public Long getPrincipal() {
		return principal;
	}

	public void setPrincipal(Long principal) {
		this.principal = principal;
	}

	public Long getInterest() {
		return interest;
	}

	public void setInterest(Long interest) {
		this.interest = interest;
	}

	public Long getPrincipalOverdue() {
		return principalOverdue;
	}

	public void setPrincipalOverdue(Long principalOverdue) {
		this.principalOverdue = principalOverdue;
	}

	public Long getInterestOverdue() {
		return interestOverdue;
	}

	public void setInterestOverdue(Long interestOverdue) {
		this.interestOverdue = interestOverdue;
	}

	public String getReservedField1() {
		return reservedField1;
	}

	public void setReservedField1(String reservedField1) {
		this.reservedField1 = reservedField1;
	}
	
	

}

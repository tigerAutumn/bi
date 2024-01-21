package com.dafy.model.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 自主放款-账单同步
 * @author Dragon & cat
 * @date 2016-11-24
 */
public class Repayments implements Serializable{
	private static final long serialVersionUID = -5792138619444892621L;
	/*账单编号*/
	private		String		repayId;
	/*账单状态*/
	private  	String  	status;
	/*账单日*/
	private		Date		repayDate;
	/*账单序号*/
	private 	Integer		repaySerial;
	/*总金额*/
	private 	Long		total;
	/*本金*/
	private 	Long		principal;
	/*利息*/
	private  	Long		interest;
	/*本金逾期金额*/
	private		Long		principalOverdue;
	/*利息逾期金额*/
	private		Long		interestOverdue;
	/*预留字段1*/
	private		String		reservedField1;
	
	private		String 		bgwOrderNo;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getRepayDate() {
		return repayDate;
	}
	public void setRepayDate(Date repayDate) {
		this.repayDate = repayDate;
	}
	public Integer getRepaySerial() {
		return repaySerial;
	}
	public void setRepaySerial(Integer repaySerial) {
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
	public String getRepayId() {
		return repayId;
	}
	public void setRepayId(String repayId) {
		this.repayId = repayId;
	}
	public String getBgwOrderNo() {
		return bgwOrderNo;
	}
	public void setBgwOrderNo(String bgwOrderNo) {
		this.bgwOrderNo = bgwOrderNo;
	}
	
	
}

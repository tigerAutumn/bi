package com.pinting.gateway.hessian.message.dafy.model;

import java.io.Serializable;
import java.util.Date;
/**
 * 自主放款-账单同步查询
 * @author Dragon & cat
 * @date 2016-12-15
 */
public class QueryBillRepayment implements Serializable{
	private static final long serialVersionUID = 4371699445938109000L;
	/*账单编号*/
	private		String		repayId;
	/*账单状态*/
	private  	String  	status;
	/*账单日*/
	private		Date		repayDate;
	/*账单序号*/
	private 	Integer		repaySerial;
	/*总金额*/
	private 	Double		total;
	/*本金*/
	private 	Double		principal;
	/*利息*/
	private  	Double		interest;
	/*本金逾期金额*/
	private		Double		principalOverdue;
	/*利息逾期金额*/
	private		Double		interestOverdue;
	/*预留字段1*/
	private		String		reservedField1;
	/*支付订单号*/
	private 	String		bgwOrderNo;
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
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public Double getPrincipal() {
		return principal;
	}
	public void setPrincipal(Double principal) {
		this.principal = principal;
	}
	public Double getInterest() {
		return interest;
	}
	public void setInterest(Double interest) {
		this.interest = interest;
	}
	public Double getPrincipalOverdue() {
		return principalOverdue;
	}
	public void setPrincipalOverdue(Double principalOverdue) {
		this.principalOverdue = principalOverdue;
	}
	public Double getInterestOverdue() {
		return interestOverdue;
	}
	public void setInterestOverdue(Double interestOverdue) {
		this.interestOverdue = interestOverdue;
	}
	public String getReservedField1() {
		return reservedField1;
	}
	public void setReservedField1(String reservedField1) {
		this.reservedField1 = reservedField1;
	}
	public String getBgwOrderNo() {
		return bgwOrderNo;
	}
	public void setBgwOrderNo(String bgwOrderNo) {
		this.bgwOrderNo = bgwOrderNo;
	}
}

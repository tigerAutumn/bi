package com.pinting.gateway.hessian.message.dafy;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.pinting.core.hessian.msg.ReqMsg;

public class G2BReqMsg_DafyLoan_QueryLoanResult extends ReqMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2170974354173107593L;
	/*放款单号*/
	@NotEmpty(message="orderNo为空")
	private			String		orderNo;
	/*提交放款日期*/
	@NotNull(message="applyDate为空")
	private			Date		applyDate;
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public Date getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	
	
}

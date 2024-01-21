package com.pinting.gateway.hessian.message.loan7;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.pinting.core.hessian.msg.ReqMsg;
/**
 * 
 * @project gateway
 * @title G2BReqMsg_DepLoan7_QueryLoanResult.java
 * @author Dragon & cat
 * @date 2017-12-12
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
public class G2BReqMsg_DepLoan7_QueryLoanResult extends ReqMsg {
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

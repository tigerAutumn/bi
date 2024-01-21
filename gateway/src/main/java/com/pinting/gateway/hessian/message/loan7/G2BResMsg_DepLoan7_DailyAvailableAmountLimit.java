package com.pinting.gateway.hessian.message.loan7;

import java.util.Date;

import com.pinting.core.hessian.msg.ResMsg;
/**
 * 7贷存管-每日可借额度查询
 * @project gateway
 * @title G2BResMsg_DepLoan7_DailyAvailableAmountLimit.java
 * @author Dragon & cat
 * @date 2017-12-12
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
public class G2BResMsg_DepLoan7_DailyAvailableAmountLimit extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8580770089449321412L;
	
	
	/*可借额度日期*/
	private			Date		loanDate;
	/*额度*/
	private			Double		amount;
	
	public Date getLoanDate() {
		return loanDate;
	}
	public void setLoanDate(Date loanDate) {
		this.loanDate = loanDate;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}

}

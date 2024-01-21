package com.pinting.gateway.hessian.message.dafy;

import java.util.Date;

import com.pinting.core.hessian.msg.ResMsg;
/**
 * 自主放款-每日可借额度查询
 * @Title: G2BResMsg_DafyLoan_DailyAvailableAmountLimit.java
 * @author Dragon & cat
 * @date 2016-11-25
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class G2BResMsg_DafyLoan_DailyAvailableAmountLimit extends ResMsg {

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

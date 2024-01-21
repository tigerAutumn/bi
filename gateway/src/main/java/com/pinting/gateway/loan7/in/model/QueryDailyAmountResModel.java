package com.pinting.gateway.loan7.in.model;

import java.util.Date;
/**
 * 自主放款-每日可借额度查询
 * @author Dragon & cat
 * @date 2016-11-25
 */
public class QueryDailyAmountResModel extends BaseResModel {
	/*可借额度日期*/
	private			Date		loanDate;
	/*额度*/
	private			Long		amount;
	public Date getLoanDate() {
		return loanDate;
	}
	public void setLoanDate(Date loanDate) {
		this.loanDate = loanDate;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	
	
}

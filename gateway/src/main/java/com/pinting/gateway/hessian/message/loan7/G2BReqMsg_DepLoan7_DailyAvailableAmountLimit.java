package com.pinting.gateway.hessian.message.loan7;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.pinting.core.hessian.msg.ReqMsg;
/**
 * 7贷存管-每日可借额度查询
 * @project gateway
 * @title G2BReqMsg_DepLoan7_DailyAvailableAmountLimit.java
 * @author Dragon & cat
 * @date 2017-12-12
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
public class G2BReqMsg_DepLoan7_DailyAvailableAmountLimit extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1094569338118419612L;
	
	/*可借额度日期*/
	@NotNull(message="queryDate为空")
	private		Date		queryDate;

	public Date getQueryDate() {
		return queryDate;
	}

	public void setQueryDate(Date queryDate) {
		this.queryDate = queryDate;
	}
	
}

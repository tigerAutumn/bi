package com.pinting.gateway.hessian.message.dafy;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.pinting.core.hessian.msg.ReqMsg;
/**
 * 自主放款-每日可借额度查询
 * @Title: G2BReqMsg_DafyLoan_DailyAvailableAmountLimit.java
 * @author Dragon & cat
 * @date 2016-11-25
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class G2BReqMsg_DafyLoan_DailyAvailableAmountLimit extends ReqMsg {

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

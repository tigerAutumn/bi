package com.pinting.gateway.dafy.in.model;

import java.util.Date;

/**
 * 自主放款-每日可借额度查询
 * @author Dragon & cat
 * @date 2016-11-25
 */
public class QueryDailyAmountReqModel extends BaseReqModel {
	/*可借额度日期*/
	private		Date		queryDate;
	/*客户端标识*/
	private		String 		clientKey;

	public Date getQueryDate() {
		return queryDate;
	}

	public void setQueryDate(Date queryDate) {
		this.queryDate = queryDate;
	}

	public String getClientKey() {
		return clientKey;
	}

	public void setClientKey(String clientKey) {
		this.clientKey = clientKey;
	}
	
}

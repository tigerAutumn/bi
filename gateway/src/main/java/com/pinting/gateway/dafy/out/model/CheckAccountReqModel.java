package com.pinting.gateway.dafy.out.model;

import java.util.Date;

/**
 * @Project: gateway
 * @Title: CheckAccountReqModel.java
 * @author Zhou Changzai
 * @date 2015-2-11 下午3:45:18
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class CheckAccountReqModel extends BaseReqModel {
	private Date queryDate;

	public Date getQueryDate() {
		return queryDate;
	}

	public void setQueryDate(Date queryDate) {
		this.queryDate = queryDate;
	}
}

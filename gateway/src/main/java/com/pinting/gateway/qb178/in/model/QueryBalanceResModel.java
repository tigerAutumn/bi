package com.pinting.gateway.qb178.in.model;

import java.util.List;

/**
 * 查询会员资金余额 res
 * @author bianyatian
 * @2017-7-28 下午4:35:13
 */
public class QueryBalanceResModel extends BaseResModel {

	/* 会员账号余额情况 */
	private List<QueryBalanceInfo> data;

	public List<QueryBalanceInfo> getData() {
		return data;
	}

	public void setData(List<QueryBalanceInfo> data) {
		this.data = data;
	}

	
}

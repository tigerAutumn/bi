package com.pinting.gateway.qb178.in.model;

import java.util.List;

public class QueryBalanceJnlResModel extends BaseResModel {
	/* 会员资金流水列表 */
	private List<QueryBalanceJnlInfo> data;

	public List<QueryBalanceJnlInfo> getData() {
		return data;
	}

	public void setData(List<QueryBalanceJnlInfo> data) {
		this.data = data;
	}
	
}

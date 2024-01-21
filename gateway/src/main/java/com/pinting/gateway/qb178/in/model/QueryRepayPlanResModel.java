package com.pinting.gateway.qb178.in.model;

import java.util.List;
/**
 * 查询还款计划res 
 * @author bianyatian
 * @2017-7-28 下午5:02:25
 */
public class QueryRepayPlanResModel extends BaseResModel {

	private List<RepayPlanInfo> data;

	public List<RepayPlanInfo> getData() {
		return data;
	}

	public void setData(List<RepayPlanInfo> data) {
		this.data = data;
	}
}

package com.pinting.gateway.qb178.in.model;

import java.util.List;
/**
 * 查询会员持仓余额res
 * @author bianyatian
 * @2017-7-28 下午4:23:54
 */
public class PositionBalanceResModel extends BaseResModel {

	/* 产品列表 */
	private List<PositionProductInfo> data;

	public List<PositionProductInfo> getData() {
		return data;
	}

	public void setData(List<PositionProductInfo> data) {
		this.data = data;
	}
}

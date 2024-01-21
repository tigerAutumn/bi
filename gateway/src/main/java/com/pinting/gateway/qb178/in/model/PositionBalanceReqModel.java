package com.pinting.gateway.qb178.in.model;

/**
 * 查询会员持仓余额req
 * @author bianyatian
 * @2017-7-28 下午4:23:39
 */
public class PositionBalanceReqModel extends BaseReqModel {
	/* 产品编码  */
	private String product_code;
	/* 创建时间开始 */
	private String create_time_begin;
	/* 创建时间结束 */
	private String create_time_end;
	
	public String getProduct_code() {
		return product_code;
	}
	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}
	public String getCreate_time_begin() {
		return create_time_begin;
	}
	public void setCreate_time_begin(String create_time_begin) {
		this.create_time_begin = create_time_begin;
	}
	public String getCreate_time_end() {
		return create_time_end;
	}
	public void setCreate_time_end(String create_time_end) {
		this.create_time_end = create_time_end;
	}
	
}

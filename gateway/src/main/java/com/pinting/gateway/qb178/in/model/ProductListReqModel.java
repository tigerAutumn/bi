package com.pinting.gateway.qb178.in.model;
/**
 * 查询产品列表
 * @project gateway
 * @title ProductListReqModel.java
 * @author Dragon & cat
 * @date 2017-7-28
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
public class ProductListReqModel extends BaseReqModel {
	/**资产方产品编码*/
	private    String 		product_code;
	/**产品创建时间开始*/
	private    String 		create_time_begin;
	/**产品创建时间结束*/
	private    String 		create_time_end;
	/**查询资产方可推送产品类型
	默认为：资产标的 0000.0004.0004*/
	private    String 		product_type;
	/**查询资产方可推送产品状态：申购中 buying 已确权 confirmed 发行失败 failure 拟定 prepared*/
	private    String 		product_status;
	
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
	public String getProduct_type() {
		return product_type;
	}
	public void setProduct_type(String product_type) {
		this.product_type = product_type;
	}
	public String getProduct_status() {
		return product_status;
	}
	public void setProduct_status(String product_status) {
		this.product_status = product_status;
	}
	
}

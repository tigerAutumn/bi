package com.pinting.gateway.qb178.in.model;

import java.io.Serializable;
import java.util.List;

public class PositionProductInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2868925090958311L;
	/* 产品编码 */
	private String product_code;
	/* 产品名称 */
	private String product_name;
	/* 总持仓量  */
	private Long total_prod_balance;
	/* 一个产品持仓被多人持有 */
	private List<PositionProduct4UserInfo> data;
	
	public String getProduct_code() {
		return product_code;
	}
	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public Long getTotal_prod_balance() {
		return total_prod_balance;
	}
	public void setTotal_prod_balance(Long total_prod_balance) {
		this.total_prod_balance = total_prod_balance;
	}
	public List<PositionProduct4UserInfo> getData() {
		return data;
	}
	public void setData(List<PositionProduct4UserInfo> data) {
		this.data = data;
	}

	
}

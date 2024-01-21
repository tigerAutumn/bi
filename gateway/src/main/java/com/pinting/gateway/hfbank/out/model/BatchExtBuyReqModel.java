package com.pinting.gateway.hfbank.out.model;

import java.util.List;

/**
 * 
 * @project gateway
 * @title BatchExtReqModel.java
 * @author Dragon & cat
 * @date 2017-4-1
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description 恒丰银行存管，批量投标请求
 */
public class BatchExtBuyReqModel extends BaseReqModel {
	/**总数量*/
	private 	Integer		total_num;
	/**产品编号*/
	private 	String 		prod_id;
	
	private		List<BatchExtBuyReqData>  data;

	public Integer getTotal_num() {
		return total_num;
	}
	public void setTotal_num(Integer total_num) {
		this.total_num = total_num;
	}
	public String getProd_id() {
		return prod_id;
	}
	public void setProd_id(String prod_id) {
		this.prod_id = prod_id;
	}
	public List<BatchExtBuyReqData> getData() {
		return data;
	}
	public void setData(List<BatchExtBuyReqData> data) {
		this.data = data;
	}
	
	
}

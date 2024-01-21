package com.pinting.gateway.hfbank.out.model;

import java.util.List;

/**
 * 
 * @project gateway
 * @title BatchExtRepayReqModel.java
 * @author Dragon & cat
 * @date 2017-4-1
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description 恒丰银行存管，借款人批量还款请求
 */
public class BatchExtRepayReqModel extends BaseReqModel {
	/*JsonArray 批量明细数据*/
	private		List<BatchExtRepayReqData>   data;
	/* 总数量 */
	private		Integer  total_num;
	public List<BatchExtRepayReqData> getData() {
		return data;
	}

	public void setData(List<BatchExtRepayReqData> data) {
		this.data = data;
	}

	public Integer getTotal_num() {
		return total_num;
	}

	public void setTotal_num(Integer total_num) {
		this.total_num = total_num;
	}
	
}

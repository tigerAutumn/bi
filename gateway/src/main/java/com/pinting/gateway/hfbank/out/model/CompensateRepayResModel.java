package com.pinting.gateway.hfbank.out.model;

import com.pinting.gateway.hfbank.out.model.BaseResModel;
/**
 * 
 * @project gateway
 * @title CompensateRepayResModel.java
 * @author Dragon & cat
 * @date 2017-4-15
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description  4.2.2.标的代偿（委托）还款
 */
public class CompensateRepayResModel extends BaseResModel {
	/*业务数据*/
	private		CompensateRepayResData 	data;

	public CompensateRepayResData getData() {
		return data;
	}

	public void setData(CompensateRepayResData data) {
		this.data = data;
	}
	
	
}

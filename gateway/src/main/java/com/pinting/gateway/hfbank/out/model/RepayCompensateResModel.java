package com.pinting.gateway.hfbank.out.model;
/**
 * 
 * @project gateway
 * @title RepayCompensateResModel.java
 * @author Dragon & cat
 * @date 2017-4-15
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description 4.2.3.借款人还款代偿（委托）
 */
public class RepayCompensateResModel extends BaseResModel {
	/*业务数据*/
	private		RepayCompensateResData  	data;

	public RepayCompensateResData getData() {
		return data;
	}

	public void setData(RepayCompensateResData data) {
		this.data = data;
	}
	
	
}

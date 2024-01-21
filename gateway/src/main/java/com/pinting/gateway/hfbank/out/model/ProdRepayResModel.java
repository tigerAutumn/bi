package com.pinting.gateway.hfbank.out.model;

import java.util.List;

/**
 * 
 * @project gateway
 * @title ProdRepayResModel.java
 * @author Dragon & cat
 * @date 2017-4-1
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description 恒丰银行存管，标的还款返回
 */
public class ProdRepayResModel extends BaseResModel {
	/*业务数据*/
	private 	ProdRepayResData 		data;

	public ProdRepayResData getData() {
		return data;
	}

	public void setData(ProdRepayResData data) {
		this.data = data;
	}

}

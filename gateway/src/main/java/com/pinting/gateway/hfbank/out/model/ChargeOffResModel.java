package com.pinting.gateway.hfbank.out.model;

import java.util.List;

/**
 * 
 * @project gateway
 * @title ChargeOffResModel.java
 * @author Dragon & cat
 * @date 2017-4-1
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description 恒丰银行存管，标的出账返回
 */
public class ChargeOffResModel extends BaseResModel {
	/*业务数据*/
	private		ChargeOffResData  data ;

	public ChargeOffResData getData() {
		return data;
	}

	public void setData(ChargeOffResData data) {
		this.data = data;
	}

	
}

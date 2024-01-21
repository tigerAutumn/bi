package com.pinting.gateway.hfbank.out.model;

import java.util.List;

/**
 * 
 * @project gateway
 * @title EstablishOrAbandonResModel.java
 * @author Dragon & cat
 * @date 2017-4-1
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description 恒丰银行存管，标的成废返回-业务数据
 */
public class EstablishOrAbandonResModel extends BaseResModel {
	/*业务数据*/
	private		EstablishOrAbandonResData  data ;


	public EstablishOrAbandonResData getData() {
		return data;
	}

	public void setData(EstablishOrAbandonResData data) {
		this.data = data;
	}
}

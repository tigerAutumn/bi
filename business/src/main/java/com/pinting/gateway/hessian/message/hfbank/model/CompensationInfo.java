package com.pinting.gateway.hessian.message.hfbank.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @project gateway
 * @title PublishFinancingInfo.java
 * @author Dragon & cat
 * @date 2017-4-14
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description  恒丰银行存管 ，标的发布请求 代偿（委托）账户列表
 */ 
public class CompensationInfo  implements Serializable {

	private static final long serialVersionUID = -3303598595801313941L;
	
	/* 代偿账户编号 */
	private		String 		platcust;
	
	/* 0-代偿还款 1-委托还款 */
	private		String 		type;

	public String getPlatcust() {
		return platcust;
	}

	public void setPlatcust(String platcust) {
		this.platcust = platcust;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}

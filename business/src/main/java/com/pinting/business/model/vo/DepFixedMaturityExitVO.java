package com.pinting.business.model.vo;

import com.pinting.business.model.BsSubAccount;
/**
 * 
 * @project business
 * @title DepFixedMaturityExitVO.java
 * @author Dragon & cat
 * @date 2017-4-5
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description  存管系统固定期限产品到期退出
 * 
 */
public class DepFixedMaturityExitVO extends BsSubAccount {
	/*用户ID*/
	private 	Integer		userId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}

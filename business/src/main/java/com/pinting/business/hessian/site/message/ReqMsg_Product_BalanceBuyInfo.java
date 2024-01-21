package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;
/**
 * 
 * @project gateway
 * @title ReqMsg_Product_BalanceBuyInfo.java
 * @author Dragon & cat
 * @date 2017-4-14
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description  余额购买页面信息
 */
public class ReqMsg_Product_BalanceBuyInfo extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3372870440286878971L;
	
	/*用户ID*/
	private		Integer		productId;
	/*用户ID*/
	private		Integer		userId;
	
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
}

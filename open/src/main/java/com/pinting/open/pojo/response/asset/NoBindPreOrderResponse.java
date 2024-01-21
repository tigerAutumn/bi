/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.open.pojo.response.asset;

import com.pinting.open.base.response.Response;

/**
 * 未绑卡充值预下单返回数据
 * @author HuanXiong
 * @version $Id: NoBindPreOrderResponse.java, v 0.1 2015-12-17 下午7:11:41 HuanXiong Exp $
 */
public class NoBindPreOrderResponse extends Response {
    
    private String orderNo;
    
    private String html;

    public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    
}

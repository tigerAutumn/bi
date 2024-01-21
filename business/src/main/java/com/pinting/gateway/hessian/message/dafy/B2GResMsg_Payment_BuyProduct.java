package com.pinting.gateway.hessian.message.dafy;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * @Project: business
 * @Title: B2GResMsg_Payment_BuyProduct.java
 * @author Zhou Changzai
 * @date 2015-2-11 下午8:03:24
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class B2GResMsg_Payment_BuyProduct extends ResMsg {
	private static final long serialVersionUID = -3813225567810536894L;
	
	private String retHtml;//达飞相应的的html页面，用于传递到我们后台，再传递到前台

	public String getRetHtml() {
		return retHtml;
	}

	public void setRetHtml(String retHtml) {
		this.retHtml = retHtml;
	}
}

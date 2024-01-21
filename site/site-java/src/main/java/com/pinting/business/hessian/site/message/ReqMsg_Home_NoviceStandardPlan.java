package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;
/**
 * 新手引导页 新手专享计划,只显示新手标产品
 * @author shuhuanghui
 * 2016-6-13 下午7:30:10
 */
public class ReqMsg_Home_NoviceStandardPlan extends ReqMsg {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3586248886377262256L;

	private String productShowTerminal;

	public String getProductShowTerminal() {
		return productShowTerminal;
	}

	public void setProductShowTerminal(String productShowTerminal) {
		this.productShowTerminal = productShowTerminal;
	}
	
}

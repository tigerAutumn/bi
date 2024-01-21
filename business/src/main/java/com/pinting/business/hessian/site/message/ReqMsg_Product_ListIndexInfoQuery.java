package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 理财计划列表分级--首页（引导页面）
 * @author SHENGP
 * @date  2017年7月5日 上午10:09:52
 */
public class ReqMsg_Product_ListIndexInfoQuery extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6284774017838912336L;

	/*展示端口*/
	private String productShowTerminal;
	
	public String getProductShowTerminal() {
		return productShowTerminal;
	}

	public void setProductShowTerminal(String productShowTerminal) {
		this.productShowTerminal = productShowTerminal;
	}
}

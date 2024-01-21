package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 
 * 双旦活动标的查询
 * @author bianyatian
 * @2016-12-12 下午4:50:08
 */
public class ReqMsg_Product_Active00Product extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6509815098353101852L;
	
	private String showTerminal;    // 显示端口

	public String getShowTerminal() {
		return showTerminal;
	}

	public void setShowTerminal(String showTerminal) {
		this.showTerminal = showTerminal;
	}

}

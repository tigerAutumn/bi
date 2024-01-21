package com.pinting.business.hessian.manage.message;


import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_MWxMenu_MWxMenuModify extends ResMsg {
	/**
	 * 序列化编号
	 */
	private static final long serialVersionUID = -5843090277013565450L;
	
    private String menuJson;

	public String getMenuJson() {
		return menuJson;
	}

	public void setMenuJson(String menuJson) {
		this.menuJson = menuJson;
	}
}

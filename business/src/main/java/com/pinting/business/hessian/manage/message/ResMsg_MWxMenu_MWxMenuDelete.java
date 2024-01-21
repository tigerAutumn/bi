package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ResMsg;


public class ResMsg_MWxMenu_MWxMenuDelete extends ResMsg {
	/**
	 * 序列化编号
	 */
	private static final long serialVersionUID = -8011285267939592723L;
	
    private Integer menuId;

    private String json;
    
	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}
}

package com.pinting.business.hessian.manage.message;


import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_MWxMenu_MWxMenuDelete extends ReqMsg {
	/**
	 * 序列化编号
	 */
	private static final long serialVersionUID = -8011285267939592723L;
	
    private Integer menuId;

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}
}

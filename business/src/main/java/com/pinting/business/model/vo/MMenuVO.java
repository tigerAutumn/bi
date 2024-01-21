package com.pinting.business.model.vo;

import java.util.HashMap;
import java.util.List;

import com.pinting.business.model.MMenu;

public class MMenuVO extends MMenu {
	//列表中元素为 子菜单
	private List<HashMap<String, Object>> childMenus;

	public List<HashMap<String, Object>> getChildMenus() {
		return childMenus;
	}

	public void setChildMenus(List<HashMap<String, Object>> childMenus) {
		this.childMenus = childMenus;
	}
	

}

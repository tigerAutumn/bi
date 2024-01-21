package com.pinting.business.model.wxmenu;

/**
 * 按钮基类
 * @author yanwl
 * @date 2015-12-08
 */
public class Button {
	private String name;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public Button(String name) {
		super();
		this.name = name;
	}

	public Button() {
		super();
	}
	
}

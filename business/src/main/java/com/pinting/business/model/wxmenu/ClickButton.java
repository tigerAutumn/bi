package com.pinting.business.model.wxmenu;

/**
 * Click类型的按钮
 * @author yanwl
 * @date 2015-12-08
 */
public class ClickButton extends Button {
	
	private String key;
	
	private String type;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ClickButton(String key, String type) {
		super();
		this.key = key;
		this.type = type;
	}

	public ClickButton(String name, String key, String type) {
		super(name);
		this.key = key;
		this.type = type;
	}
	
	public ClickButton() {
		super();
	}
	
}

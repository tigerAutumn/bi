package com.pinting.business.model.wxmenu;

/**
 * view类型的按钮
 * @author yanwl
 * @date 2015-12-08
 */
public class ViewButton extends Button {
	
	private String type;
	
	private String url;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public ViewButton(String name, String type, String url) {
		super(name);
		this.type = type;
		this.url = url;
	}

	public ViewButton() {
		super();
	}

	public ViewButton(String type, String url) {
		this.type = type;
		this.url = url;
	}
	
}

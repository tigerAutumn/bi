package com.pinting.business.model.wxmenu;

/**
 * 复合类型按钮
 * @author yanwl
 * @date 2015-12-08
 */
public class ComplexButton extends Button {
	
	private Button[] sub_button;

	public Button[] getSub_button() {
		return sub_button;
	}

	public void setSub_button(Button[] sub_button) {
		this.sub_button = sub_button;
	}

	public ComplexButton(String name, Button[] sub_button) {
		super(name);
		this.sub_button = sub_button;
	}

	public ComplexButton(String name) {
		super(name);
	}
	
	public ComplexButton() {
		super();
	}
}

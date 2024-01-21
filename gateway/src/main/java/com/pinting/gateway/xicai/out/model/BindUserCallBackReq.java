package com.pinting.gateway.xicai.out.model;

public class BindUserCallBackReq {
	// phone=13800138000&name=test&result=1&display=mobile
	
	private String phone;
	
	private String name;
	
	private String result;
	
	private String display;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}
	
	
}

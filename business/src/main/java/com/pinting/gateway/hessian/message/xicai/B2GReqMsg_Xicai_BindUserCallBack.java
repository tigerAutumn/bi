package com.pinting.gateway.hessian.message.xicai;

import com.pinting.core.hessian.msg.ReqMsg;

public class B2GReqMsg_Xicai_BindUserCallBack extends ReqMsg {
	//phone=13800138000&name=test&result=1&display=mobile
			
	/**
	 * 
	 */
	private static final long serialVersionUID = -7667576500522784301L;

	private String phone;
	
	private String name;
	
	private Integer result;
	
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

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}
	
	
	
}

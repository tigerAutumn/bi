package com.pinting.gateway.hessian.message.reapal;

import com.pinting.core.hessian.msg.ResMsg;

public class B2GResMsg_ReapalQuickPay_Certify extends ResMsg {

	private static final long serialVersionUID = 3375279976367647794L;

	private String html;

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}
}

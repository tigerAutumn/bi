package com.pinting.gateway.hessian.message.dafy;

import com.pinting.core.hessian.msg.ResMsg;

public class G2BResMsg_DafyLoan_ActiveRepayPre extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4090271228184308816L;
	/*支付单号*/
	private			String		bgwOrderNo;
	public String getBgwOrderNo() {
		return bgwOrderNo;
	}
	public void setBgwOrderNo(String bgwOrderNo) {
		this.bgwOrderNo = bgwOrderNo;
	}
}

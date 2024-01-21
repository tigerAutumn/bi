package com.pinting.gateway.hessian.message.zsd;

import com.pinting.core.hessian.msg.ResMsg;

public class G2BResMsg_ZsdRepay_CutpaymentRepay extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7929633412335926047L;
	
	private String bgwOrderNo;

	public String getBgwOrderNo() {
		return bgwOrderNo;
	}

	public void setBgwOrderNo(String bgwOrderNo) {
		this.bgwOrderNo = bgwOrderNo;
	}
}
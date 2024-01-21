package com.pinting.gateway.hessian.message.dafy;

import org.hibernate.validator.constraints.NotEmpty;

import com.pinting.core.hessian.msg.ReqMsg;

public class G2BReqMsg_DafyLoan_ActiveRepayConfirm extends ReqMsg {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4280781039419338694L;
	/*支付单号*/
	@NotEmpty(message="bgwOrderNo为空")
	private		String		bgwOrderNo;
	/*短信验证码*/
	@NotEmpty(message="smsCode为空")
	private		String		smsCode;
	public String getBgwOrderNo() {
		return bgwOrderNo;
	}
	public void setBgwOrderNo(String bgwOrderNo) {
		this.bgwOrderNo = bgwOrderNo;
	}
	public String getSmsCode() {
		return smsCode;
	}
	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}
	
}

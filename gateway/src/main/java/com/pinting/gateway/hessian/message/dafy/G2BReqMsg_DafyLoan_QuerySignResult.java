package com.pinting.gateway.hessian.message.dafy;

import org.hibernate.validator.constraints.NotEmpty;

import com.pinting.core.hessian.msg.ReqMsg;

public class G2BReqMsg_DafyLoan_QuerySignResult extends ReqMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6506093405333793487L;
	/*借款协议号*/
	@NotEmpty(message="agreementNo为空")
	private			String		agreementNo;

	public String getAgreementNo() {
		return agreementNo;
	}

	public void setAgreementNo(String agreementNo) {
		this.agreementNo = agreementNo;
	}
	
}

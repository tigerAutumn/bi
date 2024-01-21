package com.pinting.gateway.hessian.message.loan7;

import org.hibernate.validator.constraints.NotEmpty;

import com.pinting.core.hessian.msg.ReqMsg;
/**
 * 
 * @project gateway
 * @title G2BReqMsg_DepLoan7_QuerySignResult.java
 * @author Dragon & cat
 * @date 2017-12-12
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
public class G2BReqMsg_DepLoan7_QuerySignResult extends ReqMsg {
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

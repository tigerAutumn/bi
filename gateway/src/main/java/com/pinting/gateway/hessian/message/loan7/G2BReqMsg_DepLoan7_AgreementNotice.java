package com.pinting.gateway.hessian.message.loan7;

import org.hibernate.validator.constraints.NotEmpty;

import com.pinting.core.hessian.msg.ReqMsg;
/**
 * 
 * @project gateway
 * @title G2BReqMsg_DepLoan7_AgreementNotice.java
 * @author Dragon & cat
 * @date 2017-12-12
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
public class G2BReqMsg_DepLoan7_AgreementNotice extends ReqMsg{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1077385544975315158L;
	
	/*借款编号*/
	@NotEmpty(message="loanId为空")
	private	 String	 loanId;

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

}

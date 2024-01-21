package com.pinting.gateway.hessian.message.dafy;

import org.hibernate.validator.constraints.NotEmpty;

import com.pinting.core.hessian.msg.ReqMsg;

public class G2BReqMsg_DafyLoan_AgreementNotice extends ReqMsg{

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

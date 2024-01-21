package com.pinting.gateway.hessian.message.dafy;

import java.util.List;

import com.pinting.core.hessian.msg.ReqMsg;
import com.pinting.gateway.hessian.message.dafy.model.Agreements;

public class B2GReqMsg_DafyLoanNotice_AgreementNotice extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -718571156759072857L;

	private String orderNo; //代偿编号
	
	private List<Agreements> agreements; //（以下一级循环）

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public List<Agreements> getAgreements() {
		return agreements;
	}

	public void setAgreements(List<Agreements> agreements) {
		this.agreements = agreements;
	}
}

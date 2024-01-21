package com.pinting.gateway.dafy.out.model;

import java.util.List;
/**
 * 协议下载通知入参
 */
import com.pinting.gateway.hessian.message.dafy.model.Agreements;

public class AgreementNoticeReqModel extends BaseReqModel {

	private String orderNo; //代偿编号
	
	/*申请流水号*/
	private		String		requestSeq;
	
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

	public String getRequestSeq() {
		return requestSeq;
	}

	public void setRequestSeq(String requestSeq) {
		this.requestSeq = requestSeq;
	}

	@Override
	public String toString() {
		return "AgreementNoticeReqModel [orderNo=" + orderNo + ", requestSeq="
				+ requestSeq + ", agreements=" + agreements + "]";
	}
	
}

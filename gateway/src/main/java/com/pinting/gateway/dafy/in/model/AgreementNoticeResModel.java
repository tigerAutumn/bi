package com.pinting.gateway.dafy.in.model;

import com.pinting.gateway.hessian.message.dafy.model.Agreements;

import java.util.List;

public class AgreementNoticeResModel extends BaseResModel{

	private List<Agreements> agreements; //（以下一级循环）

	public List<Agreements> getAgreements() {
		return agreements;
	}

	public void setAgreements(List<Agreements> agreements) {
		this.agreements = agreements;
	}
}

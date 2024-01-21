package com.pinting.gateway.bird.out.model;

import java.util.List;


/**
 * Created by 剑钊 on 2016/8/10.
 * 银行限额
 */
public class BankLimitReq extends BaseReqModel {

	private List<BankLimit> limits;

	public List<BankLimit> getLimits() {
		return limits;
	}

	public void setLimits(List<BankLimit> limits) {
		this.limits = limits;
	}

	
}

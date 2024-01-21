package com.pinting.gateway.hessian.message.hfbank.model;

import java.io.Serializable;

/**
 * 
 * @project gateway
 * @title EstablishOrAbandonReqFundData.java
 * @author Dragon & cat
 * @date 2017-4-1
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description 恒丰银行存管，标的成废请求-成标时候需要进行的分佣说明
 */
public class EstablishOrAbandonReqFundData implements  Serializable {

	private static final long serialVersionUID = -579996013574791803L;

	/*分配平台类型(01-自有 11-垫付)*/
	private		String		payout_plat_type;
	/*手续费固定金额*/
	private		String		payout_amt;
	public String getPayout_plat_type() {
		return payout_plat_type;
	}
	public void setPayout_plat_type(String payout_plat_type) {
		this.payout_plat_type = payout_plat_type;
	}
	public String getPayout_amt() {
		return payout_amt;
	}
	public void setPayout_amt(String payout_amt) {
		this.payout_amt = payout_amt;
	}
	
	
}

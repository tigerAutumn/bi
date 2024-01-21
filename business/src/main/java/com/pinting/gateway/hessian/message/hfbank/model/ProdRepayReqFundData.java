package com.pinting.gateway.hessian.message.hfbank.model;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @project gateway
 * @title ProdRepayReqFundData.java
 * @author Dragon & cat
 * @date 2017-4-1
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description 恒丰银行存管，标的还款请求-资金数据，json格式记录还款金额
 */
public class ProdRepayReqFundData implements  Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1903144478049777757L;
	/*custRepayList表示还给投资人还款明细，
	 * 其中real_repay_amt实际还款金额
	 * （实际还款本金+体验金+加息金+利息+手续费）*/
	private		List<ProdRepayReqFundDataCustRepay> 	custRepayList;

	public List<ProdRepayReqFundDataCustRepay> getCustRepayList() {
		return custRepayList;
	}

	public void setCustRepayList(List<ProdRepayReqFundDataCustRepay> custRepayList) {
		this.custRepayList = custRepayList;
	}
	
	
}

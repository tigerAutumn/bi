package com.pinting.gateway.dafy.out.model;

import java.util.ArrayList;
import java.util.List;

import com.pinting.gateway.hessian.message.dafy.model.InvestmentAmounts;

/**
 * @Project: gateway
 * @Title: CheckAccountResModel.java
 * @author Zhou Changzai
 * @date 2015-2-11 下午3:46:00
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class CheckAccountResModel extends BaseResModel {
	private List<InvestmentAmounts> investmentAmounts = new ArrayList<InvestmentAmounts>();
	public List<InvestmentAmounts> getInvestmentAmounts() {
		return investmentAmounts;
	}
	public void setInvestmentAmounts(List<InvestmentAmounts> investmentAmounts) {
		this.investmentAmounts = investmentAmounts;
	}
	
}

package com.pinting.business.hessian.manage.message;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_FinancialAccount_ListQuery1 extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1159381300439273169L;

	private ArrayList<HashMap<String, Object>> financialAccountList;
    private Double sumTotalInvestAmount;
    private Integer count;

	public ArrayList<HashMap<String, Object>> getFinancialAccountList() {
		return financialAccountList;
	}

	public void setFinancialAccountList(
			ArrayList<HashMap<String, Object>> financialAccountList) {
		this.financialAccountList = financialAccountList;
	}
	
	public Double getSumTotalInvestAmount() {
		return sumTotalInvestAmount;
	}

	public void setSumTotalInvestAmount(Double sumTotalInvestAmount) {
		this.sumTotalInvestAmount = sumTotalInvestAmount;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
    
    
}

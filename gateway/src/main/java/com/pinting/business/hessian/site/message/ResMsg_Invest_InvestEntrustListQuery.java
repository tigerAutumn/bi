package com.pinting.business.hessian.site.message;

import java.util.ArrayList;
import java.util.HashMap;
import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_Invest_InvestEntrustListQuery extends ResMsg {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 448771151189339702L;
	
	private Integer totalPageEntrust; //委托计划总页数

	private ArrayList<HashMap<String,Object>> investAccountsEntrust;  //我的投资列表-委托计划

	public Integer getTotalPageEntrust() {
		return totalPageEntrust;
	}

	public void setTotalPageEntrust(Integer totalPageEntrust) {
		this.totalPageEntrust = totalPageEntrust;
	}

	public ArrayList<HashMap<String, Object>> getInvestAccountsEntrust() {
		return investAccountsEntrust;
	}

	public void setInvestAccountsEntrust(
			ArrayList<HashMap<String, Object>> investAccountsEntrust) {
		this.investAccountsEntrust = investAccountsEntrust;
	}
	
	
}

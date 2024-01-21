package com.pinting.business.hessian.site.message;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_Invest_PrincipalListQuery extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1893085841094091419L;
	
	private Integer userId;
	private Double totalPrincipal;
	
	/**
	 * 以下循环：
	 * investDate	投资时间	必填	Date
	 * amount		金额		必填	Double
	 * remark		备注		可选	String
	 */
	private ArrayList<HashMap<String, Object>> investPrincipals;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Double getTotalPrincipal() {
		return totalPrincipal;
	}

	public void setTotalPrincipal(Double totalPrincipal) {
		this.totalPrincipal = totalPrincipal;
	}

	public ArrayList<HashMap<String, Object>> getInvestPrincipals() {
		return investPrincipals;
	}

	public void setInvestPrincipals(
			ArrayList<HashMap<String, Object>> investPrincipals) {
		this.investPrincipals = investPrincipals;
	}
	
}

package com.pinting.business.hessian.site.message;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_Invest_InvestProportion extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7194662336146741383L;
	
	private ArrayList<HashMap<String,Object>> investProportionList;  //我的投资比例列表

	public ArrayList<HashMap<String,Object>> getInvestProportionList() {
		return investProportionList;
	}

	public void setInvestProportionList(ArrayList<HashMap<String,Object>> investProportionList) {
		this.investProportionList = investProportionList;
	}

}

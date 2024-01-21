package com.pinting.business.hessian.manage.message;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_BsDailyBonus_ServiceDetailList extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2027984251875741751L;
	private ArrayList<HashMap<String, Object>> bsDailyBonuss;
	/**奖励总金额**/
	private Double allBonus;
	public ArrayList<HashMap<String, Object>> getBsDailyBonuss() {
		return bsDailyBonuss;
	}
	public void setBsDailyBonuss(ArrayList<HashMap<String, Object>> bsDailyBonuss) {
		this.bsDailyBonuss = bsDailyBonuss;
	}
	public Double getAllBonus() {
		return allBonus;
	}
	public void setAllBonus(Double allBonus) {
		this.allBonus = allBonus;
	}
}

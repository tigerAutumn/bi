package com.pinting.business.hessian.site.message;

import java.util.HashMap;
import java.util.List;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_Home_BuyOrders extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6437519699093619037L;

	private List<HashMap<String, Object>> userBuyOrdersList;

	public List<HashMap<String, Object>> getUserBuyOrdersList() {
		return userBuyOrdersList;
	}

	public void setUserBuyOrdersList(List<HashMap<String, Object>> userBuyOrdersList) {
		this.userBuyOrdersList = userBuyOrdersList;
	}

}

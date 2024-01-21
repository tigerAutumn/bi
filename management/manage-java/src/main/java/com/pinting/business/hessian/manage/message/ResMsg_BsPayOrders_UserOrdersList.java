package com.pinting.business.hessian.manage.message;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 财务确认处理查看详情中查询用户订单信息   出参
 * Created by shh on 2016/11/3 21:26.
 */
public class ResMsg_BsPayOrders_UserOrdersList extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7606314247532164819L;
	
	private ArrayList<HashMap<String,Object>> valueList;

	public ArrayList<HashMap<String, Object>> getValueList() {
		return valueList;
	}

	public void setValueList(ArrayList<HashMap<String, Object>> valueList) {
		this.valueList = valueList;
	}
	
}

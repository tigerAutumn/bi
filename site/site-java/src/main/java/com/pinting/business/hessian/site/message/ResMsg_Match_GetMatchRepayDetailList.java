package com.pinting.business.hessian.site.message;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;
/**
 * 根据matchId查询债权关系还款明细 出参
 * @author bianyatian
 * @2016-4-22 上午10:37:08
 */
public class ResMsg_Match_GetMatchRepayDetailList extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3101032268965906058L;
	/*债权关系还款明细列表*/
	private ArrayList<HashMap<String, Object>> list;

	public ArrayList<HashMap<String, Object>> getList() {
		return list;
	}

	public void setList(ArrayList<HashMap<String, Object>> list) {
		this.list = list;
	}

}

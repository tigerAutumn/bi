package com.pinting.business.hessian.site.message;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 618活动用户中奖列表出参
 * @author bianyatian
 * @2016-6-7 下午5:19:31
 */
public class ResMsg_ActivityLuckyDraw_Get618UserLuckyList extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2608424136355604036L;
	/*中奖列表*/
	private ArrayList<HashMap<String, Object>> luckyList;
	/*总页数*/
	private int total;
	/*当前页码*/
	private int pageIndex;

	public ArrayList<HashMap<String, Object>> getLuckyList() {
		return luckyList;
	}

	public void setLuckyList(ArrayList<HashMap<String, Object>> luckyList) {
		this.luckyList = luckyList;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	
	
}

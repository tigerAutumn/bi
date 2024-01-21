package com.pinting.business.hessian.site.message;

import java.util.HashMap;
import java.util.List;

import com.pinting.core.hessian.msg.ResMsg;
/**
 * 欧洲杯活动-冠亚军列表支持率 出参
 * @author bianyatian
 * @2016-6-23 上午9:39:39
 */
public class ResMsg_Ecup2016Activity_GetChampionSilverList extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 709196623967653704L;
	/*冠军列表*/
	private List<HashMap<String, Object>> championList; 
	/*亚军列表*/
	private List<HashMap<String, Object>> silverList;
	public List<HashMap<String, Object>> getChampionList() {
		return championList;
	}
	public void setChampionList(List<HashMap<String, Object>> championList) {
		this.championList = championList;
	}
	public List<HashMap<String, Object>> getSilverList() {
		return silverList;
	}
	public void setSilverList(List<HashMap<String, Object>> silverList) {
		this.silverList = silverList;
	}
}

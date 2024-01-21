package com.pinting.business.hessian.site.message;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 2016客户年终抽奖活动特等奖相关  出参
 * Created by shh on 2016/12/01 12:00.
 * @author shh
 */
public class ResMsg_EndOf2016YearActivity_GetGrandPrize extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9070268037413503388L;
	
	/* 屏幕滚动手机号*/
	private ArrayList<HashMap<String,Object>> scrollLlist;
	
	/* 最终中奖的名单*/
	private ArrayList<HashMap<String,Object>> valueList;

	public ArrayList<HashMap<String, Object>> getScrollLlist() {
		return scrollLlist;
	}

	public void setScrollLlist(ArrayList<HashMap<String, Object>> scrollLlist) {
		this.scrollLlist = scrollLlist;
	}

	public ArrayList<HashMap<String, Object>> getValueList() {
		return valueList;
	}

	public void setValueList(ArrayList<HashMap<String, Object>> valueList) {
		this.valueList = valueList;
	}

}

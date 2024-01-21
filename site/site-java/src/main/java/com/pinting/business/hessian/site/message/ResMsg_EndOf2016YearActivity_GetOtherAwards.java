package com.pinting.business.hessian.site.message;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 2016客户年终抽奖活动其它奖(一、二、三等奖)项抽取相关  出参
 * Created by shh on 2016/12/01 12:00.
 * @author shh
 */
public class ResMsg_EndOf2016YearActivity_GetOtherAwards extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8830977353371168106L;
	
	/* 屏幕滚动手机号*/
	private ArrayList<HashMap<String,Object>> scrollLlist;
	
	/* 获奖的手机号*/
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

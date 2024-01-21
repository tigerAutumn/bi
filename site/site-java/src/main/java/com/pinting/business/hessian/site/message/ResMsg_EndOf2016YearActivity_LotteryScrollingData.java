package com.pinting.business.hessian.site.message;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 2016客户年终抽奖活动幸运奖相关  抽奖滚屏数据
 * Created by shh on 2016/12/05 12:00.
 * @author shh
 */
public class ResMsg_EndOf2016YearActivity_LotteryScrollingData extends ResMsg{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3829796695754766876L;
	
	private ArrayList<HashMap<String,Object>> valueList;

	public ArrayList<HashMap<String, Object>> getValueList() {
		return valueList;
	}

	public void setValueList(ArrayList<HashMap<String, Object>> valueList) {
		this.valueList = valueList;
	}

}

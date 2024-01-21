package com.pinting.business.hessian.site.message;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 刮刮乐获奖名单
 * @author SHENGUOPING
 * @date  2017年8月17日 下午8:24:16
 */
public class ResMsg_ScratchcardActivity_UserAwardList extends ResMsg {

	private static final long serialVersionUID = 2088861281018416012L;
	
	/*中奖列表*/
	private ArrayList<HashMap<String, Object>> awardList;

	public ArrayList<HashMap<String, Object>> getAwardList() {
		return awardList;
	}

	public void setAwardList(ArrayList<HashMap<String, Object>> awardList) {
		this.awardList = awardList;
	}
	
}

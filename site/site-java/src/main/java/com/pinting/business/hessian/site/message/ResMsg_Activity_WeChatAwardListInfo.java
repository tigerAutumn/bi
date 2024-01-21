package com.pinting.business.hessian.site.message;

import java.util.HashMap;
import java.util.List;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 
 * @author zhangpeng
 * @date 2018/05/27
 */
public class ResMsg_Activity_WeChatAwardListInfo extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6942865131768307533L;

	/* 我的奖品列表 */
    private List<HashMap<String, Object>> awardList;

	public List<HashMap<String, Object>> getAwardList() {
		return awardList;
	}

	public void setAwardList(List<HashMap<String, Object>> awardList) {
		this.awardList = awardList;
	}
    
}

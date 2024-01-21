package com.pinting.business.model.vo;

import java.util.HashMap;
import java.util.List;

/**
 * 
 * @author zhangpeng
 * @date 2018/05/27
 */
public class WeChatAwardListInfoVO extends ActivityBaseVO {

	/* 我的奖品列表 */
    private List<HashMap<String, Object>> awardList;

	public List<HashMap<String, Object>> getAwardList() {
		return awardList;
	}

	public void setAwardList(List<HashMap<String, Object>> awardList) {
		this.awardList = awardList;
	}
    
}

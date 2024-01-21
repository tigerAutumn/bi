package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 2016客户年终抽奖活动幸运奖相关  入参
 * Created by shh on 2016/12/01 12:00.
 * @author shh
 */
public class ReqMsg_EndOf2016YearActivity_GetLuckyPrizeList extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4034031845218660123L;
	
	/* 奖品等级Id */
	private Integer activityAwardId;

	public Integer getActivityAwardId() {
		return activityAwardId;
	}

	public void setActivityAwardId(Integer activityAwardId) {
		this.activityAwardId = activityAwardId;
	}
	
}

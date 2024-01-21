package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 2016客户年终抽奖活动其它奖(一、二、三等奖)项抽取相关  入参
 * Created by shh on 2016/12/01 12:00.
 * @author shh
 */
public class ReqMsg_EndOf2016YearActivity_GetOtherAwards extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1248855211361689947L;
	
	/* 奖品等级Id */
	private Integer activityAwardId;

	public Integer getActivityAwardId() {
		return activityAwardId;
	}

	public void setActivityAwardId(Integer activityAwardId) {
		this.activityAwardId = activityAwardId;
	}
	
}

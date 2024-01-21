package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 2016客户年终抽奖活动其它奖(一、二、三等奖)项抽奖次数统计 入参
 * Created by shh on 2016/12/06 12:00.
 * @author shh
 */
public class ReqMsg_EndOf2016YearActivity_NumberOfDraws extends ReqMsg {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7816807001364603550L;
	/* 奖品等级Id */
	private Integer activityAwardId;
	public Integer getActivityAwardId() {
		return activityAwardId;
	}
	public void setActivityAwardId(Integer activityAwardId) {
		this.activityAwardId = activityAwardId;
	}
	
}

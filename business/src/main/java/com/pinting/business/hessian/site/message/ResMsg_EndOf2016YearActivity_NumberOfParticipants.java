package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 2016客户年终抽奖活动-查询参加抽奖活动的人数查询  出参
 * Created by shh on 2016/12/06 12:00.
 * @author shh
 */
public class ResMsg_EndOf2016YearActivity_NumberOfParticipants extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3125625540775579383L;
	
	/** 参加抽奖的人数 */
	private Integer lotteryCount;

	public Integer getLotteryCount() {
		return lotteryCount;
	}

	public void setLotteryCount(Integer lotteryCount) {
		this.lotteryCount = lotteryCount;
	}
	
}

package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 618活动用户抽奖出参
 * @author bianyatian
 * @2016-6-6 下午3:27:15
 */
public class ResMsg_ActivityLuckyDraw_Get618Award extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8904854878500878436L;
	
	/*抽奖前可抽奖次数*/
	private Integer beforeTimes; 
	/*抽奖后可抽奖次数*/
	private Integer afterTimes; 
	/*奖品id*/
	private Integer awardId; 
	/*奖品名称*/
	private String awardContent; 

	public Integer getBeforeTimes() {
		return beforeTimes;
	}

	public void setBeforeTimes(Integer beforeTimes) {
		this.beforeTimes = beforeTimes;
	}

	public Integer getAfterTimes() {
		return afterTimes;
	}

	public void setAfterTimes(Integer afterTimes) {
		this.afterTimes = afterTimes;
	}

	public Integer getAwardId() {
		return awardId;
	}

	public void setAwardId(Integer awardId) {
		this.awardId = awardId;
	}

	public String getAwardContent() {
		return awardContent;
	}

	public void setAwardContent(String awardContent) {
		this.awardContent = awardContent;
	}


}

package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 *
 * @author SHENGUOPING
 * @date  2017年8月21日 上午11:12:52
 */
public class ResMsg_ScratchcardActivity_CountUserAward extends ResMsg {

	private static final long serialVersionUID = 2359018778597707414L;
	
	/** 用户中奖次数*/
	private Integer userAwardCount;

	/** 奖品名 */
	private String prizeContent;

	public Integer getUserAwardCount() {
		return userAwardCount;
	}

	public void setUserAwardCount(Integer userAwardCount) {
		this.userAwardCount = userAwardCount;
	}

	public String getPrizeContent() {
		return prizeContent;
	}

	public void setPrizeContent(String prizeContent) {
		this.prizeContent = prizeContent;
	}
}

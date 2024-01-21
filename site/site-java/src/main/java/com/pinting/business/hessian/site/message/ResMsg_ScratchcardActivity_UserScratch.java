package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 刮刮乐活动用户刮奖
 * @author SHENGUOPING
 * @date  2017年8月21日 下午1:51:05
 */
public class ResMsg_ScratchcardActivity_UserScratch extends ResMsg {

	private static final long serialVersionUID = -1973028092243003263L;

	/** 中奖内容 */
	private String prizeContent;

	public String getPrizeContent() {
		return prizeContent;
	}

	public void setPrizeContent(String prizeContent) {
		this.prizeContent = prizeContent;
	}
	
}

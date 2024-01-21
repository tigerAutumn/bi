package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 刮刮乐活动我的中奖信息
 * @author SHENGUOPING
 * @date  2017年8月22日 下午3:21:15
 */
public class ResMsg_ScratchcardActivity_ScratchcardPrize extends ResMsg {
	
	private static final long serialVersionUID = 348712945670441654L;
	
	/** 是否已刮奖 */
	private String isScratch;
	/** 奖品名 */
	private String prizeContent;

	public String getIsScratch() {
		return isScratch;
	}

	public void setIsScratch(String isScratch) {
		this.isScratch = isScratch;
	}

	public String getPrizeContent() {
		return prizeContent;
	}

	public void setPrizeContent(String prizeContent) {
		this.prizeContent = prizeContent;
	}

}

package com.pinting.business.model.vo;

import java.io.Serializable;

/**
 * 刮刮乐活动我的中奖信息
 * @author SHENGUOPING
 * @date  2017年8月22日 下午3:28:50
 */
public class ActivityScratchcardPrizeVO  implements Serializable {

	private static final long serialVersionUID = -6164500166644476304L;
	
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

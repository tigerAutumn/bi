package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_Activity_WeChatLuckyTurningDataInfo extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3548131832741626234L;

    /* 抽奖机会次数 */
    private int numberOfChance;
    
    /* 是否分享:yes;no */
    private String shared;

	public int getNumberOfChance() {
		return numberOfChance;
	}

	public void setNumberOfChance(int numberOfChance) {
		this.numberOfChance = numberOfChance;
	}

	public String getShared() {
		return shared;
	}

	public void setShared(String shared) {
		this.shared = shared;
	}
    
}

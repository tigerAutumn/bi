package com.pinting.business.model.vo;

/**
 * 
 * @author zhangpeng
 * @date 2018/05/27
 */
public class WeChatLuckyTurningInfoVO extends ActivityBaseVO {

    /* 抽奖机会次数 */
    private int numberOfChance;
    
    /* 用户是否分享 */
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

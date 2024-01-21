package com.pinting.business.model.vo;

/**
 * 
 * @author zhangpeng
 * @date 2018/05/27
 */
public class WeChatStartTheLotteryVO extends ActivityBaseVO {

	private String award;
	
	private String type;

	public String getAward() {
		return award;
	}

	public void setAward(String award) {
		this.award = award;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}

package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 
 * @author zhangpeng
 * @date 2018/05/27
 */
public class ResMsg_Activity_WeChatStartTheLottery extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3250419509127950683L;

	/* 奖品 */
    private String award;

	public String getAward() {
		return award;
	}

	public void setAward(String award) {
		this.award = award;
	}
    
    
}

package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 
 * @author zhangpeng
 * @date 2018/05/27
 */
public class ResMsg_Activity_WechatShareChanceToDraw extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5728958711321973661L;

	private String getChance;  //yes 第一次分享   no 不是第一次

	public String getGetChance() {
		return getChance;
	}

	public void setGetChance(String getChance) {
		this.getChance = getChance;
	}

}

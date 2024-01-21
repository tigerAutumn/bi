package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;
/**
 * 添加产品提醒消息 出参
 * @author bianyatian
 * @2016-4-21 下午4:40:57
 */
public class ResMsg_Product_AddInform extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*产品开始前x分钟发送短信提醒*/
	private String time;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
}

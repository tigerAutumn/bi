package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 根据用户id或agentId判断是否是钱报系用户
 * @author bianyatian
 * @2017-1-11 下午4:34:32
 */
public class ResMsg_User_CheckIsQianbaos extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6072505319313970201L;

	private boolean isQianbaos; //true为钱报

	public boolean isQianbaos() {
		return isQianbaos;
	}

	public void setQianbaos(boolean isQianbaos) {
		this.isQianbaos = isQianbaos;
	}
	
	
}

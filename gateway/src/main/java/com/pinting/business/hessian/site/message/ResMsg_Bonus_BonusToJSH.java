package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 奖励金转余额 出参
 * @author shiyulong
 * @2015-12-18 下午5:35:21
 */
public class ResMsg_Bonus_BonusToJSH extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3467077880373157189L;
	/*操作是否成功，无奖励金时为false*/
	private boolean flag;
	/*返回信息*/
	private String returnMsg;
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public String getReturnMsg() {
		return returnMsg;
	}
	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}
}

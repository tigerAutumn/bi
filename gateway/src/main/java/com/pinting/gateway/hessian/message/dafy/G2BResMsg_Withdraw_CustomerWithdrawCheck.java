package com.pinting.gateway.hessian.message.dafy;

import com.pinting.core.hessian.msg.ResMsg;

public class G2BResMsg_Withdraw_CustomerWithdrawCheck extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1470180720152664610L;
	public static final String WITHDRAW_CHECK_SUCCESS = "0";//校验通过
	public static final String WITHDRAW_CHECK_ERROR = "1";//校验不通过
	
	private String checkResult;

	public String getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}
	

}

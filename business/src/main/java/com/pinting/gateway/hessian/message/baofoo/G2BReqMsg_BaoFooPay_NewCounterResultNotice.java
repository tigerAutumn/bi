package com.pinting.gateway.hessian.message.baofoo;

import com.pinting.core.hessian.msg.ReqMsg;

public class G2BReqMsg_BaoFooPay_NewCounterResultNotice extends ReqMsg {

	private String transId;

	/**
	 * 支付结果
	 */
	private String result;

	/**
	 * 结果描述
	 */
	private String resultDesc;

	/**
	 * 实际成功金额
	 */
	private String factMoney;

	/**
	 * 交易成功时间
	 */
	private String succTime;

	public String getTransId() {
		return transId;
	}

	public void setTransId(String transId) {
		this.transId = transId;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getResultDesc() {
		return resultDesc;
	}

	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}

	public String getFactMoney() {
		return factMoney;
	}

	public void setFactMoney(String factMoney) {
		this.factMoney = factMoney;
	}

	public String getSuccTime() {
		return succTime;
	}

	public void setSuccTime(String succTime) {
		this.succTime = succTime;
	}
}

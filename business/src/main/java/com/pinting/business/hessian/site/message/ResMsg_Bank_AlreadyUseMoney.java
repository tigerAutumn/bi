package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;
/**
 * 计算用户当日已使用的银行额度出参
 * @author shencheng
 * @2015-11-24 上午11:30:48
 */
public class ResMsg_Bank_AlreadyUseMoney extends ResMsg {

	private static final long serialVersionUID = -112926571400429533L;
	/*银行额度*/
	private Double amount;

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
}

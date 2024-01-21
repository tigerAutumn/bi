package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;
/**
 * 根据银行ID查询银行信息 入参
 * @author shiyulong
 * @2015-11-21 下午4:17:50
 */
public class ReqMsg_Bank_QueryBankById extends ReqMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8791217989960418987L;
	/*银行id*/
	private Integer bankId;

	public Integer getBankId() {
		return bankId;
	}

	public void setBankId(Integer bankId) {
		this.bankId = bankId;
	}
}

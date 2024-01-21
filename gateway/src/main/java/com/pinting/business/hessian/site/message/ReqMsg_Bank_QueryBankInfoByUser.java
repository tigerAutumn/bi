package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 查询用户绑定的单个银行信息 入参
 * @author shencheng
 * @2015-12-21 下午3:42:11
 */
public class ReqMsg_Bank_QueryBankInfoByUser extends ReqMsg {

	private static final long serialVersionUID = 7709659543245511955L;
	/*用户id*/
	private Integer userId;
	/*银行id*/
	private Integer bankId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getBankId() {
		return bankId;
	}

	public void setBankId(Integer bankId) {
		this.bankId = bankId;
	}
}

package com.pinting.business.accounting.service;

import com.pinting.business.model.BsSysWithdraw;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_Withdraw_SysWithdrawResult;

public interface SysWithdrawService {

	/**
	 * 增加系统提现
	 * @param sysWithdraw
	 * @return
	 */
	public boolean addSysWithdraw(BsSysWithdraw sysWithdraw);
	
	/**
	 * 增加系统提现
	 * @param sysWithdraw
	 * @return
	 */
	public Integer addSysWithdrawReturnId(BsSysWithdraw sysWithdraw);
	
	/**
	 * 系统提现更新，根据id
	 * @param sysWithdraw
	 * @return
	 */
	public boolean modifySysWithdrawById(BsSysWithdraw sysWithdraw);

	/**
	 * 系统提现后，对方发起的回调
	 * 根据返回结果做如下处理：
	 * 1.判断提现金额是否相同，不同则返回错误结果，相同执行2
	 * 2.更新系统提现表
	 * @param req
	 * @return boolean
	 */
	public boolean finishSysWithdraw(G2BReqMsg_Withdraw_SysWithdrawResult req);
	
	
}

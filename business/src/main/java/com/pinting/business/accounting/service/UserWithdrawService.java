package com.pinting.business.accounting.service;

import com.pinting.business.model.BsUserWithdraw;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_Withdraw_CustomerWithdrawCheck;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_Withdraw_CustomerWithdrawResult;

public interface UserWithdrawService {
	
	/**
	 * 增加用户提现记录
	 * @param bsUserWithdraw 提现对象
	 */
	public Integer addUserWithdraw(BsUserWithdraw bsUserWithdraw);

	/**
	 * 根据applyNo修改提现信息
	 * @param bsUserWithdraw 提现对象
	 */
	void modifyUserWithdrawByApplyNo(BsUserWithdraw bsUserWithdraw);
	
	/**
	 * 根据id号修改提现信息
	 * @param bsUserWithdraw 提现对象
	 */
	void modifyUserWithdrawById(BsUserWithdraw bsUserWithdraw);
	/**
	 * 根据申请号 查询用户提现记录
	 * @param applyNo
	 * @return 成功返回用户提现记录对象，否则返回null
	 */
	public BsUserWithdraw findUserWithdrawByApplyNo(String applyNo);

	/**
	 * 用户提现申请
	 * @param userId
	 * @param amount
	 * @param pass
	 */
	@Deprecated
	public void userWithdraw(Integer userId, Double amount, String pass);
	
	/**
	 * 提交提现后，返回结果
	 * @param req
	 */
	@Deprecated
	public void finishCustomerWithdrawResult(
			G2BReqMsg_Withdraw_CustomerWithdrawResult req);

	
	/**
	 * 用户提现申请验证
	 * @param req
	 * @return 验证通过，返回true，否则返回false
	 */
	@Deprecated
	public boolean checkCustomerWithdraw(
			G2BReqMsg_Withdraw_CustomerWithdrawCheck req);

}

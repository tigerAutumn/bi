package com.pinting.business.accounting.service;


import com.pinting.business.model.BsSysSubAccount;

public interface BsSysSubAccountService {
	
	/**
	 * 系统子账户，JSH金额转到USER
	 * @param amount
	 * @return
	 */
	public boolean updateJSHToUser(Double amount);
	
	/**
	 * 系统红包充值（红包预算审核通过，红包户余额增加）
	 * @param amount 预算通过的金额
	 */
	public void redPacketTopUp(Double amount);
	
	/**
	 * 红包使用（购买使用红包，存管自有子账户-、存管红包户+）
	 * @param amount 使用红包金额
	 */
	public void redPacketUsed(Double amount);
	
	/**
	 * 提现财务登记
	 * @param amount
	 * @param opUserId
	 */
	public void financialRegistry(Double amount, Integer opUserId);

	/**
	 * 管理台赞分期风险保证金系统快捷充值后，更新（新增）风险保证金余额，可用余额，可提现余额
	 * 同时记账处理
	 * 赞分期存在用户逾期现象
	 * 需要往赞分期风险保证金户存钱，确保理财人成功回款，用于平账，和系统余额户无关。
	 * 因为是额外充值进入系统户的钱，所以要更新风险保证金户
	 * @param amount    充值金额
	 */
	void updateMarginSysSubAccount(Double amount);

	/**
	 * 根据账户号查询记录并加行级锁
	 * @param code
	 * @return 查询不到记录则返回null
     */
	BsSysSubAccount findSysSubAccount4Lock(String code);
}

package com.pinting.business.service.site;

import com.pinting.business.model.BsWithdrawCheck;

public interface BsWithdrawCheckService {
	
	/**
	 * 新增提现审核表
	 * @param WithdrawCheck
	 * @return
	 */
	public void addWithdrawCheck(BsWithdrawCheck withdrawCheck);

	/**
	 * 修改提现审核表
	 * @param WithdrawCheck
	 * @return
	 */
	public void updateWithdrawCheck(BsWithdrawCheck withdrawCheck);
	
	/**
	 * 根据id查询BsWithdrawCheck
	 * @param id
	 * @return
	 */
	public BsWithdrawCheck selectWithdrawCheck(Integer id);
}

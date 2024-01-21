package com.pinting.business.service.site;

import java.util.List;

import com.pinting.business.model.BsAccount;
import com.pinting.business.model.BsAccountJnl;
import com.pinting.business.model.vo.AvailableClaimVO;
import com.pinting.business.model.vo.ZsdAvailableClaimVO;

public interface AccountService {
	/**
	 * 根据主账户信息新增主账户
	 * @param bsAccount
	 * @return 如果全部新增成功，则返回true，否则返回false
	 */
	public boolean addAccount(BsAccount bsAccount);
	/**
	 * 根据账户码查询用户信息
	 * @param accountCode 账户码
	 * @return 账户码在数据库中也是唯一，所以最多只返回一条BsAccount记录。如果没找到，返回null
	 */
	public BsAccount findAccountByAccountCode(String accountCode);
	/**
	 * 根据用户编号查询交易信息
	 * @param userId 用户编号
	 * @param pageIndex 页码-从0开始
	 * @param pageSize 页大小
	  * @return 如果查询成功返回明细，否则返回null
	 */
	public List<BsAccountJnl> findAccountJnlByUserId(Integer userId, Integer pageIndex, Integer pageSize);
	/**
	 * 根据userId查询交易明细总数
	 * @param userId 用户编号
	 * @return 返回明细总数
	 */
	public Integer findAccountJnlCountByUserId(Integer userId);
	/**
	 * 查询赞分期可转债权量
	 * @return
	 */
	public List<AvailableClaimVO> availableClaim();
	
	/**
	 * 查询赞时贷可转债权量
	 * @return
	 */
	public List<AvailableClaimVO> zsdAvailableClaim();
	
}

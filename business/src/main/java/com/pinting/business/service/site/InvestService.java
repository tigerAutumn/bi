package com.pinting.business.service.site;

import java.util.Date;
import java.util.List;

import com.pinting.business.model.BsDailyInterest;

/**
 * @Project: business
 * @Title: InvestService.java
 * @author Dong Wenkai
 * @date 2015-1-21 下午6:48:30
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public interface InvestService {
	/**
	 * 根据userId查询投资收益明细
	 * @param userId 用户编号
	 * @param pageIndex 页码-从0开始
	 * @param pageSize 页大小
	 * @return 如果查询成功返回明细，否则返回null
	 */
	public List<BsDailyInterest> findDailyInvestByUserId(Integer userId, Integer pageIndex, Integer pageSize);
	
	/**
	 * 根据userId查询投资收益明细总数
	 * @param userId 用户编号
	 * @return 返回明细总数
	 */
	public Integer findDailyInvestCountByUserId(Integer userId);
	/**
	 * 新增利息记录
	 * @param bsDailyInterest
	 * @return 增加成功返回true，否则返回false
	 */
	public boolean addInvestService(BsDailyInterest bsDailyInterest);
	
	/**
	 * 根据用户id和日期，查询该天的投资收益
	 * @param userId 
	 * @param day 
	 * @return 成功则返回List，否则返回null
	 */
	public List<BsDailyInterest> findDailyInterestByUserAndDay(Integer userId, Date day);
	
	
	/**
	 * 根据userId查询投资收益总金额
	 * @param userId 用户编号
	 * @return 返回明细总数
	 */
	public Double findInvestTotalByUserId(Integer userId);
	
	/**
	 * 根据用户ID查询赞分期中投资收益
	 * @param userId
	 * @return
	 */
	public Double findZanInvestTotalByUserId(Integer userId);
}

package com.pinting.business.service.site;

import java.util.Date;
import java.util.List;

import com.pinting.business.model.BsDailyBonus;
import com.pinting.business.model.vo.BsDailyBonusVO;

/**
 * @Project: business
 * @Title: BonusService.java
 * @author Dong Wenkai
 * @date 2015-1-21 下午6:48:15
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public interface BonusService {
	/**
	 * 根据userId查询推荐奖励明细 带分页
	 * @param userId 用户编号
	 * @param pageIndex 页码-从0开始
	 * @param pageSize 页大小
	 * @param withdrawFlag 是否可提现标志
	 * @return 如果查询成功返回明细，否则返回null
	 */
	public List<BsDailyBonus> findDailyBonusByUserId(Integer userId, Integer pageIndex, Integer pageSize, boolean withdrawFlag);
	
	/**
	 * 根据userId查询推荐奖励明细总数
	 * @param userId 用户编号
	 * @param withdrawFlag 是否可提现标志
	 * @return 返回明细总数
	 */
	public Integer findDailyBonusCountByUserId(Integer userId, boolean withdrawFlag);
	/**
	 * 新增奖励金收益记录
	 * @param bsDailyBonus 奖励金信息
	 * @return 成功返回true，否则返回false
	 */
	public boolean addDailyBonus(BsDailyBonus bsDailyBonus);
	/**
	 * 查询某用户结算户id，并统计 某用户某一天 的日奖励金
	 * @param userId 用户编号
	 * @param time	记录时间
	 * @return 成功返回BsDailyBonusVO对象（用户结算户id不为空，日奖励金可能为空），否则返回null
	 */
	public BsDailyBonusVO findSumDailyBonusByUserIdAndTime(Integer userId, Date time);
	
	/**
	 * 计算[项目开始，今天]的奖励金额
	 * @param addDays
	 * @return
	 */
	public Double sumShouldBonus();
	
	/**
	 * 查询某天某用户的 奖励金
	 * @param userId
	 * @return
	 */
	public BsDailyBonus findDailyBonusByUserIdAndDate(Integer userId, Date date);

	/**
	 * 计算已提现的奖励金
	 * @return
	 */
	public double sumIncarnateBonus();
	
}

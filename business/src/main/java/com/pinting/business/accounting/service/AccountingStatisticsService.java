package com.pinting.business.accounting.service;

import java.util.Date;
import java.util.List;

import com.pinting.business.model.vo.BsDailyInterestVO;
import com.pinting.business.model.vo.BsSubAc4InterestVO;
import com.pinting.business.model.vo.BsUserBonusVO;

/**
 * 账务统计接口
 * @Project: business
 * @Title: AccountingStatisticsService.java
 * @author dingpf
 * @date 2015-2-4 下午3:58:21
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public interface AccountingStatisticsService {
	/**
	 * 根据计息日期查询所有有效子产品户，并直接计算各产品户的利息
	 * @param interestDate 计息日期
	 * @return 成功返回所有有效子产品户视图列表（包括各产品户利息），否则返回null
	 */
	public List<BsSubAc4InterestVO> findInterestForProduct(Date interestDate);
	/**
	 * 根据计息日期查询所有用户的该天利息（根据用户分组查询，并统计用户利息）
	 * @param interestDate 计息日期
	 * @return 成功返回各用户该天利息视图列表，否则返回null
	 */
	public List<BsDailyInterestVO> findUserDailyInterest(Date interestDate);
	/**
	 * 查询各被推荐人信息及其投资金额信息 列表
	 * @return 成功返回各被推荐人信息及投资金额信息，否则返回null
	 */
	public List<BsUserBonusVO> findRecommendedUserAndInvestAmount();
	
	/**
	 * 统计所有已到期投资，并修改投资的状态
	 * @param currentDate 当前日期
	 * 
	 */
	public void modifyExpireInvestInterest(Date currentDate);

}

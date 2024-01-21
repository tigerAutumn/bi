package com.pinting.business.service.loan;

import java.util.List;

/**
 * AUTH账户余额查询
 * @author bianyatian
 * @2016-9-1 上午10:07:30
 */
public interface AuthBalanaceQueryService {
	
	//=====================赞分期====================
	
	/**
	 * 查询所有非超级理财人的AUTH账户余额
	 * @param term 期限 当term为空时，返回总额
	 * @param day4waitLoan
	 * @param superUserList
	 * @param outUserId 出让人id，需排除
	 * @return 站岗资金余额
	 */
	Double getNormalAuthBalance(Integer term,Integer day4waitLoan, List<Integer> superUserList, Integer outUserId);

	/**
	 * 查询所有非超级理财人的AUTH账户余额（小于最小债权金额的用户）
	 * @param term 期限 当term为空时，返回总额
	 * @param day4waitLoan
	 * @param superUserList
	 * @return 站岗资金余额
     */
	Double getSmallNormalAuthBalanceNew(Integer term,Integer day4waitLoan, List<Integer> superUserList);
	
	/**
	 * 查询超级理财人的AUTH账户余额
	 * @param superUserList
	 * @return
	 */
	Double getSuperAuthBalance(List<Integer> superUserList);
	
	
	//=====================赞时贷====================
	
	/**
	 * 查询普通用户当前可匹配金额
	 * @param vipUserList
	 * @return
	 */
	Double getNormalAuthBalanceZDS(List<Integer> vipUserList);
	
	
	
	/**
	 * 查询vip用户当前可匹配金额
	 * @param vipUserList
	 * @return
	 */
	Double getVIPAuthBalanceZDS(List<Integer> vipUserList);

}

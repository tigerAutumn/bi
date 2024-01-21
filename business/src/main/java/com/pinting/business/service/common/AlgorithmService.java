package com.pinting.business.service.common;

import java.util.List;

import com.pinting.business.model.vo.AverageCapitalPlusInterestVO;
import com.pinting.business.model.vo.ZANRevenueModelVO;
/**
 * 借款相关公共算法服务
 * @author bianyatian
 * @2016-8-30 上午10:57:12
 */
public interface AlgorithmService {
	
	/**
	 * 等额本息计算，返回所有期数本金，利息
	 * @param amount 本金
	 * @param term 期数
	 * @param rate 年化利率% 不需要除100（13%传13）
	 * @return
	 */
	List<AverageCapitalPlusInterestVO> calAverageCapitalPlusInterestPlan(Double amount, Integer term, Double rate);
	
	/**
	 * 等额本息计算,返回某期
	 * @param amount 本金
	 * @param term 期数
	 * @param rate 年化利率(13%传0.13)
	 * @return
	 */
	AverageCapitalPlusInterestVO calAverageCapitalPlusInterestPlan4Serial(Double amount, Integer term, Double rate, Integer serialId);
	
	/**
	 * 计算某笔借款某期应还理财人本息
	 * @param loanId 借款编号
	 * @param serialId 期次
	 * @return
	 */
	Double calFinancerPrincipalInterest(Integer loanId, Integer serialId);

	/**
	 * 计算某笔借款某期应提保证金
	 * @param loanId 借款编号
	 * @param serialId 期次
	 * @return
	 */
	Double calZANDeposit(Integer loanId, Integer serialId);

	/**
	 * 计算某笔借款某期应提币港湾服务费
	 * 借款总额/期数+借款总额*名义月利率-投资人每期本息-VIP每期本息
	 * @param loanId 借款编号
	 * @param serialId 期次
	 * @return
	 */
	Double calBGWServiceFee(Integer loanId, Integer serialId);

	/**
	 * 计算某笔借款某期赞分期营收
	 * @param loanId 借款编号
	 * @param serialId 期次
	 * @return
	 */
	ZANRevenueModelVO calZANRevenue(Integer loanId, Integer serialId);
	
	/**
	 * 计算某笔借款某期应提币港湾结算费
	 * 借款总额/期数+借款总额*名义月利率
	 * @param loanId 借款编号
	 * @param serialId 期次
	 * @return
	 */
	Double calBGWSettlementFee(Integer loanId, Integer serialId);

	/**
	 * 等本等息计算,返回所有期数本金，利息
	 * @param amount 借款总本金
	 * @param term 借款总期数
	 * @param rate 结算年化利率(20%传0.2000)
	 * @return
	 */
	List<AverageCapitalPlusInterestVO> calEqualPrincipalInterestPlan(Double amount, Integer term, Double rate);

	/**
	 * 等本等息计算,返回某期
	 * @param amount 借款总本金
	 * @param term 借款总期数
	 * @param rate 结算年化利率(20%传0.2000)
	 * @param serialId 还款期次
	 * @return
	 */
	AverageCapitalPlusInterestVO calEqualPrincipalInterestPlan4Serial(Double amount, Integer term, Double rate, Integer serialId);
}

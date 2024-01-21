package com.pinting.business.service.site;

import com.pinting.business.model.vo.UserInfoVO;

public interface OrderService {

	/**
	 * 
	 * @Title: calculateTotal 
	 * @Description: 计算用户当日通过银行卡已使用的金额
	 * @param startDay
	 * @param endDay
	 * @param userId
	 * @param bankId
	 * @return
	 * @throws
	 */
	public Double calculateTotal(Integer userId,Integer bankId);
	
	
	/**
	 * 计算用户当日充值金额
	 * @param userId
	 * @return
	 */
	public Double dayTopUpTotal(Integer userId);
	
	/**
	 * 计算用户昨日充值金额
	 * @param userId
	 * @return
	 */
	public Double yesterdayTopUpTotal(Integer userId);
	
	
	/**
	 * 融宝招行快捷支付相关
	 * 根据订单号和产品ID查询用户购买信息（预下单前置信息查询）
	 * @param userId
	 * @return
	 */
	public UserInfoVO reapalQuickCMB(String  orderNo,Integer productId);

	/**
	 * 单日回款到卡金额（包含已回款到卡+回款到卡回款中+提现成功+提现中+审核中）
	 * @param userId	用户ID
	 * @return
     */
	Double sumWithdrawUpperLimit(int userId);
	
	/**
	 * 当日已提现+目前审核中+目前提现中 的金额
	 * @param userId
	 * @return
	 */
	Double sumWithdrawCheckAmount(int userId);
	
	/**
	 * 今天充值成功-今天余额购买
	 * @param userId
	 * @return
	 */
	Double topUpSubBuyBalanceToday(int userId);

	Double sumPayingAmountByTransType(Integer userId, String transBonusWithdraw, int orderStatusPaying);
}

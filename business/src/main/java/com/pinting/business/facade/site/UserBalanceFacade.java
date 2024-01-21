package com.pinting.business.facade.site;

import com.pinting.business.accounting.finance.service.UserBalanceWithdrawService;
import com.pinting.business.hessian.site.message.ReqMsg_UserBalance_CheckDayLimit;
import com.pinting.business.hessian.site.message.ReqMsg_UserBalance_Withdraw;
import com.pinting.business.hessian.site.message.ResMsg_UserBalance_CheckDayLimit;
import com.pinting.business.hessian.site.message.ResMsg_UserBalance_Withdraw;
import com.pinting.business.model.BsSysConfig;
import com.pinting.business.service.manage.BsSysConfigService;
import com.pinting.business.service.site.OrderService;
import com.pinting.business.util.Constants;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.MoneyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 
 * @Project: business
 * @Title: BalanceFacade.java
 * @author Zhou Changzai
 * @date 2015-11-24下午10:03:09
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Component("UserBalance")
public class UserBalanceFacade {
	private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);
	
	@Autowired
	private UserBalanceWithdrawService userBalanceWithdrawService;
	@Autowired
	private BsSysConfigService bsSysConfigService;
	@Autowired
	private OrderService orderService;

	/**
	 * 用户余额提现,只做发起，不做确认。确认成功在异步通知里面
	 * 
	 * @param req
	 *            包含userId, amount, password
	 * @param res
	 */
	//@Transactional
	public void withdraw(ReqMsg_UserBalance_Withdraw req,
			ResMsg_UserBalance_Withdraw res) {
		userBalanceWithdrawService.preWithdraw(req, res);

	}

	public void checkDayLimit(ReqMsg_UserBalance_CheckDayLimit req, ResMsg_UserBalance_CheckDayLimit res) {
		// 前置校验-单笔上限、单日上限
		res.setMoreThanDayLimit(false);
		BsSysConfig dayUpperLimitConfig = bsSysConfigService.findKey(Constants.DAY_WITHDRAW_UPPER_LIMIT);
		Double dayUpperLimit = Double.parseDouble(dayUpperLimitConfig.getConfValue());
		Double amount = orderService.sumWithdrawUpperLimit(req.getUserId());
		if(dayUpperLimit.compareTo(MoneyUtil.add(amount, req.getAmount()).doubleValue()) < 0) {
			// 单日上限
			res.setMoreThanDayLimit(true);
			res.setLeftAmount(MoneyUtil.subtract(dayUpperLimit, amount).doubleValue());
		} else {
			res.setMoreThanDayLimit(false);
			res.setLeftAmount(MoneyUtil.subtract(dayUpperLimit, amount).doubleValue());
		}
	}



}

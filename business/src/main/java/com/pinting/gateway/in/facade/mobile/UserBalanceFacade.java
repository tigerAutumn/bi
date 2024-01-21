package com.pinting.gateway.in.facade.mobile;

import com.pinting.business.accounting.finance.service.UserBalanceWithdrawService;
import com.pinting.business.dao.BsChannelCheckMapper;
import com.pinting.business.hessian.site.message.ReqMsg_UserBalance_Withdraw;
import com.pinting.business.hessian.site.message.ResMsg_UserBalance_Withdraw;
import com.pinting.business.service.manage.BsSysConfigService;
import com.pinting.business.service.site.OrderService;
import com.pinting.business.service.site.SubAccountService;
import com.pinting.business.service.site.UserTransDetailService;
import com.pinting.business.util.Constants;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.gateway.in.util.InterfaceVersion;
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
@Component("appUserBalance")
public class UserBalanceFacade {
	private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);
	
	@Autowired
	private UserBalanceWithdrawService userBalanceWithdrawService;
	@Autowired
	private BsSysConfigService bsSysConfigService;
	@Autowired
	private SubAccountService subAccountService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private UserTransDetailService userTransDetailService;
	@Autowired
	private BsChannelCheckMapper bsChannelCheckMapper;

	/**
	 * 用户余额提现,只做发起，不做确认。确认成功在异步通知里面
	 * 
	 * @param req
	 *            包含userId, amount, password
	 * @param res
	 */
	@InterfaceVersion("Withdraw/1.0.0")
	public void withdraw(ReqMsg_UserBalance_Withdraw req,
			ResMsg_UserBalance_Withdraw res) {
		userBalanceWithdrawService.preWithdraw(req, res);
	}
	
	
	/**
	 * 用户余额提现,只做发起，不做确认。确认成功在异步通知里面
	 * 存管改造
	 * @param req
	 *            包含userId, amount, password
	 * @param res
	 */
	@InterfaceVersion("Withdraw/1.0.1")
	public void withdraw_1(ReqMsg_UserBalance_Withdraw req,
			ResMsg_UserBalance_Withdraw res) {
		userBalanceWithdrawService.preWithdraw(req, res);
	}
}

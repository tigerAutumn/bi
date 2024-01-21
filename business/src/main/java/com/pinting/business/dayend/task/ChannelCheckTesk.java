package com.pinting.business.dayend.task;

import com.pinting.business.accounting.finance.service.UserBalanceWithdrawService;
import com.pinting.business.dao.BsChannelCheckMapper;
import com.pinting.business.dao.BsPayOrdersMapper;
import com.pinting.business.dao.BsWithdrawCheckMapper;
import com.pinting.business.model.BsChannelCheck;
import com.pinting.business.util.Constants;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Map;

/**
 * 每日凌晨1点开始做定时任务，统计订单表中充值和购买的金额，当融宝渠道的金额大于0，则跳过不处理；
 * 当融宝渠道的金额等于0时，则在渠道互转审核表bs_channel_check中生成一条记录，状态为CONFIRMED；
 * 
 * @author bianyatian
 * @2016-1-8 上午10:26:50
 */
@Deprecated
public class ChannelCheckTesk {
	private Logger log = LoggerFactory.getLogger(ChannelCheckTesk.class);
	@Autowired
	private BsPayOrdersMapper bsPayOrdersMapper;
	@Autowired
	private BsChannelCheckMapper bsChannelCheckMapper;
	@Autowired
    private BsWithdrawCheckMapper bsWithdrawCheckMapper;
	@Autowired
    private UserBalanceWithdrawService userBalanceWithdrawService;
	

	/**
	 * 1.查询昨日融宝支付和购买订单的金额是否为空，不为空则不操作，为空则继续
	 * 2.是否存在当日状态为CONFIRMED的bs_channel_check记录是否没有，有则不做操作，无则生成记录
	 * 3.生成记录
	 * 4.处理昨日的提现审核记录bs_withdraw_check，将昨日的提现审核记录的ID和该审核员ID传给提现服务
	 */
	protected void execute() {
		log.info("=================【检查是否需要生产渠道互转审核表】定时任务开始=====================");
		try {
			// 1.查询昨日融宝支付和购买订单的金额
			Map<String, Object> map = bsPayOrdersMapper
					.selectFinanceWithdrawAmount(DateUtil
							.formatYYYYMMDD(DateUtil.addDays(new Date(), -1)),
							DateUtil.formatYYYYMMDD(new Date()),
							Constants.CHANNEL_REAPAL);
			Double amount = 0d;
			if (map != null) {
				Double buyAmount = (Double) map.get("buyAmount") == null ? 0d
						: (Double) map.get("buyAmount");
				Double rechargeAmount = (Double) map.get("rechargeAmount") == null ? 0d
						: (Double) map.get("rechargeAmount");
				amount = MoneyUtil.add(buyAmount, rechargeAmount).doubleValue();
			}
			if (amount.compareTo(0d) == 0) {
				// 2.是否存在当日状态为CONFIRMED的bs_channel_check记录
				Date nowTime = new Date();
				String startDay = DateUtil.parseDate(DateUtil
						.formatYYYYMMDD(nowTime)) + " 00:00:00";
				String endDay = DateUtil.parseDate(DateUtil
						.formatYYYYMMDD(nowTime)) + " 23:59:59";
				Integer count = bsChannelCheckMapper.countByStatusTime(
						Constants.CHANNEL_CHECK_STATUS_CONFIRMED, startDay,
						endDay);
				if (count == 0) {
					// 3.生成记录
					BsChannelCheck channelCheck = new BsChannelCheck();
					channelCheck.setChannel(Constants.CHANNEL_REAPAL);
					channelCheck.setCreateTime(nowTime);
					channelCheck.setUpdateTime(nowTime);
					channelCheck
							.setStatus(Constants.CHANNEL_CHECK_STATUS_CONFIRMED);
					bsChannelCheckMapper.insertSelective(channelCheck);
					
					// 4.处理昨日的提现审核记录状态为TO_CHECK的提现，将提现审核记录的ID传给提现服务；
					/*String date = DateUtil.formatYYYYMMDD(new Date()) + " 00:00:00";
			        Date todayStart = DateUtil.parse(date, "yyyy-MM-dd HH:mm:ss");
					Date yesterdayStart = DateUtil.addDays(todayStart, -1);
					BsWithdrawCheckExample bsWithdrawCheckExample = new BsWithdrawCheckExample();
					bsWithdrawCheckExample.createCriteria()
							.andCreateTimeBetween(yesterdayStart, todayStart)
							.andStatusEqualTo(Constants.WITHDRAW_TO_CHECK);
					List<BsWithdrawCheck> bsWithdrawChecks = bsWithdrawCheckMapper
							.selectByExample(bsWithdrawCheckExample);
					for (BsWithdrawCheck bsWithdrawCheck : bsWithdrawChecks) {
						ReqMsg_UserBalance_WithdrawPass reqMsg = new ReqMsg_UserBalance_WithdrawPass();
						ResMsg_UserBalance_WithdrawPass resMsg = new ResMsg_UserBalance_WithdrawPass();
						reqMsg.setCheckId(bsWithdrawCheck.getId());
						userBalanceWithdrawService.pass(reqMsg, resMsg);
					}*/
				}
			}
		} catch (Exception e) {
			log.error("=================【检查是否需要生产渠道互转审核表】定时任务异常=====================");
		}
		log.info("=================【检查是否需要生产渠道互转审核表】定时任务结束=====================");
	}

}

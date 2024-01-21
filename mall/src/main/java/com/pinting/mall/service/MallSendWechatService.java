package com.pinting.mall.service;

import java.util.Date;

public interface MallSendWechatService {

	/**
	 * 积分发放的微信消息发送
	 * @param userName 客户名
	 * @param time 时间
	 * @param type 类型
	 * @param points 积分
	 * @param balance 余额
	 * @return
	 */
	String mallPointsDistribution(Integer userId, String userName, Date time, String type, Long points, Long balance);
	
	/**
	 * 积分消费的微信消息发送
	 * @param userId
	 * @param content
	 * @param points
	 * @param merchantName
	 * @param time
	 * @return
	 */
	String mallPointsConsume(Integer userId, String content, String points, String merchantName, Date time);
}

package com.pinting.business.service.site;

/**
 * 使用rabbitMQ发送微信消息模板
 * @project business
 * @author bianyatian
 * @2018-8-8 下午5:38:37
 */
public interface SendWechatByRabbitService {

	
	
	/**
     * 奖励金到账通知入rabbitMQ
     * @author bianyatian
     * @param userId 用户id
     * @param amount 到账金额
     * @param finishTime 完成时间
     * @return
     */
    void bonusArrive(Integer userId, String amount, String finishTime);
}

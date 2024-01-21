/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.dayend.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.pinting.business.service.manage.ChannelWithdrawService;

/**
 * 管理台渠道提现
 * @author HuanXiong
 * @version $Id: FinanceChannelWithdrawTask.java, v 0.1 2016-1-7 下午3:24:54 HuanXiong Exp $
 */
@Deprecated
public class ChannelWithdrawTask {

    private Logger logger = LoggerFactory.getLogger(ChannelWithdrawTask.class);
    
    @Autowired
    private ChannelWithdrawService channelWithdrawService;
    
    /**
     * 任务执行
     */
    protected void execute() {
        channelWithdrawTask();
    }
    
    /**
     * 增加后台定时服务线程
     * 1、轮询所有提现CHANNEL_WITHDRAW 为处理中的订单，
     * 每成功一笔，假设该笔金额为B，则冻结金额-B，余额和可用余额-B。
     * 一旦有成功的订单需要修改时，则检查该订单的updatetime为当日，
     * 并且当日的所有成功的订单金额等于A的金额，则bs_channel_check中生成一条记录，
     * 状态为（TRANSFERED 钱已到账）
     */
    public void channelWithdrawTask(){
        logger.info("==================日终【轮询所有提现 CHANNEL_WITHDRAW 为处理中的订单】开始====================");
        channelWithdrawService.channelWithdrawPolling();
        logger.info("==================日终【轮询所有提现 CHANNEL_WITHDRAW 为处理中的订单】结束====================");
    }
    
    
    
}

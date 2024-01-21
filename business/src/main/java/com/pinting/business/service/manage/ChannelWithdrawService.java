/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.service.manage;

import com.pinting.business.hessian.manage.message.ReqMsg_ChannelWithdraw_AuditWithdrawCheck;
import com.pinting.business.hessian.manage.message.ReqMsg_ChannelWithdraw_ChannelWithdraw;
import com.pinting.business.hessian.manage.message.ReqMsg_ChannelWithdraw_ConfirmTransfer;
import com.pinting.business.hessian.manage.message.ReqMsg_ChannelWithdraw_QueryWithdrawCheck;
import com.pinting.business.hessian.manage.message.ReqMsg_ChannelWithdraw_WithdrawIndex;
import com.pinting.business.hessian.manage.message.ResMsg_ChannelWithdraw_AuditWithdrawCheck;
import com.pinting.business.hessian.manage.message.ResMsg_ChannelWithdraw_ChannelWithdraw;
import com.pinting.business.hessian.manage.message.ResMsg_ChannelWithdraw_ConfirmTransfer;
import com.pinting.business.hessian.manage.message.ResMsg_ChannelWithdraw_QueryWithdrawCheck;
import com.pinting.business.hessian.manage.message.ResMsg_ChannelWithdraw_WithdrawIndex;

/**
 * 
 * @author HuanXiong
 * @version $Id: FinanceService.java, v 0.1 2016-1-7 上午10:47:31 HuanXiong Exp $
 */
public interface ChannelWithdrawService {
    
    /**
     * 渠道提现首页信息 
     * 默认渠道是融宝
     * @param req
     * @param res
     */
    public void withdrawIndex(ReqMsg_ChannelWithdraw_WithdrawIndex req, ResMsg_ChannelWithdraw_WithdrawIndex res);
    
    /**
     * 渠道提现操作
     * 
     * @param req
     * @param res
     */
    public void channelWithdraw(ReqMsg_ChannelWithdraw_ChannelWithdraw req, ResMsg_ChannelWithdraw_ChannelWithdraw res);

    /**
     * (定时任务)渠道提现轮询
     */
    public void channelWithdrawPolling();

    /**
     * 确认转账
     * @param req
     * @param res
     */
    public void confirmTransfer(ReqMsg_ChannelWithdraw_ConfirmTransfer req,
                                ResMsg_ChannelWithdraw_ConfirmTransfer res);
    
    /**
     * 
     * 查询bs_channel_check表,用户提现申请，财务确认处理查询
     * @param req
     * @param res
     */
    public void queryWithdrawCheck(ReqMsg_ChannelWithdraw_QueryWithdrawCheck req,
                                   ResMsg_ChannelWithdraw_QueryWithdrawCheck res);

    /**
     * 提现审核
     * @param req
     * @param res
     */
    public void auditWithdrawCheck(ReqMsg_ChannelWithdraw_AuditWithdrawCheck req,
                                   ResMsg_ChannelWithdraw_AuditWithdrawCheck res);
    
}

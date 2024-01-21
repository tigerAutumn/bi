/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.facade.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
import com.pinting.business.service.manage.ChannelWithdrawService;

/**
 * 
 * @author HuanXiong
 * @version $Id: FinanceFacade.java, v 0.1 2016-1-7 上午10:49:04 HuanXiong Exp $
 */
@Component("ChannelWithdraw")
public class ChannelWithdrawFacade {

    @Autowired
    private ChannelWithdrawService channelWithdrawService;
    
    /**
     * 财务提现首页信息 
     * 默认渠道是融宝
     * @param req
     * @param res
     */
    public void withdrawIndex(ReqMsg_ChannelWithdraw_WithdrawIndex req, 
                              ResMsg_ChannelWithdraw_WithdrawIndex res) {
        channelWithdrawService.withdrawIndex(req, res);
    }
    
    /**
     * 财务提现操作
     * 1、获取可提现金额
     * 2、修改辅助通道系统账户表bs_help_channel_acccount，可提现金额减少，冻结金额增加
     * 3、从系统配置表中获取当前支付渠道的代付限额，当提现金额大于单日单卡代付限额时，
     *    实际代付为配置中的单日单卡代付限额为准，按照单笔限额做多笔循环代付提现
     * @param req
     * @param res
     */
    public void channelWithdraw(ReqMsg_ChannelWithdraw_ChannelWithdraw req,
                                ResMsg_ChannelWithdraw_ChannelWithdraw res){
        channelWithdrawService.channelWithdraw(req, res);
    }
    
    /**
     * 财务确认转账
     * 
     * @param req
     * @param res
     */
    public void confirmTransfer(ReqMsg_ChannelWithdraw_ConfirmTransfer req,
                                ResMsg_ChannelWithdraw_ConfirmTransfer res) {
        channelWithdrawService.confirmTransfer(req, res);
    }
    
    /**
     * 查询bs_channel_check表,用户提现申请，财务确认处理查询
     * 
     * @param req
     * @param res
     */
    public void queryWithdrawCheck(ReqMsg_ChannelWithdraw_QueryWithdrawCheck req,
                                   ResMsg_ChannelWithdraw_QueryWithdrawCheck res) {
        channelWithdrawService.queryWithdrawCheck(req, res);
    }
    
    /**
     * 提现审核
     * 
     * @param req
     * @param res
     */
    public void auditWithdrawCheck(ReqMsg_ChannelWithdraw_AuditWithdrawCheck req, 
                                   ResMsg_ChannelWithdraw_AuditWithdrawCheck res) {
        channelWithdrawService.auditWithdrawCheck(req, res);
    }
}

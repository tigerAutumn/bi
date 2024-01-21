package com.pinting.business.accounting.finance.service;

import com.pinting.business.accounting.finance.model.DFResultInfo;
import com.pinting.business.hessian.manage.ReqMsg_UserBalance_WithdrawFall;
import com.pinting.business.hessian.manage.ReqMsg_UserBalance_WithdrawPass;
import com.pinting.business.hessian.manage.ResMsg_UserBalance_WithdrawFall;
import com.pinting.business.hessian.manage.ResMsg_UserBalance_WithdrawPass;
import com.pinting.business.hessian.site.message.ReqMsg_UserBalance_Withdraw;
import com.pinting.business.hessian.site.message.ResMsg_UserBalance_Withdraw;

import java.util.Date;

/**
 * Created by babyshark on 2016/9/9.
 */
public interface UserBalanceWithdrawService {
    /**
     * 发送用户提现请求(无需审核)
     * @param req
     * @param res
     */
    public void apply(ReqMsg_UserBalance_Withdraw req, ResMsg_UserBalance_Withdraw res);
    /**
     * 用户提现请求通知
     * @param req
     */
    public void notify(DFResultInfo req);

    /**
     * 发送用户提现请求(需审核)
     * @param req
     * @param res
     */
    public void check(ReqMsg_UserBalance_Withdraw req, ResMsg_UserBalance_Withdraw res);

    /**
     * 后台管理审核通过
     * @param req
     * @param res
     */
    public void pass(ReqMsg_UserBalance_WithdrawPass req, ResMsg_UserBalance_WithdrawPass res);


    /**
     * 后台管理审核不通过（置为失败）
     * @param req
     * @param res
     */
    public void refuse(ReqMsg_UserBalance_WithdrawFall req, ResMsg_UserBalance_WithdrawFall res);

    public int countHistoryWithdraw(Date startDate, Date endDate, ReqMsg_UserBalance_Withdraw req);

    /**
     * 提现前校验是否需要审核
     * @param req
     * @param res
     */
    public void preWithdraw(ReqMsg_UserBalance_Withdraw req, ResMsg_UserBalance_Withdraw res);


}

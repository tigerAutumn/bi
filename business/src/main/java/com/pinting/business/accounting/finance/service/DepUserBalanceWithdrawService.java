package com.pinting.business.accounting.finance.service;

import com.pinting.business.accounting.finance.model.DFResultInfo;
import com.pinting.business.hessian.site.message.ReqMsg_UserBalance_Withdraw;
import com.pinting.business.hessian.site.message.ResMsg_UserBalance_Withdraw;
import com.pinting.business.model.vo.BalanceWithdrawCheckVO;

/**
 * Author:      cyb
 * Date:        2017/4/12
 * Description: 存管提现余额提现
 */
public interface DepUserBalanceWithdrawService {

    /**
     * 发送用户提现请求(无需审核)
     * @param req
     * @param res
     */
    void apply(ReqMsg_UserBalance_Withdraw req, ResMsg_UserBalance_Withdraw res);

    /**
     * 发起提现（管理台审核提现实时发送，定时轮询提现调用此服务）
     * @param req
     * @param res
     * @param checkStatus   审核状态
     */
    void checkPass(ReqMsg_UserBalance_Withdraw req, ResMsg_UserBalance_Withdraw res, String checkStatus);

    /**
     * 代偿人提现前置校验（是否存入队列）
     * @param req
     * @param res
     */
    void preCompensatorApply(ReqMsg_UserBalance_Withdraw req, ResMsg_UserBalance_Withdraw res);

    /**
     * 代偿人提现申请
     * @param req
     * @param res
     */
    void compensatorApply(ReqMsg_UserBalance_Withdraw req, ResMsg_UserBalance_Withdraw res);

    /**
     * 用户提现请求通知
     * @param req
     */
    void notify(DFResultInfo req);

    /**
     * 发送用户提现请求(需审核)
     * @param req
     * @param res
     * @return  审核ID
     */
    Integer check(ReqMsg_UserBalance_Withdraw req, ResMsg_UserBalance_Withdraw res);

    /**
     * 是否赞分期代偿人
     * @param userId
     * @return
     */
    boolean isCompensatoryUserZAN(Integer userId);

    void withdrawCheck(BalanceWithdrawCheckVO req);
}

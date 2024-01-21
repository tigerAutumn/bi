package com.pinting.business.service.manage;

/**
 * Author:      cyb
 * Date:        2017/1/11
 * Description: 系统子账户风险保证金服务
 */
public interface MarginSysSubAccountService {

    /**
     * 管理台赞分期风险保证金系统快捷充值后，更新（新增）风险保证金余额，可用余额，可提现余额
     * 赞分期存在用户逾期现象
     * 需要往赞分期风险保证金户存钱，确保理财人成功回款，用于平账，和系统余额户无关。
     * 因为是额外充值进入系统户的钱，所以要更新风险保证金户
     * @param amount    充值金额
     */
    void updateMarginSysSubAccount(Double amount);

}



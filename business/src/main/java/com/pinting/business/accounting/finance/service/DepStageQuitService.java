package com.pinting.business.accounting.finance.service;

/**
 * Author:      cyb
 * Date:        2017/4/24
 * Description: 存管分期产品退出服务
 */
public interface DepStageQuitService {

    /**
     * vip退出记账服务
     * @param quitId        bs_vip_quit的ID
     * @param userId        vip用户ID
     * @param checkUserId   审核人ID
     */
    void vipQuitAccount(Integer quitId, Integer userId, Integer checkUserId);

}

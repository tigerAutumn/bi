package com.pinting.business.accounting.finance.service;

import com.pinting.business.model.BsSpecialJnl;

/**
 * Created by babyshark on 2016/9/9.
 */
public interface UserBonusExtractService {
    /**
     * 奖励金转余额
     * 用户子账户JLJ转JSH，系统子账户JSH转USER
     * @param userId
     * @param amount
     * @return
     */
    boolean transBonusToJSH(Integer userId, Double amount);

    /**
     * 针对奖励金转余额处理中交易，管理台执行通过操作
     * @param special
     * @return
     */
    boolean passBonus2JSH(BsSpecialJnl special);
}

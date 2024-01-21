package com.pinting.business.accounting.finance.service;

import com.pinting.business.accounting.finance.model.DFResultInfo;
import com.pinting.business.model.BsSpecialJnl;
import com.pinting.business.model.vo.BonusWithdrawVO;

/**
 * Author:      cyb
 * Date:        2017/4/10
 * Description: 存管体系奖励金提现
 */
public interface DepUserBonusWithdrawService {

    /**
     * 用户奖励金提现
     * @param userId        用户ID
     * @param bonusAmount   奖励金金额
     * @param payPassword   用户支付密码
     * @param terminalType  终端类型@1手机端,2PC端
     */
	BonusWithdrawVO userBonusWithdraw(Integer userId, Double bonusAmount, String payPassword, Integer terminalType);

    /**
     * 奖励金提现异步通知
     * @param req
     */
    void notifyBonusWithdraw(DFResultInfo req);

}

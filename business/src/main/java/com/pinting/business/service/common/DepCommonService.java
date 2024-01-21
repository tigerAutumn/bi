package com.pinting.business.service.common;

import com.pinting.business.accounting.loan.enums.DepTargetEnum;
import com.pinting.business.model.LnDepositionTarget;
import com.pinting.business.model.LnLoan;

/**
 * Created by zhangbao on 2017/4/15.
 */
public interface DepCommonService {
    /**
     * 修改ln_deposition_target状态,新增一条ln_deposition_target_jnl
     * @param isSuc false时不修改ln_deposition_target状态
     * @param depTargetType
     * @param depTargetOperate
     * @param lnDepositionTarget
     * @param lnLoan
     */
    void updateTargetStatus(boolean isSuc, String depTargetType, DepTargetEnum depTargetOperate, LnDepositionTarget lnDepositionTarget, LnLoan lnLoan, String orderNo);
    
    
    /**
     * 新增一条ln_deposition_target_jnl
     * @param isSuc 是否成功
     * @param depTargetType
     * @param depTargetOperate
     * @param lnDepositionTarget
     * @param lnLoan
     */
    void insertTargetJnl(boolean isSuc, String depTargetType, DepTargetEnum depTargetOperate, LnDepositionTarget lnDepositionTarget, LnLoan lnLoan, String orderNo);

}

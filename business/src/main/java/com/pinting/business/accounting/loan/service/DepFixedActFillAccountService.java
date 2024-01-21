package com.pinting.business.accounting.loan.service;

import com.pinting.business.accounting.loan.enums.PartnerEnum;

/**
 * Author:      cyb
 * Date:        2017/4/7
 * Description: 云贷补账记账服务
 */
public interface DepFixedActFillAccountService {

    /**
     * 补账完成记账服务
     * @param sysAccountCode 系统子账户code
     * @param amount 对应增加金额
     */
    void depFixedActFillFinishRecord(String sysAccountCode, Double amount, PartnerEnum partnerEnum);

}

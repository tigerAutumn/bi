package com.pinting.business.accounting.finance.service;

import java.util.Date;

/**
 * Author:      cyb
 * Date:        2017/5/10
 * Description: 恒丰出入金对账服务
 */
public interface HFBankCheckAccountService {

    /**
     * 恒丰对账总服务
     */
    void checkAccount(Date time);

}

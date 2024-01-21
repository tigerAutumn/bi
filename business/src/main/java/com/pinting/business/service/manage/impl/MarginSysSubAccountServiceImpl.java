package com.pinting.business.service.manage.impl;

import com.pinting.business.accounting.service.BsSysSubAccountService;
import com.pinting.business.service.manage.MarginSysSubAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author:      cyb
 * Date:        2017/1/11
 * Description: 系统子账户风险保证金服务
 */
@Service
public class MarginSysSubAccountServiceImpl implements MarginSysSubAccountService {

    @Autowired
    private BsSysSubAccountService bsSysSubAccountService;

    @Override
    public void updateMarginSysSubAccount(Double amount) {
        bsSysSubAccountService.updateMarginSysSubAccount(amount);
    }

}


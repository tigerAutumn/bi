/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.service.site.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.BsSubAccountMapper;
import com.pinting.business.service.site.RechargeService;

/**
 * 充值
 * @author HuanXiong
 * @version $Id: RechargeServiceImpl.java, v 0.1 2015-11-19 上午10:42:32 HuanXiong Exp $
 */
@Service
public class RechargeServiceImpl implements RechargeService {

    @Autowired
    private BsSubAccountMapper bsSubAccountMapper;
    
    /** 
     * @see com.pinting.business.service.site.RechargeService#findAvailableBalanceByUserId(java.lang.Integer)
     */
    @Override
    public Double findAvailableBalanceByUserId(Integer userId) {
        return bsSubAccountMapper.selectAvailableBalanceByUserId(userId);
    }

}

/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.service.site;


/**
 * 充值
 * @author HuanXiong
 * @version $Id: RechargeService.java, v 0.1 2015-11-19 上午10:42:13 HuanXiong Exp $
 */
public interface RechargeService {

    /**
     * 查询可用余额
     * 
     * @param userId    用户ID
     * @return
     */
    public Double findAvailableBalanceByUserId(Integer userId);
    
}

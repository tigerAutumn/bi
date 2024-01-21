/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.pay19.out.service;

import com.pinting.gateway.pay19.out.model.req.QueryAcctBalanceReq;
import com.pinting.gateway.pay19.out.model.resp.QueryAcctBalanceResp;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: AcctBalanceServiceClient.java, v 0.1 2015-11-3 下午4:19:25 BabyShark Exp $
 */
public interface AcctBalanceServiceClient {

    /**
     * 查询余额
     * 
     * @param req
     * @return
     */
    QueryAcctBalanceResp queryAcctBalance(QueryAcctBalanceReq req);

}

/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.pay19.out.service.impl;

import org.springframework.stereotype.Service;

import com.pinting.gateway.pay19.out.enums.AcctBalanceUrl;
import com.pinting.gateway.pay19.out.model.req.QueryAcctBalanceReq;
import com.pinting.gateway.pay19.out.model.resp.QueryAcctBalanceResp;
import com.pinting.gateway.pay19.out.service.AcctBalanceServiceClient;
import com.pinting.gateway.pay19.out.util.Pay19HttpUtil;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: AcctBalanceServiceClientImpl.java, v 0.1 2015-11-3 下午4:22:48 BabyShark Exp $
 */
@Service("acctBalanceServiceClient")
public class AcctBalanceServiceClientImpl implements AcctBalanceServiceClient {

    /** 
     * @see com.pinting.gateway.pay19.out.service.AcctBalanceServiceClient#queryAcctBalance(com.pinting.gateway.pay19.out.model.req.QueryAcctBalanceReq)
     */
    @Override
    public QueryAcctBalanceResp queryAcctBalance(QueryAcctBalanceReq req) {
        QueryAcctBalanceResp resp = (QueryAcctBalanceResp) Pay19HttpUtil.acctBalanceSend(
            AcctBalanceUrl.QUERY_ACCT_BALANCE, req);
        return resp;
    }

}

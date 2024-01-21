/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.pay19.out.service;

import com.pinting.gateway.pay19.out.model.req.QueryRealNameVerifyReq;
import com.pinting.gateway.pay19.out.model.req.RealNameVerifyReq;
import com.pinting.gateway.pay19.out.model.resp.QueryRealNameVerifyResp;
import com.pinting.gateway.pay19.out.model.resp.RealNameVerifyResp;

/**
 * 19付实名认证接口
 * @author Baby shark love blowing wind
 * @version $Id: RealNameServiceClient.java, v 0.1 2015-8-17 下午6:54:49 BabyShark Exp $
 */
public interface RealNameServiceClient {

    /**
     * 实名认证申请
     * 
     * @param req
     * @return
     */
    public RealNameVerifyResp realNameAuth(RealNameVerifyReq req);

    /**
     * 实名认证结果查询
     * 
     * @param req
     * @return
     */
    public QueryRealNameVerifyResp queryRealNameAuth(QueryRealNameVerifyReq req);

}

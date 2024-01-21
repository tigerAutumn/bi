/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.pay19.out.service.impl;

import org.springframework.stereotype.Service;

import com.pinting.gateway.enums.PTMessageEnum;
import com.pinting.gateway.exception.PTMessageException;
import com.pinting.gateway.pay19.out.enums.RealNameRespCode;
import com.pinting.gateway.pay19.out.enums.RealNameRespStatus;
import com.pinting.gateway.pay19.out.enums.RealNameUrl;
import com.pinting.gateway.pay19.out.model.req.QueryRealNameVerifyReq;
import com.pinting.gateway.pay19.out.model.req.RealNameVerifyReq;
import com.pinting.gateway.pay19.out.model.resp.QueryRealNameVerifyResp;
import com.pinting.gateway.pay19.out.model.resp.RealNameVerifyResp;
import com.pinting.gateway.pay19.out.service.RealNameServiceClient;
import com.pinting.gateway.pay19.out.util.Pay19HttpUtil;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: RealNameServiceClientImpl.java, v 0.1 2015-8-17 下午6:57:13 BabyShark Exp $
 */
@Service("realNameServiceClient")
public class RealNameServiceClientImpl implements RealNameServiceClient {

    /** 
     * @see com.pinting.gateway.pay19.out.service.RealNameServiceClient#realNameAuth(com.pinting.gateway.pay19.out.model.req.RealNameVerifyReq)
     */
    @Override
    public RealNameVerifyResp realNameAuth(RealNameVerifyReq req) {
        RealNameVerifyResp resp = (RealNameVerifyResp) Pay19HttpUtil.realNameSend(
            RealNameUrl.REAL_NAME_VERIFY, req);
        if (!RealNameRespStatus.SUCCESS.getCode().equals(resp.getReqStatus())) {
            RealNameRespCode code = RealNameRespCode.find(resp.getRetCode());
            String errCode = resp.getRetCode();
            String errMsg = code != null ? code.getDescription() : resp.getRetCode();
            throw new PTMessageException(PTMessageEnum.RPC_EXCEPTION, errMsg + "," + errCode);
        }
        return resp;
    }

    /** 
     * @see com.pinting.gateway.pay19.out.service.RealNameServiceClient#queryRealNameAuth(com.pinting.gateway.pay19.out.model.req.QueryRealNameVerifyReq)
     */
    @Override
    public QueryRealNameVerifyResp queryRealNameAuth(QueryRealNameVerifyReq req) {
        QueryRealNameVerifyResp resp = (QueryRealNameVerifyResp) Pay19HttpUtil.realNameSend(
            RealNameUrl.QUERY_REAL_NAME_VERIFY, req);
        if (!RealNameRespStatus.SUCCESS.getCode().equals(resp.getReqStatus())) {
            RealNameRespCode code = RealNameRespCode.find(resp.getRetCode());
            String errMsg = code != null ? code.getDescription() : resp.getRetCode();
            throw new PTMessageException(PTMessageEnum.RPC_EXCEPTION, errMsg + "," + resp.getRetCode());
        }
        return resp;
    }

}

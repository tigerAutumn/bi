/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.pay19.out.service.impl;

import org.springframework.stereotype.Service;

import com.pinting.gateway.enums.PTMessageEnum;
import com.pinting.gateway.exception.PTMessageException;
import com.pinting.gateway.pay19.out.enums.NewDFStatus;
import com.pinting.gateway.pay19.out.enums.Pay4AnotherRespCode;
import com.pinting.gateway.pay19.out.enums.Pay4AnotherUrl;
import com.pinting.gateway.pay19.out.model.req.MerchantDfQueryReq;
import com.pinting.gateway.pay19.out.model.req.NewMerchantDfReq;
import com.pinting.gateway.pay19.out.model.resp.MerchantDfQueryResp;
import com.pinting.gateway.pay19.out.model.resp.NewMerchantDfResp;
import com.pinting.gateway.pay19.out.service.Pay4AnotherServiceClient;
import com.pinting.gateway.pay19.out.util.Pay19HttpUtil;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: Pay4AnotherServiceClientImpl.java, v 0.1 2015-8-11 下午7:27:35 BabyShark Exp $
 */
@Service("pay4AnotherServiceClient")
public class Pay4AnotherServiceClientImpl implements Pay4AnotherServiceClient {

    /** 
     * @see com.pinting.gateway.pay19.out.service.Pay4AnotherServiceClient#newMerchantDf(com.pinting.gateway.pay19.out.model.req.NewMerchantDfReq)
     */
    @Override
    public NewMerchantDfResp newMerchantDf(NewMerchantDfReq req) {
        NewMerchantDfResp resp = (NewMerchantDfResp) Pay19HttpUtil.pay4AnotherSend(
            Pay4AnotherUrl.NEW_MERCHANTDF, req);
        if (!(NewDFStatus.SUCCESS.getCode().equals(resp.getStatus()) && Pay4AnotherRespCode.SUCCESS_CODE_00000
            .getCode().equals(resp.getRetCode()))) {
            Pay4AnotherRespCode code = Pay4AnotherRespCode.find(resp.getRetCode());
            String errCode = resp.getRetCode();
            String errMsg = code != null ? code.getDescription() : resp.getRetCode();
            throw new PTMessageException(PTMessageEnum.RPC_EXCEPTION, errMsg + "," + errCode);
        }
        return resp;
    }

    /** 
     * @see com.pinting.gateway.pay19.out.service.Pay4AnotherServiceClient#queryMerchantDf(com.pinting.gateway.pay19.out.model.req.MerchantDfQueryReq)
     */
    @Override
    public MerchantDfQueryResp queryMerchantDf(MerchantDfQueryReq req) {
        MerchantDfQueryResp resp = (MerchantDfQueryResp) Pay19HttpUtil.pay4AnotherSend(
            Pay4AnotherUrl.MERCHANTDF_QUERY, req);
        return resp;
    }

}

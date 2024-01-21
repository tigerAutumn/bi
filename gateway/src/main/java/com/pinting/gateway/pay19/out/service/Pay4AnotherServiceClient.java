/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.pay19.out.service;

import com.pinting.gateway.pay19.out.model.req.MerchantDfQueryReq;
import com.pinting.gateway.pay19.out.model.req.NewMerchantDfReq;
import com.pinting.gateway.pay19.out.model.resp.MerchantDfQueryResp;
import com.pinting.gateway.pay19.out.model.resp.NewMerchantDfResp;

/**
 * 19付银行代付接口
 * @author Baby shark love blowing wind
 * @version $Id: Pay4Another.java, v 0.1 2015-8-11 下午1:47:57 BabyShark Exp $
 */
public interface Pay4AnotherServiceClient {

    /**
     * 银行代付下单
     * 
     * @param req
     * @return
     */
    public NewMerchantDfResp newMerchantDf(NewMerchantDfReq req);

    /**
     * 银行代付查询
     * 
     * @param req
     * @return
     */
    public MerchantDfQueryResp queryMerchantDf(MerchantDfQueryReq req);

}

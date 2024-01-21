/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.pay19.out.service;

import com.pinting.gateway.pay19.out.model.req.ConfirmOrderReq;
import com.pinting.gateway.pay19.out.model.req.PreOrderReq;
import com.pinting.gateway.pay19.out.model.req.QueryBankCardListReq;
import com.pinting.gateway.pay19.out.model.req.QueryBankListReq;
import com.pinting.gateway.pay19.out.model.req.QueryMOrderReq;
import com.pinting.gateway.pay19.out.model.req.RSendSmsReq;
import com.pinting.gateway.pay19.out.model.req.UnBindCardReq;
import com.pinting.gateway.pay19.out.model.resp.ConfirmOrderResp;
import com.pinting.gateway.pay19.out.model.resp.PreOrderResp;
import com.pinting.gateway.pay19.out.model.resp.QueryBankCardListResp;
import com.pinting.gateway.pay19.out.model.resp.QueryBankListResp;
import com.pinting.gateway.pay19.out.model.resp.QueryMOrderResp;
import com.pinting.gateway.pay19.out.model.resp.RSendSmsResp;
import com.pinting.gateway.pay19.out.model.resp.UnBindCardResp;

/**
 * 19付快捷支付接口
 * @author Baby shark love blowing wind
 * @version $Id: Pay19QuickPayService.java, v 0.1 2015-8-6 下午3:32:57 BabyShark Exp $
 */
public interface QuickPayServiceClient {

    /**
     * 预下单
     * 
     * @param req
     * @return
     */
    public PreOrderResp preOrder(PreOrderReq req);

    /**
     * 确认支付
     * 
     * @param req
     * @return
     */
    public ConfirmOrderResp confirmOrder(ConfirmOrderReq req);

    /**
     * 重发短信
     * 
     * @param req
     * @return
     */
    public RSendSmsResp rSendSms(RSendSmsReq req);

    /**
     * 查询订单
     * 
     * @param req
     * @return
     */
    public QueryMOrderResp queryMOrder(QueryMOrderReq req);

    /**
     * 查询银行列表
     * 
     * @param req
     * @return
     */
    public QueryBankListResp queryBankList(QueryBankListReq req);

    /**
     * 查询银行卡列表
     * 
     * @param req
     * @return
     */
    public QueryBankCardListResp queryBankCardList(QueryBankCardListReq req);

    /**
     * 解绑快捷银行卡
     * 
     * @param req
     * @return
     */
    public UnBindCardResp unBindCard(UnBindCardReq req);

}

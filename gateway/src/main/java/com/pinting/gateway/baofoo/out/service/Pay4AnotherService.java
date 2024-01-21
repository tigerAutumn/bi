package com.pinting.gateway.baofoo.out.service;

import com.pinting.gateway.baofoo.out.model.req.*;
import com.pinting.gateway.baofoo.out.model.resp.*;
import com.pinting.gateway.baofoo.out.util.PaymentChannelInfo;

/**
 * Created by 剑钊 on 2016/7/21.
 */
public interface Pay4AnotherService {

    /**
     * 代付转账交易接口
     * @param req
     * @return
     * @throws Exception
     */
    Pay4AnotherResp<Pay4AnotherTransResp> trans (Pay4AnotherTransReq req) throws Exception;

    /**
     * 合作方代付转账交易接口
     * @param req
     * @param partner 合作方代码
     * @return
     * @throws Exception
     */
    Pay4AnotherResp<Pay4AnotherTransResp> partnerTrans (Pay4AnotherTransReq req,String partner) throws Exception;

    /**
     * 代付转账状态查询
     * @param req
     * @return
     * @throws Exception
     */
    Pay4AnotherResp<Pay4AnotherTransStatusQueryResp> transStatusQuery (Pay4AnotherTransStatusQueryReq req) throws Exception;


    /**
     * 合作方代付转账状态查询
     * @param req
     * @return
     * @throws Exception
     */
    Pay4AnotherResp<Pay4AnotherTransStatusQueryResp> transStatusQuery (Pay4AnotherTransStatusQueryReq req,String partner) throws Exception;

    /**
     * 退款查询
     * @param req
     * @return
     * @throws Exception
     */
    Pay4AnotherResp<Pay4AnotherRefundQueryResp> refundQuery(Pay4AnotherRefundQueryReq req) throws Exception;

    /**
     * 白名单申请接口
     * @param req
     * @return
     * @throws Exception
     */
    Pay4AnotherResp<Pay4AnotherApplyWhiteListResp> applyWhiteList(Pay4AnotherApplyWhiteListReq req) throws Exception;

    /**
     * 宝付账号间转账
     * @param req
     * @return
     * @throws Exception
     */
    Pay4AnotherResp<Pay4AnotherOnlineTransResp> onlineTrans(Pay4AnotherOnlineTransReq req) throws Exception;
    
    
    /**
     * 宝付账户（商户号）之间转账，转出方不同
     * @param req
     * @param channelInfo
     * @return
     * @throws Exception
     */
    Pay4AnotherResp<Pay4AnotherOnlineTransResp> onlineTrans4DiffChannel(Pay4AnotherOnlineTransReq req, PaymentChannelInfo channelInfo) throws Exception;
    
}

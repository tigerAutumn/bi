package com.pinting.gateway.baofoo.out.service;

import com.pinting.gateway.baofoo.out.model.req.PrepareQuickPayReq;
import com.pinting.gateway.baofoo.out.model.req.QuickPayReq;
import com.pinting.gateway.baofoo.out.model.req.QuickPayStatusReq;
import com.pinting.gateway.baofoo.out.model.resp.PrepareQuickPayResp;
import com.pinting.gateway.baofoo.out.model.resp.QuickPayResp;
import com.pinting.gateway.baofoo.out.model.resp.QuickPayStatusResp;

/**
 * Created by 剑钊 on 2016/7/19.
 * 认证支付接口
 */
public interface AttestationPayService {

    /**
     * 预支付类交易
     * @param req
     * @return
     */
    PrepareQuickPayResp prepareQuickPay(PrepareQuickPayReq req) throws Exception;

    /**
     * 确认支付类交易
     * @param req
     * @return
     * @throws Exception
     */
    QuickPayResp quickPay(QuickPayReq req) throws Exception;

    /**
     * 交易状态查询类交易
     * @param req
     * @return
     * @throws Exception
     */
    QuickPayStatusResp quickPayStatusQuery(QuickPayStatusReq req) throws Exception;
}

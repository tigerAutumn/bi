package com.pinting.gateway.baofoo.out.service;

import com.pinting.gateway.baofoo.out.model.req.PcPayGateReq;
import com.pinting.gateway.baofoo.out.model.req.PcStatusQueryReq;
import com.pinting.gateway.baofoo.out.model.resp.PcStatusQueryResp;

/**
 * Created by 剑钊 on 2016/7/22.
 */
public interface NewCounterService {

    /**
     * 宝付网银支付
     * @param req
     * @return
     */
    String pcPayGate(PcPayGateReq req);

    /**
     * 宝付网银支付状态查询
     * @param req
     * @return
     */
    PcStatusQueryResp pcStatusQuery(PcStatusQueryReq req) throws Exception;
}

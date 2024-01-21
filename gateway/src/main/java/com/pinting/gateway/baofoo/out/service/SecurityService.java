package com.pinting.gateway.baofoo.out.service;

import com.pinting.gateway.baofoo.out.model.req.CardBinQueryReq;
import com.pinting.gateway.baofoo.out.model.resp.CardBinQueryResp;

/**
 * Created by 剑钊 on 2016/8/24.
 */
public interface SecurityService {

    /**
     * 查询卡bin信息
     * @param req
     * @return
     * @throws Exception
     */
    CardBinQueryResp queryCardBin(CardBinQueryReq req) throws Exception;
}

package com.pinting.gateway.baofoo.out.service;

import com.pinting.gateway.baofoo.out.model.req.BalanceQueryReq;
import com.pinting.gateway.baofoo.out.model.req.CheckAccountFileReq;
import com.pinting.gateway.baofoo.out.model.resp.BalanceQueryResp;
import com.pinting.gateway.baofoo.out.model.resp.BalanceResp;
import com.pinting.gateway.baofoo.out.model.resp.Pay4AnotherResp;

/**
 * Created by 剑钊 on 2016/7/25.
 *
 */
public interface AccountService {

    void downloadCheckAccountFile(CheckAccountFileReq req) throws Exception;

    /**
     * 查询余额
     * @param req
     */
    BalanceResp<BalanceQueryResp> queryBalance(BalanceQueryReq req) throws Exception;
}

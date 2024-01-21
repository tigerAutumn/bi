package com.pinting.gateway.baofoo.out.service.impl;

import com.pinting.gateway.baofoo.out.model.req.PcPayGateReq;
import com.pinting.gateway.baofoo.out.model.req.PcStatusQueryReq;
import com.pinting.gateway.baofoo.out.model.resp.PcStatusQueryResp;
import com.pinting.gateway.baofoo.out.service.NewCounterService;
import com.pinting.gateway.baofoo.out.util.BaoFooHttpUtil;
import org.springframework.stereotype.Service;

/**
 * Created by 剑钊 on 2016/7/22.
 */
@Service
public class NewCounterServiceImpl implements NewCounterService {


    @Override
    public String pcPayGate(PcPayGateReq req) {

        return BaoFooHttpUtil.requestForm(req);
    }

    @Override
    public PcStatusQueryResp pcStatusQuery(PcStatusQueryReq req) throws Exception {

        String[] respStrs=BaoFooHttpUtil.requestForm(req);
        PcStatusQueryResp resp1=new PcStatusQueryResp();
        resp1.setTransID(respStrs[2]);
        resp1.setCheckResult(respStrs[3]);
        resp1.setSuccMoney(respStrs[4]);
        resp1.setSuccTime(respStrs[5]);
        return resp1;
    }
}

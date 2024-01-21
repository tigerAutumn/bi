package com.pinting.gateway.business.out.service.impl;

import com.pinting.core.hessian.service.HessianService;
import com.pinting.gateway.business.out.service.BaoFooSendBusinessService;
import com.pinting.gateway.hessian.message.baofoo.G2BReqMsg_BaoFooPay_NewCounterResultNotice;
import com.pinting.gateway.hessian.message.baofoo.G2BResMsg_BaoFooPay_NewCounterResultNotice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by 剑钊 on 2016/8/16.
 */
@Service
public class BaoFooSendBusinessServiceImpl implements BaoFooSendBusinessService {

    @Autowired
    @Qualifier("gatewayBaoFooService")
    private HessianService gatewayBaoFooService;


    @Override
    public G2BResMsg_BaoFooPay_NewCounterResultNotice sendNewCounterResultNotice(G2BReqMsg_BaoFooPay_NewCounterResultNotice req) {

        G2BResMsg_BaoFooPay_NewCounterResultNotice res=(G2BResMsg_BaoFooPay_NewCounterResultNotice)gatewayBaoFooService.handleMsg(req);
        return res;
    }
}

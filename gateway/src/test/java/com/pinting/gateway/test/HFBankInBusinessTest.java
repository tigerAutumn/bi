package com.pinting.gateway.test;

import com.pinting.gateway.hfbank.in.model.GatewayRechargeNoticeReqModel;
import com.pinting.gateway.hfbank.in.model.OutOfAccountReqModel;
import com.pinting.gateway.hfbank.in.service.HfbankInService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author:      cyb
 * Date:        2017/4/13
 * Description:
 */
public class HFBankInBusinessTest extends TestBase {

    @Autowired
    private HfbankInService hfbankInService;

    @Test
    public void gatewayRechargeNoticeTest() {
        OutOfAccountReqModel req = new OutOfAccountReqModel();
        req.setOut_amt("100.0000");
        req.setOrder_status("1");
        req.setOrder_no("9afd652abe8872457273");

        hfbankInService.outOfAccount(req);
    }
}

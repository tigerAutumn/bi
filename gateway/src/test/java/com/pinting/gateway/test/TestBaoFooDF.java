package com.pinting.gateway.test;

import com.pinting.gateway.baofoo.out.model.req.Pay4AnotherTransReq;
import com.pinting.gateway.baofoo.out.model.req.Pay4AnotherTransStatusQueryReq;
import com.pinting.gateway.baofoo.out.service.Pay4AnotherService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class TestBaoFooDF extends TestBase {
    @Autowired
    Pay4AnotherService pay4AnotherService;

    @Test
    public void testDF() throws Exception {
        Pay4AnotherTransReq req1 = new Pay4AnotherTransReq();
        req1.setTo_acc_no("6214835892879857");
        req1.setTo_acc_name("袁其亮");
        req1.setTo_bank_name("招商银行");
        req1.setTrans_money("1000.00");
        req1.setTrans_no("35022534164547590");
        req1.setTrans_card_id("身份证号");
        req1.setTrans_mobile("预留手机号");
        //pay4AnotherService.trans(req1);
        pay4AnotherService.partnerTrans(req1, "ZAN");
    }

    @Test
    public void testDFQuery() throws Exception {

        Pay4AnotherTransStatusQueryReq req=new Pay4AnotherTransStatusQueryReq();
        req.setTrans_no("35022534164547590");
        req.setTrans_batchid("31569280");
        try {
            pay4AnotherService.transStatusQuery(req);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

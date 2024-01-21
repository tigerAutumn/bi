package com.pinting.business.test.dao;

import com.pinting.business.test.TestBase;
import com.pinting.gateway.hessian.message.hfbank.*;
import com.pinting.gateway.out.service.HfbankTransportService;
import net.sf.json.JSONObject;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.Date;
/**
 * Author:      yed
 * Date:        2017/4/19
 * Description: 恒丰银行测试类
 */
public class TestHFBank_PlatCharge extends TestBase {

    private Logger logger = LoggerFactory.getLogger(TestHFBank_PlatCharge.class);

    @Autowired
    private HfbankTransportService hfbankTransportService;

    /*平台充值*/
    @Test
    public void platWithDrawTest() {
        B2GReqMsg_HFBank_PlatCharge req = new B2GReqMsg_HFBank_PlatCharge();
        req.setOrder_no("PT_CZ_20170419_001");
        req.setPartner_trans_date(new Date());
        req.setPartner_trans_time(new Date());
        req.setAmount(10000d);
        req.setNotify_url(null);
        req.setRemark("平台充值测试");
        B2GResMsg_HFBank_PlatCharge res = hfbankTransportService.platCharge(req);
        logger.info("恒丰银行测试平台充值：{}", JSONObject.fromObject(res));
    }
}
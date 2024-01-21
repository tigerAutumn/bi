package com.pinting.business.test.zsd;

import com.pinting.business.test.TestBase;
import com.pinting.gateway.hessian.message.zsd.B2GReqMsg_ZsdNotice_NoticeBankLimit;
import com.pinting.gateway.hessian.message.zsd.B2GResMsg_ZsdNotice_NoticeBankLimit;
import com.pinting.gateway.hessian.message.zsd.model.BankLimit;
import com.pinting.gateway.hessian.message.zsd.model.Limit;
import com.pinting.gateway.out.service.zsd.ZsdNoticeService;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author:      shh
 * Date:        2017/8/31
 * Description: 推送银行卡限额 测试类
 */
public class TestNoticeBankLimit extends TestBase {
    private Logger logger = LoggerFactory.getLogger(TestLoanNotice.class);

    @Autowired
    private ZsdNoticeService zsdNoticeService;

    @Test
    public void noticeBankLimitTest() {
        B2GReqMsg_ZsdNotice_NoticeBankLimit req = new B2GReqMsg_ZsdNotice_NoticeBankLimit();

        List<BankLimit> limits = new ArrayList<BankLimit>();
        BankLimit bankLimit = new BankLimit();
        bankLimit.setPayType("QUICK");

        Map<String, Limit> limitmap = new HashMap<String, Limit>();
        Limit limit = new Limit();
        limit.setDay("100000");
        limit.setSingle("50000");
        limit.setBankName("农业银行");
        limitmap.put("ABC", limit);

        bankLimit.setBankLimits(limitmap);
        limits.add(bankLimit);

        req.setLimits(limits);

        B2GResMsg_ZsdNotice_NoticeBankLimit res = zsdNoticeService.noticeBankLimit(req);
    }

}

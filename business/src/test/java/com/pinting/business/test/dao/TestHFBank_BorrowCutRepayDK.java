package com.pinting.business.test.dao;

import com.pinting.business.test.TestBase;
import com.pinting.gateway.hessian.message.hfbank.*;
import com.pinting.gateway.hessian.message.hfbank.model.*;
import com.pinting.gateway.out.service.HfbankTransportService;
import net.sf.json.JSONObject;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * Author:      cyb
 * Date:        2017/4/19
 * Description: 恒丰银行测试类
 */
public class TestHFBank_BorrowCutRepayDK extends TestBase {

    private Logger logger = LoggerFactory.getLogger(TestHFBank_BorrowCutRepayDK.class);

    @Autowired 
    private HfbankTransportService hfbankTransportService;

    /*借款人扣款还款（代扣）测试环境无法测试*/
    @Test
    public void borrowCutRepayDKTest() {
        B2GReqMsg_HFBank_BorrowCutRepayDK req = new B2GReqMsg_HFBank_BorrowCutRepayDK();
        req.setOrder_no("BORROW_CS_201704190003");
        req.setPartner_trans_date(new Date());
        req.setPartner_trans_time(new Date());
        req.setAmt(10000d);
        req.setNotify_url(null);//从系统配置文件中获取
        req.setRemark("借款人还款代扣测试");
        List<BorrowCutRepayPlatcust> data = new ArrayList<BorrowCutRepayPlatcust>();
        BorrowCutRepayPlatcust borrowCutRepayPlatcust = new BorrowCutRepayPlatcust();
        borrowCutRepayPlatcust.setPlatcust("2017041813372262011104");
        borrowCutRepayPlatcust.setAmt(10000d);
        data.add(borrowCutRepayPlatcust);
        req.setPlatcustList(data);
        B2GResMsg_HFBank_BorrowCutRepayDK res = hfbankTransportService.borrowCutRepayDK(req);
        logger.info("恒丰银行测试借款人扣款还款（代扣）：{}", JSONObject.fromObject(res));
    }
}
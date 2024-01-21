package com.pinting.business.test.dao;

import com.pinting.business.test.TestBase;
import com.pinting.gateway.hessian.message.hfbank.*;
import com.pinting.gateway.out.service.HfbankTransportService;
import net.sf.json.JSONObject;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author:      yed
 * Date:        2017/4/19
 * Description: 恒丰银行测试类
 */
public class TestHFBank_RepayCompensate extends TestBase {

    private Logger logger = LoggerFactory.getLogger(TestHFBank_RepayCompensate.class);

    @Autowired
    private HfbankTransportService hfbankTransportService;
    
    /* 借款人还款代偿(委托)*/
    @Test
    public void repayCompensateTest() {
    	
    	B2GReqMsg_HFBank_RepayCompensate req = new B2GReqMsg_HFBank_RepayCompensate();
    	req.setOrder_no("PROD_DC_20170419002");
    	req.setProd_id("BGW_CS_PRD_1");
    	req.setRepay_amt(1000d);
    	req.setPlatcust("2017041813372262011104");
    	req.setCompensation_platcust("01");	//代偿人平台客户编号(或者代偿账户编号)
    	req.setRemark("标的代偿（委托）还款测试");

    	B2GResMsg_HFBank_RepayCompensate res = hfbankTransportService.repayCompensate(req);
      	logger.info("恒丰银行测试标的代偿（委托）还款：{}", JSONObject.fromObject(res));
    }
}
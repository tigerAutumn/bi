package com.pinting.business.test.dao;

import com.pinting.business.test.TestBase;
import com.pinting.business.util.Constants;
import com.pinting.business.util.Util;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.hessian.message.hfbank.*;
import com.pinting.gateway.hessian.message.hfbank.model.*;
import com.pinting.gateway.out.service.HfbankTransportService;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Author:      yed
 * Date:        2017/4/18
 * Description: 恒丰银行测试类
 */
public class TestHFBank_Compensate extends TestBase {

    private Logger logger = LoggerFactory.getLogger(TestHFBank_Compensate.class);

    @Autowired
    private HfbankTransportService hfbankTransportService;
    
    /*标的代偿（委托）还款*/
    @Test
    public void repayCompensateTest() {
    	
    	B2GReqMsg_HFBank_CompensateRepay req = new B2GReqMsg_HFBank_CompensateRepay();
    	req.setOrder_no("PROD_DC_20170419002");
    	req.setProd_id("BGW_CS_PRD_1");
    	req.setRepay_num(1);			//还款期数,如果一次性还款,repay_num为1 
    	req.setRepay_date(new Date());
    	req.setRepay_amt(1000d);
    	req.setReal_repay_date(new Date());
    	req.setReal_repay_amt(1000d);
    	req.setPlatcust("2017041813372262011104");
    	req.setCompensation_platcust("01");	//代偿人平台客户编号(或者代偿账户编号)
    	req.setTrans_amt(1000d);
    	req.setFee_amt(0d);
    	req.setRepay_type("0");		//还款类型 0-代偿还款 1-委托还款  
    	req.setRemark("标的代偿（委托）还款测试");

    	B2GResMsg_HFBank_CompensateRepay res = hfbankTransportService.compensateRepay(req);
      	logger.info("恒丰银行测试标的代偿（委托）还款：{}", JSONObject.fromObject(res));
    }
}
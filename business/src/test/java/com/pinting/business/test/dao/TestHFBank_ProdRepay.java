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
public class TestHFBank_ProdRepay extends TestBase {

    private Logger logger = LoggerFactory.getLogger(TestHFBank_ProdRepay.class);

    @Autowired
    private HfbankTransportService hfbankTransportService;
    
    /*标的还款*/
    @Test
    public void prodRepayTest() {
    	B2GReqMsg_HFBank_ProdRepay req = new B2GReqMsg_HFBank_ProdRepay();
    	req.setProd_id("BGW_CS_PRD_1");	//BGW_CS_PRD_2 
    	req.setRepay_num(1);			//还款期数,如果一次性还款,repay_num为1 
      	req.setIs_payoff("0");			//是否整个标的还清:0-是,1-否; 
      	req.setTrans_amt(1000.11d);	
      	req.setRepay_flag("0");			//本期已还清：0-是，1-否
      	ProdRepayReqFundData prodRepayReqFundData = new ProdRepayReqFundData();
      	
      	List<ProdRepayReqFundDataCustRepay> custRepayList = new ArrayList<ProdRepayReqFundDataCustRepay>();
      	
      	ProdRepayReqFundDataCustRepay prodRepayReqFundDataCustRepay = new ProdRepayReqFundDataCustRepay();
      	prodRepayReqFundDataCustRepay.setReal_repay_amt("1000");
      	prodRepayReqFundDataCustRepay.setReal_repay_amount("900");
      	prodRepayReqFundDataCustRepay.setExperience_amt("0");
      	prodRepayReqFundDataCustRepay.setRates_amt("0");
      	prodRepayReqFundDataCustRepay.setReal_repay_val("100");
      	prodRepayReqFundDataCustRepay.setRepay_fee("0");
      	prodRepayReqFundDataCustRepay.setCust_no("2017041512511533611032");
      	prodRepayReqFundDataCustRepay.setRepay_num("1");
      	prodRepayReqFundDataCustRepay.setRepay_date(DateUtil.formatYYYYMMDD(new Date()));
      	prodRepayReqFundDataCustRepay.setReal_repay_date(DateUtil.formatYYYYMMDD(new Date()));
      	custRepayList.add(prodRepayReqFundDataCustRepay);
      	prodRepayReqFundData.setCustRepayList(custRepayList);
      	System.out.println("JsonObject=["+JSONObject.fromObject(com.alibaba.fastjson.JSONObject.toJSONString(prodRepayReqFundData))+"]");
      	req.setFunddata(prodRepayReqFundData);
      	
      	req.setRemark("标的还款测试");
      	req.setOrder_no("PRODREPAY_201704180013");
      	req.setPartner_trans_date(new Date());
      	req.setPartner_trans_time(new Date());
      	B2GResMsg_HFBank_ProdRepay res = hfbankTransportService.prodRepay(req);
      	logger.info("恒丰银行测试标的还款：{}", JSONObject.fromObject(res));
    }
}
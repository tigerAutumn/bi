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
 * Author:      cyb
 * Date:        2017/4/3
 * Description: 恒丰银行测试类
 */
public class TestHFBank_UserGatewayRechargeRequest extends TestBase {

    private Logger logger = LoggerFactory.getLogger(TestHFBank_UserGatewayRechargeRequest.class);

    @Autowired 
    private HfbankTransportService hfbankTransportService;

    /*网关充值请求*/
    @Test
    public void userGatewayRechargeRequestTest() {
        B2GReqMsg_HFBank_UserGatewayRechargeRequest req = new B2GReqMsg_HFBank_UserGatewayRechargeRequest();
        
        req.setOrder_no("CZ_20170419001");
        req.setPartner_trans_date(new Date());
        req.setPartner_trans_time(new Date());
        req.setPlatcust("2017041813372262011104");
        req.setType("1");			//充值类型(1-用户充值)
        req.setCharge_type("1");	//1-投资账户  2-融资账户  
        req.setBankcode("");
        req.setCard_type("");
        req.setCurrency_code("");
        req.setCard_no("6222021203029387056");
        req.setAmt(10000d);
        req.setReturn_url(null);	//同步回调地址
        req.setNotify_url(null);	//异步通知地址
        req.setPay_code(Constants.CHANNEL_BAOFOO);
        req.setRemark("测试网关充值");
        
        B2GResMsg_HFBank_UserGatewayRechargeRequest res = hfbankTransportService.userGatewayRechargeRequest(req);
        logger.info("恒丰银行测试网关充值请求：{}", JSONObject.fromObject(res));
    }
}
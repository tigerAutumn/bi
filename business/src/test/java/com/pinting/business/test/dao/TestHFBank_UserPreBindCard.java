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
 * Author:      YED
 * Date:        2017/4/18
 * Description: 恒丰银行测试类
 */
public class TestHFBank_UserPreBindCard extends TestBase {

    private Logger logger = LoggerFactory.getLogger(TestHFBank_UserPreBindCard.class);

    @Autowired
    private HfbankTransportService hfbankTransportService;

    /*短验绑卡申请*/
    @Test
    public void userPreBindCardTest() {
        B2GReqMsg_HFBank_UserPreBindCard req = new B2GReqMsg_HFBank_UserPreBindCard();
        
        req.setPlatcust("2017041813372262011104");	//平台客户编号:	已实名认证用户,参数必填 
        
        req.setId_type("1");	//证件类型:(1:身份证) 未实名认证用户,参数必填 
        req.setId_code("336327198806253496");		//证件号码:未实名认证用户,参数必填 
        req.setName("Gemma");	
        
        req.setCard_no("6222021203029387056");
        req.setCard_type("1");		//卡类型(1:借记卡 2:信用卡)
        req.setPre_mobile("15168487916");
        req.setPay_code(Constants.CHANNEL_HFBANK);
        req.setRemark("测试用户绑卡申请请求");
        
        req.setOrder_no("YED_BINDCARD_20170418003");
        req.setPartner_trans_date(new Date());
        req.setPartner_trans_time(new Date());
        B2GResMsg_HFBank_UserPreBindCard res = hfbankTransportService.userPreBindCard(req);
        logger.info("恒丰银行测试短验绑卡申请：{}", JSONObject.fromObject(res));
    }

    /*短验绑卡确认*/
//    @Test
//    public void userBindCardTest() {
//        B2GReqMsg_HFBank_UserBindCard req = new B2GReqMsg_HFBank_UserBindCard();
//        req.setOrder_no(UUID.randomUUID().toString());
//        req.setPartner_trans_date(new Date());
//        req.setPartner_trans_time(new Date());
//        req.setPlatcust(UUID.randomUUID().toString());
//        req.setId_type("");
//        req.setId_code("");
//        req.setName("");
//        req.setCard_no("6228480321031877111");
//        req.setCard_type("");
//        req.setPre_mobile("13575759159");
//        req.setPay_code("BAOFOO");
//        req.setIdentifying_code("1234");
//        req.setOrigin_order_no(UUID.randomUUID().toString());
//        req.setRemark("");
//        B2GResMsg_HFBank_UserBindCard res = hfbankTransportService.userBindCard(req);
//        logger.info("恒丰银行测试短验绑卡确认：{}", JSONObject.fromObject(res));
//    }
}
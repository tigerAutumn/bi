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
public class TestHFBank_UserBindCard extends TestBase {

    private Logger logger = LoggerFactory.getLogger(TestHFBank_UserBindCard.class);

    @Autowired
    private HfbankTransportService hfbankTransportService;

    /*短验绑卡确认*/
    @Test
    public void userBindCardTest() {
        B2GReqMsg_HFBank_UserBindCard req = new B2GReqMsg_HFBank_UserBindCard();
        req.setOrder_no("YED_BINDCARD_CHK20170418003");
        req.setPartner_trans_date(new Date());
        req.setPartner_trans_time(new Date());
        req.setPlatcust("2017041813372262011104");
        req.setId_type("1");
        req.setId_code("336327198806253496");
        req.setName("Gemma");
        req.setCard_no("6222021203029387056");
        req.setCard_type("1");
        req.setPre_mobile("15168487916");
        req.setPay_code(Constants.CHANNEL_HFBANK);
        req.setIdentifying_code("123456");
        req.setOrigin_order_no("YED_BINDCARD_20170418003");
        req.setRemark("测试绑卡确认验证");
        B2GResMsg_HFBank_UserBindCard res = hfbankTransportService.userBindCard(req);
        logger.info("恒丰银行测试短验绑卡确认：{}", JSONObject.fromObject(res));
    }
}
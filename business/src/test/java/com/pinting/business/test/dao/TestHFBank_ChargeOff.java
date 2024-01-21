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
 * Date:        2017/4/17
 * Description: 恒丰银行测试类
 */
public class TestHFBank_ChargeOff extends TestBase {

    private Logger logger = LoggerFactory.getLogger(TestHFBank_ChargeOff.class);
    
    @Autowired
    private HfbankTransportService hfbankTransportService;

    /*标的出账*/
    @Test
    public void chargeOffTest() {
        B2GReqMsg_HFBank_ChargeOff req = new B2GReqMsg_HFBank_ChargeOff();
        req.setProd_id("BGW_CS_PRD_2");
        List<ChargeOffReqDetail> list = new ArrayList<ChargeOffReqDetail>();
        ChargeOffReqDetail chargeOffReqDetail = new ChargeOffReqDetail();
        chargeOffReqDetail.setIs_advance(Constants.HF_WITHDRAW_IS_ADVANCE_YES);	//2:垫付 1:不垫付 
        chargeOffReqDetail.setPlatcust(UUID.randomUUID().toString());
        chargeOffReqDetail.setOut_amt("5000.00");
        chargeOffReqDetail.setOpen_branch("农业银行");
        chargeOffReqDetail.setWithdraw_account("6228480321031877111");
        chargeOffReqDetail.setPayee_name("舒煌辉");
        chargeOffReqDetail.setBank_id("");			//银行编号（对公出账必填）
        chargeOffReqDetail.setClient_property(Constants.HF_CLIENT_PROPERTY_PERSON);	//(0-个人 1-公司)个人仅允许出账至融资人本人
        chargeOffReqDetail.setCity_code("");		//城市编码(富友必填)
        list.add(chargeOffReqDetail);
        req.setCharge_off_list(list);

        req.setNotify_url("http://183.129.222.138:8083/gateway/remote/hfbank/business");
        req.setPay_code("219");
        req.setRemark("");
        req.setOrder_no("YED_CS2017417_ChargeOff002");
        req.setPartner_trans_date(new Date());
        req.setPartner_trans_time(new Date());
        B2GResMsg_HFBank_ChargeOff res = hfbankTransportService.chargeOff(req);
        logger.info("恒丰银行测试标的出账：{}", JSONObject.fromObject(res));
    }
}
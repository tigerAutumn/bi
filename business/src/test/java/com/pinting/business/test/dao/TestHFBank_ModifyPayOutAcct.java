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
import java.util.UUID;

/**
 * Author:      yed
 * Date:        2017/4/18
 * Description: 恒丰银行测试类
 */
public class TestHFBank_ModifyPayOutAcct extends TestBase {

    private Logger logger = LoggerFactory.getLogger(TestHFBank_ModifyPayOutAcct.class);

    @Autowired
    private HfbankTransportService hfbankTransportService;

    /*标的出账信息修改*/
    @Test
    public void modifyPayOutAcctTest() {
        B2GReqMsg_HFBank_ModifyPayOutAcct req = new B2GReqMsg_HFBank_ModifyPayOutAcct();
        req.setProd_id("BGW_CS_PRD_1");
        req.setOpen_branch("");
        req.setWithdraw_account("");
        req.setAccount_type("1");	//(1-个人 2-企业)
        req.setPayee_name("杨恩德");
        req.setRemark("标的出账信息修改");
        req.setOrder_no("MODIFY_20140418001");
        req.setPartner_trans_date(new Date());
        req.setPartner_trans_time(new Date());
        B2GResMsg_HFBank_ModifyPayOutAcct res = hfbankTransportService.modifyPayOutAcct(req);
        logger.info("恒丰银行测试标的出账信息修改：{}", JSONObject.fromObject(res));
    }
}
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
public class TestHFBank_PlatFormAccountConverse extends TestBase {

    private Logger logger = LoggerFactory.getLogger(TestHFBank_PlatFormAccountConverse.class);

    @Autowired
    private HfbankTransportService hfbankTransportService;

    /*平台不同账户转账*/
    @Test
    public void platformAccountConverseTest() {
        B2GReqMsg_HFBank_PlatformAccountConverse req = new B2GReqMsg_HFBank_PlatformAccountConverse();
        req.setSource_account("1");	//来源账户(平台子账户：1-自有子账户；3手续费子账户；4-体验金子账户；5-抵用金子账户；6-加息金子账户；7-保证金子账户)
        req.setAmt(10005d);
        req.setDest_account("4");	//目标账户(平台子账户：1-自有子账户；3手续费子账户；4-体验金子账户；5-抵用金子账户；6-加息金子账户；7-保证金子账户)
        req.setRemark("测试平台内部户之间转账");
        req.setOrder_no("PTAC_20170419_003");
        req.setPartner_trans_date(new Date());
        req.setPartner_trans_time(new Date());
        
        B2GResMsg_HFBank_PlatformAccountConverse res = hfbankTransportService.platformAccountConverse(req);
        logger.info("恒丰银行测试平台不同账户转账：{}", JSONObject.fromObject(res));
    }

}

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
public class TestHFBank_PlatFormTransfer extends TestBase {

    private Logger logger = LoggerFactory.getLogger(TestHFBank_PlatFormTransfer.class);

    @Autowired
    private HfbankTransportService hfbankTransportService;

    /*平台转账个人*/
    @Test
    public void platformTransferTest() {
        B2GReqMsg_HFBank_PlatformTransfer req = new B2GReqMsg_HFBank_PlatformTransfer();
        
        req.setPlat_account("01");	/**平台账户(01 为平台账户，从平台自有资金转账)*/
        req.setAmount("100.00");
        req.setPlatcust("2017041813372262011104");
        req.setCause_type("1");	//1.奖励金发放:用户参与运营活动应获得奖励金XX元---活动奖励 2.奖励金发放:用户推荐好友投资行为应获得奖励金XX元---推荐奖励
        						//3.手续费返还:用户投资手续费返还XX元  4.现金红包:用户参与运营活动应获得现金红包XX元
        req.setRemark("平台转账个人测试");
        req.setOrder_no("PT_ZZ_20170419_002");
        req.setPartner_trans_date(new Date());
        req.setPartner_trans_time(new Date());
        
        B2GResMsg_HFBank_PlatformTransfer res = hfbankTransportService.platformTransfer(req);
        logger.info("恒丰银行测试平台转账个人：{}", JSONObject.fromObject(res));
        
    }
}
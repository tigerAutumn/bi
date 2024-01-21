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
public class TestHFBank_BatchExtBuy extends TestBase {

    private Logger logger = LoggerFactory.getLogger(TestHFBank_BatchExtBuy.class);

    @Autowired
    private HfbankTransportService hfbankTransportService;

    /*批量投标*/
    @Test
    public void batchExtBuyTest() {
        B2GReqMsg_HFBank_BatchExtBuy req = new B2GReqMsg_HFBank_BatchExtBuy();
        req.setTotal_num(2);
        req.setProd_id("BGW_CS_PRD_5");
        
        List<BatchExtBuyReqData> list = new ArrayList<BatchExtBuyReqData>();
        BatchExtBuyReqData batchExtBuyReqData = new BatchExtBuyReqData();
        batchExtBuyReqData.setDetail_no("BUY_20170420006");
        batchExtBuyReqData.setPlatcust("2017041813372262011104");
        batchExtBuyReqData.setTrans_amt(40000d);
//        batchExtBuyReqData.setExperience_amt(0d);
//        batchExtBuyReqData.setCoupon_amt(0d);
        batchExtBuyReqData.setSelf_amt(40000d);
//        batchExtBuyReqData.setIn_interest(0d);
        batchExtBuyReqData.setSubject_priority("0");

        List<BatchExtBuyReqCommission> commissionList = new ArrayList<BatchExtBuyReqCommission>();
        BatchExtBuyReqCommission batchExtBuyReqCommission = new BatchExtBuyReqCommission();
        batchExtBuyReqCommission.setPayout_plat_type("01");
        batchExtBuyReqCommission.setPayout_amt("10");
        commissionList.add(batchExtBuyReqCommission);
        batchExtBuyReqData.setCommission(commissionList);

        batchExtBuyReqData.setRemark("批量购买测试1");
        
        BatchExtBuyReqData batchExtBuyReqData2 = new BatchExtBuyReqData();
        batchExtBuyReqData2.setDetail_no("BUY_20170420007");
        batchExtBuyReqData2.setPlatcust("HF-10929292");
        batchExtBuyReqData2.setTrans_amt(10000d);
//        batchExtBuyReqData2.setExperience_amt(0d);
//        batchExtBuyReqData2.setCoupon_amt(0d);
        batchExtBuyReqData2.setSelf_amt(10000d);
//        batchExtBuyReqData.setIn_interest(0d);
        batchExtBuyReqData2.setSubject_priority("0");

        List<BatchExtBuyReqCommission> commissionList2 = new ArrayList<BatchExtBuyReqCommission>();
        BatchExtBuyReqCommission batchExtBuyReqCommission2 = new BatchExtBuyReqCommission();
        batchExtBuyReqCommission2.setPayout_plat_type("01");
        batchExtBuyReqCommission2.setPayout_amt("10");
        commissionList2.add(batchExtBuyReqCommission2);
        batchExtBuyReqData2.setCommission(commissionList2);

        batchExtBuyReqData2.setRemark("批量购买测试2");
        
        list.add(batchExtBuyReqData);
        list.add(batchExtBuyReqData2);
        req.setData(list);

        req.setOrder_no("BATCHBUY20170420_004");//订单号长度 32位
        req.setPartner_trans_date(new Date());
        req.setPartner_trans_time(new Date());
        B2GResMsg_HFBank_BatchExtBuy res = hfbankTransportService.batchExtBuy(req);
        logger.info("恒丰银行测试批量投标：{}", JSONObject.fromObject(res));
    }
}
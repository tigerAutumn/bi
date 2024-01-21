package com.pinting.business.test.dao;

import com.pinting.business.test.TestBase;
import com.pinting.gateway.hessian.message.hfbank.*;
import com.pinting.gateway.hessian.message.hfbank.model.*;
import com.pinting.gateway.out.service.HfbankTransportService;
import net.sf.json.JSONObject;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.xml.stream.events.EndDocument;

/**
 * Author	:	yed 
 * Date		:	2017/4/18  
 * Description: 恒丰银行测试类
 */
public class TestHFBank_BatchExtRepay extends TestBase {

    private Logger logger = LoggerFactory.getLogger(TestHFBank_BatchExtRepay.class);
    
    @Autowired
    private HfbankTransportService hfbankTransportService;

	/*借款人批量还款*/
    @Test
    public void batchExtRepayTest() {
        B2GReqMsg_HFBank_BatchExtRepay req = new B2GReqMsg_HFBank_BatchExtRepay();
        req.setOrder_no("BATCH_REPAY_20170418010");
        req.setPartner_trans_date(new Date());
        req.setPartner_trans_time(new Date());
        req.setTotal_num(2);
        
        List<BatchExtRepayReqData> detailList = new ArrayList<BatchExtRepayReqData>();
        	BatchExtRepayReqData batchExtRepayReqData = new BatchExtRepayReqData();
            batchExtRepayReqData.setDetail_no("Detail20170418034");
            batchExtRepayReqData.setProd_id("BGW_CS_PRD_1");
            batchExtRepayReqData.setRepay_num("1");
            batchExtRepayReqData.setRepay_date(new Date());
            batchExtRepayReqData.setRepay_amt(1500.1d);
            batchExtRepayReqData.setReal_repay_date(new Date());
            batchExtRepayReqData.setReal_repay_amt(1500.1d);
            batchExtRepayReqData.setPlatcust("2017041813372262011104");
            batchExtRepayReqData.setTrans_amt(1500.1d);
            batchExtRepayReqData.setFee_amt(null);
            batchExtRepayReqData.setRemarkprivate("");
            detailList.add(batchExtRepayReqData);
            
            BatchExtRepayReqData batchExtRepayReqData2 = new BatchExtRepayReqData();
            batchExtRepayReqData2.setDetail_no("Detail20170418035");
            batchExtRepayReqData2.setProd_id("BGW_CS_PRD_2");
            batchExtRepayReqData2.setRepay_num("1");
            batchExtRepayReqData2.setRepay_date(new Date());
            batchExtRepayReqData2.setRepay_amt(1500.1d);
            batchExtRepayReqData2.setReal_repay_date(new Date());
            batchExtRepayReqData2.setReal_repay_amt(1500.1d);
            batchExtRepayReqData2.setPlatcust("2017041813372262011104");
            batchExtRepayReqData2.setTrans_amt(1500.1d);
            batchExtRepayReqData2.setFee_amt(null);
            batchExtRepayReqData2.setRemarkprivate("");
            detailList.add(batchExtRepayReqData2);
            
            req.setData(detailList);
        
        B2GResMsg_HFBank_BatchExtRepay res = hfbankTransportService.batchExtRepay(req);
        logger.info("恒丰银行测试借款人批量还款：{}", JSONObject.fromObject(res));
    }
}
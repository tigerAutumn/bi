package com.pinting.business.test.dao;

import com.pinting.business.test.TestBase;
import com.pinting.business.util.Constants;
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
/**
 * Author:      yed
 * Date:        2017/4/19
 * Description: 恒丰银行测试类
 */
public class TestHFBank_UserBatchWithdraw extends TestBase {

    private Logger logger = LoggerFactory.getLogger(TestHFBank_UserBatchWithdraw.class);

    @Autowired 
    private HfbankTransportService hfbankTransportService;

    /*批量提现*/
    @Test
    public void userBatchWithdrawTest() {
    	int Nums = 1;
        B2GReqMsg_HFBank_UserBatchWithdraw req = new B2GReqMsg_HFBank_UserBatchWithdraw();
        req.setOrder_no("BATCH_WITHDRAW_20170419009");
        req.setPartner_trans_date(new Date());
        req.setPartner_trans_time(new Date());
        req.setTotal_num(Nums);
        List<BatchWithdrawExtData> data = new ArrayList<BatchWithdrawExtData>();
        BatchWithdrawExtData batchWithdrawExtData = new BatchWithdrawExtData();
        batchWithdrawExtData.setDetail_no("WITHDRAW_009");
        batchWithdrawExtData.setPlatcust("2017041813372262011104");
        batchWithdrawExtData.setAmt(1d);
        batchWithdrawExtData.setIs_advance("");
        batchWithdrawExtData.setPay_code(Constants.CHANNEL_BAOFOO);
        batchWithdrawExtData.setFee_amt(0.1d);
        batchWithdrawExtData.setType("");
        batchWithdrawExtData.setWithdraw_type("0");	//提现的账户类型:0投资账户	1融资账户
        batchWithdrawExtData.setBank_id("");
        batchWithdrawExtData.setNotify_url("");
        batchWithdrawExtData.setClient_property(null);
        batchWithdrawExtData.setCity_code(null);
        
        batchWithdrawExtData.setRemark("客户提现测试");
        data.add(batchWithdrawExtData);
        req.setData(data);
        req.setClient_property("1");
        req.setCity_code("");

        B2GResMsg_HFBank_UserBatchWithdraw res = hfbankTransportService.userBatchWithdraw(req);
        logger.info("恒丰银行测试批量提现：{}", JSONObject.fromObject(res));
    }
}
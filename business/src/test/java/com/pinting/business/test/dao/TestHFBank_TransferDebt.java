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
 * Author	:	yed
 * Date	:	2017/4/15
 * Description: 恒丰银行测试类
 */
public class TestHFBank_TransferDebt extends TestBase {

    private Logger logger = LoggerFactory.getLogger(TestHFBank_TransferDebt.class);

    @Autowired
    private HfbankTransportService hfbankTransportService;

    /*标的转让*/
    @Test
    public void transferDebtTest() {
        B2GReqMsg_HFBank_TransferDebt req = new B2GReqMsg_HFBank_TransferDebt();
        req.setPlatcust("HF-10929291");	//转让人
        req.setTrans_share(5000.11d);	//转让份额
        req.setProd_id("BGW_CS_PRD_2");	//
        req.setRelated_prod_ids("P0001,P0002,P0003");
        req.setTrans_amt(5500.11d);
        req.setDeal_amount(5500.11d);
        req.setCoupon_amt(100.12d);
        req.setDeal_platcustprivate("HF-10929292");
        
        //转让人手续费说明
        List<TransferDebtReqCommission> detailList = new ArrayList<TransferDebtReqCommission>();
        TransferDebtReqCommission transferDebtReqCommission = new TransferDebtReqCommission();
        transferDebtReqCommission.setPayout_plat_type("01");
        transferDebtReqCommission.setPayout_amt("10.01");
        detailList.add(transferDebtReqCommission);
        req.setCommission(detailList);
        //受让人手续费说明
        List<TransferDebtReqCommissionExt> detailExtList = new ArrayList<TransferDebtReqCommissionExt>();
        TransferDebtReqCommissionExt commissionExt = new TransferDebtReqCommissionExt();
        commissionExt.setPayout_plat_type("01");
        commissionExt.setPayout_amt("10.01");
        detailExtList.add(commissionExt);
        req.setCommission_ext(detailExtList);
        
        req.setPublish_date(new Date());
        req.setTrans_date(new Date());
        req.setTransfer_income(490.11d);
        req.setIncome_acct("HF-10929292");		//收益出资方账户 平台：01  ;	个人：对应platcust	
        req.setRelated_prod_ids("");
        req.setRemark("测试标的转让");
        req.setOrder_no("YED_ZR_20170417004");
        req.setPartner_trans_date(new Date());
        req.setPartner_trans_time(new Date());
        B2GResMsg_HFBank_TransferDebt res = hfbankTransportService.transferDebt(req);
        logger.info("恒丰银行测试标的转让：{}", JSONObject.fromObject(res));
    }
}
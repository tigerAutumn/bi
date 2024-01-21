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
public class TestHFBank_Publish extends TestBase {

    private Logger logger = LoggerFactory.getLogger(TestHFBank_Publish.class);

    @Autowired 
    private HfbankTransportService hfbankTransportService;

    /*标的发布test*/
//    @Test
    public void publishTest() {
        B2GReqMsg_HFBank_Publish req = new B2GReqMsg_HFBank_Publish();
        //req.setOrder_no(UUID.randomUUID().toString());
        req.setOrder_no("YED_LIANTIAO_CS_10");
        req.setPartner_trans_date(new Date());
        req.setPartner_trans_time(new Date());
        req.setProd_id("BGW_CS_PRD_10");
        req.setProd_name("BGW测试环境10号产品");
        req.setProd_type("0");
        req.setTotal_limit(50000d);
        req.setValue_type("3");		//0满额起息1成立起息2投标起息 3 成立审核后起息
        req.setCreate_type("1");	//1-成立日成立
        req.setSell_date(null);
        req.setValue_date(null);	//起息日如起息方式为成立起息，为必选项 (YYYY-MM-DD HH:mm:ss)
        req.setExpire_date(null);
        req.setCycle(3);	//
        req.setCycle_unit("3");	//周期单位  1日 2周 3月 4季 5年
        req.setIst_year(8.9d);	//12%	送0.089
        req.setRepay_type("0");	//还款方式  0-一次性还款  1-分期还款 
        
        List<PublishFinancingInfo> list = new ArrayList<PublishFinancingInfo>();
        PublishFinancingInfo publishFinancingInfo = new PublishFinancingInfo();
        publishFinancingInfo.setCust_no("2001992719cs1");
        
        publishFinancingInfo.setReg_date(new Date());	//DateUtil.formatDateTime(obj.getReg_time(), "yyyy-MM-dd")
        publishFinancingInfo.setReg_time(new Date());	//DateUtil.formatDateTime(obj.getReg_time(), "HH:mm:ss")
        publishFinancingInfo.setFinanc_int("0.10");
        publishFinancingInfo.setFinanc_purpose("lvyouxiaofei");
        publishFinancingInfo.setUse_date(new Date());
        publishFinancingInfo.setOpen_branch("icbc");
        publishFinancingInfo.setWithdraw_account("6228480321031877111");
        publishFinancingInfo.setAccount_type("1");
        publishFinancingInfo.setPayee_name("yangende");
        publishFinancingInfo.setFinanc_amt(50000d);
        list.add(publishFinancingInfo);
        req.setFinancing_info_list(list);
        
        req.setRisk_lvl(null);
        req.setProd_info(null);
        req.setBuyer_num_limit(null);	//可不填
        req.setChargeOff_auto("1");		//出账标示： 0、成标后自动出账至借款人融资账户  1、调用成标出账接口，出账至标的对应收款账户
        req.setOverLimit("1");			//超额限制 0-不限制  1-限制
        req.setOver_total_limit(1000d);
        req.setRemark("受托报备测试");
        List<CompensationInfo> compInfoList = new ArrayList<CompensationInfo>();
        CompensationInfo compensationInfo =new CompensationInfo();
        compensationInfo.setPlatcust("111992");	//代偿账户编号 
        compensationInfo.setType("0");			//0-代偿还款 1-委托还款 
        compInfoList.add(compensationInfo);
        req.setCompensation_info_list(compInfoList);
        
        
        B2GResMsg_HFBank_Publish res = hfbankTransportService.publish(req);
        logger.info("恒丰银行测试标的发布：{}", JSONObject.fromObject(res));
    }
}
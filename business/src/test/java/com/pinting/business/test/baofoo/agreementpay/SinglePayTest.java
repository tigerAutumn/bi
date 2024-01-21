package com.pinting.business.test.baofoo.agreementpay;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pinting.business.test.TestBase;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.gateway.hessian.message.baofoo.B2GReqMsg_BaoFooAgreementPay_DirectAgreementPay;
import com.pinting.gateway.hessian.message.baofoo.model.RiskItems;
import com.pinting.gateway.out.service.BaoFooTransportService;

public class SinglePayTest extends TestBase {
	@Autowired
	private BaoFooTransportService baoFooTransportService;
	
  	@Test
    public void agreementNotice() {
  		B2GReqMsg_BaoFooAgreementPay_DirectAgreementPay req = new B2GReqMsg_BaoFooAgreementPay_DirectAgreementPay();
		//已签约，发签约代扣
  		req.setSend_time(new Date());
  		req.setMember_id("1198035");
  		req.setTrans_id("longmaotest0010");
  		req.setUser_id("7_DAI_SELF1160227");
  		req.setProtocol_no("2018040215270320000117303061");
		//云贷宝付代扣交易金额入参需要转成分，赞分期就是以分为单位，赞分期无需改动
  		req.setTxn_amt(MoneyUtil.multiply(300, 100).longValue());
  		req.setIsMain(2);
  		
  		
  		RiskItems riskItems = new RiskItems();
  		riskItems.setGoodsCategory("02");
  		riskItems.setUserLoginId("15868157902");
  		riskItems.setUserEmail("");
  		riskItems.setUserMobile("15868157902");
  		riskItems.setRegisterUserName("史玉隆");
  		riskItems.setIdentifyState("1");
  		riskItems.setUserIdNo("520203199012161817");
  		riskItems.setRegisterTime("20180402134000");
  		riskItems.setRegisterIp("");
  		riskItems.setChName("史玉隆");
  		riskItems.setChIdNo("520203199012161817");
  		riskItems.setChCardNo("6214835889901193");
  		riskItems.setChMobile("15868157902");
  		riskItems.setChPayIp("");
  		riskItems.setDeviceOrderNo("");
  		riskItems.setTradeType("2");
  		riskItems.setCustomerType("2");
  		riskItems.setHasBalance("");
  		riskItems.setHasBindCard("");
  		riskItems.setRepaymentDate("");
  		riskItems.setLendingRate("12%");
  		riskItems.setBidYields("");
  		riskItems.setLatestTradeDate("");
  		req.setRiskItems(riskItems);
  		baoFooTransportService.directAgreementPay(req);
  	}
}

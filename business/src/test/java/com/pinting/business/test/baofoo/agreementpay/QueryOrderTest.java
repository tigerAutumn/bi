package com.pinting.business.test.baofoo.agreementpay;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pinting.business.test.TestBase;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.gateway.hessian.message.baofoo.B2GReqMsg_BaoFooAgreementPay_DirectAgreementPay;
import com.pinting.gateway.hessian.message.baofoo.B2GReqMsg_BaoFooAgreementPay_QueryOrder;
import com.pinting.gateway.hessian.message.baofoo.model.RiskItems;
import com.pinting.gateway.out.service.BaoFooTransportService;

public class QueryOrderTest extends TestBase {
	@Autowired
	private BaoFooTransportService baoFooTransportService;
	
  	@Test
    public void agreementNotice() {
  		B2GReqMsg_BaoFooAgreementPay_QueryOrder req = new B2GReqMsg_BaoFooAgreementPay_QueryOrder();
  		req.setMember_id("1198035");
  		req.setOrig_trans_id("longmaotest004");
  		req.setOrig_trade_date(new Date());
  		baoFooTransportService.queryOrder(req);
  	}
}

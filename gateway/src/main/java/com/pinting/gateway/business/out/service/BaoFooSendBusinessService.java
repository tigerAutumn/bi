package com.pinting.gateway.business.out.service;

import com.pinting.gateway.hessian.message.baofoo.G2BReqMsg_BaoFooPay_NewCounterResultNotice;
import com.pinting.gateway.hessian.message.baofoo.G2BResMsg_BaoFooPay_NewCounterResultNotice;
import com.pinting.gateway.hessian.message.dafy.*;
import com.pinting.gateway.hessian.message.pay19.*;
import com.pinting.gateway.hessian.message.reapal.G2BReqMsg_ReapalQuickPay_ReapalNotify;
import com.pinting.gateway.hessian.message.reapal.G2BResMsg_ReapalQuickPay_ReapalNotify;
import com.pinting.gateway.hessian.message.xicai.*;

/**
 * 结果通知接口（宝付发起通知，由gateway再通知business）
 * 
 * @Project: gateway
 * @Title: EBankResultNoticeService.java
 * @author liujz
 * @date 2016-08-16
 * @Copyright: 2016 BiGangWan.com Inc. All rights reserved.
 */
public interface BaoFooSendBusinessService {


	/**
	 * 向business发起网银结果通知
	 * @param req 请求对象
	 * @return resp 响应回gateway
	 */
	G2BResMsg_BaoFooPay_NewCounterResultNotice sendNewCounterResultNotice(
			G2BReqMsg_BaoFooPay_NewCounterResultNotice req);
	
}

package com.pinting.gateway.in.facade;

import com.pinting.business.accounting.finance.model.EBankResultInfo;
import com.pinting.business.accounting.finance.service.UserTopUpService;
import com.pinting.gateway.hessian.message.pay19.G2BReqMsg_Pay19NewCounter_NewCounterResultNotice;
import com.pinting.gateway.hessian.message.pay19.G2BResMsg_Pay19NewCounter_NewCounterResultNotice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("Pay19NewCounter")
public class Pay19NewCounterFacade {

	@Autowired
	private UserTopUpService userTopUpService;

	/**
	 * 
	 * @Title: PayResultNotice 
	 * @Description: 网银购买或者充值19付异步返回通知
	 * @param req
	 * @param res
	 * @throws
	 */
	public void newCounterResultNotice(G2BReqMsg_Pay19NewCounter_NewCounterResultNotice req, G2BResMsg_Pay19NewCounter_NewCounterResultNotice res) {
		/*PayNoticeVO notice = new PayNoticeVO();
		notice.setMpOrderNo(req.getMpOrderId());
		notice.setOrderDate(req.getMxOrderDate());
		notice.setOrderNo(req.getMxOrderId());
		notice.setStatus(req.getResult());
		boolean isContinue = userBuyProductService.quotaFull4BuyNotify(notice);
		if(isContinue){*/
		EBankResultInfo resultInfo = new EBankResultInfo();
		resultInfo.setMxOrderId(req.getMxOrderId());
		resultInfo.setAmount(req.getAmount());
		resultInfo.setMpOrderId(req.getMpOrderId());
		resultInfo.setPayDate(req.getPayDate());
		resultInfo.setResult(req.getResult());
		userTopUpService.notifyEBank(resultInfo);
		/*}*/
	}
}

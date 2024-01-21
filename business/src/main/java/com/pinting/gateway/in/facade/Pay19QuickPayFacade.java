package com.pinting.gateway.in.facade;

import com.pinting.business.accounting.finance.model.QuickPayResultInfo;
import com.pinting.business.accounting.finance.service.UserTopUpService;
import com.pinting.gateway.hessian.message.pay19.G2BReqMsg_Pay19QuickPay_PayResultNotice;
import com.pinting.gateway.hessian.message.pay19.G2BResMsg_Pay19QuickPay_PayResultNotice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("Pay19QuickPay")
public class Pay19QuickPayFacade {
	
	@Autowired
	private UserTopUpService userTopUpService;

	/**
	 * 
	 * @Title: PayResultNotice 
	 * @Description: 充值19付异步返回通知
	 * @param req
	 * @param res
	 * @throws
	 */
	public void payResultNotice(G2BReqMsg_Pay19QuickPay_PayResultNotice req, G2BResMsg_Pay19QuickPay_PayResultNotice res) {
		/*PayNoticeVO notice = new PayNoticeVO();
		notice.setMpOrderNo(req.getMpOrderId());
		notice.setOrderDate(req.getOrderDate());
		notice.setOrderNo(req.getOrderId());
		notice.setStatus(req.getStatus());
		boolean isContinue = userBuyProductService.quotaFull4BuyNotify(notice);
		if(isContinue){*/
		QuickPayResultInfo tempReq = new QuickPayResultInfo();
		tempReq.setStatus(req.getStatus());
		tempReq.setErrorCode(req.getErrorCode());
		tempReq.setErrorMsg(req.getErrorMsg());
		tempReq.setMpOrderId(req.getMpOrderId());
		tempReq.setOrderId(req.getOrderId());
		tempReq.setOrderRemarkDesc(req.getOrderRemarkDesc());
		userTopUpService.notify(tempReq);
		/*}*/
	}
}

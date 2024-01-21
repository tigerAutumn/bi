package com.pinting.gateway.in.facade;

import com.pinting.business.accounting.finance.model.QuickPayResultInfo;
import com.pinting.business.accounting.finance.service.UserTopUpService;
import com.pinting.gateway.hessian.message.reapal.G2BReqMsg_ReapalQuickPay_ReapalNotify;
import com.pinting.gateway.hessian.message.reapal.G2BResMsg_ReapalQuickPay_ReapalNotify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("ReapalQuickPay")
public class ReapalQuickPayFacade {

	@Autowired
	private UserTopUpService userTopUpService;
	
	/**
	 * 
	 * @Title: reapalNotify 
	 * @Description: 卡购买或者充值融宝异步返回通知
	 * @param req
	 * @param res
	 * @throws
	 */
	public void reapalNotify(G2BReqMsg_ReapalQuickPay_ReapalNotify req, G2BResMsg_ReapalQuickPay_ReapalNotify res) {
		/*PayNoticeVO notice = new PayNoticeVO();
		notice.setMpOrderNo(req.getTradeNo());
		notice.setOrderDate(new Date());
		notice.setOrderNo(req.getOrderNo());
		notice.setStatus(req.getStatus());
		boolean isContinue = userBuyProductService.quotaFull4BuyNotify(notice);
		if(isContinue){*/
		QuickPayResultInfo tempReq = new QuickPayResultInfo();
		tempReq.setStatus(req.getStatus());
		tempReq.setErrorCode(req.getResultCode());
		tempReq.setErrorMsg(req.getResultMsg());
		tempReq.setMpOrderId(req.getTradeNo());
		tempReq.setOrderId(req.getOrderNo());
		userTopUpService.notify(tempReq);
		/*}*/
	}
}

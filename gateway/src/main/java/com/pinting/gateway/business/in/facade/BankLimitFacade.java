package com.pinting.gateway.business.in.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.gateway.bird.out.model.BankLimitReq;
import com.pinting.gateway.bird.out.service.NoticeService;
import com.pinting.gateway.hessian.message.loan.B2GReqMsg_BankLimit_LimitList;
import com.pinting.gateway.hessian.message.loan.B2GResMsg_BankLimit_LimitList;
/**
 * 推送银行卡限额（服务方：蜂鸟）
 * @author bianyatian
 * @2016-8-22 下午3:31:02
 */
@Component("BankLimit")
public class BankLimitFacade {
	@Autowired
    private NoticeService noticeService;
	
	public void limitList(B2GReqMsg_BankLimit_LimitList req, B2GResMsg_BankLimit_LimitList res) throws Exception {
		BankLimitReq bankLimitReq = new BankLimitReq();
		bankLimitReq.setLimits(req.getBankLimits());
		noticeService.sandBankLimit(bankLimitReq);
	}

}

package com.pinting.gateway.in.facade.zsd;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.service.site.BankCardService;
import com.pinting.gateway.hessian.message.loan.model.BankLimit;
import com.pinting.gateway.hessian.message.zsd.G2BReqMsg_ZsdBankLimit_QueryBankLimit;
import com.pinting.gateway.hessian.message.zsd.G2BResMsg_ZsdBankLimit_QueryBankLimit;

/**
 * Author:      shh
 * Date:        2017/8/30
 * Description: 查询银行卡限额
 */
@Component("ZsdBankLimit")
public class ZsdBankLimitFacade {

	private Logger log = LoggerFactory.getLogger(ZsdBankLimitFacade.class);

	@Autowired
	private BankCardService bankCardService;

	public void queryBankLimit(G2BReqMsg_ZsdBankLimit_QueryBankLimit req, G2BResMsg_ZsdBankLimit_QueryBankLimit res){
		log.info("收到查询银行卡限额请求");
		List<BankLimit> bankLimitList = bankCardService.selectBankLimitList();
		res.setLimits(bankLimitList);
	}

	
}

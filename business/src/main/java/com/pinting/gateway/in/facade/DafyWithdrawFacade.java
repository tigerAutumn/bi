package com.pinting.gateway.in.facade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.accounting.service.SysWithdrawService;
import com.pinting.business.accounting.service.UserWithdrawService;
import com.pinting.gateway.in.util.DafyInConstant;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_Withdraw_CustomerWithdrawCheck;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_Withdraw_CustomerWithdrawResult;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_Withdraw_SysWithdrawResult;
import com.pinting.gateway.hessian.message.dafy.G2BResMsg_Withdraw_CustomerWithdrawCheck;
import com.pinting.gateway.hessian.message.dafy.G2BResMsg_Withdraw_CustomerWithdrawResult;
import com.pinting.gateway.hessian.message.dafy.G2BResMsg_Withdraw_SysWithdrawResult;

/**
 * 
 * @Project: business
 * @Title: DafyWithdrawFacade.java
 * @author Huang MengJian
 * @date 2015-4-15 上午11:34:34
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Component("Withdraw")
public class DafyWithdrawFacade {
	
	@Autowired
	private SysWithdrawService sysWithdrawService;
	
	@Autowired
	private UserWithdrawService userWithdrawService;
	
	private Logger log = LoggerFactory.getLogger(DafyWithdrawFacade.class);
	public void sysWithdrawResult(G2BReqMsg_Withdraw_SysWithdrawResult req, G2BResMsg_Withdraw_SysWithdrawResult res){
		String result = req.getResult();
		log.info("====================>Business平台已收到系统提现通知结果：编号【" + req.getApplyNo() + "】提现" + ( Integer.parseInt(result)==G2BReqMsg_Withdraw_SysWithdrawResult.WITHDRAW_RESULT_SUCCESS ?"成功":"失败"));
		
		sysWithdrawService.finishSysWithdraw(req);
		
		
	}
	
	public void customerWithdrawResult(G2BReqMsg_Withdraw_CustomerWithdrawResult req,G2BResMsg_Withdraw_CustomerWithdrawResult res){
		String result = req.getResult();
		log.info("====================>Business平台已收到用户提现通知结果：：编号【" + req.getApplyNo() + "】提现" +( Integer.parseInt(result)==G2BReqMsg_Withdraw_CustomerWithdrawResult.WITHDRAW_RESULT_SUCCESS ?"成功":"失败"));
		
		userWithdrawService.finishCustomerWithdrawResult(req);
	}
	
	public void customerWithdrawCheck(G2BReqMsg_Withdraw_CustomerWithdrawCheck req,G2BResMsg_Withdraw_CustomerWithdrawCheck res){
		log.info("====================>Business平台开始用户提现验证");
		boolean flag = userWithdrawService.checkCustomerWithdraw(req);
		res.setCheckResult(flag ? DafyInConstant.CUSTOMER_WITHDRAW_CHECK_SUCCESS : DafyInConstant.CUSTOMER_WITHDRAW_CHECK_ERROR);
		log.info("====================>Business平台用户提现验证结果：" + (flag ? "验证通过" : "验证不通过"));
	}
}

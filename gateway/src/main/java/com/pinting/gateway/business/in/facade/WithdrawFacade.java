package com.pinting.gateway.business.in.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.gateway.dafy.out.model.SysWithdrawReqModel;
import com.pinting.gateway.dafy.out.model.SysWithdrawResModel;
import com.pinting.gateway.dafy.out.model.CustomerWithdrawReqModel;
import com.pinting.gateway.dafy.out.model.CustomerWithdrawResModel;
import com.pinting.gateway.dafy.out.service.SendDafyService;
import com.pinting.gateway.dafy.out.util.DafyOutConstant;
import com.pinting.gateway.enums.PTMessageEnum;
import com.pinting.gateway.exception.PTMessageException;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Withdraw_BonusWithdraw;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Withdraw_SysWithdraw;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Withdraw_BonusWithdraw;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Withdraw_SysWithdraw;

/**
 * 提现相关
 * @Project: gateway
 * @Title: WithdrawFacade.java
 * @author dingpf
 * @date 2015-3-24 下午8:17:40
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Component("Withdraw")
public class WithdrawFacade {
	
	@Autowired
	private SendDafyService dafySendService;
	/**
	 * 奖励金提现
	 * @param req
	 * @param res
	 */
	public void bonusWithdraw(B2GReqMsg_Withdraw_BonusWithdraw req, B2GResMsg_Withdraw_BonusWithdraw res){
		CustomerWithdrawReqModel reqModel = new CustomerWithdrawReqModel();
		reqModel.setAmount(req.getAmount());
		reqModel.setBankcard(req.getBankcard());
		reqModel.setCustomerId(req.getDafyUserId());
		reqModel.setCustomerName(req.getUserName());
		reqModel.setTransType(req.getTransType());
		reqModel.setApplyTime(req.getApplyTime());
		reqModel.setApplyNo(req.getApplyNo());
		CustomerWithdrawResModel resModel = dafySendService.customerWithdraw(reqModel);
		if(!DafyOutConstant.RETURN_CODE_ACCEPT.equals(resModel.getRespCode())){
			throw new PTMessageException(PTMessageEnum.DAFY_BONUS_WITHDRAW_FAIL, resModel.getRespMsg());
		}
		
	}
	
	public void sysWithdraw(B2GReqMsg_Withdraw_SysWithdraw req, B2GResMsg_Withdraw_SysWithdraw res){
		
		//组装报文
		SysWithdrawReqModel  reqModel = new SysWithdrawReqModel();
		reqModel.setAmount(req.getAmount());
		reqModel.setApplyNo(req.getApplyNo());
		reqModel.setTransType("002"); //网新提现	
		//发送给达飞
		SysWithdrawResModel dafyRes = dafySendService.sysWithdraw(reqModel);
		//返回信息
		if(!DafyOutConstant.RETURN_CODE_ACCEPT.equals(dafyRes.getRespCode())){
			throw new PTMessageException(PTMessageEnum.DAFY_SYS_WITHDRAW_FAIL, dafyRes.getRespMsg());
		}
	}

}

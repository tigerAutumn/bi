package com.pinting.gateway.business.in.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.gateway.dafy.out.model.CheckAccountReqModel;
import com.pinting.gateway.dafy.out.model.CheckAccountResModel;
import com.pinting.gateway.dafy.out.model.QueryWXAccountDetailReqModel;
import com.pinting.gateway.dafy.out.model.QueryWXAccountDetailResModel;
import com.pinting.gateway.dafy.out.service.SendDafyService;
import com.pinting.gateway.dafy.out.util.DafyOutConstant;
import com.pinting.gateway.enums.PTMessageEnum;
import com.pinting.gateway.exception.PTMessageException;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Account_CheckAccount;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Account_QueryWXAccountDetail;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Account_CheckAccount;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Account_QueryWXAccountDetail;

/**
 * 与达飞之间 资金账户 相关
 * @Project: gateway
 * @Title: AccountFacade.java
 * @author dingpf
 * @date 2015-3-3 下午3:09:39
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Component("Account")
public class AccountFacade {
	@Autowired
	private SendDafyService dafySendService;

	public void checkAccount(B2GReqMsg_Account_CheckAccount req,
			B2GResMsg_Account_CheckAccount res) {
		// 组装报文
		CheckAccountReqModel reqModel = new CheckAccountReqModel();
		reqModel.setQueryDate(req.getQueryDate());
		// 发送给达飞
		CheckAccountResModel dafyRes = dafySendService.checkAccount(reqModel);
		// 返回信息
		if(!DafyOutConstant.RETURN_CODE_SUCCESS.equals(dafyRes.getRespCode())){
			throw new PTMessageException(PTMessageEnum.DAFY_CHECKACCOUNT_FAIL, dafyRes.getRespMsg());
		}else{
			res.setInvestmentAmounts(dafyRes.getInvestmentAmounts());
		}

	}
	public void  queryWXAccountDetail(B2GReqMsg_Account_QueryWXAccountDetail req,
			B2GResMsg_Account_QueryWXAccountDetail res) {
		// 组装报文
		QueryWXAccountDetailReqModel reqModel = new QueryWXAccountDetailReqModel();
		reqModel.setPageIndex(req.getPageIndex());
		reqModel.setPageNum(req.getPageNum());
		reqModel.setStartDate(req.getStartDate());
		reqModel.setEndDate(req.getEndDate());
		reqModel.setTransType(req.getTransType());
		// 发送给达飞
		QueryWXAccountDetailResModel dafyRes = dafySendService.wXAccountDetailQuery(reqModel);
		// 返回信息
		if(!DafyOutConstant.RETURN_CODE_SUCCESS.equals(dafyRes.getRespCode())){
			throw new PTMessageException(PTMessageEnum.DAFY_QUERY_SYS_ACCOUNT_DETAIL_FAIL, dafyRes.getRespMsg());
		}else{
			res.setData(dafyRes.getData());
			res.setBalance(dafyRes.getBalance());
			res.setCount(dafyRes.getCount());
			res.setCurrPage(dafyRes.getCurrPage());
			res.setData(dafyRes.getData());
		}

	}
}

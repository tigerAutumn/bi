package com.pinting.gateway.out.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.pinting.core.hessian.service.HessianService;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Account_CheckAccount;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Account_QueryWXAccountDetail;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Customer_GetLoanRelationNew;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Customer_GetLoanRelationUrl;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Customer_IncrementDataMission;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Customer_IncrementDeptInfo;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Customer_IncrementUserInfo;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Customer_QueryCurrDeptInfo;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Customer_QueryCurrDeptUserInfo;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Customer_QueryDeptInfo;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Customer_QueryDeptUserInfo;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Customer_QueryUserById;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Customer_QueryUserByPhone;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Customer_Register;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Payment_BasicInformation;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Payment_BindCard;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Payment_BuyProduct;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Payment_QueryRepayJnl;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Payment_SysBatchBuyProduct;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Withdraw_BonusWithdraw;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Withdraw_SysWithdraw;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Account_CheckAccount;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Account_QueryWXAccountDetail;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Customer_GetLoanRelationNew;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Customer_GetLoanRelationUrl;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Customer_IncrementDataMission;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Customer_IncrementDeptInfo;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Customer_IncrementUserInfo;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Customer_QueryCurrDeptInfo;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Customer_QueryCurrDeptUserInfo;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Customer_QueryDeptInfo;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Customer_QueryDeptUserInfo;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Customer_QueryUserById;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Customer_QueryUserByPhone;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Customer_Register;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Payment_BasicInformation;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Payment_BindCard;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Payment_BuyProduct;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Payment_QueryRepayJnl;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Payment_SysBatchBuyProduct;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Withdraw_BonusWithdraw;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Withdraw_SysWithdraw;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Payment_ServerUsableCheck;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Payment_ServerUsableCheck;
import com.pinting.gateway.out.service.DafyTransportService;

@Service
public class DafyTransportServiceImpl implements DafyTransportService {
	@Autowired
	@Qualifier("dafyGatewayService")
	private HessianService dafyGatewayService;
	
	@Override
	public B2GResMsg_Customer_Register register(B2GReqMsg_Customer_Register req) {
		B2GResMsg_Customer_Register res = (B2GResMsg_Customer_Register) dafyGatewayService.handleMsg(req);
		return res;
	}

	@Override
	public B2GResMsg_Payment_BindCard bindCard(B2GReqMsg_Payment_BindCard req) {
		B2GResMsg_Payment_BindCard res = (B2GResMsg_Payment_BindCard) dafyGatewayService.handleMsg(req);
		return res;
	}
	
	@Override
	public B2GResMsg_Payment_BuyProduct buyProduct(B2GReqMsg_Payment_BuyProduct req) {
		B2GResMsg_Payment_BuyProduct res = (B2GResMsg_Payment_BuyProduct) dafyGatewayService.handleMsg(req);
		return res;
	}

	@Override
	public B2GResMsg_Account_CheckAccount checkAccount(
			B2GReqMsg_Account_CheckAccount req) {
		B2GResMsg_Account_CheckAccount res = (B2GResMsg_Account_CheckAccount) dafyGatewayService.handleMsg(req);
		return res;
	}

	@Override
	public B2GResMsg_Customer_GetLoanRelationUrl queryLoanRelation(
			B2GReqMsg_Customer_GetLoanRelationUrl req) {
		B2GResMsg_Customer_GetLoanRelationUrl res = (B2GResMsg_Customer_GetLoanRelationUrl) dafyGatewayService.handleMsg(req);
		return res;
	}

	@Override
	public B2GResMsg_Payment_BasicInformation basicInformation(
			B2GReqMsg_Payment_BasicInformation req) {
		B2GResMsg_Payment_BasicInformation res = (B2GResMsg_Payment_BasicInformation) dafyGatewayService.handleMsg(req);
		return res;
	}

	@Override
	public B2GResMsg_Withdraw_BonusWithdraw bonusWithdraw(
			B2GReqMsg_Withdraw_BonusWithdraw req) {
		B2GResMsg_Withdraw_BonusWithdraw res = (B2GResMsg_Withdraw_BonusWithdraw) dafyGatewayService.handleMsg(req);
		return res;
	}
	
	@Override
	public B2GResMsg_Withdraw_SysWithdraw sysWithdraw(
			B2GReqMsg_Withdraw_SysWithdraw req) {
		B2GResMsg_Withdraw_SysWithdraw res = (B2GResMsg_Withdraw_SysWithdraw) dafyGatewayService.handleMsg(req);
		return res;
	}

	@Override
	public B2GResMsg_Account_QueryWXAccountDetail wXAccountDetailQuery(
			B2GReqMsg_Account_QueryWXAccountDetail req) {
		B2GResMsg_Account_QueryWXAccountDetail res = (B2GResMsg_Account_QueryWXAccountDetail) dafyGatewayService.handleMsg(req);
		return res;
	}

	@Override
	public B2GResMsg_Payment_SysBatchBuyProduct sysBatchBuyProduct(
			B2GReqMsg_Payment_SysBatchBuyProduct req) {
		B2GResMsg_Payment_SysBatchBuyProduct res = (B2GResMsg_Payment_SysBatchBuyProduct) dafyGatewayService.handleMsg(req);
		return res;
	}

	@Override
	public B2GResMsg_Customer_GetLoanRelationNew queryLoanRelationNew(
			B2GReqMsg_Customer_GetLoanRelationNew req) {
		B2GResMsg_Customer_GetLoanRelationNew res = (B2GResMsg_Customer_GetLoanRelationNew) dafyGatewayService.handleMsg(req);
		return res;
	}

	@Override
	public B2GResMsg_Payment_QueryRepayJnl queryRepayJnl(
			B2GReqMsg_Payment_QueryRepayJnl req) {
		B2GResMsg_Payment_QueryRepayJnl res = (B2GResMsg_Payment_QueryRepayJnl) dafyGatewayService.handleMsg(req);
		return res;
	}

	@Override
	public B2GResMsg_Customer_QueryUserByPhone queryUserByPhone(B2GReqMsg_Customer_QueryUserByPhone req) {
		B2GResMsg_Customer_QueryUserByPhone res = (B2GResMsg_Customer_QueryUserByPhone) dafyGatewayService.handleMsg(req);
		return res;
	}

	@Override
	public B2GResMsg_Customer_QueryUserById queryUserById(B2GReqMsg_Customer_QueryUserById req) {
		B2GResMsg_Customer_QueryUserById res = (B2GResMsg_Customer_QueryUserById) dafyGatewayService.handleMsg(req);
		return res;
	}

	@Override
	public B2GResMsg_Customer_QueryDeptUserInfo queryDeptUserInfo(B2GReqMsg_Customer_QueryDeptUserInfo req) {
		B2GResMsg_Customer_QueryDeptUserInfo res = (B2GResMsg_Customer_QueryDeptUserInfo) dafyGatewayService.handleMsg(req);
		return res;
	}

	@Override
	public B2GResMsg_Customer_QueryCurrDeptUserInfo queryCurrDeptUserInfo(
			B2GReqMsg_Customer_QueryCurrDeptUserInfo req) {
		B2GResMsg_Customer_QueryCurrDeptUserInfo res = (B2GResMsg_Customer_QueryCurrDeptUserInfo) dafyGatewayService.handleMsg(req);
		return res;
	}

	@Override
	public B2GResMsg_Customer_QueryDeptInfo queryDeptInfo(B2GReqMsg_Customer_QueryDeptInfo req) {
		B2GResMsg_Customer_QueryDeptInfo res = (B2GResMsg_Customer_QueryDeptInfo) dafyGatewayService.handleMsg(req);
		return res;
	}

	@Override
	public B2GResMsg_Customer_QueryCurrDeptInfo queryCurrDeptInfo(B2GReqMsg_Customer_QueryCurrDeptInfo req) {
		B2GResMsg_Customer_QueryCurrDeptInfo res = (B2GResMsg_Customer_QueryCurrDeptInfo) dafyGatewayService.handleMsg(req);
		return res;
	}

	@Override
	public B2GResMsg_Customer_IncrementUserInfo incrementUserInfo(B2GReqMsg_Customer_IncrementUserInfo req) {
		B2GResMsg_Customer_IncrementUserInfo res = (B2GResMsg_Customer_IncrementUserInfo) dafyGatewayService.handleMsg(req);
		return res;
	}

	@Override
	public B2GResMsg_Customer_IncrementDeptInfo incrementDeptInfo(B2GReqMsg_Customer_IncrementDeptInfo req) {
		B2GResMsg_Customer_IncrementDeptInfo res = (B2GResMsg_Customer_IncrementDeptInfo) dafyGatewayService.handleMsg(req);
		return res;
	}

	@Override
	public B2GResMsg_Customer_IncrementDataMission incrementDataMission(B2GReqMsg_Customer_IncrementDataMission req) {
		B2GResMsg_Customer_IncrementDataMission res = (B2GResMsg_Customer_IncrementDataMission) dafyGatewayService.handleMsg(req);
		return res;
	}

	@Override
	public B2GResMsg_Payment_ServerUsableCheck serverUsableCheck(
			B2GReqMsg_Payment_ServerUsableCheck req) {
		B2GResMsg_Payment_ServerUsableCheck res = (B2GResMsg_Payment_ServerUsableCheck) dafyGatewayService.handleMsg(req);
		return res;
	}

}

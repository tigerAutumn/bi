package com.pinting.gateway.out.service.impl;

import com.pinting.business.util.Constants;
import com.pinting.business.util.Util;
import com.pinting.core.hessian.service.HessianService;
import com.pinting.gateway.hessian.message.hfbank.*;
import com.pinting.gateway.out.service.HfbankTransportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class HfbankTransportServiceImpl implements HfbankTransportService {
	@Autowired
	@Qualifier("hfBankGatewayService")
	private HessianService hfBankGatewayService;
	
	
	@Override
	public B2GResMsg_HFBank_Publish publish(B2GReqMsg_HFBank_Publish req) {
		B2GResMsg_HFBank_Publish res = (B2GResMsg_HFBank_Publish) hfBankGatewayService.handleMsg(req);
		return res;
	}


	@Override
	public B2GResMsg_HFBank_EstablishOrAbandon establishOrAbandon(
			B2GReqMsg_HFBank_EstablishOrAbandon req) {
		Date transTime = new Date();
		req.setPartner_trans_date(transTime);
		req.setPartner_trans_time(transTime);
		B2GResMsg_HFBank_EstablishOrAbandon res = (B2GResMsg_HFBank_EstablishOrAbandon) hfBankGatewayService.handleMsg(req);
		return res;
	}

	@Override
	public B2GResMsg_HFBank_BatchExtBuy batchExtBuy(
			B2GReqMsg_HFBank_BatchExtBuy req) {
		B2GResMsg_HFBank_BatchExtBuy res = (B2GResMsg_HFBank_BatchExtBuy) hfBankGatewayService.handleMsg(req);
		return res;
	}


	@Override
	public B2GResMsg_HFBank_ChargeOff chargeOff(B2GReqMsg_HFBank_ChargeOff req) {
		B2GResMsg_HFBank_ChargeOff res = (B2GResMsg_HFBank_ChargeOff) hfBankGatewayService.handleMsg(req);
		return res;
	}


	@Override
	public B2GResMsg_HFBank_ModifyPayOutAcct modifyPayOutAcct(
			B2GReqMsg_HFBank_ModifyPayOutAcct req) {
		B2GResMsg_HFBank_ModifyPayOutAcct res = (B2GResMsg_HFBank_ModifyPayOutAcct) hfBankGatewayService.handleMsg(req);
		return res;
	}


	@Override
	public B2GResMsg_HFBank_TransferDebt transferDebt(
			B2GReqMsg_HFBank_TransferDebt req) {
		
		req.setPublish_date(new Date());
		req.setTrans_date(new Date());
		req.setRemark("");
		req.setPartner_trans_date(new Date());
		req.setPartner_trans_time(new Date());
		req.setSubject_priority(Constants.HF_TRANS_SUBJECT_PRIORITY_OUT);
		B2GResMsg_HFBank_TransferDebt res = (B2GResMsg_HFBank_TransferDebt) hfBankGatewayService.handleMsg(req);
		return res;
	}


	@Override
	public B2GResMsg_HFBank_BatchExtRepay batchExtRepay(
			B2GReqMsg_HFBank_BatchExtRepay req) {
		B2GResMsg_HFBank_BatchExtRepay res = (B2GResMsg_HFBank_BatchExtRepay) hfBankGatewayService.handleMsg(req);
		return res;
	}


	@Override
	public B2GResMsg_HFBank_ProdRepay prodRepay(B2GReqMsg_HFBank_ProdRepay req) {
		B2GResMsg_HFBank_ProdRepay res = (B2GResMsg_HFBank_ProdRepay) hfBankGatewayService.handleMsg(req);
		return res;
	}


	@Override
	public B2GResMsg_HFBank_PlatformTransfer platformTransfer(
			B2GReqMsg_HFBank_PlatformTransfer req) {
		B2GResMsg_HFBank_PlatformTransfer res = (B2GResMsg_HFBank_PlatformTransfer) hfBankGatewayService.handleMsg(req);
		return res;
	}


	@Override
	public B2GResMsg_HFBank_PlatformAccountConverse platformAccountConverse(
			B2GReqMsg_HFBank_PlatformAccountConverse req) {
		B2GResMsg_HFBank_PlatformAccountConverse res = (B2GResMsg_HFBank_PlatformAccountConverse) hfBankGatewayService.handleMsg(req);
		return res;
	}


	/* ============================== 浣熊负责接口开始 =============================== */

	@Override
	public B2GResMsg_HFBank_BatchBindCard4Elements batchBindCard4Elements(B2GReqMsg_HFBank_BatchBindCard4Elements req) {
		return (B2GResMsg_HFBank_BatchBindCard4Elements) hfBankGatewayService.handleMsg(req);
	}

	@Override
	public B2GResMsg_HFBank_BatchBindCardRealNameAuth batchBindCardRealNameAuth(B2GReqMsg_HFBank_BatchBindCardRealNameAuth req) {
		return (B2GResMsg_HFBank_BatchBindCardRealNameAuth) hfBankGatewayService.handleMsg(req);
	}

	@Override
	public B2GResMsg_HFBank_BatchChangeCard batchChangeCard(B2GReqMsg_HFBank_BatchChangeCard req) {
		return (B2GResMsg_HFBank_BatchChangeCard) hfBankGatewayService.handleMsg(req);
	}

	@Override
	public B2GResMsg_HFBank_UserPreBindCard userPreBindCard(B2GReqMsg_HFBank_UserPreBindCard req) {
		return (B2GResMsg_HFBank_UserPreBindCard) hfBankGatewayService.handleMsg(req);
	}

	@Override
	public B2GResMsg_HFBank_UserBindCard userBindCard(B2GReqMsg_HFBank_UserBindCard req) {
		return (B2GResMsg_HFBank_UserBindCard) hfBankGatewayService.handleMsg(req);
	}

	@Override
	public B2GResMsg_HFBank_UserGatewayRechargeRequest userGatewayRechargeRequest(B2GReqMsg_HFBank_UserGatewayRechargeRequest req) {
		return (B2GResMsg_HFBank_UserGatewayRechargeRequest) hfBankGatewayService.handleMsg(req);
	}

	@Override
	public B2GResMsg_HFBank_UserQuickPayPreRecharge userQuickPayPreRecharge(B2GReqMsg_HFBank_UserQuickPayPreRecharge req) {
		return (B2GResMsg_HFBank_UserQuickPayPreRecharge) hfBankGatewayService.handleMsg(req);
	}

	@Override
	public B2GResMsg_HFBank_UserQuickPayConfirmRecharge userQuickPayConfirmRecharge(B2GReqMsg_HFBank_UserQuickPayConfirmRecharge req) {
		return (B2GResMsg_HFBank_UserQuickPayConfirmRecharge) hfBankGatewayService.handleMsg(req);
	}

	@Override
	public B2GResMsg_HFBank_BorrowCutRepayDK borrowCutRepayDK(B2GReqMsg_HFBank_BorrowCutRepayDK req) {
		return (B2GResMsg_HFBank_BorrowCutRepayDK) hfBankGatewayService.handleMsg(req);
	}

	@Override
	public B2GResMsg_HFBank_UserBatchWithdraw userBatchWithdraw(B2GReqMsg_HFBank_UserBatchWithdraw req) {
		return (B2GResMsg_HFBank_UserBatchWithdraw) hfBankGatewayService.handleMsg(req);
	}

	@Override
	public B2GResMsg_HFBank_QueryOrder queryOrder(B2GReqMsg_HFBank_QueryOrder req) {
		return (B2GResMsg_HFBank_QueryOrder) hfBankGatewayService.handleMsg(req);
	}

	@Override
	public B2GResMsg_HFBank_QueryAccountInfo queryAccountInfo(B2GReqMsg_HFBank_QueryAccountInfo req) {
		return (B2GResMsg_HFBank_QueryAccountInfo) hfBankGatewayService.handleMsg(req);
	}

	@Override
	public B2GResMsg_HFBank_QueryAccountLeftAmountInfo queryAccountLeftAmountInfo(B2GReqMsg_HFBank_QueryAccountLeftAmountInfo req) {
		return (B2GResMsg_HFBank_QueryAccountLeftAmountInfo) hfBankGatewayService.handleMsg(req);
	}

	@Override
	public B2GResMsg_HFBank_QueryProductBalance queryProductBalance(B2GReqMsg_HFBank_QueryProductBalance req) {
		return (B2GResMsg_HFBank_QueryProductBalance) hfBankGatewayService.handleMsg(req);
	}


	@Override
	public B2GResMsg_HFBank_CompensateRepay compensateRepay(
			B2GReqMsg_HFBank_CompensateRepay req) {
		return (B2GResMsg_HFBank_CompensateRepay) hfBankGatewayService.handleMsg(req);
	}


	@Override
	public B2GResMsg_HFBank_RepayCompensate repayCompensate(
			B2GReqMsg_HFBank_RepayCompensate req) {
		return (B2GResMsg_HFBank_RepayCompensate) hfBankGatewayService.handleMsg(req);
	}


	@Override
	public B2GResMsg_HFBank_PlatWithDraw platWithDraw(
			B2GReqMsg_HFBank_PlatWithDraw req) {
		return (B2GResMsg_HFBank_PlatWithDraw) hfBankGatewayService.handleMsg(req);
	}


	@Override
	public B2GResMsg_HFBank_PlatCharge platCharge(
			B2GReqMsg_HFBank_PlatCharge req) {
		return (B2GResMsg_HFBank_PlatCharge) hfBankGatewayService.handleMsg(req);
	}

	/* ============================== 浣熊负责接口结束 =============================== */

	@Override
	public B2GResMsg_HFBank_FundDataCheck fundDataCheck(
			B2GReqMsg_HFBank_FundDataCheck req) {
		return (B2GResMsg_HFBank_FundDataCheck) hfBankGatewayService.handleMsg(req);
	}

	@Override
	public B2GResMsg_HFBank_BalanceInfo balanceInfo(B2GReqMsg_HFBank_BalanceInfo req) {
		return (B2GResMsg_HFBank_BalanceInfo) hfBankGatewayService.handleMsg(req);
	}

	@Override
	public B2GResMsg_HFBank_QueryLiquidationInfo queryLiquidationInfo(
			B2GReqMsg_HFBank_QueryLiquidationInfo req) {
		return (B2GResMsg_HFBank_QueryLiquidationInfo) hfBankGatewayService.handleMsg(req);
	}
	@Override
	public B2GResMsg_HFBank_GetFundList getFundList(
			B2GReqMsg_HFBank_GetFundList req) {
		return (B2GResMsg_HFBank_GetFundList) hfBankGatewayService.handleMsg(req);
	}


	@Override
	public B2GResMsg_HFBank_UserAddCard userAddCard(
			B2GReqMsg_HFBank_UserAddCard req) {
		return (B2GResMsg_HFBank_UserAddCard) hfBankGatewayService.handleMsg(req);
	}

	@Override
	public B2GResMsg_HFBank_UpdatePlatAcct updatePlatAcct(B2GReqMsg_HFBank_UpdatePlatAcct req) {
		return (B2GResMsg_HFBank_UpdatePlatAcct) hfBankGatewayService.handleMsg(req);
	}
	@Override
	public B2GResMsg_HFBank_GetFundOrderInfo getFundOrderInfo(B2GReqMsg_HFBank_GetFundOrderInfo req) {
		return (B2GResMsg_HFBank_GetFundOrderInfo)hfBankGatewayService.handleMsg(req);
	}

}
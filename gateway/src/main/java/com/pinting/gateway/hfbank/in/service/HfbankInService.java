package com.pinting.gateway.hfbank.in.service;

import com.pinting.gateway.dafy.in.model.LoginReqModel;
import com.pinting.gateway.dafy.in.model.LoginResModel;
import com.pinting.gateway.hfbank.in.model.*;

public interface HfbankInService {


	/* ============================== 浣熊写的接口开始 =============================== */
	/**
	 * 1. 充值回调通知
	 * 2. 借款人扣款还款（代扣）通知
	 * 3. 提现通知
	 */

	/**
	 * 1. 充值回调通知
	 * @param req
	 * @return
     */
	GatewayRechargeNoticeResModel gatewayRechargeNotice(GatewayRechargeNoticeReqModel req);

	/**
	 * 2. 借款人扣款还款（代扣）通知
	 * @param req
	 * @return
     */
	BorrowCutRepayNoticeResModel borrowCutRepayNotice(BorrowCutRepayNoticeReqModel req);

	/**
	 * 3. 提现通知
	 * @param req
	 * @return
	 */
	BatchWithdrawExtNoticeResModel userBatchWithdrawNotice(BatchWithdrawExtNoticeReqModel req);

	/* ============================== 浣熊写的接口结束 ============================== */
	
	/* ============================== 龙猫写的接口开始 =============================== */

	/**
	 * 1. 成标出账通知
	 * @param req
	 * @return
     */
	OutOfAccountResModel outOfAccount(OutOfAccountReqModel req);


	/* ============================== 龙猫写的接口结束 ============================== */

	/**
	 * 平台充值回调通知
	 * @param req
	 * @return
     */
	PlatTopUpNoticeResModel platTopUpNotice(PlatTopUpNoticeReqModel req);
	
	/**
	 * 平台提现回调通知
	 * @param req
	 * @return
     */
	PlatWithdrawNoticeResModel platWithdrawNotice(PlatWithdrawNoticeReqModel req);
	/**
	 * 算成功通知
	 * @param req
	 * @return
	 */
	LiquidationNoticeResModel liquidationNotice(LiquidationNoticeReqModel req);
	
}

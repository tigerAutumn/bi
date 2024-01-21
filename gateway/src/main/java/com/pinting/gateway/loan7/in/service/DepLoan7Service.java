package com.pinting.gateway.loan7.in.service;

import com.pinting.gateway.loan7.in.model.ActiveRepayConfirmReqModel;
import com.pinting.gateway.loan7.in.model.ActiveRepayConfirmResModel;
import com.pinting.gateway.loan7.in.model.ActiveRepayPreReqModel;
import com.pinting.gateway.loan7.in.model.ActiveRepayPreResModel;
import com.pinting.gateway.loan7.in.model.ActiveRepaySmsCodeRepeatReqModel;
import com.pinting.gateway.loan7.in.model.ActiveRepaySmsCodeRepeatResModel;
import com.pinting.gateway.loan7.in.model.AgreementNoticeReqModel;
import com.pinting.gateway.loan7.in.model.AgreementNoticeResModel;
import com.pinting.gateway.loan7.in.model.ApplyLoanReqModel;
import com.pinting.gateway.loan7.in.model.ApplyLoanResModel;
import com.pinting.gateway.loan7.in.model.CutRepayConfirmReqModel;
import com.pinting.gateway.loan7.in.model.CutRepayConfirmResModel;
import com.pinting.gateway.loan7.in.model.FillFinishReqModel;
import com.pinting.gateway.loan7.in.model.FillFinishResModel;
import com.pinting.gateway.loan7.in.model.LateRepayReqModel;
import com.pinting.gateway.loan7.in.model.LateRepayResModel;
import com.pinting.gateway.loan7.in.model.LoginReqModel;
import com.pinting.gateway.loan7.in.model.LoginResModel;
import com.pinting.gateway.loan7.in.model.PushBillReqModel;
import com.pinting.gateway.loan7.in.model.PushBillResModel;
import com.pinting.gateway.loan7.in.model.QueryDailyAmountReqModel;
import com.pinting.gateway.loan7.in.model.QueryDailyAmountResModel;
import com.pinting.gateway.loan7.in.model.QueryLoanResultReqModel;
import com.pinting.gateway.loan7.in.model.QueryLoanResultResModel;
import com.pinting.gateway.loan7.in.model.QueryRepayResultReqModel;
import com.pinting.gateway.loan7.in.model.QueryRepayResultResModel;
import com.pinting.gateway.loan7.in.model.QuerySignResultReqModel;
import com.pinting.gateway.loan7.in.model.QuerySignResultResModel;

public interface DepLoan7Service {
	
	/**
	 * 登录	
	 * @param req LoginReqModel
	 * @return  LoginResModel
	 */
	public LoginResModel login(LoginReqModel req);
	
	/**
	 * 每日可借额度查询	
	 * @param req DailyAvailableAmountLimitReqModel
	 * @return  DailyAvailableAmountLimitResModel
	 */
	public QueryDailyAmountResModel dailyAvailableAmountLimit(QueryDailyAmountReqModel req);
	
	
	/**
	 * 放款	
	 * @param req ApplyLoanReqModel
	 * @return  ApplyLoanResModel
	 */
	public ApplyLoanResModel applyLoan(ApplyLoanReqModel req);
	
	
	/**
	 * 放款结果查询	
	 * @param req QueryLoanResultReqModel
	 * @return  QueryLoanResultResModel
	 */
	public QueryLoanResultResModel queryLoanResult(QueryLoanResultReqModel req);
	
	
	/**
	 * 账单（还款计划）推送	
	 * @param req PushBillReqModel
	 * @return  PushBillResModel
	 */
	public PushBillResModel pushBill(PushBillReqModel req);
	
	
	/**
	 * 借款协议签章结果查询	
	 * @param req QuerySignResultReqModel
	 * @return  QuerySignResultResModel
	 */
	public QuerySignResultResModel querySignResult(QuerySignResultReqModel req);
	

	
	/**
	 * 主动还款预下单	
	 * @param req ActiveRepayPreReqModel
	 * @return  ActiveRepayPreResModel
	 */
	public ActiveRepayPreResModel activeRepayPre(ActiveRepayPreReqModel req);
	
	
	/**
	 * 主动还款正式	
	 * @param req ActiveRepayConfirmReqModel
	 * @return  ActiveRepayConfirmResModel
	 */
	public ActiveRepayConfirmResModel activeRepayConfirm(ActiveRepayConfirmReqModel req);
	
	
	/**
	 * 补账完成通知
	 * @param reqModel
	 * @return
	 */
	public FillFinishResModel fillFinish(FillFinishReqModel reqModel);
	
	/**
	 * 代偿通知:云贷代偿划拨完成后，需通知币港湾代偿信息及结果
	 * @param reqModel
	 * @return
	 */
	public LateRepayResModel lateRepay(LateRepayReqModel reqModel);
	
	/**
	 * 还款结果查询(包括快捷和代扣的支付结果)
	 * @param reqModel
	 * @return
	 */
	public QueryRepayResultResModel queryRepayResul(QueryRepayResultReqModel reqModel);
	
	/**
	 * 代扣还款
	 * @param reqModel
	 * @return
	 */
	public CutRepayConfirmResModel cutRepayConfirm(CutRepayConfirmReqModel reqModel);

	/**
	 * 协议下载地址查询
	 * @param reqModel
	 * @return
	 */
	public AgreementNoticeResModel agreementNotice(AgreementNoticeReqModel reqModel);
	/**
	 *  还款预下单重发验证码短信
	 * @param reqModel
	 * @return
	 */
	public ActiveRepaySmsCodeRepeatResModel activeRepaySmsCodeRepeat(ActiveRepaySmsCodeRepeatReqModel reqModel);
	
	
}

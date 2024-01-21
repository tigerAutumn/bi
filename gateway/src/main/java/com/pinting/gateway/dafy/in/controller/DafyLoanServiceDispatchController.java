package com.pinting.gateway.dafy.in.controller;

import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.gateway.dafy.in.model.ActiveRepayConfirmReqModel;
import com.pinting.gateway.dafy.in.model.ActiveRepayConfirmResModel;
import com.pinting.gateway.dafy.in.model.ActiveRepayPreReqModel;
import com.pinting.gateway.dafy.in.model.ActiveRepayPreResModel;
import com.pinting.gateway.dafy.in.model.ActiveRepaySmsCodeRepeatReqModel;
import com.pinting.gateway.dafy.in.model.ActiveRepaySmsCodeRepeatResModel;
import com.pinting.gateway.dafy.in.model.AgreementNoticeReqModel;
import com.pinting.gateway.dafy.in.model.AgreementNoticeResModel;
import com.pinting.gateway.dafy.in.model.ApplyLoanReqModel;
import com.pinting.gateway.dafy.in.model.ApplyLoanResModel;
import com.pinting.gateway.dafy.in.model.BaseReqModel;
import com.pinting.gateway.dafy.in.model.CutRepayConfirmReqModel;
import com.pinting.gateway.dafy.in.model.CutRepayConfirmResModel;
import com.pinting.gateway.dafy.in.model.FillFinishReqModel;
import com.pinting.gateway.dafy.in.model.FillFinishResModel;
import com.pinting.gateway.dafy.in.model.LateRepayReqModel;
import com.pinting.gateway.dafy.in.model.LateRepayResModel;
import com.pinting.gateway.dafy.in.model.LoginReqModel;
import com.pinting.gateway.dafy.in.model.LoginResModel;
import com.pinting.gateway.dafy.in.model.PushBillReqModel;
import com.pinting.gateway.dafy.in.model.PushBillResModel;
import com.pinting.gateway.dafy.in.model.QueryDailyAmountReqModel;
import com.pinting.gateway.dafy.in.model.QueryDailyAmountResModel;
import com.pinting.gateway.dafy.in.model.QueryLoanResultReqModel;
import com.pinting.gateway.dafy.in.model.QueryLoanResultResModel;
import com.pinting.gateway.dafy.in.model.QueryRepayResultReqModel;
import com.pinting.gateway.dafy.in.model.QueryRepayResultResModel;
import com.pinting.gateway.dafy.in.model.QuerySignResultReqModel;
import com.pinting.gateway.dafy.in.model.QuerySignResultResModel;
import com.pinting.gateway.dafy.in.service.DafyInLoanService;
import com.pinting.gateway.dafy.in.util.DafyInConstant;
import com.pinting.gateway.dafy.in.util.DafyInMsgUtil;

/**
 * 自主放款
 * 根据报文交易码，进行服务分发
 * 
 * @Project: gateway
 * @Title: DafyLoanServiceDispatchController.java
 * @author Dragon & cat
 * @date  2016-12-19
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Controller
public class DafyLoanServiceDispatchController {

	private Logger log = LoggerFactory
			.getLogger(DafyLoanServiceDispatchController.class);
	
	
	@Autowired
	private	DafyInLoanService	dafyInLoanService;

	@RequestMapping("/dafyLoan/business")
	public @ResponseBody
	String serviceDispatch(HttpServletRequest request,
			HttpServletResponse response) {
		BaseReqModel reqModel = (BaseReqModel) request.getAttribute("reqModel");
		// 待返回报文密文
		String resp = null;
		switch (reqModel.getTransCode()) {
		case DafyInConstant.LOGIN:// 达飞登录 处理
			LoginResModel loginResModel = new LoginResModel();
			loginResModel = dafyInLoanService.login((LoginReqModel) reqModel);
			resp = DafyInMsgUtil.packageMsg(loginResModel);
			break;
		
		case DafyInConstant.QUERY_DAILY_AMOUNT: // 每日可借额度查询
			QueryDailyAmountResModel queryDailyAmountResModel = new QueryDailyAmountResModel();
			queryDailyAmountResModel = dafyInLoanService.dailyAvailableAmountLimit((QueryDailyAmountReqModel) reqModel);
			resp = DafyInMsgUtil.packageMsg4AmountIsNull(queryDailyAmountResModel);
			break;	
		case DafyInConstant.APPLY_LOAN: //放款
			ApplyLoanResModel applyLoanResModel = new ApplyLoanResModel();
			applyLoanResModel = dafyInLoanService.applyLoan((ApplyLoanReqModel) reqModel);
			resp = DafyInMsgUtil.packageMsg(applyLoanResModel);
			break;	
		case DafyInConstant.QUERY_LOAN_RESULT: // 放款结果查询
			QueryLoanResultResModel queryLoanResultResModel = new QueryLoanResultResModel();
			queryLoanResultResModel = dafyInLoanService.queryLoanResult((QueryLoanResultReqModel) reqModel);
			resp = DafyInMsgUtil.packageMsg(queryLoanResultResModel);
			break;		
		case DafyInConstant.PUSH_BILL: // 账单推送
			PushBillResModel pushBillResModel = new PushBillResModel();
			pushBillResModel = dafyInLoanService.pushBill((PushBillReqModel) reqModel);
			resp = DafyInMsgUtil.packageMsg(pushBillResModel);
			break;	
		case DafyInConstant.QUERY_SIGN_RESULT: // 协议签章结果查询
			QuerySignResultResModel querySignResultResModel = new QuerySignResultResModel();
			querySignResultResModel = dafyInLoanService.querySignResult((QuerySignResultReqModel) reqModel);
			resp = DafyInMsgUtil.packageMsg(querySignResultResModel);
			break;	
		case DafyInConstant.ACTIVE_REPAY_PRE: // 主动还款预下单
			ActiveRepayPreResModel activeRepayPreResModel = new ActiveRepayPreResModel();
			activeRepayPreResModel = dafyInLoanService.activeRepayPre((ActiveRepayPreReqModel) reqModel);
			resp = DafyInMsgUtil.packageMsg(activeRepayPreResModel);
			break;	
		case DafyInConstant.ACTIVE_REPAY_CONFIRM: // 主动还款确认下单
			ActiveRepayConfirmResModel activeRepayConfirmResModel = new ActiveRepayConfirmResModel();
			activeRepayConfirmResModel = dafyInLoanService.activeRepayConfirm((ActiveRepayConfirmReqModel) reqModel);
			resp = DafyInMsgUtil.packageMsg(activeRepayConfirmResModel);
			break;	
		case DafyInConstant.CUT_REPAY_CONFIRM: // 代扣还款
			CutRepayConfirmResModel cutRepayConfirmResModel = new CutRepayConfirmResModel();
			cutRepayConfirmResModel = dafyInLoanService.cutRepayConfirm((CutRepayConfirmReqModel)reqModel);
			resp = DafyInMsgUtil.packageMsg(cutRepayConfirmResModel);
			break;		
		case DafyInConstant.QUERY_REPAY_RESULT: // 还款结果查询
			QueryRepayResultResModel queryRepayResultResModel = new QueryRepayResultResModel();
			queryRepayResultResModel = dafyInLoanService.queryRepayResul((QueryRepayResultReqModel)reqModel);
			resp = DafyInMsgUtil.packageMsg(queryRepayResultResModel);
			break;	
		case DafyInConstant.LATE_REPAY: //代偿通知
			LateRepayResModel lateRepayResModel = new LateRepayResModel();
			lateRepayResModel = dafyInLoanService.lateRepay((LateRepayReqModel)reqModel);
			resp = DafyInMsgUtil.packageMsg(lateRepayResModel);
			break;	
		case DafyInConstant.FILL_FINISH: // 补账完成通知
			FillFinishResModel fillFinishResModel = new FillFinishResModel();
			fillFinishResModel = dafyInLoanService.fillFinish((FillFinishReqModel) reqModel);
			resp = DafyInMsgUtil.packageMsg(fillFinishResModel);
			break;		
		case DafyInConstant.AGREEMENT_NOTICE: //协议下载地址查询
			AgreementNoticeResModel agreementNoticeResModel = new AgreementNoticeResModel();
			agreementNoticeResModel = dafyInLoanService.agreementNotice((AgreementNoticeReqModel) reqModel);
			resp = DafyInMsgUtil.packageMsg(agreementNoticeResModel);
			break;
		case DafyInConstant.ACTIVE_REPAY_SMS_CODE_REPEAT: // 还款预下单重发验证码短信
			ActiveRepaySmsCodeRepeatResModel activeRepaySmsCodeRepeatResModel = new ActiveRepaySmsCodeRepeatResModel();
			activeRepaySmsCodeRepeatResModel = dafyInLoanService.activeRepaySmsCodeRepeat((ActiveRepaySmsCodeRepeatReqModel) reqModel);
			resp = DafyInMsgUtil.packageMsg(activeRepaySmsCodeRepeatResModel);
			break;	
		default:
			break;
		}
		// 返回响应报文（密文）
		return resp;
	}
	
	@RequestMapping("/dafyLoan/getString")
	public @ResponseBody
	String getString(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		String encryptData = request.getParameter("DATA");
		String returnCode = DafyInMsgUtil.getString(encryptData);
		return returnCode;
	}
	
	

}

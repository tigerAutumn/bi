package com.pinting.gateway.loan7.in.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
import com.pinting.gateway.loan7.in.model.BaseReqModel;
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
import com.pinting.gateway.loan7.in.service.DepLoan7Service;
import com.pinting.gateway.loan7.in.util.DepLoan7InConstant;
import com.pinting.gateway.loan7.in.util.DepLoan7InMsgUtil;

@Controller
public class DepLoan7Controller {

	private Logger log = LoggerFactory
			.getLogger(DepLoan7Controller.class);
	
	
	@Autowired
	private		DepLoan7Service 	depLoan7Service;

	@RequestMapping("/depLoan7/business")
	public @ResponseBody
	String serviceDispatch(HttpServletRequest request,
			HttpServletResponse response) {
		BaseReqModel reqModel = (BaseReqModel) request.getAttribute("reqModel");
		// 待返回报文密文
		String resp = null;
		switch (reqModel.getTransCode()) {
		case DepLoan7InConstant.LOGIN:// 7贷存管登录 处理
			LoginResModel loginResModel = new LoginResModel();
			loginResModel = depLoan7Service.login((LoginReqModel) reqModel);
			resp = DepLoan7InMsgUtil.packageMsg(loginResModel);
			break;
		
		case DepLoan7InConstant.QUERY_DAILY_AMOUNT: // 每日可借额度查询
			QueryDailyAmountResModel queryDailyAmountResModel = new QueryDailyAmountResModel();
			queryDailyAmountResModel = depLoan7Service.dailyAvailableAmountLimit((QueryDailyAmountReqModel) reqModel);
			resp = DepLoan7InMsgUtil.packageMsg4AmountIsNull(queryDailyAmountResModel);
			break;	
		case DepLoan7InConstant.APPLY_LOAN: //放款
			ApplyLoanResModel applyLoanResModel = new ApplyLoanResModel();
			applyLoanResModel = depLoan7Service.applyLoan((ApplyLoanReqModel) reqModel);
			resp = DepLoan7InMsgUtil.packageMsg(applyLoanResModel);
			break;	
		case DepLoan7InConstant.QUERY_LOAN_RESULT: // 放款结果查询
			QueryLoanResultResModel queryLoanResultResModel = new QueryLoanResultResModel();
			queryLoanResultResModel = depLoan7Service.queryLoanResult((QueryLoanResultReqModel) reqModel);
			resp = DepLoan7InMsgUtil.packageMsg(queryLoanResultResModel);
			break;		
		case DepLoan7InConstant.PUSH_BILL: // 账单推送
			PushBillResModel pushBillResModel = new PushBillResModel();
			pushBillResModel = depLoan7Service.pushBill((PushBillReqModel) reqModel);
			resp = DepLoan7InMsgUtil.packageMsg(pushBillResModel);
			break;	
		case DepLoan7InConstant.QUERY_SIGN_RESULT: // 协议签章结果查询
			QuerySignResultResModel querySignResultResModel = new QuerySignResultResModel();
			querySignResultResModel = depLoan7Service.querySignResult((QuerySignResultReqModel) reqModel);
			resp = DepLoan7InMsgUtil.packageMsg(querySignResultResModel);
			break;	
		case DepLoan7InConstant.ACTIVE_REPAY_PRE: // 主动还款预下单
			ActiveRepayPreResModel activeRepayPreResModel = new ActiveRepayPreResModel();
			activeRepayPreResModel = depLoan7Service.activeRepayPre((ActiveRepayPreReqModel) reqModel);
			resp = DepLoan7InMsgUtil.packageMsg(activeRepayPreResModel);
			break;	
		case DepLoan7InConstant.ACTIVE_REPAY_CONFIRM: // 主动还款确认下单
			ActiveRepayConfirmResModel activeRepayConfirmResModel = new ActiveRepayConfirmResModel();
			activeRepayConfirmResModel = depLoan7Service.activeRepayConfirm((ActiveRepayConfirmReqModel) reqModel);
			resp = DepLoan7InMsgUtil.packageMsg(activeRepayConfirmResModel);
			break;	
		case DepLoan7InConstant.CUT_REPAY_CONFIRM: // 代扣还款
			CutRepayConfirmResModel cutRepayConfirmResModel = new CutRepayConfirmResModel();
			cutRepayConfirmResModel = depLoan7Service.cutRepayConfirm((CutRepayConfirmReqModel)reqModel);
			resp = DepLoan7InMsgUtil.packageMsg(cutRepayConfirmResModel);
			break;		
		case DepLoan7InConstant.QUERY_REPAY_RESULT: // 还款结果查询
			QueryRepayResultResModel queryRepayResultResModel = new QueryRepayResultResModel();
			queryRepayResultResModel = depLoan7Service.queryRepayResul((QueryRepayResultReqModel)reqModel);
			resp = DepLoan7InMsgUtil.packageMsg(queryRepayResultResModel);
			break;	
		case DepLoan7InConstant.LATE_REPAY: //代偿通知
			LateRepayResModel lateRepayResModel = new LateRepayResModel();
			lateRepayResModel = depLoan7Service.lateRepay((LateRepayReqModel)reqModel);
			resp = DepLoan7InMsgUtil.packageMsg(lateRepayResModel);
			break;	
		case DepLoan7InConstant.FILL_FINISH: // 补账完成通知
			FillFinishResModel fillFinishResModel = new FillFinishResModel();
			fillFinishResModel = depLoan7Service.fillFinish((FillFinishReqModel) reqModel);
			resp = DepLoan7InMsgUtil.packageMsg(fillFinishResModel);
			break;		
		case DepLoan7InConstant.AGREEMENT_NOTICE: //协议下载地址查询
			AgreementNoticeResModel agreementNoticeResModel = new AgreementNoticeResModel();
			agreementNoticeResModel = depLoan7Service.agreementNotice((AgreementNoticeReqModel) reqModel);
			resp = DepLoan7InMsgUtil.packageMsg(agreementNoticeResModel);
			break;
		case DepLoan7InConstant.ACTIVE_REPAY_SMS_CODE_REPEAT: // 还款预下单重发验证码短信
			ActiveRepaySmsCodeRepeatResModel activeRepaySmsCodeRepeatResModel = new ActiveRepaySmsCodeRepeatResModel();
			activeRepaySmsCodeRepeatResModel = depLoan7Service.activeRepaySmsCodeRepeat((ActiveRepaySmsCodeRepeatReqModel) reqModel);
			resp = DepLoan7InMsgUtil.packageMsg(activeRepaySmsCodeRepeatResModel);
			break;	
		default:
			break;
		}
		// 返回响应报文（密文）
		return resp;
	}
	
	@RequestMapping("/depLoan7/getString")
	public @ResponseBody
	String getString(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		String encryptData = request.getParameter("DATA");
		String returnCode = DepLoan7InMsgUtil.getString(encryptData);
		return returnCode;
	}
	
	

}

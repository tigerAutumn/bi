package com.pinting.gateway.loan7.out.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.pinting.gateway.loan7.out.model.AgreementNoticeReqModel;
import com.pinting.gateway.loan7.out.model.AgreementNoticeResModel;
import com.pinting.gateway.loan7.out.model.LoanResultNoticeReqModel;
import com.pinting.gateway.loan7.out.model.LoanResultNoticeResModel;
import com.pinting.gateway.loan7.out.model.QueryBillReqModel;
import com.pinting.gateway.loan7.out.model.QueryBillResModel;
import com.pinting.gateway.loan7.out.model.RepayResultNoticeReqModel;
import com.pinting.gateway.loan7.out.model.RepayResultNoticeResModel;
import com.pinting.gateway.loan7.out.model.RevenueSettleReqModel;
import com.pinting.gateway.loan7.out.model.RevenueSettleResModel;
import com.pinting.gateway.loan7.out.model.SignResultNoticeReqModel;
import com.pinting.gateway.loan7.out.model.SignResultNoticeResModel;
import com.pinting.gateway.loan7.out.model.WaitFillReqModel;
import com.pinting.gateway.loan7.out.model.WaitFillResModel;
import com.pinting.gateway.enums.PTMessageEnum;
import com.pinting.gateway.exception.PTMessageException;
import com.pinting.gateway.loan7.out.service.SendDepLoan7Service;
import com.pinting.gateway.loan7.out.util.DepLoan7CommunicateUtil;
import com.pinting.gateway.loan7.out.util.DepLoan7OutConstant;

/**
 * 
 * @project gateway
 * @title SendDepLoan7ServiceImpl.java
 * @author Dragon & cat
 * @date 2017-12-12
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
@Service
public class SendDepLoan7ServiceImpl implements SendDepLoan7Service {

	private static Logger logger = LoggerFactory.getLogger(SendDepLoan7ServiceImpl.class);
	
	/**
	 * 账单同步
	 */
	@Override
	public QueryBillResModel queryBill(
			QueryBillReqModel reqModel) {
		String requestSeq = String.valueOf(System.currentTimeMillis());   
		reqModel.setRequestSeq(requestSeq);
		reqModel.setTransCode(DepLoan7OutConstant.QUERY_BILL);
		reqModel.setRequestTime(new Date());
		logger.info("7贷存管账单同步>>>"+JSON.toJSONString(reqModel));
		QueryBillResModel resModel = (QueryBillResModel) DepLoan7CommunicateUtil
				.doCommunicate2DepLoan7(reqModel);
		if(!DepLoan7OutConstant.RETURN_CODE_SUCCESS.equals(resModel.getRespCode())){
			throw new PTMessageException(PTMessageEnum.LOAN7_QUERY_BILL_ERROR, resModel.getRespMsg());
		}
		return resModel;
	}
	/**
	 * 待补账通知
	 */
	@Override
	public WaitFillResModel waitFill(
			WaitFillReqModel reqModel) {
		String requestSeq = String.valueOf(System.currentTimeMillis());   
		reqModel.setRequestSeq(requestSeq);
		reqModel.setTransCode(DepLoan7OutConstant.WAIT_FILL);
		reqModel.setRequestTime(new Date());
		logger.info("7贷存管待补账通知>>>"+JSON.toJSONString(reqModel));
		WaitFillResModel resModel = (WaitFillResModel) DepLoan7CommunicateUtil
				.doCommunicate2DepLoan7(reqModel);
		if(!DepLoan7OutConstant.RETURN_CODE_SUCCESS.equals(resModel.getRespCode())){
			throw new PTMessageException(PTMessageEnum.LOAN7_WAIT_FILL_ERROR, resModel.getRespMsg());
		}
		return resModel;
	}
	/**
	 * 营收结算通知
	 */
	@Override
	public RevenueSettleResModel revenueSettle(
			RevenueSettleReqModel reqModel) {
		String requestSeq = String.valueOf(System.currentTimeMillis());   
		reqModel.setRequestSeq(requestSeq);
		reqModel.setTransCode(DepLoan7OutConstant.REVENUE_SETTLE);
		reqModel.setRequestTime(new Date());
		logger.info("7贷存管营收结算通知>>>"+JSON.toJSONString(reqModel));
		RevenueSettleResModel resModel = (RevenueSettleResModel) DepLoan7CommunicateUtil
				.doCommunicate2DepLoan7(reqModel);
		if(!DepLoan7OutConstant.RETURN_CODE_SUCCESS.equals(resModel.getRespCode())){
			throw new PTMessageException(PTMessageEnum.LOAN7_REVENUE_SETTLE_ERROR, resModel.getRespMsg());
		}
		return resModel;
	}
	
	/**
	 * 放款结果通知
	 */
	@Override
	public LoanResultNoticeResModel loanResultNotice(
			LoanResultNoticeReqModel reqModel) {
		String requestSeq = String.valueOf(System.currentTimeMillis());   
		reqModel.setRequestSeq(requestSeq);
		reqModel.setTransCode(DepLoan7OutConstant.LOAN_RESULT_NOTICE);
		reqModel.setRequestTime(new Date());
		logger.info("7贷存管放款结果通知>>>"+JSON.toJSONString(reqModel));
		LoanResultNoticeResModel resModel = (LoanResultNoticeResModel) DepLoan7CommunicateUtil
				.doCommunicate2DepLoan7(reqModel);
		if(!DepLoan7OutConstant.RETURN_CODE_SUCCESS.equals(resModel.getRespCode())){
			throw new PTMessageException(PTMessageEnum.LOAN7_LOAN_RESULT_NOTICE_ERROR, resModel.getRespMsg());
		}
		return resModel;
	}
	/**
	 * 借款协议签章结果通知
	 */
	@Override
	public SignResultNoticeResModel signResultNotice(
			SignResultNoticeReqModel reqModel) {
		String requestSeq = String.valueOf(System.currentTimeMillis());   
		reqModel.setRequestSeq(requestSeq);
		reqModel.setTransCode(DepLoan7OutConstant.SIGN_RESULT_NOTICE);
		reqModel.setRequestTime(new Date());
		logger.info("7贷存管款协议签章结果通知>>>"+JSON.toJSONString(reqModel));
		SignResultNoticeResModel resModel = (SignResultNoticeResModel) DepLoan7CommunicateUtil
				.doCommunicate2DepLoan7(reqModel);
		if(!DepLoan7OutConstant.RETURN_CODE_SUCCESS.equals(resModel.getRespCode())){
			throw new PTMessageException(PTMessageEnum.LOAN7_SIGN_RESULT_NOTICE_ERROR, resModel.getRespMsg());
		}
		return resModel;
	}
	
	/**
	 * 还款结果通知
	 */
	@Override
	public RepayResultNoticeResModel repayResultNotice(
			RepayResultNoticeReqModel req) {
		String requestSeq = String.valueOf(System.currentTimeMillis());   
		req.setRequestSeq(requestSeq);
		req.setTransCode(DepLoan7OutConstant.REPAY_RESULT_NOTICE);
		req.setRequestTime(new Date());
		logger.info("7贷存管还款结果通知>>>"+JSON.toJSONString(req));
		RepayResultNoticeResModel res = (RepayResultNoticeResModel)DepLoan7CommunicateUtil
				.doCommunicate2DepLoan7(req);
		if(!DepLoan7OutConstant.RETURN_CODE_SUCCESS.equals(res.getRespCode())){
			throw new PTMessageException(PTMessageEnum.LOAN7_REPAY_RESULT_NOTICE_ERROR, res.getRespMsg());
		}
		return res;
	}
	
	/**
	 * 协议下载通知
	 */
	@Override
	public AgreementNoticeResModel agreementNotice(AgreementNoticeReqModel req) {
		String requestSeq = String.valueOf(System.currentTimeMillis());   
		req.setRequestSeq(requestSeq);
		req.setTransCode(DepLoan7OutConstant.AGREEMENT_NOTICE);
		req.setRequestTime(new Date());
		logger.info("7贷存管协议下载通知>>>"+JSON.toJSONString(req));
		AgreementNoticeResModel res = (AgreementNoticeResModel)DepLoan7CommunicateUtil
				.doCommunicate2DepLoan7(req);
		if(!DepLoan7OutConstant.RETURN_CODE_SUCCESS.equals(res.getRespCode())){
			throw new PTMessageException(PTMessageEnum.LOAN7_AGREEMENT_NOTICE_FAIL, res.getRespMsg());
		}
		return res;
	}

}

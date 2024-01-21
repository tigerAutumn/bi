package com.pinting.gateway.business.in.facade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.pinting.gateway.hessian.message.loan7.model.Loan7QueryBillRepayment;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.core.util.MoneyUtil;
import com.pinting.gateway.hessian.message.loan7.B2GReqMsg_DepLoan7Notice_AgreementNotice;
import com.pinting.gateway.hessian.message.loan7.B2GReqMsg_DepLoan7Notice_LoanResultNotice;
import com.pinting.gateway.hessian.message.loan7.B2GReqMsg_DepLoan7Notice_QueryBill;
import com.pinting.gateway.hessian.message.loan7.B2GReqMsg_DepLoan7Notice_RepayResultNotice;
import com.pinting.gateway.hessian.message.loan7.B2GReqMsg_DepLoan7Notice_RevenueSettle;
import com.pinting.gateway.hessian.message.loan7.B2GReqMsg_DepLoan7Notice_SignResultNotice;
import com.pinting.gateway.hessian.message.loan7.B2GReqMsg_DepLoan7Notice_WaitFill;
import com.pinting.gateway.hessian.message.loan7.B2GResMsg_DepLoan7Notice_AgreementNotice;
import com.pinting.gateway.hessian.message.loan7.B2GResMsg_DepLoan7Notice_LoanResultNotice;
import com.pinting.gateway.hessian.message.loan7.B2GResMsg_DepLoan7Notice_QueryBill;
import com.pinting.gateway.hessian.message.loan7.B2GResMsg_DepLoan7Notice_RepayResultNotice;
import com.pinting.gateway.hessian.message.loan7.B2GResMsg_DepLoan7Notice_RevenueSettle;
import com.pinting.gateway.hessian.message.loan7.B2GResMsg_DepLoan7Notice_SignResultNotice;
import com.pinting.gateway.hessian.message.loan7.B2GResMsg_DepLoan7Notice_WaitFill;
import com.pinting.gateway.hessian.message.loan7.model.Repayments;
import com.pinting.gateway.hessian.message.loan7.model.Lenders;
import com.pinting.gateway.loan7.out.model.AgreementNoticeReqModel;
import com.pinting.gateway.loan7.out.model.LoanResultNoticeReqModel;
import com.pinting.gateway.loan7.out.model.QueryBillReqModel;
import com.pinting.gateway.loan7.out.model.QueryBillResModel;
import com.pinting.gateway.loan7.out.model.RepayResultNoticeReqModel;
import com.pinting.gateway.loan7.out.model.RevenueSettleReqModel;
import com.pinting.gateway.loan7.out.model.SignResultNoticeReqModel;
import com.pinting.gateway.loan7.out.model.WaitFillReqModel;
import com.pinting.gateway.loan7.out.service.SendDepLoan7Service;

/**
 * 7贷自主放款相关通知
 * @author SHENGUOPING
 * @date  2017年12月13日 上午11:28:55
 */
@Component("DepLoan7Notice")
public class DepLoan7NoticeFacade {

	@Autowired
    private SendDepLoan7Service sendDepLoan7Service;
	

	/**
	 * 7贷自主-还款结果通知
	 */
	public void repayResultNotice(B2GReqMsg_DepLoan7Notice_RepayResultNotice req,
			B2GResMsg_DepLoan7Notice_RepayResultNotice res) {
		RepayResultNoticeReqModel reqModel = new RepayResultNoticeReqModel();
		reqModel.setBgwOrderNo(req.getBgwOrderNo());
		reqModel.setChannel(req.getChannelLoan());
		reqModel.setFinishTime(req.getFinishTime());
		reqModel.setOrderNo(req.getOrderNo());
		reqModel.setResultCode(req.getResultCode());
		reqModel.setResultMsg(req.getResultMsg());
		sendDepLoan7Service.repayResultNotice(reqModel);
	}

	/**
	 * 协议下载通知
	 * 
	 * @param req
	 * @param res
	 */
	public void agreementNotice(B2GReqMsg_DepLoan7Notice_AgreementNotice req,
			B2GResMsg_DepLoan7Notice_AgreementNotice res) {
		AgreementNoticeReqModel reqModel = new AgreementNoticeReqModel();
		reqModel.setAgreements(req.getAgreements());
		reqModel.setOrderNo(req.getOrderNo());
		sendDepLoan7Service.agreementNotice(reqModel);
	}

	/**
	 * 账单同步
	 * 
	 * @param req
	 * @param res
	 * @throws Exception
	 */
	public void queryBill(B2GReqMsg_DepLoan7Notice_QueryBill req,
			B2GResMsg_DepLoan7Notice_QueryBill res) throws Exception {

		QueryBillReqModel queryBillReqModel = new QueryBillReqModel();
		queryBillReqModel.setUserId(req.getUserId());
		queryBillReqModel.setLoanId(req.getLoanId());
		QueryBillResModel queryBillResModel = sendDepLoan7Service.queryBill(queryBillReqModel);

		res.setUserId(queryBillResModel.getUserId());
		res.setLoanId(queryBillResModel.getLoanId());
		res.setAgreementNo(queryBillResModel.getAgreementNo());
		res.setAgreementUrl(queryBillResModel.getAgreementUrl());
		List<Loan7QueryBillRepayment> list = new ArrayList<Loan7QueryBillRepayment>();
		for (Repayments repayment : queryBillResModel.getRepayments()) {
			Loan7QueryBillRepayment map = new Loan7QueryBillRepayment();
			map.setRepayId(repayment.getRepayId());
			map.setStatus(repayment.getStatus());
			map.setRepayDate(repayment.getRepayDate());
			map.setRepaySerial(repayment.getRepaySerial());
			map.setTotal(MoneyUtil.divide(repayment.getTotal(), 100).doubleValue());
			map.setPrincipal(MoneyUtil.divide(repayment.getPrincipal(), 100).doubleValue());
			map.setInterest(MoneyUtil.divide(repayment.getInterest(), 100).doubleValue());
			map.setPrincipalOverdue(repayment.getPrincipalOverdue() != null ? MoneyUtil.divide(repayment.getPrincipalOverdue(), 100).doubleValue():0);
			map.setInterestOverdue(repayment.getInterestOverdue() != null ? MoneyUtil.divide(repayment.getInterestOverdue(), 100).doubleValue():0);
			map.setReservedField1(repayment.getReservedField1());
			map.setBgwOrderNo(repayment.getBgwOrderNo());
			list.add(map);
		}
		res.setRepayments(list);
	}

	/**
	 * 待补账通知
	 * 
	 * @param req
	 * @param res
	 * @throws Exception
	 */
	public void waitFill(B2GReqMsg_DepLoan7Notice_WaitFill req,
			B2GResMsg_DepLoan7Notice_WaitFill res) throws Exception {
		WaitFillReqModel waitFillReqModel = new WaitFillReqModel();
		waitFillReqModel.setOrderNo(req.getOrderNo());
		waitFillReqModel.setFillDate(req.getFillDate());
		waitFillReqModel.setFillType(req.getFillType());
		waitFillReqModel.setAmount(MoneyUtil.multiply(req.getAmount(), 100)
				.longValue());
		waitFillReqModel.setFileUrl(req.getFileUrl());
		sendDepLoan7Service.waitFill(waitFillReqModel);
	}

	/**
	 * 营收结算通知
	 * 
	 * @param req
	 * @param res
	 * @throws Exception
	 */
	public void revenueSettle(B2GReqMsg_DepLoan7Notice_RevenueSettle req,
			B2GResMsg_DepLoan7Notice_RevenueSettle res) throws Exception {
		RevenueSettleReqModel revenueSettleReqModel = new RevenueSettleReqModel();
		revenueSettleReqModel.setOrderNo(req.getOrderNo());
		revenueSettleReqModel.setApplyTime(req.getApplyTime());
		revenueSettleReqModel.setFinishTime(req.getFinishTime());
		revenueSettleReqModel.setSettleType(req.getSettleType());
		revenueSettleReqModel.setAmount(MoneyUtil
				.multiply(req.getAmount(), 100).longValue());
		revenueSettleReqModel.setFileUrl(req.getFileUrl());
		sendDepLoan7Service.revenueSettle(revenueSettleReqModel);
	}

	/**
	 * 放款结果通知
	 * 
	 * @param req
	 * @param res
	 * @throws Exception
	 */
	public void loanResultNotice(B2GReqMsg_DepLoan7Notice_LoanResultNotice req,
			B2GResMsg_DepLoan7Notice_LoanResultNotice res) throws Exception {

		LoanResultNoticeReqModel loanResultNoticeReqModel = new LoanResultNoticeReqModel();
		loanResultNoticeReqModel.setOrderNo(req.getOrderNo());
		loanResultNoticeReqModel.setBgwOrderNo(req.getBgwOrderNo());
		loanResultNoticeReqModel.setAgreementNo(req.getAgreementNo());
		loanResultNoticeReqModel.setLoanServiceRate(req.getLoanServiceRate());
		loanResultNoticeReqModel.setLoanId(req.getLoanId());
		loanResultNoticeReqModel.setChannel(req.getPayChannel());
		loanResultNoticeReqModel.setResultCode(req.getResultCode());
		loanResultNoticeReqModel.setResultMsg(req.getResultMsg());
		loanResultNoticeReqModel.setFinishTime(req.getFinishTime());
		
		List<Lenders> lenderList = new ArrayList<Lenders>();
		if (CollectionUtils.isNotEmpty(req.getLenders())) {			
			for (HashMap<String, Object> lenderMap : req.getLenders()) {
				Lenders lenders = new Lenders();
				lenders.setInvestAmount(MoneyUtil.multiply(
						(Double) lenderMap.get("investAmount"), 100).longValue());
				lenders.setLenderName((String) lenderMap.get("lenderName"));
				lenders.setLenderIdcard((String) lenderMap.get("lenderIdcard"));
				lenders.setMobile((String) lenderMap.get("mobile"));
				lenderList.add(lenders);
			}
		}
		loanResultNoticeReqModel.setLenders(lenderList);
		sendDepLoan7Service.loanResultNotice(loanResultNoticeReqModel);
	}

	/**
	 * 借款协议签章结果通知
	 * 
	 * @param req
	 * @param res
	 * @throws Exception
	 */
	public void signResultNotice(B2GReqMsg_DepLoan7Notice_SignResultNotice req,
			B2GResMsg_DepLoan7Notice_SignResultNotice res) throws Exception {
		SignResultNoticeReqModel signResultNoticeReqModel = new SignResultNoticeReqModel();
		signResultNoticeReqModel.setAgreementNo(req.getAgreementNo());
		signResultNoticeReqModel.setLoanId(req.getLoanId());
		signResultNoticeReqModel.setSignResult(req.getSignResult());
		signResultNoticeReqModel.setAgreementUrl(req.getAgreementUrl());
		/*
		 * List<SignResultNoticeLender> list = new
		 * ArrayList<SignResultNoticeLender>(); if (req.getLenders() != null ) {
		 * for (SignResultNoticeLenderModel signResultNoticeLender :
		 * req.getLenders()) { SignResultNoticeLender lender = new
		 * SignResultNoticeLender();
		 * lender.setLenderName(signResultNoticeLender.getLenderName());
		 * lender.setLenderIdcard(signResultNoticeLender.getLenderIdcard());
		 * lender.setInvestAmount(MoneyUtil.multiply(signResultNoticeLender.
		 * getInvestAmount(), 100).longValue()); list.add(lender); } }
		 * signResultNoticeReqModel.setLenders(list);
		 */
		sendDepLoan7Service.signResultNotice(signResultNoticeReqModel);
	}

}

package com.pinting.gateway.business.in.facade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.gateway.dafy.out.model.AgreementNoticeReqModel;
import com.pinting.core.util.MoneyUtil;
import com.pinting.gateway.dafy.out.model.LoanResultNoticeReqModel;
import com.pinting.gateway.dafy.out.model.LoanResultNoticeResModel;
import com.pinting.gateway.dafy.out.model.QueryBillReqModel;
import com.pinting.gateway.dafy.out.model.QueryBillResModel;
import com.pinting.gateway.dafy.out.model.RepayResultNoticeReqModel;
import com.pinting.gateway.dafy.out.model.RevenueSettleReqModel;
import com.pinting.gateway.dafy.out.model.RevenueSettleResModel;
import com.pinting.gateway.dafy.out.model.SignResultNoticeLender;
import com.pinting.gateway.dafy.out.model.SignResultNoticeReqModel;
import com.pinting.gateway.dafy.out.model.SignResultNoticeResModel;
import com.pinting.gateway.dafy.out.model.WaitFillReqModel;
import com.pinting.gateway.dafy.out.model.WaitFillResModel;
import com.pinting.gateway.dafy.out.service.DafyLoanService;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_DafyLoanNotice_AgreementNotice;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_DafyLoanNotice_LoanResultNotice;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_DafyLoanNotice_QueryBill;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_DafyLoanNotice_RevenueSettle;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_DafyLoanNotice_SignResultNotice;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_DafyLoanNotice_WaitFill;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_DafyLoanNotice_LoanResultNotice;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_DafyLoanNotice_QueryBill;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_DafyLoanNotice_RevenueSettle;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_DafyLoanNotice_SignResultNotice;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_DafyLoanNotice_WaitFill;
import com.pinting.gateway.hessian.message.dafy.model.Lenders;
import com.pinting.gateway.hessian.message.dafy.model.QueryBillRepayment;
import com.pinting.gateway.hessian.message.dafy.model.Repayments;
import com.pinting.gateway.hessian.message.dafy.model.SignResultNoticeLenderModel;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_DafyLoanNotice_RepayResultNotice;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_DafyLoanNotice_AgreementNotice;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_DafyLoanNotice_RepayResultNotice;

/**
 * 达飞云贷自主放款相关通知
 * @author bianyatian
 * @2016-12-15 下午4:31:42
 */
@Component("DafyLoanNotice")
public class DafyLoanNoticeFacade {

	@Autowired
    private DafyLoanService dafyLoanService;
	
	  /**
     * 云贷-自主-还款结果通知
     */
    public void repayResultNotice(B2GReqMsg_DafyLoanNotice_RepayResultNotice req, B2GResMsg_DafyLoanNotice_RepayResultNotice res){
    	RepayResultNoticeReqModel reqModel = new RepayResultNoticeReqModel();
    	reqModel.setBgwOrderNo(req.getBgwOrderNo());
    	reqModel.setChannel(req.getChannelLoan());
    	reqModel.setFinishTime(req.getFinishTime());
    	reqModel.setOrderNo(req.getOrderNo());
    	reqModel.setResultCode(req.getResultCode());
    	reqModel.setResultMsg(req.getResultMsg());
    	
    	dafyLoanService.repayResultNotice(reqModel);
    }

    /**
     * 协议下载通知
     * @param req
     * @param res
     */
    public void agreementNotice(B2GReqMsg_DafyLoanNotice_AgreementNotice req, B2GResMsg_DafyLoanNotice_AgreementNotice res){
    	AgreementNoticeReqModel reqModel = new AgreementNoticeReqModel();
    	reqModel.setAgreements(req.getAgreements());
    	reqModel.setOrderNo(req.getOrderNo());
    	dafyLoanService.agreementNotice(reqModel);
    }
    

    /**
     * 账单同步
     * @param req
     * @param res
     * @throws Exception
     */
    public void queryBill(B2GReqMsg_DafyLoanNotice_QueryBill req, B2GResMsg_DafyLoanNotice_QueryBill res) throws Exception {

    	QueryBillReqModel queryBillReqModel = new QueryBillReqModel();
    	queryBillReqModel.setUserId(req.getUserId());
    	queryBillReqModel.setLoanId(req.getLoanId());
    	QueryBillResModel queryBillResModel =  dafyLoanService.queryBill(queryBillReqModel);
    	
    	res.setUserId(queryBillResModel.getUserId());
    	res.setLoanId(queryBillResModel.getLoanId());
    	res.setAgreementNo(queryBillResModel.getAgreementNo());
    	res.setAgreementUrl(queryBillResModel.getAgreementUrl());
    	List<QueryBillRepayment> list = new ArrayList<QueryBillRepayment>();
    	for (Repayments repayment : queryBillResModel.getRepayments()) {
			QueryBillRepayment map = new QueryBillRepayment();
			map.setRepayId(repayment.getRepayId());
			map.setStatus(repayment.getStatus());
			map.setRepayDate(repayment.getRepayDate());
			map.setRepaySerial(repayment.getRepaySerial());
			map.setTotal(MoneyUtil.divide(repayment.getTotal(), 100).doubleValue());
			map.setPrincipal(MoneyUtil.divide(repayment.getPrincipal(), 100).doubleValue());
			map.setInterest(MoneyUtil.divide(repayment.getInterest(), 100).doubleValue());
			map.setPrincipalOverdue(MoneyUtil.divide(repayment.getPrincipalOverdue(), 100).doubleValue());
			map.setInterestOverdue(MoneyUtil.divide(repayment.getInterestOverdue(), 100).doubleValue());
			map.setReservedField1(repayment.getReservedField1());
			map.setBgwOrderNo(repayment.getBgwOrderNo());
			list.add(map);
		}
    	res.setRepayments(list);
    }
    
    /**
     * 待补账通知
     * @param req
     * @param res
     * @throws Exception
     */
    public void waitFill(B2GReqMsg_DafyLoanNotice_WaitFill req, B2GResMsg_DafyLoanNotice_WaitFill res) throws Exception {
    	WaitFillReqModel waitFillReqModel = new WaitFillReqModel();
    	waitFillReqModel.setOrderNo(req.getOrderNo());
    	waitFillReqModel.setFillDate(req.getFillDate());
    	waitFillReqModel.setFillType(req.getFillType());
    	waitFillReqModel.setAmount(MoneyUtil.multiply(req.getAmount(), 100).longValue());
    	waitFillReqModel.setFileUrl(req.getFileUrl());
    	WaitFillResModel waitFillResModel = dafyLoanService.waitFill(waitFillReqModel);
    }
    
    /**
     * 营收结算通知
     * @param req
     * @param res
     * @throws Exception
     */
    public void revenueSettle(B2GReqMsg_DafyLoanNotice_RevenueSettle req, B2GResMsg_DafyLoanNotice_RevenueSettle res) throws Exception {
    	RevenueSettleReqModel revenueSettleReqModel = new RevenueSettleReqModel();
    	revenueSettleReqModel.setOrderNo(req.getOrderNo());
    	revenueSettleReqModel.setApplyTime(req.getApplyTime());
    	revenueSettleReqModel.setFinishTime(req.getFinishTime());
    	revenueSettleReqModel.setSettleType(req.getSettleType());
    	revenueSettleReqModel.setAmount(MoneyUtil.multiply(req.getAmount(), 100).longValue());
    	revenueSettleReqModel.setFileUrl(req.getFileUrl());
    	RevenueSettleResModel revenueSettleResModel = dafyLoanService.revenueSettle(revenueSettleReqModel);
    }
    
    
    /**
     * 放款结果通知
     * @param req
     * @param res
     * @throws Exception
     */
    public void loanResultNotice(B2GReqMsg_DafyLoanNotice_LoanResultNotice req, B2GResMsg_DafyLoanNotice_LoanResultNotice res) throws Exception {
    	
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
		for (HashMap<String, Object> lenderMap : req.getLenders()) {
			Lenders lenders = new Lenders();
			lenders.setInvestAmount(MoneyUtil.multiply((Double)lenderMap.get("investAmount"), 100).longValue());
			lenders.setLenderName((String)lenderMap.get("lenderName"));
			lenders.setLenderIdcard((String)lenderMap.get("lenderIdcard"));
			lenders.setMobile((String)lenderMap.get("mobile"));
			lenderList.add(lenders);
		}
		loanResultNoticeReqModel.setLenders(lenderList);
    	
    	LoanResultNoticeResModel revenueSettleResModel = dafyLoanService.loanResultNotice(loanResultNoticeReqModel);
    }
    
    
    
    /**
     * 借款协议签章结果通知
     * @param req
     * @param res
     * @throws Exception
     */
    public void signResultNotice(B2GReqMsg_DafyLoanNotice_SignResultNotice req, B2GResMsg_DafyLoanNotice_SignResultNotice res) throws Exception {
    	SignResultNoticeReqModel signResultNoticeReqModel = new SignResultNoticeReqModel();
    	signResultNoticeReqModel.setAgreementNo(req.getAgreementNo());
    	signResultNoticeReqModel.setLoanId(req.getLoanId());
    	signResultNoticeReqModel.setSignResult(req.getSignResult());
    	signResultNoticeReqModel.setAgreementUrl(req.getAgreementUrl());
    	/*List<SignResultNoticeLender> list = new ArrayList<SignResultNoticeLender>();
    	if (req.getLenders() != null ) {
    		for (SignResultNoticeLenderModel signResultNoticeLender : req.getLenders()) {
        		SignResultNoticeLender lender = new SignResultNoticeLender();
        		lender.setLenderName(signResultNoticeLender.getLenderName());
        		lender.setLenderIdcard(signResultNoticeLender.getLenderIdcard());
        		lender.setInvestAmount(MoneyUtil.multiply(signResultNoticeLender.getInvestAmount(), 100).longValue());
        		list.add(lender);
        	}
		}
    	signResultNoticeReqModel.setLenders(list);*/
    	SignResultNoticeResModel signResultNoticeResModel = dafyLoanService.signResultNotice(signResultNoticeReqModel);
    }

}

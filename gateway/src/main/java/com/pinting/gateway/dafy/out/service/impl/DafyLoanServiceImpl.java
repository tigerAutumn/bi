package com.pinting.gateway.dafy.out.service.impl;

import com.alibaba.fastjson.JSON;
import com.pinting.gateway.dafy.out.model.*;
import com.pinting.gateway.dafy.out.service.DafyLoanService;
import com.pinting.gateway.dafy.out.util.DafyOutConstant;
import com.pinting.gateway.enums.PTMessageEnum;
import com.pinting.gateway.exception.PTMessageException;
import com.pinting.gateway.util.CommunicateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 自主放款相关业务，向达飞发起请求 实现类接口
 * @author Dragon & cat
 * @date 2016-11-24
 */
@Service
public class DafyLoanServiceImpl implements DafyLoanService {

	private static Logger logger = LoggerFactory.getLogger(DafyLoanServiceImpl.class);
	
	/**
	 * 账单同步
	 */
	@Override
	public QueryBillResModel queryBill(
			QueryBillReqModel reqModel) {
		String requestSeq = String.valueOf(System.currentTimeMillis());   
		reqModel.setRequestSeq(requestSeq);
		reqModel.setTransCode(DafyOutConstant.QUERY_BILL);
		reqModel.setRequestTime(new Date());
		logger.info("自主放款账单同步>>>"+JSON.toJSONString(reqModel));
		QueryBillResModel resModel = (QueryBillResModel) CommunicateUtil
				.doCommunicate2DafyLoan(reqModel);
		if(!DafyOutConstant.RETURN_CODE_SUCCESS.equals(resModel.getRespCode())){
			throw new PTMessageException(PTMessageEnum.DAFY_QUERY_BILL_ERROR, resModel.getRespMsg());
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
		reqModel.setTransCode(DafyOutConstant.WAIT_FILL);
		reqModel.setRequestTime(new Date());
		logger.info("自主放款待补账通知>>>"+JSON.toJSONString(reqModel));
		WaitFillResModel resModel = (WaitFillResModel) CommunicateUtil
				.doCommunicate2DafyLoan(reqModel);
		if(!DafyOutConstant.RETURN_CODE_SUCCESS.equals(resModel.getRespCode())){
			throw new PTMessageException(PTMessageEnum.DAFY_WAIT_FILL_ERROR, resModel.getRespMsg());
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
		reqModel.setTransCode(DafyOutConstant.REVENUE_SETTLE);
		reqModel.setRequestTime(new Date());
		logger.info("自主放款营收结算通知>>>"+JSON.toJSONString(reqModel));
		RevenueSettleResModel resModel = (RevenueSettleResModel) CommunicateUtil
				.doCommunicate2DafyLoan(reqModel);
		if(!DafyOutConstant.RETURN_CODE_SUCCESS.equals(resModel.getRespCode())){
			throw new PTMessageException(PTMessageEnum.DAFY_REVENUE_SETTLE_ERROR, resModel.getRespMsg());
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
		reqModel.setTransCode(DafyOutConstant.LOAN_RESULT_NOTICE);
		reqModel.setRequestTime(new Date());
		logger.info("自主放款放款结果通知>>>"+JSON.toJSONString(reqModel));
		LoanResultNoticeResModel resModel = (LoanResultNoticeResModel) CommunicateUtil
				.doCommunicate2DafyLoan(reqModel);
		if(!DafyOutConstant.RETURN_CODE_SUCCESS.equals(resModel.getRespCode())){
			throw new PTMessageException(PTMessageEnum.DAFY_LOAN_RESULT_NOTICE_FAIL, resModel.getRespMsg());
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
		reqModel.setTransCode(DafyOutConstant.SIGN_RESULT_NOTICE);
		reqModel.setRequestTime(new Date());
		logger.info("自主放款款协议签章结果通知>>>"+JSON.toJSONString(reqModel));
		SignResultNoticeResModel resModel = (SignResultNoticeResModel) CommunicateUtil
				.doCommunicate2DafyLoan(reqModel);
		if(!DafyOutConstant.RETURN_CODE_SUCCESS.equals(resModel.getRespCode())){
			throw new PTMessageException(PTMessageEnum.DAFY_SIGN_RESULT_NOTICE_ERROR, resModel.getRespMsg());
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
		req.setTransCode(DafyOutConstant.REPAY_RESULT_NOTICE);
		req.setRequestTime(new Date());
		logger.info("自主放款还款结果通知>>>"+JSON.toJSONString(req));
		RepayResultNoticeResModel res = (RepayResultNoticeResModel)CommunicateUtil
				.doCommunicate2DafyLoan(req);
		if(!DafyOutConstant.RETURN_CODE_SUCCESS.equals(res.getRespCode())){
			throw new PTMessageException(PTMessageEnum.DAFY_REPAY_RESULT_NOTICE_FAIL, res.getRespMsg());
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
		req.setTransCode(DafyOutConstant.AGREEMENT_NOTICE);
		req.setRequestTime(new Date());
		logger.info("自主放款协议下载通知>>>"+JSON.toJSONString(req));
		AgreementNoticeResModel res = (AgreementNoticeResModel)CommunicateUtil
				.doCommunicate2DafyLoan(req);
		if(!DafyOutConstant.RETURN_CODE_SUCCESS.equals(res.getRespCode())){
			throw new PTMessageException(PTMessageEnum.DAFY_AGREEMENT_NOTICE_FAIL, res.getRespMsg());
		}
		return res;
	}

}

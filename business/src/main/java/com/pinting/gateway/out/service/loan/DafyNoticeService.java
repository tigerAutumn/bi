package com.pinting.gateway.out.service.loan;

import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_DafyLoanNotice_AgreementNotice;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_DafyLoanNotice_LoanResultNotice;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_DafyLoanNotice_QueryBill;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_DafyLoanNotice_RepayResultNotice;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_DafyLoanNotice_RevenueSettle;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_DafyLoanNotice_SignResultNotice;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_DafyLoanNotice_WaitFill;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_DafyLoanNotice_AgreementNotice;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_DafyLoanNotice_LoanResultNotice;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_DafyLoanNotice_QueryBill;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_DafyLoanNotice_RepayResultNotice;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_DafyLoanNotice_RevenueSettle;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_DafyLoanNotice_SignResultNotice;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_DafyLoanNotice_WaitFill;

public interface DafyNoticeService {
    /**
     * 云贷自主放款-借款通知
     * @param req
     * @return
     */
	B2GResMsg_DafyLoanNotice_LoanResultNotice noticeLoan(B2GReqMsg_DafyLoanNotice_LoanResultNotice req);

	/**
     * 云贷自主放款-还款通知
     * @param req
     * @return
     */
	B2GResMsg_DafyLoanNotice_RepayResultNotice noticeRepay(B2GReqMsg_DafyLoanNotice_RepayResultNotice req);

	/**
	 * 云贷自主放款-营收结算通知
	 * @param req
	 * @return
	 */
	B2GResMsg_DafyLoanNotice_RevenueSettle noticeRevenueSettle(B2GReqMsg_DafyLoanNotice_RevenueSettle req);

	/**
	 * 云贷自主放款 - 待补账通知
	 * @param req
	 * @return
	 */
	B2GResMsg_DafyLoanNotice_WaitFill noticeWaitFill(B2GReqMsg_DafyLoanNotice_WaitFill req);
	
	/**
	 * 云贷自主放款 - 借款四方协议签章
	 * @param req
	 * @return
	 */
	B2GResMsg_DafyLoanNotice_SignResultNotice signResultNotice(B2GReqMsg_DafyLoanNotice_SignResultNotice req);
	
	/**
	 * 云贷自主放款 - 账单同步查询
	 * @param req
	 * @return
	 */
	B2GResMsg_DafyLoanNotice_QueryBill queryBill(B2GReqMsg_DafyLoanNotice_QueryBill req);

	/**
	 * 云贷自主放款 - 代偿协议下载地址通知
	 * @param req
	 * @return
	 */
	B2GResMsg_DafyLoanNotice_AgreementNotice agreementNotice(
			B2GReqMsg_DafyLoanNotice_AgreementNotice req);

	
}

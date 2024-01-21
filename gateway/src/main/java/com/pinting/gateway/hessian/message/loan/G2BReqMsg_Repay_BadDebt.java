package com.pinting.gateway.hessian.message.loan;

import java.util.List;

import com.pinting.core.hessian.msg.ReqMsg;
import com.pinting.gateway.hessian.message.loan.model.Repayment;

public class G2BReqMsg_Repay_BadDebt extends ReqMsg {

    /**
	 * 
	 */
	private static final long serialVersionUID = 8879629994284572573L;


	/**
     * 坏账处理订单号
     */
    private String orderNo;


    /**
     * 用户编号
     */
    private String userId;

    /**
     * 借款编号
     */
    private String loanId;

    /**
     * 还款列表
     */
    private List<Repayment> repayments;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

	public List<Repayment> getRepayments() {
		return repayments;
	}

	public void setRepayments(List<Repayment> repayments) {
		this.repayments = repayments;
	}
    
    
    
}

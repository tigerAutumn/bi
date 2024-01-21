package com.pinting.gateway.bird.in.model;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.pinting.gateway.hessian.message.loan.model.Repayment;

/**
 * Created by 剑钊 on 2016/8/10.
 * 坏账处理
 */
public class BadDebtReq extends BaseReqModel {

    /**
     * 坏账处理订单号
     */
	@NotEmpty(message = "订单号不能为空")
    private String orderNo;

    /**
     * 用户编号
     */
	@NotEmpty(message = "用户编号不能为空")
    private String userId;

    /**
     * 借款编号
     */
	@NotEmpty(message = "借款编号不能为空")
    private String loanId;

    /**
     * 还款列表JSON
     */
    @NotNull(message = "还款列表不能为空")
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

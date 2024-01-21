package com.pinting.gateway.zsd.in.model;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.pinting.gateway.hessian.message.loan.model.LateRepayment;

import javax.validation.constraints.NotNull;

public class ZsdLateRepayReq extends BaseReqModel {

    /**
     * 代偿单号
     */
    @NotEmpty(message = "代偿单号不能为空")
    private String orderNo;
    /**
     * 支付单号
     * */
    @NotEmpty(message = "支付单号不能为空")
    private String payOrderNo;
    /**
     * 划拨申请时间
     */
    @NotEmpty(message = "划拨申请时间不能为空")
    private String applyTime;

    /**
     * 划拨完成时间
     */
    @NotEmpty(message = "划拨完成时间不能为空")
    private String finishTime;
    
    /**
     * 代偿总金额
     */
    @NotEmpty(message = "代偿总金额不能为空")
    private String totalAmount;
    
    /**
     * 代偿循环列表
     */
    @NotNull(message = "代偿循环列表不能为空")
    private List<LateRepayment> repayments;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getPayOrderNo() {
		return payOrderNo;
	}

	public void setPayOrderNo(String payOrderNo) {
		this.payOrderNo = payOrderNo;
	}

	public String getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}

	public String getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public List<LateRepayment> getRepayments() {
		return repayments;
	}

	public void setRepayments(List<LateRepayment> repayments) {
		this.repayments = repayments;
	}
	
}
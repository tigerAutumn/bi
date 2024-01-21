package com.pinting.gateway.zsd.in.model;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.pinting.gateway.hessian.message.loan.model.Repayment;

import javax.validation.constraints.NotNull;

public class ZsdCutpaymentReq extends BaseReqModel {

    /**
     * 还款订单号
     */
    @NotEmpty(message = "还款订单号不能为空")
    private String orderNo;

    /**
     * 用户编号
     */
    @NotEmpty(message = "用户编号不能为空")
    private String userId;

    /**
     * 绑卡编号
     */
    private String bindId;
    
    /**
     * 借款编号
     */
    @NotEmpty(message = "借款编号不能为空")
    private String loanId;
    
    /**
     * 银行类型
     */
    @NotEmpty(message = "银行类型不能为空")
    private String bankCode;
    
    /**
     * 还款总金额
     */
    @NotEmpty(message = "还款总金额不能为空")
    private String totalAmount;
    
    /**
     * 银行卡号
     */
    @NotEmpty(message = "银行卡号不能为空")
    private String bankCard;
    
    /**
     * 身份证号
     */
    @NotEmpty(message = "身份证号不能为空")
    private String idCard;
    
    /**
     * 姓名
     */
    @NotEmpty(message = "姓名不能为空")
    private String cardHolder;
    
    private String isOffline;
    
    /**
     * 还款列表
     */
    @NotNull(message = "还款列表不能为空")
    private List<Repayment> repayments;
    
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getBindId() {
		return bindId;
	}

	public void setBindId(String bindId) {
		this.bindId = bindId;
	}

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public List<Repayment> getRepayments() {
		return repayments;
	}

	public void setRepayments(List<Repayment> repayments) {
		this.repayments = repayments;
	}

	public String getBankCard() {
		return bankCard;
	}

	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getCardHolder() {
		return cardHolder;
	}

	public void setCardHolder(String cardHolder) {
		this.cardHolder = cardHolder;
	}

	public String getIsOffline() {
		return isOffline;
	}

	public void setIsOffline(String isOffline) {
		this.isOffline = isOffline;
	}
}

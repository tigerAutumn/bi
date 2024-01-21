package com.pinting.gateway.hessian.message.zsd;

import java.util.List;

import com.pinting.core.hessian.msg.ReqMsg;
import com.pinting.gateway.hessian.message.loan.model.Repayment;

public class G2BReqMsg_ZsdRepay_CutpaymentRepay extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 453198484136576407L;

	/**
     * 还款订单号
     */
    private String orderNo;

    /**
     * 用户编号
     */
    private String userId;

    /**
     * 绑卡编号
     */
    private String bindId;
    
    /**
     * 借款编号
     */
    private String loanId;
    
    /**
     * 银行类型
     */
    private String bankCode;
    
    /**
     * 还款总金额
     */
    private String totalAmount;
    /**
     * 银行卡号
     */
    private String bankCard;
    
    /**
     * 身份证号
     */
    private String idCard;
    
    /**
     * 姓名
     */
    private String cardHolder;
    
    /**
     * 手机号
     */
    private String mobile;
    
    /**
     * 是否线下还款
     */
    private String isOffline;
	/**
	 * 线下还款支付单号(宝付支付单号)
	 */
	private String offPayOrderNo;
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

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getOffPayOrderNo() {
		return offPayOrderNo;
	}

	public void setOffPayOrderNo(String offPayOrderNo) {
		this.offPayOrderNo = offPayOrderNo;
	}
}
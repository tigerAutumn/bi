package com.pinting.gateway.hessian.message.loan;

import com.pinting.core.hessian.msg.ReqMsg;
import com.pinting.gateway.hessian.message.loan.model.Repayment;

import java.util.List;

public class G2BReqMsg_Repay_WithholdingRepay extends ReqMsg {

    /**
     * 还款订单号
     */
    private String orderNo;

    /**
     * 用户编号
     */
    private String userId;

    /**
     * 还款总额
     */
    private String totalAmount;

    /**
     * 借款编号
     */
    private String loanId;

    /**
     * 还款列表
     */
    private List<Repayment> repayments;


    /**
     * 代扣目标
     */
    private String cutTarget;

    /**
     * 绑定id
     */
    private String bindId;

    /**
     * 当前还款用户姓名
     */
    private String payUserName;

    /**
     * 当前还款用户身份证
     */
    private String payUserIdCard;

    /**
     * 当前还款用户卡号
     */
    private String payUserCardNo;

    /**
     * 当前还款用户手机
     */
    private String payUserMobile;

    /**
     * 银行编码
     */
    private String bankCode;
    /**
     * 是否线下还款
     */
    private String isOffline;
    
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

    public String getBindId() {
        return bindId;
    }

    public void setBindId(String bindId) {
        this.bindId = bindId;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getCutTarget() {
        return cutTarget;
    }

    public void setCutTarget(String cutTarget) {
        this.cutTarget = cutTarget;
    }

    public String getPayUserName() {
        return payUserName;
    }

    public void setPayUserName(String payUserName) {
        this.payUserName = payUserName;
    }

    public String getPayUserIdCard() {
        return payUserIdCard;
    }

    public void setPayUserIdCard(String payUserIdCard) {
        this.payUserIdCard = payUserIdCard;
    }

    public String getPayUserCardNo() {
        return payUserCardNo;
    }

    public void setPayUserCardNo(String payUserCardNo) {
        this.payUserCardNo = payUserCardNo;
    }

    public String getPayUserMobile() {
        return payUserMobile;
    }

    public void setPayUserMobile(String payUserMobile) {
        this.payUserMobile = payUserMobile;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

	public String getIsOffline() {
		return isOffline;
	}

	public void setIsOffline(String isOffline) {
		this.isOffline = isOffline;
	}
    
}

package com.pinting.gateway.hessian.message.loan;

import com.pinting.core.hessian.msg.ReqMsg;
import com.pinting.gateway.hessian.message.loan.model.Repayment;

import java.util.List;

public class G2BReqMsg_Repay_PreRepay extends ReqMsg {

    /**
     * 还款订单号
     */
    private String orderNo;

    /**
     * 用户IP地址
     */
    private String ip;

    /**
     * 用户编号
     */
    private String userId;

    /**
     * 还款总额
     */
    private String totalAmount;

    /**
     * 绑卡id
     */
    private String bindId;

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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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
}

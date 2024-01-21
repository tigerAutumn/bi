package com.pinting.gateway.hessian.message.loan;

import com.pinting.core.hessian.msg.ReqMsg;

public class G2BReqMsg_Loan_QueryLoan extends ReqMsg {

    /**
     * 借款订单号
     */
    private String orderNo;


    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}

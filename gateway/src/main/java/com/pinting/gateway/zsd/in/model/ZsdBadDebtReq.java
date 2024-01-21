package com.pinting.gateway.zsd.in.model;


import com.pinting.gateway.hessian.message.loan.model.Repayment;
import com.pinting.gateway.hessian.message.zsd.model.ZsdRepayment;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by zhangbao on 2017/8/30.
 */
public class ZsdBadDebtReq extends BaseReqModel {
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
    private List<ZsdRepayment> repayments;

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

    public List<ZsdRepayment> getRepayments() {
        return repayments;
    }

    public void setRepayments(List<ZsdRepayment> repayments) {
        this.repayments = repayments;
    }


}

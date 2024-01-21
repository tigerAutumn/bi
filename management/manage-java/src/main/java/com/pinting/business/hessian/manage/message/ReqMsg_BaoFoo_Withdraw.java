package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Created by 剑钊 on 2016/8/3.
 */
public class ReqMsg_BaoFoo_Withdraw extends ReqMsg {

    /**
     * 充值金额
     */
    private Double amount;

    /**
     * 银行卡号
     */
    private String accNo;

    private String mobile;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}

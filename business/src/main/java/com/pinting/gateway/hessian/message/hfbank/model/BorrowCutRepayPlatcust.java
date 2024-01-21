package com.pinting.gateway.hessian.message.hfbank.model;

import java.io.Serializable;

/**
 * Author:      cyb
 * Date:        2017/4/1
 * Description: 借款人扣款还款平台客户入账列表
 */
public class BorrowCutRepayPlatcust implements Serializable {

    private static final long serialVersionUID = -5725342058842717318L;

    /* 平台客户编号 */
    private String platcust;
    /* 该平台客户入账金额 */
    private Double amt;

    public String getPlatcust() {
        return platcust;
    }

    public void setPlatcust(String platcust) {
        this.platcust = platcust;
    }

    public Double getAmt() {
        return amt;
    }

    public void setAmt(Double amt) {
        this.amt = amt;
    }
}

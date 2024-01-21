package com.pinting.gateway.hessian.message.hfbank.model;

import java.io.Serializable;

/**
 * Author:      cyb
 * Date:        2017/4/12
 * Description:
 */
public class BatchWithdrawExtSuccessData implements Serializable {

    private static final long serialVersionUID = 689800468578955107L;

    private String detail_no;
    private String platcust;
    private String amt;

    public String getDetail_no() {
        return detail_no;
    }

    public void setDetail_no(String detail_no) {
        this.detail_no = detail_no;
    }

    public String getPlatcust() {
        return platcust;
    }

    public void setPlatcust(String platcust) {
        this.platcust = platcust;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }
}

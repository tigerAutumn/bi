package com.pinting.gateway.hfbank.in.model;

import java.io.Serializable;

/**
 * Author:      cyb
 * Date:        2017/4/1
 * Description: 借款人扣款还款平台客户入账列表
 */
public class BorrowCutRepayPlatcustReqModel implements Serializable {

    private static final long serialVersionUID = 6832234750940467625L;
    /* 该平台客户入账金额 */
    private String amt;
    /* 平台客户编号 */
    private String platcust;

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }
    public String getPlatcust() {
    	return platcust;
    }
    
    public void setPlatcust(String platcust) {
    	this.platcust = platcust;
    }
}

package com.pinting.gateway.hfbank.out.model;

import java.io.Serializable;

/**
 * Author:      cyb
 * Date:        2017/4/1
 * Description: 借款人扣款还款平台客户入账列表
 */
public class BorrowCutRepayPlatcustReqModel implements Serializable {

    private static final long serialVersionUID = -5184045020265928595L;
    /* 平台客户编号 */
    private String platcust;
    /* 该平台客户入账金额 */
    private String amt;

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
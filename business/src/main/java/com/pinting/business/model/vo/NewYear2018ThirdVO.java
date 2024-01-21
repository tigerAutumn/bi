package com.pinting.business.model.vo;

/**
 * Created by cyb on 2018/1/25.
 */
public class NewYear2018ThirdVO extends ActivityBaseVO {

    /* 当前平台累计年化出借额 */
    private Double loanAmount;

    /* 我的年化出借额度 */
    private Double myLoanAmount;

    public Double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(Double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public Double getMyLoanAmount() {
        return myLoanAmount;
    }

    public void setMyLoanAmount(Double myLoanAmount) {
        this.myLoanAmount = myLoanAmount;
    }
}

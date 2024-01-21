package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

import java.util.Date;

/**
 * 赞分期APP借款咨询与服务协议 根据借款编号查询管费、信息服务费、账户管理费、保费 出参
 * Created by shh on 2016/9/28 12:06.
 */
public class ResMsg_Match_GetUserLoanFeeList extends ResMsg {

    private static final long serialVersionUID = 6354812568372138318L;

    /* 监管费 */
    private Double loanServiceFee;

    /* 信息服务费 */
    private Double loanInfoServiceFee;

    /* 账户管理费 */
    private Double loanAccountManageFee;

    /* 保费 */
    private Double loadPremium;

    /* 借款人姓名 */
    private String userName;

    /* 借款时间 */
    private Date loanTime;

    public Double getLoanServiceFee() {
        return loanServiceFee;
    }

    public void setLoanServiceFee(Double loanServiceFee) {
        this.loanServiceFee = loanServiceFee;
    }

    public Double getLoanInfoServiceFee() {
        return loanInfoServiceFee;
    }

    public void setLoanInfoServiceFee(Double loanInfoServiceFee) {
        this.loanInfoServiceFee = loanInfoServiceFee;
    }

    public Double getLoanAccountManageFee() {
        return loanAccountManageFee;
    }

    public void setLoanAccountManageFee(Double loanAccountManageFee) {
        this.loanAccountManageFee = loanAccountManageFee;
    }

    public Double getLoadPremium() {
        return loadPremium;
    }

    public void setLoadPremium(Double loadPremium) {
        this.loadPremium = loadPremium;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getLoanTime() {
        return loanTime;
    }

    public void setLoanTime(Date loanTime) {
        this.loanTime = loanTime;
    }
}

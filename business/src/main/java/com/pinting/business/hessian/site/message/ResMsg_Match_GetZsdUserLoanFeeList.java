package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

import java.util.Date;

/**
 * 赞时贷APP借款咨询与服务协议 根据借款编号查询 快速信审费、平台使用费、账户管理费、代收通道费出参
 * Created by shh on 2017/10/31 14:00.
 * @author shh
 */
public class ResMsg_Match_GetZsdUserLoanFeeList extends ResMsg {

    private static final long serialVersionUID = 2226347984259315325L;

    /* 平台服务费 */
    private Double platformServiceFee;

    /* 账户管理费 */
    private Double accountManageFee;

    /* 快速信审监管费 */
    private Double riskManageServiceFee;

    /* 代收通道费 */
    private Double collectionChannelFee;

    /* 借款人姓名 */
    private String userName;

    /* 借款时间 */
    private Date loanTime;

    /* 借款id */
    private Integer loanId;

    public Double getPlatformServiceFee() {
        return platformServiceFee;
    }

    public void setPlatformServiceFee(Double platformServiceFee) {
        this.platformServiceFee = platformServiceFee;
    }

    public Double getAccountManageFee() {
        return accountManageFee;
    }

    public void setAccountManageFee(Double accountManageFee) {
        this.accountManageFee = accountManageFee;
    }

    public Double getRiskManageServiceFee() {
        return riskManageServiceFee;
    }

    public void setRiskManageServiceFee(Double riskManageServiceFee) {
        this.riskManageServiceFee = riskManageServiceFee;
    }

    public Double getCollectionChannelFee() {
        return collectionChannelFee;
    }

    public void setCollectionChannelFee(Double collectionChannelFee) {
        this.collectionChannelFee = collectionChannelFee;
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

    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
    }
}

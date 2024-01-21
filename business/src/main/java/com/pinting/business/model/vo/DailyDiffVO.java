package com.pinting.business.model.vo;

/**
 * Created by zhangbao on 2017/4/10.
 */
public class DailyDiffVO {
    private Integer subAccountId;
    private Integer diffAccountId;
    private Double amount;
    private Integer loanRelationId;
    private Double productRate;
    private Integer accountId;


    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getSubAccountId() {
        return subAccountId;
    }

    public void setSubAccountId(Integer subAccountId) {
        this.subAccountId = subAccountId;
    }

    public Integer getLoanRelationId() {
        return loanRelationId;
    }

    public void setLoanRelationId(Integer loanRelationId) {
        this.loanRelationId = loanRelationId;
    }

    public Double getProductRate() {
        return productRate;
    }

    public void setProductRate(Double productRate) {
        this.productRate = productRate;
    }

    public Integer getDiffAccountId() {
        return diffAccountId;
    }

    public void setDiffAccountId(Integer diffAccountId) {
        this.diffAccountId = diffAccountId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }
}

package com.pinting.business.redis.sign.model;

/**
 * 代偿签章服务redis对象
 */
public class CompensateSignRedisVO extends SignRedisVO {

    private Integer lnLoanId; // 借款ln_loan_id

    public Integer getLnLoanId() {
        return lnLoanId;
    }

    public void setLnLoanId(Integer lnLoanId) {
        this.lnLoanId = lnLoanId;
    }
}

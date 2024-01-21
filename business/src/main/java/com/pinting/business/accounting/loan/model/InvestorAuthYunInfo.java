package com.pinting.business.accounting.loan.model;

/**
 * 理财人子账户信息
 *
 */
public class InvestorAuthYunInfo {

	private Integer investorAuthYunActId;
	
    private Double authYunAmount;

    private Double redPacAmount;

	@Override
    public String toString() {
        return "InvestorAuthYunInfo{" +
                "investorAuthYunActId=" + investorAuthYunActId +
                ", authYunAmount=" + authYunAmount +
                ", redPacAmount=" + redPacAmount +
                '}';
    }

    public Integer getInvestorAuthYunActId() {
        return investorAuthYunActId;
    }

    public void setInvestorAuthYunActId(Integer investorAuthYunActId) {
        this.investorAuthYunActId = investorAuthYunActId;
    }

    public Double getAuthYunAmount() {
        return authYunAmount;
    }

    public void setAuthYunAmount(Double authYunAmount) {
        this.authYunAmount = authYunAmount;
    }

    public Double getRedPacAmount() {
        return redPacAmount;
    }

    public void setRedPacAmount(Double redPacAmount) {
        this.redPacAmount = redPacAmount;
    }
}

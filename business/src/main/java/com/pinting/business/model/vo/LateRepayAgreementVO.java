package com.pinting.business.model.vo;

/**
 * Author:      shh
 * Date:        2018/1/5
 * Description: 代偿协议-收款确认函vo（代偿本金，协议利率协议编号 总金额）
 */
public class LateRepayAgreementVO {

    /* 代偿金额 */
    private Double amount;

    /* 代偿本金 */
    private Double principal;

    /* 计息天数 */
    private Integer interestDay;

    /* 协议编号 */
    private String agreementNo;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getPrincipal() {
        return principal;
    }

    public void setPrincipal(Double principal) {
        this.principal = principal;
    }

    public Integer getInterestDay() {
        return interestDay;
    }

    public void setInterestDay(Integer interestDay) {
        this.interestDay = interestDay;
    }

    public String getAgreementNo() {
        return agreementNo;
    }

    public void setAgreementNo(String agreementNo) {
        this.agreementNo = agreementNo;
    }
}

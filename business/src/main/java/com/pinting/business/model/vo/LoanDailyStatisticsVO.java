package com.pinting.business.model.vo;

/**
 * Author:      shh
 * Date:        2017/10/11
 * Description: 放款日常管理 云贷赞分期资金统计
 */
public class LoanDailyStatisticsVO {

    /* 放款成功笔数 */
    private Integer paiedCount;

    /* 放款成功总金额 */
    private Double paiedTotalAmount;

    /* 赞分期放款处理中笔数 */
    private Integer payingCount;

    /* 放款处理中总金额 */
    private Double payingTotalAmount;

    public Integer getPaiedCount() {
        return paiedCount;
    }

    public void setPaiedCount(Integer paiedCount) {
        this.paiedCount = paiedCount;
    }

    public Double getPaiedTotalAmount() {
        return paiedTotalAmount;
    }

    public void setPaiedTotalAmount(Double paiedTotalAmount) {
        this.paiedTotalAmount = paiedTotalAmount;
    }

    public Integer getPayingCount() {
        return payingCount;
    }

    public void setPayingCount(Integer payingCount) {
        this.payingCount = payingCount;
    }

    public Double getPayingTotalAmount() {
        return payingTotalAmount;
    }

    public void setPayingTotalAmount(Double payingTotalAmount) {
        this.payingTotalAmount = payingTotalAmount;
    }
}

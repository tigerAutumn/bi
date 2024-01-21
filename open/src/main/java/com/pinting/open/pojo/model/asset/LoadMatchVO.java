package com.pinting.open.pojo.model.asset;

public class LoadMatchVO {

	private String borrowerName; //借款人
	
	private String amount; //借款金额
	
	private String repayStatus; //还款状态
    /* 总期数 */
    private Integer period;

    /* 已还期数 */
    private Integer repayedPeriodCount;

	public String getBorrowerName() {
		return borrowerName;
	}

	public void setBorrowerName(String borrowerName) {
		this.borrowerName = borrowerName;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getRepayStatus() {
		return repayStatus;
	}

	public void setRepayStatus(String repayStatus) {
		this.repayStatus = repayStatus;
	}

	public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}

	public Integer getRepayedPeriodCount() {
		return repayedPeriodCount;
	}

	public void setRepayedPeriodCount(Integer repayedPeriodCount) {
		this.repayedPeriodCount = repayedPeriodCount;
	}
	
}

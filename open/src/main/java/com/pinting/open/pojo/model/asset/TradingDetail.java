package com.pinting.open.pojo.model.asset;

public class TradingDetail {
	
    private String transName;  //交易类型名称
    
    private String transTime;  //交易时间

    private Double transAmount;  //交易金额
    
    private String transStatus;   //交易状态

	private String transType;	// 交易类型

	public String getTransName() {
		return transName;
	}

	public void setTransName(String transName) {
		this.transName = transName;
	}

	

	public String getTransTime() {
		return transTime;
	}

	public void setTransTime(String transTime) {
		this.transTime = transTime;
	}

	public Double getTransAmount() {
		return transAmount;
	}

	public void setTransAmount(Double transAmount) {
		this.transAmount = transAmount;
	}

	public String getTransStatus() {
		return transStatus;
	}

	public void setTransStatus(String transStatus) {
		this.transStatus = transStatus;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}
}

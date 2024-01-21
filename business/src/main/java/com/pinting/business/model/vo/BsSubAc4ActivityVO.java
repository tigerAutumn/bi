package com.pinting.business.model.vo;

public class BsSubAc4ActivityVO {
	
	private String serialName; //红包名称
	
	private Integer term; //投资期限
	
	private Double amount; //投资金额
	
	private String productName; //投资的产品名称
	
	private String showTerminal; //显示端口

	public String getSerialName() {
		return serialName;
	}

	public void setSerialName(String serialName) {
		this.serialName = serialName;
	}

	public Integer getTerm() {
		return term;
	}

	public void setTerm(Integer term) {
		this.term = term;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getShowTerminal() {
		return showTerminal;
	}

	public void setShowTerminal(String showTerminal) {
		this.showTerminal = showTerminal;
	}

}

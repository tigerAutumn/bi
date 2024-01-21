package com.dafy.core.enums;

public enum ProductRate {
	DAYS_30("027",9),
	DAYS_90("028",10),
	DAYS_180("029",12),
	DAYS_365("030",14);
	
	//产品编码
	private String code;
	//利率
	private int rate;
	
	private ProductRate(String code, int rate) {
		this.code = code;
		this.rate = rate;
	}
	
	//根据编码查询该对象
	public static ProductRate find(String code) {
		for(ProductRate fs : ProductRate.values()) {
			if(fs.getCode().equals(code)) {
				return fs;
			}
		}
		return null;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}
	
	 public static void main(String[] args) {
		System.out.println(ProductRate.find("030").toString().substring(5));
	}
}

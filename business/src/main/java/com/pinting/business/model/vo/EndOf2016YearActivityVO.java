package com.pinting.business.model.vo;

/**
 * 2016客户年终抽奖活动VO
 * Created by shh on 2016/11/30 15:00.
 * @author shh
 */
public class EndOf2016YearActivityVO {
	
	/* 抽奖用户手机号 */
	private String mobile;
	
	/* 抽奖用户userId*/
	private Integer userId;
	
	/* 年化额 */
	private Double annualizedInvestment;

	private String type;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Double getAnnualizedInvestment() {
		return annualizedInvestment;
	}

	public void setAnnualizedInvestment(Double annualizedInvestment) {
		this.annualizedInvestment = annualizedInvestment;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}

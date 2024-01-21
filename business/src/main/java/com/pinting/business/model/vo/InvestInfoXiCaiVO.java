package com.pinting.business.model.vo;

import java.util.Date;

import com.pinting.business.model.BsUser;

public class InvestInfoXiCaiVO extends BsUser {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5216162045999692278L;
	
	/** 子账户id */
    private Integer subId;
	
    /** 产品id */
	private String pid;
	
	/** 产品利率 */
	private Double productRate;
	
	/** 产品期限 */
	private Integer term;
	
	private Integer term4Day;
	
	/** 投资金额 */
	private Double money;
	
	/** 投资时间 */
	private Date openTime;

	public Integer getSubId() {
		return subId;
	}

	public void setSubId(Integer subId) {
		this.subId = subId;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public Double getProductRate() {
		return productRate;
	}

	public void setProductRate(Double productRate) {
		this.productRate = productRate;
	}

	public Integer getTerm() {
		return term;
	}

	public void setTerm(Integer term) {
		this.term = term;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Date getOpenTime() {
		return openTime;
	}

	public void setOpenTime(Date openTime) {
		this.openTime = openTime;
	}
	
	public Integer getTerm4Day() {
		if (term == null || "".equals(term)) {
			return null;
		}
		if(term < 0){
			term4Day = Math.abs(this.term);
		}else if(term == 12){
			term4Day = 365;
		}else{
			term4Day = term*30;
		}
		return term4Day;
	}

}

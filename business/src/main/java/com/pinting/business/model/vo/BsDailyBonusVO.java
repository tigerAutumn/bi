package com.pinting.business.model.vo;

import java.util.Date;

import com.pinting.business.model.BsDailyBonus;

public class BsDailyBonusVO extends BsDailyBonus {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1539596625267949644L;
	private Date startTime;
	private Date endTime;
	
	private Integer accountId;
	private Integer subAccountId;
	
	/** 受益人姓名 **/
	private String name;
	
	/** 受益人手机号码  **/
	private String mobile;
	
	/** 投资人姓名  被推荐人姓名  **/
	private String byName;
	
	/** 投资人手机号码   推荐人手机号码  **/
	private String byMobile;
	
	/** 投资金额（余额）**/
	private Double balance;
	
	/** 奖励总金额 **/
	private Double allBonus;
	
	/** 投资期限 **/
	private Date subEndTime;
	
	/**首月奖励金**/
	private Double bonuss;
	
	/**产品期限 单位：月**/
	private int term;
	
	/**购买时间**/
	private Date buyTime;
	
	/** 普通推荐人名字  **/
	private String generalName;
	
	/** 普通推荐人手机号码  **/
	private String generalMobile;
	
	/** 查询条件推荐人名字  **/
	private String recommendName;
	
	/** 查询条件推荐人手机号码  **/
	private String recommendMobile;
	
	/** 推荐的销售人员名字 **/
	private String salesName;
	
	/** 推荐的销售人员手机号码 **/
	private String salesMobile;
	
	/** 推荐的客户经理名字 **/
	private String managerName;
	
	/** 推荐的客户经理手机号码 **/
	private String managerMobile;

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public Integer getSubAccountId() {
		return subAccountId;
	}

	public void setSubAccountId(Integer subAccountId) {
		this.subAccountId = subAccountId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getByName() {
		return byName;
	}

	public void setByName(String byName) {
		this.byName = byName;
	}

	public String getByMobile() {
		return byMobile;
	}

	public void setByMobile(String byMobile) {
		this.byMobile = byMobile;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Double getAllBonus() {
		return allBonus;
	}

	public void setAllBonus(Double allBonus) {
		this.allBonus = allBonus;
	}

	public Date getSubEndTime() {
		return subEndTime;
	}

	public void setSubEndTime(Date subEndTime) {
		this.subEndTime = subEndTime;
	}

	public Double getBonuss() {
		return bonuss;
	}

	public void setBonuss(Double bonuss) {
		this.bonuss = bonuss;
	}

	public int getTerm() {
		return term;
	}

	public void setTerm(int term) {
		this.term = term;
	}

	public Date getBuyTime() {
		return buyTime;
	}

	public void setBuyTime(Date buyTime) {
		this.buyTime = buyTime;
	}

	public String getGeneralName() {
		return generalName;
	}

	public void setGeneralName(String generalName) {
		this.generalName = generalName;
	}

	public String getGeneralMobile() {
		return generalMobile;
	}

	public void setGeneralMobile(String generalMobile) {
		this.generalMobile = generalMobile;
	}

	public String getRecommendName() {
		return recommendName;
	}

	public void setRecommendName(String recommendName) {
		this.recommendName = recommendName;
	}

	public String getRecommendMobile() {
		return recommendMobile;
	}

	public void setRecommendMobile(String recommendMobile) {
		this.recommendMobile = recommendMobile;
	}

	public String getSalesName() {
		return salesName;
	}

	public void setSalesName(String salesName) {
		this.salesName = salesName;
	}

	public String getSalesMobile() {
		return salesMobile;
	}

	public void setSalesMobile(String salesMobile) {
		this.salesMobile = salesMobile;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getManagerMobile() {
		return managerMobile;
	}

	public void setManagerMobile(String managerMobile) {
		this.managerMobile = managerMobile;
	}

}

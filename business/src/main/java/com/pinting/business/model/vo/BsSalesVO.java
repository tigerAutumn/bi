package com.pinting.business.model.vo;

import java.util.Date;

import com.pinting.business.model.BsSales;
import com.pinting.core.util.DateUtil;

public class BsSalesVO extends BsSales {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4762917355612035704L;
	
	/** 直接邀请人数 **/
	private Integer directPeople;
	/** 直接邀请金额 **/
	private Double directMoney;
	/** 间接邀请人数 **/
	private Integer indirectPeople;
	/** 间接邀请金额 **/
	private Double indirectMoney;
	/**购买开始时间**/
	private String startTime;
	/**购买结束时间**/
	private String endTime;
	/**序号**/
	private Integer number;
	/**手机号码**/
	private String mobile;
	/**等级**/
	private Integer grade;
	/**用户名称**/
	private String userName;
	/**邀请人姓名**/
	private String recommendName;
	/**产品名称**/
	private String productName;
	/**购买金额**/
	private Double balance;
	/**购买时间**/
	private Date openTime;
	/**购买金额区间（前区间）**/
	private Double startMoney;
	/**购买金额区间（后区间）**/
	private Double endMoney;
	/**入职时间**/
	private String entryTimeStr;
	
	/** 已投资客户 */
	private Integer investmentUser;
	/** 直接邀请年化金额 */
	private Double directAnnualAmount;
	/**部门名称**/
	private String deptName;
	/**部门管理员名称**/
	private String deptManagerName;
	
	/**管理员ID**/
	private Integer mUserId;
	
	public Integer getDirectPeople() {
		return directPeople == null ? 0 : directPeople;
	}

	public void setDirectPeople(Integer directPeople) {
		this.directPeople = directPeople;
	}

	public Double getDirectMoney() {
		return directMoney;
	}

	public void setDirectMoney(Double directMoney) {
		this.directMoney = directMoney;
	}

	public Double getIndirectMoney() {
		return indirectMoney;
	}

	public void setIndirectMoney(Double indirectMoney) {
		this.indirectMoney = indirectMoney;
	}

	public Integer getIndirectPeople() {
		return indirectPeople == null ? 0 : indirectPeople;
	}

	public void setIndirectPeople(Integer indirectPeople) {
		this.indirectPeople = indirectPeople;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRecommendName() {
		return recommendName;
	}

	public void setRecommendName(String recommendName) {
		this.recommendName = recommendName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Date getOpenTime() {
		return openTime;
	}

	public void setOpenTime(Date openTime) {
		this.openTime = openTime;
	}

	public Double getStartMoney() {
		return startMoney;
	}

	public void setStartMoney(Double startMoney) {
		this.startMoney = startMoney;
	}

	public Double getEndMoney() {
		return endMoney;
	}

	public void setEndMoney(Double endMoney) {
		this.endMoney = endMoney;
	}

	public String getEntryTimeStr() {
		if(getEntryTime() != null){
			entryTimeStr = DateUtil.formatYYYYMMDD(getEntryTime());
		}
		return entryTimeStr;
	}

	public void setEntryTimeStr(String entryTimeStr) {
		this.entryTimeStr = entryTimeStr;
	}

	public Integer getInvestmentUser() {
		return investmentUser;
	}

	public void setInvestmentUser(Integer investmentUser) {
		this.investmentUser = investmentUser;
	}

	public Double getDirectAnnualAmount() {
		return directAnnualAmount;
	}

	public void setDirectAnnualAmount(Double directAnnualAmount) {
		this.directAnnualAmount = directAnnualAmount;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptManagerName() {
		return deptManagerName;
	}

	public void setDeptManagerName(String deptManagerName) {
		this.deptManagerName = deptManagerName;
	}

	public Integer getmUserId() {
		return mUserId;
	}

	public void setmUserId(Integer mUserId) {
		this.mUserId = mUserId;
	}

}

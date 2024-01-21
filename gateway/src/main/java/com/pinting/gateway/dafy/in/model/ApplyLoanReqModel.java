package com.pinting.gateway.dafy.in.model;

import java.util.Date;

/**
 * 自主放款-放款
 * @author Dragon & cat
 * @date 2016-11-25
 */
public class ApplyLoanReqModel extends BaseReqModel {
	/*客户端标识*/
	private			String 		clientKey;
	/*云贷用户唯一编号*/
	private			String		userId;
	/*姓名*/
	private			String		name;
	/*身份证*/
	private			String		idCard;
	/*预留手机号*/
	private			String		mobile;
	/*卡号*/
	private			String		bankCard;
	/*银行编码*/
	private			String		bankCode;
	/*借款单号*/
	private			String		orderNo;
	/*申请时间*/
	private			Date		applyTime;
	/*业务标识*/
	private			String		businessType;
	/*借款编号*/
	private			String		loanId;
	/*借款金额*/
	private			Long		loanAmount;
	/*借款手续费*/
	private			Long		loanFee;
	/*借款期数*/
	private			Integer		loanTerm;
	/*借款利率    100 = 0.01%*/
	private			Integer		loanRate;
	/*借款标的名称*/
	private			String		subjectName;
	/*借款用途*/
	private			String		purpose;
	/*授信金额*/
	private			Long		creditAmount;
	/*已借款金额*/
	private			Long		loanedAmount;
	/*信用等级*/
	private			String		creditLevel;
	/*信用积分*/
	private			Integer		creditScore;
	/*借款次数*/
	private			Integer		loanTimes;
	/*违约次数*/
	private			Integer		breakTimes;
	/*最长违约天数*/
	private			Integer		breakMaxDays;
	/*工作单位*/
	private			String		workUnit;
	/*学历*/
	private			String		education;
	/*婚姻*/
	private			String		marriage;
	/*月收入*/
	private			Long		monthlyIncome;
	/*借款人地址*/
	private			String		address;
	/*借款人邮箱*/
	private			String		email;
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getBankCard() {
		return bankCard;
	}
	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public Date getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	public String getLoanId() {
		return loanId;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	public Long getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(Long loanAmount) {
		this.loanAmount = loanAmount;
	}
	public Long getLoanFee() {
		return loanFee;
	}
	public void setLoanFee(Long loanFee) {
		this.loanFee = loanFee;
	}
	public Integer getLoanTerm() {
		return loanTerm;
	}
	public void setLoanTerm(Integer loanTerm) {
		this.loanTerm = loanTerm;
	}
	public Integer getLoanRate() {
		return loanRate;
	}
	public void setLoanRate(Integer loanRate) {
		this.loanRate = loanRate;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public Long getCreditAmount() {
		return creditAmount;
	}
	public void setCreditAmount(Long creditAmount) {
		this.creditAmount = creditAmount;
	}
	public Long getLoanedAmount() {
		return loanedAmount;
	}
	public void setLoanedAmount(Long loanedAmount) {
		this.loanedAmount = loanedAmount;
	}
	public String getCreditLevel() {
		return creditLevel;
	}
	public void setCreditLevel(String creditLevel) {
		this.creditLevel = creditLevel;
	}
	public Integer getCreditScore() {
		return creditScore;
	}
	public void setCreditScore(Integer creditScore) {
		this.creditScore = creditScore;
	}
	public Integer getLoanTimes() {
		return loanTimes;
	}
	public void setLoanTimes(Integer loanTimes) {
		this.loanTimes = loanTimes;
	}
	public Integer getBreakTimes() {
		return breakTimes;
	}
	public void setBreakTimes(Integer breakTimes) {
		this.breakTimes = breakTimes;
	}
	public Integer getBreakMaxDays() {
		return breakMaxDays;
	}
	public void setBreakMaxDays(Integer breakMaxDays) {
		this.breakMaxDays = breakMaxDays;
	}
	public String getWorkUnit() {
		return workUnit;
	}
	public void setWorkUnit(String workUnit) {
		this.workUnit = workUnit;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getMarriage() {
		return marriage;
	}
	public void setMarriage(String marriage) {
		this.marriage = marriage;
	}
	public Long getMonthlyIncome() {
		return monthlyIncome;
	}
	public void setMonthlyIncome(Long monthlyIncome) {
		this.monthlyIncome = monthlyIncome;
	}
	public String getClientKey() {
		return clientKey;
	}
	public void setClientKey(String clientKey) {
		this.clientKey = clientKey;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}

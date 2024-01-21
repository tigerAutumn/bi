package com.pinting.business.model.vo;

import java.util.Date;
import java.util.List;

/**
 * 借款协议详细数据
 * @author bianyatian
 * @2016-9-23 下午7:46:13
 */
public class LoanDetailInfoVO {
	
	private List<BsUser4LoanVO> bsUserInfo; //出借人列表
	
	private Integer id; //借款信息表id
	
	private String partnerLoanId; //合作方借款id
	
	private String lnUserName; //借款人姓名
	
	private String lnUserIdCard; //借款人身份证号
	
	private String lnUserZANAccount; //借款人赞分期账户
	
	private Date loanTime; // 借款时间（协议签署时间）
	
	private String purpose;  //借款用途
	
	private Double approveAmount; //借款本金数额
	
	private Double lnRate; //借款年化利率
	
	private Integer period; //借款期限
	
	private Double needRepayMoney4Month; //月偿还本息数额
	
	private Date lnRepayStartDate; ////第一期还款日
	
	private Date lnRepayEndDate; //最后一期还款日
	
	private Integer day4Month; //每月还款日

	private Double principalInterestAmount; //到期偿还本息数额(赞时贷借款协议)

	private Double agreementRate; //协议利率

	private String address; // 借款人地址

	private String email; // 借款人邮箱
	
	private String partnerBusinessFlag; //业务标识	REPAY_ANY_TIME(消费循环贷)/FIXED_INSTALLMENT(等额本息)/FIXED_PRINCIPAL_INTEREST 

	private Double loanServiceRate; // 借款服务费

	public List<BsUser4LoanVO> getBsUserInfo() {
		return bsUserInfo;
	}

	public void setBsUserInfo(List<BsUser4LoanVO> bsUserInfo) {
		this.bsUserInfo = bsUserInfo;
	}

	public String getLnUserName() {
		return lnUserName;
	}

	public void setLnUserName(String lnUserName) {
		this.lnUserName = lnUserName;
	}

	public String getLnUserZANAccount() {
		return lnUserZANAccount;
	}

	public void setLnUserZANAccount(String lnUserZANAccount) {
		this.lnUserZANAccount = lnUserZANAccount;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public Double getLnRate() {
		return lnRate;
	}

	public void setLnRate(Double lnRate) {
		this.lnRate = lnRate;
	}

	public Double getNeedRepayMoney4Month() {
		return needRepayMoney4Month;
	}

	public void setNeedRepayMoney4Month(Double needRepayMoney4Month) {
		this.needRepayMoney4Month = needRepayMoney4Month;
	}

	public Date getLnRepayStartDate() {
		return lnRepayStartDate;
	}

	public void setLnRepayStartDate(Date lnRepayStartDate) {
		this.lnRepayStartDate = lnRepayStartDate;
	}

	public Date getLnRepayEndDate() {
		return lnRepayEndDate;
	}

	public void setLnRepayEndDate(Date lnRepayEndDate) {
		this.lnRepayEndDate = lnRepayEndDate;
	}

	public Integer getDay4Month() {
		return day4Month;
	}

	public void setDay4Month(Integer day4Month) {
		this.day4Month = day4Month;
	}

	public String getLnUserIdCard() {
		return lnUserIdCard;
	}

	public void setLnUserIdCard(String lnUserIdCard) {
		this.lnUserIdCard = lnUserIdCard;
	}

	public Date getLoanTime() {
		return loanTime;
	}

	public void setLoanTime(Date loanTime) {
		this.loanTime = loanTime;
	}

	public Double getApproveAmount() {
		return approveAmount;
	}

	public void setApproveAmount(Double approveAmount) {
		this.approveAmount = approveAmount;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}

	public Double getPrincipalInterestAmount() {
		return principalInterestAmount;
	}

	public void setPrincipalInterestAmount(Double principalInterestAmount) {
		this.principalInterestAmount = principalInterestAmount;
	}

	public Double getAgreementRate() {
		return agreementRate;
	}

	public void setAgreementRate(Double agreementRate) {
		this.agreementRate = agreementRate;
	}

	public String getPartnerLoanId() {
		return partnerLoanId;
	}

	public void setPartnerLoanId(String partnerLoanId) {
		this.partnerLoanId = partnerLoanId;
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

	public String getPartnerBusinessFlag() {
		return partnerBusinessFlag;
	}

	public void setPartnerBusinessFlag(String partnerBusinessFlag) {
		this.partnerBusinessFlag = partnerBusinessFlag;
	}

	public Double getLoanServiceRate() {
		return loanServiceRate;
	}

	public void setLoanServiceRate(Double loanServiceRate) {
		this.loanServiceRate = loanServiceRate;
	}
}

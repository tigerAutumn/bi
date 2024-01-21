package com.pinting.gateway.zsd.in.model;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 借款申请服务请求体
 * @author SHENGUOPING
 * @date  2017年8月30日 下午1:55:08
 */
public class ZsdLoanApplyReq extends BaseReqModel {

	/** 计费规则编码*/
	private String chargeRule;
	
	/** 借款订单号*/
    @NotEmpty(message = "订单号不能为空")
	private String orderNo;
	
	/** 业务标识*/
	private String businessType;

	/** 用户编号*/
	@NotNull(message = "用户编号不能为空")
	private String userId;
	
	/** 借款编号*/
	@NotNull(message = "借款编号不能为空")
	private String loanId;
	
	/** 借款金额*/
	private Long loanAmount;
	
	/** 借款期限*/
	private Integer loanTerm;
	
	/** 砍头息*/
	private Long headFee;
	
	/** 银行卡号*/
	@NotEmpty(message = "银行卡号不能为空")
	private String bankCard;
	
	/** 银行类型*/
	@NotEmpty(message = "银行类型不能为空")
	private String bankCode;
	
	/** 身份证号*/
	@NotEmpty(message = "身份证号不能为空")
	@Pattern(regexp="^(\\d{18,18}|\\d{15,15}|\\d{17,17}x|\\d{17,17}X)$", message="身份证号格式错误")	
	private String idCard;
	
	/** 姓名*/
	@NotEmpty(message = "姓名不能为空")
	private String cardHolder;
	
	/** 银行预留手机号*/
	@NotEmpty(message = "手机号不能为空")
	@Pattern(regexp="^[0-9]{11}$", message="手机号格式错误")
	private String mobile;
	
	/** 职业*/
	private String profession;
	
	/** 年收入*/
	private String annualIncome;
	
	/** 所在省国标编码*/
	private String provinceCode;
	
	/** 所在市国标编码*/
	private String cityCode;
	
	/** 所在区国标编码*/
	private String areaCode;
	
	/** 借款标的名称*/
	private String subjectName;
	
	/** 借款用途*/
	private String purpose;
	
	/** 借款申请时间*/
	private Date loanApplyTime;
	
	/** 授信金额*/
	private Long creditAmount; 
	
	/** 已借款金额*/
	private Long loanedAmount;
	
	/** 信用等级*/
	private String creditLevel;
	
	/** 信用积分*/
	private Integer creditScore;
	
	/** 借款次数*/
	private Integer loanTimes;
	
	/** 违约次数*/
	private Integer breakTimes;
	
	/** 最长违约天数*/
	private Integer breakMaxDays;
	
	/** 还款计划表*/
	private List<ZsdLoanRepayScheduleReq> repaySchedule;

	public String getChargeRule() {
		return chargeRule;
	}

	public void setChargeRule(String chargeRule) {
		this.chargeRule = chargeRule;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

	public Integer getLoanTerm() {
		return loanTerm;
	}

	public void setLoanTerm(Integer loanTerm) {
		this.loanTerm = loanTerm;
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

	public Date getLoanApplyTime() {
		return loanApplyTime;
	}

	public void setLoanApplyTime(Date loanApplyTime) {
		this.loanApplyTime = loanApplyTime;
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

	public List<ZsdLoanRepayScheduleReq> getRepaySchedule() {
		return repaySchedule;
	}

	public void setRepaySchedule(List<ZsdLoanRepayScheduleReq> repaySchedule) {
		this.repaySchedule = repaySchedule;
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

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getCardHolder() {
		return cardHolder;
	}

	public void setCardHolder(String cardHolder) {
		this.cardHolder = cardHolder;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public String getAnnualIncome() {
		return annualIncome;
	}

	public void setAnnualIncome(String annualIncome) {
		this.annualIncome = annualIncome;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public Long getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(Long loanAmount) {
		this.loanAmount = loanAmount;
	}

	public Long getHeadFee() {
		return headFee;
	}

	public void setHeadFee(Long headFee) {
		this.headFee = headFee;
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
	
}

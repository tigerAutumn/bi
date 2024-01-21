package com.pinting.gateway.bird.in.model;

import com.pinting.gateway.hessian.message.loan.model.RepaySchedule;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import java.util.List;

public class LoanReq extends BaseReqModel {

    /**
     * 借款订单号
     */
    @NotEmpty(message = "订单号不能为空")
    private String orderNo;

    /**
     * 业务标识
     */
    @NotEmpty(message = "业务标识不能为空")
    private String businessType;

    /**
     * 用户编号
     */
    @NotEmpty(message = "用户编号不能为空")
    private String userId;

    /**
     * 借款编号
     */
    @NotEmpty(message = "借款编号不能为空")
    private String loanId;

    /**
     * 借款金额
     */
    @NotEmpty(message = "借款金额不能为空")
    private String loanAmount;

    /**
     * 借款期限
     */
    @NotEmpty(message = "借款期限不能为空")
    private String loanTerm;
    
	/** 借款利率*/
	private Integer loanRate;
    
	/** 砍头息*/
	private Long headFee;
	
	/** 银行卡号*/
	private String bankCard;
	
	/** 银行类型*/
	private String bankCode;
	
	/** 身份证号*/
	private String idCard;
	
	/** 姓名*/
	private String cardHolder;
	
	/** 银行预留手机号*/
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

    /**
     * 绑卡编号
     */
    private String bindId;

    /**
     * 借款标的名称
     */
    private String subjectName;

    /**
     * 借款用途
     */
    private String purpose;

    /**
     * 借款申请时间
     */
    private String loanApplyTime;

    /**
     * 授信金额
     */
    private String creditAmount;

    /**
     * 已借款金额
     */
    private String loanedAmount;

    /**
     * 信用等级
     */
    private String creditLevel;

    /**
     * 信用积分
     */
    private String creditScore;

    /**
     * 借款次数
     */
    private String loanTimes;

    /**
     * 违约次数
     */
    private String breakTimes;

    /**
     * 最长违约天数
     */
    private String breakMaxDays;

    /**
     * 计费规则编码
     */
    private String chargeRule;

    /**
     * 还款计划列表
     */
    @NotNull(message = "还款计划列表不能为空")
    private List<RepaySchedule> repaySchedule;

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

    public String getBindId() {
        return bindId;
    }

    public void setBindId(String bindId) {
        this.bindId = bindId;
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

    public String getLoanApplyTime() {
        return loanApplyTime;
    }

    public void setLoanApplyTime(String loanApplyTime) {
        this.loanApplyTime = loanApplyTime;
    }

    public String getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(String creditAmount) {
        this.creditAmount = creditAmount;
    }

    public String getLoanedAmount() {
        return loanedAmount;
    }

    public void setLoanedAmount(String loanedAmount) {
        this.loanedAmount = loanedAmount;
    }

    public String getCreditLevel() {
        return creditLevel;
    }

    public void setCreditLevel(String creditLevel) {
        this.creditLevel = creditLevel;
    }

    public String getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(String creditScore) {
        this.creditScore = creditScore;
    }

    public String getLoanTimes() {
        return loanTimes;
    }

    public void setLoanTimes(String loanTimes) {
        this.loanTimes = loanTimes;
    }

    public String getBreakTimes() {
        return breakTimes;
    }

    public void setBreakTimes(String breakTimes) {
        this.breakTimes = breakTimes;
    }

    public String getBreakMaxDays() {
        return breakMaxDays;
    }

    public void setBreakMaxDays(String breakMaxDays) {
        this.breakMaxDays = breakMaxDays;
    }

    public List<RepaySchedule> getRepaySchedule() {
        return repaySchedule;
    }

    public void setRepaySchedule(List<RepaySchedule> repaySchedule) {
        this.repaySchedule = repaySchedule;
    }

    public String getChargeRule() {
        return chargeRule;
    }

    public void setChargeRule(String chargeRule) {
        this.chargeRule = chargeRule;
    }

    public String getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(String loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getLoanTerm() {
        return loanTerm;
    }

    public void setLoanTerm(String loanTerm) {
        this.loanTerm = loanTerm;
    }

	public Integer getLoanRate() {
		return loanRate;
	}

	public void setLoanRate(Integer loanRate) {
		this.loanRate = loanRate;
	}

	public Long getHeadFee() {
		return headFee;
	}

	public void setHeadFee(Long headFee) {
		this.headFee = headFee;
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
    
    
}

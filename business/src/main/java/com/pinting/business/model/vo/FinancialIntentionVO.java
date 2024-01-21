package com.pinting.business.model.vo;

import java.util.Date;

/**
 * 理财意向
 * @author HuanXiong
 * @version 2016-6-2 下午1:42:07
 */
public class FinancialIntentionVO extends PageInfoObject {
    
    /**  */
    private static final long serialVersionUID = 2339256303136879238L;

    // 请求条件 - START
    private String applyTimeStart;
    
    private String applyTimeEnd;
    
    private String interestBeginDateStart;
    
    private String interestBeginDateEnd;
    
    private String paymentDateStart;
    
    private String paymentDateEnd;
    
    // 请求条件 - END
    
    private Integer id; //
    
    private Integer subAccountId;   // 子账户ID
    
    private String userName;    //理财人姓名
    
    private String mobile;
    
    private String idCard;
    
    private String productName;
    
    private String managerName; // 客户经理
    
    private String deptName;    // 营业部
    
    private Integer status; // 理财意向状态
    
    private String statusStr; // 理财意向状态
    
    private Double baseRate;    // 预期年化利率
    
    private Double subtract;    // 红包抵扣
    
    private Double incentiveRate = 0d;   // 奖励利率，默认是0
    
    private Integer term;    // 理财周期
    
    private Double balance;  // 理财金额(分期：出借金额)
    
    private Double openBalance;  // 分期：理财金额
    
    private Double rechargeAmount;  // 充值金额
    
    private Double accumulationInerest;    // 当前收益
    
    private Date applyTime;    // 申请时间
    
    private Date successTime;  // 购买成功时间
    
    private Date interestBeginDate; // 计息开始时间
    
    private Date paymentDate;    // 实际赎回时间（回款）

    public String getApplyTimeStart() {
        return applyTimeStart;
    }

    public void setApplyTimeStart(String applyTimeStart) {
        this.applyTimeStart = applyTimeStart;
    }

    public String getApplyTimeEnd() {
        return applyTimeEnd;
    }

    public void setApplyTimeEnd(String applyTimeEnd) {
        this.applyTimeEnd = applyTimeEnd;
    }

    public String getInterestBeginDateStart() {
        return interestBeginDateStart;
    }

    public void setInterestBeginDateStart(String interestBeginDateStart) {
        this.interestBeginDateStart = interestBeginDateStart;
    }

    public String getInterestBeginDateEnd() {
        return interestBeginDateEnd;
    }

    public void setInterestBeginDateEnd(String interestBeginDateEnd) {
        this.interestBeginDateEnd = interestBeginDateEnd;
    }

    public String getPaymentDateStart() {
        return paymentDateStart;
    }

    public void setPaymentDateStart(String paymentDateStart) {
        this.paymentDateStart = paymentDateStart;
    }

    public String getPaymentDateEnd() {
        return paymentDateEnd;
    }

    public void setPaymentDateEnd(String paymentDateEnd) {
        this.paymentDateEnd = paymentDateEnd;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Double getBaseRate() {
        return baseRate;
    }

    public void setBaseRate(Double baseRate) {
        this.baseRate = baseRate;
    }

    public Double getSubtract() {
        return subtract;
    }

    public void setSubtract(Double subtract) {
        this.subtract = subtract;
    }

    public Double getIncentiveRate() {
        return incentiveRate;
    }

    public void setIncentiveRate(Double incentiveRate) {
        this.incentiveRate = incentiveRate;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getRechargeAmount() {
        return rechargeAmount;
    }

    public void setRechargeAmount(Double rechargeAmount) {
        this.rechargeAmount = rechargeAmount;
    }

    public Double getAccumulationInerest() {
        return accumulationInerest;
    }

    public void setAccumulationInerest(Double accumulationInerest) {
        this.accumulationInerest = accumulationInerest;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Date getSuccessTime() {
        return successTime;
    }

    public void setSuccessTime(Date successTime) {
        this.successTime = successTime;
    }

    public Date getInterestBeginDate() {
        return interestBeginDate;
    }

    public void setInterestBeginDate(Date interestBeginDate) {
        this.interestBeginDate = interestBeginDate;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Integer getSubAccountId() {
        return subAccountId;
    }

    public void setSubAccountId(Integer subAccountId) {
        this.subAccountId = subAccountId;
    }

	public Double getOpenBalance() {
		return openBalance;
	}

	public void setOpenBalance(Double openBalance) {
		this.openBalance = openBalance;
	}

	public String getStatusStr() {
		return statusStr;
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}
    
}

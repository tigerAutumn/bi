package com.pinting.business.model.vo;

import java.util.Date;

public class FinancialSettlementVO extends PageInfoObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3218283910725010158L;
	
	
    // 请求条件 - START
    private String timeStart;
    
    private String timeEnd;
    
    private String amountStart;
    
    private String amountEnd;
    
    // 请求条件 - END
    
    private String managerName; // 客户经理
    
    private String deptName;    // 营业部
    
    private String productName;
    
    private Integer status; // 理财状态
    
    private String strStatus; //赞分期产品理财状态（借贷关系）
    
    private Date successTime; //划扣成功时间
    
    private Date interestBeginDate; //起息日期
    
    private String userName; //出借人
    
    private String mobile; //出借人手机

    private String idCard; //出借人身份证

    private String contractId; //合同编号（订单ID）
    
    private Double balance; //出借金额
    
    private Double  actualAmount; //真实出借金额（除掉红包）
    
    private Integer term; //期限
    
    private Date returnDay; //到期时间
    
    private Double baseRate; //利率
    
    private String deptCode;//部门编号
    

	public String getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(String timeStart) {
		this.timeStart = timeStart;
	}

	public String getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}

	
	public String getAmountStart() {
		return amountStart;
	}

	public void setAmountStart(String amountStart) {
		this.amountStart = amountStart;
	}

	public String getAmountEnd() {
		return amountEnd;
	}

	public void setAmountEnd(String amountEnd) {
		this.amountEnd = amountEnd;
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

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Double getActualAmount() {
		return actualAmount;
	}

	public void setActualAmount(Double actualAmount) {
		this.actualAmount = actualAmount;
	}

	public Integer getTerm() {
		return term;
	}

	public void setTerm(Integer term) {
		this.term = term;
	}

	public Date getReturnDay() {
		return returnDay;
	}

	public void setReturnDay(Date returnDay) {
		this.returnDay = returnDay;
	}

	public Double getBaseRate() {
		return baseRate;
	}

	public void setBaseRate(Double baseRate) {
		this.baseRate = baseRate;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getStrStatus() {
		return strStatus;
	}

	public void setStrStatus(String strStatus) {
		this.strStatus = strStatus;
	}
    
}

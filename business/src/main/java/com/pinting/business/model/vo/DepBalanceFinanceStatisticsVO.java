package com.pinting.business.model.vo;

public class DepBalanceFinanceStatisticsVO {
	private Integer rowno;		 			//查询结果序号

	private Integer userId; 				//用户id
	
	private String userName; 				//投资人用户姓名
	
	private String lnUserName;				//融资人用户姓名
	
	private String mobile; 					//用户手机号
	
	private String billNo; 					//单据编号
	
	private String fnCustomerCode; 			//投资客户代码
	
	private String lnCustomerCode; 			//融资客户代码
	
	private Double planPrincipal; 			//结算本金
	
	private Double loanInterest;  			//融资客户应付利息
	
	private Double planInterest;  			//应付投资客户利息
	
  	private Double planFee; 				//手续费
  	
	private String finishTime;				//还款日期/代偿日期
	
	private String repayNote; 				//备注是否逾期
	
	private Double totalPlanPrincipal;		//结算总本金
	
	private Double totalPlanInterest;		//应付投资人利息总和
	
	private Double totalPlanLoanInterest;	//融资人应付利息总和
	
	private Double totalPlanFee;			//总手续费
	
	private Integer totalCount;				//总记录数

	private String partnerBusinessFlag;     //借款产品标识
	
	public Integer getRowno() {
		return rowno;
	}

	public void setRowno(Integer rowno) {
		this.rowno = rowno;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getFnCustomerCode() {
		return fnCustomerCode;
	}

	public void setFnCustomerCode(String fnCustomerCode) {
		this.fnCustomerCode = fnCustomerCode;
	}

	public String getLnCustomerCode() {
		return lnCustomerCode;
	}

	public void setLnCustomerCode(String lnCustomerCode) {
		this.lnCustomerCode = lnCustomerCode;
	}

	public Double getPlanPrincipal() {
		return planPrincipal;
	}

	public void setPlanPrincipal(Double planPrincipal) {
		this.planPrincipal = planPrincipal;
	}

	public Double getLoanInterest() {
		return loanInterest;
	}

	public void setLoanInterest(Double loanInterest) {
		this.loanInterest = loanInterest;
	}

	public Double getPlanInterest() {
		return planInterest;
	}

	public void setPlanInterest(Double planInterest) {
		this.planInterest = planInterest;
	}

	public Double getPlanFee() {
		return planFee;
	}

	public void setPlanFee(Double planFee) {
		this.planFee = planFee;
	}

	public String getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}

	public String getRepayNote() {
		return repayNote;
	}

	public void setRepayNote(String repayNote) {
		this.repayNote = repayNote;
	}

	public Double getTotalPlanPrincipal() {
		return totalPlanPrincipal;
	}

	public void setTotalPlanPrincipal(Double totalPlanPrincipal) {
		this.totalPlanPrincipal = totalPlanPrincipal;
	}

	public Double getTotalPlanInterest() {
		return totalPlanInterest;
	}

	public void setTotalPlanInterest(Double totalPlanInterest) {
		this.totalPlanInterest = totalPlanInterest;
	}

	public Double getTotalPlanLoanInterest() {
		return totalPlanLoanInterest;
	}

	public void setTotalPlanLoanInterest(Double totalPlanLoanInterest) {
		this.totalPlanLoanInterest = totalPlanLoanInterest;
	}

	public Double getTotalPlanFee() {
		return totalPlanFee;
	}

	public void setTotalPlanFee(Double totalPlanFee) {
		this.totalPlanFee = totalPlanFee;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public String getLnUserName() {
		return lnUserName;
	}

	public void setLnUserName(String lnUserName) {
		this.lnUserName = lnUserName;
	}

	public String getPartnerBusinessFlag() {
		return partnerBusinessFlag;
	}

	public void setPartnerBusinessFlag(String partnerBusinessFlag) {
		this.partnerBusinessFlag = partnerBusinessFlag;
	}
}
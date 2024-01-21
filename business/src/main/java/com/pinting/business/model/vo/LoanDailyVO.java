package com.pinting.business.model.vo;

import com.pinting.core.util.DateUtil;

import java.util.Date;

/**
 * Author:      shh
 * Date:        2017/10/11
 * Description: 放款日常管理
 */
public class LoanDailyVO extends PageInfoObject {

    /* 合作方编码 */
    private String partnerCode;

    /* 资产端借款编号 */
    private String partnerLoanId;

    /* 标的编号 */
    private Integer depositionId;

    /* 申请日期 */
    private Date createTime;

    /* 放款日期 */
    private Date loanTime;

    /* 借款人姓名 */
    private String userName;

    /* 借款金额 */
    private Double amount;

    /* 砍头息 */
    private Double headFee;

    /* 放款金额 */
    private Double approveAmount;

    /* 借款期数 */
    private Integer period;

    /* 银行名称 */
    private String bankName;

    /* 身份证号 */
    private String idCard;

    /* 银行卡号 */
    private String bankCardNo;

    /* 订单号 */
    private String orderNo;

    /* 订单状态 */
    private Integer orderStatus;

    /* 订单返回码 */
    private String returnCode;

    /* 订单返回信息 */
    private String returnMsg;

    /* 借款用户id */
    private Integer lnUserId;

    /* 预留手机号 */
    private String mobile;

    /* 借款状态 */
    private String loanStatus;
    
    /* 借款利率 */
    private String agreementRate;

    private String startTime;

    private String endTime;

    /* 借款编号 */
    private Integer loanId;

    /* 合作方还款编号 */
    private String partnerRepayId;

    /* 标的状态 */
    private String depositionStatus;

    /* 标的状态更新时间 */
    private Date depositionUpdateTime;

    /* 是否支持债券回退 */
    private Boolean isSupportBackLoanDebtFinancing;

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public String getPartnerLoanId() {
        return partnerLoanId;
    }

    public void setPartnerLoanId(String partnerLoanId) {
        this.partnerLoanId = partnerLoanId;
    }

    public Integer getDepositionId() {
        return depositionId;
    }

    public void setDepositionId(Integer depositionId) {
        this.depositionId = depositionId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLoanTime() {
        return loanTime;
    }

    public void setLoanTime(Date loanTime) {
        this.loanTime = loanTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getHeadFee() {
        return headFee;
    }

    public void setHeadFee(Double headFee) {
        this.headFee = headFee;
    }

    public Double getApproveAmount() {
        return approveAmount;
    }

    public void setApproveAmount(Double approveAmount) {
        this.approveAmount = approveAmount;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public Integer getLnUserId() {
        return lnUserId;
    }

    public void setLnUserId(Integer lnUserId) {
        this.lnUserId = lnUserId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLoanStatus() {
        return loanStatus;
    }

    public void setLoanStatus(String loanStatus) {
        this.loanStatus = loanStatus;
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

	public String getAgreementRate() {
		return agreementRate;
	}

	public void setAgreementRate(String agreementRate) {
		this.agreementRate = agreementRate;
	}

	public Integer getLoanId() {
		return loanId;
	}

	public void setLoanId(Integer loanId) {
		this.loanId = loanId;
	}

    public String getPartnerRepayId() {
        return partnerRepayId;
    }

    public void setPartnerRepayId(String partnerRepayId) {
        this.partnerRepayId = partnerRepayId;
    }

    public String getDepositionStatus() {
        return depositionStatus;
    }

    public void setDepositionStatus(String depositionStatus) {
        this.depositionStatus = depositionStatus;
    }

    public Date getDepositionUpdateTime() {
        return depositionUpdateTime;
    }

    public void setDepositionUpdateTime(Date depositionUpdateTime) {
        this.depositionUpdateTime = depositionUpdateTime;
    }

    public Boolean getSupportBackLoanDebtFinancing() {
        return isSupportBackLoanDebtFinancing;
    }

    public void setSupportBackLoanDebtFinancing(Boolean supportBackLoanDebtFinancing) {
        isSupportBackLoanDebtFinancing = supportBackLoanDebtFinancing;
    }
}

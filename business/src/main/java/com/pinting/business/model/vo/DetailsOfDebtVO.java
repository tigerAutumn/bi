package com.pinting.business.model.vo;

import java.util.Date;

/**
 * 存管产品 债权明细相关VO
 * Created by shh on 2016/12/27 21:40.
 */
public class DetailsOfDebtVO {

    /* 借款人 */
    private String loanName;

    /* 出借金额/初始本金/针对云贷自主放款 */
    private Double initAmount;

    /* 剩余本金 */
    private Double leftAmount;

    /* 还款状态 */
    private String status;

    /* 借款时间 */
    private Date createTime;

    /* 最近还款时间 */
    private Date updateTime;

    /* 债转标记 */
    private String transMark;

    /* 起息时间 */
    private Date interestBeginDate;

    /* 锁定期 */
    private Integer lockTerm;

    /* 计划名称 */
    private String productName;

    /* 加入时间 */
    private Date openTime;

    /* 加入金额 */
    private Double balance;

    /* 申请退出金额 */
    private Double exitAmount;

    /* 退出费用 */
    private Double exitCost;

    /* 借贷关系表id */
    private Integer lnLoanRelationId;

    /* 借款表id */
    private Integer loanId;

    private Double openBalance;

    /* 借款成功的时间 */
    private Date loanTime;

    /* 债转发生的时间 */
    private Date transferTime;

    /* 借款人身份证号 */
    private String idCard;

    // 7贷随借随还产品新增业务标识
    private String partnerBusinessFlag;
    
    // 产品类型
    private String productType;
    // 备注
    private String note;
    // 资产方
    private String partnerCode;
    
    public String getLoanName() {
        return loanName;
    }

    public void setLoanName(String loanName) {
        this.loanName = loanName;
    }

    public Double getInitAmount() {
        return initAmount;
    }

    public void setInitAmount(Double initAmount) {
        this.initAmount = initAmount;
    }

    public Double getLeftAmount() {
        return leftAmount;
    }

    public void setLeftAmount(Double leftAmount) {
        this.leftAmount = leftAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getTransMark() {
        return transMark;
    }

    public void setTransMark(String transMark) {
        this.transMark = transMark;
    }

    public Date getInterestBeginDate() {
        return interestBeginDate;
    }

    public void setInterestBeginDate(Date interestBeginDate) {
        this.interestBeginDate = interestBeginDate;
    }

    public Integer getLockTerm() {
        return lockTerm;
    }

    public void setLockTerm(Integer lockTerm) {
        this.lockTerm = lockTerm;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Date getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Date openTime) {
        this.openTime = openTime;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getExitAmount() {
        return exitAmount;
    }

    public void setExitAmount(Double exitAmount) {
        this.exitAmount = exitAmount;
    }

    public Double getExitCost() {
        return exitCost;
    }

    public void setExitCost(Double exitCost) {
        this.exitCost = exitCost;
    }

    public Integer getLnLoanRelationId() {
        return lnLoanRelationId;
    }

    public void setLnLoanRelationId(Integer lnLoanRelationId) {
        this.lnLoanRelationId = lnLoanRelationId;
    }

    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
    }

    public Double getOpenBalance() {
        return openBalance;
    }

    public void setOpenBalance(Double openBalance) {
        this.openBalance = openBalance;
    }

    public Date getLoanTime() {
        return loanTime;
    }

    public void setLoanTime(Date loanTime) {
        this.loanTime = loanTime;
    }

    public Date getTransferTime() {
        return transferTime;
    }

    public void setTransferTime(Date transferTime) {
        this.transferTime = transferTime;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

	public String getPartnerBusinessFlag() {
		return partnerBusinessFlag;
	}

	public void setPartnerBusinessFlag(String partnerBusinessFlag) {
		this.partnerBusinessFlag = partnerBusinessFlag;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getPartnerCode() {
		return partnerCode;
	}

	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
	}
   
}

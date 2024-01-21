package com.pinting.business.hessian.site.message;


import java.util.Date;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 根据主键id查询subAccount的出参
 * @author bianyatian
 * @2015-12-10 下午4:08:04
 */
public class ResMsg_Account_SubAccountById extends ResMsg {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1272511771943026377L;
	/*用户子账户主键id*/
 	private Integer id;
 	/*用户账户id*/
    private Integer accountId;
    /*子账户编码*/
    private String code;
    /*产品编号*/
    private Integer productId;
    /*产品类型*/
    private String productType;
    /*产品代码*/
    private String productCode;
    /*产品名称*/
    private String productName;
    /*产品利率*/
    private Double productRate;
    /*关联银行卡号*/
    private String bankCard;
    /*额外利率*/
    private Double extraRate;
    /*开户余额*/
    private Double openBalance;
    /*余额*/
    private Double balance;
    /*可用余额*/
    private Double availableBalance;
    /*可提现余额*/
    private Double canWithdraw;
    /*冻结余额*/
    private Double freezeBalance;
    /*可转状态*/
    private Integer transStatus;
    /*账户状态*/
    private Integer status;
    /*对账状态*/
    private String checkStatus;
    /*起息日期*/
    private Date interestBeginDate;
    /*上次交易时间*/
    private Date lastTransDate;
    /*上次计息日期*/
    private Date lastCalInterestDate;
    /*上次结息日期*/
    private Date lastFinishInterestDate;
    /*累计利息*/
    private Double accumulationInerest;
    /*开始时间*/
    private Date openTime;
    /*销户时间*/
    private Date closeTime;
    /*转让时间*/
    private Date transferTime;
    /*备注*/
    private String note;
    /*用户id*/
    private Integer userId;
	/*赞分期新旧协议时间区分标志*/
	private boolean zanAgreementDate;
    
    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Double getProductRate() {
		return productRate;
	}

	public void setProductRate(Double productRate) {
		this.productRate = productRate;
	}

	public String getBankCard() {
		return bankCard;
	}

	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}

	public Double getExtraRate() {
		return extraRate;
	}

	public void setExtraRate(Double extraRate) {
		this.extraRate = extraRate;
	}

	public Double getOpenBalance() {
		return openBalance;
	}

	public void setOpenBalance(Double openBalance) {
		this.openBalance = openBalance;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Double getAvailableBalance() {
		return availableBalance;
	}

	public void setAvailableBalance(Double availableBalance) {
		this.availableBalance = availableBalance;
	}

	public Double getCanWithdraw() {
		return canWithdraw;
	}

	public void setCanWithdraw(Double canWithdraw) {
		this.canWithdraw = canWithdraw;
	}

	public Double getFreezeBalance() {
		return freezeBalance;
	}

	public void setFreezeBalance(Double freezeBalance) {
		this.freezeBalance = freezeBalance;
	}

	public Integer getTransStatus() {
		return transStatus;
	}

	public void setTransStatus(Integer transStatus) {
		this.transStatus = transStatus;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}

	public Date getInterestBeginDate() {
		return interestBeginDate;
	}

	public void setInterestBeginDate(Date interestBeginDate) {
		this.interestBeginDate = interestBeginDate;
	}

	public Date getLastTransDate() {
		return lastTransDate;
	}

	public void setLastTransDate(Date lastTransDate) {
		this.lastTransDate = lastTransDate;
	}

	public Date getLastCalInterestDate() {
		return lastCalInterestDate;
	}

	public void setLastCalInterestDate(Date lastCalInterestDate) {
		this.lastCalInterestDate = lastCalInterestDate;
	}

	public Date getLastFinishInterestDate() {
		return lastFinishInterestDate;
	}

	public void setLastFinishInterestDate(Date lastFinishInterestDate) {
		this.lastFinishInterestDate = lastFinishInterestDate;
	}

	public Double getAccumulationInerest() {
		return accumulationInerest;
	}

	public void setAccumulationInerest(Double accumulationInerest) {
		this.accumulationInerest = accumulationInerest;
	}

	public Date getOpenTime() {
		return openTime;
	}

	public void setOpenTime(Date openTime) {
		this.openTime = openTime;
	}

	public Date getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
	}

	public Date getTransferTime() {
		return transferTime;
	}

	public void setTransferTime(Date transferTime) {
		this.transferTime = transferTime;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public boolean isZanAgreementDate() {
		return zanAgreementDate;
	}

	public void setZanAgreementDate(boolean zanAgreementDate) {
		this.zanAgreementDate = zanAgreementDate;
	}
}

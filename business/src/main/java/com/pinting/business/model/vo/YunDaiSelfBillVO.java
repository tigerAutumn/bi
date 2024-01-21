package com.pinting.business.model.vo;

import java.util.Date;
public class YunDaiSelfBillVO {
	private	Integer loanId;
	
    private Integer lnUserId;

    private String userName;
    
    private String partnerUserId;

    private String partnerLoanId;

    private Double applyAmount;

    private String payOrderNo;

    private Integer period;

    private String partnerOrderNo;

    private Date createTime;

    private Date updateTime;

	public Integer getLoanId() {
		return loanId;
	}

	public void setLoanId(Integer loanId) {
		this.loanId = loanId;
	}

	public Integer getLnUserId() {
		return lnUserId;
	}

	public void setLnUserId(Integer lnUserId) {
		this.lnUserId = lnUserId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	

	public String getPartnerUserId() {
		return partnerUserId;
	}

	public void setPartnerUserId(String partnerUserId) {
		this.partnerUserId = partnerUserId;
	}

	public String getPartnerLoanId() {
		return partnerLoanId;
	}

	public void setPartnerLoanId(String partnerLoanId) {
		this.partnerLoanId = partnerLoanId;
	}

	public Double getApplyAmount() {
		return applyAmount;
	}

	public void setApplyAmount(Double applyAmount) {
		this.applyAmount = applyAmount;
	}

	public String getPayOrderNo() {
		return payOrderNo;
	}

	public void setPayOrderNo(String payOrderNo) {
		this.payOrderNo = payOrderNo;
	}

	public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}

	public String getPartnerOrderNo() {
		return partnerOrderNo;
	}

	public void setPartnerOrderNo(String partnerOrderNo) {
		this.partnerOrderNo = partnerOrderNo;
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
    
    
}

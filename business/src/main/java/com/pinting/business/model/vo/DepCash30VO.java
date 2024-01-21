package com.pinting.business.model.vo;

import java.util.Date;

/**
 * 云贷存管未来30天兑付查询
 * @project business
 * @title DepCash30.java
 * @author Dragon & cat
 * @date 2017-10-17
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
public class DepCash30VO {
	/*兑付时间*/
	private		Date 		finishDate;
	/*预计退出债权本金*/
	private		Double  	quitPrincipalBalance;
	/*预计退出债权利息*/
	private		Double  	quitInterestBalance;
	/*预计前日还款资金*/
	private		Double  	beferDayRepayBalance;
	/*当前VIP持有债权本金*/
	private		Double  	vipAmount;
	/*预计VIP转让债权利息*/
	private		Double  	vipTransferInterest;
	
	private		String      partnerCode;
	public Date getFinishDate() {
		return finishDate;
	}
	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}
	public Double getQuitPrincipalBalance() {
		return quitPrincipalBalance;
	}
	public void setQuitPrincipalBalance(Double quitPrincipalBalance) {
		this.quitPrincipalBalance = quitPrincipalBalance;
	}
	public Double getQuitInterestBalance() {
		return quitInterestBalance;
	}
	public void setQuitInterestBalance(Double quitInterestBalance) {
		this.quitInterestBalance = quitInterestBalance;
	}
	public Double getBeferDayRepayBalance() {
		return beferDayRepayBalance;
	}
	public void setBeferDayRepayBalance(Double beferDayRepayBalance) {
		this.beferDayRepayBalance = beferDayRepayBalance;
	}
	
	public Double getVipAmount() {
		return vipAmount;
	}
	public void setVipAmount(Double vipAmount) {
		this.vipAmount = vipAmount;
	}
	public Double getVipTransferInterest() {
		return vipTransferInterest;
	}
	public void setVipTransferInterest(Double vipTransferInterest) {
		this.vipTransferInterest = vipTransferInterest;
	}
	public String getPartnerCode() {
		return partnerCode;
	}
	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
	}
	
	
}

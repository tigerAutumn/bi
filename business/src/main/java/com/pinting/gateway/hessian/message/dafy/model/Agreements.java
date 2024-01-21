package com.pinting.gateway.hessian.message.dafy.model;

import java.io.Serializable;
import java.util.List;

/**
 * 协议下载通知-一级循环对象
 * @author bianyatian
 * @2016-12-15 上午10:43:49
 */
public class Agreements implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6421232958532225295L;

	private String loanId;	//借款编号
	
	private String debtTransNoticesUrl;	//债权转让通知书下载地址
	
	private	String  serviceFeeConfirmUrl; //代偿收款确认函(服务费)下载地址-->收款确认函(服务费)下载地址
	
	private	String  debtTransConfirmUrl; //代偿收款确认函(债转)下载地址-->收款确认函(担保)下载地址
		 
	private	String	borrowerName; //借款人姓名
	
	private	String	borrowerIdCard; //借款人身份证号码

	private Integer peroid; // 剩余期数

	private Long loanServiceFee; // 借款服务费
	
	private List<DebtTransferInfo> debtTransferInfo; //二级循环

	private	List<DebtTransfers>	debtTransfers; //二级循环

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

	public String getDebtTransNoticesUrl() {
		return debtTransNoticesUrl;
	}

	public void setDebtTransNoticesUrl(String debtTransNoticesUrl) {
		this.debtTransNoticesUrl = debtTransNoticesUrl;
	}


	public List<DebtTransferInfo> getDebtTransferInfo() {
		return debtTransferInfo;
	}

	public void setDebtTransferInfo(List<DebtTransferInfo> debtTransferInfo) {
		this.debtTransferInfo = debtTransferInfo;
	}

	public String getBorrowerName() {
		return borrowerName;
	}

	public void setBorrowerName(String borrowerName) {
		this.borrowerName = borrowerName;
	}

	public String getBorrowerIdCard() {
		return borrowerIdCard;
	}

	public void setBorrowerIdCard(String borrowerIdCard) {
		this.borrowerIdCard = borrowerIdCard;
	}

	public List<DebtTransfers> getDebtTransfers() {
		return debtTransfers;
	}

	public void setDebtTransfers(List<DebtTransfers> debtTransfers) {
		this.debtTransfers = debtTransfers;
	}

	public String getServiceFeeConfirmUrl() {
		return serviceFeeConfirmUrl;
	}

	public void setServiceFeeConfirmUrl(String serviceFeeConfirmUrl) {
		this.serviceFeeConfirmUrl = serviceFeeConfirmUrl;
	}

	public String getDebtTransConfirmUrl() {
		return debtTransConfirmUrl;
	}

	public void setDebtTransConfirmUrl(String debtTransConfirmUrl) {
		this.debtTransConfirmUrl = debtTransConfirmUrl;
	}

	public Integer getPeroid() {
		return peroid;
	}

	public void setPeroid(Integer peroid) {
		this.peroid = peroid;
	}

	public Long getLoanServiceFee() {
		return loanServiceFee;
	}

	public void setLoanServiceFee(Long loanServiceFee) {
		this.loanServiceFee = loanServiceFee;
	}
}

package com.pinting.gateway.loan7.in.model;

import java.util.List;

import com.pinting.gateway.hessian.message.dafy.model.DebtTransferInfo;
import com.pinting.gateway.hessian.message.dafy.model.DebtTransfers;

public class AgreementNoticeResModel extends BaseResModel{
	
	/*借款编号*/
	private			String		loanId;
	/*债权转让通知书下载地址*/
	private			String		debtTransNoticesUrl;
	/*代偿收款确认函(服务费)下载地址*/
	private			String		serviceFeeConfirmUrl;
	/*代偿收款确认函(债转)下载地址*/
	private			String		debtTransConfirmUrl;
	/*借款人姓名*/
	private			String		borrowerName;
	/*借款人身份证号码*/
	private			String		borrowerIdCard;
	/*债权转让信息*/
	private			List<DebtTransferInfo>	debtTransferInfo;
	/*债权转让协议下载地址*/
	private			List<DebtTransfers>	debtTransfers;
	
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
	public List<DebtTransferInfo> getDebtTransferInfo() {
		return debtTransferInfo;
	}
	public void setDebtTransferInfo(List<DebtTransferInfo> debtTransferInfo) {
		this.debtTransferInfo = debtTransferInfo;
	}
	public List<DebtTransfers> getDebtTransfers() {
		return debtTransfers;
	}
	public void setDebtTransfers(List<DebtTransfers> debtTransfers) {
		this.debtTransfers = debtTransfers;
	}
	
}

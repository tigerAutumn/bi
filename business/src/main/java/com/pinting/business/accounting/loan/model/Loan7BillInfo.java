package com.pinting.business.accounting.loan.model;

import com.pinting.business.accounting.loan.enums.LoanStatus;
import com.pinting.gateway.hessian.message.loan7.model.Loan7QueryBillRepayment;

import java.util.List;

public class Loan7BillInfo {

	/*合作方用户编号*/
	private 	String partnerUserId;
	/*合作方借款编号*/
	private 	String	partnerLoanId;
	/*借款协议编号*/
	private		String	agreementNo;
	/*借款协议下载地址*/
	private		String	agreementUrl;
	/*列表*/
	private		List<Loan7QueryBillRepayment> 	repayments;

	/*合作方编号*/
	private String partnerCode;
	
	public static enum BillStatus{
		//接口中账单状态
		BILL_STATUS_INIT("INIT","未还款", LoanStatus.REPAY_SCHEDULE_STATUS_INIT),
		BILL_STATUS_REPAYING("REPAYING","还款中", LoanStatus.REPAY_SCHEDULE_STATUS_INIT),
		BILL_STATUS_LATE_NOT("LATE_NOT","逾期未还款", LoanStatus.REPAY_SCHEDULE_STATUS_INIT),
		BILL_STATUS_REPAIED("REPAIED","已还款", LoanStatus.REPAY_SCHEDULE_STATUS_REPAIED),
		BILL_STATUS_CANCELLED("CANCELLED", "废除", LoanStatus.REPAY_SCHEDULE_STATUS_CANCELLED);

		/** code */
		private String code;

		/** description */
		private String description;

		private LoanStatus loanStatus;

		/**
		 * 私有的构造方法
		 * @param code
		 * @param description
		 */
		BillStatus(String code, String description, LoanStatus loanStatus) {
			this.code = code;
			this.description = description;
			this.loanStatus = loanStatus;
		}

		public static LoanStatus getLoanStatusByCode(String code) {
			if (null == code) {
				return null;
			}
			for (BillStatus status : values()) {
				if (status.getCode().equals(code.trim()))
					return status.getLoanStatus();
			}
			return null;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public LoanStatus getLoanStatus() {
			return loanStatus;
		}

		public void setLoanStatus(LoanStatus loanStatus) {
			this.loanStatus = loanStatus;
		}
	}

	public String getAgreementNo() {
		return agreementNo;
	}

	public void setAgreementNo(String agreementNo) {
		this.agreementNo = agreementNo;
	}

	public String getAgreementUrl() {
		return agreementUrl;
	}

	public void setAgreementUrl(String agreementUrl) {
		this.agreementUrl = agreementUrl;
	}

	public List<Loan7QueryBillRepayment> getRepayments() {
		return repayments;
	}

	public void setRepayments(List<Loan7QueryBillRepayment> repayments) {
		this.repayments = repayments;
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

	public String getPartnerCode() {
		return partnerCode;
	}

	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
	}
	
}

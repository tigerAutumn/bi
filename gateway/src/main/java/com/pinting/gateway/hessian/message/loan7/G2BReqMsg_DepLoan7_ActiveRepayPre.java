package com.pinting.gateway.hessian.message.loan7;

import java.util.ArrayList;
import java.util.HashMap;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.pinting.core.hessian.msg.ReqMsg;
/**
 * 
 * @project gateway
 * @title G2BReqMsg_DepLoan7_ActiveRepayPre.java
 * @author Dragon & cat
 * @date 2017-12-12
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
public class G2BReqMsg_DepLoan7_ActiveRepayPre extends ReqMsg {
		/**
	 * 
	 */
	private static final long serialVersionUID = 5922891737546270394L;
		/*还款单号	*/
		@NotEmpty(message="orderNo为空")
		private			String		orderNo;
		/*用户编号*/
		@NotEmpty(message="userId为空")
		private			String		userId;
		/*姓名*/
		@NotEmpty(message="name为空")
		private			String		name;
		/*身份证*/
		@NotEmpty(message="idCard为空")
		private			String		idCard;
		/*预留手机号*/
		@NotEmpty(message="mobile为空")
		private			String		mobile;
		/*卡号*/
		@NotEmpty(message="bankCard为空")
		private			String		bankCard;
		/*银行编码*/
		@NotEmpty(message="bankCode为空")
		private			String		bankCode;
		/*借款编号*/
		@NotEmpty(message="loanId为空")
		private			String		loanId;
		/*还款金额*/
		@NotNull(message="totalAmount为空")
		private			Double		totalAmount;
		/*还款账单列表*/
		private			ArrayList<HashMap<String, Object>>	repayments;
		public String getOrderNo() {
			return orderNo;
		}
		public void setOrderNo(String orderNo) {
			this.orderNo = orderNo;
		}
		public String getUserId() {
			return userId;
		}
		public void setUserId(String userId) {
			this.userId = userId;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getIdCard() {
			return idCard;
		}
		public void setIdCard(String idCard) {
			this.idCard = idCard;
		}
		public String getMobile() {
			return mobile;
		}
		public void setMobile(String mobile) {
			this.mobile = mobile;
		}
		public String getBankCard() {
			return bankCard;
		}
		public void setBankCard(String bankCard) {
			this.bankCard = bankCard;
		}
		public String getBankCode() {
			return bankCode;
		}
		public void setBankCode(String bankCode) {
			this.bankCode = bankCode;
		}
		public String getLoanId() {
			return loanId;
		}
		public void setLoanId(String loanId) {
			this.loanId = loanId;
		}
		public Double getTotalAmount() {
			return totalAmount;
		}
		public void setTotalAmount(Double totalAmount) {
			this.totalAmount = totalAmount;
		}
		public ArrayList<HashMap<String, Object>> getRepayments() {
			return repayments;
		}
		public void setRepayments(ArrayList<HashMap<String, Object>> repayments) {
			this.repayments = repayments;
		}
		
}

package com.pinting.gateway.dafy.in.model;

import java.util.List;

/**
 * 自主放款-主动还款预下单
 * @author Dragon & cat
 * @date 2016-11-29
 */
public class ActiveRepayPreReqModel extends BaseReqModel {
	
		/*客户端标识*/
		private			String 		clientKey;
		/*还款单号	*/
		private			String		orderNo;
		/*用户编号*/
		private			String		userId;
		/*姓名*/
		private			String		name;
		/*身份证*/
		private			String		idCard;
		/*预留手机号*/
		private			String		mobile;
		/*卡号*/
		private			String		bankCard;
		/*银行编码*/
		private			String		bankCode;
		/*借款编号*/
		private			String		loanId;
		/*还款金额*/
		private			Long		totalAmount;
		/*还款账单列表*/
		private			List<Repayment>	repayments;
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
		public Long getTotalAmount() {
			return totalAmount;
		}
		public void setTotalAmount(Long totalAmount) {
			this.totalAmount = totalAmount;
		}
		public List<Repayment> getRepayments() {
			return repayments;
		}
		public void setRepayments(List<Repayment> repayments) {
			this.repayments = repayments;
		}
		public String getClientKey() {
			return clientKey;
		}
		public void setClientKey(String clientKey) {
			this.clientKey = clientKey;
		}
		
		
}

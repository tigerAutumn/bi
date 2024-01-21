package com.pinting.gateway.hessian.message.loan7.model;
/**
 * 
 * 出借人信息
 * @author Dragon & cat
 * @date 2016-12-14
 */
public class Lenders {
		/*出借人姓名*/
		private			String		lenderName;
		/*出借人身份证*/
		private			String		lenderIdcard;
		/*投标金额*/
		private			Long		investAmount;
		/*出借人手机号*/
		private 		String		mobile;

		public String getLenderName() {
			return lenderName;
		}

		public void setLenderName(String lenderName) {
			this.lenderName = lenderName;
		}

		public String getLenderIdcard() {
			return lenderIdcard;
		}

		public void setLenderIdcard(String lenderIdcard) {
			this.lenderIdcard = lenderIdcard;
		}

		public Long getInvestAmount() {
			return investAmount;
		}

		public void setInvestAmount(Long investAmount) {
			this.investAmount = investAmount;
		}

		public String getMobile() {
			return mobile;
		}

		public void setMobile(String mobile) {
			this.mobile = mobile;
		}
		
}

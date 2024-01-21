package com.pinting.gateway.dafy.in.model;

/**
 * 自主放款-主动还款确认下单
 * @author Dragon & cat
 * @date 2016-11-29
 */
public class ActiveRepayConfirmReqModel extends BaseReqModel {
		/*客户端标识*/
		private		String 		clientKey;
		/*支付单号*/
		private		String		bgwOrderNo;
		/*短信验证码*/
		private		String		smsCode;

		public String getBgwOrderNo() {
			return bgwOrderNo;
		}

		public void setBgwOrderNo(String bgwOrderNo) {
			this.bgwOrderNo = bgwOrderNo;
		}

		public String getSmsCode() {
			return smsCode;
		}

		public void setSmsCode(String smsCode) {
			this.smsCode = smsCode;
		}

		public String getClientKey() {
			return clientKey;
		}

		public void setClientKey(String clientKey) {
			this.clientKey = clientKey;
		}
		
		
}

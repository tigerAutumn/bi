package com.pinting.gateway.dafy.in.model;

/**
 * 自主放款-主动还款预下单
 * @author Dragon & cat
 * @date 2016-11-29
 */
public class ActiveRepayPreResModel extends BaseResModel {
		/*支付单号*/
		private			String		bgwOrderNo;

		public String getBgwOrderNo() {
			return bgwOrderNo;
		}

		public void setBgwOrderNo(String bgwOrderNo) {
			this.bgwOrderNo = bgwOrderNo;
		}
		
}

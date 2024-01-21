package com.pinting.gateway.zsd.in.model;

/**
 * 还款预下单服务
 * @author SHENGUOPING
 * @date  2017年8月30日 下午5:18:34
 */
public class ZsdRepayPreRes extends BaseResModel {
	
	/**
	 * 本次支付的交易号
	 */
	private String bgwOrderNo;

	public String getBgwOrderNo() {
		return bgwOrderNo;
	}

	public void setBgwOrderNo(String bgwOrderNo) {
		this.bgwOrderNo = bgwOrderNo;
	}
	
}

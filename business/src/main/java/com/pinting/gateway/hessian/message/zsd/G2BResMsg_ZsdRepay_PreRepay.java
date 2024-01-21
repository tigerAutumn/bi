package com.pinting.gateway.hessian.message.zsd;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 还款预下单服务
 * @author SHENGUOPING
 * @date  2017年8月30日 下午5:56:50
 */
public class G2BResMsg_ZsdRepay_PreRepay extends ResMsg {

	private static final long serialVersionUID = -5731991162942011393L;
	
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

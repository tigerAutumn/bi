package com.pinting.gateway.hfbank.out.model;

/**
 * Author	:	YED 
 * Date		:	2017/4/15
 * Description	:	平台提现 
 */
public class PlatWithDrawReqModel extends BaseReqModel {
	/* 金额 */
	private String amount;
	
	/* 异步通知地址 */
	private String notify_url;
	
	/* 备注 */
	private String remark;

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}

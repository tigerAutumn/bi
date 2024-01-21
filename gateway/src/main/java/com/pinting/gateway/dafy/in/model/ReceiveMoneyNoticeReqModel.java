package com.pinting.gateway.dafy.in.model;

import java.util.List;

import com.pinting.gateway.hessian.message.dafy.model.ReceiveMoneyDetail;

/**
 * 理财产品到期回款通知
 * @Project: gateway
 * @Title: ReceiveMoneyReqModel.java
 * @author Zhou Changzai
 * @date 2015-4-1 下午5:45:40
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class ReceiveMoneyNoticeReqModel extends BaseReqModel {
	List<ReceiveMoneyDetail> data;//一批到期信息

	public List<ReceiveMoneyDetail> getData() {
		return data;
	}

	public void setData(List<ReceiveMoneyDetail> data) {
		this.data = data;
	}
}
